package controller;
import java.util.*;

public class Queen extends Piece{

	/**
	 * @param white true if the Queen is white, false if black
	 */
    public Queen(boolean white){
        super.setWhite(white);
        super.pieceName = "Queen";
		if(isWhite()) super.setValue(90);
		else super.setValue(-90);    }

	/**
	 * Checks for the legal moves of the Queen(up, down, right, left, diagonal)
	 * @param chessBoard
	 * @return ArrayList of squares
	 */

    public ArrayList<Square> getLegalMoves(ChessBoard chessBoard){
		
		
    	ArrayList<Square> legalMoves = new ArrayList<Square>();
		Square[][] board = chessBoard.getBoard();
		
        // check straight left (X--) check if X is 0
		int X = this.getCurrentPosition().getXPos();
		int Y = this.getCurrentPosition().getYPos();

        while(true) {
        	X--;
        	if(X < 0) {
        		break;
        	}
        	Square temp = board[Y][X];
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        
        // check straight right (X++) -> check if X is 0
        X = this.getCurrentPosition().getXPos();
		Y = this.getCurrentPosition().getYPos();
                
        while(true) {
        	X++;
        	if(X > 7) {
        		break;
        	}
        	Square temp = board[Y][X];
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        
        // check straight up (Y++) -> check if Y is 0
        X = this.getCurrentPosition().getXPos();
		Y = this.getCurrentPosition().getYPos();
     
        while(true) {
        	Y++;
        	if(Y > 7) {
        		break;
        	}
        	Square temp = board[Y][X];
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        
        // check straight down (Y--) -> check if Y is 0
        X = this.getCurrentPosition().getXPos();
		Y = this.getCurrentPosition().getYPos();
        
        while(true) {
        	Y--;
        	if(Y < 0) {
        		break;
        	}
        	Square temp = board[Y][X];
        	if(temp.isTakenSquare()) {
        		if(!this.sameTeam(temp.getPieceOnSq())) {
        			legalMoves.add(temp);
        		}
        		break;
        	}
        	legalMoves.add(temp);
        }
        
        // check left up diagonal (Y ++, X --) -> check if any is 0
        X = this.getCurrentPosition().getXPos();
		Y = this.getCurrentPosition().getYPos();
        
		        
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