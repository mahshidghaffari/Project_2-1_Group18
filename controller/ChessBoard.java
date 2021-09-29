package controller;
import java.util.ArrayList;

import view.SquareButton;

public class ChessBoard{

    private Square [][] board;
    private ArrayList<Piece> livePieces;

    public ChessBoard(){
        board =  new Square[8][8];        
        for(int row=0; row<8; row++){
            for(int col=0; col<8; col++){
               board[row][col] = new Square(row, col); 
            }
        }
        livePieces= new ArrayList<Piece>();
       setUpBoard();      
    }

    //constructor for practice testing board
    public ChessBoard(boolean test){
        board = new Square[8][8];
        for(int row=0; row<8; row++){
            for(int col=0; col<8; col++){
               board[row][col] = new Square(row, col); 
            }
        }
        livePieces= new ArrayList<Piece>();
    }

    public Square[][] getBoard(){
        return board;
    }

    public void setUpBoard(){
        for(int i=0; i<8; i++ ){ 
            board[6][i].placePiece(new Pawn(true));  //White Pawns     
            board[1][i].placePiece(new Pawn(false)); //Black Pawns
        }
        
        board[7][0].placePiece(new Rook(true));//White rook
        board[7][7].placePiece(new Rook(true));  //White rook
        board[0][0].placePiece(new Rook(false)); //Black rook
        board[0][7].placePiece(new Rook(false)); //Black rook

        board[7][1].placePiece(new Knight(true));  //White Knight
        board[7][6].placePiece(new Knight(true));  //White Knight
        board[0][1].placePiece(new Knight(false)); //Black knight
        board[0][6].placePiece(new Knight(false)); // Black knight

        board[7][2].placePiece(new Bishop(true));  //White Bishop
        board[7][5].placePiece(new Bishop(true));  //White Bishop
        board[0][2].placePiece(new Bishop(false)); //Black Bishop
        board[0][5].placePiece(new Bishop(false)); // Black Bishop

        board[7][3].placePiece(new Queen(true));  //White Queen
        board[7][4].placePiece(new King(true));  //White King
        board[0][4].placePiece(new King(false)); //Black King
        board[0][3].placePiece(new Queen(false)); // Black Queen
        
        board[6][0].placePiece(new Pawn(true)); 
        board[6][1].placePiece(new Pawn(true)); 
        board[6][2].placePiece(new Pawn(true)); 
        board[6][3].placePiece(new Pawn(true)); 
        board[6][4].placePiece(new Pawn(true)); 
        board[6][5].placePiece(new Pawn(true)); 
        board[6][6].placePiece(new Pawn(true)); 
        board[6][7].placePiece(new Pawn(true)); 

        //Adding white pieces then black pieces to livePieces ArrayList
        for(int rW = 6; rW<8;rW++){
            for(int cW=0;cW<8;cW++){
                livePieces.add(board[rW][cW].getPieceOnSq());
            }
        }
        for(int rB = 1; rB>=0;rB--){
            for(int cB=0;cB<8;cB++){
                livePieces.add(board[rB][cB].getPieceOnSq());
            }
        }
    }

    public ArrayList<Piece> getLivePieces(){
        return livePieces;
    } 

       
    public Piece getOccupingPiece(int y, int x){
        
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            try {
                throw new Exception("Not on Board");
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getSquare(y, x).getPieceOnSq();
    }

    public Square getSquare(SquareButton sqb){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j].getButtonOnSquare().equals(sqb)){
                    return board[i][j];
                }
            }
        }
        return null;
    }


    public Square getSquare(int y, int x)
    {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            try {
                throw new Exception("Not on Board");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return board[y][x];
    }


    public void printBoard(){
        
        for(int i = 0; i<board.length ; i++){
            for(int y = 0; y<board.length ; y++){
                if(board[i][y].isTakenSquare()) {
                    System.out.print("  " + board[i][y].getPieceOnSq().pieceName + "  ");
                }
                else { System.out.print("  empty  ");}
            }
            System.out.println(" ");
        }
    }

    public Square[][] makeBoardCopy(){
        Square [][] board_copy = new Square[8][8];
        board_copy = board;
        return board_copy; 
    }
}