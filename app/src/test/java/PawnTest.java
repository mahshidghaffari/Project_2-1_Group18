import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.*;
import java.util.ArrayList;

public class PawnTest {
    Pawn whitePawn1 = new Pawn(true);
    Pawn whitePawn2 = new Pawn(true);
    Pawn blackPawn1 = new Pawn(false);
    Pawn blackPawn2 = new Pawn(false);
    ChessBoard cb = new ChessBoard(true);
    Square[][] board = cb.getBoard();
    public static void main(String[]args){
    }
        
    
    //Test the forward move of 1 for white when square empty
    @Test public void testForward1White(){
        board[4][4].placePiece(whitePawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(3, wPLM.get(0).getYPos());
    }
    //Test the forward move of 1 for black when square empty
    @Test public void testForward1Black(){
        board[4][4].placePiece(blackPawn1);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(board[5][4], wPLM.get(0));
    }
    //Test the forward move of 1 for white when square is taken by white
    @Test public void testForward1WhiteTakenWhite(){
        board[4][4].placePiece(whitePawn1);
        board[3][4].placePiece(whitePawn2);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(0, wPLM.size());
    }
    //Test the forward move of 1 for white when square is taken by black
    @Test public void testForward1WhiteTakenBlack(){
        board[4][4].placePiece(whitePawn1);
        board[3][4].placePiece(blackPawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(0, wPLM.size());
    }
    //Test the forward move of 1 for black when square is taken by white
    @Test public void testForward1BlackTakenWhite(){
        board[4][4].placePiece(blackPawn1);
        board[5][4].placePiece(whitePawn1);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(0, wPLM.size());
    }
    //Test the forward move of 1 for black when square is taken by black
    @Test public void testForward1BlackTakenBlack(){
        board[4][4].placePiece(blackPawn1);
        board[5][4].placePiece(blackPawn2);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(0, wPLM.size());
    }

    //Test right capture for white
    @Test public void testRightCaptureWhite(){
        board[4][4].placePiece(whitePawn1);
        board[3][5].placePiece(blackPawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(board[3][5], wPLM.get(1));
    }
    //Test right capture for white(when other piece is white)
    @Test public void testRightCaptureWhiteOnBlack(){
        board[4][4].placePiece(whitePawn1);
        board[3][5].placePiece(whitePawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(1, wPLM.size());
    }
    //Test left capture for white
    @Test public void testLeftCaptureWhite(){
        board[4][4].placePiece(whitePawn1);
        board[3][3].placePiece(blackPawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(board[3][3], wPLM.get(1));
    }
    //Test left capture for white(when other piece is white)
    @Test public void testLeftCaptureWhiteOnBlack(){
        board[4][4].placePiece(whitePawn1);
        board[3][3].placePiece(whitePawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(1, wPLM.size());
    }
    //Test right capture for black
    @Test public void testRightCaptureBlack(){
        board[4][4].placePiece(blackPawn2);
        board[5][5].placePiece(whitePawn1);
        ArrayList<Square> wPLM = blackPawn2.getLegalMoves(cb);
        assertEquals(board[5][5], wPLM.get(1));
    }
    //Test right capture for black (when other piece is black)
    @Test public void testRightCaptureBlackOnBlack(){
        board[4][4].placePiece(blackPawn2);
        board[5][5].placePiece(blackPawn1);
        ArrayList<Square> wPLM = blackPawn2.getLegalMoves(cb);
        assertEquals(1,wPLM.size());
    }
    //Test left capture for black
    @Test public void testLeftCaptureBlack(){
        board[4][4].placePiece(blackPawn1);
        board[5][3].placePiece(whitePawn1);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(board[5][3], wPLM.get(1));
    }
    //Test left capture for black (when other piece is black)
    @Test public void testLeftCaptureBlackOnBlack(){
        board[4][4].placePiece(blackPawn1);
        board[5][3].placePiece(blackPawn2);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(1, wPLM.size());
    }

    //Test forward 2 for white when both squares in front are empty
    @Test public void testForward2White(){
        board[6][0].placePiece(whitePawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(board[4][0], wPLM.get(1));
    }
    //Test forward 2 for white when not on base row (!= 6 for white)
    @Test public void testForward2WhiteImp(){
        board[5][0].placePiece(whitePawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(1, wPLM.size());
    }
    //Test forward 2 for white when there is a white on the first square
    @Test public void testForward2WhiteOn1White(){
        board[6][0].placePiece(whitePawn1);
        board[5][0].placePiece(whitePawn2);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(0,wPLM.size());
    }
    //Test forward 2 for white when there is a black on the first square
    @Test public void testForward2WhiteOn1Black(){
        board[6][0].placePiece(whitePawn1);
        board[5][0].placePiece(blackPawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(0,wPLM.size());
    }
    //Test forward 2 for white when there is a white on the second square
    @Test public void testForward2WhiteOn2White(){
        board[6][0].placePiece(whitePawn1);
        board[4][0].placePiece(whitePawn2);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(1,wPLM.size());
    }
    //Test forward 2 for white when there is a black on the second square
    @Test public void testForward2WhiteOn2Black(){
        board[6][0].placePiece(whitePawn1);
        board[4][0].placePiece(blackPawn1);
        ArrayList<Square> wPLM = whitePawn1.getLegalMoves(cb);
        assertEquals(1,wPLM.size());
    }


    //Test forward 2 for black when both squares in front are empty
    @Test public void testForward2Black(){
        board[1][0].placePiece(blackPawn1);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(board[3][0], wPLM.get(1));
    }
    //Test forward 2 for black when not on base row (!= 1 for black)
    @Test public void testForward2BlackImp(){
        board[5][0].placePiece(blackPawn1);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(1, wPLM.size());
    }
    //Test forward 2 for black when there is a white on the first square
    @Test public void testForward2BlackOn1White(){
        board[1][0].placePiece(blackPawn1);
        board[2][0].placePiece(whitePawn2);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(0,wPLM.size());
    }
    //Test forward 2 for black when there is a black on the first square
    @Test public void testForward2BlackOn1Black(){
        board[1][0].placePiece(blackPawn2);
        board[2][0].placePiece(blackPawn1);
        ArrayList<Square> wPLM = blackPawn2.getLegalMoves(cb);
        assertEquals(0,wPLM.size());
    }
    //Test forward 2 for black when there is a white on the second square
    @Test public void testForward2BlackOn2White(){
        board[1][0].placePiece(blackPawn1);
        board[3][0].placePiece(whitePawn2);
        ArrayList<Square> wPLM = blackPawn1.getLegalMoves(cb);
        assertEquals(1,wPLM.size());
    }
    //Test forward 2 for black when there is a black on the second square
    @Test public void testForward2BlackOn2Black(){
        board[1][0].placePiece(blackPawn2);
        board[3][0].placePiece(blackPawn1);
        ArrayList<Square> wPLM = blackPawn2.getLegalMoves(cb);
        assertEquals(1,wPLM.size());
    }

    //Test promotion for white
    
    @Test public void testPromotionWhite(){
        ChessBoard cb = new ChessBoard(true);
        Pawn wP = new Pawn(true);
        cb.placePiece(cb.getSquare(1,0), wP);
        Move move = new Move(cb.getSquare(1, 0), cb.getSquare(0,0));
        cb.playMove(move);
        assertEquals("Queen", cb.getSquare(0,0).getPieceOnSq().getPieceName());
        assertEquals(true, cb.getSquare(0,0).getPieceOnSq().isWhite());
        assertEquals(false, cb.getSquare(1,0).isTakenSquare());
    }
    //Test promotion for black
   
    @Test public void testPromotionBlack(){
        ChessBoard cb = new ChessBoard(true);
        Pawn wP = new Pawn(false);
        cb.placePiece(cb.getSquare(6,7), wP);
        Move move = new Move(cb.getSquare(6, 7), cb.getSquare(7,7));
        cb.playMove(move);
        assertEquals("Queen", cb.getSquare(7,7).getPieceOnSq().getPieceName());
        assertEquals(false, cb.getSquare(7,7).getPieceOnSq().isWhite());
        assertEquals(false, cb.getSquare(6,7).isTakenSquare());
    }
    
    //Test En Passant to the left for white
    @Test public void testEPLeftWhite(){
        board[3][6].placePiece(whitePawn1);
        board[1][5].placePiece(blackPawn1);
        ArrayList<Square> blackLM = blackPawn1.getLegalMoves(cb);
        blackPawn1.move(board[3][5], cb, blackLM);
        ArrayList<Square> whiteLM = whitePawn1.getLegalMoves(cb);
        assertEquals(board[2][5], whiteLM.get(1));
    }
    //Test En Passant to the right for white
    @Test public void testEPRightWhite(){
        board[3][2].placePiece(whitePawn1);
        board[1][3].placePiece(blackPawn1);
        ArrayList<Square> blackLM = blackPawn1.getLegalMoves(cb);
        blackPawn1.move(board[3][3], cb, blackLM);
        ArrayList<Square> whiteLM = whitePawn1.getLegalMoves(cb);
        assertEquals(board[2][3], whiteLM.get(1));
    }
    //Test En passant but 2 forward wasn't last ply for white
    @Test public void testEPIllegalWhite(){
        board[3][2].placePiece(whitePawn1);
        board[1][3].placePiece(blackPawn1);
        board[1][0].placePiece(blackPawn2);
        
        ArrayList<Square> black1LM = blackPawn1.getLegalMoves(cb);
        ArrayList<Square> black2LM = blackPawn2.getLegalMoves(cb);
        blackPawn1.move(board[3][3], cb, black1LM);
        blackPawn2.move(board[2][0], cb, black2LM);
        ArrayList<Square> whiteLM = whitePawn1.getLegalMoves(cb);
        assertEquals(1, whiteLM.size());
    }
    //Test En Passant to the left for black
    @Test public void testEPLeftBlack(){
        board[6][0].placePiece(whitePawn1);
        board[4][1].placePiece(blackPawn1);
        ArrayList<Square> whiteLM = whitePawn1.getLegalMoves(cb);
        whitePawn1.move(board[4][0], cb, whiteLM);
        ArrayList<Square> blackLM = blackPawn1.getLegalMoves(cb);
        assertEquals(board[5][0], blackLM.get(1));
    }
    //Test En Passant to the right for black
    @Test public void testEPRightBlack(){
        board[6][2].placePiece(whitePawn1);
        board[4][1].placePiece(blackPawn1);
        ArrayList<Square> whiteLM = whitePawn1.getLegalMoves(cb);
        whitePawn1.move(board[4][2], cb, whiteLM);
        ArrayList<Square> blackLM = blackPawn1.getLegalMoves(cb);
        assertEquals(board[5][2], blackLM.get(1));
    }
    //Test En passant but 2 forward wasn't last ply for black
    @Test public void testEPIllegalBlack(){
        board[4][3].placePiece(blackPawn1);
        board[6][4].placePiece(whitePawn1);
        board[1][0].placePiece(blackPawn2);
        ArrayList<Square> whiteLM = whitePawn1.getLegalMoves(cb);
        ArrayList<Square> black2LM = blackPawn2.getLegalMoves(cb);
        whitePawn1.move(board[4][4], cb, whiteLM);
        blackPawn2.move(board[2][0], cb, black2LM);
        ArrayList<Square> blackLM = blackPawn1.getLegalMoves(cb);
        assertEquals(1, blackLM.size());
    }
    

    

}
