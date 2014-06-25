package edu.calstatela.sawooope.entity;
import java.util.ArrayList;

import android.graphics.Bitmap;
public class Animation {

	private Bitmap[] frames;
	private int currentFrame;
	private int actionIndex = -1;
	
	//time 
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	public Animation(){
		
}
	
	public void setFrames(Bitmap[] frames){
		this.frames = frames;
		//System.out.println("frame count: " + frames.length);
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
}
	public int getCurrAction(){return actionIndex;}
	
	public void setFrames(ArrayList<Bitmap[]> sprites, int index){
		
		this.frames = sprites.get(index);
		this.actionIndex = index;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
		
	}
	
	public void resetCurrentAction(){
		actionIndex = -1;
	}
	
	
	public void setDelay(long d){
		delay = d;
}
	public void setFrame(int frameSet){
		currentFrame = frameSet;
		}
	
	public int getCurrFrame(){return currentFrame;}
	
	
	public void update(){
		
		if(delay == -1)return ;
		long elapsed = (System.nanoTime() - startTime)/1000000;
		//System.out.println("elapsed: " + elapsed);
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
			//ex.printStackTrace();
			System.out.println(null == frames);
			System.exit(1);
		}
		
	
	
		
		
}
	//getters
	
	
	public int getCurrentFrame(){return currentFrame;}
	public Bitmap getImage(){
		return frames[currentFrame];
	}
	public boolean hasPlayedOnce(){return playedOnce;}
	
	
	

}
