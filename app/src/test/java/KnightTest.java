import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.*;

class KnightTest {

	ChessBoard cb = new ChessBoard(true);
	Square[][] board = cb.getBoard();
	Knight whiteKnight = new Knight(true);
	Knight blackKnight = new Knight(false);
	Pawn whitePawn = new Pawn(true);
	Pawn blackPawn = new Pawn(false);
	Bishop whiteBishop = new Bishop(true);
	Bishop blackBishop = new Bishop(false);

	// Check all the legal move for Knight in all directions
	// checking the white knight in the WHITE square position

	@Test
	void allLegalMoveWhiteKnightForEachDirection() {

		cb.getSquare(4, 4).placePiece(whiteKnight);

		ArrayList<Square> ActualLegalMoves = whiteKnight.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
		Square[][] board = cb.getBoard();

		List<Square> AllExpectedLegalmoves = Arrays.asList(board[3][6], board[5][6], board[2][3], board[2][5],
				board[6][3], board[6][5], board[3][2], board[5][2]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);

		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));
	}

	// Check all the legal move for Knight in all directions
	// checking the Black knight in the BLACK square position

	@Test
	void allLegalMoveBlackKnightForEachDirection() {

		cb.getSquare(4, 3).placePiece(whiteKnight);

		ArrayList<Square> ActualLegalMoves = whiteKnight.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
		Square[][] board = cb.getBoard();

		List<Square> AllExpectedLegalmoves = Arrays.asList(board[3][5], board[5][5], board[2][2], board[2][4],
				board[6][4], board[6][2], board[3][1], board[5][1]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);

		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));
	}

	// check the movement when there is none
	// for white

	@Test
	void WhiteRookNoLegalMove() {
		cb.getSquare(7, 6).placePiece(whiteKnight);
		cb.getSquare(5, 7).placePiece(whitePawn);
		cb.getSquare(6, 4).placePiece(whitePawn);
		cb.getSquare(5, 5).placePiece(whiteBishop);
		assertTrue(whiteKnight.getLegalMoves(cb).isEmpty());

	}

	// check the movement when there is none
	// for Black

	@Test
	void BlackRookNoLegalMove() {
		cb.getSquare(0, 1).placePiece(blackKnight);
		cb.getSquare(2, 0).placePiece(blackPawn);
		cb.getSquare(2, 2).placePiece(blackPawn);
		cb.getSquare(1, 3).placePiece(blackBishop);
		assertTrue(blackKnight.getLegalMoves(cb).isEmpty());

	}

	// checking capturing and blocking
	// white Knight

	@Test
	void whiteKnightCapturingTest() {
		cb.getSquare(7, 6).placePiece(whiteKnight);
		cb.getSquare(5, 7).placePiece(blackPawn);
		cb.getSquare(6, 4).placePiece(whitePawn);
		cb.getSquare(5, 5).placePiece(whiteBishop);

		Square[][] board = cb.getBoard();
		ArrayList<Square> ActualLegalMoves = whiteKnight.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();

		ExpectedLegalMoves.add(board[5][7]);
		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));

	}

	// checking capturing and blocking
	// black Knight

	@Test
	void BlackKnightCapturingTest() {
		cb.getSquare(0, 1).placePiece(blackKnight);
		cb.getSquare(2, 0).placePiece(blackPawn);
		cb.getSquare(2, 2).placePiece(whitePawn);
		cb.getSquare(1, 3).placePiece(blackBishop);

		Square[][] board = cb.getBoard();
		ArrayList<Square> ActualLegalMoves = blackKnight.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();

		ExpectedLegalMoves.add(board[2][2]);
		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));

	}

}
