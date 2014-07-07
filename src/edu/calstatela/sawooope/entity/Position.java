package edu.calstatela.sawooope.entity;

/**
 * Position stores the row and column, as well as the x,y coordinates 
 * to describe any location on the map.
 * @author Benji
 *
 */
public class Position {
	
	private int col,row;
	private double x,y;
	
	/**
	 * 
	 * @param col column on map
	 * @param row row on map
	 * @param x x location on map
	 * @param y y location on map
	 */
	public Position(int col, int row, double x, double y){
		this.col = col;
		this.row = row;
		this.x = x;
		this.y = y;
		
	}
	
	/**
	 * 
	 * @param position
	 */
	public Position(Position position){
		
		this.col = position.getCol();
		this.row = position.getRow();
		this.x = position.getx();
		this.y = position.gety();
	}
	
	/**
	 * 
	 * @param mapx TileMap's x position
	 * @return the x position it has on the screen
	 */
	protected float getMapxLocation(double mapx){
		return (float)(x+mapx);
	}
	
	/**
	 * 
	 * @param mapy TileMap's y position
	 * @return the y position it has on the screen
	 */
	protected float getMapyLocation(double mapy){
		return (float)(y+mapy);
	}
	
	/**
	 * 
	 * @return the x coordinate on the map
	 */
	public double getx(){return x;}
	
	/**
	 * 
	 * @return the y coordinate on the map
	 */
	public double gety(){return y;}
	
	/**
	 * 
	 * @return current row
	 */
	public int getRow(){return row;}
	/**
	 * 
	 * @return current column
	 */
	public int getCol(){return col;}
	
	
	/**
	 * Increments the x and y coordinates by 
	 * the amount specified
	 * @param dx amount to increment x
	 * @param dy amount to increment y
	 */
	public void updateXY(double dx, double dy){
		
		x+=dx;
		y+=dy;
				
	}
	
	/**
	 * Determines if positions are the same by checking
	 * column and row
	 * 
	 * @param pos
	 * @return true if they are the same position on the 
	 * map
	 */
	public boolean equals(Position pos){
		
		return pos.getRow()== row && pos.getCol() == col;
	}
	
}
