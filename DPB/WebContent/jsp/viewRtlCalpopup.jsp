<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
	<%@ page contentType="text/html; charset=UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<s:set name="theme" value="simple" scope="page" />
<html>
<head>
<title>File Process</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
	<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
	<script src="<%=request.getContextPath() %>/js/jquery-1.12.0.min.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath() %>/js/dpb.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/js/retailCalendar.js" type="text/javascript"></script>
<style>
.serror {
	color: red;
	padding-left: 3px;
}
</style>
</head>

<body onload="document.documentElement.style.overflowX = 'hidden';">
<s:if test="hasActionErrors()">
	<span>
		<s:actionerror />
	</span>
</s:if>
<s:elseif test="hasActionMessages()">
	<span>
		<s:actionmessage />
	</span>
</s:elseif>

<s:form action="viewRtlCalpopup" name="rtlCal">
	<s:div>
		<s:label theme="simple"><h1>Retail Calendar - ${enteredYear}</h1></s:label>
		
		<table>
		<tr>
			<td>
				<s:label value="Select Year: " theme="simple"/>
				<s:textfield size="10"  name="enteredYear" id="enteredYear" theme="simple" />
				<s:submit key="Submit" id="yearsubmit" theme="simple" ></s:submit>
			</td>
		</tr>
		</table>
		<span id="spanYear" class="serror"></span><br/>
		<s:if test="%{listRtlCal.isEmpty()}">
			<span id="nodata">No data found for the year.</span><br/>
		</s:if>
		<s:else> 
			<table id = "tableRtlCal"  width="70%" height="20" border="1" >
				<thead>
				<tr>
					<th>Month#</th>
					<th>Month</th>
					<th>Month Begin</th>
					<th>Month End</th>
					<th>Year</th>
				</tr>
				</thead>
				<tbody>
					<s:iterator value="listRtlCal">
					<tr>
						<td width="50"><s:property value="retlMthNum"></s:property></td>
						<td width="50"><s:property value="retlMthName"></s:property></td>
						<td width="50"><s:date name="dteRetlMthBeg" format="MM-dd-yyyy"/></td>
						<td width="50"><s:date name="dteRetlMthEnd" format="MM-dd-yyyy"/></td>
						<td width="50"><s:property value="retlYearNum"></s:property></td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
		</s:else>
	</s:div>
</s:form>
</body>
</html>