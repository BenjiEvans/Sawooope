package edu.calstatela.sawooope.entity.plant;

import java.util.ArrayList;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.gamestates.levels.Level;

import android.graphics.Bitmap;
import android.graphics.Canvas;


public abstract class Plant extends BoardObject {

	//HashMap<String, BufferedImage[]>sprites = new HashMap<String, BufferedImage[]>();
	ArrayList<Bitmap[]> sprites = new ArrayList<Bitmap[]>();
	
	Plant(int col, int row,Level level) {
		super(col, row, level);
		setSprites(level.getGameView());
	}
	
	public void draw(Canvas g){
		setDrawablePosition();
	}

	
}
