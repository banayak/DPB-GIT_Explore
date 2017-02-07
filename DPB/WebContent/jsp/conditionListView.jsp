<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
	<%@ page contentType="text/html; charset=UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags"%>
	<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>Blocking Condition List view</title>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<script>
function fileLink(val){
	document.getElementById("conditionView").value = "";
	document.getElementById("defId").value = val;
	document.getElementById("conditionListView").action = 'conditionDefinition';
	document.getElementById("conditionListView").submit();
}
function fileLinkView(val,val1){	
	document.getElementById("conditionView").value = val1;
	document.getElementById("defId").value = val;
	document.getElementById("conditionListView").action = 'conditionDefinition';
	document.getElementById("conditionListView").submit();
}
</script>	

<style type="text/css">
	span {
		color: red;
		<%--font-weight:bold;--%>
		font: Verdana;
	}
	.errorMessage{
		<%--font-weight:bold;--%>
		color:red
	}
	.errors{
		<%--font-weight:bold;--%>
		color:red
		}

	.messages {
		<%--font-weight:bold;--%>
		color:blue
		}
	
</style>
</head>
<body>
<s:if test="hasActionErrors()">
		<p></p>
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
<s:form action="conditionListView" method="post">
<s:hidden name="defId" value="" id="defId" />
<s:hidden name="conditionView" value="" id="conditionView"/>

<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	Condition List
</div>
 <div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0" ></td>
			</tr>
			<tr>
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The conditions List for the Dealer Performance Bonus 
				(DPB) Shows the existing Condition and allow user to View and Edit.</div></td>
			<td width="363"  class="right">
	<div align="left">
			<img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
	</div>
			</td>
			</tr>
			<tr>
				<td colspan="2">	
				<div class="template8BottomLine"></div>
				</td>
			</tr>
	</table>
	
</div>
	<TABLE FRAME="VOID" CELLSPACING="0" COLS="29" RULES="NONE" BORDER="0" class="TBL2">
    <TR><b>
		<TD WIDTH="86" HEIGHT="17" ALIGN="LEFT"><b>Condition ID</b></TD>
		<TD WIDTH="86" ALIGN="LEFT"><b>Condition Name</b></TD>
		<!-- <TD WIDTH="86" ALIGN="LEFT"><b>Description</b></TD> -->
		<TD WIDTH="86" ALIGN="LEFT"><b>Condition</b></TD>
		<TD WIDTH="86" ALIGN="LEFT"><b>Condition Type</b></TD>
		<TD WIDTH="86" ALIGN="LEFT"><b>Status</b></TD>
		<TD WIDTH="86" HEIGHT="17" ALIGN="LEFT"><b>Action</b></TD></b>
	</TR> 
    <s:iterator value="cDefList" >
    <tr>
		<TD HEIGHT="17" ALIGN="LEFT"><s:a href="javascript:fileLink(%{id});"><s:property value="id"/></s:a></TD>
		<TD ALIGN="LEFT"><s:property value="conditionName"/></TD>
		<%-- <TD ALIGN="LEFT"><s:property value="conditionDesc"/></TD> --%>
		<TD ALIGN="LEFT"><s:property value="condition"/></TD>
		<TD ALIGN="LEFT"><s:property value="conditionType"/></TD>
		<TD ALIGN="LEFT"><s:property value="status"/></TD>
		<TD ALIGN="LEFT"><s:a href="javascript:fileLink(%{id});">Edit </s:a>/<s:a href="javascript:fileLinkView(%{id},'view');">View</s:a>
		</TD> 
    </TR>    
    </s:iterator> 
     </TBODY>
</TABLE>
<br /><br />
</div>
</s:form>
</body>
</html>