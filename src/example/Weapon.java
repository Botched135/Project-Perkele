package example;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Weapon extends Loot{
	
	private Image weaponTestSprite = null;
	//protected int wepDMG = (randDmg.nextInt(100))*LootLevel;
	//protected float attackSpeed = (randSpeed.nextFloat())*2*LootLevel;
	
	Weapon() {
		 
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 4;
		//20+lootLevel*(randDmg.nextInt(80));
		wepMinDMG = lootLevel*20+randDmg.nextInt(20);
		wepMaxDMG = lootLevel*20+40+randDmg.nextInt(15);
		attackSpeed = 0.5f+2*lootLevel*(randSpeed.nextFloat());
		

	}
	
	Weapon(Enemy _enemy) {
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 4;
	}
	
public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		weaponTestSprite = new Image("data/weaponTestSprite.png");
	}
	
public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		weaponTestSprite.draw(vector.getX()-32, vector.getY()-32);
	
	g.drawString("lvl:" + lootLevel, vector.getX(), vector.getY()-32);
	
}

}
