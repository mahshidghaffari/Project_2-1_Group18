package controller;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgo {

    private ArrayList<Individual> parents;
    private int pop_size=100;
    private double mutRate = 0.5; //MUTATION RATE
    private double targetFitness =100;
    private int iterations=0;

    public static void main(String[] args) {
        new GeneticAlgo();
    }
    
    public GeneticAlgo(){
        parents= new ArrayList<>();
        initializeRndPop();

        while(getTotalFitness(parents)<targetFitness){
            iterations++;
            breed();
        }
        System.out.println("Reached target! it took "+ iterations+ " generations to reach this target");
    }

    public void breed(){
        ArrayList<Individual> kids = new ArrayList<>();
        while(kids.size()< pop_size){
            Individual momma = selectParent();
            Individual papa = selectParent();
            Individual child1= new Individual("f");
            Individual child2= new Individual("g"); 
            crossOver(momma, papa,child1,child2);
            mutate(child1);
            mutate(child2);
            kids.add(child1);
            kids.add(child2);
        }
        System.out.println("Old population total fitness" + getTotalFitness(parents));
        System.out.println("New population total fitness" + getTotalFitness(kids));
        parents= kids;
    }

    public void initializeRndPop(){ 
        for(int i=0; i<100; i++){
            parents.add(new Individual());
        }
    } 

    /**
    * This is a Roulette Wheel Selection method. It randomly selects an individual 
    * from a population, with the chance of selection being proportionate to
    * the individual's fitness as part of the total fitness
     */

    public Individual selectParent(){
        while(true){
            double chance = Math.random()*(getTotalFitness(parents)+1);
            double fitnessMinus = getTotalFitness(parents);
            for(Individual indi:parents){
                fitnessMinus -= indi.getFitness();
                if(fitnessMinus<=chance){
                    return indi;
                }
            } 
        }
    }
    /**
     * In this method we are making a child from two individuals.
     * We get a random spot in the list of weights,
     * we copy all the weights up to that spot from one parent and the rest of the 
     * weights from the other parent and then pass them on to the child. 
     * @param parent1
     * @param parent2
     * @param child1
     * @param child2
     */

    public void crossOver(Individual parent1,Individual parent2,Individual child1,Individual child2 ){
        Random rand = new Random();
        int wSize = parent1.getWeights().length; //the amount of weights we are dealing with
        double [] weights1 = new double[wSize];
        double [] weights2 = new double[wSize];
        int randSplit = rand.nextInt(wSize);    //spot in which the split will occur, taking all the weight up to this point

        for(int i=0; i< wSize; i++){
            if(i<randSplit){
                weights1[i] = parent1.getWeights()[i];
                weights2[i] = parent2.getWeights()[i];
            }
            else{
                weights1[i] = parent2.getWeights()[i];
                weights2[i] = parent1.getWeights()[i];
            }
        }
        child1.setWeights(weights1);
        child2.setWeights(weights2);
    }

    /**
	 * For our mutate method, we take in the poopulation rate as a value between 0 and 1, then if a randomly
	 * generated number, also between 0 and 1, is smaller than the rate, one weight is switched out for another
	 * random weight randomly.
	 * @param child
	 */
	public void mutate(Individual child){
	    if(Math.random() <= mutRate){
	    	Random rnd = new Random(); 
            int wSize = child.getWeights().length; 
            int switchWeight = rnd.nextInt(wSize);
            child.randWeight(switchWeight);   
		}
	}


    public double getTotalFitness(ArrayList<Individual> gen){
        double sum=0;
        for(Individual individual: gen){
            sum+=individual.getFitness();
        }
        return sum;
    }

    public double getAvgFitness(ArrayList<Individual> gen){
        return getTotalFitness(gen)/gen.size();
    }

}
