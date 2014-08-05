package edu.calstatela.sawooope.entity.creature.hunger;

public interface Hungery {

	/**
	 * this method should store the food that the entity wishes to eat and let
	 * the hunger object take care of consuming it
	 */
	public void eat(Edible food);

}
