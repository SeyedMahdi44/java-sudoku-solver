package c02dt.sudoku.analysis;

import java.util.*;
import c02dt.sudoku.base.*;
import c02dt.sudoku.solver.*;;

public class AnalysisRun {
	private final ArrayList<Solver> 	solvers;
	private final PuzzleSet				puzzles;
	private final ArrayList<ResultSet>	results;
	
	
	public AnalysisRun(ArrayList<Solver> solvers, PuzzleSet puzzles) {
		this.solvers = solvers;
		this.puzzles = puzzles;
		
		this.results = new ArrayList<ResultSet>(solvers.size());
		for(int i = 0; i < solvers.size(); i++) {
			this.results.add(new ResultSet());
		}
	}
	
	public ArrayList<ResultSet> getResults() {
		return this.results;
	}
	
	public void run() throws InterruptedException {
		for(Puzzle x : puzzles) {
			this.SolverRun(x);
		}
	}
	
	private void SolverRun(Puzzle puzzle) throws InterruptedException {
		Iterator<Solver> solverIter = solvers.iterator();
		Iterator<ResultSet> resultIter = results.iterator();
		
		while(solverIter.hasNext()) {
			
			Solver thisSolver = solverIter.next();
			ResultSet thisRS  = resultIter.next();
			
			thisSolver.setPuzzle(puzzle.clone());
			Thread solveThread = new Thread(thisSolver);
			solveThread.setPriority(Thread.MAX_PRIORITY);
			
			long startT = System.currentTimeMillis();
			solveThread.run();
			solveThread.join();
			long time = System.currentTimeMillis() - startT;
			
			
			thisRS.add(new SolveResult(thisSolver.getPuzzle(), time));
		}
	}
}
