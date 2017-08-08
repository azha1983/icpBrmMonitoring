package ICPException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Vector;

public class OpcodeCreateAccount {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
	public static void main( String[] args ) throws Exception 
	{
		
		
		String outputReturn = "Null";
		String fileURL = "mainopcode";
		
		OpcodeCreateAccount opca = new OpcodeCreateAccount();
		
		String accountPoid = "Null";
		String created = "Null";
		
		opca.checkCreateAccOpcode(fileURL);
		
		Vector accountDetail = new Vector();
			
		//System.out.println("asdadasd " + opca.getAccountNumber());
		accountDetail = opca.checkAccountNumber(opca.getAccountNumber());
		
		if(accountDetail.size() > 0)
		{
	    	for(int y=0;y<accountDetail.size();y++)
	    	{
	    		//System.out.println(y+1 + ":: " + y);
	    		Vector info = (Vector)accountDetail.elementAt(y);
	    		accountPoid = (String)info.elementAt(0);
	    		created = (String)info.elementAt(1);						
	    		
	    	}
	    	
			outputReturn = "BA " + opca.getAccountNumber() + " already SUCCESS created in BRM on " + created + " with poid '/account " + accountPoid + "'";
			
		}
		else
		{
			
			outputReturn = "Account not yet created in BRM";
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
    	
	   	String accPoid = "Null";
	   	String accountNo = "Null";
	   	//String accountPoid = "Null"; 

    	

    	//boolean serviceLoginCheck = false;
    	//boolean statusCheck = false;
    	boolean accountPoidCheck = false;

    	
    	while ((strLine = br.readLine()) != null)  
   		{
 			if(strLine.indexOf("<ACCOUNT_NO>") >-1 && accountPoidCheck == false)
   			{
   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				accountNo = strLine.substring(opBegin+1, opEnd);
   				accountPoidCheck = true;
   				System.out.println("Account No Sent - " + accountNo);
   			}
 			
    		a++;
   		}
    	
    	if(!accountNo.equalsIgnoreCase("Null"))
    	{
    		this.setAccountNumber(accountNo);
    	}
    	else
    	{
    		this.setAccountNumber("Null");
    	}
    	
    	
	}
	
	public Vector checkAccountNumber(String accNo)
	{
	    Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try
		{
		conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
		
		stmt = conn.createStatement();
		
		String sqlStmt = "select poid_id0,unix_ora_ts_conv(created_t) as created_t from account_t where account_no = '" + accNo + "'";
		
		 Vector LineInfo = null;
	        
	        Vector allVector = new Vector();
	        
	       // System.out.println("2<>-------------------<>");
	        
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				//System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("poid_id0"));
				LineInfo.add(rs.getString("created_t"));
				//LineInfo.add(rs.getString("status"));

				System.out.println("<>-------------------<>");
				System.out.println("3: " + rs.getString("poid_id0"));
				System.out.println("6: " + rs.getString("created_t"));
				System.out.println("<>-------------------<>");

				
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
		//System.out.println("prodDetail------------------------" + prodDetail.size());
		return accc;
		
	}
	
	

	   
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
	 
	 public String accountNumber = "Null";
	 public String accountPoid = "Null";


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getAccountPoid() {
		return accountPoid;
	}


	public void setAccountPoid(String accountPoid) {
		this.accountPoid = accountPoid;
	}
	 
	 

}
