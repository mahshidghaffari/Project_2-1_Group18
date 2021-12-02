package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class King extends Piece {

    /**
     * @param white true if the King is white, false if black
     */
    public King(boolean white) {
        super.setWhite(white);
        super.pieceName = "King";
        if(isWhite()) super.setValue(900);
		else super.setValue(-900);
    }

    /**
     * Checks for the legal moves of the King
     * @param chessBoard
     * @return ArrayList of squares
     */
    public ArrayList <Square> getLegalMoves(ChessBoard cb) {
        ArrayList <Square> legalMoves = new ArrayList <>();
        int x = this.getCurrentPosition().getXPos();
        int y = this.getCurrentPosition().getYPos();
        Square[][] board = cb.getBoard();
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                if (row == 0 && col == 0) continue;
                int newY = y + row;
                int newX = x + col;
                if (newY > 7 || newY < 0 || newX > 7 || newX < 0) {
                    continue;
                } //preventing out of bounds error
                Square iPos = board[y + row][x + col];
                if (iPos.isTakenSquare()) { //if some other piece is on that square
                    if (!this.sameTeam(iPos.getPieceOnSq())) { //if it is from different color
                        Piece opponent = iPos.getPieceOnSq();
                        legalMoves.add(iPos); //add this square to optional moves array
                    }
                } else { //if no piece is on that square add this to legal moves
                    legalMoves.add(iPos);
                }
            }
        }
        closeCastle(legalMoves, cb); //checking for king side castling option
        farCastle(legalMoves, cb); //checking for queen side castling option

        return legalMoves;
    }

    /**
     * Moves the King to the target square if the target square it in the legalMoves
     * @param target square you want to move to
     * @param cb chessboard
     * @param legalMoves ArrayList of squares
     */
    @Override
    public void move(Square target, ChessBoard cb, ArrayList<Square> legalmoves){
        
        Square[] moveDescription = {this.getCurrentPosition(), target};
        cb.setLastPlyPlayed(moveDescription);
        
        if(super.getIfNotYetMoved()){ setNotYetMoved(false);}
        getCurrentPosition().removePiece(this);
        if(target.getPieceOnSq()!=null){                     //if there is an opposing piece on target square a.k.a Capture
            Piece captured = target.getPieceOnSq();
            //System.out.println("The " + captured.getColorName()+ " " + captured.pieceName + " was captured by the " +getColorName()+ " " + pieceName);
            cb.getLivePieces().remove(captured);    // mark this as a fallen piece
            cb.getDeadPieces().add(captured);
            if(captured.pieceName.equals("King")){
                //System.out.println("The "+ captured.getColorName() + " King has fallen");
                //System.out.println(this.getColorName() + " Wins!!!");
                //JOptionPane.showMessageDialog(null, this.getColorName()+ " Wins!!! ", "InfoBox: " + "END GAME", JOptionPane.INFORMATION_MESSAGE);
                //cb.setNewChessBoard();
            }
        }
        target.placePiece(this);
        if(getIfNotYetMoved()){ setNotYetMoved(false); }    
        checkingKing(getLegalMoves(cb));
        }
    
    /** 
     * This method adds the squares to legal moves if close castling is possible
     * Rules for castling:
     * 1. If the King or Rook in question have moved then No castling is possible
     * 2. Spaces between king and rook must be empty
     * 3. The King cannot be in check
     * 4. The Squares the king passes over must not be under attack including the landing square
     * @param legalMoves Arraylist of squares
     * @param cb chessboard
     */

    public void closeCastle(ArrayList < Square > legalMoves, ChessBoard cb) {
        Square[][] board = cb.getBoard();
        if (!super.getIfNotYetMoved()) {
            return; // RULE 1 if the king moved there is no possibilty for castling
        } 
        if (this.isWhite()) {                 //for a white king
            if(getCurrentPosition().getYPos()!=7 || getCurrentPosition().getXPos()!= 4) return;
            if(board[7][7].isTakenSquare() && board[7][7].getPieceOnSq().getPieceName().equals("Rook") && !board[7][7].getPieceOnSq().getIfNotYetMoved()){ //Rule 1 if the rook moved then we can return
                return;
            }
            if (board[7][5].isTakenSquare() || board[7][6].isTakenSquare()) { // RULE 2 if there is a piece in the way it is ilegal
                return;
            }
            for (Piece blackPiece: cb.getLivePieces()) {
                if (!blackPiece.isWhite()) { //making sure its an oponents piece
                    for (int col = 4; col < 7; col++) { //loop through all the steps from the King's to Rook's location 
                        Square sqOnPath = cb.getSquare(7, col);
                        if (blackPiece.getLegalMoves(cb).contains(sqOnPath)) { //if any of the oposing pieces are attacking the king in all its sqaurs on its path 
                            return; //RULE 3 & 4, making sure the king is not in check in any step from start-finish 
                        }
                        //this doesnt work because rook has considered the king to be in its path even though its pawn is blocking
                    }
                }
            }
            //System.out.println("close castling possible");
            legalMoves.add(cb.getSquare(7, 6));
            
        } else if (!isWhite()) { //if its the Black King
            if(getCurrentPosition().getYPos()!=0 || getCurrentPosition().getXPos()!= 4) return;
            if(board[0][7].isTakenSquare() && board[0][7].getPieceOnSq().getPieceName().equals("Rook") && !board[0][7].getPieceOnSq().getIfNotYetMoved()){ //Rule 1 if the rook moved then we can return
                return;
            }
            if (board[0][5].isTakenSquare() || board[0][6].isTakenSquare()) { // RULE 2 if there is a piece in the way it is ilegal
                return;
            }
            for (Piece whitePiece: cb.getLivePieces()) {
                if (whitePiece.isWhite()) { //making sure its an oponents piece
                    for (int col = 4; col < 7; col++) { //loop through all the steps from the King's to Rook's location 
                        Square sqOnPath = cb.getSquare(0, col);
                        if (whitePiece.getLegalMoves(cb).contains(sqOnPath)) { //if any of the oposing pieces are attacking the king in all its sqaurs on its path 
                            return; //RULE 3 & 4, making sure the king is not in check in any step from start-finish 
                        }
                    }
                }
            }
            //System.out.println("close castling possible");
            legalMoves.add(cb.getSquare(0,  6));
        }
    }

    
    /** 
     * This method adds the squares to legal moves if far castling is possible
     * Rules for castling:
     * 1. If the King or Rook in question have moved then No castling is possible
     * 2. Spaces between king and rook must be empty
     * 3. The King cannot be in check
     * 4. The Squares the king passes over must not be under attck including the landing square
     * @param legalMoves Arraylist of squares
     * @param cb chessboard
     */

    public void farCastle(ArrayList <Square> legalMoves, ChessBoard cb) {
        Square[][] board = cb.getBoard();
        if (!this.getIfNotYetMoved()) {
            return;
        } // RULE 1 if the king moved there is no possibilty for castling

        //for a white king
        if (isWhite()) {
            if(getCurrentPosition().getYPos()!=7 || getCurrentPosition().getXPos()!= 4) return;
            if(board[7][0].isTakenSquare() && board[7][0].getPieceOnSq().getPieceName().equals("Rook") && !board[7][0].getPieceOnSq().getIfNotYetMoved()){ //Rule 1 if the rook moved then we can return
                return;
            }

            if (board[7][3].isTakenSquare() || board[7][2].isTakenSquare() || board[7][1].isTakenSquare()) { // RULE 2 if there is a piece in the way it is ilegal
                return;
            }
            for (Piece blackPiece: cb.getLivePieces()) {
                if (!blackPiece.isWhite()) { //making sure its an oponents piece
                    for (int col = 4; col >= 2; col--) { //loop through all the steps from the King's to Rook's location 
                        Square sqOnPath = cb.getSquare(7, col);
                        if (blackPiece.getLegalMoves(cb).contains(sqOnPath)) { //if any of the oposing pieces are attacking the king in all its sqaurs on its path 
                            return; //RULE 3 & 4, making sure the king is not in check in any step from start-finish 
                        }
                    }
                }
            }
            //System.out.println("far castling possible");
            legalMoves.add(cb.getSquare(7, 2));

        } else { //if its the Black King
            if(getCurrentPosition().getYPos()!=0 || getCurrentPosition().getXPos()!= 4) return;
            if(board[0][0].isTakenSquare() && board[0][0].getPieceOnSq().getPieceName().equals("Rook") && !board[0][0].getPieceOnSq().getIfNotYetMoved()){ //Rule 1 if the rook moved then we can return
                return;
            }
            if (board[0][3].isTakenSquare() || board[0][2].isTakenSquare() || board[0][1].isTakenSquare()) { // RULE 2 if there is a piece in the way it is ilegal
                return;
            }
            for (Piece whitePiece: cb.getLivePieces()) {
                if (whitePiece.isWhite()) { //making sure its an oponents piece
                    for (int col = 4; col >= 2; col--) { //loop through all the steps from the King's to Rook's location 
                        Square sqOnPath = cb.getSquare(0, col);
                        if (whitePiece.getLegalMoves(cb).contains(sqOnPath)) { //if any of the oposing pieces are attacking the king in all its sqaurs on its path 
                            return; //RULE 3 & 4, making sure the king is not in check in any step from start-finish 
                        }
                    }
                }
            }
            //System.out.println("far castling possible");
            legalMoves.add(cb.getSquare(0, 2));
        }

    }



    /**
     * This takes care of all castling situations both far and close castling/
     * 
     * @param clickedSquare the sqaure the player wants to move to
     */
    public void doCastling(Square clickedSquare , ChessBoard cb, Piece heldKing){
        if(heldKing.isWhite()){
            heldKing.move(clickedSquare,cb,heldKing.getLegalMoves(cb));
            if(clickedSquare.getXPos()>4){             //if it is  a close castling
                Piece rook = cb.getBoard()[7][7].getPieceOnSq();
                rook.move(cb.getBoard()[7][5], cb, rook.getLegalMoves(cb));
            }
            else {                                      //if it is a far castling
                Piece rook = cb.getBoard()[7][0].getPieceOnSq();
                rook.move(cb.getBoard()[7][3],cb,rook.getLegalMoves(cb));
            }
        }
        else if(!heldKing.isWhite()){
            heldKing.move(clickedSquare,cb,heldKing.getLegalMoves(cb));
            if(clickedSquare.getXPos()>4){             //if it is  a close castling
                Piece rook = cb.getBoard()[0][7].getPieceOnSq();
                rook.move(cb.getBoard()[0][5], cb, rook.getLegalMoves(cb));
            }
            else {                                      //if it is a far castling
                Piece rook = cb.getBoard()[0][0].getPieceOnSq();
                rook.move(cb.getBoard()[0][3],cb,rook.getLegalMoves(cb));
            }
        }
    }

}
