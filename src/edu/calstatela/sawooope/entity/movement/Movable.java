package edu.calstatela.sawooope.entity.movement;

/**
 * An interface for objects that can be moved via touch.
 * 
 * @author Benji
 * 
 */
public interface Movable {
	/**
	 * 
	 */
	public static final int NORTH = 0;
	/**
	 * 
	 */
	public static final int SOUTH = NORTH + 1;
	/**
	 * 
	 */
	public static final int EAST = SOUTH + 1;
	/**
	 * 
	 */
	public static final int WEST = EAST + 1;

	/**
	 * 
	 */
	public static final int UNDETERMINED = WEST + 1;

	/**
	 * 
	 */
	public abstract void stop();

	/**
	 * 
	 * This method is used to move an entity that has been swiped
	 * 
	 * @param direction
	 *            direction to move
	 */
	public abstract void move(int direction);// called during swipe

	/**
	 * 
	 * This method is used to move at entity that is selected
	 * 
	 * @param col
	 * @param row
	 */
	public abstract void move(int col, int row);// called during tap to go

}
