package ICPException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

public class CheckUpdateServiceStatus {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
    private String dbasePassPIN = "DBpin_123";
    private String dbaseUserPIN = "pin";
    private String dbaseIpPIN = "10.41.68.94";
    
    
    private Connection getDBConnPIN(String dbLogin, String dbPassword, String dbIp)
    {
        Connection con = null;
        try
        {
        	con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + dbIp + ")(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=NBRMPRD)))", dbLogin, dbPassword);
        }
        catch(Exception e)
        {
            System.out.println("Error create new connection to database, " + e);
            return null;
        }
        return con;
    }
    
	public static void main( String[] args ) throws Exception 
	{
		CheckUpdateServiceStatus ch = new CheckUpdateServiceStatus();
		
		String outputReturn = "";
		
		ch.checkServiceStatusByFList("mainopcode");
		
		String accountPoidInBRM = "Null";
		String accountNumberSent = "Null";
		String accountNumberInBRM = "Null";
		
		accountPoidInBRM = ch.serviceTiedToBA(ch.getServicePoid());
		
		
		if(accountPoidInBRM.equalsIgnoreCase("Null"))
		{
			outputReturn = "Service " + ch.getServicePoidString() + " Not Found in BRM";
		}
		else if(accountPoidInBRM.equalsIgnoreCase(ch.getAccountPoid()))
		{
			System.out.println("Account and service sent tied together in BRM");
			String statusReturn = ch.checkService_TStatus(ch.getServicePoid());
			
			if(statusReturn.equalsIgnoreCase("Null"))
			{
				outputReturn = "Service '" + ch.getServicePoidString() + "' Not found in BRM";
			}
			else if(statusReturn.equalsIgnoreCase(ch.getServiceStatus()))
			{
				outputReturn = "Service '" + ch.getServicePoidString() +"' status already UPDATE in BRM. OSM to bypass";
			}
			else
			{
				outputReturn = "Service status in BRM is " + statusReturn + " but try to set status " + ch.getServiceStatus() + ". Please re-trigger";
			}
		}
		else
		{
			System.out.println("Account and service sent NOT tied together in BRM");
			accountNumberSent = ch.checkAccountNumber(ch.getAccountPoid());
			
			accountNumberInBRM = ch.checkAccountNumber(accountPoidInBRM);
			
			Vector checkServiceDetail= new Vector();
			
			String poid_id0 = "Null";
			String poid_Type = "Null";
			String loginGet = "Null";
			
			checkServiceDetail = ch.getServiceDetail(ch.getServicePoid());
			
			if(checkServiceDetail.size() > 0)
			{
		    	for(int y=0;y<checkServiceDetail.size();y++)
		    	{
		    		//System.out.println(y+1 + ":: " + y);
		    		Vector info = (Vector)checkServiceDetail.elementAt(y);
		    		poid_id0 = (String)info.elementAt(0);
		    		poid_Type = (String)info.elementAt(1);	
		    		loginGet = (String)info.elementAt(2);							
		    		
		    	}
		    	
		    	outputReturn = "[Siebel] Try to change status but '" + poid_Type + " " + poid_id0 + "' (Login:" + loginGet + ") sent Tied to other BA in BRM " + accountNumberInBRM + ". Not as send BA " + accountNumberSent;
			}
			else
			{
				outputReturn = "ERROR : Shouldnt be in this";
			}
		
		}
		
		
	}
	
	
	  public String checkService_TStatus(String svcPoid)
	    {
	        String status = "Null";
	        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select status from service_t where poid_id0 = '" + svcPoid + "'");
	    
	            if(rs.next())
	            	status = rs.getString("status");
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
	
	public void checkServiceStatusByFList(String fileURL) throws Exception
	{

   	 	
    	//CheckLoginFromEAI t = new CheckLoginFromEAI();
    	                  
    	FileInputStream fin=new FileInputStream(fileURL);
    	//FileInputStream fin=new FileInputStream("mainopcode");
    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
    	
    	PrintStream p1;
    	FileOutputStream out1 = new FileOutputStream(res.getString("file21.xmlTemp"));
    	p1 = new PrintStream(out1);
    	
    	
    	String strLine = "";
    	String output = "";

    	boolean test = false;
    	
    	int a =1;
    	
	   	String servicePoid = "Null";
	   	String serviceStatus = "Null";
	   	String accountPoid = "Null"; 
	   	//String discPoid = "Null";
	   	//String datePurchaseSent = "Null";
    	

    	boolean serviceLoginCheck = false;
    	boolean statusCheck = false;
    	boolean accountPoidCheck = false;

    	
    	while ((strLine = br.readLine()) != null)  
   		{

   			if(strLine.indexOf("/account") >-1 && accountPoidCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				accountPoid = strLine.substring(opBegin+1, opEnd);
   				accountPoidCheck = true;
   				System.out.println("Account Poid - " + accountPoid);
   			}

   			if((strLine.indexOf("</POID>") > -1 && strLine.indexOf("/service/") > -1) && serviceLoginCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				servicePoid = strLine.substring(opBegin+1, opEnd);
   				serviceLoginCheck = true;
   				System.out.println("Service Login Sent - " + servicePoid);
   			}
   			
   			
   			if(strLine.indexOf("<STATUS>") >-1 && statusCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				serviceStatus = strLine.substring(opBegin+1, opEnd);
   				statusCheck = true;
   				System.out.println("Service status Sent - " + serviceStatus);
   			}
   			
   			
   		
   		}
    	

    	if(!servicePoid.equalsIgnoreCase("Null"))
    	{
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	this.setServicePoidString(servicePoid);
    	StringTokenizer st1 = new StringTokenizer(servicePoid, " ");			
    	start = st1.nextToken();
    	type = st1.nextToken();
    	prodPoid = st1.nextToken();
    	
    	System.out.println("Service Poid -> " + prodPoid);
    	
    	
    	this.setServicePoid(prodPoid);
    	}
    	else
    	{
    		
    		this.setServicePoid("Null");	
    	}
	    	

    	if(!accountPoid.equalsIgnoreCase("Null"))
    	{
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	
    	StringTokenizer st1 = new StringTokenizer(accountPoid, " ");			
    	start = st1.nextToken();
    	type = st1.nextToken();
    	prodPoid = st1.nextToken();
    	
    	System.out.println("Account Poid -> " + prodPoid);
    	
    	
    	this.setAccountPoid(prodPoid);
    	}
    	else
    	{
    		
    		this.setAccountPoid("Null");	
    	}
    	
    	
    	this.setServiceStatus(serviceStatus);
    	

    	
    	

	   
	}
	
	
	  public String checkAlias_TStatus(String svcPoid)
	    {
	        String status = "Null";
	        Vector svc = new Vector();
			Connection conn = null;
			ResultSet rs = null;
			Statement stmt = null;
			try
			{


				
				conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
				
				stmt = conn.createStatement();
				
				String sqlStmt = "select * from service_alias_list_t where obj_id0 = " + svcPoid;
	            Vector LineInfo = new Vector();
	           // System.out.println("------------query service_t");
	            
				for(rs = stmt.executeQuery(sqlStmt); rs.next(); svc.add(LineInfo))
				{
					//System.out.println(">!------------------------1");
					LineInfo = new Vector();
					//svc.add(LineInfo);
					
					
					LineInfo.add(rs.getString("obj_id0"));
					LineInfo.add(rs.getString("rec_id"));
					LineInfo.add(rs.getString("name"));
				
					System.out.println("<>-------------------<>");
					System.out.println("obj_id0 : " + rs.getString("obj_id0"));
					System.out.println("rec_id : " + rs.getString("rec_id"));
					System.out.println("name : " + rs.getString("name"));

					//x++;
					
					//svc.add(LineInfo);
				}
				
				
				System.out.println("<>--------SIZE-----------<>" + svc.size()); 
				for(int w=0;w<svc.size();w++)
				{
					System.out.println(w + "<>--------SIZE-----------<>" + svc.elementAt(w)); 
				}
				
				
				if(svc.size() < 1)
				{
					System.out.println("object in alias_t is null");
					status = "0";
				}
				else if(svc.size() == 1)
				{
					System.out.println("object in alias_t is 1");	
					status = "1";
				}
				else
				{
					System.out.println("object in alias_t is more than 1");
					status = "2";
				}
	    

	        }
	        catch(Exception e)
	        {
	        	System.out.println("Error when query poid_id0, " + e);
	        }
	        finally
	        {
	            try
	            {
	            	conn.close();
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
	  
	
	   public Vector getServiceDetail(String servicePoid)
	    {
	        Vector accc = new Vector();
			Connection conn = null;
			ResultSet rs = null;
			Statement stmt = null;
			


			
			try
			{
				
				conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
				
				stmt = conn.createStatement();

				//System.out.println("1<>-------------------<>");
				String sqlStmt = "select poid_id0,poid_type,login from service_t where poid_id0 = " + servicePoid;
				//System.out.println("2<>-------------------<>");
				
	            Vector LineInfo = null;
	            
	            Vector allVector = new Vector();
	            
	           // System.out.println("2<>-------------------<>");
				for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
				{
					//System.out.println(">------------------------");
					LineInfo = new Vector();
					LineInfo.add(rs.getString("poid_id0"));
					LineInfo.add(rs.getString("poid_type"));
					LineInfo.add(rs.getString("login"));

					
					//allVector = new Vector();
					allVector.add(LineInfo);
					//x++;
				}
				
				accc = allVector;
				
	        }
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("Fuckrror when trying to query. " + e);
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

			return accc;
	    }
	
		public String serviceTiedToBA(String svcPoid)
		{
			String status = "Null";
			
	        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select account_obj_id0 from service_t where poid_id0 = '" + svcPoid + "'");
	    
	            if(rs.next())
	            	status = rs.getString("account_obj_id0");
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
	        System.out.println("Account poid from service_t = " + status);
	        
	        
	        
	        return status;
			
		}
		
		
		public String checkAccountNumber(String accPoid)
		{
			String status = "Null";
			
	        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select account_no from account_t where poid_id0 = '" + accPoid + "'");
	    
	            if(rs.next())
	            	status = rs.getString("account_no");
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
	        System.out.println("Account poid from service_t = " + status);
	        
	        
	        
	        return status;
			
		}
		
	public String servicePoid = "";
	public String serviceStatus = "";
	public String servicePoidString = "";
	public String accountPoid = "";



	public String getAccountPoid() {
		return accountPoid;
	}

	public void setAccountPoid(String accountPoid) {
		this.accountPoid = accountPoid;
	}

	public String getServicePoidString() {
		return servicePoidString;
	}

	public void setServicePoidString(String servicePoidString) {
		this.servicePoidString = servicePoidString;
	}

	public String getServicePoid() {
		return servicePoid;
	}

	public void setServicePoid(String servicePoid) {
		this.servicePoid = servicePoid;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	

}
