package example;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.*;

public class Player extends GameObject{
	
	private float speedMultiplier = 5.0f;
	private float hitboxX = 50.0f;
	private float hitboxY = 50.0f;
	private GameContainer input;
	
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
	public boolean isAttacking(){
		if(input.isMousePressed(input.MOUSE_LEFT_BUTTON)){
		return true;
		}
		return false;
	}

}
