package example;

import java.util.ArrayList;
import java.util.Random;

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
		private ArrayList <Loot> lootList = new ArrayList <Loot>();
		private ArrayList <Circle> circleList = new ArrayList <Circle>();
		private Random randLoot = new Random();
		private int lootDropDist = 50;
		

	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
			//CHARACTER MOVEMENT
			if(gc.getInput().isKeyDown(Input.KEY_A)) {
				test.changeXPos(-1.0f);
			}
			if(gc.getInput().isKeyDown(Input.KEY_W)) {
				test.changeYPos(-1.0f);
			}
			if(gc.getInput().isKeyDown(Input.KEY_D)) {
				test.changeXPos(1.0f);
			}
			if(gc.getInput().isKeyDown(Input.KEY_S)) {
				test.changeYPos(1.0f);
			}
			
			
			//LOOT SPAWNING
			if(gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
				lootList.add(new Loot());
				System.out.println(lootList.size());
				for(int i = lootList.size()-1; i < lootList.size(); i++) {
					Loot tempLoot = lootList.get(i);
					tempLoot.setXPos(randLoot.nextInt(lootDropDist));
					tempLoot.setYPos(randLoot.nextInt(lootDropDist));
				
					Circle tempCircle = new Circle(Mouse.getX() + tempLoot.getXPos()-(lootDropDist/2) ,Window.HEIGHT - Mouse.getY() + tempLoot.getYPos()-(lootDropDist/2), 50f);
					circleList.add(tempCircle); 
				}
			}
			

			testCircle = new Circle(test.getXPos(),test.getYPos(), test.hitboxX); 
			testLine = new Line(test.getXPos(),test.getYPos(), Mouse.getX(), Window.HEIGHT-Mouse.getY());

	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("HEJ TOSSER!", 500, 200);

		g.draw(testCircle);
		g.draw(testLine);
		
		for(int i = 0; i < circleList.size(); i++) {
			g.draw(circleList.get(i));
		}
	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
