package controller;

import javax.swing.JFrame;

public class TestFitness {
    /** 
     * Class TestFitness is used to compute the fitness of Individuals in the Genetic Algortihm
     * It checks how many games an agent with double[] new weights wins against an agent with 
     * original weights.
     * The get fitness method returns games_won/ games_played
     **/
    double[] newWeights ;
    double games=50;
    int depth;
 
    
    public TestFitness(double[] newWeights, double games, int depth){
        this.newWeights=newWeights;
        this.games =games;
        this.depth = depth;
    }
    public double getFitness(){

        int og_Weights_Wins=0;
        int new_Weights_Wins=0;
        boolean isWhite=false;
        int i = 0;
        while(i<games){
            
            isWhite= !isWhite;
            JFrame f = new JFrame("Dice Chess");
            Game game = new Game(depth);
            game.setNewWeights(newWeights);
            game.setwExpectiMaxActive(true);
            game.setbEpectiMaxActive(true);
            game.setWhichOGWeights(isWhite);
            game.play();
            String color="";
        
            for(Piece p: game.getChessBoard().getLivePieces()){
                if(p.getPieceName().equals("King")){
                    color = p.getColorName();
                    break;
                }
            }
            if(color.equals("Black")){
                if(i%2==0){
                    new_Weights_Wins++;
                } else {
                    og_Weights_Wins++;
                }
            }
            else if(color.equals("White")){
                if(i%2==0){
                    og_Weights_Wins++;
                }else{ 
                    new_Weights_Wins++;}
            }
            i++;
        }
        //System.out.println("ogWeights won: " + og_Weights_Wins+" newWeights won: "+ new_Weights_Wins);
        System.out.print(".");
        return new_Weights_Wins/games;
    }

}
