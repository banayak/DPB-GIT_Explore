<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB_Leadership_Bonus_list</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<style type="text/css">
span {
	color: red;
}
</style>
			<script>
			function fileLink(val){		
				var view = false;	
				document.getElementById("ldrshipidLinkView").value = view;			
				document.getElementById("ldrshipidL").value = val;
				document.getElementById("fromDPBList").value = true;
				document.getElementById("dpbLeadershipBonusList").action='leadershipBonusDefs';
       			document.getElementById("dpbLeadershipBonusList").submit();
			}
			</script>
			<script>
			function fileLinkView(val){	
				var view = true;	
				document.getElementById("ldrshipidLinkView").value = view;
				document.getElementById("ldrshipidL").value = val;
				document.getElementById("dpbLeadershipBonusList").action='leadershipBonusDefs';
       			document.getElementById("dpbLeadershipBonusList").submit();
			}
			</script>
</head>
<body>
<s:if test="hasActionErrors()">
		<p></p>
		<div class="errors">
			<s:actionerror />
		</div>
</s:if>
<s:form name = "dpbLeadershipBonusList" action="dpbLeadershipBonusList" method="post">
<s:hidden name="ldrshipidL" value="%{ldrshipid}" id="ldrshipidL"/>
<s:hidden name="ldrshipidLinkView" value="%{ldrshipidLinkView}" id="ldrshipidLinkView"/>
<s:hidden name="ldrshipbnsdtls.flagActive" value= "%{ldrshipbnsdtls.flagActive}" id="flagActive"   />
<s:hidden name="fromDPBList" value="%{fromDPBList}" id="fromDPBList"/>

<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	DPB Leadership Bonus Definition List
</div>
 
<div class="T8">
<table width="728" border="0" cellspacing="0" class="template8TableTop">
	<tr>
		<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
	</tr>
	<tr>
		<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The DPB application provides the option to
								business users to define and update the leadership bonus for
								current and future fiscal year that needs to be paid.</div></td>

		<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
	  </div></td>
	</tr>
	<tr>
		<td colspan="2">	
			<!-- <div class="template8BottomLine"></div></td> -->
			 <tr><td colspan="2"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
	</tr>
</table>
</div>

<table width="200" border="0" class="TBL2">
	<tr>	  </tr>
	<tr>	  </tr>
</table>

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
      <TD WIDTH="120" HEIGHT="17" ALIGN="LEFT"><s:label value ="Leadership Bonus ID"></s:label></TD>
      <TD WIDTH="150" HEIGHT="17" ALIGN="LEFT"><s:label value ="Leadership Bonus Name"></s:label></TD>
      <TD WIDTH="60" HEIGHT="17" ALIGN="LEFT"><s:label value ="Retail Start Date"></s:label></TD>
      <TD WIDTH="60" HEIGHT="17" ALIGN="LEFT"><s:label value ="Retail End Date"></s:label></TD>
      <TD WIDTH="110" ALIGN="LEFT"><s:label value ="Unused Amount"></s:label></TD>
      <TD WIDTH="160" ALIGN="LEFT"><s:label value ="Leadership Eligible Units"></s:label></TD>
       <TD WIDTH="90" ALIGN="LEFT"><s:label value ="Process Date"></s:label></TD>
      <TD WIDTH="35" ALIGN="LEFT"><s:label value ="Status"></s:label></TD>
      <TD WIDTH="50" ALIGN="LEFT"><s:label value ="Action"></s:label></TD></b>
    </TR>
    <s:iterator value="ldrshipBnsList">
    <TR>
      	 <TD HEIGHT="17" ALIGN="LEFT"><s:a href="javascript:fileLink(%{ldrshipid});"><s:property value="ldrshipid"/></s:a></TD>
      	 <TD ALIGN="LEFT"><s:property value="ldrshipname" /> </TD>
      	 <TD ALIGN="LEFT"><s:property value="rtlStartDate" /> </TD>
      	 <TD ALIGN="LEFT"><s:property value="rtlEndDate" /> </TD>
     	 <TD ALIGN="LEFT"><s:property value="unusdamt" /> </TD>
     	 <TD ALIGN="LEFT"><s:property value="UnitCount" /></TD>
      	 <TD ALIGN="LEFT"><s:property value="processDate" />  </TD>
      	 <TD ALIGN="LEFT"><s:property value="status" /> </TD>
     	 <TD ALIGN="LEFT"><s:a href="javascript:fileLink(%{ldrshipid});">Edit /</s:a>
     	 				  <s:a href="javascript:fileLinkView(%{ldrshipid});">View</s:a>
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