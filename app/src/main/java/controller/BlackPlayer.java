package controller;
import java.util.ArrayList;

public class BlackPlayer extends Player{

    private ArrayList<Piece> livePieces;
    
    public BlackPlayer(ChessBoard cb) {
        super(cb);
        livePieces = cb.getLivePieces();
        setIsMyTurn(false);
    }
    public ArrayList<Piece> getMovablePieces(String chosenPiece){
        ArrayList<Piece> movablePieces = new ArrayList<Piece>();
        for(Piece p: livePieces){
            if(!p.isWhite() && p.pieceName.equals(chosenPiece)){
                if(p.getLegalMoves(super.cb).size()>0){
                    movablePieces.add(p);
                }
            }
        }
        return movablePieces;
    }

    public boolean canMove(String chosenPiece){
        if(getMovablePieces(chosenPiece).size()>0){
            return true;
        }
        return false;
    }
}
