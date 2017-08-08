package brm;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckEaiIdXMLServlet
 */
@WebServlet("/CheckEaiIdXMLServlet")
public class CheckEaiIdXMLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckEaiIdXMLServlet() {
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
		//doGet(req, resp);
		
		try
		{
			System.out.println("Rec...doPost - " + req.getParameter("eaiID") + " -");
			System.out.println("Receiving request..." + req.getSession().getId());
			
			MainChecking mc = new MainChecking();
			
			String eaiIDget = "Null";
			String payloadResult = "Null";
			String resultReturnToJSP = "Null";
			
			String int_msg_id = "Null";
			String event_name = "Null";
			String audit_date_time = "Null";
			String audit_type = "Null";
			String payload = "Null";

			
			Vector eaiResult = new Vector();
			
			if(req.getParameter("eaiID") != null)
			{
				eaiIDget = req.getParameter("eaiID");
				
				System.out.println("EAI ID ===." + eaiIDget);
				
				GetEAIXMLFromDbase gEAIID = new GetEAIXMLFromDbase();
				
				eaiResult = gEAIID.checkEAITable(eaiIDget);
				
				if(eaiResult.size()>0)
				{
			    	for(int y=0;y<eaiResult.size();y++)
			    	{
			    		//System.out.println(y+1 + ":: " + y);
			    		Vector info = (Vector)eaiResult.elementAt(y);
			    		int_msg_id = (String)info.elementAt(0);
			    		audit_date_time = (String)info.elementAt(1);	
			    		audit_type = (String)info.elementAt(2);	
			    		payload = (String)info.elementAt(3);
						
			    		//System.out.println(int_msg_id + "|");
			    		break;
			    		
			    	}
				}
				else
				{
					payload = "Cannot find EAI XML Request on - " + eaiIDget;
				}
				


				
			}
			else
			{
				resultReturnToJSP = "Error - Parameter Sent is null ";
			}
			

			
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/getIcpEaiXML.jsp?payload=" + payload));
			
			System.out.println("Completed request..." + req.getSession().getId());
			System.out.println("Result == " + resultReturnToJSP);
			//System.out.println("System == " + systemOwner);
			
			dispatcher.forward(req, resp);
			return;
		}
		catch(Exception e)
		{
			System.out.println(req.getSession().getId() + " " + e.getMessage());
		}
	}

}
