package controller;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import view.ImageLoader;
import java.awt.event.ActionListener;
import java.awt.event.*;


public class Pawn extends Piece{


    public Pawn(boolean white){
        super.setWhite(white);
        super.pieceName = "Pawn";
        if(isWhite()) super.setValue(10);
		else super.setValue(-10);    
    }

    /*
    method legalMovesCalc
    @param cb is the current state of the chessboard
    @return ArrayList<Square> an array of squares describing the legal squares this piece can move to
     */
    public ArrayList<Square> getLegalMoves(ChessBoard cb){
        ArrayList<Square> legalMoves = new ArrayList<>();
        int x = this.getCurrentPosition().getXPos();
        int y = this.getCurrentPosition().getYPos();
        Square[][] board = cb.getBoard();
        if(this.isWhite()){
            if(y-1 >= 0){
                if(!board[y-1][x].isTakenSquare()){ // Move 1 square forward for white
                    legalMoves.add(board[y-1][x]);  
                }
                if(y == 6){
                    if(!board[y-1][x].isTakenSquare() && !board[y-2][x].isTakenSquare()){ // Move 2 squares forward for white
                        legalMoves.add(board[y-2][x]);
                    }
                }
                
                if(x+1 <= 7){ //Capture to the right for white
                    if(board[y-1][x+1].isTakenSquare()){
                        if(!this.sameTeam(board[y-1][x+1].getPieceOnSq())){
                            legalMoves.add(board[y-1][x+1]);
                        }
                    }
                }
                if(x-1 >= 0){ //Capture to the left for white
                    if(board[y-1][x-1].isTakenSquare()){
                        if(!this.sameTeam(board[y-1][x-1].getPieceOnSq())){
                            legalMoves.add(board[y-1][x-1]);
                        }
                    }
                }
                if(y==3){   //En Passant for white : only works if the pawn is on y = 3
                    if(x+1 < 8){ //En Passant to the right for white
                        Square rSq = board[y][x+1];
                        if(rSq.isTakenSquare()){ //If the piece is a pawn and it is black

                            if(rSq.getPieceOnSq().pieceName == "Pawn" && !rSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0] != null) {
                                    if (lastMove[0].getYPos() == 1 && lastMove[1].getYPos() == 3) {
                                        legalMoves.add(board[y - 1][x + 1]);
                                        //System.out.println("to the right was added!!");
                                    }
                                }
                            }
                        }
                    }
                    if(x-1 >= 0){ //En passant to the left for white
                        Square lSq = board[y][x-1];
                        if(lSq.isTakenSquare()){ //If the piece is a pawn and it is black
                            if(lSq.getPieceOnSq().pieceName == "Pawn" && !lSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0] != null) {
                                    if (lastMove[0].getYPos() == 1 && lastMove[1].getYPos() == 3) {
                                        legalMoves.add(board[y - 1][x - 1]);
                                    }
                                }
                                
                            }
                        } 
                    }
                }
            }
            
            
        } else{
            if(y+1 <= 7){
                if(!board[y+1][x].isTakenSquare()){ // Move 1 square forward for black
                    legalMoves.add(board[y+1][x]);
                }
                if(y == 1){ 
                    if(!board[y+1][x].isTakenSquare() &&!board[y+2][x].isTakenSquare()){ // Move 2 squares forward for black
                        legalMoves.add(board[y+2][x]);
                    }
                }
                if(x+1 <= 7){ //Capture to the right
                    if(board[y+1][x+1].isTakenSquare() && !this.sameTeam(board[y+1][x+1].getPieceOnSq())){
                        legalMoves.add(board[y+1][x+1]);
                    }
                }
                if(x-1 >= 0){ //Capture to the left
                    if(board[y+1][x-1].isTakenSquare() && !this.sameTeam(board[y+1][x-1].getPieceOnSq())){
                        legalMoves.add(board[y+1][x-1]);
                    }
                }
                if(y==4){   //En Passant for black : only works if the pawn is on y = 4
                    if(x+1 <= 7){ //En Passant to the right for black
                        Square rSq = board[y][x+1];
                        if(rSq.isTakenSquare()){ //If the piece is a pawn and it is white
                
                            if(rSq.getPieceOnSq().pieceName == "Pawn" && rSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0] != null) {
                                    if (lastMove[0].getYPos() == 6 && lastMove[1].getYPos() == 4) {
                                        legalMoves.add(board[y + 1][x + 1]);
                                    }
                                }
                            }
                        }
                    }
                    if(x-1 >= 0){ //En passant to the left for black
                        Square lSq = board[y][x-1];
                        if(lSq.isTakenSquare()){ //If the piece is a pawn and it is white
                            if(lSq.getPieceOnSq().pieceName == "Pawn" && lSq.getPieceOnSq().isWhite()){
                                Square[] lastMove = cb.getLastPlyPlayed();
                                if(lastMove[0] != null) {
                                    if (lastMove[0].getYPos() == 6 && lastMove[1].getYPos() == 4) {
                                        legalMoves.add(board[y + 1][x - 1]);
                                    }
                                }
                            }
                        } 
                    }
                }
            }
        }
        return legalMoves;
    }
    /*
        method move
        @param target : the target square the player wishes to move to
        @param cb : the current chessboard
        @param legalMoves : list of legal moves for the current piece
        @return void
        this override does the same as the original "move" method, but handles En Passant in addition
    */
    @Override
    public void move(Square target, ChessBoard cb, ArrayList<Square> legalMoves){
        Square[][] board = cb.getBoard();
        Square currentPos = this.getCurrentPosition();
        //Create and fill array to store last move played in the game
        Square[] moveDescription = new Square[2];
        moveDescription[0] = currentPos;
        moveDescription[1] = target;
        cb.setLastPlyPlayed(moveDescription);

        currentPos.removePiece(this);
        if(target.getPieceOnSq()!=null){//if there is an opposing piece on target square a.k.a Capture
            Piece captured = target.getPieceOnSq();
            //System.out.println("The " + captured.getColorName()+ " " + captured.pieceName + " was captured by the " +getColorName()+ " " + pieceName);
            cb.getLivePieces().remove(captured);    // mark this as a fallen piece
            if(captured.pieceName.equals("King")){
                //System.out.println("The "+ captured.getColorName() + " King has fallen");
                //System.out.println(this.getColorName() + " Wins!!!");
                //JOptionPane.showMessageDialog(null, this.getColorName()+ " Wins!!! ", "InfoBox: " + "END GAME", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } else {
            if(target.getXPos() != this.getCurrentPosition().getXPos()){ //if the target and the current pos have the different x
                //Pawn is going to go diagonally, but since target is null, it is doing en passant
                //Have to remove piece captured en passant
                Square squareToEmpty = board[this.getCurrentPosition().getYPos()][target.getXPos()];
                cb.getLivePieces().remove(squareToEmpty.getPieceOnSq());
                squareToEmpty.removePiece(squareToEmpty.getPieceOnSq());
            }
        }
        
        target.placePiece(this);
        checkingKing(legalMoves);
    }


    /**
     * This method takes care of all promotions which occur when a pawn reaches the last row.
     * 
     * @param isWhite boolean to know if the piece is black or white
     * @param pawn the pawn reaching the last row to which a promotion is needed
     * @param target the last sqaure that the pawn is moving towards
     * @param cb the chessboard
     * When the pawn reaches the last row then a dice is rolled. If the dice rolled a 5, then the Piece to promote to
     * between Knight,Bishop,Rook and Queen.
     * If the dice rolls 1 the pawn will become a knight,if 2 then bishop if 3 then rook and if 4 then queen 
     */    
    public void promote(boolean isWhite, Piece pawn, Square target, ChessBoard cb){
        Dice promoteDice = new Dice();
        promoteDice.randomize();
        Random rnd = new Random();
        int roll = rnd.nextInt(5)+1;
        target.removePiece(pawn);

        if(roll==1){
            Knight newKnight = new Knight(isWhite);
            if(isWhite)    newKnight.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wknight.png")));
            else           newKnight.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bknight.png")));

            newKnight.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rknight.png")));
            cb.getLivePieces().add(newKnight);
            target.placePiece(newKnight);
        }
        else if(roll==2){
            Bishop newBishop = new Bishop(isWhite);
            if(isWhite)    newBishop.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wbishop.png")));
            else           newBishop.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bbishop.png")));

            newBishop.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rbishop.png")));
            cb.getLivePieces().add(newBishop);
            target.placePiece(newBishop);
        }
        else if(roll==3){
            Rook newRook = new Rook(isWhite);
            if(isWhite)    newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wrook.png")));
            else           newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/brook.png")));

            newRook.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rrook.png")));
            cb.getLivePieces().add(newRook);
            target.placePiece(newRook);
        
        }
        else if(roll==4){
            Queen newQueen = new Queen(isWhite);
            if(isWhite)    newQueen.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wqueen.png")));
            else           newQueen.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/bqueen.png")));

            newQueen.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rqueen.png")));
            cb.getLivePieces().add(newQueen);
            target.placePiece(newQueen);
        }
        else if(roll==5){
            JFrame frame = new JFrame("Promotion");
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

                    newBishop.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rbishop.png")));
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

                    newKnight.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rknight.png")));
                    cb.getLivePieces().add(newKnight);
                    target.placePiece(newKnight);
                }  
            });
            
            rookButton.addActionListener(new ActionListener(){  
                public void actionPerformed(ActionEvent e){  
                    Rook newRook = new Rook(isWhite);
                    if(isWhite)    newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/wrook.png")));
                    else           newRook.setImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/brook.png")));

                    newRook.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rrook.png")));
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

                    newQueen.setHighlightedImgIcon(new ImageIcon(ImageLoader.loadImage("app/src/main/java/view/resources/rqueen.png")));
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
