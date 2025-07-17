<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Заводчанин</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../../../../css/quest_style.css">
</head>
<body>
<c:if test="${not empty questInclude}">
    <c:import url="${questInclude}" />
</c:if>

<c:if test="${empty questInclude}">
    <h2>Этап не найден</h2>
    <form action="plant_worker" method="get">
        <input type="hidden" name="questName" value="${param.questName}" />
        <input type="hidden" name="stage" value="0" />
        <button type="submit">Начать заново</button>
    </form>
</c:if>
</body>
</html>