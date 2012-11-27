
package c02dt.sudoku.solver;

public class DepthFirst2 extends Solver {
	
	public Boolean solve() {
		
		return this.recurse(0);
		
	}
	
	private boolean recurse(int pos) {
		int r = pos / 9;
		int c = pos % 9;
		
		// Catch the base case - it's solved
		if(super.puzzle.isSolved()) {
			return true;
		}
		
		if(super.puzzle.getClue(r, c) != 0) {
			
			// If it's a clue then we've already `solved' at this position
			return this.recurse(pos + 1);
		} 
		
		else {
			
			// If not, for each number
			for(int i = 1; i < 10; i++) {
				
				// Try it at current pos
				super.puzzle.setValue(r, c, i);
				
				// See if the puzzle is still valid
				if(super.puzzle.isValid()) {
					
					// It is so move on and try solving the rest
					if(this.recurse(pos + 1)) {
						return true;
					}
				}
				// It's not so try the next number
			}
		}
		
		// This sub-tree does not lead to a solution
		super.puzzle.setValue(r, c, 0);
		return false;
	}
}