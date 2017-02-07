<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
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
val.form.action = '<%=request.getContextPath()%>' +'/dashBoard.action';
}
</script>

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
	
</style><script>
 function formReset(){ 
 		$(".messages").hide();
		$("#span22").html(" ");
		$("#span2").html(" ");			
		$("#span1").html(" ");	
		$("#span6").html(" ");
		$("#span7").html(" ");
		$("#span15").html(" ");
		$("#span11").html(" ");
		$("#span10").html(" ");
		$("#span14").html(" ");
		$("#span13").html(" ");
		$("#span17").html(" ");
		$("#span12").html(" ");
		$("#span16").html(" ");
		$("#span3").html(" ");			
		$("#span4").html(" ");	
		$("#span6").html(" ");
		var inactiveDate = $("#inactiveDate").val();
		if(inactiveDate.length > 0){
		document.getElementById("text13").style.display = 'none';
		document.getElementById("text14").style.display = 'none';
		
		}
		
		
	 var paymentReset = $("#paymentSelected").val();
	        
	   
		     if(paymentReset=="Y"){
		     	$("#dealerProgram_dlrPrgmForm_fixedPayment").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerS").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerU").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerS").attr("disabled",false);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerU").attr("disabled",false);
			
			$("#dealerProgram_dlrPrgmForm_variablePymtSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePayment").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerU").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerU").attr("disabled",true);
		   }
		   if(paymentReset=="N") {
			   	$("#dealerProgram_dlrPrgmForm_variablePayment").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerS").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerU").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerS").attr("disabled",false);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerU").attr("disabled",false);
	
			$("#dealerProgram_dlrPrgmForm_fixedPayment").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerU").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerU").attr("disabled",true);
		   }
 }
 </script>
<script>  
$(document).ready(function() {

var status = $('input[name="dlrPrgmForm.programStatus"]:checked').val();
var flagActive = $("#flagActive").val();
var pay=$('input[name="dlrPrgmForm.paymentType"]:checked').val();

		
		$("#dealerProgram_dlrPrgmForm_fixedPayment").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_fixedPayment").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerS").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerU").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerS").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerU").attr("disabled",true);
	
		$("#dealerProgram_dlrPrgmForm_variablePymtSchedule").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_variablePayment").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_variablePymtTriggerS").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_variablePymtTriggerU").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerS").attr("disabled",true);
		$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerU").attr("disabled",true);
	   
		$('input[name="dlrPrgmForm.paymentType"][value="Y"]').click(function() {
			$("#dealerProgram_dlrPrgmForm_fixedPayment").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerS").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerU").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerS").attr("disabled",false);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerU").attr("disabled",false);
			
			$("#dealerProgram_dlrPrgmForm_variablePymtSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePayment").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerU").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerU").attr("disabled",true);
	 });
	       
	        
	   $('input[name="dlrPrgmForm.paymentType"][value="N"]').click(function() {
	 		$("#dealerProgram_dlrPrgmForm_variablePayment").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerS").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtTriggerU").attr("disabled",false); 
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerS").attr("disabled",false);
			$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerU").attr("disabled",false);
	
			$("#dealerProgram_dlrPrgmForm_fixedPayment").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerU").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerS").attr("disabled",true);
			$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerU").attr("disabled",true);
		   });
		     if(pay=="Y"){
		     	$("#dealerProgram_dlrPrgmForm_fixedPayment").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerS").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_fixedPymtTriggerU").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerS").attr("disabled",false);
				$("#dealerProgram_dlrPrgmForm_fixedPymtBonusTriggerU").attr("disabled",false);
		   }
		   if(pay=="N") {
			   	$("#dealerProgram_dlrPrgmForm_variablePayment").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_variablePymtSchedule").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_variablePymtTriggerS").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_variablePymtTriggerU").attr("disabled",false); 
				$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerS").attr("disabled",false);
				$("#dealerProgram_dlrPrgmForm_variablePymtBonusTriggerU").attr("disabled",false);
		   }
		  
		   // Form Validations
		  // specialDealerProgram_dlrPrgmForm_commPaymentN
		  
	$("#dealerProgram_Submit").click(function() {
				$(".messages").hide();	
				$( ".span" ).empty(); 
				$("#span22").html(" ");
				$("#span2").html(" ");			
				$("#span1").html(" ");	
				$("#span6").html(" ");
				$("#span7").html(" ");
				$("#span15").html(" ");
				$("#span11").html(" ");
				$("#span10").html(" ");
				$("#span14").html(" ");
				$("#span13").html(" ");
				$("#span17").html(" ");
				$("#span12").html(" ");
				$("#span16").html(" ");
				$("#span3").html(" ");			
				$("#span4").html(" ");	
				$("#span6").html(" ");
				var flagActive = $("#flagActive").val();
				var flag = true;
				var status = $('input[name="dlrPrgmForm.programStatus"]:checked').val();
					
									
					if(status == 'D')
					{
						var prgName=trim($("#dealerProgram_dlrPrgmForm_programName").val());
						$("#dealerProgram_dlrPrgmForm_programName").val(prgName);
						var regex_length ="^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
						var date_regex = "^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$";
						var desc = trim($("#dealerProgram_dlrPrgmForm_description").val());
						$("#dealerProgram_dlrPrgmForm_description").val(desc);
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
			        }else if(status=='A' && flagActive == 'false'){
			        	var prgName=trim($("#dealerProgram_dlrPrgmForm_programName").val());
						$("#dealerProgram_dlrPrgmForm_programName").val(prgName);
						var regex_length = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
						var regex_number = "^[0-9]+([\.][0-9]+)?$";
						var desc = trim($("#dealerProgram_dlrPrgmForm_description").val());
						$("#dealerProgram_dlrPrgmForm_description").val(desc);
						var startDate = $("#startDate").val();
						var endDate = $("#endDt").val();
						var pay=$('input[name="dlrPrgmForm.paymentType"]:checked').val();
						var fpTrigger = $('input[name="dlrPrgmForm.fixedPymtTrigger"]:checked').val();
						var fbTrigger = $('input[name="dlrPrgmForm.fixedPymtBonusTrigger"]:checked').val();
						var vpTrigger = $('input[name="dlrPrgmForm.variablePymtTrigger"]:checked').val();
						var vbTrigger = $('input[name="dlrPrgmForm.variablePymtBonusTrigger"]:checked').val();
						var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
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
							
							flag = true;
							if(!prgName.match(regex_length)){
			              		$("#span1").text(" Name should be up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
			              		flag = false;
							} else{
									$("#span1").text(" Name should be up to 40 characters. May contain alphanumeric, spaces and underscores.").hide();
							}
							if (prgName.length >40) {
								$("#span1").text(" Name should be up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
			              		flag = false;
							} 
							
						}
			            if (desc.length > 255) {
									$("#span2").text(" Description should be less than 255 characters.").show();
									flag = false;
								} /* else {
								$("#span2").text("Enter description").hide();
								} */
						 
						
						 if(fDate == ""){
							$("#span3").text(" Enter Start Date.").show();
							flag = false;
				 		} 
						if(tDate == ""){
							$("#span4").text(" Enter End Date.").show();
							flag = false;
						} else {
							$("#span4").text(" Enter Start Date.").hide();
							if(toDate < fromDate){
								$("#span4").text(" End Date should not be less than Start Date.").show();
								flag = false;
							}else {
								
								//COMMENTED TEMPORARILY FOR TESTING
								//TODO: NEED TO ENABLE AFTER THE TESTING
								/* if((fromDate.getDate() < tomorrow.getDate()) && (fromDate.getMonth() <= tomorrow.getMonth()) && (fromDate.getFullYear() <= tomorrow.getFullYear())){
									$("#span3").text(" Start Date should be greater than today's date.").show();
										flag = false;
									}else { */
								//ADDED THE BELOW ONE IF CONDITION IN THE PLACE OF ELSE..
								//TODO: NEED TO REMOVE AFTER THE TESTING	
								if(!((fromDate.getDate() < tomorrow.getDate()) && (fromDate.getMonth() <= tomorrow.getMonth()) && (fromDate.getFullYear() <= tomorrow.getFullYear()) )){	
									if((toDate.getDate() < tomorrow.getDate()) && (toDate.getMonth() <= tomorrow.getMonth()) && (toDate.getFullYear() <= tomorrow.getFullYear()))/* toDate < tomorrow) */{
									$("#span4").text(" End Date not be less than Start Date.").show();
										flag = false;
									}
								}
						
							if(!verifyDate(startDate))  {
							      $("#span3").text(" Please enter valid Start Date.").show();
							      flag = false;
							      }
							
							if(!verifyDate(endDate))  {
							      $("#span4").text(" Please enter valid End Date.").show();
							      flag = false;
							      }
							}	
						} 
						
						if(pay!="Y" && pay!="N"){
							$("#span5").text(" Select a Payment Type.").show();
							flag = false;
						} else {
							$("#span5").text(" Select a Payment Type.").hide();
						} 
						if(pay=="Y")
						{
							if($("#dealerProgram_dlrPrgmForm_fixedPayment").val()==0){
								$("#span6").text(" Enter Fixed Payment %.").show();
								flag = false;
							}else{
								if($("#dealerProgram_dlrPrgmForm_fixedPayment").val()>100){
									$("#span6").text(" Please enter valid Fixed Payment %.").show();
									flag = false;
								} else {
									var fp = $("#dealerProgram_dlrPrgmForm_fixedPayment").val();
									 if (!(fp).match(regex_number)){
									 $("#span6").text(" Please enter valid Fixed Payment %.").show();
									flag = false;
									 }
								}
								
								if($("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").val()=="" && fbTrigger !=""){
								$("#span11").text(" Select a Schedule Type.").show();
								flag = false;
								} else{
								
								if(fbTrigger == undefined && $("#dealerProgram_dlrPrgmForm_fixedPymtBonusSchedule").val()!=""){
									$("#span15").text(" Select a Schedule Type.").show();
									flag = false;
								}else{
									
									 if($("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").val()=="" && fpTrigger!=""){
									$("#span10").text(" Select a Schedule Type.").show();
									flag = false; 
									 }else{
									
									if(fpTrigger == undefined && $("#dealerProgram_dlrPrgmForm_fixedPymtSchedule").val() !=""){
									$("#span14").text(" Select a Trigger Type.").show();
									flag = false;
									}else {
									
									}
									}
									}
								}
							}
						}else if(pay=="N"){
							if($("#dealerProgram_dlrPrgmForm_variablePayment").val()==0){
								$("#span7").text(" Enter Variable Payment %.").show();
								flag = false;
							}else {
								$("#span7").text(" Enter Variable Payment %.").hide();
								if($("#dealerProgram_dlrPrgmForm_variablePayment").val()>100){
									$("#span7").text(" Please enter valid Variable Payment %.").show();
									flag = false;
								}else {
								var vp= $("#dealerProgram_dlrPrgmForm_variablePayment").val();
									 if (!(vp).match(regex_number)){
									 $("#span7").text(" Please enter valid Variable Payment %.").show();
									flag = false;
								 }
								else {
								if($("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").val()=="" && vbTrigger!=""){
									$("#span13").text(" Select a Schedule Type.").show();
									flag = false;
								}if(vbTrigger == undefined &&  $("#dealerProgram_dlrPrgmForm_variablePymtBonusSchedule").val()!="" ){
									$("#span17").text(" Select a Trigger Type.").show();
									flag = false;
								}else {
									if($("#dealerProgram_dlrPrgmForm_variablePymtSchedule").val()=="" && vpTrigger!=""){
									$("#span12").text(" Select a Schedule Type.").show();
									flag = false;
								}else{
									if(vpTrigger == undefined &&  $("#dealerProgram_dlrPrgmForm_variablePymtSchedule").val()!="" ){
									$("#span16").text(" Select a Trigger Type.").show();
									flag = false;
								}
								}
								}
								}
								}
							
						}
						}
						
						
					}//END OF ACTIVE
					
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
<SCRIPT TYPE="TEXT/JAVASCRIPT">

// process input

function proc() {

  var variablePay = $("#dealerProgram_dlrPrgmForm_variablePayment").val();
  var fixedPay = $("#dealerProgram_dlrPrgmForm_fixedPayment").val();
  $("#dealerProgram_dlrPrgmForm_variablePayment").val(r2(variablePay));
  $("#dealerProgram_dlrPrgmForm_fixedPayment").val(r2(fixedPay));
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
    document.getElementById("flagActive").value =false;
}
} */

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
                          }  else if((fromDate[0] == "04" || fromDate[0] == "06" || fromDate[0] == "09"|| fromDate[0] == "11") && fromDate[0] != "02" && fromDate[1] > "30") {
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


function trim(str)
{
      if((str != "") && (str.indexOf(" ",0)!=-1))
      {
            var iMax = str.length;
            var end = str.length;
            var c;
            for(i=0;1<iMax;i++)
            {
                  c = str.substring(0,1);
                  if (c == " ")
                  {
                        str=str.substring(1,end);
                        end = str.length;
                  }
                  else
                        break;
            }
            iMax = str.length;
            end = str.length;
            for(i=iMax;i>0;i--)
            {
                  c = str.substring(end-1,end);
                  if (c == " ")
                  {                       
                        str=str.substring(0,end-1);
                        end = str.length;
                  }
                  else
                        break;
            }
      }
      return str;
}
</script>
</head>
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

<s:form action="dealerProgram" method="post">
<s:token/>
<s:hidden name="dlrPrgmForm.flagActive"  value = "%{dlrPrgmForm.flagActive}" id="flagActive" />
<s:hidden name="dlrPrgmForm.specialProgram" value='G' />
<s:hidden name="dlrPrgmForm.inactiveDate"  value = "%{dlrPrgmForm.inactiveDate}" id="inactiveDate" />
<s:hidden name="paymentSelected" value="%{dlrPrgmForm.paymentType}" id="paymentSelected" />
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
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Dealer Performance Bonus Programs provide
						 all qualified authorized dealers in good standing an opportunity to earn a performance bonus of up to 5.50%
						 for MB and 3.5% for Vans & smart of Manufacturers Suggested Retail Price (MSRP) on eligible transactions.</div></td>
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

<table width="200" border="0" class="TBL2">
		<tr>
			<td width="30%"><s:label  value="Program Id" for="dlrPrgmForm.programId" /></td>
			<td width="70%"><s:textfield  label="Program Id" name="dlrPrgmForm.programId"  readonly="true" id="programId"/></td>
		</tr>
		<tr>
			<td><s:label for="programName" ></s:label>Program Name</td>
			<td><s:textfield   name="dlrPrgmForm.programName"/><span id="span1" ></span></td>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.kpiId" value="KPI" ></s:label></td>
			<td><s:select name="dlrPrgmForm.kpiId" list ="kpiList"  listKey="kpiId"  listValue="kpiName"/></td>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.description" value="Description" ></s:label></td>
			<td><s:textarea name="dlrPrgmForm.description"  cols="45" rows="5" maxlength=225/><span id="span2"></span></td>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.vehicleType" value="Applicable Vehicles"  ></s:label></td>
			<td><s:checkboxlist  name="dlrPrgmForm.vehicleType" theme="vertical-checkbox" list ="vehicleList"  listKey="id"  listValue="vehicleType" id="vehType" value="dlrPrgmForm.vehicleType"/></td>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.startDate" value= "Start Date"  ></s:label></td>
			<td><sj:datepicker name="dlrPrgmForm.startDate" id="startDate" displayFormat="mm/dd/yy" showOn="focus"/><span id="span3"></span></td>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.endDate" value="End Date" ></s:label></td>
			<td><sj:datepicker name="dlrPrgmForm.endDate" id="endDt" displayFormat="mm/dd/yy" showOn="focus"/><span id="span4"></span></td>
		</tr>
		<tr>
			<td><s:label for="paymentType" value="Payment Type"  ></s:label></td>
			<td><s:radio list="#{'Y':'Fixed Payment','N':'Variable Payment'}"  name="dlrPrgmForm.paymentType" value="dlrPrgmForm.paymentType"/><span id="span5"></span></td>
		</tr>
		<tr id="showFixed">
			<td><s:label for="dlrPrgmForm.fixedPayment" value="Fixed Payment%" ></s:label></td>
			<td><s:textfield name="dlrPrgmForm.fixedPayment" size="6" onchange="proc()"/>%<span id="span6"></span></td>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.fixedPymtBonusSchedule" value="Fixed Bonus Process Schedule"></s:label></td>
			<td><s:select name="dlrPrgmForm.fixedPymtBonusSchedule" label="Fixed Bonus Process Schedule" list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"/><span id="span11"></span>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.fixedPymtBonusTrigger" value="Fixed Bonus Process Trigger" ></s:label></td>
			<td><s:radio name="dlrPrgmForm.fixedPymtBonusTrigger" label = "Fixed Bonus Process Trigger" list="triggerList" listKey="triggerCode"  listValue="triggerName" value="dlrPrgmForm.fixedPymtBonusTrigger" /><span id="span15"></span></td>
		</tr>
			
		<tr id="showFixedS">
			<td><s:label for="dlrPrgmForm.fixedPymtSchedule" value="Fixed Payment Schedule"></s:label></td>
			<td><s:select name="dlrPrgmForm.fixedPymtSchedule" label="Fixed Payment Schedule" list="scheduleList" listKey="scheduleCode"  listValue="scheduleName" /><span id="span10"></span>
		</tr>
		<tr id="showFixedT">
			<td><s:label for="dlrPrgmForm.fixedPymtTrigger" value="Fixed Payment Trigger" ></s:label></td>
			<td><s:radio name="dlrPrgmForm.fixedPymtTrigger" label="Fixed Payment Trigger"  list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span14"></span></td>
		</tr>
	
		<tr id="showVariable">
			<td><s:label for="dlrPrgmForm.variablePayment" value="Variable Payment%"  ></s:label></td>
			<td><s:textfield name="dlrPrgmForm.variablePayment" label="Variable Payment" size="6" onchange="proc()"/>%<span id="span7"></span></td>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.variablePymtBonusSchedule" value="Variable Bonus Process Schedule"  ></s:label></td>
			<td><s:select name="dlrPrgmForm.variablePymtBonusSchedule" label="Variable Bonus Process Schedule" list="scheduleList" listKey="scheduleCode"  listValue="scheduleName" /><span id="span13"></span>
		</tr>
		<tr>
			<td><s:label for="dlrPrgmForm.variablePymtBonusTrigger" value="Variable Bonus Process Trigger"  ></s:label></td>
			<td><s:radio name="dlrPrgmForm.variablePymtBonusTrigger" label="Variable Bonus Process Trigger"  list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span17" ></span></td>
		</tr>		
		<tr id="showVariableS">		
			<td><s:label for="dlrPrgmForm.variablePymtSchedule" value="Variable Payment Schedule"  ></s:label></td>
			<td><s:select name="dlrPrgmForm.variablePymtSchedule" label="Variable Payment Schedule" list="scheduleList" listKey="scheduleCode"  listValue="scheduleName"  /><span id="span12"></span>
			</td>
		</tr>	
		<tr id="showVariableT">
			<td><s:label for="dlrPrgmForm.variablePymtTrigger" value="Variable Payment Trigger"  ></s:label></td>
			<td><s:radio name="dlrPrgmForm.variablePymtTrigger" label="Variable Payment Trigger"  list="triggerList" listKey="triggerCode"  listValue="triggerName" /><span id="span16"></span></td>
		</tr>

		<tr>
			<td><s:label for="dlrPrgmForm.programStatus" value="Status"  ></s:label></td>
			<%-- <td><s:radio label="Status" name="dlrPrgmForm.programStatus" list="#{'D':'Draft','A':'Active','I':'In-Active'}" /></td> --%>
			<td><s:radio name="dlrPrgmForm.programStatus" list ="defSts"  listKey="defStatusCode"  listValue="defStatusName" />
		</tr>
		
</table>
<table>
	<tr>
		<td id="text13"><s:submit key="Submit" method="submitDealerProgram" align="right" action="dlrProgram"/></td>
		 <td id="text14"><s:reset key="Reset" align="center" onclick="formReset()"/></td> 
		<!--  <td><input type="button" name="reset" id="reset" value="Reset" onclick="javascript:formReset()"></td> -->
		<s:if test="%{fromDPBList == true}">
		<td><s:submit key="Cancel" theme="simple" method="viewDealerListProgram" action="dpbProgramList"/></td>
	     <s:hidden name="fromDPBList" value="true" id="fromDPBList"/>
		</s:if><s:else>
		<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
		</s:else>
		<%-- <td><s:submit method="cancelDealerProgram" value="Cancel" name="Cancel" /></td> --%> 
		<%-- <td><s:submit type="button" name="button" key="cancel" value="Cancel" onclick="cancel(this)"/></td> --%>
	</tr>
</table>

<br /><br /><br/>
</div>
</s:form>
</body>
</html>