package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Application-tier")
public class PlayerHolderTest {

    /*
    Test the creation of a PlayerHolder Object
     */
    @Test
    public void test_create(){
        final PlayerHolder ph = new PlayerHolder();
        assertNotNull(ph);
    }

    /*
    Test the addition of a Player 1
     */
    @Test
    public void test_addPlayer1(){
        PlayerHolder.addPlayer("h1");
        PlayerHolder.addPlayer("h2");
        assertTrue(PlayerHolder.isNameTaken("h1"));
        assertTrue(PlayerHolder.isNameTaken("h2"));
    }

    /*
    Test the addition of a Player 2
     */
    @Test
    public void test_addPlayer2(){
        PlayerHolder.addPlayer("i1");
        PlayerHolder.addPlayer("i2");
        assertTrue(PlayerHolder.isNameTaken("i1"));
        assertTrue(PlayerHolder.isNameTaken("i2"));
    }

    /*
    Test to make the player is removed 1
     */
    @Test
    public void test_removePlayer1(){
        PlayerHolder.addPlayer("h1");
        PlayerHolder.addPlayer("h2");
        PlayerHolder.removePlayer("h1");
        PlayerHolder.removePlayer("h2");
        assertFalse(PlayerHolder.isNameTaken("h1"));
        assertFalse(PlayerHolder.isNameTaken("h2"));
    }

    /*
    Test to make the player is removed 2
     */
    @Test
    public void test_removePlayer2(){
        PlayerHolder.addPlayer("i1");
        PlayerHolder.addPlayer("i2");
        Player p1 = PlayerHolder.getPlayer("i1");
        Player p2 = PlayerHolder.getPlayer("i2");
        PlayerHolder.removePlayer(p1);
        PlayerHolder.removePlayer(p2);
        assertFalse(PlayerHolder.isNameTaken("i1"));
        assertFalse(PlayerHolder.isNameTaken("i2"));
    }

    /*
    Test to make sure the list being returned is not null
     */
    @Test
    public void test_list(){
        PlayerHolder.addPlayer("h1");
        PlayerHolder.addPlayer("h2");
        Player p2 = PlayerHolder.getPlayer("h2");
        assertNotNull(PlayerHolder.getList());
        assertNotNull(PlayerHolder.availablePlayerList(p2));

        Player p3 = PlayerHolder.getPlayer("not here");
        assertNull(p3);
    }

}
