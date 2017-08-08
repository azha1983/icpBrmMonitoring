package brm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Vector;

//import ExceptionICP.GetEAIXML;

public class GetEAIXMLFromDbase {
	
 	private String dbasePass = "EAI_READ";
    private String dbaseUser = "EAI_READ";
    private String dbaseIP = "10.41.33.110";
    
private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
    public static void main(String args[])throws Exception
    {
    	GetEAIXMLFromDbase ge = new GetEAIXMLFromDbase();
		Vector eaiResult = new Vector();
		String int_msg_id = "Null";
		String event_name = "Null";
		String audit_date_time = "Null";
		String audit_type = "Null";
		String payload = "Null";
		
		String result = "Null";
		String eaiIDOnly = "Null";
		
		
		String EAIid = "EAI1139910610";
		
		
		eaiResult = ge.checkEAITable(EAIid);
				
		if(eaiResult.size()>0)
		{
	    	for(int y=0;y<eaiResult.size();y++)
	    	{
	    		//System.out.println(y+1 + ":: " + y);
	    		Vector info = (Vector)eaiResult.elementAt(y);
	    		int_msg_id = (String)info.elementAt(0);
	    		audit_date_time = (String)info.elementAt(1);	
	    		audit_type = (String)info.elementAt(2);	
	    		payload = (String)info.elementAt(3);
				
	    		//System.out.println(int_msg_id + "|");
	    		break;
	    		
	    	}
		}

		
		System.out.println(payload);
    }

    public Vector checkEAITable(String eaiID)
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
			String sqlStmt = "select int_msg_id,cast(audit_date_time as varchar(40)) as audit_date_time,audit_param_2,payload "
					+ "from eai_custom.eai_audit_log "
					+ "where int_msg_id = '" + eaiID + "' and audit_type = 'WSRQ' order by audit_date_time desc";
			
			
			
			
            Vector LineInfo = null;
            //System.out.println("------------query ICP BRM after get EAI ID");
            
            Vector allVector = new Vector();
            
           
			for(rs = stmt.executeQuery(sqlStmt); rs.next(); accc.add(LineInfo))
			{
				//System.out.println(">------------------------");
				LineInfo = new Vector();
				LineInfo.add(rs.getString("int_msg_id"));
				LineInfo.add(rs.getString("audit_date_time"));
				LineInfo.add(rs.getString("audit_param_2"));
				LineInfo.add(rs.getString("payload"));
	
				
				
				//System.out.println("<>-------------------<>");
				//System.out.println("3: " + rs.getString("int_msg_id"));
				//System.out.println("6: " + rs.getString("audit_date_time"));
				//System.out.println("6: " + rs.getString("payload"));
				//System.out.println("<>-------------------<>");
				
				//allVector = new Vector();
				allVector.add(LineInfo);
				//x++;
			}
			
			//System.out.println("||||||||LineInfo||||||||" + LineInfo.size());
			
			//System.out.println("||||||||allVector||||||||" + allVector.size());
			
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
}
