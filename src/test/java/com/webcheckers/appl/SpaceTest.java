package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.appl.Space;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@Tag ("Application-tier")
public class SpaceTest{

    @Test
    public void test_valid(){
        Space spaceOne = new Space(1, null, true);
        Space spaceTwo = new Space(1, null, false);
        assertTrue(spaceOne.isValid());
        assertFalse(spaceTwo.isValid());
    }

    @Test
    public void test_identicalSpaces(){
      Space spaceOne = new Space(1,null,true);
      Space spaceTwo = new Space(1,null,true);
      assertTrue(spaceOne.equals( spaceTwo));
    }

    @Test
    public void test_differentSpaces(){
      Space spaceOne = new Space(1,null,true);
      Space spaceTwo = new Space(2,null,true);
      assertFalse(spaceOne.equals( spaceTwo));
    }

    @Test
    public void test_differentSomePieces(){
        Space spaceOne = new Space(1, null, true);
        Piece piece = new Piece("SINGLE", "RED");
        Space spaceTwo = new Space(2, piece, true);
        assertFalse(spaceOne.equals(spaceTwo));
        assertFalse(spaceTwo.equals(spaceOne));
    }

    @Test
    public void test_identicalSpacesWithPieces(){
      Piece piece1 = new Piece("SINGLE","RED");
      Piece piece2 = new Piece("SINGLE","RED");
      Space spaceOne = new Space(1,piece1,true);
      Space spaceTwo = new Space(1,piece2,true);
      assertTrue(spaceOne.equals( spaceTwo));
    }
    @Test
    public void test_differentSpacesWithPieces(){
      Piece piece1 = new Piece("SINGLE","RED");
      Piece piece2 = new Piece("SINGLE","BLACK");
      Space spaceOne = new Space(1,piece1,true);
      Space spaceTwo = new Space(1,piece2,true);
      assertFalse(spaceOne.equals( spaceTwo));
    }

    @Test
    public void test_copy(){
        Space s1 = new Space(1, null, true);
        Piece p1 = new Piece("SINGLE", "RED");
        Space s2 = new Space(1, p1, true);

        Space s1c = s1.copy();
        assertTrue(s1.equals(s1c));
        Space s2c = s2.copy();
        assertTrue(s2.equals(s2c));
    }

    @Test
    public void test_placing(){
        Piece p1 = new Piece("SINGLE", "RED");
        Space s1 = new Space(1, null, true);
        assertTrue(s1.placePiece(p1));
        assertFalse(s1.placePiece(p1));

        Piece rm = s1.removePiece();
        assertTrue(rm.equals(p1));
    }
}
