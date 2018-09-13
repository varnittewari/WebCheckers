

package com.webcheckers.handlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.controllers.GameController;
import com.webcheckers.appl.BoardView;

import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import spark.*;

import static spark.Spark.halt;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;
import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.utils.Path;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.Piece;

import com.webcheckers.handlers.CheckersSession;
import spark.template.freemarker.FreeMarkerEngine;

@Tag("Controller")
public class CheckersSessionTest {

    private CheckersSession CS;

    private Request request;
    private Session session;
    private TemplateEngine engine;
    private Response response;
    private PlayerHolder pH;

    private int expectedID;


  @BeforeEach
  public void setup(){
    request = mock(Request.class);
    session = mock(Session.class);
    //Player player = new Player("Test",3);
    String name = "Test";
    pH = new PlayerHolder();
    pH.addPlayer(name);
    expectedID = pH.getPlayer("Test").getPlayerID();
    when(request.session()).thenReturn(session);
    response = mock(Response.class);
    when(session.attribute(Path.CURRENT_USER)).thenReturn("Test");


  }

  @Test
  public void test_defaultSessionAttributes(){
    TemplateEngineTester testHelper = new TemplateEngineTester();
    CheckersSession CS = new CheckersSession(this.request);
    final HashMap<String, Object> vm = CS.getModel();
    assertEquals("Test",((Player) vm.get(Path.CURRENT_USER)).getName());

  }

  @Test
  public void test_currentPlayer(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    CheckersSession CS = new CheckersSession(this.request);
    assertEquals(CS.getCurrentPlayer().getName(),"Test");
    //assertEquals(CS.getCurrentPlayer().getPlayerID(),3);
    assertEquals(expectedID, CS.getCurrentPlayer().getPlayerID());
  }

    

}
