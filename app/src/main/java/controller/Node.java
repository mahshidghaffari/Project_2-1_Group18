package controller;

import java.util.ArrayList;
/*
Class Node : node object class. We used a linked tree : 
Every node stores : - its parent
                    - its children (ArrayList<Node>)
                    - some value (either the expected value of all children, or the evaluation of the chessboard)
There are two types of nodes :
                    - what we called "piece nodes" and "game_state node"
                    a piece node is a node that represents the random event that a specific type of piece has been selected
                    a game_state node is a node that represents the state of the board (after a new move). This node will be maximized/minimized by our algorithm

    
*/
public class Node {
    Node parentNode;
    ArrayList<Node> children = new ArrayList<Node>();
    boolean probNode; //This boolean determine whether the node stores expected value (true) or evaluation of the board (false)
    ChessBoard cb;
    String pieceName;
    int nodeValue;
    /*
    Constructor that does not take in children
    */
    public Node(Node parent, boolean prob){
        if(parent != null){this.parentNode = parent;}
        this.probNode = prob;
    }
    /*
    Constructor for game-state node
        takes in a ChessBoard
        takes in a Children array
    */
    public Node(Node parent, ArrayList<Node> children, boolean prob, ChessBoard cb){
        if(parent != null){this.parentNode = parent;} //parent may be null if  node == root of the tree
        this.children = children;
        this.probNode = prob;
        this.cb = cb;
    }
    /*
    Constructor for piece node. This constructor creates a node that stores the piece determined by the dice
        takes in a pieceName parameters
    */

    public Node(Node parent, boolean prob, String pieceName){
        if(parent != null){this.parentNode = parent;}
        this.probNode = prob;
        this.pieceName = pieceName;
    }
    //Getters and setters
    public void setParentNode(Node parent){
        this.parentNode = parent;
    }
    public Node getParentNode(){
        return this.parentNode;
    }
    public void setChildren(ArrayList<Node> children){
        this.children = children;
    }
    public ArrayList<Node> getChildren(){
        return this.children;
    }
    public boolean getProb(){
        return this.probNode;
    }
    public void setProb(boolean prob){
        this.probNode = prob;
    }
    public String getPiece(){
        return this.pieceName;
    }
    public void addChild(Node node){
        this.children.add(node);
    }

    public int getNodeValue(){
        return nodeValue;
    }
    public void setNodeValue(int val){
        this.nodeValue = val;
    }
    public void setChessBoard(ChessBoard cb){
        this.cb = cb;
    }
    public ChessBoard getChessBoard(){
        return this.cb;
    }
    /*
    Method addPieceNodes : methods that will add 6 children to a node. Each child will represent that 
                            dice constraint for a piece
    @return children : this is used to safely store the piece nodes
    */
    public ArrayList<Node> addPieceNodes(){
        ArrayList<Node> children = new ArrayList<Node>();
        Node n1 = new Node(this,true, "Pawn");
        Node n2 = new Node(this,true, "Knight");
        Node n3 = new Node(this,true, "Bishop");
        Node n4 = new Node(this,true, "Rook");
        Node n5 = new Node(this,true, "Queen");
        Node n6 = new Node(this,true, "King");
        children.add(n1);
        children.add(n2);
        children.add(n3);
        children.add(n4);
        children.add(n5);
        children.add(n6);
        this.setChildren(children);
        return children;
    }
    public boolean isLeaf(){
        return this.children == null;
    }
}
