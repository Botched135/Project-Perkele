package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.*;

import com.sun.glass.ui.Screen;

public class GameState extends BasicGameState {

	public void init(GameContainer appgc, StateBasedGame game) throws SlickException {
	}
		Player test = new Player();
		private Shape testCircle = new Circle(test.getXPos(),test.getYPos(), 50.0f);
	
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		
			if(gc.getInput().isKeyDown(Input.KEY_A)) {
				//SOME MOVEMENT TO THE LEFT HERE
				test.changeXPos(-1.0f);
			}
			if(gc.getInput().isKeyDown(Input.KEY_W)) {
				//SOME MOVEMENT DOWNWARDS HERE
				test.changeYPos(-1.0f);
			}
			if(gc.getInput().isKeyDown(Input.KEY_D)) {
				//SOME MOVEMENT TO THE RIGHT HERE
				test.changeXPos(1.0f);
			}
			if(gc.getInput().isKeyDown(Input.KEY_S)) {
				//SOME MOVEMENT DOWNWARDS HERE
				test.changeYPos(1.0f);
			}
			
			testCircle = new Circle(test.getXPos(),test.getYPos(), 50.0f); 
	}
	
	
	public void render(GameContainer appgc, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("HEJ TOSSER!", 500, 200);
		g.draw(testCircle);
		

	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
