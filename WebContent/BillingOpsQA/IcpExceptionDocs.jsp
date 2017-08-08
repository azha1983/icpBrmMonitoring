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
					<h2>ICP-BRM Exception</h2>
					It consist of tackling all exception towards BRM and do recovery of
					affected account. This include ICP order,payment,bill run,wrong
					bill etc.
					<h3>Person in Charge</h3>
					<ul>
						<li>Huzaimy Shah Abas | huzaimy@tm.com.my (Manager)</li>
						<ul>
							<li>Azhar Bin Tamby | azhar.tamby@tm.com.my</li>
							<li>Zulkifli Bin Abdullah | zulkifli.a@tm.com.my</li>
						</ul>
					</ul>

					<h3>Documentation :</h3>
					<ul>
						<li><a
							href="http://10.23.23.55:8080/IcpBrmException/Docs/ICP-BRM DailyList_update09092016.docx">ICP-Daily
								Exception List Step</a></li>
						<li><a
							href="http://10.23.23.55:8080/IcpBrmException/Docs/ICP-BRM Workflow.docx">ICP-BRM
								Exception Workflow</a></li>
						<li><a
							href="http://10.23.23.55:8080/IcpBrmException/Docs/ICP_Camelot_Billing_Exception_SOP_v1.docx">ICP-BRM
								Exception KX - Accenture</a></li>
					</ul>

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