package controller;
import java.util.ArrayList;


// package controller;

 import java.util.ArrayList;
 import java.util.Random;

 public class BaseLineAgent {

 	private Piece currentPiece; 
 	private ArrayList<Square> legalMoves = new ArrayList<>();
 	private ChessBoard cb; 
 	private Square target;
 	private Random rand = new Random();
 	private int indexRandom = this.rand.nextInt(currentPiece.getLegalMoves(cb).size());              
	
	
 	public BaseLineAgent(String pieceName,ChessBoard cb) {
		
		
 		this.currentPiece.pieceName = pieceName;
 		this.legalMoves = currentPiece.getLegalMoves(cb);
 		this.cb = cb;
 	}
	
 	public void move() {
 		if (legalMoves.size()==0) {
 			// roll the dice change the turn 
 			System.out.println("I can't do anyting with this fucking dice :)))))");
 		}else {
			
 			this.target = currentPiece.getLegalMoves(cb).get(this.indexRandom);
 			currentPiece.move(target, cb, currentPiece.getLegalMoves(cb)); //move there
 		}
 	}	
	
 }
