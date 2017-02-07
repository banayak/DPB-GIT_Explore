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

</body>
</html>