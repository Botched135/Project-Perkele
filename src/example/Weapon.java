package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Weapon extends Loot{
	
	private Image meleeWeaponTestSprite = null;
	//protected int wepDMG = (randDmg.nextInt(100))*LootLevel;
	//protected float attackSpeed = (randSpeed.nextFloat())*2*LootLevel;
	
	Weapon() {
		 
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 4;
		Name = "Smelly Stick";
		wepMinDMG = 75;
		wepMaxDMG = 125;
		attackSpeed = 5;
		Vamp = 20;
		numberOfStats=4;
		
		//20+lootLevel*(randDmg.nextInt(80));
		//wepMinDMG = lootLevel*20+randDmg.nextInt(20);
		//wepMaxDMG = lootLevel*20+40+randDmg.nextInt(15);
		//attackSpeed = 0.5f+2*lootLevel*(randSpeed.nextFloat());
		

	}
	
	Weapon(Enemy _enemy) {
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 4;
		
		this.lootLevel = _enemy.EnemyLevel;
		this.wepMinDMG = _enemy.EnemyLevel*20+randDmg.nextInt(21);
		this.wepMaxDMG = _enemy.EnemyLevel*20+40+randDmg.nextInt(16);
		this.attackSpeed = 0.3f*_enemy.EnemyLevel+(randSpeed.nextFloat());
		this.isVamp = randVamp.nextInt(101);
		if(isVamp > 80 ){
			Vamp = 2.5f*_enemy.EnemyLevel*(randDmg.nextFloat()+0.5f);
			this.numberOfStats+=1;
		}
		this.Name = this.setName();
	}
	
public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		meleeWeaponTestSprite = new Image("data/meleeWeaponTestSprite.png");
	}
	
public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	
	if(beingHit == true){
		meleeWeaponTestSprite.drawFlash(vector.getX()-32, vector.getY()-32);
	}
	else{
		meleeWeaponTestSprite.draw(vector.getX()-32, vector.getY()-32);
	}
	
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			g.drawString(this.Name+" "+"lvl:" + this.lootLevel, vector.getX()-23, vector.getY()-60);
		}
	
}
public String setName(){
	String Name = ""; 
	if(this.attackSpeed <= 0.5){
		Name += "Slow ";
	}
	else if(this.attackSpeed >= 2 ){
		Name+="Speedy ";
	}
	if(Vamp > 0){
		Name+="Vampiric";
	}
	if(this.wepMinDMG > (this.lootLevel*20+15) && this.wepMaxDMG >(this.lootLevel*20+50)){
		Name="Andúril, Flame of the West";
		return Name;
	}
	if(this.wepMinDMG < (this.lootLevel*20+10)){
		Name+="Dagger ";
	}
	else if(this.wepMinDMG >= (this.lootLevel*20+10) && this.wepMinDMG <= (this.lootLevel*20+15)){
		Name+="Broad Sword ";
	}
	else if(this.wepMinDMG > (this.lootLevel*20+15)){
		Name+="Zweihander ";
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
