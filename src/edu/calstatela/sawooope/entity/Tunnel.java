package edu.calstatela.sawooope.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.main.GameView;

/**
 * Ignore for now
 * 
 * @author Benji
 * 
 */
public class Tunnel extends BoardObject {

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
