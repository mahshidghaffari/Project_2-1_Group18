package controller;
import java.util.ArrayList;



public class MoveTest{
    
    public static void main (String[]args){

        ChessBoard cb = new ChessBoard(true);
        Square[][] board = cb.getBoard();
        Queen queen = new Queen(true);
        Pawn pawn = new Pawn(true);
        King king = new King(false);
        Knight knight = new Knight(true);
        Bishop bishop = new Bishop(true);
        Rook rook = new Rook(false);
        board[4][2].placePiece(queen);
        board[1][2].placePiece(king);
        board[1][7].placePiece(pawn);
        board[0][0].placePiece(knight);
        board[3][0].placePiece(bishop);
        board[3][2].placePiece(rook);
        
        printBoard(cb);
        ArrayList<Square> queenLM = queen.getLegalMoves(cb);
        ArrayList<Square> kingLM = king.getLegalMoves(cb);  
        ArrayList<Square> knightLM = knight.getLegalMoves(cb);     
        ArrayList<Square> bishopLM = bishop.getLegalMoves(cb);  
        ArrayList<Square> rookLM = rook.getLegalMoves(cb);   
        ArrayList<Square> pawnLM = pawn.getLegalMoves(cb);
        
        // System.out.println("Queen Legal Moves \n");
        // for(int i = 0; i<queenLM.size() ; i++){
        //     System.out.println(queenLM.get(i));
        // }
        
        // System.out.println("King Legal Moves \n");
        // for(int i = 0; i<kingLM.size() ; i++){
        //     System.out.println(kingLM.get(i));
        // }

        // System.out.println("Knight Legal Moves \n");
        // for(int i = 0; i<knightLM.size() ; i++){
        //     System.out.println(knightLM.get(i));
        // }

        // System.out.println("Bishop Legal Moves \n");
        // for(int i = 0; i<bishopLM.size() ; i++){
        //     System.out.println(bishopLM.get(i));
        // }
        
        System.out.println("Rook Legal Moves \n");
        for(int i = 0; i<pawnLM.size() ; i++){
            System.out.println(pawnLM.get(i));
        }
        
         pawn.move(board[0][7], cb, pawnLM); 
        // knight.move(knightLM.get(0), cb);
        // printBoard(cb);
        
    }

    public static void printBoard(ChessBoard cb){
        
        for(int i = 0; i<cb.getBoard().length ; i++){
            for(int y = 0; y<cb.getBoard().length ; y++){
                if(cb.getBoard()[i][y].isTakenSquare()) {
                    System.out.print("  " + cb.getBoard()[i][y].getPieceOnSq().pieceName + "  ");
                }
                else { System.out.print("  empty  ");}
            }
            System.out.println(" ");
        }
    }
}