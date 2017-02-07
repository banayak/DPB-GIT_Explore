<!DOCTYPE HTML>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.mbusa.dpb.common.db.ConnectionFactory"%>
<%@page import="com.mbusa.dpb.web.helper.WebHelper"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.Date"%>
<%@page language="java"
	contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<html>
<head>
<title>Temporary SQL access to Test Server  with Execution time</title>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
</head>
<body>

<P><B>Enter the SQL Query</B></P>
<FORM METHOD="post">
<% String queryStr = request.getParameter("query");%>
<textarea NAME="query" rows="10" cols="100"><%=queryStr%>
</textarea>

<INPUT TYPE="submit" VALUE="Submit">

<% 
ConnectionFactory conFactory = ConnectionFactory.getConnectionFactory();
Connection con = null;
ResultSet rs= null;
Statement st = null;
StringBuffer value = new StringBuffer();
String newQuery = WebHelper.makeNonNullString(queryStr);
java.util.Date beginUpdate = null;
java.util.Date endUpdate = null;
long updateDuration = 0;

			try {
			if(!newQuery.isEmpty()){
				if(!newQuery.toUpperCase().contains("DELETE")){
				if(!newQuery.toUpperCase().contains("INSERT")){
				if(!newQuery.toUpperCase().contains("UPDATE")){ 
					con = conFactory.getConnection();
					st = con.createStatement();
					beginUpdate = new Date();
rs= st.executeQuery(newQuery);
endUpdate = new Date();
updateDuration = endUpdate.getTime() - beginUpdate.getTime();                                  
					int colCount = rs.getMetaData().getColumnCount();
					ResultSetMetaData rsmd = rs.getMetaData();
%>
<h2>SQL Execution time is : <%=updateDuration%> milli seconds.</h2>^M

<table border="1">
    <tr>
    
    <%
     for (int i = 1; i <= colCount; i++) {
   		%>
   		<th> 
   				<%=rsmd.getColumnName(i)%> 
   		</th>
     <%
	  }
     %>
    </tr>
			<%					
					while(rs.next()){
%>     <tr>
<%
					   for (int i = 1; i <= colCount; i++) {
			%>					   
        	<td>
         				 <%=rs.getString(i)%>
        	</td>
	
<%    				   
						}
%>      </tr>
<%    				
					}
%>
</table>	
<%			
			}
			
			}
			}
			}
			if(newQuery.toUpperCase().contains("DELETE") || newQuery.toUpperCase().contains("INSERT") || (newQuery.toUpperCase().contains("UPDATE"))){
			%>
			<H1>No delete/insert/update operation is allowed</H1>
			<%
			}
			}catch(SQLException  e){
				%><%e.getNextException();%><%
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
				%><%e.getNextException();%><%
				}
			}					
 %>

<h2>SQL Execution time is : <%=updateDuration%> milli seconds.</h2>

</FORM>
</body>
</html>
