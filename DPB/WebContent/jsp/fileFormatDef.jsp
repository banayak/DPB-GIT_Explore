<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 <s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPB Input File Format</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
		<style>
	span {
		color: red;
	}
	div {
		color: red;
	}
	</style>
<script type="text/javascript">
	$(document).ready(function() {
      	$('input[name=formatForm.defStatusCode][value="D"]').click(function() {
            $(".span").empty();
            /* $("#span2").empty();
            $("#span3").empty();
            $("#span4").empty();
            $("#span5").empty();
            $("#span6").empty();
            $("#span8").empty(); */
            var flagActive = $("#flagActive").val();
            if(flagActive == "true"){
            $("#mapTable").hide();
            $("#submit").show();
            $("#reset").show();
			document.getElementById("fileFormatId").value =0;
			document.getElementById("formatName").value ="";
			document.getElementById("desc").value ="";
			document.getElementById("indDelimited").value ="";
			document.getElementById("fixedLineWidth").value =0;
			document.getElementById("columnCount").value =0;
			document.getElementById("tableName").value =0;
			document.getElementById("flagActive").value=false;
			$("#tableName").attr("disabled",false);
			}
   		});
	});

$(document).ready(function() {
      	$('input[name=formatForm.defStatusCode][value="I"]').click(function() {
            var flagActive = $("#flagActive").val();
            if(flagActive == "true"){
			$("#tableName").attr("disabled",false);
			}
   		});
	});

$(document).ready(function() {
      	$('input[name=formatForm.defStatusCode][value="A"]').click(function() {
            var flagActive = $("#flagActive").val();
            if(flagActive == "true"){
			$("#tableName").attr("disabled",true);
			}
   		});
	});

$(document).ready(function() {
    $('#tableName').change(function() {
	    var Num_Expression = "^([0-9]){1,2}$";
		var num_Expression = "^([0-9]){1,3}$";
	    var colCount =$('#columnCount').val();
	    var tblName =$('#tableName').val();
	    var fixedLineWidth = $("#fixedLineWidth").val();
    
	    if(colCount == 0){
	    	document.getElementById("tableName").value =0;
	    	$("#span4").text(" Enter No of columns.");
	    	return false;
	    }else{
	    	$("#span4").empty();
	    }
	    if (colCount > 0 && tblName == 0){
	    	$("#span5").text(" Select Table Name.");
	    	return false;
	    }if (!colCount.match(Num_Expression)) {
			$("#span4").text(" Please enter valid number.");
			return false;
		}else{
			$("#span4").empty();
		}
		if (!fixedLineWidth.match(num_Expression)) {
			$("#span3").text(" Please enter valid number.");
			return false;
		}
		else{
			$("#span4").empty();
		}
		if(colCount > 0 && tblName != 0 && colCount.match(Num_Expression)){
			var returnFlag = true;
						for (var i=0; i < colCount; i++){
				var answerInput = document.getElementById('fileColumnLength'+i);
				var formatText = document.getElementById('fileColumnformatText'+i);
				var msg = '';
				if (answerInput == null || formatText == null) {
					
					break;
				} else {
					answerInput = answerInput.value;
					formatText = srt_trim(formatText.value);
					document.getElementById('fileColumnformatText'+i).value = formatText;
						$("#sp"+i).empty();
						var temp =true;
						if (!answerInput.match(Num_Expression)) {
							msg = " Enter File Column Length.";
							temp = false;
						}
						if (formatText.length > 40) {
							msg += "\nColumn Format Text should be up to 40 characters.";
						 	temp = false;
						} 
						if (!temp) {
							$("#sp"+i).text(msg);
							returnFlag = false;
						}
					
				}
			}
			if(returnFlag){	
				document.getElementById("fileformatDef").action = 'fileformatDef';
				document.getElementById("fileformatDef").submit();
			}				
		}
    	 
   	});
});

$(document).ready(function() {
	var status = $('input[name="formatForm.defStatusCode"]:checked').val();
	var flagActive = $("#flagActive").val();
	if(status=="I" && flagActive=="true"){
		$("#submit").hide();
		$("#reset").hide();
		$("#tableName").attr("disabled",true);
		/* document.getElementById("stsChange").value ="I"; */
	}
	});
	
$(document).ready(function() {
	var status = $('input[name="formatForm.defStatusCode"]:checked').val();
	var flagActive = $("#flagActive").val();
	if(status=="A" && flagActive=="true"){
		$("#tableName").attr("disabled",true);
		/* document.getElementById("stsChange").value ="A"; */
	}
	});

$(document).ready(function() {
		$("#submitDef").click(function() {
			var returnFlag = true;
			var active = $('input[name=formatForm.defStatusCode]:checked').val();
			var flag = $("#flagActive").val();
					var Name_Expression = "^([A-Za-z][a-zA-Z_ 0-9-]{0,40})$";
					var Num_Expression = "^([0-9]){1,2}$";
					var num_Expression = "^([0-9]){1,3}$";
					
					var indDe = $("#indDelimited").val();
					var formatName = srt_trim($("#formatName").val());
						$("#formatName").val(formatName);
					var desc = srt_trim($("#desc").val());
						$("#desc").val(desc);
					var fixedLineWidth = $("#fixedLineWidth").val();
					var colCount = $("#columnCount").val();
					var tblName = $("#tableName").val();
					var fmtId = $("#fileFormatId").val();
					
				if (active == 'A' && flag == "false" || active == 'A' && fmtId == 0) {
					$("#span8").empty();

					if (formatName == '') {
						$("#span1").text("Enter Format name.");
						returnFlag = false;
					}else if (!formatName.match(Name_Expression)) {
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
					if(indDe.length > 1){
						$("#span6").text(" Please only one charecter.");
						returnFlag = false;
					}
					else{
					$("#span6").empty();
					}
					if (fixedLineWidth == '' || fixedLineWidth == 0) {
						$("#span3").text(" Enter Line width.");
						returnFlag = false;
					} else if (!fixedLineWidth.match(num_Expression)) {
						$("#span3").text(" Please enter valid number.");
						returnFlag = false;
					}else{
					$("#span3").empty();
					}
					
					if (colCount <= 0) {
						$("#span4").text(" Enter No of columns.");
						returnFlag = false;
					} else if (!colCount.match(Num_Expression)) {
						$("#span4").text(" Please enter valid number.");
						returnFlag = false;
					}else{
					$("#span4").empty();
					}
					
					if (tblName == 0) {
						$("#span5").text(" Select Table Name.");
						returnFlag = false;
					}else{
					$("#span5").empty();
					}
					if(colCount > 0 && tblName != 0 && colCount.match(Num_Expression) && fixedLineWidth.match(num_Expression)){
						for (var i=0; i < colCount; i++){
							var answerInput = document.getElementById('fileColumnLength'+i);
							var formatText = document.getElementById('fileColumnformatText'+i);
							var msg = '';
							if (answerInput == null || formatText == null) {
								
								break;
							} else {
								answerInput = answerInput.value;
								formatText = srt_trim(formatText.value);
								document.getElementById('fileColumnformatText'+i).value = formatText;
									$("#sp"+i).empty();
									var temp =true;
									if (answerInput <= 0 || !answerInput.match(Num_Expression)) {
										msg = " Enter File Column Length.";
										temp = false;
									}
									if (formatText.length > 40) {
										msg += "\nColumn Format Text should be up to 40 characters.";
									 	temp = false;
									} 
									if (!temp) {
										$("#sp"+i).text(msg);
										returnFlag = false;
									}
								
								}
							}
							
					}
					return returnFlag;
				}
				else if (active == 'D') {
					$(".span").empty();					
					/* $("#span1").empty();
					$("#span2").empty();
					$("#span3").empty();
					$("#span4").empty();
					$("#span5").empty();
					$("#span6").empty();
					$("#span7").empty();
					$("#span8").empty(); */
					
					if (formatName == '') {
							$("#span1").text(" Enter Definition Name.");
						returnFlag = false;
					} else if (!formatName.match(Name_Expression)) {
						$("#span1").text(" Name should contain up to 40 characters. May contain alphanumeric, spaces and underscores.");
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
					if(!indDe == '' && indDe.length != 1){
						$("#span6").text(" Please enter only one character.");
						returnFlag = false;
					}
					else{
					$("#span6").empty();
					}
					if(colCount == ""){
						$("#columnCount").val(0);
					}
					else
					if (!colCount.match(Num_Expression)) {
						$("#span4").text(" Please enter valid number.");
						returnFlag = false;
					}else{
					$("#span4").empty();
					}
					if(fixedLineWidth == ""){
						$("#fixedLineWidth").val(0);
					}
					else if (!fixedLineWidth.match(num_Expression)) {
						$("#span3").text(" Please enter valid number.");
						returnFlag = false;
					}else{
					$("#span3").empty();
					}
					if(colCount > 0 && tblName != 0 && colCount.match(Num_Expression) && fixedLineWidth.match(num_Expression)){
						for (var i=0; i < colCount; i++){
							var answerInput = document.getElementById('fileColumnLength'+i);
							var formatText = document.getElementById('fileColumnformatText'+i);
							var msg = '';
							if (answerInput == null || formatText == null) {
								
								break;
							} else {
								answerInput = answerInput.value;
								formatText = srt_trim(formatText.value);
								document.getElementById('fileColumnformatText'+i).value = formatText;
									$("#sp"+i).empty();
									var temp =true;
									if (!answerInput.match(Num_Expression)) {
										msg = " Enter File Column Length.";
										temp = false;
									}
									if (formatText.length > 40) {
										msg += "\nColumn Format Text should be up to 40 characters.";
									 	temp = false;
									} 
									if (!temp) {
										$("#sp"+i).text(msg);
										returnFlag = false;
									}
								
								}
							}
					}
					return returnFlag;
				}
				else if(active == 'I'){
					
					$(".span").empty();
					/* $("#span2").empty();
					$("#span3").empty();
					$("#span4").empty();
					$("#span5").empty();
					$("#span6").empty();
					$("#span7").empty(); */
					
					if(fmtId <= 0){
					$("#span8").text(" Cannot create a new Inactive File Format.");
					return false;
					}
				}
				else if (active == 'A' && flag == "true" && fmtId > 0){
					$("#span8").text(" Cannot re-submit an Active File Format.");
					return false;
				}
			});
});

$(document).ready(function() {
	$('input[name=formatForm.defStatusCode]:checked').change(function() {
		$(".span").empty();
		/* $("#span2").empty();
		$("#span3").empty();
		$("#span4").empty();
		$("#span5").empty();
		$("#span6").empty();
		$("#span7").empty();
		$("#span8").empty(); */
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
				$("#tableName").attr("disabled",true);
			}
			else if(stsChange=="A"){
				$("#mapTable").show();
				$("#tableName").attr("disabled",true);
			}
		}
</script>
</head>
<body>
<span class="span" id="span8">
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
<s:form action="saveFileFormatDef" method="post" id="fileformatDef">	
<s:token></s:token>
<s:hidden name="count" value="" id="ccount"></s:hidden>
<s:hidden name="formatForm.flagActive" id="flagActive"></s:hidden>
<s:hidden name="formatForm.status" id="stsChange"></s:hidden>
<s:div id="widecontentarea">
<div class="pageTitle" id="HL1">DPB Input File Format</div>
 
	<s:div class="T8">
		<table cellspacing="0" class="template8TableTop" border="0">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td id="ctl00_tdCopyText" class="left"><div class="TX1">The File Format for DPB is defined here.</div></td>
 
				<td class="right"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br></td>
			</tr><tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
		</table>
	</s:div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	   <td width="30%"><s:label for="formatForm.fileFormatId" value="File Format ID"></s:label></td>
	    <td width="70%"><s:textfield name="formatForm.fileFormatId" id="fileFormatId" readonly="true"/></td>
      </tr>
	  <tr>
	    <td><s:label for="formatForm.formatName" value="Format Name"></s:label></td>
	    <td><s:textfield name="formatForm.formatName" id="formatName"/><span class="span" id="span1"></span></td>
      </tr>
	  <tr>
	    <td><s:label for="formatForm.formatDescription" value="Description"></s:label></td>
	    <td><s:textarea name="formatForm.formatDescription" id="desc" cols="45" rows="5"/><span class="span" id="span2"></span></td>
      </tr>
	  <tr>
	  <td><s:label for="formatForm.inbountFileIndicator" value="Is In File?"></s:label></td>
	   <td><s:radio name="formatForm.inbountFileIndicator" list="#{'Y':'Yes','N':'No'}" id="inboundInd"/></td>
      </tr>
		<tr>
	  <td><s:label for="formatForm.indHeader" value="Is Header Present in file?"></s:label></td>
	  <td><s:radio name="formatForm.indHeader" list="#{'Y':'Yes','N':'No'}" id="indHeader"/></td>
      </tr>
	 <tr>
	   <td><s:label for="formatForm.indDelimited" value="De-limiter character"></s:label></td>
	    <td><s:textfield name="formatForm.indDelimited" id="indDelimited"/><span class="span" id="span6"></span></td>
      </tr>
	 <tr>
	    <td><s:label for="formatForm.fixedLineWidth" value="Line width (characters)"></s:label></td>
	    <td><s:textfield name="formatForm.fixedLineWidth" id="fixedLineWidth"/><span class="span" id="span3"></span></td>
	    </tr>
	  <tr>
	    <td><s:label for="formatForm.columnCount" value="No of columns"></s:label></td>
	    <td><s:textfield name="formatForm.columnCount" id="columnCount" /><span class="span" id="span4"></span></td>
      </tr>
	  <tr>
	    <td><s:label for="formatForm.tableName" value="Table Name"></s:label></td>
	     <td><s:select name="formatForm.tableName" headerKey="0" headerValue="Select Table" list="#{'DPB_UNBLK_VEH':'Vista File ','DPB_KPI_FIL_EXTRT':'KPI File ','LDRSP_BNS_ACRL':'Leadership Bonus'}" id="tableName"/><span class="span" id="span5"></span></td>
      </tr>
		<tr id="mapTable">
	    <td colspan="2">
          <BODY TEXT="#000000">
          <TABLE BORDER="0" align="center" CELLSPACING="0" COLS="6" FRAME="VOID" RULES="NONE">
            <COLGROUP>
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
              <COL WIDTH="86">
            </COLGROUP>
              <TBODY> 
              <s:if test="%{formatForm.kpList == null || formatForm.kpList.isEmpty()}">
              <TR>
                <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">Column No</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Format Text</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Length</TD>
                <TD WIDTH="86" ALIGN="LEFT">Column Name</TD>
              </TR>
              <s:iterator value="formatForm.fileMapingList" status="mapForm" id="formatForm">
              <TR >
              <s:hidden name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnMapId"/>
                <TD HEIGHT="17" ALIGN="LEFT"><s:textfield name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnSeqNumber" label="Column No" id="fileColumnSeqNumber" readonly="true"/></TD>
                <TD ALIGN="LEFT"><s:textfield name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnformatText" label="File Column Format Text" id="fileColumnformatText%{#mapForm.index}"/></TD>
                <TD ALIGN="LEFT"><s:textfield name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnLength" label="File Column Length" id="fileColumnLength%{#mapForm.index}"/></TD>
                <TD ALIGN="LEFT"><s:select name="formatForm.fileMapingList[%{#mapForm.index}].columnName" list="%{formatForm.columnNames}"/></TD><td><s:div id="sp%{#mapForm.index}"></s:div></td>
              </TR>
              </s:iterator> 
              </s:if>
              <s:else>
               <TR>
                <TD WIDTH="86" HEIGHT="17" ALIGN="LEFT">Column No</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Format Text</TD>
                <TD WIDTH="86" ALIGN="LEFT">File Column Length</TD>
                <TD WIDTH="86" ALIGN="LEFT">Column Name</TD>
                 <TD WIDTH="86" ALIGN="LEFT">KPI Name</TD>
              </TR>
              <s:iterator value="formatForm.fileMapingList" status="mapForm">
              <TR >
              <s:hidden name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnMapId"/>
                <TD HEIGHT="17" ALIGN="LEFT"><s:textfield name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnSeqNumber" label="Column No" id="fileColumnSeqNumber" readonly="true"/></TD>
                <TD ALIGN="LEFT"><s:textfield name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnformatText" label="File Column Format Text" id="fileColumnformatText%{#mapForm.index}"/></TD>
                <TD ALIGN="LEFT"><s:textfield name="formatForm.fileMapingList[%{#mapForm.index}].fileColumnLength" label="File Column Length" id="fileColumnLength%{#mapForm.index}"/></TD>
                <TD ALIGN="LEFT"><s:select name="formatForm.fileMapingList[%{#mapForm.index}].columnName" list="%{formatForm.columnNames}"/></TD>
                <TD ALIGN="LEFT"><s:select name="formatForm.fileMapingList[%{#mapForm.index}].kpi.kpiId" list="%{formatForm.kpList}" headerKey="0" headerValue=" " listKey="kpiId" listValue="kpiName"/></TD><td><s:div id="sp%{#mapForm.index}"></s:div></td>              
              </TR>
              </s:iterator> 
              </s:else>
            </TBODY>
        </TABLE></td>
      </tr>
	  <tr>
	      <td><s:label for="formatForm.defStatusCode" value="Status"></s:label></td>
	    <td><s:radio name="formatForm.defStatusCode" list ="defSts"  listKey="defStatusCode"  listValue="defStatusName" id="status"/>
	    <%-- <td><s:label for="formatForm.fileFormatStatus" value="Status"></s:label></td>
	    <td><s:radio name="formatForm.fileFormatStatus" list="#{'D':'Draft','A':'Active','I':'In-Active'}" id="status"/></td> --%>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
    <table>
		<tr>
			<td id="submit"><s:submit key="Submit" method="saveFileFormatDetails" align="right" theme="simple" id="submitDef" /></td>
			<td id="reset"><s:reset key="Reset" name="reset" onclick="formReset()" align="center" theme="simple" /></td>
			<s:if test="%{fromDPBList == true}">
				<td><s:submit key="Cancel" theme="simple" method="getFileFormatListDetails" action="fileformatList"/></td>
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