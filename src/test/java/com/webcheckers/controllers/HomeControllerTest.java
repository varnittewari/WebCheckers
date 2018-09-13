package com.webcheckers.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import static spark.Spark.halt;

import java.util.*;

import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.utils.Path;
import com.webcheckers.appl.Player;

import com.webcheckers.handlers.CheckersSession;

/**
 * Created by Varnit Tewari on 3/19/2018.
 */

@Tag("Controller")
public class HomeControllerTest {

    private static final String VIEW_NAME = "home.ftl";

    private HomeController CuT;

    //Mock objects
    private Request request;
    private Response response;
    private CheckersSession checkersSession;
    private Session session;
    private TemplateEngine engine;

    //Fiendlies
    private HashMap<String, Object> hash_map;
    private Player current_player;

    private List<Player> player_list;
    private List<Player> player_list2;

    private PlayerHolder playerHolder;

    private String CURRENT_PLAYER_NAME = "player1";

    @BeforeEach
    public void setup(){
        CuT = new HomeController();

        //Create mocks
        request = mock(Request.class);
        response = mock(Response.class);
        checkersSession = mock(CheckersSession.class);
        session = mock(Session.class);
        engine = mock(TemplateEngine.class);

        //Instantiate friendlies
        hash_map = new HashMap<>();

        //Instantiate not so friendlies
        playerHolder = new PlayerHolder();
        playerHolder.addPlayer(CURRENT_PLAYER_NAME);
        current_player = playerHolder.getPlayer(CURRENT_PLAYER_NAME);
        player_list = Collections.<Player>emptyList();
        player_list2= PlayerHolder.availablePlayerList(current_player);

        when(request.session()).thenReturn(session);
        when(session.attribute(Path.CURRENT_USER)).thenReturn(CURRENT_PLAYER_NAME);
        //when(PlayerHolder.getPlayer(CURRENT_PLAYER_NAME)).thenReturn(current_player);

        when(checkersSession.getModel()).thenReturn(hash_map);
        when(checkersSession.getCurrentPlayer()).thenReturn(current_player);
        //when(PlayerHolder.getList()).thenReturn(player_list);

        CuT = new HomeController();
    }

    @Test
    public void home_indexTest(){

        //hash_map.put("players", player_list);
        //hash_map.put("redPlayer", opponent);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.home_index(request, response);
        engine.render(mv);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewName(HomeController.VIEW_NAME);

        if (current_player==null)
            testHelper.assertViewModelAttribute("players", player_list);
        else
            testHelper.assertViewModelAttribute("players", player_list2);
    }
}
