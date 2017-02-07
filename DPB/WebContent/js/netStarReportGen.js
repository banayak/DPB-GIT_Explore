/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: netStarReportGen.js
 * Program Version			: 1.0
 * Program Description		: This js is used for Report query Def  validations.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   oct 15, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/



$(document).ready(function() {
	
	var spans = $('.serror');
	spans.text(''); 	
	var valueDrp = $("#dynamicRpt").val();
	
	if( valueDrp == -1)
		{
			$("#netStr tbody tr.row10").hide();
			$("#netStr tbody tr.row11").hide();
			$("#netStr tbody tr.row12").hide();
			$("#netStr tbody tr.row13").hide();
			$("#netStr tbody tr.row5").hide();
			$("#netStr tbody tr.row6").hide();
			$("#netStr tbody tr.row7").hide();
			$("#netStr tbody tr.row8").hide();
			$("#netStr tbody tr.row9").hide();
			$("#netStr tbody tr.rowVeh").hide();
		}
	
		else
		{
			$("#netStr tbody tr.row10").show();
		}
	 
	$('#Err').val("");
	
		
	$("#genDynamicReport").click(function() {
		var spans = $('.serror');
		  $('#Err').val("");
		var regEx=/^([a-z\sA-Z0-9,])*$/;
		var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
		var startDate = $("#fromDate").val();
		
		var endDate = $("#toDate").val();
		var actualDate = $("#reportDate").val();
		var fDate=startDate.replace(/-/g,"/");
		var tDate=endDate.replace(/-/g,"/");
		var rDate=actualDate.replace(/-/g,"/");
		var fromDate = new Date(fDate);
		var toDate = new Date(tDate);
		var repDate = new Date(rDate);
		var date=new Date();
		var tomorrow = new Date();
		
		var rType=$('#dynamicRpt').val();
		var rptOcc=rType.substring( rType.indexOf("_")+1 );
		
		//var select=$("#dynamicRpt option:selected").text();
		var yr= $('#year').val();
		var mnth= $('#month').val();
		var dlr=$('#dealer').val();
		var vehType="";
			//$('#vehicleTypeRd').val();
		//alert(vehType);
		
		//alert(temp);
		/*if($('input:radio[name="reportType"]')[1].checked )
		{*/
			/*if(rType == -1)
				{
					$('#Err').val("E");
					$("#span3").text("Select Report.");
					return false;
				}*/
		//}
		if(rptOcc == "M" ||  rptOcc == "Y" )
		  {
				
				var flag = true;
				var yearSpanTxt = "";
				var dealerSpanTxt = "";
				var monthErrTxt = "";
				var span12Txt = "";
				var year= $('#year').val();
				var month= $('#month').val();
				
				if($.trim($('#dealer').val()) == '' && (year <= 0) && month == -1 )
				{
						span12Txt = span12Txt + "Enter Year, Month and/or Dealer(range).";
						flag = false;
				}
				else
					{
						if($.trim($('#dealer').val())!='' )
						{
								if(month == -1 && (year <= 0) )
									{
										span12Txt = span12Txt + "Enter Year and Month.";
										flag = false;
									}
								var dealer=$('#dealer').val();
								dealerSpanTxt = validateDealer(dealer);
								if(dealerSpanTxt.length > 1)
								{	//alert(dealerSpanTxt);
									flag = false;
								}
							
						}
						
						if( (year != 0) && $.trim(year) != '' )
						{
							if(month == -1)
								{
									monthErrTxt = monthErrTxt +"<br>Select  Month.";
									flag = false;
								}
							else if($.trim($('#dealer').val())!='' && month == -1 )
								{
									monthErrTxt = monthErrTxt +"<br>Select Month.";
									flag = false;
								}
						}
						if( month != -1 )
						{
							if( year <= 0)
							{	
								yearSpanTxt = yearSpanTxt + "<br>Enter Year.";
								flag = false;
							}
							else if($.trim($('#dealer').val())!='' && year <= 0 )
							{
								yearSpanTxt = yearSpanTxt + "<br>Enter Year.";
								flag = false;
							}
						}
						
						
						if(month != -1 && month > date.getMonth()+1 && year >= date.getFullYear()){
							monthErrTxt = monthErrTxt +"<br>Do not select future month.";	
							flag = false;
						}
						
						yearSpanTxt = yearSpanTxt + validateYear(year);
						if(yearSpanTxt.length > 0){
							flag = false;
						}
						
						else if((year > 0) && year > date.getFullYear()){
							
							yearSpanTxt = yearSpanTxt + "<br>Do not select future year.";
							flag = false;
						}
		
							if( flag == false )
							{
								//alert(11);
								$('#Err').val("E");
							}
					}
					$("#yearErr").html(yearSpanTxt);	
					$("#dealerErr").html(dealerSpanTxt);
					$("#monthErr").html(monthErrTxt);
					$("#span12").text(span12Txt);
					//$("#spanrec").text('');
					
					if( flag == true && rptOcc == "M" )
					{
							$('#Err').val("S");
							if($('#year').val() <= 0){
								yr = -1;
							}
							fileLink(startDate,endDate,actualDate,dlr,rType,yr,mnth,vehType);
					}
					if( flag == true && rptOcc == "Y" )
					{
						vehType=$("input:radio[name='netStartRpt.vehicleTypeRd']:checked").val();
							$('#Err').val("S");
							if($('#year').val() <= 0){
								yr = -1;
							}
						  fileLink(startDate,endDate,actualDate,dlr,rType,yr,mnth,vehType);
					}
					
				return flag;
		  }
		  	  
		  if( rptOcc == "D" )
		  {
			  		var span21Text = "";
					var span22Text = "";
					var span12Text = "";
					var flag = true;
					var yearSpanTxt = "";
					var dealerSpanTxt = "";
				    var rptDtSpanTxt = ""; 
				   // alert($('#reportDate').val());
					  if($('#fromDate').val()=='' && $('#toDate').val()=='' && $.trim($('#dealer').val())=='' && $('#reportDate').val()=='')
					  {
						  span12Text = span12Text+"Enter From -To Date / Dealer(range) / Retail Date.";		
						  flag = false;
					  }
					  else
						  {
								if($('#reportDate').val()!='')
								{	
									rptDtSpanTxt = validateReportDate(actualDate,repDate);
									if(rptDtSpanTxt.length  > 1)
									{	
										flag = false;
									} 
									
									if($('#fromDate').val() !='' || $('#toDate').val() != ''){
										  span22Text = span22Text +"<br>Enter either From - To Date or Retail Date .";
											flag = false;
									  }
								}
								
							   
							  else{
								  if($.trim($('#toDate').val())=='' && $.trim($('#fromDate').val()) !='')
									{
										span22Text = span22Text+"<br>Enter To Date."; 
										flag = false;
									}
									else if($.trim($('#fromDate').val())=='' && $.trim($('#toDate').val())!='')
									{
										span21Text = span21Text+"<br>Enter From Date.";
										flag = false;
									}	   
					
									if($('#fromDate').val()!='')
									{	
										if(!($('#fromDate').val().match(date_regex)))
										{
											span21Text = span21Text+"<br>Enter date in MM/DD/YYYY.";    
											flag = false;
										}
										
										if(fromDate > tomorrow ){
											   span21Text = span21Text+"<br>From Date should not be greater than Current Date.";
											   flag = false; 
											}
										
										if(!verifyDate(startDate))
										{
											span21Text = span21Text+"<br>Please enter valid From Date.";
											flag = false;
										}
										if(!verifyDate(endDate))
										{
											flag = false;
											span22Text = span22Text+"<br>Please enter valid To Date."; 
										}
									}
									
									if($('#toDate').val()!='')
									{	
										if(!($('#toDate').val().match(date_regex)))
										{
											span22Text = span22Text+"<br>Enter date in MM/DD/YYYY.";   
											flag = false;
										}	   
										if(toDate < fromDate){
											span22Text = span22Text+"<br>To Date should not be less than From Date.";  
											flag = false;
										}
										
										if( toDate > tomorrow ){
											   span22Text = span22Text+"<br>To Date should not be greater than Current Date.";
											   flag = false; 
											}
									}
					 
							  }
							  
							if($.trim($('#dealer').val())!='' )
								{
									var dealer=$('#dealer').val();
									dealerSpanTxt = validateDealer(dealer);
									if(dealerSpanTxt.length > 1)
									{	//alert(dealerSpanTxt);
										flag = false;
									}
								}
								
						  }	
								
								if( flag == false)
								{
									//alert(1);
									$('#Err').val("E");
								}
								//$("#span12").html(" ");
						
								$("#span22").html(span22Text);
								$("#span21").html(span21Text);
								$("#span12").html(span12Text);
								$("#rptDtErr").html(rptDtSpanTxt);
								$("#dealerErr").html(dealerSpanTxt);
								
								//$("#spanrec").empty();
								
								if( flag == true )
								{
										$('#Err').val("S");
									  fileLink(startDate,endDate,actualDate,dlr,rType,yr,mnth,vehType);
								}
								
								
						return flag;
				
		         }
		  
	});

	$("#dynamicRpt").change(function() {
		
		
		  val = $(this).val();
		  //val = $("#dynamicRpt").val();
		  //value=$('#val :selected').text();
		 // alert(value);
		  $('#display').empty();
		  
		  $('#dealer').val("");
		  $('#fromDate').val("");
		  $('#toDate').val("");
		  $('#reportDate').val("");
		  //$('#year').val('-1');
		  $("#year").val(new Date().getFullYear());
		  $('#month').val('-1');
		 // $(':checkbox').prop('checked', false);
		  $("#span22").html("");
		  $("#span21").html("");
		  $("#rptDtErr").html("");
		  $("#dealerErr").html("");
		  $("#yearErr").html("");
		  $("#monthErr").html("");
		  var rType=val;
		  var rptOcc=rType.substring( rType.indexOf("_")+1 );
	
		  var spans = $('.serror');
		  spans.text(''); 
		  
		  
		  if(val == -1)
		  { 
			  $("#netStr tbody tr.row5").hide();
			  $("#netStr tbody tr.row6").hide();
			  $("#netStr tbody tr.row7").hide();
			  $("#netStr tbody tr.row8").hide();
			  $("#netStr tbody tr.row9").hide();
			  $("#netStr tbody tr.row10").hide();
			  $("#netStr tbody tr.row11").hide();
			  $("#netStr tbody tr.row12").hide();
			  $("#netStr tbody tr.row13").hide();
			  $("#netStr tbody tr.rowVeh").hide();
			 
		  }
		  
		  if( rptOcc == "M" )
		  {
			  $("#netStr tbody tr.row5").show();
			  $("#netStr tbody tr.row6").show();
			  $("#netStr tbody tr.row7").show();
			  $("#netStr tbody tr.row8").show();
			  $("#netStr tbody tr.row10").show();
			  $("#netStr tbody tr.row7").hide();
			  $("#netStr tbody tr.row9").hide();
			  $("#netStr tbody tr.row11").hide();
			  $("#netStr tbody tr.row12").hide();
			  $("#netStr tbody tr.row13").hide();
			  $("#netStr tbody tr.rowVeh").hide();
			 
		  }
		  
		  if(rptOcc == "D")
		  {
			  $("#netStr tbody tr.row7").show();
			  $("#netStr tbody tr.row8").show();
			  $("#netStr tbody tr.row9").show();
			  $("#netStr tbody tr.row10").show();
			  $("#netStr tbody tr.row11").show();
			  $("#netStr tbody tr.row12").show();
			  $("#netStr tbody tr.row13").show();
			  $("#netStr tbody tr.row4").hide();
			  $("#netStr tbody tr.row5").hide();
			  $("#netStr tbody tr.row6").hide();
			  $("#netStr tbody tr.rowVeh").hide();
			 
		  }
		  if(rptOcc == "Y")
		  {
			  $("#netStr tbody tr.rowVeh").show();
			  $("#netStr tbody tr.row5").show();
			  $("#netStr tbody tr.row6").hide();
			  $("#netStr tbody tr.row7").hide();
			  $("#netStr tbody tr.row8").show();
			  $("#netStr tbody tr.row10").show();
			  $("#netStr tbody tr.row7").hide();
			  $("#netStr tbody tr.row9").hide();
			  $("#netStr tbody tr.row11").hide();
			  $("#netStr tbody tr.row12").hide();
			  $("#netStr tbody tr.row13").hide();
		  }
		  
	});
	
$("#reset").click(function() {
		
		$("#homeOfficeReports").find('input:text, textarea').val('');
		
		$("#span22").html(" ");
		$("#span21").html(" ");
		$("#span11").html(" ");
		$("#span12").html(" ");	
		//("#span9").html(" ");
		$("#dealerErr").html(" ");
		$("#rptDtErr").html(" ");
		val = $("#dynamicRpt").val();
		var rType=val;
		var rptOcc=rType.substring( rType.indexOf("_")+1 );
	    if(rptOcc == "M" ||  rptOcc == "Y")
		  {
			  $("#year").val(new Date().getFullYear());
		  }
		  else
			  {
			  	$('#year').val('-1');
			  }
			
		$("#spanrec").empty();
		$('input:radio[name="netStartRpt.vehicleTypeRd"]')[0].checked =true;
	    $("#Result").empty();
	    $('#display').empty();
	    //$('#year').val('-1');
	    $('#month').val('-1');
		
		$('#dealer').val("");
		$('#fromDate').val("");
		$('#toDate').val("");
		$('#reportDate').val("");
		
		$("#span3").text('');
	});
	
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
	
	function validateYear(year){
		var regEx=/^([0-9\s])*$/;
		
		var yrSpanTxt = "";
		
		if(year.match(/\ /)	)
		{	
			yrSpanTxt = yrSpanTxt + "<br>Year should not contain any space.";	
		}
		if(!year.match(regEx))
		{		
			yrSpanTxt = yrSpanTxt + "<br>Year should contain digits only.";	
		}
		if(year.length!=0 && year.length < 4 ){
			yrSpanTxt = yrSpanTxt + "<br>Year should be of 4 digits.";	
		}
		return yrSpanTxt;
	}
	
	function validateDealer(dealer) {
		var regEx=/^([a-z\sA-Z0-9,])*$/;
		var flag = true;
		var dealerSpanTxt = "";
	if(!dealer.match(regEx))
		{		
			dealerSpanTxt = dealerSpanTxt + "<br>Dealer(range) should not contain special character.";	
			//$("#dealerErr").html("<br>Dealer(range) Should Not contain Special Charecter.");
			//flag = false;
		}
	if(dealer.length > 235)
		{
			dealerSpanTxt = dealerSpanTxt + "<br>Dealer(range) should contain 235 characters.";	
			//$("#dealerErr").html("<br>Dealer(range) Should Contain 235 Chareter's.");
			//flag = false;
		}	
	if($('#dealer').val().match(/\ /)	)
		{	
			dealerSpanTxt = dealerSpanTxt + "<br>Dealer(range) should not contain any space.";	
			//$("#dealerErr").html("<br>Dealer(range) Should not contain any Space.");
			//flag = false;
		}
		
	if(!checkLength(dealer))
		{
			dealerSpanTxt = dealerSpanTxt + "<br>Individual Dealer Id should be 5 characters.";	
			//$("#dealerErr").html("<br>Dealer(range)Should not exceed 5 Charecter's.");
			//flag = false;
		}
	
		return dealerSpanTxt;
	}
	
	
	function validateReportDate(actualDate,repDate)
	{
		var date=new Date();
		var tomorrow = new Date();
		var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
		var reportSpanTxt = "";
		
		if(!verifyDate(actualDate))
		 {
			reportSpanTxt = reportSpanTxt+"<br>Please enter valid Report Date.";
			//$("#rptDtErr").text(" Please enter valid Report Date.");
		 	//flag =  false;
		 }
		if(!($('#reportDate').val().match(date_regex)))
	    {
	   		  reportSpanTxt = reportSpanTxt+"<br>Enter valid Date in MM/DD/YYYY.";
	   		  //$("#rptDtErr").text("Enter valid Date in MM/DD/YYYY.");
	          //flag =  false;     
	    }
		if(repDate > tomorrow){
				reportSpanTxt = reportSpanTxt+"<br>Report Date should not be greater than current date.";
				//$("#rptDtErr").text("Report Date should be less than Current Date.");
				//flag =  false;
		}
		
		return reportSpanTxt ;
	}


	
});