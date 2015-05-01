package example;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Projectile extends GameObject {
	
	//VARIABLE DECLARATION ===========================================================================================================================================================
	protected GameObject owner;
	protected Vector2f target;
	protected Vector2f dir;
	protected float damage;
	protected float speedMultiplier = 0;
	protected float duration = 3;
	protected boolean disableDmg;
	protected long startTime = System.currentTimeMillis();

	//CONSTRUCTERS ===========================================================================================================================================================
	Projectile(Enemy _owner, Vector2f _target){
	
		vector.set(_owner.vector.getX(), _owner.vector.getY());
		Vector2f snapshotTargetVector = new Vector2f(_target);
		dir = new Vector2f(0.0f, 0.0f);
		dir = snapshotTargetVector.sub(vector);
		
		damage = _owner.rangedDamage;
	}
	
	Projectile(Player _owner, Vector2f _target){
		
		vector.set(_owner.vector.getX(), _owner.vector.getY());
		Vector2f snapshotTargetVector = new Vector2f(_target);
		dir = new Vector2f(0.0f, 0.0f);
		dir = snapshotTargetVector.sub(vector);
		
		damage = _owner.rangedDamage;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		

	}
	
	//UPDATE FUNCTION/METHOD ===========================================================================================================================================================
	public void update(Player _player, int index, GameContainer gc, StateBasedGame sbg, int delta, ArrayList<Projectile> _projectileList, ArrayList<Enemy> _enemyList){
		
		stateManager(_player, index, _projectileList, _enemyList);
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	}	
	
	
	//METHODS ===========================================================================================================================================================
	void stateManager(Player _player, int index, ArrayList<Projectile> _projectileList, ArrayList<Enemy> _enemyList){
		
		//Moves towards a snapshotted vector position of the enemies target
		
		Vector2f temp = new Vector2f(_player.vector.getX(), _player.vector.getY());
		moveTo();
		_player.vector.set(temp.getX(), temp.getY()); 
				
		//Destroys projectile when its duration runs out
		if(((System.currentTimeMillis()) - startTime)/1000 >= duration){
					
			destroy(index, _projectileList);
		}
	}
	
	public void spawnSubProjectile(ArrayList<Projectile> _projectileList, Player _owner, Vector2f _target){
		
		int maxRandDist = 10;
		Random randLoot = new Random();
		float tempRandX = randLoot.nextInt(maxRandDist);
		float tempRandY = randLoot.nextInt(maxRandDist);
		Projectile subProjectile = new Projectile(_owner, _target);
		subProjectile.vector.set(subProjectile.vector.getX() + tempRandX - maxRandDist/2, subProjectile.vector.getY() + tempRandY + maxRandDist/2);
		_projectileList.add(subProjectile);
		
	}
	
public void spawnSubProjectile(ArrayList<Projectile> _projectileList, Enemy _owner, Vector2f _target){
		
		int maxRandDist = 10;
		Random randLoot = new Random();
		float tempRandX = randLoot.nextInt(maxRandDist);
		float tempRandY = randLoot.nextInt(maxRandDist);
		Projectile subProjectile = new Projectile(_owner, _target);
		subProjectile.vector.set(subProjectile.vector.getX() + tempRandX - maxRandDist/2, subProjectile.vector.getY() + tempRandY + maxRandDist/2);
		_projectileList.add(subProjectile);
		
	}
	
	void seekState(Vector2f _target){
		
		Vector2f dir = new Vector2f(0.0f, 0.0f);
		
		dir = _target.sub(vector);
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir);
		
	}
	
	public void moveTo(){
		
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir);

	}
		
	public void destroy(int index, ArrayList<Projectile> _projectileList){
		
		if(_projectileList.size() > 0 && _projectileList.get(index) != null){
			_projectileList.remove(index);
		}
	}
}