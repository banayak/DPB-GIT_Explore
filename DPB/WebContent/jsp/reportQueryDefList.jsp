<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
	<%@ page contentType="text/html; charset=UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<s:set name="theme" value="'simple'" scope="page" />
<html>
<title>Input File Definition List View</title>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script  src="<%=request.getContextPath() %>/js/dpb.js" type="text/javascript"></script>
			<%-- <script  src="<%=request.getContextPath() %>/js/reportQuery.js" type="text/javascript"></script> --%>
	<script>
	function fileLinkView(val, value){
		document.getElementById("readOnly").value = value;
		document.getElementById("edit").value = val;
		document.getElementById("reportQueryDefList").action = 'reportQueryDef';
		document.getElementById("reportQueryDefList").submit();
	}
	function fileLink(val){
		document.getElementById("edit").value = val;
		document.getElementById("readOnly").value = "";
		document.getElementById("reportQueryDefList").action = 'reportQueryDef';
		document.getElementById("reportQueryDefList").submit();
	}
	</script>	
</head>
<body>
<s:form action="reportQueryDefList" method="post">
<s:hidden name="rId" id="edit" />
<s:hidden name="view" value="" id="readOnly"/>

<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Report Query Definition List </div>
 
	<div class="T8">
	  <table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The report file query is Listed here. </div></td>
 
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
	<tr>	  </tr>
	<tr>	  </tr>
	</table>
	<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
<HTML>
<!--   		BODY,DIV,TABLE,THEAD,TBODY,TFOOT,TR,TH,TD,P { font-family:"Arial"; font-size:x-small }  		 -->
<BODY TEXT="#000000">
<TABLE FRAME="VOID" CELLSPACING="0" COLS="29" RULES="NONE" BORDER="0" class="TBL2">
  <COLGROUP>
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    <COL WIDTH="86">
    </COLGROUP>
  <TBODY>
    <TR>
      <TD WIDTH="90" HEIGHT="17" ALIGN="LEFT">Report Query ID</TD>
      <TD WIDTH="180" ALIGN="LEFT">Report Query Name</TD>
      <TD WIDTH="80" ALIGN="LEFT">Status</TD>
      <TD WIDTH="80" ALIGN="LEFT">Action</TD>
    </TR>
    <s:if test="%{rptQryList.isEmpty()}">
		<tr>
			<td colspan="7">Report Qurey Definitions are not defined yet.</td>
		</tr>
	</s:if>
	<s:else>
	 <s:iterator value="rptQryList">
    <TR>
     <TD HEIGHT="17" WIDTH="86" ALIGN="LEFT"><s:a href="javascript:fileLink(%{reportQueryId});"><s:property value="reportQueryId"/></s:a></TD>
      <TD WIDTH="86" ALIGN="LEFT"><s:property value="reportQueryName"/></TD>
      <TD WIDTH="86" ALIGN="LEFT"><s:property value="status"/></TD>
      <TD WIDTH="86" ALIGN="LEFT"><s:a href="javascript:fileLink(%{reportQueryId});">Edit</s:a>/<s:a href="javascript:fileLinkView(%{reportQueryId},'view');">View</s:a></TD>
    </TR>
	</s:iterator>
	</s:else>
  </TBODY>
</TABLE>
<br /><br />
</div>
</s:form>
</body>
</html>