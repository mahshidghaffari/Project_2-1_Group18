package controller;

public class ExpectiMaxAgent extends BlackPlayer{

    private ChessBoard cb;
    private Game game;
    private String chosenPiece;
    private int depth;
    private boolean isWhite;
    private Tree tree;

    public ExpectiMaxAgent(Game game, ChessBoard cb, String chosenPiece, int depth, boolean isWhite) {
        super(cb);
        this.cb = cb;
        this.game = game;
        this.chosenPiece = chosenPiece;
        this.depth = depth;
        this.isWhite = isWhite;
        this.tree = new Tree(depth, cb, chosenPiece, isWhite);
    }

    public void expectiMaxPlay() {
        tree.generateTree();
        tree.calculateTree(tree.getRoot().getChildren());

        Piece best = tree.getBestPiece();
        Square bestMove = tree.getBestSquare();

        best.move(bestMove, cb,  best.getLegalMoves(cb));

        game.updateBoard();
        game.newTurn();
    }
}
