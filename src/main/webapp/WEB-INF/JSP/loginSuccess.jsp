<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<sec:authorize access="isAuthenticated()">
		<sec:authorize access="hasAnyAuthority('ADMIN','EDITOR')">
			<sec:authentication property="name"/><sec:authentication property="principal.authorities"/>
				<span>Hello <b><sec:authentication property="name"/></b></span>
			
		</sec:authorize>
		<sec:authorize access="!hasAnyAuthority('ADMIN','EDITOR')">
		 	<span>Sorry <b><sec:authentication property="name"/></b> You are not allowed to view this page</span>
		</sec:authorize>
	</sec:authorize>
	
	
	<a href="${pageContext.request.contextPath}/logout">Logout</a>
	
</body>
</html>