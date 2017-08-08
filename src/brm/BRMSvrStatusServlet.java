package brm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcraft.jsch.JSchException;

/**
 * Servlet implementation class BRMSvrStatusServlet
 */
@WebServlet("/BRMSvrStatusServlet")
public class BRMSvrStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRMSvrStatusServlet() {
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
		
		String nodeReq = "Null";
		
		String ipAddress = "Null";
		String nodeNo = "Null";
		
		if(req.getParameter("chooseNode") != null && !req.getParameter("chooseNode").equalsIgnoreCase("None"))
		{
			BRMNodeCheck brmNode = new BRMNodeCheck();
			
			System.out.println("==============1st==================" + req.getParameter("chooseNode"));
			
			nodeReq = req.getParameter("chooseNode");
			
			
			if(nodeReq.equalsIgnoreCase("ICPNode1"))
			{
				ipAddress = "10.41.66.10";
				nodeNo = "Node 1";
			}
			else if(nodeReq.equalsIgnoreCase("ICPNode2"))
			{
				ipAddress = "10.41.66.11";
				nodeNo = "Node 2";
			}
			else if(nodeReq.equalsIgnoreCase("ICPNode3"))
			{
				ipAddress = "10.41.66.12";
				nodeNo = "Node 3";
			}
			else
			{
				System.out.println("Shouldnt be in HERE");
			}
			
			try
			{
				System.out.println("TO CHECK NODE = " + ipAddress);
				
				brmNode.connectToNode(ipAddress);
				System.out.println("==============B4 RETURN==================");
				System.out.println(brmNode.getValueCheckReturn());
				System.out.println("==============B4 RETURN==================");
				
				RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/BRMServerCheck.jsp?resultNode=" + 
				brmNode.getValueCheckReturn() + "&&ip=" + ipAddress+ "&&nodeNo=" + nodeNo));
				dispatcher.forward(req, resp);
				return;
				
			} catch (JSchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/BRMServerCheck.jsp?resultNode=None&&nodeNo=None&&ip=None"));
			dispatcher.forward(req, resp);
			return;
		}
		
	}

}
