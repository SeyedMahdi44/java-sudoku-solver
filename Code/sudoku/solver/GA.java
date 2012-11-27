package c02dt.sudoku.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import c02dt.sudoku.base.Puzzle;


/**
 * This class attempts to solve puzzles using a Genetic algorithm.<p>
 * 
 *
 */
public class GA extends Solver {
	private final Float generations = new Float(70);
	private final Float popSize     = new Float(100);
	private final Float genGap      = new Float(0.9);
	private final Float mutRate     = new Float(0.1);
	
	Puzzle blueprint;
	
	Random generator = new Random();
	
	ArrayList<Individual> currGen;
	ArrayList<Individual> nextGen;

	Integer goThrough = Math.round(popSize * (1 - genGap));
	
	
	/**
	 * This instance of the G.A. constructor returns a new GA object initialised with a set
	 * of default parameters, stated below.
	 * <p>
	 * The parameters which govern the behaviour of this G.A. are:
	 *
	 * Maximum generations --- the maximum number of generations the solve() method will process before returning false to indicate a valid Sudoku was not found.
	 * Population Size --- the number of individuals that shall exist in each generation.
	 * Generation Gap --- a factor of the population (in the range 0 - 1) of the population size which shall be introduced to the population via cross-over. The compliment of this factor shall be individuals from the previous generation which pass through to the mutation phase un-altered.
	 * Mutation Rate --- the mutation rate, expressed as a factor of the total number of individuals which shall undergo a random mutation of one of their genes during the generation of the next generation. 
	 * 
	 * <hr>
	 * Default configuration:
	 * <ul>
	 * <li>Maximum generations: 1000</li>
	 * <li>Population Size: 100 </li>
	 * <li>Generation Gap: 90% </li>
	 * <li>Mutation Rate: 5%</li>
	 * </ul>
	 *
	 */
	public GA() {
		nextGen = new ArrayList<Individual>(popSize.intValue());
		currGen = new ArrayList<Individual>(popSize.intValue());
	}
	
	
	/**
	 * This instance of the G.A. constructor returns a new GA object initialised with the provided parameters.
	 * 
	 * @param generations The maximum number of generations the solve() method will process before returning false to indicate a valid Sudoku was not found.
	 * @param popSize The number of individuals that shall exist in each generation.
	 * @param genGap A factor of the population (in the range 0 - 1) of the population size which shall be introduced to the population via cross-over. The compliment of this factor shall be individuals from the previous generation which pass through to the mutation phase un-altered.
	 * @param mutRate The mutation rate, expressed as a factor of the total number of individuals which shall undergo a random mutation of one of their genes during the generation of the next generation.
	 */
	public GA(Float generations, Float popSize, Float genGap, Float mutRate) {
		
		generations = this.generations;
		popSize     = this.popSize;
		genGap      = this.genGap;
		mutRate     = this.mutRate;
		
		nextGen = new ArrayList<Individual>(popSize.intValue());
		currGen = new ArrayList<Individual>(popSize.intValue());
	}
	
	public void setPuzzle(Puzzle clues) {
		this.blueprint = clues;
	}

	

	public Puzzle getPuzzle() {
		if(nextGen.size() > 0) {
			return Collections.max(nextGen).getChromosome();
		} else {
			return this.blueprint;
		}
	}
	
	public Boolean solve() {
		
		// Make zeroth generation
		nextGen.addAll(this.makeIndividuals(popSize.intValue()));
		
		for(int generation = 0; generation < generations; generation++) {
			
			// Move the (previous next generation to the current generation
			currGen.addAll(nextGen);
			
			// Clear the next generation
			nextGen.clear();
			
			// Transfer those who will move, unaltered, to the next generation
			nextGen.addAll(this.select(currGen, goThrough-1));
			
			// Create the new children from the current gen, and add them into the next generation
			nextGen.addAll(this.makeChildren(currGen, popSize.intValue() - goThrough));
			
			// Perform mutation on the next generation
			nextGen = this.mutate(nextGen, Math.round(popSize * mutRate));
			
			// Move over the best performing individual
			nextGen.add(Collections.max(currGen));
			
			// We now have the next generation and are can clear the current one.
			currGen.clear();
			
			if(this.getPuzzle().isSolved()) {
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<Individual> select(ArrayList<Individual> individualPool, Integer number) {
		
		ArrayList<Individual> picked = new ArrayList<Individual>(number);
		ArrayList<Integer> fitnessPool = this.getFitnesss(individualPool);
		Integer fitnessSum = this.sum(fitnessPool)-1;
		
		for(int i = 0; i < number; i++) {
			Integer choice = generator.nextInt(fitnessSum);
			Individual curr;
			Integer total = 0;
			Iterator<Individual> popIter = individualPool.iterator();
			
			do {
				curr = popIter.next();
				total += curr.getFitness();
			} while(choice >= total);
			
			picked.add(curr);
		}
		return picked;
	}
	
	private ArrayList<Individual> makeChildren(ArrayList<Individual> individualPool, Integer number) {
		ArrayList<Individual> children = new ArrayList<Individual>(number);
		
		for(int i = 0; i < number; i++) {
			children.add(this.crossover(this.select(individualPool, 2)));
		}
		
		return children;
	}
	
	private Individual crossover(ArrayList<Individual> parents) {
		Individual p1 = parents.get(0);
		Individual p2 = parents.get(1);
		
		if(p1.getChromosome() == p2.getChromosome()) {
			return p1;
		}
		
		Individual child = p1.clone();
		
		for (int i = generator.nextInt(80)+1; i < 81; i++) {
			child.getChromosome().setValue(i / 9, i % 9, p2.getChromosome().getValue(i / 9, i % 9));
		}
		
		 return child;
	}
	
	private ArrayList<Individual> mutate(ArrayList<Individual> individualPool, Integer number) {
		
		for(int i = 0; i < number; i++) {
			Integer mutation = generator.nextInt(81*individualPool.size());
			Individual mutateMe = individualPool.get(mutation/81);
			
			if(mutateMe.getChromosome().getClue((mutation%81)/9, (mutation%81)%9) == 0) {
				mutateMe.getChromosome().setValue(((mutation%81)/9), (mutation%81)%9, generator.nextInt(9)+1);
			}
			individualPool.set(mutation/81, mutateMe);
		}
		return individualPool;
	}
	
	private ArrayList<Integer> getFitnesss(ArrayList<Individual> pool) {
		ArrayList<Integer> fitnesss = new ArrayList<Integer>();
		for(Individual x: pool) {
			fitnesss.add(x.getFitness());
		}
		
		return fitnesss;
	}
	
	private ArrayList<Individual> makeIndividuals(Integer number) {
		
		ArrayList<Individual> made = new ArrayList<Individual>(number);
		
		for(int i = 0; i < number; i++) {
			made.add(new Individual(blueprint));
		}
		return made;
	}
	
	private Integer sum(ArrayList<Integer> nums) {
		Integer sum = 0;
		for(Integer x: nums) {
			sum += x;
		}
		return sum;
	}
}
