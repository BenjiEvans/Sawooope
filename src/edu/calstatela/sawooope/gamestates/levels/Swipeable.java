package edu.calstatela.sawooope.gamestates.levels;

public interface Swipeable {
	
	public static final int SWIPE_UP = 0;
	public static final int SWIPE_DOWN = 1;
	public static final int SWIPE_RIGHT = 2;
	public static final int SWIPE_LEFT = 3;
	public static final int IGNORE_SWIPE = 4;
	
	public int getSwipeDirection(float startX, float startY, float endX, float endY);
	
}
