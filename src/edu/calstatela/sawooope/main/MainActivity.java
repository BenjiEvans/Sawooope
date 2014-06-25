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

public class MainActivity extends Activity {
	
	GameView view;
	MediaPlayer mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		DisplayMetrics metrics = new DisplayMetrics();    
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int screenDensity = metrics.densityDpi;
	//	float scale = 1.0f*screenDensity/120;
		float scale = 1.0f*screenDensity/160;
		Log.i("screen","Screen density:"+screenDensity);
		Log.i("screen","Scale:"+scale);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		view = new GameView(this,scale);
		setContentView(view);
		view.start();
		mp = MediaPlayer.create(MainActivity.this, R.raw.bg_music);
		mp.setLooping(true);
		mp.start();
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	
	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first

	    // Release the Camera because we don't need it when paused
	    // and other activities might need to use it.
	    mp.stop();
	    if (view != null) {
	       view.dispose();
	    }
	}
	
	@Override
	protected void onStop() {
	    super.onStop();  // Always call the superclass method first

	    // Save the note's current draft, because the activity is stopping
	    // and we want to be sure the current note progress isn't lost.
	    mp.stop();
	    if (view != null) {
		       view.dispose();
		 }
	}
	
	

}
