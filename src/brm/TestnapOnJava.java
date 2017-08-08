package brm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class TestnapOnJava {
	
	
    String host="10.41.66.10";
    String user="S53725";
    String password="azha1983";
    String command1="azha";
	
    public static void main(String args[])throws Exception
    {
    	TestnapOnJava t = new TestnapOnJava();
    	
    	FileInputStream fstream = new FileInputStream("C:/WorkSpace/FileInput/SiebelDateCheck/flistOut.txt");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader brIn = new BufferedReader(new InputStreamReader(in));
        
        PrintStream p1;
        FileOutputStream out1 = new FileOutputStream("C:/WorkSpace/FileInput/CheckTestnap/listOut.txt");
        p1 = new PrintStream(out1);
	   
        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session=jsch.getSession(t.user, t.host, 22);
        session.setPassword(t.password);
        session.setConfig(config);
        session.connect();
        
        String strLine = "Null";
		//Session session = jsch.getSession(user, host, 22);
		
		Channel channel = session.openChannel("shell");
		
		OutputStream inputstream_for_the_channel = channel.getOutputStream();
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		
		channel.setOutputStream(System.out, true);
		
		System.out.print("=======================================");
		channel.connect();
		
		commander.println("azha");  
		commander.println("testnap");
		commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID           POID [0] 0.0.0.1 /plan -1 0\n"
				+ "XXX\n"
				+ "xop PCM_OP_TRANS_OPEN 65536 1");		
		
		commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID           POID [0] 0.0.0.1 /account 1 1\n"
				+ "0 PIN_FLD_COMPONENT       STR [0] \"CM\"\n"
				+ "0 PIN_FLD_LOGLEVEL        INT [0] 2\n"
				+ "XXX\n"
				+ "xop PCM_OP_INFMGR_SET_LOGLEVEL 0 1");
		
		String valueT = "";
		while ((strLine = brIn.readLine()) != null)  
		{		
			valueT = valueT + strLine + "\n";		
		}
		
		//System.out.print("-----------------");
		//System.out.print(valueT);
		//System.out.print("-----------------");
		
		commander.println(valueT);
		
		/*commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID                      POID [0] 0.0.0.1 /purchased_product 524770411812 0\n"
				+ "XXX\n"
				+ "xop PCM_OP_READ_OBJ 0 1");*/
		
		commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID           POID [0] 0.0.0.1 /plan -1 0\n"
				+ "XXX\n"
				+ "xop PCM_OP_TRANS_ABORT 1 1");
		
		commander.println("q");
		commander.println("exit");
		commander.close();
		
		InputStream outputstream_from_the_channel = channel.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		String line;
		boolean toPrint = false;
		
		while ((line = br.readLine()) != null)
		    {
			//System.out.print(line+"\n");
			//line = Normalizer.normalize(line, Normalizer.Form.NFD).replaceAll("\\p{Cntrl}", "");
			
			System.out.print(line+"\n");
			p1.println(line+"\n");

			
		    }
		
		session.disconnect();
		
		p1.close();
    }
    
    
    String testnapResult(String flistReceive) throws JSchException, IOException
    {
    	String result = "Null";
    	
    	//FileInputStream fstream = new FileInputStream("C:/WorkSpace/FileInput/SiebelDateCheck/flistOut.txt");
		//DataInputStream in = new DataInputStream(fstream);
		//BufferedReader brIn = new BufferedReader(new InputStreamReader(in));
    	
    	//FileInputStream fstream = new FileInputStream("C:/WorkSpace/FileInput/SiebelDateCheck/flistOut.txt");
		//DataInputStream in = new DataInputStream(fstream);
		//BufferedReader brIn = new BufferedReader(new InputStreamReader(in));
		
		InputStream is = new ByteArrayInputStream(flistReceive.getBytes());

		// read it with BufferedReader
		BufferedReader brIn = new BufferedReader(new InputStreamReader(is));
        
        PrintStream p1;
        FileOutputStream out1 = new FileOutputStream("C:/WorkSpace/FileInput/CheckTestnap/listOut.txt");
        p1 = new PrintStream(out1);
	   
        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session=jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig(config);
        session.connect();
        
        String strLine = "Null";
		//Session session = jsch.getSession(user, host, 22);
		
		Channel channel = session.openChannel("shell");
		
		OutputStream inputstream_for_the_channel = channel.getOutputStream();
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		
		channel.setOutputStream(System.out, true);
		
		System.out.print("=======================================");
		channel.connect();
		
		commander.println("azha");  
		commander.println("testnap");
		commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID           POID [0] 0.0.0.1 /plan -1 0\n"
				+ "XXX\n"
				+ "xop PCM_OP_TRANS_OPEN 65536 1");		
		
		commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID           POID [0] 0.0.0.1 /account 1 1\n"
				+ "0 PIN_FLD_COMPONENT       STR [0] \"CM\"\n"
				+ "0 PIN_FLD_LOGLEVEL        INT [0] 2\n"
				+ "XXX\n"
				+ "xop PCM_OP_INFMGR_SET_LOGLEVEL 0 1");
		
		String valueT = "";
		while ((strLine = brIn.readLine()) != null)  
		{		
			valueT = valueT + strLine + "\n";		
		}
		
		//System.out.print("-----------------");
		//System.out.print(valueT);
		//System.out.print("-----------------");
		
		commander.println(valueT);
		
		/*commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID                      POID [0] 0.0.0.1 /purchased_product 524770411812 0\n"
				+ "XXX\n"
				+ "xop PCM_OP_READ_OBJ 0 1");*/
		
		commander.println("r << XXX 1\n"
				+ "0 PIN_FLD_POID           POID [0] 0.0.0.1 /plan -1 0\n"
				+ "XXX\n"
				+ "xop PCM_OP_TRANS_ABORT 1 1");
		
		commander.println("q");
		commander.println("exit");
		commander.close();
		
		InputStream outputstream_from_the_channel = channel.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		String line;
		boolean toPrint = false;
		
		while ((line = br.readLine()) != null)
		    {
			//System.out.print(line+"\n");
			//line = Normalizer.normalize(line, Normalizer.Form.NFD).replaceAll("\\p{Cntrl}", "");
			
			System.out.print(line+"\n");
			//p1.println(line);
			if(line.indexOf("azha") < 0)
			result = result + line+"\n";

			
		    }
		
		session.disconnect();
		
		p1.close();
    	
    	
    	return result;
    	
    }

}
