package controller;

import java.util.ArrayList;
import java.util.Random;

public class GA {
    /* This is the 2nd genetic algorithm class. This one was used to simulate the ga
    for the square weights.
    */
    private int GAMES_TO_PLAY = 80;
    private int DEPTH = 1;
    private double mutationRate = 0.1;
    private int popSize = 20;
    public int generations =150;

    public boolean evolvePieceWeights;
    public boolean evolveCC;
    public boolean evolveKingSafety;
    public static void main(String[]args){
        GA ga = new GA();
        ga.GeneticAlgo();
    }
    public void GeneticAlgo(){
        GA ga = new GA();
        ArrayList<Individual> pop = ga.initPop();
        int iterations = 0;
        
        System.out.println("Generation "+iterations);
        System.out.println("Average Fitness = " + ga.getAvgFitness(pop));
        System.out.println("Best Individual : "+ga.getFittest(pop).getFitness(GAMES_TO_PLAY, DEPTH));
        System.out.println("Best Weights : " + ga.getFittest(pop).squareToString());
        System.out.println(" ");
        System.out.println("Average Weights : " + ga.getAvgCCWeights(pop)[0]+", "+ga.getAvgCCWeights(pop)[1]+", "+ga.getAvgCCWeights(pop)[2]+", "+ga.getAvgCCWeights(pop)[3]);
        System.out.println(" ");
 

        while(iterations < ga.generations){
            iterations++;
            pop = ga.createNewGen(pop);
            System.out.println("Generation "+iterations);
            System.out.println("Average Fitness = " + ga.getAvgFitness(pop));
            System.out.println("Best Individual : "+ga.getFittest(pop).getFitness(GAMES_TO_PLAY, DEPTH));
            System.out.println("Best Weights : " + ga.getFittest(pop).squareToString());
            System.out.println(" ");
            System.out.println("Average Weights : " + ga.getAvgCCWeights(pop)[0]+", "+ga.getAvgCCWeights(pop)[1]+", "+ga.getAvgCCWeights(pop)[2]+", "+ga.getAvgCCWeights(pop)[3]);
            System.out.println(" ");


            
        }
        //System.out.println("Done!!! ");
        //System.out.println("Average weights of final pop :" + ga.getAvgWeights(pop)[0]+", "+ga.getAvgWeights(pop)[1]+", "+ga.getAvgWeights(pop)[2]+", "+ga.getAvgWeights(pop)[3]);
    }
    /*Method initPop : initializes a population of individuals*/
    public ArrayList<Individual> initPop(){
        ArrayList<Individual> pop = new ArrayList<Individual>();
        while(pop.size() < popSize){
            pop.add(new Individual(false,true,false));
        }
        return pop;
    }
    /* Method createNewGen : creates new generation of individuals given the parents*/
    public ArrayList<Individual> createNewGen(ArrayList<Individual> parents){

        ArrayList<Individual> kids = new ArrayList<Individual>();
        while(kids.size() < popSize){
            Individual dad = this.selectParent(parents);
            Individual mom = this.selectParent(parents);
            ArrayList<Individual> children = this.reproduceCC(dad, mom);
            kids.add(children.get(0));
            kids.add(children.get(1));
        }
        return kids;
    }
    /* Method mutePop : mutates input population according to mutationRate*/
    public ArrayList<Individual> mutePop(ArrayList<Individual> population){
        for(int i=0; i<popSize; i++){
            population.get(i).mutateCC(mutationRate);
        }
        return population;
    }
    /* Method selectParent : selects a parent with roulette-wheel selection (according to the individual's fitness) */
    public Individual selectParent(ArrayList<Individual> pop){
        
        ArrayList<Individual> roulette = new ArrayList<Individual>(); //Create roulette wheel
        
        for(Individual ind:pop){

            int amount = (int) (100.0 * ind.getFitness(GAMES_TO_PLAY, DEPTH));
            //System.out.println("amount to add to arrayList" + amount);
            //Amount of times ind will be added depends on Ind fitness;
            for(int i=0; i<amount; i++){
                roulette.add(ind);
            }
        }
        //System.out.println(roulette.size());
        Random rand = new Random();
        int index = rand.nextInt(roulette.size());
        return roulette.get(index);
    }
    /* */
    public Individual reproducePiece(Individual dad, Individual mom){
        double[] weights = new double[dad.getPieceWeights().length];
        Random rand = new Random();
        int index = rand.nextInt(5);
        for(int i=0; i<dad.getPieceWeights().length; i++){
            if(i < index){
                weights[i] = dad.getPieceWeights()[i];
            } else{ 
                weights[i] = mom.getPieceRanges()[i];
            }
            weights[i] = (dad.getPieceWeights()[i] + mom.getPieceWeights()[i])/2.0;

        }
        Individual child1 = new Individual(true, false, false);
        child1.setPieceWeights(weights);
        //Individual child2 = new Individual(true,weights);
        //ArrayList<Individual> children = new ArrayList<Individual>();
        //children.add(child1);
        //children.add(child2);
        return child1;
    }
    public ArrayList<Individual> reproduceCC(Individual dad, Individual mom){
        double[] weights1 = new double[dad.getSquareWeights().length];
        double[] weights2 = new double[dad.getSquareWeights().length];

        Random rand = new Random();
        int index = rand.nextInt(4);
        
        for(int i=0; i<dad.getSquareWeights().length; i++){
            if(i<index){
                weights1[i] = dad.getCCWeights()[i];
                weights2[i] = mom.getCCWeights()[i];
            } else{
                weights1[i] = mom.getCCWeights()[i];
                weights2[i] = dad.getCCWeights()[i];

            }
        }
        Individual child1 = new Individual(false, true, false);
        child1.setCCWeights(weights1);
        Individual child2 = new Individual(false,true,false);
        child2.setCCWeights(weights2);
        ArrayList<Individual> children = new ArrayList<Individual>();
        children.add(child1);
        children.add(child2);
        return children;
    }
    public Individual reproduceKS(Individual dad, Individual mom){
        double[] weightsD = new double[dad.getKSDWeights().length];
        double[] weightsP = new double[dad.getKSPWeights().length];

        Random rand = new Random();
        int index = rand.nextInt(3);
        
        for(int i=0; i<dad.getSquareWeights().length; i++){
            if(i<index){
                weightsD[i] = dad.getKSDWeights()[i];
                weightsP[i] = dad.getKSPWeights()[i];
            } else{
                weightsD[i] = mom.getKSDWeights()[i];
                weightsP[i] = mom.getKSPWeights()[i];
            }
        }
        Individual child1 = new Individual("");
        Individual child2 = new Individual("");
        child1.setKSWeight(weightsD, weightsP);
        ArrayList<Individual> children = new ArrayList<Individual>();
        children.add(child1);
        children.add(child2);
        return child1;
    }
    public double getAvgFitness(ArrayList<Individual> pop){
        double sum = 0.0;
        double n = 0.0;
        for(Individual ind:pop){
            sum += ind.getFitness(GAMES_TO_PLAY, DEPTH);
            n++;
        }
        return sum/n;
    }
    public double[] getAvgPieceWeights(ArrayList<Individual> pop){
        double[] weights = new double[5];

        for(int i=0; i<5; i++){
            double sum = 0.0;
            double n = 0.0;
            for(Individual ind:pop){
                sum += ind.getPieceWeights()[i];
                n++;
            }
            weights[i] = sum/n;
        }
        return weights;
    }
    public double[] getAvgCCWeights(ArrayList<Individual> pop){
        double[] weights = new double[4];

        for(int i=0; i<4; i++){
            double sum = 0.0;
            double n = 0.0;
            for(Individual ind:pop){
                sum += ind.getCCWeights()[i];
                n++;
            }
            weights[i] = sum/n;
        }
        return weights;
    }

    public double[] getAvgWeightsKS(ArrayList<Individual> pop){
        double[] weights = new double[4];

        for(int i=0; i<4; i++){
            double sumD = 0.0;
            double nD = 0.0;
            for(Individual ind:pop){
                sumD += ind.getKSDWeights()[i];
                nD++;
            }
            weights[i] = sumD/nD;
        }
        for(int i=4; i<8; i++){
            double sumP = 0.0;
            double nP = 0.0;
            for(Individual ind:pop){
                sumP += ind.getKSPWeights()[i];
                nP++;
            }
            weights[i] = sumP/nP;
        }
        return weights;
    }
    public Individual getFittest(ArrayList<Individual> pop){
        Individual max = pop.get(0);
        for(Individual ind:pop){
            if(ind.getFitness(GAMES_TO_PLAY, DEPTH) > max.getFitness(GAMES_TO_PLAY, DEPTH)){
                max = ind;
            }
        }
        return max;
    }
    
}
