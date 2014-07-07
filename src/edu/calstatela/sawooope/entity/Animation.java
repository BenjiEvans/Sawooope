package edu.calstatela.sawooope.entity;
import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * Animation uses sprites passed in by entities to create animations. 
 * It simply takes in an array of images (frames) and flips through each image in the array at certain time intervals. 
 * Each image in the array is a frame.
 * @author Benji
 *
 */
public class Animation {

	private Bitmap[] frames;
	private int currentFrame;
	private int actionIndex = -1;
	
	//time 
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	/**
	 * 
	 */
	public Animation(){
		
}
	
	/**
	 * Sets the animation using the images passed in
	 * @param frames images that represent a single animation
	 */
	public void setFrames(Bitmap[] frames){
		this.frames = frames;
		//System.out.println("frame count: " + frames.length);
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
}
	/**
	 * 
	 * @return if the entity is using an ArrayList to set the animation frames then it returns the index used to set the sprites other wise -1 is returned
	 */
	public int getCurrAction(){return actionIndex;}
	
	/**
	 * Sets the animation using the images passed in
	 * @param sprites list of frames
	 * @param index index of the list of frames being used
	 */
	public void setFrames(ArrayList<Bitmap[]> sprites, int index){
		
		this.frames = sprites.get(index);
		this.actionIndex = index;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
		
	}
	
	/**
	 * sets the actionIndex to -1 signifying that there is no action index
	 */
	public void resetCurrentAction(){
		actionIndex = -1;
	}
	
	/**
	 * Sets how much time must past before the next frame (image) in the animation is displayed
	 * @param d time in milliseconds
	 */
	public void setDelay(long d){
		delay = d;
}
	/**
	 * Sets the current frame to the specified value
	 * @param frameSet frame number
	 */
	public void setFrame(int frameSet){
		currentFrame = frameSet;
		}
	
	/**
	 * Updates the animations based on the set frames
	 */
	public void update(){
		
		if(delay == -1)return ;
		long elapsed = (System.nanoTime() - startTime)/1000000;
		if(elapsed > delay)
		{
			currentFrame++;
			startTime = System.nanoTime();
		}
		
		try{
			
			if(currentFrame == frames.length )
			{
				playedOnce = true;
				currentFrame = 0;
			}
			
		}catch(Exception ex){
			System.out.println(null == frames);
			System.exit(1);
		}
		
	
	
		
		
}
	/**
	 * 	
	 * @return the current frame
	 */
	public int getCurrentFrame(){return currentFrame;}
	
	/**
	 * 
	 * @return the image in the current frame
	 */
	public Bitmap getImage(){
		return frames[currentFrame];
	}
	/**
	 * 
	 * @return true if the animation has cycled through 1 or more times
	 */
	public boolean hasPlayedOnce(){return playedOnce;}
	
	
	

}
