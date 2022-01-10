package controller;
import java.util.ArrayList;

public class Evaluation {
    private ChessBoard cb;
    private Square[][] board;
    private ArrayList<Piece> livePieces;
    private double[][] whiteThreatGrid = new double[8][8];
    private double[][] blackThreatGrid = new double[8][8];
    private double score;
    
    private double pawnWeight;
    private double knightWeight;
    private double bishopWeight;
    private double rookWeight;
    private double queenWeight;
    private final double kingWeight = 10000.0;
    private double pieceOnDiceWeight;

    private boolean kingSafety = false;
    private boolean piecesOnDice = false;
    private boolean centerControl = false;
    public void activateKingSafety(){this.kingSafety = true;}
    public void activateCenterControl(){this.centerControl = true;}
    public void activatePiecesOnDice(){this.piecesOnDice = true;}

    
    
    private double[][] kingDangerWeight = { //Arbitrary weights of squares around the king,
        //Value of particular square is mutliplied by the number of threats made by player
        {2.0,2.0,2.0, 2.0, 2.0},
        {2.0,5.0,5.0, 5.0, 2.0},
        {2.0,5.0,7.0, 5.0, 2.0},
        {2.0,5.0,5.0, 5.0, 2.0},
        {2.0,2.0,2.0, 2.0, 2.0},
    };
    private double[][] kingProtectionWeight = { //Arbitrary weights of squares around the king,
        //Value of particular square is mutliplied by the number of threats made by player
        {2.0,2.0,2.0, 2.0, 2.0},
        {2.0,5.0,5.0, 5.0, 2.0},
        {2.0,5.0,1.0, 5.0, 2.0},
        {2.0,5.0,5.0, 5.0, 2.0},
        {2.0,2.0,2.0, 2.0, 2.0},
    };
    private double[][] squareEval = { //The arbitrary values I gave to all squares in a chess board.
        //Value of particular square is multiplied by the number of threats made by player
        {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}, 
        {0.0,2.0,2.0,2.0,2.0,2.0,2.0,0.0},
        {0.0,2.0,5.0,5.0,5.0,5.0,2.0,0.0},
        {0.0,2.0,5.0,8.0,8.0,5.0,2.0,0.0},
        {0.0,2.0,5.0,8.0,8.0,5.0,2.0,0.0},
        {0.0,2.0,5.0,5.0,5.0,5.0,2.0,0.0},
        {0.0,2.0,2.0,2.0,2.0,2.0,2.0,0.0},
        {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0}, 
    };
    /**
     * Constructor Evaluation : when constructed, everything is computed, and you can access the score with Evaluation.getScore()
     * @param cb the chessboard to evaluate
     */
    public Evaluation(ChessBoard cb) {
        this.setPieceWeights(10.0, 30.0, 30.0, 50.0, 90.0);
        this.setPieceOnDiceWeight(15.0);
        this.cb = cb;
        this.board = cb.getBoard();
        this.livePieces = cb.getLivePieces();

        for(int i =0; i<8; i++){
            for(int j = 0; j<8; j++){
                this.whiteThreatGrid[i][j] = this.getNumThreats(board[i][j],true);
                this.blackThreatGrid[i][j] = this.getNumThreats(board[i][j],false);

            }
        }
        score = this.getMaterialEval();
        if(centerControl){score += this.getCenterControlEval();}
        if(kingSafety){score += this.getKingSafetyEval();}
        if(piecesOnDice){score += this.getPiecesOnDiceEval();}
    }
    public double getMaxEval(){
        //Max score you can get is with all pawns promoted, so 9 queens + other pieces
        double max = 9 * queenWeight + 2*knightWeight + 2*bishopWeight + 2*rookWeight + kingWeight;
        if(centerControl){
            for(int i=0; i<4; i++){
                //Max number of threats you can put on a single square is 9
                max += 9 * squareEval[i][i];
            } 
        }
        if(kingSafety){
            for(int i=0; i<3; i++){
                max += 9*kingDangerWeight[i][i];
                max += 9*kingProtectionWeight[i][i];
            }
        }
        if(piecesOnDice){
            max += 6*pieceOnDiceWeight;
        }
        return max;
    }
    public double getPiecesOnDiceEval(){
        double whiteEval = 0.0;
        double blackEval = 0.0;
        boolean[] whitePieceCheck = {false,false,false,false,false,false};
        boolean[] blackPieceCheck = {false,false,false,false,false,false};

        for(Piece p:livePieces){
            if(p.getLegalMoves(cb).size() != 0){
                if(p.isWhite()){
                    if(p.pieceName == "Pawn"){whitePieceCheck[0] = true;}
                    if(p.pieceName == "Knight"){whitePieceCheck[1] = true;}
                    if(p.pieceName == "Bishop"){whitePieceCheck[2] = true;}
                    if(p.pieceName == "Rook"){whitePieceCheck[3]= true;}
                    if(p.pieceName == "Queen"){whitePieceCheck[4] = true;}
                    if(p.pieceName == "King"){whitePieceCheck[5] = true;}
                } else{
                    if(p.pieceName == "Pawn"){blackPieceCheck[0] = true;}
                    if(p.pieceName == "Knight"){blackPieceCheck[1] = true;}
                    if(p.pieceName == "Bishop"){blackPieceCheck[2] = true;}
                    if(p.pieceName == "Rook"){blackPieceCheck[3]= true;}
                    if(p.pieceName == "Queen"){blackPieceCheck[4] = true;}
                    if(p.pieceName == "King"){blackPieceCheck[5] = true;}
                }
            }
        }
        for(int i=0; i < 6; i++){
            if(whitePieceCheck[i]){whiteEval += pieceOnDiceWeight;}
            if(blackPieceCheck[i]){blackEval += pieceOnDiceWeight;}
        }
        return whiteEval - blackEval;
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
                whiteEval += squareEval[i][j] * whiteThreatGrid[i][j];
                blackEval += squareEval[i][j] * blackThreatGrid[i][j];
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
        int wX=-1, wY=-1;
        int bX=-1, bY=-1;

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
        if(wX >= 0 && wY >=0 && bX >= 0 && bY >= 0){
            //Determine the number of enemy threats around the king (threats)
            double[][] whiteKingDanger = this.getKingThreatsGrid(board[wY][wX], true);
            double[][] blackKingDanger = this.getKingThreatsGrid(board[bY][bX], false);

            //Determine the number of ally threats around the king (protection)
            double[][] whiteProtectionGrid = this.getKingThreatsGrid(board[wY][wX], false);
            double[][] blackProtectionGrid = this.getKingThreatsGrid(board[bY][bX], true);
            
            for(int i =0; i<whiteKingDanger.length; i++){
                for(int j=0; j<whiteKingDanger[0].length; j++){
                    blackEval += whiteKingDanger[i][j] * kingDangerWeight[i][j];
                    whiteEval += whiteProtectionGrid[i][j] * kingProtectionWeight[i][j];
                }
            }
            for(int i =0; i<blackKingDanger.length; i++){
                for(int j=0; j<blackKingDanger[0].length; j++){
                    whiteEval += blackKingDanger[i][j] * kingDangerWeight[i][j];
                    blackEval += blackProtectionGrid[i][j] * kingProtectionWeight[i][j];
                }
            }
        }
        return whiteEval - blackEval;
    }
    /**
     * Method getKingThreatsGrid : private method used to give a threat grid around the king
     * @param kingPos
     * @return a 5x5 double array representing the number of threats on each sqaure around the king.
     * The center of the array is where the king is
     */
    private double[][] getKingThreatsGrid(Square kingPos, boolean isWhite){
        int X = kingPos.getXPos();
        int Y = kingPos.getYPos();
        
        int xLeft = 2;
        int xRight = 2;
        int yLeft = 2;
        int yRight = 2;

        if(X==0){xLeft = 0;}
        if(X==1){xLeft = 1;}
        if(X==6){xRight = 1;}
        if(X==7){xRight = 0;}

        if(Y==0){yLeft = 0;}
        if(Y==1){yLeft = 1;}
        if(Y==6){yRight = 1;}
        if(Y==7){yRight = 0;}
        double[][] threatBoard = this.getThreatBoard(isWhite);

        //Creates an adaptative-sized array
        double[][] grid = new double[1+yLeft+yRight][1+xLeft+xRight];
        for (int i = 0; i< grid.length; i++){
            for(int j = 0; j<grid[0].length; j++){
                grid[i][j] = threatBoard[Y - yLeft + i][X - xLeft + j];
            }
        }
        //fill array with zeros
        double[][] grid5 = new double[5][5];
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                grid5[i][j] = 0;
            }
        }
        //Copy array into right indices of a 5x5 array (center of 5x5 is kingPos)
        int newY = Math.max(0, yRight - yLeft);
        int newX = Math.max(0, xRight - xLeft);
        
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[0].length; j++){
                grid5[i+newY][j+newX] = grid[i][j];

            }
        }

        return grid5;
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
    
    public double[][] getThreatBoard(boolean isWhite){
        if(isWhite){
            return this.whiteThreatGrid;
        } else{ return this.blackThreatGrid;}
    }

    public void setSquareEval(double[][] grid){
        this.squareEval = grid;
    }
    public void setKingDangerWeight(double[][] grid){
        this.kingDangerWeight = grid;
    }
    public void setKingProtectionWeight(double[][]grid){
        this.kingProtectionWeight = grid;
    }
    public void setPieceWeights(double pawn, double knight, double bishop, double rook, double queen){
        this.pawnWeight = pawn;
        this.knightWeight = knight;
        this.bishopWeight = bishop;
        this.rookWeight = rook;
        this.queenWeight = queen;
    }
    public void setPieceWeights(double[] weights){
        this.pawnWeight =weights[0];
        this.knightWeight = weights[1];
        this.bishopWeight = weights[2];
        this.rookWeight = weights[3];
        this.queenWeight = weights[4];

    }
    public void setPieceOnDiceWeight(double w){
        this.pieceOnDiceWeight = w;
    }
    /**
     * Method printDebug : for testing purpose
     * Print evaluation (before addition) 
     * and an 8x8 grid that represents the number of threats posed on each square
     * @param isWhite boolean indicatin whether to look for black pieces treats or white pieces threats
     */
    public void printDebug(boolean isWhite){
        //Evaluation eval = new Evaluation(cb);

        if(isWhite){
            System.out.println("White eval debug : ");
        }else {
            System.out.println("Black eval debug : ");
        }
        System.out.println("----------------------------");
        System.out.println("Material Evaluation = " + this.getMaterialEval());
        System.out.println("Center Control Evaluation = " + this.getCenterControlEval());
        System.out.println("King Safety Evaluation = " + this.getKingSafetyEval());
        System.out.println("-----------------------");
        System.out.println("Threat grid: ");
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                System.out.print(this.getNumThreats(board[y][x], isWhite)+ "  ");
            }
            System.out.println("  ");
        }
        System.out.println("-----------------------");
    }
}