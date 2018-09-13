package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Application-tier")
public class BoardViewTest {

    private BoardView CuT;

    private static final int NUM_ROWS = 8;


    @Test
    public void board_created(){
        CuT = new BoardView("WHITE");
        int rows_count = 0;
        for(Row r : CuT){
            assertNotNull(r, String.format("Row %d is null.", rows_count));
            ++rows_count;
        }
        assertEquals(NUM_ROWS, rows_count, "Incorrect number of rows in board.");

        CuT = new BoardView("RED");
        rows_count = 0;
        for(Row r : CuT){
            assertNotNull(r, String.format("Row %d is null.", rows_count));
            ++rows_count;
        }
        assertEquals(NUM_ROWS, rows_count, "Incorrect number of rows in board.");
    }


    @Test
    public void equals_test(){
        BoardView testA = new BoardView("WHITE");
        BoardView testB = new BoardView("WHITE");
        BoardView testC = new BoardView("RED");
        BoardView testD = new BoardView("RED");
        assertEquals(testA, testB);
        assertEquals(testC, testD);
        assertNotEquals(testB, testC);
        assertNotEquals(testB, null);
    }

    @Test
    public void test_creation(){
        Space[][] s = new Space[8][8];
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                s[i][j] = new Space(j, null, true);
            }
        }
        BoardView a = new BoardView(s, "RED");
        BoardView b = new BoardView(s, "WHITE");
        assertNotNull(a);
        assertNotNull(b);
    }
}
