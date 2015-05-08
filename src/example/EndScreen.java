package example;

import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

public class EndScreen extends BasicGameState {

	//Images
	private Image endScreenBG = null;
	private Image endScreenMainMenuButton0 = null;
	private Image endScreenMainMenuButton1 = null;
	
	//Keys
	private boolean leftMousePressed;
	
	//sounds
	public static Sound EndScreenTheme = null;
	private Sound evilLaugh = null;
	
	//Other Variables
	public static int wave;
	private TrueTypeFont font;
	private boolean antiAlias = true;
	private Vector2f endScreenMousePos = null;
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Font awtFont = new Font("Times New Roman", Font.BOLD, 30);
		font = new TrueTypeFont(awtFont, antiAlias);
		
		endScreenBG = new Image("data/endScreen.png");		
		endScreenMainMenuButton0  = new Image("data/endScreenMainMenuButton0.png");	
		endScreenMainMenuButton1  = new Image("data/endScreenMainMenuButton1.png");
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		
		leftMousePressed = false;
		
		endScreenMousePos = new Vector2f((gc.getInput().getMouseX()), (gc.getInput().getMouseY()));
		
		if(Menu.playerDead == false){
			Menu.playerDead = true;
		}
		
		//Clicking on main menu button returns the game to the main menu
		if(		gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false &&
				endScreenMousePos.getX() > Window.WIDTH/2 - 200 &&
				endScreenMousePos.getX() < Window.WIDTH/2 + 200  &&
				endScreenMousePos.getY() > Window.HEIGHT/2 + 200 &&
				endScreenMousePos.getY() < Window.HEIGHT/2 + 270 ){
			
			leftMousePressed = true;
			
			Menu.menuTheme.loop();
			sbg.enterState(0, new FadeOutTransition(new Color(0,0,0),250), new FadeInTransition(new Color(0,0,0),250));
		}
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException{
		
		endScreenBG.draw(0,0);
		
		g.setColor(new Color(255,0,0));
		
		font.drawString(Window.WIDTH/2-170, Window.HEIGHT/2-126, "YOU DIED AT WAVE "+EndScreen.wave);
		
		//Draw "Exit" button
				if(Menu.playerDead == true){
					if(		endScreenMousePos.getX() > Window.WIDTH/2 - 200 &&
							endScreenMousePos.getX() < Window.WIDTH/2 + 200  &&
							endScreenMousePos.getY() > Window.HEIGHT/2 + 200 &&
							endScreenMousePos.getY() < Window.HEIGHT/2 + 270){
					
						endScreenMainMenuButton1.draw(Window.WIDTH/2 - 220, Window.HEIGHT/2 + 177);
				
					}
					else{
				
						endScreenMainMenuButton0.draw(Window.WIDTH/2 - 202, Window.HEIGHT/2 + 195);
			
					}
				}
		//font.drawString(Window.WIDTH/2-206,Window.HEIGHT/2+226, "Press Enter to return to main menu");
	}
	public int getID(){
		return 2; //ID of this state
	}
}
