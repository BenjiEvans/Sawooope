package edu.calstatela.sawooope.entity.animation;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * FrameList is literally what it sounds like, a list of frames. It stores a
 * list of AnimationFrames.
 * 
 * @author Benji
 * 
 */
public class FrameList implements AnimationStates {

	private ArrayList<AnimationFrame> list;

	FrameList() {

		list = new ArrayList<AnimationFrame>();
	}

	/**
	 * 
	 * @return frames corresponding to dead sprites
	 */
	public Bitmap[] getDeadFrames() {
		return getFramesByID(DEAD);
	}

	/**
	 * 
	 * @return frames corresponding to idle sprites
	 */
	public Bitmap[] getIdleFrames() {
		return getFramesByID(IDLE);
	}

	/**
	 * 
	 * @return frames corresponding to walking sprites
	 */
	public Bitmap[] getWalkingFrames() {
		return getFramesByID(WALK);
	}

	/**
	 * 
	 * @return frames corresponding to eating sprites
	 */
	public Bitmap[] getEatingFrames() {
		return getFramesByID(EAT);
	}

	/**
	 * Sets the animation frames for when the entity is dead
	 * 
	 * @param img
	 *            dead sprite frames
	 */
	public void setDeadFrames(Bitmap[] img) {

		AnimationFrame newFrame = new AnimationFrame(DEAD, img);
		setFramesByID(DEAD, newFrame);
	}

	/**
	 * Sets the animation frames for when the entity is idle
	 * 
	 * @param img
	 *            idle sprite frames
	 */
	public void setIdleFrames(Bitmap[] img) {

		AnimationFrame newFrame = new AnimationFrame(IDLE, img);
		setFramesByID(IDLE, newFrame);

	}

	/**
	 * Sets the animation frames for when the entity is walking
	 * 
	 * @param img
	 *            walking sprite frames
	 */
	public void setWalkingFrames(Bitmap[] img) {

		AnimationFrame newFrame = new AnimationFrame(WALK, img);
		setFramesByID(WALK, newFrame);

	}

	/**
	 * Sets the animation frames for when the entity is eating
	 * 
	 * @param img
	 *            eating sprite frames
	 */
	public void setEatingFrames(Bitmap[] img) {

		AnimationFrame newFrame = new AnimationFrame(EAT, img);
		setFramesByID(EAT, newFrame);
	}

	private void setFramesByID(int id, AnimationFrame newFrame) {

		for (AnimationFrame f : list) {
			if (f.isOfType(id)) {

				f = newFrame;
				return;
			}
		}
		list.add(newFrame);

	}

	private Bitmap[] getFramesByID(int id) {

		for (AnimationFrame f : list) {

			if (f.isOfType(id))
				return f.getFrames();
		}

		return null;
	}

}