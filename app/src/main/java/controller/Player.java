package controller;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player {

     ChessBoard cb;
    private ArrayList<Piece> livePieces;
    private boolean isMyTurn;
    private String color="Black";
    Random rnd;

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

    public String rollDice(){
        int roll = rnd.nextInt(6)+1;
        String p="";
        switch (roll){
            case 1:
                p =  "Pawn";
                break;
            case 2:
                p=  "Rook";
                break;
            case 3:
                p= "Knight";
                break;
            case 4:
                p= "Bishop";
                break;
            case 5:
                p= "Queen";
                break;
            case 6:
                p= "King";
                break;
        }
        return p;
    }

    public void flipTurns(Player P){
            this.setIsMyTurn(!this.getIsMyTurn());
            P.setIsMyTurn(!P.getIsMyTurn());
        }

    public boolean getIsMyTurn(){ return isMyTurn;}
    public void setIsMyTurn(boolean a){ isMyTurn=a;}
}