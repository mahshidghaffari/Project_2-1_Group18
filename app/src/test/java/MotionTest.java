
import java.util.ArrayList;

import controller.ChessBoard;
import controller.King;
import controller.Rook;
import controller.Square;

public class MotionTest {

/*In order to test your piece do the following:
    1. Create a chess board (cb in line 49)

    2. Get your piece based on its original location, 
    eg: Bishop myBishp = (Bishop) cb.getOccupingPiece(0, 2); eg : Knight myKnight = (Knight) cb.getgetOccupingPiece(7, 1)
    
    3. Place your piece in a sqaure which is good for testing. eg. for a king/queen/bishop/rook it would
    not make sense to test in their original positions because they cant move anywhere as they are blocked. Therefore,
    start them off in an empty square in the middle of the board (eg [3][4] or [4][4])and go from there. To do so, use the
    placePiece() method inside from square class as follows:
    cb.getBoard()[4][4].placePiece(myKing);
    
    4. create the ArrayList of legalMoves from the getlegalmoves() method in your piece's class. 
    
    5. print moves (lines 54 55 here).
    
    6. remember that the board looks like this which may be confusing:
       

    LOOKS LIKE THIS:

             0  1  2  3  4  5  6  7
          0 [ ][ ][ ][ ][ ][ ][ ][ ]   Black        
    Y     1 [ ][ ][ ][ ][ ][ ][ ][ ]   Black
          2 [ ][ ][ ][ ][ ][ ][ ][ ]
    a     3 [ ][ ][ ][ ][ ][ ][ ][ ]
    x     4 [ ][ ][ ][ ][ ][ ][ ][ ]
    i     5 [ ][ ][ ][ ][ ][ ][ ][ ]
    s     6 [ ][ ][ ][ ][ ][ ][ ][ ]   White
          7 [ ][ ][ ][ ][ ][ ][ ][ ]   White

                   X axis
                   


    NOT LIKE THIS :
            
        7 [ ][ ][ ][ ][ ][ ][ ][ ]  Black
        6 [ ][ ][ ][ ][ ][ ][ ][ ]  Black
        5 [ ][ ][ ][ ][ ][ ][ ][ ]
        4 [ ][ ][ ][ ][ ][ ][ ][ ]
        3 [ ][ ][ ][ ][ ][ ][ ][ ]
        2 [ ][ ][ ][ ][ ][ ][ ][ ]
        1 [ ][ ][ ][ ][ ][ ][ ][ ]  WHite
        0 [ ][ ][ ][ ][ ][ ][ ][ ]  White
           0  1  2  3  4  5  6  7


        So if you are say in board[4][4] and you want to move 
        left: [4][3] right:[4][5] up:[3][5]
     */ 

    public static void main (String[]args){

        ChessBoard cb = new ChessBoard();

        //Castling Test
        ChessBoard cbTest = new ChessBoard(true);
        Square[][] testBoard = cbTest.getBoard();
        King newBlackKing = new King(false);
        King newWhiteKing = new King(true);
        Rook newCloseRook = new Rook(true);
        Rook newFarRook = new Rook(true);
        Square sBK =  testBoard[0][4];
        Square sBRC = testBoard[0][7];
        Square sBRF = testBoard[0][0];
        Square sWK =  testBoard[7][4];
        Square sWRC = testBoard[7][7];
        Square sWRF = testBoard[7][0];
        sBK.placePiece(newBlackKing);
        sBRC.placePiece(newCloseRook);
        sBRF.placePiece(newFarRook);
        ArrayList<Square> legalMoves = newBlackKing.getLegalMoves(cbTest);
        
        for(int i = 0; i<legalMoves.size() ; i++){
            System.out.println(legalMoves.get(i));
        }
    }
}
    


