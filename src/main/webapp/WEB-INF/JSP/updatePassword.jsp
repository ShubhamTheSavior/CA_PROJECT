<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

</head>
<body>

	<form:form action="${pageContext.request.contextPath}/user/savePassword" method="post" modelAttribute="passwordDto">
		
		<h3>Update Password</h3>
		<br>
		<label>Password :</label>
		<form:password path="newPassword"/>   
		<form:errors path="newPassword" cssClass="error"></form:errors>    
		<br>
		<label>Confirm Password :</label>
		<input id="matchPassword" type="password" value="" />
		<br>
		<label>Token</label>
		<input id="token" name="token" value="${token}" />
		
		
		<button type="submit">Password</button>
		
	</form:form>

</body>
</html>