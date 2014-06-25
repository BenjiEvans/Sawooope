package edu.calstatela.sawooope.ui;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class UtilButton {
	
	protected static final int WIDTH = (int) (32*1.5);
	protected static final int HEIGHT = (int) (32*1.5);
	private int width;
	private int height;
	String name;
	int x, y;
	boolean selected;
	int id;
	UtilButton(int x, int y,float scale){
		width= (int) (WIDTH * scale);
		height = (int)(HEIGHT * scale); 
		this.x = x;
		this.y = y;
	}
	
	
	public int getType(){return id;}
	protected int getmaxX(){return x+width;}
	
	public void draw(Canvas g){
		
		//g.setFont(font);
		
		Paint paint = new Paint();
		int startx = x;
		int starty = y;
		int endx = startx+width;
		int endy = starty+height;
		Rect box = new Rect(startx,starty,endx,endy);
		
		if(selected){
			
			paint.setARGB(255, 123, 45, 62);
			paint.setStyle(Paint.Style.FILL);
			g.drawRect(box, paint);
			/*g.setColor(new Color(123,45,62));
			g.fillRect(x, y, WIDTH, HEIGHT);*/
			
			
		}else {
			
			paint.setARGB(255, 255, 255, 255);
			paint.setStyle(Paint.Style.STROKE);
			g.drawRect(box, paint);
			/*g.setColor(Color.white);
			g.drawRect(x, y, WIDTH, HEIGHT);*/
		}
		
		
		paint.setARGB(255, 255, 255, 255);
		Rect rect = new Rect();
		paint.getTextBounds(name,0,name.length(),rect);
		paint.setStyle(Paint.Style.STROKE);
		
		/*int width = rect.width();
		int height = rect.height();
		
		int xpad = (this.width/2)-(width/2);
		int ypad = (this.height/2)-(height/2);*/
		
		//paint.setS
		//g.drawText(name, xpad+x, ypad+y,paint);
		
		
		
		
		
	}
	
	public boolean isSelected(){return selected;}
	
	public boolean isClicked(float x){
		
		
		
		return (x > this.x && x < this.x +width);
	}
	
	public void setSelected(boolean bool){
		
		selected = bool;
	}
	
	public void select(){
		
		selected = !selected;
	}

	
}
