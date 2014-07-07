package edu.calstatela.sawooope.gamestates;

import edu.calstatela.sawooope.main.GameView;
import android.graphics.Canvas;

/**
 * A GameState is essentially what's being viewed on user's screen.For Example
 * we might want to show a menu. So we draw a menu on the user's screen. On the
 * menu screen there might be a button that says "credits". If the users clicks
 * the credits button, then instead of showing the menu we would now display the
 * credits "page" (more like credits GameState). Of course those two "pages"
 * would look pretty different, but they would both be GameStates since they:
 * <ol>
 * <li>Both handle input from the user's device</li>
 * <li>Both have something to display on the user's screen</li>
 * <li>
 * Both (in the case of animations) would need to have their screen's updated
 * and redrawn</li>
 * </ol>
 * 
 * <p>
 * When a GameState is created it automatically runs initialize() on a separate
 * thread. When initialize is done executing, a boolean property "ready" is set
 * to true. <strong> Nothing should be draw or updated in a GameState until
 * ready is true. </strong>
 * </p>
 * 
 * @author Benji
 * 
 */
public abstract class GameState implements Runnable {

	protected GameStateManager gsm;
	protected static float SCALE;
	Thread thread = new Thread(this); // use this thread to setup
	protected boolean ready = false;
	protected boolean locked = false; // use to lock input

	/**
	 * 
	 * @param gsm
	 *            current GameStateManager
	 */
	protected GameState(GameStateManager gsm) {
		this.gsm = gsm;
		start();
	}

	/**
	 * Initializes objects in a GameState here
	 */
	protected abstract void initialize();

	/**
	 * clears this state from memory
	 */
	protected abstract void dispose();

	/**
	 * updates the screen
	 */
	public abstract void update();

	/**
	 * Draws what should be on the
	 * 
	 * @param g
	 *            canvas to draw on
	 */
	public abstract void draw(Canvas g);

	/**
	 * Handles screen input when screen is dragged
	 * 
	 * @param x
	 *            x location on screen of where the event took place
	 * @param y
	 *            y location on screen of where the event took place
	 */
	public abstract void screenDragged(float x, float y);

	/**
	 * Handles screen input when screen is pressed
	 * 
	 * @param x
	 *            x location on screen of where the event took place
	 * @param y
	 *            y location on screen of where the event took place
	 */
	public abstract void screenPressed(float x, float y);

	/**
	 * Handles screen input when screen is released
	 * 
	 * @param x
	 *            x location on screen of where the event took place
	 * @param y
	 *            y location on screen of where the event took place
	 */
	public abstract void screenReleased(float x, float y);

	private void start() {
		thread.start();
	}

	/**
	 * Gets the GameView being used by the gsm
	 * 
	 * @return current game view
	 */
	public GameView getGameView() {
		return gsm.getGameView();
	}

	public void run() {

		initialize();
		if (thread.isAlive())
			thread = null;
		ready = true;
	}
}
