package controller;

import java.util.ArrayList;

public class Node {
    private double value = 100000;
    private boolean isProbability;
    private ArrayList<Node> children;
    private ChessBoard board;
    private String piece;
    private double alpha = 10001;
    private double beta = -10001;
    private boolean isWhite;
    private Node bestChild;
    private Node parent;
    private Node grandParent;
    private boolean isRoot= false;


    public Node(ChessBoard board, boolean isWhite, Node parent){
        this.board= board;
        this.children = new ArrayList<Node>();
        this.isWhite = isWhite;
        this.parent = parent;
        // alpha = parent.getAlpha();
        // beta = parent.getBeta();
    }
    public Node(ChessBoard board , String piece, boolean isWhite, Node parent, boolean isRoot){
        this.board= board;
        this.children = new ArrayList<Node>();
        this.isWhite = isWhite;
        this.piece =piece;
        this.parent = parent;
        this.isRoot = isRoot;
        if(!isRoot){
            alpha = parent.getAlpha();
            beta = parent.getBeta();
        }

        //if()
    }

    public ChessBoard getBoard() {
        return board;
    }
    
    public boolean getisRoot(){
        return isRoot;
    }
    public void setIsRoot(boolean isRoot){
        this.isRoot = isRoot;
    }



    public boolean getisWhite(){
        return isWhite;
    }
    public Node getBestChild() {
        return bestChild;
    }

    public void setBestChild(Node b){
        bestChild = b;
    }
    
    public void setisWhite(boolean isWhite){
        this.isWhite = isWhite;
    }

    public double getAlpha(){
        return alpha;
    }
    public double getBeta(){
        return beta;
    }
    public void setAlpha(double alpha){
        this.alpha=alpha;
    }
    public void setBeta(double beta){
        this.beta = beta;
    }
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }
    public Node getParent() {
        return parent;
    }

    public void setGrandParent(Node abuelo) {
        this.parent = abuelo;
    }
    public Node getGrandParent() {
        return grandParent;
    }

    public void setParent(Node p) {
        this.parent = p;
    }

    public boolean isProbability() {
        return isProbability;
    }

    public void setProbability(boolean probability) {
        isProbability = probability;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
        //updating alpha and beta for the piece nodes
        if(this.piece !=null){     //if this is a piece node
            if(isWhite){ //if its the maximizing player
                if(value > alpha){
                    alpha = value; //update alpha
                }
            }   
            else{ //if this is the minimizing player
                if(value < beta){
                    beta = value; //update beta                
                }
            }
        }
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    // Methods
    public void addChild(Node n){
        this.children.add(n);
    }

    public boolean isLeaf() {
        if (getChildren().equals(null)) {
            return true;
        } else {
            return false;
        }
    }

    public double getChildrenAvg(){
        int sum=0;
        for(Node n: children){
            sum+=n.getValue();
        }
        return sum/children.size();
    }
}