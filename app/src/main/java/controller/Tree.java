package controller;

import java.util.ArrayList;

public class Tree {

    Node baseRoot;
    int depth = 2;
    Game game;
    ChessBoard cb;
    boolean whiteTurn;

    /*
    Tree consructor :
    @param Game : game currently played. The state of the game (chessboard) will be the root node of our tree
    
    */
    public Tree(Game game){
        this.game = game;
        this.cb = game.getChessBoard();
        this.whiteTurn = game.getWhitePlayer().getIsMyTurn();
        baseRoot = new Node(null, false);
        generateTree(baseRoot, cb);
    }
    /*
    method generateRoot : recursive method that builds a tree given a root node
    Each iteration of the method creates a subtree that represents Player1's moves and Player2s responding moves

    @param root : node to build subtree from
    @return a Tree : with root node = root
    */
    public Tree generateTree(Node root, ChessBoard chessBoard){
        if(depth == 0){
            return this;
        } else{
            String movingPieceStr = game.getMovingPiece();
    
            Game_States states = new Game_States(cb, movingPieceStr, whiteTurn);
            ArrayList<ChessBoard> allScenarios = states.getScenarios();
            ArrayList<Node> children = new ArrayList<Node>();
            ArrayList<Node> grandchildren = new ArrayList<Node>();

            for(int i = 0; i < allScenarios.size(); i++){
              Node n = new Node(root, false);
               n.setChessBoard(allScenarios.get(i));

               children.add(n);
               ArrayList<Node> pieceNodes = n.addPieceNodes();
               for(Node m : pieceNodes){
                    grandchildren.add(m);
                }
            }
            
            root.setChildren(children);
            depth--;
            for(Node nm : grandchildren){
                return generateTree(nm, nm.getParentNode().getChessBoard());
            }
        }
        return null;

    }
    //toString for testing
    public String toString(){
        String str =  "Piece on the root is " + baseRoot.getPiece() + " and the chessboard is : ";
        System.out.println(str);
        cb.printBoard();
        return str;
    }
}

