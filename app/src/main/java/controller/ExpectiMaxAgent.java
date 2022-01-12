package controller;

import java.util.List;
import java.util.stream.Collectors;

public class ExpectiMaxAgent extends Player {//extends BlackPlayer {

    private ChessBoard cb;
    private Game game;
    private String chosenPiece;
    private int depth;
    private boolean isWhite;
    private boolean noMoves;
    private Tree tree;
    int numNodes;

    public ExpectiMaxAgent(Game game, ChessBoard cb, String chosenPiece, int depth, boolean isWhite,double[]weights) {
        super(cb);
        this.cb = cb;
        this.game = game;
        this.chosenPiece = chosenPiece;
        this.depth = depth;
        this.isWhite = isWhite;
        this.tree = new Tree(depth, game, chosenPiece, isWhite, weights);
        numNodes = 0;
    }

    public void expectiMaxPlay(boolean isWhite) {
        game.newTurn = false;

        if (this.canMove(chosenPiece,isWhite)) {
            noMoves = false;
            tree.generateTree2(tree.getRoot());
            Piece best = tree.getBestPiece();
            Square bestMove = tree.getBestSquare(best);
            if(best == null){
                tree.getRoot().getBoard().printBoard();
                tree.getRoot().getBestChild().getBoard().printBoard();
//                System.out.println(",,,");
//                List<Piece> pieceObjects = tree.getRoot().getBoard()  //getting the pieces that can move
//                        .getLivePieces().stream()
//                        .filter(p -> p.getPieceName().equals(tree.getRoot().getPiece()))
//                        .filter(p -> p.isWhite() == isWhite)
//                        .collect(Collectors.toList());
//                for(Piece p : pieceObjects){
//                    for(Square move: p.getLegalMoves(tree.getRoot().getBoard())){
//                        ChessBoard cb = tree.getScenerio(tree.getRoot().getBoard(), p, move);
//                        cb.printBoard();
//                    }
//                }
            }
            //Square bestMove = tree.getBestSquare();
            Node child = tree.getRoot();

            // System.out.println("Yes Moves");
            // System.out.println(best.getPieceName());
            // System.out.println("X: " + bestMove.getXPos() + " Y: " + bestMove.getYPos());
           // System.out.println(isWhite + "  moved from [" + best.getCurrentPosition().getYPos()+"]["+best.getCurrentPosition().getXPos()+"] to ["+bestMove.getYPos()+"]["+bestMove.getXPos()+"]");
            best.move(bestMove, cb,  best.getLegalMoves(cb));
            //System.out.println("Number of nodes searched : " + tree.getNumberOfNodes());
            
            //Node b = tree.getBestBoard();
            //b.getBoard().printBoard();
            //System.out.println("/////////////////////////////////" + b.getValue());
            game.updateBoard();
            game.newTurn();

        } else {
            noMoves = true;
            game.newTurn();
            //System.out.println("No Moves");
        }
        this.numNodes = tree.getNumberOfNodes();
    }
}
