package edu.calstatela.sawooope.entity.plant;

import java.util.ArrayList;
import edu.calstatela.sawooope.entity.BoardObject;
import edu.calstatela.sawooope.entity.EntityID;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * No much to document.... ignore for now
 * 
 * @author Benji
 * 
 */
public class Tree extends Plant {

	public Tree(int col, int row) {
		super(col, row);
		id = EntityID.TREE;
	}

	@Override
	protected void setSpriteDimentions(GameView view) {

		sprites = new ArrayList<Bitmap[]>();
		Bitmap image;

		image = view.getScaledBitmap("sprites/tree.png");

		spriteWidth = image.getWidth();
		spriteHeight = image.getHeight();
		Bitmap[] sprite = { image };

		sprites.add(sprite);

		animator.setFrames(sprites, 0);
		animator.setDelay(-1);

	}

	public void draw(Canvas g) {
		super.draw(g);
		if (isOffScreen())
			return;
		int y = (position.getRow() * Level.getGridSize()) - spriteHeight / 2;
		drawBitmap(g, animator.getImage(), drawx, (float) (y + mapy));

	}

}
