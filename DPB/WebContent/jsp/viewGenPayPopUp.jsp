<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
	<%@ page contentType="text/html; charset=UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<s:set name="theme" value="simple" scope="page" />
<html>
<head>
<title>Generate Payment File Process</title>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script  src="<%=request.getContextPath() %>/js/dpb.js" type="text/javascript"></script>	
</head>
<body onload="document.documentElement.style.overflowX = 'hidden';">
<s:form>

			<div class="pageTitle"><span class="Header1">Payment File Process Status</span></div>
			<hr />
			<p>
			  <label>The following are the details of the Generate Payment File Processing Id <s:property  value="procDpbID"></s:property>.</label>
			  </p>
			<table width="148%" cellpadding="0" cellspacing="0" class="TBL2" >
			 <!--  <tr>
			    <th width="34" scope="col">Sno</th>
			    <th width="263" scope="col">Step</th>
			    <th width="130" scope="col">Details</th>
			    <th width="65" scope="col">Status</th>
		      </tr>
			  <tr> -->
			  
			  <tr>
			    <td width="34" scope="col">Sno</td>
			    <td width="20" scope="col">Step</td>
			    <td width="263" scope="col">Details</td>
			    <td width="65" scope="col">Status</td>
		      </tr>
		      
			    <s:iterator value="procesDetail">
    		<TR>
    		<TD ALIGN="LEFT"><s:property value="serialNo" /> </TD>
      		 <TD ALIGN="LEFT"><s:property value="dpbProcessId" /> </TD>
      		 <TD ALIGN="LEFT"><s:property value="processMessage" /> </TD>
     		 <TD ALIGN="LEFT"><s:property value="dpbProcessStatus" /> </TD> 
    		</TR>
    	</s:iterator>
    	
			  </table>

	</s:form>
</body>
</html>






