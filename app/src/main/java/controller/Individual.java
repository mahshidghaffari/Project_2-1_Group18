package controller;

public class Individual {
    public double pawnWeight;
    public double knightWeight;
    public double bishopWeight;
    public double rookWeight;
    public double queenWeight;
    //No king weigth : it is always going to be worth 100000 because it determines who wins the game

    public double pawnRange = 5.0; //Pawn weight will be between 5 and 15
    public double knightRange = 10.0; //Knight weight will be between 20 and 40
    public double bishopRange = 10.0; //Bishop weight will be between 20 and 40
    public double rookRange = 15.0; //Rook weight will be between 35 and 65
    public double queenRange = 30.0; //Queen weight will be between 60 and 120

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
    public Individual(double pawnWeight, double knightWeight, double bishopWeight, double rookWeight, double queenWeight){
        this.pawnWeight = pawnWeight;
        this.knightWeight = knightWeight;
        this.bishopWeight = bishopWeight;
        this.rookWeight = rookWeight;
        this.queenWeight = queenWeight;
    }
    /*
    Method getFitness : returns a double that symbolizes the fitness of this individual
    the fitness will be asserted by the performance of the expectimax with evalFunction with individual's weights.

    Descrition of task :
    - Create base Agent(with Evaluation function with weights {10,30,30,50,90,10000}), that uses expectimax to depth X

        (Here we still have to determine X, the depth we will use. 
        Many individuals will play many games, so it will be computationally expensive
        Therefore we'll test our expectiPruning correctly and decide then what depth we use
        )

    - Create player Agent (with Evaluation funciton with the new random weights).

    - Play Y number of games between each agent 
        (Y has to be high enough to draw out statistical noise,
        but not too high so we can compute large enough populations)

    - Get fitness from results of the games (e.g. fitness could just be ratio of win/loss, or also just number of wins)

    */
    public double getFitness(){
        //CODE HERE
        return 0.0;
    }


    public double[] getWeights(){
        double[] arr = {pawnWeight, knightWeight, bishopWeight, rookWeight, queenWeight};
        return arr;
    }
    public void setWeights(double[] weights){
        this.pawnWeight = weights[0];
        this.knightWeight = weights[1];
        this.bishopWeight = weights[2];
        this.rookWeight = weights[3];
        this.queenWeight = weights[4];

    }
    public String toString(){
        String r = "Pawn = "+ pawnWeight + ", Knight = " + knightWeight + ", Bishop = " + bishopWeight + ", Rook = " + rookWeight + ", Queen = "+queenWeight;

        return r;
    }
}
