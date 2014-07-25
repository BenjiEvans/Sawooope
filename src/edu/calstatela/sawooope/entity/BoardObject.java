package edu.calstatela.sawooope.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import edu.calstatela.sawooope.gamestates.levels.GameMode;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.gamestates.levels.LevelInputProcessor.TouchPosition;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.tilemap.TileMap;

/**
 * BoardObject is any entity that will be drawn on the TileMap and can interact
 * with other entities drawn on the TileMap. Every Board Object has a position
 * that keeps track of its location on the map.
 * 
 * @author Benji
 * 
 */
public abstract class BoardObject {

	// Position Variables
	protected static double mapx, mapy;
	protected Position position;

	// Graphic Renerdering variables
	protected Animation animator;
	protected float drawx, drawy;
	protected int width, height;

	// other vars
	protected static Level level;
	protected static GameMode gameMode;
	protected EntityID id;

	/**
	 * 
	 * @param col
	 *            starting column
	 * @param row
	 *            starting row
	 */
	protected BoardObject(int col, int row) {

		int size = Level.getGridSize();
		position = new Position(col, row, col * size, row * size);
		animator = new Animation();
		setAnimation();
	}

	/**
	 * sets the mapx and mapy to the TileMaps x, y coordinates. This method MUST
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
	 * Sets the game mode so all entities have a reference to the 
	 * current game mode
	 * @param mode
	 */
	public static void setGameMode(GameMode mode){
			gameMode = mode;
	}
	
	/**
	 * Draws the entity
	 * 
	 * @param g
	 *            canvas to draw on
	 */
	public abstract void draw(Canvas g);
	
	protected abstract void setAnimation();

	/**
	 * Checks to see if the entity was pressed
	 * 
	 * @param pos
	 *            position
	 * @return true if positions are equal
	 */
	public boolean isPressed(Position pos) {
				
		/*double x = position.getx();
		double y = position.gety();
		
		double xpress = pos.getx();
		double ypress = pos.gety();
		
		boolean withinx = xpress >= x && xpress <= x+width;
		boolean withiny = ypress >= y && ypress <= y+height;
		
		Log.i("Controls","Withinx:"+ withinx+" Withiny:"+withiny);
		
		/*if(id == SHEEP)
		{
			
			Log.i("Controlls","Withinx:"+ withinx+" Withiny:"+withiny);
			
		}*/
		
		
		
		//return withinx && withiny;*/
		//return pos.equals(position);
		return position.equals(pos);
	}
	
	public boolean hasPosition(int col, int row){
				
		return position.getCol() == col && position.getRow() == row;
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
		
		//return pos.isInTouchRadius(getCenterPosition());
	}

	/*
	 * gets a new position object that represents the center of the entity
	 */
	private Position getCenterPosition() {

		int col = position.getCol();
		int row = position.getCol();

		double x = position.getx() + (width / 2);
		double y = position.gety() + (height / 2);

		return new Position(col, row, x, y);
	}

	protected void setDrawablePosition() {

		drawx = position.getMapxLocation(mapx);
		drawy = position.getMapyLocation(mapy);

	}

	/**
	 * 
	 * @return the x position on the map
	 */
	public double getX() {
		return position.getx();
	}

	/**
	 * 
	 * @return the y position on the map
	 */
	public double getY() {
		return position.gety();
	}

	/**
	 * Checks the identity of this entity
	 * 
	 * @param id
	 *            (see BoardObjectIDS Interface )
	 * @return true if this entity has the same id as the one specified
	 */
	public boolean isOfType(EntityID id) {

		return this.id == id;
	}
	
	public EntityID getId(){
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
