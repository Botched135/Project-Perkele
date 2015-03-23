package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Player extends GameObject{
	
	protected float speedMultiplier = 5.0f;
	protected static Color playerTestCol = new Color(50,50,255);
	
	Player() {
		
		super();
		hitboxX = 25.0f;
		hitboxY = 25.0f;
	}
	
	Player(Vector2f _vector) {
		 
		super(_vector);
	 
	}
	
	
	public void changeXPos(Vector2f _vector){
	
		if(super.vector.getX() +_vector.getX()*speedMultiplier > Window.WIDTH - hitboxX || super.vector.getX() +_vector.getX()*speedMultiplier < hitboxX){
		
			super.vector.set(super.vector.getX(), super.vector.getY());
		} 
		else {
	
			super.vector.set(super.vector.getX()+_vector.getX()*speedMultiplier, super.vector.getY());
		}
	}

	public void changeYPos(Vector2f _vector){
	
		if(super.vector.getY() +_vector.getY()*speedMultiplier > Window.HEIGHT - hitboxY || super.vector.getY() +_vector.getY()*speedMultiplier < hitboxY){
			
			super.vector.set(super.vector.getX(), super.vector.getY());
		} 
		else {
		
			super.vector.set(super.vector.getX(), super.vector.getY() + _vector.getY()*speedMultiplier);
		}
	
	}
	public boolean isAttacking(Vector2f vector){
		//if(PlayerContainer.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)){//actually left button, but we need to change it later after testing is done with other function
		if(AttackReady(10f)){	
		System.out.println("Debugging: X = " + vector.getX() + " & Y = " + vector.getX());
			return true;
		}
		else
			return false;
	}
	public static boolean AttackReady(float AS){
		//updates
		return true;
	}
	public float dmg(Loot weapon){
		//return weapon.Damage;
		return 1;
	}
	public float dmg(){
		return 1;
	}

}
