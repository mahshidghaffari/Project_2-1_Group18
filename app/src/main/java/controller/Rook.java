package controller;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Rook extends Piece {

    //private boolean notYetMoved=true;
    
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
        Square[] moveDescription = {this.getCurrentPosition(), target};
        cb.setLastPlyPlayed(moveDescription);
        
        if(getIfNotYetMoved()) { setNotYetMoved(false);}
        super.getCurrentPosition().removePiece(this);
        if(target.getPieceOnSq()!=null){                     //if there is an opposing piece on target square a.k.a Capture
            Piece captured = target.getPieceOnSq();
            System.out.println("The " + captured.getColorName()+ " " + captured.pieceName + " was captured by the " +getColorName()+ " " + pieceName);
            cb.getLivePieces().remove(captured);    // mark this as a fallen piece
            cb.getDeadPieces().add(captured);
            if(captured.pieceName.equals("King")){
                System.out.println("The "+ captured.getColorName() + " King has fallen");
                System.out.println(this.getColorName() + " Wins!!!");
                JOptionPane.showMessageDialog(null, this.getColorName()+ " Wins!!! ", "InfoBox: " + "END GAME", JOptionPane.INFORMATION_MESSAGE);
                cb.setNewChessBoard();
            }
        }
        target.placePiece(this);
        if(getIfNotYetMoved()){ setNotYetMoved(false); }
        checkingKing(legalMoves);
    }
}
