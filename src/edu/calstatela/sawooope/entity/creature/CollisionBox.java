package edu.calstatela.sawooope.entity.creature;

import edu.calstatela.sawooope.entity.Rectangle;


public class CollisionBox {
	
	Creature entity;
	private int xoff;
	private int yoff;
	private int width;
	private int height;
	Rectangle box;

	CollisionBox(Creature entity,int xoff, int yoff, int width, int height){
		
		this.entity = entity;
		this.xoff = xoff;
		this.yoff = yoff;
		this.width = width;
		this.height = height;
		
		//box = new Rectangle((int)entity.getX()+xoff,(int)entity.getY()+yoff,width,height);
	}
	
	/*public void update(){
		
		int x = (int)entity.getX();
		int y = (int)entity.getY();
		
		box = new  Rectangle(x+xoff,y+yoff,width,height);
		
		
	}*/
	
	public Rectangle getRect(){
		
		setupBox();
		
		return box;
	}
	
	private void setupBox(){
		
		
		int x = (int)entity.getX();
		int y = (int)entity.getY();
		
		box = new  Rectangle(x+xoff,y+yoff,width,height);
		
		
		
	}
	
	public boolean intersects(CollisionBox b){
		
		setupBox();
		
		return box.intersects(b.getRect());
	}
	
	public boolean overLap(CollisionBox b, double area){
		
		//checks to see if the area of the interesction is greater than or equal
		// to the total area of the combo box specified in the method
		
		setupBox();
		
		Rectangle targetRec = b.getRect();
				
		if(box.intersects(targetRec))
		{
			double width = getWidth(targetRec);
			double height = getHeight(targetRec);
			double targetArea = targetRec.getWidth()*targetRec.getHeight();
			
			return width*height >= area*targetArea;
			
		}		
		
		return false;
	}
	
	private double getHeight(Rectangle rec){
		
		double height = 0;
		
		if(box.getY() > rec.getY()){
			
			if(rec.getMaxY() >= box.getMaxY()){
				
				height = box.getHeight();
				
			}else if(rec.getMaxY() < box.getMaxY()){
				
				height = rec.getMaxY() - box.getY();
			}
			
			
		}else if(box.getY() < rec.getY()){
			
			if(rec.getMaxY() >= box.getMaxY()){
				
				height = box.getMaxY() - rec.getY();
				
			}else if(rec.getMaxY() < box.getMaxY()){
				
				height = rec.getMaxY() - rec.getY();
				//use height = rec.getHeight()?
			}
			
			
			
		}else{
			
			if(rec.getMaxY() >= box.getMaxY()){
				
				 height = box.getHeight();
				
			}else if(rec.getMaxY() < box.getMaxY()){
				
				height = rec.getMaxY() - box.getY();
				
				// can use height = rec.getHeight(); ?
			}
			
			
			
		}
		
		
		return height;
	}
	
	
	private double getWidth(Rectangle rec){
		
		double width = 0;		
		
		if(box.getX() > rec.getX())
		{
			
			if(rec.getMaxX() >= box.getMaxX() )
			{
				width = box.getWidth();
				
			}else if(rec.getMaxX() < box.getMaxX()){
				
				width = rec.getMaxX() - box.getX();
				
			}
			
			
		}else if(box.getX() < rec.getX()){
			
			
			if(rec.getMaxX() >= box.getMaxX() )
			{
				width = box.getMaxX() - rec.getX();
				
			}else if(rec.getMaxX() < box.getMaxX()){
				
				width = rec.getMaxX() - rec.getX();
				//can use width = rec.getWidth()?
			}
			
			
		}else {
			
			if(rec.getMaxX() >= box.getMaxX() )
			{
				width = box.getWidth();
				
			}else if(rec.getMaxX() < box.getMaxX()){
				
				width = rec.getMaxX()- box.getX();
				
			}
			
			
		}
		
		
		
		
		
		
		return width;
	}
	
	
	
	
	

}
