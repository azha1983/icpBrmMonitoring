package brm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;

public class TestRun {
	
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
    
    public static void main(String args[])throws Exception
    {
    	TestRun tr = new TestRun();
    	
    	FileInputStream fin=new FileInputStream("C:/WorkSpace/FileInput/ICP-Billing/programStatus.txt");
    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
    	

    	

    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy HH:mm:ss");
    	Date date = new Date();
    	System.out.println(dateFormat.format(date));
    	System.out.println(date.getTime());
    	String dateSent = "1466524800";
    	String leftInv = "Null";
    	int x = 1;

    	//checkProgramstatus
    	String strLine = "Null";
    	String progStat = "Null";
    	
    	String strLine2 = "Null";
    	
    	while ((strLine = br.readLine()) != null)  
   		{
    		System.out.println("Program status : " + strLine);
    		progStat = strLine;	
    		break;
   		}
    	
    	//System.out.println("Program status : " + strLine);
    	

    	
    	
    	if(progStat.indexOf("Run") > -1)
    	{
    		//Do nothing    
    		//bleh gune javascript to auto refresh
    	}
    	else
    	{

        	PrintStream p1;
        	FileOutputStream out1 = new FileOutputStream("C:/WorkSpace/FileInput/ICP-Billing/output.txt");
        	p1 = new PrintStream(out1);
    		
        	//One minute would be (60*1000) = 60000 milliseconds.
            try {
            	
            	PrintStream pStat;
            	FileOutputStream outStat = new FileOutputStream("C:/WorkSpace/FileInput/ICP-Billing/programStatus.txt");
            	pStat = new PrintStream(outStat);
            	
            	pStat.print(dateSent + " is Running");
            	
                while (x<10) 
                {
                    //System.out.println(dateFormat.format(date));
                	date = new Date();
                	leftInv = tr.getLeftInvoice(dateSent);
                    System.out.println(x +") Left to bill = " + leftInv + " | " + dateFormat.format(date));
                    p1.println(x +") Left to bill = " + leftInv + " | " + dateFormat.format(date));
                    Thread.sleep(10 * 1000);
                    x++;
                }
    	        } catch (InterruptedException e) 
    	        {
    	            e.printStackTrace();
    	        }
            
        	PrintStream pStat1;
        	FileOutputStream outStat1 = new FileOutputStream("C:/WorkSpace/FileInput/ICP-Billing/programStatus.txt");
        	pStat1 = new PrintStream(outStat1);
            pStat1.print("Done");
            pStat1.close();
            p1.close();
    	}
    	
    	br.close();
    	
    	//p1.close();
    	

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
        //System.out.println("LEFT_TO_INVOICED = " + status);
        
        
        
        return status;
		
	}
	
	public void StartCheckInvBal(String dateIn) throws FileNotFoundException
	{

		Date date = new Date();
		String leftInv = "Null";
		int x = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy HH:mm:ss");
		
    	PrintStream p1;
    	FileOutputStream out1 = new FileOutputStream(res.getString("file29.CheckInvBill1"));
    	p1 = new PrintStream(out1);
		
    	//One minute would be (60*1000) = 60000 milliseconds.
        try {
        	
        	PrintStream pStat;
        	FileOutputStream outStat = new FileOutputStream(res.getString("file30.CheckInvBill2"));
        	pStat = new PrintStream(outStat);
        	
        	pStat.print(dateIn + " is Running");
        	
            while (x<3) 
            {
                //System.out.println(dateFormat.format(date));
            	date = new Date();
            	leftInv = this.getLeftInvoice(dateIn);
                System.out.println(x +") Left to bill = " + leftInv + " | " + dateFormat.format(date));
                p1.println(x +") Left to bill = " + leftInv + " | " + dateFormat.format(date));
                Thread.sleep(1 * 1000);
                x++;
            }
	        } catch (InterruptedException e) 
	        {
	            e.printStackTrace();
	        }
        
    	PrintStream pStat1;
    	FileOutputStream outStat1 = new FileOutputStream(res.getString("file30.CheckInvBill2"));
    	pStat1 = new PrintStream(outStat1);
        pStat1.print("Done");
        pStat1.close();
        p1.close();
	
	}
}
