package controller;

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

    /**
     *
     * @param y y Coordinate of this Square
     * @param x x Coordinate of this Square
     * @param p Piece to put on this Square
     */
    public Square(int y, int x, Piece p){
        this.x = x;
        this.y = y;
        this.piece = p;
        //piece.setCurrentPosition(this);
        isOccupied = true;
    }

    /**
     *
     * @param toPut Piece to put on this Square
     */
    public void placePiece(Piece toPut){
        piece = toPut;
        isOccupied = true;
        piece.setCurrentPosition(this);  
        if(button != null)     button.setPieceIcon(piece.getImgIcon());
    }

    /**
     *
     * @param toRemove Piece to remove from the square
     */
    public void removePiece(Piece toRemove){
        piece = null;
        isOccupied = false;
        if(button != null)  button.setPieceIcon(null);
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
    public void setButtonOnSquare(SquareButton button){
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