package com.webcheckers.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.controllers.GameController;
import com.webcheckers.appl.BoardView;

import com.webcheckers.handlers.GameLobby;
import com.webcheckers.handlers.GameSession;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import static spark.Spark.halt;

import java.util.*;

import java.util.logging.Logger;

import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.utils.Path;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.Piece;

import com.webcheckers.handlers.CheckersSession;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * Created by Thomas Morris on 3/17/2018.
 */

@Tag("Controller")
public class GameControllerTest{

    private static final String VIEW_NAME = "game.ftl";

    private GameController CuT;

    //Mock objects
    private Request request;
    private Response response;
    private CheckersSession checkersSession;
    private Session session;
    private TemplateEngine engine;

    //Fiendlies
    private HashMap<String, Object> hash_map;
    private BoardView board;
    private Player current_player;
    private Player opponent;
    private List<Player> player_list;

    //Not so friendlies
    private PlayerHolder playerHolder;

    private String CURRENT_PLAYER_NAME = "player1";
    private int CURRENT_PLAYER_ID = 0;
    private String OPPONENT_PLAYER_NAME = "player2";
    private int OPPONENT_PLAYER_ID = 1;

    private enum viewMode{
        PLAY,
        SPECTATOR,
        REPLAY
    }


    @BeforeEach
    public void setup(){
        CuT = new GameController();

        //Create mocks
        request = mock(Request.class);
        response = mock(Response.class);
        checkersSession = mock(CheckersSession.class);
        session = mock(Session.class);
        engine = mock(TemplateEngine.class);

        //Instantiate friendlies
        hash_map = new HashMap<>();//MIGHT REMOVE FROM SETUP
        board = new BoardView("WHITE");
        //player_list = new ArrayList<>();
        //opponent = new Player(OPPONENT_PLAYER_NAME, OPPONENT_PLAYER_ID);
        //current_player = new Player(CURRENT_PLAYER_NAME, CURRENT_PLAYER_ID);

        //Instantiate not so friendlies
        playerHolder = new PlayerHolder();
        playerHolder.addPlayer(CURRENT_PLAYER_NAME);
        playerHolder.addPlayer(OPPONENT_PLAYER_NAME);
        current_player = PlayerHolder.getPlayer(CURRENT_PLAYER_NAME);
        opponent = PlayerHolder.getPlayer(OPPONENT_PLAYER_NAME);
        player_list = PlayerHolder.getList();

        when(request.session()).thenReturn(session);
        when(session.attribute(Path.CURRENT_USER)).thenReturn(CURRENT_PLAYER_NAME);
        //when(PlayerHolder.getPlayer(CURRENT_PLAYER_NAME)).thenReturn(current_player);

        when(checkersSession.getModel()).thenReturn(hash_map);
        when(checkersSession.getCurrentPlayer()).thenReturn(current_player);
        //when(PlayerHolder.getList()).thenReturn(player_list);
        when(request.queryParams("player")).thenReturn(OPPONENT_PLAYER_NAME);
        //when(playerHolder.getPlayer(OPPONENT_PLAYER_NAME)).thenReturn(opponent);

        CuT = new GameController();
    }


    @Test
    public void createGameTest(){

        //hash_map.put("players", player_list);
        //hash_map.put("redPlayer", opponent);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.create_game(request, response);
        engine.render(mv);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewName(GameController.VIEW_NAME);
        //testHelper.assertViewModelAttribute("players", player_list);
        testHelper.assertViewModelAttribute("viewMode", GameController.viewMode.PLAY);
        testHelper.assertViewModelAttribute("redPlayer", opponent);
        testHelper.assertViewModelAttribute("whitePlayer", current_player);
        testHelper.assertViewModelAttribute("activeColor", Piece.color.RED);
        //testHelper.assertViewModelAttribute("board", board);
    }

    @Test
    public void test_checkTurn(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.create_game(request, response);
        engine.render(mv);

        String s = CuT.checkTurn(request, response);
        assertNotNull(s);
    }

    @Test
    public void test_submitTurn(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.create_game(request, response);
        engine.render(mv);

        String s = CuT.submitTurn(request, response);
        assertNotNull(s);
    }

    @Test
    public void test_backupMove(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.create_game(request, response);
        engine.render(mv);

        String s = CuT.backupMove(request, response);
        assertNotNull(s);
    }


    /*@Test
    public void test_resignGame(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.create_game(request, response);
        engine.render(mv);

        String s = CuT.resignGame(request, response);
        assertNotNull(s);
    }*/


    /*
    @Test
    public void newGameHTML(){
        hash_map.put("players",PlayerHolder.getList());
        // Necessary for rendering game.ftl?
        hash_map.put("viewMode", viewMode.PLAY);
        hash_map.put("redPlayer", PlayerHolder.getPlayer(req.queryParams("player")));
        hash_map.put("whitePlayer", currentPlayer);
        hash_map.put("activeColor", Piece.color.RED);

        // Adding the BoardView
        hash_map.put("board", new com.webcheckers.appl.BoardView());
    }
    */

}
