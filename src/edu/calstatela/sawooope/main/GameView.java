package edu.calstatela.sawooope.main;

import java.io.IOException;

import edu.calstatela.sawooope.entity.Rectangle;
import edu.calstatela.sawooope.gamestates.GameStateManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * GameView controls what will be drawn on the screen. It has:
 * <ul>
 * <li>GameLoopThread which sets the frame rate (how quickly things on the
 * screen are updated and drawn) of the game.</li>
 * <li>GameStateManager which controls which game screens to draw</li>
 * <li>drawing scale which ensures that all images are draw to scale based on
 * the users screen density</li>
 * </ul>
 * 
 * @author Benji
 * 
 */

public class GameView extends SurfaceView {

	private SurfaceHolder holder;
	private GameStateManager gsm;
	private GameLoopThread gameLoopThread;

	// this is the drawing scale
	private static float imgScale = 1.0f;

	/**
	 * 
	 * @param context
	 *            context (see android api... but not that important!)
	 * @param scale
	 *            drawing scale
	 */
	public GameView(Context context, float scale) {
		super(context);
		imgScale = scale;
		gameLoopThread = new GameLoopThread(this);
		gsm = new GameStateManager(this);
		holder = getHolder();
		holder.addCallback(new Callback() {

			public void surfaceDestroyed(SurfaceHolder holder) {

			}

			public void surfaceCreated(SurfaceHolder holder) {

			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}

		});

	}

	/**
	 * Updates and draws the current GameState
	 */
	protected void onDraw(Canvas canvas) {

		gsm.update();
		gsm.draw(canvas);
	}

	/**
	 * 
	 * @return the drawing scale
	 */
	public static float getBitmapScale() {

		return imgScale;
	}

	/**
	 * Passes any motion events to the GameStateManager to be handeled.
	 */
	public boolean onTouchEvent(MotionEvent event) {

		/*Log.i("Motion","RawX: "+event.getRawX()+" RawY: "+event.getRawY());
		Log.i("Motion","X: "+event.getX()+" Y: "+event.getY());*/
		
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:

			gsm.screenPressed(event.getX(), event.getY());

			break;

		case MotionEvent.ACTION_UP:

			gsm.screenReleased(event.getX(), event.getY());

			break;

		case MotionEvent.ACTION_MOVE:

			gsm.screenDragged(event.getX(), event.getY());

			break;

		}

		return true;

	}

	/**
	 * 
	 * @param location
	 *            resource URI in the assets folder
	 * @return the scaled bitmap (image) from the assets folder specified by the
	 *         location
	 */
	public Bitmap getScaledBitmap(String location) {

		AssetManager asset = this.getResources().getAssets();

		try {
			Bitmap scaledImg = BitmapFactory.decodeStream(asset.open(location));

			return Bitmap.createScaledBitmap(scaledImg,
					(int) (scaledImg.getWidth() * imgScale),
					(int) (scaledImg.getHeight() * imgScale), false);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Draws a scaled rectangle
	 * 
	 * @param g
	 *            canvas being drawn on
	 * @param x
	 *            x component of the rectangle's top left corner
	 * @param y
	 *            y component of the rectangle's top left corner
	 * @param width
	 *            rectangle's width
	 * @param height
	 *            rectangle's height
	 * @param paint
	 *            paint used to color the rectnagle(can be null);
	 */
	public static void drawScaledHeightRect(Canvas g, int x, int y, int width,
			int height, Paint paint) {

		g.drawRect(Rectangle.getRect(x, y, (int) (width),
				(int) (height * imgScale)), paint);

	}

	/**
	 * Ends game look and frees memory by clearing all objects from memory
	 */
	public void dispose() {
		gameLoopThread.setRunning(false);
		holder = null;
		gsm.dispose();
	}

	/**
	 * Starts the GameLoopTheard
	 */
	public void start() {

		gameLoopThread.setRunning(true);
		gameLoopThread.start();

	}

}
