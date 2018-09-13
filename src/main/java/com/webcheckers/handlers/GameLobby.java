package com.webcheckers.handlers;

import com.webcheckers.appl.Player;


import java.util.List;
import java.util.stream.Collectors;
import java.util.LinkedList;


public class GameLobby {


    private final static LinkedList<GameSession> sessions = new LinkedList<>();
    private static int nextID = 1;


    /**
     * Attempt to make a new GameSession. If the session is not properly made,
     * the function will return false and no session will be added to the list
     * of active game sessions.
     * @param redPlayer : Player with red pieces
     * @param whitePlayer : Player with white pieces
     * @return True if the GameSession was created successfully, false
     *         otherwise.
     */
    public static boolean newGameSession(Player redPlayer, Player whitePlayer){
        GameSession session = new GameSession(redPlayer, whitePlayer, nextID);
        if(session.validateSession()){
            session.initialize();
            sessions.add(session);
            nextID++;
            return true;
        }
        return false;
    }


    public static GameSession getActiveSession(int gameID){
        for(GameSession gameSession : sessions){
            if(gameSession.getGameID() == gameID){
                return gameSession;
            }
        }
        return null;
    }

    public static LinkedList<GameSession> getAllGameSessions(){
      return sessions;
    }

    public static void endActiveSession(GameSession gs){
        sessions.remove(gs);
    }

}
