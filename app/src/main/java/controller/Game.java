package controller;

import view.*;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game{

    private ChessBoard cb;
    public boolean newTurn=true;
    private Dice dice;
    private Player playing = null;
    private WhitePlayer wPlayer;
    private BaseLineAgent baseLinePlayer;
    private BlackPlayer bPlayer;
    private Piece heldPiece = null;
    private ButtonPanel buttonPanel;
    private boolean diceClicked=false;
    private JFrame f;
    private boolean baseLineActive = false;


    /**
     * Main Game Class, takes care of all buttons clicked by the listener and Gameplay situations
     * @param f is the ChessBoard GUI Frame 
     */
    
    public Game(JFrame f){
        this.f = f;
        cb = new ChessBoard();
        dice = new Dice();
        bPlayer = new BlackPlayer(cb);
        wPlayer = new WhitePlayer(cb);
        baseLinePlayer = new BaseLineAgent(this, cb);
        buttonPanel= new ButtonPanel(this);
    }
    public JFrame getFrame(){
        return f;
    }
    
    public ChessBoard getChessBoard(){
        return cb;
    }
    public void setNewChessBoard(){
        this.cb = new ChessBoard();
        
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
        newTurn=true;
        diceClicked=false;
        wPlayer.flipTurns(bPlayer);
        updateBoard();
    } 
    public void setButtonPanel(ButtonPanel buttonPanel){
        this.buttonPanel = buttonPanel;
    }
    public void setDiceClicked(boolean b) {
        diceClicked = b;
    }
    //public Castle getCastling(){ return castling;}
    public WhitePlayer getWhitePlayer(){return wPlayer;}
    public BlackPlayer getBlackPlayer(){return bPlayer;}

    /**
     * When Ever the Green Dice button is clicked by the user this method checks whether it is even a turn for the user
     */
    public void play(){

        if(wPlayer.getIsMyTurn()){        //if its w player's turn
            playing = wPlayer;
            newTurn= false;
            String chosen = dice.getRoleDice(); //roll the dice
            if(!wPlayer.canMove(chosen)){         //if player has no pieces to move we switch turns
                System.out.println("Sorry white , you have no possible moves. Turn goes to black");

                newTurn();
            }
        }   
        else if(bPlayer.getIsMyTurn()){
        	
        	// Normal player (NO AGENT)
        	if (!baseLineActive) {
        		playing = bPlayer;
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
            	}
        	
        	// BASELINE AGENT
        	} else if (baseLineActive) {
            	String chosen = dice.getRoleDice();
            	baseLinePlayer.baseLineplay(chosen);
        	
        	// EXPECTIMAX AGENT
        	} else if (false) {
        		
        	}
            	
        }
    }

    /**
     * This Method checks which button clicks are legal by the user and their correspondence to the ual 
     * In game play. 
     * @param clickedOnce a boolean that checks whether this click by the user is the first one or second
     * @param clickedButton is the button that was clicked by the user
     * @return true if the user is allowed to make these choices
     */

    public boolean isLegalChoice(boolean clickedOnce, SquareButton clickedButton){
        Square clickedSquare = cb.getSquare(clickedButton); //get clicked square
        Piece clickedPiece = clickedSquare.getPieceOnSq();
        
        if(wPlayer.getIsMyTurn()){ //if its the white turn
            if(!diceClicked){ return false;}
            if(!clickedOnce){ //and this click is the choice of which piece to move 
                if(!clickedSquare.isTakenSquare()){ return false; }    //if the sqaure is empty then do nothing
                else if (clickedPiece.isWhite() && clickedPiece.getPieceName().equals(dice.getRoleDice()) ){ //if the player selected the correct piece to move
                    heldPiece = clickedPiece;
                    heldPiece.setHighlighted(true);
                    highlightPiece(heldPiece, clickedSquare);
                    System.out.println("legal first click");
                    buttonPanel.setText("legal first click");

                    return true;
                }
            }
            else{ //if this is the second click
                
                if(heldPiece!=null && cb.getSquare(clickedButton).equals(heldPiece.getCurrentPosition())){   //if the player wants to move another piece
                    heldPiece.setHighlighted(false);
                    heldPiece=null;
                    clickedOnce = false;
                    System.out.println("released");
                    return true;
                }
                else if(heldPiece!=null && heldPiece.getLegalMoves(cb).contains(clickedSquare)){ //if the clicked square is in fact a legal one to move to
                   
                    int distance = Math.abs(clickedSquare.getXPos()-heldPiece.getCurrentPosition().getXPos());    //calculating the distance from the piece to desired moving location                
                    
                    if(heldPiece.getPieceName().equals("King") && distance>1){                  //if the size of this movement is larger than 1 for the king it means this is castling
                        ((King)heldPiece).doCastling(clickedSquare, cb, heldPiece);
                        newTurn();
                        return true;
                    }
                    if(heldPiece.getPieceName().equals("Pawn") && clickedSquare.getYPos()==0){
                        heldPiece.move(clickedSquare,cb, heldPiece.getLegalMoves(cb));
                        Piece newPiece = clickedSquare.getPieceOnSq();
                        ((Pawn)newPiece).promote(heldPiece.isWhite(),newPiece, clickedSquare, cb);
                        newTurn();
                        return true;
                    }

                    else{  //if its just a non castling move
                        heldPiece.move(clickedSquare, cb, heldPiece.getLegalMoves(cb)); //move there    
                        System.out.println("legal second click");
                        heldPiece.setHighlighted(false);
                        heldPiece = null;
                        newTurn();
                        return true;
                    }                    
                }
            }
        }

        else if(bPlayer.getIsMyTurn()){ //if its the black turn
            if(!diceClicked){ return false;}
            if(!clickedOnce){ //and this click is the choice of which piece to move 
                if(!clickedSquare.isTakenSquare()){ return false; }    //if the sqaure is empty then do nothing
                else if (!clickedPiece.isWhite() && clickedPiece.getPieceName().equals(dice.getRoleDice()) ){ //if the player selected the correct piece to move
                    heldPiece = clickedPiece;
                    heldPiece.setHighlighted(true);
                    highlightPiece(heldPiece, clickedSquare);
                    System.out.println("legal first click");
                    return true;
                }
            }
            else{ //if this is the second click
                if(heldPiece!=null && cb.getSquare(clickedButton).equals(heldPiece.getCurrentPosition())){   //if the player wants to move another piece
                    heldPiece.setHighlighted(false);
                    heldPiece=null;
                    clickedOnce = false;
                    System.out.println("released");
                    updateBoard();
                    return true;
                }
                else if(heldPiece!=null && heldPiece.getLegalMoves(cb).contains(clickedSquare)){ //if the clicked square is in fact a legal one to move to
                    
                    int distance = Math.abs(clickedSquare.getXPos()-heldPiece.getCurrentPosition().getXPos());    //calculating the distance from the piece to desired moving location                
                    
                    if(heldPiece.getPieceName().equals("King") && distance>1){                  //if the size of this movement is larger than 1 for the king it means this is castling
                        ((King)heldPiece).doCastling(clickedSquare,cb,heldPiece);
                        newTurn();
                        return true;
                    }
                    if(heldPiece.getPieceName().equals("Pawn") && clickedSquare.getYPos()==7){
                        heldPiece.move(clickedSquare,cb, heldPiece.getLegalMoves(cb));
                        Piece newPiece = clickedSquare.getPieceOnSq();
                        ((Pawn)newPiece).promote(heldPiece.isWhite(),newPiece, clickedSquare, cb);
                        newTurn();
                        return true;
                    }

                    else{  //if its just a non castling move
                        heldPiece.move(clickedSquare, cb, heldPiece.getLegalMoves(cb)); //move there
                        heldPiece.setHighlighted(false);
                        heldPiece = null;    
                        newTurn();
                        System.out.println("legal second click");
                        return true;                    
                    }
                }
            }    
        }
        return false;
    }

    /**
     * This repaints the board after any movement or capture
     */
    public void updateBoard(){
        Square[][] board = cb.getBoard();
        wPlayer.updateScore();  //accounting for any captures
        bPlayer.updateScore();
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
    /**
     * This method highlights a legal clicked piece
     * @param piece is the piece in question needing highlighting
     * @param square is the Square in question to highlight
     */
    public void highlightPiece(Piece piece, Square square) {
       square.removeImage();

        updateBoard();
        square.placeImage(piece);
    }
}
