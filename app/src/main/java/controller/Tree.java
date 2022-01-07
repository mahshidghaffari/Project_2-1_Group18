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
    private boolean AIblack=true;
    private int upperBound = 2000;
    private int lowerBound = -2000;

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
        //this.root = new Node(false, pieceName, game.getChessBoard(), isWhite);
        this.root = new Node(game.getChessBoard(),pieceName,isWhite,null, true);
       // root.setIsRoot(true);
        // root.setAlpha(Double.MIN_VALUE);
        // root.setBeta(Double.MAX_VALUE);
        //System.out.println("Root is a "+ pieceName);
        this.depth = depth*2;
        //this.isWhite = game.getWhitePlayer().getIsMyTurn();
        this.isWhite = isWhite;
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
    }
    public Node getBestBoard2(){
        return root.getBestChild();
    }

    public void generateTree2(Node n){
        //if (!n.getBoard().missingKing()) {
            if(depth==0){
                double boardVal = n.getBoard().getBoardValue();
                n.setValue(boardVal);
                Node parent = n.getParent();
               

                // if(parent.getisWhite()){  //if the parent of this board is maximizing then,  
                //     if(boardVal > parent.getValue()){ //if the value of the leaf is better for max then the current value 
                //         parent.setValue(boardVal);   //update it to the new explored leafs value 
                //     }
                // }
                // else if(!parent.getisWhite()){ //if the parent is minimizing
                //     if(boardVal < parent.getValue()){
                //         parent.setValue(boardVal);
                //     }
                // }
                return;
            }
            if(n.getPiece()!=null){  //if this is a piece and not a board
                    List<Piece> pieceObjects = n.getBoard()  //getting the pieces that can move
                        .getLivePieces().stream()
                        .filter(p -> p.getPieceName().equals(n.getPiece()))
                        .filter(p -> p.isWhite() == isWhite)
                        .collect(Collectors.toList());
                for(Piece p : pieceObjects){ 
                    for(Square move: p.getLegalMoves(n.getBoard())){
                        ChessBoard scenario = getScenerio(n.getBoard(), p, move);

                        Node childBoard = new Node(scenario, n.getisWhite(), n);


                        // System.out.println("next node is a board -->");
                        // childBoard.getBoard().printBoard();

                        //if(!scenario.missingKing()) {
                            depth--;
                            generateTree2(childBoard);

                            if(n.getisWhite()){//MAXIMIZE
                                if(n.getValue() == 10000){n.setValue(-10000);}
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
                    Node childPiece = new Node(n.getBoard(),name,isWhite,n, false);
                   // System.out.println("next node is a piece : "+ childPiece.getPiece());
                    depth--;
                    generateTree2(childPiece);
                    
                    if(n.getValue() == 10000){
                        n.setValue(0 + childPiece.getValue()/movableNames.size());
                    }
                    else{
                        n.setValue(n.getValue()+ childPiece.getValue()/movableNames.size());
                    }
                    depth++;

                    if(n.getisWhite()){ //if its maximizing
                        if(n.getAlpha()>= childPiece.getValue() + (upperBound*(movableNames.size()- counter))/movableNames.size()){
                            //System.out.println("prunning alpha");
                            break;
                        }
                    }
                    else{ //if its manimizing
                        double g =childPiece.getValue() + (lowerBound*(movableNames.size()- counter))/movableNames.size();
                        if(n.getBeta()<= childPiece.getValue() + (lowerBound*(movableNames.size()- counter))/movableNames.size()){
                            //System.out.println("prunning beta");
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

    public Square getBestSquare(){
        //ChessBoard best = root.getChildren().stream().min(Comparator.comparing(Node::getValue)).get().getBoard();
        ChessBoard best = root.getBestChild().getBoard();
        Square bestSquare = null;

        List<Piece> pieceObjectsBestBoard = best
                .getLivePieces().stream()
                .filter(p -> p.getPieceName().equals(root.getPiece()))
                .filter(p -> p.isWhite() == getRoot().getisWhite())
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
        //ChessBoard best = root.getChildren().stream().min(Comparator.comparing(Node::getValue)).get().getBoard();
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
            if(!squareBestBoard.isTakenSquare()){
                bestPiece = piece;
            }
        }
        return bestPiece;
    }
}



