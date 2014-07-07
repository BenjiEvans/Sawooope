package edu.calstatela.sawooope.entity.plant;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.main.GameView;

/**
 * Not yet much to document...Ignore for now
 * 
 * @author Benji
 * 
 */

public class GrassPatch extends Plant {

	private int height = 1;
	private long grassTimer;
	private long regrowTime;
	private long eatTime = 2000;

	public GrassPatch(int col, int row, long growDelay) {
		super(col, row);
		id = GRASS;
		regrowTime = growDelay;
	}

	public void update() {

		if (grassTimer != 0) {
			long grassTimerDiff = (System.nanoTime() - grassTimer) / 1000000;

			if (grassTimerDiff > regrowTime) {
				grassTimer = 0;
				height = 1;
			}
		}

		updateAnimation();
	}

	private void updateAnimation() {

		if (height == 1)
			animator.setFrames(sprites, 0);
		else if (height == 0)
			animator.setFrames(sprites, 1);

	}

	public void eatLayer() {
		if (height == 0)
			return;
		height--;
		grassTimer = System.nanoTime();
	}

	@Override
	public void draw(Canvas g) {
		update();
		if (isOffScreen())
			return;
		super.draw(g);
		drawBitmap(g, animator.getImage(), drawx, drawy);

	}

	@Override
	protected void setSprites(GameView view) {

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

		animator.setFrames(sprites.get(0));
		animator.setDelay(-1);

	}

	public boolean hasLayer() {

		return height == 1;
	}

}
