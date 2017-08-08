package brm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

public class CheckInvBillOutput {
	
	private static ResourceBundle res = ResourceBundle.getBundle("dailyList");

	
    public static void main(String args[])throws Exception
    {
    	CheckInvBillOutput ci = new CheckInvBillOutput();
    	
    	Vector t = new Vector();
    	
    	/*t = ci.getInvoiceOutput();
    	
    	for(int x=0;x<t.size();x++)
    	{
    		System.out.println("Program status : " + t.elementAt(x));
    	}*/
    	
    	t = ci.getBillOutput();
    	
    	System.out.println("Program status : " + t.size());
    	
    	String value1 = "";
    	String valueLast = "";
    	
    	for(int x=0;x<t.size();x++)
    	{
    		if(x==0)
    		value1 = (String) t.elementAt(x);	
    		
    		if(x==t.size()-1)
    		valueLast = (String) t.elementAt(x);	
    			
    		
    		System.out.println("Program status : " + t.elementAt(x));
    		
    	}
    	
    	
    	System.out.println("value 1st = " + value1);
    	System.out.println("value 1svalueLastt = " + valueLast);
    	
    	String start1 = "Null";
    	String start2 = "Null";
    	String last1 = "Null";
    	String last2 = "Null";
    	
    	StringTokenizer st1 = new StringTokenizer(value1, "|");
    	start1 = st1.nextToken();
    	last1 = st1.nextToken();
    	
    	StringTokenizer st2 = new StringTokenizer(valueLast, "|");
    	start2 = st2.nextToken();
    	last2 = st2.nextToken();
    	
    	int minus = 0;
    	
    	minus = Integer.parseInt(last1) - Integer.parseInt(last2);
    	
    	int tp = minus/60;
    	
    	System.out.println(last1 + " - " + last2 + " = " + minus);
    	System.out.println(minus + "/60 = " + tp);


    }
    

	public Vector getBillOutput() throws IOException
    {
		Vector toReturn = new Vector();
      	FileInputStream fin=new FileInputStream(res.getString("file24.BillBalMon1"));
    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
    	
    	String strLine = "Null";
    	
    	
    	while ((strLine = br.readLine()) != null)  
   		{
    		//System.out.println("Program status : " + strLine);
    		toReturn.addElement(strLine);

   		}
    	
    	return toReturn;
    }

	public Vector getInvoiceOutput() throws IOException
    {
		Vector toReturn = new Vector();
      	FileInputStream fin=new FileInputStream(res.getString("file29.CheckInvBill1"));
    	BufferedReader br=new BufferedReader(new InputStreamReader(fin));
    	
    	String strLine = "Null";
    	
    	
    	while ((strLine = br.readLine()) != null)  
   		{
    		//System.out.println("Program status : " + strLine);
    		toReturn.addElement(strLine);

   		}
    	
    	return toReturn;
    }
	
	public String checkTP()
	{
		String retVal = "";
		
		return retVal;
	}
	
}
