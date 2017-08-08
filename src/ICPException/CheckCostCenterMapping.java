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

public class CheckCostCenterMapping {
	
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
    	CheckCostCenterMapping c = new CheckCostCenterMapping();
    	
    	c.err_Blank_Value("mainopcode");
    	
    	System.out.println("Cost Center = " + c.getCostCenterVal());
    	System.out.println("Industrial Code = " + c.getIndustrialCod());
    	System.out.println("Segment Code = " + c.getSegmentCod());
    	
    	
    	String checkField = c.checkFieldTableTmSvcProfile(c.getTmSvcProfPoid());
    	
    	if(!checkField.equalsIgnoreCase("Null"))
    	{
    	
	    	String result = c.checkMappingCostCenter(c.getCostCenterVal(), c.getIndustrialCod(), c.getSegmentCod());
	    	
	    	System.out.println("Result is " + result);
	    	
	    	if(result.equalsIgnoreCase("Null"))
	    	{
	    		System.out.println("Cost Center mapping not found in BRM for -> Cost Center = " + c.getCostCenterVal() + ", Industrial Code = " + c.getIndustrialCod() + " Segment Code = " + c.getSegmentCod());
	    	}
	    	else
	    	{
	    		System.out.println("Cost center mapping found");  		
	    	}
    	}
    	else
    	{
    		System.out.println("Field TM_FLD_TRANSLATED_NUM not exist in BRM"); 
    	}
    }
    
    public void err_Blank_Value(String fileURL) throws Exception
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

    	boolean costCenterCheck = false;
    	boolean industrialCodeCheck = false;
    	boolean segmentCodeCheck = false;
    	
    	boolean svcProfCheck = false;
    	
    	//String opcodeLine = "Null";  	
    	
    	String costCenterValue = "Null";
    	String industrialCodeValue = "Null";
    	String segmentCodeValue = "Null";
    	
    	String svcProfString =  "Null";
    	
    	
    	while ((strLine = br.readLine()) != null)  
   		{
		    int opBegin = 0;
		    int opEnd = 0;
   			
    		System.out.println("Strline" + strLine);
    		
    		if(strLine.indexOf("profile/tm_service ") > -1 && svcProfCheck == false)
    		{
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				svcProfString = strLine.substring(opBegin+1, opEnd);
   				svcProfCheck = true;
   				System.out.println("Service profile Poid - " + svcProfString);
    		}
    		
    		
   			if(strLine.indexOf("TM_COST_CENTER_5") > -1 && costCenterCheck == false)
   			{

   				//opcodeLine = strLine;
   				costCenterCheck = true;
   				System.out.println("strLine - " + strLine);
   				
   				
   			}
   			else if(strLine.indexOf("TM_SEGMENT_CODE") > -1 && segmentCodeCheck == false)
   			{
   				//opcodeLine = strLine;
   				if(this.getCostCenterVal().equalsIgnoreCase("Null"))
   				{
   					this.setCostCenterVal("");
   				}
   				segmentCodeCheck = true;
   				System.out.println("strLine - " + strLine);
   			}
   			else if(strLine.indexOf("TM_INDUSTRIAL_CODE") > -1 && industrialCodeCheck == false)
   			{
   				//opcodeLine = strLine;

   				industrialCodeCheck = true;
   				System.out.println("strLine - " + strLine);
   		  			

   			}
   			else
   			{
   				
   				//System.out.println("Nothing");
   			}
   			
   			//proceed to check value
   			
   			if(costCenterCheck && strLine.indexOf("VALUE") > -1)
   			{

   		    	if(strLine.indexOf("<VALUE/>") > -1)
   		    	{
   		    		this.setCostCenterVal("");
   		    		costCenterCheck = false;
   		    	}
   		    	else
   		    	{
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				costCenterValue = strLine.substring(opBegin+1, opEnd);
   				System.out.println("Cost Center value - " + costCenterValue);
   				
   				this.setCostCenterVal(costCenterValue);
   		    	
   				costCenterCheck = false;
   		    	}
   			}
   			else if(segmentCodeCheck && strLine.indexOf("VALUE") > -1)
   			{

   				
   		    	
   		    	if(strLine.indexOf("<VALUE/>") > -1)
   		    	{
   		    		this.setSegmentCod("");
   		    		segmentCodeCheck = false;
   		    	}
   		    	else
   		    	{
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				segmentCodeValue = strLine.substring(opBegin+1, opEnd);
   				System.out.println("Segment value - " + segmentCodeValue);
   				
   				//segmentCodeValue.substring(1);
   				
   				this.setSegmentCod(segmentCodeValue.substring(0,1));
   		    	
   				segmentCodeCheck = false;
   		    	}
   			}
   			else if(industrialCodeCheck && strLine.indexOf("VALUE") > -1)
   			{

   		    	
   		    	if(strLine.indexOf("<VALUE/>") > -1)
   		    	{
   		    		this.setIndustrialCod("");
   		    		industrialCodeCheck = false;
   		    	}
   		    	else
   		    	{
   				opBegin = strLine.indexOf(">");
   				opEnd = strLine.indexOf("</");
   				
   				industrialCodeValue = strLine.substring(opBegin+1, opEnd);
   				System.out.println("Industrial Code value - " + industrialCodeValue);
   				
   				this.setIndustrialCod(industrialCodeValue);
   		    	
   				industrialCodeCheck = false;
   		    	}
   			}

   			
   			
	
   	    	
   	    	costCenterValue = "Null";
   	    	industrialCodeValue = "Null";
   	    	segmentCodeValue = "Null";
     			
   		
   		}
    	
    	
    	
    	
    	String start = "Null";
    	String type = "Null";
    	String prodPoid = "Null";
    	
    	System.out.println("Service prof String 1- =" + svcProfString + "=");
    	
    	
    	if(!svcProfString.equalsIgnoreCase("Null") || !svcProfString.equalsIgnoreCase(""))
    	{
	    	System.out.println("Service prof String 2- " + svcProfString);
	    	
	    	StringTokenizer st1 = new StringTokenizer(svcProfString, " ");			
	    	start = st1.nextToken();
	    	type = st1.nextToken();
	    	prodPoid = st1.nextToken();
	    	
	    	System.out.println("Service prof Poid - " + prodPoid);
	    	
	    	this.setTmSvcProfPoid(prodPoid);
	    	
	    	//lepas ni buat query n login je...
    	}
    	else
    	{
    		System.out.println("No service profile poid found - " + svcProfString);
    		
    	}
    	
    	
    	
    	
    	

    	
    
    }
    

	public String checkFieldTableTmSvcProfile(String tmSvcProf)
    {

        String status = "Null";
        Connection pinConn = getDBConn(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select obj_id0 from tm_cust_srv_profile_t where obj_id0 = " + tmSvcProf + " and rec_id = 8");
            if(rs.next())
            	status = rs.getString("obj_id0");
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
	
	

	public String checkMappingCostCenter(String costCenter,String industrialCode, String segmentCode)
    {

        String status = "Null";
        Connection pinConn = getDBConn(dbaseUserPIN, dbasePassPIN, dbaseIpPIN);
        ResultSet rs = null;
        Statement stmt = null;
        try
        {
            stmt = pinConn.createStatement();
            rs = stmt.executeQuery("select * from TM_CONFIG_JNL_COST_T where TM_COST_CENTER_5='" + costCenter + "' and "
            		+ "TM_INDUSTRIAL_CODE='" + industrialCode + "' and TM_SEGMENT_CODE LIKE '" + segmentCode + "%'");
            if(rs.next())
            	status = rs.getString("tm_cost_center");
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
    
    
    String costCenterVal = "Null";
    String industrialCod = "Null";
    String segmentCod = "Null";
    String tmSvcProfPoid = "Null";
    
    
    public String getTmSvcProfPoid() {
		return tmSvcProfPoid;
	}

	public void setTmSvcProfPoid(String tmSvcProfPoid) {
		this.tmSvcProfPoid = tmSvcProfPoid;
	}

	public String getCostCenterVal() {
		return costCenterVal;
	}

	public void setCostCenterVal(String costCenterVal) {
		this.costCenterVal = costCenterVal;
	}

	public String getIndustrialCod() {
		return industrialCod;
	}

	public void setIndustrialCod(String industrialCod) {
		this.industrialCod = industrialCod;
	}

	public String getSegmentCod() {
		return segmentCod;
	}

	public void setSegmentCod(String segmentCod) {
		this.segmentCod = segmentCod;
	}
    

}
