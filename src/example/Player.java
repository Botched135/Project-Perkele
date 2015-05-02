package example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Player extends GameObject{
	
	//VARIABLE DECLARATION ===========================================================================================================================================================
	//PLAYER STATS ===========================================
	protected float hitPoints = 100;
	protected float baseHp = 100;
	protected float MaxHitPoints = 100;
	
	protected float speedMultiplier = 5.0f;
	
	//Melee
	protected float playerMeleeMinDamage;
	protected float playerMeleeMaxDamage ;
	protected float playerMeleeDamage;
	protected float meleeRange = 100;
	protected float AttackSpeed = 5.0f; //Attacks per second
	protected float lifeRegen = 0.2f;
	protected float playerMeleeAttackSpeed = 5.0f; //Attacks per second
	protected int meleeWepID;
	
	//Ranged
	protected float playerRangedMinDamage;
	protected float playerRangedMaxDamage;
	protected float playerRangedAttackSpeed;
	protected float rangedDamage;
	protected int rangedWepID;
	protected float projectileSpeed = 12;
	
	//Armor
	protected float Armor = 0; //Damage reductions
	protected int armorID;
	
	//=======================================================
	
	protected boolean beingHit = false;
	protected float isReady;
	protected boolean isAttackReady = false;
	protected long StartTime = System.currentTimeMillis();
	protected long EndTime = 0;
	
	protected boolean isMeleeAttacking;
	protected boolean isRangedAttacking;
	protected static Color playerTestCol = new Color(0,0,255);
	
	protected Random randDmg = new Random();
	
	//Variables for lifeRegen timer
	protected long regSTimer = 0;
	protected long regETimer = 0;
	protected int regWTime = 1000;
	
	//Images =================================================
	
	private Image playerTestSprite = null; 
	private Image hpBar = null; 
	
	//Sounds =================================================
	
	private Sound meleeAttackSound0 = null;
	private Sound rangedAttackSound0 = null;
	protected Sound meleeHitSound = null;
	protected Sound rangedHitSound = null;
	
	
	//Misc. ==================================================	
	
	DecimalFormat df = new DecimalFormat("#.##");
	
	//CONSTRUCTORS ===========================================================================================================================================================
	Player() {
		
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 1;
	
	}
	
	Player(Vector2f _vector) {
		 
		super(_vector);
		
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 1;
	}
	
public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
	
		playerTestSprite = new Image("data/player.png");
		hpBar = new Image("data/hpBar.png");
		
		meleeAttackSound0 = new Sound("data/meleeAttackSound0.ogg");
		rangedAttackSound0 = new Sound("data/rangedAttackSound0.ogg");
		meleeHitSound = new Sound("data/meleeHitSound1.ogg");
		rangedHitSound = new Sound("data/meleeHitSound1.ogg");
		
	}
	
	//UPDATE FUNCTION/METHOD ===========================================================================================================================================================
	public void update(GameContainer gc, StateBasedGame sbg, ArrayList<Enemy> _enemyList, ArrayList<Projectile> _projectileList, ArrayList<healthGlobe> _healthGlobeList) throws SlickException{
		
		//Keeping HP from exceeding max hp.
		if(hitPoints > MaxHitPoints){
			hitPoints = MaxHitPoints;
		}
		else if(hitPoints < MaxHitPoints){
			regeneration();
		}

		
		isMeleeAttacking = false;
		isRangedAttacking = false;

		//UPDATE PLAYER ATTACK
		setAttackReady();
		for(int i = _enemyList.size()-1; i >= 0; i --) {
		beingMeleeAttacked(_enemyList.get(i));
		}
		
		beingRangedAttacked(_projectileList);

		if(gc.getInput().isKeyPressed(Input.KEY_O)){
			if(this.hitPoints <= 0){
				this.hitPoints = 0;
			}
			else{
				this.hitPoints -= 10;
			}
		}
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			isMeleeAttacking(GameState.mousePos);
		}
		
		//PLAYER SHOOTS ARROW TOWARDS "mousePos"
		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
				isRangedAttacking(gc, sbg, GameState.mousePos, _projectileList);
		}
		
		//healthGlobe pickup by player
		if(this.hitPoints < this.MaxHitPoints){
			for(int i = _healthGlobeList.size()-1; i >= 0 ; i--){
				if(vector.distance(_healthGlobeList.get(i).vector) < hitboxX + _healthGlobeList.get(i).hitboxX)
					if(this.hitPoints + 20 > this.MaxHitPoints){
						this.hitPoints = MaxHitPoints;
						_healthGlobeList.remove(i);
					}
					else{
						
					this.hitPoints += 20;
					_healthGlobeList.remove(i);
				}
			}
		}
		
		//PLAYER MOVEMENT INPUT
		
		if(gc.getInput().isKeyDown(Input.KEY_A)) {
			MoveSelf(new Vector2f(-1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_W)) {
			MoveSelf(new Vector2f(0f, -1.0f));
			}
		if(gc.getInput().isKeyDown(Input.KEY_D)) {
			MoveSelf(new Vector2f(1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_S)) {
			MoveSelf(new Vector2f(0.0f, 1.0f));
		}
	}
	
	//RENDER METHOD =====================================================================================================================================================
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.translate((-vector.getX())+(Window.WIDTH/2), (-vector.getY())+(Window.HEIGHT/2));
		
		playerTestSprite.draw(vector.getX()-32, vector.getY()-32);
		
		g.translate((vector.getX())-(Window.WIDTH/2), (vector.getY())-(Window.HEIGHT/2));
		hpBar.draw(Inventory.xOrigin+453, Inventory.yOrigin+647, 378*(this.hitPoints/this.MaxHitPoints), 43);
		//hpBar.draw(Inventory.xOrigin+453, Inventory.yOrigin+647, 1); // <----- Change the "1" to make the HP Bar resize according to remaining player health!
		g.drawString(df.format((int)this.hitPoints), Inventory.xOrigin+628, Inventory.yOrigin+659);
		
	}
	
	
	//METHODS ===========================================================================================================================================================
	public void MoveSelf(Vector2f _target){
		
		_target = _target.add(vector);
		
		Vector2f dir = _target.sub(vector);
		
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir); 
	}
	
	public void isMeleeAttacking(Vector2f vector){
		if(this.isAttackReady){			
			//Play meleeEnemy's melee attack sound 
			meleeAttackSound0.play();
			this.isMeleeAttacking = true;
			isAttackReady=false;
		}
		else
			this.isMeleeAttacking = false;
	}
	
	public void isRangedAttacking(GameContainer gc, StateBasedGame sbg, Vector2f vector, ArrayList<Projectile> _projectileList) throws SlickException{
		if(this.isAttackReady){			
			//Play players ranged attack sound
			rangedAttackSound0.play();
			this.isRangedAttacking = true;
			isAttackReady=false;
			
			_projectileList.add(new Arrow(this, GameState.mousePos, projectileSpeed));
			_projectileList.get(_projectileList.size()-1).init(gc, sbg);
		}
		else
			this.isRangedAttacking = false;
	}
	
	public boolean setAttackReady(){//End time - StartTime = CD. If CD >= 1000 then move on 
		if(this.isAttackReady == false){ 
			this.EndTime = System.currentTimeMillis();//StartTime should start from without
			if((this.EndTime-this.StartTime) >= 1000/playerMeleeAttackSpeed){
				this.StartTime = this.EndTime;
				this.isAttackReady=true;//set the isAttackReady to true
				//this.EndTime = 0;
			}
		}
		else if(this.isAttackReady){
			StartTime = System.currentTimeMillis();
		}

		return isAttackReady;
	}
	
	void beingMeleeAttacked (Enemy _enemy){
		
		if(_enemy.isMeleeAttacking && vector.distance(_enemy.vector) < _enemy.range + _enemy.hitboxX){
			_enemy.isMeleeAttacking = false;
			_enemy.AttackDamage();
			
			//Play players being melee hit sound
			meleeHitSound.play();

			this.hitPoints -= _enemy.enemyDamage - ((_enemy.enemyDamage / 100) * this.Armor); //(nextFloat()*(_player.MaxDamage-_player.MinDamage))+_player.MinDamage;
			if(this.hitPoints <0){
				this.hitPoints=0;
			}
		}
	}
	
	//Method to check if the player is being hit by a ranged attack
	void beingRangedAttacked (ArrayList<Projectile> _projectileList){
		if(_projectileList.size() > 0){
			for(int i = _projectileList.size()-1; i >= 0; i--){
				if(_projectileList.get(i).owner instanceof Enemy && _projectileList.get(i).disableDmg == false && vector.distance(_projectileList.get(i).vector) < hitboxX + _projectileList.get(i).hitboxX){
				
					//Play players being ranged hit sound
					meleeHitSound.play();
						
					//Sets "beingHit" to true -> used to make the sprite blink on taking damage (used in the render method)
					beingHit = true;
						
					if(this.hitPoints - _projectileList.get(i).damage - ((_projectileList.get(i).damage / 100) * this.Armor) < 0){
						this.hitPoints = 0;
						_projectileList.get(i).disableDmg = true;
						_projectileList.get(i).destroy(i, _projectileList);
					}
					else{
					this.hitPoints -= _projectileList.get(i).damage - ((_projectileList.get(i).damage / 100) * this.Armor);
					_projectileList.get(i).disableDmg = true;
					_projectileList.get(i).destroy(i, _projectileList);
					}
				}
			}
		}
	}
		
	public void regeneration(){
		if(regSTimer == 0){
			regSTimer = System.currentTimeMillis();
		}
		else {
			regETimer = System.currentTimeMillis() - regSTimer;
			if(regETimer >= 1000){
				this.hitPoints += lifeRegen;
				regSTimer = 0;
				regETimer = 0;
			}
		}
	}
	
	//Setting the weapon loot to the player
	public void setLootEquipment(Loot loot){
		if(loot instanceof Weapon){
		this.playerMeleeMinDamage = loot.wepMinDMG;
		this.playerMeleeMaxDamage = loot.wepMaxDMG;
		this.playerMeleeAttackSpeed = loot.attackSpeed;
		this.meleeWepID = loot.ID;
		}
		else if(loot instanceof RangedWeapon){
			this.playerRangedMinDamage = loot.wepMinDMG;
			this.playerRangedMaxDamage= loot.wepMaxDMG;
			this.playerRangedAttackSpeed = loot.attackSpeed;
			this.rangedWepID = loot.ID;
		}
		else if(loot instanceof Armor){
			this.Armor = loot.Armor;
			this.lifeRegen = loot.lifeRegen;
			if(hitPoints > baseHp + loot.hpBonus){
				hitPoints = baseHp + loot.hpBonus;
			}
			else{
			this.MaxHitPoints = baseHp + loot.hpBonus;
			}
			this.armorID = loot.ID;
		}
	}
	public void AttackDamage(){
		playerMeleeDamage = (randDmg.nextFloat()*(this.playerMeleeMaxDamage-this.playerMeleeMinDamage))+this.playerMeleeMinDamage;
	}
	public void RangedAttackDamage(){
		rangedDamage = (randDmg.nextFloat()*(this.playerRangedMaxDamage-this.playerRangedMinDamage))+this.playerRangedMinDamage;
	}
}
