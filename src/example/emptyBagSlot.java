package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class emptyBagSlot extends Loot {
	
	//Images
	private Image blankSlot = null; 

	emptyBagSlot() {
		
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		this.vector.set(new Vector2f(1, 1));
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		blankSlot = new Image("data/blankSlot.png");
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		blankSlot.draw(0,0);
	
	}
	
}

