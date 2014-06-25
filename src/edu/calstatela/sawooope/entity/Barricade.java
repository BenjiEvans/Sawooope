package edu.calstatela.sawooope.entity;

import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Barricade extends BoardObject {

	Bitmap[][] sprite;
	int strength = 3;
	
	public Barricade(int col, int row, Level level) {
		super(col, row, level);
		setSprites(level.getGameView());
		blockable = true;
		id = BARRICADE;
	}

	@Override
	public void draw(Canvas g) {
		updateAnimation();
		if(isOffScreen())return;
		setDrawablePosition();
		drawBitmap(g,animator.getImage(), drawx, drawy);
			
	}
	
	private void updateAnimation(){
		if(strength <= 0)animator.setFrames(sprite[1]);
		else animator.setFrames(sprite[0]);
		
	}
	
	public void hit(){
		if(strength <= 0)return;
		strength--;
		if(strength <= 0){
			
			blockable = false;
		}
		
	}
	

	@Override
	protected void setSprites(GameView view) {
		
		sprite = new Bitmap[2][1];
		//Bitmap spriteSheet = ResourceLoader.getBufferedImage("Sprites/barricade.png");
		
		Bitmap spriteSheet;
	
		//	spriteSheet = BitmapFactory.decodeStream(assets.open("sprites/barricade.png"));
			
			spriteSheet = view.getScaledBitmap("sprites/barricade.png");
			
			spriteHeight = spriteSheet.getHeight();
			spriteWidth = spriteSheet.getWidth()/2;
			
			sprite[0][0] = Bitmap.createBitmap(spriteSheet,0,0,spriteWidth,spriteHeight);
			sprite[1][0] = Bitmap.createBitmap(spriteSheet,spriteWidth,0,spriteWidth,spriteHeight);		
			
			
			animator.setFrames(sprite[0]);
			animator.setDelay(-1);
			
		
		
		
		
		
		
	}

	
}
