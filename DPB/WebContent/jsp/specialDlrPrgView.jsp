<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
<title>dealerPrgView</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<sj:head jquerytheme="flick"></sj:head>
</head>

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
<script>
 function formReset(){ 
 		$(".messages").hide();
		$("#span1").html(" ");
		$("#span2").html(" ");
		$("#span3").html(" ");
		$("#span4").html(" ");
		$("#span7").html(" ");
		$("#span22").html(" ");
		$("#span23").html(" ");
		$("#span24").html(" ");
		$("#span25").html(" ");
		$("#span12").html(" ");
		$("#span19").html(" ");
		$("#span10").html(" ");
		$("#span18").html(" ");
		$("#span17").html(" ");
		$("#span21").html(" ");
		$("#span14").html(" ");
		$("#span20").html(" ");
		
		var inactiveDate = $("#inactiveDate").val();
		if(inactiveDate.length > 0){
		document.getElementById("text13").style.display = 'none';
		document.getElementById("text14").style.display = 'none';
		
		}
		var commissionSelected = $("#commSelected").val();
		if(commissionSelected == 'Y'){
			$("#specialDealerProgram_dlrPrgmForm_commAmount").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_commPercent").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_rebateAmtN").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_rebateAmtY").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerS").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerU").attr("disabled",false);
		    $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerS").attr("disabled",false);
		    $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerU").attr("disabled",false);
		    $("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").attr("disabled",false);
		   
	};
	
	if(commissionSelected == 'N') {
		   $("#specialDealerProgram_dlrPrgmForm_commAmount").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_commPercent").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_rebateAmtN").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_rebateAmtY").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerS").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerU").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerS").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerU").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").attr("disabled",true);
	};
 }
 </script>
<script>  

$(document).ready(function() {
		var status = $('input[name="dlrPrgmForm.programStatus"]:checked').val();
	 var commPrcnt = $('input[name="dlrPrgmForm.commPayment"]:checked').val(); 
		var flagActive = $("#flagActive").val();
		if(status=="I" && flagActive== 'true'){
		document.getElementById("text13").style.display = 'none';
        document.getElementById("text14").style.display = 'none';
        }
		
	/**
	 Commision Payment
	*/
	
	    
	$("#specialDealerProgram_dlrPrgmForm_commAmount").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_commPercent").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_rebateAmtN").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_rebateAmtY").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerS").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerU").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerS").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerU").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").attr("disabled",true);
	 /**
	Is Commision Payment
	*/	
 	$('input[id=specialDealerProgram_dlrPrgmForm_commPaymentY][value="Y"]').click(function() {
			$("#specialDealerProgram_dlrPrgmForm_commAmount").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_commPercent").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_rebateAmtN").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_rebateAmtY").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerS").attr("disabled",false);
			$("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerU").attr("disabled",false);
		    $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerS").attr("disabled",false);
		    $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerU").attr("disabled",false);
		    $("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").attr("disabled",false);
		   
	});
	
	$('input[id=specialDealerProgram_dlrPrgmForm_commPaymentN][value="N"]').click(function() {
		   $("#specialDealerProgram_dlrPrgmForm_commAmount").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_commPercent").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_rebateAmtN").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_rebateAmtY").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerS").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerU").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerS").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerU").attr("disabled",true);
		   $("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").attr("disabled",true);
	});
	
	if(commPrcnt=="Y"){
	
	 $("#specialDealerProgram_dlrPrgmForm_commAmount").attr("disabled",false);
	$("#specialDealerProgram_dlrPrgmForm_commPercent").attr("disabled",false);
	$("#specialDealerProgram_dlrPrgmForm_rebateAmtN").attr("disabled",false);
	$("#specialDealerProgram_dlrPrgmForm_rebateAmtY").attr("disabled",false);
	$("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").attr("disabled",false);
	$("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerS").attr("disabled",false);
    $("#specialDealerProgram_dlrPrgmForm_comsnProcessTriggerU").attr("disabled",false);
    $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerS").attr("disabled",false);
    $("#specialDealerProgram_dlrPrgmForm_comsnPymtTriggerU").attr("disabled",false);
    $("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").attr("disabled",false);
	
	}
	
	$("#specialDealerProgram_submit").click(function() {
		$(".messages").hide();
		$("#span1").html(" ");
		$("#span2").html(" ");
		$("#span3").html(" ");
		$("#span4").html(" ");
		$("#span7").html(" ");
		$("#span22").html(" ");
		$("#span23").html(" ");
		$("#span24").html(" ");
		$("#span25").html(" ");
		$("#span12").html(" ");
		$("#span19").html(" ");
		$("#span10").html(" ");
		$("#span18").html(" ");
		$("#span17").html(" ");
		$("#span21").html(" ");
		$("#span14").html(" ");
		$("#span20").html(" ");
		
				var flag = true;
				var status = $('input[name="dlrPrgmForm.programStatus"]:checked').val();
				var flagActive = $("#flagActive").val();
				
					if(status == 'D')
					{
						var prgName=$("#specialDealerProgram_dlrPrgmForm_programName").val();
						var regex_length = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
						var desc = $("#specialDealerProgram_dlrPrgmForm_description").val();
						if (prgName == '') {
							$("#span1").text(" Enter Program Name.").show();
							 flag = false;
						}
						else{
						if(!prgName.match(regex_length)){
			               $("#span1").text(" Name should be up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
							 flag = false;
			            }
			            if (desc.length > 255) {
									$("#span2").text(" Description should be less than 255 characters.").show();
									flag = false;
						}
						if (prgName.length >40) {
								$("#span1").text(" Name should be up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
			              		flag = false;
						} 
			         }
			        }
			        else if(status== 'A' && flagActive == 'false'){
						var prgName=$("#specialDealerProgram_dlrPrgmForm_programName").val();
						var regex_length = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
						var desc = $("#specialDealerProgram_dlrPrgmForm_description").val();
						var regex_number = "^[0-9]{0,7}([\.][0-9]+)?";
						var startDate = $("#startDate").val();
						var endDate = $("#endDt").val();
						var commPrcnt = $('input[name="dlrPrgmForm.commPayment"]:checked').val();
						var rebateVal = $('input[name="dlrPrgmForm.rebateAmt"]:checked').val();
						var rebateCheck="Y";
						var camt = $("#specialDealerProgram_dlrPrgmForm_commAmount").val();
						var cpTrigger = $('input[name="dlrPrgmForm.comsnPymtTrigger"]:checked').val();
						var cbTrigger = $('input[name="dlrPrgmForm.comsnProcessTrigger"]:checked').val();
						var fpTrigger = $('input[name="dlrPrgmForm.fixedPymtTrigger"]:checked').val();
						var fbTrigger = $('input[name="dlrPrgmForm.fixedPymtBonusTrigger"]:checked').val();
						var vpTrigger = $('input[name="dlrPrgmForm.variablePymtTrigger"]:checked').val();
						var vbTrigger = $('input[name="dlrPrgmForm.variablePymtBonusTrigger"]:checked').val();
						var cp = $("#specialDealerProgram_dlrPrgmForm_commPercent").val();
							
						var fDate=startDate.replace(/-/g,"/");
						var tDate=endDate.replace(/-/g,"/");
						var currentDate = new Date();
						var fromDate = new Date(fDate);
						var toDate = new Date(tDate);
						var date =new Date();
						var tomorrow = new Date(date.getTime() + 24 * 60 * 60 * 1000);
						
						if (prgName == '') {
							$("#span1").text(" Enter Program Name.").show();
							flag = false;
						}else{
							if(!prgName.match(regex_length)){
			              		$("#span1").text(" Name should be up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
			              		flag = false;
							} else{
									$("#span1").text(" Name should be up to 40 characters. May contain alphanumeric, spaces and underscores.").hide();
							}if (prgName.length >40) {
								$("#span1").text(" Name should contain 40 up to characters. May contain alphanumeric, spaces and underscores.").show();
			              		flag = false;
							} 
						}
			            /* if (desc == '') {
							$("#span2").text("Enter description").show();
							flag = false;
						}else {
								$("#span2").text("Enter description").hide();
								flag = true;
						 */		if (desc.length > 255) {
									$("#span2").text(" Description should be less than 255 characters.").show();
									flag = false;
								} /* else {
								$("#span2").text("Enter description").hide();
								} */
						
							
						if(fDate==""){
							$("#span3").text(" Enter Start Date.").show();
							flag = false;
				 		} 
						if(tDate == ""){
							$("#span4").text(" Enter End Date.").show();
							flag = false;
						} else {
						if(toDate < fromDate){
								$("#span4").text(" End Date should not be less than Start Date.").show();
								flag = false;
							}else {
								//COMMENTED TEMPORARILY FOR TESTING
								//TODO: NEED TO ENABLE AFTER THE TESTING
								/* if((fromDate.getDate() < tomorrow.getDate()) && (fromDate.getMonth() == tomorrow.getMonth()) && (fromDate.getFullYear() == tomorrow.getFullYear())){
								$("#span3").text(" Start Date should be greater than today's date.").show();
									flag = false;
								}else {
									$("#span3").text(" Start Date should be greater than today's date.").hide(); */
									//COMMENTED TEMPORARILY FOR TESTING
								//TODO: NEED TO ENABLE AFTER THE TESTING
								/* if((toDate.getDate() < tomorrow.getDate()) && (toDate.getMonth() == tomorrow.getMonth()) && (toDate.getFullYear() == tomorrow.getFullYear())){
								$("#span4").text(" End Date should not be less than Start Date.").show();
									flag = false;
								}else {
									$("#span4").text(" End Date should be greater than Start Date.").hide(); */
								if(fromDate > toDate){
									$("#span3").text(" End Date should be greater than Start Date.").show();
									flag = false;
								}
								if(!verifyDate(startDate))  {
							      $("#span3").text(" Please enter valid Start Date.").show();
							      flag = false;
							    } 
							
							if(!verifyDate(endDate))  {
							      $("#span4").text(" Please enter valid End Date.").show();
							      flag = false;
							      } 
					//}
					}
					} // END OF DATE
					
					
					if(commPrcnt=="Y"){ //This is "IS Commision Payment checked" condition
						if(( ($("#specialDealerProgram_dlrPrgmForm_commAmount").val()==0.0) ) && (rebateVal=="N"))
							{
							$("#span7").text(" Enter Commission Amount or Select Rebate").show();
								flag = false;
							}
						else if(camt.length > 0){ 
						if(!(camt).match(regex_number)){
							 $("#span7").text(" Please enter valid Commission Amount.").show();
							flag = false;
						 }
						 }
						if($("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").val()=="" && cbTrigger !=""){
							$("#span22").text("Select one Schedule Type").show();
							flag = false;
						}
						if(cbTrigger == undefined && $("#specialDealerProgram_dlrPrgmForm_comsnProcessSchedule").val()!="" ){
								$("#span23").text(" Select a Trigger Type.").show();
								flag = false;
						}
						if($("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").val()=="" && cbTrigger !=""){
							$("#span24").text("Select one Schedule Type").show();
							flag = false;
						}
						if(cpTrigger == undefined && $("#specialDealerProgram_dlrPrgmForm_comsnPymtSchedule").val()!="" ){
							$("#span25").text(" Select a Trigger Type.").show();
							flag = false;
						}
					}// END OF COMM FLAG
					
					// FIXED
					
						if($("#specialDealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").val()=="" && fbTrigger !=""){
								$("#span12").text("Select one Schedule Type").show();
								flag = false;
							}
						if(fbTrigger == undefined && $("#specialDealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").val()!="" ){
								$("#span19").text(" Select a Trigger Type.").show();
								flag = false;
						}
						if($("#specialDealerProgram_dlrPrgmForm_fixedPymtSchedule").val()=="" && fpTrigger !=""){
							$("#span10").text("Select one Schedule Type").show();
							flag = false;
						}
						if(fpTrigger == undefined && $("#specialDealerProgram_dlrPrgmForm_fixedPymtSchedule").val()!="" ){
							$("#span18").text(" Select a Trigger Type.").show();
							flag = false;
						}
					//FIXED END
					
					// VARIABLE
					
						if($("#specialDealerProgram_dlrPrgmForm_variablePymtBonusSchedule").val()=="" && vbTrigger !=""){
								$("#span17").text("Select one Schedule Type").show();
								flag = false;
							}
						if(vbTrigger == undefined && $("#specialDealerProgram_dlrPrgmForm_variablePymtBonusSchedule").val()!="" ){
								$("#span21").text(" Select a Trigger Type.").show();
								flag = false;
						}
						if($("#specialDealerProgram_dlrPrgmForm_variablePymtSchedule").val()=="" && vpTrigger !=""){
							$("#span14").text("Select one Schedule Type").show();
							flag = false;
						}
						if(vpTrigger == undefined && $("#specialDealerProgram_dlrPrgmForm_variablePymtSchedule").val()!="" ){
							$("#span20").text(" Select a Trigger Type.").show();
							flag = false;
						}
					//VARIABLE END
					
					}// END OF ACTIVE FLAG
			return flag;
	 });
	
});



$(document).ready(function() {
var status = $("input[name='dlrPrgmForm.programStatus']:checked").val();
var flagActive = $("#flagActive").val();


if(status=="I" && flagActive== 'true'){
		document.getElementById("text13").style.display = 'none';
		document.getElementById("text14").style.display = 'none';
}
$('input[name="dlrPrgmForm.programStatus"][value="D"]').click(function() {	
if(flagActive == 'true'){
	$(".messages").hide();
	document.getElementById("inactiveDate").value = null;
	document.getElementById("programId").value =0;
	document.getElementById("startDate").value ="";
	document.getElementById("endDt").value ="";
	document.getElementById("text13").style.display = 'block';
    document.getElementById("text14").style.display = 'block';
    document.getElementById("flagActive").value =false;
}
});
});
 </script>
 
<script type="text/javascript">
/* function onChangeStatus(val) {

var draft = val.value;

var flagAct = document.getElementById("flagActive").value;

if(draft == 'D' && flagAct == 'true' ) {
	document.getElementById("programId").value =0;
	document.getElementById("startDate").value ="";
	document.getElementById("endDt").value ="";
	document.getElementById("text13").style.display = 'block';
    document.getElementById("text14").style.display = 'block';
    document.getElementById("flagActive").value = false;    
}
} */

function createCondition(){
	document.getElementById("specialDealerProgram").action='conditionDefinition';
	document.getElementById("specialDealerProgram").submit();
}


function verifyDate(date) {
      var dateFlag = true;
      var invalidDate = "Invalid date format";
      if(date != "" && date.length > 0) {
           if(date.match("^(0[1-9]|1[012])\/(0[1-9]|[12][0-9]|3[01])\/[0-9]{4}$"))
                {
                   var fromDate = date.split("/");
                   var leap = 0;
                   if(fromDate[2] >= "1900") {
                          if ((fromDate[2] % 4 == 0) || (fromDate[2] % 100 == 0) || (fromDate[2] % 400 == 0)) {
                              leap = 1;
                          }
                          if(leap == 1 && fromDate[0] == "02" && fromDate[1] > "29") {
                              dateFlag = false;
                          } else if(leap == 0 && fromDate[0] == "02" && fromDate[1] > "28") {
                               dateFlag = false;
                          }  else if((fromDate[0] == "04" || fromDate[0] == "06" || fromDate[0] == "11") && fromDate[0] != "02" && fromDate[1] > "30") {
                                dateFlag = false;
                          } else if((fromDate[0] == "01" || fromDate[0] == "03" || fromDate[0] == "05" || fromDate[0] == "07" || fromDate[0] == "08" || fromDate[0] == "10" || fromDate[0] == "12") && fromDate[1] > "31") {
                               dateFlag = false;
                          } else {
                                    /* if(dateType == 'enddate') {
                                          flag = verifyEndDate(date);
                                    } */
                          }
                    } else {
                              dateFlag = false;
                    }
                  }
                  else
                  {
                        dateFlag = false;
                  }
            }
           
            return dateFlag;

}
</script>
<SCRIPT TYPE="TEXT/JAVASCRIPT">

// process input

function proc() {

  var commissionPay = $("#specialDealerProgram_dlrPrgmForm_commAmount").val();
  $("#specialDealerProgram_dlrPrgmForm_commAmount").val(r2(commissionPay));
}

// Round to 2 decimal places

function r2(n) {

  var ans = n * 1000;
  ans = Math.round(ans /10) + "";
  while (ans.length < 3) {
  ans = "0" + ans;
  }
  var len = ans.length;
  ans = ans.substring(0,len-2) + "." + ans.substring(len-2,len);
  return ans;
}
</SCRIPT>
<body><s:if test="hasActionErrors()">
		<p></p>
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
<sj:head jquerytheme="flick"/>
<s:if test="hasActionMessages()"><p id="message"></p>
   <div class="messages" id="messages">
      <s:actionmessage/>
   </div>
</s:if>
<s:form action="specialDealerProgram" method="post">
<s:token/>
<div id="widecontentarea">
	<div class="pageTitle" id="HL1">
		DPB Special Program
	</div>
 
<div class="T8">
<table width="728" border="0" cellspacing="0" class="template8TableTop">
		<tr>
			<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
		</tr>
		<tr>
			<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Mercedes-Benz Dealer Performance Bonus Program, Special cases are considered as Special Programs. These programs are defined here. </div></td>
				<td width="363"  class="right">
				<div align="left">
					<img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			</div></td>
		</tr>
		<tr>
			<td colspan="2"><div class="template8BottomLine"></div></td>
		</tr>
</table>
</div>

<table width="200" border="0" class="TBL2">
	<s:hidden name="dlrPrgmForm.flagActive"  value = "%{dlrPrgmForm.flagActive}" id="flagActive" />
	<s:hidden name="dlrPrgmForm.inactiveDate"  value = "%{dlrPrgmForm.inactiveDate}" id="inactiveDate" />
	<s:hidden name="dlrPrgmForm.specialProgram" value='S' /> 
	<s:hidden name="commSelected" value = "%{dlrPrgmForm.commPayment}" id ="commSelected" />
	<tr><td width="30%"><s:label  value="Program Id" for="dlrPrgmForm.programId" />
	<td width="70%"><s:textfield  label="Program Id" name="dlrPrgmForm.programId"  readonly="true" id="programId"/></td>
		 
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.programName" value="Program Name"></s:label></td>
		<td><s:textfield   name="dlrPrgmForm.programName" /><span id="span1" ></span></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.description" value="Description" ></s:label></td>
		<td><s:textarea name="dlrPrgmForm.description"  cols="45" rows="5" /><span id="span2" ></span></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.vehicleType" value="Applicable Vehicles"  ></s:label></td>
		<td><s:checkboxlist  name="dlrPrgmForm.vehicleType" theme="vertical-checkbox" list ="vehicleList"  listKey="id"  listValue="vehicleType" id="vehType" value="dlrPrgmForm.vehicleType"/></td>
	</tr>
	 <tr>
		<td><s:label for="dlrPrgmForm.condition" value="Conditions"  ></s:label></td>
		<td><s:checkboxlist  theme="vertical-checkbox" name="dlrPrgmForm.condition" list="%{dlrPrgmForm.masterCondList}" listKey="id" listValue="conditionName" id="specialCondition"/>
		<s:if test='%{adminUser == true}'>
		 <s:a href="javascript:createCondition();">Create New Condition</s:a>
		 </s:if>
		</td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.startDate" value= "Start Date"  ></s:label></td>
		<td><sj:datepicker name="dlrPrgmForm.startDate" id="startDate" displayFormat="mm/dd/yy" showOn="focus"/><span id="span3" ></span></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.endDate" value="End Date" ></s:label></td>
		<td><sj:datepicker name="dlrPrgmForm.endDate" id="endDt" displayFormat="mm/dd/yy" showOn="focus"/><span id="span4" ></span></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.commPayment" value="Is Commission Amount?"  ></s:label></td>
		<td><s:radio name="dlrPrgmForm.commPayment" list="#{'Y':'Yes','N':'No'}" /></td>
	</tr>
	<tr>
		<td><s:label for="rebateAmount" value="Is Rebate Amount?"  ></s:label></td>
		<td><s:radio name="dlrPrgmForm.rebateAmt" list="#{'Y':'Yes','N':'No'}" /></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.commAmount" value="Commission Amount"  ></s:label></td>
	    <td><s:textfield name="dlrPrgmForm.commAmount" onchange="proc()"/><span id="span7" ></span></td>
	</tr>
<%-- 	<tr>
		<td><s:label for="dlrPrgmForm.commPercent" value="Commission Percentage"  ></s:label></td>
	    <td><s:textfield name="dlrPrgmForm.commPercent" /><span id="span8" ></span></td>
	</tr> --%>
	<tr>
		<td><s:label for="dlrPrgmForm.comsnProcessSchedule" value="Commission Process Schedule"></s:label></td>
		<td><s:select name="dlrPrgmForm.comsnProcessSchedule" list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"   /><span id="span22" ></span>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.comsnProcessTrigger" value="Commission Process Trigger" ></s:label></td>
		<td><s:radio name="dlrPrgmForm.comsnProcessTrigger" list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span23" ></span></td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.comsnPymtSchedule" value="Commission Payment Schedule"></s:label></td>
		<td><s:select name="dlrPrgmForm.comsnPymtSchedule"  list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"  /><span id="span24" ></span>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.comsnPymtTrigger" value="Commission Payment Trigger" ></s:label></td>
		<td><s:radio name="dlrPrgmForm.comsnPymtTrigger"  list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span25" ></span></td>
	</tr>			
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtBonusSchedule" value="Fixed Bonus Process Schedule"></s:label></td>
		<td><s:select name="dlrPrgmForm.fixedPymtBonusSchedule"  list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"   /><span id="span12" ></span>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtBonusTrigger" value="Fixed Bonus Process Trigger" ></s:label></td>
		<td><s:radio name="dlrPrgmForm.fixedPymtBonusTrigger" list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span19" ></span></td>
		
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtSchedule" value="Fixed Payment Schedule"></s:label></td>
		<td><s:select name="dlrPrgmForm.fixedPymtSchedule" list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"   /><span id="span10" ></span>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.fixedPymtTrigger" value="Fixed Payment Trigger" ></s:label></td>
		<td><s:radio name="dlrPrgmForm.fixedPymtTrigger" list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span18" ></span></td>		
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.maxVarPayment" value="Maximum Variable Payment?"  ></s:label></td>
		<td><s:radio name="dlrPrgmForm.maxVarPayment" list="#{'Y':'Yes','N':'No'}" /></td>
	</tr>
<%-- 	<tr>
		<td><s:label for="dlrPrgmForm.variablePayment" value="Variable Payment %"  ></s:label></td>
	    <td><s:textfield name="dlrPrgmForm.variablePayment" size="5"/>% <span id="span15" ></span></td>
	</tr> --%>
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtBonusSchedule" value="Variable Bonus Process Schedule"  ></s:label></td>
		<td><s:select name="dlrPrgmForm.variablePymtBonusSchedule"   list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"  /><span id="span17" ></span>
		</td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtBonusTrigger" value="Variable Bonus Process Trigger"  ></s:label></td>
		<td><s:radio name="dlrPrgmForm.variablePymtBonusTrigger" list="triggerList" listKey="triggerCode"  listValue="triggerName"/><span id="span21" ></span></td>
	</tr>
		
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtSchedule" value="Variable Payment Schedule"  ></s:label></td>
		<td><s:select name="dlrPrgmForm.variablePymtSchedule"  list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"  /><span id="span14" ></span>
		</td>
	</tr>
	<tr>
		<td><s:label for="dlrPrgmForm.variablePymtTrigger" value="Variable Payment Trigger"  ></s:label></td>
		<td><s:radio name="dlrPrgmForm.variablePymtTrigger"  list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span20" ></span></td>
	</tr>
	
	<tr>
		<td><s:label for="dlrPrgmForm.programStatus" value="Status"  ></s:label></td>
		<%-- <td><s:radio name="dlrPrgmForm.programStatus" list="#{'D':'Draft','A':'Active','I':'In-Active'}"/></td> --%>
		<td><s:radio name="dlrPrgmForm.programStatus" list ="defSts"  listKey="defStatusCode"  listValue="defStatusName" />
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp; </td>
	</tr>
</table>
<table>
	<tr>
		<td id="text13"><s:submit key="submit" method="saveSpecialProgram" align="right" value="Submit" action="dlrSpecialProgram"/></td>
		<td id="text14"><s:reset key="reset" value="Reset" align="center" onclick="formReset()"/></td>
		<s:if test="%{fromDPBList == true}">
		<td><s:submit key="Cancel" theme="simple" method="viewDealerListProgram" action="dpbProgramList"/></td>
	     <s:hidden name="fromDPBList" value="true" id="fromDPBList"/>
		</s:if><s:else>
		<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
		</s:else>
	</tr>
</table>
<br /><br /><br />
</div>
 
</s:form>
</body>
</html>