package example;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RangedWeapon extends Loot {
    //VARIABLE DECLARATION =============================================================================================================================================
	private Image rangedWeaponSprite = null;
	
	//CONSTRUCTORS ======================================================================================================================================================
	RangedWeapon(){//Constructor for initial ranged weapon of the player
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 5;
		Name = "Bow-ner";
		wepMinDMG = 80;
		wepMaxDMG = 120;
		attackSpeed = 5f;
		
	}
	RangedWeapon(Enemy _enemy){//Constructor for enemy dropped ranged weapon
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 5;
		
		this.lootLevel = _enemy.EnemyLevel;
		this.wepMinDMG = _enemy.EnemyLevel*20+randDmg.nextInt(21);
		this.wepMaxDMG = _enemy.EnemyLevel*20+40+randDmg.nextInt(16);
		this.attackSpeed = 0.3f*_enemy.EnemyLevel+(randSpeed.nextFloat());
		this.Name = this.setName();
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		rangedWeaponSprite = new Image("data/rangedWeaponTestSprite.png");
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		if(beingHit == true){
			rangedWeaponSprite.drawFlash(vector.getX()-32, vector.getY()-32);
		}
		else{
			rangedWeaponSprite.draw(vector.getX()-32, vector.getY()-32);
		}
	
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			g.drawString(this.Name+" "+"lvl:" + this.lootLevel, vector.getX()-23, vector.getY()-60);
		}
	
	}
	public String setName(){
		String Name="";
		if(this.attackSpeed <= 0.5){
			Name += "Rusty ";
		}
		else if(this.attackSpeed >= 2 ){
			Name+="Automated ";
		}
		if(this.wepMinDMG < (this.lootLevel*20+10)){
			Name+="Shortbow ";
		}
		else if(this.wepMinDMG >= (this.lootLevel*20+10) && this.wepMinDMG <= (this.lootLevel*20+15)){
			Name+="Longbow ";
		}
		else if(this.wepMinDMG > (this.lootLevel*20+15)){
			Name+="Crossbow ";
		}
		if(this.wepMaxDMG < (this.lootLevel*20+47)){
			Name+="made of Cotton";
		}
		else if(this.wepMaxDMG >(this.lootLevel*20+47)){
			Name+="blessed by Holmen";
		}
		return Name;
	}
}
