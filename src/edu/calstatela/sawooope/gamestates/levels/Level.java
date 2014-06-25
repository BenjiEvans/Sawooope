package edu.calstatela.sawooope.gamestates.levels;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import edu.calstatela.sawooope.entity.Barricade;
import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.Rectangle;
import edu.calstatela.sawooope.entity.creature.Sheep;
import edu.calstatela.sawooope.entity.creature.SheepDecoy;
import edu.calstatela.sawooope.entity.creature.Wolf;
import edu.calstatela.sawooope.entity.plant.GrassPatch;
import edu.calstatela.sawooope.gamestates.GameState;
import edu.calstatela.sawooope.gamestates.GameStateManager;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.tilemap.TileMap;
import edu.calstatela.sawooope.ui.UserBar;


public abstract class Level extends GameState implements BoardSettings, Swipeable, EntityMapping{
	
	BoardEntityManager entityManager;	
	LevelObserver observer;
	public int gridSize = 32;
	private static final int GRID_SIZE = 32;
	//private static float SCALE = 1f;
	boolean end;
	TileMap tileMap;
	UserBar ui;
	
	Level(GameStateManager gsm){
		super(gsm);	
		//SCALE = gsm.getDrawableImageScale();
		gridSize= (int) (GRID_SIZE*SCALE);
	}
	
	public static int getGridSize() {
		
		Log.i("DrawDebug","Scale: "+SCALE);
		return (int) (GRID_SIZE*SCALE);
	}
	
	protected void initialize(){
		
		tileMap = new TileMap();
		observer = new LevelObserver(tileMap,this);
		ui = new UserBar(this);
		entityManager = new BoardEntityManager();
		entityManager = new BoardEntityManager();
		
	}
	
	
	public void update() {
		
		if(ready){
			
			entityManager.updateEntities();
			
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
			//Log.i("DrawDebug","GridSize:"+gridSize);
			
			for(int col = colSet; col < colSet+colsToDraw; col++)
			{
				for(int row = rowSet; row < rowSet+rowsToDraw; row++)
				{
					g.drawRect(Rectangle.getDrawableRect(col*gridSize+TileMap.getMapx(), row*gridSize+TileMap.getMapy(), gridSize, gridSize),paint);
				}
			}
			
			//draw entitties 
			entityManager.drawEntities(g);
			observer.draw(g);
			drawUI(g);
			if(end)drawEndScreen(g);
			
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

	
	public boolean tileIsBlocked(int col,int row){
		
		
			if(tileMap.isTileBlocked(col,row))return true;
	
		
		//if(tileMap.isTileBlocked(col,row))return true;
		
		if(isMapObjectBlocking(col,row))return true;
		
		
		return false;
	}
	
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

	private boolean isMapObjectBlocking(int col, int row) {
		
		//if(mapObjects == null)return false;
		HashMap<String, ArrayList<BoardObject>> mapObjects = entityManager.getMapObjects();
		String[] mapHash = entityManager.getMapObjectsHash();
		
		for(int j = 0, length = mapHash.length; j < length; j++)
		{
			ArrayList<BoardObject> list = mapObjects.get(mapHash[j]);
			
			for(int i = 0, size = list.size(); i < size; i++)
			{
				BoardObject obj = list.get(i); 
				if(obj.isDoneUpdating())
				{
					mapObjects.remove(i);
					i--;
					size--;
					continue;			
				}	
				
				if(obj.hasColRow(col,row) && obj.isBlockable() /*&& obj.getID() == BoardObject.BARRICADE*/){
					
				return true;
				}
			}
			
			
		}
		
		return false;
	}

	public int getCol(float x){
		
		return (int)((x-TileMap.getMapx())/gridSize);
	}
	
	public boolean portalHasColRow(int col, int row){
		
		ArrayList<BoardObject> list = entityManager.getMapObjects().get(TUNNEL);
		
		for(BoardObject obj: list){
			
			if(obj.hasColRow(col, row))return true;
		}
		
		
		return false;
		
	}
		
	public void saveSheep(Sheep sheep){
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		for(int i = 0, size = herd.size(); i < size; i++ ){
			
			if(herd.get(i) == sheep ){
				
				herd.remove(i);
				return;
			}
		}
			
		
		
	}
	
	public int getRow(float y){
		
		return (int)((y-TileMap.getMapy())/gridSize);
	}
	
	public boolean herdHasPosition(Sheep sheep, int col, int row){
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		for(int i = 0, size = herd.size(); i < size; i++)
		{
			Sheep sheepy = (Sheep)herd.get(i);
			if(sheep != sheepy &&(sheepy.hasColRow(col, row) || sheepy.hasNextColRow(col, row))) return true;
		}
		
		return false;
		
	}
	
	public /*synchronized*/ boolean isInHerd(Sheep sheepy){
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		for(int i = 0, size = herd.size(); i < size; i++){
			
			Sheep sheep = (Sheep)herd.get(i);
			
			if(sheep == sheepy) return true;
		}
		
		return false ;
	}
	
	public boolean isHerdDead(){
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		if(herd.size() == 0) return true;
		
		for(int i = 0, size = herd.size(); i < size; i++){
			
			Sheep sheep = (Sheep)herd.get(i);
			
			if(!sheep.isDead())return false;
		}
		
		
		return true;
	}
	
	public ArrayList<Sheep> getHerd(){
		
		ArrayList<Sheep> list = new ArrayList<Sheep>();
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		for(Sheep sheep: herd){
			
			list.add(sheep);
		}
		
		ArrayList<BoardObject> decoys = entityManager.getMapObjects().get(SHEEP_DECOY);
		
		for(int i = 0, size = decoys.size(); i < size ; i++)
		{
			BoardObject o = decoys.get(i);
			
			if(o.isDoneUpdating()){
				
				decoys.remove(i);
				i--;
				size--;
				continue;
			}
			
			if(o.getID() == BoardObject.SHEEP_DECOY){
				
				list.add((Sheep)o);
			}
		}
		
		return list;
		
	}
	
	public boolean packHasTarget(Sheep sheep, Wolf wolfy){
		
		if(sheep == null) return false; //this is weird though 
		
		ArrayList<Wolf> wolfPack = entityManager.getWolfPack();
		
		
		for(int i = 0, size = wolfPack.size(); i < size; i++){
			
			Wolf wolf = (Wolf)wolfPack.get(i);
			
			if(wolf != wolfy && wolf.hasTarget(sheep)) return true;
		}
		
		return false;
		
		
		
	}

	public boolean packHasPosition(ArrayList<int[]> pos) {
		
	//	if(wolfPack == null) return false; When is the method called???
			
		Log.i("SheepDecoy","Getting Wolves...");
		ArrayList<Wolf> wolfPack = entityManager.getWolfPack();
		
		for(int i = 0, size = wolfPack.size(); i < size; i++)
		{
			Log.i("SheepDecoy","size"+size);
			Wolf wolf = wolfPack.get(i);
			for(int j = 0, size2 = pos.size(); j < size2; j++){
				Log.i("SheepDecoy","sizes:"+size2);
				if(wolf.hasColRow(pos.get(j))||wolf.hasNextColRow(pos.get(j)))return true;
			}
			
			
		}
		
		
		
		return false;
	}

	public boolean decoyHasColRow(int col, int row) {
		
		ArrayList<BoardObject> list = entityManager.getMapObjects().get(SHEEP_DECOY);
		
		for(BoardObject o: list){
			
			if(o.hasColRow(col, row))return true;
			
		}
		
		return false;
	}

	public boolean packHasPosition(Wolf wolf, int col, int row) {
		
		ArrayList<Wolf> wolfPack = entityManager.getWolfPack();
		
		for(int i = 0, size = wolfPack.size() ; i < size; i++){
			
			Wolf w = wolfPack.get(i);
			
			if(wolf != w && w.hasColRowPosition(col, row))return true;
			
		}
		
		
		return false;
	}
	


	
	private void drawEndScreen(Canvas g) {
		
		//Log.i("Debug", "End the game!!!!!!!!");
		
		double scale = .5;
		int width = (int) (gsm.getWidth()*scale);
		int height = (int)((gsm.getHeight()*-UserBar.HEIGHT)*scale);
		int x = (gsm.getWidth()/2) - (width/2);
		int y = ((gsm.getHeight()-UserBar.HEIGHT/2)) - (height/2);
		
		Log.i("Debug","Width: "+width+"Height: "+height);
		//Log.i("Debug",);
		
		Paint paint = new Paint();
		paint.setARGB(255,0,0,0);
		paint.setStyle(Paint.Style.FILL);
		g.drawRect(Rectangle.getDrawableRect(x, y, width, height), paint);
		
		String[] message = {"Nice Work!","You managed to save: "+Sheep.getAmountSaved()+" Sheep","(Click any button to return to Menu)"};
		
		for(int i = 0; i < message.length; i++)
		{
			//if(i ==2) g.setFont(new Font("Century Gothic", Font.PLAIN,10));
			
			Rect rec = new Rect();
			
			//Log.i("Debug","Height: "+rec);
			paint.getTextBounds(message[i],0,message[i].length(),rec);
			
			int stringWidth = rec.width();
			int stringHeight = rec.height();
			paint.setARGB(255,255,255,255);
			int tempx = x+((width/2)-(stringWidth/2));
			int tempy;
			if( i == 2) tempy = y+(stringHeight*(i+1)+15);
			else tempy = y+(stringHeight*(i+1)+5);
			
			g.drawText(message[i],tempx, tempy, paint);
		}
		Log.i("Debug", "End the game!!!!!!!!");
		/*double scale = .50;		
		int width = gsm.getWidth()//(int)(GamePanel.WIDTH*scale);
		int height = gsm.getHeight()-UserBar.HEIGHT;//(int)((GamePanel.HEIGHT-UserBar.HEIGHT)*scale);
		int x = (GamePanel.WIDTH/2) -(width/2);
		int y = ((GamePanel.HEIGHT-UserBar.HEIGHT)/2)-(height/2);
		
		g.setColor(new Color(0,0,0,100));
		g.fillRect(x, y, width, height);
		
		String[] message = {"Nice Work!","You managed to save: "+Sheep.getAmountSaved()+" Sheep","(Click any button to return to Menu)"};
		g.setFont(new Font("Century Gothic", Font.PLAIN,15));
		for(int i = 0; i < message.length; i++)
		{
			if(i ==2) g.setFont(new Font("Century Gothic", Font.PLAIN,10));
			int stringWidth = (int) g.getFontMetrics().getStringBounds(message[i],g).getWidth();
			int stringHeight = (int) g.getFontMetrics().getStringBounds(message[i],g).getHeight();
			
			g.setColor(Color.WHITE);
			int tempx = x+((width/2)-(stringWidth/2));
			int tempy;
			if( i == 2) tempy = y+(stringHeight*(i+1)+15);
			else tempy = y+(stringHeight*(i+1)+5);
			
			g.drawString(message[i], tempx, tempy);
		}*/
	
		
	}
	
	private void drawUI(Canvas g){
	
		if(ui.isActive()){
			
			int state = ui.getCurrentState();
			
			switch(state)
			{
				case UserBar.BARRICADE:
				case UserBar.DECOY:
					float mousey = observer.getMousey();
					if(mousey < ui.gety())
					{
						int col = getCol(/*mousex*/ observer.getMousex());
						int row = getRow(mousey);
						
						Paint paint = new Paint();
					
						if(!isValidPlacement(col,row))paint.setARGB(100, 255,36,23);
						else paint.setARGB(100,150,255,150);
						
						paint.setStyle(Paint.Style.FILL);
						
						//g.setColor(new Color(255,36,23,100));
						//g.drawRect(col*GRID_SIZE+TileMap.getMapx(), row*GRID_SIZE+TileMap.getMapy(), GRID_SIZE, GRID_SIZE,paint);
						
						g.drawRect(Rectangle.getDrawableRect(col*gridSize+TileMap.getMapx(), row*gridSize+TileMap.getMapy(), gridSize, gridSize),paint);
						
					}
					
					break;
				
				
			}
		}
		
		
		
		ui.draw(g);
		
	}
	
	/*private void drawMapObjects(Graphics2D g){
		
	
		for(BoardObject obj: mapObjects)
		{
			if(obj.getID() == BoardObject.TREE)continue;
			obj.draw(g);
		}
		
		
	}*/
	
	/*private void drawTrees(Graphics2D g){
		
		for(BoardObject obj: mapObjects)
		{
			if(obj.getID() == BoardObject.TREE)obj.draw(g);
		}
		
	}*/
	
	
	/*private void drawGrid(Canvas g){
		
		Paint paint = new Paint();
		paint.setARGB(10*2, 0, 0, 0);
		paint.setStyle(Paint.Style.STROKE);
		
		//g.setColor(new Color(0,0,0,10*2));
		int colsToDraw = tileMap.getNumOfColsToDraw();
		int rowsToDraw = tileMap.getNumOfRowsToDraw();
		int colSet = tileMap.getColumOffSet();
		int rowSet = tileMap.getRowOffSet();
		for(int col = colSet; col < colSet+colsToDraw; col++)
		{
			for(int row = rowSet; row < rowSet+rowsToDraw; row++)
			{
				
				g.drawRect(Rectangle.getDrawableRect(col*GRID_SIZE+TileMap.getMapx(), row*GRID_SIZE+TileMap.getMapy(), GRID_SIZE, GRID_SIZE),paint);
			}
		}
				
	}*/
		
	@Override
	public void screenPressed(float x, float y) {
		
		observer.screenPressed(x, y);	
		
		if(ui.isClicked(y))
		{
			observer.skipScroll();
			ui.registerAction(x,y);
			return;
		}
		
		BoardObject object = getPressedObject(x,y);
		
		if(object != null){
			
			//observer.skipScroll();
			if(object.getID() == BoardObject.SHEEP){
				
				Sheep sheep  = (Sheep) object;
				if(sheep.isMoving())sheep.stopMovement();
				observer.skipScroll();
				
			}
			observer.setPressedObject(object);
			return;
			
		}else if(observer.hasSelectedSheep()){
			
			
			return;
		}
		
		
		
	}


	private BoardObject getPressedObject(float x, float y) {
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		for(Sheep sheep : herd){
			
			if(sheep.isClicked(x, y)) return sheep;
			if(sheep.hasFinalDestination(getCol(x),getRow(y)) && isValidPlacement(getCol(x),getRow(y))){
				
				observer.clickedSheepDestination(sheep);
				return null;
				
			}
		}
		
		String [] mapHash = entityManager.getMapObjectsHash();
		HashMap<String, ArrayList<BoardObject>> mapObjects = entityManager.getMapObjects();
		
		for(int i = 0, length = mapHash.length; i < length; i++){
			
			ArrayList<BoardObject> list = mapObjects.get(mapHash[i]);
			
			for(BoardObject obj: list){
				
				if(obj.isClicked(x, y))return obj;
			}
		}
		
		
		return null;
	}

	@Override
	public void screenReleased(float x, float y) {
	
		observer.screenReleased(x,y);
		
		if(ui.isActive())
		{
			int state = ui.getCurrentState();
			if(ui.isClicked(y))return;
			applyUIEffect(state,getCol(x),getRow(y));
			return;
		}
		
		int code = observer.getGesture();
		
		if(code == LevelObserver.TAP)
		{
			
			if(observer.hasPressedObject())
			{
				BoardObject obj = observer.getPressedObject();
				
				switch(obj.getID()){
				case BoardObject.SHEEP:
					
					Sheep sheep = (Sheep) obj;
					if(sheep.isEating()) return;
					//if(sheep.isMoving())sheep.stopMovement();
					sheep.select();
					//if(sheep.isMovementPaused())sheep.stopMovement();
					//if(sheep == observer.getSelectedSheep())observer.unselectSheep();
					return;
					
				case BoardObject.BARRICADE:
					Barricade bar = (Barricade) obj;
					bar.hit();
					return;
					
				case BoardObject.GRASS:
					if(observer.hasSelectedSheep()){
						GrassPatch grass = (GrassPatch) obj;
						if(isBlocked(grass))return;
						Sheep sheepy = observer.getSelectedSheep();
						if(sheepIsInRangeToEat(sheepy,grass)){
							
							sheepy.eat(grass);
							sheepy.setSelected(false);
						}
						
					}
					
					return;
					
				}
					
				
			}
			
			if(observer.hasSelectedSheep())
			{
					
				int tapCol = observer.getPressedCol();
				int tapRow = observer.getPressedRow();
				if(!isValidPlacement(tapCol,tapRow))return;
				//Destination des = new Destination(tapCol,tapRow);
				observer.getSelectedSheep().move(tapCol, tapRow);
				observer.unselectSheep();
			}
			
			
			return;
		}
		
		if(code == LevelObserver.SWIPE)
		{
			
			if(observer.hasPressedObject())
			{
				System.out.println("swipe!");
				BoardObject obj = observer.getPressedObject();
				
				if(obj.getID() == BoardObject.SHEEP){
					
					int swipeDirection = getSwipeDirection(observer.getPressedx(),observer.getPressedy(),x,y);
					Sheep sheep = (Sheep) obj;
					if(sheep.isEating()) return;
					//if(sheep.isMoving())sheep.stopMovement();
					switch(swipeDirection){
					
					case SWIPE_RIGHT:
						sheep.moveRight();
						return;
					case SWIPE_LEFT:
						sheep.moveLeft();
						return;
					case SWIPE_UP:
						sheep.moveUp();
						return;
					case SWIPE_DOWN:
						sheep.moveDown();
						return;
					
					}
				}
			
				return;
			}

		
		
		}
		
		if(code == LevelObserver.DRAG){
			
			if(observer.hasPressedObject()){
				
				BoardObject obj = observer.getPressedObject();
				
				if(obj.getID() != BoardObject.SHEEP)return;
				
				Sheep sheep = (Sheep)obj;
				sheep.setSelected(false);
				if(observer.sheepWasDragged())sheep.move(observer.dumpDestintionQueue());
				
			}
			
			
			return;
		}
		
			
	}
		
	
	private boolean sheepIsInRangeToEat(Sheep sheepy, GrassPatch grass) {
		
		
		int col = sheepy.getCol();
		int row = sheepy.getRow();
		
		if(grass.hasColRow(col+1, row))return true;
		if(grass.hasColRow(col-1, row))return true;
		if(grass.hasColRow(col, row+1))return true;
		if(grass.hasColRow(col, row-1))return true;
		
		
		return false;
		
		
	}

	

	protected boolean isValidMovePlacement(int col, int row,int sheepCol, int sheepRow) {

		if(!isValidPlacement(col,row)) return false;
				
		if(sheepRow == row || sheepCol == col)return true;
			
		return false;
	}


	
	private void applyUIEffect(int state, int col, int row){
		
		ui.setActive(false);
		
		switch(state)
		{
			case UserBar.DECOY:
			if(!isValidPlacement(col,row))return;
			entityManager.addMapObject(SHEEP_DECOY,new SheepDecoy(col,row,this));
			break;
			
			case UserBar.BARRICADE:
			if(!isValidPlacement(col,row))return;
			entityManager.addMapObject(BARRICADE, new Barricade(col,row,this));
			break;
		}
		
		
		
	}
	
	protected boolean isValidPlacement(int col, int row){
		
		
		if(tileMap.isTileBlocked(col, row)) return false;
		
		//loop through and see if any object has this position
		
		HashMap<String, ArrayList<BoardObject>> mapObjects = entityManager.getMapObjects();
		String[] mapHash = entityManager.getMapObjectsHash();
		
			for(int j = 0, length = mapHash.length; j < length;j++)
			{
				ArrayList<BoardObject> list = mapObjects.get(mapHash[j]);
				
				for(int i = 0, size = list.size(); i < size ; i++){
					
					BoardObject object = list.get(i);
					
					if(object.isDoneUpdating())
					{
						mapObjects.remove(i);
						i--;
						size--;
						continue;
					}
					
					if(object.hasColRow(col,row))return false;
					
				}
				
				
				
			}
			
			
		ArrayList<Sheep> herd = entityManager.getHerd();
		ArrayList<Wolf> wolfPack = entityManager.getWolfPack();
		
		for(Sheep sheep: herd) if(sheep.hasColRowPosition(col, row)) return false;
			
		for(Wolf wolf: wolfPack) if(wolf.hasColRowPosition(col, row)) return false;
							
		return true;
	}


	


	@Override
	public int getSwipeDirection(float startx, float starty, float endx, float endy) {
		
		//if(/*!sheepSwipeable*/observer.isSheepSwipeable())return IGNORE_SWIPE;
		//double tolerance = 0.5;
				
		double length = gridSize/2;
		double xlowerBound = startx - length;
		double xupperBound = startx + length;
		double ylowerBound = starty - length;
		double yupperBound = starty + length;
		
		boolean withinWidth = endx > xlowerBound && endx < xupperBound;
		boolean withinHeight = endy > ylowerBound && endy < yupperBound;
		
		if(withinWidth && withinHeight)return IGNORE_SWIPE;
		
		double xdist = getDistance(startx, endx);
		double ydist = getDistance(starty, endy);
		
		if(endx > startx){
			//moved to the right 
			
			if(xdist > ydist){
				//swiped to the right 
				return SWIPE_RIGHT;
			}
			
			if(xdist < ydist ){
				//either swiped up or down 
				
				if(endy > starty)return SWIPE_DOWN;
				
				if(endy < starty)return SWIPE_UP;
				
			}
			
			if(xdist == ydist) return IGNORE_SWIPE;
			
			
		}else if (endx < startx){
			//moves to the left 
			
			if(xdist > ydist){
				
				return SWIPE_LEFT;
			}
			
			if(xdist < ydist){
				
				if(endy > starty) return SWIPE_DOWN;
						
				if(endy < starty)return SWIPE_UP;
				
			}
			
			if(xdist == ydist){
				
				return IGNORE_SWIPE;
			}
			
			
		}else if (endx == startx){
			
			if(endy < starty)return SWIPE_UP;
			
			if(endy > starty)return SWIPE_DOWN;
			
			
		}
						
		return IGNORE_SWIPE;
	}


	private double getDistance(float start, float end) {
		
		return Math.abs(start-end);
		
	}
	

	private boolean sheepHasPosition(int col, int row) {
		
		ArrayList<Sheep>list = entityManager.getHerd();
		
		for(int i = 0, size = list.size(); i < size; i++)
		{
			Sheep sheep = (Sheep)list.get(i);
			if(sheep.hasColRow(col, row) || sheep.hasNextColRow(col, row))return true;
			
		}
		
		
		return false;
	}

	@Override
	public void screenDragged(float x,float y) {
		
		observer.screenDragged(x,y);
		
		//observer.updateMousePosition(x,y);

		if(ui.isClicked(y) || ui.isActive())return;
		
		observer.scroll(x,y);
		
		//if(ui.isActive() || observer.shouldSkipScroll()) return;
		
		//tileMap.scroll(x, y);
	}
	
	

	
	private boolean isBlocked(GrassPatch grass){
		
		int row = grass.getRow();
		int col = grass.getCol();
		//System.out.print("Checking blocked!");
		
		ArrayList<Sheep> herd = entityManager.getHerd();
		
		if(herd != null)
		{
			
			for(Sheep sheep: herd){
				
				if(sheep.hasColRowPosition(col,row))return true;
			}
			
			
		}
		
		ArrayList<Wolf> wolfPack = entityManager.getWolfPack();
		
		if(wolfPack != null){
			
			for(Wolf wolf: wolfPack){
				
				if(wolf.hasColRowPosition(col,row))return true;
			}
			
			
		}
		
		/*if( mapObjects != null){
			
			/*for(BoardObject obj: mapObjects){
				
				if(obj.hasColRow(col,row))return true;
			}
			
			
		}*/
		
		return false;
		
	}

	public int getWidth() {
		
		return gsm.getWidth();
	}

	public int getHeight() {
		
		return gsm.getHeight();
	}
	
	public Resources getResources(){
		
		return gsm.getGameView().getResources();
	}

	public float getDrawableScale() {
		
		return gsm.getDrawableImageScale();
	}
		
	protected void dispose(){
		
		ui.dispose();
		tileMap.dispose();
		
		
		
	}

	

}
