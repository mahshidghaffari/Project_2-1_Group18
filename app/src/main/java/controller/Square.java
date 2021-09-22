package controller;

import javax.swing.ImageIcon;

import view.SquareButton;

public class Square{

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
        if(button != null)     button.setPiece(piece.getImgIcon());   
    }

    public void removePiece(Piece toRemove){
        if(toRemove != null && button != null){
            piece = null;
            isOccupied = false;
            button.setPiece(new ImageIcon());
        }
       
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