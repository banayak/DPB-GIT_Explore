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
span {
	color: red;
}
</style>
</head>
	</head>
<body>
	<s:form action="blockedVehiclesReport" method="post">
		<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Blocked Vehicles Report</div>
 
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
             <tr><td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td></tr>
		</table>
	</div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	    <td width="20%">Dealer Id <br></td>
	    <td width="80%">VIN(s) (comma separated values)<br></td>
      </tr>
	  <tr>
	    <td><input type="text" name="dealerId"  id="dealerId" /></td>
	    <td><textarea name="vehicleId"  cols="45" rows="5" id="vehicleId"></textarea><span id="span1" class="serror"></span></td>
      </tr>
	  <tr>
	    <td>
	    	From Date
      	</td>
	    <td>
	    	To Date
	    </td>
      </tr>
	  <tr>
	    <td><sj:datepicker name="fromDate" id="fromDate"  maxDate="today" displayFormat="mm/dd/yy" showOn="focus"  /></td>
	    <td><sj:datepicker name="toDate"   id="toDate" maxDate="today"  displayFormat="mm/dd/yy" showOn="focus" /><span id="span2" class="serror"></span></td>
      </tr>
	  <tr>
	  <tr>
	    <td>
	    <s:submit key="submit" value="Generate Report" 	method="generateBlockVehicleReport" id="genReptB" />
        <s:reset type="reset" name="reset" id="reset" value="Reset" /></td>
         <td>&nbsp;</td>
        </tr>
    </table>
	<br/>
	
		<s:if test="%{blkList.isEmpty()}">
			<tr>
				<td colspan="7"><span>No Record Present.</span></td>
			</tr>
		</s:if>
		<s:else>
			<table width="957" height="90" border="1" summary="Blocked Vehicles - Home Office Report">
			  <caption class="pageTitle">Blocked Vehicles
		      </caption>
			  <tr>
			    <th width="5%" height="17" bgcolor="#999999" scope="row"><strong>Sno</strong></th>
			    <th width="15%" height="17" bgcolor="#999999" scope="row"><strong>Dealer Id</strong></th>
			    <td width="30%" bgcolor="#999999"><strong>Purchase Order</strong></td>
			    <td width="10%"  bgcolor="#999999"><strong>Block Date</strong></td>
			    <td width="50%"  bgcolor="#999999"><strong>Blocked reason</strong></td>
		      </tr>
			  <s:iterator value="blkList">
				<tr>
					<td width="5%">
						<s:property value="srNo"></s:property>
					</td>
					<td width="15%">
						<s:property value="idDealer"></s:property>
					</td>
					<td width="30%">
						<s:iterator value="poNo" var="poNoarr"><s:property value="#poNoarr"/><br/></s:iterator>
					</td>
					<td width="10%">
						<s:property value="displayDate"></s:property>
					</td>
					<td width="50%" Style="word-wrap:break-word;">
					<s:iterator value="reason" var="reasonarr"><s:property value="#reasonarr"/><br/></s:iterator>
					</td>
				</tr>
			</s:iterator>
			</table>
		</s:else>
	  
    
	<br /><br />
</div>
 
	</s:form>
</body>
</html>
<%}catch(Exception e){
e.printStackTrace();
}%>