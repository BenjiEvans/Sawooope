package edu.calstatela.sawooope.entity.creature;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;

/**
 * 
 * @author Benji
 * 
 */
public class Sheep extends Creature {

	// Update States
	public static final int EATING = 2;

	// action states
	private boolean eating;
	private boolean dead;

	private boolean[] moves = { false, false, false, false };

	// Condenced into a hunger object
	/*
	 * private Health health; private long eatTimer; private GrassPatch grass;
	 * private long eatTime; public static int safe;
	 */

	public Sheep(int col, int row) {
		super(col, row);
		id = SHEEP;
		box = new CollisionBox(this, 7, 2, 18, 30);
	}

	@Override
	public void update() {
		if (dead)
			return;

		switch (currState) {

		case IDLE:// this is an update state

			listenForMove();

			break;

		case WALKING: // this is an update state

			if (reachedNextPosition()) {
				setNewPosition();
				listenForMove();

			} else
				position.updateXY(dx, dy);

			break;

		case EATING: // also an update state

			break;

		}

		updateAnimation();// always update animations last

	}

	@Override
	public void draw(Canvas g) {
		if (isOffScreen())
			return;
		super.draw(g);
	}

	/**
	 * listens for swipe events to see if this sheep should be moved
	 */
	private void listenForMove() {

		if (currState != IDLE)
			setStateTo(IDLE);
		if (moves[NORTH]) {

			if (northValid())
				moveNorth();
			else
				stay();

		} else if (moves[SOUTH]) {

			if (southValid())
				moveSouth();
			else
				stay();

		} else if (moves[EAST]) {
			if (eastValid())
				moveEast();
			else
				stay();

		} else if (moves[WEST]) {
			if (westValid())
				moveWest();
			else
				stay();
		} else
			stay();

	}

	protected void setSprites(GameView view) {
		super.setSprites(view);

		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/sheep/sheeps.png");

		spriteWidth = spriteSheet.getWidth() / 12;
		spriteHeight = spriteSheet.getHeight() / 8;

		String[] hashOrder = { "South", "West", "East", "North" };

		setIdleSprites(spriteSheet, hashOrder);
		setWalkingSprites(spriteSheet, hashOrder);
		setEatingSprites(view, hashOrder);
		setTransportSprites(view);
		setDeadSprites(view);
		setFacing(SOUTH);

	}

	private void setEatingSprites(GameView view, String[] hashOrder) {

		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/sheep/sheep_eat.png");

		for (int i = 0; i < hashOrder.length; i++) {
			Bitmap[] image = new Bitmap[3];
			ArrayList<Bitmap[]> sprite = sprites.get(hashOrder[i]);
			for (int j = 0; j < 3; j++) {
				image[j] = Bitmap.createBitmap(spriteSheet, j * spriteWidth, i
						* spriteHeight, spriteWidth, spriteHeight);
			}

			sprite.add(image);

		}

	}

	private void setDeadSprites(GameView view) {

		Bitmap image;

		image = view.getScaledBitmap("sprites/sheep/dead_sheep.png");

		ArrayList<Bitmap[]> sprite = new ArrayList<Bitmap[]>();
		for (int i = 0; i < 2; i++) {
			Bitmap[] img = new Bitmap[1];
			img[0] = Bitmap.createBitmap(image, i * spriteWidth, 0,
					spriteWidth, spriteHeight);
			sprite.add(img);
		}

		sprites.put("Dead", sprite);

	}

	private void setTransportSprites(GameView view) {

		Bitmap image;

		image = view.getScaledBitmap("sprites/sheep/sheep_transporter.png");

		ArrayList<Bitmap[]> sprite = new ArrayList<Bitmap[]>();
		Bitmap[] transportSprites = new Bitmap[4];
		for (int i = 0; i < 4; i++) {
			transportSprites[i] = Bitmap.createBitmap(image, i * spriteWidth,
					0, spriteWidth, spriteHeight);
		}

		sprite.add(transportSprites);

		sprites.put("Transport", sprite);

	}

	private void setIdleSprites(Bitmap spriteSheet, String[] hashOrder) {

		for (int i = 0; i < hashOrder.length; i++) {
			ArrayList<Bitmap[]> idleSprites = sprites.get(hashOrder[i]);
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);

			idleSprites.add(image);
		}

	}

	private void setWalkingSprites(Bitmap spriteSheet, String[] hashOrder) {

		for (int i = 0; i < hashOrder.length; i++) {
			ArrayList<Bitmap[]> walkingSprites = sprites.get(hashOrder[i]);
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0, spriteHeight * i,
					spriteWidth, spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet, 2 * spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			walkingSprites.add(frames);
		}

	}

	protected void updateAnimation() {

		if (dead) {
			animator.setDelay(-1);

			if (facing[EAST]) {

				animator.setFrames(sprites.get("Dead"), 1);
			} else {
				animator.setFrames(sprites.get("Dead"), 0);
			}

		} else if (eating) {

			if (facing[NORTH]) {

				if (animator.getCurrAction() != EATING) {

					animator.setFrames(sprites.get("North"), EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}

				}

			} else if (facing[SOUTH]) {

				if (animator.getCurrAction() != EATING) {

					animator.setFrames(sprites.get("South"), EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}

				}

			} else if (facing[EAST]) {

				if (animator.getCurrAction() != EATING) {

					animator.setFrames(sprites.get("East"), EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}

				}

			} else if (facing[WEST]) {

				if (animator.getCurrAction() != EATING) {

					animator.setFrames(sprites.get("West"), EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}
				}

			}

		} else {
			super.updateAnimation();
			return;
		}

		animator.update();

	}

	protected boolean southValid() {

		if (!super.southValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

		// if(level.herdHasPosition(this,col, row+1)) return false;
		if (level.isPositionBlocked(col, row + 1))
			return false;
		// if(level.decoyHasColRow(col,row+1))return false;

		return true;
	}

	protected boolean northValid() {

		if (!super.northValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

		// if(level.herdHasPosition(this,col, row-1)) return false;
		if (level.isPositionBlocked(col, row - 1))
			return false;
		// if(level.decoyHasColRow(col,row-1))return false;*/

		return true;
	}

	protected boolean eastValid() {

		if (!super.eastValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

		// if(level.herdHasPosition(this,col+1, row)) return false;
		if (level.isPositionBlocked(col + 1, row))
			return false;
		// if(level.decoyHasColRow(col+1,row))return false;
		return true;
	}

	protected boolean westValid() {

		if (!super.westValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

		// if(level.herdHasPosition(this, col-1, row)) return false;
		if (level.isPositionBlocked(col - 1, row))
			return false;
		// if(level.decoyHasColRow(col-1,row))return false;
		return true;
	}

	/**
	 * 
	 * @return true if the sheep is dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * set sheep
	 * 
	 * @param bol
	 */
	public void setDead(boolean bol) {
		dead = bol;
	}

	private void moveBack(int facingDirection) {

		setFacing(facingDirection);
		int size = Level.getGridSize();
		int col = position.getCol();
		int row = position.getRow();
		int x = col * size;
		int y = row * size;
		nextPosition = new Position(col, row, x, y);
		dx = -dx;
		dy = -dy;
	}

	/**
	 * Stops this sheep from moving
	 */
	@Override
	public void stop() {

		switch (currState) {

		case WALKING:
			int dir = getFacingDirection();
			moves[dir] = false;
			break;

		}

	}

	/**
	 * Moves this sheep in the specified direction
	 */
	@Override
	public void move(int direction) {

		if (currState == IDLE) {
			switch (direction) {

			case NORTH:
				if (northValid()) {
					moveNorth();
					moves[NORTH] = true;
				}

				break;
			case SOUTH:
				if (southValid()) {
					moveSouth();
					moves[SOUTH] = true;
				}
				break;
			case EAST:
				if (eastValid()) {
					moveEast();
					moves[EAST] = true;
				}
				break;
			case WEST:
				if (westValid()) {
					moveWest();
					moves[WEST] = true;
				}
				break;
			}
			return;
		}

		if (currState == WALKING) {

			int facingDir = getFacingDirection();
			moves[facingDir] = false;
			moves[direction] = true;

			if (direction != facingDir) {
				if (direction == NORTH && facingDir == SOUTH)
					moveBack(direction);
				else if (direction == SOUTH && facingDir == NORTH)
					moveBack(direction);
				else if (direction == EAST && facingDir == WEST)
					moveBack(direction);
				else if (direction == WEST && facingDir == EAST)
					moveBack(direction);

			}

		}

	}

}
