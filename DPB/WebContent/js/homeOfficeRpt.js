/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: homeOfficeRpt.js
 * Program Version			: 1.0
 * Program Description		: This js is used for Home Office Report validations.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 28, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

$(document).ready(function() {
	
	$("#genReptRAP").click(function() {
		
		//alert(date_regex);
		//var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
		var spans = $('.serror');
		var Name_Expression = /^([a-z\sA-Z0-9])*$/;
		var regEx=/^([a-z\sA-Z0-9,])*$/;
		
		var vehId=$("#vehicleId").val();
		var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
		var startDate = $("#fromDate").val();
		var endDate = $("#toDate").val();
		var fDate=startDate.replace(/-/g,"/");
		var tDate=endDate.replace(/-/g,"/");
		
		var currentDate = new Date();
		var fromDate = new Date(fDate);
		var toDate = new Date(tDate);
		var date =new Date();
		
		if(($('input:checkbox[name="vehicleType"]')[0].checked == false )   )
			{
				if(($('input:checkbox[name="vehicleType"]')[1].checked == false )   ){
				
							if(($('input:checkbox[name="vehicleType"]')[2].checked == false )   ){
						
										if(($('input:checkbox[name="vehicleType"]')[3].checked == false )   ){
											
														if(($('input:radio[name="level"]')[0].checked == false  ) && ($('input:radio[name="level"]')[1].checked == false  ) )
														{
																
																	if($('#vehicleId').val()==''  &&  $('#fromDate').val()=='' && $('#toDate').val()=='') 
																	
																		{
																		$("#span1").text(" Enter Franchise / VIN(s) / Level. /Form and To date ");
																		return false;
																	}			
														}
														
										}	
							}			
				}
			}						
				if($('#fromDate').val()!='')
				{	
				   if($.trim($('#toDate').val())=='')
				    {
				          $("#span2").text("Enter To Date.");
				          return false;
				          
				    }
				}
			
			 if(!verifyDate(startDate))
				 {
				 	$("#span2").text(" Please enter valid From Date.").show();
				 	return false;
				 }
			
			 else if($('#fromDate').val()!='')
				{	
				 	
				   if(!($('#fromDate').val().match(date_regex)))
				    {
				          $("#span2").text("Enter Date in MM/DD/YYYY.");
				          return false;      
				    }
				}
			 
			 if(!verifyDate(endDate))
			 {
				 $("#span2").text(" Please enter valid To Date.").show();
				 return false;
			 }
			
			 else if($('#toDate').val()!='')
				{	
					 if($.trim($('#fromDate').val())=='')
					    {
					          $("#span2").text("Enter From Date.");
					          return false;
					          
					    }
				   if(!($('#toDate').val().match(date_regex)))
				    {
				          $("#span2").text("Enter Date in MM/DD/YYYY.");
				          return false;     
				    }	   
					   if(toDate < fromDate){
							$("#span2").text("To Date should not be less than From Date.");
							 return false;
						}
				}
			 
			 if($.trim($('#vehicleId').val())!='' )
				{
				 	var val=$('#vehicleId').val();
				 	//checkLength($('#vehicleId').val());
				 	if(!verifyVehicles(val))
				 	{
				 		return false;
				 	}
					
				}
			 spans.text(''); 
	});
	
	
$("#genReptRP").click(function() {
		
	var spans = $('.serror');
	var startDate = $("#fromDate").val();
	var endDate = $("#toDate").val();
	var fDate=startDate.replace(/-/g,"/");
	var tDate=endDate.replace(/-/g,"/");
	
	var currentDate = new Date();
	var fromDate = new Date(fDate);
	var toDate = new Date(tDate);
	var date =new Date();

	var Name_Expression = /^([a-z\sA-Z0-9])*$/;
	var regEx=/^([a-z\sA-Z0-9,])*$/;
	
	var vehId=$("#vehicleId").val();
	var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
		
		 if ( $("#VIN-DealerLevel_0").prop("checked") ==false  )   {
				if($("#VIN-DealerLevel_1").prop("checked") ==false )	 
					{
						if($("#VIN-DealerLevel_2").prop("checked") ==false )	 
						{
							$("#span1").text("Select Report By VIN  / Dealears by Quarter / Vehicles by Quarter.");
							return false;
						}
					}
		}	
		 
		 
		
				if($("#VIN-DealerLevel_1").prop("checked") == true || $("#VIN-DealerLevel_2").prop("checked") == true )	 
					{				
							 if($.trim($('#fromDate').val()) !='' && $.trim($('#toDate').val())!='')
								 {
								 	$("#span1").text("Select View Account Balance by VIN.");
									return false;
								 }			
					}
				
				if($("#VIN-DealerLevel_1").prop("checked") == true  )	 
				{				
						 if($('#viewDealerLevelQuarter').val() == '-1')
							 {
							 	$("#span1").text("Select  Quarter.");
								return false;
							 }			
				}
				
				if($("#VIN-DealerLevel_2").prop("checked") == true  )	 
				{			
						 if($('#viewTotVehQuarter').val() == '-1')
							 {
							 	$("#span1").text("Select  Quarter.");
								return false;
							 }			
				}
		
			
		 if ( $("#VIN-DealerLevel_0").prop("checked") ==true  )   {
			 
			 if($.trim($('#fromDate').val())=='')
				{	
				          $("#span2").text("Enter From Date.");
				          return false;
				}
			 
			 if($.trim($('#toDate').val())=='')
				{	
				          $("#span2").text("Enter To Date.");
				          return false;
				}
		 }
		 
		 if(!verifyDate(startDate))
		 {
		 	$("#span2").text(" Please enter valid From Date.").show();
		 	return false;
		 }
	
	 else if($('#fromDate').val()!='')
		{	
		   if(!($('#fromDate').val().match(date_regex)))
		    {
		          $("#span2").text("Enter Date in MM/DD/YYYY.");
		          return false;      
		    }
		}
	 
	 if(!verifyDate(endDate))
	 {
		 $("#span2").text(" Please enter valid To Date.").show();
		 return false;
	 }
	
	 else if($('#toDate').val()!='')
		{	
		 if($.trim($('#fromDate').val())=='')
		    {
		          $("#span2").text("Enter From Date.");
		          return false;
		          
		    }
		   if(!($('#toDate').val().match(date_regex)))
		    {
		          $("#span2").text("Enter Date in MM/DD/YYYY.");
		          return false;     
		    }	   
			   if(toDate < fromDate){
					$("#span2").text("To Date should not be less than From Date.");
					 return false;
				}
		}
	 
	 spans.text(''); 	 				
	});
	
	
$("#genReptB").click(function() {

	var spans = $('.serror');
	var Name_Expression = /^([a-z\sA-Z0-9])*$/;
	var Expression = "^([A-Za-z][a-z\sA-Z,\s0-9]{1,40})$";
	var regEx=/^([a-z\sA-Z0-9,])*$/;
	var startDate = $("#fromDate").val();
	var endDate = $("#toDate").val();
	var reWhiteSpace = new RegExp("/^\s+$/");
	
	var fDate=startDate.replace(/-/g,"/");
	var tDate=endDate.replace(/-/g,"/");
	var fromDate = new Date(fDate);
	var toDate = new Date(tDate);

	var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;

	if ( $.trim($('#vehicleId').val())=='' &&  $.trim($('#dealerId').val())=='' )   {
	
			if($('#fromDate').val()=='' && $('#toDate').val()=='')
			{
				$("#span1").text("Enter Dealer ID / VIN(s) / From -To Date.");
				return false;
			}
		
	}		
	
			
		
		 if($.trim($('#vehicleId').val())!='' )
			{
			 	var val=$('#vehicleId').val();
			 	//checkLength($('#vehicleId').val());
			 	if(!verifyVehicles(val))
			 	{
			 		return false;
			 	}
				
			}
		 
		 if(!$.trim($('#dealerId').val())=='')
		{
			 if (!$('#dealerId').val().match(Name_Expression))
				{
					$("#span1").text("Dealer Id Should be Alpha-Numeric ");
					return false;
				}
			 if ($('#dealerId').val().length > 5)
				{
					$("#span1").text("Dealer Id Should  Contain 5 Chareter's.");
					return false;
				}
			 if($('#dealerId').val().match(/\ /)	)
				{	
					$("#span1").text("Dealer Id Should not contain any Space.");
					return false;
				}
		}

	 if($('#fromDate').val()!='')
		{	
		   if($.trim($('#toDate').val())=='')
		    {
		          $("#span2").text("Enter To Date.");
		          return false;
		          
		    }
		}
	
	 if(!verifyDate(startDate))
		 {
		 	$("#span2").text(" Please enter valid From Date.").show();
		 	return false;
		 }
	
	 else if($('#fromDate').val()!='')
		{	
		   if(!($('#fromDate').val().match(date_regex)))
		    {
		          $("#span2").text("Enter Date in MM/DD/YYYY.");
		          return false;      
		    }
		}
	 
	 if(!verifyDate(endDate))
	 {
		 $("#span2").text(" Please enter valid To Date.").show();
		 return false;
	 }
	
	 else if($('#toDate').val()!='')
		{	
			 if($.trim($('#fromDate').val())=='')
			    {
			          $("#span2").text("Enter From Date.");
			          return false;        
			    }
		   if(!($('#toDate').val().match(date_regex)))
		    {
		          $("#span2").text("Enter Date in MM/DD/YYYY.");
		          return false;     
		    }	   
			   if(toDate < fromDate){
					$("#span2").text("To Date should not be less than From Date.");
					 return false;
				}
		}
	 
	 spans.text(''); 			
});

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

function checkLength(val)
{
	
		var arr;
		
		if(val.indexOf(',') > -1)
		{
			arr=val.split(",");
			
			for(var i=0;i<=arr.length;i++)
			{	
				if ( arr[i].length > 17)
				{
					return false;
				}		
			}
		
		}
		
		else if(val.length > 17)
			{
				return false;
			}
	
		else
			{
				return true;
			}

}

function verifyVehicles(val)
{
	var regEx=/^([a-z\sA-Z0-9,])*$/;
	if(!val.match(regEx))
	{	
		$("#span1").text("VIN(S) Should Not contain any Space or Special Charecter.");
		return false;
	}
	else if(val.length > 235)
	{
		$("#span1").text("VIN(s) Should Contain 235 Chareter's.");
		return false;
	}	
	else if(val.match(/\ /)	)
	{	
		$("#span1").text("VIN(S) Should not contain any Space.");
		return false;
	}
	
	/*if(val.indexOf(",")== 0)
	{
		$("#span1").text("Remove Comma from Begining of the VIN(s).");
		return false;		
	}*/
	
	else if(!checkLength(val))
	{
		$("#span1").text("VIN Should not exceed 17 Charecter's.");
		return false;
	}
	else
	{
		return true;
	}
}

$('input:radio[name="viewAccountVin"]').change(function() {
	
	if($(this).val() == "D" || $(this).val() == "V")
		{
	
			  $('#fromDate').val("");
			  $('#toDate').val("");
		}
	if($(this).val() == "D")
	{
		
		$('#viewTotVehQuarter').val('-1');
	}
	if($(this).val() == "V")
	{
		$('#viewDealerLevelQuarter').val('-1');
	}
	if($(this).val() == "VIN")
	{
		$('#viewTotVehQuarter').val('-1');
		$('#viewDealerLevelQuarter').val('-1');
	}
	$('#display').empty();
});

	
});
