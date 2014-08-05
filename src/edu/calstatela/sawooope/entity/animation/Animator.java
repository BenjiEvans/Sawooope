package edu.calstatela.sawooope.entity.animation;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * Animation uses sprites passed in by entities to create animations. It simply
 * takes in an array of images (frames) and flips through each image in the
 * array at certain time intervals(specified by the set delay). Each image in
 * the array is a frame.
 * 
 * @author Benji
 * 
 */
public class Animator {

	// animation vars
	private Bitmap[] frames;
	private int currentFrame;

	// time vars
	private long startTime;
	private long delay;

	private boolean playedOnce;

	/**
	 * 
	 */
	public Animator() {

	}

	/**
	 * Sets the animation frames using the images passed in
	 * 
	 * @param frames
	 *            images that represent a single animation
	 */
	public void setFrames(Bitmap[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	/**
	 * Sets the animation frames using the images passed in
	 * 
	 * @param sprites
	 *            list of frames
	 * @param index
	 *            index of the list of frames being used
	 */
	public void setFrames(ArrayList<Bitmap[]> sprites, int index) {

		this.frames = sprites.get(index);
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;

	}

	/**
	 * Sets how much time must past before the next frame (image) in the
	 * animation is displayed
	 * 
	 * @param d
	 *            time in milliseconds
	 */
	public void setDelay(long d) {
		delay = d;
	}

	/**
	 * Sets the current frame to the specified frame
	 * 
	 * @param frameSet
	 *            frame number
	 */
	public void setFrame(int frameSet) {
		currentFrame = frameSet;
	}

	/**
	 * Updates the animations based on the set frames
	 */
	public void update() {

		if (delay == -1)
			return;
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}

		try {

			if (currentFrame == frames.length) {
				playedOnce = true;
				currentFrame = 0;
			}

		} catch (Exception ex) {
			System.out.println(null == frames);
			System.exit(1);
		}

	}

	/**
	 * 
	 * @return the current frame
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * 
	 * @return the image in the current frame
	 */
	public Bitmap getImage() {
		return frames[currentFrame];
	}

	/**
	 * 
	 * @return true if the animation has cycled through at least once
	 */
	public boolean hasPlayedOnce() {
		return playedOnce;
	}

}
