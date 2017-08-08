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
 * Servlet implementation class AccountObjectServlet
 */
@WebServlet("/AccountObjectServlet")
public class AccountObjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountObjectServlet() {
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
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/AccntObjDetail.jsp"));
			System.out.println("Completed request..." + req.getSession().getId());
			
			dispatcher.forward(req, resp);
			return;
		}
		catch(Exception e)
		{
			System.out.println(req.getSession().getId() + " " + e.getMessage());
		}
	
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
			String accPoid = "Null";
			String resultReturnToJSP = "Null";
			
			 
			String accPoid2Return = "Null";
			String billInfo2Return = "Null";
			String profElem0_2Return = "Null";
			String profElem1_2Return = "Null";
			Vector payInfo = new Vector();
			String payInfo2Return = "Null";
			String BRMRemarks = "Null";
			boolean canReturn = false;

			
			if(req.getParameter("BaNumber") != null)
			{
				baNum = req.getParameter("BaNumber");
				
				System.out.println("BaNumber===." + baNum);
				
			
				BRMCheckAccPoid baCheck = new BRMCheckAccPoid();
				//Vector accPoidCheck = new Vector();
				
				accPoid = baCheck.getAccountPoid(req.getParameter("BaNumber"));
				
				if(!accPoid.equalsIgnoreCase("Null"))
				{
					canReturn = true;
					BRMRemarks = "Detail for BA Number " + baNum + ".";
					accPoid2Return = "ACCOUNT_OBJ varchar2(100) :='0.0.0.1 /account " + accPoid + " 0'";
					
					
					billInfo2Return = baCheck.getAccountBillInfo(accPoid);
					if(!billInfo2Return.equalsIgnoreCase("Null"))
					{
						billInfo2Return = "BAL_INFO_BILLINFO_OBJ varchar2(100) :='0.0.0.1 /billinfo " + billInfo2Return + " 0'";
					}
					else
					{
						billInfo2Return = "Cannot Find Bill Info Object";
					}
					
					profElem0_2Return = baCheck.getPROFILES_elem0(accPoid);//.getAccountBillInfo(accPoid);
					if(!profElem0_2Return.equalsIgnoreCase("Null"))
					{
						profElem0_2Return = "PROFILES_elem0_PROFILE_OBJ varchar2(100) :='0.0.0.1 /profile/tm_account " + profElem0_2Return + " 0'";
					}
					else
					{
						profElem0_2Return = "Cannot PROFILES_elem0_PROFILE_OBJ Object";
					}
					
					
					profElem1_2Return = baCheck.getPROFILES_elem1(accPoid);//.getAccountBillInfo(accPoid);
					if(!profElem1_2Return.equalsIgnoreCase("Null"))
					{
						profElem1_2Return = "PROFILES_elem1_PROFILE_OBJ varchar2(100) :='0.0.0.1 /profile/tm_invoice " + profElem1_2Return + " 0'";
					}
					else
					{
						profElem1_2Return = "Cannot PROFILES_elem1_PROFILE_OBJ Object";
					}
					
					
					payInfo = baCheck.getAccountPayInfo(accPoid);					
					if(payInfo.size() > 0)
					{
						
						String poid_type = "Null";
						String poid_id0 = "Null";
						
						for(int x=0;x<payInfo.size();x++)
						{
				    		System.out.println(x+1 + ":: " + x);
				    		Vector info = (Vector)payInfo.elementAt(x);
				    		poid_id0 = (String)info.elementAt(0);
				    		poid_type = (String)info.elementAt(1);	
						}
					
						payInfo2Return = "PAYINFO_POID varchar2(100) :='0.0.0.1 " + poid_type + " " + poid_id0 + " 0'";
					}
					else
					{
						payInfo2Return = "Cannot Find Pay Info Object";
					}
				}
				else
				{
					
					BRMRemarks = "BA Number " + baNum + " not exist in BRM.";
					System.out.println("BA Number " + baNum + " not exist in BRM.");
				}

				
			}
			else if(req.getParameter("accPoid") != null)
			{
				accPoid = req.getParameter("accPoid");
				
				System.out.println("BaNumber===." + baNum);
				
			


				
			}
			else
			{
				resultReturnToJSP = "Error - Parameter Sent is null ";
			}
			

			
			
			RequestDispatcher dispatcher = req.getRequestDispatcher(resp.encodeURL("/AccntObjDetail.jsp?payInfo=" + payInfo2Return + "&accPoid="
			+ accPoid2Return + "&billInfo=" + billInfo2Return + "&profElem0=" + profElem0_2Return + "&profElem1=" + profElem1_2Return ));
			
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
	
	
	private void CheckAccountPoid(Vector accPoidCheck)
	{
		
	}

}
