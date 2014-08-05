package edu.calstatela.sawooope.entity.creature;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import edu.calstatela.sawooope.entity.EntityID;
import edu.calstatela.sawooope.entity.animation.AnimationStates;
import edu.calstatela.sawooope.entity.animation.SpriteSet;
import edu.calstatela.sawooope.entity.creature.hunger.Edible;
import edu.calstatela.sawooope.entity.creature.hunger.Hunger;
import edu.calstatela.sawooope.entity.movement.Movable;
import edu.calstatela.sawooope.entity.movement.Position;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;

/**
 * 
 * @author Benji
 * 
 */
public class Sheep extends Creature implements Edible, Movable {

	private boolean dead;
	private Hunger hunger;
	private boolean selected;
	private int move = Movable.UNDETERMINED;
	private static SpriteSet sprites;
	private static int spriteWidth;
	private static int spriteHeight;
	private double dist;

	public Sheep(int col, int row) {
		super(col, row);
		id = EntityID.SHEEP;
		float scale = Level.getScale();
		box = new CollisionBox(this, (int) (7 * scale), (int) (2 * scale),
				(int) (18 * scale), (int) (30 * scale));
	}

	@Override
	public void update() {

		switch (states[ACTION]) {

		case ActionStates.IDLE:
			listenForMove();
			break;

		case ActionStates.WALKING:
			if (position.hasNextPosition()) {
				if (position.hasReachedNextPosition()) {
					position.setNewPosition();
					if (position.hasDestination()) {
						if (position.hasReachedDestination())
							stop();
					}
					listenForMove();
				} else
					position.updateXY();
			} else {
				states[ACTION] = ActionStates.IDLE;
				states[ANIMATION] = AnimationStates.IDLE;
			}

			break;

		case ActionStates.EATING:

			break;

		case ActionStates.DIEING:

			break;

		}

		updateAnimation();// always update animations last

	}

	@Override
	public void draw(Canvas g) {
		if (isOffScreen())
			return;
		super.draw(g);

		// draw selected box
		if (selected) {
			Paint paint = new Paint();
			paint.setARGB(100, 0, 0, 255);
			int size = level.getGridSize();
			drawRect(g, (int) drawx, (int) drawy, size, size, paint);
		}

	}

	private void listenForMove() {

		switch (move) {

		case NORTH:
			if (northValid()) {
				moveNorth();
				return;
			}
			break;
		case SOUTH:
			if (southValid()) {
				moveSouth();
				return;
			}
			break;
		case EAST:
			if (eastValid()) {
				moveEast();
				return;
			}
			break;
		case WEST:
			if (westValid()) {
				moveWest();
				return;
			}

		}

		stay();

	}

	/**
	 * Sets a static reference to sprite assets for the Sheep class
	 * 
	 * @param view
	 *            current gameview
	 */
	public static void setSprites(GameView view) {

		sprites = new SpriteSet();
		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/sheep/sheeps.png");

		spriteWidth = spriteSheet.getWidth() / 12;
		spriteHeight = spriteSheet.getHeight() / 8;

		int[] order = { SOUTH, WEST, EAST, NORTH };
		int length = order.length;
		// set Idle Sprites
		for (int i = 0; i < length; i++) {
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			sprites.getFrames(order[i]).setIdleFrames(image);
		}

		// set walking sprites
		for (int i = 0; i < length; i++) {

			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0, spriteHeight * i,
					spriteWidth, spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet, 2 * spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			sprites.getFrames(order[i]).setWalkingFrames(frames);
		}

		// set eating sprites

		spriteSheet = view.getScaledBitmap("sprites/sheep/sheep_eat.png");

		for (int i = 0; i < length; i++) {
			Bitmap[] image = new Bitmap[3];

			for (int j = 0; j < 3; j++) {
				image[j] = Bitmap.createBitmap(spriteSheet, j * spriteWidth, i
						* spriteHeight, spriteWidth, spriteHeight);
			}

			sprites.getFrames(order[i]).setEatingFrames(image);

		}

		// set dead sprites

		spriteSheet = view.getScaledBitmap("sprites/sheep/dead_sheep.png");
		// only has facing east and west sprites
		// first image is facing west 2nd is east

		for (int i = 0; i < 2; i++) {
			Bitmap[] img = new Bitmap[1];
			img[0] = Bitmap.createBitmap(spriteSheet, i * spriteWidth, 0,
					spriteWidth, spriteHeight);

			if (i == 0)
				sprites.getFrames(WEST).setDeadFrames(img);
			else
				sprites.getFrames(EAST).setDeadFrames(img);

		}

		sprites.getFrames(SOUTH).setDeadFrames(
				sprites.getFrames(WEST).getDeadFrames());
		sprites.getFrames(NORTH).setDeadFrames(
				sprites.getFrames(EAST).getDeadFrames());

	}

	protected void updateAnimation() {

		if (states[ACTION] == ActionStates.DIEING) {

			if (states[ANIMATION] == AnimationStates.DEAD) {

				animator.setDelay(-1);
				animator.setFrames(sprites.getFrames(facing).getDeadFrames());

			}

		} else if (states[ACTION] == ActionStates.EATING) {

			if (states[ANIMATION] == AnimationStates.EAT) {

				animator.setFrames(sprites.getFrames(facing).getEatingFrames());
				animator.setFrame(100);
			}

			switch (animator.getCurrentFrame()) {
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

		} else {
			super.updateAnimation(sprites);
			return;
		}

		animator.update();

	}

	protected boolean southValid() {

		if (!super.southValid())
			return false;

		if (level.herdHasPosition(this, position.getCol(),
				position.getRow() + 1))
			return false;
		return true;
	}

	protected boolean northValid() {

		if (!super.northValid())
			return false;

		if (level.herdHasPosition(this, position.getCol(),
				position.getRow() - 1))
			return false;
		return true;
	}

	protected boolean eastValid() {

		if (!super.eastValid())
			return false;

		if (level.herdHasPosition(this, position.getCol() + 1,
				position.getRow()))
			return false;
		return true;
	}

	protected boolean westValid() {

		if (!super.westValid())
			return false;

		if (level.herdHasPosition(this, position.getCol() - 1,
				position.getRow()))
			return false;

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
	 * sets sheep to be selected (sheep toggling)
	 */
	public void select() {
		selected = true;
	}

	/**
	 * set sheep to be deselected (sheep toggling)
	 */
	public void deselect() {
		selected = false;
	}

	private void moveBack(int direction) {

		facing = direction;
		move = facing;
		position.reverseDirection();
		states[ANIMATION] = AnimationStates.WALK;
	}

	@Override
	public void stop() {

		clearMoves();
		position.removeDestinations();
	}

	private void clearMoves() {

		move = Movable.UNDETERMINED;

	}

	@Override
	public void move(int direction) {// called when swipped

		if (states[ACTION] == ActionStates.IDLE) {
			clearMoves();
			position.removeDestinations();
			facing = direction;
			move = facing;
			return;
		}

		if (states[ACTION] == ActionStates.WALKING) {

			position.removeDestinations();
			// check for move back swipe
			if (areOpposingDirections(facing, direction))
				moveBack(direction);
			else {
				facing = direction;
				move = facing;
				states[ANIMATION] = AnimationStates.WALK;
			}

		}

	}

	@Override
	protected void setAnimation() {
		width = spriteWidth;
		height = spriteHeight;
		facing = SOUTH;
		animator.setFrames(sprites.getFrames(facing).getIdleFrames());
		animator.setDelay(-1);

	}

	@Override
	public void move(int col, int row) {// called when move with "tap"

		if (!level.isPositionAvailable(col, row))
			return;

		position.removeDestinations();

		// check to see if the destination is valid

		int thisCol, thisRow;

		Position next = position.getNextPosition();

		if (next != null) {
			thisCol = next.getCol();
			thisRow = next.getRow();
		} else {
			thisCol = position.getCol();
			thisRow = position.getRow();
		}

		boolean sameCol = thisCol == col;
		boolean sameRow = thisRow == row;

		if (sameCol && sameRow || (!sameCol && !sameRow))
			return;

		// from here we know that the position is a valid position
		position.addDestination(col, row);
		if (sameCol) { // must move north or south

			if (row > thisRow) {// move south
				if (areOpposingDirections(facing, SOUTH) && southValid()) {
					moveSouth();
				}
				facing = SOUTH;
			} else if (row < thisRow) {// move north
				if (areOpposingDirections(facing, NORTH) && northValid()) {
					moveNorth();
				}
				facing = NORTH;
			}

		} else if (sameRow) {// must move east or west

			if (col > thisCol) {// move east
				if (areOpposingDirections(facing, EAST) && eastValid()) {
					moveEast();
				}
				facing = EAST;
			} else if (col < thisCol) {// move west
				if (areOpposingDirections(facing, WEST) && westValid()) {
					moveWest();
				}
				facing = WEST;
			}
		}

		move = facing;
	}

	public double getDistance() {
		return dist;
	}

	public void setDistance(double dist) {
		this.dist = dist;
	}

	@Override
	public void eat(Edible food) {
		// TODO Auto-generated method stub
		if (hunger == null)
			return;
		hunger.storeFood(food);

	}

	@Override
	public float consume() {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean areOpposingDirections(int direction1, int direction2) {

		if ((direction1 == NORTH && direction2 == SOUTH)
				|| (direction1 == SOUTH && direction2 == NORTH))
			return true;

		if ((direction1 == EAST && direction2 == WEST)
				|| (direction1 == WEST && direction2 == EAST))
			return true;

		return false;
	}

}
