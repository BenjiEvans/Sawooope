package edu.calstatela.sawooope.entity.plant;

import java.util.ArrayList;

import edu.calstatela.sawooope.entity.BoardObject;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Not much to document... Ignore for now
 * 
 * @author Benji
 * 
 */
public abstract class Plant extends BoardObject {

	ArrayList<Bitmap[]> sprites = new ArrayList<Bitmap[]>();

	Plant(int col, int row) {
		super(col, row);
		setSpriteDimentions(level.getGameView());
	}

	public void draw(Canvas g) {
		setDrawablePosition();
	}

}
