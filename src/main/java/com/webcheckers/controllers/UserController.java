package com.webcheckers.controllers;

import com.webcheckers.handlers.GameLobby;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import static spark.Spark.halt;

import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.utils.Path;
import com.webcheckers.appl.Player;
import com.webcheckers.handlers.CheckersSession;




public class UserController{

static final String VIEW_NAME = "signin.ftl";

  static final String SIGNIN_PARAM = "name";

  private static final Logger LOG = Logger.getLogger(UserController.class.getName());

  public UserController(){

  }


  public static Response deleteUser(Request req, Response rep){
    CheckersSession session = CheckersSession.getPlayerSession(req);
    HashMap<String, Object> vm = session.getModel();
    Player currentPlayer  = session.getCurrentPlayer();
    if(currentPlayer.inGame()){
        session.getGameSession().resign(currentPlayer);
    }
    PlayerHolder.removePlayer(currentPlayer);
    if(session.currentSession != null) session.currentSession.invalidate();
		rep.redirect(Path.HOME_INDEX);
    return rep;
  }

  //create a new user and assign a session.
  public static ModelAndView newUser(Request req, Response resp){
        HashMap<String, Object> vm = new HashMap<>();
        return new ModelAndView(vm , VIEW_NAME);
  }

  public static ModelAndView createUser(Request req, Response resp){
      final String player_name = req.queryParams(SIGNIN_PARAM);
      HashMap<String, Object> vm = new HashMap<>();

      if(validateName(player_name) && !PlayerHolder.isNameTaken(player_name)) {
          Player player = PlayerHolder.addPlayer(player_name);
          final Session httpSession = req.session();

          httpSession.attribute(Path.CURRENT_USER, player.getName());
          resp.redirect(Path.HOME_INDEX);
          return HomeController.home_index(req, resp);
        }
        else if(!validateName(player_name)){
          return badName(vm, makeInvalidNameMessage(player_name));
        }else{
          return badName(vm,makeUnavailableNameMessage(player_name));
        }
  }


    //PRIVATE METHODS//

    /**
     * Check that a string has length in a certain range and only contains
     * alphanumeric characters and spaces.
     * @param name: string for validation
     * @return: true if name if of the correct format, false otherwise
     */
    private static Boolean validateName(final String name) {
        int len = name.length();
        if(len < 21 && len > 0) {
            if (name.matches("^[a-zA-Z0-9 ]+$")) {
                return true;
            }
        }
        return false;
    }
    /**
     * Render the Signin page with an added message for why the name request
     * failed.
     * @param vm: model and view map
     * @param message: notification of problem fulfilling the last name request
     */
    private static ModelAndView badName(final Map<String, Object> vm, final String message) {
        vm.put(Path.MESSAGE_ATTR, message);
        return new ModelAndView(vm , VIEW_NAME);
    }


    static String makeUnavailableNameMessage(String name) {
        return String.format("The name \"%s\" is already in use. Please choose a different name.", name);
    }

    static String makeInvalidNameMessage(String name) {
        return String.format("\"%s\" is not a valid name. Please enter a name of length 1 to 20, containing"+
                " only alphanumeric characters or spaces.", name);
    }


}
