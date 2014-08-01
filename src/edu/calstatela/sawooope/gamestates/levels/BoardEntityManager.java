package edu.calstatela.sawooope.gamestates.levels;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Canvas;
import android.util.Log;

import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.EntityID;
import edu.calstatela.sawooope.entity.creature.Sheep;
import edu.calstatela.sawooope.entity.creature.Wolf;

/**
 * Board Entity Manager handles how entities in the game are stored, accessed,
 * updated and drawn.
 * 
 * @author Benji
 */
public class BoardEntityManager{

	ArrayList<Wolf> wolfPack = new ArrayList<Wolf>();
	ArrayList<Sheep> herd = new ArrayList<Sheep>();
	/*
	 * Tree should be the last thing in the hash list since it over laps some
	 * positions when drawn. the map hash should have the items in the order
	 * they will be draw.
	 */
	Integer[] mapHash = { EntityID.TUNNEL, EntityID.BARRICADE, EntityID.SHEEP_DECOY, EntityID.GRASS, EntityID.TREE };
	//HashMap<String, ArrayList<BoardObject>> mapObjects;
	HashMap<Integer, ArrayList<BoardObject>> mapObjects;
	/**
	 * Creates an ArrayList of sheep and wolves; also creates a hashMap of
	 * BoardObjects to store any entities on the map (like trees and barricades
	 * etc.)
	 */
	protected BoardEntityManager() {

		wolfPack = new ArrayList<Wolf>();
		herd = new ArrayList<Sheep>();
		mapObjects = new HashMap<Integer, ArrayList<BoardObject>>();

		for (int i = 0, length = mapHash.length; i < length; i++) {
			mapObjects.put(mapHash[i], new ArrayList<BoardObject>());
		}

	}

	/**
	 * Updates all creatures on the board
	 */
	public void updateCreatures() {

		for (Wolf w : wolfPack)
			w.update();

		for (Sheep sheep : herd)
			sheep.update();

	}

	/**
	 * draw all entities on the board
	 * 
	 * @param g
	 *            canvas to draw on
	 */
	public void drawEntities(Canvas g) {

		// draw all map objects that are not tree's
		int length = mapHash.length - 1;
		for (int i = 0; i < length; i++) {
			ArrayList<BoardObject> list = mapObjects.get(mapHash[i]);

			for (BoardObject obj : list)
				obj.draw(g);
		}

		// draw herd
		for (Sheep sheep : herd)
			sheep.draw(g);

		// draw wolves
		for (Wolf w : wolfPack)
			w.draw(g);

		// draw trees
		ArrayList<BoardObject> list = mapObjects.get(mapHash[length]);

		for (BoardObject obj : list)
			obj.draw(g);

	}

	/**
	 * Adds a boardObjects to the mapObject hash map based on the inputed hash
	 * 
	 * @param object
	 *            board object
	 */
	public void addMapObject(BoardObject object) {

		mapObjects.get(object.getId()).add(object);

	}

	/**
	 * add a sheep to the herd
	 * 
	 * @param sheep
	 *            sheep
	 */
	public void addSheep(Sheep sheep) {

		herd.add(sheep);
	}

	/**
	 * add a wolf to the pack
	 * 
	 * @param wolf
	 *            wolf
	 */
	public void addWolf(Wolf wolf) {

		wolfPack.add(wolf);
	}

	/**
	 * add grass to the field
	 * 
	 * @param grass
	 *            grass
	 */
	public void addGrass(BoardObject grass) {

		mapObjects.get(EntityID.GRASS).add(grass);
	}

	/*
	 * SETTER and GETTERS
	 */
	/**
	 * 
	 * @return the herd of sheep in this level
	 */
	public ArrayList<Sheep> getHerd() {
		return herd;
	}

	/**
	 * 
	 * @return the pack of wolves in this level
	 */
	public ArrayList<Wolf> getWolfPack() {
		return wolfPack;
	}

	/*
	 * 
	 * @return a map of all non-creature entities
	 *
	public HashMap<EntityID, ArrayList<BoardObject>> getMapObjects() {
		return mapObjects;
	}*/

	/**
	 * 
	 * @param hash
	 *            entity hash (see EntityMapping Interface)
	 * @return a list on the specicied entity
	 */
	public ArrayList<BoardObject> getMapObjects(int hash) {
		return mapObjects.get(hash);
	}

}
