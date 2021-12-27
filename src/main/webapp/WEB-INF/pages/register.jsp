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
		<title>Register</title>
	</head>
	<body>
		<div class="auth">
			<h1 class="auth__title">Register</h1>
			<p class="auth__text">
				Already have an account? <a href="#">Sign in</a>
			</p>
			<form:form action="registration/proceed" method="post" modelAttribute="registrationForm">
				<div class="line__input">
					<form:input path="firstName" class="input__block" type="text" id="firstName" required="true"  />

					<label for="firstName">First name</label>
				</div>
				<div class="line__input">
					<form:input class="input__block" path="middleName" type="text" id="middleName" required="true" />
					<label for="middleName">Middle name</label>
				</div>
				<div class="line__input">
					<form:input class="input__block" path="lastName" type="text" id="lastName" required="true" />
					<label for="lastName">Last name</label>
				</div>
				<div class="line__input ${errors.containsKey("dateBirth") ? 'line__input--error' : ""}" id="Date birth">
					<form:input class="input__block" path="dateBirth" type="date" id="date" required="true" />
					<label for="date"></label>
					<span>
						${errors.containsKey("dateBirth") ? errors.get("dateBirth") : ""}
					</span>
				</div>
				<div class="line__input ${errors.containsKey("login") ? 'line__input--error' : ""}" id="login" data-valid="3">
					<form:input class="input__block" path="login" type="text" id="login" required="true" />
					<label for="login">Login</label>

					<span>
						${errors.containsKey("login") ? errors.get("login") : ""}
					</span>
				</div>
				<div class="line__input ${errors.containsKey("password") ? 'line__input--error' : ''}" id="password" data-valid="8">
					<form:input class="input__block" path="password" type="password" id="password" required="true" />
					<label for="password">Password</label>

					<span>
						${errors.containsKey("password") ? errors.get("password") : ''}
					</span>
				</div>
				<div class="line__input">
					<form:select path="role" class="select__block" required="true">
						<form:option value="" label="Select your role"/>
						<form:options items="${roles}" />
					</form:select>
					<span></span>
				</div>
				<div class="line__input">
					<input class="input__block input__block--submit" type="submit" />
				</div>
			</form:form>
		</div>
	</body>
	<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
	<script src="../../js/register.js"></script>
</html>
