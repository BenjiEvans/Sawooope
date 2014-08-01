package edu.calstatela.sawooope.entity.animation;

import java.util.ArrayList;
import java.util.HashMap;

import edu.calstatela.sawooope.entity.movement.Movable;

import android.graphics.Bitmap;

public class SpriteSet implements AnimationStates {

	private HashMap<String, FrameList> map;
	/*
	 *  "n" will represent north
	 *  "s" will represent South
	 *  "w" will represent west
	 *  "e" will represent east
	 */
	
	
	public SpriteSet(){
		
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
		
		private int id;//animation state
		private Bitmap[] frames;
		
		AnimationFrame(int id, Bitmap[] frames){
			this.id = id;
			this.frames = frames;
		}
		
		public boolean isOfType(int id){
			
			return id == this.id;
		}
		
		public Bitmap[] getFrames(){
			return frames;
		}
		
		
	}
	
	/*public enum FrameID{
		EAT,WALK,IDLE,DEAD
	}*/
	
	public class FrameList{
		
		private ArrayList<AnimationFrame> list;
		
		FrameList(){
			
			list = new ArrayList<AnimationFrame>();			
		}
		
		
		
		
		
		
		public Bitmap[] getDeadFrames(){
			return getFramesByID(DEAD);
		}
		
		public Bitmap[] getIdleFrames(){
			return getFramesByID(IDLE);
		}
		
		public Bitmap[] getWalkingFrames(){
			return getFramesByID(WALK);
		}
		
		public Bitmap[] getEatingFrames(){
			return getFramesByID(EAT);
		}
		
		public void setDeadFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(DEAD,img);
			setFramesByID(DEAD,newFrame);
		}
		
		public void setIdleFrames(Bitmap[] img){
			
			
			AnimationFrame newFrame = new AnimationFrame(IDLE,img);
			setFramesByID(IDLE,newFrame);
								
		}
		
		public void setWalkingFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(WALK,img);
			setFramesByID(WALK,newFrame);
					
		}
		
		public void setEatingFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(EAT,img);
			setFramesByID(EAT,newFrame);
		}
		
		private void setFramesByID(int id, AnimationFrame newFrame){
			
			for(AnimationFrame f: list)
			{
				if(f.isOfType(id)){
					
					f = newFrame;
					return;
				}
			}
			list.add(newFrame);
						
		}
		
		private Bitmap[] getFramesByID(int id){
			
			for(AnimationFrame f: list){
				
				if(f.isOfType(id))return f.getFrames();
			}
			
			return null;
		}
		
	}
	
}
