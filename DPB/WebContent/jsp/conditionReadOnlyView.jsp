<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>Condition Definition</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			
</head>
<body>
<s:form method="post">

<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Condition</div>
 
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The conditions for the Dealer Performance Bonus (DPB) special programs, Vehicle type check, DPB program block check.</div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			  </div></td>
			</tr>		
            <tr>
            	<td colspan="2">	
            		<div class="template8BottomLine"></div>
            	</td>
            </tr>
		</table>
</div>
	
<table width="200" border="0" class="TBL2">
	<tr>
		<td width="30%"><s:label for="conditionsDefForm.id" value="Condition Id"  ></s:label></td>
		<td width="70%"><s:property value="conditionsDefForm.id" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.conditionName" value="Condition Name"  ></s:label></td>
		<td><s:property value="conditionsDefForm.conditionName" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.conditionDesc" value="Description"  ></s:label></td>
		<td><s:property value="conditionsDefForm.conditionDesc" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.condition" value="Condition"  ></s:label></td>
		<td><s:property value="conditionsDefForm.condition" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.variableName" value="Variable Name (Check to)"  ></s:label></td>
		<td><s:property value="conditionsDefForm.variableName" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.checkValue" value="Check Value"  ></s:label></td>
		<td><s:property value="conditionsDefForm.checkValue" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.conditionType" value="Condition Type"  ></s:label></td>
		<td><s:property value="conditionsDefForm.conditionType" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.regularExp" value="Format String (Regular Exp)"  ></s:label></td>
		<td><s:property value="conditionsDefForm.regularExp" /></td>
	</tr>
	<tr>
		<td><s:label for="conditionsDefForm.status" value="Status"  ></s:label></td>
		<td><s:property value="conditionsDefForm.status" />
		</td>
	</tr>

</table>
<br /><br /><br />
<table>
	<tr>
		<%-- <td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td> --%>
		<td><s:submit key="Cancel" theme="simple" method="getCondtionsList" action="conditionListView"/></td>
	</tr>
</table>

</div>
</s:form>
</body>
</html>