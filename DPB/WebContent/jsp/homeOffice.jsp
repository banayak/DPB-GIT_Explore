<!DOCTYPE HTML>
<%@page import="java.util.Calendar"%>
<%@page import="com.mbusa.dpb.common.constant.IConstants"%>
<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!-- Vehicle Details Report-start -->
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<!-- Vehicle Details Report-end -->
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>Home Office Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.12.0.min.js"></script>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery.dataTables.min.css"/>
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/buttons.dataTables.min.css"/>
			<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/displaytag.css">
			<script  src="<%=request.getContextPath() %>/js/homeofficeGen.js" type="text/javascript"></script>
		
<sj:head jquerytheme="flick"/>

<style>
.serror, .serrorHeader {
	color: red;
	padding-left: 3px;
}
<!-- Vehicle Details Report-start -->
.displayTable
{
width: 957px;
height: 50px;
border: 1px solid black;
font-size: 18px

}
.displayTable td,.displayTable th
{
border: 1px solid black;
}

.pagelinks
{
font-size: 15px
}

.spinner {
	position: absolute;
	left: 480px;
	top: 400;
	z-index: 9999;
	padding-top: 200px;
}
.LockOff {
     display: none;
     visibility: hidden;
  }

  .LockOn {
  	 display: block;
     position: absolute;
     z-index: -1;
     top: 0px;
     left: 0px;
     width: 100%;
     height: 110%;
     background-color: #ccc;
     text-align: center;
     padding-top: 8%;
     filter: alpha(opacity=75);
     opacity: 0.75;
  }
  .disabledDivArea {
    pointer-events: none;
    opacity: 0.4;
    background-color: #ccc;
    z-index: 9999;
}

.hidden {
 display: none;
}

<!-- Vehicle Details Report-end -->
</style>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.dataTables.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/buttons.flash.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/buttons.html5.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/dataTables.buttons.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/jszip.min.js" ></script>

<script type="text/javascript">
var screenLockFlag = true;
//Dealer Compliance Summary Report - start
var dlrCompSummId=<%=IConstants.DLR_COMPL_SUM_REPORT_ID%>;
//Dealer Compliance Summary Report - end
 $(window).load(function() {
	$(".spinner").fadeOut("slow");
	
}); 

$( document ).ready(function() {	
	
	$("table#tableamg").DataTable( {
		"bSort": false,
		"bAutoWidth": false,
		dom: 'Bltip',
		buttons: [
            {
                extend: "excel",
                filename: 'AMG Performance Bonus Report',
                exportOptions: {
                    orthogonal: 'sort',
                },
                text: "Export to Excel"
            }
        ]        
		
	});
	$("a").click(function(){
		showPrgessBar(true);
	});
	$(".paginate_button").click(function(){
		disableLockScreen();
	});
	$(".dt-button").click(function(){
		disableLockScreen();
	});
	$(".center-screen").click(function(){
		disableLockScreen();
	});
	$( "#exportExcel" ).click(function() {
		if(fnValidateExcel()){
			showPrgessBar();
		     $('#message-text').text('Export to Excel Report Generation is in Progress, Please wait...');
		     $('#message').show();
			document.forms[0].action ='exportToExcel';
			window.addEventListener('focus', HideDownloadMessage, false);
			document.forms[0].submit();
		}
	});
	$( "#exportExcelFloorPlan" ).click(function() {
		showPrgessBar();
	    $('#message-text').text('Export to Excel Report Generation is in Progress, Please wait...');
	    $('#message').show();
		document.forms[0].action ='exportToExcel';
		window.addEventListener('focus', HideDownloadMessage, false);
		document.forms[0].submit();
	});
	$( "#exportBlockedToExcel" ).click(function() {
		showPrgessBar();
	    $('#message-text').text('Export to Excel Report Generation is in Progress, Please wait...');
	    $('#message').show();
		document.forms[0].action ='exportBlockedToExcel';
		window.addEventListener('focus', HideDownloadMessage, false);
		document.forms[0].submit();
	});
	
	$('#homeOfficeReports').submit(function() {
		return showPrgessBar(true);
	  });
	function showPrgessBar(flag){
		if(screenLockFlag){
		    var progessDiv = document.getElementById("progressBar");
		    progessDiv.innerHTML = '<img src="<%=request.getContextPath() %>/img/processing.gif" class="spinner"/>';
		    progessDiv.style.display = '';
		    if(flag)
		    	skm_LockScreen();
		    else
		    	$("#headerarea").addClass("disabledDivArea");
		    return true;
		}
	}
	$('.tabDisable').on('keydown', function(e){ 
	  if (e.keyCode == 9)  
	  {
	    e.preventDefault();
	  }
	});
});
function disableLockScreen(){
	 $("#progressBar").hide();
	 $("#toBlockUI").removeClass("LockOn");
	 $("#widecontentarea").removeClass("disabledDivArea");
	 $("#headerarea").removeClass("disabledDivArea");
}
function skm_LockScreen()
{
   $("#toBlockUI").addClass("LockOn");
   $("#widecontentarea").addClass("disabledDivArea");
   $("#headerarea").addClass("disabledDivArea");
}

function HideDownloadMessage(){
    window.removeEventListener('focus', HideDownloadMessage, false);       
    disableLockScreen();
    $('#message').hide();
}
  
function trimText(){
var regex = new RegExp(' ', 'g');
document.getElementById("year").value=(document.getElementById("year").value).replace(regex,'');
document.getElementById("dealerQuarterYear").value=(document.getElementById("dealerQuarterYear").value).replace(regex,'');
document.getElementById("vehicleQuarterYear").value=(document.getElementById("vehicleQuarterYear").value).replace(regex,'');
document.getElementById("dealer").value=(document.getElementById("dealer").value).replace(regex,'');
document.getElementById("vehicleRange").value =(document.getElementById("vehicleRange").value).replace(regex,'');
document.getElementById("poNum").value =(document.getElementById("poNum").value).replace(regex,'');
}


</script>
</head>
<body>

<div id="progressBar" style="display: none;">
    <img src="<%=request.getContextPath() %>/img/processing.gif" class="spinner"/>
</div>
<div id="message" style="display: none">
    <div id="message-screen-mask" class="ui-widget-overlay ui-front"></div>
    <div id="message-text" class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-draggable ui-resizable waitmessage">please wait...</div>
</div>

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

<s:form action="homeOfficeReports"  name="myform">
<s:hidden name="draftrptFlag" value="%{draftrptFlag}" id="draftrptFlag"/>
<s:hidden name="draftrptSmartFlag" value="%{draftrptSmartFlag}" id="draftrptSmartFlag"/>
<div id="toBlockUI">
<div id="widecontentarea" class="tabDisable">
<div id="HL1">
				<span class="pageTitle">Home Office Reports</span>
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
								<p>The Home Office Reports for DPB will be generated here. User
									can see the reports in the UI.</p>
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
			<s:if test="showResultMsg">
				<s:if test="reportGenerationInProcess">
					<span id="spanrec" class="serrorHeader">Report generation In Process , So please try after some time.</span><br>
				</s:if>
				<s:elseif test="reportGenerated">
					<span id="spanrec" class="serrorHeader">Report generated successfully.</span><br>
				</s:elseif>
				<s:else>
					<span id="spanrec" class="serrorHeader">No Record Present.</span><br>
				</s:else>
			</s:if>
			
			<table width="200" border="0" class="TBL2" id="homeOfc">
			<tr id="row2" class="row2">
		  		<td width="320">Home Office Report</td>
		  		<!--Unearned Performance Bonus calculation start: added report id 14 -->
				<!-- Dealer Compliance Quarterly Payouts Report-Old Start -->
					<td><s:select name="netStartRpt.rptId" id="staticReport"
							 list="#{'-1':'Select','1':'CVP Program Payout Report (Bonus Year 2016)','2':'MBDEAL Payout Report','3':'Dealer Compliance Quarterly Payout Report (Bonus Year 2016)',
							 '17':'Dealer Compliance Summary Report (Bonus Year 2016)', '14':'Dealer Performance Unearned Bonus Report (Bonus Year 2016)',
							 '6':'Retail Exceptions Report','16':'Fixed Margin Summary Report',
							 '9':'Blocked Vehicles Report','12':'AMG Performance Bonus Report', 
							 '10':'VIN Lookup Report'}"	/>
							 
					<%-- 	list="#{'-1':'Select','1':'Courtesy Vehicle Program Payout Report','2':'MBDEAL Payout Report','3':'Dealer Compliance Quarterly Payouts Report',
							 '15':'Dealer Compliance Quarterly Payouts Report Old','4':'Dealer Compliance Summary Report', '14':'Dealer Performance Unearned Bonus Report',
							 '5':'Dealer Performance Unearned Bonus Report Old','6':'Floor Plan Exceptions Report','16':'Floor Plan / Dealer Reserve Report',
							 '7':'Dealer Reserve Summary Report','8':'Floor Plan Summary Report',
							 '9':'Blocked Vehicles Report','11':'Vehicle Details Report', '12':'AMG Performance Bonus Report', '13':'AMG Elite Performance Bonus Report', 
							 '10':'VIN Lookup Report'}" --%>			 
	                   <span
						id="span2" class="serror"></span></td>
						<!-- Dealer Compliance Quarterly Payouts Report-Old End -->
	               <!-- Unearned Performance Bonus calculation start end: added report id 14 -->                       
            </tr>  
            
            <tr id="row4" class="row4">
			    <td><s:label for="vehicleType" value="Vehicle Type" id="lbl_vehType"  ></s:label></td>
			    <td><s:checkboxlist  name="netStartRpt.vehicleType" theme="vertical-checkbox" list ="vehicleList"  listKey="id"  listValue="vehicleType" id="vehicleType" /><span id="vehicleType" class="serror"></span></td>
			
			</tr> 
			
			<tr id="row18" class="row18" >
					<td><s:label for="netStartRpt.vehicleTypeRd" value="Vehicle Type" id="lbl_vehType1"  ></s:label></td>
					<td><s:radio  name="netStartRpt.vehicleTypeRd"  list ="vehicleListRd"  listKey="id"  listValue="vehicleType" id="vehicleTypeRd"   /><span id="vehicleType" class="serror"></span>
					</td> 
			</tr>
			
			<tr id="row21" class="row21" >
					<td><s:label for="netStartRpt.vehTypeRadio" value="Vehicle Type" id="lbl_vehType2"  ></s:label></td>
					<td><s:radio  name="netStartRpt.vehTypeRadio"  list ="vehListRd"  listKey="id"  listValue="vehicleType" id="vehicleTypeRd"   /><span id="vehicleType" class="serror"></span>
					</td> 
			</tr>
			
	 		<tr id="row5" class="row5">
	    	<td>Year</td>
			<td><s:textfield  name="netStartRpt.year" id="year"  onblur='trimText();'></s:textfield><span id="yearErr" class="serror" onClick='trimText();'></span></td>
                                         
         	</tr>
        	<tr id="row6" class="row6">
                                             <td>Month</td>
			<td><s:select  name="netStartRpt.month" id="month"
							list="#{'-1':'Select','1':'January','2':'February','3':'March','4':'April','5':'May','6':'June','7':'July','8':'August','9':'September','10':'October','11':'November','12':'December'
                                                }" /><span id="monthErr" class="serror"></span></td>
             
              
                                                
	   		</tr>
	   		<tr id="row8" class="row8">
	   		<td><s:label for="netStartRpt.dealer" value="Dealer(s)" id="lbl_dealer"  ></s:label></td>
			<td><s:textarea name="netStartRpt.dealer"  cols="45" rows="5" id="dealer"  onblur='trimText();'></s:textarea><span id="dealerErr" class="serror"></span></td>
	
		</tr>
		<tr id="row9" class="row9">
			<td>Report Date</td>
			<td><sj:datepicker name="netStartRpt.reportDate" id="reportDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus" /><span id="rptDtErr" class="serror"></span></td>
			
		</tr>
		
		<tr id="row10" class="row10" >
	    <td >Dealer Id <br></td>
	    <td >VIN(s) (comma separated values)<br></td>
      </tr>
	  <tr id="row11" class="row11">
	    <td><s:textfield name="netStartRpt.dealerId"  id="dealerId" /><span id="span11" class="serror"></span></td>
	    <td><s:textarea name="netStartRpt.vehicleId"  cols="45" rows="5" id="vehicleId"/><span id="span9" class="serror"></span></td>
      </tr>
      
       <tr id="row16" class="row16" >
      	<td ><s:radio  name="netStartRpt.viewAccountVin" list="#{'VIN':'View Account Balance by VIN'}"  id="VIN-DealerLevel_0" />
			</td>
			<td></td>
		</tr>
      
	  <tr id="row12" class="row12" >
	    <td>
	    	From Date
      	</td>
	    <td>
	    	To Date
	    </td>
      </tr >
      
      
      
	  <tr id="row13" class="row13">
	    <td><sj:datepicker name="netStartRpt.fromDate" id="fromDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus"  /><span id="span21" class="serror"></span></td>
	    <td><sj:datepicker name="netStartRpt.toDate"   id="toDate" maxDate="today"  displayFormat="mm/dd/yy" showOn="focus" /><span id="span22" class="serror"></span></td>
      </tr>
      
             <!-- Dealer Performance Unearned Bonus Report changes start -->
      
	  <tr id="row22" class="row22">
	  <!-- From year To year persist issue - start -->
	    <td>From Year&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <s:select name="netStartRpt.fromYear" id="fromYear"
							list="%{yearList}" style="width:80px;"/>
    		<%-- <select name="netStartRpt.fromYear" id="fromYear" style="width:80px;">
            <option value="-1"> Select </option>
            <%
                                   
              int tYear = Calendar.getInstance().get(Calendar.YEAR);
              int j=0;
              while (j<7)
              {
              %>
              <option value="<%=tYear-j%>"><%=tYear-j%></option><%
              j++; }
              %>
          	</select> --%>
          	<!-- From year To year persist issue - end -->
         </td>
	    		    	
	    <td>
	    To Year&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    <!-- From year To year persist issue - start -->
	     <s:select name="netStartRpt.toYear" id="toYear"
							list="%{yearList}" style="width:80px;"/>
	   		 	    	
	    	<%-- <select name="netStartRpt.toYear" id="toYear" style="width:80px;">
            <option value="-1"> Select </option>
            <%
             
              int fYear = Calendar.getInstance().get(Calendar.YEAR);
              int i=0;
              while (i<7)
              {
              %>
              <option value="<%=fYear-i%>"><%=fYear-i%></option><%
              i++; }
              %>
          </select> --%>
          <!-- From year To year persist issue - end -->
          </td>	    										
	    <span id="yearErr" class="serror"></span></td>
      </tr >
     
      <tr id="row23" class="row23">
          <td>From Month&nbsp;&nbsp;&nbsp;
			    <s:select  name="netStartRpt.fromMonth" id="fromMonth" style="width:80px;" 
							list="#{'-1':'Select','01':'January','02':'February','03':'March','04':'April','05':'May','06':'June','07':'July','08':'August','09':'September','10':'October','11':'November','12':'December'
                                                }" /><span id="monthErr" class="serror"></span></td>
                                                
          <td>To Month&nbsp;&nbsp;&nbsp;   
			    <s:select  name="netStartRpt.toMonth" id="toMonth" style="width:80px;" 
							list="#{'-1':'Select','01':'January','02':'February','03':'March','04':'April','05':'May','06':'June','07':'July','08':'August','09':'September','10':'October','11':'November','12':'December'
                                                }" /><span id="monthErr" class="serror"></span></td>
                                            
	  </tr>
	   		
	  <tr id="row24" class="row24">
             <td>From Quarter&nbsp;
			    <s:select  name="netStartRpt.fromQuarter" id="fromQuarter" style="width:80px;" 
							list="#{'-1':'Select','1':'Q1','2':'Q2','3':'Q3','4':'Q4'}" /><span id="quarterErr" class="serror"></span></td>
                                                
              <td>To Quarter&nbsp;
			    <s:select  name="netStartRpt.toQuarter" id="toQuarter" style="width:80px;" 
							list="#{'-1':'Select','1':'Q1','2':'Q2','3':'Q3','4':'Q4'}" /><span id="quarterErr" class="serror"></span></td>
                                     
	  </tr>	
      <!-- Dealer Performance Unearned Bonus Report changes end -->
      
      
      <tr id="row14" class="row14" >
	    <td><s:radio name="netStartRpt.viewAccountVin" list="#{'D':'View Eligible Dealers by Quarter'}"  id="VIN-DealerLevel_1"/>
		</td>
	    <td><s:select name="netStartRpt.viewDealerLevelQuarter" id="viewDealerLevelQuarter"
							list="#{'-1':'Select','1':'First Quarter','2':'Second Quarter','3':'Third Quarter','4':'Fourth Quarter'
                                                }" /><span id="dlrQtrId" class="serror"></span>
		Year : <s:textfield  name="netStartRpt.dealerQuarterYear" id="dealerQuarterYear" value=""  onblur='trimText();'></s:textfield><span id="yearErr" class="serror"></span></td>
      </tr>
	  <tr id="row15" class="row15" >
	    <td><s:radio  name="netStartRpt.viewAccountVin" list="#{'V':'View Total Eligible Vehicles by Quarter'}"  id="VIN-DealerLevel_2"/>
		 </td>
	    <td>
	      <s:select name="netStartRpt.viewTotVehQuarter" id="viewTotVehQuarter"
							list="#{'-1':'Select','1':'First Quarter','2':'Second Quarter','3':'Third Quarter','4':'Fourth Quarter'}" /><span id="totElgQtrId" class="serror"></span>
		  Year : <s:textfield  name="netStartRpt.vehicleQuarterYear" id="vehicleQuarterYear" value=""  onblur='trimText();'></s:textfield><span id="yearErr" class="serror"></span></td>
      </tr>

      <!-- Alekhya Changes start  -->
      <tr id="row20" class="row20">
			<td>VIN(s) (comma/space separated values)</td>
			<td>PO(s) (comma/space separated values)</td>
	  <tr id="row19" class="row19">
			 <td><s:textarea name="netStartRpt.vehicleRange"  cols="45" rows="5" id="vehicleRange" onblur='trimText();'/><span id="spanVINText" class="serror"></span></td>
			<td><s:textarea name="netStartRpt.poNumber"  cols="45" rows="5" id="poNum" onblur='trimText();'></s:textarea><span id="spanPoTxt" class="serror"></span></td>
	  </tr>

      <!--  Alekhya Changes end -->

		<!--  Vehicle Details Report - Start -->
				<tr id="row25" class="row25">
					<td><s:label for="netStartRpt.dataTypeRadio" value="Data Type"
							id="dataType"></s:label></td>
					<td><s:radio name="netStartRpt.dataTypeRadio"
							list="#{'All':'All','Unblocked':'Unblocked','Blocked':'Blocked'}"
							id="dataTypeRd" /></td>
				</tr>
				<tr id="rowPageSizeList" class="rowPageSizeList" style="display:none;">
					<td>Page Count</td>
					<td><s:select name="pageSize" id="pageSizeList"
							list="%{pageSizeList}" /><span
						id="span2" class="serror"></span></td>
				</tr>
				<!--  Vehicle Details Report - End -->
				
		<!-- AMG Report - Start -->		
		<tr id="row26" class="row26">
					<td><s:label for="netStartRpt.amgProgramRadio" value="Program Type"
							id="pgmType"></s:label></td>
					<td><s:radio name="netStartRpt.amgProgramRadio"
							list="#{'1':'All','2':'Elite','3':'Base'}"
							id="amgProgramRd" /></td>
		</tr>
		<!-- AMG Report - End -->	
			
		<tr id="row17" class="row17" >
		  <td>
		    <s:submit key="submit" value="Generate Report" method="genStaticReport"  id="genReptStaticRP" onclick="blockUIFun();"/> 
		     <input type="button" name="reset" id="reset" value="Reset" />
		     
	        <td id="exportId">
	        <s:submit type="button" key="submit"  value="Export to Excel"   action = "exportToExcel" id = "exportExcel" onclick="return false;"/>
	        </td>
	       <!-- <td>&nbsp;</td> -->
	      </tr>
	  
	      
	    </table>
		
<div id="display">
	
	<%-- <s:if test="%{list.size() == 0 }">
			<tr>
				<td colspan="7"><span>No Record Present.</span></td>
			</tr>
		</s:if> --%>
		<s:if test="reportGenerated">
		
		<!-- Courtesy Vehicle Report Start-->
		
			 <s:if test="netStartRpt.rptId == 1">
                                    <h1 align="center">Courtesy Vehicle Program Payout Report</h1>
                                    <s:url var="actionUrl" value="homeOfficeReports.action"> </s:url>
                                    <div style="overflow:scroll;  width:954px; height:350px;">
                                    <display:table class="displayTable" name="cvpList" style="width:970px;"
                                          sort="external" defaultsort="1" pagesize="${pageSize}"
                                          id="cvpReport" partialList="true" size="${resultSize}"
                                          requestURI="${actionUrl}">
                                       <display:setProperty name="paging.banner.placement" value="bottom" />
									   <display:column property="poNum" sortName="poNum"
                                                title="PO #" />
                                          <display:column property="modelYr" sortName="modelYr" title="MY" />
                                          <display:column property="model" sortName="model" title="Model" />
                                          <display:column property="serial" sortName="serial"
                                                title="Serial" />
                                          <display:column property="dealer" sortName="dealer"
                                                title="Dealer" />
                                          <display:column property="rtlDate" sortName="rtlDate"
                                                title="Retail Date" />
                                                
                                         
                                            
                                           <s:if test="netStartRpt.vehicleType.contains('P')">
                                             <display:column property="flrPlan" sortName="flrPlan"
                                                title="Floor Plan" />
                                           	   <display:column property="custSls" sortName="custSls"
                                                title="Cex Sales" />
	                                          <display:column property="custSvc"
	                                                sortName="custSvc" title="Cex Service" />
                                               <display:column property="preOwned" sortName="preOwned"
                                                title="PO Sales" />
                                                 <display:column property="nvSalBns" sortName="nvSalBns"
                                                title="NV Sales" />
                                           </s:if>
                                           
                                            <s:if test="netStartRpt.vehicleType.contains('V')">
                                           		 <display:column property="flrPlan" sortName="flrPlan"
                                                title="Dealer Reserve" />
                                                 <display:column property="custExp" sortName="custExp"
                                                title="Cust.Exp" />
                                                  <display:column property="nvSalBns" sortName="nvSalBns"
                                                title="NV Sales" />                                           
                                           </s:if>
                                           
                                             <s:if test="netStartRpt.vehicleType.contains('F')">
                                           		 <display:column property="flrPlan" sortName="flrPlan"
                                                title="Dealer Reserve" />
                                                 <display:column property="custExp" sortName="custExp"
                                                title="Cust.Exp" />
                                                  <display:column property="nvSalBns" sortName="nvSalBns"
                                                title="NV Sales" />                                           
                                           </s:if> 
                                           
                                            <s:if test="netStartRpt.vehicleType.contains('S')">
                                           		  <display:column property="flrPlan" sortName="flrPlan"
                                                title="Dealer Reserve" />                                   
                                           </s:if> 
                                      
                                         
                                          <display:column property="brdStd" sortName="brdStd"
                                                title="Brand Std" />
                                          <display:column property="total" sortName="total"
                                                title="Total" />
                                    </display:table>
                                    </div>

            </s:if>            
            <!-- Courtesy Vehicle Report End-->
            
            <!-- Exception Report Start-->
		
			 <s:if test="netStartRpt.rptId == 6">
                                    <h1 align="center">Retail Exception Report</h1>
                                    <s:url var="actionUrl" value="homeOfficeReports.action"> </s:url>
                                    <div style="overflow:scroll;  width:954px; height:350px;">
                                    <display:table class="displayTable" name="expRptList" style="width:970px;"
                                          sort="external" defaultsort="1" pagesize="${pageSize}"
                                          id="exceptionReport" partialList="true" size="${resultSize}"
                                          requestURI="${actionUrl}">
                                     <display:setProperty name="paging.banner.placement" value="bottom"/>
                                          <display:column property="rtlDate" sortName="rtlDate"
                                                title="RETAIL DATE" />
                                          <display:column property="modelYr" sortName="modelYr" title="MY" />
                                          <display:column property="model" sortName="model" title="MODEL" />
                                          <display:column property="serial" sortName="serial"
                                                title="SERIAL" />
                                          <display:column property="poNum" sortName="poNum"
                                                title="PO NUMBER" />
                                          <display:column property="dealer" sortName="dealer"
                                                title="DEALER" />
                                          <display:column property="region" sortName="region"
                                                title="REGION" />                                      
                                          <display:column property="carCount" sortName="carCount"
                                                title="CAR COUNT" />
                                          <display:column property="reason"
                                                sortName="reason" title="REASON" />                                         
                                    </display:table>
                                    </div>
            </s:if>            
            <!-- Exception Report End-->
            
            <!-- MBDeal Report Start-->
		
			 <s:if test="netStartRpt.rptId == 2">
                                    <h1 align="center">MB Deal Payout Report</h1>
                                    <s:url var="actionUrl" value="homeOfficeReports.action"> </s:url>
                                    <div style="overflow:scroll; width:954px; height:350px;">
                                    <display:table class="displayTable" name="mbDealList" style="width:970px;"
                                          sort="external" defaultsort="1" pagesize="${pageSize}"
                                          id="mbDealReport" partialList="true" size="${resultSize}"
                                          requestURI="${actionUrl}">
                                           <display:setProperty name="paging.banner.placement" value="bottom" />										
                                          <display:column property="poNum" sortName="poNum"
                                                title="PO #" />
                                          <display:column property="cntl" sortName="cntl" title="CNTL #" />
                                          <display:column property="modelYear" sortName="modelYear" title="MY" />
                                          <display:column property="model" sortName="model"
                                                title="Model" />
                                          <display:column property="serial" sortName="serial"
                                                title="Serial" />
                                          <display:column property="dealer" sortName="dealer"
                                                title="Dealer" />
                                          <display:column property="rtlDate" sortName="rtlDate"
                                                title="Retail Date" />
                                          <display:column property="commission" sortName="commission"
                                                title="Commission" />
                                                
                                          <s:if test="netStartRpt.vehicleType.contains('P')">
                                             <display:column property="floorPlan" sortName="floorPlan"
                                                title="Floor Plan" />
                                           </s:if>
                                           <s:else>
                                               <display:column property="dlrRes" sortName="dlrRes"
	                                          		title="Dealer Reserve"/>
                                           </s:else>                         
                                          <display:column property="total" sortName="total"
                                                title="Total" />
                                    </display:table>
                                    </div>

            </s:if>
            
            <!-- MBDeal Report End-->
			
			<!-- Dealer Compliance Quarterly Payouts Report Start-->
			<s:elseif test="netStartRpt.rptId == 3">
			
			<center><div class="pageTitle" id="HL1">
					<!-- Effective Payouts Report - All Retails Paid Bonus -->
					${qtr}	Quarter ${yr} Dealer Payout Report
				</div>
				</center>
			<%-- 	<s:if test="netStartRpt.vehicleTypeRd.indexOf('P') != -1"> --%>
				<!--For Passenger  -->
									<s:if test="%{draftrptFlag == true}">
				
				<%-- Report Period : 	${qtr}	quarter ${yr}
				<center>
								<table height="50" border="1" style="width: 950px;">
									<tr>
										<s:if test="%{draftrptFlag == true}">
											<th COLSPAN=7 BGCOLOR="#999999">Effective Payout Summary</th>
										</s:if>
										<s:else>
											<th COLSPAN=4 BGCOLOR="#999999">Effective Payout Summary</th>
										</s:else>
									</tr>
									<tr>
										<td width="25%">${dsName}</td>
										<td width="25%"></td>
										<s:if test="%{draftrptFlag == true}">
											<td width="20%">${custExpSalesName}</td>
											<td width="20%">${custExpServiceName}</td>
											<td width="20%">${poName}</td>
										</s:if>
										<td width="20%">${seName}</td>
										<td width="20%">${bsName}</td>
										<td width="15%">Total Eff. Payout</td>
									</tr>
									<tr>
										<td>${dlrStandAvg}</td>
										<td></td>
										<s:if test="%{draftrptFlag == true}">
											<td>${custExpSales}</td>
											<td>${custExpService}</td>
											<td>${preOwnAvg}</td>
										</s:if>
										<td>${salesEffAvg}</td>
										<td>${mbcsiAvg}</td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<s:if test="%{draftrptFlag == true}">
											<td></td>
											<td></td>
											<td></td>
										</s:if>
										<td></td>
										<td>* Fixed:</td>
										<s:if test="%{draftrptFlag == true}">
											<td>8.00%</td>
										</s:if>
										<s:else>
											<td>10.00%</td>
										</s:else>
									</tr>
									<tr>
										<td width="25%">Total MSRP Avg:</td>
										<td>${msrp}</td>
										<s:if test="%{draftrptFlag == true}">
											<td></td>
											<td></td>
											<td></td>
										</s:if>
										<td>Variable:</td>
										<td>${sumVarAvg}%</td>
									</tr>
									<tr>
										<td></td>
										<s:if test="%{draftrptFlag == true}">
											<td></td>
										</s:if>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td>${varTotal}%</td>
									</tr>
								</table>
							</center>
							<br />

							<s:if test="%{draftrptFlag == true}">
								<p align="right">
									<b>* assumes full 7% trade margin and 1% floor plan</b>
								</p>
							</s:if>
							<s:else>
								<p align="right">
									<b>* assumes full 7% trade margin and 3% dealer reserve</b>
								</p>
							</s:else> --%>
							<div style="overflow: scroll; width: 954px; height: 350px;">
								<table width="957" height="90" border="1">
									<tr bgcolor="#999999" style="text-align: center;">
										<th rowspan="2" width="8%">Dealer</th>
										<th rowspan="2" width="5%">Region</th>
										<th rowspan="2" width="5%">Market</th>
										<th rowspan="2" width="30%">City</th>
										<th rowspan="2" width="5%">State</th>
										<th rowspan="2" width="5%">Retail Sales (units)</th>

										<s:if test="%{draftrptFlag == true}">
											<%--  <s:if test="%{custExpflag == false}"> 
											<td width="10%">${dsName}</td>
											 </s:if> --%>
											<td width="10%">${custExpSalesName}</td>
											<td width="10%">${custExpServiceName}</td>
											<td width="10%">${poName}</td>
											<td width="10%">${seName}</td>
											<td width="10%">${bsName}</td>
											<th rowspan="2" width="10%">Effective Payout*</th>
											 <%-- <s:if test="%{custExpflag == false}"> 
											<td colspan="8">Performance Bonus Payout $</td>
											 </s:if>  --%>
											<td colspan="7">Performance Bonus Payout $</td>
										</s:if>
										<s:else>
										<%-- <s:if test="%{custExpflag == false}"> 
											<td width="15%">${dsName}</td>
											 </s:if> --%> 
											<td width="15%">${seName}</td>
											<td width="15%">${bsName}</td>
											<th rowspan="2" width="15%">Effective Payout*</th>
											<%--  <s:if test="%{custExpflag == false}"> 
											<td colspan="4">Performance Bonus Payout $</td>
											 </s:if>  --%>
											
											<td colspan="3">Performance Bonus Payout $</td>
											
										</s:else>
									</tr>
									<tr bgcolor="#999999" style="text-align: center;">
										<s:if test="%{draftrptFlag == true}">
										<%-- <s:if test="%{custExpflag == false}"> 
											<td>${perc1}% Total Potential</td>
											</s:if> --%>
											<td>${perc5}% Total Potential</td>
											<td>${perc6}% Total Potential</td>
											<td>${perc2}% Total Potential</td>
											<td>${perc3}% Total Potential</td>
											<td>${perc4}% Total Potential</td>
											<%-- <s:if test="%{custExpflag == false}">
											<td>${dsName}</td>
											 </s:if> --%> 
											<td>${custExpSalesName}</td>
											<td>${custExpServiceName}</td>
											<td>${poName}</td>
											<td>${seName}</td>
											<td>${bsName}</td>
											<td>Total Payout</td>
											<td>Total Unearned</td>
										</s:if>
										<s:else>
											<%-- <s:if test="%{custExpflag == false}"> 
											<td>${perc1}% Total Potential</td>
											</s:if> --%>
											<td>${perc2}% Total Potential</td>
											<td>${perc3}% Total Potential</td>
											<%--  <s:if test="%{custExpflag == false}"> 
											<td>${dsName}</td>
											 </s:if>  --%>
											<td>${seName}</td>
											<td>${bsName}</td>
										</s:else>
									</tr>
									<s:iterator status="stat" var="temp" value="list">
										<s:iterator var="test" value="temp">
											<tr>
												<s:iterator status="countVar" var="first" value="test">
													<s:if test="%{draftrptFlag == true}">
														<s:if test="#countVar.index == 21">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 22">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 23">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 24">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 25">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 26">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="%{custExpflag == false}"> 
														<s:if test="#countVar.index == 27">
															<td><s:property value="first"></s:property>
															</td>
														</s:if>
														 </s:if> 
														<s:if test="#countVar.index == 28">
															<td><s:property value="first"></s:property>
															</td>
														</s:if>
														<s:if test="#countVar.index == 29">
															<td><s:property value="first"></s:property>
															</td>
														</s:if>
														<s:if test="#countVar.index == 30">
															<td><s:property value="first"></s:property>
															</td>
														</s:if>
														<s:if test="#countVar.index == 31">
															<td><s:property value="first"></s:property>
															</td>
														</s:if>
														<s:if test="#countVar.index == 32">
															<td><s:property value="first"></s:property>
															</td>
														</s:if>
													<%-- 	<s:if test="#countVar.index == 33">
															<td><s:property value="first"></s:property>
															</td>
														</s:if> --%>
														<!-- CUST EXP CHANGES - START(ADDED CONDITION) -->
														<s:if test="%{custExpflag == false}"> 
														<s:if test="#countVar.index == 34">
															<!-- <td style="border: none;">&nbsp;</td> -->
															<td><s:property value="first"></s:property></td>
														</s:if>
														</s:if>
														<s:else> 
														<s:if test="#countVar.index == 33">
															<!-- <td style="border: none;">&nbsp;</td> -->
															<td><s:property value="first"></s:property></td>
														</s:if>
														</s:else>
														<!-- CUST EXP CHANGES - END -->
														 <s:if test="%{custExpflag == false}">  
														<s:if test="#countVar.index == 35">
															<td><s:property value="first"></s:property></td> 
														 </s:if>
														</s:if>
														<s:if test="#countVar.index == 36">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 37">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 38">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 39">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<s:if test="#countVar.index == 40">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<!-- CUST EXP CHANGES - START(ADDED CONDITION) -->
														<s:if test="%{custExpflag == false}"> 
														<s:if test="#countVar.index == 42">
															<!-- <td style="border: none;">&nbsp;</td> -->
															<td><s:property value="first"></s:property></td>
														</s:if>
														</s:if>
														<s:else> 
														<s:if test="#countVar.index == 41">
															<!-- <td style="border: none;">&nbsp;</td> -->
															<td><s:property value="first"></s:property></td>
														</s:if>
														</s:else>
														<!-- CUST EXP CHANGES - END -->
														<!-- CUST EXP CHANGES - START -->
														<s:if test="#countVar.index == 43">
															<td><s:property value="first"></s:property></td>
														</s:if>
														<!-- CUST EXP CHANGES - END -->

													</s:if>
												</s:iterator>
											</tr>
										</s:iterator>
									</s:iterator>
									<s:if test="%{draftrptFlag == true}">
										<tr>
											<td colspan="5"></td>
											<td>${cy13Total}</td>
											<%--  <s:if test="%{custExpflag == false}"> 
											<td colspan="7"></td>
											  </s:if>  --%>
											<td colspan="6"></td>
											<%--  <s:if test="%{custExpflag == false}"> 
											<td>$${dsTotal}</td>
											 </s:if>  --%>
											<td>$${custExpSalesTotal}</td>
											<td>$${custExpServiceTotal}</td>
											<td>$${preTotal}</td>
											<td>$${slsTotal}</td>
											<td>$${mbTotal}</td>
											<td>${totalPayout}</td>
											<td>$${UnEarnedTotal}</td>
										</tr>
									</s:if>

								</table>
							</div>
						</s:if>
						<!-- For Smart  -->
						<s:elseif test="%{draftrptSmartFlag == true}">
						
				
				<%-- Report Period : 	${qtr}	quarter ${yr}
				
							<table style="width:950px; height="50" border="1">
								<tr>
							<s:if test="%{draftrptSmartFlag == true}">
										<th COLSPAN=5 BGCOLOR="#999999">Effective Payout Summary</th>
									</s:if>
									<s:else>
										<th COLSPAN=4 BGCOLOR="#999999">Effective Payout Summary</th>
									</s:else>
								</tr>
								<tr>
									<td width="20%">${dsName}</td>
									<td width="20%">${seName}</td>
									<td width="20%">${bsName}</td>
									
									<td width="20%">${mbCSINewName}</td>
									
									<td width="20%">Total Eff. Payout</td>
								</tr>
								<tr>
									<td>${dlrStandAvg}</td>
									<td>${salesEffAvg}</td>
									<td>${mbcsiAvg}</td>
									<td width="20%">${mbCSINewAvg}</td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td>* Fixed:</td>
									<s:if test="%{draftrptFlag == true}">
										<td>8.00%</td>
									</s:if>
									<s:if test="{draftrptSmartFlag == true}">
										<td>10.00%</td>
									</s:if>
									<s:else>
										<td>10.00%</td>
									</s:else>
								</tr>
								<tr>
									<td width="25%">Total MSRP Avg:</td>
									<td>${msrp}</td>
									<s:if test="{draftrptSmartFlag == true}">
										<td></td>
									</s:if>
									<td>Variable:</td>
									<td>${sumVarAvg}%</td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<s:if test="{draftrptSmartFlag == true}">
										<td></td>
									</s:if>
									<td>${varTotal}%</td>
								</tr>
							</table>
						<br />
						
			 		<s:if test="%{draftrptFlag == true}">
							<p align="right">
								<b>* assumes full 7% trade margin and 1% floor plan</b>
							</p>
						</s:if>
						<s:if test="%{draftrptSmartFlag == true}">
							<p align="right">
								<b>* assumes full 7% trade margin and 3% dealer reserve</b>
							</p>
						</s:if>
						<s:else>
							<p align="right">
								<b>* assumes full 7% trade margin and 3% dealer reserve</b>
							</p>
						</s:else> --%>
				<div style=" overflow:scroll; width:954px; height:350px;">						
				<table width="957" height="90" border="1"> 
							<tr bgcolor="#999999" style="text-align: center;">
						      	<th rowspan="2" width="8%">Dealer</th>
						      	<th rowspan="2" width="5%">Region</th>
						      	<th rowspan="2" width="5%">Market</th>						      	
						      	<th rowspan="2" width="30%">City</th>
						      	<th rowspan="2" width="5%">State</th>
						      	<th rowspan="2" width="5%">Retail Sales (units)</th>
							      	
							      	 <%-- <s:if test="%{draftrptFlag == true}">
							      	<td width="10%">${dsName}</td>
							      	<td width="10%">${poName}</td>
							      	<td width="10%">${seName}</td>
							      	<td width="10%">${bsName}</td>
							      	<th rowspan="2" width="10%">Effective Payout*</th>
							      	<td colspan="4" >Performance Bonus Payout $</td>
							      	</s:if> --%>
							      	<%-- <s:else> --%>
							      <%-- 	<td width="15%">${dsName}</td>
							      	<td width="15%">${seName}</td>
							      	<td width="15%">${bsName}</td> --%>
							      	<s:if test="%{draftrptSmartFlag == true}">
							      		<td width ="15%">${mbCSINewName}
							      	</s:if>
							      	<th rowspan="2" width="15%">Effective Payout*</th>
							      	<s:if test="%{draftrptSmartFlag == true}">
							      	<td colspan="3" >Performance Bonus Payout $</td>
							      	</s:if>
							      	
					      	</tr>
					      	
					     	<tr bgcolor="#999999" style="text-align: center;">
							      	<s:if test="%{draftrptSmartFlag == true}">
							      		<%-- <td>${perc1} Total Potential</td>
										<td>${perc2} Total Potential</td>							     
							      		<td>${perc3} Total Potential</td> --%>
							      		<td>${perc4} Total Potential</td> 
							      		<%-- <td>${dsName}</td>
							      		<td>${seName}</td>
							      		<td>${bsName}</td> --%>
							      		<td>${mbCSINewName}</td>
							      		<td>Total Payout</td>
							      		<td>UnEarned Amount</td>
							      	
							      	</s:if>
							      <%-- 	<s:else>
							      	<td>${perc1} Total Potential</td>							     
							      	<td>${perc2} Total Potential</td>
							      	<td>${perc3} Total Potential</td> 
							      	<td>${dsName}</td>
							      	<td>${seName}</td>
							      	<td>${bsName}</td>
							      	</s:else>
					      	</tr>				   --%>     	
					      	<s:iterator status="stat" var="temp" value="list">
					      	<s:iterator var="test" value="temp">
					      	<tr>
					      	<s:iterator status="countVar" var="first" value="test">
					      		<%-- <s:if test="%{draftrptSmartFlag == true}"> --%>
					      			<s:if test="#countVar.index == 15">
										<td><s:property value="first"></s:property></td>
									</s:if>
					      			<s:if test="#countVar.index == 16">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 17">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 18">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 19">
										<td><s:property value="first"></s:property></td>
									</s:if>
								 	<s:if test="#countVar.index == 20">
										<td><s:property value="first"></s:property></td>
									</s:if>
								<%--	<s:if test="#countVar.index == 21">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 22">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 23">
										<td><s:property value="first"></s:property></td>
									</s:if>--%>
									 <s:if test="#countVar.index == 24">
										<td><s:property value="first"></s:property></td>
									</s:if>  
								    <s:if test="#countVar.index == 25">
										<td><s:property value="first"></s:property></td>
									</s:if>
									 <%--<s:if test="#countVar.index == 26">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 27">
										<td><s:property value="first"></s:property></td>
									</s:if> 
									<s:if test="#countVar.index == 28">
										<td><s:property value="first"></s:property></td>
									</s:if>--%>
									<s:if test="#countVar.index == 29">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 30">
									
										<td><s:property value="first"></s:property></td>
										<!-- <td></td> -->
									</s:if>
									<s:if test="#countVar.index == 31">
									
										<td><s:property value="first"></s:property></td>
									</s:if>
								<%-- </s:if> --%>
					      	
					      	</s:iterator>
					      	
					      	</tr>
					      	</s:iterator>
					      	</s:iterator>
							<tr>
								<td colspan="5"></td>
								<td>${cy13Total}</td>
								<td colspan="2"></td>
								<!-- <td>$${dsTotal}</td>
								<td>$${slsTotal}</td>
								<td>$${mbTotal}</td> -->
								<td>$${mbCSINewTotal}</td>
								<td>${totalPayout}</td>
								<td>$${unEarnedTotal}</td>
							</tr> 
				</table>
				</div>
						</s:elseif>
						<s:else>
						<!--  for pagination -->
					<%-- 	Report Period : 	${qtr}	quarter ${yr}
				<center>
							<table style=" width:950px; height="50" border="1">
								<tr>
									<s:if test="%{draftrptFlag == true}">
										<th COLSPAN=7 BGCOLOR="#999999">Effective Payout Summary</th>
									</s:if>
									<s:else>
										<th COLSPAN=4 BGCOLOR="#999999">Effective Payout Summary</th>
									</s:else>
								</tr>
								
								
								<tr>
									<td width="25%">${dsName}</td>
									<s:if test="%{draftrptFlag == true}">
										<td width="20%">${custExpSalesName}</td>
										<td width="20%">${custExpServiceName}</td>
										<td width="20%">${poName}</td>
									</s:if>
									<td width="20%">${seName}</td>
									<td width="20%">${bsName}</td>
									<td width="15%">Total Eff. Payout</td>
								</tr>
								<tr>
									<td>${dlrStandAvg}</td>
									<s:if test="%{draftrptFlag == true}">
										<td>${custExpSales}</td>
										<td>${custExpService}</td>
										<td>${preOwnAvg}%</td>
									</s:if>
									<td>${salesEffAvg}</td>
									<td>${mbcsiAvg}</td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<s:if test="%{draftrptFlag == true}">
										<td></td>
										<td></td>
										<td></td>
									</s:if>
									<td></td>
									<td>* Fixed:</td>
									<s:if test="%{draftrptFlag == true}">
										<td>8.00%</td>
									</s:if>
									<s:else>
										<td>10.00%</td>
									</s:else>
								</tr>
								<tr>
									<td width="25%">Total MSRP Avg:</td>
									<td>${msrp}</td>
									<s:if test="%{draftrptFlag == true}">
										<td></td>
										<td></td>
										<td></td>
									</s:if>
									<td>Variable:</td>
									<td>${sumVarAvg}%</td>
								</tr>
								<tr>
									<td></td>
									
										<td></td>
										<td></td>
									
									<td>${varTotal}%</td>
								</tr>
							</table>
						</center>
						<br />
						
			 		<s:if test="%{draftrptFlag == true}">
							<p align="right">
								<b>* assumes full 7% trade margin and 1% floor plan</b>
							</p>
						</s:if>
						<s:else>
							<p align="right">
								<b>* assumes full 7% trade margin and 3% dealer reserve</b>
							</p>
						</s:else> --%>
				 <div style=" overflow:scroll; width:954px; height:350px;">
					<table width="957" height="90" border="1"> 
							<tr bgcolor="#999999" style="text-align: center;">
						      	<th rowspan="2" width="8%">Dealer</th>
						      	<th rowspan="2" width="5%">Region</th>
						      	<th rowspan="2" width="5%">Market</th>						      	
						      	<th rowspan="2" width="30%">City</th>
						      	<th rowspan="2" width="5%">State</th>
						      	<th rowspan="2" width="5%">Retail Sales (units)</th>
							      	
							      	<s:if test="%{draftrptFlag == true}">
							      	<td width="10%">${dsName}</td>
							      	<td width="10%">${poName}</td>
							      	<td width="10%">${seName}</td>
							      	<td width="10%">${bsName}</td>
							      	<th rowspan="2" width="10%">Effective Payout*</th>
							      	<td colspan="4" >Performance Bonus Payout $</td>
							      	</s:if>
							      	<s:else>
							      	<td width="15%">${dsName}</td>
							      	<td width="15%">${seName}</td>
							      	<td width="15%">${bsName}</td>
							      	<th rowspan="2" width="15%">Effective Payout*</th>
							      	<td colspan="3" >Performance Bonus Payout $</td>
							      	<th rowspan="2" width="15%">Total Payout</th>
							      	<th rowspan ="2" width="15%">UnEarned Amount</th>
							      	</s:else>
					      	</tr>
					      	
					     	<tr bgcolor="#999999" style="text-align: center;">
					      		 <s:if test="%{draftrptFlag == true}">
								 	<td>${perc1} Total Potential</td>
									<td>${perc2} Total Potential</td>							     
							      	<td>${perc3} Total Potential</td>
							      	<td>${perc4} Total Potential</td> 
							      	<td>${dsName}</td>
							      	<td>${poName}</td>
							      	<td>${seName}</td>
							      	<td>${bsName}</td>
							      	</s:if>
							      	<s:else>
							      	<td>${perc1} Total Potential</td>							     
							      	<td>${perc2} Total Potential</td>
							      	<td>${perc3} Total Potential</td> 
							      	<td>${dsName}</td>
							      	<td>${seName}</td>
							      	<td>${bsName}</td>
							      	</s:else>
					      	</tr>				       	
					      	<s:iterator status="stat" var="temp" value="list">
					      	<s:iterator var="test" value="temp">
					      	<tr>
					      	<s:iterator status="countVar" var="first" value="test">
					      	<s:if test="#countVar.index == 12">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="#countVar.index == 13">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="#countVar.index == 14">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="#countVar.index == 15">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="#countVar.index == 16">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="#countVar.index == 17">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="#countVar.index == 18">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="#countVar.index == 19">
									<td><s:property value="first"></s:property></td>
								</s:if>
								<s:if test="%{draftrptFlag == false}">
									 <s:if test="#countVar.index == 20">
										<td><s:property value="first"></s:property></td>
									</s:if>
									 <s:if test="#countVar.index == 21">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 22">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 23">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 24">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 25">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 26">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<%-- <td>${totalPayout}</td> --%>
								</s:if>
								<s:if test="%{draftrptFlag == true}">
									<s:if test="#countVar.index == 32">
										<td><s:property value="first"></s:property></td>
									</s:if>
								    <s:if test="#countVar.index == 22">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 21">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 33">
									<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 23">
										<td><s:property value="first"></s:property></td>
									</s:if>
									<s:if test="#countVar.index == 24">
										<td><s:property value="first"></s:property></td>
									</s:if>
								</s:if>
								
					      	</s:iterator>
					      	</tr>
					      	</s:iterator>
					      	</s:iterator>
							<tr>
								<td colspan="5"></td>
								<td>${cy13Total}</td>														
								<td colspan="4"></td>
								<td>$${dsTotal}</td>
								<td>$${slsTotal}</td>
								<td>$${mbTotal}</td>
								<td>${totalPayout}</td>
								<td>$${unEarnedTotal}</td>
								
							</tr> 
				</table>
						</div>
			</s:else>
			</s:elseif>
			<!-- Dealer Compliance Quarterly Payouts Report End-->
			<!-- Dealer Compliance Quarterly Payouts Report-Old Start-->
			<s:elseif test="netStartRpt.rptId == 15">
				<center><div class="pageTitle" id="HL1">Effective Payouts Report - All Retails Paid Bonus
				</div></center>
				<s:iterator status="stat" var="temp" value="list">
					<s:if test="#stat.index == 0">
					  	<s:iterator var="test" value="temp">
					  		<s:iterator status="countVar" var="first" value="test">
					  			<s:if test="#countVar.index == 0">
									<s:set name="dlrStandAvg" value="first" scope="page"></s:set>
								</s:if>
								<s:elseif test="#countVar.index == 1">
									<s:set name="preOwnAvg" value="first" scope="page"></s:set>
								</s:elseif>
								<s:elseif test="#countVar.index == 2">
									<s:set name="salesEffAvg" value="first" scope="page"></s:set>
								</s:elseif>
								<s:elseif test="#countVar.index == 3">
									<s:set name="mbcsiAvg" value="first" scope="page"></s:set>
								</s:elseif>
								<s:elseif test="#countVar.index == 4">
									<s:set name="sumVarAvg" value="first" scope="page"></s:set>
								</s:elseif>
								<s:elseif test="#countVar.index == 5">
									<s:set name="varTotal" value="first" scope="page"></s:set>
								</s:elseif>
								<s:elseif test="#countVar.index == 6">
									<s:set name="msrp" value="first" scope="page"></s:set>
								</s:elseif>
							</s:iterator>
						</s:iterator>
						
					</s:if>
					<s:elseif test="#stat.index == 1">
						
					    	<s:iterator var="test" value="temp">
					      		<s:iterator status="countVar" var="first" value="test">
							  			<s:if test="#countVar.index == 0">
							  				<s:set name="DS_NAME" value="first" scope="page"></s:set>
							  			</s:if>
							  			<s:elseif test="#countVar.index == 1">
							  				<s:set name="PO_NAME" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 2">
							  				<s:set name="SE_NAME" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 3">
							  				<s:set name="BS_NAME" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:if test="#countVar.index == 4">
							  				<s:set name="perc1" value="first" scope="page"></s:set>
							  			</s:if>
							  			<s:elseif test="#countVar.index == 5">
							  				<s:set name="perc2" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 6">
							  				<s:set name="perc3" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 7">
							  				<s:set name="perc4" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 8">
							  				<s:set name="qtr" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 9">
							  				<s:set name="yr" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:else>
											<td><s:property value="first1"></s:property>&nbsp;</td>
										</s:else>
							  	</s:iterator>  			
							  </s:iterator> 			       	
					      </s:elseif>
					      <s:elseif test="#stat.index == 2">	
					      Report Period : 	${qtr}	quarter ${yr}		      
					      		<center>
									<table width="400" height="50" border="1">
										<tr>
											<s:if test="%{draftrptFlag == true}"> 
											<th COLSPAN=5 BGCOLOR="#999999">Effective Payout Summary</th> 
											</s:if>
											<s:else>
											<th COLSPAN=4 BGCOLOR="#999999">Effective Payout Summary</th> 
											</s:else>
										</tr>
										<tr> 
											<td width="25%">${DS_NAME}</td>
											<s:if test="%{draftrptFlag == true}">
											<td width="20%">${PO_NAME}</td>											
											</s:if>
											<td width="20%">${SE_NAME}</td>
											<td width="20%">${BS_NAME}</td>
											<td width="15%">Total Eff. Payout</td>
										</tr>
										<tr>
											 <td>${dlrStandAvg}%</td>
											 <s:if test="%{draftrptFlag == true}">
											 <td>${preOwnAvg}%</td>
											 </s:if>
											 <td>${salesEffAvg}%</td>
											 <td>${mbcsiAvg}%</td>
											 <td></td>
										 </tr>
										<tr> 
											<td></td>
											 <s:if test="%{draftrptFlag == true}">
											<td></td>
											</s:if>
											<td></td>
											<td>* Fixed:</td>
											<s:if test="%{draftrptFlag == true}"> 
											<td>8.00%</td>
											</s:if>
											<s:else> 
											<td>10.00%</td>
											</s:else>
										</tr>
										<tr> 
											<td width="25%">Total MSRP Avg:</td>
											<td>${msrp}</td>
											 <s:if test="%{draftrptFlag == true}">
											<td></td>
											</s:if>
											<td>Variable:</td>
											<td>${sumVarAvg}%</td>
										</tr>
										<tr> 
											<td></td>
											 <s:if test="%{draftrptFlag == true}">
											<td></td>
											</s:if>
											<td></td><td></td>
											<td>${varTotal}%</td>
										</tr>
									 </table>
								 </center>
								 <br/>
								  <s:if test="%{draftrptFlag == true}">
								 	<p align="right"><b>* assumes full 7% trade margin and 1% floor plan</b></p>
								 </s:if>
								 <s:else>
								 	<p align="right"><b>* assumes full 7% trade margin and 3% dealer reserve</b></p>
								 </s:else>
								 <!-- SECTION 2 -->
								 <table width="957" height="90" border="1">
							<tr bgcolor="#999999">
						      	<th rowspan="2" width="8%">dlr</th>
						      	<th rowspan="2" width="5%">rgn</th>
						      	<th rowspan="2" width="5%">mkt</th>						      	
						      	<th rowspan="2" width="30%">City Name</th>
						      	<th rowspan="2" width="5%">St</th>
						      	<th rowspan="2" width="5%">Retail Sales (units)</th>
						      	<center>
							      	
							      	<s:if test="%{draftrptFlag == true}">
							      	<td width="10%">${DS_NAME}</td>
							      	<td width="10%">${PO_NAME}</td>
							      	<td width="10%">${SE_NAME}</td>
							      	<td width="10%">${BS_NAME}</td>
							      	<th rowspan="2" width="10%">Effective Payout*</th>
							      	<td colspan="4" >Performance Bonus Payout $</td>
							      	</s:if>
							      	<s:else>
							      	<td width="15%">${DS_NAME}</td>
							      	<td width="15%">${SE_NAME}</td>
							      	<td width="15%">${BS_NAME}</td>
							      	<th rowspan="2" width="15%">Effective Payout*</th>
							      	<td colspan="4" >Performance Bonus Payout $</td>
							      	</s:else>
							    </center>
					      	</tr>
					      		<tr bgcolor="#999999">
					      		<center>
					      		 <s:if test="%{draftrptFlag == true}">
								 	<td>${perc1} Total Potential</td>
									<td>${perc2} Total Potential</td>							     
							      	<td>${perc3} Total Potential</td>
							      	<td>${perc4} Total Potential</td> 
							      	<td>${DS_NAME}</td>
							      	<td>${PO_NAME}</td>
							      	<td>${SE_NAME}</td>
							      	<td>${BS_NAME}</td>
							      	</s:if>
							      	<s:else>
							      	<td>${perc1} Total Potential</td>							     
							      	<td>${perc3} Total Potential</td>
							      	<td>${perc4} Total Potential</td> 
							      	<td>${DS_NAME}</td>
							      	<td>${SE_NAME}</td>
							      	<td>${BS_NAME}</td>
							      	</s:else>
					      		</center>
					      	</tr>				      
					      
					      	<s:iterator var="test" value="temp">
							  	<tr>
							  		<s:iterator status="countVar" var="first" value="test">
							  			<s:if test="#countVar.index == 15">
							  				<s:set name="cy13Total" value="first" scope="page"></s:set>
							  			</s:if>
							  			<s:elseif test="#countVar.index == 16">
							  				<s:set name="dsTotal" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 17">
							  				<s:set name="preTotal" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 18">
							  				<s:set name="slsTotal" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			<s:elseif test="#countVar.index == 19">
							  				<s:set name="mbTotal" value="first" scope="page"></s:set>
							  			</s:elseif>
							  			 <s:elseif test="%{draftrptFlag == true}">
											<td><s:property value="first"></s:property>&nbsp;</td>
											</s:elseif>
											<s:elseif test="%{#countVar.index != 7 && #countVar.index != 12}">
							  					<td><s:property value="first"></s:property>&nbsp;</td>
							  			</s:elseif> 
									</s:iterator>								
								</tr>
							</s:iterator>								
							<tr>
								<td colspan="5"></td>
								<td>${cy13Total}</td>								
								<s:if test="%{draftrptFlag == true}">
								<td colspan="5"></td>
								<td>${dsTotal}</td>
								<td>${preTotal}</td>
								</s:if>
								<s:else>
								<td colspan="4"></td>
								<td>${dsTotal}</td>
								</s:else>
								<td>${slsTotal}</td>
								<td>${mbTotal}</td>								
							</tr> 
						</table> 
					</s:elseif>
				</s:iterator>
			</s:elseif>
			<!-- Dealer Compliance Quarterly Payouts Report-Old End-->
			<s:elseif test="netStartRpt.rptId == 4">
                        <s:if test="%{list.isEmpty() }">
                              <tr>
                                    <td colspan="7"><span>No record present.</span></td>
                              </tr>
                        </s:if>
                        <s:else>
                              <center>
                                    <div class="pageTitle" id="HL1">
                                          Dealer Compliance Summary Report<br>
                                          Company Code <s:if test="netStartRpt.vehicleTypeRd.contains('P')">1336</s:if>
                                                            <s:elseif test="netStartRpt.vehicleTypeRd.contains('S')">3309</s:elseif>
                                                            <s:elseif test="netStartRpt.vehicleTypeRd.contains('V')">1343</s:elseif>
                                                            <s:else>0869</s:else>
                                    </div>
                              </center>
                              <s:set var="tempMnt" scope="page">temp</s:set>
                              <table width="957" height="90" border="1">
                                    <tr>
                                          <th bgcolor="#999999" colspan="<s:if test="netStartRpt.vehicleTypeRd.contains('P')">10</s:if><s:else>7</s:else>"><s:property value="first"/></th>
                                    </tr>
                                    <tr>
                                        <th bgcolor="#999999" width="90"></th>
                                        <th bgcolor="#999999" width="90">Units</th>
                                        <th bgcolor="#999999" width="90">Cust Exp Bns</th>
                                        <th bgcolor="#999999" width="90">NV Sales Bns</th>
                                        <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
                                        <th bgcolor="#999999" width="90">PO Sales Bns</th>
                                        </s:if>
                                        <s:if test="netStartRpt.vehicleTypeRd.contains('V') or netStartRpt.vehicleTypeRd.contains('F') or netStartRpt.vehicleTypeRd.contains('P')">
                                        <th bgcolor="#999999" width="90">Brand Stds Bonus</th>
                                        </s:if>
                                        <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
                                        <th bgcolor="#999999" width="90">Facility Bonus</th>
                                        <th bgcolor="#999999" width="90">CVP Bonus</th>
                                        </s:if>
                                        <s:if test="netStartRpt.vehicleTypeRd.contains('S')">
                                        <th bgcolor="#999999" width="90">Smart Franchise</th>
                                        </s:if>
                                        <th bgcolor="#999999" width="90">Total</th>
                                        <th bgcolor="#999999" width="90">Unearned Bonus</th>
                                  </tr>
                                    <s:iterator var="temp" value="list">
                                          <s:iterator status="stat"  var="test" value="temp">
                                                <tr>
                                                <s:iterator status="varCount" var="first" value="test">
                                                      <s:if test="#varCount.index > 0">
                                                            <s:if test="#varCount.index == 1">
                                                                  <s:if test="#tempMnt.equals(#first)">
                                                                  </s:if>
                                                                  <s:else>
                                                                        <td colspan="<s:if test="netStartRpt.vehicleTypeRd.contains('P')">10</s:if><s:else>7</s:else>"><s:property value="first"/>
                                                                        </td></tr><tr>
                                                                        <s:set var="tempMnt" value="first" />
                                                                  </s:else>
                                                            </s:if>
                                                            <s:elseif test="#varCount.index == 2">
                                                                  <td bgcolor="#999999" align="left">
                                                                        <s:property value="first"/>
                                                                  </td>
                                                            </s:elseif>
                                                            <s:elseif test="netStartRpt.vehicleTypeRd.contains('P')">
                                                                  <td align="right">
                                                                        <s:property value="first"/>
                                                                  </td>
                                                            </s:elseif>
                                                            <s:elseif test="#varCount.index == 6 or #varCount.index == 8 or #varCount.index == 9"></s:elseif>
                                                            <s:else>
                                                                  <td align="right">
                                                                        <s:property value="first"/>
                                                                  </td>
                                                            </s:else>
                                                      </s:if>
                                                </s:iterator>
                                                </tr>
                                          </s:iterator>
                                    </s:iterator>
                                   <s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" /> 
                              </table>
                        </s:else>
                  </s:elseif>
                  <!-- Dealer compliance summary report - start -->
                  <s:elseif test="netStartRpt.rptId == 17">
                        <s:if test="%{monthlyDCSRList.isEmpty() && qtrlyDCSRList.isEmpty()}">
                              <tr>
                                    <td colspan="7"><span>No record present.</span></td>
                              </tr>
                        </s:if>
                        <s:else>
                              <center>
                                    <div class="pageTitle" id="HL1">
                                          Dealer Compliance Summary Report<br>
                                         <%--  Company Code <s:if test="netStartRpt.vehicleTypeRd.contains('P')">1336</s:if>
                                                            <s:elseif test="netStartRpt.vehicleTypeRd.contains('S')">3309</s:elseif>
                                                            <s:elseif test="netStartRpt.vehicleTypeRd.contains('V')">1343</s:elseif>
                                                            <s:else>0869</s:else> --%>
                                    </div>
                              </center>
                              <s:set var="tempMnt" scope="page">temp</s:set>
                              <table width="957" height="90" border="1">
                              <!-- kshitija - start here -->
                                    <%-- <tr>
                                          <th bgcolor="#999999" colspan="<s:if test="netStartRpt.vehicleTypeRd.contains('P')">9</s:if><s:else>7</s:else>"><s:property value="first"/></th>
                                    </tr> --%>
                                    
                                    <tr>
                                        <th bgcolor="#999999" width="90"></th>
                                        <th bgcolor="#999999" width="90">Units</th>
                                          <!-- Added by Sneh Start-->
                                         <s:if test="!netStartRpt.vehicleTypeRd.contains('P') && !netStartRpt.vehicleTypeRd.contains('S') ">
                                         	<th bgcolor="#999999" width="90">Cust Exp Bns</th> 
                                         </s:if>
                                       <!-- Added by Sneh End-->
                                        <!-- Ratna Start -->
                                        <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
                                        <th bgcolor="#999999" width="90">Cex Sales</th>
                                        <th bgcolor="#999999" width="90">Cex Service</th>
                                        </s:if>
                                        <!-- Ratna End -->
                                        <!-- Added by Sneh Start-->
                                         <s:if test="!netStartRpt.vehicleTypeRd.contains('S')">
                                        <th bgcolor="#999999" width="90">NV Sales</th>
                                        </s:if>
                                        <!-- Added by Sneh End-->
                                        <!-- Ratna Start -->
                                        <s:if test="netStartRpt.vehicleTypeRd.contains('S')">
                                        <!-- <th bgcolor="#999999" width="90">Smart Fran</th> -->
                                        </s:if>
                                        <!-- Ratna End -->
                                        <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
                                        <th bgcolor="#999999" width="90">PO Sales</th>
                                        </s:if>
                                        <!-- Ratna Start -->
                                        <%-- <s:if test="netStartRpt.vehicleTypeRd.contains('V') or netStartRpt.vehicleTypeRd.contains('F') or netStartRpt.vehicleTypeRd.contains('P')"> --%>
                                        <th bgcolor="#999999" width="90">Brand Std</th>
                                        <%-- </s:if> --%>
                                        <!-- check this -->
                                        <%-- <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
                                        <th bgcolor="#999999" width="90">Facility Bonus</th>
                                        <th bgcolor="#999999" width="90">CVP Bonus</th>
                                        </s:if> --%>
                                        <%-- <s:if test="netStartRpt.vehicleTypeRd.contains('S')">
                                        <th bgcolor="#999999" width="90">Smart Franchise</th>
                                        </s:if>
                                        <th bgcolor="#999999" width="90">Total</th> --%>
                                        <th bgcolor="#999999" width="90">Total Earned</th> 
                                        <th bgcolor="#999999" width="90">Total Unearned</th>
                                  </tr>
                                   <s:if test="%{!monthlyDCSRList.isEmpty()}">
                                 <tr><td colspan="10">${monthName}</td></tr>
                                 </s:if>
                                  <s:iterator value="monthlyDCSRList">
                                  <tr>
                                  <td nowrap="nowrap"><s:property value="bonusType"/></td>
    							  <td><s:property value="poCount"/></td>
    							  <s:if test="!netStartRpt.vehicleTypeRd.contains('P') && !netStartRpt.vehicleTypeRd.contains('S') ">
    							  	<td><s:property value="custExp"/></td>
    							  </s:if>
    							 
    							  <!-- Ratna Start -->
                                  <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
    							  <td><s:property value="custExpSales"/></td>
    							  <td><s:property value="custExpService"/></td>
    							  </s:if>
                                  <!-- Ratna End -->
                                  <!-- Added by Sneh -->
                                  <s:if test="!netStartRpt.vehicleTypeRd.contains('S')">
    							  <td><s:property value="nvSales"/></td>
    							  </s:if>
    							  <!-- Ratna Start -->
                                  <s:if test="netStartRpt.vehicleTypeRd.contains('S')">
                                 <%--  <td><s:property value="smFran"/></td> --%>
                                  </s:if>
                                  <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
    							  <td><s:property value="preOwned"/></td>
    							  </s:if>
                                  <!-- Ratna End -->
    							  <td><s:property value="brdStd"/></td>
    							  <td><s:property value="total"/></td>
    							  <td><s:property value="unearnedBonus"/></td>
    							  </tr>
								  </s:iterator>
								  <s:if test="%{!qtrlyDCSRList.isEmpty()}">
								  <tr><td colspan="10">${qtrName}</td></tr>
								  </s:if>
								  <s:iterator value="qtrlyDCSRList">
                                  <tr>
                                  <td><s:property value="bonusType"/></td>
    							  <td><s:property value="poCount"/></td>
    							  <s:if test="!netStartRpt.vehicleTypeRd.contains('P') && !netStartRpt.vehicleTypeRd.contains('S')">
    							  	<td><s:property value="custExp"/></td>
    							  </s:if>
    							 
    							  <!-- Ratna Start -->
                                  <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
    							  <td><s:property value="custExpSales"/></td>
    							  <td><s:property value="custExpService"/></td>
    							  </s:if>
                                  <!-- Ratna End -->
                                  <s:if test="!netStartRpt.vehicleTypeRd.contains('S')">
    							  <td><s:property value="nvSales"/></td>
    							  </s:if>
    							  <!-- Ratna Start -->
    							  <s:if test="netStartRpt.vehicleTypeRd.contains('S')">
    							  <%-- <td><s:property value="smFran"/></td> --%>
    							  </s:if>
                                  <s:if test="netStartRpt.vehicleTypeRd.contains('P')">
    							  <td><s:property value="preOwned"/></td>
    							  </s:if>
                                  <!-- Ratna End -->
    							  <td><s:property value="brdStd"/></td>
    							  <td><s:property value="total"/></td>
    							  <td><s:property value="unearnedBonus"/></td>
    							  </tr>
								  </s:iterator>
                                          
                                                
                                   <%-- <s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />  --%>
                              </table>
                        </s:else>
                  </s:elseif>
                   <!-- Dealer compliance summary report - end -->
			<s:elseif test="netStartRpt.rptId == 5">				
				<center><div class="pageTitle" id="HL1">Dealer Performance Unearned Bonus Report-DRAFT
				</div></center>
						<table id = "t1"  width="957" height="50" border="1" >
							<tr>
								<td nowrap BGCOLOR="#999999"><center><strong>Retail Date</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer No</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>PO Number</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Model</strong></center></td>
								<s:if test="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('P')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>PO Sales</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn PO Sls</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>NV Sales</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn NV Sls</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Floor Plan</strong></center></td>
								</s:if>
								<s:if test ="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('S')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cus Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Smart Fran</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Smt Frn</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Sales Bonus</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Sls Bns</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer Reserve</strong></center></td>
								</s:if>								
								<s:if test="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('V')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cus Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>CV Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn CV BStd</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Sales Bonus</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Sls Bns</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer Reserve</strong></center></td>
								</s:if> 								
								<s:if test ="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('F')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cus Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>CV Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn CV BStd</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Sales Bonus</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Sls Bns</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer Reserve</strong></center></td>
								</s:if>
							</tr>
							
							 <s:iterator status="stat" var="temp" value="list" >
							  	<s:iterator var="test" value="temp">
								  	<tr>
								  		<s:iterator status="firstCount" var="first" value="test">
								  		<s:if test="#firstCount.index < 4">
											<td><s:property value="first"></s:property></td>
										</s:if>
										<s:else>
											<s:if test ="netStartRpt.vehicleTypeRd.isEmpty() or 
												(netStartRpt.vehicleTypeRd.contains('P') and #firstCount.index > 3 and #firstCount.index < 14)
												or (netStartRpt.vehicleTypeRd.contains('S') and #firstCount.index > 13 and #firstCount.index < 22)
												or (netStartRpt.vehicleTypeRd.contains('V') and #firstCount.index > 21 and #firstCount.index < 30)
												or (netStartRpt.vehicleTypeRd.contains('F') and #firstCount.index > 29 and #firstCount.index < 38)">
													<td><s:property value="first"></s:property></td>
											</s:if>
										</s:else>
										</s:iterator>
									</tr>
								</s:iterator>
							</s:iterator> 
							<s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />
							</table>
							
							<br/>
			</s:elseif>
			<!-- Unearned Performance Bonus calculation start -->
			<s:elseif test="netStartRpt.rptId == 14">				
				<center><div class="pageTitle" id="HL1">Dealer Performance Unearned Bonus Report
				</div></center>
				<!-- Dealer Performance Unearned Bonus Report - FNC27 -Start -->
				<s:url var="actionUrl" value="homeOfficeReports.action">
				</s:url>
				<div style=" overflow:scroll; width:954px; height:350px;">
				<display:table class="displayTable" name="unearnedList"
				sort="external" defaultsort="1" pagesize="${pageSize}"
				id="unearned" partialList="true" size="${resultSize}"
				requestURI="${actionUrl}">
				<display:setProperty name="paging.banner.placement" value="bottom" />
				<!-- Common Columns -->
				<display:column property="dteRtl" sortName="dteRtl" title="Retail Date" />
				<display:column property="idDlr" sortName="idDlr" title="Dealer No" />
				<display:column property="numPo" sortName="numPo" title="PO Number"/>
				<display:column property="numVin" sortName="numVin" title="VIN Number" />
				<display:column property="desModl" sortName="desModl" title="Model" />
				<!-- Specific columns -->
				<s:if test="netStartRpt.vehicleTypeRd.contains('F')">
				<display:column property="custExp" sortName="custExp" title="Cust Exp" />
				<display:column property="unearnedCustExp" sortName="unearnedCustExp" title="Cust Exp Unearned" />
				<display:column property="brdStd" sortName="brdStd" title="Brand Std" />
				<display:column property="unearnedBrdStd" sortName="unearnedBrdStd" title="Brand Std Unearned" />
				<display:column property="salesBonus" sortName="salesBonus" title="Sales Bonus" />
				<display:column property="unearnedSalesBonus" sortName="unearnedSalesBonus" title="Sales Bonus Unearned" />
				<display:column property="dlrResv" sortName="dlrResv" title="Dealer Reserve" />
				</s:if>
				<s:if test="netStartRpt.vehicleTypeRd.contains('V')">
				<display:column property="custExp" sortName="custExp" title="Cust Exp" />
				<display:column property="unearnedCustExp" sortName="unearnedCustExp" title="Cust Exp Unearned" />
				<display:column property="brdStd" sortName="brdStd" title="Brand Std" />
				<display:column property="unearnedBrdStd" sortName="unearnedBrdStd" title="Brand Std Unearned" />
				<display:column property="salesBonus" sortName="salesBonus" title="Sales Bonus" />
				<display:column property="unearnedSalesBonus" sortName="unearnedSalesBonus" title="Sales Bonus Unearned" />
				<display:column property="dlrResv" sortName="dlrResv" title="Dealer Reserve" />
				</s:if>
				<s:if test="netStartRpt.vehicleTypeRd.contains('S')">
				<display:column property="brdStd" sortName="brdStd" title="Brand Std" />
				<display:column property="unearnedBrdStd" sortName="unearnedBrdStd" title="Brand Std Unearned" />
				<display:column property="dlrResv" sortName="dlrResv" title="Dealer Reserve" />
				</s:if>
				<s:if test="netStartRpt.vehicleTypeRd.contains('P')">
				<display:column property="custExpSales" sortName="custExpSales" title="Cex Sales" />
				<display:column property="unearnedCustExpSales" sortName="unearnedCustExpSales" title="Cex Sales Unearned" />
				<display:column property="custExpService" sortName="custExpService" title="Cex Service" />
				<display:column property="unearnedCustExpService" sortName="unearnedCustExpService" title="Cex Service Unearned" />
				<display:column property="poSales" sortName="poSales" title="PO Sales" />
				<display:column property="unearnedPoSales" sortName="unearnedPoSales" title="PO Sales Unearned" />
				<display:column property="nvSales" sortName="nvSales" title="NV Sales" />
				<display:column property="unearnedNvSales" sortName="unearnedNvSales" title="NV Sales Unearned" />
				<display:column property="brdStd" sortName="brdStd" title="Brand Std" />
				<display:column property="unearnedBrdStd" sortName="unearnedBrdStd" title="Brand Std Unearned" />
				<display:column property="flrPlan" sortName="flrPlan" title="Floor Plan" />
				</s:if>
				<!-- common columns -->
				<display:column property="unearnedTotal" sortName="unearnedTotal" title="Total Unearned" />
				</display:table>
				</div>
				<!-- Dealer Performance Unearned Bonus Report - FNC27 - End -->
				
						<%-- <table id = "t1"  width="957" height="50" border="1" >
							<tr>
								<td nowrap BGCOLOR="#999999"><center><strong>Retail Date</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer No</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>PO Number</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Model</strong></center></td>
								<s:if test="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('P')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>PO Sales</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn PO Sls</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>NV Sales</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn NV Sls</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Floor Plan</strong></center></td>
								</s:if>
								<s:if test ="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('S')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cus Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Smart Fran</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Smt Frn</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Sales Bonus</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Sls Bns</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer Reserve</strong></center></td>
								</s:if>								
								<s:if test="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('V')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cus Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>CV Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn CV BStd</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Sales Bonus</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Sls Bns</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer Reserve</strong></center></td>
								</s:if> 								
								<s:if test ="netStartRpt.vehicleTypeRd.isEmpty() or netStartRpt.vehicleTypeRd.contains('F')">
								<td nowrap BGCOLOR="#999999"><center><strong>Cust Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Cus Exp</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>CV Brd Std</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn CV BStd</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Sales Bonus</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Sls Bns</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Unearn Total</strong></center></td>
								<td nowrap BGCOLOR="#999999"><center><strong>Dealer Reserve</strong></center></td>
								</s:if>
							</tr>
			
							 <s:iterator status="stat" var="temp" value="list" >
							  	<s:iterator var="test" value="temp">
								  	<tr>
								  		<s:iterator status="firstCount" var="first" value="test">
								  		<s:if test="#firstCount.index < 4">
											<td><s:property value="first"></s:property></td>
										</s:if>
										<s:else>
											<s:if test ="netStartRpt.vehicleTypeRd.isEmpty() or 
												(netStartRpt.vehicleTypeRd.contains('P') and #firstCount.index > 3 and #firstCount.index < 14)
												or (netStartRpt.vehicleTypeRd.contains('S') and #firstCount.index > 13 and #firstCount.index < 22)
												or (netStartRpt.vehicleTypeRd.contains('V') and #firstCount.index > 21 and #firstCount.index < 30)
												or (netStartRpt.vehicleTypeRd.contains('F') and #firstCount.index > 29 and #firstCount.index < 38)">
													<td><s:property value="first"></s:property></td>
											</s:if>
										</s:else>
										</s:iterator>
									</tr>
								</s:iterator>
							</s:iterator> 
							<s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />
							</table> --%>
							<br/>
			</s:elseif>
			<!-- Unearned Performance Bonus calculation end -->
			<%-- <s:elseif test="netStartRpt.rptId == 6">
			<s:if test="%{list.isEmpty() }">
				<tr>
					<td colspan="7"><span>No record present.</span></td>
				</tr>
			</s:if>
			<s:else>
				<table width="957" height="90" border="1">
				  <caption class="pageTitle"> Floor Plan Exception Report
			      </caption>
				  <tr>
				    <th width="15%" height="17" bgcolor="#999999" scope="row"><strong>RETAIL DATE</strong></th>
				    <th width="5%" height="17" bgcolor="#999999" scope="row"><strong>MY</strong></th>
				    <th width="08%" bgcolor="#999999" align="justify"><strong>MODEL</strong></th>
				    <th width="10%" bgcolor="#999999" align="justify"><strong>SERIAL</strong></th>
				    <th width="10%"  bgcolor="#999999" align="justify"><strong>PO NUMBER</strong></th>
				    <th width="05%"  bgcolor="#999999" align="justify"><strong>DEALER</strong></th>
				    <th width="10%"  bgcolor="#999999" align="justify"><strong>REGION</strong></th>
				    <th width="10%"  bgcolor="#999999" align="justify"><strong>CAR COUNT</strong></th>
				    <th width="27%"  bgcolor="#999999" align="justify"><strong>REASON</strong></th>			     
				  </tr>
				 <s:iterator status="stat" var="temp" value="list">
					<s:if test="#stat.index == 0">
					  	<s:iterator var="test" value="temp">
						  	<tr>
						  		<s:iterator var="first" value="test">
								<td><s:property value="first"></s:property></td>
								</s:iterator>
							</tr>
						</s:iterator>
					</s:if>
					<s:else>
						
						<s:iterator var="test" value="temp">
							<s:iterator status="val" var="first" value="test">
								<s:if test="#val.index == 0">
									<s:set name="retail" value="first" scope="page"></s:set>
								</s:if>
								<s:if test="#val.index == 1">
									<s:set name="cancel" value="first" scope="page"></s:set>
								</s:if>
								<s:if test="#val.index == 2">
									<s:set name="total" value="first" scope="page"></s:set>
								</s:if>
								
							</s:iterator>
							<tr></tr>
							<tr></tr><br><br><br>
							<tr>
							<s:if test="#stat.index == 1">
								<td colspan="8" align="right"><b>RETAIL:</b> </td>
								<td colspan="2" align="right"><b>${retail}</b> </td>
								<tr><td colspan="8" align="right"><b>CANCEL:</b> </td>
								<td colspan="2" align="right"><b>${cancel}</b> </td></tr>
								<tr><td colspan="8" align="right"><b>TOTAL :</b></td>
								<td colspan="2" align="right"><b> ${total}</b> </td></tr>
								</s:if>
							</tr>
						</s:iterator>
					</s:else>
				</s:iterator>
					<s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />
				</table>
			</s:else> 
			</s:elseif> --%>
			<s:elseif test="netStartRpt.rptId == 16">
			<s:if test="%{list.isEmpty() }">
				<tr>
					<td colspan="7"><span>No record present.</span></td>
				</tr>
			</s:if>
			<s:else>
			<center><div class="pageTitle" id="HL1"> Floor Plan / Dealer Reserve Report</div></center>
				<table style='width:977px' height="90" border="1">
				<tr bgcolor="#999999"><th width="150" align="center">Programs</th>
				<th width="50" colspan="2" style='text-align:center;'>Monthly Data</th>
				<th width="100" colspan="2" style='text-align:center;'>Quarterly Data</th>
				<th width="100" colspan="2" style='text-align:center;'>Bonus Year Data</th></tr>
				  <s:iterator status="stat" var="temp" value="list">
				  <s:if test="#stat.index == 0">
				  	<s:iterator status="varCount" var="test" value="temp">
				  		<s:if test="#varCount.index == 0">
				  			<tr>
				  				<th align="left" bgcolor="#999999">RETAILED</th>
				  				<th width="50"  bgcolor="#999999" align="center">Units</th>
				  				<th width="50"  bgcolor="#999999" align="center">Payment</th>
				  				<th width="50"  bgcolor="#999999" align="center">Units</th>
				  				<th width="50"  bgcolor="#999999" align="center">Payment</th>
				  				<th width="50"  bgcolor="#999999" align="center">Units</th>
				  				<th width="50"  bgcolor="#999999" align="center">Payment</th>
				  			</tr>
				  		</s:if>
					  	<tr>
					  		<s:iterator status="valCount" var="first" value="test">
					  			<s:if test="#valCount.index != 0">
					  				<s:if test="#valCount.index == 1">
					  					<td bgcolor="#999999" width="100" align="left">
					  				</s:if>
					  				<s:else>
					  					<td width="200" align="right">
					  				</s:else>
										<s:property value="first"/>
									</td>
								</s:if>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:if>
				<s:elseif test="#stat.index == 1">
				  		<tr><th align="left" bgcolor="#999999" colspan="7">PAYMENTS</th></tr>
					<s:iterator status="varCount" var="test" value="temp">
				  		<tr>
					  		<s:iterator status="valCount" var="second" value="test">
				  				<s:if test="#valCount.index == 0">
				  					<td colspan="1" width="300" align="left">
				  				</s:if>
				  				<%-- <s:elseif test="#valCount.index == 1">
				  			    <td width="200" align="right" colspan="2" style='text-align:right;'>
				  				</s:elseif> --%>
				  				<s:else>
				  				    <td width="200" align="right" colspan="2" style='text-align:right;'>
				  				</s:else>
									<s:property value="second"/>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:elseif>
				<s:elseif test="#stat.index == 2">
					<tr><th align="left" bgcolor="#999999" colspan="7">SUMMARY</th></tr>
					<s:iterator status="varCount" var="test" value="temp">
				  		<tr>
					  		<s:iterator status="valCount" var="third" value="test">
					  			<s:if test="#valCount.index != 0">
				  				<s:if test="#valCount.index == 1">
				  					<td colspan="5" width="300" align="right">
				  				</s:if>
				  				<s:else>
				  					<td width="200" colspan ="2" style='text-align:right;'>
				  				</s:else>
									<s:property value="third"/>
								</td>
								</s:if>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:elseif>
				</s:iterator>
				 <%-- <s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />  --%> 
				</table>
			</s:else>
			</s:elseif>
			<s:elseif test="netStartRpt.rptId == 7">
			<s:if test="%{list.isEmpty() }">
				<tr>
					<td colspan="7"><span>No record present.</span></td>
				</tr>
			</s:if>
			<s:else>
			<center><div class="pageTitle" id="HL1"> Dealer Reserve Summary Report</div></center>
				<table width="957" height="90" border="1">
				<tr bgcolor="#999999"><th width="150" align="center">Programs</th><th width="100" colspan="2" align="center">492343</th><th width="100" colspan="2" align="center">Sub Total</th></tr>
				  <s:iterator status="stat" var="temp" value="list">
				  <s:if test="#stat.index == 0">
				  	<s:iterator status="varCount" var="test" value="temp">
				  		<s:if test="#varCount.index == 0">
				  			<tr>
				  				<th align="left" bgcolor="#999999" colspan="5">RETAILED</th>
				  			</tr>
				  		</s:if>
					  	<tr>
					  		<s:iterator status="valCount" var="first" value="test">
					  			<s:if test="#valCount.index != 0">
					  				<s:if test="#valCount.index == 1">
					  					<td bgcolor="#999999" width="100" align="left">
					  				</s:if>
					  				<s:else>
					  					<td width="200" align="right">
					  				</s:else>
										<s:property value="first"/>
									</td>
								</s:if>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:if>
				<s:elseif test="#stat.index == 1">
				  		<tr><th align="left" bgcolor="#999999" colspan="5">PAYMENTS</th></tr>
					<s:iterator status="varCount" var="test" value="temp">
				  		<tr>
					  		<s:iterator status="valCount" var="second" value="test">
				  				<s:if test="#valCount.index == 0">
				  					<td colspan="4" width="300" align="left">
				  				</s:if>
				  				<s:else>
				  					<td width="200" align="right">
				  				</s:else>
									<s:property value="second"/>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:elseif>
				<s:elseif test="#stat.index == 2">
					<tr><th align="left" bgcolor="#999999" colspan="5">OWED</th></tr>
					<s:iterator status="varCount" var="test" value="temp">
				  		<tr>
					  		<s:iterator status="valCount" var="third" value="test">
					  			<s:if test="#valCount.index != 0">
				  				<s:if test="#valCount.index == 1">
				  					<td colspan="4" width="300" align="left">
				  				</s:if>
				  				<s:else>
				  					<td width="200" align="right">
				  				</s:else>
									<s:property value="third"/>
								</td>
								</s:if>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:elseif>
				</s:iterator>
				 <s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />  
				</table>
			</s:else>
			</s:elseif>
			<s:elseif test="netStartRpt.rptId == 8">
			<s:if test="%{list.isEmpty() }">
				<tr>
					<td colspan="7"><span>No record present.</span></td>
				</tr>
			</s:if>
			<s:else>
			<center><div class="pageTitle" id="HL1"> Floor Plan Summary Report</div></center>
				<table width="957" height="90" border="1">
				<tr bgcolor="#999999"><th width="100" align="center">Programs</th><th width="160" colspan="2" align="center">492343</th><th width="160" colspan="2" align="center">Sub Total</th></tr>
				  <s:iterator status="stat" var="temp" value="list">
				  <s:if test="#stat.index == 0">
				  	<s:iterator status="varCount" var="test" value="temp">
				  		<s:if test="#varCount.index == 0">
				  			<tr>
				  				<th align="left" bgcolor="#999999" colspan="5">ACCRUED</th>
				  			</tr>
				  		</s:if>
					  	<tr>
					  		<s:iterator status="valCount" var="first" value="test">
					  			<s:if test="#valCount.index != 0">
					  				<s:if test="#valCount.index == 1">
					  					<td bgcolor="#999999" width="100" align="left">
					  				</s:if>
					  				<s:else>
					  					<td width="200" align="right">
					  				</s:else>
										<s:property value="first"/>
									</td>
								</s:if>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:if>
				<s:elseif test="#stat.index == 1">
				  		<tr><th align="left" bgcolor="#999999" colspan="5">PAYMENTS</th></tr>
					<s:iterator status="varCount" var="test" value="temp">
				  		<tr>
					  		<s:iterator status="valCount" var="second" value="test">
				  				<s:if test="#valCount.index == 0">
				  					<td colspan="4" width="300" align="left">
				  				</s:if>
				  				<s:else>
				  					<td width="200" align="right">
				  				</s:else>
									<s:property value="second"/>
								</td>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:elseif>
				<s:elseif test="#stat.index == 2">
					<tr><th align="left" bgcolor="#999999" colspan="5">OWED</th></tr>
					<s:iterator status="varCount" var="test" value="temp">
				  		<tr>
					  		<s:iterator status="valCount" var="third" value="test">
					  			<s:if test="#valCount.index != 0">
				  				<s:if test="#valCount.index == 1">
				  					<td colspan="4" width="300" align="left">
				  				</s:if>
				  				<s:else>
				  					<td width="200" align="right">
				  				</s:else>
									<s:property value="third"/>
								</td>
								</s:if>
							</s:iterator>
						</tr>
					</s:iterator>
				</s:elseif>
				</s:iterator>
				<s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />  
				</table>
			</s:else>
			</s:elseif>
			<s:elseif test="netStartRpt.rptId == 9">
			<div id="Result">
		<s:if test="%{blkList.size() > 0}">
			<table width="957" height="90" border="1" summary="Blocked Vehicles Report - Home Office Report">
			  <caption class="pageTitle">Blocked Vehicles Report
		      </caption>
			  <tr>
			    <th width="5%" height="17" bgcolor="#999999" scope="row" ><strong>Srno</strong></th>
			    <th width="15%" height="17" bgcolor="#999999" scope="row"><strong>Dealer Id</strong></th>
			    <td width="12%" bgcolor="#999999" align="center"><strong>Purchase Order</strong></td>
			    <td width="10%"  bgcolor="#999999"  align="center" ><strong>Block Date</strong></td>
			    <td width="10%"  bgcolor="#999999"  align="center" ><strong>Retail Date</strong></td>
			    <td width="18%"  bgcolor="#999999"  align="center"><strong>VIN</strong></td>
			    <td width="40%"  bgcolor="#999999"  align="center"><strong>Blocked reason</strong></td>
			    <td width="40%"  bgcolor="#999999"  align="center"><strong>DDR Usecode</strong></td>
		      </tr>
			  <s:iterator value="blkList">
				<tr>
					<td width="5%">
						<s:property value="srNo"></s:property>
					</td>
					<td width="15%">
						<s:property value="idDealer"></s:property>
					</td>
					<td width="12%">
						<s:iterator value="poNo" var="poNoarr"><s:property value="#poNoarr"/><br/></s:iterator>
					</td>
					<td width="10%">
						<s:property value="displayDate"></s:property>
					</td>
					<td width="10%">
						<s:property value="displayetailDate"></s:property>
					</td>
					<td width="18%">
						<s:property value="vinNo"></s:property>
					</td>
					<td width="40%" Style="word-wrap:break-word;">
					<s:iterator value="reason" var="reasonarr"><s:property value="#reasonarr"/><br/></s:iterator>
					</td>
					<td width="10%">
						<s:property value="ddrUsecode"></s:property>
					</td>
				</tr>
			</s:iterator>
				<s:submit type="button" key="submit"  value="Export to Excel"   action = "exportBlockedToExcel" id = "exportBlockedToExcel" onclick="return false;"/> 
			</table>
		</s:if>
	</div>  
	</s:elseif>

			<!-- Vehicle Details Report-start -->
			<s:elseif test="netStartRpt.rptId == 11">

				<h1 align="center">Vehicle Details Report</h1>
				<s:url var="actionUrl" value="homeOfficeReports.action">


				</s:url>
				<div style=" overflow:scroll; width:954px; height:350px;">
				<display:table class="displayTable" name="vehDetailsList"
					sort="external" defaultsort="1" pagesize="${pageSize}"
					id="vehDetails" partialList="true" size="${resultSize}"
					requestURI="${actionUrl}">

					<display:setProperty name="paging.banner.placement" value="bottom" />
					<display:column property="dteRtl" sortName="dteRtl"
						title="DTE_RETAIL" />
					<display:column property="idDlr" sortName="idDlr" title="ID_DLR" />
					<display:column property="numPo" sortName="numPo" title="NUM_PO" />
					<display:column property="numVin" sortName="numVin"
						title="NUM_VIN" />
					<display:column property="cdeVehSts" sortName="cdeVehSts"
						title="CDE_VEH_STS" />
					<display:column property="cdeUseVeh" sortName="cdeUseVeh"
						title="CDE_USE_VEH" />
					<display:column property="indUsedVeh" sortName="indUsedVeh"
						title="IND_USED_VEH" />
					<display:column property="cdeVehDdrSts" sortName="cdeVehDdrSts"
						title="CDE_VEH_DDR_STS" />
					<display:column property="cdeVehDdrUse" sortName="cdeVehDdrUse"
						title="CDE_VEH_DDR_USE" />
					<display:column property="indUsedVehDdrs"
						sortName="indUsedVehDdrs" title="IND_USED_VEH_DDRS" />
					<display:column property="tmeRtl" sortName="tmeRtl"
						title="TME_RTL" />
					<display:column property="idEmpPurCtrl" sortName="idEmpPurCtrl"
						title="ID_EMP_PUR_CTRL" />
					<display:column property="dteModlyr" sortName="dteModlyr"
						title="DTE_MODL_YR" />
					<display:column property="desModl" sortName="desModl"
						title="DES_MODL" />
					<display:column property="cdeRgn" sortName="cdeRgn"
						title="CDE_RGN" />
					<display:column property="namRtlCusLst" sortName="namRtlCusLst"
						title="NAM_RTL_CUS_LST" />
					<display:column property="namRtlCusFir" sortName="namRtlCusFir"
						title="NAM_RTL_CUS_FIR" />
					<display:column property="namRtlCusMid" sortName="namRtlCusMid"
						title="NAM_RTL_CUS_MID" />
					<display:column property="dteTrans" sortName="dteTrans"
						title="DTE_TRANS" />
					<display:column property="tmeTrans" sortName="tmeTrans"
						title="TME_TRANS" />
					<display:column property="indFlt" sortName="indFlt"
						title="IND_FLT" />
					<display:column property="cdeWhsleInitType"
						sortName="cdeWhsleInitType" title="CDE_WHSLE_INIT_TYP" />
					<display:column property="cdeNatlType" sortName="cdeNatlType"
						title="CDE_NATL_TYPE" />
					<display:column property="dteVehDemoSrv" sortName="dteVehDemoSrv"
						title="DTE_VEH_DEMO_SRV" />
					<display:column property="cdeVehTyp" sortName="cdeVehTyp"
						title="CDE_VEH_TYP" />
					<display:column property="amtMsrpBase" sortName="amtMsrpBase"
						title="AMT_MSRP_BASE" />
					<display:column property="amtMsrpTotAcsry"
						sortName="amtMsrpTotAcsry" title="AMT_MSRP_TOT_ACSRY" />
					<display:column property="amtDlrRbt" sortName="amtDlrRbt"
						title="AMT_DLR_RBT" />
					<display:column property="amtMsrp" sortName="amtMsrp"
						title="AMT_MSRP" />
					<s:if test="netStartRpt.dataTypeRadio == 'All'">
						<display:column property="blockedDate"
							style="white-space:nowrap;" sortName="blockedDate"
							title="BLOCKED_DATE" />
						<display:column property="blockedReason"
							style="white-space:nowrap;" sortName="blockedReason"
							title="BLOCKED_REASON" />
					</s:if>
					<s:elseif test="netStartRpt.dataTypeRadio == 'Blocked'">
						<display:column property="blockedDate"
							style="white-space:nowrap;" sortName="blockedDate"
							title="BLOCKED_DATE" />
						<display:column property="blockedReason"
							style="white-space:nowrap;" sortName="blockedReason"
							title="BLOCKED_REASON" />
					</s:elseif>
				</display:table>
				</div>

			</s:elseif>
			<!-- Vehicle Details Report-end -->

					<!-- AMG Report - START -->
			<s:elseif test="netStartRpt.rptId == 12 or netStartRpt.rptId == 13" >
				
				<s:if test="netStartRpt.rptId == 12">
					<center><div class="pageTitle" id="HL1">AMG Performance Bonus Report</div></center>
				</s:if>
				<s:elseif test="netStartRpt.rptId == 13">
					<center><div class="pageTitle" id="HL1">AMG Elite Performance Bonus Report</div></center>
				</s:elseif>
				
				<table id = "tableamg"  width="70%" height="20" border="1" >
					<thead>
					<tr>
						<th nowrap BGCOLOR="#999999"><center>Retail Date</center></th>
						<th nowrap BGCOLOR="#999999"><center>Dealer No</center></th>
						<th nowrap BGCOLOR="#999999"><center>Program Type</center></th>
						<th nowrap BGCOLOR="#999999"><center>Model</center></th>
						<th nowrap BGCOLOR="#999999"><center>Model Year</center></th>
						<th nowrap BGCOLOR="#999999"><center>VIN</center></th>
						<th nowrap BGCOLOR="#999999"><center>PO Number</center></th>
						<th nowrap BGCOLOR="#999999"><center>AMG Bonus Earned</center></th>						
						<th nowrap BGCOLOR="#999999"><center>Total Unearned</center></th>
					</tr>
					</thead>
					<tbody>
					<s:iterator status="stat" var="temp" value="list" >
						<s:iterator var="test" value="temp">
						<tr>
							<s:iterator status="firstCount" var="first" value="test">
							<s:if test="#firstCount.index < 4">
								<td><s:property value="first"></s:property></td>
							</s:if>
							<s:else>
								<s:if test ="netStartRpt.vehicleTypeRd.isEmpty() or 
									(#firstCount.index > 1 and #firstCount.index < 10)">
									<td><s:property value="first"></s:property></td>
								</s:if>
							</s:else>
							</s:iterator>
						</tr>
						</s:iterator>
					</s:iterator>
					</tbody> 
					<!--<s:submit key="submit"  value="Export to Excel"   action = "exportToExcel" />--> 
				</table><br/>
			</s:elseif>
			<!-- AMG Report - END -->
			
			<%-- code for VIN lookup start --%>
			
			<s:elseif test="netStartRpt.rptId == 10">
			<h1 align="center">VIN Lookup Report</h1>
			<div style=' overflow:scroll; width:97%; height:350px;'>
				<table height="10" border="1" style="width:95%">
				<tr>
				   	<th nowrap width="01%" style="text-align: center;" height="17" bgcolor="#999999" scope="row">VIN</th>
				   	<th nowrap width="01%" style="text-align: center;" height="17" bgcolor="#999999" scope="row">PO #</th>
				    <th nowrap width="01%" style="text-align: center;" height="17" bgcolor="#999999" scope="row">DEALER</strong></th>
				    <th nowrap width="01%" style="text-align: center;" height="17" bgcolor="#999999" scope="row">MAX ELIGIBLE</th>
				    <td nowrap width="01%" style="text-align: center;" height="17" bgcolor="#999999" scope="row"><strong>EARNED</strong></td>
				  </tr>
			      
				
				  <s:iterator status="stat" var="temp" value="list">
					<s:if test="#stat.index == 0">
					  	<s:iterator var="test" value="temp">
						  	<tr>
						  		<s:iterator var="first" value="test">
								<td nowrap ><s:property value="first"></s:property></td>
								</s:iterator>
							</tr>
						</s:iterator>
					</s:if>
					<s:else>
						<s:iterator var="test" value="temp">
							<tr>
								<s:iterator status="val" var="first" value="test">
									<s:if test="#val.index == 0">
									<td nowrap colspan="25" align="right"><b><s:property value="first"></s:property></b></td>
									</s:if><s:else>
										<td nowrap colspan="1"><b><s:property value="first"></s:property></b></td>
									</s:else>
								</s:iterator>
							</tr>
						</s:iterator>
					</s:else>
				</s:iterator>
				
				
				</table>
			</div>
			</s:elseif>
			
			
			<%-- code for VIN lookup end --%>
			
			
		</s:if>
		</div>
		 <table id="blockDetails" style="display: none ; width: 957px;border: 1px;" >
		<tr> 
		 <th  > 
		<b>From 1st October 2016 additional block details will be available</b>
		 </th> 
		</tr>
		</table> 
		<s:if test="netStartRpt.rptId == 3 ||netStartRpt.rptId == 14">
			<b>Note: Report Formatted For Bonus Margin Year 2016 Beginning 04/01/2016 <%-- <s:date name="#session.BonusYearStartdate" format="MM/dd/yyyy"/> --%> onwards</b>
		</s:if>
		<s:else>
			<b>Note:Data valid for current financial year beginning 04/01/2016 onwards</b>
		</s:else>
	</div>
	</div>
	</s:form>
</body>
</html>