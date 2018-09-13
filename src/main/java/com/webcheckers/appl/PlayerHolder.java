package com.webcheckers.appl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Collections;
/**
 * Coordinates current players
 */
public class PlayerHolder {

    /* The list of Player Objects, using name as key */
    private static final HashMap<String,Player> playerList = new HashMap<String, Player>();

    /**
     * Creates new PlayerHolder
     */
    public PlayerHolder(){
    }


    /**
     * Return the number of active players in the application.
     * @return size of playerList
     */
    public static int activePlayerCount(){
        return playerList.size();
    }


    /**
     * Checks if name is already used.
     * @param name - name to check
     * @return true if name is taken, false if available
     */
    public static boolean isNameTaken(final String name){
        return playerList.containsKey(name);
    }

    /**
     * Adds a player to the list
     * @param name - Player name to use create a Player
     */
    public static Player addPlayer(String name){
        Player player = new Player(name, playerList.size());
        playerList.put(name, player);
        return player;
    }

    /**
     * Removes a player from the list, effectively destroying it.
     * @param name - Name of Player to remove
     */
    public static void removePlayer(String name){
        playerList.remove(name);
    }

    public static void removePlayer(Player player){
      playerList.remove(player.getName());
    }

    /**
     * Gets player associated with the provided name
     * @param name - Name of Player to get
     * @return Player if it exists, null otherwise
     */
    public static Player getPlayer(String name){
        if(playerList.containsKey(name)) {
            return playerList.get(name);
        } else {
            return null;
        }
    }


    /**
     * Returns an ArrayList of all known Players
     * @return ArrayList of Player objects
     */
    public static List<Player> getList(){
       return playerList.values().stream().collect(Collectors.toList());//.stream().map(map -> map.getValue());//.filter(p -> playerofInterest.getName() == p.getName()).collect(Collectors.toList());
    }


    /**
     * Returns a List of all active Players which are not the current user and
     * are not in a game.
     * @param playerofInterest current user
     * @return List<Player> of all players available for a game
     */
    public static List<Player> availablePlayerList(Player playerofInterest){
        return playerList.values().stream().filter(p -> !(playerofInterest.getName().equals(p.getName()) || p.inGame()) ).collect(Collectors.toList());//.stream().map(map -> map.getValue());//.filter(p -> playerofInterest.getName() == p.getName()).collect(Collectors.toList());
    }

}
