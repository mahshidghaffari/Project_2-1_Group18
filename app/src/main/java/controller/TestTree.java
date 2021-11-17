// package controller;

// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;
// import java.util.stream.Collectors;

// public class TestTree {

//     public static void main(String[] args) {
//         ChessBoard cb = new ChessBoard();
//         Tree t = new Tree(4, cb, "Pawn", false);
//         t.generateTree();
//         t.calculateTree(t.getRoot().getChildren());
//         //System.out.println(t.get);
//         Piece best = t.getBestPiece();
//         Square bestMove = t.getBestSquare();

//         Node b = t.getBestBoard();
//         b.getBoard().printBoard();
//         System.out.println("/////////////////////////////////" + b.getValue());
// //
// //
// //        for (Node n : t.getRoot().getChildren()) {
// //            System.out.println(n.getValue());
// //            n.getBoard().printBoard();
// //        }

//         //System.out.println(t.root.getChildren().size());
// //        for (Node2 n : t.root.getChildren()) {
// //            System.out.println("---");
// //            System.out.println(n.getChildren().size());
// //            for (Node2 nn : n.getChildren()) {
// //                System.out.println(nn.getPiece());
// //                System.out.println(nn.getChildren().size());
// //                System.out.println(" ");
// //                for (Node2 nnn : nn.getChildren()) {
// //                    System.out.println(nnn.getChildren().size());
// //                    for (Node2 nnnn : nnn.getChildren()) {
// //                        System.out.println(nnnn.getPiece());
// //                        System.out.println(nnnn.getChildren().size());
// //                        System.out.println(" ");
// //                    }
// //                }
// //            }
// //        }
// //
// //        for (Node2 n : t.root.getChildren()) {
// //            System.out.println("---");
// //            System.out.println(n.getValue());
// //            for (Node2 nn : n.getChildren()) {
// ////                System.out.println(nn.getPiece());
// ////                System.out.println(nn.getValue());
// //                System.out.println(" ");
// //                for (Node2 nnn : nn.getChildren()) {
// //                    System.out.println(nnn.getValue());
// //                    for (Node2 nnnn : nnn.getChildren()) {
// ////                        System.out.println(nnnn.getPiece());
// ////                        System.out.println(nnnn.getValue());
// //                        System.out.println(" ");
// //                    }
// //                }
// //            }
// //        }


// //        ChessBoard cb = new ChessBoard();
// //        Tree t = new Tree(cb);
// //        t.generateTree(t.baseRoot, cb);
// //        System.out.println(t.baseRoot.getChildren().size());
// //        System.out.println("============");
// //        for (Node n : t.baseRoot.getChildren()) {
// //            System.out.println("---");
// //            System.out.println(n.getChildren().size());
// //            for (Node nn : n.getChildren()) {
// //                System.out.println(nn.getPiece());
// //                System.out.println(nn.getChildren().size());
// //            }
// //        }
//     }
// }


