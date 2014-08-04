package edu.calstatela.sawooope.entity.creature;

import android.util.Log;
import edu.calstatela.sawooope.entity.Rectangle;
import edu.calstatela.sawooope.entity.movement.Position;
import edu.calstatela.sawooope.gamestates.levels.LevelInputProcessor.TouchPosition;

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

	// may be use full later
	/*
	 * public boolean intersects(CollisionBox b){
	 * 
	 * setupBox();
	 * 
	 * return box.intersects(b.getRect()); }
	 */

	/**
	 * Checks to see if the specified CollisionBox overlaps this CollisionBox
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

		return overLap(b.getRectangle(),area);
		// checks to see if the area of the interesction is greater than or
		// equal
		// to the total area of the combo box specified in the method

		/*setupBox();

		Rectangle targetRec = b.getRectangle();

		if (box.intersects(targetRec)) {
			/*
			 * Get the dimensions of rectangle that represents the intersection
			 * of the two collision boxes
			 */

		/*	// get the rectangle's width
			double width = getWidth(targetRec);
			// get rectangele's height
			double height = getHeight(targetRec);
			//double targetArea = targetRec.getWidth() * targetRec.getHeight();
			

			//return width * height >= area * targetArea;
			return width * height >= area * box.getArea();

		}

		return false;*/
	}
	
	private boolean overLap(Rectangle targetRec, double area){
		
		setupBox();

		//Rectangle targetRec = rect.getRectangle();

		if (box.intersects(targetRec)) {
			/*
			 * Get the dimensions of rectangle that represents the intersection
			 * of the two collision boxes
			 */

			// get the rectangle's width
			double width = getWidth(targetRec);
			// get rectangele's height
			double height = getHeight(targetRec);
			//double targetArea = targetRec.getWidth() * targetRec.getHeight();
			

			//return width * height >= area * targetArea;
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
				// use height = rec.getHeight()?
			}

		} else {

			if (rec.getMaxY() >= box.getMaxY()) {

				height = box.getHeight();

			} else if (rec.getMaxY() < box.getMaxY()) {

				height = rec.getMaxY() - box.getY();

				// can use height = rec.getHeight(); ?
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
	/**
	 * checks if the user pressed an entity with this collision box 
	 * @return true is majority of the collision box is pressed 
	 */
	
	
	/*public boolean isPressed(TouchPosition pos){
		
		setupBox();
		
		Position p = pos.getPosition();
		int cx  = (int) p.getx();//center x
		int cy = (int) p.gety();// center y
		double rad = pos.getRadius();
		
		//get the central radius of the box 
		/*
		 * formula = squareroot((1/(pi*2))*AreaOfBox)
		 */
		/*double boxRad = Math.pow((1/(2.0*Math.PI))*box.getArea(),0.5);
		Log.i("Controls", "Width:"+box.getWidth()+" Height: "+box.getHeight()+"Box-Radius: "+boxRad);
		
		double distance = getDistance(cx,cy,box.getCenterX(),box.getCenterY());
		
		if(distance < boxRad+rad)return true;
		
		return false;
		
		
		
	//}*/
	
	/*
	 * One possible implementation 
	 **/ /*public boolean isPressed(TouchPosition pos){
		
	setupBox();
		
		Position p = pos.getPosition();
		int cx  = (int) p.getx();//center x
		int cy = (int) p.gety();// center y
		double rad = pos.getRadius();
		
		Rectangle rect = new Rectangle((int)(cx-rad),(int)(cy-rad),(int)(2*rad),(int)(2*rad));
		
		return box.intersects(rect);
		
		
	}//*/
	
	/*
	 * Another possible implementation 
	public boolean isPressed(TouchPosition pos){
		
		setupBox();
		
		Position p = pos.getPosition();
		int cx  = (int) p.getx();//center x
		int cy = (int) p.gety();// center y
		double rad = pos.getRadius();
		
		int cornerx = box.getX();
		int cornery = box.getY();
		int width = box.getWidth();
		int height = box.getHeight();
		
		boolean withinx = cx >= cornerx && cx <= cornerx+width;
		boolean withiny = cy >= cornery && cy <= cornerx+height;
		
		if(withinx && withiny)return true;
		
		
		if(withinx){
			
			if(cy <= cornery){//above 
				if(getDistance(cy,cornery) <= rad)return true;
				
			}else{//below
				if(getDistance(cy,cornery+height) <= rad) return true;
			}
			
			return false;
			
		}else if(withiny){
			
			if(cx <= cornerx){//to the left
				
				if(getDistance(cx,cornerx) <= rad)return true;
				
			}else{//to the right 
				
				if(getDistance(cx, cornerx+width) <= rad)return true;
			}
			
			return false;
			
		}else{
			
			if(cy <= cornery){// some where on top
				
				if(cx >= cornerx){//top right corner
					
					if(getDistance(cx,cy,cornerx+width,cornery) <= rad)return true;
					
				}else{ //top left corner
					
					if(getDistance(cx,cy,cornerx,cornery) <= rad)return true;
				}
				
				return false;
				
			}else{//some where bottom
				
				if(cx >= cornerx){//bot right corner
					
					if(getDistance(cx,cy,cornerx+width,cornery+height) <= rad)return true;
					
				}else{ //bot left corner
					
					if(getDistance(cx,cy,cornerx,cornery+height) <= rad)return true;
				}
				
				return false;
				
				
				
			}
			
			
		}
		
		
		
	}*/
	
	
	
	
	
	
	/* Test implementations 
	public boolean isPressed(TouchPosition pos){
		
		setupBox();
		
		Position p = pos.getPosition();
		int cx  = (int) p.getx();//center x
		int cy = (int) p.gety();// center y
		double rad = pos.getRadius();
		
		Rectangle rect = new Rectangle((int)(cx-rad),(int)(cy-rad),(int)(2*rad),(int)(2*rad));
		
		return box.intersects(rect);
		
		//if(box.isWithinRectangle(cx, cy))return true;		
		/*int cornerx = box.getX();
		int cornery = box.getY();
		int width = box.getWidth();
		int height = box.getHeight();
		
		boolean withinx = cx >= cornerx && cx <= cornerx+width;
		boolean withiny = cy >= cornery && cy <= cornerx+height;
		
		if(withinx && withiny)return true;
		
		
		if(withinx){
			
			if(cy <= cornery){//above 
				if(getDistance(cy,cornery) <= rad)return true;
				
			}else{//below
				if(getDistance(cy,cornery+height) <= rad) return true;
			}
			
			return false;
			
		}else if(withiny){
			
			if(cx <= cornerx){//to the left
				
				if(getDistance(cx,cornerx) <= rad)return true;
				
			}else{//to the right 
				
				if(getDistance(cx, cornerx+width) <= rad)return true;
			}
			
			return false;
			
		}else{
			
			if(cy <= cornery){// some where on top
				
				if(cx >= cornerx){//top right corner
					
					if(getDistance(cx,cy,cornerx+width,cornery) <= rad)return true;
					
				}else{ //top left corner
					
					if(getDistance(cx,cy,cornerx,cornery) <= rad)return true;
				}
				
				return false;
				
			}else{//some where bottom
				
				if(cx >= cornerx){//bot right corner
					
					if(getDistance(cx,cy,cornerx+width,cornery+height) <= rad)return true;
					
				}else{ //bot left corner
					
					if(getDistance(cx,cy,cornerx,cornery+height) <= rad)return true;
				}
				
				return false;
				
				
				
			}
			
			
		}*/
		
		
		//return overLap(rect,0.3);
	//	if(box.isWithinRectangle(cx,cy)) return true;
		
		//double rad = pos.getRadius();
		
	//	double target = box.getArea()*.05;//target area
	//	double cumArea = 0;//cumulative area
		
		/*
		 * in order to simplify this algorithim 
		 * the touch position might be used as  
		 * rectangle.
		 */
		
		/*
		 * The algorithm being used takes advantage 
		 * of the fact that the collision box is no larger that 
		 * 30 by 30 units and the touch radius is 15 units long 
		 * 
		 * the algorithm is suppose to calculate the area that the touch 
		 * radius over laps the rectangle. If the overlap area is greater 
		 * than half the area of the collision box then that means this
		 * entities collision box was pressed.
		 * 
		 * 
		 * 
		 */
		
		
		/*int[] corner = new int[2];
		boolean inRect = box.isWithinRectangle(cx,cy);
		
		
		//calculate the cumulative area
		for(int i = 0 ;i < 4; i++)
		{
			switch(i){
			case 0://top left
				corner[0] = box.getX();
				corner[1] = box.getY();
				
				break;
			case 1://bottom left
				corner[0] = box.getX();
				corner[1] = box.getMaxY();
				break;
			case 2://top right
				corner[0] = box.getMaxX();
				corner[1] = box.getY();
				break;
			case 3://bottom left
				corner[0] = box.getMaxX();
				corner[1] = box.getMaxY();
				break;
			}  
			
			cumArea += getArcArea(cx,cy,rad,corner,inRect);
			
			if(cumArea >= target) return true;
		}*/
		
		
		
	
		//return cumArea >= target;
	//}*/
	
	
	/*private double getArcArea(int x, int y, double rad, int[] corner, boolean inRect){
		
		double dist = getDistance(x,y,corner[0],corner[1]);
		
		double area = 0;
		
		if(dist < rad ){
			
			if(inRect)area = getDistance(x,corner[0])*getDistance(y,corner[1]);	
			else{
				
				
			}
			
		}else if (dist > rad){
			
		}else{
			
			
			
		}
		
		
		return area;
	}*/
	
	
	/*private double getDistance(int startx, int starty, int endx, int endy){
		
		double dxsqr = Math.pow((startx-endx),2);
		
		double dysqr = Math.pow((starty-endy), 2);
		
		
		return Math.pow(dxsqr+dysqr, 0.5);		
	}
	
	private double getDistance(int start, int end){
		
		return Math.abs(start-end);
	}*/

}
