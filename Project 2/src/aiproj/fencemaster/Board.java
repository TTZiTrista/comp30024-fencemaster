package aiproj.fencemaster;

import java.util.ArrayList;

/**
 * 
 * @author Maxim Lobanov (mlobanov) & Rongduan Zhu (rz)
 *
 */

public class Board {
	
	private ArrayList<ArrayList<Cell>> cellArray;
	private int dimension;
	private int numRows;
	
	/**
	 * Board constructor
	 * @param dimension How big each edge of board is
	 */
	public Board (int dimension) {
		this.dimension = dimension;
		this.numRows = dimension * 2 - 1;
		
		// need to check which cells are valid and which aren't
		// invalid cells content = X, valid unfilled cells content = -
		cellArray = new ArrayList<ArrayList<Cell>>(this.numRows);
		for (int i = 0; i < this.numRows; i++) {
			cellArray.add(new ArrayList<Cell>());
			for(int j = 0; j < this.numRows; j++) {
				cellArray.get(i).add(new Cell(i, j));
			}
			
		}
	}
	
	/**
	 * @param row, row index, 0 based
	 * @param column, column index, 0 based
	 * @return the cell at this position
	 */
	public Cell getCell(int row, int column) {
		// check for valid position
		return cellArray.get(row).get(column);
	}
	
	/**
	 * Converts the board to a string, same format as inputs given by lecturer/tutors
	 */
	@Override
	public String toString() {
		String boardString = new String();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numRows; j++) {
				boardString += getCell(i, j).getContent();
			}
			boardString += "\n";
		}
		return boardString;
	}

	public ArrayList<ArrayList<Cell>> getCellArray() {
		return cellArray;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

}
