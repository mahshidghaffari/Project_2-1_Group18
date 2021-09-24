package controller;
import java.util.Random;


public class Dice {
    private String [] diceName = {"King","Rook","Knight","Bishop","Queen","King"};
    private Random rand = new Random();
    private int diceNumber;

    public int getNumberDice(){
        return diceNumber;
    }

    public String getRoleDice(){
        return diceName[diceNumber];
    }

    public void randomize(){
        diceNumber=  rand.nextInt(1);
    }

}
