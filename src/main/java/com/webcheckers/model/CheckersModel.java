package com.webcheckers.model;


import com.webcheckers.appl.Piece;
import com.webcheckers.appl.Space;

public class CheckersModel {


    private int dim = 8;

    private Space[][] Board;

    private int reds;

    private int whites;


    CheckersModel(){
        Board = new Space[dim][dim];
        boolean start_black = false;
        for(int row = 0; row < dim; row++){
            boolean black = start_black;
            for(int cell = 0; cell < dim; cell++){
                Piece piece = null;
                if(row < 3 && black){
                    piece = new Piece("SINGLE", "WHITE");
                    ++whites;
                }
                else if(row > 4 && black){
                    piece = new Piece("SINGLE", "RED");
                    ++reds;
                }
                Board[row][cell] = new Space(cell, piece, black);
                black = !black;
            }
            start_black = !start_black;
        }
    }


    public Space[][] getBoard(){
        return Board;
    }


    boolean hasWon(Piece.color color){
        if(color == Piece.color.RED){
            return whites == 0;
        }
        else{
            return reds == 0;
        }
    }


    Piece getPiece(Position position){
        return Board[position.getRow()][position.getCell()].getPiece();
    }


    void updateBoard(Move move){
        Position start = move.getStart();
        int start_row = start.getRow();
        int start_cell = start.getCell();
        Position end = move.getEnd();
        int end_row = end.getRow();
        int end_cell = end.getCell();
        Piece p = Board[start_row][start_cell].removePiece();
        Board[end_row][end_cell].placePiece(p);
        if(p.getColor() == Piece.color.RED && end_row == 0){
            p.crown();
            move.setCrowning();
        }
        else if(p.getColor() == Piece.color.WHITE && end_row == dim-1){
            p.crown();
            move.setCrowning();
        }
        if(Math.abs(start_row - end_row) == 2){
            Piece removed = Board[(start_row + end_row)/2][(start_cell+end_cell)/2].removePiece();
            move.setRemoved(removed);
            if(removed.getColor() == Piece.color.RED){
                --reds;
            }
            else{
                --whites;
            }
        }
    }


    void reverseMove(Move move){
        Position start = move.getStart();
        Position end = move.getEnd();
        int end_row = start.getRow();
        int end_cell = start.getCell();
        int start_row = end.getRow();
        int start_cell = end.getCell();

        Piece p = Board[start_row][start_cell].removePiece();
        Piece.color c = p.getColor();
        if(move.Crowning()){
            switch(c){
                case RED:
                    p = new Piece("SINGLE", "RED");
                    break;
                case WHITE:
                    p = new Piece("SINGLE", "WHITE");
                    break;
            }
        }
        Board[end_row][end_cell].placePiece(p);
        if(Math.abs(start_row - end_row) == 2){
            Piece added = move.getRemoved();
            Board[(start_row + end_row)/2][(start_cell+end_cell)/2].placePiece(added);
            if(added.getColor() == Piece.color.RED){
                ++reds;
            }
            else{
                ++whites;
            }
        }
    }
}
