package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.sun.glass.ui.Screen;

public class GameState extends BasicGameState {

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {		
	}

	
	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		
			if(gc.getInput().isKeyPressed(Input.KEY_A)) {
				//SOME MOVEMENT TO THE LEFT HERE
			}
			if(gc.getInput().isKeyPressed(Input.KEY_W)) {
				//SOME MOVEMENT DOWNWARDS HERE
			}
			if(gc.getInput().isKeyPressed(Input.KEY_D)) {
				//SOME MOVEMENT TO THE RIGHT HERE
			}
			if(gc.getInput().isKeyPressed(Input.KEY_S)) {
				//SOME MOVEMENT DOWNWARDS HERE
			}
	}
	
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString("HEJ TOSSER!", 500, 200);

	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
