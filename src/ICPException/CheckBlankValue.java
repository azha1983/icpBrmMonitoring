package ICPException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ResourceBundle;



public class CheckBlankValue {
	

    
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");

    
	public static void main( String[] args ) throws Exception 
	{
		CheckBlankValue o = new CheckBlankValue();
		
		o.err_Blank_Value("mainopcode");
		System.out.println("Blank value found. It is " + o.getOpcodeLineReturn());
		
		
	}
	
	
	   public void err_Blank_Value(String fileURL) throws Exception
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

	    	boolean opcodeCheck = false;
	    	String opcodeLine = "Null";
	    	
	    	while ((strLine = br.readLine()) != null)  
	   		{
	   			
	    		
	    		System.out.println("Strline" + strLine);
	   			if(strLine.indexOf("BLANK") > -1 && opcodeCheck == false)
	   			{

	   				opcodeLine = strLine;
	   				opcodeCheck = true;
	   				System.out.println("BLANK - strLine - " + strLine);
	   				break;
	   				
	   			}
	   			else if(strLine.indexOf("<ACCOUNT_OBJ/>") > -1 && opcodeCheck == false)
	   			{
	   				opcodeLine = strLine;
	   				opcodeCheck = true;
	   				System.out.println("strLine - " + strLine);
	   				break;
	   			}
	   			
	   			
	   			
	   		
	   		}
	    	
	    	
	    	System.out.println("============ - " + opcodeLine);
	    	this.setOpcodeLineReturn(opcodeLine);
	    	

	    	
	    
	    }

	   
	   public String opcodeLineReturn = "";


	public String getOpcodeLineReturn() {
		return opcodeLineReturn;
	}

	public void setOpcodeLineReturn(String opcodeLineReturn) {
		this.opcodeLineReturn = opcodeLineReturn;
	}
}
