package example;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Weapon extends Loot{
	
	private Image[] meleeWeaponSprite = new Image[6];
	private int spriteRenderID = 0;
	protected String[]WeaponNames ={"Dagger ","Shortsword ","Warhammer ","Zweihander ","Demon-Infused Sword "};
	//protected int wepDMG = (randDmg.nextInt(100))*LootLevel;
	//protected float attackSpeed = (randSpeed.nextFloat())*2*LootLevel;
	
	//CONSTRUCTORS ======================================================================================================================================================
	Weapon() {//Constructor for initial weapon of the player
		 
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 4;
		lootLevel = 0;
		
		//Setting the starting item of this type's stats.
		Name = "Smelly Stick";
		wepMinDMG = 10;
		wepMaxDMG = 25;
		attackSpeed = 0.8f;
		Vamp = 0;
		numberOfStats=4;
	}
	
	Weapon(Enemy _enemy) {//Constructor for enemy dropped weapons.
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 4;
		
		//Setting the items stats depending on the level of the enemy which dropped it.
		this.lootLevel = _enemy.EnemyLevel;
		this.wepMinDMG = _enemy.EnemyLevel*20+randDmg.nextInt(21);
		this.wepMaxDMG = _enemy.EnemyLevel*20+40+randDmg.nextInt(16);
		this.attackSpeed = 0.3f*_enemy.EnemyLevel+(randSpeed.nextFloat());
		this.isVamp = randVamp.nextInt(101);
		if(isVamp >= 60 ){
			Vamp = 2.5f*_enemy.EnemyLevel*(randVamp.nextFloat()+0.5f);
			this.numberOfStats+=1;
		}
		
		if(this.lootLevel > 5){
			spriteRenderID = 5;
		}
		else{
			spriteRenderID = this.lootLevel;
		}
		
		this.Name = this.setName(WeaponNames);
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {//Initialise the sprite used to display the weapon		
		
	meleeWeaponSprite[0] = new Image("data/meleeWeapon0.png");
	meleeWeaponSprite[1] = new Image("data/meleeWeapon1.png");
	meleeWeaponSprite[2] = new Image("data/meleeWeapon2.png");
	meleeWeaponSprite[3] = new Image("data/meleeWeapon3.png");
	meleeWeaponSprite[4] = new Image("data/meleeWeapon4.png");
	meleeWeaponSprite[5] = new Image("data/meleeWeapon5.png");
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {//Render the name, and the flashing animation when the weapon gets attacked
		

		//Displaying either the normal sprite or a "flash" (white color) filled version of it, depending on whether the object is hit.
		if(beingHit == true){
			meleeWeaponSprite[spriteRenderID].drawFlash(vector.getX()-32, vector.getY()-32);
		}
		else{
			meleeWeaponSprite[spriteRenderID].draw(vector.getX()-32, vector.getY()-32);
		}
		//Display the name of the item if the "shift" key is hold
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			g.setColor(new Color(0, 0, 0));
			g.drawString(this.Name, vector.getX()-23, vector.getY()-60);
		}
	}
	
	/**
	 * Method used for giving weapon names based on lootLevel and their attributes
	 * @param string is a array of weapon names
	 * @return returns the name of the weapon
	 */
	public String setName(String[] names){
		String Name = ""; 
		if(this.attackSpeed <= 0.5){
			Name += "Slow ";
		}
		else if(this.attackSpeed >= 2 ){
			Name+="Speedy ";
		}
		if(Vamp > 0){
			Name+="Vampiric ";
		}
		if(this.spriteRenderID-1 < 0)
			Name += names[0];
		else{
			Name += names[this.spriteRenderID-1];
		}
		if(this.wepMaxDMG < (this.lootLevel*20+47)){
			Name+="of the Weak";
		}
		else if(this.wepMaxDMG >(this.lootLevel*20+47)){
			Name+="of the Bons";
		}
		return Name;
	}
}
