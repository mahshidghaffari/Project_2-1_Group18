package controller;
import java.util.ArrayList;

public class WhitePlayer extends Player{

    private ArrayList<Piece> whiteLivePieces;
    private ArrayList<Piece> livePieces;
    //private boolean whiteTurn= true;
    
    public WhitePlayer(ChessBoard cb) {
        super(cb);
        super.setColor("White");
        livePieces = cb.getLivePieces();
        whiteLivePieces = new ArrayList<Piece>();
        setIsMyTurn(true);
        for(Piece p: livePieces){
            if(p!=null && p.isWhite()){ 
                whiteLivePieces.add(p);
            }
        }
    }

    public ArrayList<Piece> getWhiteLivePieces(){ return whiteLivePieces;}

    public Piece getLivePiece(String pName){
        for(Piece lp : whiteLivePieces ){
            if(lp.getPieceName().equals(pName)){
                return lp;
            }
        }
        return null;
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
    
