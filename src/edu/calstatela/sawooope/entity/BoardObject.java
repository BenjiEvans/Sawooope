package edu.calstatela.sawooope.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.tilemap.TileMap;


public abstract class BoardObject implements BoardObjectIDS{
	
	//Position Variables
		protected static int mapx,mapy;
		protected Position position;
		
		
		//Graphic Renerdering variables 
		protected Animation animator;
		protected int drawx,drawy;
		protected int spriteWidth,spriteHeight;
	
		// other vars
		protected Level level;
		protected int id;
		protected boolean done;
		protected boolean blockable;
		
	
	protected BoardObject(int col, int row,Level level){
		
		int size = Level.getGridSize();
		position = new Position(col,row,col*size,row*size);
		animator = new Animation();
		this.level = level;
		//Log.i("DrawDebug","Col: "+col+" Row: "+row);
	}
	
	public static void setMapPosition(){
		
		mapx=TileMap.getMapx();
		mapy=TileMap.getMapy();
	}
		
	public abstract void draw(Canvas g);
	protected abstract void setSprites(GameView view);
	
	
	public boolean isBlockable(){return blockable;}
	public boolean isDoneUpdating(){return done;}
	
	public boolean isClicked(float screenx, float screeny){
				
		int mapx = (int) (position.getx()+TileMap.getMapx());
		int mapy = (int) (position.gety()+TileMap.getMapy());
		
		boolean withinx = screenx >= mapx && screenx <= mapx+spriteWidth;
		boolean withiny = screeny >= mapy && screeny <= mapy+spriteHeight;
		
		return withinx && withiny;
	}
	
	
	public void setDrawablePosition(){
		
		drawx = position.getMapxLocation(mapx);
		drawy = position.getMapyLocation(mapy);
		
	}	
	
	public int getRow(){return position.getRow();}
	public int getCol(){return position.getCol();}
	public double getX(){return position.getx();}
	public double getY(){return position.gety();}
	
	public boolean hasColRow(int col, int row){
		
		return position.hasColRow(col,row);
	}
	
	public boolean hasColRow(int[] pos){
		
		return position.hasColRow(pos[0],pos[1]);
		
	}
	
	public int[] getCenterPosition(){
		
		int[]pos = new int[2];
		pos[0] = (int)(position.getx()+(Level.getGridSize()/2));
		pos[1] = (int)(position.gety()+(Level.getGridSize()/2));
		
		return pos;
	}
	
	public int[] getXYPositionTopRight(){
		
		int[] pos = new int[2];
		pos[0] = (int)position.getx()+spriteWidth;
		pos[1] = (int)position.gety();
		
		return pos;
	}
	
	public int[] getXYPositionTopLeft(){
		
		int[] pos = new int[2];
		pos[0] = (int)position.getx();
		pos[1] = (int)position.gety();
		
		return pos;
		
	}
	
	public int[] getXYPositionBotRight(){
		
		int[] pos = new int[2];
		pos[0] = (int) position.getx()+spriteWidth;
		pos[1] = (int) position.gety()+spriteHeight;
		
		return pos;
	}
	
	public int[] getXYPositionBotLeft(){
		
		int[] pos = new int[2];
		pos[0] = (int)position.getx();
		pos[1] = (int)position.gety()+spriteHeight;
		
		return pos;
	}
	
	public int[] getColRowPosition(){
		
		int[] pos = new int[2];
		pos[0] = position.getCol();
		pos[1] = position.getRow();
		return pos;
	}
	
	public int getID(){return id;}
	
	public boolean isOffScreen(){
		int x = (int) position.getx();
		int y = (int) position.gety();
		if(x+spriteWidth+mapx < 0
			|| x+mapx > level.getWidth() 
			|| y+spriteHeight+mapy < 0
			|| y+mapy > level.getHeight()) return true;
		
		
		return false;
		
	}
	
	// must also create a draw line method 
	
	protected void drawRect(Canvas g,int x, int y, int width, int height, Paint paint){
		
		g.drawRect(Rectangle.getDrawableRect(x,y,width,height),paint);
		
	}
	
	protected void drawBitmap(Canvas g,Bitmap image,int x, int y){
		
		g.drawBitmap(image,x,y,null);
		
	}
	
	protected void drawRect(Canvas g, int x, int y, int width, int height, Paint paint,boolean scaleHeight, boolean scaleWidth){
		
		if(scaleHeight)GameView.drawScaledHeightRect(g,x,y,width,height,paint);
		else drawRect(g,x,y,width,height,paint);
		
	}
		
}
