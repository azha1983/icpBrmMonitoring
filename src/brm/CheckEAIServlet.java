package brm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ICPException.CheckBRMFromEAIID;


/**
 * Servlet implementation class CheckEAIServlet
 */

public class CheckEAIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckEAIServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try
		{
			System.out.println("Rec...doGet");
			System.out.println("Receiving request..." + req.getSession().getId());
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/test.jsp"));
			System.out.println("Completed request..." + req.getSession().getId());
			
			dispatcher.forward(req, resp);
			return;
		}
		catch(Exception e)
		{
			System.out.println(req.getSession().getId() + " " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try
		{
			System.out.println("Rec...doPost - " + req.getParameter("siebelOrderID") + " -");
			System.out.println("Receiving request..." + req.getSession().getId());
			
			MainChecking mc = new MainChecking();
			
			String siebelOrderID = "Null";
			String EAIId = "Null";
			String resultReturnToJSP = "Null";
			String systemOwner = "Null";
			String osmID = "Null";
			String BRM_remarks = "Null";
			
			if(req.getParameter("siebelOrderID") != null)
			{
				siebelOrderID = req.getParameter("siebelOrderID");
				
				System.out.println("siebelOrderID===." + siebelOrderID);
				
				CheckSBLStatus sbl = new CheckSBLStatus();
				String SBLstatus = "Null";
				
				SBLstatus = sbl.getSBLStatus(siebelOrderID);
				
				System.out.println("siebelOrderID===Status== " + SBLstatus);
				
				if(SBLstatus.equalsIgnoreCase("Complete"))
				{
					resultReturnToJSP = "SIEBEL ORDER ID - " + siebelOrderID + " already Completed in SIEBEL. Please close IRIS.";
					osmID = "None";
				}
				else
				{
					EAIId = mc.CheckExceptionFromSiebelID(siebelOrderID);
					
					//System.out.println("siebelOrderID===2==" + mc.getOsm_id_return());
					
					osmID = mc.getOsm_id_return();
					
					System.out.println("EAI ID recieve = " + EAIId);
					
					CheckBRMFromEAIID chBRM = new CheckBRMFromEAIID();
					
					resultReturnToJSP = chBRM.checkEAIOPcodeSentDetail(EAIId);
				}

				
			}
			else
			{
				resultReturnToJSP = "Error - Parameter Sent is null ";
			}
			
			
			systemOwner = this.checkSystemOwner(resultReturnToJSP);
			
			BRM_remarks = this.checkBRM_Remarks(resultReturnToJSP);
			
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/test.jsp?resultBRM=" + BRM_remarks + "&&system=" 
			+ systemOwner + "&&siebelOrderID=" + siebelOrderID + "&&OSM_ID=" + osmID));
			
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
	
	
	private String checkSystemOwner(String resultReturnToJSP)
	{
		String systemOwner = "Null";
		
		if(resultReturnToJSP.indexOf("Siebel") > -1)
		{
			systemOwner = "ICP-Siebel-Orders";
			System.out.println("Siebel===1");
		}
		else if(resultReturnToJSP.indexOf("[EAI]") > -1)
		{
			systemOwner = "ICP-EAI";
			System.out.println("EAI===1");
		}
		else if(resultReturnToJSP.indexOf("[OSM]") > -1)
		{
			systemOwner = "ICP-OSM";
			System.out.println("OSM===1");
		}
		else if(resultReturnToJSP.indexOf("SIEBEL ORDER ID -") > -1)
		{
			systemOwner = "None";
			System.out.println("None===1");
		}
		else
		{
			systemOwner = "ICP-Billing"; 
			System.out.println("siebelOrderID===1");
		}
		
		return systemOwner;
	}
	
	private String checkBRM_Remarks(String resultReturnToJSP)
	{
		String returnValue = "Null";
		
		if(resultReturnToJSP.indexOf("Null|Null|Cannot find BRM opcode") > -1)
		{
			returnValue = "[BRM] Cannot find exception detail. Please forward to BRM Team";
		}
		else
		{
			returnValue = resultReturnToJSP; 
		}
		
		return returnValue;
	}
	 

}
