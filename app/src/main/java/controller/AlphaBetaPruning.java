package controller;

import java.util.ArrayList;

public class AlphaBetaPruning {

    private final double MAX = 1390;
    private final double MIN = -1390;
    private Node node;
    private int depth;
    private boolean isMax;

    public AlphaBetaPruning(Node node, int depth, boolean isMax) {
        this.node = node;
        this.depth = depth;
        this.isMax = isMax;
    }

    public double doPruning(int depth, Node node, boolean isMax, double alpha, double beta) {
        // BASE CASE
        if (node.isLeaf()) {
            double value = 0;
            for (Node child : node.getChildren()) {
                value = value + ((double) child.getValue()) / (node.getChildren().size());
            }
            return value;
        }    

        if (isMax) {
            double best = MIN;
            ArrayList<Node> children = node.getChildren();

            for (int i = 0; i < children.size(); i++) {

                double value = doPruning(depth +1, children.get(i), false, alpha, beta);
                best = Math.max(best, value);
                alpha = Math.max(alpha, best);

                if (beta <= alpha)
                    break;
            }
            return best;


        } else {
            double best = MAX;
            ArrayList<Node> children = node.getChildren();

            for (int i = 0; i < children.size(); i++) {
                double value = doPruning(depth +1, children.get(i), true, alpha, beta);
                best = Math.min(best, value);
                beta = Math.min(beta, best);

                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }
}
