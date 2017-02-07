<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB Report Defination</title>
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

</script>
<script type="text/javascript">
$(document).ready(function() {
	    $("#colCount").change(function() {
		    document.getElementById("reportDef").action = 'reportDef';
			document.getElementById("reportDef").submit();
	   	});
	});

$(document).ready(function() {
      	$('input[name=reportDefForm.defStatusCode][value="D"]').click(function() {
            var flagActive = $("#flagActive").val();
            $(".span").empty();
            if(flagActive == "true"){
            $("#mapTable").hide();
            $("#submit").show();
            $("#reset").show();
			document.getElementById("reportId").value =0;
			document.getElementById("reportName").value ="";
			document.getElementById("desc").value ="";
			document.getElementById("colCount").value =0;
			document.getElementById("resLpp").value =0;
			document.getElementById("schedule").value ="D";
			document.getElementById("flagActive").value=false;
			$("#colCount").attr("disabled",false);
			}
		});
	});

$(document).ready(function() {
	var status = $('input[name="reportDefForm.defStatusCode"]:checked').val();
	var flagActive = $("#flagActive").val();
	if(status=="I" && flagActive=="true"){
	$("#submit").hide();
	$("#reset").hide();
	$("#colCount").attr("disabled",true);
	/* document.getElementById("stsChange").value ="I" */;
	}
	});
	
$(document).ready(function() {
	var status = $('input[name="reportDefForm.defStatusCode"]:checked').val();
	var flagActive = $("#flagActive").val();
	if(status=="A" && flagActive=="true"){
	$("#colCount").attr("disabled",true);
	/* document.getElementById("stsChange").value ="A"; */
	}
	});

$(document).ready(function() {
		$("#submitDef").click(function() {
			var returnFlag = true;
			var active = $('input[name=reportDefForm.defStatusCode]:checked').val();
			var flag = $("#flagActive").val();
					var reportName = srt_trim($("#reportName").val());
						$("#reportName").val(reportName);
					var colCount = $("#colCount").val();
					var desc = srt_trim($("#desc").val());
						$("#desc").val(desc);
					var Name_Expression = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
					var Num_Expression = "^([0-9]){1,3}$";
					var rptId = $("#reportId").val();
					var schedule = $("#schedule").val();
					var resLpp = $("#resLpp").val();
					
				if (active == 'A' && flag == "false" || active == 'A' && rptId == 0) {
					$("#span4").empty();
		
					if (reportName == ''){
						$("#span1").text(" Enter Report Name.");
						returnFlag = false;
					}
					else if (!reportName.match(Name_Expression)) {
						$("#span1").text("  Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
						returnFlag = false;
					}else {
					$("#span1").empty();
					}
					if (desc.length > 255) {
						$("#span2").text(" Description should be less than 255 characters.");
						returnFlag = false;
					}else {
					$("#span2").empty();
					}
					if(schedule == ''){
						$("#span5").text(" Select a process schedule.");
						returnFlag = false;
					}else{
					$("#span5").empty();
					}
					if (colCount <= 0) {
						$("#span3").text(" Select the no of sub reports.");
						returnFlag = false;
					}else{
					$("#span3").empty();
					}
					if(resLpp == ""){
						$("#resLpp").val(0);
					}
					else if(!resLpp.match(Num_Expression)){
					$("#span6").text(" Please enter valid number.");
					return false;
					}else {
					$("#span6").empty();
					}
					return returnFlag;
				}else if (active == 'D') {
					$(".span").empty();
					/* $("#span1").empty();
					$("#span2").empty();
					$("#span3").empty();
					$("#span4").empty(); */
						
					if (reportName == '') {
						$("#span1").text(" Enter Report Name.");
						return false;
					}
					else if (!reportName.match(Name_Expression)) {
						$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
						return false;
					}
					else {
					$("#span1").empty();
					}
					if (desc.length > 255) {
						$("#span2").text(" Description should be less than 255 characters.");
						return false;
					}else {
					$("#span2").empty();
					}
					if(resLpp == ""){
						$("#resLpp").val(0);
					}
					else if(!resLpp.match(Num_Expression)){
					$("#span6").text(" Please enter valid number.");
						return false;
					}else {
					$("#span6").empty();
					}
				}
				else if(active == 'I'){
					$(".span").empty();
					/* $("#span1").empty();
					$("#span2").empty();
					$("#span3").empty();
					$("#span5").empty();
					$("#span4").empty(); */
					
					if(rptId <= 0){
					$("#span4").text(" Cannot create a new Inactive Report.");
					return false;
					}
				}else if (active == 'A' && flag == "true" && rptId > 0){
					$("#span4").text(" Cannot re-submit an Active Report.");
					return false;
				}
			});
});

$(document).ready(function() {
$('input[name=reportDefForm.defStatusCode]:checked').click(function() {
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
				$("#mapTable").show();
				$("#colCount").attr("disabled",true);
			}
			else if(stsChange=="A"){
				$("#mapTable").show();
				$("#colCount").attr("disabled",true);
			}
		}
</script>
<!--
if(schedule == ''){
						$("#span5").text("Please select schedule");
						return false;
					}else{
					$("#span5").empty();
					} 
 --></head>
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
<s:form action="saveReportDef" method="post" id="reportDef" name="reportDef">
<s:token></s:token>
<s:hidden name="reportDefForm.flagActive" id="flagActive"></s:hidden>
<s:hidden name="reportDefForm.status" id="stsChange"></s:hidden>
<s:div id="widecontentarea">
<div class="pageTitle" id="HL1">DPB Report Definition</div>
	<s:div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><div class="TX1">The Report Definition as part of DPB is defined here.</div></td>
 				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			</tr>
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
		</table>
	</s:div>
	<table width="200" border="0" class="TBL2">
	<tr>
	  	<td width="30%"><s:label for="reportDefForm.reportDefId" value="Report ID"></s:label></td>
	    <td width="70%"><s:textfield name="reportDefForm.reportDefId" readonly="true" id="reportId"/></td>
	</tr>
	<tr>
	  	<td><s:label for="reportDefForm.reportName" value="Report Name"></s:label></td>
	    <td><s:textfield name="reportDefForm.reportName" id="reportName"/><span class="span" id="span1"></span></td>
      </tr>
	<tr>
	  	<td><s:label for="reportDefForm.description" value="Description"></s:label></td>
	    <td><s:textarea name="reportDefForm.description" id="desc" cols="45" rows="5"/><span class="span" id="span2"></span></td>
      </tr>
	<tr>
	    <td><s:label for="reportDefForm.subReports" value="Number of sub reports"></s:label></td>
	    <td><s:select name="reportDefForm.subReports" id="colCount" list="#{0:'00',1:'01',2:'02',3:'03',4:'04',5:'05',6:'06',7:'07',8:'08',9:'09',10:'10',11:'11',12:'12',13:'13',14:'14',15:'15'}"/>
	    <span class="span" id="span3"></span></td>
      </tr>
	<tr>
		<td><s:label for="reportDefForm.scheduleCode" value="Report Process Schedule"></s:label></td>
		<td><s:select name="reportDefForm.scheduleCode" list ="defSchd"  listKey="scheduleCode"  listValue="scheduleName" id="schedule" /><span class="span" id="span5"></span></td>
		<%-- <td><s:label for="reportDefForm.processSchdule" value="Report Process Schedule"></s:label></td>
		<td><s:select name="reportDefForm.processSchdule" list="#{'':'Select One','D':'Daily','W':'Weekly','B':'BiWeekly','M':'Monthly','Q':'Quarterly','Y':'Yearly'}" id="schedule" /><span id="span5"></span></td> --%>
	  </tr>
	<tr>
		<td><s:label for="reportDefForm.triggerCode" value="Report Processing Trigger"></s:label></td>
		<td><s:radio name="reportDefForm.triggerCode" list ="defTgr"  listKey="triggerCode"  listValue="triggerName" id="trigger" /></td>
		<%-- <td><s:label for="reportDefForm.trigger" value="Report Processing Trigger"></s:label></td>
		<td><s:radio name="reportDefForm.trigger" list="#{'A':'Auto Trigger','M':'Manual'}" id="trigger" /></td> --%>
	  </tr>
	 <tr id="mapTable">
	    <td colspan="2"><table border="0" align="center" cellspacing="0" cols="6" frame="void" rules="none">
	      <colgroup>
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	        <col width="86" />
	       </colgroup>
	      <tbody>
	      <tr>
	          <td width="120" height="17" align="left">Sequence Number</td>
	          <td width="173" align="left">Report Content Header</td>
	          <td width="208" align="left">Report Query</td>
	          <td width="208" align="left">Report Content Footer</td>
	        </tr>
		<s:iterator value="reportDefForm.qcrList" status="qcRel">
	        <tr>
	        <s:hidden name="reportDefForm.qcrList[%{#qcRel.index}].qcrId"></s:hidden>
	          <td><s:textfield name="reportDefForm.qcrList[%{#qcRel.index}].seqNum" id="seqence" readonly="true"/></td>
	          <td><s:select name="reportDefForm.qcrList[%{#qcRel.index}].contId" list="%{reportDefForm.reportContentList}" listKey="key" listvalue="value"/></td>
	          <td><s:select name="reportDefForm.qcrList[%{#qcRel.index}].queryId" list="%{reportDefForm.reportQueryList}" listKey="key" listvalue="value"/></td>
	          <td><s:select name="reportDefForm.qcrList[%{#qcRel.index}].footerId" list="%{reportDefForm.reportContentList}" listKey="key" listvalue="value" headerKey="0" headerValue="Select Footer"/></td>
	        </tr>
	        </s:iterator>
	      </tbody>
	  </table>
	  </td>
	  </tr>
	  <tr>
	    <td><s:label for="reportDefForm.reportLpp" value="Result LPP"></s:label></td>
	    <td><s:textfield name="reportDefForm.reportLpp" id="resLpp"/><span class="span" id="span6"></span></td>
      </tr>
	  <tr>
	  	<td><s:label for="reportDefForm.defStatusCode" value="Status"></s:label></td>
	   <td><s:radio name="reportDefForm.defStatusCode" list ="defSts"  listKey="defStatusCode"  listValue="defStatusName"/></td>
	  	<%-- <td><s:label for="reportDefForm.status" value="Status"></s:label></td>
	   <td><s:radio name="reportDefForm.status" list="#{'D':'Draft','A':'Active','I':'In-Active'}"/></td> --%>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
	     <table>
		<tr>
			<td id="submit"><s:submit key="Submit" method="saveReportDefinition" align="right" theme="simple" id="submitDef" /></td>
			<td id="reset"><s:reset key="Reset" name="reset" onclick="formReset()" align="center" theme="simple" /></td>
			<s:if test="%{fromDPBList == true}">
				<td><s:submit key="Cancel" theme="simple" method="getReportDefintionList" action="reportDefList"/></td>
		     	<s:hidden name="fromDPBList" value="true" id="fromDPBList"/>
			</s:if>
			<s:else>
				<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
			</s:else>
			<%-- <td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td> --%>
		</tr>
		</table>
	<br /><br /><br />
	</s:div>
</s:form>
</body>
</html>