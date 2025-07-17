<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Questland - О проекте</title>
    <link rel="stylesheet" type="text/css" href="../../css/main_style.css">
</head>
<body>
    <h1>Все доступные квесты</h1>

    <div class="quests-container">
        <div class="quest-card">
            <img src="../../images/plant_worker.png" alt="Заводчанин" class="quest-image">
            <div class="quest-title">Заводчанин</div>
            <button class="quest-button" onclick="location.href='plant_worker?questName=plant_worker'">Начать квест</button>        </div>

        <div class="quest-card">
            <img src="../../images/space_marine.webp" alt="Космодесантник (скоро)" class="quest-image">
            <div class="quest-title">Космодесант (скоро)</div>
            <button class="quest-button" disabled>Скоро</button>
        </div>

        <div class="quest-card">
            <img src="../../images/snatch.jpg" alt="Snatch (скоро)" class="quest-image">
            <div class="quest-title">Snatch (скоро)</div>
            <button class="quest-button" disabled>Скоро</button>
        </div>
    </div>
</body>
</html>