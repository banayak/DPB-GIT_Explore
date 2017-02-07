<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*,java.io.*"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.mbusa.dpb.web.helper.IWebConstants"%>
<html>
<head>
	<script language="JavaScript" src="<%=request.getContextPath() %>/js/jquery-1.12.0.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/js/jquery.popupwindow.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/js/retailCalendar.js" type="text/javascript"></script>
</head>
<body>
	<div id="headerareaframe">
		<div id="brandmark">
			<img src="<%=request.getContextPath()%>/img/mb_logo.gif" width="200"
				height="50" alt="Mercedes-Benz." border="0" />
		</div>
		<div id="headerarea">

			<div id="nav1">
				<ul>
					<li>
						<a href="#" class="center-screen">Retail Calendar</a>
						<s:a action="login" method="logout">Log out</s:a>
					</li>
					<!-- <li>
						<s:a action="dashBoard" >Home Dealer Performance Bonus</s:a>
					</li> -->
				</ul>
			</div>

			<div id="globalNav">
				<div id="ddmenu">
					<%=session.getAttribute(IWebConstants.TREE_DISPLAY)%>
				</div>
			</div>
			<div id="user" >
			<ul>
			<li>
			<%=session.getAttribute(IWebConstants.SM_USER_SESSION)%>
			</li>
			</ul>
			</div>
		</div>
	</div>
</body>
</html>