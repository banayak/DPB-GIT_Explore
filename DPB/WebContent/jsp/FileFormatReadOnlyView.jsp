<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB Input File Format View</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
</head>
<body>
<s:form action="fileformatDef" method="post">	
<s:div id="widecontentarea">
<div class="pageTitle" id="HL1">DPB Input File Format View</div>
 
	<s:div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><div class="TX1">The File Format Definition for DPB is displayed here.</div></td>
 
				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			</tr><tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
		</table>
	</s:div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	   <td width="30%">File Format ID</td>
	    <td width="70%"><s:property value="formatForm.fileFormatId"/></td>
      </tr>
	  <tr>
	    <td>Format Name</td>
	    <td><s:property value="formatForm.formatName"/>&nbsp;</td>
      </tr>
	  <%-- <tr>
	    <td>Description</td>
	    <td><s:iterator value="formatForm.description" var="Desc"><pre><s:property value="#Desc"/></pre><br/></s:iterator></td>
      </tr> --%>
      <tr>
	    <td>Description</td>
	    <td><s:property value="formatForm.formatDescription"/>&nbsp;</td>
      </tr>
	  <tr>
	  <td>Is In File?</td>
	   <td><s:property value="formatForm.inbountFileIndicator"/>&nbsp;</td>
      </tr>
		<tr>
	  <td>Is Header Present in file?</td>
	  <td><s:property value="formatForm.indHeader"/>&nbsp;</td>
      </tr>
	 <tr>
	   <td>De-limiter character</td>
	    <td><s:property value="formatForm.indDelimited"/>&nbsp;</td>
      </tr>
	 <tr>
	    <td>Line width (characters)</td>
	    <td><s:property value="formatForm.fixedLineWidth"/>&nbsp;</td>
	    </tr>
	  <tr>
	    <td>No of columns</td>
	    <td><s:property value="formatForm.columnCount"/>&nbsp;</td>
      </tr>
	  <tr>
	    <td>Table Name</td>
	     <td><s:property value="formatForm.tableName"/>&nbsp;</td>
      </tr>
		<tr id="mapTable">
	    <td colspan="2">
          <BODY TEXT="#000000">
          <TABLE BORDER="0" align="center" CELLSPACING="0" COLS="6" FRAME="VOID" RULES="NONE">
            <COLGROUP>
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
            </COLGROUP>
              <TBODY> 
              <s:if test="%{formatForm.kpList == null || formatForm.kpList.isEmpty()}">
              <TR>
              <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">Id</TD>
                <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">Column No</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Format Text</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Length</TD>
                <TD WIDTH="86" ALIGN="LEFT">Column Name</TD>
              </TR>
              <s:iterator value="formatForm.fileMapingList">
              <TR >
              	<TD><s:property value="fileColumnMapId"/></TD>
                <TD HEIGHT="17" ALIGN="LEFT"><s:property value="fileColumnSeqNumber"/>&nbsp;</TD>
                <TD ALIGN="LEFT"><s:property value="fileColumnformatText"/>&nbsp;</TD>
                <TD ALIGN="LEFT"><s:property value="fileColumnLength"/>&nbsp;</TD>
                <TD ALIGN="LEFT"><s:property value="columnName"/>&nbsp;</TD>
              </TR>
              </s:iterator> 
              </s:if>
              <s:else>
               <TR>
               <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">Id</TD>
                <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">Column No</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Format Text</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Length</TD>
                <TD WIDTH="86" ALIGN="LEFT">Column Name</TD>
                 <TD WIDTH="86" ALIGN="LEFT">KPI Name</TD>
              </TR>
              <s:iterator value="formatForm.fileMapingList">
              <TR >
              	<TD><s:property value="fileColumnMapId"/></TD>
                <TD HEIGHT="17" ALIGN="LEFT"><s:property value="fileColumnSeqNumber"/>&nbsp;</TD>
                <TD ALIGN="LEFT"><s:property value="fileColumnformatText"/>&nbsp;</TD>
                <TD ALIGN="LEFT"><s:property value="fileColumnLength"/>&nbsp;</TD>
                <TD ALIGN="LEFT"><s:property value="columnName"/>&nbsp;</TD>
                <TD ALIGN="LEFT"><s:property  value="kpi.kpiName"/>&nbsp;</TD>             
              </TR>
              </s:iterator> 
              </s:else>
            </TBODY>
        </TABLE></td>
      </tr>
	  <tr>
	      <td>Status</td>
	    <td><s:property value="formatForm.defStatusCode" />&nbsp;</td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
      <tr>
		<td><s:submit key="Cancel" theme="simple" method="getFileFormatListDetails" action="fileformatList"/></td>
	  </tr>

		<br /><br /><br />
</s:div>
</s:form>
 </body>
 </html>