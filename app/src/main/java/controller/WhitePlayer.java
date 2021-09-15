package controller;
import java.util.ArrayList;

public class WhitePlayer extends Player{

    private ArrayList<Piece> whiteLivePieces;
    private ArrayList<Piece> livePieces;
    //private boolean whiteTurn= true;
    
    public WhitePlayer(ChessBoard cb) {
        super(cb);
        livePieces = cb.getLivePieces();
        whiteLivePieces = new ArrayList<Piece>();
        for(Piece p: livePieces){
            if(p!=null&& p.isWhite()){ 
                whiteLivePieces.add(p);
            }
        }
    }
    // public boolean getIsWhiteTurn(){
    //     return whiteTurn;
    // }
    // public void setIsWhiteTurn(boolean isWhiteTurn){
    //     whiteTurn = isWhiteTurn;

    // }

    public ArrayList<Piece> getMovablePieces(String chosenPiece){
        ArrayList<Piece> movablePieces = new ArrayList<Piece>();
        for(Piece p: whiteLivePieces){
            if(p.pieceName.equals(chosenPiece)){
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
    
