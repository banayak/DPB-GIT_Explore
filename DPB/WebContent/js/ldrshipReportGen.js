$(document).ready(function() {
	$('#Err').val("");
		
$("#ldrshipReport").click(function() {
	
	var dlr=$('#dealer').val();
	if(dlr == ''|| dlr == null)
	{
		alert('Please enter a dealer Id');
		return false;
	}
	var vehType=$("input:radio[name='vehicleType']:checked").val();
			
	var flag = true;
	var dealerSpanTxt = "";
				
	if($.trim($('#dealer').val())!='' )
	{
		var dealer=$('#dealer').val();
		dealerSpanTxt = validateDealer(dealer);
		if(dealerSpanTxt.length > 1)
			flag = false;
		else 
			flag = true;
		
		if( flag == true  )
		{
			$('#Err').val("S");
			fileLink(dlr,vehType);
		}
		else
		{
			$('#Err').val("E");
			$("#dealerErr").html(dealerSpanTxt);
		}
		return flag;
	}
								
});

$("#reset").click(function() {
	$("#dealerErr").html(" ");
	$('input:radio[name="vehicleType"]')[0].checked =true;
	$('#dealer').val("");
});
	
function validateDealer(dealer) {
	var regEx1=/^([0-9,])*$/;
	var dealerSpanTxt = "";
	
	if(!dealer.match(regEx1))
	{		
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer Id should not contain any letters or special characters.";	
	}
	if(dealer.length > 235)
	{
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(range) should contain only 235 characters.";	
	}	
	if($('#dealer').val().match(/\ /)	)
	{	
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(range) should not contain any spaces.";	
	}
		
	if(!checkLength(dealer))
	{
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer Id should be 5 characters.";	
	}
	return dealerSpanTxt;
}
	
function checkLength(val)
{
	var arr;
	var flag = true;
		
	if(val.indexOf(',') > -1)
	{
		arr=val.split(",");

		for(var i=0;i<arr.length;i++)
		{	
			if (val.length !=0 && arr[i].length != 5)
			{
				flag = false;
			}		
		}
	}

	else if(val.length !=0 && val.length != 5)
	{
		flag = false;
	}
	return flag;
}
});