<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>Leadership Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<script  src="<%=request.getContextPath() %>/js/ldrshipReportGen.js" type="text/javascript"></script>
		
<sj:head jquerytheme="flick"/>
<style>
.serror {
	color: red;
	padding-left: 3px;
}
</style>

<script>
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
	/* if (wresizeable != "no") */ wresizeable = "yes";
	// default for centering window on screen if no position values stated
	if (!topPos) topPos = (screenh-h)/2;
	if (!leftPos) leftPos = (screenw-w)/2;
	if (wmenubar != "yes") wmenubar = "no";
	if (wtoolbar != "yes") wtoolbar = "no";
	if (wlocation != "yes") wlocation = "no";
	
	if (wstatus != "yes") wstatus = "no";
    if (wscrollbars != "no") wscrollbars = "yes";
      {
             w += 80; 
             h += 60;
      }
	
	// Center Popup Window In Screen
	
	// Set window name
	var windowName = "newWin";
		
	if (!winPopups[windowName] || winPopups[windowName].closed == true) {
		 winPopups[windowName]  = window.open(path,windowName,"width=" + w + ",innerwidth="+ w + ",height=" + h + ",innerHeight="+ h + ",resizable=" + wresizeable + ",top=" + topPos + ",pixelTop=" + topPos + ",left=" + leftPos + ",pixelLeft=" + leftPos + ",menubar=" + wmenubar + ",toolbar=" + wtoolbar + ",location=" + wlocation + ",scrollbars=" + wscrollbars + ",status=" + wstatus);
	} else {
		
		winPopups[windowName].close();
		winPopups[windowName] = window.open(path,windowName,"width=" + w + ",innerwidth="+ w + ",height=" + h + ",innerHeight="+ h + ",resizable=" + wresizeable + ",top=" + topPos + ",pixelTop=" + topPos + ",left=" + leftPos + ",pixelLeft=" + leftPos + ",menubar=" + wmenubar + ",toolbar=" + wtoolbar + ",location=" + wlocation + ",scrollbars=" + wscrollbars + ",status=" + wstatus);
		newWin = winPopups[windowName];
	}
 
	// focus the window
	winPopups[windowName].focus();
}

function fileLink(dealer,vehicleType)
	{
		if(document.getElementById("Err").value == "S"  ){
			var dlr=dealer;
			var waitReq = Math.floor((Math.random()*100)+1).toString();
			var contextPath = '<%=request.getContextPath()%>';
			document.getElementById("waitReq").value = waitReq; 
			document.myform.action =contextPath+'/reportsGenerate1.action?dealer='+dlr+'&waitReq='+waitReq+'&vehicleType='+vehicleType;
			openPopup(document.myform.action,'fixed');
		}
		document.getElementById("Err").value="";
		}
</script>

</head>
<body>
<s:if test="hasActionErrors()">
	<span>
		<s:actionerror />
	</span>
</s:if>
<s:elseif test="hasActionMessages()">
	<span>
		<s:actionmessage />
	</span>
</s:elseif>
	<s:form action="ldrshipReportGen" method="post" name="myform">
	<s:hidden name="dealer" id="dlr"/>	
	<s:hidden name="isErr" id="Err"  value="" />
	<s:hidden name="waitReq" id="waitReq" value="" />	
		<div id="widecontentarea">
			<div id="HL1">
				<span class="pageTitle">DPB Leadership Report</span>
			</div>

			<div class="T8">
				<table cellspacing="0" class="template8TableTop" border="0">
					<tr>
						<td colspan="2" class="line"><img
							src="<%=request.getContextPath()%>/images/img_t8_line1.gif"
							height="1" alt="" border="0">
						</td>
					</tr>
					<tr>
					<td id="ctl00_tdCopyText" class="left"><div class="TX1">
						<p>The Leadership report for DPB will be generated here. User
									can see the reports in the UI and have option to generate the
									Leadership report file after reviewing the same.</p>
					</div>
					</td>

					<td class="right"><img id="ctl00_imgLevel2"
						src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg"
						style="border-width: 0px;" /><br>
					</td>
					</tr>
					<tr><td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
				</table>
			</div>
			<br />
			<span id="span12" class="serror"></span><br>
			<table width="200" border="0" class="TBL2" id="netStr">
		 
		 	<tr id="rowVeh" class="rowVeh" >
				<td>Vehicle Type</td>
				<td><s:radio list="{'Car','Van','Freightliner','Smart'}"  name="vehicleType" value="'Car'"/>
			<span id="vehicleType" class="serror"></span></td>
			</tr>
			 <tr id="row5" class="row5">
			    <td>Year</td>
				<td><s:select  name="year" id="year" list="#{'2014':'2014'}" /> 
				<span id="yearErr" class="serror"></span></td>
		     </tr>
         
			<tr id="row8" class="row8">
				<td>Dealer(range)</td>
				<td><textarea name="dealer"  cols="45" rows="5" id="dealer"></textarea>
				<span id="dealerErr" class="serror"></span></td>
			</tr>
		  	<tr id="row10" class="row10">
			<td>
			    <input type="button" name="submit"    value="Generate Report"   id = "ldrshipReport" />
			    <input type="button" name="reset" id="reset" value="Reset" />
		    </td>
		    <td>&nbsp;</td>       
		</tr>      
    	</table>
	<br/>
	</div>
</s:form>
</body>
</html>
