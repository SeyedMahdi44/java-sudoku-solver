package c02dt.sudoku.base;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;

/**
 * The class can be used to load a set of Sudoku puzzle in from a text file. <p>
 *
 * Incoming files should be in the standard format where rows are listed back to 
 * back with no special delimitation. Thus every 81 characters should represent one 
 * puzzle - with the number of characters in the file being a multiple of 81.
 *
 */
public class FileLoader implements PuzzleLoader {
	
	private static BufferedReader 	data;
	private static Boolean			hasNextPuzzle;
	private static Puzzle 			nextPuzzle;
	private static Puzzle			thisPuzzle;
	
	private static final int PERIOD	= Character.valueOf('.');
	private static final int SPACE	= Character.valueOf(' ');
	private static final int DASH	= Character.valueOf('-');
	private static final int ZERO	= Character.valueOf('0');
	
	public FileLoader(String file) {
		
		try {
			data = new BufferedReader(new FileReader(file));
			
		} catch (java.io.FileNotFoundException  e) {
			System.err.println("Could not read in file: "+ e);
		}
		
		FileLoader.prepareNextPuzzle();
	}
	
	public Puzzle nextElement() {
		thisPuzzle = nextPuzzle;
		FileLoader.prepareNextPuzzle();
		return thisPuzzle;
	}
	
	public boolean hasMoreElements() {
		return hasNextPuzzle;
	}
	
	private static void prepareNextPuzzle() {
		
		try {
			nextPuzzle = new Puzzle();
			int i = 0;
			
			while (i < 81) {
				int charValue = data.read();
				
				// If this is indicates a non clue cell consume it
				if(charValue == PERIOD || charValue == DASH || charValue == SPACE) {
					i++;
					
					// Else see if it contains a clue
				} else if(ZERO < charValue && charValue < (ZERO + 10)) {
					nextPuzzle.setClue(i/9, i%9, charValue - ZERO);
					i++;
					
					// If we're at the end of the filea
				} else if(charValue == -1) {
					throw new EOFException("Reached end of file while reading puzzle");
				}
			}
			hasNextPuzzle = true;
			
		} catch(EOFException e) {
			hasNextPuzzle = false;
		} catch(java.io.IOException e) {
			hasNextPuzzle = false;
		}
	}
	
}
