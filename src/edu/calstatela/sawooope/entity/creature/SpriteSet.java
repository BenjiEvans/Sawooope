package edu.calstatela.sawooope.entity.creature;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;

public class SpriteSet {

	private HashMap<String, FrameList> map;
	/*
	 *  "n" will represent north
	 *  "s" will represent South
	 *  "w" will represent west
	 *  "e" will represent east
	 */
	
	
	SpriteSet(){
		
		map = new HashMap<String, FrameList>();	
		
		map.put("n", new FrameList());
		map.put("s", new FrameList());
		map.put("e", new FrameList());
		map.put("w", new FrameList());
	}
	
	public FrameList getFrames(int direction){
		
		switch(direction)
		{
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
	
	
	
	
	public class AnimationFrame{
		
		private FrameID id;
		private Bitmap[] frames;
		
		AnimationFrame(FrameID id, Bitmap[] frames){
			this.id = id;
			this.frames = frames;
		}
		
		public boolean isOfType(FrameID id){
			
			return id == this.id;
		}
		
		
	}
	
	public enum FrameID{
		EAT,WALK,IDLE,DEAD
	}
	
	public class FrameList{
		
		private ArrayList<AnimationFrame> list;
		
		FrameList(){
			
			list = new ArrayList<AnimationFrame>();			
		}
		
		
		public void setDeadFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.DEAD,img);
			setFrameByID(FrameID.DEAD,newFrame);
		}
		
		public void setIdleFrames(Bitmap[] img){
			
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.IDLE,img);
			setFrameByID(FrameID.IDLE,newFrame);
								
		}
		
		public void setWalkingFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.WALK,img);
			setFrameByID(FrameID.WALK,newFrame);
					
		}
		
		public void setEatingFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.EAT,img);
			setFrameByID(FrameID.EAT,newFrame);
		}
		
		private void setFrameByID(FrameID id, AnimationFrame newFrame){
			
			for(AnimationFrame f: list)
			{
				if(f.isOfType(id)){
					
					f = newFrame;
					return;
				}
			}
			list.add(newFrame);
						
		}
		
	}
	
}
