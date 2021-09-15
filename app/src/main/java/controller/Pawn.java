package controller;
import java.util.ArrayList;
import java.util.Random;

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
                if(!board[y-1][x].isTakenSquare()){ // Move 1 square forward
                    legalMoves.add(board[y-1][x]);  
                }
                if(y == 6){
                    if(!board[y-1][x].isTakenSquare() && !board[y-2][x].isTakenSquare()){ // Move 2 squares forward
                        legalMoves.add(board[y-2][x]);
                    }
                }
                
                if(x+1 <= 7){ //Capture to the right
                    if(board[y-1][x+1].isTakenSquare()){
                        if(!this.sameTeam(board[y-1][x+1].getPieceOnSq())){
                            legalMoves.add(board[y-1][x+1]);
                        }
                    }
                }
                if(x-1 >= 0){ //Capture to the left
                    if(board[y-1][x-1].isTakenSquare()){
                        if(!this.sameTeam(board[y-1][x-1].getPieceOnSq())){
                            legalMoves.add(board[y-1][x-1]);
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
        Square currentPos = this.getCurrentPosition();
        currentPos.removePiece(this);
        if(target.getPieceOnSq()!=null){                     //if there is an opposing piece on target square a.k.a Capture
            Piece captured = target.getPieceOnSq();
            System.out.println("The " + captured.getColorName()+ " " + captured.pieceName + " was captured by the " +getColorName()+ " " + pieceName);
            cb.getLivePieces().remove(captured);    // mark this as a fallen piece
            if(captured.pieceName.equals("King")){
                System.out.println("The "+ captured.getColorName() + " King has fallen");
                System.out.println(this.getColorName() + " Wins!!!");
                
            }
        }

        target.placePiece(this);
        checkingKing(legalMoves);

        if(isWhite()){
            if(target.getYPos() == 0){
                promote(target);
            }
        } else {
            if(target.getXPos() == 7){
                promote(target);
            }
        }


    
        
    }
    /*
        method promote : removes the pawn and places the Piece given by the dice roll promotion

        @param target : the target square where the promotion is going to happen (it's Y value should be 0
         for white and 7 for black)
        @return void
    */
    public void promote(Square target){

        if(this.isWhite()){
            target.removePiece(this);
            target.placePiece(rollDice(true));
            
        } else {
            target.removePiece(this);
            target.placePiece(rollDice(false));
            
        }
    }
    /*
        method rollDice : "throws" a dice which determines a Piece the pawn will be promoted to
        @param isWhite : boolean says whether piece is white or not
        @retrun Piece : returns the new Piece created for the promotion
    */
    public Piece rollDice(boolean isWhite){
        Random rnd = new Random();
        int roll = rnd.nextInt(5)+1;
        Piece p = null;
        switch (roll){
            case 1 :
                //TODO : create a selection with the gui to promote into a piece chosen by the player
                p = new Pawn(isWhite);
               break;
            case 2 : 
                p = new Knight(isWhite);
                break;
            case 3 :
                p = new Bishop(isWhite);
                break;
            case 4 :
                p = new Rook(isWhite);
                break;
            case 5 :
                p = new Queen(isWhite);
                break;

        }
        return p;
    }
}









