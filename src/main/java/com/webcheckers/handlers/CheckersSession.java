package com.webcheckers.handlers;

import com.webcheckers.appl.Message;
import com.webcheckers.appl.Player;
import com.webcheckers.appl.PlayerHolder;
import com.webcheckers.model.Move;
import com.webcheckers.utils.Path;
import spark.Request;
import spark.Session;

import java.util.HashMap;

public class CheckersSession {

  private HashMap<String, Object> m = new HashMap<>();
  private Player currentPlayer = null;
  public final Session currentSession;
  private GameSession game;

  public CheckersSession(Request req){
    currentSession = req.session();
    String playerID = currentSession.attribute(Path.CURRENT_USER);
    currentPlayer = PlayerHolder.getPlayer(playerID);
    m.put(Path.CURRENT_USER, currentPlayer);
    m.put(Path.PLAYER_COUNT, PlayerHolder.activePlayerCount());
    if(currentPlayer != null && currentPlayer.inGame()){
      game = GameLobby.getActiveSession(currentPlayer.getGameID());
     // m.put(Path., currentPlayer.getGameID());
    }
  }


  public Player getCurrentPlayer(){
    return this.currentPlayer;
  }

  public HashMap<String, Object> getModel(){
   return this.m;
  }

  public GameSession getGameSession(){
    return this.game;
  }

    public boolean isMyTurn(){
        return this.game.isMyTurn(this.currentPlayer);
    }

    public Message validateMove(Move mov){
      return this.game.checkTurn(mov);
    }

  public static CheckersSession getPlayerSession(Request req){
    return new CheckersSession(req);
  }

}
