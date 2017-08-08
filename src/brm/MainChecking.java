package brm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import ICPException.CheckBRMFromEAIID;

public class MainChecking {

	

	
 	private String dbasePass = "EAI_READ";
    private String dbaseUser = "EAI_READ";
    private String dbaseIP = "10.41.33.110";
    
    private Connection getDBConn(String dbLogin, String dbPassword, String dbIp) throws ClassNotFoundException
    {
    	Class.forName ("oracle.jdbc.OracleDriver");
        Connection con;
        
        try
        {
        	System.out.println("1==============================================");
        	
        	//dua2 ni sepatutnye boleh
        	//con = DriverManager.getConnection("jdbc:oracle:thin:@//10.41.33.110:1521/NEAIPRD" , "EAI_READ", "EAI_READ");
        	
        	con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + dbIp + ")(PORT=1521))(CONNECT_DATA=(SERVICE_NAME = NEAIPRD)))", dbLogin, dbPassword);
        	
        	//----------
         }
        catch(Exception e)
        {
            System.out.println("1Exception ->  " + e.getMessage());
            return null;
        }
        return con;
    }

    
    
    public static void main( String[] args ) throws Exception 
    {
    	
    	MainChecking mc = new MainChecking();
    	
    	//String EAI_ID = "NULL";
    	
    	//EAI_ID = t.CheckExceptionFromSiebelID("1-5EKQTXN");
    	DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    	Date date = new Date();
    	System.out.println(dateFormat.format(date));
    	
    	//System.out.println(")----> " + EAI_ID);
    	//System.out.println(")----> " + t.getOsm_id_return());
    	
		String siebelOrderID = "1-5EKQTXN";
		String EAIId = "Null";
		String resultReturnToJSP = "Null";
		String systemOwner = "Null";
		String osmID = "Null";
		String BRM_remarks = "Null";


			
			System.out.println("siebelOrderID===." + siebelOrderID);
			
			
			
			System.out.println("siebelOrderID===1");
			
			EAIId = mc.CheckExceptionFromSiebelID(siebelOrderID);
			
			System.out.println("siebelOrderID===2==" + mc.getOsm_id_return());
			
			osmID = mc.getOsm_id_return();
			
			System.out.println("EAI ID recieve = " + EAIId);
			
			CheckBRMFromEAIID chBRM = new CheckBRMFromEAIID();
			
			resultReturnToJSP = chBRM.checkEAIOPcodeSentDetail(EAIId);
			
			System.out.println("=============Result Final===========");
			System.out.println(resultReturnToJSP);
    	
    }
    
    
    
    public String CheckExceptionFromSiebelID(String orderId) throws Exception
    {

    	
    	//MainChecking g = new MainChecking();
    	//String orderId = "1-" + "5525RWB";
    	//String osmId = "";
    	
    	String resultReturn = "Null";
    	
    	
		String int_msg_id = "Null";
		String event_name = "Null";
		String audit_date_time = "Null";
		String audit_type = "Null";
		String payload = "Null";
		
		String result = "Null";
		String eaiIDOnly = "Null";
		
		String OSM_ID = "Null";
		
		
		System.out.println(")----> " + orderId);
		
		
		Vector eaiResult = new Vector();
		
		if(orderId.equalsIgnoreCase("xxxx"))
		{
			result = "Null|Null|Null|Null|Null|Null";
			
			eaiIDOnly = "Null";
		}
		else
		{	
		eaiResult = this.checkEAITable(orderId);
		
		
		if(eaiResult.size() > 0)
		{
			//System.out.println("================================1======================================");
	    	for(int y=0;y<eaiResult.size();y++)
	    	{
	    		System.out.println(y+1 + ":: " + y);
	    		Vector info = (Vector)eaiResult.elementAt(y);
	    		int_msg_id = (String)info.elementAt(0);
	    		event_name = (String)info.elementAt(1);	
	    		audit_date_time = (String)info.elementAt(2);	
	    		audit_type = (String)info.elementAt(3);	
	    		payload = (String)info.elementAt(4);
				
	    		//System.out.println(int_msg_id + "|");
	    		break;
	    		
	    	}
	    	
	    	result = orderId + "|" + int_msg_id + "|" + event_name + "|" + audit_date_time + "|" + audit_type + "|" + payload;
	    	
	    	eaiIDOnly = int_msg_id;
	    	
	    	OSM_ID = this.findOSM_ID_Single(orderId);
		}
		else
		{
			//System.out.println("================================2======================================");
			//kat cni tambah login osm id more than 1
			
			Vector getOSMID = new Vector();
			
			getOSMID = this.findOSM_ID(orderId);
			
			if(getOSMID.size() > 0)
			{
				
	
					for(int y=0;y<getOSMID.size();y++)
			    	{
			    		//System.out.println(y+1 + "|==========|" + y);
			    		Vector info = (Vector)getOSMID.elementAt(y);
			    		OSM_ID = (String)info.elementAt(0);

						
			    		//System.out.println("|==========|" + orderId + "======"  + OSM_ID + "|============Size+" + eaiResult.size());
			    		eaiResult = this.checkEAITablefromOSM_ID(orderId, OSM_ID);
			    		//System.out.println("|==========|" + orderId + "======"  + OSM_ID + "|============Size+" + eaiResult.size());
			    		
			    		if(eaiResult.size() > 0)			    			
			    		{
			    			//System.out.println("|=====Size=====|" + eaiResult.size());
			    			
			    			for(int t=0;t<eaiResult.size();t++)
					    	{
					    		//System.out.println(t+1 + "|=====eaiResult.size()=====| " + t);
					    		Vector info1 = (Vector)eaiResult.elementAt(t);
					    		int_msg_id = (String)info1.elementAt(0);
					    		event_name = (String)info1.elementAt(1);	
					    		audit_date_time = (String)info1.elementAt(2);	
					    		audit_type = (String)info1.elementAt(3);	
					    		payload = (String)info1.elementAt(4);
								
					    		//System.out.println("|====123======|" +orderId + "|" + int_msg_id + "|" + event_name + "|" + audit_date_time + "|" + audit_type + "|" + payload);
					    		result = orderId + "|" + int_msg_id + "|" + event_name + "|" + audit_date_time + "|" + audit_type + "|" + payload;						    	
						    	eaiIDOnly = int_msg_id;
					    		
						    	break;
					    		//break;
					    		
					    	}
			    			
			    			break;
    			
			    		}
			    		else
			    		{// kat cni tambah check create account
			    			
			    			System.out.println("|=====Size=====|" + eaiResult.size());
			    			System.out.println("|=====OSM ID=====|" + OSM_ID);
			    			
							result = "Null|Null|Null|Null|Null|Null";
							eaiIDOnly = "Null";
							
							eaiResult = new Vector();
							
							eaiResult = this.checkEAITablefromOSM_ID_Create_Acc(orderId, OSM_ID);
				    		
				    		if(eaiResult.size() > 0)			    			
				    		{

				    			
				    			for(int t=0;t<eaiResult.size();t++)
						    	{
						    		//System.out.println(t+1 + "|=====eaiResult.size()=====| " + t);
						    		Vector info1 = (Vector)eaiResult.elementAt(t);
						    		int_msg_id = (String)info1.elementAt(0);
						    		event_name = (String)info1.elementAt(1);	
						    		audit_date_time = (String)info1.elementAt(2);	
						    		audit_type = (String)info1.elementAt(3);	
						    		//payload = (String)info1.elementAt(4);
									
						    		//System.out.println("|====123======|" +orderId + "|" + int_msg_id + "|" + event_name + "|" + audit_date_time + "|" + audit_type + "|" + payload);
						    		result = orderId + "|" + int_msg_id + "|" + event_name + "|" + audit_date_time + "|" + audit_type + "|" + payload;						    	
							    	eaiIDOnly = int_msg_id;
						    		
							    	break;
						    		//break;
						    		
						    	}
				    			
				    			break;
	    			
				    		}
				    		else
				    		{
								result = "Null|Null|Null|Null|Null|Null";
								eaiIDOnly = "Null";
				    		}
				    		
				    		
			    		}
			    		
			    		//eaiResult = new Vector();
			    		
			    	}
				
				
				
			}
			else
			{
				
				//System.out.println(int_msg_id + "|");
				result = "Null|Null|Null|Null|Null|Null";
				eaiIDOnly = "Null";
			}
			
			

		}
		
		}
		
		System.out.println("|===============================================|");
		System.out.println(eaiIDOnly);
		System.out.println(OSM_ID);
		System.out.println("|===============================================|");
		
		//g.checkEAIOPcodeSentDetail(eaiIDOnly);
		resultReturn = eaiIDOnly; 
		
		this.setOsm_id_return(OSM_ID);
		
		return resultReturn;
		
		/*orderId = "Null";			
	    int_msg_id = "Null";
	    event_name = "Null";
	    audit_date_time = "Null";
	    audit_type = "Null";
	    payload = "Null";			
	    result = "Null";*/
	   
		
	
	    
		
	
    	
    
    }
    

    
    private Vector checkEAITable(String orderID)
    {
        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try
		{
			System.out.println("1------------query checkEAITable");
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			System.out.println("2------------query checkEAITable");
			stmt = conn.createStatement();

			/*
			String sqlStmt = "select int_msg_id,event_name,audit_date_time,audit_type,payload from eai_custom.eai_audit_log "
					+ "where int_msg_id IN (select int_msg_id from eai_custom.eai_audit_log where audit_param_2 in ('" + orderID + "', "
					+ "(select audit_param_3 from eai_custom.eai_audit_log where int_msg_id in (select distinct int_msg_id from eai_custom.eai_audit_log where "*/
				//	+ "event_name in ('evProcessOrderFrSBL', 'evProcessOrderFrSBL_old') and audit_param_2 = '" + orderID + "') and audit_type = 'HTTPRQ')/*Osm Order Id*/) and "
				//	+ "audit_type = 'RQI' and event_name like '%BRM%') and PAYLOAD like '%Error%' and audit_type = 'ERR' order by audit_date_time desc";
			

			
			String sqlStmt = "Select int_msg_id,event_name,audit_date_time,audit_type,audit_param_1,audit_param_2,payload from (select * "
					+ "from eai_custom.eai_audit_log where int_msg_id IN (select int_msg_id from eai_custom.eai_audit_log where "
					+ "audit_param_2 in ('" + orderID + "'/*Siebel Order Id*/,(select audit_param_3 from eai_custom.eai_audit_log where int_msg_id in ("
					+ "select distinct int_msg_id from eai_custom.eai_audit_log where event_name in ('evProcessOrderFrSBL', 'evProcessOrderFrSBL_old') and "
					+ "audit_param_2 = '" + orderID + "') and audit_type = 'HTTPRQ')/*Osm Order Id*/) and "
					+ "audit_type = 'RQI' and event_name like '%BRM%') and (PAYLOAD like '%Error%' or  PAYLOAD like '%Failed%') "
					+ "and audit_type = 'ERR' order by audit_date_time desc) where rownum < 5";
			
            
            Vector LineInfo = null;
            //System.out.println("1------------query checkEAITable" + stmt.executeQuery(sqlStmt));
            
            Vector allVector = new Vector();
            //System.out.println("2------------query checkEAITable");
  

            	
				for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
				{
					//System.out.println(">------------------------");
					LineInfo = new Vector();
					LineInfo.add(rs.getString("int_msg_id"));
					LineInfo.add(rs.getString("event_name"));
					LineInfo.add(rs.getString("audit_date_time"));
					LineInfo.add(rs.getString("audit_type"));
					LineInfo.add(rs.getString("payload"));
		
					
					
					//System.out.println("<>-------------------<>");
					System.out.println("3: " + rs.getString("int_msg_id"));
					System.out.println("6: " + rs.getString("event_name"));
					System.out.println("6: " + rs.getString("audit_date_time"));
					System.out.println("6: " + rs.getString("audit_type"));
					System.out.println("6: " + rs.getString("payload"));
					//System.out.println("<>-------------------<>");
					
					//allVector = new Vector();
					allVector.add(LineInfo);
					//x++;
				}

			
			//System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
			//System.out.println("||||||||allVector||||||||" + allVector.size());
			
			/*for(int y = 0;y<allVector.size();y++)
			{
				
				System.out.println("---->" + allVector.elementAt(y));
			}*/
			
			accc = allVector;
			
        }
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("checkEAITable when trying to query. " + e);
		}
		finally
		{
			try
			{
				stmt.close();
				rs.close();
				conn.close();
			}
			catch(Exception e)
			{
				System.out.println("error closing stmt, rs, conn " + e);
			}
		}
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return accc;
    }
    
    
    private Vector findOSM_ID(String orderID)
    {

        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try
		{
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();
			String sqlStmt = "select audit_param_3 from eai_custom.eai_audit_log where int_msg_id in (select distinct int_msg_id from eai_custom.eai_audit_log where "
            		+ "event_name in ('evProcessOrderFrSBL', 'evProcessOrderFrSBL_old') and audit_param_2 = '" + orderID + "') and audit_type = 'HTTPRQ'";
           
			
			Vector LineInfo = null;
            //System.out.println("1------------query checkEAITable" + stmt.executeQuery(sqlStmt));
            
            Vector allVector = new Vector();
           // System.out.println("2------------query checkEAITable");
            
            
  
 
            	
            for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
				{
					//System.out.println(">------------------------");
					LineInfo = new Vector();
					LineInfo.add(rs.getString("audit_param_3"));
					
					//System.out.println("<>-------------------<>");
					//System.out.println("3: " + rs.getString("audit_param_3"));

					//System.out.println("<>-------------------<>");
					
					//allVector = new Vector();
					allVector.add(LineInfo);
					
					//cc.add(allVector);
					//x++;
				}

			
			//System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
			//System.out.println("||||||||allVector||||||||" + allVector.size());
			

			
			accc = allVector;
			
        }
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("checkEAITable when trying to query. " + e);
		}
		finally
		{
			try
			{
				stmt.close();
				rs.close();
				conn.close();
			}
			catch(Exception e)
			{
				System.out.println("error closing stmt, rs, conn " + e);
			}
		}
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return accc;
    
    }
    
    
    public String findOSM_ID_Single(String orderID) throws ClassNotFoundException
    {
        String status = "Null";
        Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery ("select audit_param_3 from eai_custom.eai_audit_log where int_msg_id in (select distinct int_msg_id from eai_custom.eai_audit_log where "
            		+ "event_name in ('evProcessOrderFrSBL', 'evProcessOrderFrSBL_old') and audit_param_2 = '" + orderID + "') and audit_type = 'HTTPRQ'");
    
            if(rs.next())
            	status = rs.getString("audit_param_3");
        }
        catch(Exception e)
        {
        	System.out.println("Error when query poid_id0, " + e);
        }
        finally
        {
            try
            {
                pinConn.close();
                stmt.close();
                rs.close();
            }
            catch(Exception e)
            {
            	System.out.println("Error closing db conn, stmt and rs, " + e);
            }
        }
        System.out.println("Status get is = " + status);
        
        
        
        return status;
    }
    
    
    private Vector checkEAITablefromOSM_ID(String orderID,String OSM_ID)
    {
        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try
		{
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();

			
			String sqlStmt = "select int_msg_id,event_name,audit_date_time,audit_type,audit_param_1,audit_param_2,payload from eai_custom.eai_audit_log "
					+ "where int_msg_id IN (select int_msg_id from eai_custom.eai_audit_log where "
					+ "audit_param_2 in ('" + orderID + "'/*Siebel Order Id*/,'" + OSM_ID + "'/*Osm Order Id*/) and audit_type = 'RQI' and event_name like '%BRM%') "
					+ "and PAYLOAD like '%Error%' and audit_type = 'ERR' order by audit_date_time desc";
			

            
            Vector LineInfo = null;
            //System.out.println("1------------query checkEAITable" + stmt.executeQuery(sqlStmt));
            
            Vector allVector = new Vector();
           // System.out.println("2------------query checkEAITable");

            for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
				{
					System.out.println(">------------------------");
					LineInfo = new Vector();
					LineInfo.add(rs.getString("int_msg_id"));
					LineInfo.add(rs.getString("event_name"));
					LineInfo.add(rs.getString("audit_date_time"));
					LineInfo.add(rs.getString("audit_type"));
					LineInfo.add(rs.getString("payload"));
		
					
					
					System.out.println("<>-------------------<>");
					System.out.println("3: " + rs.getString("int_msg_id"));
					System.out.println("6: " + rs.getString("event_name"));
					System.out.println("6: " + rs.getString("audit_date_time"));
					System.out.println("6: " + rs.getString("audit_type"));
					System.out.println("6: " + rs.getString("payload"));
					System.out.println("<>-------------------<>");
					
					//allVector = new Vector();
					allVector.add(LineInfo);
					//x++;
				}

			
			//System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
		//	System.out.println("||||||||allVector||||||||" + allVector.size());
			

			
			accc = allVector;
			
        }
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("checkEAITable when trying to query. " + e);
		}
		finally
		{
			try
			{
				stmt.close();
				rs.close();
				conn.close();
			}
			catch(Exception e)
			{
				System.out.println("error closing stmt, rs, conn " + e);
			}
		}
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return accc;
    }
    
    
    private Vector checkEAITablefromOSM_ID_Create_Acc(String orderID,String OSM_ID)
    {
        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try
		{
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();

			
			String sqlStmt = "select  distinct int_msg_id,event_name,audit_date_time,audit_type from eai_custom.eai_audit_log "
					+ "where int_msg_id IN (select int_msg_id from eai_custom.eai_audit_log where audit_param_2 in "
					+ "('" + orderID + "'/*Siebel Order Id*/,'" + OSM_ID + "'/*Osm Order Id*/) and audit_type = 'RQI' and event_name = 'evBRMCreateAccountView') "
					+ "and PAYLOAD like '%ERR_%' and audit_type = 'WSRP' order by audit_date_time desc";
			

            
            Vector LineInfo = null;
            //System.out.println("1baru------------query checkEAITable" + stmt.executeQuery(sqlStmt));
            
            Vector allVector = new Vector();
           // System.out.println("2baru------------query checkEAITable");

            for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
				{
					System.out.println(">------------------------");
					LineInfo = new Vector();
					LineInfo.add(rs.getString("int_msg_id"));
					LineInfo.add(rs.getString("event_name"));
					LineInfo.add(rs.getString("audit_date_time"));
					LineInfo.add(rs.getString("audit_type"));
					//LineInfo.add(rs.getString("payload"));
		
					
					
					System.out.println("<>-------------------<>");
					System.out.println("3: " + rs.getString("int_msg_id"));
					System.out.println("6: " + rs.getString("event_name"));
					System.out.println("6: " + rs.getString("audit_date_time"));
					System.out.println("6: " + rs.getString("audit_type"));
					//System.out.println("6: " + rs.getString("payload"));
					//System.out.println("<>-------------------<>");
					
					//allVector = new Vector();
					allVector.add(LineInfo);
					//x++;
				}

			
			//System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
		//	System.out.println("||||||||allVector||||||||" + allVector.size());
			

			
			accc = allVector;
			
        }
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("checkEAITable when trying to query. " + e);
		}
		finally
		{
			try
			{
				stmt.close();
				rs.close();
				conn.close();
			}
			catch(Exception e)
			{
				System.out.println("error closing stmt, rs, conn " + e);
			}
		}
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return accc;
    }
    
 
    public String osm_id_return = "";

	public String getOsm_id_return() {
		return osm_id_return;
	}



	public void setOsm_id_return(String osm_id_return) {
		this.osm_id_return = osm_id_return;
	}
	
	


}
