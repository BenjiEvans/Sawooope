package edu.calstatela.sawooope.entity.creature;

import java.util.ArrayList;
import java.util.HashMap;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.entity.Rectangle;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.gamestates.levels.LevelInputProcessor.TouchPosition;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.tilemap.TileMap;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Creature is essentially an entity that is capable of movement. Because of
 * that, this class implements move able.
 * 
 * @author Benji
 */

public abstract class Creature extends BoardObject implements Movable {

	// update states: (see sheep class for example )
	/*
	 * update states are CONSTANT INTs that describes the state of entity
	 * (example: walking)
	 */
	/**
	 * @property
	 */
	public static final int IDLE = 0;
	/**
	 * @property
	 */
	public static final int WALKING = IDLE + 1;

	protected int currState;

	// action state:
	/*
	 * (see sheep class for example) action states are BOOLLEANS that correspond
	 * to a physical action. actions states are used to update animations(look
	 * at the animationUpdate() method ), NOT UPDATE STATEs
	 */
	protected boolean walking;

	protected CollisionBox box;

	// used for movement
	protected Position nextPosition;
	protected double dx, dy;
	protected float speed;
	boolean facing[] = { false, false, false, false };
	//protected static SpriteSet sprites;
	
	//protected HashMap<String, ArrayList<Bitmap[]>> sprites = new HashMap<String, ArrayList<Bitmap[]>>();

	/**
	 * 
	 * @param col
	 *            starting column
	 * @param row
	 *            starting row
	 */
	Creature(int col, int row) {
		super(col, row);
		speed = .5f;
	}
	
	public abstract void update();

	public void draw(Canvas g) {
		setDrawablePosition();
		drawBitmap(g, animator.getImage(), drawx, drawy);
		
		
		//draw creature position 
		
		/*Paint paint = new Paint();
		paint.setARGB(100,0,255,0);
		drawRect(g,(int)drawx,(int)drawy,width,height,paint);
		
		
		//draw Creature next Position
		
		if(nextPosition != null){
			
			paint.setARGB(100,255,0,0);
			int size = level.getGridSize();
			int col = nextPosition.getCol();
			int row = nextPosition.getRow();
			
			int x = (int)((col*size)+TileMap.getMapx());
			int y = (int)((row*size)+TileMap.getMapy());
			
			drawRect(g,x,y,size,size,paint);
			
		}
		
		
		//draw collision box 
		
				/*double xoff = TileMap.getMapx();
				double yoff = TileMap.getMapy();
				Rectangle rec = box.getRectangle();
				int x = rec.getX();
				int y = rec.getY();
				int width = rec.getWidth();
				int height = rec.getHeight();
				Paint paint = new Paint();
				paint.setARGB(100, 123, 123, 0);
				drawRect(g,(int)(x+xoff),(int)(y+yoff),width,height,paint);*/
	}

	/**
	 * Updates animation based on current action state
	 */
	protected void updateAnimation(SpriteSet sprites) {

		if (walking) {
			if (facing[NORTH]) {
				if (animator.getCurrAction() != WALKING)
					//animator.setFrames(sprites.get("North"), WALKING);
					animator.setFrames(sprites.getFrames(NORTH).getWalkingFrames(), WALKING);
			} else if (facing[SOUTH]) {
				if (animator.getCurrAction() != WALKING)
					//animator.setFrames(sprites.get("South"), WALKING);
					animator.setFrames(sprites.getFrames(SOUTH).getWalkingFrames(), WALKING);

			} else if (facing[EAST]) {
				if (animator.getCurrAction() != WALKING)
					//animator.setFrames(sprites.get("East"), WALKING);
					animator.setFrames(sprites.getFrames(EAST).getWalkingFrames(), WALKING);

			} else if (facing[WEST]) {
				if (animator.getCurrAction() != WALKING)
					//animator.setFrames(sprites.get("West"), WALKING);
					animator.setFrames(sprites.getFrames(WEST).getWalkingFrames(), WALKING);
			}

			animator.setDelay(150);

		} else { // idle
			animator.setDelay(-1);
			if (facing[NORTH]) {

				if (animator.getCurrAction() != IDLE)
					//animator.setFrames(sprites.get("North"), IDLE);
					animator.setFrames(sprites.getFrames(NORTH).getIdleFrames(), IDLE);

			} else if (facing[SOUTH]) {
				if (animator.getCurrAction() != IDLE)
					//animator.setFrames(sprites.get("South"), IDLE);
					animator.setFrames(sprites.getFrames(SOUTH).getIdleFrames(), IDLE);


			} else if (facing[EAST]) {
				if (animator.getCurrAction() != IDLE)
					//animator.setFrames(sprites.get("East"), IDLE);
					animator.setFrames(sprites.getFrames(EAST).getIdleFrames(), IDLE);

			} else if (facing[WEST]) {
				if (animator.getCurrAction() != IDLE)
					//animator.setFrames(sprites.get("West"), IDLE);
					animator.setFrames(sprites.getFrames(WEST).getIdleFrames(), IDLE);

			}
		}

		animator.update();

	}

	/**
	 * 
	 * @return true if current position object has reached nextPosition
	 */
	protected boolean reachedNextPosition() {

	//	if(nextPosition == null) return true;
		
		if (dx != 0) {
			if (dx > 0) {
				if (position.getx() >= nextPosition.getx())
					return true;
				return false;
			} else {
				if (position.getx() <= nextPosition.getx())
					return true;
				return false;
			}

		}

		if (dy != 0) {
			if (dy > 0) {
				if (position.gety() >= nextPosition.gety())
					return true;
				return false;
			} else {
				if (position.gety() <= nextPosition.gety())
					return true;
				return false;
			}

		}

		return true;

	}

	/**
	 * Sets the current Position to the next Position
	 */
	protected void setNewPosition() {
		//if(nextPosition == null )return;
		position = new Position(nextPosition);
		nextPosition = null;
		//setStateTo(IDLE);
	}

	/**
	 * 
	 * @param dir
	 *            direction(see Movable Interface)
	 */
	protected void setFacing(int dir) {

		if (dir < 0 || dir > facing.length)
			return;
		for (int i = 0; i < facing.length; i++) {
			facing[i] = false;
		}

		facing[dir] = true;
		animator.resetCurrentAction();

	}
	
	@Override 
	public boolean hasPosition(int col, int row){
		
		if(super.hasPosition(col,row))return true;
		
		if(nextPosition != null && nextPosition.equals(col,row))return true;
		
		
		return false;
	}
	
	@Override
	public boolean isPressed(Position p){
		
		if(super.isPressed(p))return true;
		
		if(nextPosition != null)return p.equals(nextPosition);
			
		return false;
		
	}
		
	/**
	 * Moves creature up
	 */
	protected void moveNorth() {

		if (!facing[NORTH])
			setFacing(NORTH);
		setStateTo(WALKING);
		int nextCol = position.getCol();
		int nextRow = position.getRow() - 1;
		int size = Level.getGridSize();
		nextPosition = new Position(nextCol, nextRow, nextCol * size, nextRow
				* size);
		dx = 0;
		dy = -speed;

	}

	/**
	 * Moves creature down
	 */
	protected void moveSouth() {

		if (!facing[SOUTH])
			setFacing(SOUTH);
		setStateTo(WALKING);
		int size = Level.getGridSize();
		int nextCol = position.getCol();
		int nextRow = position.getRow() + 1;
		nextPosition = new Position(nextCol, nextRow, nextCol * size, nextRow
				* size);
		dx = 0;
		dy = speed;

	}

	/**
	 * Moves creature to the right
	 */
	protected void moveEast() {

		if (!facing[EAST])
			setFacing(EAST);
		setStateTo(WALKING);
		int size = Level.getGridSize();
		int nextCol = position.getCol() + 1;
		int nextRow = position.getRow();
		nextPosition = new Position(nextCol, nextRow, nextCol * size, nextRow
				* size);
		dx = speed;
		dy = 0;

	}

	/**
	 * Moves creatures to the left
	 */
	public void moveWest() {

		if (!facing[WEST])
			setFacing(WEST);
		setStateTo(WALKING);
		int size = Level.getGridSize();
		int nextCol = position.getCol() - 1;
		int nextRow = position.getRow();
		nextPosition = new Position(nextCol, nextRow, nextCol * size, nextRow
				* size);
		dx = -speed;
		dy = 0;

	}

	/**
	 * 
	 */
	protected void stay() {

		nextPosition = null;
		dx = 0;
		dy = 0;
		if (currState != IDLE)
			setStateTo(IDLE);

	}
	
	/**
	 * 
	 * @return true if the creature can move north
	 */
	protected boolean northValid() {

		//in case sheep tries to off the map
		int row = position.getRow();
		if(row - 1 < 0)return false;
		int col = position.getCol();
			
		if(level.isBlockedByTile(col, row-1))return false;
		if(level.isBlockedByTree(col, row-1))return false;
		
		return true;

	}

	/**
	 * 
	 * @return true if the creature can move south
	 */
	protected boolean southValid() {

		//in case sheep tries 
		int row = position.getRow();
		if(row+1 >= level.getNumRows())return false;
		
		int col = position.getCol();
		
		if(level.isBlockedByTile(col, row+1))return false;
		if(level.isBlockedByTree(col, row+1))return false;


		return true;
	}

	/**
	 * 
	 * @return true if the creature can move to the right
	 */
	protected boolean eastValid() {

		int col = position.getCol();
		if(col+1 >= level.getNumCols())return false;
		
		int row = position.getRow();
		
		if(level.isBlockedByTile(col+1, row))return false;
		if(level.isBlockedByTree(col+1, row))return false;

		return true;
	}

	/**
	 * 
	 * @return true if the creature can move to the left
	 */
	protected boolean westValid() {

		
		int col = position.getCol();
		
		if (col - 1 < 0)return false;
		
		int row = position.getRow();
		
		if(level.isBlockedByTile(col-1, row))return false;
		if(level.isBlockedByTree(col-1, row))return false;
		
		return true;
	}

	/**
	 * 
	 * @return this creatures collision box
	 */
	public CollisionBox getCollisionBox() {
		return box;
	}

	/**
	 * Sets update
	 * 
	 * @param state
	 *            game state id (see fields)
	 */
	protected void setStateTo(int state) {

		if (state == WALKING) {

			currState = WALKING;
			walking = true;
			return;
		}

		if (state == IDLE) {

			currState = IDLE;
			walking = false;
			return;
		}

	}

	/**
	 * 
	 * @return the direction this creature is facing
	 */
	protected int getFacingDirection() {

		if (facing[NORTH])
			return NORTH;
		if (facing[SOUTH])
			return SOUTH;
		if (facing[EAST])
			return EAST;
		if (facing[WEST])
			return WEST;

		return -1;
	}
	
	/*public boolean isPressed(TouchPosition pos) {

		return box.isPressed(pos);
	}*/

}
