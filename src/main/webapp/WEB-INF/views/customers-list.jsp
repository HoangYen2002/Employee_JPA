<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %> <!-- hiển thị giá trị của ${customer.id }, ${customer.firstName }...  -->
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer List</title>
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
	
	<div class="customers-table">
		<table class="list" id="customerList">
			<tr>
				<th>ID</th>
				<th>FIRST NAME</th>
				<th>LAST NAME</th>
				<th>EMAIL</th>
				<th>ACTION</th>
			</tr>
			
			<c:forEach var="c" items="${customers}">
				<c:if test="${!empty customers}"></c:if> <!-- kiểm tra list rỗng -->
				<tr>
					<td>${c.id}</td>
					<td>${c.firstName}</td>
					<td>${c.lastName}</td>
					<td>${c.email}</td>
					<td colspan="2">
						<!-- <a href="${contextPath}/customer/edit?customerId=<c:out value='${c.id}'/>">Edit</a> --> <!-- khai báo @RequestParam("customerId") bên controller để bắt customerId -->
						<a href="${contextPath}/customer/edit/${c.id}">Edit</a> <!-- khai báo @PathVariable bên controller để bắt đc id -->
						<a href="${contextPath}/customer/delete?customerId=${c.id}">Delete</a>
						<a href="<c:url value='/customer/edit/${c.id}'/>">Edit</a> <!-- khai báo @PathVariable bên controller để bắt đc id -->
					</td>	
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>