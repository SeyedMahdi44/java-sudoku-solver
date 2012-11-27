package c02dt.sudoku.analysis;

import c02dt.sudoku.base.*;

public final class SolveResult {
	public final Puzzle puzzle;
	public final float timeTaken;
	
	public SolveResult(Puzzle puzzle, float timeTaken) {
		this.puzzle = puzzle;
		this.timeTaken = timeTaken;
	}
}
