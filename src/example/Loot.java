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
	
	protected Random randDmg = new Random();
	protected Random randSpeed = new Random();
	protected static Color lootTestCol = new Color(255,255,0);
	protected float speedMultiplier = 5.0f; 
	protected float wepMinDMG;
	protected float wepMaxDMG;
	protected float attackSpeed;
	protected float hpBonus;
	protected float Armor;
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
	
	public void update(int index, GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList, ArrayList<Loot> _inventoryList, Player _player){
		
		if(!gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			leftMousePressed = false;
		}
		
		/*if(_lootList.size() >= 0) {
			if(gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false){
				leftMousePressed = true;
					for(int i = 0; i < _lootList.size()-1; i++) {
						if(GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX) {
							//either a method for picking up armor or a weapon
							if(_lootList.get(i) instanceof Weapon){
								_inventoryList.remove(1);
								_inventoryList.add(1,_lootList.get(i));
								
							}
							else if(_lootList.get(i) instanceof Armor){
								_inventoryList.remove(0);
								_inventoryList.add(0,_lootList.get(i));
							}
								_lootList.remove(i);
								break;
						}
					}
				}
			}*/
		
		
		if(_lootList.size() >= 0) {
			if(gc.getInput().isKeyDown(Input.KEY_LSHIFT) && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && leftMousePressed == false){
				leftMousePressed = true;
						if(GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX) {
							//either a method for picking up armor or a weapon
							if(_lootList.get(index) instanceof Weapon){
								_inventoryList.remove(1);
								_inventoryList.add(1,_lootList.get(index));
								
							}
							else if(_lootList.get(index) instanceof Armor){
								_inventoryList.remove(0);
								_inventoryList.add(0,_lootList.get(index));
							}
								_lootList.remove(index);
						}
				
				}
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
	
//This loot spawning method is to be removed later in inplementation as it spawns using spacebar	
	public static void spawnLoot(GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList) {
		
		Random randLoot = new Random();
		Random randDrop = new Random();
		int lootDropDist = 10;
		int dropping = randDrop.nextInt(100);
		if(dropping > 20) {
			
			int lootType = randDrop.nextInt(2);
			if(lootType == 1) {
				_lootList.add(new Armor());	
			}
			else {
				_lootList.add(new Weapon());
			}
			
			float tempRandX = randLoot.nextInt(lootDropDist);
			float tempRandY = randLoot.nextInt(lootDropDist);
			float tempX = GameState.mousePos.getX() + (tempRandX)-(lootDropDist/2);
			float tempY = GameState.mousePos.getY() + (tempRandY)-(lootDropDist/2);
			
			_lootList.get(_lootList.size()-1).vector.set(new Vector2f(tempX, tempY));		
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
	
	public void SetLootLevel(Enemy enemy){
		this.lootLevel = enemy.EnemyLevel;
	}
}
