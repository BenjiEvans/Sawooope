package edu.calstatela.sawooope.entity.animation;

import java.util.HashMap;
import edu.calstatela.sawooope.entity.movement.Movable;

/**
 * This sprite set is a map of animation frames (primarily for entities that are
 * creatures)
 * 
 * @author Benji
 * 
 */
public class SpriteSet implements AnimationStates {

	private HashMap<String, FrameList> map;

	/*
	 * "n" will represent north "s" will represent South "w" will represent west
	 * "e" will represent east
	 */

	/**
	 * Creates a map of sprite frames that can be set and accessed based on the
	 * animations being set/retrieved and the direction the entity is facing
	 */
	public SpriteSet() {

		map = new HashMap<String, FrameList>();

		map.put("n", new FrameList());
		map.put("s", new FrameList());
		map.put("e", new FrameList());
		map.put("w", new FrameList());
	}

	/**
	 * 
	 * @param direction
	 *            direction the entity is facing
	 * @return a list of frames facing in the direction specified
	 */
	public FrameList getFrames(int direction) {

		switch (direction) {
		case Movable.NORTH:
			return map.get("n");
		case Movable.SOUTH:
			return map.get("s");
		case Movable.EAST:
			return map.get("e");
		case Movable.WEST:
			return map.get("w");
		}

		return null;
	}

}
