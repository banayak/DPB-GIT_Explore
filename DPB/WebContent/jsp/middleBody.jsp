<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Mercedes-Benz Dealer Performance Bonus (DPB)</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
		<meta http-equiv="Content-Language" content="en-GB"/>
		<meta name="description" content="Dealer Performance Bonus (DPB) Application"/>
		<meta name="keywords" content="Mercedes-Benz, Mercedes, MB, Merc, Benz, DaimlerChrysler, Daimler-Chrysler, Daimler, Chrysler, Corporate Identity, CI, Corporate Design, CD, CI/CD, CICD, Brand Identity, BI, Brand Design, BD, Brand Design System, BDS, logo, logotype, star, brand mark, brand management, guidelines, artwork, art, work, downloads, brochures, brochure, manual, manuals, Richtlinien, Markendesign, Marke, Design, Markenidentitaet, Identitaet, Handbuch, Markenmanagement, Markenrichtlinien, Firmenzeichen, Stern"/>
		<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath() %>/css/master.css" />
		<script type="text/javascript">
		function dealerProgram(){
		alert("dealerProgram"+<%=request.getContextPath() %>);
        var dealerProgram = document.getElementById('dealerProgram');
      
}
		</script>
</head>
<body>
<s:form>
<s:div id="HL1">
  <pre class="pageTitle">DashBoard</pre>
</s:div>
 
	<s:div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><s:div class="TX1">This is the dashboard of the DPB application. This shows the process(es) for the day.</s:div></td>
 
				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			</tr>
			
			<!--tr>
				<td class="leftEditLinks">&nbsp;</td>
				<td class="rightEditLinks">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" class="line"><img src="../../images/img_t8_line4.gif" width="729" height="1" alt="" border="0"></td>
			</tr-->
		</table>
	</s:div>

	<table cellpadding="0" cellspacing="0" class="TBL2">
	<tr>
		<td>
			<p><strong>Today :</strong></p>
			<table width="709"  border="0" class="placeholderTable">
			  <tr>
			    <td><strong>Process Id</strong></td>
			    <td><strong>Process</strong></td>
			    <td><strong>Status</strong></td>
			    <td><strong>Details</strong></td>
		      </tr>
		      <s:if test="%{todayList.isEmpty()}">
    			<tr>
        			<td colspan="7">No Process find in this day</td>
    			</tr>
			</s:if>
			<s:else>
			  <s:iterator value="todayList">
			  	  <tr>
				    <td width="62"><a href="dpb/pages/treasury/DPB_process_calendar_definition.html"><s:property value="dpbProcessId"></s:property></a></td>
				    <td width="239"><s:property value="dpbProcess.processType"></s:property></td>
				    <td width="150"><s:property value="dpbProcessStatus"></s:property></td>
				    <td width="154"><s:property value="processMessage"></s:property></td>
		      	 </tr>
		      </s:iterator>
		     </s:else>
		     <tr>
				    <td width="62"><a href="dpb/pages/treasury/DPB_process_calendar_definition.html">150</a></td>
				    <td width="239">VISTA FILE</td>
				    <td width="150">In-process</td>
				    <td width="154"><p>Downloaded File : &lt;File Name&gt;</p></td>
		      </tr>
			  <tr>
			    <td><a href="treasury/DPB_process_calendar_definition.html">151</a></td>
			    <td>KPI FILE</td>
			    <td>Downloading</td>
			    <td>Downloading</td>
		      </tr>
			  <tr>
			    <td><a href="treasury/DPB_process_calendar_definition.html">567</a></td>
			    <td>DAILY BONUS PROCESS</td>
			    <td>Process successfully</td>
			    <td><p>No. of Dealers :<br>
		        Total Amount :</p></td>
		      </tr>
			  <tr>
			    <td><a href="treasury/DPB_process_calendar_definition.html">568</a></td>
			    <td>DAILY BONUS PROCESS</td>
			    <td>Yet to Start</td>
			    <td>&nbsp;</td>
		      </tr>
		  </table>
			<p><strong>Yesterday :</strong></p>
			<table width="709"  border="0" class="placeholderTable">
			
			
			  <tr>
			    <td><strong>Process Id</strong></td>
			    <td><strong>Process</strong></td>
			    <td><strong>Status</strong></td>
			    <td><strong>Details</strong></td>
		      </tr>
		      
		      <s:if test="%{yesterdayList.isEmpty()}">
    			<tr>
        			<td colspan="7">No Process find in this day</td>
    			</tr>
			</s:if>
			<s:else>
		      <s:iterator value="yesterdayList">
			  	  <tr>
				    <td width="62"><a href="dpb/pages/treasury/DPB_process_calendar_definition.html"><s:property value="dpbProcessId"></s:property></a></td>
				    <td width="239"><s:property value="dpbProcess.processType"></s:property></td>
				    <td width="150"><s:property value="dpbProcessStatus"></s:property></td>
				    <td width="154"><s:property value="processMessage"></s:property></td>
		      	 </tr>
		      </s:iterator>
			  </s:else>
			  <tr>
			    <td width="62"><a href="treasury/DPB_process_calendar_definition.html">123</a></td>
			    <td width="239">VISTA FILE</td>
			    <td width="150">Process successfully</td>
			    <td width="154"><p>Downloaded File(s) : &lt;File Name&gt;<br>
			      No. of records:<br>
			      Valid records :</p></td>
		      </tr>
			  <tr>
			    <td><a href="treasury/DPB_process_calendar_definition.html">125</a></td>
			    <td>KPI FILE</td>
			    <td>Process successfully</td>
			    <td>Downloaded File : &lt;File Name&gt;<br>
No. of records:<br>
Valid records :</td>
		      </tr>
			  <tr>
			    <td><a href="treasury/DPB_process_calendar_definition.html">567</a></td>
			    <td>DAILY BONUS PROCESS</td>
			    <td>Process failed</td>
			    <td>Reason: DB Error.</td>
		      </tr>
		  </table>
		  <p>&nbsp;</p>
			<table width="1036" border="0" cellspacing="0" class="template8Table" id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3" style="border-width:0px;border-collapse:collapse;">
	<tr>
	  <td colspan="4" class="first"><p>Menu:</p></td>
	  </tr>
	<tr>
		<td width="257" class="first">
					<s:div class="LL4">
						
						
<s:div class="grayText" id="HL1"><strong>File Processing
</strong></s:div>

 
						<input name="ctl00$Level3LinkListTeaserCtrl$dlDCLevel3$ctl00$hidNodeID" type="hidden" id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl00_hidNodeID" value="1775" />
						<ul>
							<li>
								<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl00_hlOverView" href="treasury/file_processing.html">Process Input Files</a>
							</li>
						</ul>
						
								<ul>
									<li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl00_rptDCLevel4_ctl00_hlDCLevel4" href="treasury/file_processing.html">View FIle Process Status</a> </li>
                                </ul>
                      <ul>
                                  <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl00_rptDCLevel4_ctl00_hlDCLevel" href="treasury/file_processing.html">Generate Payment Files</a> </li>
                      </ul>
                                <ul>
                                  <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl00_rptDCLevel4_ctl00_hlDCLevel2" href="treasury/file_processing.html">View Payment File Status</a> </li>
                      </ul>
                      <ul>
                                  <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl00_rptDCLevel4_ctl00_hlDCLevel3" href="treasury/file_processing.html">Generate Report Files</a> </li>
                      </ul>
                                <ul>
                                  <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl00_rptDCLevel4_ctl01_hlDCLevel4" href="treasury/file_processing.html">View Report File Status</a>                                  </li>
                      </ul>
							
					</s:div>
				&nbsp;</td><td width="257" class="first">
					<s:div class="LL4">
						
						
<s:div class="grayText" id="HL1"><strong>Bonus Processing</strong></s:div>
 
						<input name="ctl00$Level3LinkListTeaserCtrl$dlDCLevel3$ctl01$hidNodeID" type="hidden" id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl01_hidNodeID" value="6481" />
						<ul>
							<li>
							<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl01_hlOverView" href="treasury/bonus_processing.html">Process  Bonus Process</a></li>
							<li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl01_hlOverView2" href="treasury/bonus_processing.html">View  Bonus process</a></li>
                            <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl01_rptDCLevel4_ctl03_hlDCLevel4" href="treasury/bonus_processing.html">Process Leadership Bonus</a></li>
                            <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl01_rptDCLevel4_ctl03_hlDCLevel" href="treasury/bonus_processing.html">View  Leadership Bonus Process</a></li>
						</ul>
							
					</s:div>
				&nbsp;</td><td width="257" class="first">
					<s:div class="LL4">
						
						
<s:div class="grayText" id="HL1"><strong>Reports</strong></s:div>
 
						<input name="ctl00$Level3LinkListTeaserCtrl$dlDCLevel3$ctl02$hidNodeID" type="hidden" id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl02_hidNodeID" value="3281" />
						<ul>
							<li>
								<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl02_hlOverView" href="reports.html">Netstar reports</a>
							</li>
						</ul>
						
								<ul>
									<li>
									<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl02_rptDCLevel4_ctl00_hlDCLevel4" href="reports.html">Home Office reports</a></li>
								</ul>
							
					</s:div>
				&nbsp;</td><td width="258" class="last">
					<s:div class="LL4">
						
						
<s:div class="grayText" id="HL1"><strong>Definitions</strong></s:div>
 
						<input name="ctl00$Level3LinkListTeaserCtrl$dlDCLevel3$ctl03$hidNodeID" type="hidden" id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_hidNodeID" value="6135" />
						<ul>
							<li>
								<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_hlOverView" href="srd/DPB_Program_def.html">Program Definition</a>
							</li>
						</ul>
						
					  <ul>
									<li>
										<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_rptDCLevel4_ctl00_hlDCLevel4" href="srd/DPB_special_program_def.html">Special Program Definition</a>
									</li>
								</ul>
							
								<ul>
									<li>
										<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_rptDCLevel4_ctl01_hlDCLevel4" href="srd/DPB_leadership_bonus_definition.html">Leadership Bonus Definition</a>
									</li>
									<li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_hlOverView2" href="accounting/DPB_pgm_dlracc_mapping_view.html">Program DealerAcc mapping</a> </li>
                                </ul>
                                <ul>
                                  <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_rptDCLevel4_ctl02_hlDCLevel4" href="DPB_input_file_def.html">File Definition</a> </li>
                                </ul>
							
								<ul>
									<li>
										<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_rptDCLevel4_ctl03_hlDCLevel4" href="DPB_report_def.html">Report Definition</a>
								  </li>
					  </ul>
							
								<ul>
									<li>
										<a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_rptDCLevel4_ctl04_hlDCLevel4" href="srd/DPB_condition_def.html">Condition Definition</a>
									</li>
									<li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_hlOverView3" href="treasury/DPB_retail_months_definition.html">Retail Months Definition</a> </li>
                                </ul>
                      <ul>
                                  <li><a id="ctl00_Level3LinkListTeaserCtrl_dlDCLevel3_ctl03_hlOverView4" href="treasury/DPB_process_calendar_definition.html">Process Calendar Definition</a> </li>
                      </ul>
                                <ul>
                                  <li></li>
                      </ul>
					</s:div>
				&nbsp;</td>
	</tr><tr>
		<td colspan="4" class="first">&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
</table></td>
	</tr>
</table>
 
<s:div class="template8BottomLine"></s:div>
<br /><br /><br />
</s:form>
</body>
</html>