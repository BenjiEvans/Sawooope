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

	Bitmap sprite;

	public Tunnel(int col, int row) {
		super(col, row);
		setSpriteDimentions(level.getGameView());
		id = EntityID.TUNNEL;
		animator = null;
	}

	@Override
	public void draw(Canvas g) {
		setDrawablePosition();
		drawBitmap(g, sprite, drawx, drawy);
	}

	@Override
	protected void setSpriteDimentions(GameView view) {

		sprite = view.getScaledBitmap("sprites/tunnel.png");

	}

}
