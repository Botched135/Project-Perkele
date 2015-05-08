package example;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Enemy extends GameObject {
	
	//VARIABLE DECLARATION =====================================================================================
	public int enemyType; // 0 = melee, 1 = ranged.
	public int EnemyLevel;
	private Random randLvl = new Random();
	private String[]EnemyNames = new String[5];
	protected String EnemyName;
	private boolean beingHit = false;

	//Enemy offensive stats ===================================================================================
	protected String WeaponName;
	protected float AttackSpeed = 0.5f;
	protected float MinDamage = 2;
	protected float MaxDamage = 10;
	protected float enemyVamp;
	protected float enemyDamage;
	protected float rangedDamage;
	private Random randDmg = new Random();
	private float projectileSpeed;
	protected boolean isMeleeAttacking = false;
	protected boolean isRangedAttacking = false;
	//Attack cooldown variables
	private long StartTime = System.currentTimeMillis();
	private long EndTime = 0;
	protected boolean isAttackReady = false;
	protected int meleeWepID = 0;
	protected int rangedWepID = 0;
	long attackSTime = 0;
	long attackETime = 0;
	int moveWaitTime = 1000;
	
	//Enemy defensive stats ===================================================================================
	protected float hitpoints = 100;
	protected float maxHitpoints;
	private Random randEnemyHP = new Random();
	protected float Armor = 10;
	protected String ArmorName;
	
	//Enemy movement variables ================================================================================
	protected float maxRange;
	protected float minRange;
	protected float minSeekDistance;
	protected float maxSeekDistance;
	protected float speedMultiplier = 1.0f;
	
	//variables used for stopping movement when attacking =====================================================
	private boolean stopMoving = false;
	
	//Variables for animations of weapons =====================================================================
	private boolean vectorSnapshotted = false;
	private float moveY = 0;
	private float maxMoveY = 32;
	private float moveYIncrement = 2*maxMoveY/(AttackSpeed);
	private float spriteAngle = 0;
	
	//Images ==================================================================================================
	private int imageDirection = 0;
	protected Image[][] sprite = new Image[3][2];
	private Image arrow = null;
	private Image smallShadow = null;
	private Image bigShadow = null;
	private Image[][] bloodOverlay = new Image[3][2]; 
	private Image[] enemyEquippedMeleeWepList = new Image[6];
	private Image[] enemyEquippedRangedWepList = new Image[6];
	private Image blood;
	
	//Sounds ==================================================================================================
	private Sound meleeAttackSound0 = null;
	private Sound rangedAttackSound0 = null;
	private Sound meleeHitSound = null;
	protected Sound rangedHitSound = null;
	private Sound evilLaugh = null;
	private boolean evilLaughCheck = false;
	
	
		
	
	//CONTRUCTERS =============================================================================================
	
	Enemy(){
		super(); 
	}
	
	Enemy(Vector2f _vector, int _enemyType) {
		 
		super(_vector);
		
		enemyType = _enemyType;
		hitboxX = 32.0f;
		//hitboxY = 32.0f;		
	}
	
	//INIT FUNCTION/METHOD ====================================================================================
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		//Setting variables if a melee enemy
		
		if(enemyType == 0){
			AttackSpeed = 1;
			projectileSpeed = 0;
			minRange = 0;
			maxRange = 90;
			minSeekDistance = 0;
			maxSeekDistance = 500;
			meleeWepID = EnemyLevel;
			
			//Enemy melee names
			EnemyNames[0] = "Dwarf Grunt";
			EnemyNames[1] = "Dwarf Soldier";
			EnemyNames[2] = "Dwarf Veteran";
			EnemyNames[3] = "Dwarf Captain";
			EnemyNames[4] = "Dwarf Warchief";
		}
		
		//Setting variables if a ranged enemy
		if(enemyType == 1){
			AttackSpeed = 1;
			rangedDamage = 10;
			projectileSpeed = 12;
			maxRange = 400;
			minRange = 100;
			minSeekDistance = 200;
			maxSeekDistance = 500;
			rangedWepID = EnemyLevel;
			
			//Ranged enemy names
			EnemyNames[0] = "Elven Stakethrower";
			EnemyNames[1] = "Elven Archer";
			EnemyNames[2] = "Elven Marksmen";
			EnemyNames[3] = "Elven Sharpshooter";
			EnemyNames[4] = "Elven Trueshot";
		}
		
		//Setting variables if enemy is a boss
		if(enemyType == 2){
			hitboxX = 64.0f;
			AttackSpeed = 1;
			rangedDamage = 10;
			projectileSpeed = 12;
			maxRange = 90;
			minRange = 0;
			minSeekDistance = 0;
			maxSeekDistance = 500;
			meleeWepID = EnemyLevel;
			speedMultiplier = 2.0f;
			
			EnemyNames[0] = "Perkele, the Destroyer of Worlds";
		}
		
		
		
		sprite[0][0] = new Image("data/meleeEnemy1Up.png"); 	
		sprite[0][1] = new Image("data/meleeEnemy1Down.png");		 
		sprite[1][0] = new Image("data/rangedEnemy1Up.png");		 
		sprite[1][1] = new Image("data/rangedEnemy1Down.png");		
		sprite[2][0] = new Image("data/bossEnemy1Up.png"); 		
		sprite[2][1] = new Image("data/bossEnemy1Down.png");		
		arrow = new Image("data/arrowSprite.png");
		smallShadow = new Image("data/64PixShadow.png");
		bigShadow = new Image("data/128PixShadow.png");
		blood = new Image("data/rangedWeaponTestSprite.png");
		
		bloodOverlay[0][0] = new Image("data/smallBloodOverlayUp.png");
		bloodOverlay[0][1] = new Image("data/smallBloodOverlayDown.png");
		bloodOverlay[1][0] = new Image("data/smallBloodOverlayUp.png");
		bloodOverlay[1][1] = new Image("data/smallBloodOverlayDown.png");
		bloodOverlay[2][0] = new Image("data/bigBloodOverlayUp.png");
		bloodOverlay[2][1] = new Image("data/bigBloodOverlayDown.png");
	
		
		enemyEquippedMeleeWepList[0] = new Image("data/meleeWepEquip0.png");
		enemyEquippedMeleeWepList[1] = new Image("data/meleeWepEquip1.png");
		enemyEquippedMeleeWepList[2] = new Image("data/meleeWepEquip2.png");
		enemyEquippedMeleeWepList[3] = new Image("data/meleeWepEquip3.png");
		enemyEquippedMeleeWepList[4] = new Image("data/meleeWepEquip4.png");
		enemyEquippedMeleeWepList[5] = new Image("data/meleeWepEquip5.png");
		
		enemyEquippedRangedWepList[0] = new Image("data/rangedWepEquip0.png");
		enemyEquippedRangedWepList[1] = new Image("data/rangedWepEquip1.png");
		enemyEquippedRangedWepList[2] = new Image("data/rangedWepEquip2.png");
		enemyEquippedRangedWepList[3] = new Image("data/rangedWepEquip3.png");
		enemyEquippedRangedWepList[4] = new Image("data/rangedWepEquip4.png");
		enemyEquippedRangedWepList[5] = new Image("data/rangedWepEquip5.png");
		
		meleeAttackSound0 = new Sound("data/meleeAttackSound0.ogg");
		rangedAttackSound0 = new Sound("data/rangedAttackSound0.ogg");
		meleeHitSound = new Sound("data/meleeHitSound1.ogg");
		rangedHitSound = new Sound("data/meleeHitSound1.ogg");
		evilLaugh = new Sound("data/evilLaugh.wav");
		
		if(enemyType == 2){
			evilLaugh.play();
		}
	
	}
	
	//UPDATE FUNCTION/METHOD ===========================================================================================================================================================
	
	public void update(int index, GameContainer gc, StateBasedGame sbg, int delta, Player _player, ArrayList<Enemy> _enemyList, ArrayList<Projectile> _projectileList, ArrayList<Loot> _lootList, ArrayList<healthGlobe> _healthGlobeList, ArrayList<EnemyIndicator> _enemyIndicatorList, ArrayList<Blood> _bloodList) throws SlickException {
		if(this.hitpoints <= 0){
			this.hitpoints=0;
			this.dropLoot(gc, sbg, _lootList, _healthGlobeList);
			_enemyIndicatorList.get(index).destroy(index, _enemyIndicatorList);
			_bloodList.add(new Blood(this,0));
			this.destroy(index, _enemyList);
		}
		//System.out.println(stopMoving);
		meleeWepID = EnemyLevel;
		rangedWepID = EnemyLevel;
		
		Vector2f posSnapshotBefore = new Vector2f(vector.getX(), vector.getY());
		beingHit = false;
		
		//Attacking for enemies based on if they are ranged or melee
		if(vector.distance(_player.vector) <  maxRange + _player.hitboxX && vector.distance(_player.vector) >  minRange){
				
			if(enemyType == 0){
				isMeleeAttacking();
			}
			if(enemyType == 1){
				isRangedAttacking(gc, sbg, _player, _projectileList);
			}			
		} 
		//Statements detecting if the boss should melee attacking or use a ranged attack
		if(vector.distance(_player.vector) < this.hitboxX + _player.hitboxX + maxSeekDistance && vector.distance(_player.vector) >  minRange){
			if(enemyType == 2){
				if(vector.distance(_player.vector) > this.hitboxX + _player.hitboxX + (maxSeekDistance/1.5)){
					isRangedAttacking(gc, sbg, _player, _projectileList);
					setAttackReady();
				}
				else{
					isMeleeAttacking();
				}
			}
		}
		
		stopMovingWhenAttacking(stopMoving);
		beingMeleeAttacked(_player);
		beingRangedAttacked(_projectileList);
		
		separate(_enemyList);
		
		//Move towards player if within the max seek distance
		if(vector.distance(_player.vector) > minSeekDistance + _player.hitboxX + hitboxX && vector.distance(_player.vector) < maxSeekDistance + _player.hitboxX + hitboxX){
			moveTo(_player.vector, _enemyList);
		}
		
		if(enemyType == 1){
			//Move away from player if below the min seek distance
			if(vector.distance(_player.vector) < minSeekDistance){
				moveAwayFrom(_player.vector, _enemyList);
			}
		}
		
		//Making the enemy's attack ready
		if(vector.distance(_player.vector) <  maxRange + _player.hitboxX){
			setAttackReady();
			}
		
		Vector2f posSnapshotAfter = new Vector2f(vector.getX(), vector.getY());
		
		if(posSnapshotBefore.getY() == posSnapshotAfter.getY()){
			if(posSnapshotBefore.getY() < _player.vector.getY()){
				imageDirection = 1;
			}
			else if(posSnapshotBefore.getY() > _player.vector.getY()){
				imageDirection = 0;
			}
		}
		else if(posSnapshotAfter.getY() > posSnapshotBefore.getY()){
			imageDirection = 1;
		}
		else{
			imageDirection = 0;
		}
		
		if(this.meleeWepID > 5){
			meleeWepID = 5;
		}
		
		if(this.rangedWepID > 5){
			rangedWepID = 5;
		}
	}
	
	
	
	//RENDER FUNCTION/METHOD ============================================================================================================================================
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, Player player) throws SlickException{
		//Display blood
		
		//Display drop shadows
		if(enemyType == 2){
			bigShadow.draw(vector.getX()-64, vector.getY()+60);
		}
		else{
			smallShadow.draw(vector.getX()-32, vector.getY()+28);
		}
		
		//Displaying either the normal sprite or a "flash" (white color) filled version of it, depending on whether the object is hit.
		if(beingHit == true){
			if(enemyType == 2){
				sprite[enemyType][imageDirection].drawFlash(vector.getX()-64, vector.getY()-64);
			}
			else{
				sprite[enemyType][imageDirection].drawFlash(vector.getX()-32, vector.getY()-32);
			}
		}
		else{
			if(enemyType == 2){
				sprite[enemyType][imageDirection].draw(vector.getX()-64, vector.getY()-64);
			}
			else{
				sprite[enemyType][imageDirection].draw(vector.getX()-32, vector.getY()-32);
			}
		}
		
		//RENDER BLOOD OVERLAY
		if(hitpoints < maxHitpoints*0.3f){
			if(enemyType == 0 || enemyType == 1){
				bloodOverlay[enemyType][imageDirection].draw(this.vector.getX()-32, this.vector.getY()-32);
			}
			else if(enemyType == 2){
				if(evilLaughCheck == false){
					evilLaughCheck = true;
					evilLaugh.play();
					}
				bloodOverlay[enemyType][imageDirection].draw(this.vector.getX()-64, this.vector.getY()-64);
					
			}
		}
	
		//RENDER EQUIPPED WEAPON IN GAME SPACE ====================================================
				Vector2f dir = new Vector2f(0.0f, 0.0f);
				if(stopMoving == true && vectorSnapshotted == false){
					vectorSnapshotted = true;
					Vector2f tempTarget = new Vector2f(player.vector.getX(), player.vector.getY());
					dir = tempTarget.sub(vector);
					dir.normalise();
					spriteAngle = (float)dir.getTheta()+90;
					
				}
				else if(stopMoving == false){
					vectorSnapshotted = false;
					Vector2f tempTarget = new Vector2f(player.vector.getX(), player.vector.getY());
					dir = tempTarget.sub(vector);
					dir.normalise();
					spriteAngle = (float)dir.getTheta()+90;
				}
				
				enemyEquippedMeleeWepList[meleeWepID].setCenterOfRotation(32,96);
				enemyEquippedMeleeWepList[meleeWepID].setRotation(spriteAngle);
				
				if(enemyType == 0 && stopMoving == false){
					moveY = 0;
					maxMoveY = 64;
					moveYIncrement = 5;
				}
				if(enemyType == 0 && stopMoving == true){
					moveY = moveY + moveYIncrement;
					
					if(moveY >= maxMoveY){
						moveYIncrement *= -1;
					}
					if(moveY < -(maxMoveY/3)){
						moveYIncrement *= -1;
					}
					
					enemyEquippedMeleeWepList[meleeWepID].setCenterOfRotation(32,96);
					if(player.vector.getX() < vector.getX()){
						enemyEquippedMeleeWepList[meleeWepID].setRotation((spriteAngle-(maxMoveY/2)) + moveY );
					}
					else{
						enemyEquippedMeleeWepList[meleeWepID].setRotation((spriteAngle+(maxMoveY/2)) - moveY );
					}
					enemyEquippedMeleeWepList[meleeWepID].draw(vector.getX()-32, vector.getY()-96);
					
				}
				else if(enemyType == 0){
					enemyEquippedMeleeWepList[meleeWepID].setCenterOfRotation(32,96);
					enemyEquippedMeleeWepList[meleeWepID].setRotation(spriteAngle);
					enemyEquippedMeleeWepList[meleeWepID].draw(vector.getX()-32, vector.getY()-96);
				}
				
				if(enemyType == 1){
					enemyEquippedRangedWepList[rangedWepID].setCenterOfRotation(32,96);
					enemyEquippedRangedWepList[rangedWepID].setRotation(spriteAngle);
					enemyEquippedRangedWepList[rangedWepID].draw(vector.getX()-32, vector.getY()-96);
				}
				
				if(enemyType == 1 && stopMoving == false){
					
					arrow.setCenterOfRotation(16,64);
					arrow.setRotation(spriteAngle);
					arrow.draw(vector.getX()-16, vector.getY()-64);
				}
				
		//=========================================================================================
		
		g.setColor(new Color(255,0,0));
		g.drawRect(vector.getX()-33, vector.getY()-60, 76.9f, 15);
		g.fillRect(vector.getX()-33, vector.getY()-60,76.9f*this.hitpoints/this.maxHitpoints, 15);
		g.drawRect(vector.getX()-33, vector.getY()-60,76.9f*this.hitpoints/this.maxHitpoints, 15);
		
		g.setColor(new Color(255,255,255));
		g.drawString(""+(int)hitpoints, vector.getX()-10, vector.getY()-61);
		
	}
	
	//METHODS ===========================================================================================================================================================
	
	/**
	 * Method to keep enemies separated from each other
	 * @param _enemyList is used in order to get the enemies positions
	 * @return returns a new vector that the enemy should move towards
	 */
	private Vector2f separate(ArrayList<Enemy > _enemyList){
		
		float desiredSeparation = hitboxX * 2;
		Vector2f sum = new Vector2f(0.0f, 0.0f);
		int count = 0;
		
		for(int i = 0; i < _enemyList.size()-1; i++){
			
			float dist = vector.distance(_enemyList.get(i).vector);
			if(dist > 0 && dist < desiredSeparation){
				Vector2f tempVec1 = new Vector2f(_enemyList.get(i).vector.getX(), _enemyList.get(i).vector.getY());
				Vector2f tempVec2 = new Vector2f(vector.getX(), vector.getY());
				Vector2f diff = tempVec2.sub(tempVec1);
				diff.normalise();
				diff.scale(1/dist);
				sum.add(diff);
				count ++;
			}
			if(count > 0){
				sum.scale(1/count);
				sum.normalise();
				sum.scale(speedMultiplier*1.5f);
				
				Vector2f tempTarget = new Vector2f(sum.getX(), sum.getY());
				if(	tempTarget.getX() > 0 &&
					tempTarget.getX() < GameState.mapBoundWidth &&
					tempTarget.getY() > 0){
					//vector.add(tempTarget);
					return tempTarget;
				}
			}
		}
		return new Vector2f(0,0);
	} 

	/**
	 * Method to move the enemy closer to a target - goes in a straight line
	 * @param _target the target used moving towards
	 * @param _enemyList list used for determining that it is the enemy who are to move
	 */
	private void moveTo(Vector2f _target, ArrayList<Enemy > _enemyList){
		
		Vector2f tempTarget = new Vector2f(_target.getX(), _target.getY());
		Vector2f dir = new Vector2f(0.0f, 0.0f);
	
		dir = tempTarget.sub(vector);
		dir.normalise();
		dir = dir.scale(speedMultiplier);
		
		if(	vector.getX() + dir.getX() > 0 &&
			vector.getX() + dir.getX() < GameState.mapBoundWidth &&
			vector.getY() + dir.getY() > 0 &&
			vector.getY() + dir.getY() < GameState.mapBoundHeight){
				
				dir.add(separate(_enemyList).scale(2));
				vector = vector.add(dir);
		}
}
	
	/**
	 * Method used by the ranged enemies in order to move away from the target
	 * @param _target the target whom the enemies are moving away from
	 * @param _enemyList list used for determining that it is the enemy who are to move
	 */
	private void moveAwayFrom(Vector2f _target, ArrayList<Enemy > _enemyList){
			
		Vector2f tempTarget = new Vector2f(_target.getX(), _target.getY());
		Vector2f dir = new Vector2f(0.0f, 0.0f);
	
		dir = tempTarget.sub(vector);
		dir.normalise();
		dir = dir.scale(speedMultiplier);
				
		if(	vector.getX() + dir.getX() > 0 &&
			vector.getX() + dir.getX() < GameState.mapBoundWidth &&
			vector.getY() + dir.getY() > 0 &&
			vector.getY() + dir.getY() < GameState.mapBoundHeight){
					
			dir.add(separate(_enemyList).scale(2));
			vector = vector.sub(dir);
		}
	}
	
	/**
	 * Method used to setting the enemies melee attack ready
	 * @return isAttackReady for use, so enemies can attack again in isMeleeAttacking
	 */
	public boolean setAttackReady(){//End time - StartTime = CD. If CD >= 1000 then move on 
		float AS = AttackSpeed;
		if(this.isAttackReady == false){ 
			this.EndTime = System.currentTimeMillis();//StartTime should start from without
			if(this.EndTime - this.StartTime >= 1000/AS){
				this.isAttackReady = true;//set the isAttackReady to true
				this.StartTime = this.EndTime;
				this.EndTime = 0;
			}
		}
		return isAttackReady;
	}
	
	//Method to check if the enemy is being hit by a melee attack
	/**
	 * Method used for detecting if the player is close enough to deal damage. Will deal damage to the enemy if the player is close enough
	 * @param _player is used in order to get the player's position and damage value. And giving the palyer hitpoints based on vampire value
	 */
	private void beingMeleeAttacked (Player _player){
		
		if(_player.isMeleeAttacking && GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX){
			_player.AttackDamage();
			
			//Play enemy's being melee hit sound
			meleeHitSound.play();
			
			//Sets "beingHit" to true -> used to make the sprite blink on taking damage (used in the render method)
			beingHit = true;
			_player.hitPoints+=_player.playerVamp;
			
			//(nextFloat()*(_player.MaxDamage-_player.MinDamage))+_player.MinDamage;
			if(this.hitpoints - _player.playerMeleeDamage - ((_player.playerMeleeDamage / 100) * this.Armor) < 0){
				this.hitpoints = 0;
			}
			else{
				this.hitpoints -= _player.playerMeleeDamage - ((_player.playerMeleeDamage / 100) * this.Armor);
			}
		}
	}	
	
	/**
	 * Method to check if the enemy is being hit by a ranged attack
	 * @param _projectileList is used in order to destroy the arrow that hit the enemy. and get the damage that the arrow have
	 */
	private void beingRangedAttacked (ArrayList<Projectile> _projectileList){
		
		if(_projectileList.size() > 0){
			for(int i = _projectileList.size()-1; i >= 0; i--){
				if(_projectileList.get(i).owner instanceof Player && _projectileList.get(i).disableDmg == false && vector.distance(_projectileList.get(i).vector) < hitboxX + _projectileList.get(i).hitboxX){
			
					//Play enemy's being ranged hit sound
					meleeHitSound.play();
					
					//Sets "beingHit" to true -> used to make the sprite blink on taking damage (used in the render method)
					beingHit = true;
					
					if(this.hitpoints - _projectileList.get(i).damage - ((_projectileList.get(i).damage / 100) * this.Armor)<0){ //Setting the hitpoints to 0 if damage taken is more then remaing hp
						this.hitpoints=0;
					}
					else{
						this.hitpoints -= _projectileList.get(i).damage - ((_projectileList.get(i).damage / 100) * this.Armor); //taking hitpoint damage
					}
					_projectileList.get(i).disableDmg = true;
					_projectileList.get(i).destroy(i, _projectileList);
				}
			}
		}
	}
	
	/**
	 * Method for detecting if the enemy is able to melee attack and will then let the enemy attack
	 */
	public void isMeleeAttacking(){
		if(this.isAttackReady){	//Detecting if the enemy are able to attack based on if its attack are ready
			//Play meleeEnemy's melee attack sound 
			meleeAttackSound0.play();
			this.isMeleeAttacking = true;
			this.isAttackReady=false;
			this.stopMoving = true;
		}
	}
	
	/**
	 * Method for detecting if the enemy is able to ranged attack and will then let the player shoot an arrow
	 * @param gc used to initialise the arrow in our GameContainer - Parameters default to slick2D init method
	 * @param sbg used to initialise the arrow in our GameContainer - Parameters default to slick2D init method
	 * @param _player is used for the direction the arrow should move
	 * @param _projectileList is used in order to add a new arrow to the ArrayList
	 * @throws SlickException
	 */
	public void isRangedAttacking(GameContainer gc, StateBasedGame sbg, Player _player, ArrayList<Projectile> _projectileList) throws SlickException{
		if(this.isAttackReady == true){
			//Play rangedEnemy's ranged attack sound
			rangedAttackSound0.play();
			this.isRangedAttacking = true;
			this.isAttackReady=false;
			this.stopMoving = true;
					
			_projectileList.add(new Arrow(this, _player.vector, projectileSpeed));
			_projectileList.get(_projectileList.size()-1).init(gc, sbg);
			if(enemyType == 2){
				_projectileList.add(new Arrow(this, new Vector2f((float)(_player.vector.getX() / Math.cos(0.2f)),(float)(_player.vector.getY() / Math.cos(0.2f))), projectileSpeed));
				_projectileList.get(_projectileList.size()-1).init(gc, sbg);
				_projectileList.add(new Arrow(this, new Vector2f((float)(_player.vector.getX() * Math.cos(0.2f)),(float)(_player.vector.getY() * Math.cos(0.2f))), projectileSpeed));
				_projectileList.get(_projectileList.size()-1).init(gc, sbg);
			}
		}
	}
	
	/**
	 * Method for stopping the enemy's movement by reducing the speedMultiplier to 0
	 * @param stopMoving is used for detecting whether or not this methods should be used
	 */
	private void stopMovingWhenAttacking(boolean stopMoving){ //Method to make the enemy have slowed movement when attacking
		if(stopMoving == true){
			if(attackSTime == 0){
				speedMultiplier = 0.5f; //Movement is slowed while attacking
				attackSTime = System.currentTimeMillis();
				//System.out.println("im here!");
			}
			
			attackETime = System.currentTimeMillis() - attackSTime;
			
			System.out.println("attackETime: "+ attackETime + "   Target: " + (moveWaitTime / this.AttackSpeed));
			
			if(attackETime > moveWaitTime / this.AttackSpeed){
				if(enemyType == 0){
					this.speedMultiplier = 1.2f;
				}
				else if(enemyType == 1){
					this.speedMultiplier = 1.0f;
				}
				else if(enemyType == 2){
					this.speedMultiplier = 2.0f;
				}
				
				attackSTime = 0;
				attackETime = 0;
				this.stopMoving = false;
			}	
		}
	}
	
	/**
	 * Method for calculating the enemy's melee damage
	 */
	public void AttackDamage(){
		enemyDamage = ((randDmg.nextFloat() * (this.MaxDamage-this.MinDamage) + (this.EnemyLevel*2)));
	}
	
	/**
	 * Method for calculating the enemy's ranged damage
	 */
	public void RangedDamage(){
		rangedDamage = ((randDmg.nextFloat() * (this.MaxDamage-this.MinDamage) + (this.EnemyLevel*2)));
	}

	/**
	 * Method used for determing the enemies levels based on the wave number
	 * @param _wave is needed in order to scale the levels so they are harder as longer as you progress in the game
	 */
	public void SetEnemyLevel(int _wave){
		this.EnemyLevel = randLvl.nextInt(3)-1 + (int)(_wave/3);
		if(this.EnemyLevel < 1){ //statement in order to prevent enemy levels being below 1
			//Adjusting stats in order to keep the enemy level 1
			this.EnemyLevel = 1;
			this.hitpoints = (50 * this.EnemyLevel) + (randEnemyHP.nextInt(51)-25);
			this.maxHitpoints=this.hitpoints;
			this.EnemyName = EnemyNames[0];
			this.Armor = 5 * 1;
		}
		else if(this.EnemyLevel > 5){ //Making sure that enemies which are higher then level 5 are set to level 5
			//Adjusting stats in order to prevent overpowered enemies
			this.EnemyLevel = 5;
			this.hitpoints = (50 * this.EnemyLevel) + (randEnemyHP.nextInt(51)-25);
			this.maxHitpoints=this.hitpoints;
			this.EnemyName = EnemyNames[4];
			this.Armor = 5 * 5;
		}
		else{
		this.hitpoints = (50 * this.EnemyLevel) + (randEnemyHP.nextInt(51)-25);
		this.maxHitpoints=this.hitpoints;
		this.EnemyName = EnemyNames[this.EnemyLevel-1];
		this.Armor = 5 * this.EnemyLevel;
		}
	}
	
	/**s
	 * Overloaded Method used for determing the enemies levels based on the wave number
	 * @param _wave is needed in order to scale the levels so they are harder as longer as you progress in the game
	 * @param _bossLevel is used as an extra parameter for determing the level of enemy as the boss is supposed to be harder then everything else
	 */
	public void SetEnemyLevel(int _wave, int _bossLevel){
		this.EnemyLevel = randLvl.nextInt(3)-1 + (_wave/3) + _bossLevel;
		if(this.EnemyLevel < 1){
			this.EnemyLevel = 1;
			this.hitpoints = (75 * this.EnemyLevel) + (randEnemyHP.nextInt(51)-25);
			this.maxHitpoints=this.hitpoints;
			this.EnemyName = EnemyNames[0];
			this.Armor = 5 * 1;
		}
		else if(this.EnemyLevel > 5){
			this.hitpoints = (75 * this.EnemyLevel) + (randEnemyHP.nextInt(51)-25);
			this.maxHitpoints=this.hitpoints;
			this.EnemyName = EnemyNames[0];
			this.Armor = 5 * 5;
		}
		else{
		this.hitpoints = (75 * this.EnemyLevel) + (randEnemyHP.nextInt(51)-25);
		this.maxHitpoints=this.hitpoints;
		this.EnemyName = EnemyNames[0];
		this.Armor = 5 * this.EnemyLevel;
		}
	}

	/**
	 * Method to drop loot from the enemy
	 * @param gc used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param sbg used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param _lootList is used to pass on the list of loot
	 * @param _healthGlobeList is used to pass on the list of healthglobes
	 * @throws SlickException
	 */
	public void dropLoot(GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList, ArrayList<healthGlobe> _healthGlobeList) throws SlickException{
		Loot.spawnLoot(gc, sbg, _lootList, this);
		Loot.spawnHealthGlobe(gc, sbg, _healthGlobeList, this);
	}
	
	/**
	 * Method to "kill" destroy the enemy (remove it from the list of enemies)
	 * @param index is used to get what enemy that died
	 * @param _enemyList is used in order to access the enemyList
	 */
	public void destroy(int index, ArrayList<Enemy> _enemyList){
			_enemyList.remove(index);
	}
	
}
