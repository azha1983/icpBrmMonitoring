package ICPException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.StringTokenizer;


public class OpcodeSearch {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
    public static void main( String[] args ) throws Exception 
    {
    	String outputReturn = "Null";
    	
    	OpcodeSearch ops = new OpcodeSearch();
    	
    	ops.checkCreateAccOpcode("mainopcode");
    	
    	if(ops.isCheckOpcodeSearchType())
    	{
    		//outputReturn = "Check proceed";
    		
    		String accountPoidInDB = "Null";
    		
    		accountPoidInDB = ops.serviceTiedToBA(ops.getSvcPoidSent());
    		
    		if(accountPoidInDB.equalsIgnoreCase("Null"))
    		{
    			outputReturn = "[EAI] " + ops.getSvcPoidSentString() + " not found in BRM. Please re-create service then re-trigger";
    			
    		}
    		else
    		{
    			if(accountPoidInDB.equalsIgnoreCase(ops.getAccPoidSent()))
    			{
    				outputReturn = "[OSM] Service poid sent tied to right BA. Should be no problem. Please re-trigger";
    			}
    			else
    			{
    				String loginInBRM = "Null";
    				String BAtiedinBRM = "Null";
    				String BAsent = "Null";
    				
    				BAtiedinBRM = ops.checkAccountNumber(accountPoidInDB);
    				BAsent = ops.checkAccountNumber(ops.getAccPoidSent());
    				loginInBRM = ops.serviceLoginInBRM(ops.getSvcPoidSent());
    				
    				
    				outputReturn = "[Siebel] Service " + ops.getSvcPoidSentString() + " (login:" + loginInBRM + ") tied to BA " + BAtiedinBRM + " in BRM. Not as sent BA " + BAsent;
    			}
    			
    		}
    		
    	}
    	else
    	{
    		outputReturn = "[BRM] Search opcode others. BRM to check";
    	}
    	
    	System.out.println(outputReturn);
    	
    }
    
    
  
	public void checkCreateAccOpcode(String fileURL) throws IOException
	{
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
    	
	   	String accountPoid = "Null";
	   	String servicePoid = "Null";
 
    	boolean accountPoidCheck = false;
	   	boolean servicePoidCheck = false;
	   	
	   	boolean checkSearchType = false;

    	
    	while ((strLine = br.readLine()) != null)  
   		{

 			    if(strLine.indexOf("select X from /profile where F1 = V1 and F2 = V2 and F3 = V3") >-1)
	   			{
 			    	this.setCheckOpcodeSearchType(true);
	   			}
    		
    		
    		
 	 			if(strLine.indexOf("<ACCOUNT_OBJ>") >-1 && accountPoidCheck == false)
 	   			{
 	   		    	int opBegin = 0;
 	   		    	int opEnd = 0;
 	   		    	
 	   				opBegin = strLine.indexOf(">");
 	   				opEnd = strLine.indexOf("</");
 	   				
 	   				accountPoid = strLine.substring(opBegin+1, opEnd);
 	   				accountPoidCheck = true;
 	   				System.out.println("Account Poid Sent - " + accountPoid);
 	   			}
 	 			
 	   			if(strLine.indexOf("</SERVICE_OBJ>") > -1 && servicePoidCheck == false)
 	   			{
 	   		    	int opBegin = 0;
 	   		    	int opEnd = 0;

 	   				opBegin = strLine.indexOf(">");
 	   				opEnd = strLine.indexOf("</");
 	   				
 	   				servicePoid = strLine.substring(opBegin+1, opEnd);
 	   				servicePoidCheck = true;
 	   				System.out.println("Service Poid Sent - " + servicePoid);
 	   			}
 			

 			
    		
		
    		a++;
   		}
    	

    	this.setSvcPoidSentString(servicePoid);
    	
       	if(!accountPoid.equalsIgnoreCase("Null"))
    	{
    		
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	//this.setSvcPoidSentString(accountPoid);
    	
    	StringTokenizer st1 = new StringTokenizer(accountPoid, " ");			
    	start = st1.nextToken();
    	type = st1.nextToken();
    	prodPoid = st1.nextToken();
    	
    	System.out.println("Account Poid -> " + prodPoid);
    	
    	
    	this.setAccPoidSent(prodPoid);
    	}
    	else
    	{
    		
    		this.setAccPoidSent("Null");	
    	}
       	
       	
    	if(!servicePoid.equalsIgnoreCase("Null"))
    	{
    		
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	
    	
    	StringTokenizer st1 = new StringTokenizer(servicePoid, " ");			
    	start = st1.nextToken();
    	type = st1.nextToken();
    	prodPoid = st1.nextToken();
    	
    	System.out.println("Service Poid -> " + prodPoid);
    	
    	
    	this.setSvcPoidSent(prodPoid);
    	}
    	else
    	{
    		
    		this.setSvcPoidSent("Null");	
    	}
    	
    	
 
    	
    	

    	
    	
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
        System.out.println("Service login in BRM = " + status);
        
        
        
        return status;
		
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
        System.out.println("BA tied in BRM = " + status);
        
        
        
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
        System.out.println("Account no = " + status);
        
        
        
        return status;
		
	}

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
    
    
    private String dbasePassPIN = "DBpin_123";
    private String dbaseUserPIN = "pin";
    private String dbaseIpPIN = "10.41.68.94";
    
    public String svcPoidSent = "Null";
    public String accPoidSent = "Null";
    
    public String svcPoidSentString = "Null";
    
    public boolean checkOpcodeSearchType = false;
    
    
    
    
 	 public boolean isCheckOpcodeSearchType() {
		return checkOpcodeSearchType;
	}



	public void setCheckOpcodeSearchType(boolean checkOpcodeSearchType) {
		this.checkOpcodeSearchType = checkOpcodeSearchType;
	}



	public String getSvcPoidSentString() {
		return svcPoidSentString;
	}



	public void setSvcPoidSentString(String svcPoidSentString) {
		this.svcPoidSentString = svcPoidSentString;
	}



	public String getSvcPoidSent() {
		return svcPoidSent;
	}



	public void setSvcPoidSent(String svcPoidSent) {
		this.svcPoidSent = svcPoidSent;
	}



	public String getAccPoidSent() {
		return accPoidSent;
	}



	public void setAccPoidSent(String accPoidSent) {
		this.accPoidSent = accPoidSent;
	}

}
