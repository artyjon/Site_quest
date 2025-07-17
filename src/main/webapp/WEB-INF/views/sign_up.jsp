<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Questland - Регистрация</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Шрифты и иконки -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500&display=swap" rel="stylesheet">

    <!-- Стили -->
    <link rel="stylesheet" href="../../css/login_register_style.css">
    <style>
        body, input, button, h2 {
            font-family: 'Roboto', Arial, sans-serif;
        }

        .error-msg {
            color: red;
            font-size: 12px;
            margin-top: -15px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<main class="main">
    <section class="signup">
        <div class="container">
            <div class="signup-content">
                <div class="signup-form">
                    <h2 class="form-title">Создание учетной записи</h2>

                    <form method="post" action="sign_up" class="register-form" id="register-form" accept-charset="UTF-8">
                        <fieldset>
                            <legend>Личные данные</legend>

                            <c:if test="${not empty errorEmptyLines}">
                                <p class="error-msg">${errorEmptyLines}</p>
                            </c:if>

                            <div class="form-group">
                                <label for="name"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input type="text" name="name" id="name" placeholder="Имя" required
                                       value="${param.name}" />
                            </div>

                            <div class="form-group">
                                <label for="login"><i class="zmdi zmdi-account material-icons-name"></i></label>
                                <input type="text" name="login" id="login" placeholder="Логин" required
                                       value="${param.login}" />
                            </div>

                            <c:if test="${not empty errorLogin}">
                                <p class="error-msg">${errorLogin}</p>
                            </c:if>

                            <div class="form-group">
                                <label for="pass"><i class="zmdi zmdi-lock"></i></label>
                                <input type="password" name="pass" id="pass" placeholder="Пароль (мин. 8 символов, буква и цифра)" required />
                            </div>

                            <c:if test="${not empty errorPassword}">
                                <p class="error-msg">${errorPassword}</p>
                            </c:if>

                            <div class="form-group">
                                <label for="repass"><i class="zmdi zmdi-lock-outline"></i></label>
                                <input type="password" name="repass" id="repass" placeholder="Повторите пароль" required />
                            </div>

                            <c:if test="${not empty errorPassRepeat}">
                                <p class="error-msg">${errorPassRepeat}</p>
                            </c:if>

                            <div class="form-group">
                                <input type="checkbox" name="agree-term" id="agree-term" class="agree-term" required />
                                <label for="agree-term" class="label-agree-term">
                                    <span><span></span></span>Я согласен с
                                    <a href="${pageContext.request.contextPath}/html/terms_of_service" class="term-service">условиями использования</a>
                                </label>
                            </div>
                        </fieldset>

                        <div class="form-button">
                            <input type="submit" name="signup" id="signup" class="form-submit" value="Зарегистрироваться" />
                        </div>

                        <c:if test="${not empty errorEmptyLines}">
                            <p class="error-msg">${errorEmptyLines}</p>
                        </c:if>
                    </form>

                    <a href="${pageContext.request.contextPath}/login" class="signup-image-link" style="font-size: 13px; margin-top: 10px;">У меня уже есть аккаунт</a>
                </div>
            </div>
        </div>
    </section>
</main>
</body>
</html>