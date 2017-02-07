<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>Mercedes-Benz Dealer Performance Bonus (DPB)</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>

<sj:head jquerytheme="flick"/>
<style>
.serror, .serrorHeader {
	color: red;
	padding-left: 3px;
}
.box {
    margin-left:200px;
}
</style>
</head>
<body>
	<s:form action="dashBoard" method="post">
<s:div id="widecontentarea">
	<div class="pageTitle" id="HL1">DashBoard</div>

		<s:div class="T8">
			<table cellspacing="0" class="template8TableTop" border="0">
				<tr>
					<td colspan="2" class="line"><img src="<%=request.getContextPath()%>/images/img_t8_line1.gif" height="1" alt="" border="0">
					</td>
				</tr>
				<tr>
					<td id="ctl00_tdCopyText" class="left">This is the dashboard of the DPB application. This shows the process(es) for the day.</td>
					<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg" style="border-width: 0px;" /><br></td>
				</tr>
				<tr>
					<td colspan="2" class="line"><img src="<%=request.getContextPath()%>/images/img_t8_line1.gif" height="1" alt="" border="0">
					</td>
				</tr>
			</table>
		</s:div>
		<s:div>
		<!-- Modified for date wise process view 
		Start
		-->
		<table class="box">
				
				<tr >
					<td>Select Date To View Processes:</td>
					<td ><sj:datepicker name="selectedDate" id="fromDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus"  /><span id="span21" class="serror"></span></td>
					<td><s:submit></s:submit></td>
				</tr>
			
		</table>
		</s:div>
		
		<s:if test="%{processesList.isEmpty()}">
			<s:if test="%{todayList.isEmpty() && processesList.isEmpty() && yesterdayList.isEmpty()}">
							<strong>No Processes found for this day.</strong>
				</s:if>
				<s:else>
			
				<table width="148%" cellpadding="0" cellspacing="0" class="TBL2">
			<tr>
				<td>
				
					<p>
						<strong>Today :</strong>
					</p>
					
					<table width="949"  border="0" class="placeholderTable">
						<tr>
							<td width="72"><strong>Process Id</strong>
							</td>
							<td width="185"><strong>Process</strong>
							</td>
							 <td width="70"><strong>Process Type</strong></td>
		    				<td width="80"><strong>Process Date</strong></td>
							<td width="80"><strong>Status</strong>
							</td>
							<td width="164"><strong>Details</strong>
							</td>
						</tr>
						<s:if test="%{todayList.isEmpty()}">
							<tr>
								<td colspan="7">No Process found for this day.
								</td>
							</tr>
						</s:if>
						<s:else>
							<s:if test="%{role == true}">
							<s:iterator value="todayList">
								<tr>
									<td width="72"><s:url id="processCalDef" namespace="/" action="processCalDef"></s:url>
									<s:a href="%{processCalDef}"><s:property value="dpbProcessId"></s:property></s:a>
									</td>
									<td ><s:property value="dpbProcess.processType"></s:property>&nbsp;
									</td>
									<td ><s:property value="actProcType"></s:property>&nbsp;</td>
									<td ><s:property value="displayDate"></s:property>&nbsp;</td>
									<td ><s:property value="dpbProcessStatus"></s:property>&nbsp;
									</td>
									<td ><s:property value="processMessage"></s:property>&nbsp;
									</td>
								</tr>
							</s:iterator>
							</s:if>
							<s:elseif test="%{role == false}">
							<s:iterator value="todayList">
								<tr>
									<td width="72"><s:property value="dpbProcessId"></s:property></td>
									<td ><s:property value="dpbProcess.processType"></s:property>&nbsp;</td>
									<td ><s:property value="actProcType"></s:property>&nbsp;</td>
									<td ><s:property value="displayDate"></s:property>&nbsp;</td>
									<td ><s:property value="dpbProcessStatus"></s:property>&nbsp;</td>
									<td ><s:property value="processMessage"></s:property>&nbsp;</td>
								</tr>
							</s:iterator>
							</s:elseif>
						</s:else>
					</table>
					<p>
						<strong>Yesterday :</strong>
					</p>
					<table width="949"  border="0" class="placeholderTable">
						<tr>
							<td width="72"><strong>Process Id</strong>
							</td>
							<td width="185"><strong>Process</strong>
							</td>
							 <td width="70"><strong>Process Type</strong></td>
		    				<td width="80"><strong>Process Date</strong></td>
							<td width="80"><strong>Status</strong>
							</td>
							<td width="164"><strong>Details</strong>
							</td>
						</tr>

						<s:if test="%{yesterdayList.isEmpty()}">
							<tr>
								<td colspan="7">No Process found for this day.</td>
							</tr>
						</s:if>
						<s:else>
							<s:if test="%{role == true}">
							<s:iterator value="yesterdayList">
								<tr>
									<td width="72"><s:url id="processCalDef" namespace="/" action="processCalDef"></s:url>
									<s:a href="%{processCalDef}"><s:property value="dpbProcessId"></s:property></s:a>
									</td>
									<td ><s:property value="dpbProcess.processType"></s:property>&nbsp;
									</td>
					    			<td ><s:property value="actProcType"></s:property>&nbsp;</td>
					    			<td ><s:property value="displayDate"></s:property>&nbsp;</td>
									<td ><s:property value="dpbProcessStatus"></s:property>&nbsp;
									</td>
									<td ><s:property value="processMessage"></s:property>&nbsp;
									</td>
								</tr>
							</s:iterator>
							</s:if>
							<s:elseif test="%{role = false}">
							<s:iterator value="yesterdayList">
								<tr>
									<td width="72"><s:property value="dpbProcessId"></s:property></td>
									<td ><s:property value="dpbProcess.processType"></s:property>&nbsp;</td>
									<td ><s:property value="actProcType"></s:property>&nbsp;</td>
									<td ><s:property value="displayDate"></s:property>&nbsp;</td>
									<td ><s:property value="dpbProcessStatus"></s:property>&nbsp;</td>
									<td ><s:property value="processMessage"></s:property>&nbsp;</td>
									
								</tr>
							</s:iterator>
							</s:elseif>
						</s:else>
					</table>										
			</tr>
		</table>
		</s:else>
		</s:if>
		<s:else>
			<table width="148%" cellpadding="0" cellspacing="0" class="TBL2">
			<tr>
				<td>
					
					<table width="949"  border="0" >
						<tr>
							<td width="72"><strong>Process Id</strong>
							</td>
							<td width="185"><strong>Process</strong>
							</td>
							 <td width="70"><strong>Process Type</strong></td>
		    				<td width="80"><strong>Process Date</strong></td>
							<td width="80"><strong>Status</strong>
							</td>
							<td width="164"><strong>Details</strong>
							</td>
						</tr>
						
						<s:if test="%{role == true}">
							<s:iterator value="processesList">
								<tr>
									<td width="72"><s:url id="processCalDef" namespace="/" action="processCalDef"></s:url>
									<s:a href="%{processCalDef}"><s:property value="dpbProcessId"></s:property></s:a>
									</td>
									<td ><s:property value="dpbProcess.processType"></s:property>&nbsp;
									</td>
					    			<td ><s:property value="actProcType"></s:property>&nbsp;</td>
					    			<td ><s:property value="displayDate"></s:property>&nbsp;</td>
									<td ><s:property value="dpbProcessStatus"></s:property>&nbsp;
									</td>
									<td ><s:property value="processMessage"></s:property>&nbsp;
									</td>
								</tr>
							</s:iterator>
							</s:if>
							<s:elseif test="%{role = false}">
							<s:iterator value="processesList">
								<tr>
									<td width="72"><s:property value="dpbProcessId"></s:property></td>
									<td ><s:property value="dpbProcess.processType"></s:property>&nbsp;</td>
									<td ><s:property value="actProcType"></s:property>&nbsp;</td>
									<td ><s:property value="displayDate"></s:property>&nbsp;</td>
									<td ><s:property value="dpbProcessStatus"></s:property>&nbsp;</td>
									<td ><s:property value="processMessage"></s:property>&nbsp;</td>
									
								</tr>
							</s:iterator>
							</s:elseif>
					</table>
															
			</tr>
		</table>
		</s:else>	
		
		<!-- End -->
		<s:div class="template8BottomLine"></s:div>
		<br />
		<br />
		<br />
		</s:div>
	</s:form>
</body>
</html>