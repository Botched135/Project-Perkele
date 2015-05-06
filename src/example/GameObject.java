package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.StateBasedGame;

public class GameObject {

	//VARIABLE DECLARATION
	
	protected Vector2f vector;
	protected boolean render;
	protected float hitboxX;
	protected float hitboxY;
	protected byte ID; //PLAYER == 1:	ENEMY == 2:		ARMOR == 3:		WEAPON == 4:
	public int lootLevel=1;

	//CONTRUCTERS
	GameObject() {
		vector = new Vector2f(Window.WIDTH/2, Window.HEIGHT/2);
		render = false;
	}
 
	GameObject(Vector2f _vector) {
		vector = _vector; 
		render = false;
	}
	
	GameObject(Vector2f _vector, boolean _render) {
		vector = _vector; 
		render = _render;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	}	
}
