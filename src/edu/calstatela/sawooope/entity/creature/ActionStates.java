package edu.calstatela.sawooope.entity.creature;

/**
 * These values correspond to an action a creature entity is performing
 * 
 * @author Benji
 * 
 */
public interface ActionStates {
	/**
     * 
     */
	public final int WALKING = 0;
	/**
	 * 
	 */
	public final int EATING = WALKING + 1;
	/**
	 * 
	 */
	public final int IDLE = EATING + 1;
	/**
	 * 
	 */
	public final int DIEING = IDLE + 1;

}
