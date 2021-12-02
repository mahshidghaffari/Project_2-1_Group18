package controller;
import java.util.ArrayList;

public class Knight extends Piece { 

    

    public Knight(boolean white) {
        super.setWhite(white);
        super.pieceName = "Knight";
        if(isWhite()) super.setValue(30);
		else super.setValue(-30);
    }
    /**
     *
     * @param cb The chessboard
     * @return Array containing the squares where the Knight can move to.
     */
    public ArrayList<Square> getLegalMoves(ChessBoard cb){
        Square[][] board = cb.getBoard();
        ArrayList<Square> legalMoves = new ArrayList<Square>();
        int y = getCurrentPosition().getYPos();
        int x = getCurrentPosition().getXPos();
        for(int r =y-2; r<y+3; r++ ){
            if(r<0 ) continue;
            if(r>7 ) break;
            for(int c= x-2; x<x+3;c++){
                if(c<0 ) continue;
                if(c>7 ) break;
                if(r==y || c==x) continue;
                if(Math.abs(y-r)==1 && Math.abs(x-c)==1) continue;
                Square toCheck = board[r][c];
                isLegalMove(toCheck, legalMoves); 
            }
        }
        return legalMoves;
    }

    /**
     *
     * @param end Square we want to check if the Knight can move to.
     * @param legalMoves Array containing the squares where the Knight can move to.
     * Add Square end to legalMoves if end is a valid square the Knight can move to
     */
    public void isLegalMove(Square end, ArrayList<Square> legalMoves){
        if(end.getPieceOnSq()!=null && this.sameTeam(end.getPieceOnSq())){
            return;
        }
        int a = Math.abs(getCurrentPosition().getYPos() - end.getYPos());
        int b = Math.abs(getCurrentPosition().getXPos() - end.getXPos());
        
        if(a * b == 2) legalMoves.add(end); 
    }  
}