package controller;
import java.util.ArrayList;

public class Rook extends Piece {

    private boolean notYetMoved=true;
    
    public Rook(boolean white) {
        super.setWhite(white);
        super.pieceName = "Rook";
        super.setValue(5);
    }

    private boolean movePermission(Square s) {
        if (!s.isTakenSquare() || !this.sameTeam(s.getPieceOnSq())) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Square> getLegalMoves(ChessBoard cb) {
        ArrayList<Square> legalMoves = new ArrayList<>();
        int x = this.getCurrentPosition().getXPos();
        int y = this.getCurrentPosition().getYPos();
        Square[][] board = cb.getBoard();

        // go stright right
        for (int i = x+1; i < 8; i++) {
            Square tempSquare = board[y][i];
            if (movePermission(tempSquare)) {
                legalMoves.add(tempSquare);
                if(tempSquare.isTakenSquare()){
                    break;
                }
            } else {
                break;
            }
        }
         x = this.getCurrentPosition().getXPos();
         y = this.getCurrentPosition().getYPos();


        // go stright left
        for (int i = x-1; i > -1; i--) {
            Square tempSquare = board[y][i];
            if (movePermission(tempSquare)) {
                legalMoves.add(tempSquare);
                if(tempSquare.isTakenSquare()){
                    break;
                }
            } else {
                break;
            }
        }
        x = this.getCurrentPosition().getXPos();
        y = this.getCurrentPosition().getYPos();

        // go stright down
        for (int j = y+1; j < 8; j++) {
            Square tempSquare = board[j][x];
            if (movePermission(tempSquare)) {
                legalMoves.add(tempSquare);
                if(tempSquare.isTakenSquare()){
                    break;
                }
            } else {
                break;
            }
        }
        x = this.getCurrentPosition().getXPos();
        y = this.getCurrentPosition().getYPos();

        // go stright up
        for (int j = y-1; j > -1; j--) {
            Square tempSquare = board[j][x];
            if (movePermission(tempSquare)) {
                legalMoves.add(tempSquare);
                if(tempSquare.isTakenSquare()){
                    break;
                }
            } else {
                break;
            }
        }

        return legalMoves;
    }
    
    @Override
    public void move(Square target, ChessBoard cb, ArrayList<Square> legalMoves){
        if(notYetMoved){ notYetMoved=false;}
    }
}
