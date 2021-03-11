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
	
	<form:form action="${pageContext.request.contextPath}/user/updatePassword" 
		method="post" modelAttribute="passwordDto">
		
		<h3>Update Password</h3>
		<br>
		<label>Old Password :</label>
		<form:input path="oldPassword"/>
		<br>
		<label>New Password :</label>
		<form:input path="newPassword"/>
	    <br>
	    <label>Confirm Password :</label>
	    <input id="passConfirm" type="password" />              
	    <span id="error" style="display:none">Password mismatch</span>
	    <br>
	    <button type="submit">Change Password</button>
	
	</form:form>
	
	
   
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</body>
</html>