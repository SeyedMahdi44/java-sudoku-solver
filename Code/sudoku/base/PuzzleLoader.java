package c02dt.sudoku.base;

import java.util.Enumeration;


/**
 * A class extending PuzzleLoader shall provide it's own implementation to retrieve puzzles 
 * from the media/data source it concerns.  
 *
 */
public interface PuzzleLoader extends Enumeration {
	
	/**
	 * Returns a puzzle from the implementing classes source. 
	 * The implementing class may or may not guarantee that the returned puzzles are orderable.
	 * @return A puzzle object from the implementing classes puzzle source.
	 */
	public Puzzle nextElement();
	
}
