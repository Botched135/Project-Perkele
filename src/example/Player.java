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
	private boolean beingHit = false;
	protected static Color playerTestCol = new Color(0,0,255);
	private DecimalFormat df = new DecimalFormat("#.##");
	
	//Player offensive stats ==============================================================================================================================
	protected float playerMeleeMinDamage;
	protected float playerMeleeMaxDamage ;
	protected float playerMeleeDamage;
	protected float meleeRange = 100;
	protected float AttackSpeed = 5.0f; //Attacks per second
	protected float lifeRegen = 0.2f;
	protected float playerMeleeAttackSpeed = 5.0f; //Attacks per second
	protected static int meleeWepID = 0;
	protected float playerVamp = 0;
	
	//Ranged
	protected float playerRangedMinDamage;
	protected float playerRangedMaxDamage;
	protected float playerRangedAttackSpeed;
	protected float rangedDamage;
	protected static int rangedWepID = 0;
	protected float projectileSpeed = 12;
	protected boolean isMeleeAttacking;
	protected boolean isRangedAttacking;
	private Random randDmg = new Random();
	
	//Attack cooldown variables
	protected float isReady;
	protected boolean isAttackReady = false;
	private long StartTime = System.currentTimeMillis();
	private long EndTime = 0;
	
	protected boolean isRangedReady = false;
	private long rangeStartTime = System.currentTimeMillis();
	private long rangeEndTime = 0;
	
	//Player defensive stats ==============================================================================================================================
	protected float Armor = 0; //Damage reductions
	protected static int armorID = 0;
	protected float hitPoints = 100;
	protected float baseHp = 100;
	protected float MaxHitPoints = 100;
	//Variables for lifeRegen timer
	private long regSTimer = 0;
	private long regETimer = 0;
	private int regWTime = 1000;
	
	//Player movement variables ===========================================================================================================================
	protected float speedMultiplier = 3.5f;
	
	//variables used for stopping movement when ranged attacking =====================================================

	
	//Images =================================================
	private int imageDirection = 0;
	private Image hpBar = null; 
	private Image arrow = null;
	private Image[]playerBaseSprite = new Image[2];
	private Image[] playerEquippedMeleeWepList = new Image[6];
	private Image[] playerEquippedRangedWepList = new Image[6];
	private Image[][] playerEquippedArmorList = new Image[6][2];
	
	//Variables for animations of weapons
	private float moveY = 0;
	private float maxMoveY = 32;
	private float moveYIncrement = 2*maxMoveY/(AttackSpeed);
	private float spriteAngle = 0;
	
	//Sounds =================================================
	private Sound meleeAttackSound0 = null;
	private Sound rangedAttackSound0 = null;
	private Sound meleeHitSound = null;
	private Sound rangedHitSound = null;
	
	
	
	
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
		
	
		playerBaseSprite[0] = new Image("data/playerBaseSpriteUp.png"); 		//index 0
		playerBaseSprite[1] = new Image("data/playerBaseSpriteDown.png");		//index 1
		arrow = new Image("data/arrowSprite.png");
		hpBar = new Image("data/hpBar.png");
		
		playerEquippedMeleeWepList[0] = new Image("data/meleeWepEquip0.png");
		playerEquippedMeleeWepList[1] = new Image("data/meleeWepEquip1.png");
		playerEquippedMeleeWepList[2] = new Image("data/meleeWepEquip2.png");
		playerEquippedMeleeWepList[3] = new Image("data/meleeWepEquip3.png");
		playerEquippedMeleeWepList[4] = new Image("data/meleeWepEquip4.png");
		playerEquippedMeleeWepList[5] = new Image("data/meleeWepEquip5.png");
		
		playerEquippedRangedWepList[0] = new Image("data/rangedWepEquip0.png");
		playerEquippedRangedWepList[1] = new Image("data/rangedWepEquip1.png");
		playerEquippedRangedWepList[2] = new Image("data/rangedWepEquip2.png");
		playerEquippedRangedWepList[3] = new Image("data/rangedWepEquip3.png");
		playerEquippedRangedWepList[4] = new Image("data/rangedWepEquip4.png");
		playerEquippedRangedWepList[5] = new Image("data/rangedWepEquip5.png");
		
		playerEquippedArmorList[0][0] = new Image("data/playerEquipArmor0Up.png");
		playerEquippedArmorList[0][1] = new Image("data/playerEquipArmor0Down.png");
		playerEquippedArmorList[1][0] = new Image("data/playerEquipArmor1Up.png");
		playerEquippedArmorList[1][1] = new Image("data/playerEquipArmor1Down.png");
		playerEquippedArmorList[2][0] = new Image("data/playerEquipArmor2Up.png");
		playerEquippedArmorList[2][1] = new Image("data/playerEquipArmor2Down.png");
		playerEquippedArmorList[3][0] = new Image("data/playerEquipArmor3Up.png");
		playerEquippedArmorList[3][1] = new Image("data/playerEquipArmor3Down.png");
		playerEquippedArmorList[4][0] = new Image("data/playerEquipArmor4Up.png");
		playerEquippedArmorList[4][1] = new Image("data/playerEquipArmor4Down.png");
		playerEquippedArmorList[5][0] = new Image("data/playerEquipArmor5Up.png");
		playerEquippedArmorList[5][1] = new Image("data/playerEquipArmor5Down.png");
		
		
		meleeAttackSound0 = new Sound("data/meleeAttackSound0.ogg");
		rangedAttackSound0 = new Sound("data/rangedAttackSound0.ogg");
		meleeHitSound = new Sound("data/meleeHitSound1.ogg");
		rangedHitSound = new Sound("data/meleeHitSound1.ogg");
		
	}
	
	//UPDATE FUNCTION/METHOD ===========================================================================================================================================================
	public void update(GameContainer gc, StateBasedGame sbg, ArrayList<Enemy> _enemyList, ArrayList<Projectile> _projectileList, ArrayList<healthGlobe> _healthGlobeList) throws SlickException{
		
		//Keeping HP from exceeding max HP.
		if(hitPoints > MaxHitPoints){
			hitPoints = MaxHitPoints;
		}
		else if(hitPoints < MaxHitPoints){
			regeneration();
		}
		
		beingHit = false;
		isMeleeAttacking = false;
		isRangedAttacking = false;

		//UPDATE PLAYER ATTACK
		setAttackReady();
		setRangedAttackReady();
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
			if(GameState.wepSwap == false){
				//PLAYER MELEE ATTACKS TOWARDS "mousePos"
				isMeleeAttacking();
			}
			else{
				//PLAYER SHOOTS ARROW TOWARDS "mousePos"
				isRangedAttacking(gc, sbg, _projectileList);
			}
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
			MoveSelf(new Vector2f(-1.0f, 0f),0);
		}
		if(gc.getInput().isKeyDown(Input.KEY_W)) {
			MoveSelf(new Vector2f(0f, -1.0f),1);
			}
		if(gc.getInput().isKeyDown(Input.KEY_D)) {
			MoveSelf(new Vector2f(1.0f, 0f),2);
		}
		if(gc.getInput().isKeyDown(Input.KEY_S)) {
			MoveSelf(new Vector2f(0.0f, 1.0f),3);
		}
		
		if(vector.getY() > GameState.mousePos.getY()){
			imageDirection = 0;
		}
		else{
			imageDirection = 1;
		}
	}
	//RENDER METHOD =====================================================================================================================================================
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.translate((-vector.getX())+(Window.WIDTH/2), (-vector.getY())+(Window.HEIGHT/2));
		
		//Displaying either the normal sprite or a "flash" (white color) filled version of it, depending on whether the object is hit.
		if(beingHit == true){
			playerBaseSprite[imageDirection].drawFlash(vector.getX()-32, vector.getY()-32);
		}
		else{
			playerBaseSprite[imageDirection].draw(vector.getX()-32, vector.getY()-32);
		}
		
		//RENDER EQUIPPED WEAPON IN GAME SPACE ======================================================
		
		Vector2f dir = new Vector2f(0.0f, 0.0f);
		Vector2f tempTarget = new Vector2f(GameState.mousePos.getX(), GameState.mousePos.getY());
		
		dir = tempTarget.sub(vector);
		dir.normalise();
		
		spriteAngle = (float)dir.getTheta()+90;
		
		playerEquippedMeleeWepList[meleeWepID].setCenterOfRotation(32,96);
		playerEquippedMeleeWepList[meleeWepID].setRotation(spriteAngle);
		
		playerEquippedRangedWepList[rangedWepID].setCenterOfRotation(32,96);
		playerEquippedRangedWepList[rangedWepID].setRotation(spriteAngle);
		
		if(GameState.wepSwap == false && isAttackReady == false){
			moveY = 0;
			maxMoveY = 64;
			moveYIncrement = 3;
		}
		if(GameState.wepSwap == false && isAttackReady == false){
			moveY = moveY + moveYIncrement;
			
			if(moveY >= maxMoveY){
				moveYIncrement *= -1;
			}
			if(moveY < -(maxMoveY/3)){
				moveYIncrement *= -1;
			}
			
			playerEquippedMeleeWepList[0].setCenterOfRotation(32,96);
			if(GameState.mousePos.getX() < vector.getX()){
				playerEquippedMeleeWepList[meleeWepID].setRotation((spriteAngle-(maxMoveY/2)) + moveY );
			}
			else{
				playerEquippedMeleeWepList[meleeWepID].setRotation((spriteAngle+(maxMoveY/2)) - moveY );
			}
			playerEquippedMeleeWepList[meleeWepID].draw(vector.getX()-32, vector.getY()-96);
			
		}
		
		if(GameState.wepSwap == false){
			playerEquippedMeleeWepList[meleeWepID].draw(vector.getX()-32, vector.getY()-96);
		}
		else{
			playerEquippedRangedWepList[rangedWepID].draw(vector.getX()-32, vector.getY()-96);
			if(isRangedReady == true){
			
				arrow.setCenterOfRotation(16,64);
				arrow.setRotation(spriteAngle);
				arrow.draw(vector.getX()-16, vector.getY()-64);
			}
		}
		
		//RENDER EQUIPPED ARMOR IN GAME SPACE ======================================================
		
		if(GameState.mousePos.getY() < vector.getY()){
			playerEquippedArmorList[armorID][0].draw(vector.getX()-32, vector.getY()-32);
		}
		else{
			playerEquippedArmorList[armorID][1].draw(vector.getX()-32, vector.getY()-32);
		}
		
		
		g.translate((vector.getX())-(Window.WIDTH/2), (vector.getY())-(Window.HEIGHT/2));
		hpBar.draw(Inventory.xOrigin+453, Inventory.yOrigin+647, 378*(this.hitPoints/this.MaxHitPoints), 43);
		//hpBar.draw(Inventory.xOrigin+453, Inventory.yOrigin+647, 1); // <----- Change the "1" to make the HP Bar resize according to remaining player health!
		g.drawString(df.format((int)this.hitPoints), Inventory.xOrigin+628, Inventory.yOrigin+659);
		
	}
	
	
	//METHODS ===========================================================================================================================================================
	/**
	 * A method to move the player in a certain direction given by "WASD" inputs
	 * @param _target is the desired position
	 * @param direction is the direction the player is moving. 0=left, 1=up, 2=right and 3=down
	 */
	private void MoveSelf(Vector2f _target, int direction){
		Vector2f tempTarget = new Vector2f(_target.getX(), _target.getY());
		tempTarget = tempTarget.add(vector);
		if(direction == 0){
			if(	tempTarget.getX() > 0){
				tempTarget = _target.add(vector);
		
				Vector2f dir = tempTarget.sub(vector);
		
				dir.normalise();
				dir = dir.scale(speedMultiplier);	
				vector = vector.add(dir); 
			}
		}
		
		if(direction == 1){
			if(	tempTarget.getY() > 0){
				tempTarget = _target.add(vector);
		
				Vector2f dir = tempTarget.sub(vector);
		
				dir.normalise();
				dir = dir.scale(speedMultiplier);	
				vector = vector.add(dir); 
			}
		}
		
		if(direction == 2){
			if(	tempTarget.getX() < GameState.mapBoundWidth){
				tempTarget = _target.add(vector);
		
				Vector2f dir = tempTarget.sub(vector);
		
				dir.normalise();
				dir = dir.scale(speedMultiplier);	
				vector = vector.add(dir); 
			}
		}
		
		if(direction == 3){
			if(	tempTarget.getY() < GameState.mapBoundHeight){
				tempTarget = _target.add(vector);
		
				Vector2f dir = tempTarget.sub(vector);
		
				dir.normalise();
				dir = dir.scale(speedMultiplier);	
				vector = vector.add(dir); 
			}
		}
	}
	
	/**
	 * Method for detecting if the player is able to melee attack and will then let the player attack
	 */
	public void isMeleeAttacking(){
		if(this.isAttackReady && this.isRangedReady){			
			//Play meleeEnemy's melee attack sound 
			meleeAttackSound0.play();
			this.isMeleeAttacking = true;
			isAttackReady=false;
		}
		else
			this.isMeleeAttacking = false;
	}
	
	/**
	 * Method for detecting if the player is able to ranged attack and will then let the player shoot an arrow
	 * @param gc used to initialise the arrow in our GameContainer - Parameters default to slick2D init method
	 * @param sbg used to initialise the arrow in our GameContainer - Parameters default to slick2D init method
	 * @param _projectileList is used in order to add a new arrow to the ArrayList
	 * @throws SlickException
	 */
	public void isRangedAttacking(GameContainer gc, StateBasedGame sbg, ArrayList<Projectile> _projectileList) throws SlickException{
		if(this.isRangedReady && this.isAttackReady){
			this.speedMultiplier = 0.8f; //Movement is stopped by making the player's speed 0 for one second

			//Play players ranged attack sound
			rangedAttackSound0.play();
			this.isRangedAttacking = true;
			isRangedReady=false;
			
			_projectileList.add(new Arrow(this, GameState.mousePos, projectileSpeed));
			_projectileList.get(_projectileList.size()-1).init(gc, sbg);
		}
		else
			this.isRangedAttacking = false;
	}
	
	/**
	 * Method used to setting the Players melee attack ready
	 * @return isAttackReady for use, so we can attack again in isMeleeAttacking
	 */
	private boolean setAttackReady(){//End time - StartTime = CD. If CD >= 1000 then move on 
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
	/**
	 * Method used to setting the Players ranged attack ready - very similar to setAttackReady, but needs different variables in order to being able to shoot without waiting on melee cooldown
	 * @return isAttackReady for use, so we can attack again in isRangedReady
	 */
	private boolean setRangedAttackReady(){
		if(this.isRangedReady == false){
			this.rangeEndTime = System.currentTimeMillis();
			if((this.rangeEndTime-this.rangeStartTime)>= 1000/playerRangedAttackSpeed){
				this.rangeStartTime=this.rangeEndTime;
				this.isRangedReady = true;
				this.speedMultiplier = 3.5f;
			}
		}
		else if(this.isRangedReady){
			rangeStartTime = System.currentTimeMillis();
		}
		return isRangedReady;
	}
	
	/**
	 * Method used for detecting if the enemy is close enough to deal damage. Will deal damage if the enemy is close enough
	 * @param _enemy is used in order to get the enemy's position, damage values and vampiric amount
	 */
	private void beingMeleeAttacked (Enemy _enemy){
		
		if(_enemy.isMeleeAttacking && vector.distance(_enemy.vector) < _enemy.maxRange + _enemy.hitboxX && vector.distance(_enemy.vector) > _enemy.minRange){
			_enemy.isMeleeAttacking = false;
			_enemy.AttackDamage();
			
			//Play players being melee hit sound
			meleeHitSound.play();
			//Sets "beingHit" to true -> used to make the sprite blink on taking damage (used in the render method)
			beingHit = true;
			
			if(_enemy.enemyVamp > 0){
				_enemy.hitpoints +=_enemy.enemyVamp;
				if(_enemy.hitpoints>_enemy.maxHitpoints){
					_enemy.hitpoints=_enemy.maxHitpoints;
				}
			}
			this.hitPoints -= _enemy.enemyDamage - ((_enemy.enemyDamage / 100) * this.Armor); //(nextFloat()*(_player.MaxDamage-_player.MinDamage))+_player.MinDamage;
			if(this.hitPoints <0){
				this.hitPoints=0;
			}
		}
	}
	/**
	 * Method to check if the player is being hit by a ranged attack
	 * @param _projectileList is used in order to destroy the arrow that hit the player. and get the damage that the arrow have
	 */
	private void beingRangedAttacked (ArrayList<Projectile> _projectileList){
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
	
	/**
	 * Method that uses a timer in order to give the player hitPoints based on their lifeRegen
	 */
	private void regeneration(){
		if(regSTimer == 0){
			regSTimer = System.currentTimeMillis();
		}
		else {
			regETimer = System.currentTimeMillis() - regSTimer;
			if(regETimer >= regWTime){
				this.hitPoints += lifeRegen;
				regSTimer = 0;
				regETimer = 0;
			}
		}
	}
	

	/**
	 * giving the picked up loot stats to the player
	 * @param loot is used to tell which item is used, and in order to get the stats from this loot
	 */
	public void setLootEquipment(Loot loot){
		if(loot instanceof Weapon){
			this.playerMeleeMinDamage = loot.wepMinDMG;
			this.playerMeleeMaxDamage = loot.wepMaxDMG;
			this.playerMeleeAttackSpeed = loot.attackSpeed;
			this.playerVamp = loot.Vamp;
			this.meleeWepID = loot.lootLevel;
			if(loot.lootLevel > 5) {
				this.meleeWepID = 5;
			}
			else{
				this.meleeWepID = loot.lootLevel;
			}
			this.playerVamp = loot.Vamp;

		}
		else if(loot instanceof RangedWeapon){
			this.playerRangedMinDamage = loot.wepMinDMG;
			this.playerRangedMaxDamage= loot.wepMaxDMG;
			this.playerRangedAttackSpeed = loot.attackSpeed;
			this.rangedWepID = loot.lootLevel;
			if(loot.lootLevel > 5) {
				this.rangedWepID = 5;
			}
			else{
				this.rangedWepID = loot.lootLevel;
			}
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
			//System.out.println(armorID);
			if(loot.lootLevel > 5) {
				this.armorID = 5;
			}
			else{
				this.armorID = loot.lootLevel;
			}
		}
	}
	
	/**
	 * Method for calculating the players melee damage
	 */
	public void AttackDamage(){
		playerMeleeDamage = (randDmg.nextFloat()*(this.playerMeleeMaxDamage-this.playerMeleeMinDamage))+this.playerMeleeMinDamage;
	}
	
	/**
	 * Method for calculating the players ranged damage
	 */
	public void RangedAttackDamage(){
		rangedDamage = (randDmg.nextFloat()*(this.playerRangedMaxDamage-this.playerRangedMinDamage))+this.playerRangedMinDamage;
	}
}
