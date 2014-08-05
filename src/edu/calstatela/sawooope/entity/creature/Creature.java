package edu.calstatela.sawooope.entity.creature;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.animation.AnimationStates;
import edu.calstatela.sawooope.entity.animation.SpriteSet;
import edu.calstatela.sawooope.entity.creature.hunger.Hungery;
import edu.calstatela.sawooope.entity.movement.Movable;
import edu.calstatela.sawooope.entity.movement.Position;
import edu.calstatela.sawooope.tilemap.TileMap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Creature is essentially an entity that is capable of movement.
 * 
 * @author Benji
 */

public abstract class Creature extends BoardObject implements Hungery {

	protected final int ACTION = 0;
	protected final int ANIMATION = 1;

	protected CollisionBox box;
	protected float speed;

	int facing;// direction the entity is facing
	int[] states;// animation and action state of the entity

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
		states = new int[2];
		states[ACTION] = ActionStates.IDLE;
		states[ANIMATION] = AnimationStates.IDLE;
	}

	/**
	 * This method is used to update all game logic of the entity
	 */
	public abstract void update();

	public void draw(Canvas g) {

		// draw creature
		setDrawablePosition();
		drawBitmap(g, animator.getImage(), drawx, drawy);

		// draw creature position
		Paint paint = new Paint();
		paint.setARGB(100, 0, 255, 0);
		drawRect(g, (int) drawx, (int) drawy, width, height, paint);

		// draw Creature next Position
		Position nextPosition = position.getNextPosition();

		if (nextPosition != null) {

			paint.setARGB(100, 255, 0, 0);
			int size = level.getGridSize();
			int col = nextPosition.getCol();
			int row = nextPosition.getRow();

			int x = (int) ((col * size) + TileMap.getMapx());
			int y = (int) ((row * size) + TileMap.getMapy());

			drawRect(g, x, y, size, size, paint);

		}

		// draw collision box

		/*
		 * double xoff = TileMap.getMapx(); double yoff = TileMap.getMapy();
		 * Rectangle rec = box.getRectangle(); int x = rec.getX(); int y =
		 * rec.getY(); int width = rec.getWidth(); int height = rec.getHeight();
		 * Paint paint = new Paint(); paint.setARGB(100, 123, 123, 0);
		 * drawRect(g,(int)(x+xoff),(int)(y+yoff),width,height,paint);
		 */
	}

	/**
	 * Updates animation based on current action state
	 */
	protected void updateAnimation(SpriteSet sprites) {

		if (states[ACTION] == ActionStates.WALKING) {

			if (states[ANIMATION] == AnimationStates.WALK) {

				animator.setFrames(sprites.getFrames(facing).getWalkingFrames());
				animator.setDelay(150);
			}

		} else if (states[ACTION] == ActionStates.IDLE) {

			if (states[ANIMATION] == AnimationStates.IDLE) {
				animator.setFrames(sprites.getFrames(facing).getIdleFrames());
				animator.setDelay(-1);
			}
		}
		animator.update();
		states[ANIMATION] = AnimationStates.EMPTY;

	}

	/**
	 * Moves creature up
	 */
	protected void moveNorth() {
		setMovementStates();
		facing = Movable.NORTH;
		position.setNextPosition(position.getCol(), position.getRow() - 1);
		position.setXYUpdateRate(0, -speed);
	}

	/**
	 * Moves creature down
	 */
	protected void moveSouth() {
		setMovementStates();
		facing = Movable.SOUTH;
		position.setNextPosition(position.getCol(), position.getRow() + 1);
		position.setXYUpdateRate(0, speed);
	}

	/**
	 * Moves creature to the right
	 */
	protected void moveEast() {
		setMovementStates();
		facing = Movable.EAST;
		position.setNextPosition(position.getCol() + 1, position.getRow());
		position.setXYUpdateRate(speed, 0);
	}

	/**
	 * Moves creatures to the left
	 */
	public void moveWest() {

		setMovementStates();
		facing = Movable.WEST;
		position.setNextPosition(position.getCol() - 1, position.getRow());
		position.setXYUpdateRate(-speed, 0);
	}

	/**
	 * Keeps entity from moving
	 */
	protected void stay() {
		position.removeNextPosition();
		position.setXYUpdateRate(0, 0);
		states[ACTION] = ActionStates.IDLE;
		states[ANIMATION] = AnimationStates.IDLE;
	}

	private void setMovementStates() {
		states[ACTION] = ActionStates.WALKING;
		states[ANIMATION] = AnimationStates.WALK;
	}

	/**
	 * 
	 * @return true if the creature can move north
	 */
	protected boolean northValid() {

		// in case sheep tries to off the map
		int row = position.getRow();
		if (row - 1 < 0)
			return false;
		int col = position.getCol();

		if (level.isBlockedByTile(col, row - 1))
			return false;
		if (level.isBlockedByTree(col, row - 1))
			return false;

		return true;

	}

	/**
	 * 
	 * @return true if the creature can move south
	 */
	protected boolean southValid() {

		// in case sheep tries to move off map
		int row = position.getRow();
		if (row + 1 >= level.getNumRows())
			return false;

		int col = position.getCol();

		if (level.isBlockedByTile(col, row + 1))
			return false;
		if (level.isBlockedByTree(col, row + 1))
			return false;

		return true;
	}

	/**
	 * 
	 * @return true if the creature can move to the right
	 */
	protected boolean eastValid() {

		// keep from moving off screen
		int col = position.getCol();
		if (col + 1 >= level.getNumCols())
			return false;

		int row = position.getRow();

		if (level.isBlockedByTile(col + 1, row))
			return false;
		if (level.isBlockedByTree(col + 1, row))
			return false;

		return true;
	}

	/**
	 * 
	 * @return true if the creature can move to the left
	 */
	protected boolean westValid() {

		int col = position.getCol();
		// keep from moving off screen
		if (col - 1 < 0)
			return false;

		int row = position.getRow();

		if (level.isBlockedByTile(col - 1, row))
			return false;
		if (level.isBlockedByTree(col - 1, row))
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
}
