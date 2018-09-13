package com.webcheckers.model;
import com.google.gson.Gson;
import com.webcheckers.appl.Piece;


public class Move {


    private Position start;


    private Position end;


    private Piece removed;


    private boolean crown;


    public Move(Position start, Position end){
        this.start = start;
        this.end = end;
        removed = null;
        crown = false;
    }


    public Move(int start_row, int start_cell, int end_row, int end_cell){
        start = new Position(start_row, start_cell);
        end = new Position(end_row, end_cell);
        removed = null;
        crown = false;
    }


    public Position getStart(){
        return start;
    }


    public Position getEnd(){
        return end;
    }


    public void setCrowning(){
        crown = true;
    }


    public boolean Crowning(){
        return crown;
    }


    public void setRemoved(Piece p){
        removed = p;
    }


    public Piece getRemoved(){
        return removed;
    }


    public void invertMove(){
        start = new Position(7-start.getRow(), 7-start.getCell());
        end = new Position(7-end.getRow(), 7-end.getCell());
    }

    /* Temporary workaround to wrong position being grabbed? */
    public void invertRow(){
        start = new Position(7-start.getRow(), start.getCell());
        end = new Position(7-end.getRow(), end.getCell());
    }

    public static Move fromJson(String json ){
        Gson gson = new Gson();
        return  gson.fromJson(json, Move.class);
    }

    public String toString() {
      return "Movement " + start.toString();
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Move){
            Move m = (Move) obj;
            return (start.equals(m.start) && end.equals(m.end));
        }
        return false;
    }
}
