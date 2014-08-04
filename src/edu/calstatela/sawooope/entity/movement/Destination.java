package edu.calstatela.sawooope.entity.movement;

public class Destination {

	int col,row;
	
	Destination(int col, int row){
		this.col = col;
		this.row = row;
	}
	
	public int getCol(){return col;}
	public int getRow(){return row;}

}
