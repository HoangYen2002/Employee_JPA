<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
	<c:if test="${param.error == 'true'}">
		<div><h1>Incorrect username123 and password123</h1></div>
	</c:if>
	<h3>Login with Username123 and Password123</h3>
	<c:url var="loginUrl" value="/j_spring_security_check"></c:url>
	<form action="${loginUrl}" method="POST">
		<table>
			<tr>
				<td>User ID:</td>
				<td><input type='text' name='username123' /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password123' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
		</table>
	</form>
</body>
</html>