package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Tree {
    private Node root;
    private int depth;
    private Boolean isWhite;
    private String[] pieceNames = { "Rook", "Bishop", "Queen", "King", "Knight", "Pawn" };

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Tree(int depth, ChessBoard cb, String pieceName, Boolean isWhite) {
        this.root = new Node(false, pieceName, cb);
        this.depth = depth;
        this.isWhite = isWhite;
    }

    // Get best board
    public Node getBestBoard(){

        return root.getChildren().stream().max(Comparator.comparing(Node::getValue)).get();
    }

    public Square getBestSquare(){
        ChessBoard best = root.getChildren().stream().max(Comparator.comparing(Node::getValue)).get().getBoard();
        Square bestSquare = null;

        List<Piece> pieceObjectsBestBoard = best
                .getLivePieces().stream()
                .filter(p -> p.getPieceName().equals(root.getPiece()))
                .filter(p -> p.isWhite() == !isWhite)
                .collect(Collectors.toList());

        for (Piece piece : pieceObjectsBestBoard)
        {
            Square squareOriginalBoard = root.getBoard().getBoard()[piece.getCurrentPosition().getYPos()][piece.getCurrentPosition().getXPos()];
            if(!squareOriginalBoard.isTakenSquare()){
                bestSquare = squareOriginalBoard;
            }
        }
        return bestSquare;
    }

    public Piece getBestPiece(){
        ChessBoard best = root.getChildren().stream().max(Comparator.comparing(Node::getValue)).get().getBoard();
        Piece bestPiece = null;

        List<Piece> pieceObjectsOriginalBoard = root.getBoard()
                .getLivePieces().stream()
                .filter(p -> p.getPieceName().equals(root.getPiece()))
                .filter(p -> p.isWhite() == !isWhite)
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

    // Generate tree methods
    public void generateTree(){
        createLegalMovesNodes(root, this.isWhite);
        this.isWhite = !this.isWhite;
        depth = (depth - 1)*2;
        generate(root.getChildren(), true);
    }

    private void generate(ArrayList<Node> nodes, Boolean pieceNode){
        if(depth > 0) {
            for (Node n : nodes) {
                if (pieceNode) {
                    createPiecesNodes(n);
                } else {
                    createLegalMovesNodes(n, this.isWhite);
                    this.isWhite = !this.isWhite;
                }
                depth = depth - 1;
                generate(n.getChildren(), !pieceNode);
                depth = depth + 1;
            }
        }
    }

    public void createPiecesNodes(Node n){
        for (String pieceName : this.pieceNames)
        {
            Node temp = new Node(false, pieceName, n.getBoard());
            n.addChild(temp);
        }
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
                    calculateProbability(n);
                } else {
                    if(isWhite){ getMaxValue(n); }
                    else{ getMinValue(n); }
                    this.isWhite = !this.isWhite;
                }
                depth = depth + 1;

            }
        }
    }

    public void calculateProbability(Node n){
        int value = 0;
        for (Node child : n.getChildren()) {
            value = value + child.getValue()/6;
        }
        n.setValue(value);
    }

    public void getMaxValue(Node n){
        int value = 0;
        for (Node child : n.getChildren()) {
            if(child.getValue() > value){
                value = child.getValue();
            }
            //System.out.println(child.getValue());
        }
        n.setValue(value);
    }

    public void getMinValue(Node n){
        int value = 10000;
        for (Node child : n.getChildren()) {
            if(child.getValue() < value){
                value = child.getValue();
            }
        }
        if(n.getChildren().size() == 0){
            value = 0;
        }
        n.setValue(value);
    }

    public ArrayList<ChessBoard> runScenarios(ChessBoard originalCB, Piece movingPiece){
        ArrayList<ChessBoard> allCenerios = new ArrayList<ChessBoard>();
        ArrayList<Square> legalMoves = movingPiece.getLegalMoves(originalCB);
        Square originalPosition = originalCB.getBoard()[movingPiece.getCurrentPosition().getYPos()][movingPiece.getCurrentPosition().getXPos()];
        ArrayList<Square> fakeLegalMoves = new ArrayList<Square>();
        fakeLegalMoves.add(originalPosition);
        for(Square legalSquare : legalMoves ){
            movingPiece.move(legalSquare, originalCB , legalMoves);
            ChessBoard copy = copyBoard(originalCB);
            allCenerios.add(copy);
            movingPiece.move(originalPosition, originalCB , fakeLegalMoves);
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
}
