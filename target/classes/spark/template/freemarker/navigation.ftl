<!DOCTYPE html>
<div class="navigation">
    <#if currentPlayer??>
      <button id="button" onclick="location.href='/'">My Home</button> |
      <button id="button" onclick="location.href='${Path.SIGN_OUT}'">Sign Out [${currentPlayer.name}]</button> |
      <button id="button" onclick="window.open('${Path.HELP}')" >Help</button>
    <#else>
      <button id="button" onclick="location.href='/'">My Home</button> |
      <button id="button" onclick="location.href='${Path.USER_SETUP}'">Sign In</button>
    </#if>

</div>
