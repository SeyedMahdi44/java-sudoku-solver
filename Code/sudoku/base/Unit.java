package c02dt.sudoku.base;

import java.util.*;
import java.io.*;

public class Unit implements Observer, Serializable {
	
	private ArrayList<Cell> cells;
	private boolean modified;
	private Integer conflicts;
	private BitSet numberRegister;
	
	/**
	 * Creates a new empty unit with the capacity to hold 9 Cell objects.
	 *
	 */
	public Unit() {
		cells = new ArrayList<Cell>(9);
		numberRegister = new BitSet(9);
		conflicts = 0;
		modified = false;
	}

	/**
	 * Sets a pointer to the given Cell object at position in the Unit.
	 * @param position The index of the Cell object within the Unit.
	 * @param cell The Cell object to reference the pointer to.
	 */
	public void setCell(Integer position, Cell cell) {
		cell.addObserver(this);
		
		try {
			cells.set(position, cell);
		} 
		catch(IndexOutOfBoundsException e) {
			cells.add(position, cell);
		}
				
		modified = true;
	}
	
	/**
	 * This returns the Cell object which lies at index in this Unit.
	 * <p>
	 * Note this returns a Cell object, which may not be set. It is the calling classe's
	 * responsibility to check the for existance before accessing the Cell's value.
	 * @param index
	 * @return Cell object which lies at index in this Unit
	 */
	public Cell get(Integer index) {
		return cells.get(index);
	}

	/**
	 *  This method return the the sum of duplicate values in this unit.
	 *  <p>
	 *  Each unit may contain each number 1 to 9 once. The conflict count is incremented by 1 each time a 
	 *  number occurs more than once. So if this unit contains [1,3,4,1,1], its conflict count is 2.
	 * @return The the sum of duplicate values in this unit.
	 */
	public Integer conflictCount() {
		
		if(modified) {
			conflicts = 0;
			numberRegister.clear();
			
			for(Cell x: cells) {
				if(x.isSet()) {
					
					if(numberRegister.get(x.getValue())) {
						conflicts++;
					}
					else {
						numberRegister.set(x.getValue());
					}
				}
			}
			modified = false;
		}
		
		return conflicts;
	}
	
	public void update(Observable cell, Object arg) {
		this.modified = true;
	}
	
	/**
	 * Returns a list representation of this Units contents, in order 
	 */
	
	public String toString() {
		return cells.toString();
	}
}
