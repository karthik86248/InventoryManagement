<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Material Tracking System</title>
</head>
<body>
My Material  Tracking System <br> <br>

<br>INDEX <br>
<form NAME="index1" action="${pageContext.request.contextPath}/About" method="get">
 <INPUT TYPE="submit" NAME="About" VALUE="About">
</form>

<form NAME="index2" action="${pageContext.request.contextPath}/Materials" method="get">
 <INPUT TYPE="submit" NAME="Update Materials" VALUE="Materials">
</form>


<br>
</body>
</html>