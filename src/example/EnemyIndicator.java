package example;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class EnemyIndicator extends GameObject {
	
	protected GameObject owner;
	protected Vector2f target;
	protected Vector2f tempTarget = new Vector2f();
	//Images 
	private Image enemyIndicatorSprite = null;
	double spriteAngle;
		
		
	//CONSTRUCTERS
	EnemyIndicator(Player _owner, Vector2f _target){
			
		owner = _owner;
		target = _target;
	}
		
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
			
		enemyIndicatorSprite = new Image("data/enemyIndicatorSprite.png");
	}
			
	public void update(Player _owner, Vector2f _target, GameContainer gc, StateBasedGame sbg, int delta){
			
			Vector2f dir = new Vector2f(0.0f, 0.0f);
			tempTarget = new Vector2f(_target.getX(), _target.getY());
				
			dir = tempTarget.sub(_owner.vector);
			dir.normalise();
				
			spriteAngle = dir.getTheta()+90;	
	}
		
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		enemyIndicatorSprite.setRotation((float) spriteAngle);
		enemyIndicatorSprite.draw(vector.getX()-64, vector.getY()-64);			
		}

		//METHODS
	/**
	 * Method for destroying the enemy indicator arrows showing around the player
	 * @param index used for determing which indicator is to be removed
	 * @param _enemyIndicatorList is used to determine which list something is to be removed from
	 */
	public void destroy(int index, ArrayList<EnemyIndicator> _enemyIndicatorList){
		_enemyIndicatorList.remove(index);
		}
	}
