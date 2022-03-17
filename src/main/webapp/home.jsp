<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EBook Store - Home</title>
</head>
<body>
<div>
	<c:choose>
		<c:when test="${logged == 'true'}">
			${currentUser.getEmail()}
		</c:when>
		<c:otherwise>
			Register nigger!
		</c:otherwise>
	</c:choose>
</div>

<div>
	<c:forEach items="${risposta.getObject()}" var="book">
		${book.getTitolo()} <br>
		${book.getAutore()} <br>
		${book.getPrezzo()} <br>
		<br><br>
	</c:forEach>
</div>

</body>
</html>