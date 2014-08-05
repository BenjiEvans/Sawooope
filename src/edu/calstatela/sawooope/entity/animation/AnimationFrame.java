package edu.calstatela.sawooope.entity.animation;

import android.graphics.Bitmap;

/**
 * AnimationFrame represents a specific animation (Walking, eating etc.). It
 * stores the frames of the specified animation.
 * 
 * @author Benji
 * 
 */
public class AnimationFrame {

	private int id;// animation state
	private Bitmap[] frames;

	AnimationFrame(int id, Bitmap[] frames) {
		this.id = id;
		this.frames = frames;
	}

	/**
	 * 
	 * @param id
	 *            (See AnimationStates Interface)
	 * @return true if the animation frames represent the AniamtionState
	 *         specified
	 */
	public boolean isOfType(int id) {

		return id == this.id;
	}

	/**
	 * 
	 * @return the animation's frames
	 */
	public Bitmap[] getFrames() {
		return frames;
	}

}
