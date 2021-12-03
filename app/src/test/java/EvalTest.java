import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import controller.*;

public class EvalTest{
    double[][] grid = {
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0}, 
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
        {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
    };
    double[][] kingGrid = {
        {1.0, 1.0, 1.0, 1.0, 1.0},
        {1.0, 1.0, 1.0, 1.0, 1.0},
        {1.0, 1.0, 1.0, 1.0, 1.0},
        {1.0, 1.0, 1.0, 1.0, 1.0},
        {1.0, 1.0, 1.0, 1.0, 1.0},
    };
    @Test public void testMaterialEvalPawn(){
        ChessBoard cb = new ChessBoard(true);
        // Currently, pawn = 10, knight = 30, bishop = 30, rook = 50, queen = 90, king = 900
        Pawn w = new Pawn(true);
        cb.addLivePiece(w);
        cb.getBoard()[0][0].placePiece(w);
        Evaluation eval = new Evaluation(cb);
        assertEquals(10.0, eval.getMaterialEval());
    }
    @Test public void testMaterialEvalKnight(){
        ChessBoard cb = new ChessBoard(true);
        Knight w = new Knight(true);
        cb.addLivePiece(w);
        cb.getBoard()[0][0].placePiece(w);
        Evaluation eval = new Evaluation(cb);
        assertEquals(30.0, eval.getMaterialEval());
    }
    @Test public void testMaterialEvalBishop(){
        ChessBoard cb = new ChessBoard(true);
        Bishop w = new Bishop(true);
        cb.addLivePiece(w);
        cb.getBoard()[0][0].placePiece(w);
        Evaluation eval = new Evaluation(cb);
        assertEquals(30.0, eval.getMaterialEval());
    }
    @Test public void testMaterialEvalRook(){
        ChessBoard cb = new ChessBoard(true);
        Rook w = new Rook(true);
        cb.addLivePiece(w);
        cb.getBoard()[0][0].placePiece(w);
        Evaluation eval = new Evaluation(cb);
        assertEquals(50.0, eval.getMaterialEval());
    }
    @Test public void testMaterialEvalQueen(){
        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);
        cb.addLivePiece(w);
        cb.getBoard()[0][0].placePiece(w);
        Evaluation eval = new Evaluation(cb);
        assertEquals(90.0, eval.getMaterialEval());
    }
    @Test public void testMaterialEvalKing(){
        ChessBoard cb = new ChessBoard(true);
        King w = new King(true);
        cb.addLivePiece(w);
        cb.getBoard()[0][0].placePiece(w);
        Evaluation eval = new Evaluation(cb);
        assertEquals(10000.0, eval.getMaterialEval());
    }
    

    @Test public void testCenterControlEvalPawn(){
        ChessBoard cb = new ChessBoard(true);
        Pawn w = new Pawn(true);
        Pawn b = new Pawn(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.getBoard()[2][2].placePiece(w);
        cb.getBoard()[5][5].placePiece(b);
        Evaluation eval = new Evaluation(cb);
        eval.setSquareEval(grid);
        assertEquals(0.0, eval.getCenterControlEval());
    }
    @Test public void testCenterControlKnight(){
        ChessBoard cb = new ChessBoard(true);
        Knight w = new Knight(true);
        Knight b = new Knight(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.getBoard()[0][0].placePiece(w);
        cb.getBoard()[7][7].placePiece(b);
        Evaluation eval = new Evaluation(cb);
        eval.setSquareEval(grid);
        assertEquals(0.0, eval.getCenterControlEval());
    }
    @Test public void testCenterControlBishop(){
        ChessBoard cb = new ChessBoard(true);
        Bishop w = new Bishop(true);
        Bishop b = new Bishop(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.getBoard()[0][0].placePiece(w);
        cb.getBoard()[7][7].placePiece(b);
        Evaluation eval = new Evaluation(cb);
        eval.setSquareEval(grid);
        assertEquals(0.0, eval.getCenterControlEval());
    }
    @Test public void testCenterControlRook(){
        ChessBoard cb = new ChessBoard(true);
        Rook w = new Rook(true);
        Rook b = new Rook(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.getBoard()[0][0].placePiece(w);
        cb.getBoard()[7][7].placePiece(b);
        Evaluation eval = new Evaluation(cb);
        eval.setSquareEval(grid);
        assertEquals(0.0, eval.getCenterControlEval());
    }
    @Test public void testCenterControlQueen(){
        ChessBoard cb = new ChessBoard(true);
        Queen w = new Queen(true);
        Queen b = new Queen(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.getBoard()[0][0].placePiece(w);
        cb.getBoard()[7][7].placePiece(b);
        Evaluation eval = new Evaluation(cb);
        eval.setSquareEval(grid);
        assertEquals(0.0, eval.getCenterControlEval());
    }
    @Test public void testCenterControlKing(){
        ChessBoard cb = new ChessBoard(true);
        King w = new King(true);
        King b = new King(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.getBoard()[0][0].placePiece(w);
        cb.getBoard()[7][7].placePiece(b);
        Evaluation eval = new Evaluation(cb);
        eval.setSquareEval(grid);
        assertEquals(0.0, eval.getCenterControlEval());
    }
    @Test public void testKingSafetyEvalEqual(){
        ChessBoard cb = new ChessBoard(true);
        King w = new King(true);
        King b = new King(true);
        Queen wq = new Queen(true);
        Queen bq = new Queen(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.addLivePiece(wq);
        cb.addLivePiece(bq);
        cb.getBoard()[2][2].placePiece(w);
        cb.getBoard()[5][5].placePiece(b);
        cb.getBoard()[0][0].placePiece(bq);
        cb.getBoard()[7][7].placePiece(wq);
        Evaluation eval = new Evaluation(cb);
        eval.setSquareEval(grid);
        assertEquals(0.0, eval.getKingSafetyEval());
    }
    @Test public void testKingSafetyEvalWhite(){
        ChessBoard cb = new ChessBoard(true);
        King w = new King(true);
        King b = new King(false);
        //Queen wq = new Queen(true);
        Queen bq = new Queen(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        //cb.addLivePiece(wq);
        cb.addLivePiece(bq);
        cb.getBoard()[2][2].placePiece(w);
        cb.getBoard()[5][5].placePiece(b);
        cb.getBoard()[0][0].placePiece(bq);
        //cb.getBoard()[7][7].placePiece(wq);
        Evaluation eval = new Evaluation(cb);
        eval.setKingDangerWeight(grid);
        assertEquals(10.0, eval.getKingSafetyEval());
    }

    @Test public void testKingSafetyEvalBlack(){
        ChessBoard cb = new ChessBoard(true);
        King w = new King(true);
        King b = new King(false);
        Queen wq = new Queen(true);
        //Queen bq = new Queen(false);
        cb.addLivePiece(w);
        cb.addLivePiece(b);
        cb.addLivePiece(wq);
        //cb.addLivePiece(bq);
        cb.getBoard()[2][2].placePiece(w);
        cb.getBoard()[5][5].placePiece(b);
        //cb.getBoard()[0][0].placePiece(bq);
        cb.getBoard()[7][7].placePiece(wq);
        Evaluation eval = new Evaluation(cb);
        eval.setKingDangerWeight(grid);
        assertEquals(-10.0, eval.getKingSafetyEval());
    }

}