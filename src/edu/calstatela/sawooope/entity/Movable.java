package edu.calstatela.sawooope.entity;

public interface Movable {
	
	public static int NULL_DIRECTION = -1;
	public abstract void stay();
	public abstract void moveNorth();
	public abstract void moveSouth();
	public abstract void moveEast();
	public abstract void moveWest();
	public abstract void moveRandom();
	public abstract void moveTo(Position pos);
}
