<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


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
<style type="text/css">
html, #page {
	padding: 0;
	margin: 0;
}

body {
	margin: 0;
	padding: 0;
	width: 100%;
	color: #959595;
	font: normal 12px/2.0em Sans-Serif;
}

h1, h2, h3, h4, h5, h6 {
	color: darkorange;
}

#page {
	background: #fff;
}

#header, #footer, #top-nav, #content, #content #contentbar, #content #sidebar
	{
	margin: 0;
	padding: 0;
}

/* Logo */
#logo {
	padding: 0;
	width: auto;
	float: left;
}

#logo h1 a, h1 a:hover {
	color: darkorange;
	text-decoration: none;
}

#logo h1 span {
	color: #f9ebdb;
}

/* Header */
#header {
	background: #fff;
}

#header-inner {
	margin: 0 auto;
	padding: 0;
	width: 970px;
}

/* Feature */
.feature {
	background: orange;
	padding: 10px;
}

.feature-inner {
	margin: auto;
	width: 970px;
}

.feature-inner h1 {
	color: #f9ebdb;
	font-size: 32px;
}

/* Menu */
#top-nav {
	margin: 0 auto;
	padding: 0px 0 0;
	height: 37px;
	float: right;
}

#top-nav ul {
	list-style: none;
	padding: 0;
	height: 37px;
	float: left;
}

#top-nav ul li {
	margin: 0;
	padding: 0 0 0 8px;
	float: left;
}

#top-nav ul li a {
	display: block;
	margin: 0;
	padding: 12px 10px;
	color: orange;
	text-decoration: none;
}

#top-nav ul li.active a, #top-nav ul li a:hover {
	color: #f9ebdb;
}

/* Content */
#content-inner {
	margin: 0 auto;
	padding: 10px 0;
	width: 970px;
	background: #fff;
}

#content #contentbar {
	margin: 0;
	padding: 0;
	float: right;
	width: 760px;
}

#content #contentbar .article {
	margin: 0 0 24px;
	padding: 0 20px 0 15px;
}

#content #sidebar {
	padding: 0;
	float: left;
	width: 200px;
}

#content #sidebar .widget {
	margin: 0 0 12px;
	padding: 8px 8px 8px 13px;
	line-height: 1.4em;
}

#content #sidebar .widget h3 a {
	text-decoration: none;
}

#content #sidebar .widget ul {
	margin: 0;
	padding: 0;
	list-style: none;
	color: #959595;
}

#content #sidebar .widget ul li {
	margin: 0;
}

#content #sidebar .widget ul li {
	padding: 4px 0;
	width: 185px;
}

#content #sidebar .widget ul li a {
	color: orange;
	text-decoration: none;
	margin-left: -16px;
	padding: 4px 8px 4px 16px;
}

#content #sidebar .widget ul li a:hover {
	color: #f9ebdb;
	font-weight: bold;
	text-decoration: none;
}

/* Footerblurb */
#footerblurb {
	background: #f9ebdb;
	color: orange;
}

#footerblurb-inner {
	margin: 0 auto;
	width: 922px;
	padding: 10px;
}

#footerblurb .column {
	margin: 0;
	text-align: justify;
	float: left;
	width: 250px;
	padding: 0 24px;
}

/* Footer */
#footer {
	background: #fff;
}

#footer-inner {
	margin: auto;
	text-align: center;
	padding: 12px;
	width: 922px;
}

#footer a {
	color: orange;
	text-decoration: none;
}

/* Clear both sides to assist with div alignment  */
.clr {
	clear: both;
	padding: 0;
	margin: 0;
	width: 100%;
	font-size: 0px;
	line-height: 0px;
}
</style>



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
					<h2>About developer</h2>

					Idea, Concept and Develop by Azhar bin Tamby





					</p>
				</div>
				</main>

				<nav id="sidebar">
				<div class="widget">
					<h3>Links | ICP-BRM</h3>
					<ul>
						<li><a
							href="http://10.23.23.55:8080/IcpBrmException/AccntObjDetail.jsp">Account
								Object Detail Check</a></li>
						<li><a
							href="http://10.23.23.55:8080/IcpBrmException/XmlToFlist.jsp">XML
								to FList Converter</a></li>
						<li><a href="#">Server Status Check</a></li>
						<li><a href="#">Siebel Order Aging Table</a></li>
						<li><a href="http://10.23.23.55:8080/IcpBrmException">Back
								to main</a></li>
					</ul>
				</div>
				<div class="widget">
					<h3>Links | ICP-Billing</h3>
					<ul>
						<li><a
							href="http://10.23.23.55:8080/IcpBrmException/CheckInvBal.jsp">Bill
								Monitoring - Left to invoice</a></li>
						<li><a
							href="http://10.23.23.55:8080/IcpBrmException/CheckBillBal.jsp">Bill
								Monitoring - Left to bill</a></li>
					</ul>
				</div>
				<div class="widget">
					<h3>Links | Others</h3>
					<ul>
						<li><a href="http://iris.tm.com.my/sm/index.do">IRIS</a></li>
						<li><a href="https://tmbill.tm.com.my/CustomerCenter/">BRM
								Customer Center</a></li>
						<li><a
							href="http://10.41.86.34:7281/iCareEAI_Web_Support_Tool_war/index.jsp">iCarePrime
								Web Support Tools</a></li>
					</ul>
				</div>
				</nav>

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