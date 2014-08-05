package edu.calstatela.sawooope.main;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

/**
 * GameLoopThread calls the GameView's onDraw() method x amount of times per
 * second (depending on the frames per second specified) which ultimately
 * updates and draws the screen.
 * 
 * @author Benji
 * 
 */
public class GameLoopThread extends Thread {

	private GameView view;
	private boolean running = false;
	/**
	 * @property
	 */
	static final long FPS = 60;

	/**
	 * @param view
	 */
	public GameLoopThread(GameView view) {
		this.view = view;
	}

	/**
	 * sets whether or not the thread should be updating and drawing
	 * 
	 * @param run
	 *            sets
	 */
	public void setRunning(boolean run) {

		running = run;
	}

	/**
	 * Begins to update and draw the GameView if runnign is set to true;
	 */
	@SuppressLint("WrongCall")
	public void run() {
		
		long targetTime = 1000/FPS;
		long startTime,waitTime;		

		while (running) {

			Canvas c = null;
			startTime = System.nanoTime();
			try {

				c = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.onDraw(c);
				}
			} finally {

				if (c != null) {

					view.getHolder().unlockCanvasAndPost(c);
				}

			}
			
			waitTime = targetTime - ((System.nanoTime()-startTime)/1000000);// divide by 1000000 to get time in milliseconds
			
			try{
				
				if(waitTime > 0)sleep(waitTime);
				
			}catch(Exception ex){
				
				
			}
		}

	}
}
