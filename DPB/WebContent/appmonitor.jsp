<%@ page import="javax.servlet.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>

<%
	HttpSession currentSession = request.getSession(true);
	String sessionID = "session is null";
	
	if (currentSession != null){
		sessionID = currentSession.getId();
	}
	String currentTime = (new Date()).toString();

	String ServerID = InetAddress.getLocalHost().toString();
%>
<html>
<title>
Application Server Monitor
</title>
<head>
<b>Application Server Monitor</b>
</head>
<body bgcolor="#ffffff">

<div size="2"><b>

Current server time: <%=currentTime%></br>
<%--
Current session ID: <%=sessionID%></br>
Server ID/IP: <%=ServerID%></br>
--%>
</div>
<hr>

</body>
</html>
