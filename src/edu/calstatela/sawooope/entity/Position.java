package edu.calstatela.sawooope.entity;

import android.util.Log;

public class Position {
	
	private int col,row;
	private double x,y;
	
	public Position(int col, int row, int x, int y){
		this.col = col;
		this.row = row;
		this.x = x;
		this.y = y;
		
		//Log.i("DrawDebug","Col: "+col+" Row: "+row+" X:"+x+ " Y:"+y);
	}
	
	public Position(Position position){
		
		this.col = position.getCol();
		this.row = position.getRow();
		this.x = position.getx();
		this.y = position.gety();
	}
	
	protected int getMapxLocation(int mapx){
		return (int)(x+mapx);
	}
	
	protected int getMapyLocation(int mapy){
		return (int)(y+mapy);
	}
	
	public double getx(){return x;}
	public double gety(){return y;}
	public int getRow(){return row;}
	public int getCol(){return col;}
	
	public void setCol(int col){
		this.col= col;
	}
	
	public void setRow(int row){
		this.row = row;
	}
	
	public void setXY(int x, int y){
		setX(x);
		setY(y);
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public boolean hasColRow(int col, int row){
		
		return row == this.row && this.col == col;
	}
	
	public void updateXY(double dx, double dy){
		
		x+=dx;
		y+=dy;
	}
	
}
