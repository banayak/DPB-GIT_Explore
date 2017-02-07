<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>ReportDefination</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>

</head>
<body>
<s:form >		

<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Report Query Definition View </div>
 
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The report file query is Viewed here. </div></td>
 
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
	    <td width="30%">Report Query ID</td>
	    <td width="70%"><s:property value="reportQueryForm.reportQueryId"   />
      </tr>
	  <tr>
	    <td>Report Query Name</td>
	    <td><s:property value="reportQueryForm.reportQueryName"  /></td>
      </tr>
	  <tr>
	    <td>Description</td>
	    <td>
	      <s:iterator value="reportQueryForm.desc" var="Desc"> <pre><s:property value="#Desc"/></pre><br/> </s:iterator> </td>
	     <%-- <pre><s:property value="reportQueryForm.description"/></pre></td> --%>
	   
      </tr>
	  <tr>
	    <td>Query
	      <p><br>
      </p></td>
	    <td>
	 <s:iterator value="reportQueryForm.arrquery" var="Drrquery"><pre><s:property value="#Drrquery"/></pre><br/></s:iterator>
	    </td>
      </tr>
	  <tr>
	    <td>Status</td>
	    <td><p>
	     <s:property value="reportQueryForm.showStat"/>
        </p></td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
    <table>
    		<tr>
    		<td><s:submit key="Cancel" theme="simple" method="showReportQueryList" action="reportQueryList"/></td>
    		</tr>
    </table>
<br /><br /><br />
</div>
</s:form>
</body>
 </html>