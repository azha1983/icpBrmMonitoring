<%@ page import="java.util.*"%>


<!DOCTYPE html>
<html>
<head>

<% 
String imag = "non";
System.out.println("-------------------------OK MASUK------------------------" + request.getParameter("month"));
%>
<script type="text/javascript">
    var today= new Date();
    function refreshPage() {
        location.reload();
    }
    </script>
 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
    google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      var data = google.visualization.arrayToDataTable([
['Bill Period', 'Hard Copy', 'Soft Copy', 'Others'],
<%
if(request.getParameter("month").equalsIgnoreCase("jan"))
{
	imag = "January 2017";
%>
['BP 1',257669,44679,360697],
['BP 4',232522,47201,305942],
['BP 7',225488,46973,306704],
['BP 10',218643,46288,290079],
['BP 13',216477,45327,293364],
['BP 16',214658,45507,284235],
['BP 19',231570,47940,315409],
['BP 22',239791,48851,321350],
['BP 25',228158,48197,310955],
['BP 28',235734,47639,332944],
<%}else if(request.getParameter("month").equalsIgnoreCase("feb"))
{
	imag = "February 2017";

%>
['BP 1',255989,45689,362695],
['BP 4',229259,48092,310359],
['BP 7',220061,47357,313098],
['BP 10',215140,47249,294643],
['BP 13',213879,46078,296322],
['BP 16',212396,46335,288079],
['BP 19',228055,48533,319938],
['BP 22',236985,49583,325017],
['BP 25',226054,49034,314257],
['BP 28',232906,48128,336576],
<%}else{
	imag = "No data selected";
	%>

['BP 1', 0, 0, 0],
['BP 4', 0, 0, 0],
['BP 7', 0, 0, 0],
['BP 10', 0, 0, 0],
['BP 13', 0, 0, 0],
['BP 16', 0, 0, 0],
['BP 19', 0, 0, 0],
['BP 22', 0, 0, 0],
['BP 25', 0, 0, 0],
['BP 28', 0, 0, 0],

<%}%>
]);

      var options = {
        chart: {
          title: '',
          subtitle: '',
        },
        bars: 'vertical',
        vAxis: {format: 'decimal'},
        height: 400,
        colors: ['#1b9e77', '#d95f02', '#7570b3']
      };

      var chart = new google.charts.Bar(document.getElementById('chart_div'));

      chart.draw(data, google.charts.Bar.convertOptions(options));

      var btns = document.getElementById('btn-group');

      btns.onclick = function (e) {

        if (e.target.tagName === 'BUTTON') {
          options.vAxis.format = e.target.id === 'none' ? '' : e.target.id;
          chart.draw(data, google.charts.Bar.convertOptions(options));
        }
      }
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
					<h2>ICP - Total Billing</h2>
<%
if(request.getParameter("month").equalsIgnoreCase("non")) 
{
%>
<form action=" ./BillInvGraph.jsp" method="POST">
					Please enter date :
									&nbsp;&nbsp;&nbsp;&nbsp; <select name="month">
										<option value="Jan">Jan</option>
										<option value="Feb">Feb</option>
										<option value="Mar">Mar</option>
										<option value="Apr">Apr</option>
										<option value="May">May</option>
										<option value="Jun">Jun</option>
										<option value="Jul">Jul</option>
										<option value="Aug">Aug</option>
										<option value="Sep">Sep</option>
										<option value="Oct">Oct</option>
										<option value="Nov">Nov</option>
										<option value="Dec">Dec</option>
								</select> <input type="text" name="year1" size=6 maxlength="4" value="2017" disabled> <i><font
										size=0.5></font></i>
										<input type="hidden" name="year" value="2017">
										<input
									type="submit">
</form>
<%
}
else
{
%>
<form action=" ./BillInvGraph.jsp" method="POST">
					Please enter date :
									&nbsp;&nbsp;&nbsp;&nbsp; <select name="month">
										<option value="Jan">Jan</option>
										<option value="Feb">Feb</option>
										<option value="Mar">Mar</option>
										<option value="Apr">Apr</option>
										<option value="May">May</option>
										<option value="Jun">Jun</option>
										<option value="Jul">Jul</option>
										<option value="Aug">Aug</option>
										<option value="Sep">Sep</option>
										<option value="Oct">Oct</option>
										<option value="Nov">Nov</option>
										<option value="Dec">Dec</option>
								</select> <input type="text" name="year1" size=6 maxlength="4" value="2017" disabled> <i><font
										size=0.5></font></i>
										<input type="hidden" name="year" value="2017">
										<input
									type="submit">
</form>
<br>
<div align="center"><b>Invoice report - <%=imag%></b></div>
 <div id="chart_div"></div>
    <br/>
    <div id="btn-group">
      <button class="button button-blue" id="none">No Format</button>
      <button class="button button-blue" id="scientific">Scientific Notation</button>
      <button class="button button-blue" id="decimal">Decimal</button>
      <button class="button button-blue" id="short">Short</button>
    </div>

<%} %>
					
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