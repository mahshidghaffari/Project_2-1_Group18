

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import controller.*;

class RookTest {
	ChessBoard cb = new ChessBoard(true);
	Square[][] board = cb.getBoard();
	Rook whiteRook = new Rook(true);
	Rook blackRook = new Rook(false);
	Pawn whitePawn = new Pawn(true);
	Pawn blackPawn = new Pawn(false);
	Bishop whiteBishop = new Bishop(true);
	Bishop blackBishop = new Bishop(false);

	// checking all the available move for all directions (up,down,left, right )
	// white player
	@Test
	void whiteRookAllDirectionsLegalMove() {

		cb.getSquare(4, 3).placePiece(whiteRook);

		ArrayList<Square> ActualLegalMoves = whiteRook.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
		Square[][] board = cb.getBoard();

		List<Square> AllExpectedLegalmoves = Arrays.asList(board[4][0], board[4][1], board[4][2], board[4][4],
				board[4][5], board[4][6], board[4][7], board[0][3], board[1][3], board[2][3], board[3][3], board[5][3],
				board[6][3], board[7][3]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);
		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));

	}

	// checking all the legal moves when there is not legal move
	// black Player

	@Test
	void BlackRookAllDirectionsLegalMove() {
		cb.getSquare(3, 4).placePiece(blackRook);

		ArrayList<Square> ActualLegalMoves = blackRook.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();

		List<Square> AllExpectedLegalmoves = Arrays.asList(board[3][0], board[3][1], board[3][2], board[3][3],
				board[3][5], board[3][6], board[3][7], board[0][4], board[1][4], board[2][4], board[4][4], board[5][4],
				board[6][4], board[7][4]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);
		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));

	}

	// check the legal move when there is no
	// white player

	@Test
	void WhiteRookNoLegalMove() {
		cb.getSquare(7, 0).placePiece(whiteRook);
		cb.getSquare(6, 0).placePiece(whitePawn);
		cb.getSquare(7, 1).placePiece(whiteBishop);
		assertTrue(whiteRook.getLegalMoves(cb).isEmpty());

	}
	// check the legal move when there is no
	// white player

	@Test
	void BlackRookNoLegalMove() {
		cb.getSquare(0, 0).placePiece(blackRook);
		cb.getSquare(1, 0).placePiece(blackPawn);
		cb.getSquare(0, 1).placePiece(blackBishop);
		assertTrue(blackRook.getLegalMoves(cb).isEmpty());

	}

	// checking the capturing and blocking
	// white Rook

	@Test
	void whiteRookCapturingTest() {

		cb.getSquare(7, 0).placePiece(whiteRook);
		cb.getSquare(1, 0).placePiece(blackPawn);
		cb.getSquare(7, 1).placePiece(blackBishop);
		
		ArrayList<Square> ActualLegalMoves = whiteRook.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();


		List<Square> AllExpectedLegalmoves = Arrays.asList(board[7][1], board[6][0], board[5][0], board[4][0],
				board[3][0], board[2][0], board[1][0]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);
		
		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));
	
	}
	@Test
	void blackRookCapturingTest() {

		cb.getSquare(0, 7).placePiece(blackRook);
		cb.getSquare(7, 7).placePiece(whitePawn);
		cb.getSquare(0, 6).placePiece(whiteBishop);
		
		ArrayList<Square> ActualLegalMoves = blackRook.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();


		List<Square> AllExpectedLegalmoves = Arrays.asList(board[0][6], board[1][7], board[2][7], board[3][7],
				board[4][7], board[5][7], board[6][7], board[7][7]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);
		
		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves));
	
	}
	

}
