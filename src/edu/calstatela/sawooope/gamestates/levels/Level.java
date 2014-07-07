package edu.calstatela.sawooope.gamestates.levels;

import java.util.ArrayList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import edu.calstatela.sawooope.entity.Barricade;
import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.Rectangle;
import edu.calstatela.sawooope.entity.creature.Sheep;
import edu.calstatela.sawooope.entity.creature.Wolf;
import edu.calstatela.sawooope.gamestates.GameState;
import edu.calstatela.sawooope.gamestates.GameStateManager;
import edu.calstatela.sawooope.tilemap.TileMap;

/**
 * Level is a GameState that deals with game entities and actually game logic,
 * making it it's own class. Every Level should have a GameMode (not yet implemented)
 * which will constantly check whether the level is completed and should end.
 * Every level has:
 * <ul>
 * 	<li> Tilemap</li>
 * 	<li> BoardEntity Manager which is in charge of updating and drawing the 
 * entities on the board. </li>
 * 	<li> LevelInputProcessor
 * which processes touch events to detect gestures (such as swipe and tap)</li>
 * </ul>
 *   
 * @author Benji
 */
public abstract class Level extends GameState implements EntityMapping{
	
	BoardEntityManager entityManager;	
	public int gridSize = 32;//this is the size of each square position
	/**
	 * @property 
	 */
	private static final int GRID_SIZE = 32;
	boolean end;
	TileMap tileMap;
	LevelInputProcessor input;
	
	Level(GameStateManager gsm){
		super(gsm);	
		gridSize= (int) (GRID_SIZE*SCALE);
		input = new LevelInputProcessor(gridSize);
	}
	
	/**
	 * @return the grid size scaled for the screen
	 */
	public static int getGridSize() {
		return (int) (GRID_SIZE*SCALE);
	}
	
	
	protected void initialize(){
		
		tileMap = new TileMap();
		BoardObject.setLevel(this);
		entityManager = new BoardEntityManager();
	}
	
	
	public void update() {
		
		if(ready){
			
			entityManager.updateCreatures();
			
			if(isNoMoreSheep())endLevel();
			return;
			
		}
		
	}
	
	public void draw(Canvas g) {
		
			
		if(ready){
			
			tileMap.draw(g);
			BoardObject.setMapPosition();
			
			//draw Grid
			Paint paint = new Paint();
			paint.setARGB(10*2, 0, 0, 0);
			paint.setStyle(Paint.Style.STROKE);
			
			int colsToDraw = tileMap.getNumOfColsToDraw();
			int rowsToDraw = tileMap.getNumOfRowsToDraw();
			int colSet = tileMap.getColumOffSet();
			int rowSet = tileMap.getRowOffSet();
						
			for(int col = colSet; col < colSet+colsToDraw; col++)
			{
				for(int row = rowSet; row < rowSet+rowsToDraw; row++)
				{
					g.drawRect(Rectangle.getRect((int)(col*gridSize+TileMap.getMapx()), (int)(row*gridSize+TileMap.getMapy()), gridSize, gridSize),paint);
				}
			}
			
			//draw entitties 
			entityManager.drawEntities(g);
			return;
			
		}
		
		
	
}
	private void endLevel(){ end = true;}
	
	private boolean isNoMoreSheep() {
		
		ArrayList<Sheep> herd = entityManager.getHerd();
				
		if(herd.size() == 0)return true;
		
		for(int i = 0, size = herd.size(); i < size; i++ ){
			
			Sheep sheep = herd.get(i);
			
			if(!sheep.isDead())return false;
		}
		
		return true;
	}
	
	/**
	 * Checks to see if the position specified is 
	 * occupied(by a blocking tile or BoardObject)to avoid collision
	 * @param col column being checked
	 * @param row row being checked
	 * @return true if that space is occupied
	 */
	public boolean isPositionBlocked(int col,int row){
		
		//more checking needs to be done here
		
		if(tileMap.isTileBlocking(col,row))return true;
	
		
		return false;
	}
	
	/**
	 * This method will likely no longer 
	 * be used... Ignore for now
	 * @deprecated
	 * @param wolf wolf that is eating
	 */
	public void eatSheep(Wolf wolf){
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		if(herd.isEmpty())return;
		
		for(int i = 0, size = herd.size(); i < size; i++)
		{
			Sheep sheep = (Sheep) herd.get(i);
			
			if(wolf.getCollisionBox().overLap(sheep.getCollisionBox(),.5))
			{
				
				herd.remove(i);
				i--;
				size--;
			}
		}
		
		
	}
	/**
	 * This method will likely no longer 
	 * be used... Ignore for now
	 * @deprecated
	 * @param sheep sheep to save
	 */
	public void saveSheep(Sheep sheep){
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		for(int i = 0, size = herd.size(); i < size; i++ ){
			
			if(herd.get(i) == sheep ){
				
				herd.remove(i);
				return;
			}
		}
			
		
		
	}
	
	/**
	 * Checks whether or not there are any live sheep 
	 * @return true if all sheep are dead or no sheep in the herd
	 */
	public boolean isHerdDead(){
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		if(herd.size() == 0) return true;
		
		for(int i = 0, size = herd.size(); i < size; i++){
			
			Sheep sheep = (Sheep)herd.get(i);
			
			if(!sheep.isDead())return false;
		}
		
		
		return true;
	}
			
	@Override
	public void screenPressed(float x, float y) {
		
		input.screenPressed(x,y);
		tileMap.setScroll(x, y);
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		//check to see if a sheep was pressed 
		for(Sheep sheep: herd){
			
			input.hasPressed(sheep);
			
		}
		
		//check to see if a barriar was pressed 
		ArrayList <BoardObject> rocks = entityManager.getMapObject(BoardEntityManager.BARRICADE);		
		for(BoardObject b : rocks){
			
			input.hasPressed(b);
						
		}
		
		
		
	}

	@Override
	public void screenReleased(float x, float y) {
		Log.i("Testing","Released");
		input.screenReleased(x,y);
		int gesture = input.getGestureCode();
		
		switch(gesture){
			
		case Touchable.SWIPE:
			
			BoardObject obj= input.getPressedObject();
			if(obj != null){
				
				if(obj.isOfType(BoardObject.SHEEP)){
					
					Sheep sheep = (Sheep) obj;
					Log.i("Testing","Swipeing sheep");
					sheep.move(input.getSwipeDirection());
					
					return;
				}
				
			}
		
		break;
		
		case Touchable.TAP:
			
			BoardObject obj2 = input.getPressedObject();
			if(obj2 != null){
				
				if(obj2.isOfType(BoardObject.BARRICADE)){
					
					Barricade b = (Barricade) obj2;
					if(b.isPressed(input.getTapLocationOnMap()))b.hit();
					
					return;
				}
				
				if(obj2.isOfType(BoardObject.SHEEP)){
					
					Log.i("Testing","Taping sheep");
					Sheep sheep = (Sheep) obj2;
					sheep.stop();
					
					return;
				}
			}			
		
			break;
			
		case Touchable.DRAG://this will also be used to move the sheep 
			
			Log.i("Testing","Dragged ");
			break;
		
		}
		
		
		
		
		
		
		
	}
	
	
	@Override
	public void screenDragged(float x,float y) {
		
		input.screenDragged(x,y);
		
		if(input.isScreenScrollable()) tileMap.scroll(x,y);
			
	}
	
	/**
	 * 
	 * @return the screen's width
	 */
	public int getWidth() {
		
		return gsm.getViewWidth();
	}

	/**
	 * 
	 * @return the screen's height
	 */
	public int getHeight() {
		
		return gsm.getViewHeight();
	}
	
	/**
	 * 
	 * @return a reference to the resources in the assets folder
	 */
	public Resources getResources(){
		
		return gsm.getGameView().getResources();
	}
	
	/**
	 * Get the value of the scale used 
	 * to draw the scaled images
	 * @return scale drawing scale 
	 */
	public float getDrawableScale() {
		
		return SCALE;
	}
	
	protected void dispose(){
		
		tileMap.dispose();
	}

	

}
