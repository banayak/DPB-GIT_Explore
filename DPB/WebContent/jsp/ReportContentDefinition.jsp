<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB Report Content</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<style>
	span {
		color: red;
	}
	</style>
<script type="text/javascript">
$(document).ready(function() {
      	$('input[name=reportContentDefForm.defStatusCode][value="D"]').click(function() {
            var flagActive = $("#flagActive").val();
            $(".span").empty();
            if(flagActive == "true"){
            $("#submit").show();
            $("#reset").show();
			document.getElementById("rptId").value =0;
			document.getElementById("qrLpp").value =0;
			document.getElementById("rptName").value ="";
			document.getElementById("desc").value ="";
			document.getElementById("content").value ="";
			document.getElementById("flagActive").value=false;
			}
		});
	});

$(document).ready(function() {
	var status = $('input[name="reportContentDefForm.defStatusCode"]:checked').val();
	var flagActive = $("#flagActive").val();
	if(status=="I" && flagActive=="true"){
	$("#submit").hide();
	$("#reset").hide();
	$("#colCount").attr("disabled",true);
	/* document.getElementById("stsChange").value ="I"; */
	}
	});
	
$(document).ready(function() {
	var status = $('input[name="reportContentDefForm.defStatusCode"]:checked').val();
	var flagActive = $("#flagActive").val();
	if(status=="A" && flagActive=="true"){
	$("#colCount").attr("disabled",true);
	}
	});
	
	$(document).ready(function() {
		$("#submitDef").click(function() {
			var returnFlag = true;
			var active = $('input[name=reportContentDefForm.defStatusCode]:checked').val();
			var flag = $("#flagActive").val();
					var rptName = srt_trim($("#rptName").val());
					var desc = srt_trim($("#desc").val());
					var Name_Expression = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
					var rptId = $("#rptId").val();
					var Num_Expression = "^([0-9]){1,3}$";
					var qrLpp = $("#qrLpp").val();
					var content = srt_trim($("#content").val());
					
				if (active == 'A' && flag == "false" || active == 'A' && rptId == 0) {
					$("#span4").empty();
		
					if (rptName == ''){
						$("#span1").text(" Enter Report Content Name.");
						returnFlag = false;
					}else if (!rptName.match(Name_Expression)) {
						$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
						returnFlag = false;
					}else{
					$("#span1").empty();
					}
					if (desc.length > 255) {
						$("#span2").text(" Description should be less than 255 characters.");
						returnFlag = false;
					}else {
					$("#span2").empty();
					}
					if(content == ""){
					$("#span3").text("Enter content.");
						returnFlag = false;
					}else if(content.length > 11999){
					$("#span3").text("Content should be less than 11999 characters.");
						returnFlag = false;
					}else{
					$("#span3").empty();
					}
					if(qrLpp == ""){
						$("#qrLpp").val(0);
					}
					else if(!qrLpp.match(Num_Expression)){
					$("#span5").text(" Please enter valid number.");
						returnFlag = false;
					}else {
					$("#span5").empty();
					}
					return returnFlag;	
				}else if (active == 'D') {
					$(".span").empty();
					/* $("#span1").empty();
					$("#span2").empty();
					$("#span3").empty();
					$("#span4").empty(); */
						
					if (rptName == '') {
						$("#span1").text(" Enter Report Content Name.");
						returnFlag = false;
					}
					else if (!rptName.match(Name_Expression)) {
						$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
						returnFlag = false;
					}
					else {
					$("#span1").empty();
					}
					if (desc.length > 255) {
						$("#span2").text(" Description should be less than 255 characters.");
						returnFlag = false;
					}else {
					$("#span2").empty();
					}
					if(content.length > 11999){
					$("#span3").text(" Content should be less than 11999 characters.");
						returnFlag = false;
					}else{
					$("#span3").empty();
					}
					if(qrLpp == ""){
						$("#qrLpp").val(0);
					}
					else if(!qrLpp.match(Num_Expression)){
					$("#span5").text(" Please enter valid number.");
					returnFlag = false;
					}else {
					$("#span5").empty();
					}
					return returnFlag;
				}
				else if(active == 'I'){
					$(".span").empty();
					/* $("#span1").empty();
					$("#span2").empty();
					$("#span3").empty(); */
					
					if(rptId <= 0){
					$("#span4").text(" Cannot create a new Inactive Report Content.");
					return false;
					}
				}else if (active == 'A' && flag == "true" && rptId > 0){
					$("#span4").text(" Cannot re-submit an Active Report Content.");
					return false;
				}
			});
});

$(document).ready(function() {
	$('input[name=reportContentDefForm.defStatusCode]:checked').change(function() {
		$(".span").empty();
		/* $("#span1").empty();
		$("#span2").empty();
		$("#span3").empty();
		$("#span4").empty();
		$("#span5").empty(); */
	});
});

function srt_trim(str)
		{
	      if((str != "") && (str.indexOf(" ",0)!=-1))
	      {
	         var iMax = str.length;
	         var end = str.length;
	         var c;
				for(var i=0;1<iMax;i++)
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
							c = str.substring(end-1,end)
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
		
		function formReset(){ 
			$(".span").empty();
			var stsChange = $("#stsChange").val();
			if(stsChange=="I"){
				$("#submit").hide();
				$("#reset").hide();
			}
		}
</script>
</head>	
<body>
<span class="span" id="span4">
<s:if test="hasActionErrors()">
	<span>
		<s:actionerror />
	</span>
</s:if>
<s:elseif test="hasActionMessages()">
	<span>
		<s:actionmessage />
	</span>
</s:elseif>
</span>
<s:form action="saveReportContentDef" method="post">
<s:token></s:token>
<s:hidden name="reportContentDefForm.flagActive" id="flagActive"></s:hidden>
<s:hidden name="reportContentDefForm.status" id="stsChange"></s:hidden>
<div id="widecontentarea">
<div class="pageTitle" id="HL1">DPB Report Content Definition</div>
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="../../images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The report file content is defined here.. </div></td>
 
				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			  
			</tr>
			<tr><td colspan="2">	<div class="template8BottomLine"></div></td></tr>
			<tr>
				<td colspan="2" class="line"><img src="../../images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
		</table>
	</div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	  	<td width="30%"><s:label for="reportContentDefForm.reportContentDefId" value="Report Content ID"></s:label></td>
	    <td width="70%"><s:textfield name="reportContentDefForm.reportContentDefId" id="rptId" readonly="true"/></td>
	  </tr>
	  <tr>
	  	<td><s:label for="reportContentDefForm.reportContentName" value="Report Content Name"></s:label></td>
	    <td><s:textfield name="reportContentDefForm.reportContentName" id="rptName"/><span class="span" id="span1"></span></td>
      </tr>
	  <tr>
	  <td><s:label for="reportContentDefForm.reportContentDescription" value="Description"></s:label></td>
	    <td><s:textarea name="reportContentDefForm.reportContentDescription" id="desc" cols="45" rows="5" /><span class="span" id="span2"></span></td>
      </tr>
	  <tr>
	   <td><s:label for="reportContentDefForm.reportContent" value="Content"></s:label></td>
	    <td><s:textarea name="reportContentDefForm.reportContent" id="content" cols="100" rows="11" /><br/><span class="span" id="span3"></span></td>
      </tr>
      <tr>
	  	<td><s:label for="reportContentDefForm.qryRsltLpp" value="Result LPP"></s:label></td>
	   <td><s:textfield name="reportContentDefForm.qryRsltLpp" id="qrLpp" /><span class="span" id="span5"></span></td>
      </tr>
	  <tr>
	  	<td><s:label for="reportContentDefForm.defStatusCode" value="Status"></s:label></td>
	   <td><s:radio name="reportContentDefForm.defStatusCode" list ="defSts"  listKey="defStatusCode"  listValue="defStatusName" />
	  	<%-- <td><s:label for="reportContentDefForm.status" value="Status"></s:label></td>
	   <td><s:radio name="reportContentDefForm.status" list="#{'D':'Draft','A':'Active','I':'In-Active'}" /></td> --%>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
     <table>
		<tr>
			<td id="submit"><s:submit key="Submit" method="saveReportContentDefinition" align="right" theme="simple" id="submitDef" /></td>
			<td id="reset"><s:reset key="Reset" name="reset" onclick="formReset()" valign="center" theme="simple" /></td>
			<s:if test="%{fromDPBList == true}">
				<td><s:submit key="Cancel" theme="simple" method="getReportContentList" action="reportContentDefList"/></td>
		     	<s:hidden name="fromDPBList" value="true" id="fromDPBList"/>
			</s:if>
			<s:else>
				<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
			</s:else>
			<%-- <td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td> --%>
		</tr>
		</table>
	<br /><br /><br />
</div> 
</s:form>
</body>
</html>   
