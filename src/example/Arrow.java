package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Arrow extends Projectile {
	
	//VARIABLE DECLARATION
	protected static Color arrowTestCol = new Color(255,255,0);
	
	//CONSTRUCTERS
	Arrow(GameObject _owner, Vector2f mousePos, float _speedMultiplier){
		
		super(_owner);
		owner = _owner;
		target = mousePos;
		speedMultiplier = _speedMultiplier;
		hitboxX = 5;
		
		vector.set(_owner.vector.getX(), _owner.vector.getY());
	}
	
	//METHODS
	
}
