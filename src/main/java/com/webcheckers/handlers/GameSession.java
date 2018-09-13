package com.webcheckers.handlers;

import com.webcheckers.appl.*;
import com.webcheckers.controllers.GameController;
import com.webcheckers.model.CheckersModel;
import com.webcheckers.model.Move;
import com.webcheckers.model.Validator;
import com.webcheckers.utils.Path;
import spark.ModelAndView;
import spark.Request;
import spark.Session;

import java.util.HashMap;

//import java.util.HashMap;


public class GameSession {


    private Player redPlayer;
    private Player whitePlayer;
    private int gameID;
    private Validator validator;
    private boolean active = true;
    private Piece.color lastActive;

    //private Piece.color activeColor = Piece.color.RED;


    public GameSession(Player redPlayer, Player whitePlayer, int gameID){
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.gameID = gameID;
    }

    //TODO Are these necessary?

    public Player getRed(){
      return this.redPlayer;
    }
    public Player getWhite(){
      return this.whitePlayer;
    }


    /**
     * Create a string unique to this session from the two player's names.
     * @return 'name' of session; "redPlayer vs whitePlayer"
     */
    public String name(){
        return redPlayer.getName()+" vs "+whitePlayer.getName();
    }


    /**
     * Check that the GameSession was created and has all the necessary
     * attributes. Return true if the session is valid, false otherwise.
     * @return True if the players are not null, the gameID is not 0 and the
     *         model is not null, false otherwise.
     */
    public boolean validateSession(){
        return (redPlayer != null && whitePlayer != null && gameID != 0);
    }


    /**
     * Assign the gameID to the players and make a new model to represent the
     * game.
     */
    public void initialize(){
        redPlayer.assignGame(gameID);
        whitePlayer.assignGame(gameID);
        validator = new Validator();
        lastActive = Piece.color.RED;
    }


    /**
     * Notify the player objects that the game has ended and they are no longer
     * in a game.
     */
    public void endSession(Player current){
        if(current.equals(redPlayer)){
            current.gameEnded();
            redPlayer = new Player(current.getName(), -1);
        }
        else if(current.equals(whitePlayer)){
            current.gameEnded();
            whitePlayer = new Player(current.getName(), -1);
        }
        if(redPlayer.getPlayerID() < 0 && whitePlayer.getPlayerID() < 0){
            GameLobby.endActiveSession(this);
        }
    }


    public void resign(Player current){
        if(current.equals(redPlayer)){
            endSession(current);
            active = false;
            if(validator.getActiveColor() == Piece.color.RED){
                validator.changeTurn();
            }
        }
        else if(current.equals(whitePlayer)){
            endSession(current);
            active = false;
            if(validator.getActiveColor() == Piece.color.WHITE){
                validator.changeTurn();
            }
        }
    }


    /**
     * Populate the current user's map with the elements necessary for rendering
     * game.ftl, using the information held by the GameSession itself and the
     * checkers game's model.
     * @param player : The Player to generate the BoardView for.
     * @param vm : The map to populate
     */
    public void preparePlayerViewModel(Player player, HashMap<String, Object> vm){
        vm.put("viewMode", GameController.viewMode.PLAY);
        vm.put("redPlayer", redPlayer);
        vm.put("whitePlayer", whitePlayer);
        vm.put("activeColor", validator.getActiveColor());
        if(player.equals(redPlayer)){
            vm.put("board", new BoardView(validator.getBoard(), "RED"));
        }
        else if(player.equals(whitePlayer)){
            vm.put("board", new BoardView(validator.getBoard(), "WHITE"));
        }
    }


    public void prepareSpectatorViewModel(HashMap<String,Object> vm){
        vm.put("viewMode", GameController.viewMode.SPECTATOR);
        vm.put("redPlayer", redPlayer);
        vm.put("whitePlayer", whitePlayer);
        vm.put("activeColor", validator.getActiveColor());
        vm.put("board", new BoardView(validator.getBoard(), "WHITE"));
    }


    public Message checkForNewTurn(){
        //System.out.println("checking?");
        if(lastActive != validator.getActiveColor()){
            switch(lastActive){
                case RED:
                    lastActive = Piece.color.WHITE;
                    break;
                case WHITE:
                    lastActive = Piece.color.RED;
                    break;
            }
            //System.out.println("return true");
            return new Message("true", "info");
        }
        else{
            //System.out.println("return false");
            return new Message("false", "info");
        }
    }


    // ### I wrote these so you don't have to retrieve the validator and make requests on it. ###

    public Message checkTurn(Move move){
        if(validator.getActiveColor()==Piece.color.WHITE){
            move.invertRow();
        }
        return validator.validate(move);
    }


    public Message undoTurn(){
        return validator.resetTurn();
    }


    public Message submitTurn(){
        Message message = validator.submitTurn();
        if(validator.hasWon(Piece.color.RED) || validator.hasWon(Piece.color.WHITE)) active = false;
        return message;
    }


    public boolean gameOver(){
        return !active;
    }


    public Message gameOverMessage(Player player){
        if(active){
            return new Message("false", "info");
        }
        else{
            Piece.color winner = null;
            if(validator.hasWon(Piece.color.RED)){
                winner = Piece.color.RED;
            }
            else if(validator.hasWon(Piece.color.WHITE)){
                winner = Piece.color.WHITE;
            }
            if(winner == null){
                return new Message("Your opponent has resigned. You may now return to home or sign out.", "info");
            }
            if(player.equals(redPlayer)){
                switch(winner){
                    case RED:
                        return new Message("You have won! You may now return to home or sign out.", "info");
                    case WHITE:
                        return new Message("You have lost! You may now return to home or sign out.", "error");
                    default:
                        return new Message("false", "error");
                }
            }
            else if(player.equals(whitePlayer)){
                switch(winner){
                    case RED:
                        return new Message("You have lost! You may now return to home or sign out.", "error");
                    case WHITE:
                        return new Message("You have won! You may now return to home or sign out.", "info");
                    default:
                        return new Message("false", "error");
                }
            }
            else{
                switch(winner){
                    case RED:
                        return new Message(redPlayer.getName()+" has won. Please return to home.", "info");
                    case WHITE:
                        return new Message(whitePlayer.getName()+" has won. Please return to home..", "info");
                    default:
                        return new Message("false", "error");
                }
            }
        }
    }


    public boolean isMyTurn(Player player){
        if(player.equals(redPlayer)){
            if(validator.getActiveColor() == Piece.color.RED){
              return true;
            }
        }else if(player.equals(whitePlayer)){
            if(validator.getActiveColor() == Piece.color.WHITE){
              return true;
            }
        }
        return false;
    }


    //[hopefully] TEMPORARY PATHWORK METHOD
    public int getGameID(){
        return this.gameID;
    }
}
