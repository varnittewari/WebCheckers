package com.webcheckers.model;

import com.webcheckers.appl.Piece;
import com.webcheckers.appl.Message;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by joshs on 3/28/2018.
 */
@Tag("Model-tier")
public class ValidatorTest {


    @Test
    public void test_newValidator(){
        Validator v = new Validator();
        assertNotNull(v);
        assertTrue(v.getActiveColor()== Piece.color.RED);
        assertNotNull(v.getBoard());
        assertFalse(v.hasWon(Piece.color.RED));
    }

    @Test
    public void test_Turns(){
        Validator v = new Validator();
        v.changeTurn();
        assertTrue(v.getActiveColor()==Piece.color.WHITE);

        v.changeTurn();
        assertTrue(v.getActiveColor()==Piece.color.RED);

        v.submitTurn();

        v.resetTurn();
    }

    @Test
    public void test_Move(){
        Validator v = new Validator();
        Position p1 = new Position(5,0);
        Position p2 = new Position(4, 1);
        Position p3 = new Position(2,1);
        Position p4 = new Position(3, 2);
        Position p5 = new Position(4, 3);
        Position p6 = new Position(6, 1);
        Move mvw = new Move(p6, p5);
        assertTrue(v.validate(mvw).getType() == Message.Type.error);
        Move mv = new Move(p1, p2);
        assertTrue(v.validate(mv).getType() == Message.Type.info);
        Move mv2 = new Move(p2, p1);
        assertTrue(v.validate(mv2).getType() == Message.Type.error);
        Move mv3 = new Move(p3, p4);
        assertTrue(v.validate(mv3).getType() == Message.Type.error);
        v.changeTurn();
        v.resetTurn();
        Move mv4 = new Move(p3, p5);
        assertTrue(v.validate(mv4).getType() == Message.Type.error);
        assertTrue(v.validate(mv3).getType() == Message.Type.info);
    }

    @Test
    public void test_full_game(){
        Validator v = new Validator();
        Position p56 = new Position(5, 6);
        Position p45 = new Position(4,5);
        Position p27 = new Position(2,7);
        Position p36 = new Position(3, 6);
        Position p25 = new Position(2, 5);
        Position p34 = new Position(3,4);
        Position p52 = new Position(5,2);
        Position p41 = new Position(4, 1);
        Position p16 = new Position(1, 6);
        Position p54 = new Position(5,4);
        Position p43 = new Position(4,3);
        Position p61 = new Position(6, 1);
        Position p05 = new Position(0, 5);
        Position p07 = new Position(0, 7);
        Position p23 = new Position(2, 3);
        Position p32 = new Position(3, 2);
        Position p21 = new Position(2, 1);
        Position p30 = new Position(3, 0);
        Position p12 = new Position(1, 2);
        Position p01 = new Position(0, 1);
        Position p63 = new Position(6, 3);
        Position p74 = new Position(7, 4);
        Position p67 = new Position(6, 7);
        Position p03 = new Position(0,3);
        Position p14 = new Position(1,4);
        Position p76 = new Position(7, 6);
        Position p65 = new Position(6, 5);
        Position p10 = new Position(1, 0);
        Move r1 = new Move(p56, p45);
        Move w1 = new Move(p27, p36);
        Move r2w = new Move(p45, p56);
        Move r2 = new Move(p45, p27);
        Move w2 = new Move(p25, p34);
        Move r3 = new Move(p52, p41);
        Move w3 = new Move(p16, p25);
        Move r4 = new Move(p54, p43);
        Move w4 = new Move(p34, p52);
        Move r5 = new Move(p61, p43);
        Move w5 = new Move(p05, p16);
        Move r6 = new Move(p27, p05);
        Move w6 = new Move(p07, p16);
        Move r7 = new Move(p05, p27);
        Move w7 = new Move(p23, p34);
        Move r8 = new Move(p43, p32);
        Move w8 = new Move(p21, p43);
        Move r9 = new Move(p41, p30);
        Move w9 = new Move(p25, p36);
        Move r10 = new Move(p27, p05);
        Move r10a = new Move(p27, p45);
        Move r10b = new Move(p45, p23);
        Move r10c = new Move(p23, p05);
        Move r10w = new Move(p27, p16);
        Move w10 = new Move(p12, p23);
        Move r11 = new Move(p05, p16);
        Move w11 = new Move(p43, p52);
        Move r12 = new Move(p63, p41);
        Move w12 = new Move(p23, p34);
        Move r13 = new Move(p16, p25);
        Move w13 = new Move(p01, p12);
        Move r14 = new Move(p25, p43);
        Move w14 = new Move(p12, p23);
        Move r15 = new Move(p43, p32);
        Move w15 = new Move(p23, p34);
        Move r16 = new Move(p74, p63);
        Move w16 = new Move(p34, p45);
        Move r17 = new Move(p67, p56);
        Move w17 = new Move(p45, p67);
        Move r18 = new Move(p65, p54);
        Move w18 = new Move(p03, p14);
        Move r19 = new Move(p76, p65);
        Move w19 = new Move(p67, p76);
        Move r20 = new Move(p54, p45);
        Move w20a = new Move(p76, p54);
        Move w20b = new Move(p54, p36);
        Move r21 = new Move(p63, p54);
        Move w21 = new Move(p10, p21);
        Move r22 = new Move(p30, p12);
        Move w22 = new Move(p14, p23);
        Move r23 = new Move(p32, p14);
        Move w23 = new Move(p36, p25);
        Move r24 = new Move(p14, p36);
        assertTrue(v.validate(r1).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w1).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r2w).getType() == Message.Type.error);
        assertTrue(v.validate(r2).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w2).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r3).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w3).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r4).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w4).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r5).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w5).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r6).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w6).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r7).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w7).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r8).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w8).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r9).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w9).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r10w).getType() == Message.Type.error);
        assertTrue(v.validate(r10).getType() == Message.Type.error);
        assertTrue(v.validate(r10a).getType() == Message.Type.info);
        assertTrue(v.submitTurn().getType() == Message.Type.error); //Incomplete jump
        v.resetTurn();
        assertTrue(v.resetTurn().getType() == Message.Type.error);
        assertTrue(v.validate(r10a).getType() == Message.Type.info);
        assertTrue(v.validate(r10b).getType() == Message.Type.info);
        assertTrue(v.validate(r10c).getType() == Message.Type.info);
        v.submitTurn();

        assertTrue(v.validate(w10).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r11).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w11).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r12).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w12).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r13).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w13).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r14).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w14).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r15).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w15).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r16).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w16).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r17).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w17).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r18).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w18).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r19).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w19).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r20).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w20a).getType() == Message.Type.info);
        assertTrue(v.validate(w20b).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r21).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w21).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r22).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w22).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r23).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(w23).getType() == Message.Type.info);
        v.submitTurn();
        assertTrue(v.validate(r24).getType() == Message.Type.info);
        v.submitTurn();

        assertFalse(v.hasWon(Piece.color.WHITE));
        assertTrue(v.hasWon(Piece.color.RED));
    }
}
