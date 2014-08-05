package edu.calstatela.sawooope.entity;

import android.graphics.Rect;

/**
 * Just a typical rectangle Object
 * 
 * @author Benji
 * 
 */
public class Rectangle {

	private int width;
	private int height;
	private int x, y;

	/**
	 * 
	 * @param x
	 *            x coordinate of top left corner
	 * @param y
	 *            y coordinate of top left corner
	 * @param width
	 *            rectangle's width
	 * @param height
	 *            rectangle's height
	 */
	public Rectangle(int x, int y, int width, int height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	/**
	 * Gets a Rect(see android api) representation of a Rectangle
	 * 
	 * @param x
	 *            top left x position of the rectangle
	 * @param y
	 *            top left y position of the rectangle
	 * @param width
	 *            rectangle's width
	 * @param height
	 *            rectangle's height
	 * @return a Rect object with the same properties specified
	 * 
	 * 
	 */
	public static Rect getRect(int x, int y, int width, int height) {

		return new Rect(x, y, x + width, y + height);

	}

	/**
	 * 
	 * @param r
	 *            rectangle
	 * @return true if the specified recatngle overlaps or touches this
	 *         rectangle
	 */
	public boolean intersects(Rectangle r) {
		int tw = this.width;
		int th = this.height;
		int rw = r.width;
		int rh = r.height;
		if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
			return false;
		}
		int tx = this.x;
		int ty = this.y;
		int rx = r.x;
		int ry = r.y;
		rw += rx;
		rh += ry;
		tw += tx;
		th += ty;
		// overflow || intersect
		return ((rw < rx || rw > tx) && (rh < ry || rh > ty)
				&& (tw < tx || tw > rx) && (th < ty || th > ry));
	}

	/**
	 * 
	 * @return the x coordinate of the top right corner
	 */
	public int getMaxX() {
		return x + width;
	}

	/**
	 * 
	 * @return the y coordinate of the bottom left corner
	 */
	public int getMaxY() {
		return y + height;
	}

	/**
	 * 
	 * @return the x coordinate of the top left corner
	 */
	public int getX() {

		return x;
	}

	/**
	 * 
	 * @return the y coordinate of the top left corner
	 */
	public int getY() {

		return y;
	}

	/**
	 * 
	 * @return width of the rectangle
	 */
	public int getWidth() {

		return width;
	}

	/**
	 * 
	 * @return height of the rectangle
	 */
	public int getHeight() {

		return height;
	}

	/**
	 * 
	 * @return the area of the rectangle
	 */
	public double getArea() {

		return width * height;
	}

	/**
	 * Determines if the point specified is within the rectangle
	 * 
	 * @param x
	 *            x coordinate of the point
	 * @param y
	 *            y coordinate of the point
	 * @return
	 */
	public boolean isWithinRectangle(int x, int y) {

		boolean withinx = x > this.x && x < this.x + width;
		boolean withiny = y > this.y && y < this.y + height;

		return withinx && withiny;
	}

	/**
	 * 
	 * @return the x coordinate of the center of this rectangle
	 */
	public int getCenterX() {

		return (int) (x + (0.5 * width));
	}

	/**
	 * 
	 * @return the y coordinate of the center of this rectangle
	 */
	public int getCenterY() {

		return (int) (y + (0.5 * height));
	}

}
