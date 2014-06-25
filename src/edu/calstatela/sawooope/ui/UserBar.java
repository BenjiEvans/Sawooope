package edu.calstatela.sawooope.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.calstatela.sawooope.gamestates.levels.Level;


public class UserBar implements UIBarSettings{

	public static final int HEIGHT = (int) (32*1.5);
	private int height;
	private int width;
	private int y;
	UtilButton[] utils;
	private int currState;
	boolean active;
	private static float SCALE = 1f; 
	//Level level;
	
	public UserBar(Level level){
		
		SCALE = level.getDrawableScale();
		height = (int)(HEIGHT*SCALE);
		currState = NULL;
		width = level.getWidth();
		y = level.getHeight()- height;
		utils = new UtilButton[2];
		int pad = (int) (10*SCALE);
		utils[DECOY] = new DecoyButton(0,y,SCALE);	
		utils[BARRICADE] = new BarricadeButton(utils[DECOY].getmaxX()+pad,y,SCALE);
		
	}
	
	
	public void draw(Canvas g){
		
		Paint paint = new Paint();
		paint.setARGB(255, 0, 0, 0);
		paint.setStyle(Paint.Style.FILL);
		
		int startx = 0;
		int starty = y;
		int endx = startx+width;
		int endy = starty+height;
		
		
		Rect rect = new Rect(startx,starty,endx,endy);
		g.drawRect(rect, paint);
				
		for(UtilButton b: utils) b.draw(g);
		
		
	}
	
	public boolean isClicked(float y ){
		
		return  y > this.y;
		
	}
	
	public int gety(){return y;}
	
	public void registerAction(float x, float y){
		
		boolean nullState = true;
		for(UtilButton b: utils)
		{
			
			if(b.isClicked(x)){
				
				//System.out.print("Was Clicked!???");
				b.select();
				
				if(b.isSelected())
				{
					currState = b.getType();
					active = true;
					nullState = false;
					
				}
				
			}else b.setSelected(false);
			
			
		}
		
		if(nullState)
		{
			currState = NULL;
			active = false;
		}
		
		
		
	}
	
	public void registerAction(int buttonId){
		
		boolean nullState = true;
		
		for(int i = 0, size = utils.length; i < size; i++)
		{
			if(buttonId == i){
				
				utils[i].select();
				
				if(utils[i].isSelected())
				{
					currState = utils[i].getType();
					active = true;
					nullState = false;
					
				}
				
				
			}else{
				utils[i].setSelected(false);
			}
		}
		
		if(nullState)
		{
			currState = NULL;
			active = false;
		}
		
		
		
		
	}

	public int getCurrentState() {
		// TODO Auto-generated method stub
		return currState;
	}
	
	public boolean isActive(){
		
		return active;
	}
	
	
	public void setActive(boolean bool){
		
		active = false;
		utils[currState].select(); 
		currState = NULL;
		
	}
	
	public void dispose(){
		utils = null;
	}
	
	public static int getHeight(){return (int) (HEIGHT*SCALE);}
	

}
