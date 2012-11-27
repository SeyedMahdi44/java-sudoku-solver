package c02dt.sudoku.solver;

import c02dt.sudoku.base.*;

/*
 * Copyright (C) 2005 Stan Chesnutt.  All rights reserved.  You are welcome
 * to reuse this code in any project, commercial or otherwise, as long as
 * you mention my name in the documentation for the program (perhaps in
 * an "about" box, or a credit page, etc).
 *
 * comments/questions to chesnutt at gmail dot com
 *
 * $Header: /home/cvs//dancinglinks/Sudoku.java,v 1.3 2006/01/03 23:29:45 chesnutt Exp $
 */
public class DancingLinks extends Solver
{
	private int primitivePuzzle[][];
	
	public DancingLinks() {
		primitivePuzzle = new int[9][9];
	}
	
	
    private class SudokuSolution implements SudokuSolutionHandler {
        public void handleSolution(int solution[][]) {
            for (int row = 0; row < 9; row++) {
                for (int column = 0; column < 9; column++)
                	
                    puzzle.setValue(row, column, solution[row][column]);
            }
        }
    }

    public Boolean solve() {
        DancingLinksSudoku s = new DancingLinksSudoku(primitivePuzzle, new SudokuSolution());
        s.solveit();
        return true;
    }

	public void setPuzzle(Puzzle clues) {
		
		super.puzzle = clues;
		
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				primitivePuzzle[r][c] = clues.getClue(r, c);
			}
		}
	}
}
