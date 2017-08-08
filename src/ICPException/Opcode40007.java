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

public class Opcode40007 {

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
		String outputReturn = "Null";
		
		Opcode40007 op4 = new Opcode40007();
		
		String serviceAccPoidinBRM = "Null";
		
		op4.checkTransferServiceFList("mainopcode");
		
		if(!op4.getServiceTransfer().equalsIgnoreCase("Null"))
		{
			//check service tied 1st
			String BAtiedInBRM = "Null";
			String BAFrom = "Null";
			String BATo = "Null";
			
			serviceAccPoidinBRM = op4.serviceTiedToBA(op4.getServiceTransfer());
			
			if(!serviceAccPoidinBRM.equalsIgnoreCase("Null"))
			{
				if(op4.getAccPoidFrom().equalsIgnoreCase(serviceAccPoidinBRM))
				{
					System.out.println("acc poid sent same with in the FList. service tied is right, check billinfo");
					//acc poid sent same with in the FList. service tied is right, check billinfo
					outputReturn = "[BRM] Service tied to right BA and transfer service not success. BRM to check other error";
				}
				else if(op4.getAccPoidTo().equalsIgnoreCase(serviceAccPoidinBRM))
				{
					System.out.println("acc poid sent same with TO acc obj. Means service already transfer. OSM to bypass");
					//acc poid sent same with TO acc obj. Means service already transfer. OSM to bypass
					String loginReturn = "Null";
					
					BATo = op4.checkAccountNumber(serviceAccPoidinBRM);
					BAFrom = op4.checkAccountNumber(op4.getAccPoidFrom());
					loginReturn = op4.serviceLoginInBRM(op4.serviceTransfer);
					
					outputReturn = "[OSM] Service "  + op4.getServiceTransferString() + "(Login:" + loginReturn + ") already transfer from BA " + BAFrom + " to BA " + BATo + ". OSM to bypass";
				}
				else
				{
					System.out.println("acc poid sent not same in the FList either FROM or TO. Get BA and ask L2 to check");
					//acc poid sent not same in the FList either FROM or TO. Get BA and ask L2 to check
					String loginReturn = "Null";
					
					BAtiedInBRM = op4.checkAccountNumber(serviceAccPoidinBRM);
					BAFrom = op4.checkAccountNumber(op4.getAccPoidFrom());
					loginReturn = op4.serviceLoginInBRM(op4.serviceTransfer);
					
					outputReturn = "[Siebel] Try to transfer service but "  + op4.getServiceTransferString() + "(Login:" + loginReturn + ") tied to BA " + BAtiedInBRM + " in BRM. Not as sent BA " + BAFrom;
										
				}
			}
			else
			{
				outputReturn = "[EAI] Try to transfer service but " + op4.getServiceTransferString() + " not found in BRM. Please re-create service, then re-trigger" ;
			}
			
			
		}
		else
		{
			//System.out.println("[EAI] Failed to transfer service due to no service poid sent in the FList. Please fill in service to be transfer, then OSM to re-trigger.");
			outputReturn = "[EAI] Failed to transfer service due to no service poid sent in the FList. Please fill in service to be transfer, then OSM to re-trigger.";
		}
		
		System.out.println(outputReturn);
		
	}
	
	public void checkTransferServiceFList(String fileURL) throws Exception
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
	   	String accountPoidFrom = "Null";
	   	String accountPoidTo = "Null";
	   	String billInfoSent = "Null";
    	

    	boolean servicePoidCheck = false;
    	boolean accountPoidFromCheck = false;
    	boolean accountPoidToCheck = false;
    	boolean billInfoSentCheck = false;
    	
    	//boolean dateCheck = false;
    	//boolean discCheck = false;
    	
    	while ((strLine = br.readLine()) != null)  
   		{


   			if(strLine.indexOf("<POID>") > -1 && servicePoidCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				servicePoid = strLine.substring(opBegin+1, opEnd);
   				servicePoidCheck = true;
   				System.out.println("Service Poid Sent - " + servicePoid);
   			}
   			
   			if(strLine.indexOf("<FROM_OBJ>") >-1 && accountPoidFromCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				accountPoidFrom = strLine.substring(opBegin+1, opEnd);
   				accountPoidFromCheck = true;
   				System.out.println("Account Poid FROM - " + accountPoidFrom);
   			}
   			
   			if(strLine.indexOf("<TO_OBJ>") >-1 && accountPoidToCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				accountPoidTo = strLine.substring(opBegin+1, opEnd);
   				accountPoidToCheck = true;
   				System.out.println("Account Poid TO - " + accountPoidTo);
   			}
   			
   			if(strLine.indexOf("<BILLINFO_OBJ>") >-1 && billInfoSentCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				billInfoSent = strLine.substring(opBegin+1, opEnd);
   				billInfoSentCheck = true;
   				System.out.println("BillInfo Poid - " + billInfoSent);
   			}
   			

   			

   		}	
   			
    	
    	
    	
	    	if(!servicePoid.equalsIgnoreCase("Null"))
	    	{
	    		
	    	String start = "Null";
	    	String type = "Null";
	    	String prodPoid = "Null";
	    	
	    	this.setServiceTransferString(servicePoid);
	    	
	    	StringTokenizer st1 = new StringTokenizer(servicePoid, " ");			
	    	start = st1.nextToken();
	    	type = st1.nextToken();
	    	prodPoid = st1.nextToken();
	    	
	    	System.out.println("Service Poid -> " + prodPoid);
	    	
	    	
	    	this.setServiceTransfer(prodPoid);
	    	}
	    	else
	    	{
	    		
	    		this.setServiceTransfer("Null");	
	    	}
    	
    	
   		//-----------------------
    	
   	    	if(!accountPoidFrom.equalsIgnoreCase("Null"))
   	    	{
   	    	String start = "Null";
   	    	String type = "Null";
   	    	String prodPoid = "Null";
   	    	
   	    	
   	    	StringTokenizer st1 = new StringTokenizer(accountPoidFrom, " ");			
   	    	start = st1.nextToken();
   	    	type = st1.nextToken();
   	    	prodPoid = st1.nextToken();
   	    	
   	    	System.out.println("Account Poid From -> " + prodPoid);
   	    	
   	    	
   	    	this.setAccPoidFrom(prodPoid);
   	    	}
   	    	else
   	    	{
   	    		
   	    		this.setAccPoidFrom("Null");	
   	    	}
   	    	
   	    	
   	   //-----------------------
   	    	
   	    	
   	    	if(!accountPoidTo.equalsIgnoreCase("Null"))
   	    	{
   	    	String start = "Null";
   	    	String type = "Null";
   	    	String prodPoid = "Null";
   	    	
   	    	
   	    	StringTokenizer st1 = new StringTokenizer(accountPoidTo, " ");			
   	    	start = st1.nextToken();
   	    	type = st1.nextToken();
   	    	prodPoid = st1.nextToken();
   	    	
   	    	System.out.println("Account Poid To -> " + prodPoid);
   	    	
   	    	
   	    	this.setAccPoidTo(prodPoid);
   	    	}
   	    	else
   	    	{
   	    		
   	    		this.setAccPoidTo("Null");	
   	    	}
    	
    	//-----------------------
   	    	
   	    	if(!billInfoSent.equalsIgnoreCase("Null"))
   	    	{
   	    	String start = "Null";
   	    	String type = "Null";
   	    	String prodPoid = "Null";
   	    	
   	    	
   	    	StringTokenizer st1 = new StringTokenizer(billInfoSent, " ");			
   	    	start = st1.nextToken();
   	    	type = st1.nextToken();
   	    	prodPoid = st1.nextToken();
   	    	
   	    	System.out.println("billInfoSent Poid To -> " + prodPoid);
   	    	
   	    	
   	    	this.setBillInfoObj(prodPoid);
   	    	}
   	    	else
   	    	{
   	    		
   	    		this.setBillInfoObj("Null");	
   	    	}
   	    	

	   
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
	
	public String serviceLoginInBRM(String svcPoid)
	{
		String status = "Null";
		
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery ("select login from service_t where poid_id0 = " + svcPoid);
    
            if(rs.next())
            	status = rs.getString("login");
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
    	
    public String serviceTransfer = "Null";
    public String accPoidFrom = "Null";
    public String accPoidTo = "Null";
    public String billInfoObj = "Null";
    public String BANumberFrom = "Null";
    public String BANumberTo = "Null";
    public String BANumberTiedInBRM = "Null";
    public String serviceTransferString = "Null";


	public String getServiceTransferString() {
		return serviceTransferString;
	}

	public void setServiceTransferString(String serviceTransferString) {
		this.serviceTransferString = serviceTransferString;
	}

	public String getBANumberFrom() {
		return BANumberFrom;
	}

	public void setBANumberFrom(String bANumberFrom) {
		BANumberFrom = bANumberFrom;
	}

	public String getBANumberTo() {
		return BANumberTo;
	}

	public void setBANumberTo(String bANumberTo) {
		BANumberTo = bANumberTo;
	}

	public String getBANumberTiedInBRM() {
		return BANumberTiedInBRM;
	}

	public void setBANumberTiedInBRM(String bANumberTiedInBRM) {
		BANumberTiedInBRM = bANumberTiedInBRM;
	}

	public String getServiceTransfer() {
		return serviceTransfer;
	}

	public void setServiceTransfer(String serviceTransfer) {
		this.serviceTransfer = serviceTransfer;
	}

	public String getAccPoidFrom() {
		return accPoidFrom;
	}

	public void setAccPoidFrom(String accPoidFrom) {
		this.accPoidFrom = accPoidFrom;
	}

	public String getAccPoidTo() {
		return accPoidTo;
	}

	public void setAccPoidTo(String accPoidTo) {
		this.accPoidTo = accPoidTo;
	}

	public String getBillInfoObj() {
		return billInfoObj;
	}

	public void setBillInfoObj(String billInfoObj) {
		this.billInfoObj = billInfoObj;
	}
    
    
    

	
	

}
