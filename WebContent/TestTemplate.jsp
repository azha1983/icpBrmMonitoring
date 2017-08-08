<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>


<!DOCTYPE html>
<html>
<head>




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
			<h1>ICP-BRM (Account Object Checking)</h1>
			<p class="description">To check account object detail mapping
				(Between EAI-BRM)</p>
		</header>
		<div id="wrapper">

			<%if(request.getParameter("BaNumber") == null) 
{%>
			<section id="content">
				<h2>Search by BA Number</h2>


				<form action="AccountObjectServlet" method="POST">

					<table style="margin: 0px auto; width: 700px">
						<tr>
							<td>Please enter BA Number</td>
							<td>:</td>
							<td><input type="text" name="BaNumber" size=21></td>
							<td><i><font size=0.5>example: A6000123456788</font></i></td>

						</tr>
						<!--  <tr>
    <td>Or Account Poid </td>
    <td>:</td>
    <td><font size=0.5>/account</font> <input type="text" name="accPoid" size=15></td>		
    <td><i><font size=0.5>example: 123456788</font></i></td>
  </tr>-->
						<tr>
							<td colspan="4" align="center" bgcolor="#333"><input
								type="submit"></td>
						</tr>
					</table>

				</form>
				<br>
			</section>

			<%}
else
{%>
			<section id="content">
				<h2>Search by BA Number</h2>


				<form action="AccountObjectServlet" method="POST">

					<table style="margin: 0px auto; width: 700px">
						<tr>
							<td>Please enter BA Number</td>
							<td>:</td>
							<td><input type="text" name="BaNumber" size=21></td>
							<td><i><font size=0.5>example: A6000123456788</font></i></td>

						</tr>
						<!-- <tr>
    <td>Or Account Poid </td> 
    <td>:</td>
    <td><font size=0.5>/account</font> <input type="text" name="accPoid" size=15></td>		
    <td><i><font size=0.5>example: 123456788</font></i></td> 
  </tr> -->
						<tr>
							<td colspan="4" align="center"><br>
							<b>Account Object Detail</b><br> <textarea id="outputText"
									rows=10 cols=90 wrap=on readonly><%=request.getParameter("accPoid")%>
<%=request.getParameter("billInfo")%> 
<%=request.getParameter("payInfo")%>
<%=request.getParameter("profElem0")%>
<%=request.getParameter("profElem1")%>
</textarea> <br></td>
						</tr>
						<tr>
							<td colspan="4" align="center" bgcolor="#333"><input
								type="submit"></td>
						</tr>

					</table>

				</form>

				<br>
			</section>
			<!--  
<section id="content">
			<h2>Detail for BA below</h2>
			<br>

<table border = 1 style="margin:0px auto; width:500px"> 
  <tr>
    <td width="500">Siebel Order ID*</td>
    <td><input type="text" name="siebelOrderID" value=""></td>		

  </tr>
  <tr>
    <td>OSM ID</td>
    <td><input type="text" name="osmID" value=""></td>	

  </tr>
  
    <tr>
    <td>Exception Owner</td> 
    <td><input type="text" name="resultSystem" readonly value=""></td>		 

   </tr>
    <tr>    
    <td  colspan="2" align="center">
    <br>
    BRM Remarks<br><br>
    <textarea id="outputText" rows=5 cols=60 wrap=on readonly><%//=request.getParameter("resultBRM")%></textarea>
    <br>
    </td>		
  </tr>
  <tr>
    <td colspan="2" align="center" bgcolor="#333"><input type="submit"></td>		
  </tr>
</table>

<br>
</section>
		-->

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
			<p>* Please fill BA number in the textbox then click submit
				button.
			<p>* L2 to give the output detail to EAI team (to be patch).
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
