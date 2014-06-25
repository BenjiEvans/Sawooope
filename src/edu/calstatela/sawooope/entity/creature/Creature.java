package edu.calstatela.sawooope.entity.creature;


import java.util.ArrayList;
import java.util.HashMap;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.Movable;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;




public abstract class Creature extends BoardObject implements Movable {
	
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	public static final int IDLE = 0;
	public static final int WALKING = 1;
	protected double speed;
	
	CollisionBox box;
	Position nextPosition;
	boolean walking;
	double dx;
	double dy;
	boolean facing[] = {false,false,false,false};
	HashMap<String, ArrayList<Bitmap[]>> sprites = new HashMap<String, ArrayList<Bitmap[]>>();
	
	Creature(int col, int row, Level level){
		super(col,row,level);
		setSprites(level.getGameView());
	}
	
	public boolean hasXY(int x, int y){
		
		double x2 = position.getx();
		double y2 = position.gety();
		
		boolean withinX = x >= x2 && x <= x2+spriteWidth;
		boolean withinY = y >= y2 && y <= y2+spriteHeight;
		
		return withinX && withinY;
	}
	
	public boolean hasColRowPosition(int col, int row){
		
		return hasNextColRow(col,row) || hasColRow(col,row);
	}
	
	public boolean hasNextColRow(int col, int row){
		
		if(nextPosition == null) return false;
		
		return nextPosition.getCol() == col && nextPosition.getRow() == row;
	}
	
	public boolean hasNextColRow(int[] pos){
		
		return hasNextColRow(pos[0],pos[1]);
	}
	
	
	
	public abstract void update();
	
	public void draw(Canvas g){
		setDrawablePosition();
		drawBitmap(g,animator.getImage(),drawx,drawy);
	}
	
	public boolean isClicked(float tempCol, float tempRow){
		
		return super.isClicked(tempCol, tempRow); 
	}

	
	
	protected void updateAnimation(){
		
		if(walking)
		{
			if(facing[NORTH])
			{
				if(animator.getCurrAction() != WALKING) animator.setFrames(sprites.get("North"),WALKING);
								
			}else if(facing[SOUTH])
			{
				if(animator.getCurrAction() != WALKING) animator.setFrames(sprites.get("South"),WALKING);
				
			}else if(facing[EAST])
			{
				if(animator.getCurrAction() != WALKING) animator.setFrames(sprites.get("East"), WALKING);
				
			}else if(facing[WEST])
			{
				if(animator.getCurrAction() != WALKING)animator.setFrames(sprites.get("West"), WALKING);			
			}
			
			animator.setDelay(150);	
			
		}else{
			animator.setDelay(-1);
			if(facing[NORTH])
			{
				//System.out.println("facing NORTH");
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("North"),IDLE);
				
			}else if(facing[SOUTH])
			{
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("South"), IDLE);
				
			}else if(facing[EAST])
			{
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("East"), IDLE);
	
			}else if(facing[WEST])
			{
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("West"), IDLE);	
				
			}
		//	animator.update();
		}
		
		animator.update();
		
	}
	
	protected boolean reachedNextPosition(){
		
		if(dx != 0)
		{
			if(dx > 0)
			{
				if(position.getx() >= nextPosition.getx())return true;
				return false;
			}
			else 
			{
				if(position.getx() <= nextPosition.getx()) return true;
				return false;
			}
			
		}
		
		
		if(dy != 0)
		{
			if(dy > 0)
			{
				if(position.gety() >= nextPosition.gety())return true;
				return false;
			}
			else 
			{
				if(position.gety() <= nextPosition.gety()) return true;
				return false;
			}
			
		}
		
		return true;

	}
	
	protected void setNewPosition(){
		
		//walking = false;

		
		position = new Position(nextPosition);
		nextPosition = null;
		//dx = 0;
		//dy = 0;
		
	}
	
	/*protected void setMovablePosition(){
		
		int size = Level.GRID_SIZE;
		int x = position.getCol()*size;
		int y = position.getRow()*size;
		
		position.setXY(x,y);
		
		walking = true;
		
	}*/
	
	protected void setFacing(int dir){
		
		if(dir < 0 || dir > facing.length)return;
		for(int i = 0; i < facing.length;i++)
		{
			facing[i] = false;
		}
		
		facing[dir] = true;
		animator.resetCurrentAction();
		
	}
	
	protected void setSprites(GameView view){
		
		String[] spriteStates = {"South","West","East","North"};
		
		for(int i = 0; i < spriteStates.length; i++)
		{	
			ArrayList<Bitmap[]> frames = new ArrayList<Bitmap[]>();
			
			sprites.put(spriteStates[i], frames);
		}
	}
	
	/*protected void setTargetPosition(){
		
		targetX = nextCol*Level.GRID_SIZE;
		targetY = nextRow*Level.GRID_SIZE;
	}*/
	
	/*public void move(int col, int row){
		
		int direction = getDirectionOf(col,row);
		
		setFacing(direction);
		setMovablePosition();
		nextCol = col;
		nextRow = row;
		setTargetPosition();
		
		switch(direction){
		case NORTH:
			prepareMovementNorth();
			break;
		case SOUTH:
			prepareMovementSouth();
			break;
		case WEST:
			prepareMovementWest();
			break;
			
		case EAST:
			prepareMovementEast();
			break;
			
		}
		
	}*/
	
	protected int getDirectionOf(int col, int row) {
		
		int thisCol = position.getCol();
		int thisRow = position.getRow();
		
		if(thisCol == col){
			
			if(row > thisRow) return SOUTH;
			else if(row < thisRow) return NORTH;
			
		}
		
		if(thisRow == row){
			
			if(col > thisCol)return EAST;
			else if(col < thisCol) return WEST; 
			
		}
		
		//this signifies an error
		return -1;
	}

	public void moveNorth(){
		
		if(!facing[NORTH])setFacing(NORTH);
		walking = true;
		int nextCol = position.getCol();
		int nextRow = position.getRow()-1;
		int size = Level.getGridSize();
		nextPosition = new Position(nextCol, nextRow, nextCol*size,nextRow*size);
		dx = 0;
		dy = -speed;
		
		
	}
	
	public void moveSouth(){
		
		if(!facing[SOUTH])setFacing(SOUTH);
		walking = true;
		int size = Level.getGridSize();
		int nextCol = position.getCol();
		int nextRow = position.getRow()+1;
		nextPosition = new Position(nextCol, nextRow, nextCol*size,nextRow*size);
		dx = 0;
		dy = speed;
		
		
	}
	
	public void moveEast(){
		
		if(!facing[EAST])setFacing(EAST);
		walking = true;
		int size = Level.getGridSize();
		int nextCol = position.getCol()+1;
		int nextRow = position.getRow();
		nextPosition = new Position(nextCol, nextRow, nextCol*size,nextRow*size);
		dx = speed;
		dy = 0;
		
	}
		
	public void moveWest(){
		
		if(!facing[WEST])setFacing(WEST);
		walking = true;
		int size = Level.getGridSize();
		int nextCol = position.getCol()-1;
		int nextRow = position.getRow();
		nextPosition = new Position(nextCol, nextRow, nextCol*size,nextRow*size);
		dx = -speed;
		dy = 0;
		
		
	}
	
	public void stay(){
		
		walking = false;
		nextPosition = null;
		dx = 0;
		dy = 0;
		
	}
	
		
	protected boolean northValid(){
		
		if(position.getRow()-1 < 0)return false;
		
		return true;
		
	}
	
	protected boolean southValid(){
		
		//if(row+1 >= level.getNumRows())return false;
		
		return true;
	}
	
	protected boolean eastValid(){
		
	//	if(col+1 >= level.getNumCols())return false;
		
		return true;
	}
	
	protected boolean westValid(){
		
		if(position.getCol()-1 < 0)return false;
		
		return true;
	}
	
	
	/*public boolean hasNext(int col, int row){
		
		return this.nextCol == col && this.nextRow == row;
	}*/
	
	public int getNextCol(){
		
		if(nextPosition == null) return position.getCol();
		
		return nextPosition.getCol();
		
	}
	public int getNextRow(){
		
		if(nextPosition == null) return position.getRow();
		
		return nextPosition.getRow();
	}
	public int getSpriteWidth(){return spriteWidth;}
	public int getSpriteHeight(){return spriteHeight;}
	
	public CollisionBox getCollisionBox(){
		return box;
	}
	
	
	
		
}
