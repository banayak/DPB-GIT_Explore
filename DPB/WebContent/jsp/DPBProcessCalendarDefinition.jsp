<!DOCTYPE HTML>
<%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title>DPBProcessCalender</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script> 
			
					 function refreshPage(){
					location.reload();
				} 
				function goTo() {   document.frm.submit(); } 
				function processDefinitionClick(processId){
					var url ='<%=request.getContextPath()%>' +'/viewProcesspopup.action?processId='+processId;
					openPopup(url,'fixed');
				}
				
				
				function openPopup(url,fix) {
                  
            	if (fix == "nofixed") {
                  wresizeable = "yes";
                  }
            	else {
            
                  wresizeable = "no";
                  }           
                  popup(url,549,549,wresizeable);                      
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
      if (wscrollbars != "no") wscrollbars = "no";
      if (wstatus != "yes") wstatus = "no";
      
      // Center Popup Window In Screen
      
      // Set window name
      var windowName = "newWin";
            
      
            
      if (!winPopups[windowName] || winPopups[windowName].closed==true) {
            winPopups[windowName]  = window.open(path,windowName,"width=" + w + ",height=" + h + ",resizable=" + wresizeable + ",top=" + topPos + ",pixelTop=" + topPos + ",left=" + leftPos + ",pixelLeft=" + leftPos + ",menubar=" + wmenubar + ",toolbar=" + wtoolbar + ",location=" + wlocation + ",scrollbars=" + wscrollbars + ",status=" + wstatus);
      } else {
            
            winPopups[windowName].close();
            winPopups[windowName] = window.open(path,windowName,"width=" + w + ",height=" + h + ",resizable=" + wresizeable + ",top=" + topPos + ",pixelTop=" + topPos + ",left=" + leftPos + ",pixelLeft=" + leftPos + ",menubar=" + wmenubar + ",toolbar=" + wtoolbar + ",location=" + wlocation + ",scrollbars=" + wscrollbars + ",status=" + wstatus);
            newWin = winPopups[windowName];
      }

      
      // focus the window
      winPopups[windowName].focus();
}
			</script> 
</head>

<body>
<s:set name="theme" value="'simple'" scope="page" />
<s:form action="processCalDef" >
<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Process Calendar</div>
 
	<div class="T8">
	  <table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The DPB application generates the tasks (process id) in the retail calendar when we create/edit any program or report or file schedule. User has facility to view and edit the same for any future dates.</div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			  </div></td>
			</tr>
			<tr><td colspan="2">	<img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
		</table>


<TABLE width="958" height="530" BORDER=3 CELLPADDING=3 CELLSPACING=3>
<TR>		
<TD COLSPAN="7" ALIGN=center>
<B>
	<s:property value = "monthYr"/>
</B></TD> 
</TR>
<TR> 
<TD COLSPAN="7" ALIGN=center><i>DPB Process Calendar</i></TD>
</TR>
	<TR> 
	<s:iterator value="weekDays" var="weekDay">
		<TD width="137" ALIGN=center> <s:property value = "weekDay"/>  </TD>
	</s:iterator>
	</TR> 
<s:iterator value="weeksInCurrMonth" var="weekCount" status="stat1">
	<TR>
		<s:iterator value="weekDays" status="stat2">
			<TD height="96" ALIGN=left valign="top">
				<Table>
				<TR>
				<TD><s:property value="retrieveDay()"/></TD> 
				</TR>
				<s:iterator value="retrieveProcessPerDay()">
				<TR>
				<TD><s:a   href="javascript:processDefinitionClick(%{processDefinitionID});"  title="%{processType}" cssStyle="text-decoration:underline;"><s:property value="processDefinitionID" /></s:a></TD> 
				</TR>
				</s:iterator>
				</Table>
			</TD>
			
		</s:iterator>
	</TR>
</s:iterator>

</TABLE>
<table>
    		<tr>
    		<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
    		</tr>
    </table>
&nbsp;&nbsp;     
	</div>
	</div>
    <div align = 'center'>
	
<br /><br /><br />
</div>
</s:form>
</body>
</html>