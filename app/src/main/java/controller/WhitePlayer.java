package controller;
import java.util.ArrayList;

public class WhitePlayer extends Player{


    private ArrayList<Piece> livePieces;
    private double score=0;
    private ChessBoard cb;

    public WhitePlayer(ChessBoard cb) {
        super(cb);
        super.setColor("White");
        this.cb = cb;
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


    public ArrayList<Piece> getAllMovablePieces(){
        ArrayList<Piece> movablePieces = new ArrayList<Piece>();
        for(Piece p: livePieces){
            if(p.isWhite() && p.getLegalMoves(cb).size()>0){
                movablePieces.add(p);
            }
        }
        return movablePieces;
    }

    public ArrayList<String> getMovableNames(){
        ArrayList<Piece> movablePieces = getAllMovablePieces();
        ArrayList<String> names = new ArrayList<>();
        for(Piece p : movablePieces){
            if(!names.contains(p.pieceName)){
                names.add(p.pieceName);
            }
        }
        return names;
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