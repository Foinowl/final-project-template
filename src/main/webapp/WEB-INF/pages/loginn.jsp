<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
  <head>
    <meta charset="UTF-8">
    <title>Sign up</title>
  </head>

  <body>
    <form action="login/process" method="post">
      <p title="Login form">Sign up</p>
      <div class="group">
        <label for="">Login</label>
        <input name="login" />
      </div>

      <div class="group">
        <label for="">Password</label>
        <input name="password" type="password" />
        <div class="error">${error_login_placeholder}</div>
      </div>


      <div class="group">
        <button class="button">Confirm</button>
      </div>
    </form>

    <form action="registration" method="get">
      <div>
        <button class="button">registration</button>
      </div>
    </form>

  </body>
</html>
