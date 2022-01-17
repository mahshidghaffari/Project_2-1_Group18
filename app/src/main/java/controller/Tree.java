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
    private int numNodes = 0;
    private int upperBound = 11030;
    private int lowerBound = -11030;
    private double[] weights;

    public Node getRoot() {
        return root;
    }
    public int getNumberOfNodes(){
        return numNodes;
    }
    public Boolean getIsWhite(){ return this.isWhite;}

    public Tree(int depth, Game game, String pieceName, Boolean isWhite) {
        this.root = new Node(game.getChessBoard(),pieceName,isWhite,null, true);
        this.depth = depth*2;
        this.isWhite = isWhite;
    }
    public Tree(int depth, Game game, String pieceName, Boolean isWhite, double [] weights) {
        this.root = new Node(game.getChessBoard(),pieceName,isWhite,null, true);
        this.depth = depth*2;
        this.isWhite = isWhite;
        this.weights = weights;
    }

    public void generateTree2(Node n){
            if(depth==0 || n.getBoard().missingKing()){
                //double boardVal = n.getBoard().getBoardValue();
                Evaluation eval = new Evaluation(n.getBoard(), weights);
                //eval.setSquareWeights(weights);
                n.setValue(eval.getScore());
                return;
            }
            if(n.getPiece()!=null){  //if this is a piece and not a board
                    List<Piece> pieceObjects = n.getBoard()  //getting the pieces that can move
                        .getLivePieces().stream()
                        .filter(p -> p.getPieceName().equals(n.getPiece()))
                        .filter(p -> p.isWhite() == n.getisWhite())
                        .collect(Collectors.toList());
                for(Piece p : pieceObjects){ 
                    for(Square move: p.getLegalMoves(n.getBoard())){
                        ChessBoard scenario = getScenerio(n.getBoard(), p, move);
                        Node childBoard = new Node(scenario, n.getisWhite(), n);
                        numNodes++;

                        // System.out.println("next node is a board -->");
                        // childBoard.getBoard().printBoard();

                        //if(!scenario.missingKing()) {
                            depth--;
                            generateTree2(childBoard);
                            if(n.getisWhite()){//MAXIMIZE
                                if(n.getValue() == 100000){n.setValue(-100000);}
                                if(n.getValue() <= childBoard.getValue()){
                                    n.setValue(childBoard.getValue());
                                    n.setBestChild(childBoard);
                                }
                            }else{
                                if(n.getValue() >= childBoard.getValue()){//MINIMAZE
                                    n.setValue(childBoard.getValue());
                                    n.setBestChild(childBoard);
                                }
                            }
                            depth++;
                    }
                }
            }
            else if(n.getPiece()==null){ //if its a board node
                ArrayList<String> movableNames = getMovableNames(n.getBoard(),!n.getisWhite());
                int counter=0;
                for(String name: movableNames){
                    counter++;
                    Node childPiece = new Node(n.getBoard(),name,!n.getisWhite(),n, false);
                    numNodes++;
                   // System.out.println("next node is a piece : "+ childPiece.getPiece());
                    depth--;
                    generateTree2(childPiece);
                    
                    if(n.getValue() == 100000){
                        n.setValue(0 + childPiece.getValue()/movableNames.size());
                    }
                    else{
                        n.setValue(n.getValue()+ childPiece.getValue()/movableNames.size());
                    }
                    depth++;

                    if(n.getisWhite()){ //if its maximizing
                        if(n.getValue() >= childPiece.getValue() + (upperBound*(movableNames.size()- counter))/movableNames.size()){
                            break;
                        }
                    }
                    else{ //if its manimizing
                        if(n.getValue() <= childPiece.getValue() + (lowerBound*(movableNames.size()- counter))/movableNames.size()){
                            break;
                        }
                    }
                }
            }
        }

    public ChessBoard getScenerio(ChessBoard originalCB, Piece movingPiece, Square target){
        ChessBoard copyBoard = this.copyBoard(originalCB);
        Piece movingPieceCopy = copyBoard.getBoard()[movingPiece.getCurrentPosition().getYPos()][movingPiece.getCurrentPosition().getXPos()].getPieceOnSq();
        Square toMove = copyBoard.getBoard()[target.getYPos()][target.getXPos()]; //target sqaure in the new board
        ArrayList<Square> fakeLegalMoves = new ArrayList<Square>();
        fakeLegalMoves.add(toMove);
        movingPieceCopy.move(toMove, copyBoard , fakeLegalMoves);

        return copyBoard;
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

    public Square getBestSquare(Piece bestPiece) {
        Square best = null;
        ArrayList<Square> legalMoves = bestPiece.getLegalMoves(root.getBoard());
        for (Square s : legalMoves) {
            Square bestBoard = root.getBestChild().getBoard().getSquare(s.getYPos(), s.getXPos());
            if (bestBoard.getPieceOnSq() != null) {
                if (bestBoard.getPieceOnSq().getPieceName() == bestPiece.getPieceName()) {
                    if(bestPiece.isWhite() == bestBoard.getPieceOnSq().isWhite()){
                        best = s;
                    }
                }
            }
        }
        if(best == null){
            for (Square s : legalMoves) {
                Square bestBoard = root.getBestChild().getBoard().getSquare(s.getYPos(), s.getXPos());
                if (bestBoard.getPieceOnSq() != null) {
                   if(bestPiece.getPieceName() == "Pawn" && bestBoard.getPieceOnSq().getPieceName() == "Queen"){
                        if(bestPiece.isWhite() == bestBoard.getPieceOnSq().isWhite()){
                            best = s;
                        }
                    }
                }
            }
        }
        return best;
    }

    public Piece getBestPiece(){
        ChessBoard best = root.getBestChild().getBoard();
        Piece bestPiece = null;


        List<Piece> pieceObjectsOriginalBoard = root.getBoard()
                .getLivePieces().stream()
                .filter(p -> p.getPieceName().equals(root.getPiece()))
                .filter(p -> p.isWhite() == isWhite)
                .collect(Collectors.toList());

        for (Piece piece : pieceObjectsOriginalBoard)
        {
            Square squareBestBoard = best.getBoard()[piece.getCurrentPosition().getYPos()][piece.getCurrentPosition().getXPos()];
            if(squareBestBoard.getPieceOnSq() == null){
                for (Square s : piece.getLegalMoves(root.getBoard())) {
                    Square bestBoard = root.getBestChild().getBoard().getSquare(s.getYPos(), s.getXPos());
                    if(bestBoard.getPieceOnSq() != null){
                        if (bestBoard.getPieceOnSq().getPieceName() == root.getPiece()) {
                            if(piece.isWhite() == bestBoard.getPieceOnSq().isWhite())
                                bestPiece = piece;
                        }
                    }
                }
            }
        }
        if(bestPiece == null){

            for (Piece piece : pieceObjectsOriginalBoard)
            {
                Square squareBestBoard = best.getBoard()[piece.getCurrentPosition().getYPos()][piece.getCurrentPosition().getXPos()];
                if(squareBestBoard.getPieceOnSq() == null){
                    for (Square s : piece.getLegalMoves(root.getBoard())) {
                        Square bestBoard = root.getBestChild().getBoard().getSquare(s.getYPos(), s.getXPos());
                        if(bestBoard.getPieceOnSq() != null){
                            if(bestBoard.getPieceOnSq().getPieceName() == "Queen" && root.getPiece() == "Pawn"){
                                if(piece.isWhite() == bestBoard.getPieceOnSq().isWhite())
                                    bestPiece = piece;
                            }
                        }
                    }
                }
            }
        }
        return bestPiece;
    }
}



