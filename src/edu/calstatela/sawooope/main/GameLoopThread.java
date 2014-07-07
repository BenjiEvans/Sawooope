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

		long ticksPerSec = 700 / FPS;
		long startTime;
		long sleepTime;

		while (running) {

			Canvas c = null;
			startTime = System.currentTimeMillis();
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

			sleepTime = ticksPerSec - (System.currentTimeMillis() - startTime);

			try {

				if (sleepTime > 0)
					sleep(sleepTime);
				else
					sleep(10);

			} catch (Exception ex) {
			}

		}

	}
}
