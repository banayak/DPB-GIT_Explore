 <%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
 
 <s:set name="theme" value="'simple'" scope="page" />
 <html>
<head>
<title>DPBRetailMonthsDefinition</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js">
	</script>
	<script language="JavaScript" src="<%=request.getContextPath() %>/js/dpb.js" type="text/javascript"></script>
	
<sj:head jquerytheme="flick"/>
</head>
<body>
<s:form action="rtlMonthDef" >

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
	    <td> <s:select name="rtlMonthDefForm.yearSelection" id="yearSelection" onchange=""
         	list="%{#{'2013':'2013','2014':'2014','2015':'2015','2016':'2016'}}">
      		</s:select>  
		 </td>
      </tr>
	  <tr>
	    <td>Month</td>
	    <td><s:select name="rtlMonthDefForm.monthSelection" id="monthSelection"
         list="%{#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12'}}">
      </s:select>

       </td>
      </tr>
	  <tr> 
	    <td>Start Date</td>
	    <td><s:textfield name="rtlMonthDefForm.startDate" id="startDate" readOnly="true" />
	    </td>
      </tr>
	  <tr>
	    <td>End Date</td>
	    <td><sj:datepicker name="rtlMonthDefForm.endDate" minDate="%{rtlMonthDefForm.dateCounter}" displayFormat="mm-dd-yy" required="true" id="endDate"   onChange="populateNOFdays();"  showOn="focus"  validate="true"/>
	    <%-- <sx:datetimepicker  name="rtlMonthDefForm.endDate" id="Datepicker2" displayFormat="MM-dd-yy" required="true" /> --%>
	    </td>
      </tr>
	  <tr>
	    <td>No. of days</td>
	  	<td> <s:textfield name="rtlMonthDefForm.noOfDays" id="numberOfDays"  readOnly="true"/> </td>
      </tr>
	  <tr>
	    <td>Status</td>
	    <td><p>      	
	        <s:radio name="rtlMonthDefForm.status"  id="status" list="#{'1':'Draft','2':'Active','3':'Inactive'}"   />
	      <br>
        </p></td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
    </table>
    <table>
	<tr>
		<td><s:submit key="Save" method="saveRetailMonthDef" align="right" /></td>
		<td><s:reset key="Reset" align="center" /></td>
		<td><s:submit value="Cancel" name="Cancel" onclick="cancel(this)" /></td>   
	</tr>
</table>
<br /><br /><br />
</div>
</s:form>
</body>
 </html>