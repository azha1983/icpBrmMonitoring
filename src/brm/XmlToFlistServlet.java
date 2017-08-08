package brm;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class XmlToFlistServlet
 */
@WebServlet("/XmlToFlistServlet")
public class XmlToFlistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XmlToFlistServlet() {
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
		if(req.getParameter("xmlValue") != null)
		{
			String resultReturn = "Null";
			XmlToFlist x = new XmlToFlist();
			
			try {
				
				if(req.getParameter("xmlValue").indexOf("<flist>") > -1)
				{
				resultReturn = x.xmlToFListMain(req.getParameter("xmlValue").toString());
				
				//if(resultReturn.equalsIgnoreCase("Null"))
				//{
					
				//}
				
				RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/XmlToFlist.jsp?resultFlist=" + resultReturn));
				dispatcher.forward(req, resp);
				return;
				}
				else
				{
					RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/XmlToFlist.jsp?resultFlist=XML sent is invalid"));
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

}
