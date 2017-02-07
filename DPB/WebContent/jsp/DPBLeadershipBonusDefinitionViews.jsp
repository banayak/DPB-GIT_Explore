<%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
<title>LeadershipBonusDefination</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script type="text/javascript">
			function cancels(val) {			
				val.form.action = ctxPath + '/ldrShipBonusList';
				val.form.submit();
			}
			</script>
			
</head>

<body>
	<s:form name ="leadershipBonusDef" id="leadershipBonusDef" action="leadershipBonusDef" method="post">
		<s:hidden name="ldrshipbnsdtls.unitCountVal" value= "%{ldrshipbnsdtls.unitCount}" id="number8"  />
		<s:hidden name="ldrshipidLinkView" value= "%{ldrshipidLinkView}" id="ldrshipidLinkView"   />
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
								current and future fiscal year that needs to be paid..</div>
						</td>
						<td width="363" class="right">
							<div align="left">
								<img id="ctl00_imgLevel2" src="<%=request.getContextPath()%>/resources/13554/image_22643.jpg" style="border-width: 0px;" /><br>
						    </div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="template8BottomLine"></div>
						</td>
					</tr>
				</table>
			</div>
			<table width="200" border="0" class="TBL2">
				<tr>
					<td><b><s:label value ="Leadership Bonus ID"></s:label></b></td>
					<td>
					    <s:property value="ldrshipbnsdtls.ldrshipid" />
					</td>
				
				</tr>	
				<tr>
					<td><b><s:label value ="Leadership Bonus Name"></s:label></b></td>
					<td>
					    <s:property value="ldrshipbnsdtls.ldrshipname"/>
					</td>
				
				</tr>	
					<tr>
					<td><b><s:label value ="Accrual Start Date"></s:label></b></td>
					<td>
					    <s:property value="ldrshipbnsdtls.startDate"/> 
					
					</td>
				
				</tr>	
				<tr>
					<td><b><s:label value ="Accrual End Date"></s:label></b></td>
					<td>
					    <s:property value="ldrshipbnsdtls.endDate"/>
					</td>				
				</tr>	
						<tr>
					<td><b><s:label value ="Retail Start Date"></s:label></b></td>
					<td>
					    <s:property value="ldrshipbnsdtls.rtlStartDate"/> 
					
					</td>
				
				</tr>	
				<tr>
					<td><b><s:label value ="Retail End Date"></s:label></b></td>
					<td>
					    <s:property value="ldrshipbnsdtls.rtlEndDate"/>
					</td>				
				</tr>	
					<tr>
					<td><b><s:label value ="Applicable Vehicles"></s:label></b></td>
					<%-- <td>
					   <s:checkboxlist theme="vertical-checkbox" list="vehicleList" name="ldrshipbnsdtls.ldrshipAppVehs" listKey="id"  listValue="vehicleType"  disabled="true"/>
					</td>	 --%>
					 <td>
	          			<s:iterator value="ldrshipbnsdtls.ldrshipAppVehs" status="count">
						<li><s:text name="ldrshipbnsdtls.ldrshipAppVehs[%{#count.index}]"></s:text></li>
		       			 </s:iterator>
         		</td>			
				</tr>
				<tr>
				<td><b><s:label value ="Unused Amount"></s:label></b></td>
				<td><s:property value="ldrshipbnsdtls.unusdamt" />
				Note: This amount is calculated based on netAccrual data from COFICO and selected fiscal year above.</td>
				</tr>
				<tr>
				<td><b><s:label value ="Leadership Eligible Units"></s:label></b></td>
				<td><s:property value="ldrshipbnsdtls.unitCount" /></td>
				</tr>
				<tr>
				<td><b><s:label value ="Business Reserve Percentage"></s:label></b></td>
					<td><s:property value="ldrshipbnsdtls.busresvper" />
					</td>
				</tr>
				<tr>
				<td><b><s:label value ="Business Reserve Amount"></s:label></b></td>
					<td><s:property value="ldrshipbnsdtls.busresvamt" />
					</td>
				</tr>
				<tr>
				<td><b><s:label value ="Leadership Bonus Amount"></s:label></b></td>
					<td><s:property value="ldrshipbnsdtls.ldrbnsamt"/>
					 Note: Based on the Business reserve and Unused amount, this Leadership Amount will be calculated.</td>
				</tr>
				<tr>
				<td><b><s:label value ="Calculated Amount per Unit"></s:label></b></td>
					<td><s:property value="ldrshipbnsdtls.calcamtperunt"/> 
					Note: This amount is calculated based on leadership file details (dealer, po number) and leadership bonus amount.</td>
				</tr>
				<tr>
				<td><b><s:label value ="Actual Amount per Unit"></s:label></b></td>
					<td><s:property value="ldrshipbnsdtls.actamtperunt"/>
					</td>
				</tr>				
				<tr>
					<td><b><s:label value ="Process Date"></s:label></b></td>
					<td><s:property  value="ldrshipbnsdtls.processDate" /><del></del>
					</td>
				</tr>
				<tr>
					<td><b><s:label value ="Status"></s:label></b></td>
					<td><s:property  value="ldrshipbnsdtls.status" /><del></del>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>				
			</table>
				 <s:submit type="button" name="button" key="cancel" value="Cancel" onclick="cancels(this)"/>
			<br />
			<br />
			<br />
		</div>
	</s:form>
</body>
</html>