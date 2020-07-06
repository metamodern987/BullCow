<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Страница регистрации</title>
</head>
<body>
    <div align="center">
        <form name="register-form" class="register-form" method="POST" action="controller">

            <label for="fullname" class="label-ed">Полное имя</label>
            <input type="text" id="fullname" name="fullname" maxlength="20" required>
            <br>
            <label for="username" class="label-ed">Логин</label>
            <input type="text" id="username" name="username" maxlength="20" required>
            <br>
            <label for="password" class="label-ed">Пароль</label>
            <input type="password" id="userpassword" name="userpassword" maxlength="32" required>
            <div class="error-message">
                <%
                    String msg = (String) request.getAttribute("registMessage");
                    if (msg == null || msg.trim().isEmpty()) msg = "<br>";
                %>
                <%= msg %>
            </div>
            <button type="submit" name="action" value="registration" class="btn">Зарегистрироваться</button>
            <br>
            <button type="submit" name="action" value="show-login" class="btn-link" formnovalidate>Войти</button>
            <br>
        </form>
    </div>
</body>
</html>