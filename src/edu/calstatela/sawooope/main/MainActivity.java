package edu.calstatela.sawooope.main;

import edu.calstatela.sawooope.R;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * MainActivity is the starting point of the Application. It sets up the screen
 * view based on Gameview.
 * 
 */

public class MainActivity extends Activity {

	/**
	 * @property
	 */
	GameView view;

	/**
	 * @property
	 * 
	 */
	MediaPlayer mp;

	/**
	 * Calculates the device's screen density so the GameView can draw all
	 * images to scale in order to support screens with different pixel
	 * desity(VERY important!); and sets the screen's view to that of the
	 * GameView. This method also start the background music of the application
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screenDensity = metrics.densityDpi;

		float scale = 1.0f * screenDensity / 160;

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//just added.... May need to comment out
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		view = new GameView(this, scale);
		setContentView(view);
		view.start();

		mp = MediaPlayer.create(MainActivity.this, R.raw.bg_music);
		mp.setLooping(true);
		mp.start();
	}

	/**
	 * Stops the background musics and ends the game (no pause state yet
	 * implemented)
	 */
	@Override
	public void onPause() {
		super.onPause(); // Always call the superclass method first

		// Release the Camera because we don't need it when paused
		// and other activities might need to use it.
		mp.stop();
		if (view != null) {
			view.dispose();
		}
	}

	/**
	 * See onPause (does the same thing..)
	 */
	@Override
	protected void onStop() {
		super.onStop(); // Always call the superclass method first

		// Save the note's current draft, because the activity is stopping
		// and we want to be sure the current note progress isn't lost.
		mp.stop();
		if (view != null) {
			view.dispose();
		}
	}

}
