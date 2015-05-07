package example;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RangedWeapon extends Loot {

	//VARIABLE DECLARATION ============================================================================================================================================
	private Image[] rangedWeaponSprite = new Image[6];
	private String[] rangedWeaponNames = {"Shortbow ","Crossbow ", "Compoundbow ","Repeater Crossbow ", "Eaglehorn "}; 
	private int spriteRenderID = 0;
	
	RangedWeapon(){ //Constructor for initial ranged weapon of the player
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 5;
		lootLevel = 0;
		
		//Setting the starting item of this type's stats.
		Name = "Stick with String";
		wepMinDMG = 100;
		wepMaxDMG = 200;
		attackSpeed = 5.8f;
		numberOfStats = 3;
		
	}
	RangedWeapon(Enemy _enemy){//Constructor for enemy dropped ranged weapon
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		numberOfStats =3;
		ID = 5;
		
		
		
		//Setting the items stats depending on the level of the enemy which dropped it.
		this.lootLevel = _enemy.EnemyLevel;
		this.wepMinDMG = _enemy.EnemyLevel*20+randDmg.nextInt(21);
		this.wepMaxDMG = _enemy.EnemyLevel*20+40+randDmg.nextInt(16);
		this.attackSpeed = 0.3f*_enemy.EnemyLevel+(randSpeed.nextFloat());
		this.Name = this.setName(rangedWeaponNames);
		
		if(this.lootLevel > 5){
			spriteRenderID = 5;
		}
		else{
			spriteRenderID = this.lootLevel;
		}
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
	rangedWeaponSprite[0] = new Image("data/rangedWeapon0.png");
	rangedWeaponSprite[1] = new Image("data/rangedWeapon1.png");
	rangedWeaponSprite[2] = new Image("data/rangedWeapon2.png");
	rangedWeaponSprite[3] = new Image("data/rangedWeapon3.png");
	rangedWeaponSprite[4] = new Image("data/rangedWeapon4.png");
	rangedWeaponSprite[5] = new Image("data/rangedWeapon5.png");
	
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		

		//Displaying either the normal sprite or a "flash" (white color) filled version of it, depending on whether the object is hit.
		if(beingHit == true){
			rangedWeaponSprite[spriteRenderID].drawFlash(vector.getX()-32, vector.getY()-32);
		}
	
		else{
			rangedWeaponSprite[spriteRenderID].draw(vector.getX()-32, vector.getY()-32);
		}
	
		//Display the name of the item if the "shift" key is hold
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			g.drawString(this.Name+" "+"lvl:" + this.lootLevel, vector.getX()-23, vector.getY()-60);
		}
	
	}

	/**
	 * Method used for giving ranged weapon names based on lootLevel and their attributes
	 * @param string is a array of ranged weapon names
	 * @return returns the name of the weapon
	 */

	private String setName(String[] names){
		String Name="";
		if(this.attackSpeed <= 0.5){
			Name += "Rusty ";
		}
		else if(this.attackSpeed >= 2 ){
			Name+="Automated ";
		}
		
		Name+=names[this.lootLevel-1];
		
		if(this.wepMaxDMG < (this.lootLevel*20+47)){
			Name+="made of Glulam";
		}
		else if(this.wepMaxDMG >(this.lootLevel*20+47)){
			Name+="blessed by Holmen";
		}
		return Name;
	}
}
