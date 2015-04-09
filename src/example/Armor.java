package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Armor extends Loot{
	
	private Image armorTestSprite = null;
	//protected float hpBonus = 10;
	
	Armor() {
		 
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		//hpBonus = 100+(10*LootLevel);
		ID = 3;

	}
	
	Armor(Enemy _enemy) {
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 3;
	}
	
public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		armorTestSprite = new Image("data/armorTestSprite.png");
	}
	
public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		armorTestSprite.draw(vector.getX()-32, vector.getY()-32);
	
	g.drawString("lvl:" + lootLevel, vector.getX(), vector.getY()-32);
	
}
	
	public void hpBonus() {
		
	}
	
	public void getInfo() {
		
	}

}
