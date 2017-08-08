package brm;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.Normalizer;
import java.util.ResourceBundle;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;



public class BRMNodeCheck {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");


	public static void main( String[] args ) throws Exception 
	{

		BRMNodeCheck b = new BRMNodeCheck();
		
        String host="10.41.66.11";
        String user="S53725";
        String password="azha1234";
        String command1="/home/pin/check.sh";
        
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
        

		//Session session = jsch.getSession(user, host, 22);
		
		Channel channel = session.openChannel("shell");
		
		OutputStream inputstream_for_the_channel = channel.getOutputStream();
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		
		channel.setOutputStream(System.out, true);
		
		//channel.setExtOutputStream(p1.print(System.out), true);
		
		System.out.print("=======================================");
		channel.connect();
		
		commander.println(command1);    
		//commander.println("cd folder");
		//commander.println("ls -la");
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
			
			if(line.indexOf("/app/brm/base") > -1)
			toPrint = true;

			
			if(toPrint)
			p1.println(line+"\n");
			
		    }
		
		session.disconnect();
		
		p1.close();
		
		//this.MassageCheckOutput mO = new MassageCheckOutput();
		
		String returnVal = "Null";
		b.massageOutput();
		returnVal = b.valueFinal2();
		System.out.println("================================");
		System.out.println("===============FINAL=================");
		System.out.println(returnVal);
		System.out.println("================================");
		b.setValueCheckReturn(returnVal);
		

	
	}
	
	private String valueFinal2() throws IOException
	{
		
		 BufferedReader brF = new BufferedReader(new FileReader(res.getString("file27.NodeCheck1")));
		    try {
		        StringBuilder sbF = new StringBuilder();
		        String lineF = brF.readLine();

		        while (lineF != null) {
		        	
		        	if(lineF.length()>0)
		        	sbF.append(lineF + "\n");
		            //sbF.append("\n");
		            lineF = brF.readLine();
		        }
		        return sbF.toString();
		    } finally {
		    	brF.close();
		    }	
	}
	
	public void connectToNode(String host) throws JSchException, IOException
	{
	
		System.out.println("================================");
		System.out.println("================================" + host);

		//BRMNodeCheck b = new BRMNodeCheck();
		   
       //String host="10.41.66.11";
        String user="S53725";
        String password="S53725";
        String command1="/home/pin/check.sh";
        
        PrintStream p1;
        FileOutputStream out1 = new FileOutputStream(res.getString("file28.NodeCheck2"));
        p1 = new PrintStream(out1);
	   
        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session=jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig(config);
        session.connect();
        

		//Session session = jsch.getSession(user, host, 22);
		
		Channel channel = session.openChannel("shell");
		
		OutputStream inputstream_for_the_channel = channel.getOutputStream();
		PrintStream commander = new PrintStream(inputstream_for_the_channel, true);
		
		channel.setOutputStream(System.out, true);
		
		//channel.setExtOutputStream(p1.print(System.out), true);
		
		//System.out.print("=======================================");
		channel.connect();
		
		commander.println(command1);    
		//commander.println("cd folder");
		//commander.println("ls -la");
		commander.println("exit");
		commander.close();
		
		InputStream outputstream_from_the_channel = channel.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(outputstream_from_the_channel));
		String line;
		boolean toPrint = false;
		
		while ((line = br.readLine()) != null)
		    {
			//System.out.print(line+"\n");
			line = Normalizer.normalize(line, Normalizer.Form.NFD).replaceAll("\\p{Cntrl}", "");
			
			System.out.print(line+"\n");
			
			if(line.indexOf("[32m") > -1 )
			{	
				line = line.replace("[32m", "");
				line = line.replace("[0m", "");
			}
			else
				//do nothing}
			
			if(line.indexOf("/app/brm/base") > -1)
			toPrint = true;

			
			if(toPrint)
			p1.println(line+"\n");
			
		    }
		
		session.disconnect();
		
		p1.close();
		
		//this.MassageCheckOutput mO = new MassageCheckOutput();
		String returnVal = "Null";
		massageOutput();
		returnVal = this.valueFinal2();
		System.out.println("================================");
		System.out.println("===============FINAL=================");
		System.out.println(returnVal);
		System.out.println("================================");
		this.setValueCheckReturn(returnVal);
	
	}
	

	private String valueFinal() throws IOException
	{
		
		 BufferedReader brF = new BufferedReader(new FileReader(res.getString("file27.NodeCheck1")));
		    try {
		        StringBuilder sbF = new StringBuilder();
		        String lineF = brF.readLine();

		        while (lineF != null) {
		        	sbF.append(lineF + "\n");
		            //sbF.append("\n");
		            lineF = brF.readLine();
		        }
		        return sbF.toString();
		    } finally {
		    	brF.close();
		    }	
	}
	
	
	   public void massageOutput() throws IOException
	   {
	        PrintStream p1;
	        FileOutputStream out1 = new FileOutputStream(res.getString("file27.NodeCheck1"));
	        p1 = new PrintStream(out1);
	        
			FileInputStream fstream = new FileInputStream(res.getString("file28.NodeCheck2"));
			
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br1 = new BufferedReader(new InputStreamReader(in));
			
			int x = 1;
			
			String strLine = "Null";
			
			String out = "Null";
			boolean toWrite = false;
			
			while ((strLine = br1.readLine()) != null)  
			{
				//strLine = strLine.trim();
				
				///System.out.println(x + "(" + strLine + ")");
				//System.out.println(x + "(" + strLine.length() + ")");
				
				if(strLine.length() < 0)
				strLine = "xxxxxxx" + strLine;
				

				if(strLine != null && strLine.indexOf("xxxxxxx") <= 0)
				{
					//System.out.println( x + "=" + strLine + "=");
					
					if(strLine.indexOf("STATUS") > -1 || strLine.indexOf("Process") > -1 || strLine.indexOf("TESTNAP") > -1)
					toWrite = true;					
					else if(strLine.indexOf("xxxxxxx") <= 0)
					toWrite = false;	
					
					if(strLine.indexOf("/app/brm/base> exit") > -1 && toWrite)
					{
						toWrite = false;
						out = "";
						p1.println(out);
						break;
					}
					
					
					if(toWrite)
					{
						
						//System.out.println( x + "==!WRITE!===" + out);
						out = strLine + "\n";				
						p1.println(out);  

					}
				}
				else
				{
					//p1.println(x + "-----------");
					//System.out.println(strLine);
				}
				
				x++;
			} 
	   }
	   
	   
		String valueCheckReturn = "Null";
		
		public String getValueCheckReturn() {
			return valueCheckReturn;
		}


		public void setValueCheckReturn(String valueCheckReturn) {
			this.valueCheckReturn = valueCheckReturn;
		}

}
