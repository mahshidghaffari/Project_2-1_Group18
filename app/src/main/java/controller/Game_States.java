package controller;

import java.util.ArrayList;

public class Game_States {
    /*Class Game_States : basically this class takes in a chessboard, a pieceName and a whiteTurn boolean.
                it creates new chessboards for every future state of the base board
            AKA : creates the children of a node that has base chessboard as state

        how to use : create a game_state object, with constructor that has params :
        - chessboard, the current state of the board
        - pieceName : the name of the piece allowed by the dice. We are gonna return every legal move for every piece of this type
        - whiteTurn : boolean that will determine which pieces to select (e.g. Pawn : don't take all pawns, but only take white's pawns!!)
                    
    */
    private String pieceName;
    private ChessBoard originalCB;
    private ArrayList<ChessBoard> allScenarios;
    private ArrayList<ChessBoard> pieceScenarios;
    private boolean whiteTurn;

    public Game_States(ChessBoard cb, String pieceName, boolean whiteTurn){
        this.whiteTurn = whiteTurn;
        this.pieceName = pieceName;
        this.originalCB = cb;
        allScenarios= new ArrayList<ChessBoard>();
        pieceScenarios = new ArrayList<ChessBoard>();
    }
    public ArrayList<ChessBoard> getScenarios(){
        
        for(Piece p : originalCB.getLivePieces()){
            if(p.isWhite() == whiteTurn){
                if(p.getPieceName().equals(pieceName)){
                    ArrayList<ChessBoard> curr = runScenarios(p);
                    for (ChessBoard c : curr){
                        allScenarios.add(c);
                    }
                }
            }
        }
        return allScenarios;
    }


    
    /*
    public double getBestMove(boolean isWhite){
        double bestWhite = 0;
        double bestBlack = 0;
        Square bestWhiteSquare;
        Square bestBlackSquare;
        for(ChessBoard cb: allScenarios){
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
    */

    public ArrayList<ChessBoard> runScenarios(Piece movingPiece){
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
            pieceScenarios.add(scenario);
        }
        return this.pieceScenarios;
    }

    public void printScenarios(){
        for(ChessBoard cb: allScenarios){
            cb.printBoard();
            System.out.println("\n");
        }
    }

    
    
}
