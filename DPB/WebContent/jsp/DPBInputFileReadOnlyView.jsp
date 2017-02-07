<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<sj:head jquerytheme="flick"/>
<sj:head/>
<title>DPB Input File View</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
</head>
<body>
<s:form action="inputFile" method="post">	
<s:div id="widecontentarea">
<div class="pageTitle" id="HL1">DPB Input File View</div>
 
	<s:div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><div class="TX1">The Inbound files processed as part of DPB is Displayed here.</div></td>
 
				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			</tr>
			<tr>
					<td colspan="2" class="line"><img src="<%=request.getContextPath()%>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
				</tr>
		</table>
	</s:div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	   <td width="30%">File ID</td>
	    <td width="70%"><s:property value="fileDefForm.fileDefId"/>&nbsp;</td>
      </tr>
      <tr>
	   <td>File Definition Name</td>
	    <td><s:property value="fileDefForm.fileDefName"/>&nbsp;</td>
      </tr>
      <tr>
	   <td>File Name (Format)</td>
	    <td><s:property value="fileDefForm.fileNameFormat"/>&nbsp;</td>
      </tr>
	  <%-- <tr>
	  	<td>Description</td>
	    <td><s:iterator value="fileDefForm.fileDescription" var="Desc"><pre><s:property value="#Desc"/></pre><br/></s:iterator></td>
      </tr> --%>
      <tr>
	  	<td>Description</td>
	    <td><s:property value="fileDefForm.description"/>&nbsp;</td>
      </tr>
	  <tr>
	  <td>In Time</td>
	    <td><s:property value="fileDefForm.inTime"/>&nbsp;
      </td>
      </tr>
	  <tr>
	  <td>Process Time</td>
	    <td><s:property value="fileDefForm.readTime"/>&nbsp;
      </td>
      </tr>
      <tr>
	  <td>Is Condition Applicable</td>
	   <td><s:property value="fileDefForm.indCondition"/>&nbsp;</td>
      </tr>
      <tr>
	  <td>File Format</td>
	    <td><s:property value="fileDefForm.fileFormats.formatName"/>&nbsp;</td>
      </tr>
	  <tr>
	  <td>File Base Directory</td>
	    <td><s:property value="fileDefForm.baseFolder"/>&nbsp;</td>
      </tr>
	  <tr>
	  <td>File Process Schedule</td>
	    <td><s:property value="fileDefForm.scheduleCode"/>&nbsp;</td>
      </tr>
	  <tr>
	  <td>File Processing Trigger</td>
	   <td><s:property value="fileDefForm.triggerCode"/>&nbsp;</td>
      </tr>
	  <tr>
	  <td>Status</td>
	    <td><s:property value="fileDefForm.defStatusCode"/>&nbsp;</td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	   <td>&nbsp;</td>
      </tr>
    </table>
      <tr>
		<td><s:submit key="Cancel" theme="simple" method="getFileListDetails" action="inputFileList"/></td>
	  </tr>

		<br /><br /><br />
		</s:div>
</s:form>
 </body>
 </html>