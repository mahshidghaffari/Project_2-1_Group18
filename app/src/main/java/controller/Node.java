package controller;

import java.util.ArrayList;

public class Node {
    private double value;
    private boolean isProbability;
    private ArrayList<Node> children;
    private ChessBoard board;
    private String piece;
    private double alpha;
    private double beta;
    

    public Node(boolean isProbability, String piece, ChessBoard board) {
        this.isProbability = isProbability;
        this.piece = piece;
        this.board = board;
        this.children = new ArrayList<Node>();
    }

    public Node(boolean isProbability, ChessBoard board, int value) {
        this.isProbability = isProbability;
        this.board = board;
        this.children = new ArrayList<Node>();
        this.value = value;
    }

    public ChessBoard getBoard() {
        return board;
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

    public double getChildrenAvg(){
        int sum=0;
        for(Node n: children){
            sum+=n.getValue();
        }
        return sum/children.size();
    }
}