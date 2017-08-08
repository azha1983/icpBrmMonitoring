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
					<h2>Process Diagram/Flow Chart</h2>

					Process flow chart provides a visual representation of the steps in a process.
Process flow chart is important to develop understanding of how
Billing and Invoicing process is done.
Process flow chart can be used as a way to communicate to others on how
Billing and Invoicingprocess is carry out.It helps to study a process 
improvement by identify non value added operations

					<h1>
						<span><img
							src="http://10.23.23.55:8080/IcpBrmException/images/billInvProcessFLow.png"
							alt="ProcessDiagramDetail" style="width:800px;height:400px;" />
							<br><font size=2>Figure 1 : Billing and Invoicing process flow</font></span>
					</h1>
<br>
					<h1>
						<span><img
							src="http://10.23.23.55:8080/IcpBrmException/images/processflow2.png"
							alt="ProcessDiagramDetail" style="width:800px;height:400px;" />
							<br><font size=2>Figure 2:  Billing and Invoicing job by BP</font></span>
					</h1>
					<br>
										<h1>
						<span><img
							src="http://10.23.23.55:8080/IcpBrmException/images/processflow3.png"
							alt="ProcessDiagramDetail" style="width:450px;height:160px;" />
							<br><font size=2>Figure 3:  Billing and Invoicing job by daily</font></span>
					</h1>


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