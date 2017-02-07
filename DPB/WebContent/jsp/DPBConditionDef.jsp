<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>ConditionDefination</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			<script src="<%=request.getContextPath() %>/jQueryAssets/jquery-1.8.3.min.js" type="text/javascript"></script>
</head>
<body>
<s:form action="conditionDefination" method="post">

<div id="widecontentarea">
	
<div class="pageTitle" id="HL1">
	
	DPB Condition</div>
 
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The conditions for the Dealer Performance Bonus (DPB) special programs, Vehicle type check, DPB program block check.</div></td>
 
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
	    <td>Condition  ID</td>
	    <td>&nbsp;</td>
      </tr>
	  <tr>
	    <td>Condition Name</td>
	    <td><input type="text" name="textfield2" id="textfield2"></td>
      </tr>
	  <tr>
	    <td>Description</td>
	    <td><textarea name="textarea" id="textarea" cols="45" rows="5"></textarea></td>
      </tr>
	  <tr>
	    <td>Condition</td>
	    <td><select name="select3" id="select3">
	      <option value="CHK_EQL">CHECK EQUAL</option>
	      <option value="CHK_GT">CHECK GREATER THAN</option>
	      <option value="CHK_GE">CHECK GREATER THAN AND EQUAL TO</option>
	      <option>CHECK LESS THAN</option>
	      <option>CHECK LESS THAN AND EQUAL TO</option>
	      <option>CHECK LEAD CHAR</option>
	      <option>CHECK TRIAL CHAR</option>
	      <option>CHECK YES</option>
	      <option>CHECK NO</option>
	      <option>CHECK FORMAT</option>
        </select></td>
      </tr>
	  <tr>
	    <td>Variable Name (Check to)</td>
	    <td><input type="text" name="textfield" id="textfield"></td>
      </tr>
	  <tr>
	    <td>Check Value</td>
	    <td><input type="text" name="textfield" id="textfield"></td>
      </tr>
	  <tr>
	    <td>Condition Type</td>
	    <td><select name="select2" id="select2">
	      <option value="B">Block</option>
	      <option value="S">Special Program</option>
	      <option value="V">Vehicle Type</option>
        </select></td>
      </tr>
	  <tr>
	    <td>Format String (Regular Exp)</td>
	    <td><input type="text" name="textfield3" id="textfield3"></td>
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