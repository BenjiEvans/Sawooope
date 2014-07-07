package edu.calstatela.sawooope.entity.creature;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.main.GameView;

/**
 * 
 * @author Benji
 *
 */
public class Wolf extends Creature{
	
	public Wolf(int col, int row){
		super(col,row);
		id = WOLF;
		speed = 1;
		box = new CollisionBox(this,4,4,24,28);
		
	}
		
	@Override
	public void update() {
		
	
		
		
	}
	
	
	@Override
	public void draw(Canvas g) {
		if(isOffScreen())return;
		super.draw(g);
	}
	
	
	protected void updateAnimation(){
		
		super.updateAnimation();
	}
	
	
	@Override
	public void setSprites(GameView view) {
		
		super.setSprites(view);
		
		Bitmap spriteSheet;
	
			spriteSheet = view.getScaledBitmap("sprites/wolf/wolf.png");
			
			spriteWidth = spriteSheet.getWidth()/3;
			spriteHeight = spriteSheet.getHeight()/4;
			
			String[] hashOrder = {"South","West","East", "North"};
			
			
			setIdleSprites(spriteSheet,hashOrder);
			setWalkingSprites(spriteSheet,hashOrder);
			
			animator.setFrames(sprites.get("South"),IDLE);
			animator.setDelay(-1);

		
		
	}
	
	private void setIdleSprites(Bitmap spriteSheet, String[] hashOrder){
		
		for(int i = 0; i < hashOrder.length; i++)
		{
			ArrayList<Bitmap[]> idleSprites = sprites.get(hashOrder[i]);
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,spriteHeight*i,spriteWidth,spriteHeight);
			
			idleSprites.add(image);
		}
		
		
	}
	
	private void setWalkingSprites(Bitmap spriteSheet, String[] hashOrder){
		
		for(int i = 0; i < hashOrder.length; i++)
		{
			ArrayList<Bitmap[]> walkingSprites = sprites.get(hashOrder[i]);
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0,spriteHeight*i,spriteWidth,spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet,2*spriteWidth,spriteHeight*i,spriteWidth,spriteHeight);
			walkingSprites.add(frames);
		}
		
		
	}
	
	
	
	protected boolean northValid(){
		
		if(!super.northValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();		
		
		if(level.isPositionBlocked(col, row-1))return false;
		//if(level.packHasPosition(this,col,row-1))return false;
	//	if(level.portalHasColRow(col,row-1))return false;
		//if(packHasPosition(col,row-1)) return false;
		//if(packHasNextPosition(col,row-1)) return false;
		return true;
	}
	
	protected boolean southValid(){
		
		if(!super.southValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();		
		
		if(level.isPositionBlocked(col, row+1))return false;
		//if(level.packHasPosition(this,col,row+1))return false;
		//if(level.portalHasColRow(col, row+1))return false;
		
		//if(packHasPosition(col,row+1)) return false;
		//if(packHasNextPosition(col,row+1)) return false;
		
		return true;
		
	}
	
	protected boolean eastValid(){
		
		if(!super.eastValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();				
		
		if(level.isPositionBlocked(col+1, row))return false;
		//if(level.packHasPosition(this,col+1,row))return false;
		//if(level.portalHasColRow(col+1,row))return false;
		//if(packHasPosition(col+1,row)) return false;
		//if(packHasNextPosition(col+1,row)) return false;
		
		return true;
	}
	
	protected boolean westValid(){
		
		if(!super.westValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();				
		
		if(level.isPositionBlocked(col-1,row))return false;
		//if(level.packHasPosition(this,col-1,row))return false;
		//if(level.portalHasColRow(col-1, row))return false;
		//if(level.packHasPosition(this,col-1,row))return false;
		//if(packHasPosition(col-1,row)) return false;
		//if(packHasNextPosition(col-1,row)) return false;
		return true;
	}
	
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(int direction) {
		// TODO Auto-generated method stub
		
	}

	
}
