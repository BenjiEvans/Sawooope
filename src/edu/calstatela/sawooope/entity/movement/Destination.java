package edu.calstatela.sawooope.entity.movement;

/**
 * A static/definite location on the map.
 * 
 * @author Benji
 * 
 */
public class Destination {

	int col, row;

	/**
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 */
	Destination(int col, int row) {
		this.col = col;
		this.row = row;
	}

	/**
	 * 
	 * @return column on map
	 */
	public int getCol() {
		return col;
	}

	/**
	 * 
	 * @return row on map
	 */
	public int getRow() {
		return row;
	}

}
