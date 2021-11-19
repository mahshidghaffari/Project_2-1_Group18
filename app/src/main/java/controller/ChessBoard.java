package controller;
import java.util.ArrayList;

import view.SquareButton;

public class ChessBoard{

    private Square [][] board;
    private ArrayList<Piece> livePieces;
    private ArrayList<Piece> deadPieces;
    private Square[] lastPlyPlayed = new Square[2];


    /**
     * Constructor ChessBoard() : constructs a chessboard and fills it with the standard piece set
     */
    public ChessBoard(){
        board =  new Square[8][8];        
        for(int row=0; row<8; row++){
            for(int col=0; col<8; col++){
               board[row][col] = new Square(row, col); 
            }
        }
        livePieces= new ArrayList<Piece>();
        deadPieces = new ArrayList<Piece>();
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
        deadPieces= new ArrayList<Piece>();
    }

    public void copyCB(ChessBoard original){
        //ChessBoard newCB = new ChessBoard(false);
        ArrayList<Piece> newLiveP = getLivePieces();
        Square[][] ogBoard = original.getBoard();
        Square[][] newBoard = getBoard();
        for(int row=0; row<8; row++){
            for(int col=0; col<8; col++){
                if(ogBoard[row][col].isTakenSquare()){
                    Piece taken = ogBoard[row][col].getPieceOnSq();
                    if(taken.pieceName.equals("Pawn")){
                        Pawn pC= new Pawn(taken.isWhite());
                        pC.copyPiece(taken);
                        newLiveP.add(pC);
                        newBoard[row][col].placePiece(pC);
                    }else if (taken.pieceName.equals("Rook")){
                        Rook rC = new Rook(taken.isWhite());
                        rC.copyPiece(taken);
                        newLiveP.add(rC);
                        newBoard[row][col].placePiece(rC);
                    }else if (taken.pieceName.equals("Bishop")){
                        Bishop bC = new Bishop(taken.isWhite());
                        bC.copyPiece(taken);
                        newLiveP.add(bC);
                        newBoard[row][col].placePiece(bC);
                    }else if (taken.pieceName.equals("Knight")){
                        Knight kC = new Knight(taken.isWhite());
                        kC.copyPiece(taken);
                        newLiveP.add(kC);
                        newBoard[row][col].placePiece(kC);
                    }else if (taken.pieceName.equals("Queen")){
                        Queen qC = new Queen(taken.isWhite());
                        qC.copyPiece(taken);
                        newLiveP.add(qC);
                        newBoard[row][col].placePiece(qC);
                    }else if (taken.pieceName.equals("King")){
                        King kC = new King(taken.isWhite());
                        kC.copyPiece(taken);
                        newLiveP.add(kC);
                        newBoard[row][col].placePiece(kC);
                    }
                }
            }
        }
    }

    public Square[][] getBoard(){
        return board;
    }
    public void setBoard(Square[][] board){
        this.board = board;
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
    public void addLivePiece(Piece p){
        livePieces.add(p);
    }
    public ArrayList<Piece> getDeadPieces(){
        return deadPieces;
    }
    /**
     * Method getOccupingPiece : 
     * @param y : y coordinate of square
     * @param x : x coordinate of square
     * @return : piece that occupies Square[y][x] of chessboard
     */
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

    /**
     * Method getSquare : 
     * @param y : y coordinate of square
     * @param x : x coordinate of square
     * @return : the square located at board[y][x]
     */
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
    /**
     * Method makeBoardCopy : copies the current board
     * @return 2d array of square (=board) copying current board
     */
    public Square[][] makeBoardCopy(){
        Square [][] board_copy = new Square[8][8];
        board_copy = board;
        return board_copy; 
    }
    /**
     * method setLastPlyPlayed : used for En Passant
     * @param lastMove array of length 2 of squares, describing the last move played :
     * first element is original position square and second element is destination square
     */
    public void setLastPlyPlayed(Square[] lastMove){
        lastPlyPlayed[0] = lastMove[0];
        lastPlyPlayed[1] = lastMove[1];
    }
    /**
     * method getLastPlyPlayed : used for En Passant
     * @return Square array of size 2 describing the last move played
     */
    public Square[] getLastPlyPlayed(){
        return this.lastPlyPlayed;
    }
    /**
     * Method setNewChessBoard : delete all pieces and create new set of  std pieces
     */
    public void setNewChessBoard(){
        this.deletePieces();
        this.setUpBoard();
    }
    /**
     * Method deletePieces : deletes all pieces of the board
     */
    public void deletePieces(){
        for(int i = 0; i<=7; i++){
            for(int j = 0; j<= 7; j++){
                Piece p = board[i][j].getPieceOnSq();
                board[i][j].removePiece(p);
            }
        }
    }

    public double getBoardValue(){
        Evaluation eval = new Evaluation(this);
        return eval.getScore();
    }
    public double getBoardValueOld(){
        double sum = 0.0;
        for(Piece p: livePieces){
            sum += p.getValue();
        }
        return sum;
    }

    public boolean missingKing(){
        int sum = 0;
        for(Piece p: livePieces){
            if(p.pieceName.equals("King")) { sum++;} 
        } 
        if(sum!=2){
            return true;
        }
        return false;
    }

}