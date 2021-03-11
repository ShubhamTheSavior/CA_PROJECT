<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration Page</title>
</head>
<body>
	<form:form action="${pageContext.request.contextPath}/user/registration" method="POST" modelAttribute="user">
		<div>
			<label>Username</label>
			<form:input path="username"/>
			<form:errors path="username" cssClass="error"></form:errors>
		</div>
		
		<div>
			<label>Password</label>
			<form:password path="password"/>
			<form:errors path="password" cssClass="error"></form:errors>
		</div>
		
		<div>
			<label>Confirm Password</label>
			<form:password path="matchingPassword"/>
			<form:errors path="matchingPassword" cssClass="error"></form:errors>
		</div>
		
		    <button type="submit">submit</button>
		
	</form:form>
</body>
</html>