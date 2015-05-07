package example;

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
	private Image menuResumeButton0 = null;
	private Image menuResumeButton1 = null;
	private Image menuExitButton0 = null;
	private Image menuExitButton1 = null;
	
	//Sounds
	
	//public static Sound menuTheme = null;
	
	public static boolean playerDead = true;
	public static boolean resetGame = true;
	
	protected static Vector2f menuMousePos;
	private boolean leftMousePressed;
	
	public void init(GameContainer arg0, StateBasedGame sbg) throws SlickException {		
	
		menuNewGameButton0 = new Image("data/menuNewGameButton0.png");	
		menuNewGameButton1 = new Image("data/menuNewGameButton1.png");
		menuResumeButton0 = new Image("data/menuResumeButton0.png");	
		menuResumeButton1 = new Image("data/menuResumeButton1.png");
		menuExitButton0 = new Image("data/menuExitButton0.png");	
		menuExitButton1 = new Image("data/menuExitButton1.png");	
		menuBg = new Image("data/menuBg.png");
		menuMousePos = new Vector2f(0,0);
		
		//menuTheme = new Sound("data/bossBattleTheme.ogg");
		
		//menuTheme.loop();
		
	}
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		menuMousePos = new Vector2f((gc.getInput().getMouseX()), (gc.getInput().getMouseY()));
		
		if(gc.getInput().isKeyDown(Input.KEY_M)){
			
			//menuTheme.stop();
		}
		
		if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			leftMousePressed = false;
		}
		
		//Clicking on first menu button (can be New Game or Resume depending on game state)
		if(		gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false &&
				menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 70 ){
			
			leftMousePressed = true;
			//menuTheme.stop();
			
			//if(GameState.mainTheme.playing() == false){
			//	GameState.mainTheme.loop();
			//}
			
			if(playerDead == true){
				resetGame = true;
				playerDead = false;
			}
			sbg.enterState(1);
			//gc.reinit();
		}
		
		//Clicking on seconds menu button (can be Exit or new game depending on game state)
		if(		gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false &&
				menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 + 100 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 170 ){
			
			leftMousePressed = true;
			
			if(playerDead == false){
				resetGame = true;
				playerDead = false;
				sbg.enterState(1);
			}
			
			if(playerDead == true){
				gc.exit();
				}
		}
		
		//Clicking on third menu button (can be Exit depending on game state)
		if(		gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false &&
				menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 + 200 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 270 ){
			
			leftMousePressed = true;
			
			if(playerDead == false){
			gc.exit();
			}
		}
		
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			leftMousePressed = true;
		}
		
		//Alternative exit - Press escape to close application
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE) && playerDead == true) {
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
			
			if(playerDead == true){
				menuNewGameButton1.draw(Window.WIDTH/2 - 219, Window.HEIGHT/2 - 19);
			}
			else{
				menuResumeButton1.draw(Window.WIDTH/2 - 220, Window.HEIGHT/2 - 20);
			}
		}
		else{
			if(playerDead == true){
				menuNewGameButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 - 2);
			}
			else{
				menuResumeButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 - 2);
			}
		}
	
		//Draw "load game" button
		if(		menuMousePos.getX() > Window.WIDTH/2 - 200 &&
				menuMousePos.getX() < Window.WIDTH/2 + 200  &&
				menuMousePos.getY() > Window.HEIGHT/2 + 100 &&
				menuMousePos.getY() < Window.HEIGHT/2 + 170){
			
			if(playerDead == true){
				menuExitButton1.draw(Window.WIDTH/2 - 219, Window.HEIGHT/2 + 81);
			}
			else{
				menuNewGameButton1.draw(Window.WIDTH/2 - 219, Window.HEIGHT/2 + 81);
			}
		}
		else{
			if(playerDead == true){
				menuExitButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 + 98);
			}
			else{
				menuNewGameButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 + 98);
			}
		}
		
		//Draw "Exit" button
		if(playerDead == false){
			if(		menuMousePos.getX() > Window.WIDTH/2 - 200 &&
					menuMousePos.getX() < Window.WIDTH/2 + 200  &&
					menuMousePos.getY() > Window.HEIGHT/2 + 200 &&
					menuMousePos.getY() < Window.HEIGHT/2 + 270){
			
				menuExitButton1.draw(Window.WIDTH/2 - 219, Window.HEIGHT/2 + 178);
		
			}else{
		
				menuExitButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 + 195);
	
			}
		}
	}

	public int getID() {
		return 0;			// The ID of this state
	}

}
