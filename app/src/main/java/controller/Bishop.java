package controller;

import java.util.ArrayList;

public class Bishop extends Piece { 

    public Bishop(boolean white){
        super.setWhite(white);
        super.pieceName = "Bishop";
        if(isWhite()) super.setValue(30);
		else super.setValue(-30);
    }

	/**
	 *
	 * @param chessBoard The chessboard
	 * @return Array containing the squares where the Bishop can move to.
	 */
    public ArrayList<Square> getLegalMoves(ChessBoard chessBoard){
        
    	ArrayList<Square> legalMoves = new ArrayList<Square>();
		Square[][] board = chessBoard.getBoard();
		
        int X = this.getCurrentPosition().getXPos();
		int Y = this.getCurrentPosition().getYPos();
        
		        
        while(true) {
        	Y--;
        	X++;
        	if(Y < 0 || X > 7) {
        		break;
        	}
        	Square temp = board[Y][X]; //this is up right diagonal
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        
        // check right up diagonal (Y ++, X ++) -> check if any is 0
        X = this.getCurrentPosition().getXPos();
		Y = this.getCurrentPosition().getYPos();

        while(true) {
        	Y++;
        	X++;
        	if(Y > 7 || X > 7) {
        		break;
        	}
        	Square temp = board[Y][X]; //down right diagonal
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        
        // check left down diagonal (Y --, X --) -> check if any is 0
        X = this.getCurrentPosition().getXPos();
		Y = this.getCurrentPosition().getYPos();
         
        while(true) {
        	Y--;
        	X--;
        	if(Y < 0 || X < 0) {
        		break;
        	}
        	Square temp = board[Y][X]; //up left diagonal
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        
        // check right down diagonal (Y--, X ++) -> check if any is 0
        X = this.getCurrentPosition().getXPos();
		Y = this.getCurrentPosition().getYPos();
		        
        while(true) {
        	Y++;
        	X--;
        	if(Y > 	7 || X < 0) {
        		break;
        	}
        	Square temp = board[Y][X]; //down left diagonal
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        return legalMoves;
    }
}
   

 