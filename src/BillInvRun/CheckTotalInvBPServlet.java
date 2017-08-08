package BillInvRun;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckTotalInvBPServlet
 */
@WebServlet("/CheckTotalInvBPServlet")
public class CheckTotalInvBPServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckTotalInvBPServlet() {
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
		try
		{
			System.out.println("Rec...doPost - " + req.getParameter("month") + " -");
			System.out.println("Rec...doPost - " + req.getParameter("year") + " -");
			System.out.println("doPost..." + req.getSession().getId());
			
			TotalBillBP tbp = new TotalBillBP();
			
			String monthRec = req.getParameter("month") + " " + req.getParameter("year");
			
			//req.getSession().setAttribute("objeto", tbp);
			   Vector c = new Vector();
			   c = tbp.getTotalInvBP(req.getParameter("month"),req.getParameter("year"));
			   
			   if(c.size()>0)
			   {
				   System.out.println("-------------------------------------------------ADA");
				   for(int i=0;i<c.size();i++)
				   {
					   System.out.println(i + "--> " + c.elementAt(i)); 
				   }
			   }
			   else
			   {
				   System.out.println("-------------------------------------------------EMPTY");
			   }
			
			   System.out.println("-1-");
			   req.getSession().setAttribute("objetBPInv", c);
			   System.out.println("-2-");
			   RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/BillInvCheck/TotalBPInv.jsp?bpSent=yes&monthcheck=" + monthRec));
			   System.out.println("-3-");
			   dispatcher.forward(req, resp);
			   
			   return;
			   
		}
		catch(Exception e)
		{
			System.out.println("-1-" + e);
		}
		doGet(req, resp);
	}

}
