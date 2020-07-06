<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Страница авторизации</title>
</head>
<body>
    <div align="center">
        <form name="login-form" class="login-form" method="POST" action="controller">
          <input type="text" name="username" maxlength="20" placeholder="Имя игрока" required>
              <br>
          <input type="password" name="userpassword" maxlength="32" placeholder="Пароль" required>
            <div class="error-message">
                 <%
                    String msg = (String) request.getAttribute("loginMessage");
                    if (msg == null || msg.trim().isEmpty()) msg = "<br>";
                    %>
                    <%= msg %>

            </div>
            <button type="submit" name="action" value="login" class="btn">Войти</button>
            <br>
            <label>Если нет учетной записи:</label>
            <br>
            <button type="submit" name="action" value="show-registration" class="btn-link" formnovalidate>Зарегистрируйтесь</button>
        </form>
    </div>
</body>
</html>