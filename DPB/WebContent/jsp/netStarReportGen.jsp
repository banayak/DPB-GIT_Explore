<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.mbusa.dpb.business.util.TreeStructure"%>
<%@ page import="com.mbusa.dpb.common.props.PropertyManager"%>

 <s:set name="theme" value="'simple'" scope="page" />
 
 <%
 	ArrayList list = new ArrayList();
	TreeStructure fw = new TreeStructure();
	String filePath = PropertyManager.getPropertyManager().getPropertyAsString("payment.path5");
	
	list = fw.listDirectoryYear(filePath, 0);
    pageContext.setAttribute("reports", list);
%>
<html>
<head>
<title>ConditionDefination</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<script  src="<%=request.getContextPath() %>/js/netStarReportGen.js" type="text/javascript"></script>
		
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

	function fileLink(fromDate,toDate,reportDate,dealer,dynamicRpt,year,month,vehType)
			{
				if(document.getElementById("Err").value == "S"  ){
				
						var frmDate= fromDate;
						var tDate= toDate;
						var rDate=reportDate;
						var dlr=dealer;
						var dynRpt=dynamicRpt;
						var waitReq = Math.floor((Math.random()*100)+1).toString();
						var contextPath = '<%=request.getContextPath()%>';
						document.getElementById("waitReq").value = waitReq; 
						document.myform.action =contextPath+'/reportsGenerate.action?fromDate='+frmDate+'&toDate='+tDate+'&reportDate='+rDate+'&dealer='+dlr+'&waitReq='+waitReq+'&dynamicReport='+dynRpt+'&yearAct='+year+'&monthAct='+month+'&vehTypeRd='+vehType;
						//document.getElementById("netStarReportGen").submit();
						//alert(document.myform.action);
						openPopup(document.myform.action,'fixed');
					}
					document.getElementById("Err").value="";
			}
    /* Performance improvement on netstar report -Start*/	
    function activateTab(pageId) {
          var tabCtrl = document.getElementById('tabCtrl');
          var pageToActivate = document.getElementById(pageId);
          for (var i = 0; i < tabCtrl.childNodes.length; i++) {
              var node = tabCtrl.childNodes[i];
              if (node.nodeType == 1) {
                  node.style.display = (node == pageToActivate) ? 'block' : 'none';
              }
          }
      }
      

function showMonths(id){
     var value=$("#"+id).text();
	value = value.replace(/(\s|\n|\t)+/, '');
	$.ajax({  
        url : "getMonths",   
        type : "POST",   
        data: {year : value},    
        success : function(data) {  
        	data = data.substring(1,data.length-1);
   	 		var monthArray = data.split(",");
   	 		var monthUIContent = $("#monthUl"+value).html();
   	 		monthUIContent = monthUIContent.replace(/^\s+/, '');
	 		if(monthUIContent == ""){
	 			$("#monthUl"+value).html("");
	   	 		for(var i=0;i<monthArray.length;i++){
	   	 			 $("#monthUl"+value).append("<li><a href='#' onclick='showFiles(this.text,"+value+")'>"+monthArray[i].replace(/(\s|\n|\t)+/, '')+"</a><ul  id='fileList"+value.replace(/(\s|\n|\t)+/, '')+monthArray[i].replace(/(\s|\n|\t)+/, '')+"' ></ul></li>");
	   	 		}
	 		}
	 		else{
	 			$("#monthUl"+value).html("");
	 		}
        },
        error : function(xhr) {  
            alert(xhr.status);   
        }  
	}); 
	
}


function showFiles(monthValue,yearValue){
	var fPath=$("#filePath").val();
	$.ajax({  
        url : "getFiles",   
        type : "POST",   
        data: {year: yearValue, month : monthValue.replace(/(\s|\n|\t)+/, '')},    
        success : function(data) {  
        		data = data.substring(1,data.length-1);
        		if(data != ""){
        			var filesArray = data.split(",");
        			var fileUIContent = $("#fileList"+yearValue+monthValue).html();
        	 		if(fileUIContent == ""){
        	 			$("#fileList"+yearValue+monthValue).html("");
               	 		for(var i=0;i<filesArray.length;i++){
               	 			var v=fPath+yearValue+"/"+monthValue+"/"+filesArray[i];
               	 			$("#fileList"+yearValue+monthValue).append("<li><a href='#' onclick=\" saveFile('"+v+"')\" ><span>"+filesArray[i]+"</span></a></li>"); 
               	 		}
               	 		var noOfMonths = -1;
	               	 	$('ul#monthUl'+yearValue+' li').each(function () {
	               	 		noOfMonths++;
	               	 	});
               	 		for(var j=1;j<=noOfMonths;j++){
               	 			if(monthValue.substring(1) != j){
               	 				$("#fileList"+yearValue+"0"+j).html("");	
               	 			}
               	 		}
        	 		}
        	 		else{
        	 			$("#fileList"+yearValue+monthValue).html("");
        	 		}
        		}
        },
        error : function(xhr) {  
            alert(xhr.status);   
           }  
	}); 
}

function saveFile(fPath)
{
	location.href="savePDFFile?fpath="+fPath.replace(/(\s|\n|\t)+/, '');
}

/* Performance improvement on netstar report -End*/   
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
	<s:form action="netStarReportGen" method="post" name="myform">
	<s:hidden name="dynamicReport" id="rpt" />
	<s:hidden name="fromDate" id="frmDate"/>
	<s:hidden name="toDate" id="tDate"/>
	<s:hidden name="reportDate"  id="rDate" />
	<s:hidden name="dealer" id="dlr"/>	
	<s:hidden name="isErr" id="Err"  value="" />
	<s:hidden name="yearAct" id="yearAct" value=""/>
	<s:hidden name="monthAct" id="monthAct" value="" />
	<s:hidden name="vehTypeRd" id="vehTypeRd" value="" />
		<s:hidden name="reportId" id="edit"  value=""/>
		<s:hidden name="justTest" id="justTest"/>
	<s:hidden name="waitReq" id="waitReq" value="" />	
		<div id="widecontentarea">

			<div id="HL1">
				<span class="pageTitle">DPB Netstar Reports</span>
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
								<p>The netstar reports for DPB will be generated here. User
									can see the reports in the UI and have option to generate the
									netstar report file after reviewed the same.</p>
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
			
		  <input type="button" value="Reports"   onclick="javascript:activateTab('page1')"/>
		  <input type="button" value="PreviousReports" onclick="javascript:activateTab('page2')" />
		  
		
			<br />
			<div id="tabCtrl" class="row3">
      		<div id="page1" style="display: block;" class="row3">
			<span id="span12" class="serror"></span><br>
			<table width="200" border="0" class="TBL2" id="netStr">
	           	<tr id="row3" class="row3">
	         		<td width="320">NetStar Report</td>
		   		 	<td><s:select  headerKey="-1" headerValue="Select" 
	  				list ="beanList" listKey="flgDailyMonthly"  listValue="reportName" name="netStartRpt.dynamicReport"  id="dynamicRpt" /><span id="span3" class="serror"></span></td>
	  			</tr>
		 
		 		<tr id="rowVeh" class="rowVeh" >
					<td><s:label for="netStartRpt.vehicleTypeRd" value="Vehicle Type" id="lbl_vehType1"  ></s:label></td>
			<td><s:radio  name="netStartRpt.vehicleTypeRd"  list ="vehicleListRd"  listKey="id"  listValue="vehicleType" id="vehicleTypeRd"   /><span id="vehicleType" class="serror"></span> 
					</td> 
				</tr>
				
			 	<tr id="row5" class="row5">
			    	<td>Year</td>
					<td>
					<s:textfield  name="netStartRpt.year" id="year"></s:textfield>
					<!-- <s:select  name="netStartRpt.year" id="year"
									list="#{'-1':'Select','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'
		                                                }" /> --><span id="yearErr" class="serror"></span></td>
		         </tr>
         
		        <tr id="row6" class="row6">
		                                             <td>Month</td>
					<td><s:select  name="netStartRpt.month" id="month"
									list="#{'-1':'Select','1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10','11':'11','12':'12'
		                                                }" /><span id="monthErr" class="serror"></span></td>                                            
		   		</tr>  
  
		  		<tr id="row7" class="row7">
					<td colspan="2">
			    		<b>Enter Date or Date Range:</b>
			    	</td>
			    </tr>
			    
			    <tr id="row11" class="row11">
			    	<td colspan="2">
			    		<b>Date:</b>
			    	</td>
			    </tr>
			    <tr id="row9" class="row9">
			    	<td>
			    		Retail Date
			    	</td>
			    	<td>
			    		<sj:datepicker name="netStartRpt.reportDate" id="reportDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus" /><span id="rptDtErr" class="serror"></span>
			    	</td>
			    </tr>
			    <tr id="row12" class="row12">
			    	<td colspan="2">
			    		<b>Date Range:</b>
			    	</td>
			    </tr>
			    <tr id="row13" class="row13">
			    	<td>
			    		Retail From Date<br>
			    		<sj:datepicker name="netStartRpt.fromDate" id="fromDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus" id="fromDate"/><span id="span21" class="serror"></span>
			    	</td>
			    	<td>
			    		Retail To Date<br>
			    		<sj:datepicker name="netStartRpt.toDate"   id="toDate" maxDate="today"  displayFormat="mm/dd/yy" showOn="focus" id="toDate"/><span id="span22" class="serror"></span>
			    	</td>
			    </tr>
			   
			  	<tr id="row8" class="row8">
					<td>Dealer(range)</td>
					<td><textarea name="netStartRpt.dealer"  cols="45" rows="5" id="dealer"></textarea><span id="dealerErr" class="serror"></span></td>
				</tr>
	
						<s:set var="dynamicReport" value="netStartRpt.dynamicReport"/>
						<s:set var="fromDate" value="netStartRpt.fromDate"/>
					  	<s:set var="toDate" value="netStartRpt.toDate"/>
					  	<s:set var="reportDate" value="netStartRpt.reportDate"/>
					  	<s:set var="dealer" value="netStartRpt.dealer"/>
					  	<s:set var="waitReq" value="waitReq" />		
					  			
		  		 <tr id="row10" class="row10">
			  		<td>
			    		<input type="button" name="submit"   value="Generate Report"   id="genDynamicReport" />
			     		<input type="button" name="reset" id="reset" value="Reset" />
		        	</td>
		          	<td>&nbsp;</td>       
		        </tr>      
    	</table>
    	</div>
        <!--   Performance improvement on netstar report -Start    -->
          
 	
	<div id="page2" style="display: none;" >
		<!-- <h3>Simple expand/collapse block</h3> -->
			<!-- <a href="#" onclick="toggle2('content', this)">Collapse</a> -->
	<div id="content">
	</div>
			<h3>Previous Reports Links respective to the vehicle type and months:</h3>
	<ul>
    	<c:forEach items="${reports}" var="current">
			<li >
	    		<a href="#"  id="monthAnchor${current}" onclick="showMonths(this.id)" >
	    			<c:out value="${current}" />
	    		</a>
	    		<ul id="monthUl${current}" >
		    	</ul> 
	     	</li>
     	</c:forEach>
	</ul>	
	</div>
<input type="hidden" name="filePath"   value="${filePath}"   id="filePath" />
		<!--  Performance improvement on netstar report -End     -->
    	</div>
	<br/>
	</div>
</s:form>
</body>
</html>