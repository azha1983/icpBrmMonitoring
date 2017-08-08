package brm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class BRMCheckAccPoid {
	
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
    	String BANum = "A600209361807";
    	//Vector getSvcFromBA = new Vector();
    	//Vector getSvcFromBA2 = new Vector();
    	BRMCheckAccPoid baCheck = new BRMCheckAccPoid();
    	String accPoid2Return = "Null";
    	
    	accPoid2Return = baCheck.getAccountPoid(BANum);	
    	
    	
    	System.out.println(accPoid2Return);
    	
    	
    	
    	/*BRMCheckAccPoid b = new BRMCheckAccPoid();
    	
    	getSvcFromBA = b.checkAccountNumber(BANum);
    	
		if(getSvcFromBA.size() > 0)
		{
	    	for(int y=0;y<getSvcFromBA.size();y++)
	    	{
	    		//System.out.println(y+1 + ":: " + y);
	    		System.out.println(getSvcFromBA.elementAt(y));
	    		Vector info = (Vector)getSvcFromBA.elementAt(y);
	    		b.setSvcPoid((String)info.elementAt(0));
	    		b.setSvcLogin((String)info.elementAt(1));
	    		b.setSvcStatus((String)info.elementAt(3));
	    		b.setSvcCreated((String)info.elementAt(4));
	    		b.setAccObj((String)info.elementAt(2));
	    		System.out.println(y+1 + ":: " + y);
	    		
	    		//getSvcFromBA2.addElement(info);
	    	}
	    	
			//outputReturn = "BA " + BANum + " already SUCCESS created in BRM on " + created + " with poid '/account " + accountPoid + "'";
	    	
	    	outputReturn = "This BA has service";
		}
		else
		{
			
			outputReturn = "No service under this BA";
		}
		
		System.out.println("0000000000000000000");*/
		////for(int i=0;i<getSvcFromBA2.size();i++)
		//{
		//	System.out.println(getSvcFromBA2.elementAt(i));
		//}
		
		//System.out.println(outputReturn);
    	
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
		
		String sqlStmt = "select poid_id0,poid_type,login,account_obj_id0,status,unix_ora_ts_conv(created_t) as created_t from service_t where"
				+ " account_obj_id0 = (select poid_id0 from account_t where account_no = '" + accNo + "')";
		
		 Vector LineInfo = null;
	        
	        Vector allVector = new Vector();
	        
	       // System.out.println("2<>-------------------<>");
	        
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				//System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("poid_id0"));
				LineInfo.add(rs.getString("poid_type"));
				LineInfo.add(rs.getString("login"));
				LineInfo.add(rs.getString("account_obj_id0"));
				LineInfo.add(rs.getString("status"));
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
	
	
	//-----------------------> INI UNTUK CHECK ACCOUNT POID MAPPING
	
	
	public String getAccountPoid(String accNo)
	{

        String accountPoid = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        System.out.println("Account No To Query -" + accNo + "-");
        
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select poid_id0 as poid from account_t where account_no = '" + accNo + "'");
            if(rs.next())
            	accountPoid = rs.getString("poid");
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
        return accountPoid;
    
    }
	
	
	public Vector getAccountNumber(String accPoid)
	{
	    Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try
		{
		conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
		
		stmt = conn.createStatement();
		
		String sqlStmt = "Select account_no from account_t where poid_id0 = " + accPoid + "";
		
		 Vector LineInfo = null;
	        
	        Vector allVector = new Vector();
	        
	       // System.out.println("2<>-------------------<>");
	        
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				//System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("account_no"));
				//LineInfo.add(rs.getString("poid_type"));


				System.out.println("<>-------------------<>");
				//System.out.println("3: " + rs.getString("poid_id0"));
				System.out.println("6: " + rs.getString("account_no"));
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
	
	
	
	public String getAccountBillInfo(String accPoid)
	{

        String billInfo = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        System.out.println("getAccountBillInfo | Account No To Query -" + accPoid + "-");
        
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select poid_type, poid_id0 from billinfo_t where account_obj_id0 = " + accPoid);
            if(rs.next())
            	billInfo = rs.getString("poid_id0");
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
       System.out.println("getAccountBillInfo | Account status get is = " + billInfo);
       
        return billInfo;
    
    }
	
	
	public Vector getAccountPayInfo(String accPoid)
	{
	    Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		System.out.println("getAccountPayInfo | Account No To Query -" + accPoid + "-");
		try
		{
		conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
		
		stmt = conn.createStatement();
		
		String sqlStmt = "select poid_type, poid_id0 from payinfo_t where account_obj_id0 = " + accPoid;
		
		 Vector LineInfo = null;
	        
	        Vector allVector = new Vector();
	        
	       // System.out.println("2<>-------------------<>");
	        
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				//System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("poid_id0"));
				LineInfo.add(rs.getString("poid_type"));


				System.out.println("<>-------------------<>");
				System.out.println("3: " + rs.getString("poid_id0"));
				System.out.println("6: " + rs.getString("poid_type"));
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
	
	public String getPROFILES_elem0(String accPoid)
	{

        String profElem0 = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        System.out.println("getPROFILES_elem0 | Account No To Query -" + accPoid + "-");
        
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select poid_type, poid_id0 from profile_t where poid_type = '/profile/tm_account' and account_obj_id0 = " + accPoid);
            if(rs.next())
            	profElem0 = rs.getString("poid_id0");
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
       System.out.println("getPROFILES_elem0 | Account status get is = " + profElem0);
       
        return profElem0;
    
    }	
	
	public String getPROFILES_elem1(String accPoid)
	{

        String profElem1 = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        System.out.println("getPROFILES_elem1 | Account No To Query -" + accPoid + "-");
        
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select poid_type, poid_id0 from profile_t where poid_type = '/profile/tm_invoice' and account_obj_id0 = " + accPoid);
            if(rs.next())
            	profElem1 = rs.getString("poid_id0");
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
       System.out.println("getPROFILES_elem1 | Account status get is = " + profElem1);
       
        return profElem1;
    
    }	
	
	
	public Vector getAccountProfile(String accPoid)
	{
	    Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		try
		{
		conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
		
		stmt = conn.createStatement();
		
		String sqlStmt = "select poid_type, poid_id0 from profile_t where account_obj_id0 = " + accPoid + "";
		
		 Vector LineInfo = null;
	        
	        Vector allVector = new Vector();
	        
	       // System.out.println("2<>-------------------<>");
	        
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				//System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("poid_id0"));
				LineInfo.add(rs.getString("poid_type"));


				System.out.println("<>-------------------<>");
				System.out.println("3: " + rs.getString("poid_id0"));
				System.out.println("6: " + rs.getString("poid_type"));
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
	
	
	
	//-----------------------> INI UNTUK CHECK ACCOUNT POID MAPPING
	

	public String svcPoid =  "Null";
	public String svcLogin = "Null";
	public	String accObj = "Null";
	public	String svcStatus = "Null";
	public	String svcCreated =  "Null";

	
	
	
	public String getSvcPoid() {
		return svcPoid;
	}

	public void setSvcPoid(String svcPoid) {
		this.svcPoid = svcPoid;
	}

	public String getSvcLogin() {
		return svcLogin;
	}

	public void setSvcLogin(String svcLogin) {
		this.svcLogin = svcLogin;
	}

	public String getAccObj() {
		return accObj;
	}

	public void setAccObj(String accObj) {
		this.accObj = accObj;
	}

	public String getSvcStatus() {
		return svcStatus;
	}

	public void setSvcStatus(String svcStatus) {
		this.svcStatus = svcStatus;
	}

	public String getSvcCreated() {
		return svcCreated;
	}

	public void setSvcCreated(String svcCreated) {
		this.svcCreated = svcCreated;
	}
}
