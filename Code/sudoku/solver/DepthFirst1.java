package c02dt.sudoku.solver;

public class DepthFirst1 extends Solver {

public Boolean solve() {
		
		// In this approach we track our position as a cell number from 0 in top left(0x0)
		// to 80 in bottom right (8x8).
		int iter = 0;
		double avgPosTot = 1;
		int pos = 0;
		int maxPos = -1;
		
		// The x and y variables are updated to our 'real' position in the puzzle
		int x;
		int y;
		
		// Set to true if we're okay to keep moving forward through the puzzle
		boolean forward = true;
		
			while(pos < 81) {
				
				if(puzzle.isSolved()) {
					return true;
				}
				
				// Get x and y coords from pos
				x = pos / 9;
				y = pos % 9;
				
				while(puzzle.getClue(x, y) > 0) {
					if(forward) {
						pos++;
					} else {
						pos--;
					}
					x = pos / 9;
					y = pos % 9;
				}
				
				if(puzzle.getValue(x, y) == 9) {
					puzzle.setValue(x, y, 0);
					pos--;
					forward = false;
				} else {
					puzzle.setValue(x, y, puzzle.getValue(x, y)+1);
					forward = true;
				}
				
				
				if(forward && puzzle.isValid()) {
					pos++;
				}

				iter++;
				avgPosTot+=pos;
			}
			return true;
	}
}
