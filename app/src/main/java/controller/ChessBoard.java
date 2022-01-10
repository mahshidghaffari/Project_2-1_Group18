package controller;
import java.util.ArrayList;
import view.SquareButton;

public class ChessBoard{

    private Square [][] board;
    private ArrayList<Piece> livePieces;
    private ArrayList<Piece> deadPieces;
    private Square[] lastPlyPlayed = new Square[2];
    public ArrayList<Move> moveHistory = new ArrayList<Move>();
    
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
    /*
    ChessBoard Constructor, takes a fen string as parameter
    A fen string is a string that can fully describe the state of a chessBoard
    In this constructor we build the chessboard according to the string
    */
    public ChessBoard(String fenString){
        board = new Square[8][8];
        for(int row=0; row<8; row++){
            for(int col=0; col<8; col++){
               board[row][col] = new Square(row, col); 
            }
        }
        livePieces= new ArrayList<Piece>();
        deadPieces= new ArrayList<Piece>();
        String[]arr = fenString.split("/| ");
        //Loop through the array of strings : 8 first lines describe the board, 
        //9th line describes the target position of enPassant legal moves
        //10th line describes which type of castle is legal at this point (namely which rook has/hasn't moved yet)
        
        int x; //Create x offset variable
        for(int y=0; y<arr.length; y++){
            if(y<8){ //Loop though all ranks
                x = 0; //Reset offset

                for(int i=0; i<arr[y].length(); i++){ //Loop through length of description

                    if(!Character.isDigit(arr[y].charAt(i))){ //If character isn't a number --> describes position of a piece
                        //Create and add piece, add offset of 1
                        Piece p = this.getNewPiece(arr[y].charAt(i));
                        board[y][x].placePiece(p);                       
                        this.addLivePiece(p);
                        x++;
                    } else{//If character is a number, it indicates the x offset to take for the next piece
                        
                        x += (int) arr[y].charAt(i) - '0';   
                    }
                }
            }
            if(y==8){ //When y==8, we are reading the EP legalities
                if(arr[y].length() !=0){ //if there is a EnPassant square described

                    int col = arr[y].charAt(0) - '0';
                    int row = arr[y].charAt(1) - '0';

                    if(col==2){ //Pawn moved by 2 was white
                        //Add move to move history, that way the getFENString() can output EP legalities
                        Move m = new Move(board[col-1][row], board[col+1][row]);
                        this.moveHistory.add(m);
                    }
                    if(col==5){
                        //Add move to move history, that way the getFENString() can output EP legalities
                        Move m = new Move(board[col+1][row], board[col-1][row]);
                        this.moveHistory.add(m);
                    }
                }
            }
            
            if(y==9){
                //By default, we assume the rooks have moved and cannot castle
                if(board[0][7].isTakenSquare()){board[0][7].getPieceOnSq().setNotYetMoved(false);}
                if(board[0][0].isTakenSquare()){board[0][0].getPieceOnSq().setNotYetMoved(false);}
                if(board[7][7].isTakenSquare()){board[7][7].getPieceOnSq().setNotYetMoved(false);}
                if(board[7][0].isTakenSquare()){board[7][0].getPieceOnSq().setNotYetMoved(false);}
                //Check for castling legalites, set respective rooks to "unmoved"
                for(int i=0; i<arr[y].length();i++){
                    switch(arr[y].charAt(i)){
                        case 'K': board[7][7].getPieceOnSq().setNotYetMoved(true); break;
                        case 'Q': board[7][0].getPieceOnSq().setNotYetMoved(true); break;
                        case 'k': board[0][7].getPieceOnSq().setNotYetMoved(true); break;
                        case 'q': board[0][0].getPieceOnSq().setNotYetMoved(true); break;
                    }
                }
            }     
        }
    }

    /*
    Method playMove : allows us to play a move using a move object, and calling the method directly on the chessboard object
    param myMove : move to play
    */
    public void playMove(Move myMove){
        moveHistory.add(myMove);
        Piece p = myMove.origin.getPieceOnSq();
        ArrayList<Square> legalMovesSquares = p.getLegalMoves(this);
        if(legalMovesSquares.contains(myMove.target)){
            p.move(myMove.target, this , legalMovesSquares);
        }
    }
    /*
    Method unplayMove : allows us to unplay a move using a move object, and calling the method directly on the chessboard object
    param myMove : move that was last played
    */
    public void unplayMove(Move movePlayed){
        if(moveHistory.size() >0){
            moveHistory.remove(moveHistory.size()-1);
        

            Piece p = movePlayed.target.getPieceOnSq();
            movePlayed.target.removePiece(p);
            movePlayed.origin.placePiece(p);
            if(movePlayed.captured != null){
                movePlayed.target.placePiece(movePlayed.captured);
            }
        } else{ 
            System.out.println("Can't unplay Move : ");
            movePlayed.printMove(); 
            System.out.println(", there were no moves made yet");
        }
        
    }
    /*
    Method getLegalMoves : returns arraylist of Move s that are legal in the current chessboard for a given player
    param isWhite : which player's legal moves we are requesting
    */
    public ArrayList<Move> getLegalMoves(boolean isWhite){
        ArrayList<Move> moves = new ArrayList<Move>();

        for(Piece p:livePieces){
            if(p.isWhite() == isWhite){
                for(int i = 0; i<p.getLegalMoves(this).size(); i++){
                    Move move = new Move(p.getCurrentPosition(), p.getLegalMoves(this).get(i));
                    moves.add(move);
                }
            } 
        }
        return moves;
    }
    /*
        Method getLegalMoves : returns arraylist of Move s that are legal in the current chessboard for a given player and a given piece
        param isWhite : player color
        param pieceName : name of piece type we want elgal moves for
    */
    public ArrayList<Move> getLegalMoves(boolean isWhite, String pieceName){
        ArrayList<Move> moves = new ArrayList<Move>();

        for(Piece p:livePieces){
            if(p.isWhite() == isWhite){
                if(p.pieceName.equals(pieceName)){
                    for(int i = 0; i<p.getLegalMoves(this).size(); i++){
                        moves.add(new Move(p.getCurrentPosition(), p.getLegalMoves(this).get(i)));
                    }
                }
            } 
        }
        return moves;
    }
    public void placePiece(Square s, Piece p){
        this.board[s.getYPos()][s.getXPos()].placePiece(p);
        this.livePieces.add(p);
    }
    /*
    Method createCopy : called on a cb, returns a new chessboard object identical to the cb
    */
    public ChessBoard createCopy(){
        //Create new empty board
        ChessBoard newCb = new ChessBoard(true); 
        newCb.moveHistory = this.moveHistory;
        Square[][] newBoard = newCb.getBoard();
        System.out.println("number of live pieces : " + livePieces.size());
        //For each livePiece, copy it and place it on the board
        for(int y=0; y<8;y++){
            for(int x=0; x<8; x++){
                if(this.board[y][x].isTakenSquare()){
                    Piece p = this.board[y][x].getPieceOnSq();
                    boolean isWhite = p.isWhite();
                    boolean checkingKing = p.getCheckingKing();
                    boolean hasMoved = p.getIfNotYetMoved();
                    String pieceName = p.getPieceName();

                    switch(pieceName){
                        case "Pawn":
                            Pawn nP = new Pawn(isWhite);
                            nP.setCurrentPosition(newBoard[y][x]);
                            nP.checkingKing = checkingKing;
                            newBoard[y][x].placePiece(nP); 
                            nP.setNotYetMoved(hasMoved);
                            newCb.addLivePiece(nP);
                            break;
                        case "Knight":
                            Knight nK = new Knight(isWhite);
                            nK.setCurrentPosition(newBoard[y][x]);
                            nK.checkingKing = checkingKing;
                            newBoard[y][x].placePiece(nK); 
                            nK.setNotYetMoved(hasMoved);
                            newCb.addLivePiece(nK);
                            break;
                        case "Bishop":
                            Bishop nB = new Bishop(isWhite);
                            nB.setCurrentPosition(newBoard[y][x]);
                            nB.checkingKing = checkingKing;
                            newBoard[y][x].placePiece(nB); 
                            nB.setNotYetMoved(hasMoved);
                            newCb.addLivePiece(nB);
                            break;
                        case "Rook":
                            Rook nR = new Rook(isWhite);
                            nR.setCurrentPosition(newBoard[y][x]);
                            nR.checkingKing = checkingKing;
                            newBoard[y][x].placePiece(nR); 
                            nR.setNotYetMoved(hasMoved);
                            newCb.addLivePiece(nR);
                            break;
                        case "Queen":
                            Queen nQ = new Queen(isWhite);
                            nQ.setCurrentPosition(newBoard[y][x]);
                            nQ.checkingKing = checkingKing;
                            newBoard[y][x].placePiece(nQ); 
                            nQ.setNotYetMoved(hasMoved);
                            newCb.addLivePiece(nQ);
                            break;
                        case "King":
                            King nKi = new King(isWhite);
                            nKi.setCurrentPosition(newBoard[y][x]);
                            nKi.checkingKing = checkingKing;
                            newBoard[y][x].placePiece(nKi); 
                            nKi.setNotYetMoved(hasMoved);
                            newCb.addLivePiece(nKi);
                            break;
                    }
                }
            }
        }
        //newCb.setBoard(newBoard);
        return newCb;
    }
    
    

    public void copyCB(ChessBoard newChessBoard){
        ChessBoard newCB = new ChessBoard(false);
        ArrayList<Piece> newLiveP = getLivePieces();
        Square[][] ogBoard = this.getBoard();
        Square[][] newBoard = newChessBoard.getBoard();
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
        System.out.println("____________________________________________________________________________");
        for(int i = 0; i<board.length ; i++){
            for(int y = 0; y<board.length ; y++){
                if(board[i][y].isTakenSquare()) {
                    System.out.print("  " + board[i][y].getPieceOnSq().pieceName + "  ");
                }
                else { System.out.print("  -----  ");}
            }
            System.out.println(" ");
        }
        System.out.println("____________________________________________________________________________");
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
        //eval.setPieceWeights();
        return eval.getScore();
    }
    
    public void printEval(){
        Evaluation eval = new Evaluation(this);
        // System.out.println("Center control eval : " + eval.getCenterControlEval());
        // System.out.println("King safety eval : " + eval.getKingSafetyEval());
        // System.out.println("Material eval : " + eval.getMaterialEval());
        eval.printDebug(true);
        eval.printDebug(false);
    }

    public boolean missingKing(){
        int sum = 0;
        for(Piece p: livePieces){
            if(p.pieceName.equals("King"))  sum++;  
        } 
        if(sum!=2){
            return true;
        }
        return false;
    }
    /*
        Method getFENString() : a FEN string is a string that can fully describe the state of a chessboard
        here is the fenstring of the basic chess setup : 
        rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR  KQkq

        Each compartiment between "/" represents a row of the chessboard. UpperCase are white, LowerCase are black
        p = Pawn, n = Knight, b = bishop, q = Queen, k = King

        There can also be coordinates for a square that is legal for enpassant moves : (between origin and target of the past move)
        rnbqkbnr/p1pppppp/8/1p6/8/8/PPPPPPPP/RNBQKBNR 21 KQkq

        Here, the 21 indicates that previous move was pawn move of 2squares, so En Passant can be played on 21 square

        The "KQkq" part indicates which type of castle is still legal :
        K = white King side castle,
        Q = white Queen side castle,
        k = black King side castle,
        q = black Queen side castle
    */

    public String getFENString(){
        
        //Create string that describes board setup
        String fen = "";
        int emptySquareCount = 0;

        for(int y=0; y<8; y++){
            emptySquareCount = 0;
            for(int x=0; x<8; x++){
        
                if(this.getSquare(y, x).isTakenSquare()){

                    if(emptySquareCount > 0){
                        fen += emptySquareCount;
                        emptySquareCount = 0;
                    }
                    String toAdd = "";

                    switch(this.getSquare(y,x).getPieceOnSq().getPieceName()){
                        case "Pawn" : toAdd = "p"; break;
                        case "Knight" : toAdd = "n"; break;
                        case "Bishop" : toAdd = "b"; break;
                        case "Rook" : toAdd = "r"; break;
                        case "Queen" : toAdd = "q"; break;
                        case "King" : toAdd = "k"; break;
                    }
                    if(this.getSquare(y,x).getPieceOnSq().isWhite()){
                        toAdd = toAdd.toUpperCase();
                    }
                    fen += toAdd;
                } else{emptySquareCount++;}
            }
            if(emptySquareCount!=0){
                fen += emptySquareCount;
            }
            if(y !=7){fen += '/';}
        }

        //Create String that describes En Passant squares
        String ep = "";
        if(moveHistory.size() >0){ //If there is a move in moveHistory
            Move lastMove = moveHistory.get(moveHistory.size()-1);
            if(lastMove.pieceName == "Pawn"){ //If last move was with a pawn
                if(Math.abs(lastMove.origin.getYPos() - lastMove.target.getYPos()) == 2){ //If the pawn moved 2 squares
                    if(!lastMove.target.getPieceOnSq().isWhite()){//If pawn is black
                        ep += lastMove.origin.getYPos()+1;
                        ep += lastMove.origin.getXPos();
                    } else { //If pawn is white
                        ep += lastMove.origin.getYPos()-1;
                        ep += lastMove.origin.getXPos();
                    }
                }
            }
        }

        //Create String that describes castling legalities
        String castle = "";
        Piece p;

        if(this.getSquare(7,7).isTakenSquare()){ //If there is a piece on starting square of rook
            p = this.getSquare(7,7).getPieceOnSq();
            if(p.getIfNotYetMoved() && p.pieceName == "Rook"){ //If the piece is a rook and hasn't moved yet
                castle += "K"; //Add letter to string : indicates that white King side castle is legal
            }
        }
        if(this.getSquare(7,0).isTakenSquare()){ //If there is a piece on starting square of rook
            p = this.getSquare(7,0).getPieceOnSq();
            if(p.getIfNotYetMoved() && p.pieceName == "Rook"){ //If the piece is a rook and hasn't moved yet
                castle += "Q"; //Add letter to string : indicates that white Queen side castle is legal
            }
        }
        if(this.getSquare(0,7).isTakenSquare()){ //If there is a piece on starting square of rook
            p = this.getSquare(0,7).getPieceOnSq();
            if(p.getIfNotYetMoved() && p.pieceName == "Rook"){ //If the piece is a rook and hasn't moved yet
                castle += "k"; //Add letter to string : indicates that black King side castle is legal
            }
        }
        if(this.getSquare(0,0).isTakenSquare()){ //If there is a piece on starting square of rook
            p = this.getSquare(0,0).getPieceOnSq();
            if(p.getIfNotYetMoved() && p.pieceName == "Rook"){ //If the piece is a rook and hasn't moved yet
                castle += "q"; //Add letter to string : indicates that black Queen side castle is legal
            }
        }
        

        return fen + " " + ep + " " + castle;
    
    }
    /*
    Method getNewPiece : method used in the FEN string constructor
    Input : a character that indicates a piece type and its colour : Pawn kNight Bishop Rook Queen King (pnbrqk)
            uppercase is white, lowercase is black
    Return : a new Piece, of type and colour matching desired input
    
    */
    public Piece getNewPiece(char c){
        boolean isWhite = Character.isUpperCase(c);
        c = Character.toLowerCase(c);
        Piece p = new Pawn(true);
        switch(c){
            case 'k': p = new King(isWhite);break;
            case 'q': p = new Queen(isWhite);break;
            case 'r': p = new Rook(isWhite);break;
            case 'b': p = new Bishop(isWhite);break;
            case 'n': p = new Knight(isWhite);break;
            case 'p': p = new Pawn(isWhite);break;
        }
        return p;
    }
}