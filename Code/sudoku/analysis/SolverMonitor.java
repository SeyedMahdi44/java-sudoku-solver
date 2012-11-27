package c02dt.sudoku.analysis;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import c02dt.sudoku.base.*;
import c02dt.sudoku.solver.*;

public class SolverMonitor implements Runnable {
	
	private Solver 	mySolver;
	private Puzzle 	myPuzzle;
	private Integer	resolution;
	
	private static ThreadMXBean tmxb = ManagementFactory.getThreadMXBean();
	
	
	public SolverMonitor(Solver mySolver, Puzzle myPuzzle, Integer resolution) {
		this.mySolver 	= mySolver;
		this.myPuzzle 	= myPuzzle;
		this.resolution = resolution;
	}
	
	public Puzzle getPuzzle () {
		return myPuzzle;
	}
	
	public void run() {
		
		// Make sure the monitor gets prority
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
		// Set up the solver and its thread
		mySolver.setPuzzle(myPuzzle);
		Thread solverThread = new Thread(mySolver);
		solverThread.setPriority(Thread.MAX_PRIORITY - 1);
		solverThread.setName(mySolver.getClass() + " Thread");
		long sThreadID = solverThread.getId();
		
		// Start solving
		solverThread.start();
	}
}