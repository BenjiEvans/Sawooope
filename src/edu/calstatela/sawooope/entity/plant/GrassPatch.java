package edu.calstatela.sawooope.entity.plant;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;



public class GrassPatch extends Plant  {
	
	ArrayList<BoardObject> view = new ArrayList<BoardObject>();
	private int height = 1;
	private boolean munched;
	private long grassTimer;
	private long regrowTime;
	private long eatTime = 2000;
	
	public GrassPatch(int col, int row, Level level, long growDelay){
		super(col,row,level);
		id = GRASS;
		regrowTime = growDelay;
		//thread.start();
	}
	
	public void update(){
		
		if(grassTimer != 0)
		{
			long grassTimerDiff = (System.nanoTime() - grassTimer)/1000000;
			
			if(grassTimerDiff > regrowTime)
			{
				grassTimer = 0;	
				height = 1;
				munched = false;
			}
		}
		
		updateAnimation();
	}
	
	private void updateAnimation(){
		
		if(height == 1)animator.setFrames(sprites,0);
		else if( height == 0)animator.setFrames(sprites,1);
		
	}
	
	public long getEatTime(){return eatTime;}
	
	public void eatLayer(){
		if(height == 0) return;
		height--;
		grassTimer = System.nanoTime();
	}
		
	
	@Override
	public void draw(Canvas g) {
		update();
		if(isOffScreen())return;
		super.draw(g);
		drawBitmap(g,animator.getImage(), drawx, drawy);
		
		
	}
	@Override
	protected void setSprites(GameView view) {
		// TODO Auto-generated method stub
		
		//Bitmap spriteSheet = ResourceLoader.getBufferedImage("grass.png");
		
		Bitmap spriteSheet;
			
		//	spriteSheet = BitmapFactory.decodeStream(assets.open("sprites/grass.png"));
			
			spriteSheet = view.getScaledBitmap("sprites/grass.png");
			
			spriteWidth = spriteSheet.getWidth()/2;
			spriteHeight = spriteSheet.getHeight();
			
			for(int i =0; i < 2; i++)
			{
				Bitmap[] sprite = new Bitmap[1];
				
				sprite[0] = Bitmap.createBitmap(spriteSheet,i*spriteWidth,0, spriteWidth, spriteHeight);
				
				sprites.add(sprite);
				
			}
				
		animator.setFrames(sprites.get(0));
		animator.setDelay(-1);
		
	}

	
	public boolean hasLayer() {
		// TODO Auto-generated method stub
		return height == 1;
	}

	public boolean isBeingMunchedOn() {
		// TODO Auto-generated method stub
		return munched;
	}
	
	public void setMunchedOn(boolean bool){
		
		munched = bool;
	}
	
	
}
