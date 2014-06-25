package edu.calstatela.sawooope.gamestates;

import java.util.Stack;

import edu.calstatela.sawooope.gamestates.levels.TestingGround;
import edu.calstatela.sawooope.main.GameView;

import android.graphics.Canvas;


public class GameStateManager {
	

	private Stack<GameState> gameStates;
	private GameView view;
	public static final int MENU = 0;
	public static final int TESTING = 1;
		
	public GameStateManager(GameView view){
	
		this.view = view;
		GameState.SCALE = GameView.getBitmapScale();
		gameStates = new Stack<GameState>();
		pushState(MENU);
		
	}
	
	public void update(){
		
		try
		{
			gameStates.peek().update();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			
		}
		
	}
	public void draw(Canvas g){
		
		try
		{
			gameStates.peek().draw(g);
		}
		catch(Exception e){
			
			e.printStackTrace();			
			
				
		}
		
		}
	
	
	public void screenDragged(float x, float y){
		
		gameStates.peek().screenDragged(x,y);
		
	}
	public void screenPressed(float x, float y) {
		
		gameStates.peek().screenPressed(x,y);
	}
		
	public void screenReleased(float x, float y){
		
		gameStates.peek().screenReleased(x,y);
		
	}
	
	private GameState getState(int gameState){
		
		switch(gameState){
		
		case MENU: return new Menu(this);
		
		case TESTING: return new TestingGround(this);
			
			default: return null;
		}
	}
	
	public void setState(int gameState){
		
		popState();
		pushState(gameState);
		
	}
	public void pushState(int gameState){
		
		gameStates.push(getState(gameState));
	}
	public void popState(){
		
		GameState g = gameStates.pop();
		g.dispose();
	}
	 
	public GameView getGameView(){
		return view;
	}
	
	public int getWidth(){
		return view.getWidth();
	}

	public int getHeight() {
		
		return view.getHeight();
	}
	
	public float getDrawableImageScale(){
		
		return view.getBitmapScale();
	}
	
	public void dispose(){
		
		GameState state;
		
		try{
			
		while((state = gameStates.pop()) != null){
				state.dispose();
			}
			
			
		}catch(Exception ex){
			
		}
		
	}
	
}
