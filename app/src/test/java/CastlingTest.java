

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.*;

// this class is for testing all of the test cases for king

//useful code line for debugging :  
//	cb.printBoard();       for printing the current state of the chess board
//	System.out.println(ActualLegalMoves);   printing the the legal move that is happening at the moment
//	System.out.println(ExpectedLegalMoves);  printing the expected and right legal movement

class CastlingTest {	
	ChessBoard cb = new ChessBoard(true);
	King whiteKing = new King(true);
	King blackKing = new King(false);
	Pawn whitePawn = new Pawn(true);
	Pawn blackPawn = new Pawn(false);
	Rook whiteRookRight = new Rook(true);
	Rook whiteRookLeft = new Rook(true);
	Rook blackRookRight = new Rook(false);
	Rook blackRookLeft = new Rook(false);
	Square[][] board = cb.getBoard();

	// checking all movement for close castling for white king and Rook when there
	// is not obstacle
	@Test
	void whiteKingCloseCastling() {
		// setting a suitable position for close castling
		cb.getSquare(7, 4).placePiece(whiteKing);
		cb.getSquare(7, 7).placePiece(whiteRookRight);
		cb.getSquare(7, 1).placePiece(whiteRookLeft);
		cb.getSquare(6, 7).placePiece(whitePawn);
		cb.getSquare(6, 6).placePiece(whitePawn);
		cb.getSquare(6, 5).placePiece(whitePawn);
		whiteKing.setNotYetMoved(true);

		ArrayList<Square> ActualLegalMovesWhiteKing = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesWhiteRook = whiteRookRight.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesWhiteKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesWhiteRook = new ArrayList<Square>();

		// adding all the possible movements
		List<Square> AllExpectedLegalmovesWhiteKing = Arrays.asList(board[7][3], board[6][3], board[6][4], board[7][5],
				board[7][6]);
		List<Square> AllExpectedLegalmovesWhiteRook = Arrays.asList(board[7][5], board[7][6]);

		ExpectedLegalMovesWhiteKing.addAll(AllExpectedLegalmovesWhiteKing);
		ExpectedLegalMovesWhiteRook.addAll(AllExpectedLegalmovesWhiteRook);
		


		assertTrue(ActualLegalMovesWhiteKing.size() == ExpectedLegalMovesWhiteKing.size()
				&& ExpectedLegalMovesWhiteKing.containsAll(ActualLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteKing.containsAll(ExpectedLegalMovesWhiteKing)
				&&ActualLegalMovesWhiteRook.size() == ExpectedLegalMovesWhiteRook.size()
				&& ExpectedLegalMovesWhiteRook.containsAll(ActualLegalMovesWhiteRook)
				&& ActualLegalMovesWhiteRook.containsAll(ExpectedLegalMovesWhiteRook)
				);

	}
	

	// checking all movement for close castling for black king and Rook when there
	// is not obstacle
	@Test
	void blackKingCloseCastling() {

		// setting a suitable position for close castling
		cb.getSquare(0, 4).placePiece(blackKing);
		cb.getSquare(0, 7).placePiece(blackRookRight);
		cb.getSquare(0, 1).placePiece(blackRookLeft);
		cb.getSquare(1, 7).placePiece(blackPawn);
		cb.getSquare(1, 6).placePiece(blackPawn);
		cb.getSquare(1, 5).placePiece(blackPawn);
		blackKing.setNotYetMoved(true);

		ArrayList<Square> ActualLegalMovesBlackKing = blackKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesBlackRook = blackRookRight.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesBlackKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesBlackRook = new ArrayList<Square>();

		// adding all the possible movements
		List<Square> AllExpectedLegalmovesBlackKing = Arrays.asList(board[0][3], board[1][3], board[1][4], board[0][5],
				board[0][6]);
		List<Square> AllExpectedLegalmovesBlackRook = Arrays.asList(board[0][5], board[0][6]);

		ExpectedLegalMovesBlackKing.addAll(AllExpectedLegalmovesBlackKing);
		ExpectedLegalMovesBlackRook.addAll(AllExpectedLegalmovesBlackRook);
		

		assertTrue(ActualLegalMovesBlackKing.size() == ExpectedLegalMovesBlackKing.size()
				&& ExpectedLegalMovesBlackKing.containsAll(ActualLegalMovesBlackKing)
				&& ActualLegalMovesBlackKing.containsAll(ExpectedLegalMovesBlackKing)
				&&ActualLegalMovesBlackRook.size() == ExpectedLegalMovesBlackRook.size()
				&& ExpectedLegalMovesBlackRook.containsAll(ActualLegalMovesBlackRook)
				&& ActualLegalMovesBlackRook.containsAll(ExpectedLegalMovesBlackRook)
				);

	}
	
	// checking all movement for far castling for white king and Rook when there
	// is not obstacle
	@Test
	void whiteKingFarCastling() {

		// setting a suitable position for close castling
		cb.getSquare(7, 4).placePiece(whiteKing);
		cb.getSquare(7, 6).placePiece(whiteRookRight);
		cb.getSquare(7, 0).placePiece(whiteRookLeft);
		cb.getSquare(6, 0).placePiece(whitePawn);
		cb.getSquare(6, 1).placePiece(whitePawn);
		cb.getSquare(6, 2).placePiece(whitePawn);
		whiteKing.setNotYetMoved(true);

		ArrayList<Square> ActualLegalMovesWhiteKing = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesWhiteRook = whiteRookLeft.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesWhiteKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesWhiteRook = new ArrayList<Square>();

//		 adding all the possible movements
		List<Square> AllExpectedLegalmovesWhiteKing = Arrays.asList(board[7][5], board[6][5], board[6][4], board[7][3],
				board[6][3],board[7][2]);
		List<Square> AllExpectedLegalmovesWhiteRook = Arrays.asList(board[7][1], board[7][2],board[7][3]);

		ExpectedLegalMovesWhiteKing.addAll(AllExpectedLegalmovesWhiteKing);
		ExpectedLegalMovesWhiteRook.addAll(AllExpectedLegalmovesWhiteRook);
		

		assertTrue(ActualLegalMovesWhiteKing.size() == ExpectedLegalMovesWhiteKing.size()
				&& ExpectedLegalMovesWhiteKing.containsAll(ActualLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteKing.containsAll(ExpectedLegalMovesWhiteKing)
				&&ActualLegalMovesWhiteRook.size() == ExpectedLegalMovesWhiteRook.size()
				&& ExpectedLegalMovesWhiteRook.containsAll(ActualLegalMovesWhiteRook)
				&& ActualLegalMovesWhiteRook.containsAll(ExpectedLegalMovesWhiteRook)
				);

	}

	// checking all movement far close castling for black king and Rook when there
	// is not obstacle
	@Test
	void blackKingFarCastling() {

		// setting a suitable position for close castling
		cb.getSquare(0, 4).placePiece(blackKing);
		cb.getSquare(0, 6).placePiece(blackRookRight);
		cb.getSquare(0, 0).placePiece(blackRookLeft);
		cb.getSquare(1, 0).placePiece(blackPawn);
		cb.getSquare(1, 1).placePiece(blackPawn);
		cb.getSquare(1, 2).placePiece(blackPawn);
		blackKing.setNotYetMoved(true);

		ArrayList<Square> ActualLegalMovesBlackKing = blackKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesBlackRook = blackRookLeft.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesBlackKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesBlackRook = new ArrayList<Square>();

//		 adding all the possible movements
		List<Square> AllExpectedLegalmovesBlackKing = Arrays.asList(board[0][5], board[1][5], board[1][4], board[0][3],
				board[1][3],board[0][2]);
		List<Square> AllExpectedLegalmovesBlackRook = Arrays.asList(board[0][1], board[0][2],board[0][3]);

		ExpectedLegalMovesBlackKing.addAll(AllExpectedLegalmovesBlackKing);
		ExpectedLegalMovesBlackRook.addAll(AllExpectedLegalmovesBlackRook);
		
		cb.printBoard();
		System.out.println(ExpectedLegalMovesBlackKing);
		System.out.println(ActualLegalMovesBlackKing);

		assertTrue(ActualLegalMovesBlackKing.size() == ExpectedLegalMovesBlackKing.size()
				&& ExpectedLegalMovesBlackKing.containsAll(ActualLegalMovesBlackKing)
				&& ActualLegalMovesBlackKing.containsAll(ExpectedLegalMovesBlackKing)
				&&ActualLegalMovesBlackRook.size() == ExpectedLegalMovesBlackRook.size()
				&& ExpectedLegalMovesBlackRook.containsAll(ActualLegalMovesBlackRook)
				&& ActualLegalMovesBlackRook.containsAll(ExpectedLegalMovesBlackRook)
				);

	}

	// checking for the close castling when rook moved before
	@Test
	void closeCastlingBlockedRook() {

		// setting a suitable position for close castling
		cb.getSquare(7, 4).placePiece(whiteKing);
		cb.getSquare(7, 7).placePiece(whiteRookRight);
		cb.getSquare(7, 1).placePiece(whiteRookLeft);
		cb.getSquare(6, 7).placePiece(whitePawn);
		cb.getSquare(6, 6).placePiece(whitePawn);
		cb.getSquare(6, 5).placePiece(whitePawn);
		whiteKing.setNotYetMoved(true);
		whiteRookRight.setNotYetMoved(false);

		ArrayList<Square> ActualLegalMovesWhiteKing = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesWhiteRook = whiteRookRight.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesWhiteKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesWhiteRook = new ArrayList<Square>();

		// adding all the possible movements
		List<Square> AllExpectedLegalmovesWhiteKing = Arrays.asList(board[7][3], board[6][3], board[6][4], board[7][5]);
		List<Square> AllExpectedLegalmovesWhiteRook = Arrays.asList(board[7][5], board[7][6]);

		ExpectedLegalMovesWhiteKing.addAll(AllExpectedLegalmovesWhiteKing);
		ExpectedLegalMovesWhiteRook.addAll(AllExpectedLegalmovesWhiteRook);

		assertTrue(ActualLegalMovesWhiteKing.size() == ExpectedLegalMovesWhiteKing.size()
				&& ExpectedLegalMovesWhiteKing.containsAll(ActualLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteKing.containsAll(ExpectedLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteRook.size() == ExpectedLegalMovesWhiteRook.size()
				&& ExpectedLegalMovesWhiteRook.containsAll(ActualLegalMovesWhiteRook)
				&& ActualLegalMovesWhiteRook.containsAll(ExpectedLegalMovesWhiteRook));

	}

	// checking all movement far castling when Rook already moves
	// is not obstacle
	@Test
	void farCaslingBlockRook() {

		// setting a suitable position for close castling
		cb.getSquare(7, 4).placePiece(whiteKing);
		cb.getSquare(7, 6).placePiece(whiteRookRight);
		cb.getSquare(7, 0).placePiece(whiteRookLeft);
		cb.getSquare(6, 0).placePiece(whitePawn);
		cb.getSquare(6, 1).placePiece(whitePawn);
		cb.getSquare(6, 2).placePiece(whitePawn);
		whiteKing.setNotYetMoved(true);
		whiteRookLeft.setNotYetMoved(false);

		ArrayList<Square> ActualLegalMovesWhiteKing = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesWhiteRook = whiteRookLeft.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesWhiteKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesWhiteRook = new ArrayList<Square>();

//		 adding all the possible movements
		List<Square> AllExpectedLegalmovesWhiteKing = Arrays.asList(board[7][5], board[6][5], board[6][4], board[7][3],
				board[6][3]);
		List<Square> AllExpectedLegalmovesWhiteRook = Arrays.asList(board[7][1], board[7][2], board[7][3]);

		ExpectedLegalMovesWhiteKing.addAll(AllExpectedLegalmovesWhiteKing);
		ExpectedLegalMovesWhiteRook.addAll(AllExpectedLegalmovesWhiteRook);



		assertTrue(ActualLegalMovesWhiteKing.size() == ExpectedLegalMovesWhiteKing.size()
				&& ExpectedLegalMovesWhiteKing.containsAll(ActualLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteKing.containsAll(ExpectedLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteRook.size() == ExpectedLegalMovesWhiteRook.size()
				&& ExpectedLegalMovesWhiteRook.containsAll(ActualLegalMovesWhiteRook)
				&& ActualLegalMovesWhiteRook.containsAll(ExpectedLegalMovesWhiteRook));

	}

	// checking for the close castling when king moved before
	@Test
	void closeCastlingBlockedKing() {

		// setting a suitable position for close castling
		cb.getSquare(7, 4).placePiece(whiteKing);
		cb.getSquare(7, 7).placePiece(whiteRookRight);
		cb.getSquare(7, 1).placePiece(whiteRookLeft);
		cb.getSquare(6, 7).placePiece(whitePawn);
		cb.getSquare(6, 6).placePiece(whitePawn);
		cb.getSquare(6, 5).placePiece(whitePawn);
		whiteKing.setNotYetMoved(false);
		whiteRookRight.setNotYetMoved(true);

		ArrayList<Square> ActualLegalMovesWhiteKing = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesWhiteRook = whiteRookRight.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesWhiteKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesWhiteRook = new ArrayList<Square>();

		// adding all the possible movements
		List<Square> AllExpectedLegalmovesWhiteKing = Arrays.asList(board[7][3], board[6][3], board[6][4], board[7][5]);
		List<Square> AllExpectedLegalmovesWhiteRook = Arrays.asList(board[7][5], board[7][6]);

		ExpectedLegalMovesWhiteKing.addAll(AllExpectedLegalmovesWhiteKing);
		ExpectedLegalMovesWhiteRook.addAll(AllExpectedLegalmovesWhiteRook);

		assertTrue(ActualLegalMovesWhiteKing.size() == ExpectedLegalMovesWhiteKing.size()
				&& ExpectedLegalMovesWhiteKing.containsAll(ActualLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteKing.containsAll(ExpectedLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteRook.size() == ExpectedLegalMovesWhiteRook.size()
				&& ExpectedLegalMovesWhiteRook.containsAll(ActualLegalMovesWhiteRook)
				&& ActualLegalMovesWhiteRook.containsAll(ExpectedLegalMovesWhiteRook));

	}

	// checking all movement far close castling when king already moves
	// is not obstacle
	@Test
	void farcastlingBlockedKing() {

		// setting a suitable position for far castling
		cb.getSquare(7, 4).placePiece(whiteKing);
		cb.getSquare(7, 6).placePiece(whiteRookRight);
		cb.getSquare(7, 0).placePiece(whiteRookLeft);
		cb.getSquare(6, 0).placePiece(whitePawn);
		cb.getSquare(6, 1).placePiece(whitePawn);
		cb.getSquare(6, 2).placePiece(whitePawn);
		whiteKing.setNotYetMoved(false);
		whiteRookLeft.setNotYetMoved(true);

		ArrayList<Square> ActualLegalMovesWhiteKing = whiteKing.getLegalMoves(cb);
		ArrayList<Square> ActualLegalMovesWhiteRook = whiteRookLeft.getLegalMoves(cb);

		ArrayList<Square> ExpectedLegalMovesWhiteKing = new ArrayList<Square>();
		ArrayList<Square> ExpectedLegalMovesWhiteRook = new ArrayList<Square>();

//		 adding all the possible movements
		List<Square> AllExpectedLegalmovesWhiteKing = Arrays.asList(board[7][5], board[6][5], board[6][4], board[7][3],
				board[6][3]);
		List<Square> AllExpectedLegalmovesWhiteRook = Arrays.asList(board[7][1], board[7][2], board[7][3]);
		ExpectedLegalMovesWhiteKing.addAll(AllExpectedLegalmovesWhiteKing);
		ExpectedLegalMovesWhiteRook.addAll(AllExpectedLegalmovesWhiteRook);

		assertTrue(ActualLegalMovesWhiteKing.size() == ExpectedLegalMovesWhiteKing.size()
				&& ExpectedLegalMovesWhiteKing.containsAll(ActualLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteKing.containsAll(ExpectedLegalMovesWhiteKing)
				&& ActualLegalMovesWhiteRook.size() == ExpectedLegalMovesWhiteRook.size()
				&& ExpectedLegalMovesWhiteRook.containsAll(ActualLegalMovesWhiteRook)
				&& ActualLegalMovesWhiteRook.containsAll(ExpectedLegalMovesWhiteRook));

	}

}