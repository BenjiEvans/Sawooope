package edu.calstatela.sawooope.gamestates.levels;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.Rectangle;
import edu.calstatela.sawooope.entity.creature.Destination;
import edu.calstatela.sawooope.entity.creature.Sheep;
import edu.calstatela.sawooope.tilemap.TileMap;

public class LevelObserver {
	
	public static final int SWIPE = 0;
	public static final int TAP = 1;
	public static final int DRAG = 2;
	
	private float mousex;
	private float mousey;
	
	private float pressedx; 
	private float pressedy;
	
	private float releasedx;
	private float releasedy;
	
	private int pressedCol;
	private int pressedRow;
	
	private int releasedRow;
	private int releasedCol;
	
	private long pressTimer;
	private long elapse;
	private TileMap tilemap;
	private boolean skipScroll;
	private boolean pressed;
	private boolean dragged;
	private boolean draggedSheep;
	private boolean drawCursorPosition;
	private int pressTime = 500;
	private Level level;
	private boolean clickedSheepDestiny;
	
	Sheep selectedSheep;
	Sheep destinySheep;
	BoardObject pressedObject;
	private ArrayList<Destination> destinationQueue = new ArrayList<Destination>();
	
	
	
	LevelObserver(TileMap tileMap, Level level){
		
		tilemap = tileMap;
		this.level = level;
		
	}
	
	public void screenPressed(float x, float y){
		updateMousePosition(x,y);
		
		pressedx = x;
		pressedy = y;
		
		pressedCol = getCol(x);
		pressedRow = getRow(y);
		skipScroll = false;
		pressed = true;
		draggedSheep = false;
		dragged = false;
		tilemap.setScroll(x, y);
		pressedObject = null;
		
		if(selectedSheep != null && !selectedSheep.isSelected())selectedSheep = null;
		
		pressTimer = System.nanoTime();
		
	
		
	}
	
	public void screenReleased(float x, float y){
		updateMousePosition(x,y);
		pressed = false;
		dragged = false;
		if(clickedSheepDestiny){
			destinySheep = null;
			clickedSheepDestiny = false;
		}
		if(drawCursorPosition)draggedSheep = true;
		drawCursorPosition = false;
		releasedx = x;
		releasedy = y;
		
		releasedCol = getCol(x);
		releasedRow = getRow(y);
		
		elapse = (System.nanoTime() - pressTimer)/1000000;
				
	}	

	private void updateMousePosition(float x, float y) {
		
		mousex = x;
		mousey = y;
		
	}
	
	public void skipScroll(){
		skipScroll = true;
	}

	public void setPressedObject(BoardObject object) {
		
		pressedObject = object;
		
		if(object.getID() == BoardObject.SHEEP){
			
			if(selectedSheep!= null)
			{
				selectedSheep.setSelected(false);
			}
			
			selectedSheep = (Sheep) pressedObject;
			//selectedSheep.setSelected(true);
		}
		
		
	}

	public boolean hasSelectedSheep() {
				
		return selectedSheep != null && selectedSheep.isSelected();
	}

	public boolean hasPressedObject() {
		// TODO Auto-generated method stub
		return pressedObject != null;
	}

	public BoardObject getPressedObject() {
		
		return pressedObject;
	}
	
	public float getPressedx(){return pressedx;}
	
	public float getPressedy(){return pressedy;}
	
	public float getMousey(){return mousey;}
	public float getMousex(){return mousex;}
	
	public void scroll(float x, float y){
		
		if(skipScroll)return;
		
		tilemap.scroll(x,y);
		
		
	}

	public int getGesture() {
		
		if(elapse <= pressTime){
			
			
			if(pressedCol == releasedCol && pressedRow == releasedRow && !dragged){
				
				//System.out.println("Should return TAP!!!");
				
				
				return TAP;
			}
			else return SWIPE;
			
			
			
			
			
		}else /*if(dragged)*/return DRAG;
		
		
		
		//null gesture 
		//return -1;
	}

	public Sheep getSelectedSheep() {
		
		return selectedSheep;
	}
	
	public void draw(Canvas g){
		
		if(!pressed || pressedObject == null || pressedObject.getID() != BoardObject.SHEEP)return;
		long time = (System.nanoTime() - pressTimer)/1000000;
		
		/*if(time > pressTime){
			
			int col = getCol(mousex);
			int row = getRow(mousey);
			int size = Level.GRID_SIZE;
			g.setColor(new Color(150,255,150,100));
			g.fillRect(col*size+tilemap.getMapx(), row*size+tilemap.getMapy(),size,size);
			
		}*/
		

		
					
		if(time > pressTime){
			
			int col = getCol(mousex);
			int row = getRow(mousey);
			
			if(!drawCursorPosition){
				
				if(col != pressedCol || row != pressedRow)return;
				
				drawCursorPosition = true;
				
			}
						
					
		int size = Level.getGridSize();
		Paint paint = new Paint();
		paint.setARGB(100, 150, 255, 150);
		g.drawRect(Rectangle.getDrawableRect(col*size+TileMap.getMapx(), row*size+TileMap.getMapy(),size,size), paint);
		//g.setColor(new Color(150,255,150,100));
		//g.fillRect(col*size+tilemap.getMapx(), row*size+tilemap.getMapy(),size,size);
		
			drawDestinationQueue(g,paint);
		
		}
			
		
	}
	
	private void drawDestinationQueue(Canvas g,Paint paint){
			
		if(destinationQueue.isEmpty()) return;
		
		int size = Level.getGridSize();
		for(Destination d: destinationQueue){
			
			int col = d.getCol();
			int row = d.getRow();
			
			g.drawRect(Rectangle.getDrawableRect(col*size+TileMap.getMapx(),row*size+TileMap.getMapy(),size,size),paint);
			
			
		}
		
		
		
	}	
	
	private int getCol(float x){
		
		return (int)((x-TileMap.getMapx())/Level.getGridSize());
	}
	
	private int getRow(float y){
		
		return (int)((y-TileMap.getMapy())/Level.getGridSize());
	}

	public void screenDragged(float x, float y) {
		updateMousePosition(x,y);
		dragged = true;
		if(clickedSheepDestiny){
			
			int col = getCol(x);
			int row = getRow(y);
			
			boolean inRange = Destination.withinOneUnit(destinySheep.getFinalDestination(),col,row);
			
			if( inRange && level.isValidPlacement(getCol(x),getRow(y))){
				
				destinySheep.addToQueue(col, row);
				
			}
			
		return;
		}
		
		long time = (System.nanoTime() - pressTimer)/1000000;
		if(!(time > pressTime))return;
		if(!drawCursorPosition)return;
		if(pressedObject == null)return;
		if(pressedObject.getID() != BoardObject.SHEEP)return;
		Sheep sheep = (Sheep) pressedObject;
		if(!sheep.isSelected()) sheep.setSelected(true);
		Destination newDest = new Destination(getCol(x),getRow(y));
		if(canAddToDestinationQueue(newDest))
		{
			if(destinationQueue.isEmpty() && newDest.equals(pressedCol, pressedRow) && sheep.hasColRow(pressedCol,pressedRow) ) return;
			if(tilemap.isTileBlocked(newDest.getCol(), newDest.getRow()))return;
			destinationQueue.add(newDest);		
		}
	}

	private boolean canAddToDestinationQueue(Destination newDest) {
		
		if(destinationQueue.isEmpty())return true;
		
		int lastIndex = destinationQueue.size()-1;
		Destination des = destinationQueue.get(lastIndex);
		
		if(des.equals(newDest))return false;
		if(lastIndex+1 > 1){
			
			if(destinationQueue.get(lastIndex-1).equals(newDest))
			{
				destinationQueue.remove(lastIndex);
				return false;
			}
		}
		
		
			
		
		
		if(des.getCol() == newDest.getCol()){
			
			int desRow = des.getRow();
			int newRow = newDest.getRow();
			
			if(desRow +1 == newRow || desRow-1 == newRow)return true;
			
			
		}
		
		if(des.getRow() == newDest.getRow()){
			
			int desCol = des.getCol();
			int newCol = newDest.getCol();
			
			if(desCol +1 == newCol || desCol-1 == newCol)return true;
			
			
		}
		
		
		return false;
	}

	public ArrayList<Destination> dumpDestintionQueue() {
		
		ArrayList<Destination> dest = new ArrayList<Destination>();
		
		for(int i = 0,length = destinationQueue.size();i < length;length--)
		{
			dest.add(destinationQueue.get(i));
			destinationQueue.remove(i);
		}
		
		System.out.println("Reset Size: "+destinationQueue.size());
		
		
		return dest;
	}

	public boolean sheepWasDragged() {
		
		return draggedSheep && !destinationQueue.isEmpty();
	}

	public int getPressedCol() {
		
		return pressedCol;
	}
	
	public int getPressedRow(){
		
		return pressedRow;
	}

	public void unselectSheep() {
		
		selectedSheep.setSelected(false);
		selectedSheep = null;
		
		
	}

	public void clickedSheepDestination(Sheep sheep) {
		
		clickedSheepDestiny = true;
		skipScroll = true;
		destinySheep = sheep;
		
	}

}
