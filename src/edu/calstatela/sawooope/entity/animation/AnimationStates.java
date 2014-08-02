package edu.calstatela.sawooope.entity.animation;

public interface AnimationStates {

	public static final int IDLE = 0;
	public static final int WALK = IDLE+1;
	public static final int DEAD = WALK+1;
	public static final int EAT = DEAD+1;
}
