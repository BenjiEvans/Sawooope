package edu.calstatela.sawooope.tilemap;

import android.graphics.Bitmap;

public class Tile {

	private Bitmap image;
	private int type;
	
	//tile types
	public static int BLOCKED = 0;
	public static int NORMAL = 1;
	
	public Tile(Bitmap image, int type){
		
		this.image = image;
		this.type = type;		
}
	
	public Bitmap getImage(){ 
		return image;
}
	public int getType(){
		return type;
}
	
	
	
	
	
	
}
