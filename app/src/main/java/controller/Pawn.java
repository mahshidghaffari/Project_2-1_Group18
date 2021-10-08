package controller;
import java.util.ArrayList;

import javax.swing.*;

public class Pawn extends Piece{


    public Pawn(boolean white){
        super.setWhite(white);
        super.pieceName = "Pawn";
        super.setValue(1.0);
    }

    /*
    method legalMovesCalc
    @param cb is the current state of the chessboard
    @return ArrayList<Square> an array of squares describing the legal squares this piece can go to
     */
    public ArrayList<Square> getLegalMoves(ChessBoard cb){
        ArrayList<Square> legalMoves = new ArrayList<>();
        int x = this.getCurrentPosition().getXPos();
        int y = this.getCurrentPosition().getYPos();
        Square[][] board = cb.getBoard();
        if(this.isWhite()){
            if(y-1 >= 0){
                if(!board[y-1][x].isTakenSquare()){ // Move 1 square forward for white
                    legalMoves.add(board[y-1][x]);  
                }
                if(y == 6){
                    if(!board[y-1][x].isTakenSquare() && !board[y-2][x].isTakenSquare()){ // Move 2 squares forward for white
                        legalMoves.add(board[y-2][x]);
                    }
                }
                
                if(x+1 <= 7){ //Capture to the right for white
                    if(board[y-1][x+1].isTakenSquare()){
                        if(!this.sameTeam(board[y-1][x+1].getPieceOnSq())){
                            legalMoves.add(board[y-1][x+1]);
                        }
                    }
                }
                if(x-1 >= 0){ //Capture to the left for white
                    if(board[y-1][x-1].isTakenSquare()){
                        if(!this.sameTeam(board[y-1][x-1].getPieceOnSq())){
                            legalMoves.add(board[y-1][x-1]);
                        }
                    }
                }
                if(y==3){   //En Passant for white : only works if the pawn is on y = 3
                    if(x+1 < 8){ //En Passant to the right for white
                        Square rSq = board[y][x+1];
                        if(rSq.isTakenSquare()){ //If the piece is a pawn and it is black

                            if(rSq.getPieceOnSq().pieceName == "Pawn" && !rSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0].getYPos() == 1 && lastMove[1].getYPos() == 3){        
                                    legalMoves.add(board[y-1][x+1]);
                                    System.out.println("to the right was added!!");
                                }
                                
                            }
                        }
                    }
                    if(x-1 >= 0){ //En passant to the left for white
                        Square lSq = board[y][x-1];
                        if(lSq.isTakenSquare()){ //If the piece is a pawn and it is black
                            if(lSq.getPieceOnSq().pieceName == "Pawn" && !lSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0].getYPos() == 1 && lastMove[1].getYPos() == 3){
                                    legalMoves.add(board[y-1][x-1]);
                                }
                                
                            }
                        } 
                    }
                }
            }
            
            
        } else{
            if(y+1 <= 7){
                if(!board[y+1][x].isTakenSquare()){ // Move 1 square forward for black
                    legalMoves.add(board[y+1][x]);
                }
                if(y == 1){ 
                    if(!board[y+1][x].isTakenSquare() &&!board[y+2][x].isTakenSquare()){ // Move 2 squares forward for black
                        legalMoves.add(board[y+2][x]);
                    }
                }
                if(x+1 <= 7){ //Capture to the right
                    if(board[y+1][x+1].isTakenSquare() && !this.sameTeam(board[y+1][x+1].getPieceOnSq())){
                        legalMoves.add(board[y+1][x+1]);
                    }
                }
                if(x-1 >= 0){ //Capture to the left
                    if(board[y+1][x-1].isTakenSquare() && !this.sameTeam(board[y+1][x-1].getPieceOnSq())){
                        legalMoves.add(board[y+1][x-1]);
                    }
                }
                if(y==4){   //En Passant for black : only works if the pawn is on y = 4
                    if(x+1 <= 7){ //En Passant to the right for black
                        Square rSq = board[y][x+1];
                        if(rSq.isTakenSquare()){ //If the piece is a pawn and it is white
                
                            if(rSq.getPieceOnSq().pieceName == "Pawn" && rSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0].getYPos() == 6 && lastMove[1].getYPos() == 4){        
                                    legalMoves.add(board[y+1][x+1]);
                                }
                                
                            }
                        }
                    }
                    if(x-1 >= 0){ //En passant to the left for black
                        Square lSq = board[y][x-1];
                        if(lSq.isTakenSquare()){ //If the piece is a pawn and it is white
                            if(lSq.getPieceOnSq().pieceName == "Pawn" && lSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0].getYPos() == 6 && lastMove[1].getYPos() == 4){
                                    legalMoves.add(board[y+1][x-1]);
                                } 
                            }
                        } 
                    }
                }
            }
        }
        return legalMoves;
    }
    /*
        method move
        @param target : the target square the player wishes to move to
        @param cb : the current chessboard
        @param legalMoves : list of legal moves for the current piece
        @return void
        this override does the same as the original "move" method, but also handles promotion
    */
    @Override
    public void move(Square target, ChessBoard cb, ArrayList<Square> legalMoves){
        Square[][] board = cb.getBoard();
        Square currentPos = this.getCurrentPosition();
        //Create and fill array to store last move played in the game
        Square[] moveDescription = new Square[2];
        moveDescription[0] = currentPos;
        moveDescription[1] = target;
        cb.setLastPlyPlayed(moveDescription);

        currentPos.removePiece(this);
        if(target.getPieceOnSq()!=null){//if there is an opposing piece on target square a.k.a Capture
            Piece captured = target.getPieceOnSq();
            System.out.println("The " + captured.getColorName()+ " " + captured.pieceName + " was captured by the " +getColorName()+ " " + pieceName);
            cb.getLivePieces().remove(captured);    // mark this as a fallen piece
            if(captured.pieceName.equals("King")){
                System.out.println("The "+ captured.getColorName() + " King has fallen");
                System.out.println(this.getColorName() + " Wins!!!");
                JOptionPane.showMessageDialog(null, this.getColorName()+ " Wins!!! ", "InfoBox: " + "END GAME", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else {
            if(target.getXPos() != this.getCurrentPosition().getXPos()){ //if the target and the current pos have the different x
                //Pawn is going to go diagonally, but since target is null, it is doing en passant
                //Have to remove piece captured en passant
                Square squareToEmpty = board[this.getCurrentPosition().getYPos()][target.getXPos()];
                cb.getLivePieces().remove(squareToEmpty.getPieceOnSq());
                squareToEmpty.removePiece(squareToEmpty.getPieceOnSq());
            }
        }
        
        target.placePiece(this);
        checkingKing(legalMoves);
    }
    /*
        method promote : removes the pawn and places the Piece given by the dice roll promotion
        @param target : the target square where the promotion is going to happen (it's Y value should be 0
         for white and 7 for black)
        @return void
    */
}
