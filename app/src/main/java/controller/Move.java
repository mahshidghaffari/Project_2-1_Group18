package controller;

public class Move {
    Square origin;
    Square target;
    String pieceName;
    Piece captured;
    double eval;
    public Move(){

    }
    public Move(Square origin, Square target){
        this.origin = origin;
        this.target = target;
        if(origin.isTakenSquare()){
            this.pieceName = origin.getPieceOnSq().getPieceName();
        } else{
            if(target.isTakenSquare()){
                this.pieceName = target.getPieceOnSq().pieceName;
            }
        }
        if(target.isTakenSquare()){
            this.captured = target.getPieceOnSq();
        }
    }
    public void printMove(){
        System.out.println("Piece : "+origin.getPieceOnSq().pieceName);
        System.out.println("Origin : ["+origin.getYPos()+","+origin.getXPos()+"]");
        System.out.println("Target : ["+target.getYPos()+","+target.getXPos()+"]");

    }
    public void setEval(double d){
        this.eval = d;
    }

}
