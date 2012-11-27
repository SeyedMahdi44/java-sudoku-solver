package c02dt.sudoku.base;

import java.util.*;
import java.io.*;

/**
 * A cell object represents one cell in a Sudoku puzzle grid.<p>
 * It knows whether or not it contains a clue, whether it contains a value, 
 * what such a value is and the row, column, and box upon which it lies.
 *
 */
public class Cell extends Observable implements Cloneable, Serializable {
	
	static final long serialVersionUID = 01;
	final Integer row;
	final Integer column;
	final Integer box;
	
	private boolean isClue;
	private boolean isSet;
	private Integer value;
	
	/**
	 * Creates a new Cell object which has no value and as such not set or a clue.
	 *
	 */
	public Cell(Integer row, Integer column, Integer box) {
		this.row = row;
		this.column = column;
		this.box = box;
		isClue = false;
		isSet  = false;
	}
	
	/**
	 * Asserts that this cell contains a clue value and stores it.
	 * @param value The number which is this Cells clue.
	 */
	public void setClue(Integer value) {
		this.modified();
		this.value  = value;
		this.isClue = true;
		this.isSet  = true;
	}
	
	/**
	 * Asserts that this cell contains a value (big number) and stores it.
	 * <p>
	 * If this cell has been set as a clue then the set will have no effect.
	 * @param value The big number this Cell is to contain.
	 */
	public void setValue(Integer value) {
		if(isClue) {return;}
		this.modified();
		this.value = value;
		this.isSet = true;
	}
	
	/**
	 * Asserts that this cell does not contain a value (clue or big number).
	 *
	 */
	
	public void unSet() {
		this.modified();
		this.isClue = false;
		this.isSet  = false;
	}
	
	/**
	 * If this Cell contains a value (big number or clue), then it is defined as 'set'
	 * @return True if cell is set, false if not (unset).
	 */
	public Boolean isSet() {
		return this.isSet;
	}
	
	/**
	 * If this Cells current value was added with the setClue() method then it is a clue.
	 * @return True if this Cell contains a clue, false if not.
	 */
	public Boolean isClue() {
		return this.isClue;
	}

	/**
	 * A Cells value may either be a clue or a big number. Note it is the calling classes 
	 * responsibilty to check this Cell contains a value or erroneous data will be returned.
	 * @return The value (clue or big number) this Cells contains (assumes isSet()).
	 */
	public Integer getValue() {
		return this.value;
	}
	
	/**
	 * A cell always exists as part of a puzzle on a defined row, it is written as a static variable 
	 * when the Cell object is created. 
	 * <p>
	 * Although this variable is not used when accessing the Cell object (which is done via the 
	 * Puzzle class which contains the Units is lies in), it is useful to cache it at creation 
	 * time to reduce overhead later on.
	 * @return The row in which this cell lies
	 */
	public Integer getRow() {
		return row;
	}
	
	/**
	 * A cell always exists as part of a puzzle on a defined column, it is written as a static variable 
	 * when the Cell object is created. 
	 * <p>
	 * Although this variable is not used when accessing the Cell object (which is done via the 
	 * Puzzle class which contains the Units is lies in), it is useful to cache it at creation 
	 * time to reduce overhead later on.
	 * @return The column in which this cell lies
	 */
	public Integer getColumn() {
		return column;
	}
	
	/**
	 * A cell always exists as part of a puzzle on a defined box, it is written as a static variable 
	 * when the Cell object is created. 
	 * <p>
	 * Although this variable is not used when accessing the Cell object (which is done via the 
	 * Puzzle class which contains the Units is lies in), it is useful to cache it at creation 
	 * time to reduce overhead later on.
	 * @return The box in which this cell lies
	 */
	public Integer getBox() {
		return box;
	}
	
	
	/**
	 * If set returns the Cells value, else returns one space character to preserve alignment.
	 * @return The Cells value or one space character.
	 */
	public String toString() {
		if(this.isSet) {
			 return this.value.toString();
		}
		else {
			return " ";
		}
	}
	
	public Cell clone() {
		Cell clonedCell = new Cell(this.row, this.column, this.box);
		clonedCell.isClue = this.isClue;
		clonedCell.isSet = this.isSet;
		clonedCell.value = this.value;
		
		return clonedCell;
	}
	
	private void modified() {
		super.setChanged();
		super.notifyObservers();
		super.clearChanged();
	}
}