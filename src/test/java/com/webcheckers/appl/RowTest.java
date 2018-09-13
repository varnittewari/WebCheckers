package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag("Application-tier")
public class RowTest {

    private Row CuT;

    private static final int NUM_SPACES = 8;

    @Test
    public void index_test(){
        Row row = new Row(1, "RED");
        assertEquals(1, row.getIndex());
    }

    @Test
    public void white_rows(){
        for(int i = 0; i < NUM_SPACES; i++){
            CuT = new Row(i, "WHITE");
            int space_count = 0;
            for(Space s : CuT){
                assertNotNull(s, String.format("Space %d is null.", space_count));
                ++space_count;
            }
            assertEquals(NUM_SPACES, space_count, "Incorrect number of spaces in row.");
        }
    }


    @Test
    public void red_rows(){
        for(int i = 0; i < NUM_SPACES; i++){
            CuT = new Row(i, "RED");
            int space_count = 0;
            for(Space s : CuT){
                assertNotNull(s, String.format("Space %d is null.", space_count));
                ++space_count;
            }
            assertEquals(NUM_SPACES, space_count, "Incorrect number of spaces in row.");
        }

    }


    @Test
    public void equals_test(){
        Row white_zero = new Row(0, "WHITE");
        Row white_seven = new Row(7, "WHITE");
        Row red_zero = new Row(0, "RED");
        Row red_seven = new Row(7, "RED");
        assertNotEquals(white_zero, white_seven);
        assertNotEquals(red_zero, red_seven);
        assertEquals(white_seven, red_zero);
        assertNotEquals(white_seven, null);
    }


    @Test
    public void spaces_test(){
        Row white_zero = new Row(0, "WHITE");
        boolean piece = false;
        int idx = 0;
        for(Space s : white_zero){
            assertEquals(s.getCellIdx(), idx);
            if(idx%2 == 1){
                assert(s.getPiece().getColor() == Piece.color.RED);
            }
            piece = !piece;
            idx++;
        }

        Row white_seven = new Row(7, "WHITE");
        piece = true;
        idx = 0;
        for(Space s : white_seven){
            assertEquals(s.getCellIdx(), idx);
            if(idx%2 == 0){
                assert(s.getPiece().getColor() == Piece.color.WHITE);
            }
            piece = !piece;
            idx++;
        }

        Row red_zero = new Row(0, "RED");
        piece = false;
        idx = 0;
        for(Space s : red_zero){
            assertEquals(s.getCellIdx(), idx);
            if(idx%2 == 1){
                assert(s.getPiece().getColor() == Piece.color.WHITE);
            }
            piece = !piece;
            idx++;
        }

        Row red_seven = new Row(7, "RED");
        piece = true;
        idx = 0;
        for(Space s : red_seven){
            assertEquals(s.getCellIdx(), idx);
            if(idx%2 == 0){
                assert(s.getPiece().getColor() == Piece.color.RED);
            }
            piece = !piece;
            idx++;
        }
    }

    @Test
    public void test_creation(){
        Space s1 = new Space(1, null, true);
        Space s2 = new Space(2, null, true);
        Space s3 = new Space(1, null, true);
        Space s4 = new Space(2, null, true);
        Space s5 = new Space(1, null, true);
        Space s6 = new Space(2, null, true);
        Space s7 = new Space(1, null, true);
        Space s8 = new Space(2, null, true);
        Space[] s = {s1, s2, s3, s4, s5, s6, s7, s8};
        Row r = new Row(s, 1, "WHITE");
        Row r2 = new Row(s, 2, "RED");
        assertNotNull(r);
        assertNotNull(r2);
    }
}
