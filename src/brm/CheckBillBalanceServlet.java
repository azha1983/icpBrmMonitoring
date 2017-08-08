package brm;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckBillBalanceServlet
 */
@WebServlet("/CheckBillBalanceServlet")
public class CheckBillBalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckBillBalanceServlet() {
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
			

			//dateIn = req.getParameter("bp") + " " + req.getParameter("month") + " " + req.getParameter("year");
			
			int bp = Integer.parseInt(req.getParameter("bp"));
			
			if(bp<10)
			dateIn = req.getParameter("year") + "-" + req.getParameter("month") + "-0" + bp + " 00:00:00";
			else
			dateIn = req.getParameter("year") + "-" + req.getParameter("month") + "-" + bp + " 00:00:00";
			
			String expectedPattern = "yyyy-MMM-dd HH:mm:ss";
			//year+"-"+month+"-0"+String.valueOf(x)+" 00:00:00";
			SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);		
			formatter.toString();
			Date date = formatter.parse(dateIn);
			long unixTime = (long) date.getTime()/1000;
			
			String dateUnix = String.valueOf(unixTime);
			System.out.println("-------------------------------------------------");
			System.out.println("Value Check  Bill Balance servlet - " + dateUnix);
			System.out.println("Value Check Bill Balance servlet - " + dateIn);
			System.out.println("-------------------------------------------------");
			

			BillBalMonitor im = new BillBalMonitor();
			im.setDateIn(dateUnix);
			im.generate();
			
			String bal1 = "Null";
			
			bal1 = im.getLeftBill(im.getDateIn());
			//resp.getsession.setAttribute("reporter",im);
			resp.sendRedirect(resp.encodeURL("/IcpBrmException/CheckBillBal.jsp?bal1=" + bal1));
		}
		catch(Exception e)
		{
			System.out.println("-------------------Exception on servlet------------------------------" + e);
		}
	}

	
	
}
