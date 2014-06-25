package edu.calstatela.sawooope.main;

import android.annotation.SuppressLint;
import android.graphics.Canvas;

public class GameLoopThread extends Thread {
	
	private GameView view;
	private boolean running = false;
	static final long FPS = 60;
	
	public GameLoopThread(GameView view){
		this.view = view;
	}
	
	public void setRunning(boolean run){
		
		running = run;
	}
	
	@SuppressLint("WrongCall")
	public void run(){
		
		long ticksPerSec = 700/FPS;
		long startTime;
		long sleepTime;
		
		while(running){
			
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try{
				
				c = view.getHolder().lockCanvas();
				synchronized(view.getHolder()){
					view.onDraw(c);
				}
			}finally {
				
				if(c != null) {
					
					view.getHolder().unlockCanvasAndPost(c);
				}
				
			}
			
			sleepTime = ticksPerSec - (System.currentTimeMillis() - startTime);
			
			
			try{
				
				if(sleepTime > 0) sleep(sleepTime);
				else sleep(10);				
				
			}catch(Exception ex){}
			
			
			
		}
		
		
	}	
}
