package controller;

import view.*;

import javax.swing.*;


public class Game{

    private boolean gameOver=false;
    ChessBoard cb;
    public boolean newTurn=true;
    private Dice dice;
    private Player playing = null;
    private WhitePlayer wPlayer;
    private BlackPlayer bPlayer;
    private Piece heldPiece = null;
    private JFrame f;

    public Game(JFrame f){
        this.f=f;
        cb  = new ChessBoard();
        dice = new Dice();
        bPlayer = new BlackPlayer(cb);
        wPlayer = new WhitePlayer(cb);
    }
    public JFrame getFrame(){
        return f;
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

        updateBoard();
    }

    public WhitePlayer getWhitePlayer(){return wPlayer;}
    public BlackPlayer getBlackPlayer(){return bPlayer;}


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
            playing = bPlayer;
            newTurn= false;
            String chosen = dice.getRoleDice();
            if(!bPlayer.canMove(chosen)){
                System.out.println("Sorry black , you have no possible moves. Turn goes to white");
                newTurn();
            }
        }
    }


    public boolean isLegalChoice(boolean clickedOnce, SquareButton clickedButton){
        Square clickedSquare = cb.getSquare(clickedButton); //get clicked square
        Piece clickedPiece = clickedSquare.getPieceOnSq();

        if(wPlayer.getIsMyTurn()){ //if its the white turn
            if(!clickedOnce){ //and this click is the choice of which piece to move
                if(!clickedSquare.isTakenSquare()){ return false; }    //if the square is empty then do nothing
                else if (clickedPiece.isWhite() && clickedPiece.getPieceName().equals(dice.getRoleDice()) ){ //if the player selected the correct piece to move
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
                    return true;
                }
                else if(heldPiece!=null && heldPiece.getLegalMoves(cb).contains(clickedSquare)){ //if the clicked square is in fact a legal one to move to
                    heldPiece.move(clickedSquare, cb, heldPiece.getLegalMoves(cb)); //move there
                    System.out.println("legal second click");
                    heldPiece.setHighlighted(false);
                    heldPiece = null;
                    newTurn();
                    return true;
                }
            }
        }

        else if(bPlayer.getIsMyTurn()){ //if its the black turn
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
                    return true;
                }
                else if(heldPiece!=null && heldPiece.getLegalMoves(cb).contains(clickedSquare)){ //if the clicked square is in fact a legal one to move to
                    heldPiece.move(clickedSquare, cb, heldPiece.getLegalMoves(cb)); //move there
                    heldPiece.setHighlighted(false);
                    heldPiece = null;
                    newTurn();
                    System.out.println("legal second click");
                    return true;
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

    public void highlightPiece(Piece piece, Square square) {
       square.removeImage();

        updateBoard();
        square.placeImage(piece);
    }
}