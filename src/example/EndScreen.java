package example;

import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

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
	private TrueTypeFont font;
	private boolean antiAlias = true;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Font awtFont = new Font("Times New Roman", Font.BOLD, 30);
		font = new TrueTypeFont(awtFont, antiAlias);
		
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
			sbg.enterState(0, new FadeOutTransition(new Color(0,0,0),250), new FadeInTransition(new Color(0,0,0),250));
			enterPressed = true;
		}
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException{
		
		endScreenBG.draw(0,0);
		
		g.setColor(new Color(255,0,0));
		
		font.drawString(Window.WIDTH/2-170, Window.HEIGHT/2-126, "YOU DIED AT WAVE "+EndScreen.wave);
		font.drawString(Window.WIDTH/2-206,Window.HEIGHT/2+226, "Press Enter to return to main menu");
	}
	public int getID(){
		return 2; //ID of this state
	}
}
