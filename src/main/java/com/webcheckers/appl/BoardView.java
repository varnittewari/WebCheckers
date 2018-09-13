package com.webcheckers.appl;


import java.util.Iterator;


public class BoardView implements Iterable<Row>{


    private static final int NUM_ROWS = 8;


    private Row[] board = new Row[NUM_ROWS];


    public BoardView(Space[][] board, String color){
        if(color.equals("RED")){
            for(int r = 0; r < NUM_ROWS; r++){
                this.board[r] = new Row(board[r],r,"RED");
            }
        }
        else{
            int back = NUM_ROWS-1;
            for(int r = 0; r < NUM_ROWS; r++){
                this.board[r] = new Row(board[back],r, "WHITE");
                --back;
            }
        }
    }


    public BoardView(String color) {
        for (int r = 0; r < NUM_ROWS; r++) {
            board[r] = new Row(r, color);
        }
    }


    @Override
    public Iterator<Row> iterator() {
        return new Iterator<Row>() {

            private int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < NUM_ROWS;
            }

            @Override
            public Row next() {
                return board[counter++];
            }
        };
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof BoardView){
            BoardView bv = (BoardView) obj;
            if(bv.board != null){
                int i = 0;
                for(Row r : bv.board){
                    if( !r.equals(board[i])){
                        return false;
                    }
                    i++;
                }
                return true;
            }
        }
        return false;
    }
}
