package controller;

import view.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;

public abstract class Piece {
    private boolean isAlive = true;
    private boolean isWhite  = true;
    private Square currentPos;
    private double value;
    private boolean promoted;
    private boolean checkingKing=false;
    String pieceName;
    private ImageIcon imgIcon = new ImageIcon(); 
    private boolean notYetMoved = true; 
    private ImageIcon highlightedImgIcon = new ImageIcon();
    private boolean highlighted= false;

    public Piece(){}

    //copying constructor
    public void copyPiece(Piece toCopy){
        isAlive = toCopy.isAlive();
        isWhite = toCopy.isWhite();
        currentPos = toCopy.getCurrentPosition();
        value = toCopy.getValue();
        promoted = toCopy.getPromoted();
        checkingKing = toCopy.getCheckingKing();
        pieceName = toCopy.pieceName;
        imgIcon = toCopy.getImgIcon();
        notYetMoved = toCopy.getIfNotYetMoved();
        highlightedImgIcon = toCopy.getHighlightedImgIcon();
        highlighted = toCopy.getHighlighted();
    }


    /**
     * Assigns square to the position proparty of the class
     * @param position square
     */
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
    public void setHighlightedImgIcon(ImageIcon highlightedImgIcon) {this.highlightedImgIcon = highlightedImgIcon; }
    public ImageIcon getHighlightedImgIcon() { return highlightedImgIcon;}
    public void removeIcon() {this.imgIcon = null; }
    public void setHighlighted(boolean b) {this.highlighted = b;}
    public boolean getHighlighted() {return highlighted; }
    
    public boolean getCheckingKing(){
        return checkingKing;
    }
    public boolean getIfNotYetMoved(){
        return notYetMoved;
    }
    public void setNotYetMoved(boolean b){
        notYetMoved=b;
    }

    /**
     * Check if King is being checked and change checkingKing proparty status
     * @param legalMoves ArrayList of squares
     */
    public void checkingKing(ArrayList<Square> legalMoves){
            for(Square sq : legalMoves){
                if(sq.isTakenSquare() && sq.getPieceOnSq().pieceName.equals("King")){
                    checkingKing = true;
                    //System.out.println(pieceName + " is Checking the King");
                    break;
                }
                checkingKing=false;
            }
        }

    /**
     * Compares if two pieces are from the same team
     * @param otherP piece to compare
     * @return true if the otherP is the same color as this piece
     */
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

    /**
     * Moves the Piece to the target square if the target square it in the legalMoves
     * @param target square you want to move to
     * @param cb chessboard
     * @param legalMoves ArrayList of squares
     */
    public void move(Square target, ChessBoard cb, ArrayList<Square> legalMoves){
        

        if(currentPos.getButtonOnSquare()!=null)     currentPos.getButtonOnSquare().setBackground(Color.ORANGE);
        if(target.getButtonOnSquare()!=null)         target.getButtonOnSquare().setBackground(Color.ORANGE);
        Square[] moveDescription = {currentPos, target};
        cb.setLastPlyPlayed(moveDescription);

        currentPos.removePiece(this);
        if(target.getPieceOnSq()!=null){                     //if there is an opposing piece on target square a.k.a Capture
            Piece captured = target.getPieceOnSq();
            //System.out.println("The " + captured.getColorName()+ " " + captured.pieceName + " was captured by the " +getColorName()+ " " + pieceName);
            cb.getLivePieces().remove(captured);    // mark this as a fallen piece
            cb.getDeadPieces().add(captured);
            if(captured.pieceName.equals("King")){
                //System.out.println("The "+ captured.getColorName() + " King has fallen");
                //System.out.println(this.getColorName() + " Wins!!!");
                //JOptionPane.showMessageDialog(null, this.getColorName()+ " Wins!!! ", "InfoBox: " + "END GAME", JOptionPane.INFORMATION_MESSAGE);
                cb.setNewChessBoard();
            }
        }
        target.placePiece(this);
        if(notYetMoved){ 
            notYetMoved = false;
        }
        checkingKing(legalMoves);
    }
    public boolean getPromoted(){
        return this.promoted;
    }
    public void setPromoted(boolean isPromoted){
        this.promoted = isPromoted;
    }


    public abstract ArrayList<Square> getLegalMoves(ChessBoard cb);
    //abstract boolean isCheckingKing(Square location, Square OpposingKing); 

}
