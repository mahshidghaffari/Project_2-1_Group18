package controller;
import java.util.ArrayList;
import java.util.Random;

public class BaseLineAgent extends Player {

/**
 * This object represents the BaseLine agent. 
 * This can be represented as a player in the game class and play against variuos opponents.
 * By changing the clever boolean to true, one can expect to play against a greedy semi-random player.
 * If the clever boolean is false then it the agent will be completly random, making it a less challenging rival 
 */

	private Game game; 
	private boolean isWhite;
	String chosenPiece;
	private boolean clever = true; //if not clever it will play completly random moves
	
	public BaseLineAgent(Game game, ChessBoard cb, boolean isWhite) {
		super(cb);
		this.game = game;
		this.isWhite = isWhite;
	}
	
    public boolean baseLinePlay(String chosenPiece) {
    	//System.out.println("AI is playing ");

        Random rand = new Random();

    	//System.out.println(chosenPiece);
    	
    	// go for select the piece with randomization action
    	ArrayList<Piece> movablePieces = this.getMovablePieces(chosenPiece, isWhite);
    	
     	if(movablePieces.size() == 0) {
     		//System.out.println("there is no movable piece");
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
     					//System.out.println(legalMove.getPieceOnSq().pieceName);
     					captureMoves.add(legalMove);
						pieceToCapture.add(piece);
     				}
     				legalMoves.add(legalMove);
     				pieceToMove.add(piece);
     			}
     		}

			if(!clever){
				int randomMove = rand.nextInt(legalMoves.size());
				int randPiece = rand.nextInt(movablePieces.size());
					 pieceToMove.get(randPiece).move(legalMoves.get(randomMove), cb, legalMoves);
			}
			else{

				// System.out.println("Move:" + pieceToMove.toString());
				// System.out.println("Capture:" + pieceToCapture.toString());
				
				if(captureMoves.size()!=0) {
					int randomMoveCapture = rand.nextInt(captureMoves.size());
					double maxPoint = 0;
					for(int i =0; i< captureMoves.size();i++) {
						if (captureMoves.get(i).getPieceOnSq().getValue() > maxPoint) {
							maxPoint = captureMoves.get(i).getPieceOnSq().getValue();
							randomMoveCapture = i;
						}
					}
					//System.out.println(maxPoint);
					pieceToCapture.get(randomMoveCapture).move(captureMoves.get(randomMoveCapture), cb, captureMoves);
					
				}else {

					int randomMove = rand.nextInt(legalMoves.size());
					pieceToMove.get(randomMove).move(legalMoves.get(randomMove), cb, legalMoves);
				}
			}
    		game.updateBoard();
     		game.newTurn();
     		return true; 
     	}
     	return true;
    }


	public boolean getClever(){ return clever; }
	public void setClever(boolean simple){ this.clever = simple;}
}	

