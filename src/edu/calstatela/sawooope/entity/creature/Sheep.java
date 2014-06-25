package edu.calstatela.sawooope.entity.creature;


import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.entity.plant.GrassPatch;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;


public class Sheep extends Creature implements Comparable {
	
	protected double distance;
	private boolean selected;
	private boolean[] moves;
	private boolean walkingPause;
	private boolean dead;
	private Health health;
	public static int EATING = 2;
	private boolean eat;
	private boolean eating;
	private long eatTimer;
	private GrassPatch grass;
	private long eatTime;
	public static int safe;
	private ArrayList<Destination> destinationQueue = new ArrayList<Destination>();	
			
	public Sheep(int col, int row, Level board, long healthDelay){
		super(col,row,board);
		id = SHEEP;
		speed = 1;
		moves = new boolean[4];
		box = new CollisionBox(this,7,2,18,30);
		health = new Health(4,healthDelay);
		health.start();
		
	}
	
	Sheep(int col,int row,Level board){
		super(col,row,board);
		
	}
	
	public void setSelected(boolean bol){
		
		selected = bol;
	}
	
	public static int getAmountSaved(){return safe;}
	
	public boolean isMoving(){
		
		for(int i = 0; i < moves.length;i++){
			
			if(moves[i]) return true;
		}
		
		
		return false;
	}
	
	private void stopMoving(){
		
		for(int i = 0; i < moves.length; i++){
			moves[i] = false;
		}
	}
	
	public void stopMovement(){
		
		walkingPause = false;
		stopMoving();
		destinationQueue.clear();				
	}
	
	public void pauseMovement(){
		walkingPause = true;
		stopMoving();
		
	}

	@Override
	public void update() {
		if(dead)return;
		//System.out.println("Queue Size: "+ destinationQueue.size());
		//if(!eat && !walking )moveSheep();
		if(!eat && !walking )listenForMove();
		
		if(eat){
			
			int x = (int) position.getx();
			int y = (int) position.gety();
			
			if(facing[SOUTH]){
				
				if(!walking && !eating)
				{
					moveSouth();
					nextPosition.setY(y+(Level.getGridSize()/2));
					health.pauseUpdate();
				} 
				
				if(reachedNextPosition() && eatTimer == 0){
					walking = false;
					eating = true;
					eatTimer = System.nanoTime();
				}
				
			}else if(facing[NORTH]){
				
				if(!walking && !eating)
				{
					moveNorth();
					nextPosition.setY(y-(Level.getGridSize()/2));
				} 
				
				if(reachedNextPosition() && eatTimer == 0){
					walking = false;
					eating = true;
					eatTimer = System.nanoTime();
				}
				
			} else if (facing[WEST]){
				
				if(!walking && !eating)
				{
					moveWest();
					nextPosition.setX(x-(Level.getGridSize()/2));
				} 
				
				if(reachedNextPosition() && eatTimer == 0){
					walking = false;
					eating = true;
					eatTimer = System.nanoTime();
				}
				
				
				
			}else if(facing[EAST]){
				
				if(!walking && !eating)
				{
					moveEast();
					//targetX = (int)x+(int)(Level.GRID_SIZE/2);
					nextPosition.setX(x+(Level.getGridSize()/2));
				} 
				
				if(reachedNextPosition() && eatTimer == 0){
					walking = false;
					eating = true;
					eatTimer = System.nanoTime();
				}
				
				
			}
			
			
		}
		
		if(eating)
		{
			long eatTimerDiff = (System.nanoTime() - eatTimer)/1000000;
			
			if(eatTimerDiff > eatTime)
			{
				eatTimer = 0;
				eat = false;
				eating = false;
				int targety = nextPosition.getRow()*Level.getGridSize();
				int targetx = nextPosition.getCol()*Level.getGridSize();
				nextPosition.setXY(targetx,targety);
				walking = true;
				grass.eatLayer();
				health.add(1);
				health.resetUpdate();
										
			}
			
			
			
		}
		
		
			
			
		if(walking)
		{
			position.updateXY(dx,dy);
			
			if(reachedNextPosition() && !eat)
			{
				setNewPosition();
				int col = position.getCol();
				int row = position.getRow();
				//only need this for movement speed boost (resetting to original speed)speed = 1;
				if(level.portalHasColRow(col,row))
				{
					safe++;
					level.saveSheep(this);
				}else if(!destinationQueue.isEmpty()){
					//move to next destination itn the queue
					Destination des = destinationQueue.get(0);
					destinationQueue.remove(0);
					
					int newcol = des.getCol(); 
					int newrow =  des.getRow();
					int size = Level.getGridSize();
					Position pos = new Position(newcol,newrow,newcol*size,newrow*size);
					
					moveTo(pos);
					
				}else{
					
					listenForMove();
				}
				
			}
		}
		
	
		updateHealth();
		
		updateAnimation();//always update last 
		
		
	}

	@Override
	public void draw(Canvas g) {
		if(isOffScreen())return; 
		super.draw(g);
	
		if(!dead)
		{
			if(!destinationQueue.isEmpty())drawMoves(g);			
			
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.FILL);
							
			if(selected)
			{
				paint.setARGB(74, 24, 56, 84);								
				drawRect(g,drawx,drawy,spriteWidth,spriteHeight,paint);
			}
			
			//draw health bar
			paint.setARGB(255,255,0,0);
			drawRect(g,drawx+3,drawy,health.getRemaining()*((spriteWidth/4)-3),2,paint,true,false);
		}
		
		
	}
	
	private void drawMoves(Canvas g){
		
		Paint paint = new Paint();
		paint.setARGB(100,150,255,150);
		int size = Level.getGridSize();
		
		for(int i = 0, length = destinationQueue.size(); i < length; i++ ){
			
			Destination des = destinationQueue.get(i);
			
			int desCol = des.getCol();
			int desRow = des.getRow();
			int x = (int) position.getx();
			int y = (int) position.gety();
			
			if(i == 0){
				
				if(length == 1){
					paint.setStyle(Paint.Style.FILL);
									
					//g.drawRect(Rectangle.getDrawableRect(desCol*size+mapx, desRow*size+mapy,size, size),paint);
					drawRect(g,desCol*size+mapx, desRow*size+mapy,size, size,paint);
					
				}
				
				paint.setStyle(Paint.Style.STROKE);
				g.drawLine((int)x+(size/2)+mapx,(int) y+(size/2)+mapy, (desCol*size)+(size/2)+mapx, (desRow*size)+(size/2)+mapy,paint);
				
			}else{
				
				Destination previousDes = destinationQueue.get(i-1);
				
				int desCol2 = previousDes.getCol();
				int desRow2 = previousDes.getRow();
				
				if(i == length-1){
					
					paint.setStyle(Paint.Style.FILL);
					drawRect(g,desCol*size+mapx, desRow*size+mapy,size, size,paint);
				}
				
				paint.setStyle(Paint.Style.STROKE);
				g.drawLine((desCol2*size)+(size/2)+mapx, (desRow2*size)+(size/2)+mapy, (desCol*size)+(size/2)+mapx, (desRow*size)+(size/2)+mapy, paint);
				
				
			}
			
			
		}
						
	}
			
	private void updateHealth(){
		if(dead)return;
		health.update();
		//sight.setLength(visionRadius);
		if(health.isDepleated())
		{
			dead = true;
			selected = false;
		}
		
	}
	
	private void listenForMove(){
		
			if(moves[NORTH]){
				
				if(northValid())moveNorth();
				else{
					//if(!hasDestination())moves[NORTH] = false;
					selected = false;
					stay();
				}
			}
			else if(moves[SOUTH]){
				
				if(southValid())moveSouth();
				else{
					//if(!hasDestination())moves[SOUTH] = false;
					selected = false;
					stay();
					
				}
			}
			else if (moves[EAST]){
				if(eastValid())moveEast();
				else {
					//if(!hasDestination())moves[EAST] = false;
					selected = false;
					stay();
				}
			}
			else if (moves[WEST]){
				if(westValid())moveWest();
				else{
					//if(!hasDestination())moves[WEST] = false;
					selected = false;
					stay();
				}
			}
			else{
				stay();
				//System.out.println("Stay!!!");
			}	
		
	}

	protected void setSprites(GameView view){
		super.setSprites(view);
	
		//Bitmap spriteSheet = ResourceLoader.getBufferedImage("Sprites/Sheep/sheeps.png");
		
		Bitmap spriteSheet;
		
			//spriteSheet = BitmapFactory.decodeStream(assets.open("sprites/sheep/sheeps.png"));
			
			spriteSheet = view.getScaledBitmap("sprites/sheep/sheeps.png");
			
			spriteWidth = spriteSheet.getWidth()/12;
			spriteHeight = spriteSheet.getHeight()/8;
			
			String[] hashOrder = {"South","West","East", "North"};
			
			
			setIdleSprites(spriteSheet,hashOrder);
			setWalkingSprites(spriteSheet,hashOrder);
			setEatingSprites(view,hashOrder);
			setTransportSprites(view);
			setDeadSprites(view);
			setFacing(SOUTH);
		
	}
	
	public void stay(){
		
		super.stay();
	}
	
	
	private void setEatingSprites(GameView view, String[] hashOrder){
		
		//BufferedImage spriteSheet = ResourceLoader.getBufferedImage("Sprites/Sheep/sheepEat.png");
		Bitmap spriteSheet;
	
			//spriteSheet = BitmapFactory.decodeStream(assets.open("sprites/sheep/sheep_eat.png"));
			
			spriteSheet = view.getScaledBitmap("sprites/sheep/sheep_eat.png");
			
			for(int i = 0; i < hashOrder.length;i++)
			{
				Bitmap[] image = new Bitmap[3];
				ArrayList<Bitmap[]> sprite = sprites.get(hashOrder[i]);
				for(int j = 0; j < 3; j++)
				{
					image[j] = Bitmap.createBitmap(spriteSheet,j*spriteWidth,i*spriteHeight, spriteWidth, spriteHeight);
				}
				
				sprite.add(image);
				
			}
			
		
		
	}
	
	private void setDeadSprites(GameView view){
		
		//BufferedImage image = ResourceLoader.getBufferedImage("Sprites/Sheep/deadSheep.png");
		Bitmap image;
	
			//image = BitmapFactory.decodeStream(assets.open("sprites/sheep/dead_sheep.png"));
			
			image = view.getScaledBitmap("sprites/sheep/dead_sheep.png");
			
			ArrayList<Bitmap[]> sprite = new ArrayList<Bitmap[]>();
			for(int i = 0; i < 2; i++)
			{
				Bitmap[] img = new Bitmap[1];
				img[0] = Bitmap.createBitmap(image,i*spriteWidth, 0, spriteWidth, spriteHeight);
				sprite.add(img);
			}
			
			sprites.put("Dead", sprite);
			
		
		
		
	}
	
	private void setTransportSprites(GameView view){
		
		//BufferedImage image = ResourceLoader.getBufferedImage("Sprites/Sheep/sheep_transporter.png");
		
		Bitmap image;
			//image = BitmapFactory.decodeStream(assets.open("sprites/sheep/sheep_transporter.png"));
			
			image = view.getScaledBitmap("sprites/sheep/sheep_transporter.png");
			
			ArrayList<Bitmap[]> sprite = new ArrayList<Bitmap[]>();
			Bitmap[] transportSprites = new Bitmap[4];
			for(int i = 0; i < 4; i++)
			{
				transportSprites[i] = Bitmap.createBitmap(image,i*spriteWidth,0,spriteWidth,spriteHeight);
			}
			
			sprite.add(transportSprites);
			
			sprites.put("Transport",sprite);
					
		
	}
	
	private void setIdleSprites(Bitmap spriteSheet, String[] hashOrder){
		
		for(int i = 0; i < hashOrder.length; i++)
		{
			ArrayList<Bitmap[]> idleSprites = sprites.get(hashOrder[i]);
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet,spriteWidth,spriteHeight*i,spriteWidth,spriteHeight);
			
			idleSprites.add(image);
		}
		
		
	}
	
	private void setWalkingSprites(Bitmap spriteSheet, String[] hashOrder){
		
		for(int i = 0; i < hashOrder.length; i++)
		{
			ArrayList<Bitmap[]> walkingSprites = sprites.get(hashOrder[i]);
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0,spriteHeight*i,spriteWidth,spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet, 2*spriteWidth,spriteHeight*i,spriteWidth,spriteHeight);
			walkingSprites.add(frames);
		}
		
		
	}

	
	
	protected void updateAnimation(){
		
		//super.updateAnimation();
		
		if(dead){
			animator.setDelay(-1);
			
			if(facing[EAST]){
				
				animator.setFrames(sprites.get("Dead"),1);
			}
			else{
				animator.setFrames(sprites.get("Dead"),0);
			}
			
		}else if(eating){
			
			if(facing[NORTH]){
				
				if(animator.getCurrAction() != EATING){
					
					animator.setFrames(sprites.get("North"),EATING);
					animator.setDelay(100);
					
				}
				else{
					
					int frame = animator.getCurrentFrame();
					
					switch(frame){
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
				
				
			}else if(facing[SOUTH]){
				
				if(animator.getCurrAction() != EATING){
					
					animator.setFrames(sprites.get("South"),EATING);
					animator.setDelay(100);
					
				}else{
					
					int frame = animator.getCurrentFrame();
					
					switch(frame){
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
				
				
			}else if(facing[EAST]){
				
				if(animator.getCurrAction() != EATING){
					
					animator.setFrames(sprites.get("East"),EATING);
					animator.setDelay(100);
					
				}else{
					
					int frame = animator.getCurrentFrame();
					
					switch(frame){
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
				
			}else if(facing[WEST]){
				
				if(animator.getCurrAction() != EATING){
					
					animator.setFrames(sprites.get("West"),EATING);
					animator.setDelay(100);
					
				}else{
					
					int frame = animator.getCurrentFrame();
					
					switch(frame){
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
			
			
			
			
		}else{
			super.updateAnimation();
			return;
		}	
			/*else if(!walking && !dead && !eating){
		}
			
			animator.setDelay(-1);
			if(facing[NORTH])
			{
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("North"),IDLE);
				
			}else if(facing[SOUTH])
			{
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("South"), IDLE);
				
			}else if(facing[EAST])
			{
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("East"), IDLE);
	
			}else if(facing[WEST])
			{
				if(animator.getCurrAction() != IDLE) animator.setFrames(sprites.get("West"), IDLE);	
				
			}
		}*/
		
		animator.update();
		
	}


	@Override
	public void moveRandom() {
		
		if(unableToMove())
		{
			stay();
			return;
		}
			
		boolean valid = false;
		
		do{
			int random = (int)(Math.random()*4);
			switch(random)
			{
				case 0:
					if(northValid())
					{
						moveNorth();
						valid = true;
					}
					break;
							
				case 1:
					if(southValid())
					{
						moveSouth();
						valid = true;
					}
					break;
				
				case 2:
					if(eastValid())
					{
						moveEast();
						valid = true;
					}
					break;
					
				case 3:
					if(westValid())
					{
						moveWest();
						valid = true;
					}
			}
			
			
		
		}while(!valid);
		
	}
	
	public void setDistance(double dist){distance = dist;}
	public double getDistance(){return distance;}
	
	
	private boolean unableToMove(){
		
		return !northValid() && !eastValid() && !westValid() && !southValid();
	}

	@Override
	public int compareTo(Object sheep) {
		
		if(dead) return -1;
		
		Sheep s = (Sheep)sheep;
		
		double dist = s.getDistance();
		
		if(dist > distance)return 1;
		else if(dist < distance) return -1;
		else return 0;		
		
	}
	
	protected boolean southValid(){
		
		if(!super.southValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();
		
		if(level.herdHasPosition(this,col, row+1)) return false;
		if(level.tileIsBlocked(col,row+1))return false;
		if(level.decoyHasColRow(col,row+1))return false;
		
		return true;
	}
	
	protected boolean northValid(){
		
		if(!super.northValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();
		
		
		if(level.herdHasPosition(this,col, row-1)) return false;
		if(level.tileIsBlocked(col,row-1))return false;
		if(level.decoyHasColRow(col,row-1))return false;
		
		return true;
	}
	
	protected boolean eastValid(){
		
		if(!super.eastValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();
				
		if(level.herdHasPosition(this,col+1, row)) return false;
		if(level.tileIsBlocked(col+1,row))return false;
		if(level.decoyHasColRow(col+1,row))return false;
		return true;
	}
	
	protected boolean westValid(){
		
		if(!super.westValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();		
		
		if(level.herdHasPosition(this, col-1, row)) return false;
		if(level.tileIsBlocked(col-1,row))return false;
		if(level.decoyHasColRow(col-1,row))return false;
		return true;
	}
	
	public void moveDown(){
		
		if(!destinationQueue.isEmpty()) clearDestinationMovements();
		//if(!facing[SOUTH])speed = 2;
		setFacing(SOUTH);
		moves[SOUTH] = true;
		//speed = 2;
	}
	
	public void moveRight(){
		
		if(!destinationQueue.isEmpty())clearDestinationMovements();
	//	if(!facing[EAST])speed = 2;
		setFacing(EAST);
		moves[EAST] = true;
		//speed = 2;
		
	}
	
	public void moveLeft(){
		
		if(!destinationQueue.isEmpty())clearDestinationMovements();
		//if(!facing[WEST])speed = 2;
		setFacing(WEST);
		moves[WEST] = true;
		//speed = 2;
		
	}
	
	public void moveUp(){
		
		if(!destinationQueue.isEmpty())clearDestinationMovements();
		//if(!facing[NORTH])speed = 2;
		setFacing(NORTH);
		moves[NORTH] = true;
		//speed = 2;		
	}
	
	private void clearDestinationMovements(){
		
		destinationQueue.clear();
		for(int i = 0; i < 4; i++){
			
			moves[i] = false;
		}
		
	}
		
	public boolean isDead(){
		return dead;
	}
	
	public void setDead(boolean bol){
		dead = bol;
	}
	
	
	public void eat(GrassPatch grass){
		
		int[] pos = grass.getColRowPosition();
		int col = position.getCol();
		int row = position.getRow();
		
		if(pos[0] > col){
			
			setFacing(EAST);
			
		}else if (pos[0] < col){
			
			setFacing(WEST);
			
		}else if(pos[1] > row){
			
			setFacing(SOUTH);
			
		}else if(pos[1] < row){
			
			setFacing(NORTH);
			
		}
		
		this.grass = grass;
		grass.setMunchedOn(true);
		eatTime = grass.getEatTime();
		eat = true;
	}

	public boolean isEating() {
		
		return eat;
	}
	
	/*private void moveToNextDestination(){
		
		Destination des = destinationQueue.get(0);
		
		int desCol = des.getCol();
		int desRow = des.getRow();
		
		desDirection = getDirectionOf(desCol,desRow);
		setFacing(desDirection);
		switch(desDirection){
		case NORTH:
			if(northValid())moveNorth();
			break;
		case SOUTH:
			if(southValid())moveSouth();
			break;
		case WEST:
			if(westValid())moveWest();
			break;
			
		case EAST:
			if(eastValid())moveEast();
			break;
			
		}
		
		stopMoving();
		moves[desDirection] = true;
		
	}*/


	public void move(int desCol, int desRow){
		
		int direction = getDirectionOf(desCol,desRow);
		
		if(direction != -1 ){
			
			destinationQueue.add(new Destination(desCol,desRow));
			Destination dest = destinationQueue.get(0);
			destinationQueue.remove(0);
			int col = dest.getCol();
			int row = dest.getRow();
			int size = Level.getGridSize();
			moveTo(new Position(col,row,col*size,row*size));	
			
		}
			
		else return; //destinationQueue  = getApproximation(desCol, desRow);
		
	}
	
	public boolean hasDestination(int col, int row){
		
		if(destinationQueue.isEmpty())return false;
		
		Destination des = destinationQueue.get(destinationQueue.size()-1);
		
		return des.equals(col,row);
	}
	
	public void addToQueue(int col, int row){
		
		if(destinationQueue.isEmpty())	destinationQueue.add(new Destination(col,row));
		
		Destination des = destinationQueue.get(destinationQueue.size()-1);
		
		if(des.equals(col,row))return;
		else destinationQueue.add(new Destination(col,row));
		
		
	}

	public int getLastDestinationCol() {
		
	
		return destinationQueue.get(destinationQueue.size()-1).getCol();
	}
	
	public int getLastDestinationRow() {
		
		
		return destinationQueue.get(destinationQueue.size()-1).getRow();
	}

	public void select() {
		selected = !selected;
	}

	public boolean isSelected() {
		
		return selected;
	}

	public void move(ArrayList<Destination> dumpDestintionQueue) {
		
		
		destinationQueue = dumpDestintionQueue;
		
		//normalize queue before moving :D
		Destination dest = destinationQueue.get(0);
		destinationQueue.remove(0);
		int col = dest.getCol();
		int row = dest.getRow();
		int size = Level.getGridSize();
		moveTo(new Position(col,row,col*size,row*size));		
		
	}
	
	
	//override these methods to reset to orriginal speed if 
	//sheep gets speed boost 
	public void moveSouth(){
		super.moveSouth();
		speed = 1;
	}
	
	public void moveNorth(){
		super.moveNorth();
		speed = 1;
	}
	
	public void moveEast(){
		super.moveEast();
		speed = 1;
	}
	

	public void moveWest(){
		super.moveWest();
		speed = 1;
	}

	public boolean isMovementPaused() {
		// TODO Auto-generated method stub
		return walkingPause;
	}

	public boolean hasFinalDestination(int col, int row) {
		
		
		if(destinationQueue.isEmpty())return false;
		
		Destination des = destinationQueue.get(destinationQueue.size()-1);
		
		return des.equals(col,row);
		
	}

	public Destination getFinalDestination() {
		
		System.out.print("dest Empty?: "+destinationQueue.isEmpty());
		if(destinationQueue.isEmpty())return null;
		
		return destinationQueue.get(destinationQueue.size()-1);
	}

	@Override
	public void moveTo(Position pos) {
		
		int dir = getDirectionOf(pos.getCol(),pos.getRow());
		
		switch(dir){
		case NORTH:
			if(northValid())moveNorth();
			else{
				clearDestinationMovements();
				stay();
			}
			break;
		case SOUTH:
			if(southValid())moveSouth();
			else {
				clearDestinationMovements();
				stay();
			}
			break;
		case WEST:
			if(westValid())moveWest();
			else {
				clearDestinationMovements();
				stay();
			}
			break;
			
		case EAST:
			if(eastValid())moveEast();
			else {
				clearDestinationMovements();
				stay();
			}
			break;
			
		}
		
		
		
	}
	
	
	
	
}
