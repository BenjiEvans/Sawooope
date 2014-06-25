package edu.calstatela.sawooope.entity;

import java.io.IOException;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import edu.calstatela.sawooope.R;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.tilemap.TileMap;

public class Tunnel extends BoardObject {

	Bitmap sprite;
	
	public Tunnel(int col, int row, Level level) {
		super(col, row, level/*, map*/);
		setSprites(level.getGameView());
		id = TUNNEL;
		animator = null;
	}

	@Override
	public void draw(Canvas g) {
		setDrawablePosition();		
		drawBitmap(g,sprite,drawx,drawy);		
	}

	@Override
	protected void setSprites(GameView view) {
		
		sprite = view.getScaledBitmap("sprites/tunnel.png");

	}

	
}
