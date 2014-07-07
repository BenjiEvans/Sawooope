package edu.calstatela.sawooope.tilemap;

/**
 * 
 */
import android.graphics.Bitmap;

/**
 * Tile is a SQUARE(important!) image that is either of type normal(meaning that
 * objects can occupy the same Position as the tile) or of type blocked(where an
 * object cannot occupy the same Position).
 * 
 * @author Benji
 */
public class Tile {

	private Bitmap image;
	private int type;

	// tile types
	/**
	 * @property
	 */
	public static final int BLOCKED = 0;

	/**
	 * @property
	 */
	public static final int NORMAL = 1;

	/**
	 * 
	 * @param image
	 *            tile image
	 * @param type
	 *            can be BLOCKED or NORMAL
	 */
	public Tile(Bitmap image, int type) {

		this.image = image;
		this.type = type;
	}

	/**
	 * 
	 * @return the tile's image
	 */
	public Bitmap getImage() {
		return image;
	}

	/**
	 * Gets the tile type
	 * 
	 * @return either BLOCKED or NORMAL
	 */
	public int getType() {
		return type;
	}

}
