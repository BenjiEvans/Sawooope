package edu.calstatela.sawooope.entity.movement;


/**
 * An interface for objects that can be moved via touch.
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
	public static final int SOUTH = NORTH+1;
	/**
	 * @property
	 */
	public static final int EAST = SOUTH+1;
	/**
	 * @property
	 */
	public static final int WEST = EAST+1;
	
	public static final int UNDETERMINED = WEST+1;

	/**
	 * 
	 */
	public abstract void stop();

	/**
	 * 
	 * @param direction
	 *            direction to move
	 */
	public abstract void move(int direction);//called during swipe 
	public abstract void move(int col, int row);//called during tap to go 

}
