package com.webcheckers.controllers;

//import com.webcheckers.appl.BoardView;
import com.webcheckers.appl.Message;
import com.webcheckers.handlers.GameLobby;
import com.webcheckers.handlers.GameSession;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import static spark.Spark.halt;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.utils.Path;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.Piece;
import com.webcheckers.handlers.CheckersSession;
import com.webcheckers.appl.Message;
import com.webcheckers.model.Move;

public class GameController{

  public static final String VIEW_NAME = "game.ftl";
  private static final String UNAVAILABLE_PLAYER = "Player %s is spectating a game.";

  private static final Logger LOG = Logger.getLogger(GameController.class.getName());

  public enum viewMode{
    PLAY,
    SPECTATOR,
    REPLAY
  }


  public GameController(){

  }


  public static ModelAndView create_game(Request req, Response resp) {
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();

    if(currentPlayer.inGame()){
        int gameID = currentPlayer.getGameID();
        GameSession gameSession = GameLobby.getActiveSession(gameID);
        gameSession.preparePlayerViewModel(currentPlayer, vm);
    }
    else {
        Player opponent = PlayerHolder.getPlayer(req.queryParams("player"));
        if(opponent.inGame()){
            LOG.log(Level.FINE, "Attempted to start game with unavailable player.");
            req.session().attribute(Path.MESSAGE_ATTR,
                    String.format(UNAVAILABLE_PLAYER, opponent.getName()));
            resp.redirect(Path.HOME_INDEX);
            return HomeController.home_index(req, resp);
        }

        if(!GameLobby.newGameSession(opponent, currentPlayer)){
            LOG.log(Level.WARNING, "Couldn't make the GameSession.");
            resp.redirect(Path.HOME_INDEX);
            return HomeController.home_index(req, resp);
        }
    }

    return GameController.getGame(req,resp);
  }


  public static ModelAndView getGame(Request req ,Response resp){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    GameSession gameSession = session.getGameSession();
    LOG.log(Level.INFO, currentPlayer.getGameID() + " ");
    gameSession.preparePlayerViewModel(currentPlayer, vm);
    return new ModelAndView(vm, VIEW_NAME);
  }


  public static String checkGameOver(Request req, Response resp){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    GameSession gs = session.getGameSession();
    return gs.gameOverMessage(currentPlayer).toJson();
  }


  public static String checkTurn(Request req , Response resp){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    boolean myTurn =  session.isMyTurn();
    return new Message(Boolean.toString(myTurn),"info").toJson();
  }


  public static String validateMove(Request req , Response resp){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    Move mov =  Move.fromJson(req.body());
    Message message = session.validateMove(mov);
    return message.toJson();
  }


  /*
  public static ModelAndView getSpectateGame(Request req, Response resp){
      LOG.log(Level.INFO, "getSpectateGame called");
      CheckersSession session = CheckersSession.getPlayerSession(req);
      HashMap<String, Object> vm = session.getModel();
      int gameID = Integer.parseInt(req.params(":gameid"));
      GameSession gameSession = GameLobby.getActiveSession(gameID);
      gameSession.prepareSpectatorViewModel(vm);
      return new ModelAndView(vm, VIEW_NAME);
  }
  */


  public static String checkForChange(Request req, Response resp){
      CheckersSession session = CheckersSession.getPlayerSession(req);
      GameSession gs = session.getGameSession();
      return gs.checkForNewTurn().toJson();
  }



  public static ModelAndView spectateGame(Request req, Response resp){
      LOG.log(Level.INFO, "get spectate");
      CheckersSession session = CheckersSession.getPlayerSession(req);
      HashMap<String, Object> vm = session.getModel();
      Player currentPlayer  = session.getCurrentPlayer();

      if(currentPlayer.inGame()){
          int gameID = currentPlayer.getGameID();
          GameSession gameSession = GameLobby.getActiveSession(gameID);
          try{
              gameSession.prepareSpectatorViewModel(vm);
          }
          catch(NullPointerException ne){
              currentPlayer.gameEnded();
              req.session().attribute(Path.MESSAGE_ATTR, "The game no longer exists.");
              resp.redirect(Path.HOME_INDEX);
          }
      }
      else {
          GameSession gs = GameLobby.getActiveSession(Integer.parseInt(req.queryParams("session")));
          try{
              currentPlayer.assignGame(gs.getGameID());
              gs.prepareSpectatorViewModel(vm);
          }
          catch(NullPointerException ne){
              currentPlayer.gameEnded();
              req.session().attribute(Path.MESSAGE_ATTR, "The game no longer exists.");
              resp.redirect(Path.HOME_INDEX);
          }
      }

      return new ModelAndView(vm, VIEW_NAME);
  }



  public static String submitTurn(Request req , Response resp){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    Message message = session.getGameSession().submitTurn();
    return message.toJson();
  }


  public static String backupMove(Request req , Response resp){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    Message message = session.getGameSession().undoTurn();
    return message.toJson();
  }


  public static String resignGame(Request req , Response resp){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    session.getGameSession().resign(currentPlayer);
    return new Message("Resigned","info").toJson();
  }


}
