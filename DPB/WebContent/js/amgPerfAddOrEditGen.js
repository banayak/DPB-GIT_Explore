/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 *  *********************************************************************************************/

function getDealerName(){
	
	location.href = "amgPerfAddOrEditretrieveAMGDealerName.action?dealerIdValue="+$("#dealerId").val();	
}
function resetFormData() {
	
	
	$("#dealerId").val("");
	$("#dealerName").val("");	
	document.getElementById("retailYear").value = "-1";
	document.getElementById("retailStartMonth").value = "-1";
	document.getElementById("retailEndMonth").value = "-1";
	$("#dealerIdError").hide();	
	
	
}

function restoreData(rowId){
	
	var rowIndex = rowId.substring(7,rowId.length);
	
	$('input[name=result]').filter(':checked').removeAttr('checked');
	
	$("#dealerId").val(rowIndex);
	
	$("#dealerName").val($("#dealerName_"+rowIndex).text());
	
	
	//$("input:radio[name='dealerType'][value ='ignore']").prop('checked', true);

	if($("#dealerType_"+rowIndex).text()=='ELT'){		
		$('input:radio[name=dealerType]')[0].checked = true;
	}
	else{
		$('input:radio[name=dealerType]')[1].checked = true;
	}
	
	var startDate = $("#retailStartMonth_"+rowIndex).text();
	var retailYr = startDate.substring(0,4);
	var startMonth = startDate.substring(5,7);
	var enddate = $("#retailEndMonth_"+rowIndex).text();
	var endMonth = enddate.substring(5,7);
	
	document.getElementById("retailYear").value = retailYr;
	document.getElementById("retailStartMonth").value = startMonth;
	document.getElementById("retailEndMonth").value = endMonth;
	
}

$(document).ready(function() {

	$("#searchDealerButton").click(function() {
		
		$("#display").empty();	
		var spanDealerIdError = "";
		var flag = true;
		var dealer = $.trim($('#dealerId').val());
		$('#dealerId').val(dealer);
		if(dealer == ''){
			spanDealerIdError = spanDealerIdError + "<br>Enter Dealer Id. It should not be blank.";
			flag = false;
		}
		if(dealer != '' )
		{
			spanDealerIdError = validateDealer(dealer);
			if(spanDealerIdError.length > 1)
			{
				flag = false;
			}
		}
		$("#dealerIdError").html(spanDealerIdError);
		
		return flag;			 

	});	
	$("#submitDealerButton").click(function() {
	
		$("#display").empty();		
		var spanDealerIdError = "";
		var retailYearError = "";
		var startMonthError = "";
		var endMonthError = "";
		
		document.getElementById("submitDealerButton").name = "method:modifyAMGDealerInfo";
		var flag = true;
		var dealer = $.trim($('#dealerId').val());
		$('#dealerId').val(dealer);
		if(dealer == ''){
			spanDealerIdError = spanDealerIdError + "<br>Enter Dealer Id. It should not be blank.";
			flag = false;
		}
		if(dealer != '' )
		{
			spanDealerIdError = validateDealer(dealer);
			if(spanDealerIdError.length > 1)
			{
				flag = false;
			}
		}
		
		$("#dealerIdError").html(spanDealerIdError);
		
		var retailYear = $.trim($('#retailYear').val());
		if(retailYear == -1) {
			
			retailYearError = retailYearError + "<br>Select valid Retail Year";
			flag = false;
		}
		
		$("#retailYearIdError").html(retailYearError);
		
		
		var startMonth = $.trim($('#retailStartMonth').val());
		if(startMonth == -1) {
			
			startMonthError = startMonthError + "<br>Select valid Start Month";
			flag = false;
		}
		
		$("#startMonthIdError").html(startMonthError);
		
		return flag;			 

	});
	

	function validateDealer(dealer) {

		var regEx=/^([a-z\sA-Z0-9,])*$/;
		var dealerSpanTxt = "";

		if(!dealer.match(regEx))
		{		
			dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should not contain special character.";	
		}
		if(dealer.length < 5)
		{
			dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should contain 5 digits.";	
		}
		if(dealer.length > 5)
		{
			dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should contain 5 digits.";	
		}
		if($.trim($('#dealerId').val()).match(/\ /))
		{	
			dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should not contain any space.";	
		}
		
		return dealerSpanTxt;
	}
});