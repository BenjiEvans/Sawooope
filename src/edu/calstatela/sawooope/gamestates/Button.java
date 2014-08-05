package edu.calstatela.sawooope.gamestates;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Button is just a click-able image
 * 
 * @author Benji
 * 
 */
public class Button {

	private int x;
	private int y;
	private String title;
	private Bitmap img;

	/**
	 * 
	 * @param x
	 *            x location on screen
	 * @param y
	 *            y location on screen
	 * @param img
	 *            button image
	 * @param title
	 *            button descriptor
	 */
	protected Button(int x, int y, Bitmap img, String title) {

		this.x = x;
		this.y = y;
		this.img = img;
		this.title = title;
	}

	/**
	 * 
	 * @return the button's descriptor
	 */
	public String getDescriptor() {
		return title;
	}

	/**
	 * 
	 * @return the y coordinate for the bottom of the button
	 */
	public int getMaxY() {

		return y + img.getHeight();
	}

	/**
	 * Draws the button on screen
	 * 
	 * @param g
	 *            canvas to draw on
	 */
	public void draw(Canvas g) {

		g.drawBitmap(img, x, y, null);
	}

	/**
	 * Determins if the button has been clicked
	 * 
	 * @param x
	 *            x location where screen was pressed
	 * @param y
	 *            y location where screen was pressed
	 * @return true if the button is clicked
	 */
	public boolean isClicked(float x, float y) {

		boolean withinx = x > this.x && x < this.x + img.getWidth();
		boolean withiny = y > this.y && y < this.y + img.getHeight();

		return withinx && withiny;
	}

}
