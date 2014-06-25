package edu.calstatela.sawooope.tilemap;


import java.util.Scanner;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import edu.calstatela.sawooope.R;
import edu.calstatela.sawooope.main.GameView;
import edu.calstatela.sawooope.ui.UserBar;

public class TileMap {

	//position
		private double x ,y;
		private static  int mapx, mapy;
		double scrollx;
		double scrolly;
		//private boolean locked;
		//bounds
		private int xmin;
		private int ymin;
		private int xmax;
		private int ymax;
		
		//private double tween; //for camera scrolling 
		
		//map
		
		private int[][] map;
		private int tileSize;
		private int numRows;
		private int numCols;
		private int width;
		private int height;
		//private boolean loaded;
		
		//tileSet
		private Bitmap tileSet;
		private int numTilesAcross;
		private Tile[][] tiles;
		
		//drawing
		
		private int rowOffSet;
		private int colOffSet;
		private int numOfColsToDraw;
		private int numOfRowsToDraw;
			
		public TileMap(){
		
			mapx = 0;
			mapy = 0;
		
	}
		
		public int getx(){return (int)x;}
		public int gety(){return (int)y;}
		
		public void loadTiles(GameView view,String location){
			
			
				
				//tileSet = BitmapFactory.decodeStream(assets.open(location));
				
				tileSet = view.getScaledBitmap(location);
				tileSize  = tileSet.getWidth()/2;
				
				numOfRowsToDraw = (view.getHeight()) / tileSize +2;
				numOfColsToDraw = (view.getWidth())/ tileSize +2;
								
				
				Log.i("Debug","tileSize: "+tileSize);
				
				numTilesAcross = tileSet.getWidth() /tileSize;
				tiles = new Tile[2][numTilesAcross];
			//	System.out.print(numTilesAcross);
				
				Bitmap subImage;
				
				for (int col = 0; col < numTilesAcross; col++)
				{
					subImage = 	Bitmap.createBitmap(tileSet,col*tileSize, 0 , tileSize, tileSize);
					
					tiles[0][col] = new Tile(subImage, Tile.BLOCKED);
					
					subImage = Bitmap.createBitmap(tileSet,col * tileSize,tileSize, tileSize, tileSize);
					
					
					tiles[1][col] = new Tile(subImage, Tile.NORMAL);
					
					
					
					/*subImage = tileSet.getSubimage(col*tileSize, 0 , tileSize, tileSize);
					
					tiles[0][col] = new Tile(subImage, Tile.BLOCKED);
					
					subImage = tileSet.getSubimage(col * tileSize,tileSize, tileSize, tileSize);
					
					
					tiles[1][col] = new Tile(subImage, Tile.NORMAL);*/
					
				}
				// tilesLoaded = true;
				
				
				
			
	}

		public void loadMap(GameView view,String location){
			
			try{
				
				/*InputStream in = getClass().getResourceAsStream(s);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));*/
				Scanner scan = new Scanner(view.getResources().getAssets().open(location));
				numRows = Integer.parseInt(scan.nextLine());
				numCols = Integer.parseInt(scan.nextLine());
				map = new int[numRows][numCols];
				width = numCols * tileSize;
				height = numRows * tileSize;
				
				xmin = view.getWidth() - this.width;
				xmax = 0;
				ymin = (view.getHeight()-UserBar.getHeight()) - this.height;
				ymax = 0;
				
				//read in the matrix
				
				String delimeter = "\\s+"; //white space
				for(int row = 0 ; row < numRows; row++ )
				{
					String line = scan.nextLine();
					String[] tokens = line.split(delimeter);
					
					for(int col = 0 ; col < numCols; col++ )
					{
						map[row][col] = Integer.parseInt(tokens[col]);				
					}
					
				}
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
	}
		
	public int getType(int row, int col) throws ArrayIndexOutOfBoundsException {
		

			int rc = map[row][col];
			int r = rc / numTilesAcross;
			int c = rc % numTilesAcross;
			
			return tiles[r][c].getType();
		
	
	}
	
	public void setScroll(float x , float y){
		scrollx = x;
		scrolly = y;
		
	}
	
	public void scroll(float x, float y){
		
		//if(drawing)return;
		double dx = (x - scrollx);
		double dy = (y - scrolly);
		
		//sSystem.out.println("Dx: "+dx+" Dy: "+dy);
		
		//try and cap at 50
		/*int capx = 5;
		int capy = 5;
		if(dx > capx) dx= capx;
		if(dy > capy) dy= capy;
		if(dx < (-1)*capx) dx = (-1)*capx;
		if(dy < (-1)*capy) dy = (-1)*capy;*/
		
			this.x+=dx;
			this.y+=dy;	
			//mapx+=dx;
			//mapy+=dy;
			//scrollx = x;
			//scrolly = y;
		
		//this.x+=dx;
		//this.y+=dy;	
		
		fixBounds(); 
		
		colOffSet = (int) -this.x/ tileSize;
		rowOffSet = (int) -this.y/ tileSize;
		
		scrollx = x;
		scrolly = y;
		
		//mapx = (int) this.x;
		//mapy = (int) this.y;
		
	}
	
	public int getNumCols(){
		
		return numCols;
	}
	
	public int getNumRows(){
		
		return numRows;
	}
	
	public void fixBounds(){
		if(x < xmin) x = xmin;
		//if(mapx < xmin) mapx = xmin;
		if(y < ymin) y = ymin;
		//if(mapy < ymin) mapy = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	//	if(mapx > xmax) mapx = xmax;
	//	if(mapy > ymax) mapy = ymax;
	
	}
	
	
		
	public void draw(Canvas g){
		
		mapx = (int)x;
		mapy = (int)y;
		
		for(int row = rowOffSet; row < rowOffSet+numOfRowsToDraw; row++ )
		{
			if (row >= numRows)break;
			
			for(int col = colOffSet; col < colOffSet+numOfColsToDraw; col++)
			{
			//	System.out.print("hello");
				if (col >= numCols)break;
				
				//if  (map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				//System.out.println("rc : " + rc + "r: " + r + " c: "+ c);
				//System.out.println("x: " + this.x + " y: " + this.y);
				g.drawBitmap(tiles[r][c].getImage(),(int)x + col*tileSize ,(int)y + row*tileSize ,null);
				
				//if(rc == 2)g.drawRect((int)x+col*tileSize, (int)y+row*tileSize, tileSize, tileSize);
			}
			
		}
	}
	
	public static int getMapx(){return mapx;}
	public static int getMapy(){return mapy;}
	
//	
	

	public boolean isTileBlocked(int col, int row) {
		try{
			return Tile.BLOCKED == getType(row,col);
		}catch(ArrayIndexOutOfBoundsException ex){
			
			ex.printStackTrace();
			
			return true;
		}
		
		
		
	}

	public int getNumOfRowsToDraw() {
		
		return numOfRowsToDraw;
	}

	public int getNumOfColsToDraw() {
		
		return numOfColsToDraw;
	}

	public int getColumOffSet() {
		// TODO Auto-generated method stub
		return colOffSet;
	}
	
	public int getRowOffSet(){
		
		return rowOffSet;
	}
	
	public void dispose(){
		map = null;
		tileSet = null;
		tiles = null;
	}

}
