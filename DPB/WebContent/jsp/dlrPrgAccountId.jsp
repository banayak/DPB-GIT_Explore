<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>DPBProgramDealerAccountMapping</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" 
	href="<%=request.getContextPath()%>/css/master.css" />
<link href="<%=request.getContextPath()%>/css/master.css"
	type="text/css" rel="stylesheet" />
<link href="<%=request.getContextPath()%>/css/admin.css"
	type="text/css" rel="stylesheet" />

<script
	src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js"
	type="text/javascript"></script>
<style type="text/css">
span {
	color: red;
}
</style>
<script>
	function fileLink(val){	
				document.getElementById(val).removeAttribute("readonly",0);	
				var acc2 = val;
				var accDT = acc2.replace("acc","Ind"); 
				document.getElementById(accDT).removeAttribute("readonly",0);	
			}
	function onAccountidEdit(accidval) {
			var accval = document.getElementById(accidval).value;
			var n = accval.indexOf(" "); 
			//var regex_acc= "^\S+$"; 
			if(n >= 0 ) {
				alert("Please enter valid account id");	
				return ;	
			} 
			if(accval.length > 18) {
				alert("Please enter valid account id");	
				return ;	
			}
			}
</script>

<script>
$(document).ready(function() {
$("#submitabc").click(function() {
 var pcListSize = 0;
 var smartListSize = 0;
 var vanListSize = 0;
 var ftlListSize = 0; 
pcListSize = document.getElementById('pcAccidListSize').value;
smartListSize = document.getElementById('smAccidListSize').value;
vanListSize = document.getElementById('vanAccidListSize').value;
ftlListSize = document.getElementById('ftlAccidListSize').value;
var msg ="";
var flag = true;
for (var i=0; i<pcListSize;i++) {
	var accval = document.getElementById('pcaccList'+i);
	var accvalDT = document.getElementById('pcIndList'+i);
	//var regex_acc= "^\S+$";    
	if (accval == null ) {
		break;
	} 
	else if(accvalDT == null) {
		break;
	}
	else {
	accval= accval.value;
	accvalDT = accvalDT.value;
		var temp = true;
		var n = accval.indexOf(" "); 
		var ndt = accvalDT.indexOf(" "); 
		if (n >= 0) {
		msg = "Please enter valid account id";
		temp = false;
		}
		else if (accval.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}
		  
		else if (ndt >= 0) {
		msg = "Please enter valid account id";
		temp = false;
		}  
		else if (accvalDT.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}
		if (!temp) {
		$("#sp").text(msg);
		flag = false;
		} 
	//msg+= 
}
}
for (var i=0; i<smartListSize;i++) {
	var accValSmart = document.getElementById('smartaccList'+i);
	var accValSmartDT = document.getElementById('smartIndList'+i);
	//var regex_acc= "^\S+$";    
	 if(accValSmart == null) {
		break;
	} 
	else if(accValSmartDT == null) {
		break;
	}
	else {
	accValSmart = accValSmart.value;
	accValSmartDT = accValSmartDT.value;
	var sm = accValSmart.indexOf(" "); 
	var smdt = accValSmartDT.indexOf(" "); 
	var temp = true;
	 if (sm >= 0) {
		msg = "Please enter valid account id";
		temp = false;
		}
		else if (accValSmart.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}
		else if ((smdt >= 0)) {
		msg = "Please enter valid account id";
		temp = false;
		}  
		else if (accValSmartDT.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}
		if (!temp) {
		$("#sp").text(msg);
		flag = false;
		} 
}
}
for (var i=0; i<vanListSize;i++) {
	var accValVan = document.getElementById('vanaccList'+i);
	var accValVanDT = document.getElementById('vanIndList'+i);
	//var regex_acc= "^\S+$";    
	 if(accValVan == null) {
		break;
	} 
	else if(accValVanDT == null) {
		break;
	}else {
	accValVan = accValVan.value;
	accValVanDT = accValVanDT.value;
	var vn = accValVan.indexOf(" "); 
	var vndt = accValVanDT.indexOf(" "); 
		var temp = true;
	 if (vn >= 0) {
		msg = "Please enter valid account id";
		temp = false;
		}
		else if (accValVan.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}
		else if (vndt >= 0) {
		msg = "Please enter valid account id";
		temp = false;
		}  
		else if (accValVanDT.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}
		if (!temp) {
		$("#sp").text(msg);
		flag = false;
		} 
	//msg+= 
}
}
for (var i=0; i<ftlListSize;i++) {
	var accValFTL = document.getElementById('ftlaccList'+i);
	var accValFTLDT = document.getElementById('ftlIndList'+i);
	//var regex_acc= "^\S+$";    
	 if(accValFTL == null) {
		break;
	} 
	else if(accValFTLDT == null) {
		break;
	}else {
	accValFTL = accValFTL.value;
	accValFTLDT = accValFTLDT.value;
	var ftln = accValFTL.indexOf(" "); 
	var ftldt = accValFTLDT.indexOf(" "); 
	var temp = true;
	 if (ftln >= 0) {
		msg = "Please enter valid account id";
		temp = false;
		}  
		else if (accValFTL.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}  
		else if (ftldt >= 0) {
		msg = "Please enter valid account id";
		temp = false;
		}  
		else if (accValFTLDT.length > 18) {
		msg = "Please enter valid account id";
		temp = false;
		}
		if (!temp) {
		$("#sp").text(msg);
		flag = false;
		} 
	//msg+= 
}
}
return flag;
});
});
</script>   
</head>
<body>
<s:if test="hasActionMessages()"><p></p>
	<b>
   			<div class="messages" ></b>
     		<s:actionmessage/>
  			 </div>
	</s:if>
	<s:form action="prgDlrAccMapping" method="post">
	<s:hidden name="pcAccidListSize" value= "%{pcAccidListSize}" id="pcAccidListSize" /> 
	<s:hidden name="smAccidListSize" value= "%{smAccidListSize}" id="smAccidListSize"  /> 
	<s:hidden name="vanAccidListSize" value= "%{vanAccidListSize}" id="vanAccidListSize" />
	<s:hidden name="ftlAccidListSize" value= "%{ftlAccidListSize}" id="ftlAccidListSize" />
		<div id="widecontentarea">
			<span id="sp"></span>
			<div class="pageTitle" id="HL1">DPB Program Dealer Account
				Mapping</div>

			<div class="T8">
				<table width="728" border="0" cellspacing="0"
					class="template8TableTop">
					<tr>
						<td colspan="2" class="line"><img
							src="<%=request.getContextPath()%>/images/img_t8_line1.gif"
							height="1" alt="" border="0">
						</td>
					</tr>
					<tr>
						<td width="362" class="left" id="ctl00_tdCopyText"><div
								class="TX1">The account number of dealers is not stored
								in the Dealer Performance Bonus application. Instead, the
								account id referred in COFICO for the corresponding account is
								mapped to the DPB programs here. These data will be provided by
								COFICO team. This page displays all the mapped dealer programs
								and dealer details.</div>
						</td>

						<td width="363" class="right"><div align="left">
								<img id="ctl00_imgLevel2"
									src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg"
									style="border-width: 0px;" /><br>
							</div>
						</td>
					</tr>

					<!--tr>
				<td class="leftEditLinks">&nbsp;</td>
				<td class="rightEditLinks">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" class="line"><img src="../../images/img_t8_line4.gif" width="729" height="1" alt="" border="0"></td>
			</tr-->
					<tr>
						<td colspan="2">
					<tr>
						<td colspan="2"><img
							src="<%=request.getContextPath()%>/images/img_t8_line1.gif"
							height="1" alt="" border="0">
						</td>
					</tr>
					<!--  <div class="template8BottomLine"></div></td></tr>-->
				</table>
			</div>
			<p>
				<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2//EN">
				<HTML>
<!--   		BODY,DIV,TABLE,THEAD,TBODY,TFOOT,TR,TH,TD,P { font-family:"Arial"; font-size:x-small }  		 -->
<BODY TEXT="#000000">
			</p>
			<table width="200" border="0" class="TBL2">
				<COLGROUP>
					<COL WIDTH="86">
					<COL WIDTH="86">
					<COL WIDTH="86">
					<COL WIDTH="86">
				</COLGROUP>
				<TBODY>
					<tr>
						<td width="15%"><b><s:label value ="Vehicle Type (Subsidiary)"></s:label></b>
						</td>
						<td width="20%"><b><s:label value ="Program Component"></s:label></b>
						</td>
						<td width="15%"><b><s:label value ="Credit Account Id(COFICO)"></s:label></b>
						</td>
						<td width="15%"><b><s:label value ="Debit Account Id (COFICO)"></s:label></b>
						</td>
						<td width="25%"><b><s:label value ="Status"></s:label></b>
						</td>
						<td width="10%"><b><s:label value ="Action"></s:label></b>
						</td>
					</tr>
					<s:iterator value="accountIDTypeList" var="vList" status="abc">
						<tr>
							<s:set name="tempId" value="#vList.id"/>
							<%-- <s:property value="%{tempId}" /> --%>
								<s:if test='%{#vList.id=="P"}'>	
									<td><b> 
										<s:property value="vehicleType" />
										<s:hidden name="ldrspAccIDMapForm.pcId" value="%{tempId}"/> 
										</b>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.PcConditionList" var="pcList" status="count">
										<p style="height:13px;">
										<s:text name="ldrspAccIDMapForm.PcConditionList[%{#count.index}]"  ></s:text><br/><!-- <br/> -->
										</p>
										</s:iterator>	
									</td>
									<td> 
										<s:iterator value="ldrspAccIDMapForm.pcAccountIdList" var="pcaccList" status="count">
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.pcAccountIdList[%{#count.index}]" id="pcaccList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('pcaccList%{#count.index}');"></s:textfield><br/>
										</p>
										</s:iterator>
									</td>
									<td> 
										<s:iterator value="ldrspAccIDMapForm.pcIndicatorList" var="pcIndList" status="count">
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.pcIndicatorList[%{#count.index}]" id="pcIndList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('pcIndList%{#count.index}');"></s:textfield><br/>
										</p>
										</s:iterator>										
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.pcStatusList" var="pcstsList" status="count">
										<p style="height:13px;">
										<s:radio name="ldrspAccIDMapForm.pcStatusList[%{#count.index}]" list="#{'D':'Draft','A':'Active','I':'In-Active'}"  /><br/>
										</p>
										</s:iterator>	
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.pcAccountIdList" var="pcaccList" status="count">
										<p style="height:13px;">
										<s:a href="javascript:fileLink('pcaccList%{#count.index}');" id="pcaccList%{#count.index}" >Edit</s:a><%-- <s:div id="sp%{#count.index}"></s:div> --%>
										 <br>
										</p>
										 <!--<br /> -->
										</s:iterator>
									</td>
									<td>
										
										<s:iterator value="ldrspAccIDMapForm.pcLdrspBnsPgmIdList" var="pcIdBnsPgmList" status="count">
										<s:hidden name="ldrspAccIDMapForm.pcLdrspBnsPgmIdList[%{#count.index}]"></s:hidden><br/>
										</s:iterator>
									</td> 
								 </s:if>
								 <s:elseif test='%{#vList.id=="S"}'>
									 <s:set name="tempId" value="#vList.id"/>
									 <td><b>
									 	 <s:property value="vehicleType" /> 
										 <s:hidden name="ldrspAccIDMapForm.smartId" value="%{tempId}" /> </b>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.smartConditionList" var="smartList" status="count">
										<p style="height:13px;">
										<s:text name="ldrspAccIDMapForm.smartConditionList[%{#count.index}]"></s:text><br/><!-- <br/> -->
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.smartAccountIdList" var="smartaccList" status="count">
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.smartAccountIdList[%{#count.index}]" id="smartaccList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('smartaccList%{#count.index}');"></s:textfield> <br/>
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.smartIndicatorList" var="smartIndList" status="count">
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.smartIndicatorList[%{#count.index}]" id="smartIndList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('smartIndList%{#count.index}');"></s:textfield> <br/>
										</p>
										</s:iterator>										
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.smartStatusList" var="smartstsList" status="count">
										<p style="height:13px;">
										<s:radio name="ldrspAccIDMapForm.smartStatusList[%{#count.index}]" list="#{'D':'Draft','A':'Active','I':'In-Active'}" /><br/>
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.smartAccountIdList" var="smartaccList" status="count">
										<p style="height:13px;">
										<s:a href="javascript:fileLink('smartaccList%{#count.index}');" id="smartaccList%{#count.index}" >Edit</s:a><%-- <s:div id="sp%{#count.index}"></s:div> <br/> --%>
										<br/>
										</p>
										</s:iterator>
									</td> 
									<td>
										
										<s:iterator value="ldrspAccIDMapForm.smartLdrspBnsPgmIdList" var="smartIdBnsPgmList" status="count">
										<s:hidden name="ldrspAccIDMapForm.smartLdrspBnsPgmIdList[%{#count.index}]"></s:hidden><br/>
										</s:iterator>
									</td> 
								</s:elseif>
								<s:elseif test='%{#vList.id=="V"}'>
									<s:set name="tempId" value="#vList.id"/>
									<td><b> 
										<s:property value="vehicleType" /> 
										<s:hidden name="ldrspAccIDMapForm.vanId" value="%{tempId}" /> </b>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.vanConditionList" var="vanList" status="count">
										<p style="height:13px;">
										<s:text name="ldrspAccIDMapForm.vanConditionList[%{#count.index}]"></s:text><br/><!-- <br/> -->
										</p>
										</s:iterator></td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.vanAccountIdList" var="vanaccList" status="count" >
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.vanAccountIdList[%{#count.index}]" id="vanaccList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('vanaccList%{#count.index}');"></s:textfield><br/>
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.vanIndicatorList" var="vanIndList" status="count" >
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.vanIndicatorList[%{#count.index}]" id="vanIndList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('vanIndList%{#count.index}');"></s:textfield><br/>
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.vanStatusList" var="vanstsList" status="count">
										<p style="height:13px;">
										<s:radio name="ldrspAccIDMapForm.vanStatusList[%{#count.index}]" list="#{'D':'Draft','A':'Active','I':'In-Active'}" /><br/>
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.vanAccountIdList" var="vanaccList" status="count">
										<p style="height:13px;">
										<s:a href="javascript:fileLink('vanaccList%{#count.index}');" id="vanaccList%{#count.index}">Edit</s:a><%-- <s:div id="sp%{#count.index}"></s:div> <br/>--%>
										<br/>
										</p>
										</s:iterator>
									</td>
									<td>
										
										<s:iterator value="ldrspAccIDMapForm.vanLdrspBnsPgmIdList" var="vanIdBnsPgmList" status="count">
										<s:hidden name="ldrspAccIDMapForm.vanLdrspBnsPgmIdList[%{#count.index}]"></s:hidden><br/>
										</s:iterator>
									</td> 
								</s:elseif>
								<s:elseif test='%{#vList.id=="F"}'>
									<s:set name="tempId" value="#vList.id"/>
										<td><b> 
											<s:property value="vehicleType" />
									 		<s:hidden name="ldrspAccIDMapForm.ftlId" value="F" /> </b>
										</td>
										<td>
											<s:iterator value="ldrspAccIDMapForm.ftlConditionList" var="fList" status="count">
											<p style="height:13px;">
											<s:text name="ldrspAccIDMapForm.ftlConditionList[%{#count.index}]"></s:text><br/><!-- <br/> -->
											</p>
											</s:iterator>
											</td>
										<td>
										<s:iterator value="ldrspAccIDMapForm.ftlAccountIdList" var="ftlaccList" status="count">
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.ftlAccountIdList[%{#count.index}]" id="ftlaccList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('ftlaccList%{#count.index}');"></s:textfield><br/>
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.ftlIndicatorList" var="ftlIndList" status="count">
										<p style="height:13px;">
										<s:textfield name="ldrspAccIDMapForm.ftlIndicatorList[%{#count.index}]" id="ftlIndList%{#count.index}" readonly="true" style="height:13px;" onchange = "javascript:onAccountidEdit('ftlIndList%{#count.index}');"></s:textfield><br/>
										</p>
										</s:iterator>
									</td>
									<td>
										<s:iterator value="ldrspAccIDMapForm.ftlStatusList" var="ftlstsList" status="count">
										<p style="height:13px;">
										<s:radio name="ldrspAccIDMapForm.ftlStatusList[%{#count.index}]" list="#{'D':'Draft','A':'Active','I':'In-Active'}" /><br/>
										</p>
										</s:iterator>
									</td>
										<td>
											<s:iterator value="ldrspAccIDMapForm.ftlAccountIdList" var="ftlaccList" status="count">
											<p style="height:13px;">
											<s:a href="javascript:fileLink('ftlaccList%{#count.index}');" id="ftlaccList%{#count.index}">Edit</s:a><%-- <s:div id="sp%{#count.index}"></s:div> <br/>--%>
											<br/>
											</p>
											</s:iterator>
									</td>
									<td> 
										
										<s:iterator value="ldrspAccIDMapForm.ftlLdrspBnsPgmIdList" var="ftlIdBnsPgmList" status="count">
										<s:hidden name="ldrspAccIDMapForm.ftlLdrspBnsPgmIdList[%{#count.index}]"></s:hidden><br/>
										</s:iterator>
									</td> 
								</s:elseif>
							</tr>
					</s:iterator>
				</TBODY>
			</table>
				<s:submit method="prgDlrAccMapping" type="button" id="submitabc" value="Submit" align="left" theme="simple" />
				<s:reset type="button" key="Reset"  value="Reset" theme="simple"/>
				<s:submit type="button" name="button" key="cancel" value="Cancel" onclick="cancel(this)" theme="simple"/>
		</div>
	</s:form>
</body>
</html>