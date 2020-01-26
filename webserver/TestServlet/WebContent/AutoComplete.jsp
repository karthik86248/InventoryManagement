<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="mypackage.CatalogDB"%>
<% 
CatalogDB obj = new CatalogDB();
String query = request.getParameter("q");
List<String> entries = obj.DoQuery(query);
Iterator<String> iterator = entries.iterator();
while(iterator.hasNext()) {
	String entry = (String)iterator.next();
	out.println(entry);
}
%>
