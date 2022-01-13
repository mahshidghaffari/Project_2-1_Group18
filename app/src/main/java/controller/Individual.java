package controller;

import java.util.Random;

public class Individual {
    public double pawnWeight;
    public double knightWeight;
    public double bishopWeight;
    public double rookWeight;
    public double queenWeight;
    // No king weigth : it is always going to be worth 100000 because it determines
    // who wins the game

    // public double pawnRange = pawnWeight / 10.0; // Pawn weight will be between 5 and 15
    // public double knightRange = knightWeight / 10.0; // Knight weight will be between 20 and 40
    // public double bishopRange = bishopWeight / 10.0; // Bishop weight will be between 20 and 40
    // public double rookRange = rookWeight / 10.0; // Rook weight will be between 35 and 65
    // public double queenRange = queenWeight / 10.0; // Queen weight will be between 60 and 120

    public double pawnRange = 5.0; // Pawn weight will be between 5 and 15
    public double knightRange = 10.0; // Knight weight will be between 20 and 40
    public double bishopRange = 10.0; // Bishop weight will be between 20 and 40
    public double rookRange = 15.0; // Rook weight will be between 35 and 65
    public double queenRange =10.0; // Queen weight will be between 60 and 120

    double ogPawn = 10.0;
    double ogKnight = 30.0;
    double ogBishop = 30.0;
    double ogRook = 50.0;
    double ogQueen = 90.0;

    public double fitness = -1;

    public Individual() {
        // The following formula is to obtain a random number between a min and a max
        // range
        // Math.random() outputs a pseudorandom number between 0 and 1
        // The formula : ((Math.random() * (max - min)) + min) gives us a random in a
        // range of max and min
        // Here max = ogPiece + pieceRange
        // and min = ogPiece - pieceRange
        this.pawnWeight = (Math.random() * ((ogPawn + pawnRange) - (ogPawn - pawnRange))) + (ogPawn - pawnRange);
        this.knightWeight = (Math.random() * ((ogKnight + knightRange) - (ogKnight - knightRange)))
                + (ogKnight - knightRange);
        this.bishopWeight = (Math.random() * ((ogBishop + bishopRange) - (ogBishop - bishopRange)))
                + (ogBishop - bishopRange);
        this.rookWeight = (Math.random() * ((ogRook + rookRange) - (ogRook - rookRange))) + (ogRook - rookRange);
        this.queenWeight = (Math.random() * ((ogQueen + queenRange) - (ogQueen - queenRange))) + (ogQueen - queenRange);
    }

    public Individual(double pawnWeight, double knightWeight, double bishopWeight, double rookWeight,
            double queenWeight) {
        this.pawnWeight = pawnWeight;
        this.knightWeight = knightWeight;
        this.bishopWeight = bishopWeight;
        this.rookWeight = rookWeight;
        this.queenWeight = queenWeight;
    }

    public Individual(double[] weights) {
        this.pawnWeight = weights[0];
        this.knightWeight = weights[1];
        this.bishopWeight = weights[2];
        this.rookWeight = weights[3];
        this.queenWeight = weights[4];
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
            TestFitness tf = new TestFitness(getWeights(), gamesToPlay, depth);
            fitness = tf.getFitness();
        }
        return fitness;
    }

    public void mutate(double mutationRate) {
        if (Math.random() < mutationRate) { // Randomly choose whether to mutate
            Random rand = new Random();
            int pieceIndex = rand.nextInt(5); // Randomly choose pieceIndex
            double delta = Math.random() * this.getRanges()[pieceIndex]; // Randomly choose change of weight
            boolean plusMinus = Math.random() < 0.5; // Randomly choose whether to addition or substract
            // Define new weights
            double newWeights = this.getWeights()[pieceIndex];
            if (plusMinus) {
                newWeights += delta;
            } else {
                newWeights -= delta;
            }
            this.setWeight(pieceIndex, newWeights);
        }
    }

    public double[] getOgValues() {
        double arr[] = { ogPawn, ogKnight, ogBishop, ogRook, ogQueen };
        return arr;
    }

    public double[] getRanges() {
        double arr[] = { pawnRange, knightRange, bishopRange, rookRange, queenRange };
        return arr;
    }

    public double[] getWeights() {
        double[] arr = { pawnWeight, knightWeight, bishopWeight, rookWeight, queenWeight };
        return arr;
    }

    public void setWeights(double[] weights) {
        this.pawnWeight = weights[0];
        this.knightWeight = weights[1];
        this.bishopWeight = weights[2];
        this.rookWeight = weights[3];
        this.queenWeight = weights[4];
    }

    public void setWeight(int index, double weight) {
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
