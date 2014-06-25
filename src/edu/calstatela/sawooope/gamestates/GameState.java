package edu.calstatela.sawooope.gamestates;

import edu.calstatela.sawooope.main.GameView;
import android.graphics.Canvas;

public abstract class GameState implements Runnable {
	

	protected GameStateManager gsm;
	protected static float SCALE;
	Thread thread = new Thread(this); // use this thread to setup and dispose
	protected boolean ready = false; 
	protected boolean locked = false; // use to lock input 
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		start();
	}
	
	protected  abstract void initialize();
	protected abstract void dispose();
	public abstract void update();
	public abstract void draw(Canvas g);
	public abstract void screenDragged(float x, float y);
	public abstract void screenPressed(float x, float y);
	public abstract void screenReleased(float x,float y);
	
	private void start(){thread.start();}
	public GameView getGameView(){return gsm.getGameView();}
	
	public void run(){ 
		
		initialize();
		if(thread.isAlive())thread = null;
		ready = true;				
	}
}
