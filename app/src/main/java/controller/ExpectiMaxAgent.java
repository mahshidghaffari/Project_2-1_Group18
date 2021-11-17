package controller;

public class ExpectiMaxAgent extends BlackPlayer{

    private ChessBoard cb;
    private Game game;
    private String chosenPiece;
    private int depth;
    private boolean isWhite;
    private boolean noMoves;
    private Tree tree;

    public ExpectiMaxAgent(Game game, ChessBoard cb, String chosenPiece, int depth, boolean isWhite) {
        super(cb);
        this.cb = cb;
        this.game = game;
        this.chosenPiece = chosenPiece;
        this.depth = depth;
        this.isWhite = isWhite;
        this.tree = new Tree(depth, game, chosenPiece, isWhite);
    }

    public void expectiMaxPlay() {
        game.newTurn = false;

        if (this.canMove(chosenPiece)) {
            noMoves = false;

            tree.generateTree();
            tree.calculateTree(tree.getRoot().getChildren());

            Piece best = tree.getBestPiece();
            Square bestMove = tree.getBestSquare();

            System.out.println("Yes Moves");
            System.out.println(best.getPieceName());
            System.out.println("X: " + bestMove.getXPos() + " Y: " + bestMove.getYPos());

            best.move(bestMove, cb,  best.getLegalMoves(cb));

            Node b = tree.getBestBoard();
            b.getBoard().printBoard();
            System.out.println("/////////////////////////////////" + b.getValue());
            game.updateBoard();
            game.newTurn();

        } else {
            noMoves = true;
            game.newTurn();
            System.out.println("No Moves");
        }
    }
}
