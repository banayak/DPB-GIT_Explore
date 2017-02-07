<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB Input File</title>
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
    $('#readTime').change(function() {
	 	var inTime =$("#inTime").val();
		var readTime = $("#readTime").val();
    if(inTime > readTime){
    $("#span8").text(" Process Time should be greater than In Time.");
    return false;
    }else{
    $("#span8").empty();
    }
   	});
});				

$(document).ready(function() {
      	$('input[name=fileDefForm.defStatusCode][value="D"]').click(function() {
            $(".span").empty();
            var flagActive = $("#flagActive").val();
            if(flagActive == "true"){
			document.getElementById("defId").value =0;
			document.getElementById("defName").value ="";
			document.getElementById("formatName").value ="";
			document.getElementById("desc").value ="";
			document.getElementById("inTime").value ="00:00";
			document.getElementById("readTime").value ="00:00";
			document.getElementById("formatId").value =0;
			document.getElementById("directory").value ="";
			document.getElementById("schedule").value ="D";			
			document.getElementById("flagActive").value=false;
			$("#submit").show();
			$("#reset").show();
			}
		});
	});

$(document).ready(function() {
	var status = $('input[name="fileDefForm.defStatusCode"]:checked').val();
	var flagActive = $("#flagActive").val();
	if(status=="I" && flagActive=="true"){
	$("#submit").hide();
	$("#reset").hide();
	/* document.getElementById("stsChange").value ="I"; */
	}
	});

$(document).ready(function() {
		$("#submitDef").click(function() {
			var returnFlag = true;
			var active = $('input[name=fileDefForm.defStatusCode]:checked').val();
			var flag = $("#flagActive").val();
				var rptId = $("#defId").val();
				var Name_Expression = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
				var dir = "^/([^/\0]+(/)?)+$";
				var defName = srt_trim($("#defName").val());
					$("#defName").val(defName);
				var desc = srt_trim($("#desc").val());
					$("#desc").val(desc);
				var formatId = $("#formatId").val();
				var directory = srt_trim($("#directory").val());
					$("#directory").val(directory);
				var format = srt_trim($("#formatName").val());
					$("#formatName").val(format);
				var schedule = $("#schedule").val();
				var inTime =$("#inTime").val();
				var readTime = $("#readTime").val();
				
				if (active == 'A' && flag == "false" || active == 'A' && rptId == 0) {
					$("#span5").empty();
		
					if (defName == '') {
						$("#span1").text(" Enter File Definition Name.");
						returnFlag = false;
					} else if (!defName.match(Name_Expression)) {
						$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
						returnFlag = false;
					}else{
					$("#span1").empty();
					}
					if(format == ''){
					$("#span6").text(" Enter File Format Name.");
					returnFlag = false;
					}
					else if(!format.match(Name_Expression)){
					$("#span6").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
					returnFlag = false;
					}else{
					$("#span6").empty();
					}
					if (desc.length > 255) {
						$("#span2").text(" Description should be less than 255 characters.");
						returnFlag = false;
					}else{
						$("#span2").empty();
					}
					if(inTime > readTime){
					    $("#span8").text(" Process Time should be greater than In Time.");
					    returnFlag = false;
				    }else{
				    	$("#span8").empty();
				    }
					if (formatId == 0) {
						$("#span3").text(" Select Format Name.");
						returnFlag = false;
					}else{
						$("#span3").empty();
					}
					if (directory == '') {
						$("#span4").text(" Enter directory structure.");
						returnFlag = false;
					}else if (!directory.match(dir)) {
						$("#span4").text(" Please enter valid Base Directory.");
						returnFlag = false;
					}else if (directory.length > 255) {
						$("#span4").text(" Base Directory should be less than 255 characters.");
						returnFlag = false;
					}else{
					$("#span4").empty();
					}
					if(schedule == ''){
						$("#span7").text(" Select File Process Schedule.");
						returnFlag = false;
					}else{
					$("#span7").empty();
					}
					return returnFlag;
				} 
				else if (active == 'D') {
					$("#defName").empty();
					$(".span").empty();
					/* $("#span1").empty();
					$("#span2").empty();
					$("#span3").empty();
					$("#span4").empty();
					$("#span5").empty(); */
					
					if (defName == '') {
							$("#span1").text(" Enter File Definition Name. ");
						returnFlag = false;
					} else if (!defName.match(Name_Expression)) {
						$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
						returnFlag = false;
					}else {
					$("#span1").empty();
					}
					if (desc.length > 255) {
						$("#span2").text(" Description should be less than 255 characters.");
						returnFlag = false;
					}else{
						$("#span2").empty();
					}
					if (directory.length > 255) {
						$("#span4").text(" Base Directory should be less than 255 characters.");
						returnFlag = false;
					}else{
					$("#span4").empty();
					}
					if(!format=="" && !format.match(Name_Expression)){
					$("#span6").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
					returnFlag = false;
					}else{
					$("#span6").empty();
					}
					if(formatId == 0){
						$("#span3").text(" Select Format Name.");
						returnFlag = false;
					}else {
					$("#span3").empty();
					}
					return returnFlag;
				}
				else if(active == 'I'){
					$(".span").empty();
					/* $("#span1").empty();
					$("#span2").empty();
					$("#span3").empty();
					$("#span4").empty(); */
					
					if(rptId <= 0){
					$("#span5").text(" Cannot create a new Inactive Input File.");
					return false;
					}
				}
				else if (active == 'A' && flag == "true" && rptId > 0){
					$("#span5").text(" Cannot re-submit an Active Input File.");
					return false;
				}
		});
});

$(document).ready(function() {
	$('input[name=fileDefForm.defStatusCode]:checked').change(function() {
		$(".span").empty();
		/* $("#span1").empty();
		$("#span2").empty();
		$("#span3").empty();
		$("#span4").empty();
		$("#span5").empty();
		$("#span6").empty();
		$("#span7").empty(); */
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
<!-- if(schedule == ''){
						$("#span7").text("Please select schedule");
						returnFlag = false;
					}else{
					$("#span7").empty();
					}
 -->	</head>
	<body>
	<span class="span" id="span5">
	<s:if test="hasActionErrors()">
		<p>
			<s:actionerror />
		</p>
	</s:if>
	<s:elseif test="hasActionMessages()">
		<p>
			<s:actionmessage />
		</p>
	</s:elseif>
	</span>
	<s:form action="saveInputFile" method="post">
	<s:token></s:token>
	<s:div id="widecontentarea">
		<div class="pageTitle" id="HL1">DPB Input File</div>
		<s:hidden name="fileDefForm.flagActive" id="flagActive"></s:hidden>
		<s:hidden name="fileDefForm.status" id="stsChange"></s:hidden>
		<s:div class="T8">
			<table cellspacing="0" class="template8TableTop" border="0">
				<tr>
					<td colspan="2" class="line"><img src="<%=request.getContextPath()%>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
				</tr>
				<tr>
					<td id="ctl00_tdCopyText" class="left"><div class="TX1">The inbound files processed as part of DPB is defined here.</div></td>
					<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg" style="border-width: 0px;" /><br></td>
				</tr>
				<tr>
					<td colspan="2" class="line"><img src="<%=request.getContextPath()%>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
				</tr>
			</table>
		</s:div>
		<table width="200" border="0" class="TBL2">
				<tr>
					<td width="30%"><s:label for="fileDefForm.fileDefId" value="File ID"></s:label> </td>
					<td width="70%"><s:textfield name="fileDefForm.fileDefId" id="defId" readonly="true" /></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.fileDefName" value="File Definition Name"></s:label></td>
					<td><s:textfield name="fileDefForm.fileDefName" id="defName" /><span class="span" id="span1"></span></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.nameFormat" value="File Name (Format)"></s:label></td>
					<td><s:textfield name="fileDefForm.fileNameFormat" id="formatName" /><span class="span" id="span6"></span></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.description" value="Description"></s:label> </td>
					<td><s:textarea name="fileDefForm.description" cols="45" rows="5" id="desc" /><span class="span" id="span2"></span></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.inTime" value="In Time"></s:label> </td>
					<td> <s:select name="fileDefForm.inTime" id="inTime"
							list="#{'00:00':'00:00','00:30':'00:30','01:00':'01:00','01:30':'01:30','02:00':'02:00','02:30':'02:30','03:00':'03:00','03:30':'03:30',
                                                '04:00':'04:00','04:30':'04:30','05:00':'05:00','05:30':'05:30','06:00':'06:00','06:30':'06:30','07:00':'07:00','07:30':'07:30','08:00':'08:00','08:30':'08:30','09:00':'09:00','09:30':'09:30','10:00':'10:00',
                                                '10:30':'10:30','11:00':'11:00','11:30':'11:30','12:00':'12:00','12:30':'12:30','13:00':'13:00','13:30':'13:30','14:00':'14:00',
                                                '14:30':'14:30','15:00':'15:00','15:30':'15:30','16:00':'16:00','16:30':'16:30','17:00':'17:00','17:30':'17:30','18:00':'18:00',
                                                '18:30':'18:30','19:00':'19:00','19:30':'19:30','20:00':'20:00','20:30':'20:30','21:00':'21:00','21:30':'21:30','22:00':'22:00','22:30':'22:30','23:00':'23:00',
                                                '23:30':'23:30'
                                                }" /></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.readTime" value="Process Time"></s:label> </td>
					<td><s:select name="fileDefForm.readTime" id="readTime"
							list="#{'00:00':'00:00','00:30':'00:30','01:00':'01:00','01:30':'01:30','02:00':'02:00','02:30':'02:30','03:00':'03:00','03:30':'03:30',
                                                '04:00':'04:00','04:30':'04:30','05:00':'05:00','05:30':'05:30','06:00':'06:00','06:30':'06:30','07:00':'07:00','07:30':'07:30','08:00':'08:00','08:30':'08:30','09:00':'09:00','09:30':'09:30','10:00':'10:00',
                                                '10:30':'10:30','11:00':'11:00','11:30':'11:30','12:00':'12:00','12:30':'12:30','13:00':'13:00','13:30':'13:30','14:00':'14:00',
                                                '14:30':'14:30','15:00':'15:00','15:30':'15:30','16:00':'16:00','16:30':'16:30','17:00':'17:00','17:30':'17:30','18:00':'18:00',
                                                '18:30':'18:30','19:00':'19:00','19:30':'19:30','20:00':'20:00','20:30':'20:30','21:00':'21:00','21:30':'21:30','22:00':'22:00','22:30':'22:30','23:00':'23:00',
                                                '23:30':'23:30'
                                                }" /><span class="span" id="span8"></span></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.indCondition" value="Is Condition Applicable"></s:label></td>
					<td><s:radio name="fileDefForm.indCondition" list="#{'Y':'Yes','N':'No'}" id="indCond" /></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.fileFormatId" value="File Format"></s:label></td>
					<td><s:select name="fileDefForm.fileFormatId" list="%{fileDefForm.fileFormat}" listKey="fileFormatId" listValue="formatName" id="formatId" headerKey="0" headerValue=""/><span class="span" id="span3"></span></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.baseFolder" value="File Base Directory"></s:label></td>
					<td><s:textfield name="fileDefForm.baseFolder" id="directory" /><span class="span" id="span4"></span></td>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.scheduleCode" value="File Process Schedule"></s:label></td>
					<td><s:select name="fileDefForm.scheduleCode" list ="defSchd"  listKey="scheduleCode"  listValue="scheduleName" id="schedule"/><span class="span" id="span7"></span></td>
					<%-- <td><s:label for="fileDefForm.processSchdule" value="File Process Schedule"></s:label></td>
					<td><s:select name="fileDefForm.processSchdule" list="#{'':'Select One','D':'Daily','W':'Weekly','B':'BiWeekly','M':'Monthly','Q':'Quarterly','Y':'Yearly'}" id="schedule" /><span id="span7"></span></td> --%>
				</tr>
			<tr>
					<td><s:label for="fileDefForm.triggerCode" value="File Processing Trigger"></s:label></td>
					<td><s:radio name="fileDefForm.triggerCode" list ="defTgr"  listKey="triggerCode"  listValue="triggerName" /></td>
					<%-- <td><s:label for="fileDefForm.trigger" value="File Processing Trigger"></s:label></td>
					<td><s:radio name="fileDefForm.trigger" list="#{'A':'Auto Trigger','M':'Manual'}" /></td> --%>
				</tr>
			<tr>
				<td><s:label for="fileDefForm.defStatusCode" value="Status"></s:label></td>
				<td><s:radio name="fileDefForm.defStatusCode" list ="defSts"  listKey="defStatusCode"  listValue="defStatusName" id="status"/></td>
				<%-- <td><s:label for="fileDefForm.fileStatus" value="Status"></s:label></td>
				<td><s:radio name="fileDefForm.fileStatus" list="#{'D':'Draft','A':'Active','I':'In-Active'}" id="status"/></td> --%>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table>
		<tr>
			<td id="submit"><s:submit key="Submit" method="saveFileDefinition" align="right" theme="simple" id="submitDef" /></td>
			<td id="reset"><s:reset key="Reset" name="reset" onclick="formReset()" align="center" theme="simple" /></td>
			<s:if test="%{fromDPBList == true}">
				<td><s:submit key="Cancel" theme="simple" method="getFileListDetails" action="inputFileList"/></td>
		     	<s:hidden name="fromDPBList" value="true" id="fromDPBList"/>
			</s:if>
			<s:else>
				<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
			</s:else>
			<%-- <td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td> --%>
		</tr>
		</table>
		<br />
		<br />
		<br />
	</s:div>
	</s:form>
</body>
</html>