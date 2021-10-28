package controller;
import java.util.ArrayList;

public class WhitePlayer extends Player{


    private ArrayList<Piece> livePieces;
    private double score=0;

    public WhitePlayer(ChessBoard cb) {
        super(cb);
        super.setColor("White");
        livePieces = cb.getLivePieces();
        setIsMyTurn(true);
        for(Piece piece: livePieces){
            if(piece.isWhite())
            score+= piece.getValue();
        }
    }

    public ArrayList<Piece> getMovablePieces(String chosenPiece){
        ArrayList<Piece> movablePieces = new ArrayList<Piece>();
        //check if this piece is even a live or not
        for(Piece p: livePieces){
            if(p.isWhite() && p.pieceName.equals(chosenPiece)){
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

    public double getWhiteScore(){ return score;}

    public void updateScore(){
        score = 0;
        for(Piece piece: livePieces){
            if(piece.isWhite())
            score+= piece.getValue();
        }
    }
}