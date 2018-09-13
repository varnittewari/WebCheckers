<!DOCTYPE html>

<head xmlns="http://www.w3.org/1999/html">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${Path.NEW_USER_TITLE} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
  <div class="page">

    <h1>Web Checkers</h1>

    <#include "navigation.ftl">

    <div class="body">
       <#if message??>
       <div class="error">${message}</div>
       </#if>
       <#if currentPlayer??>
        <p>Welcome to the world of online Checkers, <b>${(currentPlayer.getName())}</b>.</p>
       <#else>
           <h3>Welcome to the World of Online Checkers!</h3>
           <button class="button" type="button" onclick="location.href='${Path.USER_SETUP}'">Sign-in</button>
       </#if>

    </div>
    <ul>
    <#list players as player>
      <li>${player.name}</li>
      <form action="${Path.CREATE_GAME}" method="post">
        <input type="hidden" name="player" value="${player.name}">
        <input type="submit" value="Play Game" />
      </form>
    </#list>
    </ul>

  <hr>

    <ul>

    <#list games as session>
        <li>${session.name()}</li>
        <form action="${Path.SPECTATE_GAME}" method="get">
            <input type="hidden" name="session" value="${session.getGameID()}">
            <input type="submit" value="Spectate Game" />
        </form>
    </#list>

    <p> There are currently ${playerCount} players online. </p>

    </ul>

  </div>
</body>
</html>
