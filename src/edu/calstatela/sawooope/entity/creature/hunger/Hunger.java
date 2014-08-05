package edu.calstatela.sawooope.entity.creature.hunger;

/**
 * Hunger object keeps track of
 * 
 * @author Benji
 * 
 */
public class Hunger {

	Edible food;
	private long life;// time in milliseconds
	private long start;// time hunger began in milliseconds
	long eatTime;// how long eating animation should take
	long startEat;// time entity begins eating

	/**
	 * 
	 * @param lifeSpan
	 *            time in milliseconds the entity can go without eating food
	 */
	public Hunger(long lifeSpan) {
		life = lifeSpan;
	}

	/**
	 * Sets a reference to the food that should be eaten
	 * 
	 * @param food
	 *            edible object
	 */
	public void storeFood(Edible food) {
		this.food = food;
	}

	/**
	 * Check whether or not the entity has health
	 * 
	 * @return true there is time remaining for the entity to stay alive
	 */
	public boolean hasHealth() {

		return timeElapsed() < life;
	}

	/**
	 * consumes the stored food
	 */
	public void eatFood() {

		float replenish = food.consume();
		start += (replenish * life);
	}

	/**
	 * 
	 * @return the percentange of health that remains for the entity
	 */
	public float getPercentHealthRemaining() {

		if (!hasHealth())
			return 0;

		return (float) (1.0 * (timeElapsed() / life));

	}

	private long timeElapsed() {
		return (System.currentTimeMillis() - start);
	}

}
