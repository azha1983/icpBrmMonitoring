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
<jsp:include page="cssMain.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ICP-Billing Operation Web</title>



</head>
<body>
	<div id="page">
		<header id="header"> <jsp:include page="headerFile.jsp" />
		</header>
		<div class="feature">
			<div class="feature-inner">
				<h1>
					<b>ICP-Billing Operation Web</b>
				</h1>
				To make life easier
			</div>
		</div>


		<div id="content">
			<div id="content-inner">

				<main id="contentbar">
				<div class="article">
					<p></p>
					<p id="dateC"></p>
					<p>
					<h2>Get EAI ID XML</h2>
					<%if(request.getParameter("payload") == null) 
{%>

					<font color="orange">Search by EAI ID request that stuck on BRM Exception</font>


					<form action="CheckEaiIdXMLServlet" method="POST">

						<table style="margin: 0px auto; width: 500px" align="left"
							border=0>
							<tr>
								<td colspan=4 align="left">Please enter EAI ID :
									&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="eaiID"
									size=20>&nbsp;&nbsp;&nbsp;&nbsp; <i><font size=0.5>example:
											EAI1139910610</font></i>
								</td>

							</tr>
							<tr>
								<td colspan="4" align="center" bgcolor="#f9ebdb"><input
									type="submit"></td>
							</tr>
						</table>

					</form>
					<br>


					<%}
else
{%>

					<font color="orange">Search by EAI ID request that stuck on BRM Exception</font>



					<form action="CheckEaiIdXMLServlet" method="POST">

						<table style="margin: 0px auto; width: 500px" align="left"
							border=0>
							<tr>
								<td colspan=4 align="left">Please enter EAI ID :
									&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="eaiID"
									size=20>&nbsp;&nbsp;&nbsp;&nbsp; <i><font size=0.5>example:
											EAI1139910610</font></i>
								</td>

							</tr>

							<tr>
								<td colspan="4" align="left"><b>EAI XML</b><br>
									<textarea id="outputText" name="xmlValue" rows=20
										cols=100 wrap=on style="color: white; background-color: black"><%=request.getParameter("payload")%></textarea> <br></td>
							</tr>
							<tr>
								<td colspan="4" align="center" bgcolor="#f9ebdb"><input
									type="submit"></td>
							</tr>

						</table>

					</form>


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