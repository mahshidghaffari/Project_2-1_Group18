package controller;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgo {

    
    final private int POP_SIZE=20;            //population size
    final private double MUTATION_RATE = 0.15;     //MUTATION RATE
    final private double GAMES_TO_PLAY = 50;   // games to play to determine fitness
    final private int DEPTH_TO_PLAY=3;         //in which depth should the games be played 
    final private double GENERATIONS = 10;  // generations to run
    
    private ArrayList<Individual> parents;
    //private double targetFitness = 8; 
    private double parents_totalFitness=-1;

    public static void main(String[] args) {
        new GeneticAlgo();
    }
    
    public GeneticAlgo(){
        parents= new ArrayList<>();
        initializeRndPop();
        int iterations=0;
        while(iterations<GENERATIONS){
            iterations++;
            System.out.println("Generation #" + iterations);
            breed();
            System.out.println("Best weights are :" + getFittest(parents).toString()+ " and the fitness score is" + getFittest(parents).getFitness(GAMES_TO_PLAY,DEPTH_TO_PLAY));
        }

        //System.out.println("Reached target! it took "+ iterations+ " generations to reach this target");
        double[] bestWeights = getFittest(parents).getWeights();
        //System.out.println("Best weights are :" + getFittest(parents).toString());
    }

    public void breed(){
        System.out.println("breeding . . . ");
        ArrayList<Individual> kids = new ArrayList<>();
        while(kids.size()< POP_SIZE){
            Individual momma = selectParent();
            Individual papa = selectParent();
            //System.out.println("Selected parents");
            Individual child1= new Individual("f");
            Individual child2= new Individual("g"); 
            crossOver(momma,papa,child1,child2);
            //System.out.println("created children");
            mutate(child1);
            mutate(child2);
            kids.add(child1);
            kids.add(child2);
           //System.out.println("kids size currently: "+kids.size() );
        }
        double kids_totalFitness = getTotalFitness(kids,true);
        System.out.println("Old population total fitness" + parents_totalFitness);
        System.out.println("New population total fitness" + kids_totalFitness);
        parents= kids;
        parents_totalFitness = kids_totalFitness;
    }

    public void initializeRndPop(){ 
        for(int i=0; i<POP_SIZE; i++){
            parents.add(new Individual());
        }
    } 

    /**
    * This is a Roulette Wheel Selection method. It randomly selects an individual 
    * from a population, with the chance of selection being proportionate to
    * the individual's fitness as part of the total fitness
     */

    public Individual selectParent(){
        if(parents_totalFitness==-1){
            parents_totalFitness = getTotalFitness(parents,false);
        }
        while(true){
            double chance = Math.random()*(parents_totalFitness+1);
            double fitnessMinus = parents_totalFitness;
            for(Individual indi:parents){
                fitnessMinus -= indi.getFitness(GAMES_TO_PLAY, DEPTH_TO_PLAY);
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
	    if(Math.random() <= MUTATION_RATE){
            //System.out.println("A mutation occured");
	    	Random rnd = new Random(); 
            int wSize = child.getWeights().length; 
            int switchWeight = rnd.nextInt(wSize);
            child.randWeight(switchWeight);   
		}
	}


    public double getTotalFitness(ArrayList<Individual> gen, boolean isKids){
        double sum=0;
        if((!isKids && parents_totalFitness==-1)){
            int i=0;
            for(Individual individual: gen){
                double fit = individual.getFitness(GAMES_TO_PLAY,DEPTH_TO_PLAY);
                //System.out.println( "for individual " +i + " fitness is: " +fit);
                i++;
                sum+=fit;
            }
            parents_totalFitness=sum;
            return parents_totalFitness;
        }
        else if(isKids){
            int i=0;
            for(Individual individual: gen){
                double fit = individual.getFitness(GAMES_TO_PLAY,DEPTH_TO_PLAY);
                //System.out.println( "for individual " +i + " fitness is: " +fit);
                i++;
                sum+=fit;
            }   
        }
        return sum;
    }
/**
 * 
 * @param gen input the generation
 * @param isKids input whether or not this is the parents generation or the kids
 * @return
 */
    public double getAvgFitness(ArrayList<Individual> gen,boolean isKids){
        return getTotalFitness(gen,isKids)/gen.size();
    }

    public Individual getFittest(ArrayList<Individual> gen){
        Individual max= new Individual();
        for(Individual indi:gen){
            if(indi.getFitness(GAMES_TO_PLAY,DEPTH_TO_PLAY)>max.getFitness(GAMES_TO_PLAY,DEPTH_TO_PLAY)){
                max = indi;
            }
        }
        return max;
    }

}
