package edu.calstatela.sawooope.entity.creature;

import java.util.ArrayList;

import edu.calstatela.sawooope.entity.Position;

public class DestinationQueue {

	ArrayList<Position> destinations;
	
	DestinationQueue(){
		destinations = new ArrayList<Position>();
	}
	
	public boolean isEmpty(){
		return destinations.isEmpty();
	}
	
	public void enqueue(Position p){
		destinations.add(p);
	}
	
	public void dequeue(){
		destinations.remove(destinations.size()-1);
	}
	
	public boolean reachedDestination(Position p){
		return destinations.get(0).equals(p);
	}
	
	public void clear(){
		destinations.clear();
	}
	
	public Position getCurrentDestination()
	{
		return destinations.get(0);
	}
	
	

}
