

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.*;

// this class is for testing all of the test cases for king

//1. check for all the possible movement in all directions
//2. check when there is not any legal move
//3. when it has the capturing options

//useful code line for debugging :  
//	cb.printBoard();       for printing the current state of the chess board
//	System.out.println(ActualLegalMoves);   printing the the legal move that is happening at the moment
//	System.out.println(ExpectedLegalMoves);  printing the expected and right legal movement

class KingTest {
	ChessBoard cb = new ChessBoard(true);
	King whiteKing = new King(true);
	King blackKing = new King(false);
	Pawn whitePawn = new Pawn(true);
	Pawn blackPawn = new Pawn(false);
	Rook whiteRook = new Rook(true);
	Rook blackRook = new Rook(false);
	

	// check all of the movement options for all directions
	// checking the white king

	@Test
	void whiteKingLegalMoveInAllDirectionTest() {

		cb.getSquare(4, 3).placePiece(whiteKing); // insert the king in the middle of chess board without any other
		whiteKing.setNotYetMoved(false);										// pieces

		ArrayList<Square> ActualLegalMoves = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
		Square[][] board = cb.getBoard();
		// adding all the possible movements
		List<Square> AllExpectedLegalmoves = Arrays.asList(board[3][2], board[3][3], board[3][4], board[4][2],
				board[5][2], board[5][3], board[5][4], board[4][4]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);

		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));
		

	}

	// check all of the movement options for all directions
	// checking the black king

	@Test
	void blackKingLegalMoveInAllDirectionTest() {

		cb.getSquare(4, 2).placePiece(blackKing); // insert the king in the middle of chess board without any other
		blackKing.setNotYetMoved(false);							// pieces

		ArrayList<Square> ActualLegalMoves = blackKing.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
		Square[][] board = cb.getBoard();
		// adding all the possible movements
		List<Square> AllExpectedLegalmoves = Arrays.asList(board[3][1], board[3][2], board[3][3], board[4][1],
				board[5][2], board[5][3], board[5][1], board[4][3]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);

		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));

	}

	// check all of the movement options for all directions
	// checking the white king

	@Test
	void whiteKingLegalBlockMoveTest() {

		cb.getSquare(7, 0).placePiece(whiteKing); // insert king in the block position
		cb.getSquare(6, 0).placePiece(whitePawn);
		cb.getSquare(6, 1).placePiece(whitePawn);
		cb.getSquare(7, 1).placePiece(whiteRook);
		whiteKing.setNotYetMoved(false);	

		assertTrue(whiteKing.getLegalMoves(cb).isEmpty());

	}

	// check all of the movement options for all directions
	// checking the Black king

	@Test
	void blackKingLegalBlockMoveTest() {

		cb.getSquare(0, 7).placePiece(blackKing); // insert king in the block position
		cb.getSquare(1, 7).placePiece(blackPawn);
		cb.getSquare(1, 6).placePiece(blackPawn);
		cb.getSquare(0, 6).placePiece(blackRook);
		blackKing.setNotYetMoved(false);	
		


		assertTrue(blackKing.getLegalMoves(cb).isEmpty());

	}

	// checking the capturing situation
	// white King
	@Test
	void whiteKingLegalMoveCapturingTest() {

		cb.getSquare(4, 0).placePiece(whiteKing); // insert the king in position the have the block move , free move and
													// capturing situation
		cb.getSquare(5, 0).placePiece(whitePawn);
		cb.getSquare(4, 1).placePiece(whiteRook);
		cb.getSquare(3, 1).placePiece(blackRook);
		cb.getSquare(3, 0).placePiece(blackPawn);
		whiteKing.setNotYetMoved(false);

		ArrayList<Square> ActualLegalMoves = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
		Square[][] board = cb.getBoard();
		// adding all the possible movements
		List<Square> AllExpectedLegalmoves = Arrays.asList(board[3][0], board[3][1], board[5][1]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);

		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));

	}

	// checking the capturing situation
	// black King
	@Test
	void blackKingLegalMoveCapturingTest() {

		cb.getSquare(3, 7).placePiece(blackKing); // insert the king in position the have the block move , free move and
													// capturing situation
		cb.getSquare(2, 7).placePiece(blackPawn);
		cb.getSquare(3, 6).placePiece(blackRook);
		cb.getSquare(4, 6).placePiece(whiteRook);
		cb.getSquare(4, 7).placePiece(whitePawn);
		blackKing.setNotYetMoved(false);	

		ArrayList<Square> ActualLegalMoves = blackKing.getLegalMoves(cb);
		ArrayList<Square> ExpectedLegalMoves = new ArrayList<>();
		Square[][] board = cb.getBoard();
		// adding all the possible movements
		List<Square> AllExpectedLegalmoves = Arrays.asList(board[2][6], board[4][6], board[4][7]);
		ExpectedLegalMoves.addAll(AllExpectedLegalmoves);

		assertTrue(
				ActualLegalMoves.size() == ExpectedLegalMoves.size() && ExpectedLegalMoves.containsAll(ActualLegalMoves)
						&& ActualLegalMoves.containsAll(ExpectedLegalMoves));

	}
	
	
}
