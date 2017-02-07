<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
	<title>DPBProgramDealerAccountMapping</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/master.css" />
	<link href="<%=request.getContextPath()%>/css/master.css" type="text/css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/admin.css" type="text/css" rel="stylesheet" />
	<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
	</head>
<body>
	<s:form action="vehicleConditionDef" method="post">
	<s:token/>
		<div id="widecontentarea">
			<div class="pageTitle" id="HL1">
			<s:set name="type" value="vcMappingForm.condType"/>
			<s:if test='%{#type=="B"}'>
				Blocking Vehicle Condition Definition
			</s:if>
			<s:else>
			    Vehicle Condition Definition
			</s:else>
			
			</div>
			<div class="T8">
				<table width="728" border="0" cellspacing="0" class="template8TableTop">
					<tr>
						<td colspan="2" class="line"><img
							src="<%=request.getContextPath()%>/images/img_t8_line1.gif"
							height="1" alt="" border="0">
						</td>
					</tr>
					<tr>
						<td width="362" class="left" id="ctl00_tdCopyText">
							<div class="TX1">The account number of dealers is not
								stored in the Dealer Performance Bonus application. Instead, the
								account id referred in COFICO for the corresponding account is
								mapped to the DPB programs here. These data will be provided by
								COFICO team. This page displays all the mapped dealer programs
								and dealer details.</div>
						</td>

						<td width="363" class="right"><div align="left">
								<img id="ctl00_imgLevel2" src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg"
									style="border-width: 0px;" /><br>
							</div>
						</td>
					</tr>

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
			 <s:hidden name="vcMappingForm.condType" />
			<TABLE FRAME="VOID" CELLSPACING="0" COLS="29" RULES="NONE" BORDER="0" class="TBL2">
				<COLGROUP>
					<COL WIDTH="86">
					<COL WIDTH="86">
					<COL WIDTH="86">
					<COL WIDTH="86">
				</COLGROUP>
				<TBODY>
					<tr>
						<td width="30%">
							<b> 
								<s:label value="Vehicle Type (Subsidiary)"></s:label> 
							</b>
						<td width="70%"><b><s:label value="Condition"></s:label>
						</b>
						</td>
						<%-- <td width="24%"><b><s:label value="Status"></s:label> </b>
						</td> --%>
					</tr>
					<s:iterator value="vehicleTypeList" var="vList" status="abc">
						<tr><s:set name="tempId" value="#vList.id"/>
						
							<s:if test='%{#tempId=="P"}'>
								<td>
									<b>
										<s:property value="vehicleType" />
								 		<s:hidden name="vcMappingForm.pcId" value="%{tempId}" />
									</b>
								</td>
								<td>
									<s:checkboxlist name="vcMappingForm.pcConditionList"
										theme="vertical-checkbox" list="vehicleCond" listKey="id"
										listValue="conditionName" />
								</td>
								<%-- <td>
									<s:radio name="vcMappingForm.pcStatus"	list="#{'D':'Draft','A':'Active','I':'In-Active'}" />
								</td> --%>
								<td></td>
							</s:if>
							<s:elseif test='%{#tempId=="S"}'>
								<s:set name="tempId" value="#vList.id"/> 
								<td>
									<b>
										<s:property value="vehicleType" /> 
										<s:hidden name="vcMappingForm.smartId" value="%{tempId}" /> 
									</b>
								</td>
								<td>
									<s:checkboxlist name="vcMappingForm.smartConditionList"
										theme="vertical-checkbox" list="vehicleCond" listKey="id"
										listValue="conditionName" />
								</td>
								<%-- <td>
									<s:radio name="vcMappingForm.smartStatus" list="#{'D':'Draft','A':'Active','I':'In-Active'}" />
								</td> --%>
								<td></td>
							</s:elseif>
							<s:elseif test='%{#tempId=="V"}'>
								<s:set name="tempId" value="#vList.id" />
								<td>
									<b> 
										<s:property value="vehicleType" /> 
										<s:hidden name="vcMappingForm.vanId" value="%{tempId}" /> 
									</b>
								</td>
								<td>
									<s:checkboxlist name="vcMappingForm.vanConditionList"
										theme="vertical-checkbox" list="vehicleCond" listKey="id"
										listValue="conditionName" />
								</td>
								<%-- <td>
									<s:radio name="vcMappingForm.vanStatus" list="#{'D':'Draft','A':'Active','I':'In-Active'}" />
								</td> --%>
								<td>
								</td>
							</s:elseif>
							<s:elseif test='%{#tempId=="F"}'>
								<td>
									<b> 
										<s:property value="vehicleType" /> 
										<s:hidden name="vcMappingForm.ftlId" value="%{tempId}" /> 
									</b>
								</td>
								<td>
									<s:checkboxlist name="vcMappingForm.ftlConditionList"
										theme="vertical-checkbox" list="vehicleCond" listKey="id"
										listValue="conditionName" />
								</td>
								<%-- <td>
									<s:radio name="vcMappingForm.ftlStatus"	list="#{'D':'Draft','A':'Active','I':'In-Active'}" />
								</td> --%>
								<td></td>
							</s:elseif>
						</tr>
					</s:iterator>
				</TBODY>
			</TABLE>
			<br />
			<table>
			<tr>
			<td>
				<s:submit key="submit" value="Submit"	method="saveVehicleMappedCondition" 
					align="right"/>
			</td> 
			<td>	<input type="reset" name="reset" id="reset" value="Reset" /> </td> 
			<td><s:submit key="Cancel" theme="simple" method="navigateToHomePage"/></td>
			</tr>
			</table>
			<br/>
		</div>
	</s:form>
</body>
</html>