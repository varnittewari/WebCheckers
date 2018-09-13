package com.webcheckers.appl;

/**
 * Piece Object
 */
public class Piece {

    /* Piece can be SINGLE or KING */
    public enum type{SINGLE, KING}
    /* Piece can be RED or WHITE */
    public enum color{RED, WHITE}

    /* This piece's type */
    private type pieceType;

    /* This piece's color */
    private color pieceColor;

    /**
     * Piece Constructor
     * @param T - The Type
     * @param C - The Color
     */
    public Piece(String T, String C){
        if(T.equals("SINGLE")){
            pieceType = type.SINGLE;
        } else if(T.equals("KING")){
            pieceType = type.KING;
        }

        if(C.equals("RED")){
            pieceColor = color.RED;
        } else if(C.equals("WHITE")){
            pieceColor = color.WHITE;
        }
    }

    /**
     * Gets this piece's type.
     * @return SINGLE or KING
     */
    public type getType(){
        return pieceType;
    }

    /**
     * Gets this piece's color.
     * @return RED or WHITE
     */
    public color getColor(){
        return pieceColor;
    }


    /**
     * Change a piece of type SINGLE to type KING
     */
    public void crown() {
        if (this.pieceType == type.SINGLE) {
            this.pieceType = type.KING;
        }
    }


    public Piece copy(){
        if(this.pieceType == type.SINGLE){
            if(this.pieceColor == color.RED){
                return new Piece("SINGLE", "RED");
            }
            else{
                return new Piece("SINGLE", "WHITE");
            }
        }
        else{
            if(this.pieceColor == color.RED){
                return new Piece("KING", "RED");
            }
            else{
                return new Piece("KING", "WHITE");
            }
        }
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Piece){
            Piece temp = (Piece) obj;
            if(pieceType == temp.pieceType && pieceColor == temp.pieceColor){
                return true;
            }
        }
        return false;
    }
}
