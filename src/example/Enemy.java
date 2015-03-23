package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Enemy extends GameObject {
	
	//VARIABLE DECLARATION
	
	protected float speedMultiplier = 5.0f;
	
	protected static Color enemyTestCol = new Color(255,0,0);
	
	//CONTRUCTERS
	
	Enemy(){
		
		super(); 
	}
	
	Enemy(Vector2f _vector) {
		 
		super(_vector);
	 
	}
	
	Enemy(float _xPos, float _yPos) {
		 
		super(_xPos, _yPos);
		
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		//Makes sure that the entire sprite (test circle at this point) is inside the window when spawned.
		if(_xPos > Window.WIDTH - hitboxX){
			super.xPos = Window.WIDTH - hitboxX;
		
		}
		else if(_xPos < 0 + hitboxX){
			super.xPos = hitboxX;
		}
		else{
			super.xPos = _xPos;
		}
		
		if(_yPos > Window.HEIGHT - hitboxY){
			super.yPos = Window.HEIGHT - hitboxY;
		
		}
		else if(_yPos <hitboxY){
			super.yPos = hitboxY;
		}
		else{
			super.yPos = _yPos;
		}
	}
	
	//METHODS
	
	//stateManager chooses the state of the Enemy based on certain criteria
	void stateManager(Player _player){
		
		if(this.vector.distance(_player.vector) < 300){
			
			fleeState(_player);
		}
	}
	
	//fleeState makes the Enemy flee from the player (OBS, only works in one direction so far..)
	void fleeState(Player _player){
		
		//float dotProduct = this.vector.dot(_player.vector);
		//double magnitude1 = Math.sqrt(this.vector.length());
		//double magnitude2 = Math.sqrt(_player.vector.length());
		//double angleBewteenVectors = dotProduct/(magnitude1*magnitude2);
		//float a
		float xDistance = this.vector.getX() - _player.vector.getX();
		float yDistance = this.vector.getY() - _player.vector.getY();
		double angleToTurn = Math.toDegrees(Math.atan2(yDistance, xDistance));
		System.out.println(angleToTurn);
		this.changeXPos(new Vector2f(0.1f * (float)Math.cos(angleToTurn - Math.PI), 0f));
		this.changeYPos(new Vector2f(0f, 0.1f * (float)Math.sin(angleToTurn  - Math.PI)));
	}
	
	public void changeXPos(Vector2f _vector){
		
		if(this.vector.getX() +_vector.getX()*speedMultiplier > Window.WIDTH - hitboxX || this.vector.getX() +_vector.getX()*speedMultiplier < hitboxX){
		
		} 
		else {
	
			this.vector.set(this.vector.getX()+_vector.getX()*speedMultiplier, this.vector.getY());
		}
	}

	public void changeYPos(Vector2f _vector){
	
		if(this.vector.getY() +_vector.getY()*speedMultiplier > Window.HEIGHT - hitboxY || this.vector.getY() +_vector.getY()*speedMultiplier < hitboxY){
		
		} 
		else {
		
			this.vector.set(this.vector.getX(), this.vector.getY() + _vector.getY()*speedMultiplier);
		}
	
	}
}
