<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="BillInvRun.BillRunProgStatus"%>

<%

BillRunProgStatus brun = new BillRunProgStatus();
brun.checkProgramStatus("billRun");

String typeRec = "Err";

if(request.getParameter("type").equalsIgnoreCase("inv"))
	typeRec = "Invoice Run";
	else
		typeRec = "Bill Run";	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
    var today= new Date();
    function refreshPage() {
        location.reload();
    }
    </script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ICP-Billing Operation Web</title>
<jsp:include page="../cssMain.jsp" />

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
					<h2><%=typeRec%> - Program Status</h2>

					To check program either the program is running or not. 
					<br><br>
					<table border=1 style="margin: 0px auto; width: 500px">
							<tr>
								<td width="500" align="center" bgcolor="#f9ebdb"><b><font color="orange">Below is current <%=typeRec%> status</font></b></td>


							</tr>
							<tr>
								<td><textarea id="outputText" name="xmlValue" rows=13 cols=100 wrap=on style="color: white; background-color: black">
								<%=brun.getReturnValue()%></textarea></td>


							</tr>
							<tr>
							<td colspan="3" align="center" bgcolor="#f9ebdb">
							<button type="button" onClick="refreshPage()">Re-Check</button>
							</td>
							</tr>
						</table>

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