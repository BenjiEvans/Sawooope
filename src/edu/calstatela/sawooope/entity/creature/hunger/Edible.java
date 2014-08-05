package edu.calstatela.sawooope.entity.creature.hunger;

/**
 * Used for entities that can be eaten by other entities
 * 
 * @author Benji
 * 
 */
public interface Edible {

	/**
	 * called when the object is actually being eaten (called by hunger object).
	 * return the percent of "health" if should replenish for the entity that is
	 * consuming it.(return a number from 0-1 )
	 */
	public float consume();
}
