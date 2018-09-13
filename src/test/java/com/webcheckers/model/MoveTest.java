package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by joshs on 3/28/2018.
 */
@Tag("Model-tier")
public class MoveTest {

    @Test
    public void test_newWithPosition(){
        Position p1 = new Position(1, 1);
        Position p2 = new Position(2, 2);
        Move mov = new Move(p1, p2);

        assertEquals(p1, mov.getStart());
        assertEquals(p2, mov.getEnd());
        assertNull(mov.getRemoved());
    }

    @Test
    public void test_newWithNums(){
        int sr = 1;
        int sc = 2;
        int er = 3;
        int ec = 4;
        Move mov = new Move(sr, sc, er, ec);

        assertEquals(sr, mov.getStart().getRow());
        assertEquals(sc, mov.getStart().getCell());
        assertEquals(er, mov.getEnd().getRow());
        assertEquals(ec, mov.getEnd().getCell());
        assertNull(mov.getRemoved());
    }

    @Test
    public void test_Invert(){
        Position p1 = new Position(1, 2);
        Position p2 = new Position(3, 4);
        Position i1 = new Position(6, 5);
        Position i2 = new Position(4, 3);
        Move mov = new Move(p1, p2);
        mov.invertMove();
        assertEquals(i1.getRow(), mov.getStart().getRow());
        assertEquals(i1.getCell(), mov.getStart().getCell());
        assertEquals(i2.getRow(), mov.getEnd().getRow());
        assertEquals(i2.getCell(), mov.getEnd().getCell());

        mov.invertRow();
        assertEquals(p1.getRow(), mov.getStart().getRow());
        assertEquals(i1.getCell(), mov.getStart().getCell());
    }

    @Test
    public void test_equals(){
        Position p1 = new Position(1,2);
        Position p2 = new Position(1, 2);
        Position p3 = new Position(2, 4);
        Position p4 = new Position(2, 4);
        Position p5 = new Position(3, 5);
        String notAMove = "Wait, this isn't a move!";
        Move m1 = new Move(p1, p3);
        Move m2 = new Move(p2, p4);
        Move m3 = new Move(p1, p5);
        assertTrue(m1.equals(m2));
        assertFalse(m1.equals(m3));
        assertFalse(m1.equals(notAMove));
    }

    @Test
    public void test_toString(){
        Position p1 = new Position(1, 2);
        Position p2 = new Position(2, 4);
        Move m1 = new Move(p1, p2);
        String mov = m1.toString();
        assertNotNull(mov);
    }


}
