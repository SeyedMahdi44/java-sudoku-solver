package c02dt.sudoku.analysis;

import java.util.*;
import c02dt.sudoku.base.*;

public class ResultSet extends ArrayList<SolveResult> {
	
	/**
	 * This method returns the average time taken by the solves which
	 * logged a SolveResult object in this ResultSet.
	 * @return The average time, in nanoseconds, the solves logged here took. 
	 */
	public float averageTime() {
		float out = 0;
		
		Iterator<SolveResult> iter = super.iterator();
		
		while(iter.hasNext()) {
			out += iter.next().timeTaken;
		}
		
		return out / super.size();
	}
	
	/**
	 * We define the `answers' a solve returns as the factor of the number of cells
	 * filled by the solver over the number of empty cells which were present in 
	 * the initial grid.
	 * @return This returns the average number of answers found by the solves logged here.
	 */
	public float averageAnswers() {
		float out = 0;
		
		Iterator<SolveResult> iter = super.iterator();
		
		while(iter.hasNext()) {
			SolveResult thisResult = iter.next();
			float clues = ResultSet.clueCount(thisResult.puzzle);
			float cardinality = thisResult.puzzle.getCardinality();
			float conflicts = thisResult.puzzle.conflictCount();
			
			if(!(cardinality - clues - conflicts < 0)) {
				out += ((cardinality - clues - conflicts) / (81 - clues));
			}
		}
		return out / super.size();
	}
	
	private static int clueCount(Puzzle puzzle) {
		int out = 0;
		
		for(int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++){
				if (puzzle.getClue(r, c) != 0) {
					out++;
				}
			}
		}
		
		return out;
	}
}
