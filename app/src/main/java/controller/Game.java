package controller;
import java.util.Random;


public class Game{

    public static void main(String[] args) {
        new Game();
    }
    
    public Game(){
        ChessBoard cb  = new ChessBoard();
        BlackPlayer bPlayer = new BlackPlayer(cb);
        WhitePlayer wPlayer = new WhitePlayer(cb);
        Random rnd = new Random();
        int i=0;
        wPlayer.setIsMyTurn(true);
        while(i<20){
            i++;
            System.out.println(i);
            String rollW = wPlayer.rollDice();
            String rollB = bPlayer.rollDice();
            if(wPlayer.getIsMyTurn() && wPlayer.canMove(rollW)){
                    wPlayer.setIsMyTurn(true);
                    int rndIndexPiece = rnd.nextInt(wPlayer.getMovablePieces(rollW).size()); //randomize which piece to move
                    Piece p = wPlayer.getMovablePieces(rollW).get(rndIndexPiece);
                    int rndIndexMove = rnd.nextInt(p.getLegalMoves(cb).size()); //randomize which move to choose from randomly chosen piece
                    Square move = p.getLegalMoves(cb).get(rndIndexMove);
                    p.move(move, cb, p.getLegalMoves(cb));
                    wPlayer.flipTurns(bPlayer);
                    cb.printBoard();
                    System.out.println();
        
            }
            else if(bPlayer.getIsMyTurn() && bPlayer.canMove(rollB)){
                    bPlayer.setIsMyTurn(true);
                    int rndIndexPiece = rnd.nextInt(bPlayer.getMovablePieces(rollB).size());
                    Piece p = bPlayer.getMovablePieces(rollB).get(rndIndexPiece);
                    int rndIndexMove = rnd.nextInt(p.getLegalMoves(cb).size());
                    Square move = p.getLegalMoves(cb).get(rndIndexMove);
                    p.move(move, cb, p.getLegalMoves(cb));
                    bPlayer.flipTurns(wPlayer);
                    cb.printBoard();
                    System.out.println();
        
                }            
            }
        }
    }

