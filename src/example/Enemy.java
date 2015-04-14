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

public class Enemy extends GameObject {
	
	//VARIABLE DECLARATION ===========================================================================================================================================================
	public int EnemyLevel;
	protected float hitpoints = 100;
	protected float speedMultiplier = 0.5f;
	protected float AttackSpeed = 1;
	protected long StartTime = System.currentTimeMillis();
	protected long EndTime = 0;
	protected boolean isAttackReady = false;
	protected static Color enemyTestCol = new Color(255,0,0);
	protected Random randLvl = new Random();
	protected String[]EnemyNames = {"Dwarf","Dwarf Soldier","Dwarf Veteran","Dwarf Lord", "Dwarf Faggot"};
	protected String EnemyName;
	
	//Sprites
	private Image enemyTestSprite = null; 
	
	//CONTRUCTERS ===========================================================================================================================================================
	
	Enemy(){
		
		super(); 
		ID = 2;
	}
	
	Enemy(Vector2f _vector) {
		 
		super(_vector);
	 
		hitboxX = 32.0f;
		hitboxY = 32.0f;
		ID = 2;
		
	}
	
	//INIT FUNCTION/METHOD ===============================================================================================================================================
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		enemyTestSprite = new Image("data/enemyTestSprite.png");
	}
	
	//UPDATE FUNCTION/METHOD ===========================================================================================================================================================
	
	public void update(int index, GameContainer gc, StateBasedGame sbg, int delta, Player _player, ArrayList<Enemy> _enemyList, ArrayList<Projectile> _projectileList, ArrayList<Loot> _lootList) throws SlickException {
		
		stateManager(index, gc, sbg, _player, _enemyList, _projectileList, _lootList);
		
	}
	//RENDER FUNCTION/METHOD ============================================================================================================================================
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g, Player _player) throws SlickException {
		
		enemyTestSprite.draw(vector.getX()-32, vector.getY()-32);
		
	
		g.setColor(new Color(255,0,0));
		g.drawRect(vector.getX()-33, vector.getY()-60, 76.9f, 15);
		g.fillRect(vector.getX()-33, vector.getY()-60,(this.hitpoints/this.EnemyLevel)/1.3f, 15);
		g.drawRect(vector.getX()-33, vector.getY()-60,(this.hitpoints/this.EnemyLevel)/1.3f, 15);
		
		g.setColor(new Color(255,255,255));
		g.drawString(""+(int)hitpoints, vector.getX()-10, vector.getY()-61);
		g.drawString("Nr." + Integer.toString(index), vector.getX()-32, vector.getY()+32);
		g.drawString("Lvl " + EnemyLevel, vector.getX()-20, vector.getY()-80);
		
		
		
	}
	
	//METHODS ===========================================================================================================================================================
	//stateManager chooses the state of the Enemy based on certain criteria
	void stateManager(int index, GameContainer gc, StateBasedGame sbg, Player _player, ArrayList<Enemy> _enemyList, ArrayList<Projectile> _projectileList, ArrayList<Loot> _lootList) throws SlickException{
			
		if(this.hitpoints <= 0){

			this.hitpoints=0;
			this.dropLoot(gc, sbg, _lootList);
			this.destroy(index, _enemyList);
		}
		
		beingMeleeAttacked(_player);
		beingRangedAttacked(_projectileList);
		
		separate(_enemyList);
		
		if(vector.distance(_player.vector) < 300){
		
		Vector2f temp = new Vector2f(_player.vector.getX(), _player.vector.getY());
		seekState(_player.vector);
		_player.vector.set(temp.getX(), temp.getY()); 
		}
	}
	
	//fleeState makes the enemy seek out the player
	void seekState(Vector2f _target){
				
		moveTo(_target);
		
	}
	//Method to keep enemies separated from each other
	void separate(ArrayList<Enemy> _enemyList){
		
		float desiredSeparation = hitboxX*2;
		Vector2f sum = new Vector2f(0.0f, 0.0f);
		int count = 0;
		
		for(int i = 0; i < _enemyList.size(); i++){
			
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
				vector.add(sum);
			}
			
		}
	} 
	//Method to move the enemy closer to a target - goes in a straight line
	public void moveTo(Vector2f _target){
		
		Vector2f dir = new Vector2f(0.0f, 0.0f);
	
		dir = _target.sub(vector);
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir);

	}
	//Method to set the enemy's attack to be ready according to its cooldowns
	public boolean setAttackReady(){//End time - StartTime = CD. If CD >= 1000 then move on 
		float AS =AttackSpeed;
		if(this.isAttackReady == false){ 
		this.EndTime = System.currentTimeMillis();//StartTime should start from without
		if(this.EndTime-this.StartTime >= 1000/AS){
			this.isAttackReady=true;//set the isAttackReady to true
			this.StartTime = this.EndTime;
			this.EndTime = 0;
		}
		}
		//isAttackReady = this.isAttackReady;
		return isAttackReady;
	}
	//Method to check if the enemy is being hit by a melee attack
	void beingMeleeAttacked (Player _player){
		
		if(_player.isMeleeAttacking && GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX){
			_player.AttackDamage();
			this.hitpoints -= _player.PlayerDamage;//(nextFloat()*(_player.MaxDamage-_player.MinDamage))+_player.MinDamage;
			if(this.hitpoints <0){
				this.hitpoints=0;
			}
		}
	}
	//Method to check if the enemy is being hit by a ranged attack
	void beingRangedAttacked (ArrayList<Projectile> _projectileList){
		
		if(_projectileList.size() > 0){
			for(int i = _projectileList.size()-1; i >= 0; i--){
				if(_projectileList.get(i).disableDmg == false && vector.distance(_projectileList.get(i).vector) < hitboxX + _projectileList.get(i).hitboxX+1){
			
					this.hitpoints -= _projectileList.get(i).damage;
					_projectileList.get(i).disableDmg = true;
				}
			}
		}
	}
	//Method to set the enemy's level
	void SetEnemyLevel(){
		this.EnemyLevel=randLvl.nextInt(5)+1;
		this.hitpoints = 100*this.EnemyLevel;
		this.EnemyName = EnemyNames[this.EnemyLevel-1];
	}
	//Method to drop loot from the enemy
	void dropLoot(GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList) throws SlickException{
		Loot.spawnLoot(gc, sbg, _lootList, this);
		
		if(_lootList.size()>0){
			_lootList.get(_lootList.size()-1).SetLootLevel(this);
		}
	}
	//Method to "kill" destroy the enemy (remove it from the list of enemies)
	void destroy(int index, ArrayList<Enemy> _enemyList){
			_enemyList.remove(index);
	
	}
}
