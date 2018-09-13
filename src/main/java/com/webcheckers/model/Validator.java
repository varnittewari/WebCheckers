package com.webcheckers.model;

import com.webcheckers.appl.Message;
import com.webcheckers.appl.Piece;
import com.webcheckers.appl.Space;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class Validator {


    private Piece.color activeColor;

    private boolean firstMove = true;

    private final String MESSAGE_TYPE_INFO = "info";
    private final String MESSAGE_TYPE_ERROR = "error";

    // Message strings
    private final String SUBMISSION_SUCCESSFUL = "Turn completed.";
    private final String INCOMPLETE_JUMP = "You must continue the multi-jump.";
    private final String NO_BACKUP_AVAILABLE = "No proposed move to be undone.";
    private final String BACKUP_SUCCESSFUL = "Move undone.";

    private final String VALID = "Good move.";
    private final String INVALID = "Your piece cannot move there.";
    private final String WRONG_COLOR = "That is not one of your pieces.";

    private final String WRONG_DIRECTION = "Your piece cannot move in that direction.";
    private final String NO_CAPTURE = "No piece is captured in that jump.";
    private final String CAPTURE_ALLY = "You cannot jump your own pieces.";
    private final String JUMP_AVAILABLE = "You must make one of the available capture moves.";

    private CheckersModel model;

    private boolean redJumps;
    private boolean whiteJumps;
    private Stack<Move> moveStack;
    private List<Move> forcedMoves;


    public Validator(){
        activeColor = Piece.color.RED;
        model = new CheckersModel();
        moveStack = new Stack<>();
        redJumps = false;
        whiteJumps = false;
        forcedMoves = new LinkedList<>();
    }


    public Message validate(Move move){
        boolean jumpsAvailable = redJumps;
        Position start = move.getStart();
        Position end = move.getEnd();
        Piece p = model.getPiece(start);
        Piece.type p_type = p.getType();
        Piece.color p_color = p.getColor();
        if(p_color != activeColor){
            return new Message(WRONG_COLOR, MESSAGE_TYPE_ERROR);
        }
        if(p_color == Piece.color.WHITE){
            jumpsAvailable = whiteJumps;
            start = move.getStart();
            end = move.getEnd();
        }

        int row_diff = end.getRow() - start.getRow();
        int cell_diff = end.getCell() - start.getCell();

        if(!jumpsAvailable && firstMove){
            if(Math.abs(row_diff) == 1 && Math.abs(cell_diff) == 1){
                if(p_type == Piece.type.SINGLE){
                    if(p_color == Piece.color.RED){
                        if(row_diff == -1){
                            model.updateBoard(move);
                            moveStack.push(move);
                            firstMove = false;
                            return new Message(VALID, MESSAGE_TYPE_INFO);
                        }
                    }
                    else{
                        if(row_diff == 1){ //Edit, row_diff still negative since inverted
                            model.updateBoard(move);
                            moveStack.push(move);
                            firstMove = false;
                            return new Message(VALID, MESSAGE_TYPE_INFO);
                        }
                    }
                    return new Message(WRONG_DIRECTION, MESSAGE_TYPE_ERROR);
                }
                else{
                    model.updateBoard(move);
                    moveStack.push(move);
                    firstMove = false;
                    return new Message(VALID, MESSAGE_TYPE_INFO);
                }
            }
            else{
                return new Message(INVALID, MESSAGE_TYPE_ERROR);
            }
        }
        else if(jumpsAvailable && firstMove){
            if(Math.abs(row_diff) == 2 && Math.abs(cell_diff) == 2){
                int cap_row = (start.getRow() + end.getRow())/2;
                int cap_cell = (start.getCell() + end.getCell())/2;
                Position captured = new Position(cap_row, cap_cell);
                if(model.getPiece(captured) == null){
                    return new Message(NO_CAPTURE, MESSAGE_TYPE_ERROR);
                }
                if(model.getPiece(captured).getColor() != p_color){
                    model.updateBoard(move);
                    moveStack.push(move);
                    firstMove = false;
                    updateForcedMoves(move);
                    return new Message(VALID, MESSAGE_TYPE_INFO);
                }
                else{
                    return new Message(CAPTURE_ALLY, MESSAGE_TYPE_ERROR);
                }
            }
            else{
                return new Message(JUMP_AVAILABLE, MESSAGE_TYPE_ERROR);
            }
        }
        else {
            if(forcedMoves.contains(move)){
                model.updateBoard(move);
                moveStack.push(move);
                updateForcedMoves(move);
                return new Message(VALID, MESSAGE_TYPE_INFO);
            }
            else{
                return new Message(INCOMPLETE_JUMP, MESSAGE_TYPE_ERROR);
            }
        }
    }


    public Space[][] getBoard(){
        return model.getBoard();
    }


    public Piece.color getActiveColor(){
        return activeColor;
    }


    public boolean hasWon(Piece.color color){
        return model.hasWon(color);
    }


    public void changeTurn(){
        if(activeColor == Piece.color.RED){
            activeColor = Piece.color.WHITE;
        }
        else{
            activeColor = Piece.color.RED;
        }
    }


    public Message resetTurn(){
        if(moveStack.isEmpty()){
            return new Message(NO_BACKUP_AVAILABLE, MESSAGE_TYPE_ERROR);
        }
        model.reverseMove(moveStack.pop());
        if(moveStack.empty()){
            firstMove = true;
            updateJumpsAvailable();
            if(!forcedMoves.isEmpty()){
                forcedMoves = new LinkedList<>();
            }
        }
        else{
            firstMove = false;
            updateForcedMoves(moveStack.peek());
        }
        return new Message(BACKUP_SUCCESSFUL, MESSAGE_TYPE_INFO);
    }


    public Message submitTurn(){
        if(forcedMoves.isEmpty()){
            changeTurn();
            firstMove = true;
            updateJumpsAvailable();
            while(!moveStack.empty()){
                moveStack.pop();
            }
            return new Message(SUBMISSION_SUCCESSFUL, MESSAGE_TYPE_INFO);
        }
        return new Message(INCOMPLETE_JUMP, MESSAGE_TYPE_ERROR);
    }


    private void updateForcedMoves(Move move){
        Position end = move.getEnd();
        PieceMoves pm;
        Piece.type p_type = model.getPiece(end).getType();
        Piece.color p_color = model.getPiece(end).getColor();

        if(p_type == Piece.type.SINGLE){
            if(p_color == Piece.color.RED){
                pm = new SingleRedMoves(model);
            }
            else{
                pm = new SingleWhiteMoves(model);
            }
        }
        else{
            if(p_color == Piece.color.RED){
                pm = new KingRedMoves(model);
            }
            else{
                pm = new KingWhiteMoves(model);
            }
        }

        forcedMoves = pm.getJumpMoves(end);
    }


    private void updateJumpsAvailable(){
        redJumps = false;
        whiteJumps = false;
        int r = 0;
        int c = 0;
        LinkedList<Move> jumpMoves;
        PieceMoves pm;
        Position pos;
        Piece piece;
        while(!redJumps || !whiteJumps){
            pos = new Position(r,c);
            piece = model.getPiece(pos);
            if(piece != null){
                if(piece.getColor() ==  Piece.color.RED){
                    if(piece.getType() == Piece.type.SINGLE){
                        pm = new SingleRedMoves(model);
                    }
                    else{
                        pm = new KingRedMoves(model);
                    }
                    jumpMoves = pm.getJumpMoves(pos);
                    if(jumpMoves.size() > 0){
                        redJumps = true;
                    }
                }
                else{
                    if(piece.getType() == Piece.type.SINGLE){
                        pm = new SingleWhiteMoves(model);
                    }
                    else{
                        pm = new KingWhiteMoves(model);
                    }
                    jumpMoves = pm.getJumpMoves(pos);
                    if(jumpMoves.size() > 0){
                        whiteJumps = true;
                    }
                }
            }

            // Update the coordinates and break if necessary
            ++c;
            if(c == 8){
                ++r;
                if(r == 8){
                    break;
                }
                c = 0;
            }
        }
    }
}
