package brm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtendingExample {

	   public static void main(String args[]) throws ParseException
	   {
		  /* JavaThread cnt = new JavaThread();
	      try
	      {
	         while(cnt.isAlive())
	         {
	           System.out.println("Main thread will be alive till the child thread is live");
	           Thread.sleep(1500);
	         }
	      }
	      catch(InterruptedException e)
	      {
	        System.out.println("Main thread interrupted");
	      }
	      System.out.println("Main thread's run is over" );
	      */
		   //1466784000
		   String dateIn = "25 Jun 2016";
		   String expectedPattern = "dd MMM yyyy";
		   SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);		   
		   Date date = formatter.parse(dateIn);

		   System.out.println(date);
		   
		   long unixTime = (long) date.getTime()/1000;
		   
		   System.out.println(unixTime );
		   
	   }
}
