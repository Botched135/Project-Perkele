package example;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.*;

import org.newdawn.slick.Color;

public class Player extends GameObject{
	
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
	public boolean isAttacking(float mouseX, float mouseY){
		//if(PlayerContainer.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)){//actually left button, but we need to change it later after testing is done with other function
		if(AttackReady(10f)){	
		System.out.println("Debugging: X = "+mouseX+" & Y = "+mouseY);
			return true;
		}
		else
			return false;
	}
	public static boolean AttackReady(float AS){
		//updates
		return true;
	}

}
