package c02dt.sudoku.solver;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import c02dt.sudoku.base.Puzzle;
import c02dt.sudoku.analysis.*;

/**
 * When extending this abstract class the solving class can be used with the Sudoku test bed.<p>
 * This provides the methods to be given a sudoku puzzle (attempt) to solve it, and indicate when it is done.
 * Along with monitoring information.
 *
 */
public abstract class Solver implements Runnable  {
	
	protected Puzzle puzzle; 
	
	/**
	 * This method is used to set the puzzle for this solver, a solver should only solve one puzzle in its life.<p>
	 * To solve other puzzle, a new new solver class is instanciated for each puzzle.
	 * @param aPuzzle should be a puzzle in the 'ready to be solved' state, indicated numerically as state 1.
	 */
	public void setPuzzle(Puzzle aPuzzle) {
		this.puzzle = aPuzzle;
	}
	
	/**
	 * Called to return the puzzle on which solver is assigned to.
	 * @return the puzzle this solver is/has solving/ed
	 */
	public Puzzle getPuzzle() {
			return this.puzzle;
	}
	
	/**
	 * This method allows the thread to implement Runnable an as such a solve
	 * may be executed in its own thread.<p>
	 * By running a solver in it's own thread it shall record the CPU time utilised 
	 * by the solver during invocation of its solve() method. This shall be optionally
	 * (if a ResultSet object has been specified) logged when the solve completes.
	 */
	public void run() {
		this.solve();
	}
	
	/**
	 * The solve method can only be called on a solve which has a puzzle set at state 1.<p>
	 * Once called it will attempt to solve the puzzle and indicate its success or failure.
	 * @return True if a solution was found, False if not.
	 */
	protected abstract Boolean solve();
	
}
