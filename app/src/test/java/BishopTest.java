import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import controller.*;

public class BishopTest {
    @Test
    void bishopNoLegalMovesAvaiable() {
        ChessBoard cb = new ChessBoard();
        Bishop bishop = (Bishop) cb.getOccupingPiece(7, 2);
        cb.getSquare(7, 2).removePiece(bishop);
        assertTrue(bishop.getLegalMoves(cb).isEmpty());
    }

    @Test
    void bishopLegalMovesAvaiable() {
        ChessBoard cb = new ChessBoard();
        Bishop bishop = (Bishop) cb.getOccupingPiece(7, 2);
        cb.getSquare(7, 2).removePiece(bishop);
        cb.getSquare(4, 5).placePiece(bishop);

        ArrayList<Square> ActualLegalMoves = bishop.getLegalMoves(cb);
        ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
        Square[][] board = cb.getBoard();

        List<Square> AllExpectedLegalMoves = Arrays.asList(
                board[5][4],
                board[5][6],
                board[3][6],
                board[2][7],
                board[3][4],
                board[2][3],
                board[1][2]
        );
        ExpectedLegalMoves.addAll(AllExpectedLegalMoves);
        assertTrue(
                ActualLegalMoves.size() == ExpectedLegalMoves.size()
                        && ExpectedLegalMoves.containsAll(ActualLegalMoves)
                        && ActualLegalMoves.containsAll(ExpectedLegalMoves)
        );
    }

    @Test void bishopMoveTest() {
        ChessBoard cb = new ChessBoard();
        Bishop bishop = (Bishop) cb.getOccupingPiece(7, 2);
        cb.getSquare(7, 2).removePiece(bishop);
        cb.getSquare(5, 4).placePiece(bishop);
        bishop.move(cb.getSquare(3, 6), cb, bishop.getLegalMoves(cb));
        assertEquals(cb.getSquare(3,6), bishop.getCurrentPosition());
    }

    @Test void bishopCaptureTest() {
        ChessBoard cb = new ChessBoard();
        Bishop bishop = (Bishop) cb.getOccupingPiece(7, 2);
        cb.getSquare(7, 2).removePiece(bishop);
        cb.getSquare(5, 4).placePiece(bishop);
        bishop.move(cb.getSquare(1, 0), cb, bishop.getLegalMoves(cb));
        assertEquals(cb.getSquare(1,0), bishop.getCurrentPosition());
    }

}
