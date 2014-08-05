package edu.calstatela.sawooope.entity.plant;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.entity.EntityID;
import edu.calstatela.sawooope.entity.creature.hunger.Edible;
import edu.calstatela.sawooope.main.GameView;

/**
 * *
 * 
 * @author Benji
 * 
 */

public class GrassPatch extends Plant implements Edible {

	private int layer = 1;
	private long grassTimer;
	private static long regrowTime = 3000l;
	private static ArrayList<Bitmap[]> sprites = new ArrayList<Bitmap[]>();
	private static int spriteWidth;
	private static int spriteHeight;

	/**
	 * 
	 * @param col
	 *            column on map
	 * @param row
	 *            row on map
	 */
	public GrassPatch(int col, int row) {
		super(col, row);
		id = EntityID.GRASS;
	}

	public void update() {

		if (grassTimer != 0) {
			long grassTimerDiff = (System.nanoTime() - grassTimer) / 1000000;

			if (grassTimerDiff > regrowTime) {
				grassTimer = 0;
				layer = 1;
			}
		}

		updateAnimation();
	}

	private void updateAnimation() {

		if (layer == 1)
			animator.setFrames(sprites, 0);
		else if (layer == 0)
			animator.setFrames(sprites, 1);

	}

	@Override
	public void draw(Canvas g) {
		update();
		if (isOffScreen())
			return;
		super.draw(g);
		drawBitmap(g, animator.getImage(), drawx, drawy);

	}

	/**
	 * Sets static reference to sprites for the GrassPatch class
	 * 
	 * @param view
	 *            current game view
	 */
	public static void setSprites(GameView view) {

		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/grass.png");

		spriteWidth = spriteSheet.getWidth() / 2;
		spriteHeight = spriteSheet.getHeight();

		for (int i = 0; i < 2; i++) {
			Bitmap[] sprite = new Bitmap[1];

			sprite[0] = Bitmap.createBitmap(spriteSheet, i * spriteWidth, 0,
					spriteWidth, spriteHeight);

			sprites.add(sprite);

		}
	}

	/**
	 * Determines whether this grass patch has a layer of grass
	 * 
	 * @return true if the grass layer is greater than 0
	 */
	public boolean hasLayer() {

		return layer == 1;
	}

	@Override
	protected void setAnimation() {

		width = spriteWidth;
		height = spriteHeight;

		animator.setFrames(sprites.get(0));
		animator.setDelay(-1);
	}

	@Override
	public float consume() {
		if (layer == 0)
			return 0;
		layer--;
		grassTimer = System.nanoTime();
		return 0.25f;
	}

}
