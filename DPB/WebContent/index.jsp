<%@page import="com.mbusa.dpb.common.props.PropertyManager"%>
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%@ page import="java.net.URLDecoder"%>

<%
	String pagePath=request.getContextPath();
	String schema = request.getScheme();
	String serverName = request.getServerName();
	String serverPort = ""+request.getServerPort();
	String servletPath = request.getServletPath();
	String contextPath = request.getContextPath();
	String baseURL = schema + "://" + serverName + ":" + serverPort + contextPath;
	String dpbUserObject = request.getParameter("UserObject") ;
	String userid = "";
	    String costCtr ="";
		String name  ="";
		String fname="";
		String lname ="";
		String URLString = "";
	PropertyManager PROPERTY_MANAGER = PropertyManager.getPropertyManager();
		boolean isDevelopment = PROPERTY_MANAGER.getPropertyAsBoolean("dpb.development");
	if(isDevelopment) 	{
//response.sendRedirect("http://localhost:9083/DPB/login.action");
	response.sendRedirect("http://mbhobgnapp801.americas.bg.corpintra.net:9080/DPB/index.jsp");
	}
	else {
	if (null != dpbUserObject && !dpbUserObject.trim().equals(""))
	{
		String xmlUserObject = URLDecoder.decode(dpbUserObject); 
		
		if  (xmlUserObject!=null && xmlUserObject.length()>0 )
		{
			userid = getTagValueFromXMLString(xmlUserObject,"UserID");
			costCtr = getTagValueFromXMLString(xmlUserObject,"CostCenter");
			name = getTagValueFromXMLString(xmlUserObject,"Name");
			String [] names = name.split(" ");
			fname = names[0];
			lname = names[1];
			URLString = "?UserID=" + userid + "&CostCenter=" + costCtr + "&FName=" +names[0] + "&LName=" +names[1];
		}
		
		session.setAttribute("UserID",userid);
		session.setAttribute("CostCenter",costCtr);
		session.setAttribute("FName",fname);
		session.setAttribute("Lname",lname);
		session.setAttribute("xmlUserObject",xmlUserObject);
	
		response.sendRedirect(baseURL +"/login.action");
	}
	else
	{
		//String ssoURLPage = MBPropertiesReader.getPropertyValue("ssoURL");
        //String ssoURLPage = "http://dev-connect.e.corpintra.net/pages/MyPage?site=http%3A%2F%2F"+serverName+"%3A9080%2FDPB%2Fjsp%2Findex.jsp" ;
        //String ssoURLPage = "http://dev-connect.e.corpintra.net/pages/MyPage?site=http%3A%2F%2F"+serverName+"%3A"+serverPort+"%2FDPB%2Fjsp%2Findex.jsp" ;
       // Current prod/QA/Test login URL
       // String ssoURLPage = "http://connect.e.corpintra.net/pages/MyPage?site=http%3A%2F%2F"+serverName+"%3A"+serverPort+"%2FDPB%2Fjsp%2Findex.jsp" ;
        // New QA/Test login URL
        //String ssoURLPage = "https://mbportal-qa.e.corpintra.net/pages/MyPage?site=http%3A%2F%2F"+serverName+"%3A"+serverPort+"%2FDPB%2Fjsp%2Findex.jsp" ;
		// New PROD login URL
		String ssoURLPage = "https://mbportal.e.corpintra.net/pages/MyPage?site=http%3A%2F%2F"+serverName+"%3A"+serverPort+"%2FDPB%2Fjsp%2Findex.jsp";
		
	// RequestDispatcher rd = getServletContext().getRequestDispatcher(pagePath+"/login.jsp");
	// rd.forward(request,response);
	response.sendRedirect(ssoURLPage);
	}
	}

%>
	

<%!

	private String getTagValueFromXMLString(String input, String tagName)  throws Exception 
	{
				String BEGIN_TAG = "<" + tagName + ">";
				String END_TAG = "</" + tagName + ">";
				String tagValue = "";
				int bind = input.lastIndexOf(BEGIN_TAG);
				int eind = input.lastIndexOf(END_TAG);
				if (bind > -1)
					tagValue = input.substring(bind + BEGIN_TAG.length(), eind);
				
				return tagValue;
	}
%>

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>DBP</TITLE>
</HEAD>
<BODY>
<input type="hidden" name="UserID" value="<%=userid%>">   
<input type="hidden" name="CostCenter" value="<%=costCtr%>">  
<input type="hidden" name="LName" value="<%=lname%>">  
<input type="hidden" name="FName" value="<%=fname%>"> 
PLEASE CHECK AFTER SOMETIME AS THE SYSTEM IS DOWN FOR MAINTENANCE.
<BR>
THANK YOU FOR YOUR PATIENCE.
</BODY>
</HTML>
