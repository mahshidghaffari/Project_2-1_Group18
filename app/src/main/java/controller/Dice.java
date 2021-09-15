package controller;
import java.util.Random;


public class Dice {
    private String [] diceName = {"Pawn","Rook","Knight","Bishop","Queen","King"};
    private Random rand = new Random();
    private int diceNumber = rand.nextInt(6);

    public int getNumberDice(){
        return diceNumber;
    }

    public String getRoleDice(){
        return diceName[diceNumber];
    }


}
