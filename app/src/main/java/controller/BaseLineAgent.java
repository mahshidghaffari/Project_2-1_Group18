package controller;

import java.util.ArrayList;
import java.util.Random;

public class BaseLineAgent extends BlackPlayer {
	private Game game; 
	String chosenPiece;
	
	public BaseLineAgent(Game game, ChessBoard cb) {
		super(cb);
		this.game = game;
	}
	
    public boolean baseLineplay(String chosenPiece) {
    	System.out.println("AI is playing ");
//    	diceClicked=true;
//        this.setDiceClicked(true);
//        this.getDice().randomize();
//        String piceName =  piceName;
        Random rand = new Random();
//        Random rand1 = new Random();
        

    	System.out.println(chosenPiece);
    	
    	// go for select the piece with randomization action
    	 ArrayList<Piece> movablePieces = this.getMovablePieces(chosenPiece);
    	
     	if(movablePieces.size() == 0) {
     		System.out.println("there is no movable piece");
     		game.newTurn();
     	}else {
     		ArrayList<Square> legalMoves = new ArrayList<Square>();
     		ArrayList<Square> captureMoves = new ArrayList<Square>();
     		ArrayList<Piece> pieceToMove = new ArrayList<Piece>();
     		ArrayList<Piece> pieceToCapture = new ArrayList<Piece>();
     		
     		for (Piece piece : movablePieces) {
     			ArrayList<Square> legalMovesForPiece = piece.getLegalMoves(cb);
     			for(Square legalMove : legalMovesForPiece) {
     				if(legalMove.isTakenSquare()){
     					System.out.println(legalMove.getPieceOnSq().pieceName);
     					captureMoves.add(legalMove);
     					pieceToCapture.add(piece);
     				}
     				legalMoves.add(legalMove);
     				pieceToMove.add(piece);
     			}
     		}  
     		
    		if(captureMoves.size()!=0) {
    			int randomMoveCapture = rand.nextInt(captureMoves.size());
    			double maxPoint = 0;
    			for(int i =0; i< captureMoves.size();i++) {
    				if (captureMoves.get(i).getPieceOnSq().getValue() > maxPoint) {
    					maxPoint = captureMoves.get(i).getPieceOnSq().getValue();
    					randomMoveCapture = i;
    				}
    			}
    			System.out.println(maxPoint);
    			pieceToMove.get(randomMoveCapture).move(captureMoves.get(randomMoveCapture), cb, legalMoves);
    			
    		}else {

         		int randomMove = rand.nextInt(legalMoves.size());
         		pieceToMove.get(randomMove).move(legalMoves.get(randomMove), cb, legalMoves);
    		}
    		game.updateBoard();
     		game.newTurn();
     		return true; 
     	}
     	return true;
    }
}
