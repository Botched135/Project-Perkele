package example;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class RangedWeapon extends Loot {

	private Image rangedWeaponSprite = null;
	RangedWeapon(){
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 5;
		Name = "Bow-ner";
		wepMinDMG = 20;
		wepMaxDMG = 40;
		attackSpeed = 0.5f;
		
	}
	RangedWeapon(Enemy _enemy){
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 5;
		
		this.lootLevel = _enemy.EnemyLevel;
		this.Health = this.lootLevel;
		this.wepMinDMG = _enemy.EnemyLevel*20+randDmg.nextInt(21);
		this.wepMaxDMG = _enemy.EnemyLevel*20+40+randDmg.nextInt(16);
		this.attackSpeed = 0.3f*_enemy.EnemyLevel+(randSpeed.nextFloat());
		this.Name = this.setName();
		
	}
public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		//rangedWeaponSprite = new Image("data/weaponTestSprite.png");
	}
	
public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	
	if(beingHit == true){
		//rangedWeaponSprite.drawFlash(vector.getX()-32, vector.getY()-32);
	}
	else{
		//rangedWeaponSprite.draw(vector.getX()-32, vector.getY()-32);
	}
	
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			g.drawString(this.Name+" "+"lvl:" + this.lootLevel, vector.getX()-23, vector.getY()-60);
		}
	
}
	public String setName(){
		String Name="";
		return Name;
	}
}
