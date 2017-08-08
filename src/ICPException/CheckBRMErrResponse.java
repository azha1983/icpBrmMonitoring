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
import java.util.Vector;



public class CheckBRMErrResponse {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
 	private String dbasePass = "EAI_READ";
    private String dbaseUser = "EAI_READ";
    private String dbaseIP = "10.41.33.110";
	
    public static void main( String[] args ) throws Exception 
    {
    	CheckBRMErrResponse cbrm = new CheckBRMErrResponse();
    	String resultBRMResp = "Null";
    	String EAI_ID = "EAI0823847275";
    	String outputReturn = "Null";
    	
		//CheckBRMErrResponse cbrm = new CheckBRMErrResponse();
		
    	resultBRMResp = cbrm.CheckErrRespPayload(EAI_ID);
    	
    	System.out.println("Error code resp is 1 " + resultBRMResp);
    	
    	if(resultBRMResp.equalsIgnoreCase("Null"))
    	{
    		resultBRMResp = cbrm.CheckErrRespPayload2nd(EAI_ID);
    		System.out.println("Error code resp is 2 " + resultBRMResp);
    	}
    	
    
    	
    	if(resultBRMResp.indexOf("TRANS") > -1 || resultBRMResp.indexOf("NAP") > -1 || resultBRMResp.indexOf("CONNECT") > -1 )
    	{
    		outputReturn = "[OSM] Connection issue. Please re-trigger";
    	}
    	else if(resultBRMResp.indexOf("GST") > -1 )
    	{
    		outputReturn = "[BRM] GST inflight order issue. BRM to update missing tax code";
    	}
    	else if(resultBRMResp.indexOf("XREF") > -1 )
    	{
    		outputReturn = "[EAI] Unsync Asset Integration ID between SIEBEL - EAI";
    	}
    	else if(resultBRMResp.indexOf("BAD_ARG") > -1 )
    	{
    		outputReturn = "[BRM] BRM to investigate. ERR_BAD_ARG issue";
    	}
    	else
    	{
    		outputReturn = "Null";
    	}
    	
    	
    	System.out.println("Error code resp is " + outputReturn);
    }
    
    public String CheckErrRespPayload2nd(String EAI_ID) throws IOException
    {
    	
    	String outputReturnAll = "Null";
    	
		String eaiID = "Null";
		String auditDate = "Null";
		String opcode = "Null";
		String payload = "Null";
		String fileURL = "Null";
		String outputReturn = "Null";
		String agingCount = "Null";
		
		PrintStream p2;
		
		
		//CheckBRMErrResponse cbrm = new CheckBRMErrResponse();
    	
		if(!EAI_ID.equalsIgnoreCase("Null"))
		{
			
			Vector output1 = new Vector();
			
			output1 = this.checkEAITableResp2nd(EAI_ID);
			
			if(output1.size()>0)
			{
	    		System.out.println("Vector 1 not null...proceed");
	    		
		    	for(int y=0;y<output1.size();y++)
		    	{
		    		System.out.println(y+1 + ":: " + y);
		    		Vector info = (Vector)output1.elementAt(y);
		    		eaiID = (String)info.elementAt(0);
		    		auditDate = (String)info.elementAt(1);	
		    		payload = (String)info.elementAt(2);	
					
		    		System.out.println(eaiID + "|" + auditDate + "|" + opcode);
		    		break;
		    		
		    	}
		    	
		    	
				//fileURL = "C:/WorkSpace/FileInput/ExceptionICP/CheckOpcodeFromEAI/ListEAI_ID_Resp_Payload.txt";
				fileURL = res.getString("file20.eaiPayload");
				
				FileOutputStream out2 = new FileOutputStream(fileURL);
				p2 = new PrintStream(out2);
				
				
				p2.println(payload);
				outputReturn = "Null";
				
				this.checkRespFromBRM(fileURL);
				

			    outputReturnAll = this.getBRM_ERR_CODE();

				//outputReturnAll = this.getBRM_ERR_CODE();
				
			}
			else
			{
				System.out.println("<>----------No payload---2------<>");
			}
			
		}
		else
		{
			System.out.println("<>----------EAI ID null---------<>");
		}
    	
		
		System.out.println("<>---------FINAL---2-------<> " + outputReturnAll);
		
    	return outputReturnAll;
    }
    
    public String CheckErrRespPayload(String EAI_ID) throws IOException
    {
    	
    	String outputReturnAll = "Null";
    	
		String eaiID = "Null";
		String auditDate = "Null";
		String opcode = "Null";
		String payload = "Null";
		String fileURL = "Null";
		//String outputReturn = "Null";
		//String agingCount = "Null";
		
		PrintStream p2;
		
		
		//CheckBRMErrResponse cbrm = new CheckBRMErrResponse();
    	
		if(!EAI_ID.equalsIgnoreCase("Null"))
		{
			
			Vector output1 = new Vector();
			
			output1 = this.checkEAITableResp(EAI_ID);
			
			if(output1.size()>0)
			{
	    		System.out.println("Vector 1 not null...proceed");
	    		
		    	for(int y=0;y<output1.size();y++)
		    	{
		    		System.out.println(y+1 + ":: " + y);
		    		Vector info = (Vector)output1.elementAt(y);
		    		eaiID = (String)info.elementAt(0);
		    		auditDate = (String)info.elementAt(1);	
		    		payload = (String)info.elementAt(2);	
					
		    		System.out.println(eaiID + "|" + auditDate + "|" + opcode);
		    		break;
		    		
		    	}
		    	
		    	
				//fileURL = "C:/WorkSpace/FileInput/ExceptionICP/CheckOpcodeFromEAI/ListEAI_ID_Resp_Payload.txt";
				fileURL = res.getString("file20.eaiPayload");
				
				FileOutputStream out2 = new FileOutputStream(fileURL);
				p2 = new PrintStream(out2);
				
				
				p2.println(payload);
				//outputReturn = "Null";
				
				this.checkRespFromBRM(fileURL);
				

			    outputReturnAll = this.getBRM_ERR_CODE();

				//outputReturnAll = this.getBRM_ERR_CODE();
				
			}
			else
			{
				System.out.println("<>----------No payload---------<>");
			}
			
		}
		else
		{
			System.out.println("<>----------EAI ID null---------<>");
		}
    	
		
		System.out.println("<>---------FINAL---1-------<> " + outputReturnAll);
		
		
		
		
    	return outputReturnAll ;
    }

    
    public Vector checkEAITableResp(String eaiID)
    {
        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		
		try
		{
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();

			
			//String sqlStmt = "select int_msg_id,to_char(audit_date_time, 'dd-Mon-yyyy hh24:mi:ss') as audit_date_time,audit_param_2,payload "
			String sqlStmt = "select int_msg_id,cast(audit_date_time as varchar(40)) as audit_date_time,payload "
					+ "from eai_custom.eai_audit_log "
					+ "where int_msg_id = '" + eaiID + "' and audit_type = 'WSRP' order by audit_date_time desc";
			
			
			
			
            Vector LineInfo = null;
            System.out.println("------------query ICP BRM after get EAI ID");
            
            Vector allVector = new Vector();
            
           
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("int_msg_id"));
				LineInfo.add(rs.getString("audit_date_time"));
				LineInfo.add(rs.getString("payload"));
	
				
				
				System.out.println("<>-------------------<>");
				System.out.println("3: " + rs.getString("int_msg_id"));
				System.out.println("6: " + rs.getString("audit_date_time"));
				System.out.println("<>-------------------<>");
				
				//allVector = new Vector();
				allVector.add(LineInfo);
				//x++;
			}
			
			System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
			System.out.println("||||||||allVector||||||||" + allVector.size());
			
			//for(int y = 0;y<allVector.size();y++)
			//{
				
			//	System.out.println("---->" + allVector.elementAt(y));
			///}
			
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
    
    
    public Vector checkEAITableResp2nd(String eaiID)
    {
        Vector accc = new Vector();
		Connection conn = null;
		ResultSet rs = null;
		Statement stmt = null;
		
		
		try
		{
			
			conn = getDBConn(dbaseUser, dbasePass, dbaseIP);
			
			stmt = conn.createStatement();

			
			//String sqlStmt = "select int_msg_id,to_char(audit_date_time, 'dd-Mon-yyyy hh24:mi:ss') as audit_date_time,audit_param_2,payload "
			String sqlStmt = "select int_msg_id,cast(audit_date_time as varchar(40)) as audit_date_time,payload "
					+ "from eai_custom.eai_audit_log "
					+ "where int_msg_id = '" + eaiID + "' and audit_type = 'ERR' order by audit_date_time desc";
			
			
			
			
            Vector LineInfo = null;
            System.out.println("------------query ICP BRM after get EAI ID");
            
            Vector allVector = new Vector();
            
           
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("int_msg_id"));
				LineInfo.add(rs.getString("audit_date_time"));
				LineInfo.add(rs.getString("payload"));
	
				
				
				System.out.println("<>-------------------<>");
				System.out.println("3: " + rs.getString("int_msg_id"));
				System.out.println("6: " + rs.getString("audit_date_time"));
				System.out.println("<>-------------------<>");
				
				//allVector = new Vector();
				allVector.add(LineInfo);
				//x++;
			}
			
			System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
			System.out.println("||||||||allVector||||||||" + allVector.size());
			
			//for(int y = 0;y<allVector.size();y++)
			//{
				
			//	System.out.println("---->" + allVector.elementAt(y));
			///}
			
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
    
    private void checkRespFromBRM(String fileURL) throws IOException
    {
    	
    	FileInputStream fin=new FileInputStream(fileURL);

    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));

       	
    	String strLine = "";
    	String output = "";

    	boolean test = false;
    	
    	int a =1;

    	boolean opcodeCheck = false;
    	String opcodeLine = "Null";
    	
    	while ((strLine = br.readLine()) != null)  
   		{
   			
    		
    		//System.out.println("Strline" + strLine);
   			if(strLine.indexOf("ERR_BAD_VALUE") > -1 && opcodeCheck == false)
   			{

   				opcodeLine = "ERR_BAD_VALUE";
   				opcodeCheck = true;
   				System.out.println("BLANK - strLine - " + strLine);
   				break;
   				
   			}
   			else if(strLine.indexOf("ERR_BAD_ARG") > -1 && opcodeCheck == false)
   			{
   				opcodeLine = "ERR_BAD_ARG";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			else if(strLine.indexOf("ERR_NOT_FOUND") > -1 && opcodeCheck == false)
   			{
   				opcodeLine = "ERR_NOT_FOUND";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			else if(strLine.indexOf("GST") > -1 && opcodeCheck == false)
   			{
   				opcodeLine = "GST";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			else if(strLine.indexOf("XREF") > -1 && opcodeCheck == false)
   			{
   				opcodeLine = "XREF";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			else if(strLine.indexOf("TRANS_ALREADY_OPEN") > -1 && opcodeCheck == false)
   			{
   				opcodeLine = "TRANS_OPEN";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			else if(strLine.indexOf("TRANS_LOST") > -1 && opcodeCheck == false)
   			{
   				opcodeLine = "TRANS_LOST";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			else if((strLine.indexOf("NAP_CONNECT") > -1 || strLine.indexOf("IM_CONNECT") > -1)&& opcodeCheck == false)
   			{
   				opcodeLine = "NAP_CONNECT";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			else if(strLine.indexOf("STREAM_EOF") > -1 && opcodeCheck == false)
   			{
   				opcodeLine = "STREAM_EOF";
   				opcodeCheck = true;
   				System.out.println("strLine - " + strLine);
   				break;
   			}
   			
   			
   		
   		}
    	
    	
    	System.out.println("============ - " + opcodeLine);
    	this.setBRM_ERR_CODE(opcodeLine);
    	
    	
    }
    
    private Connection getDBConn(String dbLogin, String dbPassword, String dbIp)
    {
        Connection con = null;
        try
        {

        	con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + dbIp + ")(PORT=1521))(CONNECT_DATA=(SERVICE_NAME = NEAIPRD)))", dbLogin, dbPassword);
        	
        	//con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = 10.41.23.166) (PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = 10.41.23.167) (PORT = 1521))(LOAD_BALANCE = yes)(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = HBRMPRD) (FAILOVER_MODE =(TYPE = SESSION)(METHOD = BASIC)(RETRIES = 20)(DELAY = 5))))", dbLogin, dbPassword);
        	
        }
        catch(Exception e)
        {
            System.out.println("Error create new connection to database, " + e);
            return null;
        }
        return con;
    }
    
    
    public String BRM_ERR_CODE = "Null";

	public String getBRM_ERR_CODE() {
		return BRM_ERR_CODE;
	}


	public void setBRM_ERR_CODE(String bRM_ERR_CODE) {
		BRM_ERR_CODE = bRM_ERR_CODE;
	}
    
}
