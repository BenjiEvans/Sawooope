package edu.calstatela.sawooope.entity.plant;

import java.util.ArrayList;
import edu.calstatela.sawooope.entity.EntityID;
import edu.calstatela.sawooope.gamestates.levels.Level;
import edu.calstatela.sawooope.main.GameView;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * 
 * @author Benji
 * 
 */
public class Tree extends Plant {

	private static ArrayList<Bitmap[]> sprites = new ArrayList<Bitmap[]>();
	private static int spriteWidth, spriteHeight;

	public Tree(int col, int row) {
		super(col, row);
		id = EntityID.TREE;
	}

	public static void setSprites(GameView view) {

		sprites = new ArrayList<Bitmap[]>();
		Bitmap image;

		image = view.getScaledBitmap("sprites/tree.png");

		spriteWidth = image.getWidth();
		spriteHeight = image.getHeight();
		Bitmap[] sprite = { image };

		sprites.add(sprite);

	}

	public void draw(Canvas g) {
		super.draw(g);
		if (isOffScreen())
			return;
		int y = (position.getRow() * Level.getGridSize()) - height / 2;
		drawBitmap(g, animator.getImage(), drawx, (float) (y + mapy));

	}

	@Override
	protected void setAnimation() {
		width = spriteWidth;
		height = spriteHeight;

		animator.setFrames(sprites, 0);
		animator.setDelay(-1);

	}

}
