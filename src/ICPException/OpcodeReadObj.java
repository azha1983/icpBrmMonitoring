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

public class OpcodeReadObj {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
 	private String dbasePass = "DBtmpin_123";
    private String dbaseUser = "tmpin_batch";
    private String dbaseIP = "10.41.68.53";
    
    private String dbasePassPIN = "DBpin_123";
    private String dbaseUserPIN = "pin";
    private String dbaseIpPIN = "10.41.68.94";
    
    
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
    
    public static void main(String args[])throws Exception
    {
    	OpcodeReadObj opc = new OpcodeReadObj();
    	
    	opc.missingPoidInPurchasedTable("mainopcode");
    	
    	String outputReturn = "";
    	
    	//if(!opc.getPurchasedPoidString().equalsIgnoreCase(""))
    	//{
    		
    		
    		String purchaseType = "Null";
    		String result = "Null";
    		
    		if(opc.getPurchasedPoidString().indexOf("product") > -1)
    		{
    			purchaseType = "Product";
    			
    			result = opc.checkPurchaseTable(opc.getPurchasedPoid(), purchaseType);
    			
    			if(result.equalsIgnoreCase("Null"))
    			{
    				outputReturn = "[EAI] " + opc.getPurchasedPoidString() + " not found in BRM.";
    			}
    			else
    			{
    				outputReturn = "[OSM] Please re-trigger";
    			}
    			
    			
    		}
    		else if(opc.getPurchasedPoidString().indexOf("discount") > -1)
    		{
    			purchaseType = "Discount";
    			
   			result = opc.checkPurchaseTable(opc.getPurchasedPoid(), purchaseType);
    			
    			if(result.equalsIgnoreCase("Null"))
    			{
    				outputReturn = "[EAI] " + opc.getPurchasedPoidString() + " not found in BRM.";
    			}
    			else
    			{
    				outputReturn = "[OSM] Please re-trigger";
    			}
    		}
    		else
    		{
    			outputReturn = "Poid sent not found in BRM. " + opc.getPurchasedPoidString();
    		}
    	//}
    	//else
    	//{
    	//	outputReturn = "No reading purchased table";
    	//}
    	
    	
    	System.out.println(outputReturn);
    }
    
    public void missingPoidInPurchasedTable(String fileURL) throws Exception
    {
    	
      
    	FileInputStream fin=new FileInputStream(fileURL);

    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
    	
    	PrintStream p1;
    	FileOutputStream out1 = new FileOutputStream(res.getString("file21.xmlTemp"));
    	p1 = new PrintStream(out1);
    	
    	
    	String strLine = "";
    	String output = "";


    	
    	int a =1;

    	boolean discountCheck = false;
    	boolean productCheck = false;
    	
    	String discountCheckValue = "Null";
    	String productCheckValue = "Null";
 	
    	
    	while ((strLine = br.readLine()) != null)  
   		{

   			
    		System.out.println("Strline" + strLine);
   			if(strLine.indexOf("<POID>") > -1)
   			{

   			    int opBegin = 0;
   			    int opEnd = 0;
   			    
   				if(strLine.indexOf("purchased_discount") > -1 && discountCheck == false)
   				{
  				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				discountCheckValue = strLine.substring(opBegin+1, opEnd);
   				System.out.println("purchased_discount - " + discountCheckValue);
   				
   				this.setPurchasedPoidString(discountCheckValue);
   				
   				break;
   				}
   				else if(strLine.indexOf("purchased_product") > -1 && productCheck == false)
   				{
  				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				productCheckValue = strLine.substring(opBegin+1, opEnd);
   				System.out.println("purchased_product - " + productCheckValue);
   				
   				this.setPurchasedPoidString(productCheckValue);
   				
   				break;
   				}
   				else
   				{
   					this.setPurchasedPoidString(strLine);
   				}
   					
   			}
   			else
   			{
   				
   				//System.out.println("Nothing");
   			}
   			
			
   			//discountCheckValue = "Null";
   	    	//productCheckValue = "Null";
   	    	
   		}
    	
    	if(this.getPurchasedPoidString().indexOf("purchased_product") > 0 || this.getPurchasedPoidString().indexOf("purchased_discount") > 0)
    	{
        	String start = "Null";
        	String type = "Null";
        	String prodPoid = "Null";
        	
        	
        	StringTokenizer st1 = new StringTokenizer(this.getPurchasedPoidString(), " ");			
        	start = st1.nextToken();
        	type = st1.nextToken();
        	prodPoid = st1.nextToken();
        	
        	System.out.println("Purchased Poid -> " + prodPoid);
        	
        	this.setPurchasedPoid(prodPoid);
    	}
    	else
    	{
    		System.out.println("");
    	}
    	
    	
    	
   
    	
    }
    
    
	   public String checkPurchaseTable(String poid,String purchaseType)
	    {


	        String status = "Null";
	        Connection pinConn = getDBConn(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
	        ResultSet rs = null;
	        Statement stmt = null;
	        
			String tableToCheck = "";
			//String fieldToCheck = "";
			

	        if(purchaseType.equalsIgnoreCase("Product"))
	        {
	        	tableToCheck = "purchased_product_t";
	        	//fieldToCheck = "product_obj_id0";
	        	
	        }
	        else
	        {
	        	tableToCheck = "purchased_discount_t";
	        	//fieldToCheck = "discount_obj_id0";
	        }
			
	        System.out.println("Query table " + tableToCheck + " with poid " + poid); 
	        


	        try
	        {
	            stmt = pinConn.createStatement();
	            rs = stmt.executeQuery("select status from " + tableToCheck + "  where poid_id0 = " + poid);
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
    
    
    public String purchasedPoidString= "";
    public String purchasedPoid = "";


	public String getPurchasedPoidString() {
		return purchasedPoidString;
	}

	public void setPurchasedPoidString(String purchasedPoidString) {
		this.purchasedPoidString = purchasedPoidString;
	}

	public String getPurchasedPoid() {
		return purchasedPoid;
	}

	public void setPurchasedPoid(String purchasedPoid) {
		this.purchasedPoid = purchasedPoid;
	}




}
