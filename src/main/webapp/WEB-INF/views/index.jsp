<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Questland</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<main class="container">
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/home">Questland</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarNav" aria-controls="navbarNav"
                        aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/about">О проекте</a></li>
                        <li class="nav-item dropdown">
                            <button class="nav-link dropdown-toggle" id="questsDropdown" data-bs-toggle="dropdown">
                                Все квесты
                            </button>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/plant_worker?questName=plant_worker">Заводчанин</a></li>                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item disabled" href="#">Космодесант (скоро)</a></li>
                                <li><a class="dropdown-item disabled" href="#">Snatch (скоро)</a></li>
                            </ul>
                        </li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
                    </ul>
                    <ul class="navbar-nav">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <li class="nav-item dropdown">
                                    <button class="nav-link dropdown-toggle" id="userDropdown" data-bs-toggle="dropdown">
                                        Добро пожаловать, ${sessionScope.user.name}!
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Выйти</a></li>
                                    </ul>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="nav-item">
                                    <a class="btn btn-outline-light" href="${pageContext.request.contextPath}/login">Вход / Регистрация</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <div style="padding-top: 80px;">
        <c:choose>
            <c:when test="${not empty pagePath}">
                <jsp:include page="${pagePath}" />
            </c:when>
            <c:otherwise>
                <jsp:include page="about.jsp" />
            </c:otherwise>
        </c:choose>
    </div>

    <c:if test="${not empty errorLogin}">
        <p style="color: red; font-size: 10px; margin-top: -20px;">${errorLogin}</p>
    </c:if>
</main>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>