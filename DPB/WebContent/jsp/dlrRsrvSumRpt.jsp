<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
	<title>Dealer Reserve Summary Report</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/master.css" />
	<link href="<%=request.getContextPath()%>/css/master.css" type="text/css" rel="stylesheet" />
	<link href="<%=request.getContextPath()%>/css/admin.css" type="text/css" rel="stylesheet" />
	<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
	<script  src="<%=request.getContextPath() %>/js/homeOfficeRpt.js" type="text/javascript"></script>
	<sj:head jquerytheme="flick"/>
	<style>
	.serror {
		color: red;
		padding-left: 3px;
	}em {
		color: red;
	}span {
		color: red;
	}
	</style>
</head>
<body>
<s:form action="dlrRsrvSumReport" method="post">
<div id="widecontentarea">
	<div class="pageTitle" id="HL1">Dealer Reserve Summary Report</div>
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The home office report for Dealer Reserve Summary.</div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			  </div></td>
			</tr>
             <tr><td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
		</table>
	</div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	    <td width="20%">Vehicle<br></td>
	   <td><s:checkboxlist name="vehicles" theme="vertical-checkbox" list="vehList" listKey="id" listValue="vehicleType"/></td>
      </tr>	
      <tr>
       <td width="20%">Year<br></td>
        <td><s:textfield name="year" id="year" /></td>
       </tr>
      <tr>
       <td width="20%">Month<br></td>
        <td><s:textfield name="month" id="month" /></td>
      </tr>
	  <tr> 
	  <td>
	    <s:submit method="viewDlrRsrvSumReport" id="submitabc" value="Generate Report" align="left"/>
        <s:reset type="reset" value="Reset" theme="simple"/></td>
         <td>&nbsp;</td>
        </tr>
    </table>
    <p>&nbsp;</p>
		<s:if test="%{list.isEmpty()}">
			<%-- <table>
			<tr>
				<td>REPORT: ZVERDLRRES_RECONCILE_CAR_VAN</td>
				<td width="400"></td><td>DATE:<s:property value="date"></s:property></td>
			</tr>
			<tr>
			<td>SYSTEM: M11</td>
			<td width="500"></td><td>TIME:<s:property value="time"></s:property></td>
			</tr>
			<tr></tr>
			<tr>
			<td width="400"></td><td>Model Year/Account -&nbsp;<s:property value="month"></s:property>&nbsp;&nbsp;<s:property value="year"></s:property></td>
			</tr>
			</table> --%>
			<table class="TBL2"><tr class="header"><td width="100" align="center">Model Year</td><td width="120" align="center">2000</td><td width="120" align="center">2011</td><td width="120" align="center">2012</td><td width="120" align="center">2013</td><td width="120" align="center">2014</td><td width="120" align="center">&nbsp;</td></tr></table>
			<table class="TBL2"><tr class="header"><td width="100" align="center">GL Account</td><td width="120" align="center">Old MY</td><td width="120" align="center">492360</td><td width="120" align="center">492370</td><td width="120" align="center">492380</td><td width="120" align="center">492390</td><td width="120" align="center">Subtotal</td></tr></table>
			<tr></tr>
			<table class="TBL2">
			<tr class="header"><td align="left">ACCRUED</td></tr>
			<!-- <tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr> -->
			<tr class="header"><td align="left">RETAILED</td></tr>
			<s:iterator value="list">
			<tr>
				<td width="60" align="right"><s:property value="prgType"></s:property></td>
				<td width="60" align="right"><s:property value="Year"></s:property></td>
				<td width="60" align="right"><s:property value="prgCount"></s:property></td>
				<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
			</tr>
			</s:iterator>
			
			<!-- <tr><td width="100" class="header" align="center">Non MVP/Alt</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td><td width="60" align="right">&nbsp;</td>
			<tr><td width="100" class="header" align="center">MVP</td></tr>
			<tr><td width="100" class="header" align="center">Subtotal*</td></tr>
			<tr><td width="100" class="header" align="center">Alt Trans</td></tr>
			<tr><td width="100" class="header" align="center">Auctions</td></tr>
			<tr><td width="100" class="header" align="center">Fleet</td></tr>
			<tr><td width="100" class="header" align="center">MY Cashout</td></tr>
			<tr><td width="100" class="header" align="center">Subtotal</td></tr>
			<tr><td width="100" class="header" align="center">Prior retls</td></tr>
			<tr><td width="100" class="header" align="center">Total</td></tr>
			<tr class="header"><td align="left">NET</td></tr>
			<tr><td width="100" class="header" align="center">Accrued</td></tr>
			<tr><td width="100" class="header" align="center">Retailed</td></tr>
			<tr><td width="100" class="header" align="center">Net total</td></tr> -->
			</table>
		</s:if>
		<s:else>
			<table class="TBL2"><tr class="header"><td width="100" align="center">Model Year</td><td width="120" align="center">2000</td><td width="120" align="center">2011</td><td width="120" align="center">2012</td><td width="120" align="center">2013</td><td width="120" align="center">2014</td><td width="120" align="center">&nbsp;</td></tr></table>
			<table class="TBL2"><tr class="header"><td width="100" align="center">GL Account</td><td width="120" align="center">Old MY</td><td width="120" align="center">492360</td><td width="120" align="center">492370</td><td width="120" align="center">492380</td><td width="120" align="center">492390</td><td width="120" align="center">Subtotal</td></tr></table>
			<tr></tr>
			<table class="TBL2">
			<tr><td width="100" class="header" align="left">ACCRUED</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
			<tr><td width="100" class="header" align="left">RETAILED</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
			<tr><td width="100" class="header" align="center">Non MVP/Alt</td>
				<s:iterator value="list" var="rList">
				<s:if test='%{#rList.prgType=="GEN"}'>
					<s:if test='%{#rList.Year=="2000"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:if>
					<s:elseif test='%{#rList.Year=="2011"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:if test='%{#rList.Year=="2012"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:if>
					<s:elseif test='%{#rList.Year=="2013"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2014"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="999999999"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
				</s:if>
				</s:iterator></tr>
			<tr><td width="100" class="header" align="center">MVP</td>
			<s:iterator value="list" var="rList">
				<s:if test='%{#rList.prgType=="MBDEAL"}'>
					<s:if test='%{#rList.Year=="2000"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:if>
					<s:elseif test='%{#rList.Year=="2011"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2012"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2013"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2014"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="999999999"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
				</s:if>
				</s:iterator></tr>
			<tr><td width="100" class="header" align="center">Subtotal*</td>
			<s:iterator value="list" var="rList">
				<s:if test='%{#rList.prgType=="SUB_TOT1"}'>
					<s:if test='%{#rList.Year=="2000"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:if>
					<s:elseif test='%{#rList.Year=="2011"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2012"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2013"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2014"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="999999999"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
				</s:if>
				</s:iterator></tr>
			<tr><td width="100" class="header" align="center">Alt Trans</td>
			<s:iterator value="list" var="rList">
				<s:if test='%{#rList.prgType=="CVP"}'>
					<s:if test='%{#rList.Year=="2000"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:if>
					<s:elseif test='%{#rList.Year=="2011"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2012"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2013"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2014"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="999999999"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
				</s:if>
				</s:iterator></tr>
			<tr><td width="100" class="header" align="center">Auctions</td></tr>
			<tr><td width="100" class="header" align="center">Fleet</td></tr>
			<tr><td width="100" class="header" align="center">MY Cashout</td></tr>
			<tr><td width="100" class="header" align="center">Subtotal</td>
			<s:iterator value="list" var="rList">
				<s:if test='%{#rList.prgType=="SUB_TOT1"}'>
					<s:if test='%{#rList.Year=="2000"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:if>
					<s:elseif test='%{#rList.Year=="2011"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2012"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2013"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="2014"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
					<s:elseif test='%{#rList.Year=="999999999"}'>
					<td width="60" align="right"><s:property value="prgCount"></s:property></td>
					<td width="60" align="right"><s:property value="sumAmount"></s:property></td>
					</s:elseif>
				</s:if>
				</s:iterator></tr>
			<tr><td width="100" class="header" align="center">Prior retls</td></tr>
			<tr><td width="100" class="header" align="center">Total</td></tr>
			<tr class="header"><td align="left">NET</td></tr>
			<tr><td width="100" class="header" align="center">Accrued</td></tr>
			<tr><td width="100" class="header" align="center">Retailed</td></tr>
			<tr><td width="100" class="header" align="center">Net total</td></tr>
			</table>
		</s:else>
	<br /><br />
	</div>
	</s:form>
</body>
</html>