<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Questland - Sign in</title>

    <!-- Main css -->
    <link rel="stylesheet" href="../../css/login_register_style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <style>
        body, input, button {
            font-family: 'Roboto', Arial, sans-serif;
        }
    </style>

    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <style>
        body, input, button, h2 {
            font-family: 'Roboto', Arial, sans-serif;
        }
    </style>

</head>
<body>

<div class="main">

    <!-- Sing in  Form -->
    <section class="sign-in">
        <div class="container">
            <div class="signin-content">


                <div class="signin-form">
                    <h2 class="form-title">Вход</h2>
                    <form method="post" action="login" class="register-form" id="login-form">
                        <div class="form-group">
                            <label for="login"><i
                                    class="zmdi zmdi-account material-icons-name"></i></label> <input
                                type="text" name="login" id="login"
                                placeholder="Логин"/>
                        </div>
                        <div class="form-group">
                            <label for="password"><i class="zmdi zmdi-lock"></i></label> <input
                                type="password" name="password" id="password"
                                placeholder="Пароль"/>
                        </div>
                        <c:if test="${not empty errorLogin}">
                            <p style="color: red; font-size: 10px; margin-top: -20px;">${errorLogin}</p>
                        </c:if>
                        <c:if test="${not empty errorPassword}">
                            <p style="color: red; font-size: 10px; margin-top: -20px;">${errorPassword}</p>
                        </c:if>
                        <div class="form-group form-button">
                            <input type="submit" name="signin" id="signin"
                                   class="form-submit" value="Вход"/>
                        </div>
                        <div class="signin-image">
                            <a href="${pageContext.request.contextPath}/sign_up" class="signup-image-link">Нет учетной
                                записи?</a>
                        </div>

                        <c:if test="${not empty errorEmptyLines}">
                            <p style="color: red; font-size: 10px; margin-top: -20px;">${errorEmptyLines}</p>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>