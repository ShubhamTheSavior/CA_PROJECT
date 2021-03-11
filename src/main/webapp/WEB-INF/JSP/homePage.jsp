<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<form:form action="${pageContext.request.contextPath}/login" method="post" modelAttribute="loginForm">
		
		<label>name:</label>
		<form:input path="username"/>
		<br>
		<label>password:</label>
		<form:password path="password"/>
		<br>
		<label>Remember me on this Computer:</label>
		<input type="checkbox" name="remember-me">
		<button type="submit">Login</button>
		
	</form:form>
	
	<form:form action="${pageContext.request.contextPath}/user/resetPassword" method="post" modelAttribute="loginForm">
		
		<h3>Forgot Password</h3>
		<br>
		<label>User name :</label>
		<form:input path="username"/>
		
		<button type="submit">Password</button>
		
		<c:if test="${expired}">
			<h3>Your Verification link has been expired, please click 
			<button onclick="resendToken('${token}')">Here</button> to resend</h3>
		</c:if>
	
	</form:form>
	
	<script type="text/javascript">
	
		
		function resendToken(token){
		
			$.ajax({
				type : "GET",
				 url : "user/resendRegistrationToken",
				data : {token:token},
			 success : function(response){
				 alert(response);
			 }
			});
		}
	</script>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	
</body>
</html>