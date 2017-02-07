<!DOCTYPE HTML>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mbusa.dpb.common.db.ConnectionFactory"%>
<%@page import="com.mbusa.dpb.web.helper.WebHelper"%>
<%@page import="java.sql.Connection"%>
<%@page language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<html>
<head>
<title>Temporary SQL access to Test Server To Update</title>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
</head>
<body>

<P><B>Enter the SQL Query To Update</B></P>
<FORM METHOD="post">
<% String queryStr = request.getParameter("query");%>
<textarea NAME="query" rows="10" cols="100"><%=queryStr%>
</textarea>

<INPUT TYPE="submit" VALUE="Submit">

<% 
ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
Connection con = null;
ResultSet rs= null;
PreparedStatement st = null;
StringBuffer value = new StringBuffer();
String newQuery = WebHelper.makeNonNullString(queryStr);
			try {
			if(!newQuery.isEmpty()){
				if(!newQuery.toUpperCase().contains("DELETE")){
					con = conFactory.getConnection();
					st = con.prepareStatement(newQuery);
					st.executeUpdate();
					%>
			<H1>Record(s) Updated Successfully</H1>
			<%

			}
			
			}
			if(newQuery.toUpperCase().contains("DELETE")){
			%>
			<H1>No delete allowed</H1>
			<%
			}
			}catch(SQLException  e){
				%><%=e.getNextException()%><%
			}finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (st != null) {
						st.close();
					}
					if (con != null) {
						con.close();
					}
				} catch (SQLException e) {
					%><%=e.getNextException()%><%
				}
			}					
 %>



</FORM>
</body>
</html>