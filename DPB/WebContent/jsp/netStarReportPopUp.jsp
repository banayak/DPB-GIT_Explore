
<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js"></script>
<script>
$(document).ready(function() {
$("#refreshParent").click(function(){
//alert("");
	window.close();
	window.opener.location.reload();
    // 5000 to load it after 5 seconds from page load
});
$("#closeWindow").click(function(){
//alert("");
	window.close(); 
});
});

</script> 
<style type="text/css" media="screen">
#headline1 {
POSITION: absolute;
}
</style>
<s:form>
<s:token/>
<% if(request.getAttribute("sessionExpire") != null && "Y".equalsIgnoreCase((String)request.getAttribute("sessionExpire")) ){%>
<h2>Session expired! Please close the window and login again.</h2>
	<s:submit key="Close Window" name="Close Window" value ="Close Window"  id="refreshParent" align="left"/>
	<%}else{ %>
<div id="printHideBr">
			<div class="loginLabel" id="HL9p"><span class="Header1">Report View</span></div>
			<s:if test='strBuffer.toString().trim().equals("")'>
					<br><br><div id="headline1"><h3>No Record Found.</h3></div><br><br><br>
					<s:submit key="Close Window" name="Close Window" value ="Close Window" align="left" id="closeWindow"></s:submit>
			</s:if>
			<s:elseif test='strBuffer.toString() == null'>
					<br><br><div id="headline1"><h3>No Record Found.</h3></div><br><br><br>
					<s:submit key="Close Window" name="Close Window" value ="Close Window" align="left" id="closeWindow"></s:submit>
			</s:elseif>
</div>	
			<s:else>
			<div id="headline1"><h3 id="printHideBr">Note: For Monthly Reports, please adjust the Print format to Landscape and change Print size to Shrink to Fit</h3></div> 
			<br><br id="printHideBr"><div class="loginLabel" id="HL9p"><input type="button" id="printpagebutton" name="Print" value="Print" onClick="javascript:onClickPrint()"/></div>
			<br id="printHideBr">
				<div id="printable">
			 	<pre><font size="2" style="font-family:Courier New;"><s:property escape="false" value="strBuffer"></s:property></font></pre>
			 	</div>
			</s:else>
			<%} %>
</s:form>
	<script type="text/javascript">
		function onClickPrint(){
			window.print();
        }
	</script>