package edu.calstatela.sawooope.gamestates;

import java.util.Stack;

import edu.calstatela.sawooope.gamestates.levels.TestingGround;
import edu.calstatela.sawooope.main.GameView;

import android.graphics.Canvas;

/**
 * Our Game Is made up of many GameStates. In order to keep track of which one
 * we want to display on screen we can use a GameState Manager. The GameState
 * Manager (gsm for short) keeps track of which GameState we are currently
 * updating and drawing; and also passed any motion(touch, swipe, etc) input to
 * the current GameState so it can be handled properly
 * <p>
 * Also note that the GameState Manager uses the getState() (which takes in and
 * integer as a parameter)to create instances of a GameState. Every GameState
 * should be archived in the GameState Manager class as a "static final int".
 * For example:
 * <ul>
 * <li>Menu is a GameState, archived as in int with the value 0</li>
 * <li>Testing is a GameState, archived as in int with the value 1</li>
 * </ul>
 * </p>
 * 
 * @author Benji
 */
public class GameStateManager {

	private Stack<GameState> gameStates;
	private GameView view;
	/**
	 * @property
	 */
	public static final int MENU = 0;
	/**
	 * @property
	 */
	public static final int TESTING = 1;

	/**
	 * 
	 * @param view
	 *            current game view
	 */
	public GameStateManager(GameView view) {

		this.view = view;
		GameState.SCALE = GameView.getBitmapScale();
		gameStates = new Stack<GameState>();
		pushState(MENU);

	}

	/**
	 * Gets the current GameState and updates the screen
	 */
	public void update() {

		try {
			gameStates.peek().update();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Gets the current GameState and draws the updated screen
	 * 
	 * @param g
	 *            canvas to draw on
	 */
	public void draw(Canvas g) {

		try {
			gameStates.peek().draw(g);
		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	/**
	 * Passes coordinates to the current GameState correlating to where the
	 * screen was pressed and dragged to.
	 * 
	 * @param x
	 *            x location on screen of where the event took place
	 * @param y
	 *            y location on screen of where the event took place
	 */
	public void screenDragged(float x, float y) {

		gameStates.peek().screenDragged(x, y);

	}

	/**
	 * Passes coordinates to the current GameState correlating to where the
	 * screen was pressed
	 * 
	 * @param x
	 *            x location on screen of where the event took place
	 * @param y
	 *            y location on screen of where the event took place
	 */
	public void screenPressed(float x, float y) {

		gameStates.peek().screenPressed(x, y);
	}

	/**
	 * 
	 * Passes coordinates to the current GameState correlating to where the
	 * screen was Released
	 * 
	 * @param x
	 *            x location on screen of where the event took place
	 * @param y
	 *            y location on screen of where the event took place
	 */
	public void screenReleased(float x, float y) {

		gameStates.peek().screenReleased(x, y);

	}

	private GameState getState(int gameState) {

		switch (gameState) {

		case MENU:
			return new Menu(this);

		case TESTING:
			return new TestingGround(this);

		default:
			return null;
		}
	}

	/**
	 * Replaces the current GameState with the one specified
	 * 
	 * @param gameState
	 *            gamestate "id" (see fields)
	 */
	public void setState(int gameState) {

		popState();
		pushState(gameState);

	}

	/**
	 * Stacks a new GameState on top of the current GameState, making the new
	 * GameState the current state. (use for back caching)
	 * 
	 * @param gameState
	 *            gamestate "id" (see fields)
	 */
	public void pushState(int gameState) {

		gameStates.push(getState(gameState));
	}

	/**
	 * Remove current GameState
	 */
	public void popState() {

		GameState g = gameStates.pop();
		g.dispose();
	}

	/**
	 * @return the gameview
	 */
	public GameView getGameView() {
		return view;
	}

	/**
	 * 
	 * @return the screen's width
	 */
	public int getViewWidth() {
		return view.getWidth();
	}

	/**
	 * 
	 * @return the screen's height
	 */
	public int getViewHeight() {

		return view.getHeight();
	}

	/**
	 * Clears all states from memory
	 */
	public void dispose() {

		GameState state;

		try {

			while ((state = gameStates.pop()) != null) {
				state.dispose();
			}

		} catch (Exception ex) {

		}

	}

}
