import javax.swing.JFrame;

import controller.*;

public class Base_vs_Expecti {
    public static void main(String[] args) {
        new Base_vs_Expecti();
    }

    /**
     * Experimental Class that checks the number of wins between the Base-Line agent 
     * and the ExpectiMax agent. 
     *  Assign max_depth as the maximum depth to be experimented.
     *  Assign runs to the maximum amount of games played at each depth.
     */

    public Base_vs_Expecti(){
        int runs = 1000;
        int max_depth=3;
        //for each of the depths run 100 games
        for(int depth = 1 ; depth <= max_depth ; depth++){
            System.out.println("In depth "+ depth+" ");
            experiment(runs, depth);
        }
    }
    public void experiment(int runs, int depth){
        int eMax = 0;
        int base = 0;
        double avgMoves = 0;
        for(int i=0;i<runs;i++){
            JFrame f = new JFrame("Dice Chess");
            Game game = new Game(f, depth);
            game.setwBaseLineActive(true);
            //game.getBaseLineAgent().setSimple(simpleAgent);
            game.setbEpectiMaxActive(true);
            game.play();
            String color="";
            //checking which team won the game
            for(Piece p: game.getChessBoard().getLivePieces()){
                if(p.getPieceName().equals("King")){
                    color = p.getColorName();
                    break;
                }
            }
            if(color.equals("Black")){
                eMax++;
                avgMoves+=game.getMoveCounter()/2;
            }
            else if(color.equals("White")){
                base++;
            }
        }
        System.out.println("- Base Player won "+ base + " games out of "+ runs);
        System.out.println("- ExpectiMax Player won "+ eMax + " games out of "+ runs);
        System.out.println("- In total the  expectiMax agent won "+ eMax*100/runs + "% of the games");
        System.out.println("- On average, it took expectiMax " + avgMoves/runs+ " moves to beat its opponent"+"\n\n");
        
    }
}
