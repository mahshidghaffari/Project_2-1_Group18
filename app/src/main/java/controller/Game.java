package controller;

import view.*;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Game{

    private boolean gameOver=false;
    private ChessBoard cb;
    public boolean newTurn=true;
    private Dice dice;
    private Player playing = null;
    private WhitePlayer wPlayer;
    private BlackPlayer bPlayer;
    private Piece heldPiece;
    private ButtonPanel buttonPanel;
    private boolean showCastleButton= false;
    private JButton castleButton;
    private boolean diceClicked=false;
    private boolean BaseLIneAgentActive = true;
    
    
    public Game(){
        cb  = new ChessBoard();
        dice = new Dice();
        bPlayer = new BlackPlayer(cb);
        wPlayer = new WhitePlayer(cb);

    }
    
//    bugs
//    1- this method is call after white!
//	  2- dice is not changing the turn (i.e black get chance to roll dice twice)
    
    public boolean AIplay(String piceName) {
    	System.out.println("AI is playing ");
//    	diceClicked=true;
//        this.setDiceClicked(true);
//        this.getDice().randomize();
//        String piceName =  piceName;
        Random rand = new Random();
//        Random rand1 = new Random();
        updateBoard();

    	System.out.println(piceName);
    	
    	// go for select the piece with randomization action
    	 ArrayList<Piece> movablePieces = bPlayer.getMovablePieces(piceName);
    	
     	if(movablePieces.size() == 0) {
     		System.out.println("there is no movable piece");
     		newTurn();
     	}else {
     		ArrayList<Square> legalMoves = new ArrayList<Square>();
     		ArrayList<Piece> pieceToMove = new ArrayList<Piece>();
     		
     		for (Piece piece : movablePieces) {
     			ArrayList<Square> legalMovesForPiece = piece.getLegalMoves(cb);
     			//legalMoves.addAll(piece.getLegalMoves(cb));
     			//System.out.println(piece.getLegalMoves(cb));
     			for(Square legalMove : legalMovesForPiece) {
     				legalMoves.add(legalMove);
     				pieceToMove.add(piece);
     			}
     		}     
     		int randomMove = rand.nextInt(legalMoves.size());
     		pieceToMove.get(randomMove).move(legalMoves.get(randomMove), cb, legalMoves);
     		newTurn();
     		return true; 
     	}
     	return true;
    }
    
    public ChessBoard getChessBoard(){
        return cb;
    }
    public boolean isNewTurn(){
        return newTurn;
    }
    public Dice getDice(){
        return dice;
    }
    public Player whosPlaying(){
        return playing;
    }
    public void newTurn(){
        wPlayer.flipTurns(bPlayer);
        newTurn=true;
        diceClicked=false;
        castleButton.setVisible(false);
        updateBoard();
    } 
    public boolean getShowCastleButton(){
        return showCastleButton;
    }
    public void setButtonPanel(ButtonPanel buttonPanel){
        this.buttonPanel = buttonPanel;
        castleButton = buttonPanel.getCastleButton();
    }
    public void setDiceClicked(boolean b) {
        diceClicked = b;
    }
    
    //public Castle getCastling(){ return castling;}
    public WhitePlayer getWhitePlayer(){return wPlayer;}
    public BlackPlayer getBlackPlayer(){return bPlayer;}
    
    
    public void play(){
        
        if(wPlayer.getIsMyTurn()){        //if its w player's turn
            // playing = wPlayer;
            newTurn= false;
            String chosen = dice.getRoleDice(); //roll the dice
            if(!wPlayer.canMove(chosen)){         //if player has no pieces to move we switch turns
                System.out.println("Sorry white , you have no possible moves. Turn goes to black");
                newTurn();
            }
        }   
        else if(bPlayer.getIsMyTurn()){
            // playing = bPlayer;
            newTurn= false;
            String chosen = dice.getRoleDice();
            //loop through all live pieces to see if dice chosen piece piece is there
            for(Piece p: cb.getLivePieces()){  
                if(p.isWhite()){ continue; }   
                
                if(p.getPieceName().equals(chosen) ){ //if the chosen piece is not dead 
                        break;
                }
            }
            if(!bPlayer.canMove(chosen)){    
                System.out.println("Sorry black , you have no possible moves. Turn goes to white");     
                newTurn();
            }else {
            	AIplay(chosen);
            	
            }
        }
    }


    public boolean isLegalChoice(boolean clickedOnce, SquareButton clickedButton){
        Square clickedSquare = cb.getSquare(clickedButton); //get clicked square 
        Piece clickedPiece = clickedSquare.getPieceOnSq();
        if(wPlayer.getIsMyTurn()){ //if its the white turnc
        	System.out.println("0");
            if(!diceClicked){ return false;}
            if(!clickedOnce){ //and this click is the choice of which piece to move 
                if(!clickedSquare.isTakenSquare()){ return false; }    //if the sqaure is empty then do nothing
                else if (clickedPiece.isWhite() && clickedPiece.getPieceName().equals(dice.getRoleDice()) ){ //if the player selected the correct piece to move
                    heldPiece = clickedPiece;   
                    System.out.println("legal first click");
                    return true;
               }
            }
            else{ //if this is the second click
                if(heldPiece!=null && cb.getSquare(clickedButton).equals(heldPiece.getCurrentPosition())){   //if the player wants to move another piece
                    heldPiece=null;
                    clickedOnce = false;
                    System.out.println("released");
                    return true;
                }
                else if(heldPiece!=null && heldPiece.getLegalMoves(cb).contains(clickedSquare)){ //if the clicked square is in fact a legal one to move to
                   
                    int distance = Math.abs(clickedSquare.getXPos()-heldPiece.getCurrentPosition().getXPos());    //calculating the distance from the piece to desired moving location                
                    if(heldPiece.getPieceName().equals("King") && distance>1){                  //if the size of this movement is larger than 1 for the king it means this is castling
                        heldPiece.move(clickedSquare,cb,heldPiece.getLegalMoves(cb));

                        if(clickedSquare.getXPos()>4){             //if it is  a close castling
                            Piece rook = cb.getBoard()[7][7].getPieceOnSq();
                            rook.move(cb.getBoard()[7][5], cb, rook.getLegalMoves(cb));
                        }

                        else {                                      //if it is a far castling
                            Piece rook = cb.getBoard()[7][0].getPieceOnSq();
                            rook.move(cb.getBoard()[7][3],cb,rook.getLegalMoves(cb));
                        }
                        
//                        if (BaseLIneAgentActive) {
//                        	AIplay();
//                        }else {
                        	newTurn();                        	
//                        }
                        
                        return true;
                     
                    }

                    else{  //if its just a non castling move
                        heldPiece.move(clickedSquare, cb, heldPiece.getLegalMoves(cb)); //move there    
                        System.out.println("legal second click");
//                        if (BaseLIneAgentActive) {
//                        	AIplay();
//                        }else {
                        	newTurn();                        	
//                        }
                        return true;
                    }                    
                }
            }
        }

        else if(bPlayer.getIsMyTurn()){ //if its the black turn
        	System.out.println(BaseLIneAgentActive);
        	if (BaseLIneAgentActive) {
        		System.out.println("lalalalal");
//        		AIplay();
        		return true;
        	}else {
        		System.out.println("lilililili");
                if(!diceClicked){ return false;}
                if(!clickedOnce){ //and this click is the choice of which piece to move 
                    if(!clickedSquare.isTakenSquare()){ return false; }    //if the sqaure is empty then do nothing
                    else if (!clickedPiece.isWhite() && clickedPiece.getPieceName().equals(dice.getRoleDice()) ){ //if the player selected the correct piece to move
                        heldPiece = clickedPiece; 
                        System.out.println("legal first click");
                        return true;
                   }
                }
                else{ //if this is the second click
                    if(heldPiece!=null && cb.getSquare(clickedButton).equals(heldPiece.getCurrentPosition())){   //if the player wants to move another piece
                        heldPiece=null;
                        clickedOnce = false;
                        System.out.println("released");
                        return true;
                    }
                    else if(heldPiece!=null && heldPiece.getLegalMoves(cb).contains(clickedSquare)){ //if the clicked square is in fact a legal one to move to
                        
                        int distance = Math.abs(clickedSquare.getXPos()-heldPiece.getCurrentPosition().getXPos());    //calculating the distance from the piece to desired moving location                
                        if(heldPiece.getPieceName().equals("King") && distance>1){                  //if the size of this movement is larger than 1 for the king it means this is castling
                            heldPiece.move(clickedSquare,cb,heldPiece.getLegalMoves(cb));           //move the king to desired location

                            if(clickedSquare.getXPos()>4){             //if it is  a close castling
                                Piece rook = cb.getBoard()[0][7].getPieceOnSq();
                                rook.move(cb.getBoard()[0][5], cb, rook.getLegalMoves(cb));  //move the rook to correct location next to king
                            }

                            else {                                      //if it is a far castling
                                Piece rook = cb.getBoard()[0][0].getPieceOnSq();
                                rook.move(cb.getBoard()[0][3],cb,rook.getLegalMoves(cb));   //move the rook to correct location next to king
                            }
                            newTurn();
                            return true;
                        }

                        else{  //if its just a non castling move
                            heldPiece.move(clickedSquare, cb, heldPiece.getLegalMoves(cb)); //move there
                            newTurn();
                            System.out.println("legal second click");
                            return true;                    
                        }
                    }
                } 
        	}
        }
        return false;
    }


    public void updateBoard(){
        Square[][] board = cb.getBoard(); 
        for(int i=0; i<8; i++){
            for(int j=0;j<8;j++){
                if(board[i][j].isTakenSquare()){
                    Piece occupying = board[i][j].getPieceOnSq();
                    board[i][j].getButtonOnSquare().setIcon(occupying.getImgIcon());
                }
                else{
                    board[i][j].getButtonOnSquare().setIcon(new ImageIcon());
                }
            }
        }
    }
    
}

























// package controller;

// import view.*;
// import java.util.Random;


// public class Game{

//     public static void main(String[] args) {
//         new Game();
//     }
    
//     public Game(){
//         ChessBoard cb  = new ChessBoard();
//         BlackPlayer bPlayer = new BlackPlayer(cb);
//         WhitePlayer wPlayer = new WhitePlayer(cb);
//         Random rnd = new Random();
//         int i=0;
//         wPlayer.setIsMyTurn(true);
//         while(i<20){
//             i++;
//             System.out.println(i);
//             String rollW = wPlayer.rollDice();
//             String rollB = bPlayer.rollDice();
//             if(wPlayer.getIsMyTurn() && wPlayer.canMove(rollW)){
//                     wPlayer.setIsMyTurn(true);
//                     int rndIndexPiece = rnd.nextInt(wPlayer.getMovablePieces(rollW).size()); //randomize which piece to move
//                     Piece p = wPlayer.getMovablePieces(rollW).get(rndIndexPiece);
//                     int rndIndexMove = rnd.nextInt(p.getLegalMoves(cb).size()); //randomize which move to choose from randomly chosen piece
//                     Square move = p.getLegalMoves(cb).get(rndIndexMove);
//                     p.move(move, cb, p.getLegalMoves(cb));
//                     wPlayer.flipTurns(bPlayer);
//                     cb.printBoard();
//                     System.out.println();
        
//             }
//             else if(bPlayer.getIsMyTurn() && bPlayer.canMove(rollB)){
//                     bPlayer.setIsMyTurn(true);
//                     int rndIndexPiece = rnd.nextInt(bPlayer.getMovablePieces(rollB).size());
//                     Piece p = bPlayer.getMovablePieces(rollB).get(rndIndexPiece);
//                     int rndIndexMove = rnd.nextInt(p.getLegalMoves(cb).size());
//                     Square move = p.getLegalMoves(cb).get(rndIndexMove);
//                     p.move(move, cb, p.getLegalMoves(cb));
//                     bPlayer.flipTurns(wPlayer);
//                     cb.printBoard();
//                     System.out.println();
        
//                 }            
//             }
//         }
//     }

