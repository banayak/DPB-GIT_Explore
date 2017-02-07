<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>

<head>
<title>DPB_Program_list</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<script type="text/javascript">
			
		
			function fileLink(val,isSpecial){
			document.getElementById("programListView").value = "";
				document.getElementById("programIdD").value = val;
				if(isSpecial=='S'){
					document.getElementById("dealerProgram").action='specialDealerProgram';
				}else{
					document.getElementById("dealerProgram").action='dealerProgramListEdit';
					}
				document.getElementById("dealerProgram").submit();
       			
			}
			
			function fileLinkView(val,display){
				document.getElementById("programListView").value = display;
				document.getElementById("programIdD").value = val;
				document.getElementById("dealerProgram").action='dealerProgramListEdit';
				document.getElementById("dealerProgram").submit();
       		
       			
			}
			</script>
			<style type="text/css">
			
			
			</style>
			
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
<s:form action="dealerProgram" method="post">
<s:hidden name="programIdD" value="%{programId}" id="programIdD"/>
<s:hidden name="programListView" value="" id="programListView"/>
 <s:hidden name="dlrPrgmForm.programId" />
<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	DPB Program List
</div>
 <div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0" ></td>
			</tr>
			<tr>
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Mercedes-Benz Dealer Performance Bonus Program, which was initially launched on January 3, 2008, has
				  been updated for 2013 and now provides all qualified authorized dealers in good standing an opportunity to
				  earn a performance bonus of up to 5.50% of Manufacturers Suggested Retail Price on eligible transactions.. </div></td>
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
    <TR><b>
      <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">Program ID</TD>
      <TD WIDTH="86" ALIGN="LEFT"><s:label for="dlrPrgmForm.programName" value="Program Name"></s:label></td>
      <TD WIDTH="86" ALIGN="LEFT"><s:label for="dlrPrgmForm.startDate" value= "Start Date"  ></s:label></td>
      <TD WIDTH="86" ALIGN="LEFT"><s:label for="dlrPrgmForm.endDate" value="End Date" ></s:label></td>
      <TD WIDTH="86" ALIGN="LEFT"><s:label for="kpiValue" value="KPI" ></s:label></td>
      <TD WIDTH="86" ALIGN="LEFT"><s:label for="dlrPrgmForm.programStatus" value="Status"  ></s:label></td>
      <TD WIDTH="86" ALIGN="LEFT"><s:label  value="Action"  ></s:label></TD></b>
    </TR>
    <s:iterator value="prgList">

    
    <TR>
      <TD HEIGHT="17" ALIGN="CENTER"><s:a href="javascript:fileLink(%{programId},'%{specialProgram}');"> <s:property value="programId" /> </s:a></TD>
      <TD ALIGN="LEFT"><s:property value="programName" /> </TD>
      <TD ALIGN="LEFT"><s:property value="startDate" /> </TD>
      <TD ALIGN="LEFT"><s:property value="endDate" /> </TD> 
      <TD ALIGN="LEFT"><s:property value="kpiValue" /> </TD>
      <%-- <TD ALIGN="LEFT"><s:property value="vehicleType" />  </TD>
      
       --%><TD ALIGN="LEFT"><s:property value="programStatus" /> </TD> 
      <TD  ALIGN="LEFT">
	  <s:a href="javascript:fileLink(%{programId},'%{specialProgram}');">Edit /</s:a>
	  <s:a href="javascript:fileLinkView(%{programId},'view');">View</s:a></TD>
    </TR>
    </s:iterator>
   
  </TBODY>
</TABLE>
<br /><br />
</div>
</s:form>
</body>
</html>