<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <%@page import="java.io.*" %> 
 <%@page import="java.util.*" %> 
 <%@page import = com.mbusa.dpb.common.props.PropertyManager" %> 
 <%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>AMG Dealer Add/Edit</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/jquery.dataTables.min.css"/>
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/buttons.dataTables.min.css"/>
			<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/css/displaytag.css">
			<script  src="<%=request.getContextPath() %>/js/amgPerfAddOrEditGen.js" type="text/javascript"></script>
<sj:head jquerytheme="flick"/>
<style>
.serror, .serrorHeader {
	color: red;
	padding-left: 3px;
}
</style>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.dataTables.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/buttons.flash.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/buttons.html5.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/dataTables.buttons.min.js" ></script>
<script  type="text/javascript" src="<%=request.getContextPath() %>/js/jszip.min.js" ></script>
<script>

$( document ).ready(function() {	
	
	$('#amgPerfAddOrEdit').submit(function() {
	    $(".spinner").fadeOut("slow");
	    
	    return true;
	  });
	var dlrName =$( "#dealerName" ).val();
	var dealerId =$( "#dealerId" ).val();
	if(dealerId && ! dlrName !=''){		
		$("#dealerIdError").show();

	}
});


function trimText(){
 
var regex = new RegExp(' ', 'g');
document.getElementById("dealerId").value=(document.getElementById("dealerId").value).replace(regex,'');
document.getElementById("dealerName").value=(document.getElementById("dealerQuarterYear").value).replace(regex,'');
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

	<s:form action="amgPerfAddOrEditRetrieveData" name="myform">
		<div id="widecontentarea"  style =" margin-bottom: -50px;">
			<div class="pageTitle" id="HL1">AMG Dealer Add/Edit</div>
			<div class="T8">
				<table width="728" border="0" cellspacing="0"
					class="template8TableTop">
					<tr>
						<td colspan="2" class="line"><img
							src="<%=request.getContextPath() %>/images/img_t8_line1.gif"
							height="1" alt="" border="0">
						</td>
					</tr>
					<tr>
						<td width="500" class="left" id="ctl00_tdCopyText"><div
								class="TX1">This Page is for Admin to Add/Edit a Dealer as Elite or Regular.</div>
						</td>
						<td width="363" class="right">
							<div align="left">
								<img id="ctl00_imgLevel2"
									src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg"
									style="border-width: 0px;" /><br>
							</div></td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="template8BottomLine"></div></td>
					</tr>
				</table>
			</div>
			<br />
			<span id="span12" class="serror"></span><br>
			<%-- <s:if test="showResultMsg">
				<span id="spanrec" class="serrorHeader">Please wait while the changes are made to Database.</span>
				<br>
			</s:if> --%>
			<table width="200" class="TBL2">
				<tr>
					<td width="30%">Dealer Id</td>
					<td width="70%"><s:textfield name="dealerId" id="dealerId"   onchange="getDealerName();" maxlength="5"/>
					<span id="dealerIdError"  class="serror" style="display:none">Dealer name does not exist in system for requested dealer Id.</span>
						
					</td>
				</tr>
				<tr>
					<td width="30%">Dealer Name</td>
					<td width="70%"><s:textfield name="dealerName"  onblur='trimText();' id="dealerName" readonly="true"/>
					<span id="dealerNameError" class="serror" ></span>
					</td>
				</tr>
				<tr>
					<td><s:label for="Type" value="Type"></s:label>
					</td>
					<td><s:radio id="dealerType" name="dealerType"
							list="#{'ELT':'Elite','REG':'Regular'}" />
					</td>
				</tr>
				<tr>
					<td width="30%">Retail Year:</td>
					<td width="70%">
					<s:select label="Retail Year" headerKey="-1" headerValue="--Select--"
					list="{2016,2017,2018,2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,
					2034,2035,2036,2037,2038,2039,2040}" name="retailYear" id="retailYear" />
					<span id="retailYearIdError"  class="serror" ></span>
					</td>
				<tr>
				<tr>
					<td width="30%">Start Month :</td>
					<td width="70%">
					<s:select label="Select a month" headerKey="-1" headerValue="--Select--"
						list="#{'01':'Jan', '02':'Feb', '03':'Mar', '04':'Apr','05':'May','06':'Jun','07':'Jul','08':'Aug','09':'Sep','10':'Oct','11':'Nov','12':'Dec'}" 	
						name="retailStartMonth" id="retailStartMonth" />
					<span id="startMonthIdError"  class="serror" ></span>
					</td>
					</td>
				<tr>
					<td width="30%">End Month :</td>
					<td width="70%">
					<s:select label="Select a month" headerKey="12" headerKey="-1" headerValue="--Select--"
						list="#{'01':'Jan', '02':'Feb', '03':'Mar', '04':'Apr','05':'May','06':'Jun','07':'Jul','08':'Aug','09':'Sep','10':'Oct','11':'Nov','12':'Dec'}" 	
						name="retailEndMonth" id="retailEndMonth" />
					<span id="endMonthIdError"  class="serror" ></span>
					</td>
				</tr>		
				<tr>
					<td><s:submit key="Search"  method="retrieveAMGDealerInfo" value="Search"  align="left" />				
					
					 <input type="button" name="reset" id="reset" value="Reset"  onclick="resetFormData();"/>		
					</td>
					<td><s:submit key="submit" value="Submit" method="modifyAMGDealerInfo" id="submitDealerButton" align="left" />
					</td>					
				</tr>
				<b>Note: Clicking on Search button with empty Dealer Id will retrieve all AMG dealer(s)</b>
				<p><b>Default End date is marked as Jan 2020  if End Month is not selected</b></p>
				<p><b>To remove a dealer from the amg list, retrieve and select End Month as previous month from the current month</b></p>							
			</table>
		</div>
		<div style="overflow: scroll; width: 954px;">
			</tr>
			<s:if test="%{listDlrs.size>0}">
				<table id="dealerTable" border=1 style=" width: 95%;" >
				<tr bgcolor="#999999" style="text-align: center; ">
					<td></td>
					<td>Dealer Id</td>
					<td>Dealer Name</td>
					<td>Type</td>					
					<td>Retail Start Date</td>
					<td>Retail End  Date</td>				
			
				<s:iterator value="listDlrs" var="amgDealerResultsVO" status="kpiAllocDet2">
					<tr style="text-align: center; ">
						<td><input type="radio" name="result" id = "select_${amgDealerResultsVO.dealerID}" onclick="restoreData(this.id);"/></td>
						<td  class="panel-layout-hd-text"><label id="dealerId_${amgDealerResultsVO.dealerID}">${amgDealerResultsVO.dealerID}</label></td>
						<td  class="panel-layout-hd-text"><label id="dealerName_${amgDealerResultsVO.dealerID}">${amgDealerResultsVO.dealerName}</label></td>
						<td  class="panel-layout-hd-text"><label id="dealerType_${amgDealerResultsVO.dealerID}">${amgDealerResultsVO.dealerType}</label></td>
						<td  class="panel-layout-hd-text"><label id="retailStartMonth_${amgDealerResultsVO.dealerID}">${amgDealerResultsVO.retailStartMonth}</label></td>
						<td  class="panel-layout-hd-text"><label id="retailEndMonth_${amgDealerResultsVO.dealerID}">${amgDealerResultsVO.retailEndMonth}</label></td>						
					</tr>
				</s:iterator>
				</table>
			</s:if>
		</div>
	</s:form>
</body>
</html>