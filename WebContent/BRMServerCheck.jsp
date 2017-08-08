<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html;charset=ISO-8859-1"
	import="com.jcraft.jsch.JSch"%>

<!DOCTYPE html>
<html>
<head>

<script type="text/javascript"> 
var myVar=setInterval(function () {myTimer(0)}, null); 
//var counter = 0;
function myTimer() 
{
    var date = new Date();
    document.getElementById("demo").innerHTML = date.toString('yyyy-MM-dd');
}
</script>

<meta charset="utf-8">
<meta name="Description" content="Your Title">
<meta name="author" content="CoffeeCup Software, Inc.">
<meta name="Copyright"
	content="Copyright (c) 2010 CoffeeCup, all rights reserved.">
<title>ICP - BRM Exception</title>
<style type="text/css" media="screen">
html, body, div, header, footer, aside, nav, article, section {
	margin: 0;
	padding: 0;
}

header, footer, aside, nav, article, section {
	display: block;
}

body {
	color: #333;
	font: 12px Helvetica, Arial, sans-serif;
	line-height: 18px;
}

h2 {
	color: #333;
}

a {
	color: #337810;
}

p {
	margin: 0 0 18px;
}

#container {
	width: 760px;
	margin: 0 auto;
}

/* Header */
header {
	background: #333;
	border-bottom: 2px solid #aaa;
}

header h1 {
	color: #fff;
	margin: 0 0 3px;
	padding: 24px 18px 0;
}

header p {
	color: #ccc;
	font-size: 11px;
	font-weight: bold;
	padding: 0 18px;
}

/* Content Style */
nav {
	margin-left: 18px;
}

nav ul {
	padding: 0 18px 9px;
}

#extra {
	margin: 0 18px;
}

#extra small {
	font-size: 11px;
	line-height: 18px;
}

#content {
	border-bottom: 1px solid #ccc;
	margin: 0 18px;
}

#content p, #extra p {
	padding-right: 18px;
}

/* Content Positioning and Size */
nav {
	float: right;
	width: 350px;
}

#content {
	
}

#extra {
	float: left;
	width: 350px;
} /* Footer */
footer {
	background: #666;
	border-bottom: 2px solid #aaa;
	clear: both;
}

footer a {
	color: #fff;
}

footer	p {
	color: #ccc;
	margin: 0;
	padding: 0 18px 10px;
}

footer ul {
	border-bottom: 1px solid #999;
	list-style: none;
	margin: 0 18px 6px;
	padding: 10px 0 6px;
}

footer li {
	display: inline;
	font-size: 11px;
	font-weight: bold;
	padding-right: 5px;
}
</style>
<!--[if IE]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>
<body>
	<div id="container">
		<header>
			<h1>ICP-BRM (Server Status)</h1>
			<p class="description">To check BRM Online node status</p>
		</header>
		<div id="wrapper">

			<%if(request.getParameter("chooseNode") == null) 
{%>
			<section id="content">
				<h2>ICP-BRM Online Node</h2>


				<form action="BRMSvrStatusServlet" method="POST">

					<table style="margin: 0px auto; width: 700px">
						<tr>
							<td>Please select ICP-BRM Server Node</td>
							<td>:</td>
							<td><select name="chooseNode">
									<option value="None">Please select...</option>
									<option value="ICPNode1">ICP-BRM Node 1</option>
									<option value="ICPNode2">ICP-BRM Node 2</option>
									<option value="ICPNode3">ICP-BRM Node 3</option>
							</select></td>
							<td><i><font size=0.5>To check ICP-BRM Online
										node only</font></i></td>

						</tr>
						<!--  <tr>
    <td>Or Account Poid </td>
    <td>:</td>
    <td><font size=0.5>/account</font> <input type="text" name="accPoid" size=15></td>		
    <td><i><font size=0.5>example: 123456788</font></i></td>
  </tr>-->
						<tr>
							<td colspan="4" align="center" bgcolor="#333"><input
								type="submit" value="Check"></td>
						</tr>
					</table>

				</form>
				<br>
			</section>

			<%}
else
{%>
			<section id="content">
				<h2>ICP-BRM Online Node</h2>


				<form action="BRMSvrStatusServlet" method="POST">

					<table style="margin: 0px auto; width: 700px">
						<tr>
							<td>Please select ICP-BRM Server Node</td>
							<td>:</td>
							<td><select name="chooseNode">
									<option value="None">Please select...</option>
									<option value="ICPNode1">ICP-BRM Node 1</option>
									<option value="ICPNode2">ICP-BRM Node 2</option>
									<option value="ICPNode3">ICP-BRM Node 3</option>
							</select></td>
							<td><i><font size=0.5>To check ICP-BRM Online
										node only</font></i></td>

						</tr>
						<!-- <tr>
    <td>Or Account Poid </td> 
    <td>:</td>
    <td><font size=0.5>/account</font> <input type="text" name="accPoid" size=15></td>		
    <td><i><font size=0.5>example: 123456788</font></i></td> 
  </tr> -->
						<tr>
							<td colspan="4" align="center"><br>
							<b>ICP BRM | <%=request.getParameter("nodeNo")%> | <%=request.getParameter("ip")%></b><br>
								<b><span id="demo"></span></b><br> <textarea
									id="outputText" rows=25 cols=70 readonly
									style="color: white; background-color: black"><%=request.getParameter("resultNode")%>
</textarea> <br></td>
						</tr>
						<tr>
							<td colspan="4" align="center" bgcolor="#333"><input
								type="submit" value="Check"></td>
						</tr>

					</table>

				</form>

				<br>
			</section>


			<%}%>



		</div>
		<br>



		<nav>
			<h2>LINKS</h2>
			<ul>
				<li><a href="http://iris.tm.com.my/">IRIS</a></li>
				<li><a href="https://tmbill.tm.com.my/CustomerCenter/">BRM
						Customer Center</a></li>
				<li><a
					href="http://10.41.86.34:7281/iCareEAI_Web_Support_Tool_war/index.jsp">iCarePrime
						Web Support Tools</a></li>
				<li><a href="http://10.23.23.55:8080/IcpBrmException">BACK
						| ICP-BRM Exception</a></li>
				<!--  <li><a href="">Navigation 4</a></li>
				<li><a href="">Navigation 5</a></li>
				<li><a href="">Navigation 6</a></li>-->
			</ul>
		</nav>
		<section id="extra">
			<h2>FAQ</h2>
			<p>* Please select online node to check current status.
			<p>* Only 3 online node available for checking.
			<p>
				<!--  <p>Sometimes this would be called a <em>sidebar</em>, but it doesn't always have to be on the side to be called a <em>sidebar</em>. Sidebars can be on tops of things, below things, but they are usually beside things &ndash; hence it being a called a sidebar.</p>
			<p><small>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</small></p>
			-->
		</section>
		<footer>
			<ul>
				<li><a href="http://iris.tm.com.my/">IRIS</a></li>
				<li><a href="https://tmbill.tm.com.my/CustomerCenter/">BRM
						Customer Center</a></li>
				<li><a
					href="http://10.41.86.34:7281/iCareEAI_Web_Support_Tool_war/index.jsp">iCarePrime
						Web Support Tools</a></li>
				<!--  <li><a href="">Navigation 4</a></li>
				<li><a href="">Navigation 5</a></li>
				<li><a href="">Navigation 6</a></li>-->
			</ul>
			<p>ICP-BRM Exception by Azha-ITOC</p>
		</footer>
	</div>
</body>
</html>
