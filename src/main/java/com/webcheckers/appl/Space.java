package com.webcheckers.appl;

/**
 * Space on the board.
 */
public class Space{

    /* The cell index */
    private int cellIdx;
    /* The piece on this Space */
    private Piece piece;
    /* True if dark, false if not */
    private boolean valid;

    /**
     * Makes a new space
     * @param idx - Space's index
     */
    public Space(int idx, Piece piece, boolean valid){
        cellIdx = idx;
        this.valid = valid;
        this.piece = piece;
    }

    /**
     * Gets the cell index of this Space
     * @return the cell index
     */
    public int getCellIdx(){
        return cellIdx;
    }

    /**
     * Finds if this space is valid for a piece
     * @return true if valid (dark) and no piece is on it
     */
    public boolean isValid(){
        return (valid && (piece==null));
    }

    /**
     * Gets the piece on this Space
     * @return Piece if a piece exists, null otherwise
     */
    public Piece getPiece(){
        return piece;
    }

    public Piece removePiece(){
        Piece p = piece;
        piece = null;
        return p;
    }


    public boolean placePiece(Piece piece){
        if(this.piece == null){
            this.piece = piece;
            return true;
        }
        return false;
    }


    public Space copy(){
        if(piece != null){
            return new Space(cellIdx, piece.copy(), valid);
        }
        return new Space(cellIdx, null, valid);
    }


    @Override
    public boolean equals(Object obj){
        if(obj instanceof Space){
            Space temp = (Space) obj;

            if(piece == null && temp.piece == null){
                if(cellIdx == temp.cellIdx){
                    return true;
                }
            }
            else if(piece == null && temp.piece != null){
                return false;
            }
            else if(piece != null && temp.piece == null){
                return false;
            }
            else{
                if(cellIdx == temp.cellIdx && piece.equals(temp.piece)){
                    return true;
                }
            }
        }
        return false;
    }

}
