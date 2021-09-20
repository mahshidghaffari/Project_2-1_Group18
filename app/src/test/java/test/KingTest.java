package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import controller.*;

class KingTest {

    @Test void whiteKingLegalMoveTest() {
        ChessBoard cb = new ChessBoard(true);
        King king = new King(true);
        cb.getSquare(4, 4).placePiece(king);
        assertEquals(8, king.getLegalMoves(cb).size());
    }
    @Test void blackKingLegalMoveTest() {
        ChessBoard cb = new ChessBoard(true);
        King king = new King(false);
        cb.getSquare(4, 4).placePiece(king);
        assertEquals(8, king.getLegalMoves(cb).size());
    }

    @Test void blacKingMoveTest() {
        ChessBoard cb = new ChessBoard();
        King king = new King(false);
        cb.getSquare(4, 4).placePiece(king);
        king.move(cb.getSquare(3, 3), cb, king.getLegalMoves(cb));
        assertEquals(cb.getSquare(3,3), king.getCurrentPosition());
    }

    @Test void KingCapturePieceTest() {
        ChessBoard cb = new ChessBoard(true);
        King king = new King(true);
        Queen queen = new Queen(false);
        cb.getSquare(4, 4).placePiece(king);
        cb.getSquare(5,5).placePiece(queen);
        king.move(queen.getCurrentPosition(), cb, king.getLegalMoves(cb));
        assertEquals(king, cb.getSquare(5,5));
    }

    @Test void BlackKingCloseCastleTest(){
        ChessBoard cb = new ChessBoard(true);
        King king = new King(false);
        Rook rook = new Rook(false);
        cb.getSquare(0, 4).placePiece(king);
        cb.getSquare(0, 7).placePiece(rook);
        assertTrue(king.getLegalMoves(cb).contains(rook.getCurrentPosition()));
    }
    @Test void BlackKingFarCastleTest(){
        ChessBoard cb = new ChessBoard(true);
        King king = new King(false);
        Rook rook = new Rook(false);
        cb.getSquare(0, 4).placePiece(king);
        cb.getSquare(0, 0).placePiece(rook);
        assertTrue(king.getLegalMoves(cb).contains(rook.getCurrentPosition()));
    }
    @Test void WhiteKingCloseCastleTest(){
        ChessBoard cb = new ChessBoard(true);
        King king = new King(true);
        Rook rook = new Rook(true);
        cb.getSquare(7, 4).placePiece(king);
        cb.getSquare(7, 7).placePiece(rook);
        assertTrue(king.getLegalMoves(cb).contains(rook.getCurrentPosition()));
    }
    @Test void WhiteKingFarCastleTest(){
        ChessBoard cb = new ChessBoard(true);
        King king = new King(true);
        Rook rook = new Rook(true);
        cb.getSquare(7, 4).placePiece(king);
        cb.getSquare(7, 0).placePiece(rook);
        assertTrue(king.getLegalMoves(cb).contains(rook.getCurrentPosition()));
    }
    @Test void PieceThreateningKingBeforeCastle(){
        ChessBoard cb = new ChessBoard(true);
        King king = new King(true);
        Rook rook = new Rook(true);
        Pawn pawn = new Pawn(false);
        cb.getSquare(7, 4).placePiece(king);
        cb.getSquare(7, 0).placePiece(rook); 
        cb.getSquare(6, 3).placePiece(pawn);
        assertFalse(king.getLegalMoves(cb).contains(rook.getCurrentPosition()));
    }
    @Test void PieceThreateningSpotInCastlePath(){
        ChessBoard cb = new ChessBoard(true);
        King king = new King(true);
        Rook rook = new Rook(true);
        Queen queen = new Queen(false);
        cb.getSquare(7, 4).placePiece(king);
        cb.getSquare(7, 7).placePiece(rook);
        cb.getSquare(5, 5).placePiece(queen);
        assertFalse(king.getLegalMoves(cb).contains(rook.getCurrentPosition()));
    }
    @Test void PieceThreateningLastSpotInCastle(){
        ChessBoard cb = new ChessBoard(true);
        King king = new King(false);
        Rook rook = new Rook(false);
        Queen queen = new Queen(true);
        cb.getSquare(0, 4).placePiece(king);
        cb.getSquare(0, 0).placePiece(rook);
        cb.getSquare(5, 0).placePiece(queen);
        assertFalse(king.getLegalMoves(cb).contains(rook.getCurrentPosition()));
    }


}

