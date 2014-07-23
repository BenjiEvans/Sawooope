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
		
		public Bitmap[] getFrames(){
			return frames;
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
		
		
		
		
		
		
		public Bitmap[] getDeadFrames(){
			return getFramesByID(FrameID.DEAD);
		}
		
		public Bitmap[] getIdleFrames(){
			return getFramesByID(FrameID.IDLE);
		}
		
		public Bitmap[] getWalkingFrames(){
			return getFramesByID(FrameID.WALK);
		}
		
		public Bitmap[] getEatingFrames(){
			return getFramesByID(FrameID.EAT);
		}
		
		public void setDeadFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.DEAD,img);
			setFramesByID(FrameID.DEAD,newFrame);
		}
		
		public void setIdleFrames(Bitmap[] img){
			
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.IDLE,img);
			setFramesByID(FrameID.IDLE,newFrame);
								
		}
		
		public void setWalkingFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.WALK,img);
			setFramesByID(FrameID.WALK,newFrame);
					
		}
		
		public void setEatingFrames(Bitmap[] img){
			
			AnimationFrame newFrame = new AnimationFrame(FrameID.EAT,img);
			setFramesByID(FrameID.EAT,newFrame);
		}
		
		private void setFramesByID(FrameID id, AnimationFrame newFrame){
			
			for(AnimationFrame f: list)
			{
				if(f.isOfType(id)){
					
					f = newFrame;
					return;
				}
			}
			list.add(newFrame);
						
		}
		
		private Bitmap[] getFramesByID(FrameID id){
			
			for(AnimationFrame f: list){
				
				if(f.isOfType(id))return f.getFrames();
			}
			
			return null;
		}
		
	}
	
}
