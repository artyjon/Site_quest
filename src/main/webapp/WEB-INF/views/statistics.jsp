<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Статистика игроков</title>
    <link rel="stylesheet" type="text/css" href="../../css/stat_style.css">
    <link rel="stylesheet" type="text/css" href="../../css/main_style.css">
</head>
<body>
<h1>Статистика по квестам игрока:<br> ${sessionScope.user.name}</h1>

<table>
    <thead>
    <tr>
        <th>Название квеста</th>
        <th>Количество игр</th>
        <th>Победы</th>
        <th>Поражения</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${sessionScope.user.quests}">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${not empty sessionScope.questNameMap[entry.key]}">
                        <c:out value="${sessionScope.questNameMap[entry.key]}" />
                    </c:when>
                    <c:otherwise>
                        <c:out value="${entry.key}" />
                    </c:otherwise>
                </c:choose>
            </td>
            <td><c:out value="${entry.value.totalGamesPlayed}" /></td>
            <td><c:out value="${entry.value.wins}" /></td>
            <td><c:out value="${entry.value.losses}" /></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>