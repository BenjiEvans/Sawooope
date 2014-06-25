package edu.calstatela.sawooope.gamestates.levels;

import edu.calstatela.sawooope.entity.Tunnel;
import edu.calstatela.sawooope.entity.creature.Sheep;
import edu.calstatela.sawooope.entity.creature.Wolf;
import edu.calstatela.sawooope.entity.plant.GrassPatch;
import edu.calstatela.sawooope.entity.plant.Tree;
import edu.calstatela.sawooope.gamestates.GameStateManager;
import edu.calstatela.sawooope.main.GameView;

public class TestingGround extends Level {
	
	
	public TestingGround(GameStateManager gsm){
		super(gsm);
	}

	protected void initialize(){	
		super.initialize();	
		GameView view = gsm.getGameView();
		tileMap.loadTiles(view,"tiles/tileset.png");
		tileMap.loadMap(view,"maps/map.txt");
		entityManager.addSheep(new Sheep(5,5,this,30000));
		entityManager.addSheep(new Sheep(6,5,this,30000));
		entityManager.addWolf(new Wolf(10,7,this));
		entityManager.addWolf(new Wolf(15,6,this));
		entityManager.addWolf(new Wolf(19,15,this));
		entityManager.addGrass(new GrassPatch(5,10,this,3000));
		entityManager.addMapObject(TREE,new Tree(10,5,this));
		entityManager.addMapObject(TREE,new Tree(12,13,this));
		entityManager.addMapObject(TREE,new Tree(10,19,this));
		entityManager.addMapObject(TREE,new Tree(14,5,this));
		entityManager.addMapObject(TUNNEL, new Tunnel(14,18,this));
	}	
	
	@Override
	protected void dispose() {
		super.dispose();
		
	}
	
}