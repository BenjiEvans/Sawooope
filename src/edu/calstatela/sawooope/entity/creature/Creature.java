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
	
	//protected Hunger hunger;
	protected HashMap<String, ArrayList<Bitmap[]>> sprites = new HashMap<String, ArrayList<Bitmap[]>>();

	/**
	 * 
	 * @param col
	 *            starting column
	 * @param row
	 *            starting row
	 */
	Creature(int col, int row) {
		super(col, row);
		setSprites(level.getGameView());
		speed = .5f;
	}

	public abstract void update();

	public void draw(Canvas g) {
		setDrawablePosition();
		drawBitmap(g, animator.getImage(), drawx, drawy);
		
		//draw collision box 
		
				double xoff = TileMap.getMapx();
				double yoff = TileMap.getMapy();
				Rectangle rec = box.getRectangle();
				int x = rec.getX();
				int y = rec.getY();
				int width = rec.getWidth();
				int height = rec.getHeight();
				Paint paint = new Paint();
				paint.setARGB(100, 123, 123, 0);
				drawRect(g,(int)(x+xoff),(int)(y+yoff),width,height,paint);
	}

	/**
	 * Updates animation based on current action state
	 */
	protected void updateAnimation() {

		if (walking) {
			if (facing[NORTH]) {
				if (animator.getCurrAction() != WALKING)
					animator.setFrames(sprites.get("North"), WALKING);

			} else if (facing[SOUTH]) {
				if (animator.getCurrAction() != WALKING)
					animator.setFrames(sprites.get("South"), WALKING);

			} else if (facing[EAST]) {
				if (animator.getCurrAction() != WALKING)
					animator.setFrames(sprites.get("East"), WALKING);

			} else if (facing[WEST]) {
				if (animator.getCurrAction() != WALKING)
					animator.setFrames(sprites.get("West"), WALKING);
			}

			animator.setDelay(150);

		} else { // idle
			animator.setDelay(-1);
			if (facing[NORTH]) {

				if (animator.getCurrAction() != IDLE)
					animator.setFrames(sprites.get("North"), IDLE);

			} else if (facing[SOUTH]) {
				if (animator.getCurrAction() != IDLE)
					animator.setFrames(sprites.get("South"), IDLE);

			} else if (facing[EAST]) {
				if (animator.getCurrAction() != IDLE)
					animator.setFrames(sprites.get("East"), IDLE);

			} else if (facing[WEST]) {
				if (animator.getCurrAction() != IDLE)
					animator.setFrames(sprites.get("West"), IDLE);

			}
		}

		animator.update();

	}

	/**
	 * 
	 * @return true if current position object has reached nextPosition
	 */
	protected boolean reachedNextPosition() {

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
		position = new Position(nextPosition);
		nextPosition = null;
		setStateTo(IDLE);
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

	protected void setSprites(GameView view) {

		String[] spriteStates = { "South", "West", "East", "North" };

		for (int i = 0; i < spriteStates.length; i++) {
			ArrayList<Bitmap[]> frames = new ArrayList<Bitmap[]>();

			sprites.put(spriteStates[i], frames);
		}
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
	public void stay() {

		nextPosition = null;
		dx = 0;
		dy = 0;

	}

	/**
	 * 
	 * @return true if the creature can move north
	 */
	protected boolean northValid() {

		if (position.getRow() - 1 < 0)
			return false;

		return true;

	}

	/**
	 * 
	 * @return true if the creature can move south
	 */
	protected boolean southValid() {

		// if(row+1 >= level.getNumRows())return false;

		return true;
	}

	/**
	 * 
	 * @return true if the creature can move to the right
	 */
	protected boolean eastValid() {

		// if(col+1 >= level.getNumCols())return false;

		return true;
	}

	/**
	 * 
	 * @return true if the creature can move to the left
	 */
	protected boolean westValid() {

		if (position.getCol() - 1 < 0)
			return false;

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
