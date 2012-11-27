package c02dt.sudoku.base;

import java.util.*;
import java.io.*;

public class Puzzle implements Cloneable, Serializable {
	
	private ArrayList<Unit> rows;
	private ArrayList<Unit> cols;
	private ArrayList<Unit> boxes;
	
	private Integer cardinality;
	private Boolean modified;
	private Integer conflicts;
	
	/**
	 * The constructor creates a new empty Sudoku puzzle containing 81 unset clues.
	 * <p>
	 * A new puzzle will have a cardinality of 0, will be unmodified, and will contain 0 conflicts.
	 */
	public Puzzle() {
		
		rows  = new ArrayList<Unit>(9);
		cols  = new ArrayList<Unit>(9);
		boxes = new ArrayList<Unit>(9);
		
		for(int i = 0; i < 9; i++) {
			rows.add(new Unit());
			cols.add(new Unit());
			boxes.add(new Unit());
		}
		
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				int b = this.boxNumber(r, c);
				Cell thisCell = new Cell(r, b, c);
				rows.get(r).setCell(c, thisCell);
				cols.get(c).setCell(r, thisCell);
				boxes.get(b).setCell(this.boxPosition(r, c), thisCell);
			}
		}
		
		cardinality = 0;
		modified 	= false;
		conflicts 	= 0;
		
	}
	
	/**
	 * Sets the clue of a cell on the Sudoku board.
	 * <p>
	 * The clue flag for this cell will be set to True.
	 * Calling this method will increment the cardinality of the puzzle by 1.
	 * @param row The row of the value to change
	 * @param col The column of the value to change
	 * @param value The new value (big number) for the specified cell
	 */
	public void setClue(Integer row, Integer col, Integer value) {
		getCell(row, col).setClue(value);
		
		cardinality++;
		modified = true;
	}
	
	/**
	 * Sets the value (big number) of a cell on the Sudoku board.
	 * <p>
	 * Calling this method will automatically increment the cardinality of the puzzle by 1
	 * only if the cell is being set (to a non 0 value), or unset (to 0).
	 * @param row The row of the value to change
	 * @param col The column of the value to change
	 * @param value The new value (big number) for the specified cell
	 */
	public void setValue(Integer row, Integer col, Integer value) {
		
		if(value == 0) {
			if(getCell(row, col).isSet()) {
				cardinality--;
			}
			getCell(row, col).unSet();
		}
		
		else {
			if(!getCell(row, col).isSet()) {
				cardinality++;
			}
			getCell(row, col).setValue(value);
		}
		
		modified = true;
	}
	
	/**
	 * This returns the clue the specified cell contains, 0 indicates it is not a clue.
	 * @param row The row of the clue to return
	 * @param col The column of the clue to return
	 * @return the value of the current cell where 0 indictates it is not a clue.
	 */
	public Integer getClue(Integer row, Integer col) {
		if(getCell(row, col).isClue()) {
			return getCell(row, col).getValue();
		}
		else {
			return 0;
		}
	}
	
	/**
	 * This returns the value (big number or clue) of the specified cell, 0 indicates unset.
	 * @param row The row of the value to return
	 * @param col The column of the value to return
	 * @return the value of the current cell where 0 indictates it is unset.
	 */
	public Integer getValue(Integer row, Integer col) {
		if(getCell(row, col).isSet()) {
			return getCell(row, col).getValue();
		}
		else {
			return 0;
		}
	}
	
	/**
	 *  This method return the the sum of duplicate values in each unit.
	 *  <p>
	 *  Each unit may contain each number 1 to 9 once. The conflict count is incremented by 1 each time a 
	 *  number occurs more than once in a unit. So if a unit contains [1,3,4,1,1], its conflict count is 2.
	 * @return The the sum of duplicate values in each unit.
	 */
	public Integer conflictCount() {
		
		if(modified) {
			conflicts = 0;
			
			for(Unit x: rows) {
				conflicts += x.conflictCount();
			}
			
			for(Unit x: cols) {
				conflicts += x.conflictCount();
			}
			
			for(Unit x: boxes) {
				conflicts += x.conflictCount();
			}
		}
		
		return conflicts;
	}
	
	/**
	 *  This method checks if a puzzle is still 'valid'.
	 *  <p>
	 *  A valid puzzle is one which has no conflicting numbers in each row column and box.
	 * @return True if the puzzle is valid in its current state, False if not.
	 */
	public Boolean isValid() {
		return (conflictCount() == 0);
	}
	
	/**
	 * This method calls isValid and if it is True and all 81 cells contain values then the puzzled is solved.
	 * @return True if the puzzle has been solved, false if not.
	 */
	public Boolean isSolved() {
		return (isValid() && (cardinality == 81));
	}
	
	/**
	 * Returns the current cardinality of the puzzle, which is defined as the number of cells in the puzzle 
	 * which contain a value (clue or big number).
	 * @return The number of cells in the puzzle which contain a value.
	 */
	public Integer getCardinality() {
		return cardinality;
	}
	
	/**
	 * This is used when a copy of the puzzle is needed, such that updates to the returned puzzle 
	 * will not affect the original.
	 * @return An exact copy of this Puzzle.
	 */
	public Puzzle clone() {
		Puzzle clonedPuzzle = new Puzzle();
		
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				Cell thisCell = this.getCell(r, c).clone();
				
				clonedPuzzle.rows.get(r).setCell(c, thisCell);
				clonedPuzzle.cols.get(c).setCell(r, thisCell);
				clonedPuzzle.boxes.get(this.boxNumber(r, c)).setCell(this.boxPosition(r, c), thisCell);
			}
		}
		
		clonedPuzzle.cardinality = this.cardinality;
		
		return clonedPuzzle;
	}
	
	/**
	 * Produces a string representation of the puzzle, this shall be a 9 columns and 9 rows in size,
	 * separated by new line characters. 
	 * @return A string representation of this puzzle. 
	 */
	public String toString() {
		
		String out  = new String();
		
		for(Unit x : rows) {
			out += x.toString() + "\n";
		}
		
		return out;
	}
	
	private Cell getCell(Integer row, Integer col) {
		return this.rows.get(row).get(col);
	}
	
	private Integer boxNumber(Integer row, Integer col) {
		
		Double box = ((Math.floor(row/3)*3) + (Math.floor(col/3)));
		return box.intValue();
	}
	
	private Integer boxPosition(Integer row, Integer col) {
		Double pos = ((row - (Math.floor(row / 3) * 3)) * 3) + (col - (Math.floor(col / 3) * 3));
		return pos.intValue();
	}
}
