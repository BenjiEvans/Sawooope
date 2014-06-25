package edu.calstatela.sawooope.entity.creature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import edu.calstatela.sawooope.R;
import edu.calstatela.sawooope.entity.Position;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.tilemap.TileMap;

public class Wolf extends Creature{
	
	static ArrayList<Sheep> huntList = new ArrayList<Sheep>();
	boolean searchingForSheep = false;	
	Sheep targetSheep;
	int steps;
	static boolean doneHunting;
	static boolean startTally;
	
	
	
	public Wolf(int col, int row, Level board){
		super(col,row,board);
		id = WOLF;
		speed = 1;
		box = new CollisionBox(this,4,4,24,28);
		
	}
		
	@Override
	public void update() {
		
		if(!searchingForSheep)chooseNextTarget();		
		
		updateMovement();
		updateAnimation();
		
		
	}
	
	private void updateMovement(){
		//searchingForSheep = true;
		if(!walking){
			
			//moveNorth();
			//moveRandom();
			if(level.isInHerd(targetSheep))chooseNextPosition();
			else if(!huntList.isEmpty()){
				
				chooseNextTarget();
			}
			
			if(level.isHerdDead())
			{
				//if(!startTally)gameBoard.tallyResults();
				//updateLoop.stop();
				
			}
			
		}
		
		
		if(walking)
		{
			position.updateXY(dx, dy);
			
			//System.out.print("Walking");
			if(reachedNextPosition())
			{
				setNewPosition();
				stay();
			}
			//level.eatSheep(this);
		}
		
		level.eatSheep(this);
		
	}
	
	@Override
	public void draw(Canvas g) {
		if(isOffScreen())return;
		super.draw(g);
	}
	
	
	protected void updateAnimation(){
		
		super.updateAnimation();
		
		/*if(!walking)
		{
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
		}
		
		
		animator.update();*/
		
		
		
	}
	
	
	@Override
	public void setSprites(GameView view) {
		
		super.setSprites(view);
		
		Bitmap spriteSheet;
	
			//spriteSheet = BitmapFactory.decodeStream(assets.open("sprites/wolf/wolf.png"));
			spriteSheet = view.getScaledBitmap("sprites/wolf/wolf.png");
			
			spriteWidth = spriteSheet.getWidth()/3;
			spriteHeight = spriteSheet.getHeight()/4;
			
			String[] hashOrder = {"South","West","East", "North"};
			
			
			setIdleSprites(spriteSheet,hashOrder);
			setWalkingSprites(spriteSheet,hashOrder);
			
			animator.setFrames(sprites.get("South"),IDLE);
			animator.setDelay(-1);

		
		
	}
	
	private void setIdleSprites(Bitmap spriteSheet, String[] hashOrder){
		
		for(int i = 0; i < hashOrder.length; i++)
		{
			ArrayList<Bitmap[]> idleSprites = sprites.get(hashOrder[i]);
			Bitmap[] image = new Bitmap[1];
			image[0] = Bitmap.createBitmap(spriteSheet, spriteWidth,spriteHeight*i,spriteWidth,spriteHeight);
			
			idleSprites.add(image);
		}
		
		
	}
	
	private void setWalkingSprites(Bitmap spriteSheet, String[] hashOrder){
		
		for(int i = 0; i < hashOrder.length; i++)
		{
			ArrayList<Bitmap[]> walkingSprites = sprites.get(hashOrder[i]);
			Bitmap[] frames = new Bitmap[2];
			frames[0] = Bitmap.createBitmap(spriteSheet, 0,spriteHeight*i,spriteWidth,spriteHeight);
			frames[1] = Bitmap.createBitmap(spriteSheet,2*spriteWidth,spriteHeight*i,spriteWidth,spriteHeight);
			walkingSprites.add(frames);
		}
		
		
	}
	
	public int getSteps(){return steps;}

	
	private double getDistance(int col , int row){
		
		double dist;//
		
		double x = Math.pow(position.getCol() - col, 2);
		double y = Math.pow(position.getRow() - row, 2);
		
		dist = Math.sqrt(x+y);
		
		return dist;
	}
	
	private void chooseNextPosition(){
		
	//System.out.println("Choosing next position...");
		//************* sheep to our right ***************//
		int col = position.getCol();
		int row = position.getRow();
		
		
				if(targetSheep.getNextCol() > col)
				{
					if(targetSheep.getNextRow() > row)
					{
						if(southValid()){
							moveSouth();
							//System.out.println("Should move South");
						}
						else if(eastValid()){
							moveEast();
							//System.out.println("Should move East");
						}
						else{
							//System.err.println("Should stay!?");
							stay();
						}
					}
					else if(targetSheep.getNextRow() < row)
					{
						if(northValid()){
							//System.out.println("Should move North");
							moveNorth();
						}
						else if(eastValid()){
							//System.out.println("Should move East");
							moveEast();
						}
						else{
							//System.err.println("Should Stay!?");
							stay();
						}
					}
					else
					{
						if(eastValid()){
							//System.out.println("Should move East");
							moveEast();
						}
						else {
							//System.err.println("Should Stay!?");
							stay();
						}
					}
					
					return;
				}
				
		    //****************Sheep to our left ***************************//		
				
				if(targetSheep.getNextCol() < col)
				{
					if(targetSheep.getNextRow() > row)
					{
						if(southValid()){
							//System.out.println("Should move South");
							moveSouth();
						}
						else if(westValid()){
							//System.out.println("Should move West");
							moveWest();
						}
						else{
							//System.err.println("Should Stay!?");
							stay();
						}
					}
					else if(targetSheep.getNextRow() < row){
						
						if(northValid()){
							//System.out.println("Should move North ");
							moveNorth();
						}
						else if(westValid()){
							//System.out.println("Should move West");
							moveWest();
						}
						else{
							//System.err.println("Should Stay!?");
							stay();
						}
					}
					else{
						if(westValid()){
							//System.out.println("Should move West");
							moveWest();
						}
						else{
							//System.err.print("Should Stay!?");
							stay();
						}
					}
					
					return;
				}
				
				
			//*************Sheep has same column********************//
				
				if(targetSheep.getNextCol() == col)
				{
					if(targetSheep.getNextRow() > row)
					{
						if(southValid()){
							//System.out.println("Should move South");
							moveSouth();
						}
					}
					else if(targetSheep.getNextRow() < row)
					{
						
						if(northValid()){
							//System.out.println("Should move North");
							moveNorth();
						}
					}
					
				}
				

		
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void chooseNextTarget(){
		//System.out.println("Testing");
		
		searchingForSheep = true;
		
		huntList = level.getHerd();
		System.out.print("get next target!");
		if(huntList.isEmpty())return;
		//ArrayList<BoardObject> list = level.getEntities("Sheep");
	//	huntList.clear();
		//huntList = sheepList;
				
		for(int i = 0; i < huntList.size(); i++)
		{
			Sheep sheep = (Sheep)huntList.get(i);
			
			if(sheep != null && !sheep.isDead())sheep.setDistance(getDistance(sheep.getCol(), sheep.getRow()));	
			
		}
		
		//then sort the list:
		/*
		 */
			
		Collections.sort(huntList);
		/*  
		 * 	 * 
		 */ 
		//nothing 
  		
		try{
			int index = 0;
			do{
				index++;
				targetSheep = (Sheep)huntList.get(huntList.size()-index);
			}/*while(gameBoard.packHasTarget(this,targetSheep))*/
			while(level.packHasTarget(targetSheep, this));
		}catch(Exception ex){
			
			targetSheep = (Sheep)huntList.get(huntList.size()-1);			
		}
				
		chooseNextPosition();
			
		
	}
	
	public boolean hasTarget(Sheep sheep){
		
		return targetSheep == sheep;
	}
	
		
	
	
	protected boolean northValid(){
		
		if(!super.northValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();		
		
		if(level.tileIsBlocked(col, row-1))return false;
		if(level.packHasPosition(this,col,row-1))return false;
		if(level.portalHasColRow(col,row-1))return false;
		//if(packHasPosition(col,row-1)) return false;
		//if(packHasNextPosition(col,row-1)) return false;
		return true;
	}
	
	protected boolean southValid(){
		
		if(!super.southValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();		
		
		if(level.tileIsBlocked(col, row+1))return false;
		if(level.packHasPosition(this,col,row+1))return false;
		if(level.portalHasColRow(col, row+1))return false;
		
		//if(packHasPosition(col,row+1)) return false;
		//if(packHasNextPosition(col,row+1)) return false;
		
		return true;
		
	}
	
	protected boolean eastValid(){
		
		if(!super.eastValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();				
		
		if(level.tileIsBlocked(col+1, row))return false;
		if(level.packHasPosition(this,col+1,row))return false;
		if(level.portalHasColRow(col+1,row))return false;
		//if(packHasPosition(col+1,row)) return false;
		//if(packHasNextPosition(col+1,row)) return false;
		
		return true;
	}
	
	protected boolean westValid(){
		
		if(!super.westValid()) return false;
		
		int col = position.getCol();
		int row = position.getRow();				
		
		if(level.tileIsBlocked(col-1,row))return false;
		if(level.packHasPosition(this,col-1,row))return false;
		if(level.portalHasColRow(col-1, row))return false;
		//if(level.packHasPosition(this,col-1,row))return false;
		//if(packHasPosition(col-1,row)) return false;
		//if(packHasNextPosition(col-1,row)) return false;
		return true;
	}
	
	
	public Sheep getTarget(){
		return targetSheep;
	}

	@Override
	public void moveRandom() {
		if(unableToMove())
		{
			System.out.println("Ok....Think i found the bug :D");
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
	
	private boolean unableToMove(){
		
		return !northValid() && !eastValid() && !westValid() && !southValid();
	}

	@Override
	public void moveTo(Position pos) {
		// TODO Auto-generated method stub
		
	}

	
}
