package controller;
import java.util.ArrayList;
import java.util.Random;


/**
 * this is a super class that white and black player  classes extend it
 */


public abstract class Player {

    ChessBoard cb;
    private boolean isMyTurn;
    private String color="Black";
    Random rnd;
    /**
    * constructor
    * @param chessBoard Object Giving the board States 
    * 
	*/
    public Player(ChessBoard cb){
        this.cb= cb;
        rnd = new Random();
    }
    public void setColor(String color){
        this.color =color;
    }
    public String getColor(){
        return color;
    }

    

    
    /** 
     * this method changing players  rounds
     * @param P player object of this class 
     */

    public void flipTurns(Player P){
            this.setIsMyTurn(!this.getIsMyTurn());
            P.setIsMyTurn(!P.getIsMyTurn());
        }

    public boolean getIsMyTurn(){ return isMyTurn;}
    public void setIsMyTurn(boolean a){ isMyTurn=a;}

    public boolean canMove(String chosenPiece, boolean isWhite){

        if(getMovablePieces(chosenPiece,isWhite).size()>0){
            return true;
        }
        return false;
    }

   public ArrayList<Piece> getMovablePieces(String chosenPiece, boolean isWhite){
        ArrayList<Piece> movablePieces = new ArrayList<Piece>();
        if(isWhite){
            for(Piece p: cb.getLivePieces()){
                if(p.isWhite() && p.pieceName.equals(chosenPiece)){
                    if(p.getLegalMoves(cb).size()>0){
                        movablePieces.add(p);
                    }
                }
            }
        }
        else{
            for(Piece p: cb.getLivePieces()){
                if(!p.isWhite() && p.pieceName.equals(chosenPiece)){
                    if(p.getLegalMoves(cb).size()>0){
                        movablePieces.add(p);
                    }
                }
            }

        }
        return movablePieces;
    } 
}