package edu.calstatela.sawooope.entity.plant;

import java.io.IOException;
import java.util.ArrayList;

import edu.calstatela.sawooope.R;
import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Tree extends Plant {

	
	public Tree(int col, int row, Level level) {
		super(col, row, level);
		blockable = true;
		id = BoardObject.TREE;
	}

	@Override
	protected void setSprites(GameView view) {
		// TODO Auto-generated method stub
		sprites = new ArrayList<Bitmap[]>();
		Bitmap image;
			//image = BitmapFactory.decodeStream(assets.open("sprites/tree.png"));
			
			image = view.getScaledBitmap("sprites/tree.png");
			
			spriteWidth = image.getWidth();
			spriteHeight = image.getHeight();
			Bitmap[] sprite = {image};
			
			sprites.add(sprite);
			
			animator.setFrames(sprites,0);
			animator.setDelay(-1);
			
		
		//BufferedImage image = ResourceLoader.getBufferedImage("Sprites/tree.png");
		
		
		
	}
	
	public void draw(Canvas g){
		super.draw(g);
		if(isOffScreen())return;
		int y = (position.getRow()*Level.getGridSize())-spriteHeight/2;
		drawBitmap(g,animator.getImage(),drawx,y+mapy);
			
	}

	

}
