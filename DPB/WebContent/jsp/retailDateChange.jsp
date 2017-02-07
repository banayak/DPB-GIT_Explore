<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>

<head>
<title>DPB Retail Date Change</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			<script type="text/javascript">
			
	function onDateChange() {	
	
	
	$("#span3").html(" ");
	$("#span4").html(" ");
	
		var rtlEndDt = document.getElementById("rtlEndDate").value;
		var rtlStartDt = document.getElementById("rtlStartDate").value;
		
		var rStartDate=rtlStartDt.replace(/-/g,"/");
		var startDate = new Date(rStartDate);
		
		var rEndDate=rtlEndDt.replace(/-/g,"/");
		var endDate = new Date(rEndDate);
		var date =new Date();
		var tomorrow = new Date(date.getTime() + 24 * 60 * 60 * 1000);
		var flag = true;
	    if(verifyDate(rtlEndDt)) {
		//return true;
		} else {
			// alert("Enter valid Retail End Date in MM/DD/YYYY format");
			$("#span4").text("Enter valid Retail End Date in MM/DD/YYYY format").show();
			 flag =  false;	
		}
		
		
		if((endDate <= date) || (endDate <= startDate)){
//		alert("Retail End Date should be greater than Current Date/Retail Start date");
	$("#span4").text("Retail End Date should be greater than Current Date/Retail Start date").show();	
	//$("#span4").text("").show();
		flag = false;
		}
		}  
		

			
			function monthYearValidation(){
				$("#span1").html(" ");
				$("#span2").html(" ");
				var  month = rForm.mnth.value;
			 	var year = rForm.currentYr.value;
				var d = new Date(year,month-1);  // month is 0 to 11
			  	var date1 =new Date();
			   	var i =year.length;
			   	var regex_number = "^[0-9]+([\.][0-9]+)?$";
				   	if (!(year).match(regex_number)  || i >4){
					//alert("Enter valid year");
					$("#span2").text("Enter valid Year").show();
					}else{
					
			         if(d.getFullYear() < date1.getFullYear() && d.getMonth() <  date1.getMonth()){
			         	//alert("Select future Year and Month");
			         	$("#span2").text("Select future Year and Month").show();
			         	return false;
			         }if(d.getFullYear() < date1.getFullYear()){
			         //	alert("Select future Year");
			         	$("#span2").text("Select future Year").show();
			         	return false;
		         	}
			         if(d.getMonth() < date1.getMonth() && d.getFullYear() <= date1.getFullYear()){
					   // alert("Select future Month");
					   $("#span1").text("Select future Month").show();
					    return false;
		            }
		        	else{
		            document.getElementById("rForm").action='rtlDateChange';
					document.getElementById("rForm").submit();
		       		}
		       	}
       		}
			
	
	// To verify dates
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
</script>
<script>
$(document).ready(function() {
$("#span1").html(" ");
	$("#span2").html(" ");
	$("#span3").html(" ");
	$("#span4").html(" ");
		$("#rForm_rtlDateChange_submitDateChange").click(function() {
				var flag = true;
	
				var  month = rForm.mnth.value;
			 	var year = rForm.currentYr.value;
				var d = new Date(year,month-1);  // month is 0 to 11
			  	var date1 =new Date();
			   	var regex_number = "^[0-9]+([\.][0-9]+)?$";
			   	
			   	if (!(year).match(regex_number) && year.length > 4){
					flag = false;
				}else{
				 if(d.getFullYear() < date1.getFullYear() && d.getMonth() <  date1.getMonth()){
		         	flag = false;
		         }if(d.getFullYear() < date1.getFullYear()){
		         	flag = false;
		         }
		         if(d.getMonth() < date1.getMonth() && d.getFullYear() <= date1.getFullYear()){
				    flag = false;
		         }
		         }
		         
		var rtlEndDt = document.getElementById("rtlEndDate").value;
		var rtlStartDt = document.getElementById("rtlStartDate").value;
		
		var rStartDate=rtlStartDt.replace(/-/g,"/");
		var startDate = new Date(rStartDate);
		
		var rEndDate=rtlEndDt.replace(/-/g,"/");
		var endDate = new Date(rEndDate);
		var date =new Date();
		 if(verifyDate(rtlEndDt)) {
		//return true;
		} else {
			 flag =  false;	
		}
		
		
		if((endDate <= date) || (endDate <= startDate)){
		flag = false;
		}
	
	if(flag == false){
		alert("Retail Data contains validation errors. Please verify and re-sbumit again.");
		return false;
	}else{
		return true;
	}
	});	
	
	});		

</script>
<script>
 function formReset(){ 
 		$(".errors").hide();
 		$("#span1").html(" ");
	$("#span2").html(" ");
	$("#span3").html(" ");
	$("#span4").html(" ");
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
	
</style>
</head>

<body>
<sj:head jquerytheme="flick"/>
<s:if test="hasActionErrors()">
		<p></p>
		<div class="errors">
			<s:actionerror />
		</div>
	</s:if>
<s:form action="rtlDateChange" id="rForm" method="post">

<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	DPB Retail Date Change
</div>
 <div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0" ></td>
			</tr>
			<tr>
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Mercedes-Benz Dealer Performance Bonus Program, which was initially launched on January 3, 2008, has
				  been updated for 2013 and now provides all qualified authorized dealers in good standing an opportunity to
				  earn a performance bonus of up to 5.50% of Manufacturers Suggested Retail Price on eligible transactions.. </div></td>
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
	
</div><table width="200" border="0" class="TBL2">
				
				<tr>
					<td width="30%">
						<s:label for="dateForm.currentMonth" value="Retail Month"></s:label></td>
					<td width="70%">
						<s:select name="dateForm.currentMonth" label="Retail Month"   list="#{'01':'January','02':'February','03':'March','04':'April','05':'May','06':'June','07':'July','08':'August','09':'September','10':'October','11':'November','12':'December'}"  listValue="value"onchange="javascript:monthYearValidation()" id="mnth"/><span id="span1"></span>
				</tr>
				<tr>
					<td><s:label value ="Retail Year"></s:label></td>
						<td>
					    	<s:textfield name="dateForm.currentYear" id="currentYr" onchange="javascript:monthYearValidation()"   />
							<span id="span2" ></span>
						</td>			
				</tr>
				<tr>
					<td><s:label value ="Retail Start Date"></s:label></td>
						<td>
					   		<s:textfield name="dateForm.rtlStartDate" id = "rtlStartDate" readonly="true"/> 
							<span id="span3" ></span>
						</td>
				</tr>	
				<tr>
					<td><s:label value ="Retail End Date"></s:label></td>
						<td>
					    	<sj:datepicker name="dateForm.rtlEndDate" id="rtlEndDate" displayFormat="mm/dd/yy"  showOn="focus" onchange="javascript:onDateChange()"/>
							<span id="span4" ></span>
						</td>			
				</tr>
				 
				 
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>				
				</table>
				<table>
					<tr>
						<td id="text13">  <s:submit  align="right" value="Submit" method="submitDateChange" action="rtlDateChange"/>  </td>
		<td id="text14"><s:reset key="reset" value="Reset" align="center" onclick="formReset()"/></td>
		
		<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
					</tr>
			</table>
<br /><br />
</div>
</s:form>
</body>
</html>