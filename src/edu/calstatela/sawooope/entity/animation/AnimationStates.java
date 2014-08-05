package edu.calstatela.sawooope.entity.animation;

/**
 * Each value represents a set of animation frames
 * 
 * @author Benji
 * 
 */
public interface AnimationStates {

	public static final int IDLE = 0;
	public static final int WALK = IDLE + 1;
	public static final int DEAD = WALK + 1;
	public static final int EAT = DEAD + 1;
	public static final int EMPTY = EAT + 1;

}
