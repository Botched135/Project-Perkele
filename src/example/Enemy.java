package example;

import org.newdawn.slick.Color;

public class Enemy extends GameObject {
	
	//VARIABLE DECLARATION
	protected float speedMultiplier = 5.0f;
	
	protected static Color enemyTestCol = new Color(255,0,0);
	
	//CONTRUCTERS
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
		
		if(this.distToObj(_player) < 100){
			
			fleeState();
		}
	}
	
	//fleeState makes the Enemy flee from the player (OBS, only works in one direction so far..)
	void fleeState(){
		
		this.changeXPos(1.0f);
		this.changeYPos(1.0f);
	}
	
	public void changeXPos(float _xPos){
		
		if(this.getXPos() +_xPos*speedMultiplier > Window.WIDTH - hitboxX || this.getXPos() +_xPos*speedMultiplier < hitboxX){
		
		} 
		else {
	
			this.setXPos(this.getXPos()+_xPos*speedMultiplier);
		}
	}

	public void changeYPos(float _yPos){
	
		if(this.getYPos() +_yPos*speedMultiplier > Window.HEIGHT - hitboxY || this.getYPos() +_yPos*speedMultiplier < hitboxY){
		
		} 
		else {
		
			this.setYPos(this.getYPos()+_yPos*speedMultiplier);
		}
	
	}
}
