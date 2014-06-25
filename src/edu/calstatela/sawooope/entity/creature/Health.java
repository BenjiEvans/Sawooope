package edu.calstatela.sawooope.entity.creature;

public class Health {

	private int health;
	public final int MAXHEALTH = 4;
	private long healthTimer;
	//private long healthTimerDiff;
	private long healthLength;
	private boolean pause;
	
	Health(int health, long delay){
		
		this.health = health;
		healthLength = delay;
	}
	
	public boolean isDepleated(){
		
		return health==0;
	}
	
	public int getRemaining(){
		
		return health;
	}
	
	public void update(){
		
		if(health == 0 || pause)return;
		
		if(healthTimer != 0)
		{
			long healthTimerDiff = (System.nanoTime() - healthTimer)/1000000;
			
			if(healthTimerDiff > healthLength)
			{
				healthTimer = 0;
				health--;
				healthTimer = System.nanoTime();								
			}
		}
		
	}
	
	public void start(){
		
		healthTimer = System.nanoTime();
		
	}
	
	public void add(int lp){
		
		if(health == MAXHEALTH)return; 
		else{
			health+=lp;
			if(health > MAXHEALTH)health = MAXHEALTH;
		}
	}
	
	public void pauseUpdate(){
		
		pause = true;
	}
	
	public void resetUpdate(){
		
		pause = false;
		
		healthTimer = System.nanoTime();
	}

}
