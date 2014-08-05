package edu.calstatela.sawooope.entity;

import edu.calstatela.sawooope.main.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Barricades are objects that the user can place on the map to keep wolves
 * away.
 * 
 * @author Benji
 * 
 */
public class Barricade extends BoardObject {

	private static Bitmap[][] sprite;
	private static int spriteWidth;
	private static int spriteHeight;
	int hitPoints = 3;

	/**
	 * 
	 * @param col
	 *            column the barricade is placed on the map
	 * @param row
	 *            row the barricade is placed on the map
	 */
	public Barricade(int col, int row) {
		super(col, row);
		id = EntityID.BARRICADE;
	}

	@Override
	public void draw(Canvas g) {
		updateAnimation();
		if (isOffScreen())
			return;
		setDrawablePosition();
		drawBitmap(g, animator.getImage(), drawx, drawy);

	}

	private void updateAnimation() {
		if (hitPoints <= 0)
			animator.setFrames(sprite[1]);
		else
			animator.setFrames(sprite[0]);

	}

	/**
	 * Decreases the number of hit points the barricade has by 1. Barricades
	 * have 3 hit points initially
	 * 
	 */
	public void hit() {
		if (hitPoints <= 0)
			return;
		hitPoints--;
		if (hitPoints <= 0) {

		}

	}

	/**
	 * 
	 * @param view
	 *            gameview currently in use
	 */
	public static void setSprites(GameView view) {

		sprite = new Bitmap[2][1];

		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/barricade.png");

		spriteHeight = spriteSheet.getHeight();
		spriteWidth = spriteSheet.getWidth() / 2;

		sprite[0][0] = Bitmap.createBitmap(spriteSheet, 0, 0, spriteWidth,
				spriteHeight);
		sprite[1][0] = Bitmap.createBitmap(spriteSheet, spriteWidth, 0,
				spriteWidth, spriteHeight);

	}

	@Override
	protected void setAnimation() {
		width = spriteWidth;
		height = spriteHeight;
		animator.setFrames(sprite[0]);
		animator.setDelay(-1);

	}

}
