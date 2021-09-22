package controller;

import view.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public abstract class Piece {
    private boolean isAlive = true;
    private boolean isWhite  = true;
    private Square currentPos;
    private double value;
    private boolean checkingKing=false;
    public Square[] lastMove= new Square[2];
    String pieceName;
    private ImageIcon imgIcon = new ImageIcon(); 

    public Piece(){}

    public Piece(Square position){
        currentPos = position;
    }
    public String getPieceName(){
        return pieceName;
    }
    public boolean isWhite(){
        return isWhite;
    }
    public void setWhite(boolean w){
        this.isWhite = w;
    }
    public boolean isAlive(){
        return isAlive;
    }
    public void setAlive(boolean l){
        this.isAlive = l;
    }
    public Square getCurrentPosition(){
        return currentPos;
    }
    public void setCurrentPosition(Square s){
        currentPos = s;
    }
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    public ImageIcon getImgIcon(){
        return imgIcon;
    }
    public void setImgIcon(ImageIcon imgIcon){
        this.imgIcon = imgIcon;
    }
    public boolean getCheckingKing(){
        return checkingKing;
    }
    public void checkingKing(ArrayList<Square> legalMoves){
            for(Square sq : legalMoves){
                if(sq.isTakenSquare() && sq.getPieceOnSq().pieceName.equals("King")){
                    checkingKing = true;
                    System.out.println(pieceName + " is Checking the King");
                    break;
                }
                checkingKing=false;
            }
        }
      //checks whether the two pieces have the same color 
    public boolean sameTeam(Piece otherP){
        if(this.isWhite() == otherP.isWhite()){
            return true;
        }
        return false;
    }
    
    public String getColorName(){
        if(isWhite) return "White";
        return "Black"; 
    }

    public void move(Square target, ChessBoard cb, ArrayList<Square> legalMoves){
        lastMove[0]= currentPos;
        lastMove[1]= target;

        currentPos.removePiece(this);
        if(target.getPieceOnSq()!=null){                     //if there is an opposing piece on target square a.k.a Capture
            Piece captured = target.getPieceOnSq();
            System.out.println("The " + captured.getColorName()+ " " + captured.pieceName + " was captured by the " +getColorName()+ " " + pieceName);
            cb.getLivePieces().remove(captured);    // mark this as a fallen piece
            if(captured.pieceName.equals("King")){
                System.out.println("The "+ captured.getColorName() + " King has fallen");
                System.out.println(this.getColorName() + " Wins!!!");
                JOptionPane.showMessageDialog(null, this.getColorName()+ " Wins!!! ", "InfoBox: " + "END GAME", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        target.placePiece(this);

        checkingKing(legalMoves);
    }

    public Square[] getLastMove() {return lastMove;}

    public abstract ArrayList<Square> getLegalMoves(ChessBoard cb);
    //abstract boolean isCheckingKing(Square location, Square OpposingKing); 

}
