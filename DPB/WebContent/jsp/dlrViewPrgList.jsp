<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>

<title>dealerPrgView</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
			<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/master.css"/>
			<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
			<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
			
			
			<script language="JavaScript" src="<%=request.getContextPath() %>/js/jquery-ui-1.9.2.datepicker.custom.min.js" type="text/javascript"></script>
			<script src="<%=request.getContextPath() %>/jQueryAssets/jquery-1.8.3.min.js" type="text/javascript"></script>
			<script type="text/javascript">
			
			function cancelFromDlrPrg(){
			alert('cancelFromDlrPrg');
			
			
			}
			</script>
			
			
			  <script type="text/javascript">
    
    </script>

</head>
<body> 
<form action="dpbProgramList" method="post">
<div id="widecontentarea">
<div class="pageTitle" id="HL1">
	DPB Program
</div>
 
<div class="T8">
	<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="<%=request.getContextPath() %>/images/img_t8_line1.gif" height="1" alt="" border="0" ></td>
			</tr>
			<tr>
				<td width="500"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Mercedes-Benz Dealer Performance Bonus Program, which was initially launched on January 3, 2008, has
				  been updated for 2013 and now provides all qualified authorized dealers in good standing an opportunity to
				  earn a performance bonus of up to 5.50% of Manufacturers Suggested Retail Price on eligible transactions.. </div></td>
			<td width="363"  class="right">
	<div align="left">
			<img id="ctl00_imgLevel2" src="<%=request.getContextPath() %>/resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
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
	
<div class="pageTitle" id="HL1">
	
	DPB Program</div>
 
	<div class="T8">
		<table width="728" border="0" cellspacing="0" class="template8TableTop">
			<tr>
				<td colspan="2" class="line"><img src="../../../images/img_t8_line1.gif" height="1" alt="" border="0"></td>
			</tr>
			<tr>
				<td width="362"  class="left" id="ctl00_tdCopyText"><div class="TX1">The Mercedes-Benz Dealer Performance Bonus Program, which was initially launched on January 3, 2008, has
				  been updated for 2013 and now provides all qualified authorized dealers in good standing an opportunity to
				  earn a performance bonus of up to 5.50% of Manufacturers Suggested Retail Price on eligible transactions.. </div></td>
 
				<td width="363"  class="right"><div align="left"><img id="ctl00_imgLevel2" src="../../../resources/13554/image_22643.jpg" style="border-width:0px;" /><br>
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
	  <td> Program Id</td>
	  <td>Program Name</td>
	  <td>Start Date</td>
	  <td> End Date</td>
	  <td> KPI </td>
	  <td> Applicable Vehicles</td>
	  <td>Program Status</td>
	  
	  </tr>
	  <tr>
	  	<td width="239"><s:property value="programId"></s:property></td>
		<td width="239"><s:property value="programName"></s:property></td>
		<td width="239"><s:property value="startDate"></s:property></td>
		<td width="239"><s:property value="endDate"></s:property></td>
		<td width="239"><s:property value="KPI" ></s:property></td>
		<td width="239"><s:property value="applicableVehicles" ></s:property></td>
		<td width="239"><s:property value="programStatus" ></s:property></td>
	  </tr>
    </table>
	<br /><br /><br />
</div>
 </form>

</body>
</html>