<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
    
    function goBack() {
        window.history.back();
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
					<h2>Standard Operating Procedure (SOP) Document</h2>

					Standard Operating Procedure (SOP)

Standard Operating Procedure (SOP) is a document consisting of step-by-step instructions on how Billing and Invoicing is carry out.
The purpose of SOP is to ensure consistent and quality output.


Below are the Standard Operation Procedure documents for Billing and Invoicing. <br><br>

ICP Billing_BRM_Billing_BilllRun <br>
ICP Billing Monitoring and Troubleshoot<br>
ICP Post Billing Extraction<br>
ICP Billing BIVE Run<br>
ICP Billing BIVE Monitoring and Troubleshoot<br>
ICP Billing CMC Checker and Fixer<br>
ICP Invoicing Manual Run<br>
ICP Invoicing Monitor and Troubleshoot<br>
ICP Invoice Regen and Export<br>
ICP Billing Large Account XML Export (XML Spooler)	<br>
ICP Billing PDF Generation<br>
ICP Billing PDF Generation Monitoring and Troubleshoot<br>
ICP Billing Post Extraction<br>
ICP Billing Delay Billing Setting Manual<br>
ICP Billing manually send to BPM<br>
ICP Billing Split XML manually<br>
ICP Billing verify XML & output<br>
ICP Billing Bill Readiness in MyTM Portal SMS Notification<br>
ICP Billing Send to Archival<br>

					<p align="center">
						<button onclick="goBack()">Go Back</button>
					</p>


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