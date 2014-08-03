package edu.calstatela.sawooope.entity.creature.hunger;

public class Hunger {

	Edible food;
	private long life;// time in milliseconds
	private long start;// time hunger began in milliseconds

	public Hunger(long lifeSpan) {
		life = lifeSpan;
	}

	public void storeFood(Edible food) {
		this.food = food;
	}

	public boolean hasHealth() {

		return timeElapsed() < life;
	}

	public void eatFood() {

		float replenish = food.consume();

		start += (replenish * life);
	}

	public float getPercentHealthRemaining() {

		if (!hasHealth())
			return 0;

		return (float) (1.0 * (timeElapsed() / life));

	}

	private long timeElapsed() {
		return (System.currentTimeMillis() - start);
	}

}
