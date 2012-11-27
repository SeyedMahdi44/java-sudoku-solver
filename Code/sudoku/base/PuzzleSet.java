package c02dt.sudoku.base;

import java.util.*; 
import java.io.*;

/**
 * This class serves to hold a collection of sudoku puzzles.<p>
 * Implementing enumeration it provides the puzzles one at a time 
 * so they may be passed to a solver. <p>
 * Additionally the puzzle can then be passed back to the set in a
 * different (completed) state.
 */
public class PuzzleSet implements Enumeration, Iterable<Puzzle>, Serializable {
	
	// This ArrayList holds the puzzles;
	private ArrayList<Puzzle> thePuzzles;
	
	// Pos holds the current position of the enumeration through the puzzle set.
	private int pos;
	
	/**
	 * Creates a new puzzles set containing no Puzzles.
	 *
	 */
	public PuzzleSet() {
		this.thePuzzles = new ArrayList<Puzzle>();
		pos = 0;
	}
	

	/**
	 * This method is used to `load up' a PuzzleSet object with puzzles. This is done by passing in
	 * an object which implements PuzzleLoader to provide the puzzles, and the number of them to (attempt)
	 * to load. 
	 * 
	 * @param source A PuzzleLoader object to provide a source of puzzles.
	 * @param number The number of puzzles to attempt to load.
	 * @return True if the specified number of puzzles were loaded, false if they were not. 
	 */
	public boolean loadPuzzles(PuzzleLoader source, Integer number) {
		
		while(source.hasMoreElements() && number > 0) {
			this.thePuzzles.add(source.nextElement());
			number--;
		}
		
		return number == 0;
	}
	
	/**
	 * Returns true if the current position of this set is before the last puzzle it contains.
	 */
	public boolean hasMoreElements() {
		return pos < this.thePuzzles.size()-1;
	}
	
	/**
	 * Returns the next puzzle in the set
	 */
	public Puzzle nextElement() {
		return this.thePuzzles.get(pos++);
	}
	
	/**
	 * Overwrites the last puzzle returned by this class with the incoming one.
	 * @param puzzle the new / updated puzzle
	 */
	public void setLastElement(Puzzle puzzle) {
		this.thePuzzles.set(pos-1, puzzle);
	}
	
	public Iterator<Puzzle> iterator() {
		return thePuzzles.iterator();
	}
}
