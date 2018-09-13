package com.webcheckers.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static com.webcheckers.controllers.UserController.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.handlers.GameLobby;
import com.webcheckers.utils.Path;
import org.mockito.Mockito.*;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.controllers.UserController;



import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.HaltException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;


/**
 * @author Josh Abrams
 * Test class for UserController. Tests proper user creation, case of invalid name, and case of
 * unavailable name.
 */
@Tag("Controller")
public class UserControllerTest {
    private UserController CuT;

    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private PlayerHolder pH;


    /**
     * Setup new mock objects for each test.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);

        // create a unique CuT for each test
        // the GameCenter is friendly but the engine mock will need configuration
        CuT = new UserController();
    }

    /**
     * Tests the creation of a new user session.
     */
    @Test
    public void new_user(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView test = newUser(request, response);
        engine.render(test);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(UserController.VIEW_NAME);
    }

    /**
     * Tests creating a user with invalid name. View remains on sign-in page and invalid name
     * message given.
     */
    @Test
    public void create_user_bad() {
        pH = new PlayerHolder();
        String strB = "bad1{}";
        when(request.queryParams(eq(UserController.SIGNIN_PARAM))).thenReturn(strB);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.createUser(request, response);
        engine.render(mv);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(Path.MESSAGE_ATTR,
                UserController.makeInvalidNameMessage(strB));

        //Back to signin
        testHelper.assertViewName(UserController.VIEW_NAME);

    }

    /**
     * Tests creating a user who's name is already taken. View remains on sign-in and unavailable
     * name message given.
     */
    @Test
    public void create_user_taken(){
        pH = new PlayerHolder();
        String strT = "taken1";
        pH.addPlayer(strT);
        when(request.queryParams(eq(UserController.SIGNIN_PARAM))).thenReturn(strT);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.createUser(request, response);
        engine.render(mv);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(Path.MESSAGE_ATTR,
                UserController.makeUnavailableNameMessage(strT));

        //Back to signin
        testHelper.assertViewName(UserController.VIEW_NAME);
    }

    /**
     * Tests creation of new user with a valid, available name. View back to home, no messages.
     */
    @Test
    public void create_name_good(){
        pH = new PlayerHolder();
        String strG = "good1";
        when(request.queryParams(eq(UserController.SIGNIN_PARAM))).thenReturn(strG);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        ModelAndView mv = CuT.createUser(request, response);
        engine.render(mv);

        // Analyze the results:
        //   * model is a non-null Map
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        //   * model contains all necessary View-Model data
        testHelper.assertViewModelAttribute(Path.MESSAGE_ATTR,
                null);

        //Back to home
        testHelper.assertViewName(HomeController.VIEW_NAME);
    }



}
