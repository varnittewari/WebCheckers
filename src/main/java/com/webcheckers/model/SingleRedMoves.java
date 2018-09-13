package com.webcheckers.model;


import com.webcheckers.appl.Piece;

import java.util.LinkedList;


public class SingleRedMoves extends PieceMoves {

    private CheckersModel model;

    private Piece.color opp_color = Piece.color.WHITE;


    public SingleRedMoves(CheckersModel model){
        this.model = model;
    }


    public LinkedList<Move> getStandardMoves(Position origin){
        return new LinkedList<>();
    }


    public LinkedList<Move> getJumpMoves(Position origin){
        LinkedList<Move> moves = new LinkedList<>();

        int row = origin.getRow();
        int cell = origin.getCell();

        if(row > 1){
            if(cell > 1){
                Position target = new Position(row-2, cell-2);
                Position jumped = new Position(row-1, cell-1);
                if(model.getPiece(target) == null && model.getPiece(jumped) != null){
                    if(model.getPiece(jumped).getColor() == opp_color) {
                        moves.add(new Move(origin, target));
                    }
                }
            }
            if(cell < 6){
                Position target = new Position(row-2, cell+2);
                Position jumped = new Position(row-1, cell+1);
                if(model.getPiece(target) == null && model.getPiece(jumped) != null){
                    if(model.getPiece(jumped).getColor() == opp_color) {
                        moves.add(new Move(origin, target));
                    }
                }
            }
        }

        return moves;
    }
}
