<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Основная страница</title>
</head>
<body>
    <div align="center">
        <form name="index-form" class="index-form" method="POST" action="controller">
            <div class="form-message">
                <b>Это игра «Бык и корова»!</b>
                <p>
                Для начала игры необходимо зарегистрироваться или войти, используя
                имеющуюся учетную запись.
                </p>
            </div>
            <button type="submit" name="action" value="show-registration" class="btn-link-ind">Регистрация</button>
            <br>
            <br>
            <button type="submit" name="action" value="show-login" class="btn-link-ind">Вход</button>
        </form>
    </div>
</body>
</html>