package controller;

import java.util.ArrayList;

public class Game_States {

    private ChessBoard originalCB;
    private Piece movingPiece;
    private ArrayList<ChessBoard> allCenerios;

    public Game_States(ChessBoard cb, Piece movingPiece){
        originalCB = cb;
        this.movingPiece = movingPiece;
        allCenerios= new ArrayList<ChessBoard>();
    }

    

    public double getBestMove(boolean isWhite){
        double bestWhite = 0;
        double bestBlack = 0;
        Square bestWhiteSquare;
        Square bestBlackSquare;
        for(ChessBoard cb: allCenerios){
            if(bestWhite < cb.getBoardValue()){
                bestWhite = cb.getBoardValue();
                bestWhiteSquare = movingPiece.getCurrentPosition();
            }
            if(bestBlack> cb.getBoardValue()){
                bestBlack = cb.getBoardValue();
            }
        }
        if(isWhite) return bestWhite;
        else return bestBlack;
    }

    public void runCenerios(){
        ChessBoard ogCopy = new ChessBoard(true);
        ogCopy.copyCB(originalCB);
        ArrayList<Square> legalMoves = movingPiece.getLegalMoves(ogCopy);

        for(Square legalSquare : legalMoves ){
            ChessBoard scenario = new ChessBoard(true);
            scenario.copyCB(originalCB);
            Piece copy = movingPiece;
            Square current = scenario.getBoard()[movingPiece.getCurrentPosition().getYPos()][movingPiece.getCurrentPosition().getXPos()];
            Square target = scenario.getBoard()[legalSquare.getYPos()][legalSquare.getXPos()];
            current.removePiece(current.getPieceOnSq());
            
            if(movingPiece.getPieceName().equals("Bishop")) {
                Bishop bC = new Bishop(movingPiece.isWhite());
                bC.copyPiece(copy);
                bC.move(target, scenario , legalMoves);
            }else if(movingPiece.getPieceName().equals("Knight")){
                Knight bC = new Knight(movingPiece.isWhite());
                bC.copyPiece(copy);
                bC.move(target, scenario , legalMoves);
            }else if(movingPiece.getPieceName().equals("Pawn")){
                Pawn bC = new Pawn(movingPiece.isWhite());
                bC.copyPiece(copy);
                bC.move(target, scenario , legalMoves);
            }else if(movingPiece.getPieceName().equals("Queen")){
                Queen bC = new Queen(movingPiece.isWhite());
                bC.copyPiece(copy);
                bC.move(target, scenario , legalMoves);
            }else if(movingPiece.getPieceName().equals("King")){
                King bC = new King(movingPiece.isWhite());
                bC.copyPiece(copy);
                bC.move(target, scenario , legalMoves);
            }   

            //game.newTurn();
            allCenerios.add(scenario);
        }
    }

    public void printScenarios(){
        for(ChessBoard cb: allCenerios){
            cb.printBoard();
            System.out.println("\n");
        }
    }

    
    
}
