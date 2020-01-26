<%@ page language="java" import="java.sql.*" %>
<% response.setContentType("text/html");%>
<%
String str=request.getParameter("queryString");
try {

out.println("<li onclick='fill("+"HELLO"+");'>"
+ "HELLO"+"</i>");
}catch(Exception e){
out.println("Exception is ;"+e);
}
%>