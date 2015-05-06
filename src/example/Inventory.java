package example;

import java.text.DecimalFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Inventory {
	
	//VARIABLE DECLARATION
	protected Player player;
	
	//Inventory window boundaries
	static protected float xOrigin = 0;
	static protected float yOrigin = 0;
		
	//Images
	private Image guiButtom = null; 
	
	//Misc.
	DecimalFormat df = new DecimalFormat("#.##");
	
	//CONTRUCTERS - Go for default only?
	Inventory(Player _player){
		
		//toggleInventory = false;
		player = _player;
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		guiButtom = new Image("data/guiButtom.png");
		
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
			g.translate((player.vector.getX())-(Window.WIDTH/2), (player.vector.getY())-(Window.HEIGHT/2));
			
			guiButtom.draw(xOrigin, yOrigin);
			
			g.setColor(new Color(255,255,255));
			
			if(GameState.wepSwap == false){
				g.drawString(df.format(player.playerMeleeMinDamage)+"-"+df.format(player.playerMeleeMaxDamage), xOrigin+250, yOrigin+621);
				g.drawString(df.format(player.playerMeleeAttackSpeed), xOrigin+250, yOrigin+642);
				g.drawString(df.format(((player.playerMeleeMinDamage+player.playerMeleeMaxDamage)/2)*player.playerMeleeAttackSpeed), xOrigin+250, yOrigin+663);
				g.drawString(df.format(player.playerVamp)+" hp/hit", xOrigin+250, yOrigin+684);
			}
			else{
				
				g.drawString(df.format(player.playerRangedMinDamage)+"-"+df.format(player.playerRangedMaxDamage), xOrigin+250, yOrigin+621);
				g.drawString(df.format(player.playerRangedAttackSpeed), xOrigin+250, yOrigin+642);
				g.drawString(df.format(((player.playerRangedMinDamage+player.playerRangedMaxDamage)/2)*player.playerRangedAttackSpeed), xOrigin+250, yOrigin+663);
			}
			
			g.drawString(df.format(player.MaxHitPoints), xOrigin+960, yOrigin+621);
			g.drawString(df.format(player.Armor), xOrigin+960, yOrigin+642);
			g.drawString(df.format(player.lifeRegen), xOrigin+960, yOrigin+663);
	}
}

