package brm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class CheckSBLStatus {

	
    private String dbasePass = "ICP_READ123";
    private String dbaseUser = "ICP_READ";
    private String dbaseIP = "10.41.33.92";
	
	public static void main(String args[]) throws Exception
	{
		CheckSBLStatus s = new CheckSBLStatus();
		
		String SBLstatus = "Null";
		String SBLdate = "Null";
		String orderID = "1-5XCLP53";
		
		
		//SBLdate = s.getDateSubmitted(orderID);
		
		//SBLstatus = s.getSBLStatus(orderID);
		
		System.out.println("=========");
		System.out.println("Siebel Date Submitted : " + SBLdate);
		System.out.println("Siebel Status : " + SBLstatus);
		
	}
	
	   public Connection getDBConn(String dbLogin, String dbPassword, String dbIp)
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
	
	   public String getDateSubmitted(String orderID)
	    {
	        String dateReturn = "Null";
	        Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
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
	   
	   
	   public String getSBLStatus(String orderID)
	    {
	        String dateReturn = "Null";
	        Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select status_cd from siebel.s_order where row_id = '" + orderID + "'");
	    
	            if(rs.next())
	            	dateReturn = rs.getString("status_cd");
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
	        System.out.println("Siebel Status = " + dateReturn);
	        return dateReturn;
	    }



}
