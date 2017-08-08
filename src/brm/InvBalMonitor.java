package brm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class InvBalMonitor implements Runnable  {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
	String dateIn;
    private String dbasePassPIN = "DBpin_123";
    private String dbaseUserPIN = "pin";
   //private String dbaseIpPIN = "10.41.68.90";
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
            System.out.println("Error create new connection to database, " + e.getMessage());
            return null;
        }
        return con;
    }
    
	   public static void main(String args[]) throws ParseException
	   {
		   InvBalMonitor i = new InvBalMonitor();
		   String dateSent = "1489377600";
		   i.getLeftInvoice(dateSent);
	   }

	public String getDateIn() {
		return dateIn;
	}

	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}
	
	public void generate(){
		Thread myThread = new Thread(this);
		myThread.start() ;
		}
	
	public void stopThread()
	{
		Thread myThread = new Thread(this);
		//myThread.
		myThread = null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		


		Date date = new Date();
		String leftInv = "Null";
		int x = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy HH:mm:ss");
		 try {
    	PrintStream p1;
    	FileOutputStream out1 = new FileOutputStream(res.getString("file29.CheckInvBill1"));
    	p1 = new PrintStream(out1);
		
    	//One minute would be (60*1000) = 60000 milliseconds.
       
        	
        	PrintStream pStat;
        	FileOutputStream outStat = new FileOutputStream(res.getString("file30.CheckInvBill2"));
        	pStat = new PrintStream(outStat);
        	
        	pStat.print(dateIn + " is Running");
        	//p1.println(x +") Left to Invoice = " + this.getLeftInvoice(dateIn) + " | " + dateFormat.format(date));
        	
        	//int tp = 0;
        	//int invBal = 0;
        	
        	/*leftInv = this.getLeftInvoice(dateIn);
        	invBal = Integer.parseInt(leftInv);
        	
        	if(invBal>100000)
        	{
        		tp = invBal/60;
	        	while(x<invBal)
	        	{
	        		
	        		
	        		p1.println(x+1 +") Left to invoice = " + leftInv + " | " + dateFormat.format(date) + " | TP = " + tp + " per minute");
	        		
	        		if(invBal < 10000)
	        		break;
	        	}
        	
        	}        	
        	else if(invBal>10000)
        	{
	        	while(x<invBal)
	        	{
	        		
	        		
	        		
	        		if(invBal < 1000)
	        		break;
	        	}
        	
        	}
        	else
        	{
	        	while(x<invBal)
	        	{
	        		
	        		
	        		
	        		//if(invBal < 1000)
	        		//break;
	        	}
        	}
        	*/
        	int tempValue = 0;
        	double tpPerminute = 0;
        	int leftInvTemp = 0;
        	
        	/*int temp1stTP = 0;
        	int templastTP = 0;
        	double tpPerHour = 0;
        	
        	int templeftBill10_1 = 0;
        	int templeftBill10_2 = 0;
        	double tpPer10 = 0;*/
        	
        	
            while (leftInvTemp>0)  
            {
                //System.out.println(dateFormat.format(date));
                //if(x<1)
                //Thread.sleep(60 * 10);
                //else
                
                
            	date = new Date();
            	leftInv = this.getLeftInvoice(dateIn);
            	
            	//tp=Integer.parseInt(leftInv)/60;
            	
                System.out.println("Current Left to invoice = " + leftInv + " | " + dateFormat.format(date));
                
                leftInvTemp = Integer.parseInt(leftInv);
                //Thread.sleep(60 * 1000);
                if(x>0)
                {
                	tpPerminute = tempValue-leftInvTemp;
                	p1.println(x+1 +") Left to invoice |" + leftInv + "| " + dateFormat.format(date) + "| TP perMinute = " + tpPerminute);
                	
                  /*	if(x == 9)
                	{
                		templeftBill10_2 = leftInvTemp;
                		tpPer10 = (temp1stTP-templeftBill10_2)/10;
                		
                		p1.println(temp1stTP + " - " + templeftBill10_2 + " /10 ");
                		
                		p1.println("------- For 10 minutes - Total TP per minute = " + tpPer10 + " -------");
                	}
                	
                	if(x == 10 || x == 20 || x == 30 || x == 40 || x == 50 )
                	{
                		templeftBill10_2 = leftInvTemp;
                	}
                	
                	if(x == 19 || x == 29 || x == 39 || x == 49 || x == 59 )
                	{
                		templeftBill10_1 = leftInvTemp;
                		tpPer10 = (templeftBill10_2-templeftBill10_1)/10;
                		
                		p1.println(templeftBill10_2 + " - " + templeftBill10_1 + " /10 ");
                		               		
                		p1.println("------- For 10 minutes - Total TP per minute = " + tpPer10 + " -------");
                	}
                	
                	if(x==59)
                		{
                		templastTP = Integer.parseInt(leftInv);
                		tpPerHour = (temp1stTP-templastTP)/60;
                		System.out.println("------" + templastTP);
                		System.out.println("------" + temp1stTP);
                		
                		
                		p1.println("------- For 1 hour - Total TP per minute is = " + tpPerHour + " -------");
                		}*/
                }
                else
                {
                	p1.println(x+1 +") Left to invoice |" + leftInv + "| " + dateFormat.format(date));
                	//temp1stTP = Integer.parseInt(leftInv);
                	
                }
                
                if(leftInvTemp == 0)
                    break;
                
                Thread.sleep(60 * 1000);
                
                
                tempValue = leftInvTemp;
                

                
                //if(x==59)
                //	break;
                
                x++;
            }

        
    	PrintStream pStat1;
    	FileOutputStream outStat1 = new FileOutputStream(res.getString("file30.CheckInvBill2"));
    	pStat1 = new PrintStream(outStat1);
        pStat1.print("Done");
        pStat1.close();
        p1.close();
        } catch (InterruptedException e) 
        {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
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
