package edu.calstatela.sawooope.entity;

import edu.calstatela.sawooope.main.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Ignore for now
 * 
 * @author Benji
 * 
 */
public class Barricade extends BoardObject {

	private static Bitmap[][] sprite;
	private static int spriteWidth;
	private static int spriteHeight;
	int strength = 3;

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
		if (strength <= 0)
			animator.setFrames(sprite[1]);
		else
			animator.setFrames(sprite[0]);

	}

	public void hit() {
		if (strength <= 0)
			return;
		strength--;
		if (strength <= 0) {

		}

	}

	public static  void setSprites(GameView view) {

		sprite = new Bitmap[2][1];
		// Bitmap spriteSheet =
		// ResourceLoader.getBufferedImage("Sprites/barricade.png");

		Bitmap spriteSheet;

		// spriteSheet =
		// BitmapFactory.decodeStream(assets.open("sprites/barricade.png"));

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
