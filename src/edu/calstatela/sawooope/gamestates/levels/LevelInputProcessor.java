package edu.calstatela.sawooope.gamestates.levels;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.tilemap.TileMap;

/**
 * LevelInputProcessor keeps track of all touch events and detects what kind of
 * gestures the users is making on the screen( for example: swiping )
 * 
 * @author Benji
 * 
 */
public class LevelInputProcessor {

	private TouchPosition pressed;
	private long pressStamp;
	private static long SWIPE_TIME = 500l;
	private boolean dragged;
	private TouchPosition release;
	private BoardObject pressedObj;
	private TouchAction gesture;
	private int gridSize;

	LevelInputProcessor(int grid) {

		gridSize = grid;
	}

	/**
	 * 
	 * @param x
	 *            x location where the screen is pressed
	 * @param y
	 *            y location wheere the screen is pressed
	 */
	public void screenPressed(float x, float y) {
		dragged = false;
		release = null;
		pressedObj = null;
		pressed = new TouchPosition(x, y);
		pressStamp = System.nanoTime();
	}

	/**
	 * Checks to see if the object was pressed and stores it if it is pressed
	 * 
	 * @param object
	 *            some board object
	 * @return true if the object was pressed
	 */
	public boolean hasPressed(BoardObject object) {

		if (object.isPressed(pressed)) {
			pressedObj = object;
			return true;
		}
		return false;
	}

	/**
	 * Gets the Object that was pressed
	 * 
	 * @return null if no object was pressed
	 */
	public BoardObject getPressedObject() {

		return pressedObj;
	}

	/**
	 * 
	 * @param x
	 *            x location where user dragged to
	 * @param y
	 *            y location where user dragged to
	 */
	public void screenDragged(float x, float y) {

		dragged = true;
	}

	/**
	 * Checks to see if the screen can be scrolled
	 * 
	 * @return true if a sheep has not been pressed
	 */
	public boolean isScreenScrollable() {

		return pressedObj == null || !pressedObj.isOfType(BoardObject.SHEEP);
	}

	/**
	 * 
	 * @param x
	 *            x location of where user released screen
	 * @param y
	 *            y location of where user released screen
	 */
	public void screenReleased(float x, float y) {

		long finalStamp = System.nanoTime();
		release = new TouchPosition(x, y);

		gesture = new TouchAction(pressStamp, finalStamp);

	}

	/**
	 * Gets the gesture performed
	 * 
	 * @return the id of the TouchEvent
	 */
	public int getGestureCode() {

		return gesture.getId();
	}

	/**
	 * 
	 * @return the direction the user swiped
	 */
	public int getSwipeDirection() {

		return gesture.getSwipeDirection();
	}

	/**
	 * Gets the world coordinate(position in game) that the user tapped
	 */
	public Position getTapLocationOnMap() {

		return gesture.getTapPosition();
	}

	/**
	 * ToucPositions are imaginary circles on the screen that represents where
	 * the user has pressed. Each TouchPosition corresponds to a position on the
	 * Level's map (map position) and is used by the LevelInputProcesser to
	 * detect gestures on the screen.
	 * 
	 * @author Benji
	 */
	public class TouchPosition {

		// center positions in the world
		private double x, y;

		// position pressed in the game
		private Position mapPosition;

		// center position on screen
		private float screenx, screeny;

		private float radius;

		/**
		 * 
		 * @param x
		 *            x location where touch event occurred
		 * @param y
		 *            y location where touch event occurred
		 */
		TouchPosition(float x, float y) {

			this.screenx = x;
			this.screeny = y;
			radius = (float) (Level.getGridSize() * 0.5);
			double mapx2 = Math.abs(TileMap.getMapx());
			double mapy2 = Math.abs(TileMap.getMapy());
			this.x = mapx2 + x;
			this.y = mapy2 + y;
			int col = (int) ((this.x) / gridSize);
			int row = (int) ((this.y) / gridSize);

			mapPosition = new Position(col, row, this.x, this.y);
		}

		/**
		 * Checks to see if the position was touched
		 * 
		 * @param pos
		 *            position
		 * @return true if the TouchPosition's position on the map is within the
		 *         radius of the TouchPosition
		 */
		public boolean isInTouchRadius(Position pos) {

			double centerx = pos.getx();
			double centery = pos.gety();

			// distance formula
			double dxsqr = Math.pow((centerx - x), 2);
			double dysqr = Math.pow((centery - y), 2);

			double distance = Math.pow(dxsqr + dysqr, 0.5);

			return distance < radius;

		}

		/**
		 * 
		 * @return the x coordinate of where the the screen was touched
		 */
		public float getScreenx() {
			return screenx;
		}

		/**
		 * 
		 * @return the y coordinate of where the the screen was touched
		 */
		public float getScreeny() {
			return screeny;
		}

		/**
		 * 
		 * @return the position on the TileMap that was pressed
		 */
		public Position getPosition() {
			return new Position(mapPosition);
		}

		/**
		 * Determines if to TouchPositions are the same (determined by their map
		 * position)
		 * 
		 * @param pos
		 *            touch position being compared
		 * @return true if the Position objects are equal
		 */
		public boolean equals(TouchPosition pos) {

			return pos.getPosition().equals(mapPosition);
		}

	}

	/**
	 * TouchAction represents the gesture that the user is making on the screen.
	 * As of now the only gestures are:
	 * <ul>
	 * <li>Swipe</li>
	 * <li>Tap</li>
	 * <li>Drag</li>
	 * </ul>
	 * 
	 * @deprecated This class will be redesigned due to inconsistent
	 *             Object-Oriented design, However... it will have to do for
	 *             now.
	 * @author Benji
	 * 
	 */
	public class TouchAction implements Touchable {

		int id;
		int swipeDirection = -1;
		Position pos;

		/**
		 * 
		 * @param beginTime
		 *            time stamp of when the touch event started
		 * @param endTime
		 *            time stamp if when the touch event ended
		 */
		TouchAction(long beginTime, long endTime) {

			long elapsed = (endTime - beginTime) / 1000000;

			if (elapsed < SWIPE_TIME) {

				int direction = getSwipeDirection(pressed.getScreenx(),
						pressed.getScreeny(), release.getScreenx(),
						release.getScreeny());

				if (direction != IGNORE_SWIPE) {
					id = SWIPE;
					swipeDirection = direction;
					return;
				}

			}

			if (pressed.equals(release) && !dragged) {

				id = TAP;

				pos = pressed.getPosition();

			} else {

				id = DRAG;
			}

		}

		/**
		 * Gets the position on the TileMap that was tapped if a tap event
		 * occurred
		 * 
		 * @return null if tap event didn't take place
		 */
		public Position getTapPosition() {

			return pos;
		}

		/**
		 * 
		 * @return the identity of the gesture (swipe , tap ,or drag)
		 */
		public int getId() {
			return id;
		}

		/**
		 * Gets the numerical representation of the direction
		 * 
		 * @return -1 if swipe didn't occur
		 */
		public int getSwipeDirection() {

			return swipeDirection;
		}

		// This is the algorithim used to detect swiping directions
		@Override
		public int getSwipeDirection(float startx, float starty, float endx,
				float endy) {

			double length = gridSize / 2;
			double xlowerBound = startx - length;
			double xupperBound = startx + length;
			double ylowerBound = starty - length;
			double yupperBound = starty + length;

			boolean withinWidth = endx > xlowerBound && endx < xupperBound;
			boolean withinHeight = endy > ylowerBound && endy < yupperBound;

			if (withinWidth && withinHeight)
				return IGNORE_SWIPE;

			double xdist = Math.abs(startx - endx);
			double ydist = Math.abs(starty - endy);

			if (endx > startx) {
				// moved to the right

				if (xdist > ydist) {
					// swiped to the right
					return SWIPE_RIGHT;
				}

				if (xdist < ydist) {
					// either swiped up or down

					if (endy > starty)
						return SWIPE_DOWN;

					if (endy < starty)
						return SWIPE_UP;

				}

				if (xdist == ydist)
					return IGNORE_SWIPE;

			} else if (endx < startx) {
				// moves to the left

				if (xdist > ydist) {

					return SWIPE_LEFT;
				}

				if (xdist < ydist) {

					if (endy > starty)
						return SWIPE_DOWN;

					if (endy < starty)
						return SWIPE_UP;

				}

				if (xdist == ydist) {

					return IGNORE_SWIPE;
				}

			} else if (endx == startx) {

				if (endy < starty)
					return SWIPE_UP;

				if (endy > starty)
					return SWIPE_DOWN;

			}

			return IGNORE_SWIPE;
		}

	}

}
