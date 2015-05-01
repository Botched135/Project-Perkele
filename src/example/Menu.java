package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

	//Images
	private Image menuBg = null;
	private Image menuNewGameButton0 = null;
	private Image menuNewGameButton1 = null;
	private Image menuLoadGameButton0 = null;
	private Image menuLoadGameButton1 = null;
	private Image menuExitButton0 = null;
	private Image menuExitButton1 = null;
	
	//Sounds
	
	public static Sound menuTheme = null;
	
	protected static Vector2f menuMousePos;
	private boolean leftMousePressed;
	
	public void init(GameContainer arg0, StateBasedGame sbg) throws SlickException {		
	
		menuNewGameButton0 = new Image("data/menuNewGameButton0.png");	
		menuNewGameButton1 = new Image("data/menuNewGameButton1.png");
		menuLoadGameButton0 = new Image("data/menuLoadGameButton0.png");	
		menuLoadGameButton1 = new Image("data/menuLoadGameButton1.png");	
		menuExitButton0 = new Image("data/menuExitButton0.png");	
		menuExitButton1 = new Image("data/menuExitButton1.png");	
		menuBg = new Image("data/menuBg.png");
		menuMousePos = new Vector2f(0,0);
		
		menuTheme = new Sound("data/bossBattleTheme.ogg");
		
		menuTheme.loop();
		
	}
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		menuMousePos = new Vector2f((gc.getInput().getMouseX()), (gc.getInput().getMouseY()));
		
		if(gc.getInput().isKeyDown(Input.KEY_M)){
			
			//menuTheme.stop();
			
		}
		
		if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			leftMousePressed = false;
		}
		
		//Clicking on "new game" button
		if(		gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false &&
				menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 70 ){
			
			leftMousePressed = true;
			menuTheme.stop();
			
			if(GameState.mainTheme.playing() == false){
				GameState.mainTheme.loop();
			}
			sbg.enterState(1);
		}
		
		//Clicking on "load game" button
		if(		gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false &&
				menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 + 100 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 170 ){
			
			leftMousePressed = true;
			//INSERT METHOD TO LOAD A SAVE GAME!
		}
		
		//Clicking on "exit" button
		if(		gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false &&
				menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 + 200 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 270 ){
			
			leftMousePressed = true;
			gc.exit();
		}
		
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			leftMousePressed = true;
		}
		
		//Alternative exit - Press escape to close application
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(1);
		}
		
	}
	
	
	public void render(GameContainer arg0, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		menuBg.draw(0,0);
		
		//Draw "new game" button
		if(		menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 70 ){
			
			menuNewGameButton1.draw(Window.WIDTH/2 - 219, Window.HEIGHT/2 - 19);
		}
		else{
		menuNewGameButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 - 2);
		}
	
		//Draw "load game" button
		if(		menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 + 100 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 170){
			
			menuLoadGameButton1.draw(Window.WIDTH/2 - 219, Window.HEIGHT/2 + 81);
		}
		else{
		menuLoadGameButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 + 98);
		}
		
		//Draw "Exit" button
		if(		menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 + 200 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 270){
					
			menuExitButton1.draw(Window.WIDTH/2 - 219, Window.HEIGHT/2 + 181);
		}
		else{
		menuExitButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 + 198);
		}	
	}


	public int getID() {
		return 0;			// The ID of this state
	}

}
