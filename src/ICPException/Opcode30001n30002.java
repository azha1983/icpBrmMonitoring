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
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

public class Opcode30001n30002 {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
 	//private String dbasePass = "DBtmpin_123";
    //private String dbaseUser = "tmpin_batch";
    //private String dbaseIP = "10.41.68.53";
    
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
		
		String outputReturn = "";
		String statusGet = "";
		
		Opcode30001n30002 o = new Opcode30001n30002();
		
		o.err_30001_30002("mainopcode");
		
		
		
		if(!o.wrongOpcode)
		{
			statusGet = o.checkProdDiscPuchaseStatus(o.purchasePoid,o.getOpcodeType());
			
			if(statusGet.equalsIgnoreCase("3"))
			{
				outputReturn = "[EAI] " + o.getPurchasePoidString() + " already cancelled in BRM";
			}
			else if(statusGet.equalsIgnoreCase("2"))
			{
				outputReturn = "[EAI] Discount/Product status is inActive in BRM";
			}
			else
			{
				outputReturn = "[OSM] Discount/Product still active, please re-trigger";
			}
		}
		else
		{
			outputReturn = "[EAI] Wrong opcode sent";
		}
			
		System.out.println (outputReturn);
		
	}
    
    
    public void err_30001_30002(String fileURL) throws Exception
    {
   	 	
    	CheckLoginFromEAI t = new CheckLoginFromEAI();
    	                  
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
    	
	   	String purchaseProdPoid = "";
	   	String opcodeSent = "";

    	

    	boolean opcodeCheck = false;
    	boolean purchasePoidCheck = false;
    	
    	while ((strLine = br.readLine()) != null)  
   		{

		    	int opBegin = 0;
   		    	int opEnd = 0;
   		    	
    		/*if(strLine.equalsIgnoreCase("<flist>"))
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
   			*/
   			
   			if(strLine.indexOf("</opcode>") > -1 && opcodeCheck == false)
   			{

 
   		    	
   				if(strLine.indexOf("30001") > -1)
   				{
   					opcodeSent = "30001";
   				}
   				else
   					
   				{
   					opcodeSent = "30002";
   				}
   				

   				opcodeCheck = true;
   				System.out.println("Opcode sent - " +opcodeSent);
   			}
   			
    		//System.out.println("Strline" + strLine);
   			if(strLine.indexOf("</OFFERING_OBJ>") > -1 && purchasePoidCheck == false)
   			{

   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				purchaseProdPoid = strLine.substring(opBegin+1, opEnd);
   				purchasePoidCheck = true;
   				System.out.println("Purchased Poid - " + purchaseProdPoid);
   			}
   			
   			
   			
   			
   		
   		}
    	
    	
    	
    	//--------------check opcode salah sent
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	if(opcodeSent.equalsIgnoreCase("30001") && purchaseProdPoid.indexOf("product") > -1)
    	{
	    
	    	this.setPurchasePoidString(purchaseProdPoid);
	    	
	    	
	    	System.out.println("Purchased Poid - " + purchaseProdPoid);
	    	
	    	StringTokenizer st1 = new StringTokenizer(purchaseProdPoid, " ");			
	    	start = st1.nextToken();
	    	type = st1.nextToken();
	    	prodPoid = st1.nextToken();
	    	
	    	System.out.println("Purchased Poid - " + prodPoid);
	    	
	    	if(!opcodeSent.equalsIgnoreCase(""))   	
	    	{
	    		this.setOpcodeType(opcodeSent);
	    		this.setPurchasePoid(prodPoid);
	    	}
	    	else
	    	{
	    		System.out.println("Opcode not found");
	    	}
    	}
    	else if(opcodeSent.equalsIgnoreCase("30002") && purchaseProdPoid.indexOf("discount") > -1)
    	{

    		this.setPurchasePoidString(purchaseProdPoid);
    		
	    	System.out.println("Purchased Poid - " + purchaseProdPoid);
	    	
	    	StringTokenizer st1 = new StringTokenizer(purchaseProdPoid, " ");			
	    	start = st1.nextToken();
	    	type = st1.nextToken();
	    	prodPoid = st1.nextToken();
	    	
	    	System.out.println("Purchased Poid - " + prodPoid);
	    	
	    	if(!opcodeSent.equalsIgnoreCase(""))   	
	    	{
	    		this.setOpcodeType(opcodeSent);
	    		this.setPurchasePoid(prodPoid);
	    	}
	    	else
	    	{
	    		System.out.println("Opcode not found");
	    	}
    	}
    	else
    	{
    		this.setOpcodeType(opcodeSent);
    		this.setPurchasePoid(purchaseProdPoid);
    		this.wrongOpcode = true;
    	}
    	
    	System.out.println("Opcode not found" + this.getOpcodeType());
    	System.out.println("Opcode not found" + this.getPurchasePoid());
    	System.out.println("Opcode not found" + this.wrongOpcode);
    

    	
    
    }
    
    public String checkProdType(String fieldPoid,String purchaseType)//--------------cari product type
    {
    	
    	//format dalam EAI
    	//0.0.0.1 /product 118213
    	//0.0.0.1 /discount 51303
        String status = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        
		String tableToCheck = "";
		String fieldToCheck = "";
		String statusReturn = "";
		

        if(purchaseType.equalsIgnoreCase("30001"))
        {
        	tableToCheck = "purchased_product_t";
        	fieldToCheck = "product_obj_id0";
        	
        }
        else
        {
        	tableToCheck = "purchased_discount_t";
        	fieldToCheck = "discount_obj_id0";
        }
        
        System.out.println("1)Checking table " + tableToCheck + " with field to check " +  fieldToCheck + " and poid " + fieldPoid);
        
        System.out.println("select " + fieldToCheck + " from " + tableToCheck + " where poid_id0 = " + fieldPoid);
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery ("select " + fieldToCheck + " from " + tableToCheck + " where poid_id0 = " + fieldPoid);
          //  rs = stmt.executeQuery ("select product_obj_id0 from " + tableToCheck + " where poid_id0 = " + fieldPoid);
    
            if(rs.next())
            	status = rs.getString(fieldToCheck);
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
        
        System.out.println(fieldToCheck + " get is = " + status);
        
        if(purchaseType.equalsIgnoreCase("30001"))
        {
        	statusReturn = "0.0.0.1 /product " + status;
        	
        }
        else
        {
        	statusReturn = "0.0.0.1 /discount " + status;
        }
       
        
        return statusReturn;
    }
    
    
    public String checkProdStatus(String prodPoid)
    {
        String status = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery ("select status from purchased_product_t where poid_id0 = '" + prodPoid + "'");
    
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
        System.out.println("Product get is = " + status);
        
        
        
        return status;
    }
    
    
    public String checkDiscStatus(String discPoid)
    {
        String status = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery ("select status from purchased_discount_t where poid_id0 = '" + discPoid + "'");
    
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
        System.out.println("Product get is = " + status);
        return status;
    }
    
    
    public String checkProdDiscPuchaseStatus(String fieldPoid,String purchaseType)
    {
        String status = "Null";
        Connection pinConn = getDBConnPIN(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        
		String tableToCheck = "";
		String fieldToCheck = "";
		

        if(purchaseType.equalsIgnoreCase("30001"))
        {
        	tableToCheck = "purchased_product_t";
        	fieldToCheck = "product_obj_id0";
        	
        }
        else
        {
        	tableToCheck = "purchased_discount_t";
        	fieldToCheck = "discount_obj_id0";
        }
        
        System.out.println("Checking table " + tableToCheck + " with poid " +  fieldToCheck + "/ " + fieldPoid);
        
        
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery ("select status from " + tableToCheck + " where poid_id0 = " + fieldPoid);
    
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
        System.out.println(fieldToCheck + " get is = " + status);
        return status;
    }
    
    
	public String opcodeType = "";
	public String purchasePoid = "";
	public boolean wrongOpcode = false;

	public String purchasePoidString = "";
			
			

	public String getPurchasePoidString() {
		return purchasePoidString;
	}

	public void setPurchasePoidString(String purchasePoidString) {
		this.purchasePoidString = purchasePoidString;
	}

	public boolean isWrongOpcode() {
		return wrongOpcode;
	}

	public void setWrongOpcode(boolean wrongOpcode) {
		this.wrongOpcode = wrongOpcode;
	}

	public String getPurchasePoid() {
		return purchasePoid;
	}

	public void setPurchasePoid(String purchasePoid) {
		this.purchasePoid = purchasePoid;
	}

	public String getOpcodeType() {
		return opcodeType;
	}

	public void setOpcodeType(String opcodeType) {
		this.opcodeType = opcodeType;
	}
	
    


	
    


}
