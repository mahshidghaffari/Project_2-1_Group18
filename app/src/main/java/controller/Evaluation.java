package controller;

import java.util.ArrayList;

public class Evaluation {
    private Square[][] board;
    private ArrayList<Piece> livePieces;

    private double pawnWeight;
    private double knightWeight;
    private double bishopWeight;
    private double rookWeight;
    private double queenWeight;
    private double kingWeight;
    private double score;
    private double[][] kingThreatWeight = { //Arbitrary weights of squares around the king,
        //Value of particular square is mutliplied by the number of threats made by player
        // {2.0,2.0,2.0},
        // {2.0,4.0,2.0},
        // {2.0,2.0,2.0}
        {2.0,2.0,2.0, 2.0, 2.0},
        {2.0,5.0,5.0, 5.0, 2.0},
        {2.0,5.0,15.0, 5.0, 2.0},
        {2.0,5.0,5.0, 5.0, 2.0},
        {2.0,4.0,2.0, 2.0, 2.0},
        

    };

    private double[][] squareEval = { //The arbitrary values I gave to all squares in a chess board.
        //Value of particular square is multiplied by the number of threats made by player
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0}, 
        {-1.0,-4.0,-4.0,-4.0,-4.0,-4.0,-4.0,-1.0},
        {-1.0,-4.0,-6.0,-6.0,-6.0,-6.0,-4.0,-1.0},
        {-1.0,-4.0,-6.0,-8.0,-8.0,-6.0,-4.0,-1.0},
        {-1.0,-4.0,-6.0,-8.0,-8.0,-6.0,-4.0,-1.0},
        {-1.0,-4.0,-6.0,-6.0,-6.0,-6.0,-4.0,-1.0},
        {-1.0,-4.0,-4.0,-4.0,-4.0,-4.0,-4.0,-1.0},
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0}, 

        // {-10,-10,-10,-10,-10,-10,-10,-10}, 
        // {-10,-20,-20,-20,-20,-20,-20,-10},
        // {-10,-20,-30,-30,-30,-30,-20,-10},
        // {-10,-20,-30,-40,-40,-30,-20,-10},
        // {-10,-20,-30,-40,-40,-30,-20,-10},
        // {-10,-20,-30,-30,-30,-30,-20,-10},
        // {-10,-20,-20,-20,-20,-20,-20,-10},
        // {-10,-10,-10,-10,-10,-10,-10,-10},
    };
    /**
     * Constructor Evaluation : when constructed, everything is computed, and you can access the score with Evaluation.getScore()
     * @param cb the chessboard to evaluate
     */
    public Evaluation(ChessBoard cb) {
        this.board = cb.getBoard();
        this.livePieces = cb.getLivePieces();
        pawnWeight = 10.0;
        knightWeight = 30.0;
        bishopWeight = 30.0;
        rookWeight = 50.0;
        queenWeight = 90.0;
        kingWeight = 10000.0;
        score = this.getCenterControlEval() + this.getKingSafetyEval() + this.getMaterialEval();
    }

    public double getScore(){
        return this.score;
    }
    /*
    public double getMobilityEval(){
        //Get a score that measures mobility
    }

    public double getKingTropism(){
        //Get a score that measures king tropism (number of moves for pieces to reach the king)
        //i.e. the king might be safe but queen can threaten it in 1 move
    }
    */
    /**
     * Method getMaterialEval : not neessary for now but for later, since we'll probably have to tune piece weights
     * @return score of difference in material
     */
    public double getMaterialEval(){
        int whiteKings = 0, blackKings = 0;
        int whiteQueens = 0, blackQueens = 0;
        int whiteKnights = 0, blackKnights = 0;
        int whiteBishops = 0, blackBishops = 0;
        int whiteRooks = 0, blackRooks = 0;
        int whitePawns = 0, blackPawns = 0;

        for(Piece p : livePieces){
            if(p.isWhite()){
                if(p.getPieceName().equals("Pawn")) whitePawns++;
                if(p.getPieceName().equals("Knight")) whiteKnights++;
                if(p.getPieceName().equals("Bishop")) whiteBishops++;
                if(p.getPieceName().equals("Rook")) whiteRooks++;
                if(p.getPieceName().equals("Queen")) whiteQueens++;
                if(p.getPieceName().equals("King")) whiteKings++;

            } else{
                if(p.getPieceName().equals("Pawn")) blackPawns++;
                if(p.getPieceName().equals("Knight")) blackKnights++;
                if(p.getPieceName().equals("Bishop")) blackBishops++;
                if(p.getPieceName().equals("Rook")) blackRooks++;
                if(p.getPieceName().equals("Queen")) blackQueens++;
                if(p.getPieceName().equals("King")) blackKings++;
            }
        }
        return (pawnWeight * (whitePawns - blackPawns)) + (knightWeight * (whiteKnights - blackKnights)) + (bishopWeight * (whiteBishops-blackBishops)) + (rookWeight * (whiteRooks - blackRooks)) + (queenWeight * (whiteQueens - blackQueens)) + (kingWeight * (whiteKings - blackKings)); 
    }
    /**
     * Method getCenterControlEval : assesses difference in center control scores
     * @return a double indicating the center control evaluation
     */
    public double getCenterControlEval(){
        double whiteEval = 0.0;
        double blackEval = 0.0;
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                whiteEval += squareEval[i][j] * this.getNumThreats(board[i][j], false);
                blackEval += squareEval[i][j] * this.getNumThreats(board[i][j], true);
            }
        }

        return blackEval - whiteEval;
    }
    /**
     * Method getKingSafetyEval : returns a double indicating the difference in evaluation of king safety
     * between white and black
     * @return double king safety evaluation
     */
    public double getKingSafetyEval(){
        double whiteEval = 0.0;
        double blackEval = 0.0;
        int wX=0, wY=0;
        int bX=0, bY=0;

        
        for(Piece p : livePieces){
            if(p.getPieceName() == "King"){
                if(p.isWhite()){
                    wX = p.getCurrentPosition().getXPos();
                    wY = p.getCurrentPosition().getYPos();
                } else {
                    bX = p.getCurrentPosition().getXPos();
                    bY = p.getCurrentPosition().getYPos();
                }
                
            }
        }
        
        double[][] whiteGrid = this.getKingThreatsGrid(board[wY][wX]);
        double[][] blackGrid = this.getKingThreatsGrid(board[bY][bX]);
        
        for(int i =0; i<3; i++){
            for(int j=0; j<3; j++){
                whiteEval += whiteGrid[i][j] * kingThreatWeight[i][j];
                blackEval += blackGrid[i][j] * kingThreatWeight[i][j];
            }
        }
        return blackEval - whiteEval;
    }
    /**
     * Method getKingThreatsGrid : private method used to give a threat grid around the king
     * @param kingPos
     * @return a 3x3 double array representing the number of threats on each sqaure around the king.
     * The center of the array ([1][1]) is where the king is
     */
    private double[][] getKingThreatsGrid(Square kingPos){
        //System.out.println("King pos : " + kingPos.getXPos() + " " + kingPos.getYPos());
        boolean isWhite = kingPos.getPieceOnSq().isWhite();
        double[][] grid = new double [5][5];
        int X = kingPos.getXPos();
        int Y = kingPos.getYPos();
        if(Y+1 < 8){
            if(X-1 >= 0){
                grid[0][0]= (double) this.getNumThreats(board[Y+1][X-1], isWhite);

            }
            grid[0][1]= (double) this.getNumThreats(board[Y+1][X], isWhite);
            if(X+1 < 8){grid[0][2]= (double) this.getNumThreats(board[Y+1][X+1], isWhite);}
            if(Y+2 < 8){}
        }
        

        if(X-1 >= 0){grid[1][0]= (double) this.getNumThreats(board[Y][X-1], isWhite);}
        grid[1][1]= (double) this.getNumThreats(board[Y][X], isWhite);
        if(X+1 < 8){grid[1][2]= (double) this.getNumThreats(board[Y][X+1], isWhite);}

        if(Y-1 > 8){
            if(X-1 >= 0){grid[2][0]= (double) this.getNumThreats(board[Y-1][X-1], isWhite);}
            grid[2][1]= (double) this.getNumThreats(board[Y-1][X], isWhite);
            if(X+1 < 8){grid[2][2]= (double) this.getNumThreats(board[Y-1][X+1], isWhite);}
        }
        
        return grid;
    }
    /**
     * Method getNumThreats :
     * @param pos square at which we evaluate threats
     * @param isWhite boolean indicating what player tries to access threat grid
     * @return an int representing the number of attacks on the square
     */

    public int getNumThreats(Square pos, boolean isWhite){
        int numThreats = 0;
        ArrayList<Piece> enemyPieces = new ArrayList<Piece>();
        for(Piece pie : livePieces){
            if(!(pie.isWhite() == isWhite)){
                enemyPieces.add(pie);
            }
        }
        for(Piece p : enemyPieces){

            if(p.getPieceName() == "Pawn"){
                if(isWhite){
                    if(p.getCurrentPosition().getXPos() == pos.getXPos()-1){
                        if(p.getCurrentPosition().getYPos() == pos.getYPos()-1){
                            numThreats++;
                        }
                    }
                    if(p.getCurrentPosition().getXPos() == pos.getXPos()+1){
                        if(p.getCurrentPosition().getYPos() == pos.getYPos()-1){
                            numThreats++;
                        }
                    }
                } else{
                    if(p.getCurrentPosition().getXPos() == pos.getXPos()-1){
                        if(p.getCurrentPosition().getYPos() == pos.getYPos()+1){
                            numThreats++;
                        }
                    }
                    if(p.getCurrentPosition().getXPos() == pos.getXPos()+1){
                        if(p.getCurrentPosition().getYPos() == pos.getYPos()+1){
                            numThreats++;
                        }
                    }
                }
                
            }
            if(p.getPieceName() == "Knight"){
                int a = Math.abs(p.getCurrentPosition().getYPos() - pos.getYPos());
                int b = Math.abs(p.getCurrentPosition().getXPos() - pos.getXPos());
                
                if(a * b == 2) numThreats++;
            }

            if(p.getPieceName() == "Bishop"){
                int X = p.getCurrentPosition().getXPos();
                int Y = p.getCurrentPosition().getYPos();
                while(true) {
                    Y--;
                    X++;
                    if(Y < 0 || X > 7) {
                        break;
                    }
                      //p is up right diagonal
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    }  
                }
                
                // check right up diagonal (Y ++, X ++) -> check if any is 0
                X = p.getCurrentPosition().getXPos();
                Y = p.getCurrentPosition().getYPos();
        
                while(true) {
                    Y++;
                    X++;
                    if(Y > 7 || X > 7) {
                        break;
                    }
                    //down right diagonal
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                
                // check left down diagonal (Y --, X --) -> check if any is 0
                X = p.getCurrentPosition().getXPos();
                Y = p.getCurrentPosition().getYPos();
                 
                while(true) {
                    Y--;
                    X--;
                    if(Y < 0 || X < 0) {
                        break;
                    }
                    //up left diagonal
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                
                // check right down diagonal (Y--, X ++) -> check if any is 0
                X = p.getCurrentPosition().getXPos();
                Y = p.getCurrentPosition().getYPos();
                        
                while(true) {
                    Y++;
                    X--;
                    if(Y > 	7 || X < 0) {
                        break;
                    }
                    //down left diagonal
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
            }
            if(p.getPieceName() == "Rook"){
                int X = p.getCurrentPosition().getXPos();
                int Y = p.getCurrentPosition().getYPos();
                while(true){
                    X++;
                    if(X > 7){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                X = p.getCurrentPosition().getXPos();
                while(true){
                    X--;
                    if(X < 0){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                X = p.getCurrentPosition().getXPos();
                while(true){
                    Y++;
                    if(Y > 7){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                Y = p.getCurrentPosition().getYPos();
                while(true){
                    Y--;
                    if(Y < 0){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }

            }
            if(p.getPieceName() == "Queen"){
                //Diagonal
                int X = p.getCurrentPosition().getXPos();
                int Y = p.getCurrentPosition().getYPos();
                while(true) {
                    Y--;
                    X++;
                    if(Y < 0 || X > 7) {
                        break;
                    }
                    //p is up right diagonal

                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                
                // check right up diagonal (Y ++, X ++) -> check if any is 0
                X = p.getCurrentPosition().getXPos();
                Y = p.getCurrentPosition().getYPos();
        
                while(true) {
                    Y++;
                    X++;
                    if(Y > 7 || X > 7) {
                        break;
                    }
                    //down right diagonal
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                
                // check left down diagonal (Y --, X --) -> check if any is 0
                X = p.getCurrentPosition().getXPos();
                Y = p.getCurrentPosition().getYPos();
                 
                while(true) {
                    Y--;
                    X--;
                    if(Y < 0 || X < 0) {
                        break;
                    }
                    //up left diagonal
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                
                // check right down diagonal (Y--, X ++) -> check if any is 0
                X = p.getCurrentPosition().getXPos();
                Y = p.getCurrentPosition().getYPos();
                        
                while(true) {
                    Y++;
                    X--;
                    if(Y > 	7 || X < 0) {
                        break;
                    }
                    //down left diagonal
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                
                //Horizontal
                X = p.getCurrentPosition().getXPos();
                Y = p.getCurrentPosition().getYPos();
                while(true){
                    X++;
                    if(X > 7){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                X = p.getCurrentPosition().getXPos();
                while(true){
                    X--;
                    if(X < 0){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                X = p.getCurrentPosition().getXPos();
                while(true){
                    Y++;
                    if(Y > 7){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }
                Y = p.getCurrentPosition().getYPos();
                while(true){
                    Y--;
                    if(Y < 0){ break; }
                    if(X == pos.getXPos() && Y == pos.getYPos()){
                        numThreats++;
                    }
                    
                    if(board[Y][X].isTakenSquare()) {
                        
                        break;
                    } 
                }

            }
            if(p.getPieceName() == "King"){
                int X = p.getCurrentPosition().getXPos();
                int Y = p.getCurrentPosition().getYPos();
                if(pos.getYPos() == Y+1 && pos.getXPos() == X-1){numThreats++;}
                if(pos.getYPos() == Y+1 && pos.getXPos() == X){numThreats++;}
                if(pos.getYPos() == Y+1 && pos.getXPos() == X+1){numThreats++;}

                if(pos.getYPos() == Y && pos.getXPos() == X-1){numThreats++;}
                if(pos.getYPos() == Y && pos.getXPos() == X+1){numThreats++;}

                if(pos.getYPos() == Y-1 && pos.getXPos() == X-1){numThreats++;}
                if(pos.getYPos() == Y-1 && pos.getXPos() == X){numThreats++;}
                if(pos.getYPos() == Y-1 && pos.getXPos() == X+1){numThreats++;}
                
            }
        }
        return numThreats;
    }
    /**
     * Method printThreatGrid : for testing purpose
     * Print a 8x8 grid that represents the number of threats posed on each square
     * @param isWhite boolean indicatin whether to look for black pieces treats or white pieces threats
     */
    public void printThreatGrid(boolean isWhite){
        System.out.println("Threat grid : ");
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                System.out.print(this.getNumThreats(board[y][x], isWhite)+ "  ");
            }
            System.out.println("  ");
        }
    }


}

