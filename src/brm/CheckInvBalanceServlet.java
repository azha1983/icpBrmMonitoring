package brm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckInvBalanceServlet
 */
@WebServlet("/CheckInvBalanceServlet")
public class CheckInvBalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckInvBalanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
		
		try
		{
			System.out.println("Rec...doPost - " + req.getParameter("bp") + " -");
			String dateIn = "Null";
			

			dateIn = req.getParameter("bp") + " " + req.getParameter("month") + " " + req.getParameter("year");
			String expectedPattern = "dd MMM yyyy";
			SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);		   
			Date date = formatter.parse(dateIn);
			long unixTime = (long) date.getTime()/1000;
			
			String dateUnix = String.valueOf(unixTime);
			System.out.println("-------------------------------------------------");
			System.out.println("Value Check Invoice Balance servlet - " + dateUnix);
			System.out.println("Value Check Invoice Balance servlet - " + dateIn);
			System.out.println("-------------------------------------------------");
			
			InvBalMonitor im = new InvBalMonitor();
			im.setDateIn(dateUnix);
			im.generate();
			String bal1 = "Null";
			
			bal1 = im.getLeftInvoice(im.getDateIn());
			//resp.getsession.setAttribute("reporter",im);
			resp.sendRedirect(resp.encodeURL("/IcpBrmException/CheckInvBal.jsp?bal1=" + bal1));
		}
		catch(Exception e)
		{
			System.out.println("-------------------Exception on servlet------------------------------" + e);
		}
	}
	


}
