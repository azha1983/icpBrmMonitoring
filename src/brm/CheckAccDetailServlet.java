package brm;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ICPException.CheckBRMFromEAIID;

/**
 * Servlet implementation class CheckAccDetailServlet
 */
@WebServlet("/CheckAccDetailServlet")
public class CheckAccDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckAccDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		try
		{
			System.out.println("Rec...doPost - " + req.getParameter("siebelOrderID") + " -");
			System.out.println("Receiving request..." + req.getSession().getId());
			
			MainChecking mc = new MainChecking();
			
			String baNum = "Null";
			String EAIId = "Null";
			String resultReturnToJSP = "Null";
			String systemOwner = "Null";
			String osmID = "Null";
			String BRM_remarks = "Null";
			
			if(req.getParameter("BaNumber") != null)
			{
				baNum = req.getParameter("BaNumber");
				
				System.out.println("BaNumber===." + baNum);
				
			
				BRMCheckAccPoid baCheck = new BRMCheckAccPoid();
				Vector checkDet = new Vector();
				
				checkDet = baCheck.checkAccountNumber(req.getParameter("BaNumber"));
				
				if(checkDet.size() > 0)
				{
					BRM_remarks = "Ade service";
				}
				else
				{
					BRM_remarks = "takde service";
				}

				
			}
			else
			{
				resultReturnToJSP = "Error - Parameter Sent is null ";
			}
			

			
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/ICP_BRM.jsp?resultBRM=" + BRM_remarks + "&BAnum=" + baNum));
			
			System.out.println("Completed request..." + req.getSession().getId());
			System.out.println("Result == " + resultReturnToJSP);
			System.out.println("System == " + systemOwner);
			
			dispatcher.forward(req, resp);
			return;
		}
		catch(Exception e)
		{
			System.out.println(req.getSession().getId() + " " + e.getMessage());
		}
	
	}

}
