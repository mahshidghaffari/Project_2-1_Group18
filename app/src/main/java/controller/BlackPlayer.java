package controller;
import java.util.ArrayList;

public class BlackPlayer extends Player{

    private ArrayList<Piece> blackLivePieces;
    private ArrayList<Piece> livePieces;
    //private boolean blackTurn= false;
    
    public BlackPlayer(ChessBoard cb) {
        super(cb);
        livePieces = cb.getLivePieces();
        blackLivePieces = new ArrayList<Piece>();
        setIsMyTurn(false);
        for(Piece p: livePieces){
            if(p!=null && !p.isWhite()){ 
                blackLivePieces.add(p);
            }
        }
    }
    
    
    public ArrayList<Piece> getMovablePieces(String chosenPiece){
        ArrayList<Piece> movablePieces = new ArrayList<Piece>();
        for(Piece p: blackLivePieces){
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

    public ArrayList<Piece> getBlackLivePieces(){ 
        return blackLivePieces;
    }

    public Piece getLivePiece(String pName){
        for(Piece lp : blackLivePieces ){
            if(lp.getPieceName().equals(pName)){
                return lp;
            }
        }
        return null;
    }

}
    
