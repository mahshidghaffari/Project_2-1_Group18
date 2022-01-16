package controller;

import java.util.Random;

public class Individual {
    //Variable for pieceWeights
    public double pawnWeight;
    public double knightWeight;
    public double bishopWeight;
    public double rookWeight;
    public double queenWeight;

    public double pawnRange = 2.5; // Pawn weight will be between 5 and 15
    public double knightRange = 5.0; // Knight weight will be between 20 and 40
    public double bishopRange = 5.0; // Bishop weight will be between 20 and 40
    public double rookRange = 7.5; // Rook weight will be between 35 and 65
    public double queenRange =15.0; // Queen weight will be between 60 and 120

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

    public double d1OgValue = 0.5202320588728317;
    public double d2OgValue = 1.6396821346508677;
    public double d3OgValue = 1.8715389206457845;
    public double d4OgValue = 2.1227005286165497;

    public double d1Range = 0.2;
    public double d2Range = 0.3;
    public double d3Range = 0.3;
    public double d4Range = 0.3;


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

    public Individual(boolean pieceWeights, boolean squareWeights, boolean kingWeights) {
        // The following formula is to obtain a random number between a min and a max
        // range
        // Math.random() outputs a pseudorandom number between 0 and 1
        // The formula : ((Math.random() * (max - min)) + min) gives us a random in a
        // range of max and min
        // Here max = ogPiece + pieceRange
        // and min = ogPiece - pieceRange
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


    public Individual(double pawnWeight, double knightWeight, double bishopWeight, double rookWeight,
            double queenWeight) {
        this.pawnWeight = pawnWeight;
        this.knightWeight = knightWeight;
        this.bishopWeight = bishopWeight;
        this.rookWeight = rookWeight;
        this.queenWeight = queenWeight;
    }

    public Individual(double[] weights, boolean pieceWeights, boolean squareWeights, boolean kingWeights) {
        this.pieceWeightsEval = pieceWeights;
        this.centerControlEval = squareWeights;
        this.kingSafetyEval = kingWeights;

        if(pieceWeightsEval){

        }
    }
    public Individual(boolean cc, double[]weights){
        this.d1Weight = weights[0];
        this.d2Weight = weights[1];
        this.d3Weight = weights[2];
        this.d4Weight = weights[3];
    }
    public double[] getSquareWeights(){
        double[] weights ={d1Weight, d2Weight, d3Weight, d4Weight};
        return weights;
    }

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
                tf = new TestFitness(getPieceWeights(), gamesToPlay, depth);
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

    public void mutate(double mutationRate) {
        if (Math.random() < mutationRate) { // Randomly choose whether to mutate
            System.out.println("Mutation happening!");
            Random rand = new Random();
            int pieceIndex = rand.nextInt(5); // Randomly choose pieceIndex
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
    public void mutateCC(double mutationRate) {
        if (Math.random() < mutationRate) { // Randomly choose whether to mutate
            System.out.println("Mutation happening!");
            Random rand = new Random();
            int pieceIndex = rand.nextInt(4); // Randomly choose pieceIndex
            double delta = Math.random() * this.getCCRanges()[pieceIndex]; // Randomly choose change of weight
            boolean plusMinus = Math.random() < 0.5; // Randomly choose whether to addition or substract
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

    public double[] getOgPieceValues() {
        double arr[] = { ogPawn, ogKnight, ogBishop, ogRook, ogQueen };
        return arr;
    }

    public double[] getPieceRanges() {
        double arr[] = { pawnRange, knightRange, bishopRange, rookRange, queenRange };
        return arr;
    }
    public double[] getCCRanges(){
        double arr[] = {d1Range, d2Range, d3Range, d4Range};
        return arr;
    }
    public double[] getKSPRanges(){
        double arr[] = {kd1ProtectionRange, kd2ProtectionRange, kd3ProtectionRange};
        return arr;
    }
    public double[] getKSDRanges(){
        double arr[] = {kd1ProtectionRange, kd2ProtectionRange, kd3ProtectionRange};

        return arr;
    }
    public double[] getPieceWeights() {
        double[] arr = { pawnWeight, knightWeight, bishopWeight, rookWeight, queenWeight };
        return arr;
    }
    public double[] getCCWeights(){
        double[] arr = {d1Weight, d2Weight, d3Weight, d4Weight};
        return arr;
    }
    public double[] getKSDWeights(){
        double arr[] = {kd1DangerWeight, kd2DangerWeight, kd3DangerWeight};
        return arr;
    }
    public double[] getKSPWeights(){
        double arr[] = {kd1ProtectionWeight, kd2ProtectionWeight, kd3ProtectionWeight};
        return arr;
    }

    public void setPieceWeights(double[] weights) {
        this.pawnWeight = weights[0];
        this.knightWeight = weights[1];
        this.bishopWeight = weights[2];
        this.rookWeight = weights[3];
        this.queenWeight = weights[4];
    }

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
    public void setKSWeight(double[] weightD, double[] weightP) {
        this.kd1DangerWeight = weightD[0];
        this.kd1ProtectionWeight = weightP[0];

        this.kd2DangerWeight = weightD[1];
        this.kd2ProtectionWeight = weightP[1];

        this.kd2DangerWeight = weightD[2];
        this.kd2ProtectionWeight = weightP[2]; 
    }
    public String DToString(){
        String r = kd1DangerWeight + ", " + kd2DangerWeight + ", " + kd3DangerWeight;
        return r;
    }
    public String PToString(){
        String r = kd1ProtectionWeight + ", " + kd2ProtectionWeight + ", " + kd3ProtectionWeight;
        return r;
    }
    public String squareToString(){
        String r = d1Weight + ", " + d2Weight + ", "+d3Weight + ", "+d4Weight;
        return r;
    }
    public String toString() {
        String r = "Pawn = " + pawnWeight + ", Knight = " + knightWeight + ", Bishop = " + bishopWeight + ", Rook = "
                + rookWeight + ", Queen = " + queenWeight;

        return r;
    }

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
