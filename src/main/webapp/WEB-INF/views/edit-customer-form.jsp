<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page isELIgnored="false" %> <!-- hiển thị giá trị của ${customer.id }, ${customer.firstName }...  -->
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Form</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/styles.css">
<script src="${contextPath}/js/script.js"></script>
</head>

<body>
	<h1>Customer Management</h1>
	<hr/>
	<h3>
		<a href="${contextPath}/customer/new">Add New Customer</a>
		<a href="${contextPath}/customer/list">List All Customers</a>
	</h3>
	<hr/>
	
	<c:url var="updateAction" value="/customer/update" ></c:url>
	<!-- dùng để chỉ định url sẽ gửi tới controller =>ko dùng url mặc định đc tạo ra của form  -->
	<div class="customers-form">
		<form:form action="${updateAction}" method="post" modelAttribute="customer">
			<h2>Edit Customer</h2>
			<table class="list" id="customerList">
				<tr>
					<td><form:hidden path="id"/></td> <!-- để form:hidden để ko cho sửa id và sẽ truyền được id xuống Controller -->
				</tr>
				<tr>
					<th>First Name:</th>
					<td><form:input path="firstName"/></td>
				</tr>
				<tr>
					<th>Last Name:</th>
					<td><form:input path="lastName"/></td>
				</tr>
				<tr>
					<th>Email:</th>
					<td><form:input path="email"/></td>
				</tr>
			</table>
			<div class="form-action-buttons">
				<input type="submit" value="Submit">
			</div>
		</form:form>
		
		<!-- 
		<form action="update" method="post">
			<h2>Edit Customer</h2>
			<table>
				<tr>
					<td><input type="hidden" name="id"
						value="<c:out value='${customer.id }'></c:out>"></td>
				</tr>
				<tr>
					<th>First Name:</th>
					<td><input type="text" name="firstName"
						value="<c:out value='${customer.firstName }'></c:out>"></td>
				</tr>
				<tr>
					<th>Last Name:</th>
					<td><input type="text" name="lastName"
						value="<c:out value='${customer.lastName }'></c:out>"></td>
				</tr>
				<tr>
					<th>Email:</th>
					<td><input type="text" name="email"
						value="<c:out value='${customer.email }'></c:out>"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						name="Save"></td>
				</tr>
			</table>
		</form>
		 -->
	</div>
</body>
</html>
