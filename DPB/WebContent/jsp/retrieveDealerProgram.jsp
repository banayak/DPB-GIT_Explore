<!DOCTYPE HTML><%@page import="java.util.ArrayList"%>
<%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>dealerPrgView</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<script type="text/javascript">
		
			function onClickCancel(val){ 

var abc= val.value;

alert(abc);

val.form.action = '<%=request.getContextPath()%>' +'/dashBoard.action';

}
</script>


			<script>
 function formReset()
 {
$("#dlrPrgm1").resetForm();
 }
 </script>
</head>
<body> 
<s:form action="dealerProgram" method="post" id="dlrPrgm1">
<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	DPB Program
</div>
 
<div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0" ></td>
			</tr>
			<tr>
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Dealer Performance Bonus Programs provide all qualified authorized dealers in good standing an opportunity to earn a performance bonus of up to 5.50% for MB and 3.5% for Vans & smart of Manufacturers Suggested Retail Price (MSRP) on eligible transactions.</div></td>
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
</div><%-- 
<% List l1 = new ArrayList();
List l2 = new ArrayList();
List displayList =  new ArrayList();
WebHelper helper = new WebHelper();
l1=myBean.getVehicleType();
l2 = myBean.getCacheVehType();
 displayList =helper.getVehicleName(l1,l2);
%>
 --%>

<table width="200" border="0" class="TBL2">
	<tr>
		<td width="30%"><s:label  value="Program Id" for="dlrPrgmForm.programId" /></td>
		<td width="70%"><s:property value="dlrPrgmForm.programId"/>
			 <s:hidden name="dlrPrgmForm.specialProgram" value='N' /> 
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.programName" value="Program Name"></s:label></td>
		<td><s:property value="dlrPrgmForm.programName"/>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.kpiValue" value="KPI" ></s:label></td>
		<td><s:property value="dlrPrgmForm.kpiValue"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.description" value="Description" ></s:label></td>
		<td><s:property value="dlrPrgmForm.description"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.vehicleType" value="Applicable Vehicles"  ></s:label></td>
        <td>
         	<s:iterator value="dlrPrgmForm.vehicleType" status="count">
		<li><s:text name="dlrPrgmForm.vehicleType[%{#count.index}]"></s:text></li>
        </s:iterator>
       	</td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.startDate" value= "Start Date"  ></s:label></td>
		<td><s:property value="dlrPrgmForm.startDate"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.endDate" value="End Date" ></s:label></td>
		<td><s:property value="dlrPrgmForm.endDate"/></td>
	</tr>
	<tr>
		<td><s:label for="paymentType" value="Payment Type"  ></s:label></td>
		<td><s:property value="dlrPrgmForm.paymentType"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPayment" value="Fixed Payment" ></s:label></td>
		<td><s:property value="dlrPrgmForm.fixedPayment"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtBonusSchedule" value="Fixed Bonus Process Schedule"></s:label></td>
		<td><s:property value="dlrPrgmForm.fixedPymtBonusSchedule"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtBonusTrigger" value="Fixed Bonus Process Trigger" ></s:label></td>
		<td><s:property value="dlrPrgmForm.fixedPymtBonusTrigger"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtSchedule" value="Fixed Payment Schedule"></s:label></td>
		<td><s:property value="dlrPrgmForm.fixedPymtSchedule"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtTrigger" value="Fixed Payment Trigger" ></s:label></td>
		<td><s:property value="dlrPrgmForm.fixedPymtTrigger"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.variablePayment" value="Variable Payment"  ></s:label></td>
	    <td><s:property value="dlrPrgmForm.variablePayment"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtBonusSchedule" value="Variable Bonus Process Schedule"></s:label></td>
		<td><s:property value="dlrPrgmForm.variablePymtBonusSchedule"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtBonusTrigger" value="Variable Bonus Process Trigger" ></s:label></td>
		<td><s:property value="dlrPrgmForm.variablePymtBonusTrigger"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtSchedule" value="Variable Payment Schedule"  ></s:label></td>
		<td><s:property value="dlrPrgmForm.variablePymtSchedule"/></td>
	</tr>	
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtTrigger" value="Variable Payment Trigger"  ></s:label></td>
		<td><s:property value="dlrPrgmForm.variablePymtTrigger"/></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.programStatus" value="Status"  ></s:label></td>
		<td><s:property value="dlrPrgmForm.programStatus"/></td>
	</tr>
</table>
<br /><br /><br />
<table>
	<tr>
		<%-- <td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td> --%>
		<td><s:submit key="Cancel" theme="simple" method="viewDealerListProgram" action="dpbProgramList"/></td>
	</tr>
</table>

</div>
</s:form>
</body>
</html>