package edu.calstatela.sawooope.gamestates.levels;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import edu.calstatela.sawooope.entity.Tunnel;
import edu.calstatela.sawooope.entity.creature.Sheep;
import edu.calstatela.sawooope.entity.creature.Wolf;
import edu.calstatela.sawooope.entity.plant.GrassPatch;
import edu.calstatela.sawooope.entity.plant.Tree;
import edu.calstatela.sawooope.gamestates.GameStateManager;
import edu.calstatela.sawooope.main.GameView;

/**
 * Testing level
 * 
 * @author Benji
 * 
 */
public class TestingGround extends Level {

	public TestingGround(GameStateManager gsm) {
		super(gsm, GameMode.TIME);
	}

	protected void initialize() {
		super.initialize();
		GameView view = gsm.getGameView();
		tileMap.loadTiles(view, "tiles/tileset.png");
		tileMap.loadMap(view, "maps/map.txt");
		entityManager.addSheep(new Sheep(5, 5));
		entityManager.addSheep(new Sheep(7,6));
		entityManager.addSheep(new Sheep(4,6)); 
		entityManager.addWolf(new Wolf(10, 7));
		entityManager.addGrass(new GrassPatch(5, 10, 3000));
		entityManager.addMapObject(new Tree(10, 5));
		entityManager.addMapObject(new Tree(12, 13));
		entityManager.addMapObject(new Tree(10, 19));
		entityManager.addMapObject(new Tree(14, 5));
		entityManager.addMapObject(new Tunnel(14, 18));
	}

	@Override
	protected void dispose() {
		super.dispose();

	}
	
	public void draw(Canvas g){
		
		super.draw(g);
		
		
		/*if(input.isScreenPressed()){
			
			int radius = input.getTouchEventRadius();
			float x,y;
			
			if(input.isScreenDragged()){
				
				x = input.getDraggedX();
				y = input.getDraggedY();
				
			}else{
				x = input.getPressedX();
				y = input.getPressedY();
			}
			
			Paint paint = new Paint();
			paint.setARGB(150, 255, 0, 0);
			//g.drawRect(new Rect((int)(x-radius),(int)(y-radius),(int)(x+radius),(int)(y+radius)), paint);
			g.drawCircle(x, y, radius, paint);			
			
		}*/
		
		
	}

}