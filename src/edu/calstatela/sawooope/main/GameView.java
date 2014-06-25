package edu.calstatela.sawooope.main;

import java.io.IOException;

import edu.calstatela.sawooope.entity.Rectangle;
import edu.calstatela.sawooope.gamestates.GameStateManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.MotionEvent;
import android.view.SurfaceView;


public class GameView extends SurfaceView {
		
	private SurfaceHolder holder;
	private GameStateManager gsm;
	private GameLoopThread gameLoopThread;
	private static float imgScale = 1.0f;
	
	public GameView(Context context, float scale) {
		super(context);
		imgScale = scale;
		gameLoopThread = new GameLoopThread(this);
		gsm = new GameStateManager(this);
		holder = getHolder();
		holder.addCallback(new Callback() {

			public void surfaceDestroyed(SurfaceHolder holder) {

			}

			@SuppressLint("WrongCall")
			public void surfaceCreated(SurfaceHolder holder) {

				/*
				 * Canvas c = holder.lockCanvas(); onDraw(c);
				 * holder.unlockCanvasAndPost(c);
				 */
				//gameLoopThread.setRunning(true);
				//gameLoopThread.start();

			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {

			}

		});
		
		
	}
	
	
	@SuppressLint("WrongCall")
	protected void onDraw(Canvas canvas) {
		
		gsm.update();
		gsm.draw(canvas);
	}
	

	public static float getBitmapScale() {
		
		return imgScale;
	}
		
	public boolean onTouchEvent(MotionEvent event){
		
		switch(event.getAction()){
			
		case MotionEvent.ACTION_DOWN:
			
			gsm.screenPressed(event.getX(),event.getY());
			
			break;
		
		case MotionEvent.ACTION_UP:
			
			gsm.screenReleased(event.getX(),event.getY());
			
			break;
		
		case MotionEvent.ACTION_MOVE:
			
			gsm.screenDragged(event.getX(), event.getY());
			
			break;
		
		}
		
		return true;
		
	}
	
	
	public Bitmap getScaledBitmap(String location){
		
		AssetManager asset = this.getResources().getAssets();
		
		try {
			Bitmap scaledImg = BitmapFactory.decodeStream(asset.open(location));
			
			return Bitmap.createScaledBitmap(scaledImg, (int)(scaledImg.getWidth()*imgScale), (int)(scaledImg.getHeight()*imgScale), false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}




	public static void drawScaledHeightRect(Canvas g, int x, int y, int width,
			int height,Paint paint) {
		
		g.drawRect(Rectangle.getDrawableRect(x,y,(int)(width),(int)(height*imgScale)), paint);
		
		
	}
	
	public void dispose(){
		gameLoopThread.setRunning(false);
		holder = null;
		gsm.dispose();		
	}
	
	public void start(){
		
		gameLoopThread.setRunning(true);
		gameLoopThread.start();

	}
	
	

}
