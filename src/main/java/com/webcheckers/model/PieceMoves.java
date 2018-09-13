package com.webcheckers.model;

import com.webcheckers.appl.Space;

import java.util.LinkedList;


public abstract class PieceMoves {


    private Space[][] board;

    public abstract LinkedList<Move> getStandardMoves(Position origin);

    public abstract LinkedList<Move> getJumpMoves(Position origin);
}
