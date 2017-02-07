<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>


<title>DPB Condition Definition</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link href="<%=request.getContextPath()%>/css/master.css" type="text/css" rel="stylesheet" />
			<link href="<%=request.getContextPath()%>/css/admin.css" type="text/css" rel="stylesheet" />
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
			

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
		font-weight:bold;
		color:blue
		}
	
</style>

<script>
 function formReset(){ 
$("#errorMessageDisplay").html(" ");
$("#regExpspan").html(" ");
$("#condnamespan").html(" ");
$("#checkvalspan").html(" ");
$("#variablenamspan").html(" ");
$("#conddescrspan").html(" ");

var oldStatus = $("#statusChange").val();

if(oldStatus == 'I'){
document.getElementById("text13").style.display = 'none';
document.getElementById("text14").style.display = 'none';
}
}
 </script>
 
<script>

$(document).ready(function() {
var status = $("input[name='conditionsDefForm.status']:checked").val();
var flagActive = $("#flagActive").val();


if(status=="I" && flagActive== 'true'){
		document.getElementById("text13").style.display = 'none';
		document.getElementById("text14").style.display = 'none';
}
$('input[name=conditionsDefForm.status][value="D"]').click(function() {	
if(flagActive == 'true'){
	$("#errorMessageDisplay").html(" ");
	document.getElementById("id").value =0;
	document.getElementById("text13").style.display = 'block';
    document.getElementById("text14").style.display = 'block';
    document.getElementById("flagActive").value = false;
}
});
		
});


$(document).ready(function() {

if($("#condition").val()=='R'){
	$("#checkValue").attr("disabled",true);
		$("#regularExp").attr("disabled",false);
		} else {
		$("#checkValue").attr("disabled",false);
		$("#regularExp").attr("disabled",true);
		
		}
$("#condition").change(function() {
if($("#condition").val()=='R'){
		$("#checkValue").attr("disabled",true);
		$("#regularExp").attr("disabled",false);
		} else {
		$("#checkValue").attr("disabled",false);
		$("#regularExp").attr("disabled",true);
		
		}
		
});
	
	
	
$("#submit").click(function() {
		$("#errorMessageDisplay").html(" ");
		$("#regExpspan").html(" ");
		$("#condnamespan").html(" ");
		$("#checkvalspan").html(" ");
		$("#variablenamspan").html(" ");
		$("#conddescrspan").html(" ");

		var flag = true;
		var status = $("input[name='conditionsDefForm.status']:checked").val();
		var flagActive = $("#flagActive").val();
		
		if (status == 'A' && flagActive == 'false' ) {
			var regex_length =  "^([A-Za-z][a-zA-Z0-9_ -]{0,40})$";
			var cond_regex = "^[a-zA-Z_]*$";
			var conditionName =  $("#conditionName").val();
			var variableName  =  $("#variableName").val();
			var checkValue    =  trim($("#checkValue").val());
			$("#checkValue").val(checkValue);
			var description   =  $("#conditionDesc").val();
			var regularExp   =  trim($("#regularExp").val());
			var condition = $("#condition").val();
			$("#variablenamspan").text(" Variable Name should contain up to 40 characters. May contain alphabets and underscores.").hide();
			if (conditionName == '') {
				$("#condnamespan").text(" Enter condition name.").show();
				flag = false;
			}else{
				if(!conditionName.match(regex_length)){
              		$("#condnamespan").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
              		flag = false;
				}
				if(conditionName.length >40){
              		$("#condnamespan").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
              		flag = false;
				}
			}
			if (description.length > 235) {
					$("#conddescrspan").text(" Description should be less than 255 characters.").show();
					flag = false;
				} /* else {
				$("#conddescrspan").text("Enter description").hide();
				} */
			
			if(condition=='R'){
				if(regularExp==''){
					$("#regExpspan").text(" Enter Regular Expression.").show();
					flag = false;
				}
				if(regularExp.length >235){
					$("#regExpspan").text(" Regular Expression should be less than 255 characters.").show();
					flag = false;
				}
			} else {
			if($("#condition").length> 0){
					if(checkValue==''){
					$("#checkvalspan").text(" Enter Check Value.").show();
					flag = false;
					}
			else if(checkValue.length > 235){
			    $("#checkvalspan").text(" Check Value should be less than 255 characters.").show();
				flag = false;                                       
			}
			}
		}
		if(variableName==''){
				$("#variablenamspan").text(" Enter Variable Name.").show();
				flag = false;
			}
			else if(!(variableName).match(cond_regex)){
			    $("#variablenamspan").text(" Variable Name should contain up to 40 characters. May contain alphabets and underscores.").show();
				flag = false;
				}                                       
			else if(variableName.length > 40){
			    $("#variablenamspan").text(" Variable Name should contain up to 40 characters. May contain alphabets and underscores.").show();
				flag = false;                                       
			}		
	}
		else if (status == 'D') {
			var regex_length =  "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
			var variableName  = $("#variableName").val();
			var conditionName = $("#conditionName").val();
			var cond_regex = "^[a-zA-Z_]*$";
			var checkValue    =  trim($("#checkValue").val());
			var regularExp   =  trim($("#regularExp").val());
			var description   =  $("#conditionDesc").val();
			if (conditionName == '') {
				$("#condnamespan").text(" Enter Condition name.").show();
				flag = false;
			} else if(!conditionName.match(regex_length)){
				$("#condnamespan").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
				flag = false;
			}
			if(conditionName.length >40){
				$("#condnamespan").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.").show();
				flag = false;
			}
			if (description.length > 235) {
					$("#conddescrspan").text(" Description should be less than 255 characters.").show();
					flag = false;
			}
			if(variableName==''){
				$("#variablenamspan").text(" Enter Variable Name.").show();
				flag = false;
			}	
			if(!(variableName).match(cond_regex)){
			    $("#variablenamspan").text(" Variable Name should contain up to 40 characters. May contain alphabets and underscores.").show();
				flag = false;                                       
			}
			if(variableName.length > 40){
			    $("#variablenamspan").text(" Variable Name Name should contain up to 40 characters. May contain alphabets and underscores.").show();
				flag = false;                                       
			}
			if(checkValue.length > 235){
			    $("#checkvalspan").text(" Check Value should be less than 255 characters.").show();
				flag = false;                                       
			}			
			
			if(regularExp.length > 235){
			    $("#regExpspan").text(" Regular Expression should be less than 255 characters.").show();
				flag = false;                                       
			}
		}
		return flag;
			
});
});

</script>

<script type="text/javascript">
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
<body>
<s:if test="hasActionErrors()">
		<p></p>
		<div class="errors" id="errorMessageDisplay">
			<s:actionerror />
		</div>
	</s:if>
  
	<s:form action="conditionDefinition" method="post" name="mainForm" id="myform"  >
 			<s:token/>
		<div id="widecontentarea">
			<div class="pageTitle" id="HL1">DPB Condition</div>
					
			<div class="T8">
				<table width="728" border="0" cellspacing="0"
					class="template8TableTop">

					<tr>
						<td colspan="2" class="line"><img
							src="<%=request.getContextPath()%>/images/img_t8_line1.gif"
							height="1" alt="" border="0"></td>
					</tr>
					<tr>
						<td width="362" class="left" id="ctl00_tdCopyText"><div
								class="TX1">The conditions for the Dealer Performance
								Bonus (DPB) special programs, Vehicle type check, DPB program
								block check.</div></td>

						<td width="363" class="right"><div align="left">
								<img id="ctl00_imgLevel2"
									src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg"
									style="border-width: 0px;" /><br>
							</div></td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="template8BottomLine">
							</div>
						</td>
					</tr>
				</table>
			</div>
	<table width="200" border="0" class="TBL2">
	
		<tr>
			<td width="30%"><s:label  value="Condition Id" for="conditionsDefForm.id"  /></td>
			<td width="70%"><s:textfield label="Condition Id" name="conditionsDefForm.id" readonly="true" id="id"  /></td>
			<s:hidden name="conditionsDefForm.flagActive"  id="flagActive"></s:hidden>
			<s:hidden name="conditionsDefForm.statusChange" id="statusChange" ></s:hidden>
		</tr>
		<tr>
			<td><s:label  value="Condition Name" for="conditionsDefForm.conditionName" /></td>
			<td><s:textfield label="Condition Name" name="conditionsDefForm.conditionName" id="conditionName"  /><span id="condnamespan"></span></td>
		</tr>
		<tr>
			<td><s:label  value="Description" for="conditionsDefForm.conditionDesc" /></td>
			<td><s:textarea label="Description" name="conditionsDefForm.conditionDesc" id="conditionDesc" cols="45" rows="5"  /><span id="conddescrspan"></span></td>
		</tr>
		<tr>
			<td><s:label  value="Condition" for="conditionsDefForm.condition" /></td>
			<td><s:select label="Condition" name="conditionsDefForm.condition" id="condition" list="condCode" listKey="conditionCode" listValue="conditionName"  /></td>
		</tr>
		<tr>
			<td><s:label  value="Variable Name (Check to)" for="conditionsDefForm.variableName"  disabled="true"/></td>
			<td><s:textfield label="Variable Name (Check to)" name="conditionsDefForm.variableName" id="variableName"   /><span id="variablenamspan"></span></td>
		</tr>
		<tr>
			<td><s:label  value="Check Value" for="conditionsDefForm.checkValue" /></td>
			<td><s:textfield label="Check Value" name="conditionsDefForm.checkValue" id="checkValue"  /><span id="checkvalspan"></span></td>
		</tr>
		<tr>
			<td><s:label  value="Condition Type" for="conditionsDefForm.conditionType" /></td>
			<td><s:select label="Condition Type" name="conditionsDefForm.conditionType" id="conditionType" list="condType" listKey="conditionTypeCode" listValue="conditionCodeName"  /></td>
		</tr>
		<tr>
			<td><s:label  value="Format String (Regular Exp)" for="conditionsDefForm.regularExp" /></td>
			<td><s:textfield label="Format String (Regular Exp)" name="conditionsDefForm.regularExp" id="regularExp"   /><span id="regExpspan"></span></td>
		</tr>
		<tr>
			<td><s:label  value="Status" for="conditionsDefForm.status" /></td>
			<td>
				<%-- <s:radio label="Status" name="conditionsDefForm.status" id="Status"   list="#{'D':'Draft','A':'Active','I':'In-Active'}"  /> --%>
				<s:radio name="conditionsDefForm.status" list ="defSts"  listKey="defStatusCode"  listValue="defStatusName"   />
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table>
		<tr>
			<td id="text13"><s:submit id="submit" value="Submit" method="saveCondition" action="conditionDefActionToken" /></td>
			<td id="text14"><s:reset name="reset" id="reset" value="Reset" onclick="formReset()" /></td>
			<s:if test="%{fromDPBList == true}">
			<td><s:submit key="Cancel" theme="simple" method="getCondtionsList" action="conditionListView"/></td>
			    <s:hidden name="fromDPBList" value="true" id="fromDPBList"/>
			</s:if><s:else>
			<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
			</s:else>
		</tr>
	</table>
	</div>
	</s:form>
</body>
</html>