<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page isELIgnored="false" %> <!-- hiển thị giá trị của ${customer.id }, ${customer.firstName }...  -->
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <!-- khai báo biến toàn cục sử dụng chung cho file này -->

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
	
	<div class="customers-form">
	    <!-- phải truyền model.addAttribute("customer", customer) từ controller qua mới ko báo lỗi khi sử dụng modelAttribute trong form -->
		<!-- khi sử dụng form:form thì bắt buộc khai báo thuộc tính modelAttribute và bên controller phải khai báo @ModelAttribute("customer")
		có tên giống nhau thì mới có thể lấy đc tất cả thuộc tính có trong form này-->
		<!-- form:form ko giống như form bình thường để có thể gửi từng param =>nếu xài @RequestParam sẽ báo lỗi  -->
		<form:form action="insert" method="post" modelAttribute="customer"> <!-- tên phải giống @ModelAttribute("customer") -->
			<h2>Add New Customer</h2>
			<table class="list" id="customerList">
				<tr>
					<th>First Name:</th>
					<td><form:input path="firstName"/></td> <!-- tên phải giống tên thuộc tính firstName của Customer -->
					<td><form:errors path="firstName" cssClass="error"/></td>
				</tr>
				<tr>
					<th>Last Name:</th>
					<td><form:input path="lastName"/></td> <!-- tên phải giống tên thuộc tính lastName của Customer -->
					<td><form:errors path="lastName" cssClass="error"/></td>
				</tr>
				<tr>
					<th>Email:</th>
					<td><form:input path="email"/></td> <!-- tên phải giống tên thuộc tính email của Customer -->
					<td><form:errors path="email" cssClass="error"/></td>
				</tr>
			</table>
			<div class="form-action-buttons">
				<input type="submit" value="Submit">
			</div>
		</form:form>
		
		<!-- /customer/insert?firstName=Tom&lastName=Cat&email=tomCat@gmail.com -->
		<!-- 
		<form action="insert" method="post">
			<h2>Add New Person</h2>
			<table>
				<tr>
					<th>First Name:</th>
					<td><input type="text" name="firstName"></td>
				</tr>
				<tr>
					<th>Last Name:</th>
					<td><input type="text" name="lastName"></td>
				</tr>
				<tr>
					<th>Email:</th>
					<td><input type="text" name="email"></td>
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