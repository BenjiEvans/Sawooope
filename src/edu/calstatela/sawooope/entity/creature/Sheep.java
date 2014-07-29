package edu.calstatela.sawooope.entity.creature;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import edu.calstatela.sawooope.entity.EntityID;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.tilemap.TileMap;

/**
 * 
 * @author Benji
 * 
 */
public class Sheep extends Creature {

	// Update States
	public static final int EATING = 2;

	// action states
	private boolean eating;
	private boolean dead;

	private boolean selected;
	
	private boolean[] moves = { false, false, false, false };
	private static SpriteSet sprites;
	private static int spriteWidth;
	private static int spriteHeight;
	private DestinationQueue destinations;
	
	// Condenced into a hunger object
	/*
	 * private Health health; private long eatTimer; private GrassPatch grass;
	 * private long eatTime; public static int safe;
	 */

	public Sheep(int col, int row) {
		super(col, row);
		id = EntityID.SHEEP;
		destinations = new DestinationQueue();
		float scale = Level.getScale();
		box = new CollisionBox(this, (int)(7*scale),(int)(2*scale), (int)(18*scale), (int)(30*scale));
	}

	@Override
	public void update() {
		if (dead)
			return;

		switch (currState) {

		case IDLE:// this is an update state

			listenForMove();

			break;

		case WALKING: // this is an update state

			if (reachedNextPosition()) {
				setNewPosition();
				//check the destination queue 
				if(!destinations.isEmpty()){
					
					if(destinations.reachedDestination(position))
					{
						destinations.dequeue();
						clearMoves();
						
						if(!destinations.isEmpty()){
							
							Position p = destinations.getCurrentDestination();
							
							int col = p.getCol();
							int row = p.getRow();
							int thisCol = position.getCol();
							int thisRow = position.getRow();
							//check to see what direction to move in
							boolean sameCol = col == thisCol;
							boolean sameRow = row == thisRow;
							
							if(sameRow){
								
								if(col > thisCol)moves[EAST] = true;
								else if(col < thisCol)moves[WEST] = true;
								
								
							}
							else if(sameCol){
								
								if(row > thisRow)moves[SOUTH] = true;
								else if(row > thisRow)moves[NORTH] = true;
								
							}
							
							
							
						}
						
						
					}
					listenForMove();
				}
				else{
					
					listenForMove();
				}

			} else
				position.updateXY(dx, dy);

			break;

		case EATING: // also an update state

			break;

		}

		updateAnimation();// always update animations last

	}

	@Override
	public void draw(Canvas g) {
		if (isOffScreen())
			return;
		super.draw(g);
		
		//draw selected box
		
		if(selected){
			Paint paint = new Paint();
			paint.setARGB(100, 0, 0, 255);
			int size = level.getGridSize();
			drawRect(g,(int)drawx,(int)drawy,size,size, paint);		
		}
		
		//draw destinations
		
		if(!destinations.isEmpty())
		{
			
			ArrayList<Position> list = destinations.getDestinations();
			int size = level.getGridSize();
			double tilex = TileMap.getMapx();
			double tiley = TileMap.getMapy();
			Paint paint = new Paint();
			paint.setARGB(150, 155, 75, 42);
			for(int i = 0, length = list.size(); i < length ; i++)
			{
				Position p = list.get(i);
				int col = p.getCol();
				int row = p.getRow();
			
				if(i == length-1){
					drawRect(g,(int)((col*size)+tilex),(int)((row*size)+tiley),width,height,paint);
				}
				
				//draw a center line from one destination to the next 
				int dist = size/2;
				int endx = (int)((col*size)+dist+tilex);
				int endy = (int)((row*size)+dist+tiley);
				if(i > 0)
				{
					Position lastP = list.get(i-1);
					g.drawLine((int)((lastP.getCol()*size)+dist+tilex), (int)((lastP.getRow()*size)+dist+tiley),endx ,endy, paint);
					
				}else{
					
					g.drawLine((int)(drawx+dist),(int)(drawy+dist),endx,endy, paint);
					
					
				}
				
			}
			
			
		}
		
		
		
		
		//draw collision box 
		
		/*double xoff = TileMap.getMapx();
		double yoff = TileMap.getMapy();
		Rectangle rec = box.getRectangle();
		int x = rec.getX();
		int y = rec.getY();
		int width = rec.getWidth();
		int height = rec.getHeight();
		Paint paint = new Paint();
		paint.setARGB(100, 123, 123, 0);
		drawRect(g,(int)(x+xoff),(int)(y+yoff),width,height,paint);*/
		
		
	}

	/**
	 * listens for swipe events to see if this sheep should be moved
	 */
	private void listenForMove() {

		/*if (currState != IDLE)
			setStateTo(IDLE);*/
		
		if (moves[NORTH]) {

			if (northValid()){
				moveNorth();
				return;
			}
			else setFacing(NORTH);
				
		} else if (moves[SOUTH]) {

			if (southValid()){
				moveSouth();
				return;
			}
			else setFacing(SOUTH);

		} else if (moves[EAST]) {
			
			if (eastValid()){
				moveEast();
				return;
			}				
			else setFacing(EAST);

		} else if (moves[WEST]) {
			if (westValid()){
				moveWest();
				return;
			}				
			else setFacing(WEST);
		} 
		
			stay();

	}

	public static void setSprites(GameView view) {
		
		sprites= new SpriteSet();
		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/sheep/sheeps.png");

		spriteWidth = spriteSheet.getWidth() / 12;
		spriteHeight = spriteSheet.getHeight() / 8;
		
		int[] order = {SOUTH,WEST,EAST,NORTH};
		int length = order.length;
		//set Idle Sprites
		for(int i = 0; i < length; i++)
		{
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			sprites.getFrames(order[i]).setIdleFrames(image);		  
		}
		
		//set walking sprites
		for (int i = 0; i < length; i++) {
			
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0, spriteHeight * i,
					spriteWidth, spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet, 2 * spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			sprites.getFrames(order[i]).setWalkingFrames(frames);
		}
		 
		//set eating sprites
		
		spriteSheet = view.getScaledBitmap("sprites/sheep/sheep_eat.png");

		for (int i = 0; i < length; i++) {
			Bitmap[] image = new Bitmap[3];
			
			for (int j = 0; j < 3; j++) {
				image[j] = Bitmap.createBitmap(spriteSheet, j * spriteWidth, i
						* spriteHeight, spriteWidth, spriteHeight);
			}

			sprites.getFrames(order[i]).setEatingFrames(image);

		}
		
		//set dead sprites

		

		spriteSheet = view.getScaledBitmap("sprites/sheep/dead_sheep.png");
		//only has facing east and west sprites
		//first image is facing west 2nd is east
		
		for (int i = 0; i < 2; i++) {
			Bitmap[] img = new Bitmap[1];
			img[0] = Bitmap.createBitmap(spriteSheet, i * spriteWidth, 0,
					spriteWidth, spriteHeight);
			
			
			if(i == 0)sprites.getFrames(WEST).setDeadFrames(img);
			else sprites.getFrames(EAST).setDeadFrames(img);
			
			
		}

		sprites.getFrames(SOUTH).setDeadFrames(sprites.getFrames(WEST).getDeadFrames());
		sprites.getFrames(NORTH).setDeadFrames(sprites.getFrames(EAST).getDeadFrames());
		//setFacing(SOUTH);
		
		
		/*String[] hashOrder = { "South", "West", "East", "North" };

		setIdleSprites(spriteSheet, hashOrder);
		setWalkingSprites(spriteSheet, hashOrder);
		setEatingSprites(view, hashOrder);
		setTransportSprites(view);
		setDeadSprites(view);
		setFacing(SOUTH);*/

	}

	/*private void setEatingSprites(GameView view, String[] hashOrder) {

		Bitmap spriteSheet;

		spriteSheet = view.getScaledBitmap("sprites/sheep/sheep_eat.png");

		for (int i = 0; i < hashOrder.length; i++) {
			Bitmap[] image = new Bitmap[3];
			ArrayList<Bitmap[]> sprite = sprites.get(hashOrder[i]);
			for (int j = 0; j < 3; j++) {
				image[j] = Bitmap.createBitmap(spriteSheet, j * spriteWidth, i
						* spriteHeight, spriteWidth, spriteHeight);
			}

			sprite.add(image);

		}

	}

	private void setDeadSprites(GameView view) {

		Bitmap image;

		image = view.getScaledBitmap("sprites/sheep/dead_sheep.png");

		ArrayList<Bitmap[]> sprite = new ArrayList<Bitmap[]>();
		for (int i = 0; i < 2; i++) {
			Bitmap[] img = new Bitmap[1];
			img[0] = Bitmap.createBitmap(image, i * spriteWidth, 0,
					spriteWidth, spriteHeight);
			sprite.add(img);
		}

		sprites.put("Dead", sprite);

	}

	private void setTransportSprites(GameView view) {

		Bitmap image;

		image = view.getScaledBitmap("sprites/sheep/sheep_transporter.png");

		ArrayList<Bitmap[]> sprite = new ArrayList<Bitmap[]>();
		Bitmap[] transportSprites = new Bitmap[4];
		for (int i = 0; i < 4; i++) {
			transportSprites[i] = Bitmap.createBitmap(image, i * spriteWidth,
					0, spriteWidth, spriteHeight);
		}

		sprite.add(transportSprites);

		sprites.put("Transport", sprite);

	}

	private void setIdleSprites(Bitmap spriteSheet, String[] hashOrder) {

		for (int i = 0; i < hashOrder.length; i++) {
			ArrayList<Bitmap[]> idleSprites = sprites.get(hashOrder[i]);
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);

			idleSprites.add(image);
		}

	}

	private void setWalkingSprites(Bitmap spriteSheet, String[] hashOrder) {

		for (int i = 0; i < hashOrder.length; i++) {
			ArrayList<Bitmap[]> walkingSprites = sprites.get(hashOrder[i]);
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0, spriteHeight * i,
					spriteWidth, spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet, 2 * spriteWidth,
					spriteHeight * i, spriteWidth, spriteHeight);
			walkingSprites.add(frames);
		}

	}*/

	protected void updateAnimation() {

		if (dead) {
			animator.setDelay(-1);

			if (facing[EAST]) {

				//animator.setFrames(sprites.get("Dead"), 1);
				animator.setFrames(sprites.getFrames(EAST).getDeadFrames());
				
				
			} else {
				
				//animator.setFrames(sprites.get("Dead"), 0);
				animator.setFrames(sprites.getFrames(WEST).getDeadFrames());
			}

		} else if (eating) {

			if (facing[NORTH]) {

				if (animator.getCurrAction() != EATING) {

					//animator.setFrames(sprites.get("North"), EATING);
					animator.setFrames(sprites.getFrames(NORTH).getEatingFrames(), EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}

				}

			} else if (facing[SOUTH]) {

				if (animator.getCurrAction() != EATING) {

					//animator.setFrames(sprites.get("South"), EATING);
					animator.setFrames(sprites.getFrames(SOUTH).getEatingFrames(), EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}

				}

			} else if (facing[EAST]) {

				if (animator.getCurrAction() != EATING) {

					//animator.setFrames(sprites.get("East"), EATING);
					animator.setFrames(sprites.getFrames(EAST).getEatingFrames(),EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}

				}

			} else if (facing[WEST]) {

				if (animator.getCurrAction() != EATING) {

					//animator.setFrames(sprites.get("West"), EATING);
					animator.setFrames(sprites.getFrames(WEST).getEatingFrames(), EATING);
					animator.setDelay(100);

				} else {

					int frame = animator.getCurrentFrame();

					switch (frame) {
					case 0:
						animator.setDelay(100);
						break;
					case 1:
						animator.setDelay(200);
						break;
					case 2:
						animator.setDelay(200);
						break;
					}
				}

			}

		} else {
			super.updateAnimation(sprites);
			return;
		}

		animator.update();

	}

	protected boolean southValid() {

		if (!super.southValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

		 if(level.herdHasPosition(this,col, row+1)) return false;
	//	if (level.isPositionBlocked(col, row + 1))	return false;
		// if(level.decoyHasColRow(col,row+1))return false;

		return true;
	}

	protected boolean northValid() {

		if (!super.northValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

		if(level.herdHasPosition(this,col, row-1)) return false;
		//if (level.isPositionBlocked(col, row - 1))return false;
		// if(level.decoyHasColRow(col,row-1))return false;*/

		return true;
	}

	protected boolean eastValid() {

		if (!super.eastValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

		if(level.herdHasPosition(this,col+1, row)) return false;
		//if (level.isPositionBlocked(col + 1, row))return false;
		// if(level.decoyHasColRow(col+1,row))return false;
		return true;
	}

	protected boolean westValid() {

		if (!super.westValid())
			return false;

		int col = position.getCol();
		int row = position.getRow();

	    if(level.herdHasPosition(this, col-1, row)) return false;
		//if (level.isPositionBlocked(col - 1, row))return false;
		// if(level.decoyHasColRow(col-1,row))return false;
		return true;
	}

	/**
	 * 
	 * @return true if the sheep is dead
	 */
	public boolean isDead() {
		return dead;
	}
	
	public void select(){
		selected = true;
	}
	
	public void deselect(){
		selected = false;
	}

	/**
	 * set sheep
	 * 
	 * @param bol
	 */
	public void setDead(boolean bol) {
		dead = bol;
	}

	private void moveBack(int facingDirection) {

		setFacing(facingDirection);
		int size = Level.getGridSize();
		int col = position.getCol();
		int row = position.getRow();
		int x = col * size;
		int y = row * size;
		moves[facingDirection] = true;
		nextPosition = new Position(col, row, x, y);
		dx = -dx;
		dy = -dy;
	}

	/**
	 * Stops this sheep from moving
	 */
	@Override
	public void stop() {

		clearMoves();
		destinations.clear();
		/*switch (currState) {

		case WALKING:
			/*int dir = getFacingDirection();
			moves[dir] = false;*/
			//clearMoves();
			//break;

		//}*/

	}
	
	/*@Override
	protected void stay(){
		super.stay();
		//clearMoves();
	}*/
	
	private void clearMoves(){
		
		for(int i = 0, length=moves.length; i < length ;i++){
		
			moves[i] = false;
		
			
		}
		
	}

	/**
	 * Moves this sheep in the specified direction
	 */
	@Override
	public void move(int direction) {//called when swipped 

		if (currState == IDLE) {
			clearMoves();
			destinations.clear();
			setFacing(direction);
			moves[direction] = true;
			return;
			/*switch (direction) {

			case NORTH:
				if (northValid()) {
					moveNorth();
					moves[NORTH] = true;
				}

				break;
			case SOUTH:
				if (southValid()) {
					moveSouth();
					moves[SOUTH] = true;
				}
				break;
			case EAST:
				if (eastValid()) {
					moveEast();
					moves[EAST] = true;
				}
				break;
			case WEST:
				if (westValid()) {
					moveWest();
					moves[WEST] = true;
				}
				break;
			}
			return;*/
		}

		if (currState == WALKING) {
			destinations.clear();
			int facingDir = getFacingDirection();
			setFacing(direction);
			moves[facingDir] = false;
			moves[direction] = true;

			if (direction != facingDir) {
				if (direction == NORTH && facingDir == SOUTH)
					moveBack(direction);
				else if (direction == SOUTH && facingDir == NORTH)
					moveBack(direction);
				else if (direction == EAST && facingDir == WEST)
					moveBack(direction);
				else if (direction == WEST && facingDir == EAST)
					moveBack(direction);

			}

		}

	}

	@Override
	protected void setAnimation() {
		width = spriteWidth;
		height = spriteHeight;
		
		animator.setFrames(sprites.getFrames(SOUTH).getIdleFrames(), IDLE);
		animator.setDelay(-1);
		
	}

	@Override
	public void move(int col, int row) {//called when move with "tap"
		
		if(!level.isPositionAvailable(col,row))return;
		clearMoves();
		destinations.clear();
		
		
		int thisCol ,thisRow;
		
		/*int thisCol = position.getCol();
		int thisRow = position.getRow();*/
		
		if(nextPosition != null){
			
			thisCol = nextPosition.getCol();
			thisRow = nextPosition.getRow();			
			
		}else {
			thisCol = position.getCol();
			thisRow = position.getRow();
		}
		
		
		boolean sameCol = col == thisCol;
		boolean sameRow = row == thisRow;
		
		int facing = getFacingDirection();	
		int size = level.getGridSize();
		if(sameCol && sameRow)return;
		/*
		 * direction moved methods are called incase 
		 * user uses a tap to do a move back. Keeps 
		 * sheep from walking backwards(moon walking)
		 */
		
		else if(sameCol){
			
			if(row > thisRow){
				
				setFacing(SOUTH);
				moves[SOUTH] = true;
				if(southValid() && facing==NORTH){
					moveSouth();
				}
				
			}
			else {
				
				setFacing(NORTH);
				moves[NORTH]= true;
				if(northValid() && facing==SOUTH){
					
					moveNorth();
				}
				
			}
			destinations.enqueue(new Position(col,row,col*size,row*size));
		}else if(sameRow){
			
			if(col > thisCol){
				
				setFacing(EAST);
				moves[EAST] = true;
				if(eastValid() && facing==WEST){
					
					moveEast();
				}
				
			}
			else {
				
				setFacing(WEST);
				moves[WEST] = true;
				if(westValid() && facing==EAST){
					
					moveWest();
				}
			}
	
			destinations.enqueue(new Position(col,row,col*size,row*size));
			
		}else{// user pressed an area that is not on the same row or collum  of this sheep 
		
			//get the two positions that need to be enqueued
				
			int colx = position.getCol();			
			destinations.enqueue(new Position(colx,row,colx*size,row*size));
			destinations.enqueue(new Position(col,row,col*size,row*size));
			
			int rowx = position.getRow();
			if(rowx < row)moves[SOUTH] = true;
			else if(rowx > row)moves[NORTH] = true;
			
			
		}
		
	}

}
