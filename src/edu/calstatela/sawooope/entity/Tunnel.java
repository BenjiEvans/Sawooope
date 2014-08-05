package edu.calstatela.sawooope.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.main.GameView;

/**
 * This tunnel entity is a trap that can be used to consume sheep
 * 
 * @author Benji
 * 
 */
public class Tunnel extends BoardObject {

	// static sprite references
	private static Bitmap sprite;
	private static int spriteWidth;
	private static int spriteHeight;

	public Tunnel(int col, int row) {
		super(col, row);
		setSprites(level.getGameView());
		id = EntityID.TUNNEL;
		animator = null;
	}

	@Override
	public void draw(Canvas g) {
		setDrawablePosition();
		drawBitmap(g, sprite, drawx, drawy);
	}

	/**
	 * Sets static sprite references for this class
	 * 
	 * @param view
	 *            gameview in use
	 */
	public static void setSprites(GameView view) {

		sprite = view.getScaledBitmap("sprites/tunnel.png");

		spriteHeight = sprite.getHeight();
		spriteWidth = sprite.getWidth();

	}

	@Override
	protected void setAnimation() {
		width = spriteWidth;
		height = spriteHeight;
	}

}
