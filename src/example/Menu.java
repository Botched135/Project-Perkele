package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {		
	}

	
	public void update(GameContainer gc, StateBasedGame sbg, int arg2)
			throws SlickException {
		if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {						//Code for changing state, each state is a new class
			sbg.enterState(1);													//and in this line of code we go to the state "GameState"
		}
		
	}
	
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawString("Menu", 600, 200);
		g.drawString("Press enter to start", 530, 230);
	}


	public int getID() {
		return 0;			// The ID of this state
	}

}
