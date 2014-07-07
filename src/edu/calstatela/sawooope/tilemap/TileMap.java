package edu.calstatela.sawooope.tilemap;

import java.util.Scanner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.main.GameView;

/**
 * TileMap is just as it's name suggests, a map of tiles. Every Level must have
 * a TileMap otherwise there is no point to the game. TileMap has x amount of
 * columns and y amount of row and each row, column position has a Tile
 * 
 * @author Benji
 * 
 */
public class TileMap {

	private double x, y; // position of where to "start" drawing the map
	/**
	 * @property This broad cast's the offset of the map so that the game can be
	 *           view ported.
	 */
	private static double mapx, mapy;

	double scrollx;
	double scrolly;

	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;

	// Map Variables
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;

	// tileSet
	private Bitmap tileSet;
	private int numTilesAcross;
	private Tile[][] tiles;

	// drawing
	private int rowOffSet;
	private int colOffSet;
	private int numOfColsToDraw;
	private int numOfRowsToDraw;

	/**
		 * 
		 */
	public TileMap() {

		mapx = 0;
		mapy = 0;

	}

	public static double getMapx() {
		return mapx;
	}

	public static double getMapy() {
		return mapy;
	}

	/**
	 * Gets a set of tiles in the assets folder specified at the inputed
	 * location and loads them into memory
	 * 
	 * @param view
	 *            game view in use
	 * @param location
	 *            tile URI in assets folder
	 */
	public void loadTiles(GameView view, String location) {

		// gets an image that holds all the tiles
		tileSet = view.getScaledBitmap(location);

		tileSize = tileSet.getWidth() / 2;

		numOfRowsToDraw = (view.getHeight()) / tileSize + 2;
		numOfColsToDraw = (view.getWidth()) / tileSize + 2;

		numTilesAcross = tileSet.getWidth() / tileSize;
		tiles = new Tile[2][numTilesAcross];

		Bitmap subImage;

		for (int col = 0; col < numTilesAcross; col++) {
			subImage = Bitmap.createBitmap(tileSet, col * tileSize, 0,
					tileSize, tileSize);

			tiles[0][col] = new Tile(subImage, Tile.BLOCKED);

			subImage = Bitmap.createBitmap(tileSet, col * tileSize, tileSize,
					tileSize, tileSize);

			tiles[1][col] = new Tile(subImage, Tile.NORMAL);

		}

	}

	/**
	 * Gets the map specified by the location in the assets folder and loads it
	 * into memory
	 * 
	 * @param view
	 *            game view in use
	 * @param location
	 *            URI of map file
	 */
	public void loadMap(GameView view, String location) {

		try {

			Scanner scan = new Scanner(view.getResources().getAssets()
					.open(location));
			numRows = Integer.parseInt(scan.nextLine());
			numCols = Integer.parseInt(scan.nextLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;

			xmin = view.getWidth() - this.width;
			xmax = 0;
			ymin = (view.getHeight()) - this.height;
			ymax = 0;

			// read in the matrix

			String delimeter = "\\s+"; // white space

			for (int row = 0; row < numRows; row++) {
				String line = scan.nextLine();
				String[] tokens = line.split(delimeter);

				for (int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the tile type at the specified position
	 * 
	 * @param row
	 *            tile's row
	 * @param col
	 *            tile's column
	 * @return the tile type are the specified position
	 * @throws ArrayIndexOutOfBoundsException
	 *             if the row and column specified are not on the map
	 */
	public int getType(int row, int col) throws ArrayIndexOutOfBoundsException {

		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;

		return tiles[r][c].getType();

	}

	/**
	 * @param x
	 *            x position on the screen that was pressed
	 * @param y
	 *            y position on the screen that was pressed
	 */
	public void setScroll(float x, float y) {
		scrollx = x;
		scrolly = y;

	}

	/**
	 * 
	 * @param x
	 *            x position on the screen when the screen is being dragged
	 * @param y
	 *            y position on the screen when the screen is being dragged
	 */
	public void scroll(float x, float y) {

		double dx = (x - scrollx);
		double dy = (y - scrolly);

		this.x += dx;
		this.y += dy;

		fixBounds();

		colOffSet = (int) -this.x / tileSize;
		rowOffSet = (int) -this.y / tileSize;

		scrollx = x;
		scrolly = y;

	}

	/**
	 * 
	 * @return the total number of columns on the map
	 */
	public int getNumCols() {

		return numCols;
	}

	/**
	 * 
	 * @return the total number of rows on the map
	 */
	public int getNumRows() {

		return numRows;
	}

	private void fixBounds() {
		if (x < xmin)
			x = xmin;

		if (y < ymin)
			y = ymin;

		if (x > xmax)
			x = xmax;
		if (y > ymax)
			y = ymax;

	}

	/**
	 * Draws the TileMap
	 * 
	 * @param g
	 *            canvas beign drawn on
	 */
	public void draw(Canvas g) {

		mapx = x;
		mapy = y;

		for (int row = rowOffSet; row < rowOffSet + numOfRowsToDraw; row++) {
			if (row >= numRows)
				break;

			for (int col = colOffSet; col < colOffSet + numOfColsToDraw; col++) {

				if (col >= numCols)
					break;
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				g.drawBitmap(tiles[r][c].getImage(), (int) x + col * tileSize,
						(int) y + row * tileSize, null);

			}

		}
	}

	/**
	 * Checks to see if the specified column and row are blocked by a tile
	 * 
	 * @param col
	 *            column being checked
	 * @param row
	 *            row being checked
	 * @return true if the tile at position if of type BLOCKED
	 */
	public boolean isTileBlocking(int col, int row) {
		try {
			return Tile.BLOCKED == getType(row, col);
		} catch (ArrayIndexOutOfBoundsException ex) {

			ex.printStackTrace();

			return true;
		}

	}

	/**
	 * 
	 * @return the number of rows that should be drawn on the screen
	 */
	public int getNumOfRowsToDraw() {

		return numOfRowsToDraw;
	}

	/**
	 * 
	 * @return the number of columns that should be drawn on the screen
	 */
	public int getNumOfColsToDraw() {

		return numOfColsToDraw;
	}

	/**
	 * 
	 * @return the starting column that should be drawn
	 */
	public int getColumOffSet() {
		// TODO Auto-generated method stub
		return colOffSet;
	}

	/**
	 * 
	 * @return the starting row that should be drawn
	 */
	public int getRowOffSet() {

		return rowOffSet;
	}

	public void dispose() {
		map = null;
		tileSet = null;
		tiles = null;
	}

}
