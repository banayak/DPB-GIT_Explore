<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>

<title>fileProcessManualExecution</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			

</head>
<body>
<sj:head jquerytheme="flick"/>


<s:form name="myform" id="fileProcessingInp" action="fileProcessingInp"  >
<s:hidden name="actDate" id="actDate" value="" />
<s:token/>
<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	Start Manual Process
</div>
 

<table width="200" border="0" class="TBL2">
		<tr>
			<td><s:label value= "Start Date"  ></s:label></td>
			<td><sj:datepicker name="fileProcessDefBean.scheduledDate" id="startDate" displayFormat="mm/dd/yy" showOn="focus"/></td>
		</tr>
</table>
<table>
	<tr>
		<td id="text13"><td><s:submit action="fileProcessingInpStartProc" theme="simple"  key="submit" value="Start Process" method="startSchedulerManualCall" />
	</tr>
</table>

<br /><br /><br/>
</div>
</s:form>
</body>
</html>