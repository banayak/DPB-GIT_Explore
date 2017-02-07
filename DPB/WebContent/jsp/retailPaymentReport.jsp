<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
  <head>
	<title>DPB - Home Office Reports - Retails and Payment</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta content="Copyright (c) BrandWizard Technologies" name="Copyright"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/master.css" />
	<link href="<%=request.getContextPath()%>/css/master.css" type="text/css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/admin.css" type="text/css" rel="stylesheet" />
	<script	src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath() %>/js/homeOfficeRpt.js" type="text/javascript"></script>
	<sj:head jquerytheme="flick"/>
	<style>
	.serror {
	color: red;
	padding-left: 3px;
}

em {
	color: red;
}
</style>
  </head>
<div>
</div>
<body>
<s:form action="generateReport" method="post">			
<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB 
    Leadership Bonus Report</div>
 
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The home office report for Leadership Bonus Program.</div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			  </div></td>
			</tr>
			<tr><td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
		</table>
	</div>
	<table width="81%" border="0" class="TBL2">
	  <tr>
	  	<td><s:label for="vehicleType" value="Franchise"  ></s:label></td>
	    <td><s:checkboxlist  name="vehicleType" theme="vertical-checkbox" list ="vehicleList"  listKey="id"  listValue="vehicleType"/></td>
      </tr>
     <tr>
      <td width="33%"><input type="radio" name="viewAccountVin" value="VIN" id="VIN-DealerLevel_0">
			View Account Balance by VIN</td>
       <td width="14%">
	    	From Date<br>
      	
			<sj:datepicker name="fromDate" id="fromDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus" id="fromDate"/></td>
			 <td width="53%">
	    	To Date<br>
	    
			<sj:datepicker name="toDate"   id="toDate" maxDate="today"  displayFormat="mm/dd/yy" showOn="focus" id="toDate"/><span id="span2" class="serror"></span></td>
		</tr>
<tr>
	    <td><input type="radio" name="viewAccountVin" value="D" id="VIN-DealerLevel_1">
		View Eligible Dealers by Quarter</td>
	    <td><s:select name="viewDealerLevelQuarter" id="viewDealerLevelQuarter"
							list="#{'-1':'Select','1':'First Quarter','2':'Second Quarter','3':'Third Quarter','4':'Fourth Quarter'
                                                }" /></td>
	    <td>&nbsp;</td>
      </tr>
	  <tr>
	    <td><input type="radio" name="viewAccountVin"  value="V" id="VIN-DealerLevel_2">
		View Total Eligible Vehicles by Quarter </td>
	    <td>
	      <s:select name="viewTotVehQuarter" id="viewTotVehQuarter"
							list="#{'-1':'Select','1':'First Quarter','2':'Second Quarter','3':'Third Quarter','4':'Fourth Quarter'}" /></td>
        	    <td><span id="span1" class="serror"></span></td>
      </tr>
	  <tr>
	  <td>
	    <s:submit key="submit" value="Generate Report" method="generateReport"  id="genReptRP"/>
          <input type="reset" name="reset" id="reset" value="Reset" /></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
      </tr>
    </table>
	<p>&nbsp;</p>
	
			 <s:if test="%{vehicleTypeList.isEmpty() }">
				<s:if test="%{vehicleCond.isEmpty()}">
					<s:if test="%{totVehList.isEmpty()}">
					<tr>
						<td colspan="7"><span>No Record Present.</span></td>
					</tr>
			</s:if>
			 </s:if>
			 </s:if>
			 <s:if test="%{vehicleTypeList.isEmpty() }">
			 </s:if>
				<s:else>
				<table width="422" height="140" border="1" summary="Blocked Vehicles - Home Office Report">
			      <caption class="pageTitle">
			      Leadership Bonus Account Balances by VIN</caption>
			      <tr>
			        <th width="100" height="17" bgcolor="#999999" scope="row">Sno</th>
			        <td width="89" bgcolor="#999999"><strong>VIN</strong></td>
			        <td width="105" bgcolor="#999999"><strong>Quarter</strong></td>
			        <td width="100" bgcolor="#999999"><strong> Account Balance</strong></td>
			      </tr>
			       <s:iterator value="vehicleTypeList">
							<tr>
								<td >
									<s:property value="srNo"></s:property>
								</td>
								<td>
									<s:property value="vin"></s:property>
								</td>
								<td>
									<s:property value="quarter"></s:property><br/>
								</td>
								<td>
									<s:property value="accountBalance"></s:property><br/>
								</td>
							</tr>
					</s:iterator>
				 </table>
			    </s:else>
				<p><br />
			    </p>
		    	<s:if test="%{vehicleCond.isEmpty()}">
				</s:if>
				<s:else>
				<table width="650" height="119" border="1" summary="Blocked Vehicles - Home Office Report">
				  <caption class="pageTitle">
				    Leadership Bonus Eligible Dealers
			- Quarter<s:property value="viewDealerLevelQuarter"/>	  
				  </caption>
				  <tr>
				    <th width="30" height="17" bgcolor="#999999" scope="row">Sno</th>
				    <td width="70" bgcolor="#999999"><strong>Dealer Id</strong></td>
				    <td width="150" bgcolor="#999999"><strong>Dealer Name</strong></td>
				    <td width="190" bgcolor="#999999"><strong>PO Number</strong></td>
			      </tr>
			       <s:iterator value="vehicleCond">
							<tr>
								<td >
									<s:property value="srNo"></s:property>
								</td>
								<td>
									<s:property value="dealerId"></s:property>
								</td>
								<td>
									<s:property value="dealerName"></s:property><br/>
								</td>
								<td>
									<s:property value="poNumber"></s:property><br/>
								</td>
							</tr>
					</s:iterator>
				 </table>
			    </s:else>
			<p>&nbsp;</p>
	
			 
		    <s:if test="%{totVehList.isEmpty()}">		
				</s:if>
				<s:else>
			<table width="650" height="143" border="1" summary="Blocked Vehicles - Home Office Report">
				  <caption class="pageTitle">
				    Leadership Bonus Eligible Vehicles
			      - Quarter <s:property value="viewTotVehQuarter"/>
				  </caption>
				  <tr>
				    <th width="30" height="17" bgcolor="#999999" scope="row">Sno</th>
				    <td width="70" bgcolor="#999999"><strong>Dealer Id</strong></td>
				    <td width="190" bgcolor="#999999"><strong>Dealer Name</strong></td>
				    <td width="100" bgcolor="#999999"><strong>Total Vehicles</strong></td>
			      </tr>
       			  <s:iterator value="totVehList">
       			  			<s:if test="%{flag != true}">
							<tr>
								<td>
									<s:property value="srNo"></s:property>
								</td>
								<td>
									<s:property value="dealerId"></s:property>
								</td>
								<td>
									<s:property value="dealerName"></s:property>
								</td>
								<td>
									<s:property value="totalVehicles"></s:property><br/>
								</td>
							</tr>
							</s:if>
							<s:else>
							<tr>
	   							 <th height="22" colspan="3" scope="row">Total Vehicles</th>
	    							<td><s:property value="totalVehicles"></s:property><br/></td>
     						</tr>
     						</s:else>			
					</s:iterator>
    			</table>
    			</s:else>
			<p><br />
    	</p>
	</div>
 
</s:form> 
</body>
</html>