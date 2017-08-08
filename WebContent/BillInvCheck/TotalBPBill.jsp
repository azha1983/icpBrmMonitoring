<%@ page import="java.util.*"%>



<!DOCTYPE html>
<html>
<head>

<% 
System.out.println("-------------------------OK MASUK------------------------");
%>
<script type="text/javascript">
    var today= new Date();
    function refreshPage() {
        location.reload();
    }
    </script>
<jsp:include page="../cssMain.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ICP-Billing Operation Web</title>



</head>
<body>
	<div id="page">
		<header id="header"> <jsp:include page="../headerFile.jsp" />
		</header>
		<div class="feature">
			<div class="feature-inner">
				<h1>
					<b>ICP-Billing Operation Web</b>
				</h1>
				To made life easier
			</div>
		</div>


		<div id="content">
			<div id="content-inner">

				<main id="contentbar">
				<div class="article">
					<p></p>
					<p id="dateC"></p>
					<p>
					<h2>Total Account to be Bill - Each BP</h2>
<% if(request.getParameter("bpSent") == null)
	{%>
<form action=" ../CheckTotalBillBPServlet" method="POST">
					Please enter date :
									&nbsp;&nbsp;&nbsp;&nbsp; <select name="month">
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
								</select> <input type="text" name="year1" size=6 maxlength="4" value="2017" disabled> <i><font
										size=0.5></font></i>
										<input type="hidden" name="year" value="2017">
										<input
									type="submit">
</form>
					<br>
					<br>
					
<%
	}else{
		
		//Vector vc = new Vector();
		System.out.println("------------------------JSP Vec Size-------------------------");
		Vector  vc = (Vector)session.getAttribute("objetBP");
		System.out.println("------------------------JSP Vec Size-------------------------" + vc.size());
		//vc = session.getAttribute("objetBP");
%>
<form action=" ./CheckTotalBillBPServlet" method="POST">
					Please enter date :
									&nbsp;&nbsp;&nbsp;&nbsp; <select name="month">
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
								</select> <input type="text" name="year1" size=6 maxlength="4" value="2017" disabled> <i><font
										size=0.5></font></i>
										<input type="hidden" name="year" value="2017">
										<input
									type="submit">
</form>

					<br>
					 <table width=100% border=1>
					  <tr align=center bgcolor="#f9ebdb">
					    <th>BP</th>
					    <th>Total Bill - <%=request.getParameter("monthcheck") %></th>
					  </tr>
					  <%
					  int y = 1;
					  String str = "Null";
					  for(int x=0;x<vc.size();x++)
					  {
					  str = vc.elementAt(x).toString().replace("[", "");
					  str = str.replace("]", "");
					  %>
					  <tr align=center>
					    <td><%=y%></td>
					    <td><%=str%></td>
					  </tr>
					  <%
					  y+=3;} 
					  %>
					  </table>
					  
				
<%} %>
					
				</div>
				</main>

				<nav id="sidebar"> <jsp:include page="../sideBar.jsp" /> </nav>

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