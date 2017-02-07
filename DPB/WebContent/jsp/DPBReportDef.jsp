<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB Report Query Definition</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/jQueryAssets/jquery-1.8.3.min.js" type="text/javascript"></script>
</head>
<body>
<s:form action="reportDefination" method="post">	
<s:div class="pageTitle" id="HL1">DPB Report Query Definition</s:div> 
	<s:div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><s:div class="TX1">The report file query is defined here. </s:div></td>
 
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
	<table width="200" border="0" class="TBL2">
	  <tr>
	    <td><s:label for="reportQueryDefForm.queryId" value="Report Query ID"></s:label></td>
	    <td><s:textfield name="reportQueryDefForm.queryId" id=""/></td>
      </tr>
	  <tr>
	  <td><s:label for="reportQueryDefForm.queryName" value="Report Query Name"></s:label></td>
	    <td><s:textfield name="reportQueryDefForm.queryName" id=""/></td>
      </tr>
	  <tr>
	  <td><s:label for="reportQueryDefForm.description" value="Description"></s:label></td>
	    <td><s:textarea name="reportQueryDefForm.description" id="" cols="45" rows="5" /></td>
      </tr>
	  <tr>
	  <td><s:label for="reportQueryDefForm.queyText" value="Query"></s:label></td>
	    <td><s:textarea name="reportQueryDefForm.queyText" id="" cols="45" rows="5" /></td>
      </tr>
	  <tr>
	  <td><s:label for="reportQueryDefForm.status" value="Status"></s:label></td>
	     <td><s:radio name="reportQueryDefForm.status" list="#{'D':'Draft','A':'Active','I':'In-Active'}" /></td>
      </tr>
	  <tr>
	  <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
      </table>
		<tr>
    	<td><s:submit key="submit" method="SaveReportQueryDefinition" align="right" theme="simple"/></td>
		<td><s:reset key="reset" name="reset" align="center" theme="simple"/></td>
		<td><input type="button" name="button" id="button" value="Cancel"></td>
	  </tr>
	  <br /><br /><br />
</s:form>
</body>
 </html>