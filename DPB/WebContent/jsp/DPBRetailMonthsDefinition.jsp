
<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>DPBRetailMonthsDefinition</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/jQueryAssets/jquery-1.8.3.min.js" type="text/javascript"></script>
</head>
<body>
<s:form action="DPBRetailMonthsDef" method="post">

<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Retail Months Definition</div>
 
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The DPB application provides the option to define the retail month for the future months (current and future fiscal year).. </div></td>
 
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
            <tr><td colspan="2">	<div class="template8BottomLine"></div></td></tr>
		</table>
	</div>
	<table width="200" border="0" class="TBL2">
	  <tr>
	    <td>Fiscal Year</td>
	    <td><select name="select3" id="select3">
	      <option value="fy13">2013</option>
	      <option value="fy14">2014</option>
	      <option value="fy15">2015</option>
	      <option value="fy16">2016</option>
        </select></td>
      </tr>
	  <tr>
	    <td>Month</td>
	    <td><select name="select5" id="select5">
	      <option value="01">01</option>
	      <option value="02">02</option>
	      <option value="03">03</option>
	      <option value="04">04</option>
	      <option value="05">05</option>
	      <option value="06">06</option>
	      <option value="07">07</option>
	      <option value="08">08</option>
	      <option value="09">09</option>
	      <option value="10">10</option>
	      <option value="11">11</option>
	      <option value="12">12</option>
        </select></td>
      </tr>
	  <tr>
	    <td>Start Date</td>
	    <td><input type="text" id="Datepicker1"></td>
      </tr>
	  <tr>
	    <td>End Date</td>
	    <td><input type="text" id="Datepicker2"></td>
      </tr>
	  <tr>
	    <td>No. of days</td>
	    <td><input name="number" type="number" disabled id="number"></td>
      </tr>
	  <tr>
	    <td>Status</td>
	    <td><p>
	      <label>
	        <input type="radio" name="DPBStatus" value="D" id="DPBStatus_0">
	        Draft</label>
	      <label>
	        <input type="radio" name="DPBStatus" value="A" id="DPBStatus_1">
	        Active</label>
	      <label>
	        <input type="radio" name="DPBStatus" value="I" id="DPBStatus_2">
	        In-Active</label>
	      <br>
        </p></td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
	<input name="submit" type="button" id="submit" value="Submit">
	<input type="reset" name="reset" id="reset" value="Reset">
	<input type="button" name="button" id="button" value="Cancel">
<br /><br /><br />
</div>
</s:form>
</body>
 </html>