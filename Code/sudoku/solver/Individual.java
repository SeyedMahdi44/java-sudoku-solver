package c02dt.sudoku.solver;

import java.util.Random;

import c02dt.sudoku.base.Puzzle;

public class Individual implements Cloneable, Comparable<Individual> {
	
	private Puzzle chromosome;
	Random generator = new Random();
	
	public Individual(Puzzle blueprint) {
		
		if(blueprint.getCardinality() != 81) {
			this.chromosome = this.generateIndividual(blueprint);
		}
		else {
			this.chromosome = blueprint.clone();
		}
	}
	
	public Puzzle getChromosome() {
		return this.chromosome;
	}
	
	public Integer getFitness() {
		return  216 - this.chromosome.conflictCount();
	}
	
	public int compareTo(Individual o) {
		return this.getFitness() - o.getFitness();
	}
	
	public Individual clone() {
		return new Individual(this.getChromosome());
	}
	
	private Puzzle generateIndividual(Puzzle blueprint) {
		
		Puzzle newChromosome = blueprint.clone();
		
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				if(newChromosome.getClue(r, c) == 0) {
					newChromosome.setValue(r, c, generator.nextInt(9)+1);
				}
			}
		}
		
		return newChromosome;
	}
}