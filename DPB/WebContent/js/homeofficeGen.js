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
	
	value = $("#staticReport").val();
	var checkBoxes = $('input[type=checkbox]');
	var spans = $('.serror');
	spans.text(''); 		
	
	//var checkBoxes = $('input[type=checkbox]');	  
	 if( value == 1)
	  {
		 $('#lbl_vehType').text("Vehicle Type");
		 $('#lbl_dealer').text("Dealer(s)");
		 // $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").show();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").show();
		  $("#homeOfc tbody tr.row13").show();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
          $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		  //Vehicle Details Report-start
		 // $("#homeOfc tbody td.exportId").hide();
		  $("#exportId").show();
		  $("#homeOfc tbody tr.row22").show();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").show();
		  //Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").show();
		  $("#homeOfc tbody tr.row24").show();
	  }
	  
	else  if(value == 2)
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 // $("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").show();   
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").show();
		  $("#homeOfc tbody tr.row13").show();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").show();
		  $("#homeOfc tbody tr.row22").show();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").show();
		 //Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").show();
		  $("#homeOfc tbody tr.row24").show();
	  }
	  
	else  if(value == 3)
	  {	 
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 // $("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").show();    
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").show();
		  $("#exportId").show();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	  
	else if(value == 4)
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 //$("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").show();
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//vehicle details report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//vehicle details report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	//dealer compliance summary report  - start
	else if(value == dlrCompSummId)
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 //$("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").show();
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//vehicle details report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").show();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//vehicle details report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	//dealer compliance summary report  - end	    
	else if( value == 5 )
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 // $("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		  $("#homeOfc tbody tr.row23").show();
		  $("#homeOfc tbody tr.row24").show();
		  //Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		//To show From and To Year row and hide Page Count-Shweta
		  $("#homeOfc tbody tr.row22").show();
		  $("#rowPageSizeList").hide();
		//To show From and To Year row and hide Page Count-Shweta
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		//Vehicle Details Report-end
		  /*var n = $("input:checked").length;                       
			
			 if (n >= 1) {                                                        
			        $(':checkbox:not(:checked)').prop('disabled', true);  
			    }                                                        
			    else {                                                        
			        $(':checkbox:not(:checked)').prop('disabled', false); 
			    }*/
	  }
	  
	else  if( value == 6 )
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		// $("#genDynamicReport").hide();
		 $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").show();
		  $("#homeOfc tbody tr.row5").show();
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").show();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").show();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	  
	else  if( value == 7)
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 //$("#genDynamicReport").hide();
		 $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").show();
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row8").hide();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").show();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	else  if( value == 16)
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 //$("#genDynamicReport").hide();
		 $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").show();
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row8").hide();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").show();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	  
	else if( value == 8)
	  {
		$('#lbl_vehType').text("Vehicle Type");
		 // $("#genDynamicReport").hide();
		$('#lbl_dealer').text("Dealer(s)");
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row5").show();
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row8").hide();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row20").hide();	
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	 
	else if(value == 9)
	  {
		//document.getElementById("homeOfficeReports").action = 'generateReport';
	
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 // $("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row10").show();
		  $("#homeOfc tbody tr.row11").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row8").hide();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row12").show();
		  $("#homeOfc tbody tr.row13").show();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	
 //VIN lookup report - start
	 
	else if(value == 10)
	  { 
		 $('#lbl_dealer').text("Dealer(s) (comma/space seperated values)");
		  $("#homeOfc tbody tr.row3").hide();  
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row8").hide();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row20").show();
		  $("#homeOfc tbody tr.row19").show();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
		
	  }
	 //VIN lookup report -end
	
	 // Alekhya
	else if(value == 11)
	  { 
		//  $('#lbl_vehType').text("Vehicle Type");
		  $('#lbl_dealer').text("Dealer(s) (comma/space seperated values)");
		  $("#homeOfc tbody tr.row3").hide();  
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row12").show();
		  $("#homeOfc tbody tr.row13").show();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row20").show();
		  $("#homeOfc tbody tr.row19").show();
		  $("#homeOfc tbody tr.row21").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
		  //Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").show();
		  $("#exportId").show();
		  $("#homeOfc tbody tr.row25").show();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").show();
		  //Vehicle Details Report-end
		  $("#blockDetails").show();
		
	  }
	else if( value == 12 )
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 // $("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").show();
		  $("#homeOfc tbody tr.row13").show();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").hide();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	}
	 
	else if( value == 13 )
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 // $("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").show();
		  $("#homeOfc tbody tr.row13").show();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		//Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").hide();
		  $("#exportId").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  $("#rowPageSizeList").hide();
		//Vehicle Details Report-end
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
	  }
	//Unearned Performance Bonus calculation start
	else if( value == 14 )
	  {
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").hide();
		  $("#homeOfc tbody tr.row6").hide();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		  $("#homeOfc tbody tr.row23").show();
		  $("#homeOfc tbody tr.row24").show();
		  //Vehicle Details Report-start
		  //$("#homeOfc tbody td.exportId").show();
		  $("#exportId").show();
		  //To show From and To Year row and hide Page Count-Shweta
		  $("#homeOfc tbody tr.row22").show();
		  $("#rowPageSizeList").show();
		  //To show From and To Year row and hide Page Count-Shweta
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
		  //Vehicle Details Report-end
	  }
	//Unearned Performance Bonus calculation end
	 /* Dealer Compliance Quarterly Payouts Report-Old Start*/
	else if(value == 15)
	{
		$('#lbl_vehType').text("Vehicle Type");
		$('#lbl_dealer').text("Dealer(s)");
		 // $("#genDynamicReport").hide();
		  $("#homeOfc tbody tr.row3").hide();
		  $("#homeOfc tbody tr.row4").hide();
		  $("#homeOfc tbody tr.row5").show();
		  $("#homeOfc tbody tr.row6").show();
		  $("#homeOfc tbody tr.row8").show();
		  $("#homeOfc tbody tr.row17").show();
		  $("#homeOfc tbody tr.row9").hide();
		  $("#homeOfc tbody tr.row10").hide();
		  $("#homeOfc tbody tr.row11").hide();
		  $("#homeOfc tbody tr.row12").hide();
		  $("#homeOfc tbody tr.row13").hide();
		  $("#homeOfc tbody tr.row14").hide();
		  $("#homeOfc tbody tr.row15").hide();
		  $("#homeOfc tbody tr.row16").hide();
		  $("#homeOfc tbody tr.row18").show();   
		  $("#homeOfc tbody tr.row20").hide();
		  $("#homeOfc tbody tr.row19").hide();
		  $("#homeOfc tbody tr.row21").hide();
		  $("#homeOfc tbody tr.row22").hide();
		  /*$("#homeOfc tbody td.exportId").hide();*/
		  $("#exportId").hide();
		  $("#rowPageSizeList").hide();
		  $("#homeOfc tbody tr.row23").hide();
		  $("#homeOfc tbody tr.row24").hide();
		  $("#homeOfc tbody tr.row25").hide();
		  $("#homeOfc tbody tr.row26").hide();
	  }
/* Dealer Compliance Quarterly Payouts Report-Old End*/	
	else
		{
	
	 		$('#Err').val("");
			//$("#genDynamicReport").hide();
	 		$('#lbl_dealer').text("Dealer(s)");
	 		$('#lbl_vehType').text("Vehicle Type");
			$("#homeOfc tbody tr.row3").hide();
			$("#homeOfc tbody tr.row4").hide();
			$("#homeOfc tbody tr.row5").hide();
			$("#homeOfc tbody tr.row6").hide();
			$("#homeOfc tbody tr.row8").hide();
			$("#homeOfc tbody tr.row9").hide();
			$("#homeOfc tbody tr.row10").hide();
			$("#homeOfc tbody tr.row11").hide();
			$("#homeOfc tbody tr.row12").hide();
			$("#homeOfc tbody tr.row13").hide();
			$("#homeOfc tbody tr.row14").hide();
			$("#homeOfc tbody tr.row15").hide();
			$("#homeOfc tbody tr.row16").hide();
			$("#homeOfc tbody tr.row17").hide();
			$("#homeOfc tbody tr.row18").hide();
			$("#homeOfc tbody tr.row20").hide();
			$("#homeOfc tbody tr.row19").hide();
			$("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			//$("#homeOfc tbody td.exportId").hide();
			 $("#exportId").hide();
			$("#homeOfc tbody tr.row22").hide();
			$("#homeOfc tbody tr.row25").hide();
			 $("#homeOfc tbody tr.row26").hide();
			$("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			$("#homeOfc tbody tr.row23").hide();
			$("#homeOfc tbody tr.row24").hide();
			$("#display").hide();
		}
	
	
	 $('#Err').val("");	
	 

	$("#genReptStaticRP").click(function() {
	
		
		var spans = $('.serror');
		$("#display").empty();
		var regEx=/^([a-z\sA-Z0-9,])*$/;
		var date_regex = /^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/ ;
		var startDate = $.trim($("#fromDate").val());	
		var endDate = $.trim($("#toDate").val());
		var actualDate = $("#reportDate").val();
		var fDate=startDate.replace(/-/g,"/");
		var tDate=endDate.replace(/-/g,"/");
		var rDate=actualDate.replace(/-/g,"/");
		var fromDate = new Date(fDate);
		var toDate = new Date(tDate);
		var repDate = new Date(rDate);
		var date=new Date();
		var tomorrow = new Date();
		
		var yearSpanTxt = "";
		var dealerSpanTxt = "";
		var rptDtSpanTxt = "";
		var vehicleTypeSpan = "";
		var monthErrTxt ="";
		var quarterErrTxt ="";
		var Name_Expression = /^([a-z\sA-Z0-9])*$/;
		var span22Text = "";
		var span21Text = "";
		val = $("#staticReport").val();
		document.getElementById("genReptStaticRP").name = "method:genStaticReport";
	
		if(val == 1 || val == 2 || val == 12 || val == 13 )
			{
				var flag = true;
				var dealer = $.trim($('#dealer').val()).split(' ').join('');
				$('#dealer').val(dealer);
				if(dealer != '' )
				{
					dealerSpanTxt = validateDealer(dealer);
					if(dealerSpanTxt.length > 1)
					{
						flag = false;
					}
				}
	
				/*if($('#reportDate').val()!='')
				{	
					rptDtSpanTxt = validateReportDate(actualDate,repDate);
					if(rptDtSpanTxt.length  > 1)
					{	
						flag = false;
					} 	
				}*/
				
				if($.trim($('#toDate').val())=='' && $.trim($('#fromDate').val()) !="")
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
						span22Text = span22Text+"<br>Please enter valid To Date."; 
						flag = false;
					}
				}

				else if($('#toDate').val()!='')
				{	
					if(!($('#toDate').val().match(date_regex)))
					{
						span22Text = span22Text+"<br>Enter Date in MM/DD/YYYY.";    
						flag = false; 
					}
					
					if( toDate  > tomorrow ){
						   span22Text = span22Text+"<br>To Date should not be greater than Current Date.";
						   flag = false; 
						}
					if(!verifyDate(endDate))
					{
						span22Text = span22Text+"<br>Please enter valid To Date."; 
						flag = false;
					}
				}
				$("#span22").html(span22Text);
				$("#dealerErr").html(dealerSpanTxt);
				$("#rptDtErr").html(rptDtSpanTxt);	
				$("#span21").html(span21Text);
				$("#spanrec").text('');
				
				
				var fromYr = $.trim($('#fromYear').val());
				var toYr = $.trim($('#toYear').val());
				var fromMonth = $.trim($('#fromMonth').val());
				var toMonth = $.trim($('#toMonth').val());
				var fromQuarter = $.trim($('#fromQuarter').val());
				var toQuarter = $.trim($('#toQuarter').val());
				
				//Courtesy Vehicle Report-start
				
				if($.trim($('#fromDate').val())=='' && $.trim($('#toDate').val())=='')  {
					
					
					if(toYr == -1 && fromYr == -1)
					{
						span22Text = span22Text+"<br>Please Select From and To Years."; 
						flag = false;
					}
					
					else if(toYr == -1 && fromYr != -1)
					{
						span22Text = span22Text+"<br>Select To Year."; 
						flag = false;
					}
					else if(toYr != -1 && fromYr == -1)
					{
						span22Text = span22Text+"<br>Select From Year.";
						flag = false;
					}	  
					
					else if(toYr != -1 && fromYr != -1)
					{
						if(toYr < fromYr ){
							span22Text = span22Text+"<br>To Year should not be less than From Year.";  
							flag = false;
						}
						else if(fromMonth != -1 && toMonth != -1){
							if(fromQuarter !=-1 ||  toQuarter !=-1){
								span22Text = span22Text+"<br>Select Month Range or Quarter range, not both.";  
								flag = false;
								
							}
							if(toYr == fromYr && fromMonth > toMonth){
								span22Text = span22Text+"<br>To month should not be less than From month.";  
								flag = false;
							}
						}
						else if(fromQuarter != -1 && toQuarter != -1){
							if(fromMonth !=-1 || toMonth != -1){
								span22Text = span22Text+"<br>Select Month Range or Quarter range, not both.";  
								flag = false;
								
							}
							if(toYr == fromYr && fromQuarter > toQuarter ){
								span22Text = span22Text+"<br>To Quarter should not be less than From Quarter.";  
								flag = false;
							}
						}
														
						else{
						span22Text = span22Text+"<br>Select Month or Quarter Range."; 
						flag = false;
						}
					}
					
					else if(toYr != -1 && fromYr != -1 && fromMonth != -1 && toMonth != -1 && fromQuarter != -1 && toQuarter != -1)
					{
						span22Text = span22Text+"<br>Select either Month Range or Quarter Range but not both."; 
						flag = false;
					}
					
					
				}
				else{
					
					if(($.trim($('#fromDate').val())!='' && $.trim($('#toDate').val())!='') && val != 12 )  {
						
						if((toYr != -1 && fromYr != -1 && fromMonth != -1 && toMonth != -1) || (toYr != -1 && fromYr != -1 && fromQuarter != -1 && toQuarter != -1 ) ) {
							
							span22Text = span22Text+"<br>Select only one of the combination: From Date and To Date (or) Year and Month Range (or) Year and Quarter Range."; 
							flag = false;
							
						}
						
					}
					
				}
				
				//Courtesy Vehicle Report-end
				
							
				$("#span12").html(span22Text);
				
				
				$("#dealerErr").html(dealerSpanTxt);
					
				$("#span21").html(span21Text);
				$("#spanrec").text('');
				
				if(flag==false){
					document.getElementById("widecontentarea").style.zIndex = "0"; 
					$(".spinner").hide();
				}
				
				return flag;			 
			}
		
		//Unearned Performance Bonus calculation : added report id 14	
		if( val == 5 || val == 14 )
		{
			
			var flag = true;
			var dealer = $.trim($('#dealer').val()).split(' ').join('');
			$('#dealer').val(dealer);
			if(dealer != '' )
			{
				dealerSpanTxt = validateDealer(dealer);
				if(dealerSpanTxt.length > 1)
				{	
					flag = false;
				}
			}

			
			var fromYr = $.trim($('#fromYear').val());
			var toYr = $.trim($('#toYear').val());
			var fromMonth = $.trim($('#fromMonth').val());
			var toMonth = $.trim($('#toMonth').val());
			var fromQuarter = $.trim($('#fromQuarter').val());
			var toQuarter = $.trim($('#toQuarter').val());

			
			
			if(toYr == -1 && fromYr == -1)
			{
				span22Text = span22Text+"<br>Please Select From and To Years."; 
				flag = false;
			}
			
			if(toYr == -1 && fromYr != -1)
			{
				span22Text = span22Text+"<br>Select To Year."; 
				flag = false;
			}
			else if(toYr != -1 && fromYr == -1)
			{
				span22Text = span22Text+"<br>Select From Year.";
				flag = false;
			}	  
			
			else if(toYr != -1 && fromYr != -1)
			{
				if(toYr < fromYr ){
					span22Text = span22Text+"<br>To Year should not be less than From Year.";  
					flag = false;
				}
				else if(fromMonth != -1 && toMonth != -1){
					if(fromQuarter !=-1 ||  toQuarter !=-1){
						span22Text = span22Text+"<br>Select Month Range or Quarter range, not both.";  
						flag = false;
						
					}
					if(toYr == fromYr && fromMonth > toMonth){
						span22Text = span22Text+"<br>To month should not be less than From month.";  
						flag = false;
					}
				}
				else if(fromQuarter != -1 && toQuarter != -1){
					if(fromMonth !=-1 || toMonth != -1){
						span22Text = span22Text+"<br>Select Month Range or Quarter range, not both.";  
						flag = false;
						
					}
					if(toYr == fromYr && fromQuarter > toQuarter ){
						span22Text = span22Text+"<br>To Quarter should not be less than From Quarter.";  
						flag = false;
					}
				}
												
				else{
				span22Text = span22Text+"<br>Select Month or Quarter Range."; 
				flag = false;
				}
			}
			
			else{
				if(toYr != -1 && fromYr != -1 && fromMonth != -1 && toMonth != -1 && fromQuarter != -1 && toQuarter != -1)
			{
				span22Text = span22Text+"<br>Select either Month Range or Quarter Range but not both."; 
				flag = false;
			}
			}
						
			$("#span12").html(span22Text);
			
			
			$("#dealerErr").html(dealerSpanTxt);
				
			$("#span21").html(span21Text);
			$("#spanrec").text('');
			
			if(flag==false){
				document.getElementById("widecontentarea").style.zIndex = "0"; 
				$(".spinner").hide();
			}
			return flag;			 
		}

		
			 //Dealer Compliance Summary Report - added id
			 if(val == 3 || val == 4 || val == 6 || val == dlrCompSummId)
			{
				 	var flag = true;
				 	var dealer = $.trim($('#dealer').val()).split(' ').join('');
				 	$('#dealer').val(dealer);
					if(dealer!='' )
					{
						dealerSpanTxt = validateDealer(dealer);
						if(dealerSpanTxt.length > 1)
						{	//alert(dealerSpanTxt);
							flag = false;
						}
					}
					var year= $.trim($('#year').val());
					$('#year').val(year);
					var month= $.trim($('#month').val());
					$('#month').val(month);
			
					if( month != -1 )
					{
						if( year <= 0 )
						{	
							yearSpanTxt = yearSpanTxt + "<br>Select year.";
							flag = false;
						}
					}
					
					if(month != -1 && month > date.getMonth()+1 &&  year >= date.getFullYear()){
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
					//Dealer Compliance Summary Report - Start
					if(val == dlrCompSummId && year >=0 && month == -1) {
						
						monthErrTxt = monthErrTxt +"<br>Select month.";	
						flag = false;
						
					}
					//Dealer Compliance Summary Report - End
				/*	if( val == 5 )
					{
						var n = $("input:checked").length;
							if(n == 0 )
							{
								vehicleTypeSpan = vehicleTypeSpan  + "<br>Select Vehicle Type";
								flag = false;
							}
					} */
					$("#monthErr").html(monthErrTxt);
					$("#dealerErr").html(dealerSpanTxt);
					$("#vehicleType").html(vehicleTypeSpan);
					$("#yearErr").html(yearSpanTxt);	
					$("#spanrec").text('');
					if(flag==false){
						document.getElementById("widecontentarea").style.zIndex = "0"; 
						$(".spinner").hide();
					}
					
					return flag;	
			}
			 
			if( val == 8 || val == 7 || val == 16)
			{
				var flag = true;
				var year= $.trim($('#year').val());
				$('#year').val(year);
				var month= $.trim($('#month').val());
				$('#month').val(month);
				
				if( year <= 0 && month == -1 )
				{	
					yearSpanTxt = yearSpanTxt + "<br>Select year.";
					flag = false;
				}
				else if( month != -1 )
				{
					if( year <= 0 )
					{	
						yearSpanTxt = yearSpanTxt + "<br>Select year.";
						flag = false;
					}
				}
				else if( year >=0 && month == -1) {
					
					monthErrTxt = monthErrTxt +"<br>Select month.";	
					flag = false;
					
				}
				if(month != -1 && month > date.getMonth()+1 &&  year >= date.getFullYear() ){
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
				$("#yearErr").html(yearSpanTxt);
				$("#monthErr").html(monthErrTxt);
				$("#spanrec").text('');
				
				if(flag==false){
					document.getElementById("widecontentarea").style.zIndex = "0"; 
					$(".spinner").hide();
				}
				
				return flag;
			}
			
			
			if( val == 9 )
				{
						var span1Text = "";
						var span21Text = "";
						var span22Text = "";
						var span9Text = "";
						var flag = true;
						//document.getElementById("homeOfficeReports").action = 'blockedVehiclesReport';
						
						///$( 'input').attr( "name", "method:generateBlockVehicleReport" );
						//$('#genReptStaticRP').attr("method" ,"generateBlockVehicleReport");
						
						document.getElementById("genReptStaticRP").name = "method:generateBlockVehicleReport";
						
						if ( $.trim($('#vehicleId').val())=='' &&  $.trim($('#dealerId').val())=='' && $('#fromDate').val()=='' && $('#toDate').val()=='')   {
							
							$("#span12").html("Enter Dealer ID / VIN(s) / From-To Date.");
							$("#span9").text('');
							flag = false;
						}
						
						else{
								if($.trim($('#vehicleId').val())!='' )
								{
										//alert($.trim($('#vehicleId').val()));
									var val=$('#vehicleId').val();
									//checkLength($('#vehicleId').val());
									span9Text = verifyVehicles(val);
									//alert(span9Text);
									if( span9Text.length > 1)
									{	//alert(span9Text.length);
										flag = false;
									}
									/*if(!verifyVehicles(val))
									{
										flag = false;
									}else{
										$("#span9").text('');
									}*/
								}
							/*else{
								$("#span9").text('');
							}*/


							if(!$.trim($('#dealerId').val())=='')
							{	
								if (!$('#dealerId').val().match(Name_Expression))
								{
									span1Text = span1Text+	"<br>Dealer Id should be Alpha-Numeric.";
									flag =  false;
								}
								if ($('#dealerId').val().length != 0 && $('#dealerId').val().length != 5)
								{
									span1Text = span1Text+	"<br>Dealer Id should contain 5 characters.";
									flag =  false;
								}
								if($('#dealerId').val().match(/\ /)	)
								{	
									span1Text = span1Text+	"<br>Dealer Id should not contain any space.";
									flag =  false;
								}
							}

							if($.trim($('#toDate').val())=='' && $.trim($('#fromDate').val()) !="")
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
									span22Text = span22Text+"<br>Please enter valid To Date."; 
									flag = false;
								}
							}

							else if($('#toDate').val()!='')
							{	
								if(!($('#toDate').val().match(date_regex)))
								{
									span22Text = span22Text+"<br>Enter Date in MM/DD/YYYY.";    
									flag = false; 
								}
								
								if( toDate  > tomorrow ){
									   span22Text = span22Text+"<br>To Date should not be greater than Current Date.";
									   flag = false; 
									}
								if(!verifyDate(endDate))
								{
									span22Text = span22Text+"<br>Please enter valid To Date."; 
									flag = false;
								}
							}
							$("#span12").html(" ");	
						}
						$("#span22").html(span22Text);
						$("#span21").html(span21Text);
						$("#span11").html(span1Text);
						$("#span9").html(span9Text);
						$("#spanrec").empty();
						$("#Result").empty();
						
						if(flag==false){
							document.getElementById("widecontentarea").style.zIndex = "0"; 
							$(".spinner").hide();
						}
						
						return flag;
			
				}
			
			
			if( val == 10 )
			{
				$("#span12").html = "";
				var spanVINText = "";
				var spanPoTxt = "";
				var flag = true;
				
				if(($.trim($('#vehicleRange').val())!== '' )&& ($.trim($('#poNum').val())!== '' ))
				{
					$("#span12").html("Enter either VIN or PO but not both the values");
					flag = false;
				}	
					
				else if(($.trim($('#vehicleRange').val()) == '')  && ($.trim($('#poNum').val()) =='')){
					$("#span12").html("Both values should not be empty. Enter either VIN or PO");
					flag = false;
					}
					
					
				else{
					if($.trim($('#vehicleRange').val())!='' )
					{
						var val=$('#vehicleRange').val();
						spanVINText = verifyVehiclesVehRpt(val);
						if( spanVINText.length > 1)
						{
							flag = false;
						}
					}
					
					if($.trim($('#poNum').val())!='' )
					{
						var val=$('#poNum').val();
						spanPoTxt = verifyPONumbers(val);
						if( spanPoTxt.length > 1)
						{
							flag = false;
						}
					}
					
					$("#span12").html(" ");
					}
					
					$("#spanVINText").html(spanVINText);
					$("#spanPoTxt").html(spanPoTxt);
					$("#spanrec").empty();
					$("#Result").empty();
					
					if(flag==false){
						document.getElementById("widecontentarea").style.zIndex = "0"; 
						$(".spinner").hide();
					}
					
					return flag;
			}
			
			if( val == 11){
				var span21Text = "";
				var span22Text = "";
				var spanVINText = "";
				var spanPoTxt = "";
				$("#span12").html = "";
				var flag = true;
					if(($.trim($('#toDate').val())=='') &&  ($.trim($('#fromDate').val()) =='') && ($.trim($('#dealer').val())=='') && ($.trim($('#vehicleRange').val()) == '')  &&  ($.trim($('#poNum').val()) =='')){
						$("#span12").html("Enter Dealer(s) / VIN(s) / PO(s) / From-To Date.");
						flag = false;
					} else {
					// From Date - To Date Validations Start	
						if($.trim($('#toDate').val())=='' && $.trim($('#fromDate').val()) !="")
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
								span22Text = span22Text+"<br>Please enter valid To Date."; 
								flag = false;
							}
						}
			
						else if($('#toDate').val()!='')
						{	
							if(!($('#toDate').val().match(date_regex)))
							{
								span22Text = span22Text+"<br>Enter Date in MM/DD/YYYY.";    
								flag = false; 
							}
							
							if( toDate  > tomorrow ){
								   span22Text = span22Text+"<br>To Date should not be greater than Current Date.";
								   flag = false; 
								}
							if(!verifyDate(endDate))
							{
								span22Text = span22Text+"<br>Please enter valid To Date."; 
								flag = false;
							}
						}
					// From Date - To Date End
					// Dealer Range Validations Start
						var dealer = $.trim($('#dealer').val()).split(' ').join('');
						//var dealer=$.trim($('#dealer').val());
						$('#dealer').val(dealer);
						if(dealer!='' )
						{
							//var dealer=$('#dealer').val();
							dealerSpanTxt = validateDealerVehReport(dealer);
							if(dealerSpanTxt.length > 1)
							{	//alert(dealerSpanTxt);
								flag = false;
							}
						}
					// Dealer Range Validations End
					
					// VIN Validations Start
					
						if($.trim($('#vehicleRange').val())!='' )
						{
							var val=$('#vehicleRange').val();
							spanVINText = verifyVehiclesVehRpt(val);
							if( spanVINText.length > 1)
							{
								flag = false;
							}
						}
						if($.trim($('#poNum').val())!='' )
						{
							var val=$('#poNum').val();
							/*alert("PO VALIDATION");*/
							spanPoTxt = verifyPONumbers(val);
							if( spanPoTxt.length > 1)
							{
								flag = false;
							}
						}
						$("#span12").html(" ");	
					}
					// VIN Validations End
						$("#span22").html(span22Text);
						$("#span21").html(span21Text);
						$("#spanVINText").html(spanVINText);
						$("#dealerErr").html(dealerSpanTxt);
						$("#spanPoTxt").html(spanPoTxt);
						$("#spanrec").text('');
						
						if(flag==false){
							document.getElementById("widecontentarea").style.zIndex = "0"; 
							$(".spinner").hide();
						}
						
			return flag;
		}
			//spans.text(''); 
	});
	
                  /* Vehicle Details Report - Start*/ 

checkBoxes.click(function() {
	   
		val = $("#staticReport").val();
		/*if( val == 5 )
			{
				var n = $("input:checked").length;                       
			
				 if (n >= 1) {                                                        
				        $(':checkbox:not(:checked)').prop('disabled', true);  
				    }                                                        
				    else {                                                        
				        $(':checkbox:not(:checked)').prop('disabled', false); 
				    }
			}*/
	});
	
	$("#reset").click(function() {
		
		$("#homeOfficeReports").find('input:text, textarea').val('');
		$("#homeOfficeReports").find('input:checkbox').removeAttr('checked');
		$("#span22").html(" ");
		$("#span21").html(" ");
		$("#span11").html(" ");
		$("#span9").text('');
		$("#span12").html(" ");	
		//("#span9").html(" ");
		$("#dealerErr").html(" ");
		$("#rptDtErr").html(" ");
		$("#yearErr").html(" ");
		$("#monthErr").html(" ");
		$("#quarterErr").html(" ");
		$("#vehicleType").html("");	
		$("#spanrec").empty();
	    $("#Result").empty();
	    $('#display').empty();
	  //Vehicle Details Report-start
	    $('input[type=radio]').prop('checked', function () {
	        return this.getAttribute('checked') == 'checked';
	    });
	  //Vehicle Details Report-end
	    val = $("#staticReport").val();
	    /*Dealer Compliance Quarterly Payouts Report-Old Start*/
	  //dealer compliance summary report - added id
	    if( val == 3 || val == 4 || val == 6 || val == 7 || val == 8 || val == 15 || val == 16 || val == dlrCompSummId)
	    	/*Dealer Compliance Quarterly Payouts Report-Old End*/
		  {
			  $("#year").val(new Date().getFullYear());
		  }
		  else
			  {
			  	$('#year').val('-1');
			  }
		$('#month').val('-1');
		$("#totElgQtrId").html("");
		$("#dlrQtrId").html("");
		//$("input:radio").attr("checked", false);
		$('input:radio[name="netStartRpt.vehicleTypeRd"]')[0].checked =true;
		$('input:radio[name="netStartRpt.vehTypeRadio"]')[0].checked =true;
		$('input:radio[name="netStartRpt.amgProgramRadio"]')[0].checked =true;
		$('#viewTotVehQuarter').val('-1');
		$('#viewDealerLevelQuarter').val('-1');
		$(':checkbox:not(:checked)').prop('disabled', false); 
		$("#spanVINText").html(" ");
		$("#spanPoTxt").html(" ");
		$('#fromYear').val('-1');  
	    $('#toYear').val('-1');  
	    $('#fromMonth').val('-1');  
	    $('#toMonth').val('-1');  
	    $('#fromQuarter').val('-1');  
	    $('#toQuarter').val('-1'); 

	});
  		
	$('input:radio[name="netStartRpt.viewAccountVin"]').change(function() {
		
		$("#span12").text('');
		$("#spanrec").text('');
		//alert($(this).val());
			if($(this).val() == "D" || $(this).val() == "V" )
			{
				
				  $('#fromDate').val("");
				  $('#toDate').val("");
			}
		if($(this).val() == "D")
		{	$("#totElgQtrId").html("");
			$("#span22").html("");
			$("#span21").html("");
			$('#viewTotVehQuarter').val('-1');
		}
		if($(this).val() == "V")
		{	$("#dlrQtrId").html("");
			$("#span22").html("");
			$("#span21").html("");
			$('#viewDealerLevelQuarter').val('-1');
		}
		if($(this).val() == "VIN")
		{	$("#span22").html("");
			$("#span21").html("");
			$("#dlrQtrId").html("");
			$("#totElgQtrId").html("");
			$('#viewTotVehQuarter').val('-1');
			$('#viewDealerLevelQuarter').val('-1');
		}
		$('#display').empty();
	});
	
	$("#viewTotVehQuarter").change(function() {
		
		if($("#VIN-DealerLevel_1").prop("checked") == true  && $("#VIN-DealerLevel_2").prop("checked") == false  &&  $("#VIN-DealerLevel_0").prop("checked") == false)	 
		{
			$('#viewTotVehQuarter').val('-1');
		}
		if  ($("#VIN-DealerLevel_0").prop("checked") == true )
		{
			$('#viewTotVehQuarter').val('-1');
		}
		
	});
	
	$("#viewDealerLevelQuarter").change(function() {
		
		if($("#VIN-DealerLevel_2").prop("checked") == true  && $("#VIN-DealerLevel_1").prop("checked") == false  &&  $("#VIN-DealerLevel_0").prop("checked") == false)	 
		{
			
			$('#viewDealerLevelQuarter').val('-1');
		}
		 if  ($("#VIN-DealerLevel_0").prop("checked") == true )
		{
			$('#viewDealerLevelQuarter').val('-1');
		}
	});
	
	$("#staticReport").change(function() {
		$("#blockDetails").hide();
		//$('#display').val("");
		//$("#display > *").text("");
		$('#display').empty();
		  val = $(this).val();
		  // val = $("#staticReport").val();
		  var spans = $('.serror');
		  spans.text(''); 
		  $('#dealerId').val("");
		  $('#dealer').val("");
		  $('#fromDate').val("");
		  $('#toDate').val("");
		  $('#reportDate').val("");
		  /* Dealer Compliance Quarterly Payouts Report-Old Start */
		  //dealer compliance summary report - added id
		  if( val == 3 || val == 4 || val == 6 || val == 7 || val == 8 || val == 15 || val == 16 || val == dlrCompSummId)
			  /* Dealer Compliance Quarterly Payouts Report-Old End */
		  {
			  $("#year").val(new Date().getFullYear());
		  }
		  else
			  {
			  	$('#year').val('-1');
			  }
		 
		  $('#month').val('-1');
		  $('#dynamicRpt').val('-1');
		  $('#vehicleId').val("");
		  $('#vehicleRange').val("");
		  $(':checkbox').prop('checked', false);
		  $(':checkbox:not(:checked)').prop('disabled', false);
		  $('#spanrec').text('');
		 // spnas.html(" ");
		  
		  
		  if(val == -1)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row8").hide();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").hide();
			  $("#homeOfc tbody tr.row18").hide();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").show();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		  }
		  	  
		  if(val == 1)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").show();
			  $("#homeOfc tbody tr.row8").show();
			  /*$("#homeOfc tbody tr.row9").show();*/
			  
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").show();
			  $("#homeOfc tbody tr.row13").show();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").show();
			  $("#homeOfc tbody tr.row22").show();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").show();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").show();
			  $("#homeOfc tbody tr.row24").show();
		  }
		  
		  if(val == 2)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").show();
			  $("#homeOfc tbody tr.row8").show();
			 /* $("#homeOfc tbody tr.row9").show();
			 */ $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").show();
			  $("#homeOfc tbody tr.row13").show();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").show();
			  $("#homeOfc tbody tr.row22").show();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").show();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").show();
			  $("#homeOfc tbody tr.row24").show();
		  }
		  
		  if(val == 3)
		  {	 
			 
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").show();
			  $("#homeOfc tbody tr.row6").show();
			  $("#homeOfc tbody tr.row8").show();
			  
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").show();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").show();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		  }
		  
		  if(val == 4)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").show();
			  $("#homeOfc tbody tr.row6").show();
			  $("#homeOfc tbody tr.row8").show();
			  
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").show();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		}
		//dealer compliance summary report - start
		  if(val == dlrCompSummId)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").show();
			  $("#homeOfc tbody tr.row6").show();
			  $("#homeOfc tbody tr.row8").show();
			  
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").show();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").show();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		}
		//dealer compliance summary report - start
		  
		  if(val == 5)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row8").show();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").show();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			  $("#homeOfc tbody tr.row23").show();
			  $("#homeOfc tbody tr.row24").show();
		        //Vehicle Details Report-start
			  $("#exportId").hide();
			//To show From and To Year row and hide Page Count-Shweta
			  $("#homeOfc tbody tr.row22").show();
			  $("#rowPageSizeList").hide();
			//To show From and To Year row and hide Page Count-Shweta
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			//Vehicle Details Report-end
		  }
		  
		  if(val == 6)
		  {		
			  $('#lbl_vehType').text("Vehicle Type");
              $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").show();
			  $("#homeOfc tbody tr.row5").show();
			  $("#homeOfc tbody tr.row6").show();
			  $("#homeOfc tbody tr.row8").show();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
              $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  //$("#homeOfc tbody td.exportId").hide();
			  $("#exportId").show();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
			  $("#homeOfc tbody tr.row26").hide();
		  }
		  
		  if(val == 7)
		  {	 
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").show();
			  $("#homeOfc tbody tr.row6").show();
			  $("#homeOfc tbody tr.row8").hide();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
              $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").show();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		  }
		  
		  if(val == 16)
		  {	 
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").show();
			  $("#homeOfc tbody tr.row6").show();
			  $("#homeOfc tbody tr.row8").hide();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").show();
              $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").show();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		  }
		  
		  if(val == 8)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row5").show();
			  $("#homeOfc tbody tr.row6").show();
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row8").hide();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
              $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		  }
		  
		  if( val == 9 )
		  { 
			  //document.getElementById("homeOfficeReports").action = 'generateReport';
			 
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row3").hide();
			  $("#homeOfc tbody tr.row10").show();
			  $("#homeOfc tbody tr.row11").show();
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row8").hide();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row12").show();
			  $("#homeOfc tbody tr.row13").show();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
}
		  
		  
		  if(val == 10)
		  { 
			  $("#homeOfc tbody tr.row3").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row4").hide(); 
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row8").hide();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
              $("#homeOfc tbody tr.row20").show();
			  $("#homeOfc tbody tr.row19").show();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		  }
		  // Alekhya
		  if(val == 11)
		  { 
			  $('#lbl_dealer').text("Dealer(s) (comma/space separated values)");
			//  $('#lbl_vehType').text("Vehicle Type");
			  $("#homeOfc tbody tr.row3").hide();  
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row18").hide();
			  $("#homeOfc tbody tr.row12").show();
			  $("#homeOfc tbody tr.row13").show();
		  	  $("#homeOfc tbody tr.row8").show();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row20").show();
			  $("#homeOfc tbody tr.row19").show();
			  $("#homeOfc tbody tr.row21").hide();
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
			//Vehicle Details Report-start
			  $("#exportId").show();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").show();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").show();
			//Vehicle Details Report-end
			  $("#blockDetails").show();
		}
		// AMG Changes - START
		  if(val == 12)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row8").show();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").show();
			  $("#homeOfc tbody tr.row13").show();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").hide();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();			  
		  }
		  if(val == 13)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row8").show();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").show();
			  $("#homeOfc tbody tr.row13").show();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").show();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			//Vehicle Details Report-start
			  $("#exportId").hide();
			  $("#homeOfc tbody tr.row22").hide();
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			  $("#rowPageSizeList").hide();
			//Vehicle Details Report-end
			  $("#homeOfc tbody tr.row23").hide();
			  $("#homeOfc tbody tr.row24").hide();
		  }
		// AMG Changes - END
		//Unearned Performance Bonus calculation start
		  if(val == 14)
		  {
			  $('#lbl_vehType').text("Vehicle Type");
			  $('#lbl_dealer').text("Dealer(s)");
			  $("#homeOfc tbody tr.row4").hide();
			  $("#homeOfc tbody tr.row5").hide();
			  $("#homeOfc tbody tr.row6").hide();
			  $("#homeOfc tbody tr.row8").show();
			  $("#homeOfc tbody tr.row9").hide();
			  $("#homeOfc tbody tr.row10").hide();
			  $("#homeOfc tbody tr.row11").hide();
			  $("#homeOfc tbody tr.row12").hide();
			  $("#homeOfc tbody tr.row13").hide();
			  $("#homeOfc tbody tr.row14").hide();
			  $("#homeOfc tbody tr.row15").hide();
			  $("#homeOfc tbody tr.row16").hide();
			  $("#homeOfc tbody tr.row17").show();
			  $("#homeOfc tbody tr.row18").show();
			  $("#homeOfc tbody tr.row20").hide();
			  $("#homeOfc tbody tr.row19").hide();
			  $("#homeOfc tbody tr.row21").hide();
			  $("#homeOfc tbody tr.row23").show();
			  $("#homeOfc tbody tr.row24").show();
		  	//Vehicle Details Report-start
			  $("#exportId").show();
			//To show From and To Year row and hide Page Count-Shweta
			  $("#homeOfc tbody tr.row22").show();
			  $("#rowPageSizeList").show();
			//To show From and To Year row and hide Page Count-Shweta
			  $("#homeOfc tbody tr.row25").hide();
			  $("#homeOfc tbody tr.row26").hide();
			//Vehicle Details Report-end
		  }
		//Unearned Performance Bonus calculation end
			 if(val == 15){
				$('#lbl_vehType').text("Vehicle Type");
				$('#lbl_dealer').text("Dealer(s)");
				 // $("#genDynamicReport").hide();
				  $("#homeOfc tbody tr.row3").hide();
				  $("#homeOfc tbody tr.row4").hide();
				  $("#homeOfc tbody tr.row5").show();
				  $("#homeOfc tbody tr.row6").show();
				  $("#homeOfc tbody tr.row8").show();
				  $("#homeOfc tbody tr.row17").show();
				  $("#homeOfc tbody tr.row9").hide();
				  $("#homeOfc tbody tr.row10").hide();
				  $("#homeOfc tbody tr.row11").hide();
				  $("#homeOfc tbody tr.row12").hide();
				  $("#homeOfc tbody tr.row13").hide();
				  $("#homeOfc tbody tr.row14").hide();
				  $("#homeOfc tbody tr.row15").hide();
				  $("#homeOfc tbody tr.row16").hide();
				  $("#homeOfc tbody tr.row18").show();   
				  $("#homeOfc tbody tr.row20").hide();
				  $("#homeOfc tbody tr.row19").hide();
				  $("#homeOfc tbody tr.row21").hide();
				  $("#homeOfc tbody tr.row22").hide();
				  /*$("#homeOfc tbody td.exportId").hide();*/
				  $("#exportId").hide();
				  $("#rowPageSizeList").hide();
				  $("#homeOfc tbody tr.row23").hide();
				  $("#homeOfc tbody tr.row24").hide();
				  $("#homeOfc tbody tr.row25").hide();
				  $("#homeOfc tbody tr.row26").hide();
			  }
		/* Dealer Compliance Quarterly Payouts Report-Old End*/	
			
	});
});

function fnValidateExcel() {
	var spans = $('.serror');
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
	var flag = true;
	var yearSpanTxt = "";
	var dealerSpanTxt = "";
	var rptDtSpanTxt = "";
	var vehicleTypeSpan = "";
	var monthErrTxt ="";
	var Name_Expression = /^([a-z\sA-Z0-9])*$/;
	var span22Text = "";
	var span21Text = "";
	val = $("#staticReport").val();
	document.getElementById("exportExcel").name = "method:exportToExcel";
	//Vehicle Details Report-start
	if( val != 11 && val != 14 && val != 3 && val != 17) {
		$("#display").empty();
	}
	
	//Vehicle Details Report-end
	if( val == 11){
			var span21Text = "";
			var span22Text = "";
			var spanVINText = "";
			var spanPoTxt = "";
			$("#span12").html = "";
				if(($.trim($('#toDate').val())=='') &&  ($.trim($('#fromDate').val()) =='') && ($.trim($('#dealer').val())=='') && ($.trim($('#vehicleRange').val()) == '')  &&  ($.trim($('#poNum').val()) =='')){
					$("#span12").html("Enter Dealer(s) / VIN(s) / PO(s) / From-To Date.");
					flag = false;
				} else {
				// From Date - To Date Validations Start	
					if($.trim($('#toDate').val())=='' && $.trim($('#fromDate').val()) !="")
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
							span22Text = span22Text+"<br>Please enter valid To Date."; 
							flag = false;
						}
					}
		
					else if($('#toDate').val()!='')
					{	
						if(!($('#toDate').val().match(date_regex)))
						{
							span22Text = span22Text+"<br>Enter Date in MM/DD/YYYY.";    
							flag = false; 
						}
						
						if( toDate  > tomorrow ){
							   span22Text = span22Text+"<br>To Date should not be greater than Current Date.";
							   flag = false; 
							}
						if(!verifyDate(endDate))
						{
							span22Text = span22Text+"<br>Please enter valid To Date."; 
							flag = false;
						}
					}
				// From Date - To Date End
				// Dealer Range Validations Start
					var dealer=$.trim($('#dealer').val());
					$('#dealer').val(dealer);
					if(dealer!='' )
					{
						//var dealer=$('#dealer').val();
						dealerSpanTxt = validateDealerVehReport(dealer);
						if(dealerSpanTxt.length > 1)
						{	//alert(dealerSpanTxt);
							flag = false;
						}
					}
				// Dealer Range Validations End
				
				// VIN Validations Start
				
					if($.trim($('#vehicleRange').val())!='' )
					{
						var val=$('#vehicleRange').val();
						spanVINText = verifyVehiclesVehRpt(val);
						if( spanVINText.length > 1)
						{
							flag = false;
						}
					}
					if($.trim($('#poNum').val())!='' )
					{
						var val=$('#poNum').val();
						alert("PO VALIDATION");
						spanPoTxt = verifyPONumbers(val);
						if( spanPoTxt.length > 1)
						{
							flag = false;
						}
					}
					$("#span12").html(" ");	
				}
				// VIN Validations End
					$("#span22").html(span22Text);
					$("#span21").html(span21Text);
					$("#spanVINText").html(spanVINText);
					$("#dealerErr").html(dealerSpanTxt);
					$("#spanPoTxt").html(spanPoTxt);
					$("#spanrec").text('');
	}
/* Vehicle Details Report - End */
	//Dealer Performance Unearned Bonus Report-New - Start	
	if( val == 14)
	{
		var span21Text = "";
		var span22Text = "";
		var dealer = $.trim($('#dealer').val()).split(' ').join('');
		$('#dealer').val(dealer);
		if(dealer != '' )
		{
			dealerSpanTxt = validateDealer(dealer);
			if(dealerSpanTxt.length > 1)
			{	
				flag = false;
			}
		}
		var fromYr = $.trim($('#fromYear').val());
		var toYr = $.trim($('#toYear').val());
		var fromMonth = $.trim($('#fromMonth').val());
		var toMonth = $.trim($('#toMonth').val());
		var fromQuarter = $.trim($('#fromQuarter').val());
		var toQuarter = $.trim($('#toQuarter').val());
		
		if(toYr == -1 && fromYr == -1)
		{
			span22Text = span22Text+"<br>Please Select From and To Years."; 
			flag = false;
		}
		
		if(toYr == -1 && fromYr != -1)
		{
			span22Text = span22Text+"<br>Select To Year."; 
			flag = false;
		}
		else if(toYr != -1 && fromYr == -1)
		{
			span22Text = span22Text+"<br>Select From Year.";
			flag = false;
		}	  
		
		else if(toYr != -1 && fromYr != -1)
		{
			if(toYr < fromYr ){
				span22Text = span22Text+"<br>To Year should not be less than From Year.";  
				flag = false;
			}
			else if(fromMonth != -1 && toMonth != -1){
				if(fromQuarter !=-1 ||  toQuarter !=-1){
					span22Text = span22Text+"<br>Select Month Range or Quarter range, not both.";  
					flag = false;
					
				}
				if(toYr == fromYr && fromMonth > toMonth){
					span22Text = span22Text+"<br>To month should not be less than From month.";  
					flag = false;
				}
			}
			else if(fromQuarter != -1 && toQuarter != -1){
				if(fromMonth !=-1 || toMonth != -1){
					span22Text = span22Text+"<br>Select Month Range or Quarter range, not both.";  
					flag = false;
					
				}
				if(toYr == fromYr && fromQuarter > toQuarter ){
					span22Text = span22Text+"<br>To Quarter should not be less than From Quarter.";  
					flag = false;
				}
			}
											
			else{
			span22Text = span22Text+"<br>Select Month or Quarter Range."; 
			flag = false;
			}
		}
		
		else{
			if(toYr != -1 && fromYr != -1 && fromMonth != -1 && toMonth != -1 && fromQuarter != -1 && toQuarter != -1)
		{
			span22Text = span22Text+"<br>Select either Month Range or Quarter Range but not both."; 
			flag = false;
		}
		}
					
		$("#span12").html(span22Text);
		
		
		$("#dealerErr").html(dealerSpanTxt);
			
		$("#span21").html(span21Text);
		$("#spanrec").text('');
	}
	//Dealer Performance Unearned Bonus Report-New - End
	return flag;
}

function validateDealer(dealer) {
	var regEx=/^([a-z\sA-Z0-9,])*$/;
	var dealerSpanTxt = "";
	var dealArr = new Array();
if(!dealer.match(regEx))
	{		
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should not contain special character.";	
		//$("#dealerErr").html("<br>Dealer(s) Should Not contain Special Charecter.");
		//flag = false;
	}
if(dealer.length > 235)
	{
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should contain 235 characters.";	
		//$("#dealerErr").html("<br>Dealer(s) Should Contain 235 Chareter's.");
		//flag = false;
	}	
if($.trim($('#dealer').val()).match(/\ /))
	{	
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should not contain any space.";	
		//$("#dealerErr").html("<br>Dealer(s) Should not contain any Space.");
		//flag = false;
	}

if(!checkLength(dealer,dealArr))
	{
		dealerSpanTxt = dealerSpanTxt + "<br>Individual Dealer Id should be 5 characters.";	
		if(dealArr.length > 0){
			dealerSpanTxt = dealerSpanTxt+"<br>Problematic Dealer Id(S) are:";
			 for(var i=0; i < dealArr.length;i++){
				 if(i>0){
					 dealerSpanTxt = dealerSpanTxt+ ",";
				 }
				 else{
					 dealerSpanTxt = dealerSpanTxt+ " "; 
				 }
				 dealerSpanTxt = dealerSpanTxt+ dealArr[i];
			 }
		 }
		 //$("#dealerErr").html("<br>Dealer(s)Should not exceed 5 Charecter's.");
		//flag = false;
	}

	return dealerSpanTxt;
}

function verifyVehicles(val)
{
	var regEx=/^([a-z\sA-Z0-9,])*$/;
	var flag = true;
	span9Txt="";
	if(!val.match(regEx))
	{	
		span9Txt = span9Txt+"<br>VIN(S) should not contain any  special character.";
		//flag = false;
	}
	 if(val.length > 235)
	{
		 span9Txt = span9Txt+"<br>VIN(s) should not contain more than 235 characters.";
		//flag = false;
	}	
	if(val.match(/\ /)	)
	{	
		span9Txt =span9Txt+"<br>VIN(S) should not contain any space.";
		//flag = false;
	}
	
	/*if(val.indexOf(",")== 0)
	{
		$("#span9").text("Remove Comma from Begining of the VIN(s).");
		return false;		
	}*/
	
	var probVins = new Array(); 	
	 if(!checkVehLength(val, probVins))
	{
		 span9Txt =span9Txt+"<br>Individual VIN should be of 17 characters.";
		 if(probVins.length > 0){
			 span9Txt = span9Txt+"<br>Problematic VIN(S) are:";
			 for(var i=0; i < probVins.length;i++){
				 if(i>0){
					 span9Txt = span9Txt+ ",";
				 }
				 else{
					 span9Txt = span9Txt+ " "; 
				 }
				 span9Txt = span9Txt+ " " + probVins[i];
			 }
		 }
		 
		 
		// flag = false;
	}
   // $("#span9").html(span9Txt);
	return span9Txt ;
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
	if(repDate > tomorrow){
			reportSpanTxt = reportSpanTxt+"<br>Report Date should not be greater than current date.";
			//$("#rptDtErr").text("Report Date should be less than Current Date.");
			//flag =  false;
		}
	 
   if(!($('#reportDate').val().match(date_regex)))
	    {
	   		  reportSpanTxt = reportSpanTxt+"<br>Enter valid Date in MM/DD/YYYY.";
	   		  //$("#rptDtErr").text("Enter valid Date in MM/DD/YYYY.");
	          //flag =  false;     
	    }
	return reportSpanTxt ;
}


function checkVehLength(val, probVins)
{

	var arr;
	var flag = true;
	var j =0;
	if(val.indexOf(',') > -1)
	{
		arr=val.split(",");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 17 )
			{
				flag = false;
				probVins[j] = arr[i];
				j = j+1;
			}		
		}
	}

	else if(val.length !=0 && val.length != 17)
	{
		flag = false;
	}
	return flag;

}

function checkLength(val,dealArr)
{
	
	var arr;
	var flag = true;
	var j =0;
	if(val.indexOf(',') > -1)
	{
		arr=val.split(",");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 5 )
			{
				flag = false;
				dealArr[j] = arr[i];
				j = j+1;
			}		
		}
	}

	else if(val.length !=0 && val.length != 5)
	{
		flag = false;
	}

	return flag;

}

function verifyPONumbers(val)
{
	var regEx=/^([a-z\sA-Z0-9, ])*$/;
	var spanPoTxt="";
	if(!val.match(regEx))
	{	
		spanPoTxt = spanPoTxt+"<br>PO(S) should not contain any  special character.";
		//flag = false;
	}
	 if(val.length > 235)
	{
		 spanPoTxt = spanPoTxt+"<br>PO(s) should not contain more than 235 characters.";
		//flag = false;
	}	
	/*if(val.match(/\ /)	)
	{	
		spanPoTxt =spanPoTxt+"<br>PO(S) can contain single space.";
		//flag = false;
	}*/
	
	/*if(val.indexOf(",")== 0)
	{
		$("#span9").text("Remove Comma from Begining of the VIN(s).");
		return false;		
	}*/
	
	var invalidPOs = new Array(); 	
	 if(!checkPOLength(val, invalidPOs)){
		 spanPoTxt =spanPoTxt+"<br>Individual PO should be of 10 characters.";
		 if(invalidPOs.length > 0){
			 spanPoTxt = spanPoTxt+"<br>Problematic PO(s) are:";
			 for(var i=0; i < invalidPOs.length;i++){
				 if(i>0){
					 spanPoTxt = spanPoTxt+ ",";
				 }
				 else{
					 spanPoTxt = spanPoTxt+ " "; 
				 }
				 spanPoTxt = spanPoTxt+ " " + invalidPOs[i];
			 }
		 }
	}
   return spanPoTxt ;
}

function checkPOLength(val, invalidPOs)
{
	var arr;
	var flag = true;
	var j =0;
	if(val.indexOf(',') > -1)
	{
		arr=val.split(",");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 10 )
			{
				flag = false;
				invalidPOs[j] = arr[i];
				j = j+1;
			}		
		}
	}
	else if(val.indexOf(' ') > -1)
	{
		arr=val.split(" ");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 10 )
			{
				flag = false;
				invalidPOs[j] = arr[i];
				j = j+1;
			}		
		}
	}

	else if(val.length !=0 && val.length != 10)
	{
		flag = false;
	}
	return flag;
}

function validateDealerVehReport(dealer) {
	var regEx=/^([a-z\sA-Z0-9, ])*$/;
	//var regExSpace=/^([a-z\sA-Z0-9 ])*$/;
	var dealerSpanTxt = "";
	var dealArr = new Array();
if(!dealer.match(regEx))
	{		
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should not contain special character.";	
		//$("#dealerErr").html("<br>Dealer(s) Should Not contain Special Charecter.");
		//flag = false;
	}
if(dealer.length > 235)
	{
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should contain 235 characters.";	
		//$("#dealerErr").html("<br>Dealer(s) Should Contain 235 Chareter's.");
		//flag = false;
	}	
/*if($('#dealer').val().match(/\ /))
	{	
		dealerSpanTxt = dealerSpanTxt + "<br>Dealer(s) should not contain any space.";	
		//$("#dealerErr").html("<br>Dealer(s) Should not contain any Space.");
		//flag = false;
	}*/

if(!checkLengthVehRpt(dealer,dealArr))
	{
		dealerSpanTxt = dealerSpanTxt + "<br>Individual Dealer Id should be 5 characters.";	
		if(dealArr.length > 0){
			dealerSpanTxt = dealerSpanTxt+"<br>Problematic Dealer Id(S) are:";
			 for(var i=0; i < dealArr.length;i++){
				 if(i>0){
					 dealerSpanTxt = dealerSpanTxt+ ",";
				 }
				 else{
					 dealerSpanTxt = dealerSpanTxt+ " "; 
				 }
				 dealerSpanTxt = dealerSpanTxt+ dealArr[i];
			 }
		 }
		 //$("#dealerErr").html("<br>Dealer(s)Should not exceed 5 Charecter's.");
		//flag = false;
	}

	return dealerSpanTxt;
}

function checkLengthVehRpt(val,dealArr)
{
	
	var arr;
	var flag = true;
	var j =0;
	if(val.indexOf(',') > -1)
	{
		arr=val.split(",");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 5 )
			{
				flag = false;
				dealArr[j] = arr[i];
				j = j+1;
			}		
		}
	}
	else if(val.indexOf(' ') > -1)
	{
		arr=val.split(" ");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 5 )
			{
				flag = false;
				dealArr[j] = arr[i];
				j = j+1;
			}		
		}
	}
	else if(val.length !=0 && val.length != 5)
	{
		flag = false;
	}
	return flag;
}

function verifyVehiclesVehRpt(val)
{
	var regEx=/^([a-z\sA-Z0-9, ])*$/;
	var flag = true;
	var spanVINText="";
	if(!val.match(regEx))
	{	
		spanVINText = spanVINText+"<br>VIN(S) should not contain any  special character.";
		//flag = false;
	}
	 if(val.length > 235)
	{
		 spanVINText = spanVINText+"<br>VIN(s) should not contain more than 235 characters.";
		//flag = false;
	}	
	/*if(val.match(/\ /)	)
	{	
		span9Txt =span9Txt+"<br>VIN(S) should not contain any space.";
		//flag = false;
	}*/
	
	/*if(val.indexOf(",")== 0)
	{
		$("#span9").text("Remove Comma from Begining of the VIN(s).");
		return false;		
	}*/
	
	var probVins = new Array(); 	
	 if(!checkVehLengthVehRpt(val, probVins))
	{
		 spanVINText =spanVINText+"<br>Individual VIN should be of 17 characters.";
		 if(probVins.length > 0){
			 spanVINText = spanVINText+"<br>Problematic VIN(S) are:";
			 for(var i=0; i < probVins.length;i++){
				 if(i>0){
					 spanVINText = spanVINText+ ",";
				 }
				 else{
					 spanVINText = spanVINText+ " "; 
				 }
				 spanVINText = spanVINText+ " " + probVins[i];
			 }
		 }
		 
		 
		// flag = false;
	}
   // $("#span9").html(span9Txt);
	return spanVINText ;
}

function checkVehLengthVehRpt(val, probVins)
{

	var arr;
	var flag = true;
	var j =0;
	if(val.indexOf(',') > -1)
	{
		arr=val.split(",");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 17 )
			{
				flag = false;
				probVins[j] = arr[i];
				j = j+1;
			}		
		}
	}
	else if(val.indexOf(' ') > -1)
	{
		arr=val.split(" ");

		for( var i=0;i<arr.length;i++)
		{	
			if ( val.length !=0 && arr[i].length != 17 )
			{
				flag = false;
				probVins[j] = arr[i];
				j = j+1;
			}		
		}
	}

	else if(val.length !=0 && val.length != 17)
	{
		flag = false;
	}
	return flag;

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