package controller;
import java.util.ArrayList;
import java.util.Random;


/**
 * this is a super class that white and black player  classes extend it
 */


public abstract class Player {

     ChessBoard cb;
    private ArrayList<Piece> livePieces;
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
        livePieces = cb.getLivePieces();
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
}