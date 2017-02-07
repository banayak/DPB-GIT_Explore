<%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>LeadershipBonusDefination</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<style type="text/css">
span {
	color: red;
}
</style>
<script type="text/javascript">
			function cancels(val) {			
				val.form.action = ctxPath + '/ldrShipBonusList';
				val.form.submit();
			}
	


 function onTaboutOnBusinessRsrvPer(val) {
	var form = val.form;
	var decimalSeparator=".";
    var val=""+percent; 
    var calcAmtperUnit = 0.0;
	var percent = document.getElementById("number3").value;
	if(isNaN(percent) || percent < 0 || percent > 100){
		document.getElementById('number4').value= 0.0;
		document.getElementById("number5").value = 0.0;
		alert("Please enter proper percentage");
		return;
	} else {
	     if(val.indexOf(decimalSeparator)< val.length-3)
	     {
	        percent = Math.round(percent * 100) / 100;
	        document.getElementById("number3").value = percent;  
	        document.getElementById("number7").value = 0.0;
	     } 
  } 
	var netAccrualAmt = document.getElementById("number2").value;
	//Business Reserve Amount
	document.getElementById('number4').value= (netAccrualAmt*percent)/100;
	document.getElementById('number4').value=Math.round(document.getElementById('number4').value * 100) / 100;
	//Leadership Bonus Amount 
	if(percent == 0) {
	document.getElementById("number5").value = netAccrualAmt;
	} else {
	document.getElementById("number5").value = netAccrualAmt - document.getElementById('number4').value;
	}
	
}

function onChangeBusinessRsrvAmt(val) {
	var form = val.form;
	var busrsrvamt = document.getElementById("number4").value;
	if(!isNaN(busrsrvamt) && busrsrvamt >= 0 ) {
	var netAccrualAmt = document.getElementById("number2").value;
	var percent = document.getElementById("number3").value;
	var unitCount = document.getElementById("number8").value;
	busrsrvamt = Math.round(busrsrvamt*100)/100 ;
	document.getElementById("number4").value = busrsrvamt; 
	if(Number(busrsrvamt) >= Number(netAccrualAmt)) {
		alert("Please enter less than Unused Amount. Business Reserve Amount-"+busrsrvamt+"-Unused Amount-"+netAccrualAmt);
		document.getElementById('number5').value = 0.0;
		document.getElementById('number7').value = 0.0;
		return;
	}
	//Business Reserve Percentage
	percent = (busrsrvamt/netAccrualAmt) * 100;
	document.getElementById('number3').value = Math.round(percent * 100) / 100;
	//Leadership Bonus Amount 
	document.getElementById('number5').value= Math.round((netAccrualAmt - busrsrvamt) * 100)/100; 
	
	} else {
	document.getElementById("number3").value = 0.0;
	document.getElementById('number5').value = 0.0;
	}
		
}
function onChangeLdrshipAmt(val) {
	var form = val.form;
	var netAccrualAmt = document.getElementById("number2").value;
	var unitCount = document.getElementById("number8").value;
	var ldrshipAmt = document.getElementById("number5").value;
    if(ldrshipAmt ==  undefined || ldrshipAmt == null || ldrshipAmt <= 0  ) {
		document.getElementById("number3").value = 0.0;
	}
	if(!isNaN(ldrshipAmt) && ldrshipAmt >= 0 ) {
	ldrshipAmt = Math.round(ldrshipAmt*100)/100;
	document.getElementById("number5").value = ldrshipAmt;
	if( Number(ldrshipAmt) > Number(netAccrualAmt)) {
		alert("Please enter less than Unused Amount. Leadership Bonus Amount-"+ldrshipAmt+"-Unused Amount-"+netAccrualAmt);
		return;
	}
	//Business Reserve Amount
	document.getElementById("number4").value = Math.round((netAccrualAmt - ldrshipAmt) * 100)/100;
	//Business Reserve Percentage
	var percent = (document.getElementById('number4').value/netAccrualAmt) * 100;
	document.getElementById('number3').value = Math.round(percent * 100) / 100;
	
	} 
	else {
	document.getElementById("number3").value = 0.0;
	document.getElementById('number4').value = 0.0;
	
	}
}

function retrieveUnsedAmt(val) {
	var checkbox=document.getElementsByName("ldrshipbnsdtls.ldrshipAppVehs");
	var count = 0;
	for(i=0;i<checkbox.length;i++) {
		if (document.getElementsByName("ldrshipbnsdtls.ldrshipAppVehs")[i].checked == true){
		count ++;
		var apVeh=document.getElementsByName("ldrshipbnsdtls.ldrshipAppVehs")[i].value;
		}	
	}
	if(count == 0) {
	document.getElementById("number2").value = 0.0;
	document.getElementById("number8").value = 0;
	}
	
	var rtlStrDt = document.getElementById("rtlStartDate").value;
    if(verifyDate(rtlStrDt)) {
	//return true;
	} else {
			 alert("Enter valid retail start date in MM/DD/YYYY format");
             return false;	
	}
	var rtlEnDt = document.getElementById("rtlEndDate").value;
    if(verifyDate(rtlEnDt)) {
	//return true;
	} else {
			 alert("Enter valid retail end date in MM/DD/YYYY format");
             return false;	
	}
	if( count > 0 && strDt != null && strDt.length > 0  && enDt != null && enDt.length > 0 && rtlStrDt != null && rtlStrDt.length > 0 && rtlEnDt != null && rtlEnDt.length > 0) {
		document.getElementById('number3').value = 0.0;
		document.getElementById('number4').value = 0.0;
		document.getElementById('number5').value = 0.0;
		document.getElementById('number7').value = 0.0;
		val.form.action = '<%=request.getContextPath()%>' +'/leadershipBonusDefFYRchange.action';
       	val.form.submit();
	} else {
		return false;
	}
}
function verifyDate(date) {
      var flag = true;
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
                              flag = false;
                          } else if(leap == 0 && fromDate[0] == "02" && fromDate[1] > "28") {
                               flag = false;
                          }  else if((fromDate[0] == "04" || fromDate[0] == "06" || fromDate[0] == "11" || fromDate[0] == "09") && fromDate[0] != "02" && fromDate[1] > "30") {
                                flag = false;
                          } else if((fromDate[0] == "01" || fromDate[0] == "03" || fromDate[0] == "05" || fromDate[0] == "07" || fromDate[0] == "08" || fromDate[0] == "10" || fromDate[0] == "12") && fromDate[1] > "31") {
                               flag = false;
                          } else {
                                    /* if(dateType == 'enddate') {
                                          flag = srt_verifyEndDate(date);
                                    } */
                          }
                    } else {
                              flag = false;
                    }
                  }
                  else
                  {
                        flag = false;
                  }
            }
            return flag;

}



function onChangeStatus(val) {
	var draft = val.value;
	var flagAct = document.getElementById("flagActive").value;
	if(draft == 'D' && flagAct == 'true' ) {
		document.getElementById("select3").value = 0;
		/* document.getElementById("ldrshipname").value =" ";
		document.getElementById("ldrshipname").disabled = false;
		document.getElementById("startDate").value =" ";
		document.getElementById("startDate").disabled = false;
		document.getElementById("endDate").value =" ";
		document.getElementById("endDate").disabled = false;
	 	var checkboxd=document.getElementsByName("ldrshipbnsdtls.ldrshipAppVehs");
		for(i=0;i<checkboxd.length;i++){
		if (document.getElementsByName("ldrshipbnsdtls.ldrshipAppVehs")[i].checked == true){
			document.getElementsByName("ldrshipbnsdtls.ldrshipAppVehs")[i].checked = false;
		 }
		} 
		document.getElementById("number2").value = "";
		document.getElementById("number2").disabled = false;
		document.getElementById("number8").value = "";
		document.getElementById("number8").value = false;
		document.getElementById("number3").value = "";
		document.getElementById("number3").disabled = false;
		document.getElementById("number4").value = "";
		document.getElementById("number4").disabled = false;
		document.getElementById("number5").value = "";
		document.getElementById("number5").disabled = false;
		document.getElementById("number6").value = "";
		document.getElementById("number6").disabled = false;
		document.getElementById("number7").value = "";
		document.getElementById("number7").disabled = false;
		document.getElementById("processDate").value = "";
		document.getElementById("processDate").disabled = false; */
		document.getElementById("submitabc").style.display = 'block';
    	document.getElementById("text14").style.display = 'block';
   	    document.getElementById("flagActive").value =false;
	}	
	if(draft == 'A' && flagAct == 'true' ) {
	document.getElementById("select3").value = 0;
	document.getElementById("flagActive").value = false;
	document.getElementById("submitabc").style.display = 'block';
    document.getElementById("text14").style.display = 'block';
	}
	
	
}
</script>
<script>
$(document).ready(function() {
	var status = $('input[name="ldrshipbnsdtls.status"]:checked').val();
	var flagActive = $("#flagActive").val();
	var flag = true;
        if(status == "I" && flagActive =="true"){
        document.getElementById("submitabc").style.display = 'none';
        document.getElementById("text14").style.display = 'none';
        }
	
	$("#submitabc").click(function() {	
	var bnsName=$("#ldrshipname").val();
	var regex_length = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var rsrvPct =  $("#number3").val();
	var unusedAmt = $("#number2").val();   
	var procDate = $("#processDate").val(); 
	var alpha = "^[0-9]+([\,\.][0-9]+)?$";	
	var regex_amtpercalc = "^[0-9]{1,9}([\,\.][0-9]{1,2})?$";	
	var regex_per ="^(100(\.0{1,2})?([0-9]?[0-9](\.[0-9]{1,2})))$";
	var rsrvAmt = $("#number4").val();
	var ldrspAmt = $("#number5").val();
	var actAmtUnt = $("#number7").val();   
	var totalEligibleVeh = $("#number8").val();
	var rtlStartDate = $("#rtlStartDate").val();
	var rtlEndDate = $("#rtlEndDate").val();
		if (bnsName == '') {
			$("#span1").text(" Enter Leadership Bonus Name.").show();
			flag = false;
		}
		else{
		$("#span1").text(" Enter Leadership Bonus Name.").hide();
			if(!bnsName.match(regex_length) ){
	           		$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
	           		flag = false;
			}
			else{
			$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").hide();
			flag = false;
			if(bnsName.length > 40) {
			$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
	           		flag = false;
			} else {
			$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").hide();
			flag = false;
				if(procDate == ""){
		 		$("#span9").text(" Enter Process Date.").show();
				flag = false;
	 			} else {
	 			$("#span9").text(" Enter Process Date.").hide();
 					if(!verifyDate(procDate))  {
						$("#span9").text(" Please enter valid Process Date.").show();
						flag = false;
				 		} else {
				 		 $("#span9").text(" Please enter valid Process Date.").hide();
												
														if(rtlStartDate==""){
														$("#span2a").text(" Enter Retail Start Date.").show();
														flag = false;
														 } else {
														$("#span2a").text(" Enter Retail Start Date.").hide();
													 		if(!verifyDate(rtlStartDate))  {
															$("#span2a").text(" Please enter valid Retail Start Date.").show();
															flag = false;
													 		} else {
													 		 $("#span2a").text(" Please enter valid Retail Start Date.").hide();
														 			  if(rtlEndDate == ""){
																	   $("#span3a").text(" Enter Retail End Date.").show();
																		flag = false;
																	   } else {
																	  $("#span3a").text(" Enter Retail End Date.").hide();
																	        if(!verifyDate(rtlEndDate))  {
													 					 	$("#span3a").text(" Please enter valid Retail End Date.").show();
													 						} else {
													 						 $("#span3a").text(" Please enter valid Retail End Date.").hide();
													 						 
																				//if(rtlStartDate >= rtlEndDate){
																				//	$("#span3a").text(" Retail End Date should be greater than  Retail Start Date.").show();
																					//flag = false;
																					//}else {
																				//	$("#span3a").text(" Retail End Date should be greater than Retail Start Date.").hide();
																					
																					if(!rsrvPct.match(alpha) ) {
																					$("#span4").text(" Please enter valid Business Reserve Percentage.").show();
								           											flag = false;
																					} else {
																					$("#span4").text(" Please enter valid Business Reserve Percentage.").hide();
																					if(rsrvPct < 0 || rsrvPct > 100) {
																					$("#span4").text(" Please enter valid Business Reserve Percentage.").show();
								           											flag = false;
																					} else {
																					$("#span4").text(" Please enter valid Business Reserve Percentage.").hide();
																						//flag = true;
																						if(rsrvAmt == '' ) {
																						$("#span5").text(" Enter Business Reserve Amount.").show();
								           												flag = false;
																						} else {
																						$("#span5").text("Enter  Business Reserve Amount.").hide();
																						if(!rsrvAmt.match(alpha) ) {
																						$("#span5").text(" Please enter valid Business Reserve Amount.").show();
								           												flag = false;
																						} else {
																						$("#span5").text(" Please enter valid Business Reserve Amount.").hide();
																						if(ldrspAmt == '' ) {
																						$("#span6").text(" Enter Leadership Bonus Amount.").show();
								           												flag = false;
																						} else {
																						$("#span6").text(" Enter Leadership Bonus Amount.").hide();
																						if(!ldrspAmt.match(alpha) ) {
																						$("#span6").text(" Please enter valid Leadership Bonus Amount.").show();
								           												flag = false;
																						} else {
																						$("#span6").text(" Please enter valid Leadership Bonus Amount.").hide();
																						
																						if(actAmtUnt == '' ) {
																						$("#span8").text(" Enter Actual Amount per Unit.").show();
								           												flag = false;
																						} else {
																						$("#span8").text(" Enter Actual Amount per Unit.").hide();
																						if(!actAmtUnt.match(regex_amtpercalc) ) {
																						$("#span8").text(" Please enter valid Actual Amount per Unit.").show();
								           												flag = false;
																						} else {
																						$("#span8").text(" Please enter valid Actual Amount per Unit.").hide();
																						if( (actAmtUnt * totalEligibleVeh) > unusedAmt) {
																						$("#span8").text(" This Per unit amount exceeds the net accrual amount. Please re-check and change the amount.").show();
																						flag = false;
																						} else {
																						$("#span8").text(" This Per unit amount exceeds the net accrual amount. Please re-check and change the amount.").hide();
																						flag = true;
																						} 
																					}
																				}
																			} 
																		}
																	  }	
																   } 
																} 
															}
														} 
													}
												}
											}
  		  								}
  		  						 	  }
  		  							}
  		  					 	 }
  		  					   }
  		  			 		//}
		return flag;
			
		});
		});
</script>
<script>
 function formReset(){ 
 		$(".messages").hide();
		$("#span1").html(" ");
		$("#span2").html(" ");			
		$("#span3").html(" ");	
		$("#span2a").html(" ");
		$("#span3a").html(" ");
		$("#span4").html(" ");
		$("#span5").html(" ");
		$("#span6").html(" ");
		$("#span7").html(" ");
		$("#span8").html(" ");
		$("#span9").html(" ");
		var inactiveDate = $("#inactDate").val();
		if(inactiveDate.length > 0){
		document.getElementById("submitabc").style.display = 'none';
		document.getElementById("text14").style.display = 'none';
		document.getElementById("flagActive").value =true;
		}
 }
 </script>
<sj:head jquerytheme="flick" ></sj:head>	
</head>
<body>
	<s:if test="hasActionErrors()">
		<p></p>
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
<sj:head jquerytheme="flick"/>
	<s:if test="hasActionMessages()"><p></p>
	<b>
   			<div class="messages" ></b>
     		<s:actionmessage/>
  			 </div>
	</s:if>
	
	<s:form name ="leadershipBonusDef" id="leadershipBonusDef" action="leadershipBonusDef" method="post">
	<s:token/>
		<s:hidden name="ldrshipbnsdtls.unitCountVal" value= "%{ldrshipbnsdtls.unitCount}" id="number8"  />
		<s:hidden name="ldrshipidLinkView" value= "%{ldrshipidLinkView}" id="ldrshipidLinkView"   />
		<s:hidden name="ldrshipbnsdtls.flagActive" value= "%{ldrshipbnsdtls.flagActive}" id="flagActive"   />
		<s:hidden name="fromDPBList" value="%{fromDPBList}" id="fromDPBList"/>
		<s:hidden name="inactDate"  value = "%{inactDate}" id="inactDate" />
		<div id="widecontentarea">
			<div class="pageTitle" id="HL1">DPB Leadership Bonus Definition</div>
			
			<div class="T8">
				<table width="728" border="0" cellspacing="0"
					class="template8TableTop">
					<tr>
						<td colspan="2" class="line"><img src="<%=request.getContextPath()%>/images/img_t8_line1.gif" height="1" alt="" border="0">
						</td>
					</tr>
					<tr>
						<td width="362" class="left" id="ctl00_tdCopyText">
						<div class="TX1">The DPB application provides the option to
								business users to define and update the leadership bonus for
								current and future fiscal year that needs to be paid.</div>
						</td>
						<td width="363" class="right">
							<div align="left">
								<img id="ctl00_imgLevel2" src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg" style="border-width: 0px;" /><br>
						    </div>
						</td>
					</tr>

					<tr>
						<td colspan="2">
						  <tr><td colspan="2"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0">
						  </td>
						  </tr>
										
						</td>
						</tr>
				</table>
			</div>
			<table width="200" border="0" class="TBL2">
				<tr>
					<td><s:label value ="Leadership Bonus ID"></s:label></td>									
						<td>
					    <s:textfield name="ldrshipbnsdtls.ldrshipid" id="select3" readonly="true" />
						</td>	
				</tr>
				<tr>
					<td><s:label value ="Leadership Bonus Name"></s:label></td>
						<td>
					    	<s:textfield name="ldrshipbnsdtls.ldrshipname" id="ldrshipname" /><span id="span1" ></span>
						</td>
				</tr>	
				<tr>
					<td><s:label value ="Accrual Start Date"></s:label></td>
						<td>
					   		<sj:datepicker name="ldrshipbnsdtls.startDate" id = "startDate" displayFormat="mm/dd/yy" showOn="focus" onChange="retrieveUnsedAmt(this);" /> 
							<span id="span2" ></span>
						</td>
				</tr>	
				<tr>
					<td><s:label value ="Accrual End Date"></s:label></td>
						<td>
					    	<sj:datepicker name="ldrshipbnsdtls.endDate" id="endDate" displayFormat="mm/dd/yy"  showOn="focus" onchange="retrieveUnsedAmt(this);" />
							<span id="span3" ></span>
						</td>			
				</tr>	
				<tr>
					<td><s:label value ="Retail Start Date"></s:label></td>
						<td>
					   		<sj:datepicker name="ldrshipbnsdtls.rtlStartDate" id = "rtlStartDate" displayFormat="mm/dd/yy" showOn="focus" onChange="retrieveUnsedAmt(this);" /> 
							<span id="span2a" ></span>
						</td>
				</tr>	
				<tr>
					<td><s:label value ="Retail End Date"></s:label></td>
						<td>
					    	<sj:datepicker name="ldrshipbnsdtls.rtlEndDate" id="rtlEndDate" displayFormat="mm/dd/yy"  showOn="focus" onchange="retrieveUnsedAmt(this);" />
							<span id="span3a" ></span>
						</td>			
				</tr>
				<tr>
					<td><s:label value ="Applicable Vehicles"></s:label></td>
						<td>
					    	<s:checkboxlist theme="vertical-checkbox" list="vehicleList" name="ldrshipbnsdtls.ldrshipAppVehs" listKey="id"  listValue="vehicleType" onclick="retrieveUnsedAmt(this);" />
						</td>
				</tr>
				<tr>
					<td><s:label value ="Unused Amount:"></s:label></td>
					<td>
						<p>
							$<s:textfield name="ldrshipbnsdtls.unusdamt"  id="number2"  />
						</p>
					</td>
				</tr>
				<tr>
					<td><s:label value ="Leadership Eligible Units"></s:label></td>
					<td><s:textfield name="ldrshipbnsdtls.unitCount"  id="number8" ></s:textfield></td>
				</tr>
				<tr>
					<td><s:label value ="Business Reserve Percentage"></s:label></td>
						<td>
							<s:textfield name="ldrshipbnsdtls.busresvper"  id="number3"   onchange="onTaboutOnBusinessRsrvPer(this);"/>%
							<span id="span4" > </span>
						</td>
				</tr>
				<tr>
					<td><s:label value ="Business Reserve Amount"></s:label></td>
						<td>
							$<s:textfield name="ldrshipbnsdtls.busresvamt"  id="number4" onchange="onChangeBusinessRsrvAmt(this)" />
							<span id="span5" > </span>
						</td>
				</tr>
				<tr>
					<td><s:label value ="Leadership Bonus Amount"></s:label></td>
						<td>
							$<s:textfield name="ldrshipbnsdtls.ldrbnsamt"  id="number5" onchange="onChangeLdrshipAmt(this)"   />
						 	Note: Based on the Business reserve and Unused amount, this Leadership Amount will be calculated.
						 	<p>
						 	<span id="span6" > </span></p>
						 </td>
				</tr>
				
				<tr>
				<td><s:label value ="Actual Amount per Unit"></s:label></td>
						<td>
							$<s:textfield name="ldrshipbnsdtls.actamtperunt" id="number7"   />
							<span id="span8" > </span>
						</td>
				</tr>
				<tr>
					<td><s:label value ="Process Date"></s:label></td>
						<td>
					    	<sj:datepicker name="ldrshipbnsdtls.processDate" id="processDate" displayFormat="mm/dd/yy"  showOn="focus" />
							<span id="span9" > </span>
						</td>		
				</tr>	
				<tr>
					<td><s:label value ="Status"></s:label></td>
					<td><s:radio  name="ldrshipbnsdtls.status" list="#{'D':'Draft','A':'Active','I':'In-Active'}" id = "status" onchange="onChangeStatus(this)" /><del></del>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>				
				</table>
				<table>
					<tr>
						<td><s:submit method="getLdrShipBonus" type="button" id="submitabc" value="Submit" align="left" theme="simple" action="leadershipBonusDefSubmit" /></td>
						<td id="text14"><s:reset  type="button" key="Reset" align="center"  value="Reset" theme="simple" onclick="formReset()" /></td>
						<s:if test="%{fromDPBList == true}">
						<td><s:submit type="button" name="button" key="cancel" value="Cancel" onclick="cancels(this)" theme="simple"/></td>
						</s:if><s:else>
						<td><s:submit type="button" name="button" key="cancel" value="Cancel" onclick="cancel(this)" theme="simple"/></td>
						</s:else>
						
					</tr>
			</table>
			<br />
			<br />
			<br />
		</div>
	</s:form>
</body>
</html>