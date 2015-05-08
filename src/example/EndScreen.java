package example;

import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class EndScreen extends BasicGameState {

	//Images
	private Image EndScreenBG = null;
	
	//Keys
	private boolean leftMousePressed;
	private boolean enterPressed;
	
	//sounds
	public static Sound EndScreenTheme = null;
	
	//Other Variables
	public static int wave;
	private TrueTypeFont font;
	private boolean antiAlias = true;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Font awtFont = new Font("Times New Roman", Font.BOLD, 30);
		font = new TrueTypeFont(awtFont, antiAlias);
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
		sbg.enterState(0);	
		leftMousePressed = true;
		}
		if(gc.getInput().isKeyDown(Input.KEY_ENTER) && enterPressed == false){
			sbg.enterState(0);
			enterPressed = true;
		}
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException{
		g.setColor(new Color(255,0,0));
		
		font.drawString(Window.WIDTH/2-146, Window.HEIGHT/2-64, "YOU DIED AT WAVE "+EndScreen.wave);
		font.drawString(Window.WIDTH/2-206,Window.HEIGHT/2+64, "Press Enter to return to main menu");
	}
	public int getID(){
		return 2; //ID of this state
	}
}
