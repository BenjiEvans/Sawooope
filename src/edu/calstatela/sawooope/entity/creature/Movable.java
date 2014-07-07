package edu.calstatela.sawooope.entity.creature;

/**
 * Movable should be implemented by any Entities capable of movement
 * (Creatures).
 * 
 * @author Benji
 * 
 */
public interface Movable {
	/**
	 * @property
	 */
	public static final int NORTH = 0;
	/**
	 * @property
	 */
	public static final int SOUTH = 1;
	/**
	 * @property
	 */
	public static final int EAST = 2;
	/**
	 * @property
	 */
	public static final int WEST = 3;

	/**
	 * 
	 */
	public abstract void stop();

	/**
	 * 
	 * @param direction
	 *            direction to move
	 */
	public abstract void move(int direction);

}
