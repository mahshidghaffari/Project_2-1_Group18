package controller;
import java.util.ArrayList;
import java.util.Random;


/**

*	Dice class generate random number between 1 to 6 
* @return      a string or number  
*/

public class  Dice {
    private String [] diceName = {"Pawn","Knight","Bishop","Rook","Queen","King"};
    private Random rand = new Random();
    private int diceNumber;
    private String chosen;
    
    /**
    * @return an integer number between 1 to 6  
    */
    
    public int getNumberDice(){
        return diceNumber;
    }
    
    public String getChosen() { return chosen;}
    /**
     * @return String , name of the each piece name which it's equivalents to its number
     */

    public String getRoleDice(){
        return diceName[diceNumber];
    }

    public void randomize(){
        diceNumber=  rand.nextInt(6);
    }

    public void setChosen(String c){ chosen =c;}
    
    
    public String getRndPiece(ArrayList<String> movableP){
        ArrayList<String> movablePieces = movableP;
        int rndPiece = rand.nextInt(movableP.size()); 
        chosen  = movablePieces.get(rndPiece);
        return chosen;
    }
}