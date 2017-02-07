<!DOCTYPE HTML><%@page language="java" pageEncoding="ISO-8859-1"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set name="theme" value="'simple'" scope="page" />
<%@page import ="java.lang.Exception" %>
<%@ page isErrorPage="true" import="java.io.*" %>
<%@page import="com.mbusa.dpb.common.exceptions.DPBExceptionHandler"%>

<html>
	<head>
		<title>DPB Error Page</title>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="<%=request.getContextPath() %>/css/master.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath() %>/css/admin.css" type="text/css" rel="stylesheet"/>
		<script>
			var expand = false;
			function srt_showError(){
				if(expand == false){
					document.getElementById("info").style.display="block";
					expand = true;
				}else{
					document.getElementById("info").style.display="none";
					expand = false;	
				}
			}
		</script>
	</head>
	<body>
	<s:form>
		<h2>
			<s:text name="dpb.technical.exception" />
			<br/>
			<br/>
			<a href="#" onclick="srt_showError();" ><s:text name="dpb.exception.info" /></a> <br/>
			<div style="display:none" id="info">
				ID: <%= DPBExceptionHandler.getInstance().getExceptionKey((Exception)exception)%><BR/>
				Message: <%=  exception.getMessage()%> <br/>
			</div>
		</h2>
		<br/>
		<br/>
		<a href="<%=request.getContextPath()%>/dashBoard">Home Page</a>
	</s:form>	
	</body>
</html>