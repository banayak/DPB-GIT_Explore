<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set name="theme" value="simple" scope="page" />
<html>
<head>
<meta http-equiv="refresh" content="5;url=<s:url includeParams="all" />"/> 
<script type="text/javascript">
window.onbeforeunload = function(e) {
 var urlVar = document.getElementById("url");
 window.location.assign(urlVar);
};
</script>
</head>
<body>
<H1>
Please wait while we process your request.<br>
</H1>
<h2>
Please press Refresh only if this page stays for too long! Don't close the browser or navigate away from this page. 
</h2>

</body>
<input id="url" type="hidden" value = "<s:url includeParams="all" />"/>
</html>
