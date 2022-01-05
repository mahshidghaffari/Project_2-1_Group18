import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import controller.*;

public class ChessBoardTest {
    public static void main(String[]args){
        
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(0,6), cb.getSquare(2,5)));
        cb.playMove(new Move(cb.getSquare(0,7), cb.getSquare(0,6)));
        System.out.println(cb.getFENString());
        ChessBoard fenBoard = new ChessBoard(cb.getFENString());
    }
    @Test public void testPlayMove(){
        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);
        cb.placePiece(cb.getSquare(0,0), w);
        Move move= new Move(cb.getSquare(0,0), cb.getSquare(7,7));
        cb.playMove(move);
        assertEquals(true, cb.getSquare(7,7).isTakenSquare());
        assertEquals(false, cb.getSquare(0,0).isTakenSquare());
    }
   
    @Test public void testUnplayMove(){

        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);

        cb.getBoard()[0][0].placePiece(w);
        cb.addLivePiece(w);
        
        Move move = new Move(cb.getSquare(0, 0), cb.getSquare(7,7));
        
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
        
        Move move = new Move(cb.getSquare(0, 0), cb.getSquare(7,7));
        
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
        Move move1 = new Move(cb.getSquare(4,4), cb.getSquare(5,4));
        cb.playMove(move1);
        assertEquals(true, cb.getBoard()[5][4].isTakenSquare());
        assertEquals(false, cb.getBoard()[4][4].isTakenSquare());

        assertEquals(false, newCB.getBoard()[5][4].isTakenSquare());
        assertEquals(true, newCB.getBoard()[4][4].isTakenSquare());
    }
    @Test public void testGetNewPiece(){
        char c = 'k';
        ChessBoard cb = new ChessBoard(true);
        assertEquals("King", cb.getNewPiece(c).getPieceName());
        assertEquals(false, cb.getNewPiece(c).isWhite());
        c = 'K';
        assertEquals("King", cb.getNewPiece(c).getPieceName());
        assertEquals(true, cb.getNewPiece(c).isWhite());
        c = 'q';
        assertEquals("Queen", cb.getNewPiece(c).getPieceName());
        assertEquals(false, cb.getNewPiece(c).isWhite());
        c = 'Q';
        assertEquals("Queen", cb.getNewPiece(c).getPieceName());
        assertEquals(true, cb.getNewPiece(c).isWhite());
        c = 'r';
        assertEquals("Rook", cb.getNewPiece(c).getPieceName());
        assertEquals(false, cb.getNewPiece(c).isWhite());
        c = 'R';
        assertEquals("Rook", cb.getNewPiece(c).getPieceName());
        assertEquals(true, cb.getNewPiece(c).isWhite());
        c = 'b';
        assertEquals("Bishop", cb.getNewPiece(c).getPieceName());
        assertEquals(false, cb.getNewPiece(c).isWhite());
        c = 'B';
        assertEquals("Bishop", cb.getNewPiece(c).getPieceName());
        assertEquals(true, cb.getNewPiece(c).isWhite());
        c = 'n';
        assertEquals("Knight", cb.getNewPiece(c).getPieceName());
        assertEquals(false, cb.getNewPiece(c).isWhite());
        c = 'N';
        assertEquals("Knight", cb.getNewPiece(c).getPieceName());
        assertEquals(true, cb.getNewPiece(c).isWhite());
        c = 'p';
        assertEquals("Pawn", cb.getNewPiece(c).getPieceName());
        assertEquals(false, cb.getNewPiece(c).isWhite());
        c = 'P';
        assertEquals("Pawn", cb.getNewPiece(c).getPieceName());
        assertEquals(true, cb.getNewPiece(c).isWhite());
    }
    @Test public void testBasicFenString(){
        ChessBoard cb = new ChessBoard();
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR  KQkq", cb.getFENString());
    }
    @Test public void testEPFenString(){
        ChessBoard cb = new ChessBoard();
        Move move = new Move(cb.getSquare(1,1), cb.getSquare(3,1));
        cb.playMove(move);
        assertEquals("rnbqkbnr/p1pppppp/8/1p6/8/8/PPPPPPPP/RNBQKBNR 21 KQkq", cb.getFENString());
    }
    @Test public void testMessyFenString(){
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(0,1), cb.getSquare(2,2)));
        cb.playMove(new Move(cb.getSquare(6,3), cb.getSquare(4,3)));
        cb.playMove(new Move(cb.getSquare(1,1), cb.getSquare(3,1)));
        assertEquals("r1bqkbnr/p1pppppp/2n5/1p6/3P4/8/PPP1PPPP/RNBQKBNR 21 KQkq", cb.getFENString());
    }
    @Test public void testNoCastleFenString1(){
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(0,1), cb.getSquare(2,2)));
        cb.playMove(new Move(cb.getSquare(0,0), cb.getSquare(0,1)));
        assertEquals("1rbqkbnr/pppppppp/2n5/8/8/8/PPPPPPPP/RNBQKBNR  KQk", cb.getFENString());
    }
    @Test public void testNoCastleFenString2(){
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(7,1), cb.getSquare(5,2)));
        cb.playMove(new Move(cb.getSquare(7,0), cb.getSquare(7,1)));
        assertEquals("rnbqkbnr/pppppppp/8/8/8/2N5/PPPPPPPP/1RBQKBNR  Kkq", cb.getFENString());
    }
    @Test public void testNoCastleFenString3(){
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(7,6), cb.getSquare(5,5)));
        cb.playMove(new Move(cb.getSquare(7,7), cb.getSquare(7,6)));
        assertEquals("rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKBR1  Qkq", cb.getFENString());
    }
    @Test public void testNoCastleFenString4(){
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(0,6), cb.getSquare(2,5)));
        cb.playMove(new Move(cb.getSquare(0,7), cb.getSquare(0,6)));
        assertEquals("rnbqkbr1/pppppppp/5n2/8/8/8/PPPPPPPP/RNBQKBNR  KQq", cb.getFENString());
    }
    @Test public void testFenConstructor(){
        ChessBoard cb = new ChessBoard();
        ChessBoard fenBoard = new ChessBoard(cb.getFENString());
        for(int col=0; col<8;col++){
            for(int row=0; row<8; row++){
                if(fenBoard.getSquare(col,row).isTakenSquare()){
                    assertEquals(fenBoard.getSquare(col,row).getPieceOnSq().getPieceName(), cb.getSquare(col,row).getPieceOnSq().getPieceName() );
                    assertEquals(fenBoard.getSquare(col,row).getPieceOnSq().isWhite(), cb.getSquare(col,row).getPieceOnSq().isWhite());
                }
            }
        }
    }
    @Test public void testFenConstructorEP(){
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(1,1), cb.getSquare(3,1)));
        ChessBoard fenBoard = new ChessBoard(cb.getFENString());
        assertEquals(cb.getSquare(3,1).isTakenSquare(), fenBoard.getSquare(3,1).isTakenSquare());
        assertEquals("rnbqkbnr/p1pppppp/8/1p6/8/8/PPPPPPPP/RNBQKBNR 21 KQkq", fenBoard.getFENString());
    }
    @Test public void testFenConstructorNoCastle(){
        ChessBoard cb = new ChessBoard();
        cb.playMove(new Move(cb.getSquare(0,6), cb.getSquare(2,5)));
        cb.playMove(new Move(cb.getSquare(0,7), cb.getSquare(0,6)));
        ChessBoard fenBoard = new ChessBoard(cb.getFENString());


        assertEquals("rnbqkbr1/pppppppp/5n2/8/8/8/PPPPPPPP/RNBQKBNR  KQq", fenBoard.getFENString());
    }
}


