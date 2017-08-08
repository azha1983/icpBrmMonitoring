package ICPException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;

import org.xml.sax.InputSource;



public class CheckLoginFromEAI {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
    public static void main(String args[])throws Exception
    {
    	
    	CheckLoginFromEAI t = new CheckLoginFromEAI();
    	
    	//HashMap h=new HashMap();                        
    	FileInputStream fin=new FileInputStream("mainopcode");
    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
    	
    	PrintStream p1;
    	FileOutputStream out1 = new FileOutputStream(res.getString("file21.xmlTemp"));
    	p1 = new PrintStream(out1);
    	
    	
    	String strLine = "";
    	String output = "";

    	boolean test = false;
    	
    	int a =1;
    	
    	String login = "";
    	String accObj = "";
    	String poid_type = "";
    	String serviceType = "";
    	
    	int opBegin = 0;
    	int opEnd = 0;
    	boolean opcodeCheck = false;
    	
    	while ((strLine = br.readLine()) != null)  
		{

			if(strLine.equalsIgnoreCase("<flist>"))
			test = true;		
			
			if(strLine.equalsIgnoreCase("</flist>]]>"))
			{			
					test = false;	
					output = "</flist>";
					p1.println(output);
					break;
			}
			
			if(test)
		    {
				output = strLine + "\n";				
				p1.println(output);
			}
			
			
			if(strLine.indexOf("</ACCOUNT_OBJ>") > -1 && opcodeCheck == false)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				accObj = strLine.substring(opBegin+1, opEnd);
				//opcodeCheck = true;
				System.out.println("Account Obj - " +accObj);
			}
			
			
			if(strLine.indexOf("</LOGIN>") > -1 && opcodeCheck == false)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				login = strLine.substring(opBegin+1, opEnd);
				opcodeCheck = true;
				System.out.println("Login - " + login);
			}
			
			if(strLine.indexOf("</SERVICE_OBJ>") > -1)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				poid_type = strLine.substring(opBegin+1, opEnd);
				
				if(poid_type.indexOf("telephony") > -1)
					serviceType = "/service/telephony";
				else if(poid_type.indexOf("tmm_streamyx") > -1)
					serviceType = "/service/tmm_streamyx";
				else if(poid_type.indexOf("hotspot") > -1)
					serviceType = "/service/tmm_hotspot";
				else if(poid_type.indexOf("tm_calling_card") > -1)
					serviceType = "/service/tm_calling_card";
				else if(poid_type.indexOf("tmm_wholesale_dsl") > -1)
					serviceType = "/service/tmm_wholesale_dsl"; 
				else if(poid_type.indexOf("tmm_hypptv") > -1)
					serviceType = "/service/tmm_hypptv";
				else if(poid_type.indexOf("tmm_webhosting") > -1)
						serviceType = "/service/tmm_webhosting";
				else if(poid_type.indexOf("tmm_audio_conferencing") > -1)
						serviceType = "/service/tmm_audio_conferencing";
				else
					serviceType = "Cannot Recognize";
				//opcodeCheck = true;
				System.out.println("Service - " + serviceType);
			}
			
			
		
		}
    	
    	
    	
		Vector checkServiceT = new Vector();
		checkServiceT = t.getServiceDetail(login,accObj,serviceType);
		
		
		//for(int y=0;y<checkServiceT.size();y++)
		//{
		
		//}
    	
    	
    	
 	
    	//System.out.println("----------");
    	//System.out.println("----------");
 
  	
    }
    
    
    static  String readFile( String file ) throws Exception {
        BufferedReader reader = new BufferedReader( new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
        }

        return stringBuilder.toString();
    }
    
    private Connection getDBConn(String dbLogin, String dbPassword, String dbIp)
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
    
    private String getBAfromAccNo(String poid)
    {

        String status = "Null";
        Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select account_no from account_t where poid_id0 = " + poid);
            if(rs.next())
            	status = rs.getString("account_no");
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
    
    private String getBAStatusfromAccNo(String poid)
    {

        String status = "Null";
        Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select status from account_t where poid_id0 = " + poid);
            if(rs.next())
            	status = rs.getString("status");
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
    
    public Vector getServiceDetail(String login,String accObj,String serviceType)
    {
        Vector svc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try
		{
			String accPoid = "null";
			String dateCreated = "null";
			String poidType = "null";
			String servicePoid = "null";
			String status = "null";
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();
			
			String sqlStmt = "select login,poid_type,poid_id0,unix_ora_ts_conv(created_t) as created_date,status,account_obj_id0 from service_t "
					+ "where login = '" + login + "' and poid_type = '" + serviceType + "' and (status = '10100' or status = '10102')";
            Vector LineInfo;
           // System.out.println("------------query service_t");
            
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); svc.add(LineInfo))
			{
				//System.out.println(">!------------------------1");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("login"));
				LineInfo.add(rs.getString("poid_type"));
				LineInfo.add(rs.getString("poid_id0"));
				LineInfo.add(rs.getString("created_date"));
				LineInfo.add(rs.getString("status"));
				LineInfo.add(rs.getString("account_obj_id0"));
			
				System.out.println("<>-------------------<>");
				System.out.println("Login : " + rs.getString("login"));
				System.out.println("Service : " + rs.getString("poid_type") + " " + rs.getString("poid_id0"));
				System.out.println("Created : " + rs.getString("created_date"));
				System.out.println("Status : " + rs.getString("status"));
				System.out.println("Account_Obj : " + rs.getString("account_obj_id0"));
				System.out.println("<>-------------------<>");
				
				accPoid =  rs.getString("account_obj_id0");
				dateCreated = rs.getString("created_date");
				poidType = rs.getString("poid_type");
				status = rs.getString("status");
				servicePoid = rs.getString("poid_type") + " " + rs.getString("poid_id0");
				//x++;
			}
			
			if(status.equalsIgnoreCase("null"))
			{
				System.out.println("No login created in Service T");
			}
			else
			{
			
			String statusReturn = "null";
			String accountNo = getBAfromAccNo(accPoid);
			
			if(status.equalsIgnoreCase("10100"))
				statusReturn = "Active";
			else if(status.equalsIgnoreCase("10102"))
				statusReturn = "Inactive";
			else if(status.equalsIgnoreCase("10103"))
				statusReturn = "Closed";
			else
				statusReturn = "Unknown";
			
			//System.out.println("-----------------------");
			if(accObj.indexOf(accPoid) > -1)
			{
			System.out.println("Yes - Account Poid found same with in DB");
			System.out.println("======================");
			System.out.println("[EAI] Service already created under this account on " + dateCreated + " with poid [" + servicePoid + "] and service status " + statusReturn);
			}
			else
			{
			System.out.println("No - Account Poid found NOT same with in DB");
			System.out.println("======================");
			System.out.println("[Siebel] Try to add service " + poidType + " with login " + login + " but the login already tied to BA " + accountNo + " in BRM");
			System.out.println("======================");
			}
			
			
			}
			
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
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return svc;
    }
    
    
    
 	//private String dbasePass = "DBtmpin_123";
  //  private String dbaseUser = "tmpin_batch";
  //  private String dbaseIP = "10.41.68.53";
    
    private String dbasePass = "DBpin_123";
    private String dbaseUser = "pin";
    private String dbaseIP = "10.41.68.90";
    
    
    
 public void err_Bad_value(String fileURL) throws Exception
 {
	 	
 	CheckLoginFromEAI t = new CheckLoginFromEAI();
 	                  
 	FileInputStream fin=new FileInputStream(fileURL);
 	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
 	
 	PrintStream p1;
 	FileOutputStream out1 = new FileOutputStream(res.getString("file21.xmlTemp"));
 	p1 = new PrintStream(out1);
 	
 	
 	String strLine = "";
 	String output = "";

 	boolean test = false;
 	
 	int a =1;
 	
	String login = "";
	String accObj = "";
	String poid_type = "";
	String serviceType = "";
 	
 	int opBegin = 0;
 	int opEnd = 0;
 	boolean opcodeCheck = false;
 	
 	while ((strLine = br.readLine()) != null)  
		{

			if(strLine.equalsIgnoreCase("<flist>"))
			test = true;		
			
			if(strLine.equalsIgnoreCase("</flist>]]>"))
			{			
					test = false;	
					output = "</flist>";
					p1.println(output);
					break;
			}
			
			if(test)
		    {
				output = strLine + "\n";				
				p1.println(output);
			}
			
			
			if(strLine.indexOf("</ACCOUNT_OBJ>") > -1 && opcodeCheck == false)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				accObj = strLine.substring(opBegin+1, opEnd);
				//opcodeCheck = true;
				System.out.println("Account Obj - " +accObj);
			}
			
			
			if(strLine.indexOf("</LOGIN>") > -1 && opcodeCheck == false)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				login = strLine.substring(opBegin+1, opEnd);
				opcodeCheck = true;
				System.out.println("Login - " + login);
			}
			
			if(strLine.indexOf("</SERVICE_OBJ>") > -1)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				poid_type = strLine.substring(opBegin+1, opEnd);
				
				if(poid_type.indexOf("telephony") > -1)
					serviceType = "/service/telephony";
				else if(poid_type.indexOf("tmm_streamyx") > -1)
					serviceType = "/service/tmm_streamyx";
				else if(poid_type.indexOf("hotspot") > -1)
					serviceType = "/service/tmm_hotspot";
				else if(poid_type.indexOf("tm_calling_card") > -1)
					serviceType = "/service/tm_calling_card";
				else if(poid_type.indexOf("tmm_wholesale_dsl") > -1)
					serviceType = "/service/tmm_wholesale_dsl"; 
				else if(poid_type.indexOf("tmm_hypptv") > -1)
					serviceType = "/service/tmm_hypptv";
				else if(poid_type.indexOf("tmm_webhosting") > -1)
						serviceType = "/service/tmm_webhosting";
				else if(poid_type.indexOf("tmm_audio_conferencing") > -1)
						serviceType = "/service/tmm_audio_conferencing";
				else
					serviceType = "Cannot Recognize";
				//opcodeCheck = true;
				System.out.println("Service - " + serviceType);
			}
			
			
		
		}
 	
 	
 	 this.setAccObjX(accObj);
 	 this.setLoginX(login);
 	 this.setPoid_typeX(poid_type);
 	 this.setServiceTypeX(serviceType);
 	
		//Vector checkServiceT = new Vector();
		//checkServiceT = t.getServiceDetail(login,accObj,serviceType);

 
 }
 
 
 public String getServiceDetailFromTable(String login,String accObj,String serviceType)
 {
  	String returnValue = "Null";
  	Vector svc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		try
		{
			String accPoid = "null";
			String dateCreated = "null";
			String poidType = "null";
			String servicePoid = "null";
			String status = "null";
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();
			
			String sqlStmt = "select login,poid_type,poid_id0,unix_ora_ts_conv(created_t) as created_date,status,account_obj_id0 from service_t "
					+ "where login = '" + login + "' and poid_type = '" + serviceType + "' and (status = '10100' or status = '10102')";
      
			Vector LineInfo;
     // System.out.println("------------query service_t");
      
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); svc.add(LineInfo))
			{
				//System.out.println(">!------------------------1");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("login"));
				LineInfo.add(rs.getString("poid_type"));
				LineInfo.add(rs.getString("poid_id0"));
				LineInfo.add(rs.getString("created_date"));
				LineInfo.add(rs.getString("status"));
				LineInfo.add(rs.getString("account_obj_id0"));
			
				System.out.println("<>-------------------<>");
				System.out.println("Login : " + rs.getString("login"));
				System.out.println("Service : " + rs.getString("poid_type") + " " + rs.getString("poid_id0"));
				System.out.println("Created : " + rs.getString("created_date"));
				System.out.println("Status : " + rs.getString("status"));
				System.out.println("Account_Obj : " + rs.getString("account_obj_id0"));
				System.out.println("<>-------------------<>");
				
				accPoid =  rs.getString("account_obj_id0");
				dateCreated = rs.getString("created_date");
				poidType = rs.getString("poid_type");
				status = rs.getString("status");
				servicePoid = rs.getString("poid_type") + " " + rs.getString("poid_id0");
				//x++;
			}
			
			if(status.equalsIgnoreCase("null"))
			{
				
				System.out.println("No login created in Service T");
				
				String aliasCheck = "Null";
				aliasCheck = this.checkAlias_t(login);
				
				if(!aliasCheck.equalsIgnoreCase("Null"))
				{
					String aliasMainLogin = "Null";
					
					aliasMainLogin = this.checkAlias_t_service_login(aliasCheck);
					
					if(!aliasMainLogin.equalsIgnoreCase("Null"))
					{
						returnValue = "[Siebel] Try to add service " + serviceType + " with login " + login + " but the login is a PRIME LINK number in BRM with main service login is " + aliasMainLogin;
					}
					else
					{
						returnValue = "Try to add service " + serviceType + " with login " + login + " but the login already exist in BRM ALIAS_T table with no main service login";
					}
				}
				else
				{
					returnValue = "No login created in Service T. BRM to check";
				}
				
			}
			else
			{
			
			String statusReturn = "null";
			String accountNo = getBAfromAccNo(accPoid);
			
			if(status.equalsIgnoreCase("10100"))
				statusReturn = "Active";
			else if(status.equalsIgnoreCase("10102"))
				statusReturn = "Inactive";
			else if(status.equalsIgnoreCase("10103"))
				statusReturn = "Closed";
			else
				statusReturn = "Unknown";
			
			//System.out.println("-----------------------");
			if(accObj.indexOf(accPoid) > -1)
			{
			System.out.println("Yes - Account Poid found same with in DB");
			System.out.println("======================");
			System.out.println("[EAI] Service already created under this account on " + dateCreated + " with poid [" + servicePoid + "] and service status " + statusReturn);
			
			returnValue = "[EAI] Service already created under this account on " + dateCreated + " with poid [" + servicePoid + "] and service status " + statusReturn;
			}
			else
			{
			System.out.println("No - Account Poid found NOT same with in DB");
			System.out.println("======================");
			System.out.println("[Siebel] Try to add service " + poidType + " with login " + login + " but the login already tied to BA " + accountNo + " in BRM");
			System.out.println("======================");
			
			returnValue = "[Siebel] Try to add service " + poidType + " with login " + login + " but the login already tied to BA " + accountNo + " in BRM";
			}
			
			
			}
			
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
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return returnValue;
}
 
 
 private String checkAlias_t(String login)
 {
     String status = "Null";
     Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
     ResultSet rs = null;
     Statement stmt = null;
     try
     {
         stmt = pinConn.createStatement();
         rs = stmt.executeQuery ("select obj_id0 from service_alias_list_t where name = '" + login + "'");
 
         if(rs.next())
         	status = rs.getString("obj_id0");
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
     //System.out.println("status from service_t = " + status);
     
     
     
     return status;
 }
 
 
 private String checkAlias_t_service_login(String servicePoid)
 {
     String status = "Null";
     Connection pinConn = getDBConn(dbaseUser, dbasePass, dbaseIP);
     ResultSet rs = null;
     Statement stmt = null;
     try
     {
         stmt = pinConn.createStatement();
         rs = stmt.executeQuery ("select login from service_t where poid_id0 =" + servicePoid);
 
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
     //System.out.println("status from service_t = " + status);
     
     
     
     return status;
 }
 
 
	public String loginX = "";
	public String accObjX = "";
	public String poid_typeX = "";
	public String serviceTypeX = "";



	public String getLoginX() {
		return loginX;
	}


	public void setLoginX(String loginX) {
		this.loginX = loginX;
	}


	public String getAccObjX() {
		return accObjX;
	}


	public void setAccObjX(String accObjX) {
		this.accObjX = accObjX;
	}


	public String getPoid_typeX() {
		return poid_typeX;
	}


	public void setPoid_typeX(String poid_typeX) {
		this.poid_typeX = poid_typeX;
	}


	public String getServiceTypeX() {
		return serviceTypeX;
	}


	public void setServiceTypeX(String serviceTypeX) {
		this.serviceTypeX = serviceTypeX;
	}


}
