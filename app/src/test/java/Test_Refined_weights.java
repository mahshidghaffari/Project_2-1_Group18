import javax.swing.JFrame;
import controller.*;
public class Test_Refined_weights {

    /**
     * In this class we test the refined weights obtained from the gentic algorithm 
     * against the original weights that were  {10.0, 30.0, 30.0, 50.0, 90.0}
     * Thus we can conclude which agent performs better.
     */
    
    boolean checked_new_Weight=false;
    boolean checked_og_Weights=false;
    double[] newSquareWeights = {0.8799210485005461, 2.9349973480987495, 4.322591169108735, 5.493702210305353};
    double[] newWeights = {9.720980133600229,  29.542732308055086, 30.68359217160083, 55.710377964516184, 68.72427084012212};
    double[] ogWeights = {10.0, 30.0, 30.0, 50.0, 90.0};
    public static void main(String[] args) {
        new Test_Refined_weights();
    }
    public Test_Refined_weights(){
        experiment(500, 2);
    }
    public void experiment(int runs, int depth){
        while(!checked_new_Weight ||!checked_og_Weights){    
            if(!checked_new_Weight){
                System.out.println("~~~~~~~~~~~~~~~~~~~~ CHECKING THE NEW WEIGHTS ~~~~~~~~~~~~~~~~~~~~");
                int[] eMax = {0,0}; //Array to count number of expecti wins : arr[0] = wins as white, arr[1] = wins as black
                int[] base = {0,0}; 
                double avgMoves = 0;
                boolean isWhite = true; //Whether expecti agent isWhite 
                double[] gameTimes = new double[runs];
                
                //games that the newWeights played vs clever
                for(int i=0;i<runs;i++){
                    JFrame f = new JFrame("Dice Chess");
                    Game game = new Game(f, depth, isWhite);
                    game.setTest_refined(true);
                    game.setTestNew(true);
                    game.setNewWeights(newSquareWeights);
                    if(i<runs/2){
                        game.setwExpectiMaxActive(true);
                        game.setbBaseLineActive(true);
                    }
                    else{
                        game.setwBaseLineActive(true);
                        game.setbEpectiMaxActive(true);
                    }
                    //System.out.print("Game "+i + " : ");
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
                    if(color.equals("White")){
                        if(i<runs/2){
                            eMax[0]++;
                            //System.out.println("Expecti won as white!");
                            //System.out.print(".");
                        } else{
                            base[0]++;
                            //System.out.println("Base won as white!");
                            //System.out.print(".");
                        }
                        avgMoves+=game.getMoveCounter()/2;
                        //eMax[0]++;
                    } else if(color.equals("Black")){
                        if(i>runs/2){
                            eMax[1]++;
                            //System.out.println("Expecti won as black!");
                            //System.out.print(".");
                        } else{
                            base[1]++;
                            //System.out.println("Base won as black!");
                            //System.out.println(".");
                        }
                        avgMoves+=game.getMoveCounter()/2; 
                        //base[1] ++;              
                    }
                    
                    // System.out.println("eMax array : " + eMax[0] + ", " + eMax[1]);
                    // System.out.println("base array : " + base[0] + ", " + base[1]);
                }
                double sum = 0;
                for(double x:gameTimes){
                    sum += x;
                }
                System.out.println("");
                System.out.println("- Games played :" + runs);
                System.out.println("- ExpectiMax Player won "+ (eMax[0]+eMax[1]));

                System.out.println("- Expecti won " + eMax[0] + " as white, " + eMax[1] + " as black");
                System.out.println("- Expecti win rate : "+ (double)(eMax[0]+eMax[1])/runs);
                System.out.println("- Expecti took " + avgMoves/runs+ " moves to beat its opponent");
                System.out.println("- Game time average = " + sum/runs + " ms" + "\n\n");   
                checked_new_Weight=true;
            }
            else{
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~ CHECKING THE ORIGINAL WEIGHTS ~~~~~~~~~~~~~~~~~~~~");
                int[] eMax = {0,0}; //Array to count number of expecti wins : arr[0] = wins as white, arr[1] = wins as black
                int[] base = {0,0}; 
                double avgMoves = 0;
                boolean isWhite = true; //Whether expecti agent isWhite 
                double[] gameTimes = new double[runs];
                
                //games that the newWeights played vs clever
                for(int i=0;i<runs;i++){
                    JFrame f = new JFrame("Dice Chess");
                    Game game = new Game(f, depth, isWhite);
                    game.setTest_refined(true);
                    game.setTestNew(true);

                    game.setNewWeights(ogWeights);
                    if(i<runs/2){
                        game.setwExpectiMaxActive(true);
                        game.setbBaseLineActive(true);
                    }
                    else{
                        game.setwBaseLineActive(true);
                        game.setbEpectiMaxActive(true);
                    }
                    //System.out.print("Game "+i + " : ");
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
                    if(color.equals("White")){
                        if(i<runs/2){
                            eMax[0]++;
                            //System.out.println("Expecti won as white!");
                            //System.out.println(".");

                        } else{
                            base[0]++;
                            //System.out.println("Base won as white!");
                            //System.out.println(".");

                        }
                        avgMoves+=game.getMoveCounter()/2;
                        //eMax[0]++;
                    } else if(color.equals("Black")){
                        if(i>runs/2){
                            eMax[1]++;
                            //System.out.println("Expecti won as black!");
                            //System.out.println(".");
                        } else{
                            base[1]++;
                            //System.out.println("Base won as black!");
                            //System.out.println(".");
                        }
                        avgMoves+=game.getMoveCounter()/2; 
                        //base[1] ++;              
                    }
                    
                    // System.out.println("eMax array : " + eMax[0] + ", " + eMax[1]);
                    // System.out.println("base array : " + base[0] + ", " + base[1]);
                }
                double sum = 0;
                for(double x:gameTimes){
                    sum += x;
                }
                System.out.println("");
                System.out.println("- Games played :" + runs);
                System.out.println("- ExpectiMax Player won "+ (eMax[0]+eMax[1]));

                System.out.println("- Expecti won " + eMax[0] + " as white, " + eMax[1] + " as black");
                System.out.println("- Expecti win rate : "+ (double)(eMax[0]+eMax[1])/runs);
                System.out.println("- Expecti took " + avgMoves/runs+ " moves to beat its opponent");
                System.out.println("- Game time average = " + sum/runs + " ms" + "\n\n");   
                checked_og_Weights=true;
            }
        }
    }
}
