
package com.webcheckers.utils;

public class Path{

  public Path(){

  }
  //login and user setup routes
  public static String USER_SETUP = "/session/signin";
  public static String SIGN_OUT = "/session/signout";
  public static String PLAYER_COUNT = "playerCount";

  public static String HOME_INDEX = "/";

  public static String CREATE_GAME = "/game";
  public static String HELP = "/help";

  public static String SPECTATE_GAME = "/spectate";
  public static String SPECTATOR = "/game/:gameid";
  public static String CHECK_FOR_CHANGE = "/checkForChange";

  public static String TITLE_ATTR = "title";
  public static String MESSAGE_ATTR = "message";
  //Page Titles
  public static String HOME_TITLE = "Welcome Page";
  public static String NEW_USER_TITLE = "Sign In Page";
  public static String GAME_TITLE = "Checkers Game";

  //Game Ajax routes
  public static String CHECK_TURN = "checkTurn";
  public static String VALIDATE_MOVE = "validateMove";
  public static String SUBMIT_TURN = "submitTurn";
  public static String BACKUP_MOVE = "backupMove";
  public static String RESIGN_GAME = "resignGame";
  public static String GAME_OVER = "gameOver";




  //user attributes
  public static String USER_ID = "id";
  public static String CURRENT_USER  = "currentPlayer";

}
