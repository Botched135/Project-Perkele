package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class Arrow extends Projectile {
	
	//VARIABLE DECLARATION
	protected static Color arrowTestCol = new Color(255,255,0);
	
	//CONSTRUCTERS
	Arrow(GameObject _owner, Vector2f _target, float _speedMultiplier){
		
		super(_owner, _target);
		owner = _owner;
		target = _target;
		damage = 5;
		speedMultiplier = _speedMultiplier;
		hitboxX = 5;
		disableDmg = false;
		
		vector.set(_owner.vector.getX(), _owner.vector.getY());
	}
	
	//METHODS
	
}
