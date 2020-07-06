<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Что-то пошло не так</title>
</head>
<body>

    <div align="center">
        <form name="error-form" class="error-form" method="POST" action="controller">
            <div class="form-message">
                <b>Упс! Такой страницы нет!</b>
            </div>
            <br>
            <button type="submit" name="action" value="show-index" class="btn-link-ind">Возвратиться на основную страницу</button>
        </form>
    </div>

</body>
</html>