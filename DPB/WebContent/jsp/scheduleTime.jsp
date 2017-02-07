<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@page import="java.util.Calendar"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>

<title>dealerPrgView</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			
</head>	
			
<body><s:if test="hasActionErrors()">
		<p></p>
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
<sj:head jquerytheme="flick"/>
<s:form action="schTime" method="post">
<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	DPB 
</div>
<div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0" ></td>
			</tr>
			<tr>
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Dealer Performance Bonus Programs provide
						 all qualified authorized dealers in good standing an opportunity to earn a performance bonus of up to 5.50%
						 for MB and 3.5% for Vans & smart of Manufacturers Suggested Retail Price (MSRP) on eligible transactions.</div></td>
			<td width="363"  class="right">
	<div align="left">
			<img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
	</div>
			</td>
			</tr>
			<tr>
				<td colspan="2">	
				<div class="template8BottomLine"></div>
				</td>
			</tr>
	</table>
</div>
<%--  Data Cleanup for process rerun start --%>
<div class="pageTitle" id="HL2">
	Cleanup Data 
</div>
<p>
	This will clean up the data for the date given as the processing date 
</p>
<table width="200" class="TBL2">
<tr>
	<td width="30%">Enter date:</td>
	<td width="70%"><sj:datepicker name="processingDate" id="processingDate" displayFormat="mm/dd/yy" showOn="focus"/></td>
</tr>
</table>
 	
<table>
 	<tr><td id="text13"><s:submit key="CleanUp" method="cleanUpData" align="right" action="schTime" /></td>
 	<td><b><font color="red">Please don't enter invalid data and submit. After submitting please wait until the first execution completes successfully.</font></b>
	</tr>
</table>
<s:if test="null != cleanUpMessage && cleanUpMessage =='ShowCleanUpMsg'">
<div id = "cleanUpDiv">Your request is submitted successfully and will receive an email once the cleanup is completed.</div>
</s:if>
<br /><br /><br/>
<%--  Data Cleanup for process rerun end --%>


<div class="pageTitle" id="HL2">
	Manual Scheduler 
</div>
<table width="200" border="0" class="TBL2">
 <tr>
<td colspan=2><b><font color="red">Note :This page has been created for running bulk processes through Schedular. Once business confirms, this page will be removed.</font></b>
</td></tr>
<tr>
	<td width="30%">Enter date:</td>
	<td width="70%"><sj:datepicker name="startDate" id="startDate" displayFormat="mm/dd/yy" showOn="focus"/></td>
</tr>
<tr>
	<td width="30%">No. of Days:</td>
	<td width="70%"><s:textfield   name="numberDays" /><span id="span1" ></span></td>
</tr> 
<tr><td width="30%">
<% Calendar c1 = Calendar.getInstance();  
long l1 = c1.getTimeInMillis()+100000;
 %><h2>Current Time in milliseconds:  <%=l1 %></h2></td></tr>
</table>
 	
 	<table><tr><td id="text13"><s:submit key="Submit" method="submitDate" align="right" action="schTime" /></td>
 	<td><b><font color="red">Please don't enter invalid data and submit. After submitting please wait until the first execution completes successfully.</font></b>
	</tr></table>
<br /><br /><br/>
<s:if test="%{schedulerStatus != null && (schedulerStatus=='off' || schedulerStatus=='on')}">
<div class="pageTitle" id="HL1">
	Automatic Scheduler Status Toggle 
</div>
<table width="200" border="0" class="TBL2">
<tr><td width="30%">
<h2>Automatic Scheduler Status:  <s:label value=" %{schedulerStatus}"></s:label></h2></td></tr>
<tr>
<td>
<h2><s:if test="%{schedulerStatus=='off'}">
<s:hidden name="schedulerStatus" value="on"/>
Start
</s:if>
<s:elseif test="%{schedulerStatus=='on'}">
<s:hidden name="schedulerStatus" value="off"/>
Stop
</s:elseif>
Automatic Scheduler</h2>
</td>
</tr>
<tr>
<td id="text13"><s:submit key="Submit" method="toggleScheduler" align="right" action="schTime" /></td>
</tr>
</table>
</s:if>
</div>
</s:form>
</body>

</html>