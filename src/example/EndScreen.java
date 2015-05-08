package example;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class EndScreen extends BasicGameState {

	//Images
	private Image endScreenBG = null;
	
	//Keys
	private boolean leftMousePressed;
	private boolean enterPressed;
	
	//sounds
	public static Sound EndScreenTheme = null;
	
	//Other Variables
	public static int wave;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		
		endScreenBG = new Image("data/endScreen.png");
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		if(Menu.playerDead == false){
			Menu.playerDead = true;
		}
		
		if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			leftMousePressed = false;
		}
		if(!gc.getInput().isKeyDown(Input.KEY_ENTER)){
			enterPressed = false;
		}
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false){
		//sbg.enterState(0);	
		leftMousePressed = true;
		}
		if(gc.getInput().isKeyDown(Input.KEY_ENTER) && enterPressed == false){
			sbg.enterState(0);
			enterPressed = true;
		}
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException{
		
		endScreenBG.draw(0,0);
		
		g.setColor(new Color(255,0,0));
		
		g.drawString("YOU DIED AT WAVE "+EndScreen.wave, (Window.WIDTH/2)-86, Window.HEIGHT/2-64);
		g.drawString("Press Enter or Mouse to return to main menu", Window.WIDTH/2-190,Window.HEIGHT/2+64);
	}
	public int getID(){
		return 2; //ID of this state
	}
}
