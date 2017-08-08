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

public class CheckOSMTask {
	
	//private String dbasePass = "OSM_READ";
    //private String dbaseUser = "OSM_READ";
    
	private String dbasePass = "ICP_READ123";
    private String dbaseUser = "ICP_READ";
    private String dbaseIP = "10.41.33.140";
    
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


    public static void main( String[] args ) throws Exception 
    {
    	CheckOSMTask c = new CheckOSMTask();
    	
    	//String result = c.getOSMTaskStuckAtBRM();
    	// System.out.println("--------------" + result);
    	int threeDays = 3 *24*60*60;        	
    	int tenDays = 10 *24*60*60;
    	long todays = System.currentTimeMillis() / 1000L;
    	
    	int todaysDate = (int) todays;
    	
    	String agingIn = "Null";
    	String agingOut = "Null";
    	
    	int totalIn3Days = 0;
    	int totalIn3DaysLess10Days = 0;
    	int totalMore10Days = 0;
    	
    	Vector agingStat = c.getOSMTaskStuckAtBRM_byTime();
    	
    	for(int q=0;q<agingStat.size();q++)
    	{
    		
    		System.out.println("==========================================");
    		System.out.println(q+1 + "--------------" + agingStat.elementAt(q));
    		
    		agingIn = agingStat.elementAt(q).toString();
    		System.out.println("!!AgingIn-->" + agingIn);
    		agingIn = agingIn.replace("[","");
    		agingIn = agingIn.replace("]","");
    		System.out.println("!!AgingIn-->" + agingIn);
    		
    		int dateReturn = c.dateConverter(agingIn);
    		
    		System.out.println("!!-->" + todays);
    		System.out.println("!!!-->" + dateReturn);
    		//System.out.println("!!-->" + threeDays);
    		
    		
    		int todayMinusDateList = todaysDate - dateReturn;

    		
    		System.out.println("!!todaysDate - dateReturn-->" + todayMinusDateList);
    		System.out.println("!!threeDays-->" + threeDays);
    		System.out.println("!!tenDays-->" + tenDays);

    		
    		if(todayMinusDateList < threeDays)
    		{
    			System.out.println("--> Date is within 3 days");
    			totalIn3Days++;
    		}
    		else if(todayMinusDateList > tenDays)
    		{
    			System.out.println("--> More than 10 days");
    			totalMore10Days++;
    			
    		}
    		else if((todayMinusDateList < tenDays) && (todayMinusDateList > threeDays))
    		{
    			System.out.println("--> Between 3 to 10 days");
    			totalIn3DaysLess10Days++;
    		}
    		else
    		{
    			System.out.println("-->  Not relevan");
    		}
    		
    	}
    	
    	
    	System.out.println("Total Date is within 3 days " + totalIn3Days);
    	System.out.println("Total More than 10 days " + totalMore10Days);
    	System.out.println("Total Between 3 to 10 days " + totalIn3DaysLess10Days);
    	System.out.println("------");
    	System.out.println("Total All " + agingStat.size());
    	
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
    
    public Vector agingTimeStamp()
    {
    	Vector age = new Vector();
    	
    	
    	return age;
    }
    
    public String getOSMTaskStuckAtBRM() throws ClassNotFoundException 
    {

        String status = "Null";
        Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("SELECT count(ooh.reference_number) as total FROM osm.om_order_flow oof2 INNER JOIN OSM.OM_TASK OT2 ON (OOF2.TASK_ID = OT2.TASK_ID) "
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
					+ "AND OT2.TASK_MNEMONIC LIKE 'Exception%BRM%'");

            if(rs.next())
            	status = rs.getString("total");
        }
        catch(Exception e)
        {
        	System.out.println("Error when query , " + e);
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
       // System.out.println("Account status get is = " + status);
        return status;
    
    }
    
    public Vector getOSMTaskStuckAtBRM_byTime() throws ClassNotFoundException
    {

    	    Vector accc = new Vector();
    		Connection conn = null;
    		ResultSet rs = null;
    		Statement stmt = null;
    		
    		try
    		{
    		conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
    		
    		stmt = conn.createStatement();
    		String sqlStmt = "SELECT to_char(oof2.date_pos_started , 'dd-Mon-yyyy hh24:mi:ss') AS TIMESTAMP FROM osm.om_order_flow oof2 INNER JOIN OSM.OM_TASK OT2 ON (OOF2.TASK_ID = OT2.TASK_ID) "
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
 				LineInfo.add(rs.getString("TIMESTAMP"));

 				//System.out.println("<>-------------------<>");
 				System.out.println("3: " + rs.getString("TIMESTAMP"));
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
    
    
}
