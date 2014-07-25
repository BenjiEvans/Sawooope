package edu.calstatela.sawooope.entity.creature;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import edu.calstatela.sawooope.entity.EntityID;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;

/**
 * 
 * @author Benji
 * 
 */
public class Wolf extends Creature {

	private static SpriteSet sprites;
	private static int spriteWidth;
	private static int spriteHeight;
	
	public Wolf(int col, int row) {
		super(col, row);
		id = EntityID.WOLF;
		speed = 1;
		float scale = Level.getScale();
		box = new CollisionBox(this, (int)(4*scale), (int)(4*scale),(int)(24*scale), (int)(28*scale));

	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Canvas g) {
		if (isOffScreen())
			return;
		super.draw(g);
	}

	protected void updateAnimation() {

		super.updateAnimation(sprites);
	}


	public static void setSprites(GameView view) {

		sprites = new SpriteSet();
		
		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/wolf/wolf.png");

		spriteWidth = spriteSheet.getWidth() / 3;
		spriteHeight = spriteSheet.getHeight() / 4;
		
		int[] order  = {SOUTH,WEST,EAST,NORTH};
		int length = order.length;
		//set Idle sprites 
		
		for (int i = 0; i < length; i++) {
		
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);

			sprites.getFrames(order[i]).setIdleFrames(image);
		}
		
		//set walking sprites 
		for (int i = 0; i < length; i++) {
			
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0, spriteHeight * i,
					spriteWidth, spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet, 2 * spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			sprites.getFrames(order[i]).setWalkingFrames(frames);
		}
		

		/*animator.setFrames(sprites.getFrames(SOUTH).getIdleFrames(), IDLE);
		animator.setDelay(-1);*/

		/*String[] hashOrder = { "South", "West", "East", "North" };

		setIdleSprites(spriteSheet, hashOrder);
		setWalkingSprites(spriteSheet, hashOrder);

		animator.setFrames(sprites.get("South"), IDLE);
		animator.setDelay(-1);*/

	}

	/*private void setIdleSprites(Bitmap spriteSheet, String[] hashOrder) {

		for (int i = 0; i < hashOrder.length; i++) {
			ArrayList<Bitmap[]> idleSprites = sprites.get(hashOrder[i]);
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);

			idleSprites.add(image);
		}

	}

	private void setWalkingSprites(Bitmap spriteSheet, String[] hashOrder) {

		for (int i = 0; i < hashOrder.length; i++) {
			ArrayList<Bitmap[]> walkingSprites = sprites.get(hashOrder[i]);
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0, spriteHeight * i,
					spriteWidth, spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet, 2 * spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			walkingSprites.add(frames);
		}

	}*/

	protected boolean northValid() {

		if (!super.northValid())
			return false;

		return true;
	}

	protected boolean southValid() {

		if (!super.southValid())
			return false;

		return true;

	}

	protected boolean eastValid() {

		if (!super.eastValid())
			return false;

		

		return true;
	}

	protected boolean westValid() {

		if (!super.westValid())
			return false;

		
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

	@Override
	protected void setAnimation() {
		
		width = spriteWidth;
		height = spriteHeight;
		
		animator.setFrames(sprites.getFrames(SOUTH).getIdleFrames(), IDLE);
		animator.setDelay(-1);

	}

	@Override
	public void move(int col, int row) {
		
		
		
	}

}
