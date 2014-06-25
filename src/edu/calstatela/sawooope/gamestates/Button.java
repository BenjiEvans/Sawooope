package edu.calstatela.sawooope.gamestates;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Button {
	
	private int x;
	private int y;
	private String title;
	private Bitmap img;
	
	protected Button(int x, int y , Bitmap img, String title){
		
		this.x = x;
		this.y = y;
		this.img = img;		
		this.title = title;
	}
	
	public String getTitle(){return title;}
	
	public int getMaxY(){
		
		return y+img.getHeight();
	}
	
	public void draw(Canvas g){
		
		g.drawBitmap(img, x, y, null);
	}
	
	public boolean isClicked(float x, float y){
		
		
		boolean withinx = x > this.x && x < this.x+img.getWidth();
		boolean withiny = y > this.y && y < this.y+img.getHeight();
		
		return withinx && withiny;
	}	
	
}
