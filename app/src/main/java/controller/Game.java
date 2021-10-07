package controller;

import view.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.event.*;



public class Game{

    private boolean gameOver=false;
    private ChessBoard cb;
    public boolean newTurn=true;
    private Dice dice;
    private Player playing = null;
    private WhitePlayer wPlayer;
    private BlackPlayer bPlayer;
    private Piece heldPiece = null;
    private ButtonPanel buttonPanel;
    private boolean diceClicked=false;
    private JFrame f;


    
    
    public Game(JFrame f){
        this.f = f;
        cb = new ChessBoard();
        //cb  = new ChessBoard(false);
        //cb.getLivePieces().remove(cb.getBoard()[7][6].getPieceOnSq());
        
        
        //Knight queen   = new Knight(true);
         //cb.getLivePieces().add(queen);
         //cb.getBoard()[4][3].placePiece(queen);
        // // King Wking = new King(true); 
        //  Rook Wrook = new Rook(false);
        //  cb.getLivePieces().add(Wrook);
        //  cb.getBoard()[1][6].placePiece(Wrook);

         // Rook Wrook1 = new Rook(true);
        // cb.getBoard()[7][4].placePiece(Wking);
        // cb.getBoard()[7][0].placePiece(Wrook);
        // cb.getBoard()[7][7].placePiece(Wrook1);
        // cb.getLivePieces().add(Wking);
        // cb.getLivePieces().add(Wrook);
        // cb.getLivePieces().add(Wrook1);
        //  cb.getBoard()[7][5].removePiece(cb.getOccupingPiece(7, 5));
        //  cb.getBoard()[7][6].removePiece(cb.getOccupingPiece(7, 6));
        //  cb.getBoard()[7][1].removePiece(cb.getOccupingPiece(7, 1));
        //  cb.getBoard()[7][2].removePiece(cb.getOccupingPiece(7, 2));
        //  cb.getBoard()[7][3].removePiece(cb.getOccupingPiece(7, 3));
        //  cb.getBoard()[6][2].removePiece(cb.getOccupingPiece(6, 2));
        //  cb.getBoard()[6][2].placePiece(cb.getBoard()[0][3].getPieceOnSq());
         //  cb.getBoard()[0][1].removePiece(cb.getOccupingPiece(0, 1));
        //  cb.getBoard()[0][2].removePiece(cb.getOccupingPiece(0, 2));
        //  cb.getBoard()[0][5].removePiece(cb.getOccupingPiece(0, 5));
        //  cb.getBoard()[0][6].removePiece(cb.getOccupingPiece(0, 6));
        //  cb.getBoard()[0][3].removePiece(cb.getOccupingPiece(0, 3));
        dice = new Dice();
        bPlayer = new BlackPlayer(cb);
        wPlayer = new WhitePlayer(cb);
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
        }
    }


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
                        doCastling(clickedSquare);
                        newTurn();
                        return true;
                
                        // heldPiece.move(clickedSquare,cb,heldPiece.getLegalMoves(cb));

                        // if(clickedSquare.getXPos()>4){             //if it is  a close castling
                        //     Piece rook = cb.getBoard()[7][7].getPieceOnSq();
                        //     rook.move(cb.getBoard()[7][5], cb, rook.getLegalMoves(cb));
                        // }

                        // else {                                      //if it is a far castling
                        //     Piece rook = cb.getBoard()[7][0].getPieceOnSq();
                        //     rook.move(cb.getBoard()[7][3],cb,rook.getLegalMoves(cb));
                        // }
                        
                    }
                    if(heldPiece.getPieceName().equals("Pawn") && clickedSquare.getYPos()==0){
                        heldPiece.move(clickedSquare,cb, heldPiece.getLegalMoves(cb));
                        Piece newPiece = clickedSquare.getPieceOnSq();
                        promote(heldPiece.isWhite(),newPiece, clickedSquare, cb);
                        newTurn();
                        // doPromotion(newPiece);
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
                        heldPiece.setHighlighted(false);
                        heldPiece = null;    
                        newTurn();
                        System.out.println("legal second click");
                        return true;                    
                    }

                    // heldPiece.move(clickedSquare, cb, heldPiece.getLegalMoves(cb)); //move there
                    // heldPiece.setHighlighted(false);
                    // heldPiece = null;
                    // newTurn();
                    // System.out.println("legal second click");
                    // return true;

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

    public void doCastling(Square clickedSquare ){
        heldPiece.move(clickedSquare,cb,heldPiece.getLegalMoves(cb));
        if(clickedSquare.getXPos()>4){             //if it is  a close castling
            Piece rook = cb.getBoard()[7][7].getPieceOnSq();
            rook.move(cb.getBoard()[7][5], cb, rook.getLegalMoves(cb));
        }
        else {                                      //if it is a far castling
            Piece rook = cb.getBoard()[7][0].getPieceOnSq();
            rook.move(cb.getBoard()[7][3],cb,rook.getLegalMoves(cb));
        }
    }

    public void doPromotion(Piece piece){
        String name = piece.getPieceName();
        switch(name){
            case("Rook"):
                if(piece.isWhite())   piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wrook.png"))); 
                else                  piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/brook.png"))); 
                break;
            case("Knight"):
                if(piece.isWhite())   piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wknight.png")));
                else                  piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bknight.png"))); 
                break;
            case("Bishop"):
                if(piece.isWhite())   piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wbishop.png"))); 
                else                  piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bbishop.png"))); 
                break;   
            case("Queen"):
                if(piece.isWhite())   piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wqueen.png"))); 
                else                  piece.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bqueen.png"))); 
                break;        
        }
    }

    public void promote(boolean isWhite, Piece pawn, Square target, ChessBoard cb){
        Dice promoteDice = new Dice();
        promoteDice.randomize();
        Random rnd = new Random();
        int roll = rnd.nextInt(5)+1;
        target.removePiece(pawn);
        roll=5;

        if(roll==1){
            Knight newKnight = new Knight(isWhite);
            if(isWhite)    newKnight.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wknight.png")));
            else           newKnight.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bknight.png")));
            cb.getLivePieces().add(newKnight);
            target.placePiece(newKnight);
        }
        else if(roll==2){
            Bishop newBishop = new Bishop(isWhite);
            if(isWhite)    newBishop.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wbishop.png")));
            else           newBishop.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bbishop.png")));
            cb.getLivePieces().add(newBishop);
            target.placePiece(newBishop);
        }
        else if(roll==3){
            Rook newRook = new Rook(isWhite);
            if(isWhite)    newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wrook.png")));
            else           newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/brook.png")));
            cb.getLivePieces().add(newRook);
            target.placePiece(newRook);
        
        }
        else if(roll==4){
            Queen newQueen = new Queen(isWhite);
            if(isWhite)    newQueen.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wqueen.png")));
            else           newQueen.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rqueen.png")));
            cb.getLivePieces().add(newQueen);
            target.placePiece(newQueen);
        }
        else if(roll==5){
            JFrame frame = new JFrame("Promotion");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            JPanel pPanel = new JPanel();
            JButton bishopButton = new JButton("Bishop");
            JButton knightButton = new JButton("Knight");
            JButton rookButton = new JButton("Rook");
            JButton queenButton = new JButton("Queen");

            bishopButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    Bishop newBishop = new Bishop(isWhite);
                    if(isWhite)    newBishop.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wbishop.png")));
                    else           newBishop.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bbishop.png")));
                    cb.getLivePieces().add(newBishop);
                    target.placePiece(newBishop);
                    frame.dispose();
                    cb.printBoard();
                }  
            });  
            knightButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    Knight newKnight = new Knight(isWhite);
                    if(isWhite)    newKnight.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wknight.png")));
                    else           newKnight.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bknight.png")));
                    cb.getLivePieces().add(newKnight);
                    target.placePiece(newKnight);
                }  
            });
            
            rookButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    Rook newRook = new Rook(isWhite);
                    if(isWhite)    newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wrook.png")));
                    else           newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/brook.png")));
                    cb.getLivePieces().add(newRook);
                    target.placePiece(newRook); 
                    frame.dispose();
                }  
            });
            queenButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){ 
                    Queen newQueen = new Queen(isWhite);
                    if(isWhite)    newQueen.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wqueen.png")));
                    else           newQueen.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rqueen.png")));
                    cb.getLivePieces().add(newQueen);
                    target.placePiece(newQueen);
                    frame.dispose();
                }  
            });
            pPanel.add(bishopButton);
            pPanel.add(knightButton);
            pPanel.add(rookButton);
            pPanel.add(queenButton);
            frame.add(pPanel);
            frame.pack();
            frame.setVisible(true);
        }
    }
}
