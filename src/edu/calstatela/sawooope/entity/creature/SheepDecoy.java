package edu.calstatela.sawooope.entity.creature;

import java.io.IOException;
import java.util.ArrayList;

import edu.calstatela.sawooope.R;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class SheepDecoy extends Sheep{

	Bitmap[][] sprites;
	private static final int IDLE = 0;
	private static final int TELE = 1;
	private int currentAction;
	boolean teleport;
	ArrayList<int[]>pos;
	/*public static Bitmap getImage(){
		
		BufferedImage sprite = ResourceLoader.getBufferedImage("Sprites/Sheep/sheeps.png");
		int width = sprite.getWidth()/12;
		int height = sprite.getHeight()/8;
		return sprite.getSubimage(width, 0, width, height);
	}*/
	
	//Timer updateLoop = new Timer(1000/60, new UpdateLoop());
	
	public SheepDecoy(int col, int row, Level level) {
		super(col, row, level);
		id = SHEEP_DECOY;
		setSprites(level.getGameView());
		pos = new ArrayList<int[]>();
		
		int[] p1 = {col,row+1};
		int[] p2 = {col,row-1};
		int[] p3 = {col+1,row};
		int[] p4 = {col-1,row};
		
		pos.add(p1);
		pos.add(p2);
		pos.add(p3);
		pos.add(p4);
		
		
	}
		
	public void update(){
		
		if(done)return;
		
		Log.i("SheepDecoy","Checking wolves positions....");
		if(level.packHasPosition(pos))
		{
			teleport = true;
		}
		Log.i("SheepDecoy","Upating animation...");
		updateAnimation();	
	}
	
	
	protected void updateAnimation(){
		
		if(teleport)
		{
			Log.i("SheepDecoy","Teleporting...");
			if(currentAction != TELE)
			{
				
					
					currentAction = TELE;
					animator.setFrames(sprites[currentAction]);
					animator.setDelay(100);									
			}
			
			if(animator.hasPlayedOnce()){
				done = true;
			}
			
		}
		Log.i("SheepDecoy","Done Upating animation...");
		
		animator.update();
	}
	

	@Override
	public void draw(Canvas g) {
		if(done)return;
		try{
			update();
		}catch(Exception ex){
			Log.i("SheepDecoy",ex.getMessage());
		}
		//update();
		setDrawablePosition();
		drawBitmap(g,animator.getImage(),drawx,drawy);	
	}

	@Override
	protected void setSprites(GameView view) {
		
		
		/*Bitmap spriteSheet1 = BitmapResourceLoader.getBufferedImage("Sprites/Sheep/sheeps.png");
		Bitmap spriteSheet2 = ResourceLoader.getBufferedImage("Sprites/Sheep/sheep_transporter.png");
		*/
		
			/*Bitmap spriteSheet1 = BitmapFactory.decodeStream(assets.open("sprites/sheep/sheeps.png"));
			Bitmap spriteSheet2 = BitmapFactory.decodeStream(assets.open("sprites/sheep/sheep_transporter.png"));*/
			Bitmap spriteSheet1 = view.getScaledBitmap("sprites/sheep/sheeps.png");
			Bitmap spriteSheet2 = view.getScaledBitmap("sprites/sheep/sheep_transporter.png");
			
			
			spriteWidth = spriteSheet1.getWidth()/12;
			spriteHeight = spriteSheet1.getHeight()/8;
				
			sprites = new Bitmap[2][4];
			
			for(int i = 0; i < 4 ; i++)
			{
				sprites[IDLE][i] = Bitmap.createBitmap(spriteSheet1, spriteWidth,spriteHeight*i,spriteWidth,spriteHeight);
				sprites[TELE][i] = Bitmap.createBitmap(spriteSheet2, i*spriteWidth,0, spriteWidth, spriteHeight);	
			}
			currentAction = IDLE;
			animator.setFrames(sprites[currentAction]);
			animator.setDelay(-1);
			
		
	}
	
	public int compareTo(Object sheep) {
		
		//if(dead) return -1;
		
		Sheep s = (Sheep)sheep;
		
		double dist = s.getDistance();
		
		if(dist > distance)return 1;
		else if(dist < distance) return -1;
		else return 0;		
		
	}

	
}
