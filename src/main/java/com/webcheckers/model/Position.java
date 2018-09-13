package com.webcheckers.model;
import com.google.gson.Gson;


public class Position {


    private int row;


    private int cell;


    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }


    public int getRow(){
        return row;
    }


    public int getCell(){
        return cell;
    }

    public String toString() {
      return "Position " + this.row + " , " + this.cell;
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Position){
            Position p = (Position) obj;
            return (row == p.row && cell == p.cell);
        }
        return false;
    }
}
