package controller;

import javax.swing.ImageIcon;

import view.SquareButton;

public class Square {

    private int x; //columns
    private int y; //rows
    private Piece piece=null;
    private boolean isOccupied;
    SquareButton button = null; 

    public Square(int y, int x){
       this.x = x;
        this.y = y;
    }
    public Square(int y, int x, Piece p){
        this.x = x;
        this.y = y;
        this.piece = p;
        //piece.setCurrentPosition(this);
        isOccupied = true;
    }

    public void placePiece(Piece toPut){
        piece = toPut;
        isOccupied = true;
        piece.setCurrentPosition(this);  
        if(button != null)     button.setPieceIcon(piece.getImgIcon());
    }

    public void removePiece(Piece toRemove){
        piece = null;
        isOccupied = false;
        button.setPieceIcon(null);
    }

    public int getXPos(){
        return x;
    }
    public void setXPos(int x){
        this.x = x;
    }
    public int getYPos(){
        return y;
    }
    public void setYPos(int y){
        this.y = y;
    }
    public Piece getPieceOnSq(){
        return piece;
    }
    public boolean isTakenSquare(){
        return isOccupied;
    }
    public SquareButton getButtonOnSquare(){
        return button;
    }
    public void setButtonOnSqaure(SquareButton button){
        this.button = button;
    }
    public void removeImage() { this.button.setPieceIcon(null);}
    public void placeImage(Piece piece) {
        if (piece.getHighlighted()) {
            this.button.setPieceIcon(piece.getHighlightedImgIcon());

        } else {
            this.button.setPieceIcon(piece.getImgIcon());
        }
    }
    public String toString(){
        String msg ="";
        if(piece!=null){
            msg+="Occupied by: ";
            if(piece.isWhite()) msg+= "White "+ piece.pieceName;
            else msg+="Black " + piece.pieceName;
        }
        else msg+= " Not Occupied";
        
        return "[" + y + "," + x +"] " + msg;    
    }
}