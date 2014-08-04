package edu.calstatela.sawooope.entity.movement;

import edu.calstatela.sawooope.gamestates.levels.Level;

/**
 * Position stores the row and column, as well as the x,y coordinates to
 * describe any location on the map.
 * 
 * @author Benji
 * 
 */
public class Position {

	private int col, row;
	private double x, y;
	private float dx, dy;
	private Position next;
	private Destination dest;

	public Position(int col, int row) {

		this.col = col;
		this.row = row;
		int size = Level.getGridSize();
		x = col * size;
		y = row * size;
	}

	/**
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 * @param x
	 *            x location on map
	 * @param y
	 *            y location on map
	 */
	public Position(int col, int row, double x, double y) {
		this.col = col;
		this.row = row;
		this.x = x;
		this.y = y;

	}

	/**
	 * 
	 * @param position
	 */
	/*
	 * public Position(Position position) {
	 * 
	 * this.col = position.getCol(); this.row = position.getRow(); this.x =
	 * position.getx(); this.y = position.gety(); }
	 */

	/**
	 * 
	 * @param mapx
	 *            TileMap's x position
	 * @return the x position it has on the screen
	 */
	public float getMapxLocation(double mapx) {
		return (float) (x + mapx);
	}

	/**
	 * 
	 * @param mapy
	 *            TileMap's y position
	 * @return the y position it has on the screen
	 */
	public float getMapyLocation(double mapy) {
		return (float) (y + mapy);
	}

	/**
	 * 
	 * @return the x coordinate on the map
	 */
	public double getx() {
		return x;
	}

	/**
	 * 
	 * @return the y coordinate on the map
	 */
	public double gety() {
		return y;
	}

	/**
	 * 
	 * @return current row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * 
	 * @return current column
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Increments the x and y coordinates by the amount specified
	 * 
	 * @param dx
	 *            amount to increment x
	 * @param dy
	 *            amount to increment y
	 */
	public void updateXY() {

		x += dx;
		y += dy;

	}

	/**
	 * Determines if positions are the same by checking column and row
	 * 
	 * @param pos
	 * @return true if they are the same position on the map
	 */
	public boolean equals(Position pos) {

		return equals(pos.getCol(), pos.getRow());
	}

	public boolean equals(int col, int row) {

		return this.col == col && this.row == row;
	}

	public boolean hasColRow(int col, int row) {

		if (equals(col, row))
			return true;

		if (next != null)
			return next.equals(col, row);

		return false;
	}

	public boolean hasPosition(Position p) {

		if (equals(p))
			return true;

		if (next != null)
			return next.equals(p);

		return false;
	}

	public void setNextPosition(int col, int row) {

		next = new Position(col, row);

	}

	public void removeNextPosition() {

		if (next != null)
			next = null;
	}

	public void setNewPosition() {

		col = next.getCol();
		row = next.getRow();
		x = next.getx();
		y = next.gety();

		next = null;
	}

	public boolean hasReachedNextPosition() {
		/**
		 * FINISH IMPLEMENTATION FOOOOL!
		 */

		if (dx != 0) {
			if (dx > 0) {
				if (x >= next.getx())
					return true;
				return false;
			} else {
				if (x <= next.getx())
					return true;
				return false;
			}

		}

		if (dy != 0) {
			if (dy > 0) {
				if (y >= next.gety())
					return true;
				return false;
			} else {
				if (y <= next.gety())
					return true;
				return false;
			}

		}
		/*
		 * boolean sameCol = next.getCol() == col; boolean sameRow =
		 * next.getRow() == row;
		 * 
		 * if(sameCol && sameRow){
		 * 
		 * return x >= next.getx() && y >= next.gety();
		 * 
		 * } else if (sameRow) {
		 * 
		 * int nCol = next.getCol();
		 * 
		 * if (nCol > col) {// next col is to the right
		 * 
		 * return x >= next.getx(); } else if (nCol < col) {// next col is to
		 * the left
		 * 
		 * return x <= next.getx(); }
		 * 
		 * } else if (sameCol) {
		 * 
		 * int nRow = next.getRow();
		 * 
		 * if (nRow > row) {// next row is below
		 * 
		 * return y >= next.gety();
		 * 
		 * } else if (nRow < row) {// next row if above
		 * 
		 * return y <= next.gety();
		 * 
		 * } }
		 */

		return false;

	}

	public Position getNextPosition() {
		return next;
	}

	public boolean hasNextPosition() {

		return next != null;
	}

	public void setXYUpdateRate(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void reverseDirection() {

		// find the direction you are headed
		int direction = Movable.UNDETERMINED;
		if (dx > 0)
			direction = Movable.EAST;
		else if (dx < 0)
			direction = Movable.WEST;
		else if (dy < 0)
			direction = Movable.NORTH;
		else if (dy > 0)
			direction = Movable.SOUTH;

		// set next position to be opossite of the direction headed
		int col = next.getCol();
		int row = next.getRow();
		// reverse the direction the possition is updated
		dx = -dx;
		dy = -dy;
		switch (direction) {
		case Movable.NORTH:// move south
			next = new Position(col, row + 1);
			return;
		case Movable.SOUTH:// move north
			next = new Position(col, row - 1);
			return;
		case Movable.EAST:// move west
			next = new Position(col - 1, row);
			return;
		case Movable.WEST:// move east
			next = new Position(col + 1, row);
			return;
		}

	}

	public void addDestination(int col, int row) {

		dest = new Destination(col, row);
	}

	public void removeDestinations() {
		dest = null;
	}

	public boolean hasDestination() {
		return dest != null;
	}
	
	public boolean hasReachedDestination(){
		
		return equals(dest.getCol(),dest.getRow());
	}

}
