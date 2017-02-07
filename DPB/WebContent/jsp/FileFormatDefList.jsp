<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
	<head>
		<title>DPB Input File Format List</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>		
			<style>
			span {
				color: red;
			}
			</style>
			<script>
			function fileLinkView(val, value){
				document.getElementById("readOnly").value = value;
				document.getElementById("edit").value = val;
				document.getElementById("fileFormatDefList").action = 'fileformatDef';
				document.getElementById("fileFormatDefList").submit();
			}
			function fileLink(val){
				document.getElementById("edit").value = val;
				document.getElementById("readOnly").value = "";
				document.getElementById("fileFormatDefList").action = 'fileformatDef';
				document.getElementById("fileFormatDefList").submit();
			}
			</script>
</head>
<body>
	<s:if test="hasActionMessages()">
		<p>
			<s:actionmessage />
		</p>
	</s:if>
<s:form action="fileFormatDefList" method="post">
<s:hidden name="fileId" id="edit" />
<s:hidden name="view" value="" id="readOnly"/>
<s:div id="widecontentarea">
<div class="pageTitle" id="HL1">DPB Input File Format List</div>
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The File Format Definition List for the Dealer Performance Bonus (DPB) Shows the existing File Format Definitions and allow user to View and Edit.</div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			  </div></td>
			</tr>
			<tr><td colspan="2">	<div class="template8BottomLine"></div></td></tr>
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
		</table>
	</div>
	
	<BODY TEXT="#000000">
<div>
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
  <TBODY border="1">
    <TR>
      <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">File Format ID</TD>
      <TD WIDTH="86" ALIGN="LEFT">File Format Name</TD>
      <TD WIDTH="86" ALIGN="LEFT">Is In File?</TD>
      <TD WIDTH="86" ALIGN="LEFT">Is Header Present in File?</TD>
      <TD WIDTH="86" ALIGN="LEFT">Delimiter Character</TD>
      <TD WIDTH="86" ALIGN="LEFT">No Of Columns</TD>
      <TD WIDTH="86" ALIGN="LEFT">Status</TD>
      <TD WIDTH="86" ALIGN="LEFT">Action</TD>
    </TR>
    <s:if test="%{fileFormatList.isEmpty()}">
		<tr>
			<td colspan="7">File Format Definitions are not defined yet.</td>
		</tr>
	</s:if>
	<s:else>
		<s:if test="%{role == true}">
	    <s:iterator value="fileFormatList">
	    <TR>
	      <TD HEIGHT="17" ALIGN="LEFT"><s:a href="javascript:fileLinkView(%{fileFormatId});"><s:property value="fileFormatId"/></s:a></TD>
	      <TD ALIGN="LEFT"><s:property value="formatName"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="inbountFileIndicator"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="indHeader"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="indDelimited"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="columnCount"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="defStatusCode"/>&nbsp;</TD>
	      <TD WIDTH="86" ALIGN="LEFT"><s:a href="javascript:fileLink(%{fileFormatId});">Edit</s:a>/<s:a href="javascript:fileLinkView(%{fileFormatId},'view');">View</s:a></TD>
	    </TR>
	    </s:iterator>
	    </s:if>
	    <s:elseif test="%{role == false}">
	    <s:iterator value="fileFormatList">
	    <TR>
	      <TD HEIGHT="17" ALIGN="LEFT"><s:property value="fileFormatId"/></TD>
	      <TD ALIGN="LEFT"><s:property value="formatName"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="inbountFileIndicator"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="indHeader"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="indDelimited"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="columnCount"/>&nbsp;</TD>
	      <TD ALIGN="LEFT"><s:property value="defStatusCode"/>&nbsp;</TD>
	      <TD WIDTH="86" ALIGN="LEFT"><s:a href="javascript:fileLinkView(%{fileFormatId},'view');">View</s:a></TD>
	    </TR>
	    </s:iterator>
	    </s:elseif>
    </s:else>
  </TBODY>
</TABLE>
<br /><br />
<br /><br />
</div>
</body>
</s:div>
</s:form>
</body>
</html>