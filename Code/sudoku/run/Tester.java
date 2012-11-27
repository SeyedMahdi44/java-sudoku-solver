package c02dt.sudoku.run;

import c02dt.sudoku.base.*;
import c02dt.sudoku.solver.*;
import c02dt.sudoku.analysis.*;
import java.util.*;

public class Tester {
	
	public static void main(String[] args)  throws InterruptedException {
		
		ArrayList<Solver> solvers = new ArrayList<Solver>();
		solvers.add(new DepthFirst1());
		solvers.add(new DepthFirst2());
		solvers.add(new RuleBased());
		solvers.add(new GA());
		solvers.add(new DancingLinks());
		
		
		for(int level = 0; level < 9; level++) {
			System.out.println("Results for level " + level  + " puzzles.");
			
			PuzzleSet puzzles = new PuzzleSet();
			puzzles.loadPuzzles(new MenneskeLoader(level) , 4);
			
			
			AnalysisRun test = new AnalysisRun(solvers, puzzles);
			test.run();
			ArrayList<ResultSet> results = test.getResults();
			
			Iterator<ResultSet> resultIter = results.iterator();
			while(resultIter.hasNext()) {
				ResultSet eachsolver = resultIter.next();
				System.out.println("Average time: " + eachsolver.averageTime());
				System.out.println("Average answers: " + eachsolver.averageAnswers()*100 + "%");
			}
		}
	}
}
