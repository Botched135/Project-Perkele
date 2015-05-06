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
	double spriteAngle;
	
	
	//CONSTRUCTERS
	Arrow(Enemy _owner, Vector2f _target, float _speedMultiplier){
		
		super(_owner, _target);
		owner = _owner;
		target = _target;
		damage = _owner.rangedDamage;
		speedMultiplier = _speedMultiplier;
		hitboxX = 5;
		disableDmg = false;
		
		vector.set(_owner.vector.getX(), _owner.vector.getY());
	}
	
	Arrow(Player _owner, Vector2f _target, float _speedMultiplier){
		
		super(_owner, _target);
		owner = _owner;
		target = _target;
		damage = _owner.rangedDamage;
		speedMultiplier = _speedMultiplier;
		hitboxX = 5;
		disableDmg = false;
		
		vector.set(_owner.vector.getX(), _owner.vector.getY());
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		arrowSprite = new Image("data/arrowSprite.png");
		
		Vector2f dir = new Vector2f(0.0f, 0.0f);
		Vector2f tempTarget = new Vector2f(target.getX(), target.getY());
		
		dir = tempTarget.sub(vector);
		dir.normalise();
		
		spriteAngle = dir.getTheta()+90;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	
		arrowSprite.setRotation((float) spriteAngle);
		arrowSprite.draw(vector.getX()-16, vector.getY()-16);
	}

	//METHODS
	
}
