package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by joshs on 3/28/2018.
 */
@Tag("Model-tier")
public class CheckersModelTest {

    @Test
    public void test_createNew(){
        CheckersModel cm = new CheckersModel();
        assertNotNull(cm);
        assertNotNull(cm.getBoard());
    }

    @Test
    public void test_createBase(){
        CheckersModel cm = new CheckersModel();
        Position p1 = new Position(1,0);
        Position p2 = new Position(2, 1);
        Move mv = new Move(p1, p2);
        cm.updateBoard(mv);
        assertNotNull(cm.getBoard()[2][1].getPiece());

        //CheckersModel cm2 = new CheckersModel(cm);
        assertNotNull(cm.getBoard()[2][1].getPiece());
    }

    // TODO: 3/28/2018 Need to make test_Won method. Play out a game to winning state, then see if won?

}
