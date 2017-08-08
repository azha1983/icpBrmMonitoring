<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="brm.CheckInvBillOutput"%>

<%

FileInputStream fin=new FileInputStream("C:/WorkSpace/FileInput/ICP-Billing/programStatus.txt");
BufferedReader br=new BufferedReader(new InputStreamReader(fin));

//checkProgramstatus
String strLine = "Null";
String progStat = "Null";

while ((strLine = br.readLine()) != null)  
	{
	System.out.println("Program status : " + strLine);
	progStat = strLine;	
	break;
	}


CheckInvBillOutput ci = new CheckInvBillOutput();
Vector invOutput = new Vector();

invOutput = ci.getInvoiceOutput();

%>


<script type="text/JavaScript">
<%if(progStat.indexOf("Run") > -1){%>
function timedRefresh(timeoutPeriod) {
	setTimeout("location.reload(true);",timeoutPeriod);
}

window.onload = timedRefresh(60*1000);
<%}%>

</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ICP-BRM Exception Web</title>

<jsp:include page="cssMain.jsp" />


</head>
<body>
	<div id="page">
		<header id="header"> <jsp:include page="headerFile.jsp" /> </header>
		<div class="feature">
			<div class="feature-inner">
				<h1>
					<b>ICP-BRM Exception Web</b>
				</h1>
				To identify exception owner before log an IRIS
			</div>
		</div>


		<div id="content">
			<div id="content-inner">

				<main id="contentbar">
				<div class="article">
					<p>
					<h2>Invoice Run Check</h2>

					<%if(progStat.indexOf("Run") > -1)
{


%>

					<font color="orange">Program is running - Please do nothing
						(Screen will auto refresh for every 1 minute)</font>
 
					<table style="margin: 0px auto; width: 500px" align="left" border=0>
						<tr>

						</tr>
						<tr>
							<td colspan="4" align="left"><b>Left to invoice<%//if(request.getParameter("bal1") != null) {%<%=//request.getParameter("bal1") %>
<%//} %></b><br>
								<textarea id="outputText" rows=20 cols=75 wrap=on readonly
									style="color: white; background-color: black">
									<%for (Iterator it = invOutput.iterator(); it.hasNext();){out.println(it.next());}%>
	</textarea> <br></td>
						</tr>
						<tr>

						</tr>

					</table>



					<%}
else
{

%>

					<font color="orange">Checking by BP</font>



					<form action="CheckInvBalanceServlet" method="POST">

						<table style="margin: 0px auto; width: 500px" align="left"
							border=0>
							<tr>
								<td colspan=4 align="left">Please enter date :
									&nbsp;&nbsp;&nbsp;&nbsp; <select name="bp">
										<option value="1">1</option>
										<option value="4">4</option>
										<option value="7">7</option>
										<option value="10">10</option>
										<option value="13">13</option>
										<option value="16">16</option>
										<option value="19">19</option>
										<option value="22">22</option>
										<option value="25">25</option>
										<option value="28">28</option>
								</select> <select name="month">
										<option value="Jan">Jan</option>
										<option value="Feb">Feb</option>
										<option value="Mar">Mar</option>
										<option value="Apr">Apr</option>
										<option value="May">May</option>
										<option value="Jun">Jun</option>
										<option value="Jul">Jul</option>
										<option value="Aug">Aug</option>
										<option value="Sep">Sep</option>
										<option value="Oct">Oct</option>
										<option value="Nov">Nov</option>
										<option value="Dec">Dec</option>
								</select> 
								<input type="text" name="year1" size=6 maxlength="4" value="2017" disabled> <i><font
										size=0.5></font></i>
										<input type="hidden" name="year" value="2017">
								</td>

							</tr>
							<tr>
								<td colspan="4" align="left"><br> <textarea
										id="outputText" rows=20 cols=75 wrap=on readonly
										style="color: white; background-color: black">
										<%for (Iterator it = invOutput.iterator(); it.hasNext();){out.println(it.next());}%>
</textarea> <br></td>
							</tr>
							<tr>
								<td colspan="4" align="center" bgcolor="#f9ebdb"><input
									type="submit"></td>
							</tr>

						</table>

					</form>

					<br>



					<%}%>


				</div>
				</main>

				<nav id="sidebar"> <jsp:include page="sideBar.jsp" /> </nav>

				<div class="clr"></div>
			</div>
		</div>

		<div id="footerblurb">
			<div id="footerblurb-inner">

				<div class="column">
					<h2>
						<span></span>
					</h2>
					<p></p>
				</div>
				<div class="column">
					<h2 align="center">
						<span></span>
					</h2>
					<p align="center">Version 2.0 by azha-ITOC</p>
				</div>
				<div class="column">
					<h2>
						<span></span>
					</h2>
					<p></p>
				</div>

				<div class="clr"></div>
			</div>
		</div>
		<footer id="footer">
		<div id="footer-inner">
			<p>
				&copy; Copyright <a href="#">TM</a> &#124; <a href="#">Terms of
					Use</a> &#124; <a href="#">Privacy Policy</a>
			</p>
			<div class="clr"></div>
		</div>
		</footer>
	</div>
</body>
</html>