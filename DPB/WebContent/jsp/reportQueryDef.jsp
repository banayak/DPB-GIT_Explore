<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>ReportDefination</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<script  src="<%=request.getContextPath() %>/js/dpb.js" type="text/javascript"></script>
			<script  src="<%=request.getContextPath() %>/js/reportQuery.js" type="text/javascript"></script>
			
<style>
.serror {
	color: red;
	padding-left: 3px;
}

span {
	color: red;
}

</style>
<style type ="text/css">.errors {
	background-color: #ADD8E6;
	border: 1px solid #1E90FF;
	width: 500px;
	margin-bottom: 8px;
}

.errors li {
	list-style: none;
}
</style>
<sj:head jquerytheme="flick"/>
</head>
<body>
<s:form action="reportQueryDefSave" validate="true" >
<s:token></s:token>
<div id="test">
<s:if test="hasActionErrors()">
	<span >
		<s:actionerror />
	</span>
</s:if>
<s:elseif test="hasActionMessages()">
	<span >
		<s:actionmessage />
	</span>
</s:elseif>	
</div>
<s:hidden name="reportQueryForm.flagActive" id="flagActive"></s:hidden>
<s:hidden name="reportQueryForm.flagDraft" id="flagDraft"></s:hidden>
<s:hidden name="reportQueryForm.flagInActive" id="flagInActive"></s:hidden>
<div id="widecontentarea">
<span id="span6" class="serror"></span>
 <div id="HL1"><span class="pageTitle">DPB Report Query Definition</span></div>
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The report file query is defined here. </div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			  </div></td>
			</tr>
			
			<!--tr>
				<td class="leftEditLinks">&nbsp;</td>
				<td class="rightEditLinks">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" class="line"><img src="../../images/img_t8_line4.gif" width="729" height="1" alt="" border="0"></td>
			</tr-->
            <tr><td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
		</table>
	</div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	    <td>Report Query ID</td>
	    <td><s:textfield name="reportQueryForm.reportQueryId"  id="reportQueryId" readonly="true" /></td>
      </tr>
	  <tr>
	    <td>Report Query Name</td>
	    <s:if test="%{reportQueryForm.flagActive == true}">
	    <td><s:textfield name="reportQueryForm.reportQueryName"  id="reportQueryName"  readonly="true"/></td>
	    </s:if>
	    <s:else>
	    <td><s:textfield name="reportQueryForm.reportQueryName"  id="reportQueryName"  /><span id="span1" class="serror"></span></td>
	    </s:else>
      </tr>
      
	  <tr>
	    <td>Description</td>
	    <s:if test="%{reportQueryForm.flagActive == true}">
	    <td><s:textarea  name="reportQueryForm.description" id="description" cols="45" rows="5"  readonly="true" /></td>
	    </s:if>
	    <s:else>
	    <td><s:textarea  name="reportQueryForm.description" id="description" cols="45" rows="5"  /><span id="span2" class="serror"></span></td>
	    </s:else>
      </tr>
      
	  <tr>
	    <td>Query
	      <p><br>
      </p></td>
      <s:if test="%{reportQueryForm.flagActive == true}">
	    <td>
	       <s:textarea  name="reportQueryForm.query" id="query" cols="100" rows="11" readonly="true"  /></td>
	       </s:if>
	       <s:else>
	       <td><s:textarea  name="reportQueryForm.query" id="query" cols="100" rows="11" /><span id="span3" class="serror"></span></td>
	       </s:else>
      </tr>
      
	  <tr>
	    <td>Status</td>
	    <td><p>
	      <s:radio  name="reportQueryForm.status" list="#{'D':'Draft','A':'Active','I':'In-Active'}" id="status"   /><span id="span5" class="serror"></span>
	      <br>
        </p></td>
      </tr>
      
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
 
	<table>
	<tr>
		<td id="submit"><s:submit key="Submit" method="saveReportQueryDefinition" align="right" id ="submitRqry" /></td>
		<td id="reset"><s:reset key="Reset" align="Left" /> </td>
		<s:if test="%{fromDPBList == true}">
			<td><s:submit key="Cancel" theme="simple" method="showReportQueryList" action="reportQueryList"/></td>
	     	<s:hidden name="fromDPBList" value="true" id="fromDPBList"/>
		</s:if>
		<s:else>
			<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
		</s:else>
		<%-- <td><s:submit key="Cancel"  method="navigateToHomePage"/></td> --%>   
	</tr>
</table>
<br /><br /><br />
</div>
</s:form>
</body>
 </html>