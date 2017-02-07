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
<s:form action="dlrReserveExceptiont" method="post">			
<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	DPB Dealer Reserve Exception report
</div>
 
<div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
		<tr>
			<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
		</tr>
		<tr>
			<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The home office report for Dealer Reserve Exception.</div></td>

			<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
		  </div></td>
		</tr>
		<tr><td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
	</table>
</div>
<table width="81%" border="0" class="TBL2">
	<tr>
		<td width="30%"><s:label for="vehicleType" value="Franchise"  ></s:label></td>
		<td width="60%">
			<s:checkboxlist  name="vehicleType" theme="vertical-checkbox" list ="vehicleList"  listKey="id"  listValue="vehicleType"/>
		</td>
		 <td width="10%">&nbsp;</td>
	</tr>
	<tr>
	   <td width="30%">
		Date Range
		</td>
	   <td width="60%">
		  	Month :    
		  		<s:select name="month" id="month"
					list="#{'':'Select One','1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10','11':'11','12':'12'}" />
        	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	Year:  
		  	<s:select name="year" id="year"
							list="#{'':'Select One','2013':'2013','2014':'2014','2015':'2015'}" />
        	     
		</td>
		<td width="10%">
	    </td>
	</tr>
	<tr>
	    <td width="30%">Dealer<br></td>
	    <td width="60%">
			<textarea name="dealerIds"  cols="45" rows="5" id="dealerIds" ></textarea><span id="span1" class="serror"></span>
		</td>
	    <td width="10%">&nbsp;</td>
	</tr>
	<tr>
	  	<td>
	    	<s:submit key="submit" value="Generate Report" method="generateReport"  id="genReptRP"/>
	         	<input type="reset" name="reset" id="reset" value="Reset" />
	    </td>
	</tr>
</table>
<p>&nbsp;</p>
<s:if test="%{vehExceptionList.isEmpty() }">
	<tr>
		<td colspan="7"><span>No record present.</span></td>
	</tr>
</s:if>
<s:else>
	<table width="100%" height="140" border="1" summary="Dealer Reserve Exception report">
	     <caption class="pageTitle">
	     	Dealer Reserve Exception report
	     </caption>
	     <tr>
	      <th width="10%" height="17" bgcolor="#999999" scope="row">RETAIL DT</th>
	      <td width="10%" bgcolor="#999999"><strong>MY</strong></td>
	      <td width="10%" bgcolor="#999999"><strong>MODEL</strong></td>
	      <td width="10%" bgcolor="#999999"><strong>SERIAL</strong></td>
	      <td width="10%" bgcolor="#999999"><strong>PO NUMBER</strong></td>
	      <td width="10%" bgcolor="#999999"><strong>DEALER</strong></td>
	      <td width="10%" bgcolor="#999999"><strong>REGION</strong></td>
	      <td width="10%" bgcolor="#999999"><strong>CAR COUNT</strong></td>
	      <td width="20%" bgcolor="#999999"><strong>REASON</strong></td>
	    </tr>
	    <s:iterator value="vehExceptionList">
		<tr>
		   <td width="10%"><s:property value="retailDate"/></td>
	       <td width="10%"><s:property value="modelYearDate"/></td>
	       <td width="10%"><s:property value="modelText"/></td>
	       <td width="10%"><s:property value="vinNum"/></td>
	       <td width="10%"><s:property value="poNo"/></td>
	       <td width="10%"><s:property value="dealerId"/></td>
	       <td width="10%" align="right"><s:property value="regionCode"/></td>
	       <td width="10%" align="right"><s:property value="vehicleCount"/></td>
	       <td width="20%"><s:property value="reason"/></td>
		</tr>
		</s:iterator>
	</table>
</s:else>
<p>
	<br/>
</p>
<p>
	<br/>
</p>
</div>
</s:form> 
</body>
</html>