package brm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Vector;

public class CheckSBLAgingDate {
	
	   private String dbasePassSBL = "ICP_READ123";
	   private String dbaseUserSBL = "ICP_READ";
	   private String dbaseIPSBL = "10.41.33.92";
	   
		private String dbasePass = "ICP_READ123";
	    private String dbaseUser = "ICP_READ";
	    private String dbaseIP = "10.41.33.140";
	   

	   
	    public static void main( String[] args ) throws Exception 
	    {
	    	
	    	CheckSBLAgingDate cs = new CheckSBLAgingDate();
	    	

	    	
	    	int threeDays = 3 *24*60*60;        	
	    	int tenDays = 10 *24*60*60;
	    	long todays = System.currentTimeMillis() / 1000L;
	    	
	    	int todaysDate = (int) todays;
	    	
	    	Vector orderIdVec = cs.getSBLorderIdFromOSM();
	    	
	    	String orderID = "Null";
	    	String dateSBL = "Null";
	    	
	    	int SBLtotalIn3Days = 0;
	    	int SBLtotalIn3DaysLess10Days = 0;
	    	int SBLtotalMore10Days = 0;
	    	    	
	    	for(int b=0;b<orderIdVec.size();b++)
	    	{
	    		System.out.println("==========================================");
	    		//System.out.println(b+1 + ")" + orderIdVec.elementAt(b));
	    		orderID = orderIdVec.elementAt(b).toString();
	    		orderID = orderID.replace("[","");
	    		orderID = orderID.replace("]","");
	    		System.out.println(b+1 + ")" + orderID);
	    		System.out.println("==========================================");
	    		
	    		dateSBL = cs.getDateSubmitted(orderID);
	    		
	    		int dateReturn = cs.dateConverter(dateSBL);
    		
	    		int todayMinusDateList = todaysDate - dateReturn;

	    		
	    		if(todayMinusDateList < threeDays)
	    		{
	    			//System.out.println("--> Date is within 3 days");
	    			SBLtotalIn3Days++;
	    		}
	    		else if(todayMinusDateList > tenDays)
	    		{
	    			//System.out.println("--> More than 10 days");
	    			SBLtotalMore10Days++;
	    			
	    		}
	    		else if((todayMinusDateList < tenDays) && (todayMinusDateList > threeDays))
	    		{
	    			//System.out.println("--> Between 3 to 10 days");
	    			SBLtotalIn3DaysLess10Days++;
	    		}
	    		else
	    		{
	    			System.out.println("-->  Not relevan");
	    		}
	    		
	    	}
	    	
	    	int totalAllSBL = SBLtotalIn3Days + SBLtotalMore10Days + SBLtotalIn3DaysLess10Days;
	    	
	    	System.out.println("Total Date is within 3 days " + SBLtotalIn3Days);
	    	System.out.println("Total More than 10 days " + SBLtotalMore10Days);
	    	System.out.println("Total Between 3 to 10 days " + SBLtotalIn3DaysLess10Days);
	    	System.out.println("------");
	    	System.out.println("Total All " + totalAllSBL);
	    

	    }
	    
	    
	
	    public Vector getSBLorderIdFromOSM() throws ClassNotFoundException
	    {

	    	    Vector accc = new Vector();
	    		Connection conn = null;
	    		ResultSet rs = null;
	    		Statement stmt = null;
	    		
	    		try
	    		{
	    		conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
	    		
	    		stmt = conn.createStatement();
	    		String sqlStmt = "SELECT ooh.reference_number AS SIEBEL_ID FROM osm.om_order_flow oof2 INNER JOIN OSM.OM_TASK OT2 ON (OOF2.TASK_ID = OT2.TASK_ID) "
	            		+ "INNER JOIN osm.om_state os ON (os.STATE_ID = oof2.STATE_ID) "
						+ "INNER JOIN osm.om_cartridge oc2 ON (oc2.cartridge_id = oof2.cartridge_id) "
						+ "INNER JOIN osm.om_order_header ooh ON (oof2.order_seq_id = ooh.order_seq_id) "
						+ "INNER JOIN osm.om_ospolicy_state oos ON (ooh.ord_state_id = oos.ID) "
						+ "INNER JOIN osm.om_process op ON (OP.PROCESS_ID = oof2.PROCESS_ID) "
						+ "INNER JOIN osm.om_order_type oot ON (ooh.order_type_id = oot.order_type_id) "
						+ "LEFT JOIN (SELECT ooi.order_seq_id AS ooi_order_seq_id "
						+ ",oof.order_seq_id AS oof_order_seq_id "
						+ ",ooi.node_value_text AS ooi_node_value_text "
						+ ",oof.state_id AS oof_state_id "
						+ ",oof.hist_seq_id AS oof_hist_seq_id "
						+ "FROM osm.om_order_instance ooi "
						+ "JOIN osm.om_order_flow oof ON (ooi.order_seq_id = oof.order_seq_id) "
						+ "JOIN osm.om_order_data_dictionary oodd ON (ooi.data_dictionary_id = oodd.data_dictionary_id) "
						+ "WHERE oodd.data_dictionary_mnemonic = 'response_error_message' "
						+ "AND oof.task_type = 'M' "
						+ "AND ooi.hist_seq_id = CASE "
						+ "WHEN oof.state_id = '1' "
						+ "THEN (SELECT a.hist_seq_id_from "
						+ "FROM osm.om_hist$flow a "
						+ "JOIN osm.om_hist$flow b ON (a.hist_seq_id = b.hist_seq_id_from) "
						+ "WHERE b.hist_seq_id = oof.hist_seq_id "
						+ "AND b.order_seq_id = ooi.order_seq_id "
						+ "AND a.order_seq_id = ooi.order_seq_id) "
						+ "ELSE (SELECT a1.hist_seq_id "
						+ "FROM osm.om_hist$flow a1 "
						+ "JOIN osm.om_hist$flow b1 ON (a1.hist_seq_id = b1.hist_seq_id_from) "
						+ "JOIN osm.om_hist$flow c1 ON (b1.hist_seq_id = c1.hist_seq_id_from) "
						+ "JOIN osm.om_hist$flow d1 ON (c1.hist_seq_id = d1.hist_seq_id_from) "
						+ "WHERE a1.order_seq_id = ooi.order_seq_id "
						+ "AND b1.order_seq_id = ooi.order_seq_id "
						+ "AND c1.order_seq_id = ooi.order_seq_id "
						+ "AND d1.order_seq_id = ooi.order_seq_id "
						+ "AND d1.hist_seq_id = oof.hist_seq_id "
						+ ")END) TEMP ON (oof2.order_seq_id = TEMP.oof_order_seq_id AND oof2.hist_seq_id = TEMP.oof_hist_seq_id) "
						+ "WHERE oos.mnemonic = 'in_progress' "
						+ "AND OC2.cartridge_id = ot2.cartridge_id "
						+ "AND oc2.cartridge_id = ooh.cartridge_id "
						+ "AND oc2.cartridge_id = op.cartridge_id "
						+ "AND OOF2.TASK_TYPE = 'M' "
						+ "AND OT2.TASK_MNEMONIC LIKE 'Exception%BRM%'";

	    		 Vector LineInfo = null;
	 	        
	 	        Vector allVector = new Vector();
	 	        
	 	       // System.out.println("2<>-------------------<>");
	 	        
	 			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
	 			{
	 				//System.out.println(">------------------------");
	 				LineInfo = new Vector();
	 				LineInfo.add(rs.getString("SIEBEL_ID"));

	 				//System.out.println("<>-------------------<>");
	 				//System.out.println("3: " + rs.getString("SIEBEL_ID"));
	 				//System.out.println("<>-------------------<>");

	 				
	 				//allVector = new Vector();
	 				allVector.add(LineInfo);
	 				//x++;
	 			}
	 			
	 			accc = allVector;
	 			
	 			for(int q=0;q<accc.size();q++)
	 	    	{
	 				System.out.println("===========>" + accc.elementAt(q));
	 	    	}
	        }
	        catch(Exception e)
	        {
	        	System.out.println("Error when query , " + e);
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
	    
	    
	    public int  dateConverter(String timeStamp)
	    {
	    	int unixDateReturn = 0;
	    	
	    	DateFormat dfm = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
			
			//String time = "23-Feb-2015 15:34:58";

		    long unixtime = 0;
		    
		    dfm.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//Specify your timezone 
		    try
		    {
		        unixtime = dfm.parse(timeStamp).getTime();  
		        unixtime=unixtime/1000;
		    } 
		    catch (ParseException e) 
		    {
		        e.printStackTrace();
		    }
		    
		    System.out.println("-->" + unixtime);
	    	
		    
		    unixDateReturn = (int) unixtime;
	    	
	    	return unixDateReturn;
	    	
	    }
	    
		   public String getDateSubmitted(String orderID)
		    {
		        String dateReturn = "Null";
		        Connection pinConn = getDBConnSBL(dbaseUserSBL, dbasePassSBL, dbaseIPSBL);
		        ResultSet rs = null;
		        Statement stmt = null;
		        try
		        {
		            stmt = pinConn.createStatement();
		            rs = stmt.executeQuery ("select to_char(SUBMIT_DT,'dd-Mon-yyyy hh24:mi:ss') AS dateSBL from siebel.s_order_dtl where row_id = '" + orderID + "'");
		    
		            if(rs.next())
		            	dateReturn = rs.getString("dateSBL");
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
		        System.out.println("Date submitted = " + dateReturn);
		        return dateReturn;
		    }
		   
		   
		   public Connection getDBConnSBL(String dbLogin, String dbPassword, String dbIp)
		    {
		        Connection con = null;
		        try
		        {
		        
		        con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + dbIp + ")(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=NSBLPRD)))", dbLogin, dbPassword);
		        }
		        catch(Exception e)
		        {
		            System.out.println("Error create new connection to database, " + e);
		            return null;
		        }
		        return con;
		    }
		   
		    private Connection getDBConn(String dbLogin, String dbPassword, String dbIp) throws ClassNotFoundException
		    {
		    	Class.forName ("oracle.jdbc.OracleDriver");
		        Connection con;
		        
		        try
		        {
		        	System.out.println("1==============================================");
		        	
		        	//dua2 ni sepatutnye boleh
		        	//con = DriverManager.getConnection("jdbc:oracle:thin:@//10.41.33.110:1521/NEAIPRD" , "EAI_READ", "EAI_READ");
		        	
		        	con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + dbIp + ")(PORT=1521))(CONNECT_DATA=(SERVICE_NAME = NOSMPRD)))", dbLogin, dbPassword);
		        	
		        	//----------
		         }
		        catch(Exception e)
		        {
		            System.out.println("1Exception -->  " + e.getMessage());
		            return null;
		        }
		        return con;
		    }

}
