package edu.calstatela.sawooope.entity.creature;

import edu.calstatela.sawooope.entity.Rectangle;

/**
 * If you look closely at the entity sprites you will see that the image of the
 * entity doesn't take up the full sprite width and sprite height of the image
 * which means the picture of the entity is smaller than sprite width and height
 * dimensions. This makes it tricky to detect collisions with other entities
 * (primarily Wolves)
 * 
 * <p>
 * A CollisionBox is a solution to this dilemma. A CollisionBox can when
 * entities intersect or overlap such as: a wolf entity overlapping a sheep,
 * which is primarily what we will use it for.
 * </p>
 * 
 * <p>
 * The CollisionBox is simply the smallest rectangle that the entitie's image
 * can fit in.
 * </p>
 * 
 * @author Benji
 * 
 */

public class CollisionBox {

	Creature entity;
	private int xoff;
	private int yoff;
	private int width;
	private int height;
	Rectangle box;

	/**
	 * 
	 * @param entity
	 *            creature that is using a collision box
	 * @param xoff
	 *            x location of where the top left corner of the box should
	 *            start (with respect to the entities sprite)
	 * @param yoff
	 *            y location of where the top left corner of the box should
	 *            start (with respect to the entities sprite)
	 * @param width
	 *            box width
	 * @param height
	 *            box height
	 */
	CollisionBox(Creature entity, int xoff, int yoff, int width, int height) {

		this.entity = entity;
		this.xoff = xoff;
		this.yoff = yoff;
		this.width = width;
		this.height = height;

	}

	/**
	 * 
	 * @return the rectangle that represents the collision box
	 */
	public Rectangle getRectangle() {

		setupBox();

		return box;
	}

	private void setupBox() {

		int x = (int) entity.getX();
		int y = (int) entity.getY();

		box = new Rectangle(x + xoff, y + yoff, width, height);

	}

	/**
	 * Checks to see if the specified CollisionBox overlaps this CollisionBox
	 * with a percent area greater than or equal to the area specified
	 * 
	 * @param b
	 *            collision box
	 * @param area
	 *            the percentage of the area of the specified collision box
	 *            being overlapped (should be a value between 0 and 1 )
	 * @return true if overlapping area between the two boxes is greater than or
	 *         equal to percent area inputed
	 */
	public boolean overLap(CollisionBox b, double area) {

		return overLap(b.getRectangle(), area);
	}

	private boolean overLap(Rectangle targetRec, double area) {

		setupBox();

		if (box.intersects(targetRec)) {
			/*
			 * Get the dimensions of rectangle that represents the intersection
			 * of the two collision boxes
			 */

			// get the rectangle's width
			double width = getWidth(targetRec);
			// get rectangele's height
			double height = getHeight(targetRec);
			// double targetArea = targetRec.getWidth() * targetRec.getHeight();

			// return width * height >= area * targetArea;
			return width * height >= area * box.getArea();
		}

		return false;
	}

	private double getHeight(Rectangle rec) {

		double height = 0;

		if (box.getY() > rec.getY()) {

			if (rec.getMaxY() >= box.getMaxY()) {

				height = box.getHeight();

			} else if (rec.getMaxY() < box.getMaxY()) {

				height = rec.getMaxY() - box.getY();
			}

		} else if (box.getY() < rec.getY()) {

			if (rec.getMaxY() >= box.getMaxY()) {

				height = box.getMaxY() - rec.getY();

			} else if (rec.getMaxY() < box.getMaxY()) {

				height = rec.getMaxY() - rec.getY();
			}

		} else {

			if (rec.getMaxY() >= box.getMaxY()) {

				height = box.getHeight();

			} else if (rec.getMaxY() < box.getMaxY()) {

				height = rec.getMaxY() - box.getY();
			}

		}

		return height;
	}

	private double getWidth(Rectangle rec) {

		double width = 0;

		if (box.getX() > rec.getX()) {

			if (rec.getMaxX() >= box.getMaxX()) {
				width = box.getWidth();

			} else if (rec.getMaxX() < box.getMaxX()) {

				width = rec.getMaxX() - box.getX();

			}

		} else if (box.getX() < rec.getX()) {

			if (rec.getMaxX() >= box.getMaxX()) {
				width = box.getMaxX() - rec.getX();

			} else if (rec.getMaxX() < box.getMaxX()) {

				width = rec.getMaxX() - rec.getX();
				// can use width = rec.getWidth()?
			}

		} else {

			if (rec.getMaxX() >= box.getMaxX()) {
				width = box.getWidth();

			} else if (rec.getMaxX() < box.getMaxX()) {

				width = rec.getMaxX() - box.getX();

			}

		}

		return width;
	}
}
