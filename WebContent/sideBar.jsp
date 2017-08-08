<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">a {text-decoration: none}</style>
<style>
body {
    font-family: "Lato", sans-serif;
}

.sidenav {
    height: 100%;
    width: 0;
    position: fixed;
    z-index: 1;
    top: 0;
    left: 0;
    background-color: #f9ebdb;
    overflow-x: hidden;
    transition: 0.5s;
    padding-top: 50px;
}

.sidenav a {
    padding: 8px 8px 8px 32px;
    text-decoration: none;
    font-size: 15px;
    color: #111;
    display: block;
    transition: 0.3s;
}

.sidenav a:hover, .offcanvas a:focus{
    color: grey;
}

.sidenav .closebtn {
    position: absolute;
    top: 0;
    right: 0px;
    font-size: 40px;
    margin-left: 60px;
}


@media screen and (max-height: 450px) {
  .sidenav {padding-top: 15px;}
  .sidenav a {font-size: 15px;}
}


ul {
    display: block;
    list-style-type: bullet;
    margin-top: 1em;
    margin-bottom: 1 em;
    margin-left: 0;
    margin-right: 0;
    padding-left: 20px;
}


</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class = "widget">
			<h3>Links | ICP-Billing QA</h3>
		<ul>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/BillingOpsQA/BillOpsQaDocs.jsp">Billing</a></li>
		    <li>Usage</li>
		    <li>Payment/Adjustment</li>
		    <li>Journaling</li>
		    <li>Bill Printing</li>
			<!--  <li><a
				href="http://10.23.23.55:8080/IcpBrmException/BillingOpsQA/BillOpsQaMain.jsp"><strike>Billing
					Ops QA - Process</strike></a></li>-->
		</ul>
		
			<h3>Links | ICP-Billing </h3>
			<ul>
			  <li><span style="font-size:12px;cursor:pointer;color:orange" onclick="openNav('mySidenav')">Billing</span></li>
			  <li><span style="font-size:12px;cursor:pointer;color:orange" onclick="openNav('mySidenav2')">Invoice</span></li>
			  <li><span style="font-size:12px;cursor:pointer;color:orange" onclick="openNav('mySidenav3')">ICP-BRM Exception</span></li>			  
			  <li>BIVE</li>
			  <li>BIP</li>
			  <li><span style="font-size:12px;cursor:pointer;color:orange" onclick="openNav('mySidenav4')">Bill/Invoice Statistic</span></li>
			  
			</ul>
		<!--  <h3>Links | ICP-Exception</h3>
		<ul>
			<li><a
				href="http://10.23.23.55:8080/IcpBrmException/AccntObjDetail.jsp">Account
					Object Detail Check</a></li>
			<li><a
				href="http://10.23.23.55:8080/IcpBrmException/XmlToFlist.jsp">XML
					to FList Converter</a></li>
								<li><a
				href="http://10.23.23.55:8080/IcpBrmException/testnapBRM.jsp">BRM Testnap</a></li>
			 <li><a href="#">Server Status Check</a></li>
		</ul>-->





		<h3>Links | Others</h3>
		<ul>
			<li><a href="http://iris.tm.com.my/sm/index.do">IRIS</a></li>
			<li><a href="https://tmbill.tm.com.my/CustomerCenter/">BRM
					Customer Center</a></li>
			<li><a
				href="http://10.41.86.34:7281/iCareEAI_Web_Support_Tool_war/index.jsp">iCarePrime
					Web Support Tools</a></li>
		</ul>

		<!--  <h3>
			<a href="http://10.23.23.55:8080/IcpBrmException/">MAIN PAGE</a>
		</h3>-->
		<br>
<a href="http://10.23.23.55:8080/IcpBrmException/">Back to main</a>
	</div>
	
<div id="mySidenav" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav('mySidenav')">&times;</a>

			    <h2>Bill Run</h2>
			        <ul>
			        <li><a href="http://10.23.23.55:8080/IcpBrmException/BillInvCheck/TotalBPBill.jsp">Total BP</a></li>
			        <li><a href="http://10.23.23.55:8080/IcpBrmException/CheckBillBal.jsp">Balance RT/TP</a></li>
			        <li><a href="http://10.23.23.55:8080/IcpBrmException/BillInvCheck/CheckProgStatus.jsp?type=bill">Program Status</a></li>
			        </ul>

			    <br><br><br><br><br><br>
<div align="center">
<a href="http://10.23.23.55:8080/IcpBrmException/"><font size="2" color="orange">Back to Main</font></a>
<a href="javascript:void(0)" onclick="closeNav('mySidenav')"><font size="2" color="orange">Close</font></a>
</div>  
</div>


<div id="mySidenav2" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav('mySidenav2')">&times;</a>

<h2>Invoice Run</h2>
<ul>
			        <li><a href="http://10.23.23.55:8080/IcpBrmException/BillInvCheck/TotalBPInv.jsp">Total BP</a></li>
			        <li><a href="http://10.23.23.55:8080/IcpBrmException/CheckInvBal.jsp">Balance RT/TP</a></li>
			        <li><a href="http://10.23.23.55:8080/IcpBrmException/BillInvCheck/CheckProgStatus.jsp?type=inv">Program Status</a></li>
			        </ul>
			    
			    <br><br><br><br><br><br>
<div align="center">
<a href="http://10.23.23.55:8080/IcpBrmException/"><font size="2" color="orange">Back to Main</font></a>
<a href="javascript:void(0)" onclick="closeNav('mySidenav2')"><font size="2" color="orange">Close</font></a>
</div> 
</div>

<div id="mySidenav3" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav('mySidenav3')">&times;</a>
  
<h2>ICP-BRM Exception</h2>
		<ul>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/AccntObjDetail.jsp">Account Object Detail Check</a></li>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/getIcpEaiXML.jsp">Get EAI Request XML</a></li>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/XmlToFlist.jsp">XML to FList Converter</a></li>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/testnapBRM.jsp">BRM Testnap</a></li>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/ExceptionReport.jsp">Generate Exception Report</a></li>
			<!--  <li><a href="#">Server Status Check</a></li>-->
		</ul>
			    
			    <br><br><br><br><br><br>
<div align="center">
<a href="http://10.23.23.55:8080/IcpBrmException/"><font size="2" color="orange">Back to Main</font></a>
<a href="javascript:void(0)" onclick="closeNav('mySidenav3')"><font size="2" color="orange">Close</font></a>
</div> 
</div>

<div id="mySidenav4" class="sidenav">
  <a href="javascript:void(0)" class="closebtn" onclick="closeNav('mySidenav4')">&times;</a>
  
<h2>Bill Invoice Stats</h2>
		<ul>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/BillInvCheck/BillInvStats.jsp?month=non">Bill/Invoice Table</a></li>
			<li><a href="http://10.23.23.55:8080/IcpBrmException/BillInvCheck/BillInvGraph.jsp?month=non">Bill/Invoice Graph</a></li>
		</ul>
			    
			    <br><br><br><br><br><br>
<div align="center">
<a href="http://10.23.23.55:8080/IcpBrmException/"><font size="2" color="orange">Back to Main</font></a>
<a href="javascript:void(0)" onclick="closeNav('mySidenav4')"><font size="2" color="orange">Close</font></a>
</div> 
</div>
	<script>

    
	function openNav(id) {
	    document.getElementById(id).style.width = "200px";
	    if(id.indexOf("2") > 0)
	    {	    	
		    document.getElementById("mySidenav").style.width = "0";
		    document.getElementById("mySidenav3").style.width = "0";
		    document.getElementById("mySidenav4").style.width = "0";
	    }
	    else if(id.indexOf("3") > 0)
	    {
	    	document.getElementById("mySidenav").style.width = "0";
	    	document.getElementById("mySidenav2").style.width = "0";
	    	document.getElementById("mySidenav4").style.width = "0";
	    }
	    else if(id.indexOf("4") > 0)
	    {
	    	document.getElementById("mySidenav").style.width = "0";
	    	document.getElementById("mySidenav2").style.width = "0";
	    	document.getElementById("mySidenav3").style.width = "0";
	    }
	    else
	    {
	    	document.getElementById("mySidenav4").style.width = "0";
	    	document.getElementById("mySidenav2").style.width = "0";
	    	document.getElementById("mySidenav3").style.width = "0";
	    }
	}




	function closeNav(id) {
	    document.getElementById(id).style.width = "0";
	}
</script>
</body>
</html>