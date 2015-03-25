package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Enemy extends GameObject {
	
	//VARIABLE DECLARATION
	
	protected float hitpoints = 100;
	protected float speedMultiplier = 5.0f;

	protected static Color enemyTestCol = new Color(255,0,0);
	
	//CONTRUCTERS
	
	Enemy(){
		
		super(); 
	}
	
	Enemy(Vector2f _vector) {
		 
		super(_vector);
	 
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		
		//Makes sure that the entire sprite (test circle at this point) is inside the window when spawned.
		
		float tempX;
		float tempY;
		
		if(_vector.getX() > Window.WIDTH - hitboxX){
			
			tempX = Window.WIDTH - hitboxX;
		}
		else if(_vector.getX() < 0 + hitboxX){
			
			tempX = hitboxX;
		}
		else{
			
			tempX = _vector.getX();
		}

		if(_vector.getY() > Window.HEIGHT - hitboxY){
			
			tempY = Window.HEIGHT - hitboxY;
		
		}
		else if(_vector.getY() < hitboxY){
			tempY = hitboxY;
			
		}
		else{
			
			tempY = _vector.getY();
		}
		
		_vector.set(tempX, tempY);
	}
	
	//METHODS
	
	//stateManager chooses the state of the Enemy based on certain criteria
	void stateManager(Player _player){
			
		beingMeleeAttacked(_player);
		
		if(this.vector.distance(_player.vector) < 300){
			
		seekState(_player);
		}
	}
	
	//fleeState makes the Enemy flee from the player (OBS, only works in one direction so far..)
	void seekState(Player _player){
		
		//float dotProduct = this.vector.dot(_player.vector);
		//double magnitude1 = Math.sqrt(this.vector.length());
		//double magnitude2 = Math.sqrt(_player.vector.length());
		//double angleBewteenVectors = dotProduct/(magnitude1*magnitude2);
		//float a
		float xDistance = this.vector.getX() - _player.vector.getX();
		float yDistance = this.vector.getY() - _player.vector.getY();
		double angleToTurn = Math.toDegrees(Math.atan2(yDistance, xDistance));
		//System.out.println(angleToTurn);
		this.changeXPos(new Vector2f(0.1f * (float)Math.cos(angleToTurn - Math.PI), 0f));
		this.changeYPos(new Vector2f(0f, 0.1f * (float)Math.sin(angleToTurn  - Math.PI)));
	}
	
	void beingMeleeAttacked (Player _player){
		
		if(_player.isAttacking = true && GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX){
			
			this.hitpoints -= 10;
			//System.out.println("ATTACKED!");
		}
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
