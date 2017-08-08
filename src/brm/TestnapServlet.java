package brm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestnapServlet
 */
@WebServlet("/TestnapServlet")
public class TestnapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestnapServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.getWriter().append("Served at: ").append(req.getContextPath());
		if(req.getParameter("xmlValue") != null)
		{
			String flistOutput = "Null";
			XmlToFlist x = new XmlToFlist();
			TestnapOnJava t = new TestnapOnJava();
			String resultReturn = "Null";;
			
			try {
				

				if(req.getParameter("xmlValue").indexOf("PCM_OP_CUST_COMMIT_CUSTOMER") > -1)
				{
					RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/testnapBRM.jsp?resultFlist=PCM_OP_CUST_COMMIT_CUSTOMER cannot be use here"));
					dispatcher.forward(req, resp);
					return;	
				}
				else if(req.getParameter("xmlValue").indexOf("<flist>") > -1)
				{
					flistOutput = x.xmlToFListMain(req.getParameter("xmlValue").toString());
					
					resultReturn = t.testnapResult(flistOutput);
				
				
				RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/testnapBRM.jsp?resultFlist=" + resultReturn));
				dispatcher.forward(req, resp);
				return;
				}
				else
				{
					RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/testnapBRM.jsp?resultFlist=XML sent is invalid"));
					dispatcher.forward(req, resp);
					return;	
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Parameter sent is NULL");
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/XmlToFlist.jsp?resultFlist=XML sent is NULL"));
			dispatcher.forward(req, resp);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
