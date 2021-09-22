package test;

import com.sun.security.jgss.GSSUtil;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import controller.*;

public class QueenTest {
    @Test
    void queenNoLegalMovesAvaiable() {
        ChessBoard cb = new ChessBoard();
        Queen queen = (Queen) cb.getOccupingPiece(7, 3);
        assertTrue(queen.getLegalMoves(cb).isEmpty());
    }

    @Test
    void queenLegalMovesAvaiable() {
        ChessBoard cb = new ChessBoard();
        Queen queen = new Queen(true);
        cb.getSquare(5, 4).placePiece(queen);

        ArrayList<Square> ActualLegalMoves = queen.getLegalMoves(cb);
        ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
        Square[][] board = cb.getBoard();

        List<Square> AllExpectedLegalmoves = Arrays.asList(
                board[5][5],
                board[5][6],
                board[5][7],
                board[5][3],
                board[5][2],
                board[5][1],
                board[5][0],
                board[4][4],
                board[3][4],
                board[2][4],
                board[1][4],
                board[4][3],
                board[3][2],
                board[2][1],
                board[1][0],
                board[4][5],
                board[3][6],
                board[2][7]
        );

        ExpectedLegalMoves.addAll(AllExpectedLegalmoves);
        assertTrue(
                ActualLegalMoves.size() == ExpectedLegalMoves.size()
                        && ExpectedLegalMoves.containsAll(ActualLegalMoves)
                        && ActualLegalMoves.containsAll(ExpectedLegalMoves)
        );
    }

    @Test void queenMoveTest() {
        ChessBoard cb = new ChessBoard();
        Queen queen = new Queen(true);
        cb.getSquare(5, 4).placePiece(queen);
        queen.move(cb.getSquare(2, 7), cb, queen.getLegalMoves(cb));
        assertEquals(cb.getSquare(2,7), queen.getCurrentPosition());
    }

    @Test void queenCaptureTest() {
        ChessBoard cb = new ChessBoard();
        Queen queen = new Queen(true);
        cb.getSquare(5, 4).placePiece(queen);
        queen.move(cb.getSquare(1, 4), cb, queen.getLegalMoves(cb));
        assertEquals(cb.getSquare(1,4), queen.getCurrentPosition());
    }

}
