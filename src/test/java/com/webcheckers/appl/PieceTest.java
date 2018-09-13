package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("Application-tier")

/**
 * Created by Varnit Tewari on 3/18/2018.
 */
public class PieceTest {


    @Test
    public void test_cons(){
        Piece one=new Piece("SINGLE", "RED");
        Piece two=new Piece("SINGLE", "WHITE");
        Piece three=new Piece("KING", "RED");
        Piece four=new Piece("KING", "WHITE");

        assertEquals(Piece.type.SINGLE,one.getType());
        assertEquals(Piece.type.SINGLE,two.getType());
        assertEquals(Piece.type.KING,three.getType());
        assertEquals(Piece.type.KING,four.getType());
        assertEquals(Piece.color.RED,one.getColor());
        assertEquals(Piece.color.WHITE,two.getColor());
        assertEquals(Piece.color.RED,three.getColor());
        assertEquals(Piece.color.WHITE,four.getColor());
    }

    @Test
    public void test_equals(){
        Piece one=new Piece("SINGLE", "RED");
        Piece two=new Piece("SINGLE", "RED");
        Piece three=new Piece("KING", "RED");
        Piece four=new Piece("KING", "WHITE");
        Piece five=new Piece("SINGLE", "RED");
        Piece six=new Piece("KING", "RED");

        assertSame(true, one.equals(two));
        assertSame(false, three.equals(four));
        assertSame(false, five.equals(six));
    }


    @Test
    public void test_crowning(){
        String message = "%s %s piece not crowned properly.";

        Piece one=new Piece("SINGLE", "RED");
        Piece two=new Piece("SINGLE", "WHITE");
        Piece three=new Piece("KING", "RED");
        Piece four=new Piece("KING", "WHITE");

        one.crown();
        two.crown();
        three.crown();
        four.crown();

        Piece red_king = new Piece("KING", "RED");
        Piece white_king = new Piece("KING", "WHITE");

        assertEquals(one, red_king, String.format(message, "SINGLE", "RED"));
        assertEquals(two, white_king, String.format(message, "SINGLE", "WHITE"));
        assertEquals(three, red_king, String.format(message, "KING", "RED"));
        assertEquals(four, white_king, String.format(message, "KING", "WHITE"));
    }

    @Test
    public void test_copy(){
        Piece one = new Piece("SINGLE", "RED");
        Piece two = new Piece("KING", "RED");
        Piece three = new Piece("SINGLE", "WHITE");
        Piece four = new Piece("KING", "WHITE");

        Piece one_c = one.copy();
        assertTrue(one.getColor()==one_c.getColor() && one.getType() == one_c.getType());
        Piece two_c = two.copy();
        assertTrue(two.getColor()==two_c.getColor() && two.getType() == two_c.getType());
        Piece three_c = three.copy();
        assertTrue(three.getColor()==three_c.getColor() && three.getType() == three_c.getType());
        Piece four_c = four.copy();
        assertTrue(four.getColor()==four_c.getColor() && four.getType() == four_c.getType());
    }
}
