package edu.calstatela.sawooope.gamestates;

import edu.calstatela.sawooope.main.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

/**
 * No Much Interesting content here...
 * 
 * @author Benji
 * 
 */
public class Menu extends GameState {

	private Bitmap background;
	private Button title;
	private Button[] buttons;
	private final int START = 0;
	private final int HELP = 1;
	private final int QUIT = 2;
	private float pressedx;
	private float pressedy;

	/**
	 * 
	 * @param gsm
	 */
	public Menu(GameStateManager gsm) {
		super(gsm);
	}

	/**
	 * Updates screen
	 */
	public void update() {

		if (ready) {

		}

	}

	/**
	 * Draws updated screen
	 */
	public void draw(Canvas g) {

		if (ready) {
			g.drawBitmap(background, 0, 0, null);
			drawTitle(g);
			drawOptions(g);
		}

	}

	private void drawTitle(Canvas g) {
		// TODO

	}

	private void drawOptions(Canvas g) {

		for (Button b : buttons) {

			b.draw(g);

		}

	}

	@Override
	public void screenDragged(float x, float y) {

	}

	@Override
	public void screenPressed(float x, float y) {

		pressedx = x;
		pressedy = y;
	}

	@Override
	public void screenReleased(float x, float y) {

		if (locked)
			return;

		for (Button t : buttons) {
			if (t.isClicked(pressedx, pressedy)) {
				String name = t.getDescriptor();

				if (name.equalsIgnoreCase("start")) {

					Log.i("Debug", "StartGame!!");
					gsm.pushState(GameStateManager.TESTING);
					locked = true;

				} else if (name.equalsIgnoreCase("help")) {

				} else if (name.equalsIgnoreCase("quit")) {

					System.exit(0);
				}
				// t.setColor(Color.BLACK);
				break;
			}
		}

	}

	@Override
	protected void initialize() {

		GameView view = gsm.getGameView();
		int viewHeight = view.getHeight();
		int viewWidth = view.getWidth();
		Log.i("DrawDebug", "ViewHeight: " + viewHeight + " ViewWidth: "
				+ viewWidth);

		try {
			background = view.getScaledBitmap("bgs/menu.png");
			Bitmap titleImg = view.getScaledBitmap("buttons/sawooope.png");
			int pos = getCenteredX(titleImg.getWidth(), viewWidth);
			int padding = (int) (10 * SCALE);
			title = new Button(pos, padding, titleImg, "title");

			buttons = new Button[3];

			int y = (int) (viewHeight * 0.75);

			for (int i = 0, length = buttons.length; i < length; i++) {

				if (i != 0)
					y = buttons[i - 1].getMaxY() + padding;

				switch (i) {
				case START:
					Bitmap img = view.getScaledBitmap("buttons/start.png");
					int x = getCenteredX(img.getWidth(), viewWidth);
					buttons[i] = new Button(x, y, img, "start");
					break;
				case QUIT:
					Bitmap img2 = view.getScaledBitmap("buttons/quit.png");
					int x2 = getCenteredX(img2.getWidth(), viewWidth);
					buttons[i] = new Button(x2, y, img2, "quit");
					break;

				case HELP:
					Bitmap img3 = view.getScaledBitmap("buttons/help.png");
					int x3 = getCenteredX(img3.getWidth(), viewWidth);
					buttons[i] = new Button(x3, y, img3, "help");
					break;

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void dispose() {

		background = null;
		title = null;
		buttons = null;
	}

	private int getCenteredX(int objectWidth, int screenWidth) {

		return (screenWidth / 2) - (objectWidth / 2);
	}

}
