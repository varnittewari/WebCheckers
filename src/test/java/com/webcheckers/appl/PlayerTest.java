package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


/**
 * @author Josh Abrams
 * Test class for Player. Verifies proper Player creation and cases where no params specified.
 */
@Tag ("Application-tier")
public class PlayerTest {

    /**
     * Tests player creation with no name specified.
     */
    @Test
    public void new_no_name(){
        String name = null;
        int num = 0;
        Player test = new Player(name, num);

        assertNotNull(test);
        assertNull(test.getName());
        assertEquals(num, test.getPlayerID());
    }

    /**
     * Tests proper player creation. Correct attributes set.
     */
    @Test
    public void new_player(){
        String name = "test";
        int num = 0;
        Player test = new Player(name, num);

        assertNotNull(test);
        assertNotNull(test.getName());
        assertEquals(name, test.getName());
        assertEquals(num, test.getPlayerID());
    }

    /**
     * Tests proper player creation, showing two players are equal with same parameters.
     */
    @Test
    public void same_players(){
        String name = "test";
        int num = 1;
        Player test1 = new Player(name, num);
        Player test2 = new Player(name, num);

        assertNotNull(test1);
        assertNotNull(test2);
        assertSame(test1.getName(), test2.getName());
        assertSame(test1.getPlayerID(), test2.getPlayerID());
    }

    @Test
    public void test_gameID(){
        Player test1 = new Player("name", 1);
        test1.assignGame(1);
        assertTrue(test1.getGameID()==1);
        assertTrue(test1.inGame());
        test1.gameEnded();
        assertFalse(test1.inGame());
    }
}
