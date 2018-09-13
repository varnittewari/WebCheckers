package com.webcheckers.appl;


import java.util.Iterator;

public class Row implements Iterable<Space>{

    private static int NUM_SPACES = 8;

    private int index;

    private Space[] row = new Space[NUM_SPACES];


    public Row(Space[] spaces,int index, String color){

        this.index = index;

        if(color.equals("RED")){
            for(int i = 0; i < NUM_SPACES; i++){
                Space s = spaces[i];
                row[i] = s.copy();
            }
        }
        else{
            int back = NUM_SPACES-1;
            for(int i = 0; i < NUM_SPACES; i++){
                Space s = spaces[back];
                row[i] = s.copy();
                --back;
            }
        }
    }


    /**
     * Constructor for Row. Based on the row index and which color perspective
     * the board should be made from, spaces will be initialized with the
     * proper Piece objects.
     * @param index
     * @param color
     */
    public Row(int index, String color){
        this.index = index;
        row = new Space[NUM_SPACES];

        boolean black;
        switch(index%2){
            case 0:
                black = false;
                break;
            default:
                black = true;
                break;
        }

        String anticolor;
        if(color.equals("RED")){
            anticolor = "WHITE";
        }
        else{
            anticolor = "RED";
        }

        for( int s = 0; s < NUM_SPACES; s++ ){
            Piece piece = null;
            if(index < 3){
                piece = new Piece("SINGLE", anticolor);
            }
            else if(index > 4){
                piece = new Piece("SINGLE", color);
            }

            row[s] = new Space(s, piece, black);

            black = !black;
        }

    }

    public int getIndex() {
        return index;
    }

    @Override
    public Iterator<Space> iterator() {
        Iterator<Space> iter = new Iterator<Space>() {

            private int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < NUM_SPACES;
            }

            @Override
            public Space next() {
                return row[counter++];
            }
        };
        return iter;
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Row){
            Row temp = (Row) obj;
            int i = 0;
            for( Space s : temp.row ){
                if( !s.equals(row[i])){
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
    }
}
