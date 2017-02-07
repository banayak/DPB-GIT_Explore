<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB Report View</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<s:head/>
</head>
<body>
<s:form action="reportDef" method="post" id="reportDef" name="reportDef">
<s:div id="widecontentarea">
<div class="pageTitle" id="HL1">DPB Report Definition View</div>
	<s:div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><div class="TX1">The in bound files processed as part of DPB is defined here.</div></td>
 				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			</tr>
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
		</table>
	</s:div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	  	<td width="30%">Report ID</td>
	    <td width="70%"><s:property value="reportDefForm.reportDefId"/></td>
	  </tr>
	  <tr>
	  	<td>Report Name</td>
	    <td><s:property value="reportDefForm.reportName"/></td>
      </tr>
	 <%--  <tr>
	  	<td>Description</td>
	    <td><s:iterator value="reportDefForm.reportDescription" var="Desc"><pre><s:property value="#Desc"/></pre><br/></s:iterator></td>
      </tr> --%>
      <tr>
	  	<td>Description</td>
	    <td><s:property value="reportDefForm.description"/></td>
      </tr>
      <tr>
		<td>Report Process Schedule</td>
		<td><s:property value="reportDefForm.scheduleCode"/></td>
	  </tr>
	  <tr>
		<td>Report Processing Trigger</td>
		<td><s:property value="reportDefForm.triggerCode"/></td>
	  </tr>
	  <%-- <tr>
	    <td><s:label for="reportDefForm.reportContent" value="Report Content"></s:label></td>
	    <td><s:select name="reportDefForm.reportContent" list="reportDefForm.reportContentList"/>
	   </td>
      </tr>
	  <tr>
	   <td><s:label for="reportDefForm.reportQuery" value="Report Query"></s:label></td>
	    <td><s:select name="reportDefForm.reportQuery" list="reportDefForm.reportQueryList"/>
	    </td>
      </tr> --%>
       <tr>
	    <td colspan="2"><table border="0" align="center" cellspacing="0" cols="6" frame="void" rules="none">
	      <colgroup>
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	       </colgroup>
	      <tbody>
	        <tr>
	          <td width="120" height="17" align="left">QCR Id</td>
	          <td width="120" height="17" align="left">Sequence Number</td>
	          <td width="173" align="left">Report Content Header</td>
	          <td width="208" align="left">Report Query</td>
	          <td width="208" align="left">Report Content Footer</td>
	        </tr>

	       <s:iterator value="reportDefForm.qcrList">
	        <tr>
	          <td><s:property value="qcrId"/></td>
	          <td><s:property value="seqNum"/></td>
	          <td><s:property value="nameCtnt"/></td>
	          <td><s:property value="nameQry"/></td>
	          <td><s:property value="nameFooter"/></td>
	        </tr>
	        </s:iterator>

	      </tbody>
	  </table>
	  </td>
	  </tr>
	  <tr>
	  	<td>Result Lpp</td>
	  	<td><s:property value="reportDefForm.reportLpp"/></td>
	  </tr>
	  <tr>
	  	<td>Status</td>
	   <td><s:property value="reportDefForm.defStatusCode"/></td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
	     <tr><td>
		<s:submit key="Cancel" theme="simple" method="getReportDefintionList" action="reportDefList"/>
		</td>
	  </tr>
	<br /><br /><br />
	</s:div>
</s:form>
</body>
</html>