package controller;

public class Move {
    Square origin;
    Square target;
    ChessBoard cb;
    Piece captured;
    double eval;
    public Move(){

    }
    public Move(Square origin, Square target, ChessBoard cb){
        this.origin = origin;
        this.target = target;
        this.cb = cb;
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
