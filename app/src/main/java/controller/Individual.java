package controller;

import java.util.Random;

public class Individual {
    //Variable for pieceWeights
    public double pawnWeight;
    public double knightWeight;
    public double bishopWeight;
    public double rookWeight;
    public double queenWeight;

    public double pawnRange = 5.0; 
    public double knightRange = 15.0;
    public double bishopRange = 15.0;
    public double rookRange = 25.0;
    public double queenRange =45.0;
    //Store the OG, arbitrary values
    public double ogPawn = 10.0;
    public double ogKnight = 30.0;
    public double ogBishop = 30.0;
    public double ogRook = 50.0;
    public double ogQueen = 90.0;



    //Variables for centercontrol weigths
    public double d1Weight;
    public double d2Weight;
    public double d3Weight;
    public double d4Weight;

    public double d1OgValue = 1.0;
    public double d2OgValue = 2.0;
    public double d3OgValue = 3.0;
    public double d4OgValue = 5.0;

    public double d1Range = 0.5;
    public double d2Range = 1.0;
    public double d3Range = 1.5;
    public double d4Range = 2.0;


    //Variables for king protection (ally threats around the king)
    public double kd1ProtectionWeight;
    public double kd2ProtectionWeight;
    public double kd3ProtectionWeight;
    
    public double kd1ProtectionRange = 0.2;
    public double kd2ProtectionRange = 0.2;
    public double kd3ProtectionRange = 0.2;

    public double kd1ProtectionOgValue = 1.0;
    public double kd2ProtectionOgValue = 3.0;
    public double kd3ProtectionOgValue = 5.0;
    

    //Variables for king danger (enemy threats around the king)
    public double kd1DangerWeight;
    public double kd2DangerWeight;
    public double kd3DangerWeight;
    
    public double kd1DangerRange = 0.2;
    public double kd2DangerRange = 0.2;
    public double kd3DangerRange = 0.2;

    public double kd1DangerOgValue = 1.0;
    public double kd2DangerOgValue = 3.0;
    public double kd3DangerOgValue = 5.0;


    public boolean pieceWeightsEval;
    public boolean centerControlEval;
    public boolean kingSafetyEval;
    public double fitness = -1;
    /**
     * Constructor Individual : constructs a new Individual with random weights depending on the config
     * @param pieceWeights : if true, constructor will init random weights for the pieces
     * @param squareWeights : if true constructor will init random weights for the squares
     * @param kingWeights : if true constructor will init random weights for the king safety squares
     */

    public Individual(boolean pieceWeights, boolean squareWeights, boolean kingWeights) {
        // The following formula is to obtain a random number between a min and a max range
        // Math.random() outputs a pseudorandom number between 0 and 1
        // The formula : ((Math.random() * (max - min)) + min) gives us a random in a range of max and min
        // Here max = ogPiece + pieceRange and min = ogPiece - pieceRange

        this.pieceWeightsEval = pieceWeights;
        this.centerControlEval = squareWeights;
        this.kingSafetyEval = kingWeights;
        if(pieceWeights){ 
            this.pawnWeight = (Math.random() * ((ogPawn + pawnRange) - (ogPawn - pawnRange))) + (ogPawn - pawnRange);
            this.knightWeight = (Math.random() * ((ogKnight + knightRange) - (ogKnight - knightRange)))+ (ogKnight - knightRange);
            this.bishopWeight = (Math.random() * ((ogBishop + bishopRange) - (ogBishop - bishopRange)))+ (ogBishop - bishopRange);
            this.rookWeight = (Math.random() * ((ogRook + rookRange) - (ogRook - rookRange))) + (ogRook - rookRange);
            this.queenWeight = (Math.random() * ((ogQueen + queenRange) - (ogQueen - queenRange))) + (ogQueen - queenRange);
        }
        if(squareWeights){
            this.d1Weight = (Math.random() * ((d1OgValue+d1Range) - (d1OgValue-d1Range))) + (d1OgValue-d1Range);
            this.d2Weight = (Math.random() * ((d2OgValue+d2Range) - (d1OgValue-d2Range))) + (d1OgValue-d2Range);
            this.d3Weight = (Math.random() * ((d3OgValue+d3Range) - (d1OgValue-d3Range))) + (d1OgValue-d3Range);
            this.d4Weight = (Math.random() * ((d4OgValue+d4Range) - (d1OgValue-d4Range))) + (d1OgValue-d4Range);
            if(d1Weight<0){ d1Weight = -d1Weight;}
            if(d2Weight<0){ d2Weight = -d2Weight;}
            if(d3Weight<0){ d3Weight = -d3Weight;}
            if(d4Weight<0){ d4Weight = -d4Weight;}
        }
        if(kingWeights){
            this.kd1ProtectionWeight = (Math.random() * ((kd1ProtectionOgValue + kd1ProtectionRange) - (kd1ProtectionOgValue - kd1ProtectionRange))) + (kd1ProtectionOgValue - kd1ProtectionRange);
            this.kd2ProtectionWeight = (Math.random() * ((kd1ProtectionOgValue + kd1ProtectionRange) - (kd1ProtectionOgValue - kd1ProtectionRange))) + (kd1ProtectionOgValue - kd1ProtectionRange);
            this.kd3ProtectionWeight = (Math.random() * ((kd1ProtectionOgValue + kd1ProtectionRange) - (kd1ProtectionOgValue - kd1ProtectionRange))) + (kd1ProtectionOgValue - kd1ProtectionRange);
            
            this.kd1DangerWeight = (Math.random() * ((kd1DangerOgValue + kd1DangerRange) - (kd1DangerOgValue - kd1DangerRange))) + (kd1DangerOgValue - kd1DangerRange);
            this.kd2DangerWeight = (Math.random() * ((kd1DangerOgValue + kd1DangerRange) - (kd1DangerOgValue - kd1DangerRange))) + (kd1DangerOgValue - kd1DangerRange);
            this.kd3DangerWeight = (Math.random() * ((kd1DangerOgValue + kd1DangerRange) - (kd1DangerOgValue - kd1DangerRange))) + (kd1DangerOgValue - kd1DangerRange);

        }
        
    }

    /**
     * Constructor for Individual : given piece weights
     * @param pawnWeight : given pawn weight
     * @param knightWeight: given knight weight
     * @param bishopWeight: given bishop weight
     * @param rookWeight: given rook weight
     * @param queenWeight: given queen weight
     */
    public Individual(double pawnWeight, double knightWeight, double bishopWeight, double rookWeight,
            double queenWeight) {
        this.pawnWeight = pawnWeight;
        this.knightWeight = knightWeight;
        this.bishopWeight = bishopWeight;
        this.rookWeight = rookWeight;
        this.queenWeight = queenWeight;
    }
    /**
     * Constructor Individual : constructor used for given square weigths
     * @param cc determines which constructor to use
     * @param weights given weights to use
     */
    public Individual(boolean cc, double[]weights){
        this.d1Weight = weights[0];
        this.d2Weight = weights[1];
        this.d3Weight = weights[2];
        this.d4Weight = weights[3];
    }
    /**
     * Method getSquareWeights : 
     * @return double[] for the weights of the squares
     */
    public double[] getSquareWeights(){
        double[] weights ={d1Weight, d2Weight, d3Weight, d4Weight};
        return weights;
    }
    /**
     * Empty Constructor, used when we later SET the weights
     * @param empty
     */
    public Individual(String empty) {
    }

    /*
     * Method getFitness : returns a double that symbolizes the fitness of this
     * individual
     * the fitness will be asserted by the performance of the expectimax with
     * evalFunction
     * with individual's weights.
     * 
     * Descrition of task :
     * - Create base Agent(with Evaluation function with weights
     * {10,30,30,50,90,10000}),
     * that uses expectimax to depth X.
     * 
     * (Here we still have to determine X, the depth we will use.
     * Many individuals will play many games, so it will be computationally
     * expensive
     * Therefore we'll test our expectiPruning correctly and decide then what depth
     * we use)
     * 
     * - Create player Agent (with Evaluation funciton with the new random weights).
     * 
     * - Play Y number of games between each agent
     * (Y has to be high enough to draw out statistical noise,
     * but not too high so we can compute large enough populations)
     * 
     * - Get fitness from results of the games (e.g. fitness could just be ratio of
     * win/loss,
     * or also just number of wins)
     * 
     */
    public double getFitness(double gamesToPlay, int depth) {
        if (fitness == -1) {
            TestFitness tf = null;
            //if(pieceWeightsEval){
                tf = new TestFitness(getCCWeights(), gamesToPlay, depth);
            //}
            // if(centerControlEval){
            //     tf = new TestFitness(getCCWeights(), gamesToPlay, depth);
            // }
            //if(kingSafetyEval){
                //tf = new TestFitness(getKSDWeights(), getKSPWeights(), gamesToPlay, depth);

            //}
            fitness = tf.getFitness();
        }
        return fitness;
    }
    /**
     * Method mutate : Mutates the pieceWeights of Individual
     * @param mutationRate 0 < mutationRate < 1. Probability of mutation on an individual
     */
    public void mutate(double mutationRate) {
        if (Math.random() < mutationRate) { // Randomly choose whether to mutate
            System.out.println("Mutation happening!");
            Random rand = new Random();
            int pieceIndex = rand.nextInt(5); // Randomly choose piece Index
            double delta = Math.random() * this.getPieceRanges()[pieceIndex]; // Randomly choose change of weight
            boolean plusMinus = Math.random() < 0.5; // Randomly choose whether to addition or substract
            // Define new weights
            double newWeights = this.getPieceWeights()[pieceIndex];
            if (plusMinus) {
                newWeights += delta;
            } else {
                newWeights -= delta;
            }
            this.setPieceWeight(pieceIndex, newWeights);
        }
    }
    /**
     * Method mutateCC : mutate Individual, by changing its CenterControl (/square) weights
     * @param mutationRate : 0 < mutationRate < 1. Probability of mutation on an individual
     */
    public void mutateCC(double mutationRate) {
        if (Math.random() < mutationRate) { // Randomly choose whether to mutate
            System.out.println("Mutation happening!");
            Random rand = new Random();
            int pieceIndex = rand.nextInt(4); // Randomly choose squareIndex
            double delta = Math.random() * this.getCCRanges()[pieceIndex]; // Randomly choose change of weight
            boolean plusMinus = Math.random() < 0.5; // Randomly choose whether to add or substract
            // Define new weights
            double newWeights = this.getCCWeights()[pieceIndex];
            if (plusMinus) {
                newWeights += delta;
            } else {
                newWeights -= delta;
            }
            this.setCCWeight(pieceIndex, newWeights);
        }
    }
    /**
     * Method mutateKS : mutates individual by changing its King Safety Weights
     * @param mutationRate : 0 < mutationRate < 1. Probability of mutation on an individual
     */
    public void mutateKS(double mutationRate) {
        if (Math.random() < mutationRate) { // Randomly choose whether to mutate
            System.out.println("Mutation happening!");
            Random rand = new Random();
            int pieceIndex = rand.nextInt(3); // Randomly choose pieceIndex
            double deltaP = Math.random() * this.getKSPRanges()[pieceIndex]; // Randomly choose change of weight
            double deltaD = Math.random() * this.getKSDRanges()[pieceIndex];
            boolean plusMinus = Math.random() < 0.5; // Randomly choose whether to addition or substract
            // Define new weights
            double newWeightsD = this.getKSDWeights()[pieceIndex];
            double newWeightsP = this.getKSPWeights()[pieceIndex];
            if (plusMinus) {
                newWeightsD += deltaD;
                newWeightsP += deltaD;
            } else {
                newWeightsD -= deltaD;
                newWeightsP -= deltaP;
            }
            this.setKSWeight(pieceIndex, newWeightsD, newWeightsP);
        }
    }
    /*Method getOgPieceValues : return the original weights of the pieces */
    public double[] getOgPieceValues() {
        double arr[] = { ogPawn, ogKnight, ogBishop, ogRook, ogQueen };
        return arr;
    }
    /* Method getPieceRanges : return the ranges of weight for the pieces*/
    public double[] getPieceRanges() {
        double arr[] = { pawnRange, knightRange, bishopRange, rookRange, queenRange };
        return arr;
    }
    /*Method getCCRanges : return the ranges of the centercontrol weights for the squares */
    public double[] getCCRanges(){
        double arr[] = {d1Range, d2Range, d3Range, d4Range};
        return arr;
    }
    /*Method getKSPRanges : return the ranges of the king protection weights*/
    public double[] getKSPRanges(){
        double arr[] = {kd1ProtectionRange, kd2ProtectionRange, kd3ProtectionRange};
        return arr;
    }
    /*Method getKSDRanges : return the ranges of the king danger weights*/
    public double[] getKSDRanges(){
        double arr[] = {kd1ProtectionRange, kd2ProtectionRange, kd3ProtectionRange};

        return arr;
    }
    /* Method getPieceWeights : returns the current weights of the pieces*/
    public double[] getPieceWeights() {
        double[] arr = { pawnWeight, knightWeight, bishopWeight, rookWeight, queenWeight };
        return arr;
    }
    /* Method getCCWeights : returns the current weights for the centercontrol*/
    public double[] getCCWeights(){
        double[] arr = {d1Weight, d2Weight, d3Weight, d4Weight};
        return arr;
    }
    /* Method getKSDWeights : returns the current weights for the king danger*/
    public double[] getKSDWeights(){
        double arr[] = {kd1DangerWeight, kd2DangerWeight, kd3DangerWeight};
        return arr;
    }
    /* Method getKSPWeights : returns the current weights for the king protection*/
    public double[] getKSPWeights(){
        double arr[] = {kd1ProtectionWeight, kd2ProtectionWeight, kd3ProtectionWeight};
        return arr;
    }
    /* Method setPieceWeights : sets piece weights to the given array*/
    public void setPieceWeights(double[] weights) {
        this.pawnWeight = weights[0];
        this.knightWeight = weights[1];
        this.bishopWeight = weights[2];
        this.rookWeight = weights[3];
        this.queenWeight = weights[4];
    }
    /* Method setPieceWeight : sets the pieceWeight at index to the weight  */
    public void setPieceWeight(int index, double weight) {
        switch (index) {
            case 0:
                this.pawnWeight = weight;
                break;
            case 1:
                this.knightWeight = weight;
                break;
            case 2:
                this.bishopWeight = weight;
                break;
            case 3:
                this.rookWeight = weight;
                break;
            case 4:
                this.queenWeight = weight;
                break;
        }
    }
    /* Method setCCWeight : sets the squareWeight at index to weight*/
    public void setCCWeight(int index, double weight) {
        switch (index) {
            case 0:
                this.d1Weight = weight;
                break;
            case 1:
                this.d2Weight = weight;
                break;
            case 2:
                this.d3Weight = weight;
                break;
            case 3:
                this.d4Weight = weight;
                break;
        }
    }
    /* Method setCCWeights : sets the center control weights to the input array*/
    public void setCCWeights(double[] weights){
        this.d1Weight = weights[0];
        this.d2Weight = weights[1];
        this.d3Weight = weights[2];
        this.d4Weight = weights[3];
    }
    /* Method setKSWeight : sets king safety D & P weight at index to weightD and weightP*/
    public void setKSWeight(int index, double weightD, double weightP) {
        switch (index) {
            case 0:
                this.kd1DangerWeight = weightD;
                this.kd1ProtectionWeight = weightP;
                break;
            case 1:
                this.kd2DangerWeight = weightD;
                this.kd2ProtectionWeight = weightP;
                break;
            case 2:
                this.kd2DangerWeight = weightD;
                this.kd2ProtectionWeight = weightP;
                break; 
        }
    }
    /* Method setKSWeight : sets the danger and protection weights to the input values*/
    public void setKSWeight(double[] weightD, double[] weightP) {
        this.kd1DangerWeight = weightD[0];
        this.kd1ProtectionWeight = weightP[0];

        this.kd2DangerWeight = weightD[1];
        this.kd2ProtectionWeight = weightP[1];

        this.kd2DangerWeight = weightD[2];
        this.kd2ProtectionWeight = weightP[2]; 
    }
    /* get king danger weights to String*/
    public String DToString(){
        String r = kd1DangerWeight + ", " + kd2DangerWeight + ", " + kd3DangerWeight;
        return r;
    }
    /* get king protection weights to String*/
    public String PToString(){
        String r = kd1ProtectionWeight + ", " + kd2ProtectionWeight + ", " + kd3ProtectionWeight;
        return r;
    }
    /* get center control weights to String*/
    public String squareToString(){
        String r = d1Weight + ", " + d2Weight + ", "+d3Weight + ", "+d4Weight;
        return r;
    }
    /* get piece weights to String*/
    public String toString() {
        String r = "Pawn = " + pawnWeight + ", Knight = " + knightWeight + ", Bishop = " + bishopWeight + ", Rook = "
                + rookWeight + ", Queen = " + queenWeight;

        return r;
    }
    /* set piece weight at index to a new random weight */
    public void randWeight(int whichWeight) {
        if (whichWeight == 0) {
            pawnWeight = (Math.random() * ((ogPawn + pawnRange) - (ogPawn - pawnRange))) + (ogPawn - pawnRange);
        } else if (whichWeight == 1) {
            knightWeight = (Math.random() * ((ogKnight + knightRange) - (ogKnight - knightRange)))
                    + (ogKnight - knightRange);
        } else if (whichWeight == 2) {
            bishopWeight = (Math.random() * ((ogBishop + bishopRange) - (ogBishop - bishopRange)))
                    + (ogBishop - bishopRange);
        } else if (whichWeight == 3) {
            rookWeight = (Math.random() * ((ogRook + rookRange) - (ogRook - rookRange))) + (ogRook - rookRange);
        } else if (whichWeight == 4) {
            queenWeight = (Math.random() * ((ogQueen + queenRange) - (ogQueen - queenRange))) + (ogQueen - queenRange);
        }
    }
}
