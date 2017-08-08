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
import java.util.Vector;

public class OpcodePurchase {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
	public static void main( String[] args ) throws Exception 
	{
		
		OpcodePurchase o = new OpcodePurchase();
		
		
		
		o.err_Purchase("mainopcode");
		
		
		
		if(o.getServicePoid().equalsIgnoreCase("Null"))
		{
			System.out.println("No service poid found. This is account level product");
		}
		else
		{
			String result = o.checkServiceStatus(o.servicePoid);
			
			String accountPoidSent = "Null";
			
			if(result.equalsIgnoreCase("10100"))
			{
				accountPoidSent = o.serviceTiedToBA(o.getServicePoid());
				
				if(!accountPoidSent.equalsIgnoreCase("Null"))
				{
					if(accountPoidSent.equalsIgnoreCase(o.getAccountPoid()))
					{
						Vector checkPurchase = new Vector();
						
						checkPurchase = o.checkProdDiscPuchaseStatus(o.getAccountPoid(), o.getServicePoid(),o.getPoidObj(), o.getPurchaseType());
						
						String purchasePoid = "";
						String created = "";
						String status = "";
						String finalStatus = "";
						
						
						if(checkPurchase.size()>0)
						{
					    	for(int y=0;y<checkPurchase.size();y++)
					    	{
					    		//System.out.println(y+1 + ":: " + y);
					    		Vector info = (Vector)checkPurchase.elementAt(y);
					    		purchasePoid = (String)info.elementAt(0);
					    		created = (String)info.elementAt(1);	
					    		status = (String)info.elementAt(2);							
					    		
					    	}
					    	
					    	if(status.equalsIgnoreCase("1"))
					    	{
					    		finalStatus = "Active";
					    	}
					    	else if(status.equalsIgnoreCase("2"))
					    	{
					    		finalStatus = "Inactive";
					    	}
					    	else if(status.equalsIgnoreCase("3"))
					    	{
					    		finalStatus = "Closed";
					    	}
					    	else
					    	{
					    		finalStatus = "Unknown";
					    	}
					    	
					    	System.out.println("[EAI] " + o.getPurchaseType() + " already purchased on " + created + " with purchase poid " + purchasePoid + " and status is " + finalStatus);
						}
						else
						{

								System.out.println("[EAI] Service is Active (Tied to right BA) and no purchase happen");

							
						}
					}
					else
					{
						String accountNumberSent = "Null";
						String accountNumberInBRM = "Null";
						
						accountNumberSent = o.checkAccountNumber(accountPoidSent);
						
						accountNumberInBRM = o.checkAccountNumber(o.getAccountPoid());
						
						Vector checkServiceDetail= new Vector();
						
						String poid_id0 = "Null";
						String poid_Type = "Null";
						String loginGet = "Null";
						
						checkServiceDetail = o.getServiceDetail(o.getServicePoid());
						
						if(checkServiceDetail.size() > 0)
						{
					    	for(int y=0;y<checkServiceDetail.size();y++)
					    	{
					    		//System.out.println(y+1 + ":: " + y);
					    		Vector info = (Vector)checkServiceDetail.elementAt(y);
					    		poid_id0 = (String)info.elementAt(0);
					    		poid_Type = (String)info.elementAt(1);	
					    		loginGet = (String)info.elementAt(2);							
					    		
					    	}
					    	
					    	System.out.println("[Siebel] Try to purchase deal but '" + poid_Type + " " + poid_id0 + "' (Login:" + loginGet + ") sent Tied to other BA in BRM " + accountNumberInBRM + ". Not as send BA " + accountNumberSent);
						}
						else
						{
							
						}
						

						
					}
				}
				else
				{
					System.out.println("[EAI] Cannot find service in BRM");
				}
				
				
				

				
				
			}
			else if(result.equalsIgnoreCase("10102"))
			{
				System.out.println("[EAI] Service is InActive");
			}
			else if(result.equalsIgnoreCase("10103"))
			{
				System.out.println("[EAI] Service is Closed");
			}
			else
			{
				System.out.println("[EAI] Service is 0.0.0.1 /service/telephony " + o.servicePoid + " 0 not found in BRM. Please re-create service then re-trigger");
			}
		}
		
		
		
		
		String accEffectDate = "Null";
		
		accEffectDate = o.getAccEffectDate(o.getAccountPoid());
		
		System.out.println("Purchase date sent is " + o.getDatePurchSent() + " Account Effective date get is " + accEffectDate);
		
		int datePurch = Integer.parseInt(o.getDatePurchSent());
		
		if(!accEffectDate.equalsIgnoreCase("Null"))
		{
			
			int x = Integer.parseInt(accEffectDate);
			
			
			
			if(datePurch < x)
			{
				int result = x + 10;
				
				System.out.println("Purchase date sent is EARLIER than account effective date. Need to fix\n\n");
				System.out.println("Please replace field <START_T> and <END_T> with this value " + result + " then re-trigger");
				
			}
			else
			{
				System.out.println("Date Purchase GREATER then Account effective date. Should be no problem");
			}
			
			
		}
		else
		{
			System.out.println("Account effective date not found");
		}

	}
	
	public void err_Purchase(String fileURL) throws Exception
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
    	
	   	String ServicePoid = "Null";
	   	String accountPoid = "Null";
	   	String discPoid = "Null";
	   	String datePurchaseSent = "Null";
    	

    	boolean servicePoidCheck = false;
    	boolean accountPoidCheck = false;
    	boolean dateCheck = false;
    	boolean discCheck = false;
    	
    	while ((strLine = br.readLine()) != null)  
   		{


   			
    		//System.out.println("Strline" + strLine);
   			if(strLine.indexOf("</SERVICE_OBJ>") > -1 && servicePoidCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				ServicePoid = strLine.substring(opBegin+1, opEnd);
   				servicePoidCheck = true;
   				System.out.println("Service Poid - " + ServicePoid);
   			}
   			
   			if(strLine.indexOf("/account") >-1 && accountPoidCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				accountPoid = strLine.substring(opBegin+1, opEnd);
   				accountPoidCheck = true;
   				System.out.println("Account Poid - " + accountPoid);
   			}
   			
   			//-------------------------------------------get date sent
   			
   			
   			if(strLine.indexOf("START_T") >-1 && dateCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				datePurchaseSent = strLine.substring(opBegin+1, opEnd);
   				dateCheck = true;
   				System.out.println("Purchase date sent - " + datePurchaseSent);
   				
   				this.setDatePurchSent(datePurchaseSent);
   			}
   			
   			//-----------------
   			
   			if(strLine.indexOf("DISCOUNTS") >-1)
   			{
   				this.setPurchaseType("Discount");
   				 				

   			}
   			else if(strLine.indexOf("PRODUCTS") >-1)
   			{
   				this.setPurchaseType("Product");
   			}
   			
   			
   			//----------------
   			
   			if(strLine.indexOf("<DISCOUNT_OBJ>") >-1 && discCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				discPoid = strLine.substring(opBegin+1, opEnd);
   				discCheck = true;
   				System.out.println("Discount Poid - " + discPoid);
   			}
   			
   			if(strLine.indexOf("<PRODUCT_OBJ>") >-1 && discCheck == false)
   			{

   		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				discPoid = strLine.substring(opBegin+1, opEnd);
   				discCheck = true;
   				System.out.println("Discount Poid - " + discPoid);
   			}
   			
   			
   		
   		}
    	
    	System.out.println("This is purchase " + this.getPurchaseType());
    	
    	if(!ServicePoid.equalsIgnoreCase("Null"))
    	{
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	
    	StringTokenizer st1 = new StringTokenizer(ServicePoid, " ");			
    	start = st1.nextToken();
    	type = st1.nextToken();
    	prodPoid = st1.nextToken();
    	
    	System.out.println("Service Poid -> " + prodPoid);
    	
    	
    	this.setServicePoid(prodPoid);
    	}
    	else
    	{
    		
    		this.setServicePoid("Null");	
    	}
    	
    	//===========
    
    			
    	if(!accountPoid.equalsIgnoreCase("Null"))
    	{
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	
    	StringTokenizer st1 = new StringTokenizer(accountPoid, " ");			
    	start = st1.nextToken();
    	type = st1.nextToken();
    	prodPoid = st1.nextToken();
    	
    	System.out.println("Account Poid -> " + prodPoid);
    	
    	
    	this.setAccountPoid(prodPoid);
    	}
    	else
    	{
    		
    		this.setAccountPoid("Null");	
    	}
    	
    	//===========
    	
    	
    	if(!discPoid.equalsIgnoreCase("Null"))
    	{
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	
    	StringTokenizer st1 = new StringTokenizer(discPoid, " ");			
    	start = st1.nextToken();
    	type = st1.nextToken();
    	prodPoid = st1.nextToken();
    	
    	System.out.println("Discount Poid -> " + prodPoid);
    	
    	
    	this.setPoidObj(prodPoid);
    	}
    	else
    	{
    		
    		this.setPoidObj("Null");	
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
	
	   public String checkServiceStatus(String svcPoid)
	    {
	        String status = "Null";
	        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select status from service_t where poid_id0 = '" + svcPoid + "'");
	    
	            if(rs.next())
	            	status = rs.getString("status");
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
	        System.out.println("status from service_t = " + status);
	        
	        
	        
	        return status;
	    }
	   
	   
	   public Vector checkProdDiscPuchaseStatus(String accPoid,String svcPoid,String fieldPoid,String purchaseType)
	    {
	        Vector accc = new Vector();
			Connection conn = null;
			ResultSet rs = null;
			Statement stmt = null;
			
			String tableToCheck = "";
			String fieldToCheck = "";
			

	        if(purchaseType.equalsIgnoreCase("Product"))
	        {
	        	tableToCheck = "purchased_product_t";
	        	fieldToCheck = "product_obj_id0";
	        	
	        }
	        else
	        {
	        	tableToCheck = "purchased_discount_t";
	        	fieldToCheck = "discount_obj_id0";
	        }

			
			try
			{
				
				conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
				
				stmt = conn.createStatement();

				//System.out.println("1<>-------------------<>");
				String sqlStmt = "select poid_id0,unix_ora_ts_conv(created_t) as created_t,status from " + tableToCheck + " "
	            		+ "where account_obj_id0 = " + accPoid + " and service_obj_id0 = " + svcPoid + " and " + fieldToCheck + " = " + fieldPoid;
				//System.out.println("2<>-------------------<>");
				
	            Vector LineInfo = null;
	            
	            Vector allVector = new Vector();
	            
	           // System.out.println("2<>-------------------<>");
				for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
				{
					//System.out.println(">------------------------");
					LineInfo = new Vector();
					LineInfo.add(rs.getString("poid_id0"));
					LineInfo.add(rs.getString("created_t"));
					LineInfo.add(rs.getString("status"));

					//System.out.println("<>-------------------<>");
					//System.out.println("3: " + rs.getString("poid_id0"));
					//System.out.println("6: " + rs.getString("created_t"));
					//System.out.println("6: " + rs.getString("status"));
					//System.out.println("<>-------------------<>");
					
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
	   
	   
	   
	   public Vector getServiceDetail(String servicePoid)
	    {
	        Vector accc = new Vector();
			Connection conn = null;
			ResultSet rs = null;
			Statement stmt = null;
			


			
			try
			{
				
				conn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
				
				stmt = conn.createStatement();

				//System.out.println("1<>-------------------<>");
				String sqlStmt = "select poid_id0,poid_type,login from service_t where poid_id0 = " + servicePoid;
				//System.out.println("2<>-------------------<>");
				
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

			return accc;
	    }
	   
	
	   public String getAccEffectDate(String accPoid)
	    {
	        String status = "Null";
	        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery ("select effective_t from account_t where poid_id0 = " + accPoid);
	    
	            if(rs.next())
	            	status = rs.getString("effective_t");
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
    
    
    public String servicePoid = "";
    public String accountPoid = "";
    public String purchaseType = "";
    public String poidObj = "";
    public String datePurchSent = "";




	public String getDatePurchSent() {
		return datePurchSent;
	}

	public void setDatePurchSent(String datePurchSent) {
		this.datePurchSent = datePurchSent;
	}

	public String getPoidObj() {
		return poidObj;
	}

	public void setPoidObj(String poidObj) {
		this.poidObj = poidObj;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getAccountPoid() {
		return accountPoid;
	}

	public void setAccountPoid(String accountPoid) {
		this.accountPoid = accountPoid;
	}

	public String getServicePoid() {
		return servicePoid;
	}


	public void setServicePoid(String servicePoid) {
		this.servicePoid = servicePoid;
	}

}
