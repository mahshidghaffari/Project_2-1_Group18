package controller;
import java.util.ArrayList;

public class Knight extends Piece { 

    public Knight(boolean white){
        super.setWhite(white);
        super.pieceName = "Knight";
        super.setValue(3);

    }
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

    public void isLegalMove(Square end, ArrayList<Square> legalMoves){
        if(end.getPieceOnSq()!=null && this.sameTeam(end.getPieceOnSq())){
            return;
        }
        int a = Math.abs(getCurrentPosition().getYPos() - end.getYPos());
        int b = Math.abs(getCurrentPosition().getXPos() - end.getXPos());
        
        if(a * b == 2) legalMoves.add(end); 
    }  
}