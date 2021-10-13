package controller;
import java.util.Random;


/**

*	Dice class generate random number between 1 to 6 
* @return      a string or number  
*/

public class  Dice {
    private String [] diceName = {"Pawn","Knight","Bishop","Rook","Queen","King"};
    private Random rand = new Random();
    private int diceNumber;
    
    
    /**
    * @return an integer number between 1 to 6  
    */
    
    public int getNumberDice(){
        return diceNumber;
    }
    
    /**
     * @return String , name of the each piece name which it's equivalents to its number
     */

    public String getRoleDice(){
        return diceName[diceNumber];
    }

    public void randomize(){
        diceNumber=  rand.nextInt(6);
    }
}