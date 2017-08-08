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
					<h2>List of Activities by process</h2>

					Quality assurance (QA) is a way of preventing mistakes or defects
					in manufactured products and avoiding problems when delivering
					solutions or services to customers; which ISO 9000 defines as "part
					of quality management focused on providing confidence that quality
					requirements will be fulfilled". <br> Framework for each
					processes:-<br>
					
						
<style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
.tg .tg-om3m{background-color:#3531ff;color:#ffffff;text-align:center;vertical-align:top}
.tg .tg-xazt{background-color:#f8a102;color:#000000;text-align:center}
.tg .tg-q7u4{background-color:#00009b;color:#ffffff}
.tg .tg-efv9{font-family:Arial, Helvetica, sans-serif !important;}
.tg .tg-ugrf{background-color:#3531ff;color:#ffffff;text-align:center}
.tg .tg-x64g{background-color:#9b9b9b;color:#000000;text-align:center}
.tg .tg-s8kq{background-color:#fcff2f;color:#000000;text-align:center}
.tg .tg-yw4l{vertical-align:top}
</style>
<table class="tg">
  <tr>
    <th class="tg-q7u4">Process</th>
    <th class="tg-q7u4">Activity</th>
    <th class="tg-q7u4">PIC</th>
    <th class="tg-q7u4">Time/Date</th>
    <th class="tg-q7u4">Remarks</th>
  </tr>
  <tr>
    <td class="tg-xazt">SI SYNC after REL<br>(DBA)</td>
    <td class="tg-efv9">Job<br>excuted by DBA</td>
    <td class="tg-031e">DBA<br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">LA Tagging<br>(ICBRM165)</td>
    <td class="tg-031e">Change the status of an account<br>from Normal account to Large account based on number of usages and services,<br>Node 1,<br>Path :<br>/app/brm/base/apps/largeTagging<br>Command : <br>nohup large_acct_tag_mnl.sh,&amp;,Example : nohup large_acct_tag_mnl.sh 01/16/2015 &amp;</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-x64g">WRITEOFF Loader<br>(ICBRM034)</td>
    <td class="tg-031e">Execute WRITEOFF Loader after DB sync and before start Billing BP1<br>Path: <br>/app/shared/tm_batch/brm_batch/bfw/scriptdir/bin<br>Script :<br>brm_writoffldr01l.ksh</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-x64g">WRITEOFF Process<br>(ICBRM035)</td>
    <td class="tg-031e">Execute WRITEOFF Process after DB sync and before start Billing BP1<br>Staging,<br>Path: /app/shared/tm_batch/brm_batch/bfw/scriptdir/bin<br>Script:<br>brm_writoffproc01p.ksh</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">pin_bill_day_large<br>(ICBRM187)</td>
    <td class="tg-031e">Wrapper Script has been created to Execute Bill run<br>Node 1,<br>Path : <br>/app/brm/base/bin,Script : pin_bill_day_large<br>Command : <br>nohup pin_bill_day_large &amp;</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">Checking billing completeness<br>(ICBRM87a)</td>
    <td class="tg-031e">Gateway for billing.<br>Script will exit with 0 signal,if no more unbilled.<br>Node 1,<br>Path :<br>/app/brm/base/apps/gateway/<br>Script : count_unbill.ksh,Command : nohup count_unbill.ksh &amp;</td>
    <td class="tg-031e">A-Auto <br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">pin_bill_day_large2<br>(ICBRM188)</td>
    <td class="tg-031e">A wrapper script has been created to run bill_stream_batch and pin_inv_accts together which located in<br>node 1. If this script triggered, there no need to run bill_stream_batch and pin_inv_accts<br>manually<br>Path : <br>/app/brm/base/bin,Script : pin_bill_day_large2<br>Command : <br>nohup pin_bill_day_large2 &amp;</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">Checking invoice completeness<br>(ICBRM188a)</td>
    <td class="tg-031e">Gateway for invoicing.,Script will exit with 0 signal if no more uninvoice<br>Node 1<br>Path :<br>/app/brm/base/apps/gateway/count_uninvoice.ksh<br>Script : <br>count_uninvoice.ksh <br>Command : <br>nohup count_uninvoice.ksh &amp;<br></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-xazt">XML ready for BPM<br>(ICBRM003)</td>
    <td class="tg-031e">Exporting invoiced bill into XML<br>form so that can be send to BPM for printing and generating PDF for TM Archival<br>and E-Bill.<br>Node 8<br>Path :<br>/app/brm/tmbrm_batch/BPMInvoiceExtraction/scriptdir/bin<br>Script: <br>brm_bive_wrapper_909.ksh<br>Command:<br> nohup<br>brm_bive_wrapper_909.ksh &gt; brm_bive_wrapper_909.ksh.log &amp;</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">Trigger manually.<br>Refer runbook for the execution<br>steps</td>
  </tr>
  <tr>
    <td class="tg-s8kq">Verify output &amp; send manually to BPM</td>
    <td class="tg-031e">To verify total count whether tally or not. <br>Check first acc, last acc, total account &amp; file number for all xml files.<br>Staging<br>Path : /app/shared/brm/pin/tmbrm_batch/BPM/outbox/test/</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">Split manually.<br>Refer runbook for the spliting steps</td>
  </tr>
  <tr>
    <td class="tg-s8kq">Split XML file<br>Manually</td>
    <td class="tg-031e">To split files into smaller amount (10k each).<br>Staging<br>Path :<br>/app/shared/batchfs/BIP/inbox/<br>Script : <br>split_new_909.ksh<br>Command : <br>nohup split_new_909.ksh &gt; test.log &amp;</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">Split manually.<br>Refer runbook for the spliting steps</td>
  </tr>
  <tr>
    <td class="tg-xazt">PDF Generation<br>(ICBRM004)</td>
    <td class="tg-031e">PDF generation is a process to generating PDF bill from the input XML files produce by XML export process which is BIVE run for normal accounts.<br>Staging<br>Path,:<br>/app/shared/tm_batch/brm_batch/BPMPDFInvoiceGeneration/bin<br>Script : inv_gen<br>Command : nohup inv_gen &gt;<br>inv_BP25May_batch3.log &amp;</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">Generate manually.<br>Refer runbook for generating steps.</td>
  </tr>
  <tr>
    <td class="tg-s8kq">Verify XML &amp; output</td>
    <td class="tg-031e">To verify whether pdf generation output are tally.<br>Path : <br>/app/shared/batchfs/BIP/outbox</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">Split manually.<br>Refer runbook for the spliting steps</td>
  </tr>
  <tr>
    <td class="tg-xazt">Send to archival,(ICBRM005)</td>
    <td class="tg-031e">To consolidates the PDF bills to be sent to TM Archival.<br>Staging<br>Path :<br>/home/staging/kuz/auto_xml<br>Script : auto_pdf_PT_v1_scp<br>Command : nohup auto_pdf_PT_v1_scp bp25may_batch2 153853 &amp;</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
    <td class="tg-031e">Archive manually.<br>Refer runbook for archival steps</td>
  </tr>
  <tr>
    <td class="tg-ugrf">Credit Utilization<br>(ICBRM072)</td>
    <td class="tg-031e">Execute in staging<br>Path : <br>/app/shared/tm_batch/brm_batch/bfw/scriptdir/bin_CU_Reporting<br>Script :<br>brm_CamsCreditUtilization.ksh<br>Command : nohup brm_CamsCreditUtilization.ksh &gt; cu_20170308_rerun.log &amp;</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">Credit Limit<br>(ICBRM071)</td>
    <td class="tg-031e">Execute in staging<br>Path : /app/shared/tm_batch/brm_batch/bfw/scriptdir/bin<br>Script : brm_camsCreditLimit.ksh<br>Command : nohup brm_camsCreditLimit.ksh &lt; /dev/null&gt; CreditLimit_20160605.out &amp;</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">Invoice Details Extraction,(ICBRM010)</td>
    <td class="tg-031e">Invoice extraction jobs are dependent on invoicing job in every BP<br>Path :<br>/app/shared/tm_batch/brm_batch/InvoiceExtraction/bin<br>Scipt : inv_extract2<br>Command : nohup inv_extract2,&gt; xxxxxx.out &amp;, Example : BP07: nohup<br>inv_extract2 02/04/2014 02/07/2014 &gt; Inv_BP07Feb.out &amp;<br>Log Location : Staging <br>/app/shared/tm_batch/brm_batch/InvoiceExtraction/log,New File:,i.<br>/app/shared/batchfs/FAAMS/outbox/new &gt; ../send,1.<br>ICP_CAMSA_INVOICE_MYR_20120916_20120916_150028_0021a.dat,2.<br>ICP_CAMSA_INVOICE_MYR_20120916_20120916_150028_END.dat,ii.<br>/app/shared/batchfs/eQuest/outbox/new&gt; ../send,1.<br>ICP_CAMSC_INVOICE_MYR_20120916_20120916_150028_0021a.dat,2.<br>ICP_CAMSC_INVOICE_MYR_20120916_20120916_150028_END.dat,iii.<br>/app/shared/batchfs/NPCS/outbox/new &gt; ../send,1. ICP_NPCS_invoice_20121022_212338.dat</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e">3.00 PM</td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">Billed Payment Extraction<br>(ICBRM011)</td>
    <td class="tg-031e">Bill payment extraction jobs are dependent on billing job in every BP<br>Path :/app/shared/tm_batch/brm_batch/BilledPaymentExtraction/bin<br>Script : ./billed_extract<br>Command :nohup./billed_extract last BP date this BP date &gt;,xxxxx.out &amp;<br>Sample: nohup ./billed_extract 10/01/2016 10/04/2016 &gt; Billed_BP04Oct2016rerun.out</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e">5.00 PM</td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">Billed Adjustment Extractor (ICBRM030)</td>
    <td class="tg-031e">Bill adjustment extraction need<br>to be run daily. If the day fall on BP day, it must wait for BP job for</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e">11.30 AM<br>(Daily)</td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">LA Spooler</td>
    <td class="tg-031e">Large Account XML can be exported using XML Spooler. XML Spooler are used because the number of serviceand usage for the invoice are big in numbers and cannot be cater by using normal export module.<br>Node 1<br>Path :<br>/app/brm/tmbrm_batch/InvXmlSpooler<br>Script :<br>brm_custom_invoice_extract.sh<br>Command : nohup brm_custom_invoice_extract.sh &amp;</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e">After<br>billing/invoicing complete</td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-ugrf">Pin Cycle Fee<br>(ICBRM001)</td>
    <td class="tg-031e">Batch to drop the pin cycle fees charge<br>Node 1<br>Path : <br>/app/brm/base/bin,Script : pin_bill_day_cycle_fees<br>Command : <br>nohup pin_bill_day_cycle_fees &amp;</td>
    <td class="tg-031e">A-Auto<br>team</td>
    <td class="tg-031e"></td>
    <td class="tg-031e"></td>
  </tr>
  <tr>
    <td class="tg-om3m">SMS<br>(ICBRM173)</td>
    <td class="tg-yw4l">ICP BRM will receive a response<br>file from TM Archival on the status of success or failed archival for all bills<br>received in each file.<br>Execute auto_sms_ST_V1.sh<br>Run auto_pdf_ST_V1.sh<br>Command: nohup auto_SMS_ST_V1.sh<br> sms0904.out &amp;</td>
    <td class="tg-yw4l">A-Auto team</td>
    <td class="tg-yw4l"></td>
    <td class="tg-yw4l"></td>
  </tr>
</table>



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