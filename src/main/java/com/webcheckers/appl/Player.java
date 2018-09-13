package com.webcheckers.appl;


/**
 * Player Object
 * @author Josh Abrams
 */
public class Player {

    /**
     * Player's name
     */
    private String name;
    private int id ;
    private int gameID = 0;

    /**
     * Constructor for Player object
     * @param name - player's name
     */
    public Player(String name, int id){
        this.name = name;
        this.id = id;
    }

    public int getPlayerID(){
      return this.id;
    }
    

    /**
     * Gets Player's name
     * @return Player's name
     */
    public String getName(){
        return this.name;
    }


    /**
     * Assign a game session to the player. The player will only hold the
     * session's ID number.
     */
    public void assignGame(int gameID){
        this.gameID = gameID;
    }


    /**
     * Set the gameID to zero when the game has ended.
     */
    public void gameEnded(){
        gameID = 0;
    }


    /**
     * Return the ID of the game the player is in. If the player is not in a
     * game, gameID will be 0.
     * @return int corresponding to a GameSession
     */
    public int getGameID(){
        return this.gameID;
    }


    /**
     * Check if the player is in a game.
     * @return True if gameID is not 0, false otherwise.
     */
    public boolean inGame(){
        return gameID != 0;
    }
}
