package BillInvRun;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import brm.BillBalMonitor;

public class TotalBillBP {
	
    private String dbasePassPIN = "DBpin_123";
    private String dbaseUserPIN = "pin";
    private String dbaseIpPIN = "10.41.68.94";
    
    
    private Connection getDBConnPIN(String dbLogin, String dbPassword, String dbIp)
    {
        Connection con = null; 
        try
        {
        	//jdbc:oracle:thin:@(description=(address=(host=HOSTNAME)(protocol=tcps)(port=PORT))(connect_data=(service_name=SERVICENAME)(server=SHARED)))
        	con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + dbIp + ")(PORT=1521))(CONNECT_DATA=(SERVICE_NAME=NBRMPRD)))", dbLogin, dbPassword);
        }
        catch(Exception e)
        {
            System.out.println("Error create new connection to database, " + e.getMessage());
            return null;
        }
        return con;
    }
    
	   public static void main(String args[]) throws ParseException
	   {
		   TotalBillBP b = new TotalBillBP();
		   //String dateSent = "1484496000"; 
		   //i.getLeftBill(dateSent);
		   String month = "April";
		   String year = "2017";
		   
		   String dateIn = "Null";
		   String expectedPattern = "dd MMM yyyy";
		   String result = "Null";
		   //String expectedPattern = "yyyy-MMM-dd HH:mm:ss";
		   
		   
		   Vector c = new Vector();
		   c = b.getTotalBillBP(month,year);
		   
		   if(c.size()>0)
		   {
			   System.out.println("-------------------------------------------------ADA");
			   for(int i=0;i<c.size();i++)
			   {
				   System.out.println(i + "--> " + c.elementAt(i)); 
			   }
		   }
		   else
		   {
			   System.out.println("-------------------------------------------------EMPTY");
		   }
		   
		   
		   /*for(int x=1;x<30;x+=3)
		   {
			  // dateIn = year+"-"+month+"-"+String.valueOf(x)+" 16:00:00";
			   dateIn = String.valueOf(x) + " " + month + " "  + year;
			   
			   System.out.println(x + ": " + dateIn);
			   
				
				SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);		   
				Date date = formatter.parse(dateIn);
				long unixTime = (long) date.getTime()/1000;
				
				String dateUnix = String.valueOf(unixTime);

				
				result = b.getLeftBill(dateUnix);
				
				System.out.println("-------------------------------------------------");
				System.out.println("Date unix time - " + unixTime);
				System.out.println("Date IN - " + dateIn);
				System.out.println("Date UNIX - " + dateUnix);
				System.out.println("BP Balance - " + result);
				System.out.println("-------------------------------------------------");
		   }
		   
		   Vector c = new Vector();
		   c = b.getTotalBillBP(month,year);
		   
		   if(c.size()>0)
		   {
			   System.out.println("-------------------------------------------------ADA");
			   for(int i=0;i<c.size();i++)
			   {
				   System.out.println(i + "--> " + c.elementAt(i)); 
			   }
		   }
		   else
		   {
			   System.out.println("-------------------------------------------------EMPTY");
		   }*/
		   
	   }
	   
	   
	   public Vector getTotalBillBP(String month,String year) throws ParseException
	   {
		   Vector returnVec = new Vector();
		   Vector LineInfo = new Vector();
		   String dateIn = "Null";
		   //String expectedPattern = "dd MMM yyyy";
		   String result = "Null";
		   String expectedPattern = "yyyy-MMM-dd HH:mm:ss";
		   
		   for(int x=1;x<30;x+=3)
		   {
			   //dateIn = String.valueOf(x) + " " + month + " "  + year;
			   if(x<10)
			   dateIn = year+"-"+month+"-0"+String.valueOf(x)+" 00:00:00";
			   else
			   dateIn = year+"-"+month+"-"+String.valueOf(x)+" 00:00:00";   
			   
			   System.out.println(x + ": " + dateIn);
			   //LocalDate currentDate = LocalDate.now();
				
				SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);		
				//formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
				formatter.toString();
				Date date = formatter.parse(dateIn);
				date.toGMTString();
				long unixTime = (long) date.getTime()/1000;
				
			
				
				//String dateUnix = String.valueOf(unixTime+10000);

				
				result = this.getLeftBill(unixTime);
				
				System.out.println("-------------------------------------------------");
				System.out.println("Date  - " + date);
				System.out.println("Date IN - " + dateIn);
				//System.out.println("Date UNIX - " + dateUnix);
				System.out.println("BP Balance - " + result);
				System.out.println("unixTime - " + unixTime);
				System.out.println("-------------------------------------------------");
				
				LineInfo = new Vector();
				//LineInfo.add(x);
				LineInfo.add(result);
				
				returnVec.add(LineInfo);
		   }
		   
		   
		   System.out.println("-------------------------------------------------" + returnVec.size());
		   
		   return returnVec;
	   }
	   
		public String getLeftBill(long dateSent)
		{
			String status = "Null";
			
	        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select count(1) LEFT_TO_BILL from billinfo_t where actg_next_t in (" + dateSent + ") and billing_status_flags=0");
	    
	            if(rs.next())
	            	status = rs.getString("LEFT_TO_BILL");
	        }
	        catch(Exception e)
	        {
	        	System.out.println("Error when query LEFT_TO_BILL, " + e);
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
	        System.out.println("LEFT_TO_BILL = " + status);
	        
	        
	        
	        return status;
			
		}
		
		
		
		   public Vector getTotalInvBP(String month,String year) throws ParseException
		   {
			   Vector returnVec = new Vector();
			   Vector LineInfo = new Vector();
			   String dateIn = "Null";
			   String expectedPattern = "dd MMM yyyy";
			   String result = "Null";
			   
			   
			   for(int x=1;x<30;x+=3)
			   {
				   dateIn = String.valueOf(x) + " " + month + " "  + year;
				   
				   System.out.println(x + ": " + dateIn);
				   
					
					SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);		   
					Date date = formatter.parse(dateIn);
					long unixTime = (long) date.getTime()/1000;
					
					String dateUnix = String.valueOf(unixTime);

					
					result = this.getLeftInvoice(dateUnix);
					
					System.out.println("-------------------------------------------------");
					System.out.println("Date IN - " + dateIn);
					System.out.println("Date UNIX - " + dateUnix);
					System.out.println("BP Balance - " + result);
					System.out.println("-------------------------------------------------");
					
					LineInfo = new Vector();
					//LineInfo.add(x);
					LineInfo.add(result);
					
					returnVec.add(LineInfo);
			   }
			   
			   
			   System.out.println("-------------------------------------------------" + returnVec.size());
			   
			   return returnVec;
		   }
		
		public String getLeftInvoice(String dateSent)
		{
			String status = "Null";
			
	        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select count(1) LEFT_TO_INVOICED from bill_t b where b.END_T= " + dateSent + " and b.INVOICE_OBJ_ID0=0");
	    
	            if(rs.next())
	            	status = rs.getString("LEFT_TO_INVOICED");
	        }
	        catch(Exception e)
	        {
	        	System.out.println("Error when query LEFT_TO_INVOICED, " + e);
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
	        System.out.println("LEFT_TO_INVOICED = " + status);
	        
	        
	        
	        return status;
			
		}

}
