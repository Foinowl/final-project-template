<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="../../css/auth.css" />
		<title>Login</title>
	</head>
	<body>
		<div class="auth">
			<h1 class="auth__title">Login</h1>
			<p class="auth__text">No have an account? <a href="/registration">Sign up</a></p>
			<form:form class="auth__form" action="login/process" method="post" modelAttribute="authorizationForm">
				<div class="line__input">
					<form:input path="login" id="login" class="input__block" type="text" required="true" />
					<label for="login">Login</label>

					<span></span>
				</div>
				<div class="line__input">
					<form:input path="password" id="password" class="input__block" type="password" required="true" />
					<label for="password">Password</label>
					<form:errors path="password"></form:errors>
					<span>${param.error}</span>
				</div>
				<div class="line__input--error">
					${error_login_placeholder}
				</div>
				<div class="line__input">
					<input class="input__block input__block--submit" type="submit" />
				</div>
			</form:form>
		</div>
	</body>
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
	<script src="../../js/login.js"></script>
</html>
