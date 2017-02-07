<!DOCTYPE HTML>
<%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="simple" scope="page" />
<html>
	<script src="<%=request.getContextPath() %>/js/jquery-1.4.3.min.js" type="text/javascript"></script>
<head>
<style type="text/css" media="screen">

#headline1 {
POSITION: absolute; TOP: 35%; LEFT: 40%;

}
#headline2 {
POSITION: absolute; TOP: 25%; LEFT: 33%;

}
</style>
<% int refSecsVal = 0, reqSecsVal =0; 
 if(session.getAttribute("refSecs") != null && session.getAttribute("reqSecs") != null){
 	refSecsVal = Integer.parseInt(String.valueOf(session.getAttribute("refSecs")));
 	reqSecsVal = Integer.parseInt(String.valueOf(session.getAttribute("reqSecs"))); 
}
%>
<meta http-equiv="refresh" content="<%= refSecsVal%>;url=<s:url includeParams="all" />"/>

<%-- <script type="text/javascript">
var remSecs = <%= reqSecsVal %>;
var mins = 0;
var secs = 0;
var timer = setInterval(function(){
	if(remSecs > 0){
		remSecs = remSecs -1;
		mins = remSecs/60;
		secs = remSecs%60;
		mins = Math.floor(mins);
		document.getElementById("mins").innerHTML = mins;
		document.getElementById("secs").innerHTML = secs;
	}else{
		document.getElementById("time").innerHTML = 'Something is taking longer than expected. Please wait while we are recalculating the time.';
	}
},1000);

window.onbeforeunload = function(e) {
 	clearInterval(timer);
};
</script> --%>
<style>
	.time{
		color = red;
	}
</style>
</head>
<body>
<br><br><br><br><br>
<div id="headline2"><H2>&nbsp;Please wait while we are generating the report</H2>
<br><br><br><br><br></div>
<div id="headline1"><img src="<%=request.getContextPath() %>/img/loadingblack.gif" width="30%" height="30%" alt="top" ></div>

<%-- <h2>
We will render the report as soon as it is generated, so please don't refresh the page!<br>
</h2>
<% if(session.getAttribute("rcount") != null){
	int recCnt = Integer.parseInt(String.valueOf(session.getAttribute("rcount")));
	if(recCnt > 0){
%>
<h2>We are processing vehicles for <%= recCnt %> dealers. This may take some time!</h2>
<h3 id = "time">Your maximum waiting time is: <span id="mins"><%= reqSecsVal/60 %></span> minutes and <span id="secs"><%= reqSecsVal%60 %></span> seconds.</h3>
<% 	
}else{
%>
<h2>We are calculating the time required to generate the report....</h2>
<%
}
}
%>
  --%>
</body>
</html>