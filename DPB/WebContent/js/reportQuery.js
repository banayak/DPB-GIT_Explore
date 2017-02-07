/*********************************************************************************************
 * Project Name				: DPB    
 * Module Name				:   
 * Author					: Syntel 
 * Program Name				: reportQuery.js
 * Program Version			: 1.0
 * Program Description		: This js is used for Report query Def  validations.
 * 
 * Modification History		:   
 * ------------------------------------------------------------------------------------------
 * Author   Date (DD/MM/YYYY)   Version     Modification Details     Change Request Reference
 * ------------------------------------------------------------------------------------------
 * Syntel   Aug 28, 2013     	  1.0        First Draft
 *  * ------------------------------------------------------------------------------------------
 *  *********************************************************************************************/

$(document).ready(function() {
	
	var spans = $('.serror');
	spans.text(''); 
	if ($('input:radio[name="reportQueryForm.status"]')[2].checked  && $("#flagInActive").val()  != 'false' )   {
	
		$('#submit').hide();
		$('#reset').hide();
		$('#reportQueryName').attr("readonly", true);
		$('#description').attr("readonly", true);
		$('#query').attr("readonly", true);
		
	}
	

	$("#submitRqry").click(function() {
		
			var spans = $('.serror');
			var Name_Expression = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
			var regEx=/^([a-z\sA-Z0-9])*$/;
			var reportQueryName = $("#reportQueryName").val();
			var query=$('#query').val();
			var nameexp="^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
			var description=$('#description').val();
			var reportQueryID=$('#reportQueryId').val();
		
			//alert($('input:radio[name="reportQueryForm.status"]')[1].checked);
			
			if ( $('input:radio[name="reportQueryForm.status"]')[2].checked)   {
					var flag = true;
					
					var span5Txt = "";
					var span1Txt = "";
					var span2Txt = "";
					var span3Txt = "";
					
					if( reportQueryID <=0 )
					{
						span5Txt = span5Txt + "<br>Create Report Query in Active/Draft.";
						flag = false;
					}
					else{
							 if (!reportQueryName.match(Name_Expression)) {
								 	span1Txt = span1Txt+ "<br>Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.";
									flag = false;
							}
							 if(reportQueryName.length > 40)
								{
								 	span1Txt = span1Txt+"<br>Name should contain 40 charecters only.";
									flag = false;
								}
							
							 if(description.length > 235)
								{
								 	span2Txt = span2Txt+"<br>Description should contain 235 charecters only .";
									flag = false;
								}
							
							 if(query.length > 15000){
								
								 	span3Txt = span3Txt+"<br>Query Should contain 15000 chareters.";
									flag = false;
						     }
					}
					$("#span1").html(span1Txt);
					$("#span2").html(span2Txt);
					$("#span3").html(span3Txt);
					$("#span5").html(span5Txt);
					 return flag;
			}
			
			if ($('input:radio[name="reportQueryForm.status"]')[0].checked) {
				var flag = true;
				
				var span1Txt = "";
				var span2Txt = "";
				var span3Txt = "";
					if($.trim(reportQueryName) == '')
						{
							span1Txt = span1Txt+"<br>Enter Report Query Name.";
							flag = false;
						}
					
					 if (!reportQueryName.match(Name_Expression)) {
						 	span1Txt = span1Txt+"<br>Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.";
							flag = false;
					}
					 if(reportQueryName.length > 40)
						{
						 	span1Txt = span1Txt+"<br>Name should contain 40 charecters only.";
							flag = false;
						}
					
					 if(description.length > 235)
						{
						 	span2Txt =span2Txt+"<br>Description should contain 235 charecters only .";
							flag = false;
						}
					
					 if(query.length > 15000){
						 	span3Txt = span3Txt +"<br>Query Should contain 15000 chareters.";
							flag = false;
				     }
					 	$("#span1").html(span1Txt);
						$("#span2").html(span2Txt);
						$("#span3").html(span3Txt);
					return flag;
			} 
			
		 if ( $('input:radio[name="reportQueryForm.status"]')[1].checked) {
			 		var flag = true;
			 		
			 		var span5Txt = "";
					var span1Txt = "";
					var span2Txt = "";
					var span3Txt = "";
					
					if($.trim(reportQueryName) == '' && $.trim($('#query').val()) == '' )
					{
						span1Txt = span1Txt+"<br>Enter Report Query Name.";
						span3Txt = span3Txt+"<br>Enter Report Query."; 
						
						flag = false;
					}
					if ( $.trim(reportQueryName) == '' && $.trim($('#query').val()) != '')
						{
							span1Txt = span1Txt+"<br>Enter Report Query Name.";
							flag = false;
						}
					if( $.trim(reportQueryName) != '' && $.trim($('#query').val()) == '' )
						{
							
							span3Txt = span3Txt+"<br>Enter Report Query.";
							flag = false;
						}
					
					if ( reportQueryID > 0)
			 		{
					 	if($("#flagActive").val() != 'false')
					 	{
					 		span5Txt = span5Txt + "<br>Can not Resubmit Active Record.";
					 		flag = false;
					 	}
			 		}
					
					 if (!reportQueryName.match(Name_Expression)) {
						 	span1Txt = span1Txt+"<br>Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.";
							flag = false;
					}
					 
					 if(reportQueryName.length > 40)
						{
						 	span1Txt = span1Txt+"<br>Name should contain 40 charecters only.";
							flag = false;
						}
					
					 if(description.length > 235)
						{
						 	span2Txt=span2Txt+"<br>Description should contain 235 charecters only .";
							flag = false;
						}
					
					 if(query.length > 15000){
							span3Txt = span3Txt +"<br>Query Should contain 15000 chareters.";
							flag = false;
				     }
					 	$("#span1").html(span1Txt);
						$("#span2").html(span2Txt);
						$("#span3").html(span3Txt);
						$("#span5").html(span5Txt);
					return flag;
			} 
			spans.text(''); 
	});
	
	
	$('input:radio[name="reportQueryForm.status"]').change(function(){
			
				$("#test").empty();
				$("#span1").html("");
				$("#span2").html("");
				$("#span3").html("");
				$("#span5").html("");
				var span5Txt = "";
				spans.text('');
				var query=$('#query').val();
				var reportQueryID=$('#reportQueryId').val();
				
				if ($('input:radio[name="reportQueryForm.status"]')[0].checked  && ( $("#flagActive").val()  != 'false' || $("#flagInActive").val()  != 'false') )   {
					
						$("#submit").show();
						$("#reset").show();
						span5Txt = span5Txt +"<br>Creating New Report Query.";
						$("#span5").html(span5Txt);
						$("#reportQueryId").val(0);
						$('#reportQueryName').attr("readonly", false);
						$('#reportQueryName').val("");
						$('#description').attr("readonly", false);
						$('#query').attr("readonly", false);
							
					}
				if ($('input:radio[name="reportQueryForm.status"]')[1].checked && $("#flagInActive").val()  != 'false' && reportQueryID > 0)
					{
						$("#submit").hide();
						$("#reset").hide();
						$('#reportQueryName').attr("readonly", true);
						$('#description').attr("readonly", true);
						$('#query').attr("readonly", true);
						
					}
					
				if ($('input:radio[name="reportQueryForm.status"]')[2].checked  && $("#flagInActive").val()  != 'false'  )   {
					//alert('In Active');
					$('#submit').hide();
					$('#reset').hide();
					
					$('#reportQueryName').attr("readonly", true);
					$('#description').attr("readonly", true);
					$('#query').attr("readonly", true);
					
				}
				
				/* if ($('input:radio[name="reportQueryForm.status"]')[1].checked && $("#flagInActive").val()  == 'false' &&  $("#flagActive").val()  == 'false' )   {
					 	if ((query == ''))   {
					 		
					 		$("#span3").text("Query Should not be Blank for Active.");
							return false;
					 	}
				 }*/
				
		 });
	
	
});

