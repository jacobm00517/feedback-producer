<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Login</title>
  </head>
  <body>
    <h2>Login</h2>
    <form method="post" action="LoginServlet">
      <label>Username:</label>
      <input type="text" name="username" /><br/>
      <label>Password:</label>
      <input type="password" name="password" /><br/>
      <input type="submit" value="Login" />
    </form>

    <c:if test="${not empty error}">
      <p style="color:red">${error}</p>
    </c:if>
  </body>
</html>