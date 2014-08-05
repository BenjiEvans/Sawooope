package edu.calstatela.sawooope.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import edu.calstatela.sawooope.entity.animation.Animator;
import edu.calstatela.sawooope.entity.movement.Position;
import edu.calstatela.sawooope.gamestates.levels.GameMode;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.gamestates.levels.LevelInputProcessor.TouchPosition;
import edu.calstatela.sawooope.tilemap.TileMap;

/**
 * BoardObject is any entity that will be drawn on the TileMap and can interact
 * with other entities drawn on the TileMap.
 * 
 * @author Benji
 * 
 */
public abstract class BoardObject {

	// Position Variables
	protected static double mapx, mapy;
	protected Position position;

	// Graphic Renerdering variables
	protected Animator animator;
	protected float drawx, drawy;
	protected int width, height;

	// other vars
	protected static Level level;
	protected static GameMode gameMode;
	protected int id;

	/**
	 * 
	 * @param col
	 *            starting column
	 * @param row
	 *            starting row
	 */
	protected BoardObject(int col, int row) {
		position = new Position(col, row);
		animator = new Animator();
		setAnimation();
	}

	/**
	 * sets the x and y coordinates of the TileMaps x, y coordinates so all
	 * entities can be Properly draw when the map is scrolled. This method MUST
	 * be called before any entity is drawn
	 */
	public static void setMapPosition() {

		mapx = TileMap.getMapx();
		mapy = TileMap.getMapy();
	}

	/**
	 * Sets the level so that all entities can reference the current level
	 * 
	 * @param lev
	 *            current level
	 */
	public static void setLevel(Level lev) {
		level = lev;
	}

	/**
	 * Sets the game mode so all entities have a reference to the current game
	 * mode
	 * 
	 * @param mode
	 */
	public static void setGameMode(GameMode mode) {
		gameMode = mode;
	}

	/**
	 * Draws the entity
	 * 
	 * @param g
	 *            canvas to draw on
	 */
	public abstract void draw(Canvas g);

	/**
	 * sets the initial animation frames of the entity
	 */
	protected abstract void setAnimation();

	/**
	 * Checks to see if the entity was pressed
	 * 
	 * @param pos
	 *            position
	 * @return true if positions are equal
	 */
	public boolean isPressed(Position pos) {

		double x = position.getx();
		double y = position.gety();

		double xpress = pos.getx();
		double ypress = pos.gety();

		boolean withinx = xpress >= x && xpress <= x + width;
		boolean withiny = ypress >= y && ypress <= y + height;
		if (withinx && withiny)
			return true;

		return position.hasPosition(pos);
	}

	/**
	 * Checks to see if this the entity has the position specified
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 * @return
	 */
	public boolean hasPosition(int col, int row) {

		return position.hasColRow(col, row);

	}

	/**
	 * Checks to see if the entity was pressed
	 * 
	 * @param pos
	 *            touch position
	 * @return true if the entity is within the touch radius
	 */
	public boolean isPressed(TouchPosition pos) {

		return isPressed(pos.getPosition());
	}

	/**
	 * Combines Tilemap position with the eneity's position so the entity can be
	 * properly draw when the screen is scrolled
	 */
	protected void setDrawablePosition() {

		drawx = (float) (mapx + position.getx());
		drawy = (float) (mapy + position.gety());
	}

	/**
	 * 
	 * @return the entity's x position on the map
	 */
	public double getX() {
		return position.getx();
	}

	/**
	 * 
	 * @return the entity's y position on the map
	 */
	public double getY() {
		return position.gety();
	}

	/**
	 * 
	 * @return the entity's maximum x coordinate
	 */
	public double getMaxX() {

		return getX() + width;
	}

	/**
	 * 
	 * @return the entity's maximum y coordinate
	 */
	public double getMaxY() {

		return getY() + height;
	}

	/**
	 * Checks the identity of this entity
	 * 
	 * @param id
	 *            (see BoardObjectIDS Interface )
	 * @return true if this entity has the same id as the one specified
	 */
	public boolean isOfType(int id) {

		return this.id == id;
	}

	/**
	 * 
	 * @return the id of the entity
	 */
	public int getId() {
		return id;
	}

	/**
	 * Checks to see if this entity is on the screen
	 * 
	 * @return true if the entities should be drawn in the view port
	 */
	protected boolean isOffScreen() {
		int x = (int) position.getx();
		int y = (int) position.gety();
		if (x + width + mapx < 0 || x + mapx > level.getWidth()
				|| y + height + mapy < 0 || y + mapy > level.getHeight())
			return true;

		return false;

	}

	/**
	 * draws a rectangle based on the arguments passed in
	 * 
	 * @param g
	 *            canvas to draw on
	 * @param x
	 *            top left x position of the rectangle
	 * @param y
	 *            top left y position of the rectangle
	 * @param width
	 *            rectangle's width
	 * @param height
	 *            rectangle's height
	 * @param paint
	 *            paint used to color the rectangle
	 */
	protected void drawRect(Canvas g, int x, int y, int width, int height,
			Paint paint) {

		g.drawRect(Rectangle.getRect(x, y, width, height), paint);

	}

	/**
	 * Draws an image based on the arguments passed in
	 * 
	 * @param g
	 *            canvas to draw on
	 * @param image
	 *            image to draw
	 * @param x
	 *            top left x position of the image
	 * @param y
	 *            top right y position of the image
	 */
	protected void drawBitmap(Canvas g, Bitmap image, double x, double y) {

		g.drawBitmap(image, (float) x, (float) y, null);

	}

}
