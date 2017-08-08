package BillInvRun;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ResourceBundle;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class BillRunProgStatus {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
    /*public static String host="10.41.66.10";
    public static String user="S53725";
    public static String password="Kuda1983";
    public static String command1="azha";*/
    
    public String returnValue = "";
    
    public String getReturnValue() {
		return returnValue;
	}


	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}


	public static void main( String[] args ) throws IOException, ParseException, JSchException 
    {
    	//BillRunProgStatus brun = new BillRunProgStatus();
    	
        PrintStream p1;
        FileOutputStream out1 = new FileOutputStream(res.getString("file32.Programstatus"));
        p1 = new PrintStream(out1);
    	
        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session=jsch.getSession(res.getString("unixUsername"), res.getString("unixHost"), 22);
        session.setPassword(res.getString("unixPassword"));
        session.setConfig(config);
        session.connect();
        
        String strLine = "";
		//Session session = jsch.getSession(user, host, 22);
		
		Channel channel = session.openChannel("shell");
		
		OutputStream inputstream_for_the_channel = channel.getOutputStream();
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		
		channel.setOutputStream(System.out, true);
		
		System.out.print("=======================================");
		channel.connect();
		
		commander.println("cd /app/brm/base/bin");
		commander.println("ps -ef | grep pin_");
		
		
		//commander.println("q");
		commander.println("exit");
		commander.close();
		
		InputStream outputstream_from_the_channel = channel.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		String line;
		boolean toPrint = false;
		
		while ((line = br.readLine()) != null)
		    {
			System.out.print(line+"\n");
			line = Normalizer.normalize(line, Normalizer.Form.NFD).replaceAll("\\p{Cntrl}", "");
			
			System.out.print(line+"\n");
			
			if(line.indexOf("[32m") > -1 )
			{	
				line = line.replace("[32m", "");
				line = line.replace("[0m", "");
			}
			else
				//do nothing}
			
			if(line.indexOf("/app/brm/base/bin> ps -ef") > -1)
			toPrint = true;

			
			if(toPrint)
				{
					p1.println(line+"\n");
					strLine = strLine + "\n"+ line+"\n";
				}
			
			
		    }
		
		System.out.print("1=======================================");
		System.out.print(strLine);
		System.out.print("2=======================================");
		
		p1.close();
		session.disconnect();

    }
    
    
    public String checkProgramStatus(String progName)
    {
    	
    	String returnVal = "";
    	try
    	{
    		 PrintStream p1;
    	        FileOutputStream out1 = new FileOutputStream("C:/WorkSpace/FileInput/CheckTestnap/BillInvStatus.txt");
    	        p1 = new PrintStream(out1);
    	    	
    	        java.util.Properties config = new java.util.Properties(); 
    	        config.put("StrictHostKeyChecking", "no");
    	        JSch jsch = new JSch();
    	        Session session=jsch.getSession(res.getString("unixUsername"), res.getString("unixHost"), 22);
    	        session.setPassword(res.getString("unixPassword"));
    	        session.setConfig(config);
    	        session.connect();
    	        
    	        String strLine = "";
    			//Session session = jsch.getSession(user, host, 22);
    			
    			Channel channel = session.openChannel("shell");
    			
    			OutputStream inputstream_for_the_channel = channel.getOutputStream();
    			PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
    			
    			channel.setOutputStream(System.out, true);
    			
    			System.out.print("=======================================");
    			channel.connect();
    			
    			commander.println("cd /app/brm/base/bin");
    			commander.println("ps -ef | grep pin_");
    			
    			
    			//commander.println("q");
    			commander.println("exit");
    			commander.close();
    			
    			InputStream outputstream_from_the_channel = channel.getInputStream();
    			BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
    			String line;
    			boolean toPrint = false;
    			
    			while ((line = br.readLine()) != null)
    			    {
    				System.out.print(line+"\n");
    				line = Normalizer.normalize(line, Normalizer.Form.NFD).replaceAll("\\p{Cntrl}", "");
    				
    				System.out.print(line+"\n");
    				
    				if(line.indexOf("[32m") > -1 )
    				{	
    					line = line.replace("[32m", "");
    					line = line.replace("[0m", "");
    				}
    				else
    					//do nothing}
    				
    				if(line.indexOf("/app/brm/base/bin> ps -ef") > -1)
    				toPrint = true;

    				
    				if(toPrint)
	    				{
    					p1.println(line+"\n");
    					strLine = strLine + "\n"+ line+"\n";
	    				}
    				
    			    }
    			
    			this.setReturnValue(strLine);
    			p1.close();
    			session.disconnect();
    		
    	}
    	catch(Exception e)
    	{
    		System.out.print("====================Exception on unix===================" + e);
    	}
    	
    	
    	return returnVal;
    }

}
