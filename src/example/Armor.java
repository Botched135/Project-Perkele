package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Armor extends Loot{
	
	private Image armorTestSprite = null;
	//protected float hpBonus = 10;
	
	Armor() {
		 
		super();
		//Start values for the armor you have when the game starts
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 3;
		Name = "Slighly used Loincloth";
		Armor = 50;
		numberOfStats = 1;
	}
	
	Armor(Enemy _enemy) {
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 3;
		this.lootLevel = _enemy.EnemyLevel;
		if(_enemy.EnemyLevel > 5){
			this.Armor = (5 * 10) + (randArmor.nextInt(11)-5);
		}
		else{
			this.Armor = (_enemy.EnemyLevel * 10) + (randArmor.nextInt(11)-5);
		}
		this.hpBonus = (_enemy.EnemyLevel * 10) + (randArmor.nextInt(11)-5);
		this.lifeRegen = ((float)_enemy.EnemyLevel / 5) + (((float)randlifeRegen.nextInt(3)-1)/10);
		this.Name = this.setName(ArmorNames);
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		armorTestSprite = new Image("data/armorTestSprite.png");
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		if(this.beingHit == true){
			armorTestSprite.drawFlash(vector.getX()-32, vector.getY()-32);
		}
		else{
			armorTestSprite.draw(vector.getX()-32, vector.getY()-32);
		}
		
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			g.drawString(this.Name+" lvl:" + this.lootLevel, vector.getX()-23, vector.getY()-60);
		}
	
	}
	
	/**
	 * Method used for giving armor names based on lootLevel
	 * @param string is a array of armor names
	 * @return returns the name of the armor
	 */
	public String setName(String[] string){
		String setName;
		if(this.lootLevel-1 > 4){
			setName = string[4]; 
		}
		else{
			setName = string[this.lootLevel-1]; 
		}
		return setName;
	}

}
