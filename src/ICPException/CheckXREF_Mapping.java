package ICPException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;



public class CheckXREF_Mapping {

	
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
		boolean returnValue = false;
		
		CheckXREF_Mapping xref = new CheckXREF_Mapping();
		
		returnValue = xref.checkXref_Err_Return("mainopcode");
		
		if(returnValue)
		{
			outputReturn = "Unsync Asset Integration ID between SIEBEL - EAI";
		}
		else
		{
			outputReturn = "Cannot find XREF Mapping issue. BRM to double check";
		}
	
		System.out.println(outputReturn);
		
	}

	
	  public boolean checkXref_Err_Return(String fileURL) throws Exception
	    {
		  	boolean assetIDCheck = false;
	   	 	
	    	CheckLoginFromEAI t = new CheckLoginFromEAI();
	    	                  
	    	FileInputStream fin=new FileInputStream(fileURL);
	    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
	    	
	    	String strLine = "";
	    		    	    	
	    	while ((strLine = br.readLine()) != null)  
	   		{

   	   				if(strLine.indexOf("Service POID does not exists in EAI XREF!") > -1 && assetIDCheck == false)
	   	   			{

	   	   				assetIDCheck = true;
	   	   				System.out.println(" " + strLine);
	   	   				
	   	   				break;
	   	   			}
   		
	   		}
	    	
	    	
	    return assetIDCheck;
	    
	    }


}
