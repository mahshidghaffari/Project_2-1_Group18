import javax.swing.JFrame;

import controller.*;

public class Base_vs_Expecti {
    public static void main(String[] args) {
        //for(int i=0; i<5; i++){
            new Base_vs_Expecti();
        //}
        
    }

    /**
     * Experimental Class that checks the number of wins between the Base-Line agent 
     * and the ExpectiMax agent. 
     *  Assign max_depth as the maximum depth to be experimented.
     *  Assign runs to the maximum amount of games played at each depth.
     */

    public Base_vs_Expecti(){
        int runs = 10;
        int max_depth=5;
        //for each of the depths run 100 games
        //for(int depth = 1 ; depth <= max_depth ; depth++){
            System.out.println("In depth "+ max_depth+" ");
            experiment(runs, max_depth);
        //}
    }
    public void experiment(int runs, int depth){
        int[] eMax = {0,0}; //Array to count number of expecti wins : arr[0] = wins as white, arr[1] = wins as black
        int[] base = {0,0}; 
        double avgMoves = 0;
        boolean isWhite = true; //Whether expecti agent isWhite 
        double[] gameTimes = new double[runs];
            
        
        
        for(int i=0;i<runs;i++){
            JFrame f = new JFrame("Dice Chess");
            Game game = new Game(f, depth, isWhite);
            System.out.print("Game "+i + " : ");
            if(isWhite){
                game.setwExpectiMaxActive(true); //Expecti will be white on iterations 0,2,4
                game.setbBaseLineActive(true);
                
                //System.out.println("Expecti : white player");
                //System.out.println("BaseLine : black player");
            } else{
                game.setbEpectiMaxActive(true);
                game.setwBaseLineActive(true);
                //System.out.println("Expecti : black player");
                //System.out.println("BaseLine : white player");
            }

            double startTime = System.nanoTime();
            game.play();
            //game.getChessBoard().printBoard();
            double endTime = System.nanoTime();
            gameTimes[i] = (endTime - startTime)/1000000; //milliseconds
            
            
            String color="";
            int kingCount = 0;
            //checking which team won the game
            for(Piece p: game.getChessBoard().getLivePieces()){
                if(p.getPieceName().equals("King")){
                    color = p.getColorName();
                    kingCount++;
                }
            }
            System.out.println(kingCount);
            if(color.equals("White")){
                if(i%2 == 0){
                    eMax[0]++;
                    System.out.println("Expecti won!");
                } else{
                    base[0]++;
                    System.out.println("Base won!");
                }
                avgMoves+=game.getMoveCounter()/2;
                //eMax[0]++;
            } else
            if(color.equals("Black")){
                if(i%2 == 0){
                    base[1]++;
                    System.out.println("Base won!");
                } else{
                    eMax[1]++;
                    System.out.println("Expecti won!");
                }
                avgMoves+=game.getMoveCounter()/2; 
                //base[1] ++;              
            }
             
            //System.out.println("eMax array : " + eMax[0] + ", " + eMax[1]);
            //System.out.println("base array : " + base[0] + ", " + base[1]);
            isWhite = !isWhite;
            System.out.println(isWhite);
        }
        double sum = 0;
        for(double x:gameTimes){
            sum += x;
        }
        System.out.println("- Games played :" + runs);
        System.out.println("- ExpectiMax Player won "+ (eMax[0]+eMax[1]));

        System.out.println("- Expecti won " + eMax[0] + " as white, " + eMax[1] + " as black");
        System.out.println("- Expecti win rate : "+ (double)(eMax[0]+eMax[1])/runs);
        System.out.println("- Expecti took " + avgMoves/runs+ " moves to beat its opponent");
        System.out.println("- Game time average = " + sum/runs + " ms" + "\n\n");
        
    }



    // public void experiment(int runs, int depth){
    //     int eMax = 0; //Array to count number of expecti wins : arr[0] = wins as white, arr[1] = wins as black
    //     int base = 0; 
    //     double avgMoves = 0;
    //     boolean isWhite = true; //Whether expecti agent isWhite 
    //     double[] gameTimes = new double[runs];
            
        
        
    //     for(int i=0;i<runs;i++){
    //         JFrame f = new JFrame("Dice Chess");
    //         Game game = new Game(f, depth, isWhite);
    //         //System.out.print("Game "+i + " : ");
    //         //if(isWhite){
    //             game.setbEpectiMaxActive(true); //Expecti will be white on iterations 0,2,4
    //             game.setwBaseLineActive(true);
    //             //System.out.println("Expecti : white player");
    //             //System.out.println("BaseLine : black player");
    //         //} else{
    //             //game.setbEpectiMaxActive(true);
    //             //game.setwBaseLineActive(true);
    //             //System.out.println("Expecti : black player");
    //             //System.out.println("BaseLine : white player");
    //         //}

    //         double startTime = System.nanoTime();
    //         game.play();
    //         game.getChessBoard().printBoard();
    //         double endTime = System.nanoTime();
    //         gameTimes[i] = (endTime - startTime)/1000000; //milliseconds
            
            
    //         String color="";
    //         //checking which team won the game
    //         for(Piece p: game.getChessBoard().getLivePieces()){
    //             if(p.getPieceName().equals("King")){
    //                 color = p.getColorName();
    //                 break;
    //             }
    //         }
    //         if(color.equals("Black")){
    //             base++;
    //             // if(i%2 == 0){
    //             //     base++;
    //             //     System.out.println("Base won!");
    //             // } else{
    //             //     eMax++;
    //             //     System.out.println("Expecti won!");
    //             // }
    //             avgMoves+=game.getMoveCounter()/2;
    //         }
    //         else if(color.equals("White")){
    //             eMax++;
    //             // if(i%2 == 0){
    //             //     eMax++;
    //             //     System.out.println("Expecti won!");
    //             // } else{
    //             //     base++;
    //             //     System.out.println("Base won!");
    //             // }
    //         }
    //         //System.out.println("eMax array : " + eMax[0] + ", " + eMax[1]);
    //         //System.out.println("base array : " + base[0] + ", " + base[1]);
    //         isWhite = !isWhite;
    //     }
    //     double sum = 0;
    //     for(double x:gameTimes){
    //         sum += x;
    //     }
    //     System.out.println("- Games played :" + runs);
    //     System.out.println("- ExpectiMax Player won "+ (eMax));

    //     //System.out.println("- Expecti games won as white = " + eMax[0]);
    //     //System.out.println("- Expecti win rate : "+ (double)(eMax[0]+eMax[1])/runs);
    //     System.out.println("- Expecti took " + avgMoves/runs+ " moves to beat its opponent");
    //     System.out.println("- Game time average = " + sum/runs + " ms" + "\n\n");
        
    // }
}
