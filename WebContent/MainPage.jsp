<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="brm.CheckOSMTask"%>

<% 

//-- Aging by OSM trigger date

int threeDays = 3 *24*60*60;        	
int tenDays = 10 *24*60*60;
long todays = System.currentTimeMillis() / 1000L;

int todaysDate = (int) todays;

String agingIn = "Null";
String agingOut = "Null";

int OSMtotalIn3Days = 0;
int OSMtotalIn3DaysLess10Days = 0;
int OSMtotalMore10Days = 0;


CheckOSMTask c = new CheckOSMTask();



Vector agingStat = c.getOSMTaskStuckAtBRM_byTime();

for(int q=0;q<agingStat.size();q++)
{
	
	System.out.println("==========================================");
	System.out.println(q+1 + "--------------" + agingStat.elementAt(q));
	
	agingIn = agingStat.elementAt(q).toString();
	System.out.println("!!AgingIn-->" + agingIn);
	agingIn = agingIn.replace("[","");
	agingIn = agingIn.replace("]","");
	System.out.println("!!AgingIn-->" + agingIn);
	
	int dateReturn = c.dateConverter(agingIn);
	
	int todayMinusDateList = todaysDate - dateReturn;
	
	if(todayMinusDateList < threeDays)
	{
		System.out.println("--> Date is within 3 days");
		OSMtotalIn3Days++;
	}
	else if(todayMinusDateList > tenDays)
	{
		System.out.println("--> More than 10 days");
		OSMtotalMore10Days++;
		
	}
	else if((todayMinusDateList < tenDays) && (todayMinusDateList > threeDays))
	{
		System.out.println("--> Between 3 to 10 days");
		OSMtotalIn3DaysLess10Days++;
	}
	else
	{
		System.out.println("-->  Not relevan");
	}
}


int totalOSM = OSMtotalMore10Days+OSMtotalIn3DaysLess10Days+OSMtotalIn3Days;
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
<jsp:include page="cssMain.jsp" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ICP-Billing Operation Web</title>



</head>
<body>
	<div id="page">
		<header id="header"> <jsp:include page="headerFile.jsp" /> </header>
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
				
				<br>
				<div align="center">
					<img
							src="http://localhost:8080/IcpBrmException/images/orange-button1.png"
							alt="ProcessDiagramDetail" style="width:200px;height:180px;" />
							
							<img
							src="http://localhost:8080/IcpBrmException/images/orange-button1.png"
							alt="ProcessDiagramDetail" style="width:200px;height:180px;" />
							
							<img
							src="http://localhost:8080/IcpBrmException/images/orange-button1.png"
							alt="ProcessDiagramDetail" style="width:200px;height:180px;" />
							</div>
					<p id="dateC"></p>
					<p>
					<h2>
						Total ICP-BRM Exception on <font size="2"><script>document.writeln(today);</script></font>
						<br />
						<font size="1">Based on OSM Last Trigger Date</font>
					</h2>

					<form action="CheckTotalExcept" method="POST">
						<table border=0">
							<tr>
								<td width="500" align="center">< 3 days</td>
								<td width="500" align="center">3 to 10 days</td>
								<td width="500" align="center">> 10 days</td>
							</tr>
							<tr>
								<td width="500" align="center"><font size="4"><b><%=OSMtotalIn3Days%></b></font></td>
								<td width="500" align="center"><font size="4"><b><%=OSMtotalIn3DaysLess10Days%></b></font></td>
								<td width="500" align="center"><font size="4"><b><%=OSMtotalMore10Days%></b></font></td>
							</tr>
							<tr>
								<td colspan="3" align="center">Total</td>
							</tr>
							<tr>
								<td colspan="3" align="center"><font size="4"><b><%=totalOSM%></b></font></td>
							</tr>
							<tr>
								<td colspan="3" align="center" bgcolor="#f9ebdb"><button
										type="button" onClick="refreshPage()">Refresh</button> <!--  <a href="javascript:;" onclick="resetTimeout();">Stop auto refresh</a></td>-->
							</tr>
						</table>

					</form>

					</p>
				</div>
				</main>

				<nav id="sidebar"> <jsp:include page="sideBarMain.jsp" /> </nav>

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