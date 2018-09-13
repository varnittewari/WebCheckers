<!DOCTYPE html>

<#include "main.ftl">

<body>
  <div class="page">
    <h1>Web Checkers</h1>

    <#include "navigation.ftl">

    <div class="body">
      <h2>Sign in:</h2>

      <#if message??>
      <div class="error">${message}</div>
      </#if>

      <form action="${Path.USER_SETUP}" method="POST">
        Your name: <input name="name" />
        <br/>
        <button id="ok" type="submit">Ok</button>
      </form>
    </div>

  </div>
</body>
</html>
