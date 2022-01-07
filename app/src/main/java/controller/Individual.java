package controller;

import java.util.Random;

public class Individual {
    double pawnWeight;
    double knightWeight;
    double bishopWeight;
    double rookWeight;
    double queenWeight;
    //No king weigth : it is always going to be worth 100000 because it determines who wins the game

    double pawnRange = 5.0; //Pawn weight will be between 5 and 15
    double knightRange = 10.0; //Knight weight will be between 20 and 40
    double bishopRange = 10.0; //Bishop weight will be between 20 and 40
    double rookRange = 15.0; //Rook weight will be between 35 and 65
    double queenRange = 30.0; //Queen weight will be between 60 and 120

    double ogPawn = 10.0;
    double ogKnight = 30.0;
    double ogBishop = 30.0;
    double ogRook = 50.0;
    double ogQueen = 90.0;

    public Individual(){
        //The following formula is to obtain a random number between a min and a max range
        //Math.random() outputs a pseudorandom number between 0 and 1
        // The formula : ((Math.random() * (max - min)) + min) gives us a random in a range of max and min
        //Here max = ogPiece + pieceRange
        // and min = ogPiece - pieceRange
        this.pawnWeight = (Math.random() * ((ogPawn+pawnRange) - (ogPawn-pawnRange))) + (ogPawn-pawnRange);
        this.knightWeight = (Math.random() * ((ogKnight+knightRange) - (ogKnight-knightRange))) + (ogKnight-knightRange);
        this.bishopWeight = (Math.random() * ((ogBishop+bishopRange) - (ogBishop-bishopRange))) + (ogBishop-bishopRange);
        this.rookWeight = (Math.random() * ((ogRook+rookRange) - (ogRook-rookRange))) + (ogRook-rookRange);
        this.queenWeight = (Math.random() * ((ogQueen+queenRange) - (ogQueen-queenRange))) + (ogQueen-queenRange);
    }
    public double[] getWeights(){
        double[] arr = {pawnWeight, knightWeight, bishopWeight, rookWeight, queenWeight};
        return arr;
    }
    public String toString(){
        String r = "Pawn = "+ pawnWeight + ", Knight = " + knightWeight + ", Bishop = " + bishopWeight + ", Rook = " + rookWeight + ", Queen = "+queenWeight;

        return r;
    }

    public static void main(String[]args){
        int popSize = 100;
        for(int i=0; i<popSize; i++){
            Individual ind = new Individual();
            System.out.println(ind.toString());
        }
    }


}
