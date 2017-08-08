<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="brm.CheckSBLAgingDate"%>


<% 
//Aging by SIEBEL Submit Date

int threeDays = 3 *24*60*60;        	
int tenDays = 10 *24*60*60;
long todays = System.currentTimeMillis() / 1000L;

int todaysDate = (int) todays;

CheckSBLAgingDate cs = new CheckSBLAgingDate();

Vector orderIdVec = cs.getSBLorderIdFromOSM();

String orderID = "Null";
String dateSBL = "Null";

int SBLtotalIn3Days = 0;
int SBLtotalIn3DaysLess10Days = 0;
int SBLtotalMore10Days = 0;
  	
for(int b=0;b<orderIdVec.size();b++)
{
	System.out.println("==========================================");
	//System.out.println(b+1 + ")" + orderIdVec.elementAt(b));
	orderID = orderIdVec.elementAt(b).toString();
	orderID = orderID.replace("[","");
	orderID = orderID.replace("]","");
	System.out.println(b+1 + ")" + orderID); 
	System.out.println("==========================================");
	
	dateSBL = cs.getDateSubmitted(orderID);
	
	int dateReturn = cs.dateConverter(dateSBL);

	int todayMinusDateList = todaysDate - dateReturn;

	
	if(todayMinusDateList < threeDays)
	{
		//System.out.println("--> Date is within 3 days");
		SBLtotalIn3Days++;
	}
	else if(todayMinusDateList > tenDays)
	{
		//System.out.println("--> More than 10 days");
		SBLtotalMore10Days++;
		
	}
	else if((todayMinusDateList < tenDays) && (todayMinusDateList > threeDays))
	{
		//System.out.println("--> Between 3 to 10 days");
		SBLtotalIn3DaysLess10Days++;
	}
	else
	{
		System.out.println("-->  Not relevan");
	}
	
}
%>
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript"> 
var myVar=setInterval(function () {myTimer()}, 1000); 
var counter = 0;
function myTimer() 
{
    var date = new Date();
    document.getElementById("demo").innerHTML = date.toString('yyyy-MM-dd');
}


function refreshPage(){
    window.location.reload();
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
			<h1>ICP-BRM (Aging Order Table)</h1>
			<p class="description">Order stuck at SIEBEL due to BRM Exception</p>
		</header>
		<div id="wrapper">
			<h3>
				Total ICP-BRM Exception on <span id="demo"></span><br />
				<font size="1">Based on SIEBEL submit date</font>
			</h3>



			<form action="CheckTotalExcept" method="POST">
				<table border=0 style="margin: 0px auto; width: 500px">
					<tr>
						<td width="500" align="center">< 3 days</td>
						<td width="500" align="center">3 to 10 days</td>
						<td width="500" align="center">> 10 days</td>
					</tr>
					<tr>
						<td width="500" align="center"><font size="4"><b><%=SBLtotalIn3Days%></b></font></td>
						<td width="500" align="center"><font size="4"><b><%=SBLtotalIn3DaysLess10Days%></b></font></td>
						<td width="500" align="center"><font size="4"><b><%=SBLtotalMore10Days%></b></font></td>
					</tr>
					<tr>
						<td colspan="3" align="center">Total</td>
					</tr>
					<tr>
						<td colspan="3" align="center"><font size="4"><b><%=SBLtotalMore10Days+SBLtotalIn3DaysLess10Days+SBLtotalIn3Days%></b></font></td>
					</tr>
					<tr>
						<td colspan="3" align="center" bgcolor="#333"><button
								type="button" onClick="refreshPage()">Refresh</button></td>
					</tr>
				</table>

			</form>
			<br>

			</section>
		</div>


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
				<!--
				<li><a href="">Navigation 5</a></li>
				<li><a href="">Navigation 6</a></li>-->
			</ul>
		</nav>
		<section id="extra">
			<h2>FAQ</h2>
			<p>* Due to slow performance, this aging order table cannot be
				put into main page.
			<p>* The aging is based on SIEBEL submit date.
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
