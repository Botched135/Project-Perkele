package example;


import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Loot extends GameObject {
	
	//VARIABLE DECLARATION ==============================================================================================================================================
	
	protected String[]ArmorNames = {"Leather Armor","Ringmail","Breast Plate","Full Plate","Dragonbone Armor"};
	protected Random randDmg = new Random();
	protected Random randSpeed = new Random();
	protected static Color lootTestCol = new Color(255,255,0);
	protected float speedMultiplier = 5.0f; 
	protected float wepMinDMG;
	protected float wepMaxDMG;
	protected float attackSpeed;
	protected float hpBonus;
	protected float Armor;
	protected boolean beingHit = false;
	protected int Health=1;
	protected String Name="";
	protected float Average;
	protected float EnemyAverage;
	protected float LootAverage;
	public int lootLevel = 1;
	boolean leftMousePressed = false;
	
	//CONSTRUCTORS ======================================================================================================================================================
	
	Loot() {
		
		vector = new Vector2f(0,0);
	}
	
	
public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {	
	
	leftMousePressed = false;
		
	}
	
	//UPDATE FUNCTION/METHOD ============================================================================================================================================
	
	public void update(int index, GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList, ArrayList<Loot> _inventoryList, ArrayList<Enemy> _enemyList, Player _player){
		
		beingHit = false;
		if(this.Health <= 0){
			this.Health=0;
			this.destroy(index,_lootList);
		}
		
		if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			leftMousePressed = false;
		}
		this.playerPickUp(index, gc, sbg, _player, _lootList, _inventoryList);
		
		this.enemyPickUp(index, gc, _enemyList, _lootList, _inventoryList);
		

		if(!gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			this.beingMeleeAttacked(_player);
		}
		
		stateManager(index, _lootList);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
	}

	//METHODS
	
	void stateManager(int _index, ArrayList<Loot> _lootList){
		
		if(_lootList.size() > 0 && _index == _lootList.size()-1) {
			separate(_lootList);
		}

	}
	
	
	void separate(ArrayList<Loot> _lootList){

		float desiredSeparation = hitboxX*2;
		Vector2f sum = new Vector2f(0.0f, 0.0f);
		int count = 0;
		
		for(int i = 0; i < _lootList.size(); i++){
			
			float dist = vector.distance(_lootList.get(i).vector);
			if(dist > 0 && dist < desiredSeparation){
				Vector2f tempVec1 = new Vector2f(_lootList.get(i).vector.getX(), _lootList.get(i).vector.getY());
				Vector2f tempVec2 = new Vector2f(vector.getX(), vector.getY());
				Vector2f diff = tempVec2.sub(tempVec1);
				diff.normalise();
				//diff.scale(1/dist);
				sum.add(diff);
				count ++;
			}
			if(count > 0){
				sum.scale(1/count);
				sum.normalise();
				sum.scale(speedMultiplier);				
				vector.add(sum);
			}
			
		}
	}
	

//spawnLoot method used for spawning loot at the enemys position regardless of where the mouse is.
	public static void spawnLoot(GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList, Enemy enemy) throws SlickException {
		
		Random randLoot = new Random();
		Random randDrop = new Random();
		int lootDropDist = 10;
		int dropping = randDrop.nextInt(100);
		if(dropping > 20) {
			
			int lootType = randDrop.nextInt(2);
			if(lootType == 1) {
				_lootList.add(new Armor(enemy));
				_lootList.get(_lootList.size()-1).init(gc, sbg);
			}
			else {
				_lootList.add(new Weapon(enemy));
				_lootList.get(_lootList.size()-1).init(gc, sbg);
			}
			
			float tempRandX = randLoot.nextInt(lootDropDist);
			float tempRandY = randLoot.nextInt(lootDropDist);
			float tempX = enemy.vector.getX() + (tempRandX)-(lootDropDist/2);
			float tempY = enemy.vector.getY() + (tempRandY)-(lootDropDist/2);
			
			_lootList.get(_lootList.size()-1).vector.set(new Vector2f(tempX, tempY));
			
		}
	}
	public static void spawnLoot(GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList, Loot _lootIndex, ArrayList<Loot> _inventoryList, Loot _inventoryIndex, Player _player){
		Random randLoot = new Random();
		int lootDropDist = 10;
		
		if(_inventoryIndex instanceof Weapon){
			_lootList.add(_inventoryIndex);
			_inventoryList.remove(1);
			_inventoryList.add(1,_lootIndex);
		}
		else if(_inventoryIndex instanceof Armor){
			_lootList.add(_inventoryIndex);
			_inventoryList.remove(0);
			_inventoryList.add(0,_lootIndex);
			
		}
		
		float tempRandX = randLoot.nextInt(lootDropDist);
		float tempRandY = randLoot.nextInt(lootDropDist);
		float tempX = _player.vector.getX() + (tempRandX)-(lootDropDist/2);
		float tempY = _player.vector.getY() + (tempRandY)-(lootDropDist/2);
		
		_lootList.get(_lootList.size()-1).vector.set(new Vector2f(tempX, tempY));
		
	}
	
public static void spawnHealthGlobe(GameContainer gc, StateBasedGame sbg, ArrayList<healthGlobe> _healthGlobeList, Enemy enemy) throws SlickException {
		
		Random randLoot = new Random();
		Random randDrop = new Random();
		int lootDropDist = 10;
		int dropping = randDrop.nextInt(100);
		if(dropping > 80) {
			
			_healthGlobeList.add(new healthGlobe());
			_healthGlobeList.get(_healthGlobeList.size()-1).init(gc, sbg);
			
			float tempRandX = randLoot.nextInt(lootDropDist);
			float tempRandY = randLoot.nextInt(lootDropDist);
			float tempX = enemy.vector.getX() + (tempRandX)-(lootDropDist/2);
			float tempY = enemy.vector.getY() + (tempRandY)-(lootDropDist/2);
			
			_healthGlobeList.get(_healthGlobeList.size()-1).vector.set(new Vector2f(tempX, tempY));
			
		}
	}
	
	public void SetLootLevel(Enemy _enemy){
		this.lootLevel = _enemy.EnemyLevel;
	}
	
	public void beingMeleeAttacked(Player _player){
		
		if(_player.isMeleeAttacking && 
				GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX){
			
			beingHit = true;
			
			this.Health -= 1;
					
			
			if(this.Health <0){
				this.Health=0;
			}
		}
	}
	public void playerPickUp(int index, GameContainer gc, StateBasedGame sbg, Player _player, ArrayList <Loot> _lootList, ArrayList<Loot> _inventoryList){
		if(_lootList.size() >= 0) {
			if(gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false){
				leftMousePressed = true;
						if(GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX) {
							//either a method for picking up armor or a weapon
							if(_lootList.get(index) instanceof Weapon){
								spawnLoot(gc, sbg, _lootList, _lootList.get(index), _inventoryList, _inventoryList.get(1), _player);
								//_inventoryList.remove(1);
								//_inventoryList.add(1,_lootList.get(index));
								
							}
							else if(_lootList.get(index) instanceof Armor){
								spawnLoot(gc, sbg, _lootList, _lootList.get(index), _inventoryList, _inventoryList.get(0), _player);
								//_inventoryList.remove(0);
								//_inventoryList.add(0,_lootList.get(index));
							}
								_lootList.remove(index);
						}
				}
			}
	}
	public void enemyPickUp(int index, GameContainer gc, ArrayList<Enemy> _enemyList, ArrayList <Loot> _lootList, ArrayList<Loot> _inventoryList){
		if(_lootList.size()>=0){
			for(int i = 0; i<_enemyList.size()-1;i++){
				
				if(_lootList.get(index) instanceof Weapon){
					    EnemyAverage = ((_enemyList.get(i).MinDamage+_enemyList.get(i).MaxDamage)*_enemyList.get(i).AttackSpeed)/2;
						LootAverage =((_lootList.get(index).wepMinDMG+_lootList.get(index).wepMaxDMG)/2)*_lootList.get(index).attackSpeed;
				}
				else if(_lootList.get(index) instanceof Armor){
					EnemyAverage= _enemyList.get(i).Armor;
					LootAverage = _lootList.get(index).Armor;
				}
				if(vector.distance(_enemyList.get(i).vector)<  _enemyList.get(i).hitboxX+this.hitboxX && EnemyAverage<LootAverage){
					if(_lootList.get(index) instanceof Weapon){
						_enemyList.get(i).MinDamage=_lootList.get(index).wepMinDMG;
						_enemyList.get(i).MaxDamage=_lootList.get(index).wepMaxDMG;
						_enemyList.get(i).AttackSpeed = _lootList.get(index).attackSpeed;
						_lootList.remove(index);
					}
					else if(_lootList.get(index) instanceof Armor){
						_enemyList.get(i).Armor = _lootList.get(index).Armor;
						_lootList.remove(index);
					}
				}
			}
		}
	}
	void destroy(int index, ArrayList<Loot> _lootList){
		_lootList.remove(index);

}
}
