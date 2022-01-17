package controller;

import java.util.List;
import java.util.stream.Collectors;


/**
 * This class represents the object for the Exepectiminimax agent.
 * This agent can play multiple rivals, and uses a tree to calcultae the best move based on
 * the evaluation function.
 * 
 */
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
            }
            Node child = tree.getRoot();

            best.move(bestMove, cb,  best.getLegalMoves(cb));
            game.updateBoard();
            game.newTurn();

        } else {
            noMoves = true;
            game.newTurn();
        }
        this.numNodes = tree.getNumberOfNodes();
    }
}
