<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="simple" scope="page" />
<html>
<head>
<title>Report Processing</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			
			<script type="text/javascript">
				function openPopup(url,fix) {
	if (fix == "nofixed") {
	wresizeable = "yes";
	} else {
	wresizeable = "no";
	}		
	popup(url,549,494,wresizeable);			 	
	}
	/* Popup Window Function */
var uniqueWin = false;
var winPopups =  new Array();
function popup(path,w,h,wresizeable,topPos,leftPos,wmenubar,wtoolbar,wlocation,wscrollbars,wstatus) {
	
	// Set variables for Screen Dimensions	
	var screenw = 800, screenh =600;
	var FOX;
	if (document.all || document.layers) {
	   screenw = screen.availWidth;
	   screenh = screen.availHeight;
	}

	// setup defaults for everything but path, w, h
	if (wresizeable != "no") wresizeable = "yes";
	// default for centering window on screen if no position values stated
	if (!topPos) topPos = (screenh-h)/2;
	if (!leftPos) leftPos = (screenw-w)/2;
	if (wmenubar != "yes") wmenubar = "no";
	if (wtoolbar != "yes") wtoolbar = "no";
	if (wlocation != "yes") wlocation = "no";
	
	if (wstatus != "yes") wstatus = "no";
	
	// Center Popup Window In Screen
	
	// Set window name
	var windowName = "newWin";
		
	
		
	if (!winPopups[windowName] || winPopups[windowName].closed==true) {
		 winPopups[windowName]  = window.open(path,windowName,"scrollbars=1,menubar=0,toolbar=0,location=0,directories=0,status=0,copyhistory=0,width= 549,height=549");
	} else {
		
		winPopups[windowName].close();
		winPopups[windowName] = window.open(path,windowName,"scrollbars=1,menubar=0,toolbar=0,location=0,directories=0,status=0,copyhistory=0,width= 549,height=549");
		newWin = winPopups[windowName];
	}
 
	
	// focus the window
	winPopups[windowName].focus();
}
 
 function fileLink(val){
// alert(1);
 //alert(val);
 document.getElementById("pId").value = val;
document.myform.action ='<%=request.getContextPath()%>' +'/viewGenReportpopup.action?procDpbID='+val;
//document.getElementById("submit").submit();
openPopup(document.myform.action,'fixed');
}

function fun(pId,procType,actDate)
{
	
	
	//document.forms[0].getElementById("role").value = prgType;
	document.getElementById("prgType").value = procType;
	document.getElementById("procId").value = pId;
	document.getElementById("actDate").value = actDate;
	//alert("prgType"+document.getElementById("prgType").value);
	//alert("pId"+document.getElementById("pId").value);
	//alert(procDpbID);
	//alert(document.forms[0].name);
	//$("#fileProcessingInp").submit();
	/* document.forms[0].action = 'fileProcessingInp';*/
	//document.getElementById('myform').action = "genReports";
	//document.getElementById('testForm').submit(); 

}
</script>
</head>
<body>

<div id="widecontentarea">

<div id="HL1"><span class="pageTitle">DPB Report Processing</span></div>
 
	<div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><div class="TX1">Report Process Details are displayed here. The manual Report process can be triggered here.</div></td>
 
				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			</tr>
			
			<!--tr>
				<td class="leftEditLinks">&nbsp;</td>
				<td class="rightEditLinks">&nbsp;</td>
			</tr>-->
			<tr>
				<td colspan="2" class="line"><img src="../../images/img_t8_line4.gif" width="729" height="1" alt="" border="0"></td>
			</tr>
		</table>
	</div>
<s:form name="myform" id="generateReport" action="genReports">
<s:hidden name="prgType" id="prgType" value="" />
<s:hidden name="procId" id="procId" value="" />
<s:hidden name="pId" id="pId" value="" />	
<s:hidden name="actDate" id="actDate" value="" />
<table width="148%" cellpadding="0" cellspacing="0" class="TBL2">
	<tr>
		<td width="1417"><table width="949"  border="0" class="placeholderTable">
		  <tr>
		    <td width="72"><strong>Process Id</strong></td>
		    <td width="185"><strong>Process</strong></td>
		    <td width="80"><strong>Process Date</strong></td>
		    <td width="90"><strong>Status</strong></td>
		    <td width="321"><strong>Details</strong></td>
		    <td width="100"><strong>Action</strong></td>
		    </tr>
		  <tr>
			<s:if test="%{genReportFileList.isEmpty()}">	
			</s:if>
			<s:else>
				  <s:iterator value="genReportFileList">
				  	<tr>
				  	
					    <td ><s:url id="processCalDef" namespace="/" action="processCalDef" ></s:url>
									<s:a href="%{processCalDef}"><s:property value="procDpbID"></s:property></s:a>&nbsp;</td>
					    <td ><s:property value="processName"></s:property>&nbsp;</td>
					    <td ><s:property value="displayDate"></s:property>&nbsp;</td>
					    <td ><s:property value="procActStat"></s:property>&nbsp;</td>
					    <td ><s:property value="processMsg"></s:property>&nbsp;</td>
					    <s:set name="pID" value="procDpbID"/>
					  	<s:set name="pType" value="prgType"/>
					  	<s:set name="actDate" value="displayDate"/>
					  	<s:if test='flag.trim().equals("V")'>
					   <td ><input type="button" name="submit"   value="View Status"  onclick="fileLink('<s:property value="procDpbID"/>')"/>&nbsp;</td>
					   </s:if>
					   <s:elseif test='flag.trim().equals("S")'>
					  	<td ><s:submit action="genReportsStartProc" theme="simple" key="submit" value="Start Process" align="left" onclick="fun('%{pID}','%{pType}','%{actDate}');"/>&nbsp;
					  		</td>
					   </s:elseif>
			      	 </tr>
			      </s:iterator>
			     
		     </s:else>
		 </tr>
	    </table></td>
	</tr>
</table>
<s:token/>
 </s:form>
	<div class="template8BottomLine"></div>
	<br />
	<br /><br />
</div>

</body>
</html>