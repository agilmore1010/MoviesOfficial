<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- For JSTL Forms -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Movie Hub</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
</head>
<body>
	<h1 class="text-center">Welcome!</h1>
	<h2 class="text-center">Let's add some movies!</h2>
	<div class="container mt-5">

		<div class="card mb-4">
			<div class="card-header text-center">
				<h4 class="mb-0">Registration</h4>
			</div>
			<div class="card-body">
				<form:form action="/register/user" method="post" modelAttribute="newUser">
					<table class="table">
						<tr>
							<td><form:label path="firstName">First Name:</form:label></td>
							<td><form:input class="form-control" path="firstName" /> 
							    <form:errors class="text-danger" path="firstName" /></td>
						</tr>
						<tr>
							<td><form:label path="lastName">Last Name:</form:label></td>
							<td><form:input class="form-control" path="lastName" /> 
							    <form:errors class="text-danger" path="lastName" /></td>
						</tr>
						<tr>
							<td><form:label path="email">Email:</form:label></td>
							<td><form:input class="form-control" path="email" /> 
							    <form:errors class="text-danger" path="email" /></td>
						</tr>
						<tr>
							<td><form:label path="birthdate">Birthdate:</form:label></td>
							<td><form:input path="birthdate" type="date" class="form-control"/> 
							    <form:errors class="text-danger" path="birthdate" /></td>
						</tr>
						<tr>
							<td><form:label path="password">Password:</form:label></td>
							<td><form:input type="password" class="form-control" path="password" /> 
							    <form:errors class="text-danger" path="password" /></td>
						</tr>
						<tr>
							<td><form:label path="confirm">Confirm Password:</form:label></td>
							<td><form:input type="password" class="form-control" path="confirm" /> 
							    <form:errors class="text-danger" path="confirm" /></td>
						</tr>
					</table>
					<div class="d-flex text-center">
						<button type="submit" class="btn btn-primary w-100">Register</button>
					</div>
				</form:form>
			</div>
		</div>

		<div class="card">
			<div class="card-header text-center">
				<h4 class="mb-0">Login</h4>
			</div>
			<div class="card-body">
				<form:form action="/login" method="post" modelAttribute="newLogin">
					<table class="table">
						<tr>
							<td><form:label path="email">Email:</form:label></td>
							<td><form:input class="form-control" path="email" /> 
							    <form:errors class="text-danger" path="email" /></td>
						</tr>
						<tr>
							<td><form:label path="password">Password:</form:label></td>
							<td><form:input type="password" class="form-control" path="password" /> 
							    <form:errors class="text-danger" path="password" /></td>
						</tr>
					</table>
					<div class="d-flex text-center">
						<button type="submit" class="btn btn-primary w-100">Login</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
