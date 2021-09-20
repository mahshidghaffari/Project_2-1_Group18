package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import controller.*;

class KnightTest {

    @Test void whiteKnightMoveTest() {
        ChessBoard cb = new ChessBoard();
        Knight knight = (Knight)cb.getOccupingPiece(7, 1);
        Square target = new Square(5, 0);
        knight.move(target, cb, knight.getLegalMoves(cb));
        assertEquals(target, knight.getCurrentPosition());
    }

    @Test void blacKnightMoveTest() {
        ChessBoard cb = new ChessBoard();
        Knight knight = (Knight)cb.getOccupingPiece(0, 6);
        Square target = new Square(2, 5);
        knight.move(target, cb, knight.getLegalMoves(cb));
        assertEquals(target, knight.getCurrentPosition());
    }

    @Test void KnightCapturePieceTest() {
        ChessBoard cb = new ChessBoard(true);
        Knight knight = new Knight(true);
        Queen queen = new Queen(false);
        cb.getSquare(4, 4).placePiece(knight);
        cb.getSquare(2,3).placePiece(queen);
        knight.move(queen.getCurrentPosition(), cb, knight.getLegalMoves(cb));
        assertEquals(knight, cb.getSquare(2,3));
    }
}

