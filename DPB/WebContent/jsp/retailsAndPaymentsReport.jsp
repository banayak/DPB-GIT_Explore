<%try{ %>
<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<s:set name="theme" value="'simple'" scope="page" />
<html>
<head>
	<title>DPBProgramDealerAccountMapping</title>
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
}

em {
	color: red;
}
</style>
</head>
	
<body>
	<s:form action="retailsAndPaymentsReport" method="post">
		<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Retails and Payments Report</div>
 
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The home office report for Blocked Vehicles.</div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
			  </div></td>
			</tr>
			
			<!--tr>
				<td class="leftEditLinks">&nbsp;</td>
				<td class="rightEditLinks">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" class="line"><img src="../../images/img_t8_line4.gif" width="729" height="1" alt="" border="0"></td>
			</tr-->
            <tr><td colspan="2">	<img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
		</table>
	</div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	    <td width="20%">Franchise <br></td>
	    <td width="80%">VIN(s) (comma separated values)<br></td>
      </tr>
	  <tr>
	    <td><s:checkboxlist name="vehicleType"
										theme="vertical-checkbox" list="vehicleTypeList" listKey="id"
										listValue="vehicleType" id="vehicleType"/></td>
	    <td><textarea name="vehicleId"  cols="45" rows="5" id="vehicleId" ></textarea><span id="span1" class="serror"></span></td>
      </tr>
	  <tr>
	    <td>
	    	From Date
      	</td>
	    <td>
	    	To date
	    </td>
      </tr>
	  <tr>
	    <td><sj:datepicker name="fromDate" id="fromDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus" id="fromDate"/></td>
	    <td><sj:datepicker name="toDate"   id="toDate" maxDate="today"  displayFormat="mm/dd/yy" showOn="focus" id="toDate"/> <span id="span2" class="serror"></span></td>
      </tr>
      <tr>
	    <td>    	
	        <s:radio name="level"  id="level" list="#{'1':' VIN level','2':'Dealer level'}"  value="1"  />
	      </td>
        <td>&nbsp;</td>
      </tr>
	  <tr>
	    <td >
	    <s:submit key="submit" value="Generate Report" 	method="generateRetailsAndPaymentsReport" id="genReptRAP" />
	     <s:reset type="reset" name="reset" id="reset" value="Reset" /></td>
          <td>&nbsp;</td>
    
	</tr>
    </table>
	<br/>
	
		<%-- <s:if test="%{blockedList.isEmpty()}">
			<tr>
				<td colspan="7">No Process find in this day</td>
			</tr>
		</s:if>
		<s:else>
			<table width="957" height="90" border="1" summary="Blocked Vehicles - Home Office Report">
			  <caption class="pageTitle">Blocked Vehicles
		      </caption>
			  <tr>
			    <th width="64" height="17" bgcolor="#999999" scope="row"><strong>Sno</strong></th>
			    <td width="177" bgcolor="#999999"><strong>Purchase Order</strong></td>
			    <td width="159" bgcolor="#999999"><strong>Block Date</strong></td>
			    <td width="548" bgcolor="#999999"><strong>Blocked reason</strong></td>
		      </tr>
			  <s:iterator value="bList">
				<tr>
					<td width="62">
						<s:property value="dpbProcessId"></s:property>
					</td>
					<td width="239">
						<s:property value="poNumber"></s:property>
					</td>
					<td width="150">
						<s:property value="updatedDate"></s:property>
					</td>
					<td width="154">
						<s:property value="txtBlckReason"></s:property>
					</td>
				</tr>
			</s:iterator>
			</table>
		</s:else> --%>
	  
	  <p>&nbsp;</p>
	<table width="1460" height="406" border="1" summary="Blocked Vehicles - Home Office Report">
      <caption class="pageTitle">
      Dealer Performance Bonus Retails and Payments 2013
      </caption>
      <tr>
        <th width="100" height="17" bgcolor="#999999" scope="row">&nbsp;</th>
        <td width="66" bgcolor="#999999"><strong>Units</strong></td>
        <td width="126" bgcolor="#999999"><strong>Cust Exp Bns</strong></td>
        <td width="185" bgcolor="#999999"><strong>NV  Sales Bns</strong></p></td>
        <td width="185" bgcolor="#999999"><strong>PO Sales Bns</strong></p></td>
        <td width="185" bgcolor="#999999"><strong> Brand Stds Bonus</strong></td>
        <td width="185" bgcolor="#999999"><strong> Facility Bonus</strong></td>
        <td width="185" bgcolor="#999999"><strong> CVP  Bonus</strong></td>
        <td width="185" bgcolor="#999999"><strong> Total   Unearned Bonus</strong></td>
      </tr>
      <tr>
        <th height="21" colspan="9" scope="row">Month of April</th>
      </tr>
      <tr>
        <th height="22" scope="row"> Retails  </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">MBDEAL </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">Subtotal </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">Term 224</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">Term 224</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row"> Subtotal</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row"> Subtotal</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">CVP </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">Total</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="21" colspan="9" scope="row">Quarter 2</th>
      </tr>
      <tr>
        <th height="22" scope="row"> Retails </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">MBDEAL </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">Subtotal </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">Term 224</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">Term 224</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row"> Subtotal</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row"> Subtotal</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">CVP </th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th scope="row">Total</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th height="22" scope="row">&nbsp;</th>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    
	<br /><br />
</div>
 
	</s:form>
</body>
</html>
<%}catch(Exception e){
e.printStackTrace();
}%>