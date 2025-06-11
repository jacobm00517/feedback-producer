<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Welcome</title>
  </head>
  <body>
    <h2>Login successful!</h2>

    <p>Welcome, <strong>${sessionScope.username}</strong> 👋</p>

    <form action="logout" method="post">
      <button type="submit">Logout</button>
    </form>
  </body>
</html>
