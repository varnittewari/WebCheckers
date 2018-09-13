package com.webcheckers.controllers;

import com.webcheckers.handlers.GameLobby;
import com.webcheckers.handlers.GameSession;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import static spark.Spark.halt;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.utils.Path;
import com.webcheckers.appl.Player;
import com.webcheckers.handlers.CheckersSession;

public class HomeController{

  private static final Logger LOG = Logger.getLogger(UserController.class.getName());


  public static final String VIEW_NAME = "home.ftl";

  public HomeController(){

  }

  public static ModelAndView home_index(Request req, Response resp) {
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    GameSession gameSession = session.getGameSession();

    // Check if the player is in an active game.
    if(gameSession != null){
      if(gameSession.gameOver() ||
              (currentPlayer != gameSession.getRed() && currentPlayer != gameSession.getWhite())){
        gameSession.endSession(currentPlayer);
      }
      else{
        resp.redirect(Path.CREATE_GAME);
        return GameController.create_game(req, resp);
      }
    }

    // Decide which list of available players to provide.
    if (currentPlayer == null){
      vm.put("players", Collections.<Player>emptyList());
      vm.put("games", Collections.<GameSession>emptyList());
    }
    else{
      vm.put("players",PlayerHolder.availablePlayerList(currentPlayer));
        vm.put("games", GameLobby.getAllGameSessions());
    }

    vm.put(Path.MESSAGE_ATTR, flashMessage(req));
    return new ModelAndView(vm , VIEW_NAME);
  }


  /**
   * This can probably be put in CheckersSession
   * @param request
   * @return
   */
  private static String flashMessage(Request request){
    final Session session = request.session();
    String message = session.attribute(Path.MESSAGE_ATTR);
    session.removeAttribute(Path.MESSAGE_ATTR);
    LOG.log(Level.FINE, message);
    return message;
  }



}
