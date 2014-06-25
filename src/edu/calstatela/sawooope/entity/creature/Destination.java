package edu.calstatela.sawooope.entity.creature;

public class Destination {
	
	private int col, row;
	
	public Destination(int col, int row ){
		
		this.col = col;
		this.row = row;
	}
	
	public int getRow(){return row;}
	public int getCol(){return col;}

	public boolean hasBeenReached(int col, int row) {
		return this.col == col && this.row == row;
	}

	public boolean equals(int col, int row) {
		
		return hasBeenReached(col,row);
	}
	
	public boolean equals(Destination des){
		
		return equals(des.getCol(),des.getRow());
	}

	public double getUnitDistanceAway(int curCol, int curRow) {
		
		double dx = Math.abs(curCol-col);
		double dy = Math.abs(curRow-row);
		
		return Math.sqrt((dx*dx)+(dy*dy));
		
	}

	public static boolean withinOneUnit(Destination dest, int col, int row) {
		
		Destination[] list = {
		
			new Destination(dest.getCol(),dest.getRow()+1),
			new Destination(dest.getCol(),dest.getRow()-1),
			new Destination(dest.getCol()+1,dest.getRow()),
			new Destination(dest.getCol()-1,dest.getRow())
		};
		
		for(Destination d: list){
			
			if(d.equals(col,row))return true;
			
		}
		
		
		
		return false;
	}

}
