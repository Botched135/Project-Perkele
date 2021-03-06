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
import org.newdawn.slick.Sound;

public class Loot extends GameObject {
	
	//VARIABLE DECLARATION =============================================================================================================================================

	//RNG-sus
	protected Random randDmg = new Random();
	protected Random randSpeed = new Random();
	protected Random randArmor = new Random();
	protected Random randlifeRegen = new Random();
	protected Random randVamp = new Random();
	
	//General Loot Stats
	protected boolean beingHit = false;
	protected int Health=2;
	protected String Name="";
	protected float Average;
	protected float EnemyAverage;
	protected float LootAverage;
	protected int numberOfStats=3;
	public int lootLevel = 1;
	private boolean leftMousePressed = false;
	private long startTime = System.currentTimeMillis();
	private float duration = 60;
	
	//Weapons Stats
	protected static Color lootTestCol = new Color(255,255,0);
	protected float speedMultiplier = 5.0f; 
	protected float wepMinDMG;
	protected float wepMaxDMG;
	protected float attackSpeed;
	protected int isVamp;
	protected float Vamp = 0;
	
	//Armor Stats
	protected float hpBonus;
	protected float Armor;
	protected int isLifeRegen;
	protected float lifeRegen;
	

	
	
	//Sounds =============================================
	
	public static Sound lootPickupSound = null;
	
	//CONSTRUCTORS ======================================================================================================================================================
	
	Loot() {
		
		vector = new Vector2f(0,0);
	}
	
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {	
	
	lootPickupSound = new Sound("data/lootPickupSound.ogg");
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
		this.enemyPickUp(index, gc, _enemyList, _lootList);
		this.playerPickUp(index, gc, _player, _lootList, _inventoryList);
		
		if(!gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			this.beingMeleeAttacked(_player);
		}
		
		if(_lootList.size() > 0){
			if(((System.currentTimeMillis()) - startTime)/1000 >= duration){
				destroy(index, _lootList);
			}
		}
		
		stateManager(index, _lootList);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
	}

	//METHODS
	/**
	 * Method used for calling the separate method if the conditions are right
	 * @param _index
	 * @param _lootList
	 */
	public void stateManager(int _index, ArrayList<Loot> _lootList){
		if(_lootList.size() > 0 && _index == _lootList.size()-1) {
			separate(_lootList);
		}
	}
	
	/**
	 * Method used for separating the the loot given in the lootList
	 * @param _lootList gives the loot which are to be separated
	 */
	private void separate(ArrayList<Loot> _lootList){
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

	/**
	 * spawnLoot method used for spawning loot at the enemys position regardless of where the mouse is.
	 * @param gc used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param sbg used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param _lootList is used to add a new item to the lootLists and initialise it
	 * @param enemy is used in order to pass on the enemy's variables, and is used for spawning the loot at the enemy's position
	 * @throws SlickException
	 */
	public static void spawnLoot(GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList, Enemy enemy) throws SlickException {
		Random randLoot = new Random();
		Random randDrop = new Random();
		int lootDropDist = 10;
		int dropping = randDrop.nextInt(100);
		if(dropping > 20) {
			
			int lootType = randDrop.nextInt(3);
			if(lootType == 1) {
				_lootList.add(new Armor(enemy));
				_lootList.get(_lootList.size()-1).init(gc, sbg);
			}
			else if(lootType == 2){
				_lootList.add(new RangedWeapon(enemy));
				_lootList.get(_lootList.size()-1).init(gc,sbg);
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
	
	/**
	 * Method used to drop the loot which the player had, when something new is picked up
	 * @param _lootList is used to add a new item to the lootLists and initialise it
	 * @param _lootType is used to determine what type of item it is
	 * @param _inventoryList is used to get the list of items that the player have equipped
	 * @param _inventoryIndex is used to determine what of the items equipped is to be changed when picking something new up
	 * @param _player is used to get the position of the player
	 */
	public static void spawnLoot(ArrayList<Loot> _lootList, Loot _lootType, ArrayList<Loot> _inventoryList, Loot _inventoryIndex, Player _player){
		Random randLoot = new Random();
		int lootDropDist = 10;
		
		if(_inventoryIndex instanceof Weapon){
			_lootList.add(_inventoryIndex);
			_inventoryList.remove(1);
			_inventoryList.add(1,_lootType);
		}
		else if(_inventoryIndex instanceof RangedWeapon){
			_lootList.add(_inventoryIndex);
			_inventoryList.remove(2);
			_inventoryList.add(2, _lootType);
		}
		else if(_inventoryIndex instanceof Armor){
			_lootList.add(_inventoryIndex);
			_inventoryList.remove(0);
			_inventoryList.add(0,_lootType);
			
		}
		
		float tempRandX = randLoot.nextInt(lootDropDist);
		float tempRandY = randLoot.nextInt(lootDropDist);
		float tempX = _player.vector.getX() + (tempRandX)-(lootDropDist/2);
		float tempY = _player.vector.getY() + (tempRandY)-(lootDropDist/2);
		
		_lootList.get(_lootList.size()-1).vector.set(new Vector2f(tempX, tempY));
	}
	
	/**
	 * Method used to spawn a healthglobe based on a 80% chance
	 * @param gc used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param sbg used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param _healthGlobeList is used in order to add a new healthglobe to the game
	 * @param enemy is used to get the position that the healthglobe should spawn at
	 * @throws SlickException
	 */
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
	
	/**
	 * Method used to setting the loot level based on the enemy level
	 * @param _enemy is used to get the enemy level
	 */
	public void SetLootLevel(Enemy _enemy){
		System.out.println("enemy: "+ _enemy.EnemyLevel);
		this.lootLevel = _enemy.EnemyLevel;
	}
	
	/**
	 * Method used to giving damage to the loot on the ground
	 * @param _player is used in order to detect if the player is close enough to the loot
	 */
	private void beingMeleeAttacked(Player _player){
		if(_player.isMeleeAttacking && 
				GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX){
			
			beingHit = true;
			
			this.Health -= 1;
			if(this.Health <0){
				this.Health=0;
			}
		}
	}
	
	/**
	 * Method used for letting the player pick up loot based on certain conditions
	 * @param index is used for determing which item is being handleded
	 * @param gc used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param _player is used to get the players position and its meleeRange in order to know if the player is close enough to pick up loot
	 * @param _lootList is used in order to get the info of the certain item being picked up
	 * @param _inventoryList is used in order to swap out the equipped item with the new one
	 */
	private void playerPickUp(int index, GameContainer gc, Player _player, ArrayList <Loot> _lootList, ArrayList<Loot> _inventoryList){
		if(_lootList.size() >= 0) {
			if(gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false){
				leftMousePressed = true;
					if(GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX) {
						//either a method for picking up armor or a weapon
							
						if(_lootList.get(index) != null){
							if(_lootList.get(index) instanceof Weapon){
								spawnLoot(_lootList, _lootList.get(index), _inventoryList, _inventoryList.get(1), _player);
								//_inventoryList.remove(1);
								//_inventoryList.add(1,_lootList.get(index));
							}
							else if(_lootList.get(index) instanceof RangedWeapon){
								spawnLoot(_lootList,_lootList.get(index), _inventoryList, _inventoryList.get(2),_player);
							}
							else if(_lootList.get(index) instanceof Armor){
								spawnLoot(_lootList, _lootList.get(index), _inventoryList, _inventoryList.get(0), _player);
								//_inventoryList.remove(0);
								//_inventoryList.add(0,_lootList.get(index));
							}
							_lootList.remove(index);
						}
					}
				}
			}
		}
	
	/**
	 * Method used for letting the enemy pick up loot based on certain conditions
	 * @param index is used for determing which item is being handleded
	 * @param gc used to initialise the loot in our GameContainer - Parameters default to slick2D init method
	 * @param _enemyList is used to get the enemy position and its meleeRange in order to know if the enemy is close enough to pick up loot
	 * @param _lootList is used in order to get the info of the certain item being picked up
	 */
	private void enemyPickUp(int index, GameContainer gc, ArrayList<Enemy> _enemyList, ArrayList <Loot> _lootList){
		if(_lootList.size()>=0){
			for(int i = _enemyList.size()-1; i>=0;i--){ 
				if(index <= _lootList.size()-1 && _lootList.get(index) instanceof Weapon && _enemyList.get(i).enemyType ==0 && !gc.getInput().isKeyDown(Input.KEY_LSHIFT) && !gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					EnemyAverage = ((_enemyList.get(i).MinDamage+_enemyList.get(i).MaxDamage)*_enemyList.get(i).AttackSpeed)/2;
					LootAverage =((_lootList.get(index).wepMinDMG+_lootList.get(index).wepMaxDMG)/2)*_lootList.get(index).attackSpeed;
				}
				else if(index <= _lootList.size()-1 && _lootList.get(index) instanceof RangedWeapon && _enemyList.get(i).enemyType ==1 && !gc.getInput().isKeyDown(Input.KEY_LSHIFT) && !gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
					EnemyAverage = ((_enemyList.get(i).MinDamage+_enemyList.get(i).MaxDamage)*_enemyList.get(i).AttackSpeed)/2;
					LootAverage =((_lootList.get(index).wepMinDMG+_lootList.get(index).wepMaxDMG)/2)*_lootList.get(index).attackSpeed;
				}
				else if(index <= _lootList.size()-1 && _lootList.get(index) instanceof Armor && !gc.getInput().isKeyDown(Input.KEY_LSHIFT) && !gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){ 
					EnemyAverage= _enemyList.get(i).Armor;
					LootAverage = _lootList.get(index).Armor;
				}
				if(index <= _lootList.size()-1 && vector.distance(_enemyList.get(i).vector)< _enemyList.get(i).hitboxX+_lootList.get(index).hitboxX+10 && EnemyAverage<LootAverage){
					if(_lootList.get(index) instanceof Weapon && _enemyList.get(i).enemyType ==0){
						_enemyList.get(i).MinDamage=_lootList.get(index).wepMinDMG;
						_enemyList.get(i).MaxDamage=_lootList.get(index).wepMaxDMG;
						_enemyList.get(i).AttackSpeed = _lootList.get(index).attackSpeed;
						_enemyList.get(i).enemyVamp = _lootList.get(index).Vamp;
						_enemyList.get(i).WeaponName = _lootList.get(index).Name;
						_enemyList.get(i).meleeWepID = _lootList.get(index).lootLevel;
						_lootList.remove(index);
					}
					else if(_lootList.get(index) instanceof RangedWeapon && _enemyList.get(i).enemyType==1){
						_enemyList.get(i).MinDamage=_lootList.get(index).wepMinDMG;
						_enemyList.get(i).MaxDamage=_lootList.get(index).wepMaxDMG;
						_enemyList.get(i).AttackSpeed = _lootList.get(index).attackSpeed;
						_enemyList.get(i).WeaponName = _lootList.get(index).Name;
						_enemyList.get(i).rangedWepID = _lootList.get(index).lootLevel;
						_lootList.remove(index);
					}
					else if(_lootList.get(index) instanceof Armor){
						_enemyList.get(i).Armor = _lootList.get(index).Armor;
						_enemyList.get(i).ArmorName = _lootList.get(index).Name;
						_lootList.remove(index);
					}
					break;
				}
			}
		}
	}
	
	/**
	 * Method used to remove a certain item
	 * @param index is used to determine which item is to be removed from the list
	 * @param _lootList is used for getting the list which items should be removed from
	 */
	private void destroy(int index, ArrayList<Loot> _lootList){
		_lootList.remove(index);

}
}
