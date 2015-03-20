package example;

public class Player extends GameObject{
	
	private float speedMultiplier = 5.0f;
	private float hitboxX = 50.0f;
	private float hitboxY = 50.0f;
	
	
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
