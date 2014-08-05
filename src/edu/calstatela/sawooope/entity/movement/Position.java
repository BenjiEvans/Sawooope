package edu.calstatela.sawooope.entity.movement;

import edu.calstatela.sawooope.gamestates.levels.Level;

/**
 * A Dynamic/constantly changing location on the map
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

	/**
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 */
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
	 * Determines if positions are the same
	 * 
	 * @param pos
	 * @return true if they are the same position on the map
	 */
	public boolean equals(Position pos) {

		return equals(pos.getCol(), pos.getRow());
	}

	/**
	 * Determines if positions are the same
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 * @return true if this position has the column and row specified
	 * 
	 */
	public boolean equals(int col, int row) {

		return this.col == col && this.row == row;
	}

	/**
	 * Determines if this position has the column and row specified
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 * @return true if position (or next position) has the specified column and
	 *         row
	 */
	public boolean hasColRow(int col, int row) {

		if (equals(col, row))
			return true;

		if (next != null)
			return next.equals(col, row);

		return false;
	}

	/**
	 * Determines if this position(or it's next position) is the same as the
	 * position specified
	 * 
	 * @param p
	 *            position on map
	 * @return true if this position (or next position) are the same as the one
	 *         specified
	 */
	public boolean hasPosition(Position p) {

		if (equals(p))
			return true;

		if (next != null)
			return next.equals(p);

		return false;
	}

	/**
	 * sets the next position that the entity will move to
	 * 
	 * @param col
	 *            column of the next position
	 * @param row
	 *            row of the next position
	 */
	public void setNextPosition(int col, int row) {

		next = new Position(col, row);

	}

	/**
	 * removes next position
	 */
	public void removeNextPosition() {

		if (next != null)
			next = null;
	}

	/**
	 * Sets the current position to the next position
	 */
	public void setNewPosition() {

		col = next.getCol();
		row = next.getRow();
		x = next.getx();
		y = next.gety();

		next = null;
	}

	/**
	 * Determines whether or not the current position has reached it's next
	 * position
	 * 
	 * @return true if the current position is equal to the next position
	 */
	public boolean hasReachedNextPosition() {

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
		return false;
	}

	/**
	 * 
	 * @return the next position
	 */
	public Position getNextPosition() {
		return next;
	}

	/**
	 * Determines if the position has a next position
	 * 
	 * @return true if the position has a next position
	 */
	public boolean hasNextPosition() {

		return next != null;
	}

	/**
	 * Sets the update speed of the position change from the current position to
	 * the next position
	 * 
	 * @param dx
	 *            rate of increase in the x direction
	 * @param dy
	 *            rate of increase in the y direction
	 */
	public void setXYUpdateRate(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Reverses the direction of the positions current movement
	 */
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

	/**
	 * adds a destination at the column and row specified
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 */
	public void addDestination(int col, int row) {

		dest = new Destination(col, row);
	}

	/**
	 * Removes all destinations
	 */
	public void removeDestinations() {
		dest = null;
	}

	/**
	 * Determines if the position has a destination
	 * 
	 * @return true if the position has a destination
	 */
	public boolean hasDestination() {
		return dest != null;
	}

	/**
	 * 
	 * @return true if the current position has the same col and row as it's
	 *         destination
	 */
	public boolean hasReachedDestination() {

		return equals(dest.getCol(), dest.getRow());
	}

}
