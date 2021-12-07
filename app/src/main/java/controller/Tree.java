package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Tree {
    private Node root;
    private int depth;
    private Boolean isWhite;
    private String[] pieceNames ;
    private List test; //generate
    private int[]  bounds = {-1290,1290};
    private int numNodes = 0;

    public Node getRoot() {
        return root;
    }
    public int getNumberOfNodes(){
        return numNodes;
    }

    public Boolean getIsWhite(){ return this.isWhite;}

    public void setRoot(Node root) {
        this.root = root;
        numNodes++;
    }

    public Tree(int depth, Game game, String pieceName, Boolean isWhite) {
        this.root = new Node(false, pieceName, game.getChessBoard());
        this.depth = depth;
        this.isWhite = game.getWhitePlayer().getIsMyTurn();

        this.pieceNames = new String[]{ "Rook", "Bishop", "Queen", "King", "Knight", "Pawn" };
        this.test = new ArrayList();
        this.generateTurns();
    }

    private void generateTurns(){
        boolean toadd = isWhite;
        for (int i = 0; i < depth; i++) {
            this.test.add(toadd);
            this.test.add(toadd);
            toadd = !toadd;
        }
        Collections.reverse(test);
        //System.out.println(test);
    }

    // Get best board
    public Node getBestBoard(){

        return root.getChildren().stream().min(Comparator.comparing(Node::getValue)).get();
    }

    // Generate tree methods
    public void generateTree(){
        createLegalMovesNodes(root, this.isWhite);
        // Check if you can capture king
        if(checkCapture()){
            return;
        }
        depth = (depth - 1)*2;
        generate(root.getChildren(), true);
        calculateTree(root.getChildren());
    }

    public boolean checkCapture(){

        boolean canCaptureKing = false;

        for (Node child : this.root.getChildren())
        {
           if(child.getBoard().missingKing()){
               child.setValue(-10000);
               canCaptureKing = true;
           }
        }
        return canCaptureKing;
    }

    private void generate(ArrayList<Node> nodes, Boolean pieceNode){
        if(depth > 0) {
            
            for (Node n : nodes) {
                if (!n.getBoard().missingKing()) {
                    if (pieceNode) {
                        createPiecesNodes(n, (boolean) test.get(depth));
                    } else {
                        //System.out.println(depth);
                        // createLegalMovesNodes(n, this.isWhite); //check if number is even or not
                        createLegalMovesNodes(n, (boolean) test.get(depth));
                        //this.isWhite = !this.isWhite;
                    }
                    if((boolean)test.get(depth) == true){
                        
                    }

                    depth = depth - 1;
                    generate(n.getChildren(), !pieceNode);
                    depth = depth + 1;
                }
            }
        }
    }

    public void createPiecesNodes(Node n, boolean isWhite){
        ArrayList<String> pieceNames = getMovableNames(n.getBoard(), isWhite);
        for (String pieceName : pieceNames)
        {
            Node temp = new Node(false, pieceName, n.getBoard());
            n.addChild(temp);
            numNodes++;
        }
    }


    public ArrayList<Piece> getAllMovablePieces(ChessBoard cb, boolean isWhite){
        ArrayList<Piece> movablePieces = new ArrayList<Piece>();
        for(Piece p: cb.getLivePieces() ){
            if((p.isWhite() == isWhite) && p.getLegalMoves(cb).size()>0){
                movablePieces.add(p);
            }
        }
        return movablePieces;
    }

    public ArrayList<String> getMovableNames(ChessBoard currentboard, boolean isWhite){
        ArrayList<Piece> movablePieces = getAllMovablePieces(currentboard, isWhite);
        ArrayList<String> names = new ArrayList<>();
        for(Piece p : movablePieces){
            if(!names.contains(p.pieceName)){
                names.add(p.pieceName);
            }
        }
        return names;
    }


    public void createLegalMovesNodes(Node n, Boolean isWhite){
        List<Piece> pieceObjects = n.getBoard()
                .getLivePieces().stream()
                .filter(p -> p.getPieceName().equals(n.getPiece()))
                .filter(p -> p.isWhite() == isWhite)
                .collect(Collectors.toList());
        for (Piece piece : pieceObjects)
        {
            ArrayList<ChessBoard> tempScenarios = runScenarios(n.getBoard(), piece);
            for (ChessBoard scenario : tempScenarios){
                int value = (int)scenario.getBoardValue();
                Node temp = new Node(true, scenario, value);
                n.addChild(temp);
                numNodes++;
            }
        }
    }

    // Calculate tree methods
    public void calculateTree(ArrayList<Node> nodes){
        if(depth > 0) {
            for (Node n : nodes) {
                depth = depth - 1;
                calculateTree(n.getChildren());
                if (n.isProbability()) {
                    //calculateProbability(n);
                    getMaxValue(n);
                }
                else {
                    //getMaxValue(n);
                   if((boolean)test.get(depth) == true){
                       getMaxValue(n);
                   }
                   else{
                       getMinValue(n);
                   }
                //   if(isWhite){ getMaxValue(n); }
                //    else{ getMinValue(n); }
                //    this.isWhite = !this.isWhite; // Check if depth is even or odd
                }
                depth = depth + 1;
            }
        }
    }

    public void calculateProbability(Node n){
        double value = 0;
        for (Node child : n.getChildren()) {
            value = value + ((double)child.getValue())/(n.getChildren().size());
        }
        n.setValue(value);
    }

    public void getMaxValue(Node n){
        double value = 0;
        for (Node child : n.getChildren()) {
            if(child.getValue() > value){
                value = child.getValue();
            }
            //System.out.println(child.getValue());
        }
        //System.out.println(value);
        n.setValue(value);
        n.setAlpha(value);
    }

    public void getMinValue(Node n){
        double value = 10000;
        for (Node child : n.getChildren()) {
            if(child.getValue() < value){
                value = child.getValue();
            }
        }
        if(n.getChildren().size() == 0){
            value = 0;
        }
        //System.out.println(value);
        n.setValue(value);
        n.setBeta(value);
    }

    public ArrayList<ChessBoard> runScenarios(ChessBoard originalCB, Piece movingPiece){
        ArrayList<ChessBoard> allCenerios = new ArrayList<ChessBoard>();
        ChessBoard copyBoard = this.copyBoard(originalCB);
        Piece movingPieceCopy = copyBoard.getBoard()[movingPiece.getCurrentPosition().getYPos()][movingPiece.getCurrentPosition().getXPos()].getPieceOnSq();
        ArrayList<Square> legalMoves = movingPieceCopy.getLegalMoves(copyBoard);

//        System.out.println("CHESSBOARD: ");
//        originalCB.printBoard();
//        System.out.println();

        for(Square legalSquare : legalMoves ){
            copyBoard = this.copyBoard(originalCB);
            movingPieceCopy = copyBoard.getBoard()[movingPiece.getCurrentPosition().getYPos()][movingPiece.getCurrentPosition().getXPos()].getPieceOnSq();

            Square toMove = copyBoard.getBoard()[legalSquare.getYPos()][legalSquare.getXPos()];
            ArrayList<Square> fakeLegalMoves = new ArrayList<Square>();
            fakeLegalMoves.add(toMove);

            movingPieceCopy.move(toMove, copyBoard , fakeLegalMoves);
            ChessBoard copy = copyBoard(copyBoard);
            allCenerios.add(copy);
        }
        return allCenerios;
    }

    public ChessBoard copyBoard(ChessBoard cb){
        ChessBoard copy = new ChessBoard(true);
        for(Piece p : cb.getLivePieces()){
            int y = p.getCurrentPosition().getYPos();
            int x = p.getCurrentPosition().getXPos();
            Piece newP = null;
            if(p.getPieceName().equals("Pawn")) {
                newP = new Pawn(p.isWhite());
            }
            if(p.getPieceName().equals("Rook")) {
                newP = new Rook(p.isWhite());
            }
            if(p.getPieceName().equals("Knight")) {
                newP = new Knight(p.isWhite());
            }
            if(p.getPieceName().equals("Queen")) {
                newP = new Queen(p.isWhite());
            }
            if(p.getPieceName().equals("King")) {
                newP = new King(p.isWhite());
            }
            if(p.getPieceName().equals("Bishop")) {
                newP = new Bishop(p.isWhite());
            }
            copy.getLivePieces().add(newP);
            copy.getBoard()[y][x].placePiece(newP);
        }
        return copy;
    }

    public Square getBestSquare(){
        ChessBoard best = root.getChildren().stream().min(Comparator.comparing(Node::getValue)).get().getBoard();
        Square bestSquare = null;
        //System.out.println("best board is : ");
        //best.printBoard();
        //System.out.println("The children of the root: ");
        // for (Node n : root.getChildren()) {
        //     System.out.println(n.getValue());
        //     n.getBoard().printBoard();
//        }
        List<Piece> pieceObjectsBestBoard = best
                .getLivePieces().stream()
                .filter(p -> p.getPieceName().equals(root.getPiece()))
                .filter(p -> p.isWhite() == isWhite)
                .collect(Collectors.toList());

        for (Piece piece : pieceObjectsBestBoard)
        {
            Square squareOriginalBoard = root.getBoard().getBoard()[piece.getCurrentPosition().getYPos()][piece.getCurrentPosition().getXPos()];
            if(!squareOriginalBoard.isTakenSquare() || squareOriginalBoard.getPieceOnSq().isWhite() != this.isWhite){
                bestSquare = squareOriginalBoard;
            }
        }
        return bestSquare;
    }

    public Piece getBestPiece(){
        ChessBoard best = root.getChildren().stream().min(Comparator.comparing(Node::getValue)).get().getBoard();
        Piece bestPiece = null;

        List<Piece> pieceObjectsOriginalBoard = root.getBoard()
                .getLivePieces().stream()
                .filter(p -> p.getPieceName().equals(root.getPiece()))
                .filter(p -> p.isWhite() == isWhite)
                .collect(Collectors.toList());

        for (Piece piece : pieceObjectsOriginalBoard)
        {
            Square squareBestBoard = best.getBoard()[piece.getCurrentPosition().getYPos()][piece.getCurrentPosition().getXPos()];
            if(!squareBestBoard.isTakenSquare()){
                bestPiece = piece;
            }
        }
        return bestPiece;
    }
}
