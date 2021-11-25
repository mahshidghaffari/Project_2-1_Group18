package controller;

import view.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game{

    private ChessBoard cb;
    public boolean newTurn=true;
    private Dice dice;
    private Player playing = null;
    private WhitePlayer wPlayer;
    private BaseLineAgent baseLinePlayer;
    private ExpectiMaxAgent expectiMaxPlayer;
    private BlackPlayer bPlayer;
    private Piece heldPiece = null;
    private ButtonPanel buttonPanel;
    private boolean diceClicked=false;
    private JFrame f;
    private boolean noMoves = false;
    // Booleans to select which agent is going to play against which other agent
    private boolean wNoAgent = false;
    private boolean bNoAgent = false;
    private boolean wBaseLineActive  = false;
    private boolean bBaseLineActive = false;
    private boolean wExpectiMaxActive = false;
    private boolean bEpectiMaxActive = false;
    private boolean noAgent = false;
    private boolean baseLineActive = false;     // Make true to play with BaseLine Agent
    private boolean expectiMaxActive = true;
    private int depth=-1;                       //-1 means no depth (PvP)
    private DicePanel dp;

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
        buttonPanel= new ButtonPanel(this);
        this.noAgent=noAgent;
        this.depth=depth;
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
        if(bEpectiMaxActive && bPlayer.getIsMyTurn() ){   // for black AI agent
            whichPiece();  
            play();
        }
        if(wBaseLineActive && wPlayer.getIsMyTurn() ){
            whichPiece();  
            play();    
        }

        updateBoard();
    }
    public DicePanel getDicePanel(){
        return dp;
    }
    public void setDicePanel(DicePanel dp){
        this.dp = dp;
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

    public void setbNoAgent(boolean b) { this.bNoAgent = b; }
    public void setwNoAgent(boolean b) { this.wNoAgent = b; }
    public void setbBaseLineActive(boolean b) { this.bBaseLineActive = b; }
    public void setwBaseLineActive(boolean b) { this.wBaseLineActive = b; }
    public void setbEpectiMaxActive(boolean b) { this.bEpectiMaxActive = b; }
    public void setwExpectiMaxActive(boolean b) { this.wExpectiMaxActive = b; }

    public void setDepth(int d) { this.depth = d; }

    /**
     * When Ever the Green Dice button is clicked by the user this method checks whether it is even a turn for the user
     */
    public void play(){
        //noMoves = false;

        if(wPlayer.getIsMyTurn()){        //if its w player's turn

            // Normal player (NO AGENT)
            if (wNoAgent) {
                playing = wPlayer;
                newTurn = false;
                String chosen = dice.getChosen();
                //String chosen = dice.getRoleDice(); //roll the dice

                if (!wPlayer.canMove(chosen)) {         //if player has no pieces to move we switch turns
                    //System.out.println("Sorry white , you have no possible moves. Turn goes to black");
                    noMoves = true;
                    newTurn();
                } else {
                    noMoves = false;
                }

            // BASELINE AGENT
            } else if (wBaseLineActive) {
                baseLinePlayer = new BaseLineAgent(this, cb);
                String chosen = dice.getChosen();
                baseLinePlayer.baseLinePlay(chosen);

            // EXPECTIMAX AGENT
            } else if (wExpectiMaxActive) {
                String chosen = dice.getChosen();
                int depth = 3;
                expectiMaxPlayer = new ExpectiMaxAgent(this, cb, chosen, depth, false);
                expectiMaxPlayer.expectiMaxPlay();
            }
        }

        else if(bPlayer.getIsMyTurn()){

        	// Normal player (NO AGENT)
        	if (bNoAgent) {
        		playing = bPlayer;
        		newTurn= false;
            	String chosen = dice.getChosen();
            	//loop through all live pieces to see if dice chosen piece piece is there
            	for(Piece p: cb.getLivePieces()){
            		if(p.isWhite()){ continue; }

            		if(p.getPieceName().equals(chosen) ){ //if the chosen piece is not dead
                        break;
            		}
            	}

            	if(!bPlayer.canMove(chosen)){
            		//System.out.println("Sorry black , you have no possible moves. Turn goes to white");
            		noMoves = true;
            		newTurn();
            	} else {
                    noMoves = false;
                }

        	// BASELINE AGENT
        	} else if (bBaseLineActive) {
                baseLinePlayer = new BaseLineAgent(this, cb);
                String chosen = dice.getChosen();
            	baseLinePlayer.baseLinePlay(chosen);

        	// EXPECTIMAX AGENT
        	} else if (bEpectiMaxActive) {
                String chosen = dice.getChosen();
                int depth = 3;
                expectiMaxPlayer = new ExpectiMaxAgent(this, cb, chosen, depth, false);
                expectiMaxPlayer.expectiMaxPlay();
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
                else if (clickedPiece.isWhite() && clickedPiece.getPieceName().equals(dice.getChosen()) ){ //if the player selected the correct piece to move
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
                else if (!clickedPiece.isWhite() && clickedPiece.getPieceName().equals(dice.getChosen())){ //if the player selected the correct piece to move
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
                SquareButton button = board[i][j].getButtonOnSquare();
                    if(button!=null && button.getButtonColor().equals(Color.DARK_GRAY)) {
                        button.setBackground(Color.DARK_GRAY);
                    }
                    else if(button!=null && button.getButtonColor().equals(Color.WHITE)) {
                        button.setBackground(Color.WHITE);
                    }
                if(board[i][j].isTakenSquare()){
                    Piece occupying = board[i][j].getPieceOnSq();
                    button.setIcon(occupying.getImgIcon());
                }
                else{
                    button.setIcon(new ImageIcon());
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

    public void whichPiece(){
        String name="";
        boolean whiteTurn = getWhitePlayer().getIsMyTurn();
        if (whiteTurn) {
            ArrayList<String> movable = getWhitePlayer().getMovableNames();
            name = getDice().getRndPiece(movable);

            if (isNoMoves()) {
                dp.getTextLabel().setText("NO MOVES AVAILABLE, BLACK'S TURN!");
            } else {
                dp.getTextLabel().setText("WHITE PLAYER'S TURN");
            }

        } else {
            ArrayList<String> movable = getBlackPlayer().getMovableNames();
            name = getDice().getRndPiece(movable);    
            if (isNoMoves()) {
                dp.getTextLabel().setText("NO MOVES AVAILABLE, WHITE'S TURN!");
            } else {
                dp.getTextLabel().setText("BLACK PLAYER'S TURN");
            }
        }
        //game.newTurn();
        setDiceClicked(true);
      
            switch (name){
                case "Pawn":
                    if(whiteTurn){
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("wpawn.png"))));
                        break;
                    }
                    else{
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("bpawn.png"))));
                        break;
                    }

                case "Rook":
                    if(whiteTurn){
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("wrook.png"))));
                        break;
                    }
                else{
                    dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("brook.png"))));
                    break;
                }

                case "Knight":
                    if(whiteTurn){
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("wknight.png"))));
                        break;
                    }
                else{
                    dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("bknight.png"))));
                    break;
                }

                case "Bishop":
                    if(whiteTurn){
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("wbishop.png"))));
                        break;
                    }
                    else{
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("bbishop.png"))));
                        break;
                        }

                case "Queen":
                if(whiteTurn){
                    dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("wqueen.png"))));
                    break;
                }
                else{
                    dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("bqueen.png"))));
                    break;
                }

                case "King":
                    if(whiteTurn){
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("wking.png"))));
                        break;
                    }
                    else{
                        dp.getResultLabel().setIcon(new ImageIcon(ImageLoader.loadImage(dp.getFilePath().getFilePath("bking.png"))));
                        break;
                    }
                }
    
    }

    public boolean isNoMoves() { return noMoves; }
}
