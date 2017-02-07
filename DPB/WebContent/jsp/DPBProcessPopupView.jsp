<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function closeWindow(){
		//window.opener.refreshPage();
		window.close();
	}
	
	/* window.onunload = refreshParent;
        function refreshParent() {
            window.opener.location.reload();
            window.close();
        } */

/* if (window.opener && !window.opener.closed  ) {
      window.opener.location.reload();
 } */
 		//window.onunload = refreshParent;
		/*function refreshParent(){
			alert(1);
		if(document.getElementById("shouldRefreshParentId").value == 'Y'){
			window.opener.refreshPage();
			//window.opener.location.reload();
			//window.close();
		}
	}*/
	
	function addCancel(){
	if(document.getElementById("viewProcesspopup_submit")){
		var submitCol = document.getElementById("viewProcesspopup_submit").parentNode;
		var closeBut = document.getElementById("CloseId");
		var closeCol = closeBut.parentNode;
		var closeRow = closeCol.parentNode;
		var closeTable = closeRow.parentNode;
		closeCol.removeChild(closeBut);
		closeRow.removeChild(closeCol);
		closeTable.removeChild(closeRow);
		submitCol.appendChild(closeBut);
	}
	}
	$(document).ready(function() {
	
	if($("#shouldRefreshParentId").val() == 'Y'){
		window.opener.refreshPage();
	}
	
	$("#submit").click(function() {
	var dTyp = $("#defT").val();
	var defId = $("#idDef").val();
	var startDate = $("#processDate").val();
	//alert(startDate);
	var fDate=startDate.replace(/-/g,"/");
	var fromDate = new Date(fDate);
	var currentdate=new Date();
	var reProcessFlag =$("#rFlag").val();
	// Reprocess Validations
	
	if(reProcessFlag == 'true'){
	if($.trim($('#processDate').val())!=''){
		$("#span1").text('');
		if(!verifyDate(startDate)){
		 $("#span1").text(" Please enter valid Process Date.").show();
		 return false;
		}
	}else{
			$("#span1").text('');
			$("#span1").text(" Enter  Process Date.");
			return false;
	}
	
	if(!confirmSubmit(defId,dTyp))
	    {
	      return false;
	    }
	
	}
	
		
		if(reProcessFlag == 'false'){
		if($('input:radio[name="fileProcessDefForm.processingTrigger"]')[0].checked)
		{
			$("#span1").text("Processing Trigger should be User Initiation.");
			return false;
		} 
		if($.trim($('#processDate').val())!='')
		{
			$("#span1").text('');
			if(fromDate  <= currentdate)
			{
				$("#span1").text("Process date should be Greater than Current Date.");
				return false;
			}
			else if(!verifyDate(startDate))
			 {
			 	$("#span1").text(" Please enter valid Process Date.").show();
			 	return false;
			 }
		}
		else
		{
			$("#span1").text('');
			$("#span1").text(" Enter  Process Date.");
			return false;
		}
		 
		 if($("#reasonForUpdate").val() != '')
					{
						if($("#reasonForUpdate").val().length > 235)
						{
							$("#span1").text("Reason Length should be less than 235 charecter's.").show();
						 	return false;
						}
					}
					
					
		}
		//submitChildWindow();
	});

	
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

function confirmSubmit(defId, defT)
{
var r;
	if(defT=='PF'){
		 r=confirm("You are re-processing process id "+ " "+ defId + " " +"file processing, "+
			" which will impact all the bonus calculations for this day");
		
	}else{
		 r=confirm("You are re-processing process id "+ " "+ defId + " " +"bonus processing, "+
		" which will impact all respective bonus calculations for this process");		
	}
		if (r==true)
		  {
		  return true;
		  }
		else
		  {
		  return false;
	
		}	  
}
});	
	
</script>
<script type="text/javascript">

</script>
<s:if test="hasActionErrors()">   
    <div id="errsDiv">   
        <s:iterator value="actionErrors">   
            <span class="errors"><s:property escapeHtml="false"/></span>   
        </s:iterator>   
    </div>   
	</s:if>  
	<s:if test="hasActionMessages()">   
    <div id="msgsDiv">   
        <s:iterator value="actionMessages">   
            <span class="messages"><s:property escapeHtml="false"/></span>   
        </s:iterator>   
    </div>   
	</s:if>  
<style>
.serror {
	color: red;
	padding-left: 5px;
}

span {
	color: red;
}

</style>
<s:set name="theme" value="'xhtml'" scope="page" />
<sj:head jquerytheme="flick"/>
<body onLoad="javascript:addCancel()">
<s:form>
<s:hidden name="defType" value="%{defType}" id ="defT" />
<s:hidden name="fileProcessDefForm.recCount"/>
<s:hidden id="shouldRefreshParentId" name="shouldRefreshParent"/>
<%-- <s:if test="%{fileProcessDefForm.flag != true && fileProcessDefForm.reProcessFlag == false}">
<div class="pageTitle"><span class="Header1">Re-Schedule Process</span></div>
<hr />
</s:if> --%>
<s:if test="%{fileProcessDefForm.flag == true && fileProcessDefForm.reProcessFlag == false}">
<div class="pageTitle"><span class="Header1">Process Details</span></div>
<s:hidden name="fileProcessDefForm.processDefinitionId"/>		
<s:label label="Process Definition Id" name="fileProcessDefForm.processDefinitionId" value="%{fileProcessDefForm.processDefinitionId}"/>
<s:hidden name="fileProcessDefForm.definitionId"/>
<s:hidden name="fileProcessDefForm.reProcessFlag" value="%{fileProcessDefForm.reProcessFlag}" id="rFlag"  />
<s:hidden name="fileProcessDefForm.progId"/>
<s:label label="Definition Id" name="fileProcessDefForm.progId" value="%{fileProcessDefForm.progId}"/>
<s:hidden name="fileProcessDefForm.definitionType"/>
<s:label label="Definition Type" name="fileProcessDefForm.definitionType" value="%{fileProcessDefForm.definitionType}" id="definitionType"/>
<s:label label="Process Date" name="fileProcessDefForm.processDate" value="%{fileProcessDefForm.processDate}" id="processDate"/>
<s:label label="Reason for Update" name="fileProcessDefForm.reasonForUpdate" value="%{fileProcessDefForm.reasonForUpdate}" id="reasonForUpdate" />
<s:label label="Processing Trigger" name="fileProcessDefForm.processingTrigger" value="%{fileProcessDefForm.processingTrigger}" id="showTrigger" />
<s:if test="%{defType == 'PF'}">
<s:label  label="Processing Time" name="fileProcessDefForm.processingTime" value="%{fileProcessDefForm.processingTime}" id="processingTime" />
</s:if>
<s:label label="Status" name="fileProcessDefForm.showStat"  value="%{fileProcessDefForm.showStat}" id="showStat"/>
<table>
<tr>
<td>
<input type="button" id="CloseId" align="right" value="Close"  onClick="javascript:closeWindow()"/>
</td>
</tr>
</table>
</s:if>
<s:if test="%{fileProcessDefForm.flag == false && fileProcessDefForm.reProcessFlag == false}">
<div class="pageTitle"><span class="Header1">Re-Schedule Process</span></div>
<hr />
<s:hidden name="fileProcessDefForm.processDefinitionId"/>	
<span id="span1" class="serror"></span>	
<s:label label="Process Definition Id" name="fileProcessDefForm.processDefinitionId" value="%{fileProcessDefForm.processDefinitionId}"/>
<s:hidden name="fileProcessDefForm.definitionId"/>
<s:hidden name="fileProcessDefForm.processType" value="R" />
<s:hidden name="fileProcessDefForm.reProcessFlag" value="%{fileProcessDefForm.reProcessFlag}" id="rFlag" />
<s:hidden name="fileProcessDefForm.progId"/>
<s:label label="Definition Id" name="fileProcessDefForm.progId" value="%{fileProcessDefForm.progId}"/>
<s:hidden name="fileProcessDefForm.definitionType"/>
<s:label label="Definition Type" name="fileProcessDefForm.definitionType" value="%{fileProcessDefForm.definitionType}"/>
<sj:datepicker label="Process Date" name="fileProcessDefForm.processDate" id="processDate" minDate="tomorrow" displayFormat="mm/dd/yy" showOn="focus"/>
<s:textarea label="Reason for Update" name="fileProcessDefForm.reasonForUpdate" id="reasonForUpdate"/>
<s:radio label="Processing Trigger" name="fileProcessDefForm.processingTrigger" list="#{'S':'System Initiation','U':'User Initiation'}" id="processingTrigger"/>
<s:if test="%{defType == 'PF'}">
<s:select label="Processing Time" name="fileProcessDefForm.processingTime" id="processingTime"
							list="#{'00:00':'00:00','00:30':'00:30','01:00':'01:00','01:30':'01:30','02:00':'02:00','02:30':'02:30','03:00':'03:00','03:30':'03:30',
                                                '04:00':'04:00','04:30':'04:30','05:00':'05:00','05:30':'05:30','06:00':'06:00','06:30':'06:30','07:00':'07:00','07:30':'07:30','08:00':'08:00','08:30':'08:30','09:00':'09:00','09:30':'09:30','10:00':'10:00',
                                                '10:30':'10:30','11:00':'11:00','11:30':'11:30','12:00':'12:00','12:30':'12:30','13:00':'13:00','13:30':'13:30','14:00':'14:00',
                                                '14:30':'14:30','15:00':'15:00','15:30':'15:30','16:00':'16:00','16:30':'16:30','17:00':'17:00','17:30':'17:30','18:00':'18:00',
                                                '18:30':'18:30','19:00':'19:00','19:30':'19:30','20:00':'20:00','20:30':'20:30','21:00':'21:00','21:30':'21:30','22:00':'22:00','22:30':'22:30','23:00':'23:00',
                                                '23:30':'23:30'
                                                }" />
</s:if>

<table>
<tr>
<s:if test='shouldRefreshParent.equals("N")'>
<td><s:submit theme="simple" key="Submit" method="saveProcess"  id="submit" align="left" />
</td>
</s:if>
<td>
<input type="button" id="CloseId" align="right" value="Close"  onClick="javascript:closeWindow()"/>
</td>
</tr>
</table>
</s:if>
<s:if test="%{fileProcessDefForm.reProcessFlag == true}">
<div class="pageTitle"><span class="Header1">Re-Process Details</span></div>

<s:hidden name="fileProcessDefForm.processDefinitionId"/>
<span id="span1" class="serror"></span>	
<s:label label="Process Definition Id" name="fileProcessDefForm.processDefinitionId" value="%{fileProcessDefForm.processDefinitionId}"/>
<s:hidden name="fileProcessDefForm.definitionId" value="%{fileProcessDefForm.processDefinitionId}"  id="idDef"/>
<s:hidden name="fileProcessDefForm.progId"/>
<s:label label="Definition Id" name="fileProcessDefForm.progId" value="%{fileProcessDefForm.progId}"/>

<s:hidden name="fileProcessDefForm.definitionType"/>
<s:hidden name="fileProcessDefForm.reProcessFlag" value="%{fileProcessDefForm.reProcessFlag}"  id="rFlag" />
<s:hidden name="fileProcessDefForm.processType" value="P" />
<s:label label="Definition Type" name="fileProcessDefForm.definitionType" value="%{fileProcessDefForm.definitionType}"/>
<sj:datepicker label="Process Date" name="fileProcessDefForm.processDate" minDate="today" maxDate="today" displayFormat="mm/dd/yy" showOn="focus" id="processDate"/>
<s:textarea label="Reason for Update" name="fileProcessDefForm.reasonForUpdate" id="reasonForUpdate"/>
<s:radio label="Processing Trigger" name="fileProcessDefForm.processingTrigger" list="#{'S':'System Initiation','U':'User Initiation'}" id="processingTrigger"/>

<s:if test="%{defType == 'PF'}">
<s:select label="Processing Time" name="fileProcessDefForm.processingTime" id="processingTime"
							list="#{'00:00':'00:00','00:30':'00:30','01:00':'01:00','01:30':'01:30','02:00':'02:00','02:30':'02:30','03:00':'03:00','03:30':'03:30',
                                                '04:00':'04:00','04:30':'04:30','05:00':'05:00','05:30':'05:30','06:00':'06:00','06:30':'06:30','07:00':'07:00','07:30':'07:30','08:00':'08:00','08:30':'08:30','09:00':'09:00','09:30':'09:30','10:00':'10:00',
                                                '10:30':'10:30','11:00':'11:00','11:30':'11:30','12:00':'12:00','12:30':'12:30','13:00':'13:00','13:30':'13:30','14:00':'14:00',
                                                '14:30':'14:30','15:00':'15:00','15:30':'15:30','16:00':'16:00','16:30':'16:30','17:00':'17:00','17:30':'17:30','18:00':'18:00',
                                                '18:30':'18:30','19:00':'19:00','19:30':'19:30','20:00':'20:00','20:30':'20:30','21:00':'21:00','21:30':'21:30','22:00':'22:00','22:30':'22:30','23:00':'23:00',
                                                '23:30':'23:30'
                                                }"/>
</s:if>

<table>
<tr>
<s:if test='shouldRefreshParent.equals("N")'>
<td><s:submit theme="simple" key="Submit" method="saveProcess"  id="submit" align="left" />
</td>
</s:if>
<td>
<input type="button" id="CloseId" align="right" value="Close"  onClick="javascript:closeWindow()"/>
</td>
</tr>
</table>
</s:if>
</s:form>


</body>