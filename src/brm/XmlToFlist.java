package brm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ResourceBundle;

import org.xml.sax.InputSource;

import com.portal.pcm.XMLToFlist; 

public class XmlToFlist {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");
	
    public static void main(String args[])throws Exception
    {
    	
    	//TestReadFile t = new TestReadFile();
    	
    	//HashMap h=new HashMap();                        
    	FileInputStream fin=new FileInputStream("mainopcode");
    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
    	
    	PrintStream p1;
    	FileOutputStream out1 = new FileOutputStream("C:/WorkSpace/FileInput/Testxml.txt");
    	p1 = new PrintStream(out1);
    	
    	
    	String strLine = "";
    	String output = "";

    	boolean test = false;
    	
    	String opcode = "";
    	int opBegin = 0;
    	int opEnd = 0;
    	boolean opcodeCheck = false;
    	
    	while ((strLine = br.readLine()) != null)  
		{

			if(strLine.equalsIgnoreCase("<flist>"))
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
			
			if(strLine.indexOf("</opcode>") > -1 && opcodeCheck == false)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				opcode = strLine.substring(opBegin+1, opEnd);
				opcodeCheck = true;
			}
			
			//System.out.println("----------" + a++);
		
		}
    	
 	
    	// System.out.println("----------1");
    	
    	 String xml=readFile( "C:/WorkSpace/FileInput/Testxml.txt" );
         
    	// System.out.println("----------2");
    	 
        // System.out.println(xml);
         
         //System.out.println("----------3");
         
         XMLToFlist x = XMLToFlist.getInstance();
         
         InputSource inputSource = new InputSource( new StringReader( xml ) );        
         x.convert(inputSource);      
         //x.getFList();
         
        // System.out.println("----------------------------");
         System.out.println(x.getFList() + "XXX");
         System.out.print("xop " + opcode + " 0 1");
    	
    	br.close();
    	p1.close();
    }
    
    public String xmlToFListMain(String inputXML) throws Exception
    {
    	String returnValue = "Null";
    	
    	//FileInputStream fin=new FileInputStream(inputXML);
    	InputStream is = new ByteArrayInputStream(inputXML.getBytes());
    	BufferedReader br=new BufferedReader(new InputStreamReader(is));
    	
    	PrintStream p1;
    	FileOutputStream out1 = new FileOutputStream(res.getString("file21.xmlTemp"));
    	p1 = new PrintStream(out1);
    	
    	PrintStream p2;
    	FileOutputStream out2 = new FileOutputStream(res.getString("file31.TestFList"));
    	p2 = new PrintStream(out2);
    	
    	
    	String strLine = "";
    	String output = "";

    	boolean test = false;
    	
    	String opcode = "";
    	int opBegin = 0;
    	int opEnd = 0;
    	boolean opcodeCheck = false;
    	
    	while ((strLine = br.readLine()) != null)  
		{

			if(strLine.equalsIgnoreCase("<flist>"))
			test = true;		
			
			if(strLine.indexOf("</flist>]]>") > -1)
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
			
			if(strLine.indexOf("</opcode>") > -1 && opcodeCheck == false)
			{

				opBegin = strLine.indexOf(">");
				opEnd = strLine.indexOf("</");
				
				opcode = strLine.substring(opBegin+1, opEnd);
				opcodeCheck = true;
			}
			
			//System.out.println("----------" + a++);
		
		}
    	
    	if(opcode.indexOf("<opcode>") > -1)
    	{
    		System.out.println("Opcode not properly get----------" + opcode);  		
    		opcode = opcode.replace("<opcode>", "");
    		System.out.println("Fixed already----------" + opcode);
    	}
 	
    	// System.out.println("----------1");
    	
    	 String xml=readFile( res.getString("file21.xmlTemp") );
         
    	// System.out.println("----------2");
    	 
        // System.out.println(xml);
         
         //System.out.println("----------3");
         
         XMLToFlist x = XMLToFlist.getInstance();
         
         InputSource inputSource = new InputSource( new StringReader( xml ) );        
         x.convert(inputSource);      
         //x.getFList();
         
        // System.out.println("----------------------------");
         System.out.println(x.getFList() + "XXX");
         System.out.print("xop " + opcode + " 0 1");
         
         returnValue = "r << XXX 1" + "\n" + x.getFList() + "XXX" + "\n" + "xop " + opcode + " 0 1";
         
         System.out.println(returnValue);
         
    	br.close();
    	p1.close();
    	
    	return returnValue;
    	
    }
    
    
    static  String readFile( String file ) throws Exception {
        BufferedReader reader = new BufferedReader( new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
            stringBuilder.append( ls );
        }

        return stringBuilder.toString();
    }



}
