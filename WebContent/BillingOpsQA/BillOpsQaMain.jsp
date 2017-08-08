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
					<h2>QA Framework For Billing Operation</h2>

					Quality assurance (QA) is a way of preventing mistakes or defects
					in manufactured products and avoiding problems when delivering
					solutions or services to customers; which ISO 9000 defines as "part
					of quality management focused on providing confidence that quality
					requirements will be fulfilled". <br>
					<br> Framework for each processes:-<br>
					<br>
					<li><a href="#">Process Diagram /Flow Chart</a></li>
					<li><a href="#">Roles & Responsibilities</a></li>
					<li><a href="#">IStandard Operating Procedure (SOP)</a></li>
					<li><a href="#">Interface Agreement (IFA)</a></li>
					<li><a href="#">Runbooks- Detail procedure & work
							instruction</a></li>
					</ul>

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