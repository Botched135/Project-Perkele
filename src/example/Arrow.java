package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Arrow extends Projectile {
	
	//VARIABLE DECLARATION
	protected static Color arrowTestCol = new Color(255,255,0);
	
	//Images 
	private Image arrowSprite = null; 
	
	//CONSTRUCTERS
	Arrow(GameObject _owner, Vector2f _target, float _speedMultiplier){
		
		super(_owner, _target);
		owner = _owner;
		target = _target;
		damage = 100;
		speedMultiplier = _speedMultiplier;
		hitboxX = 5;
		disableDmg = false;
		
		vector.set(_owner.vector.getX(), _owner.vector.getY());
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		//arrowSprite = new Image("data/arrow.png");
		
	}
	
public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	
		//arrowSprite.draw(vector.getX()-5, vector.getY()-5);
		
	}

	//METHODS
	
}
