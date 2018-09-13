package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by joshs on 3/28/2018.
 */
@Tag("Model-tier")
public class PositionTest{

    @Test
    public void test_create(){
        Position pos = new Position(1,0);
        assertEquals(0, pos.getCell());
        assertEquals(1, pos.getRow());
    }

    @Test
    public void test_equals(){
        Position pos1 = new Position(1, 0);
        Position pos2 = new Position(1, 0);
        Position pos3 = new Position(2, 1);
        String notAPos = "Wait, this isn't a Position!";
        assertSame(pos1.getRow(), pos2.getRow());
        assertSame(pos1.getCell(), pos2.getCell());
        assertTrue(pos1.equals(pos2));
        assertFalse(pos1.equals(pos3));
        assertFalse(pos1.equals(notAPos));
    }

    @Test
    public void test_toString(){
        Position pos1 = new Position(1, 0);
        String received = pos1.toString();
        assertNotNull(received);
    }

}
