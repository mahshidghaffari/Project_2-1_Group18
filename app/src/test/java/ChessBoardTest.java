import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import controller.*;

public class ChessBoardTest {
    public static void main(String[]args){
        ChessBoard cb = new ChessBoard(true);
        Pawn p = new Pawn(true);
        cb.getSquare(4,4).placePiece(p);
        System.out.println("Method Piece.getLegalMoves(cb) size : "+p.getLegalMoves(cb).size());
        System.out.println("Method ChessBoard.getLegalMoves(true) : " + cb.getLegalMoves(true).size());
        System.out.println("Method ChessBoard.geTLegalMoves(true) : " + cb.getLegalMoves(true, "Pawn").size());
    }
    @Test public void testPlayMove(){
        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);
        cb.placePiece(cb.getSquare(0,0), w);
        Move move= new Move(cb.getSquare(0,0), cb.getSquare(7,7), cb);
        cb.playMove(move);
        assertEquals(true, cb.getSquare(7,7).isTakenSquare());
        assertEquals(false, cb.getSquare(0,0).isTakenSquare());
    }
   
    @Test public void testUnplayMove(){

        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);

        cb.getBoard()[0][0].placePiece(w);
        cb.addLivePiece(w);
        
        Move move = new Move(cb.getSquare(0, 0), cb.getSquare(7,7), cb);
        
        cb.playMove(move);     
        cb.printBoard();
        assertEquals(true, cb.getSquare(7,7).isTakenSquare());
        cb.unplayMove(move);
        
        
        assertEquals(true, cb.getSquare(0,0).isTakenSquare());
    }
    @Test public void testUnplayMoveCapture(){

        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);
        Queen b = new Queen(false);
        cb.placePiece(cb.getSquare(0,0), w);
        cb.placePiece(cb.getSquare(7,7), b);
        
        Move move = new Move(cb.getSquare(0, 0), cb.getSquare(7,7), cb);
        
        cb.playMove(move);     
        cb.printBoard();
        assertEquals(false, cb.getSquare(0,0).isTakenSquare());
        cb.unplayMove(move);
        
        
        assertEquals(true, cb.getSquare(7,7).isTakenSquare());
    }
    @Test public void testGetLegalMoves(){
        ChessBoard cb = new ChessBoard(true);
        Pawn w = new Pawn(true);
        cb.placePiece(cb.getSquare(4, 4), w);
        assertEquals(1, cb.getLegalMoves(true).size());
    }
    @Test public void testGetLegalMovesPiece(){
        ChessBoard cb = new ChessBoard(true);
        Pawn w = new Pawn(true);
        Queen q = new Queen(true);
        cb.placePiece(cb.getSquare(4,4),w);
        cb.placePiece(cb.getSquare(7,7),q);
        assertEquals(1, cb.getLegalMoves(true, "Pawn").size());
    }
    @Test public void testcreateCopy(){
        ChessBoard cb = new ChessBoard(true);
        Pawn w = new Pawn(true);
        cb.placePiece(cb.getSquare(4, 4), w);

        ChessBoard newCB = cb.createCopy();

        assertEquals(1, newCB.getLivePieces().size());
        assertEquals(true, newCB.getBoard()[4][4].isTakenSquare());
        assertEquals(false, newCB.getBoard()[5][4].isTakenSquare());
    }
    @Test public void testCopyImmutability(){
        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);
        cb.placePiece(cb.getSquare(4, 4), w);

        ChessBoard newCB = cb.createCopy();
        Move move1 = new Move(cb.getSquare(4,4), cb.getSquare(5,4), cb);
        cb.playMove(move1);
        assertEquals(true, cb.getBoard()[5][4].isTakenSquare());
        assertEquals(false, cb.getBoard()[4][4].isTakenSquare());

        assertEquals(false, newCB.getBoard()[5][4].isTakenSquare());
        assertEquals(true, newCB.getBoard()[4][4].isTakenSquare());

    }

}

