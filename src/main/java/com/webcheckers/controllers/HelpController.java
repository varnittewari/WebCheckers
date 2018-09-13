package com.webcheckers.controllers;

import com.webcheckers.appl.Player;
import com.webcheckers.handlers.CheckersSession;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by Varnit Tewari on 4/15/2018.
 */
public class HelpController {

    public static final String VIEW_NAME = "help.ftl";

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    public HelpController(){}

    public static ModelAndView helpPage(Request req , Response resp){
        CheckersSession session = CheckersSession.getPlayerSession(req);
        HashMap<String, Object> vm = session.getModel();
        //Player currentPlayer  = session.getCurrentPlayer();
        //session.getGameSession().endSession();
        return new ModelAndView(vm, VIEW_NAME);
    }

}
