package edu.calstatela.sawooope.entity.plant;

import edu.calstatela.sawooope.entity.BoardObject;
import android.graphics.Canvas;

/**
 * 
 * 
 * @author Benji
 * 
 */
public abstract class Plant extends BoardObject {

	Plant(int col, int row) {
		super(col, row);
	}

	public void draw(Canvas g) {
		setDrawablePosition();
	}

}
