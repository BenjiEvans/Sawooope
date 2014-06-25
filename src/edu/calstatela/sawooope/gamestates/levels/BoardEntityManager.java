package edu.calstatela.sawooope.gamestates.levels;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Canvas;
import android.util.Log;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.creature.Sheep;
import edu.calstatela.sawooope.entity.creature.Wolf;

public class BoardEntityManager implements EntityMapping {
	
	/******* 
	 * Tree should be the last thing in the hash list since it over laps 
	 * some positions when drawn. the map hash should have the items in the order they will be draw 
	 */	
	
	ArrayList<Wolf> wolfPack = new ArrayList<Wolf>();
	ArrayList<Sheep> herd = new ArrayList<Sheep>();
	String[] mapHash = {TUNNEL,BARRICADE,SHEEP_DECOY,GRASS,TREE};
	HashMap<String, ArrayList<BoardObject>> mapObjects;
	
	protected BoardEntityManager(){
		
		wolfPack = new ArrayList<Wolf>();
		herd = new ArrayList<Sheep>();
		mapObjects = new HashMap<String, ArrayList<BoardObject>>();
		
		for(int i = 0, length = mapHash.length; i < length; i++)
		{
			mapObjects.put(mapHash[i], new ArrayList<BoardObject>());
		}
		
	}
	
	public void updateEntities(){
		
		//update wolves 
		for(Wolf w: wolfPack) w.update();
		
		//update sheeps
		for(Sheep sheep: herd) sheep.update();
		
	}
	
	public void drawEntities(Canvas g){
		
		//draw all map objects that are not tree's
		int length = mapHash.length - 1;
		for(int i = 0; i < length ; i++)
		{
			ArrayList<BoardObject> list = mapObjects.get(mapHash[i]);
			
			for(BoardObject obj: list) obj.draw(g);
		}
		
		//draw herd
		for(Sheep sheep: herd) {
			
			sheep.draw(g);	
		//	Log.i("DrawDebug","Drew Sheep ");
			
		}
		
		//draw wolves
		for(Wolf w: wolfPack) w.draw(g);
		
		//draw trees
		ArrayList<BoardObject> list = mapObjects.get(mapHash[length]);
		
		for(BoardObject obj: list) obj.draw(g);
		
	}
	
	public void addMapObject(String hash, BoardObject object){
		
		mapObjects.get(hash).add(object);
		
	}
	
	public void addSheep(Sheep sheep){
		
		herd.add(sheep);
	}
	
	public void addWolf(Wolf wolf){
		
		wolfPack.add(wolf);
	}
	
	public void addGrass(BoardObject grass){
		
		mapObjects.get(GRASS).add(grass);
	}
	
/*
 *  SETTER and GETTERS	
 */
	
	public ArrayList<Sheep> getHerd(){return herd;}
	public ArrayList<Wolf> getWolfPack(){return wolfPack;}
	public String[] getMapObjectsHash(){return mapHash;}
	public HashMap<String, ArrayList<BoardObject>> getMapObjects(){ return mapObjects;}

/**
 *  HELPER METHODS
 */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
