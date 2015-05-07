package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Armor extends Loot{
	
	protected String[]ArmorNames = {"Leather Armor","Scalemail","Breast Plate","Full Plate","Dragonbone Armor"};
	private Image[] armorSprite = new Image[6];
	private int spriteRenderID = 0;
	
	Armor() {
		 
		super();
		//Start values for the armor you have when the game starts
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 3;
		
		//Setting the starting item of this type's stats.
		Name = "Slighly used Loincloth";
		Armor = 50;
		hpBonus = 0;
		lifeRegen = 0.0f;
		lootLevel = 0;
		Armor = 1;
		numberOfStats = 1;
	}
	
	Armor(Enemy _enemy) {
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 3;
		
		//Setting the items stats depending on the level of the enemy which dropped it.
		this.lootLevel = _enemy.EnemyLevel;
		
		if(this.lootLevel > 5){
			spriteRenderID = 5;
		}
		else{
			spriteRenderID = this.lootLevel;
		}
		
		this.Health = this.lootLevel;

		if(_enemy.EnemyLevel > 5){
			this.Armor = (5 * 10) + (randArmor.nextInt(11)-5);
		}
		else{
			this.Armor = (_enemy.EnemyLevel * 10) + (randArmor.nextInt(11)-5);
		}
		this.hpBonus = (_enemy.EnemyLevel * 10) + (randArmor.nextInt(11)-5);
		this.isLifeRegen = randlifeRegen.nextInt(101);
		if(isLifeRegen > 50){
			this.lifeRegen = ((float)_enemy.EnemyLevel / 5) + (((float)randlifeRegen.nextInt(3)-1)/10);
		}
		this.Name = this.setName(ArmorNames);
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		armorSprite[0] = new Image("data/armorSprite0.png");
		armorSprite[1] = new Image("data/armorSprite1.png");
		armorSprite[2] = new Image("data/armorSprite2.png");
		armorSprite[3] = new Image("data/armorSprite3.png");
		armorSprite[4] = new Image("data/armorSprite4.png");
		armorSprite[5] = new Image("data/armorSprite5.png");
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
	
		//Displaying either the normal sprite or a "flash" (white color) filled version of it, depending on whether the object is hit.
	
		if(this.beingHit == true){
		armorSprite[spriteRenderID].drawFlash(vector.getX()-32, vector.getY()-32);
		}
		else{
			armorSprite[spriteRenderID].draw(vector.getX()-32, vector.getY()-32);
		}
		//Display the name of the item if the "shift" key is hold
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			g.drawString(this.Name, vector.getX()-23, vector.getY()-60);
		}
	
	}
	
	/**
	 * Method used for giving armor names based on lootLevel and their attributes
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
		if(this.hpBonus <=7*this.lootLevel){
			setName += " of Thorns";
		}
		else if(this.hpBonus>10*this.lootLevel){
			setName +=", Ryge Incarnated";
		}
		return setName;
	}

}
