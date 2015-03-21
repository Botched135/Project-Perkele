package example;

import org.newdawn.slick.Color;

public class Player extends GameObject{
	
	//VARIABLE DECLARATION
	protected float speedMultiplier = 5.0f;
	protected float hitboxX = 25.0f;
	protected float hitboxY = 25.0f;
	
	protected static Color playerTestCol = new Color(50,50,255);
	
	
	public void changeXPos(float _xPos){
	
		if(xPos +_xPos*speedMultiplier > Window.WIDTH - hitboxX || xPos +_xPos*speedMultiplier < 0 + hitboxX){
		
		} 
		else {
	
			xPos += _xPos*speedMultiplier;
		}
	}

	public void changeYPos(float _yPos){
	
		if(yPos +_yPos*speedMultiplier > Window.HEIGHT - hitboxY || yPos +_yPos*speedMultiplier < 0 + hitboxY){
		
		} 
		else {
		
			yPos += _yPos*speedMultiplier;
		}
	
	}

}
