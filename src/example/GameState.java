package example;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.*;


public class GameState extends BasicGameState {

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
	}
		Player test = new Player();
		private Shape testCircle = new Circle(test.getXPos(),test.getYPos(), test.hitboxX);
		private Shape testLine = new Line(test.getXPos(),test.getYPos(), Mouse.getX(), Mouse.getY());
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
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
			

			testCircle = new Circle(test.getXPos(),test.getYPos(), test.hitboxX); 
			testLine = new Line(test.getXPos(),test.getYPos(), Mouse.getX(), Window.HEIGHT-Mouse.getY());

	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("HEJ TOSSER!", 500, 200);

		g.draw(testCircle);
		g.draw(testLine);
	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
