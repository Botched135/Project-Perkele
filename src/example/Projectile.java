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
	private float duration = 1;
	protected boolean disableDmg;
	private long startTime = System.currentTimeMillis();

	//CONSTRUCTERS ===========================================================================================================================================================
	Projectile(Enemy _owner, Vector2f _target){
	
		vector.set(_owner.vector.getX(), _owner.vector.getY());
		Vector2f snapshotTargetVector = new Vector2f(_target);
		dir = new Vector2f(0.0f, 0.0f);
		dir = snapshotTargetVector.sub(vector);
		_owner.RangedDamage();
		damage = _owner.rangedDamage;
	}
	
	Projectile(Player _owner, Vector2f _target){
		
		vector.set(_owner.vector.getX(), _owner.vector.getY());
		Vector2f snapshotTargetVector = new Vector2f(_target);
		dir = new Vector2f(0.0f, 0.0f);
		dir = snapshotTargetVector.sub(vector);
		_owner.RangedAttackDamage();
		damage = _owner.rangedDamage;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		

	}
	
	//UPDATE FUNCTION/METHOD ===========================================================================================================================================================
	public void update(int index, ArrayList<Projectile> _projectileList){
		
		moveTo();
		if(((System.currentTimeMillis()) - startTime)/1000 >= duration){
			destroy(index, _projectileList);
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	}	
	
	
	/**
	 * Method which enables projectile to fork
	 * @param _projectileList is used in order to add new projectiles to a list
	 * @param _owner is used in order to get whom shot the projectile
	 * @param _target is used to get the direction the projectile should move towards
	 */
	/* This method is not being used atm
	private void spawnSubProjectile(ArrayList<Projectile> _projectileList, Player _owner, Vector2f _target){
		
		int maxRandDist = 10;
		Random randLoot = new Random();
		float tempRandX = randLoot.nextInt(maxRandDist);
		float tempRandY = randLoot.nextInt(maxRandDist);
		Projectile subProjectile = new Projectile(_owner, _target);
		subProjectile.vector.set(subProjectile.vector.getX() + tempRandX - maxRandDist/2, subProjectile.vector.getY() + tempRandY + maxRandDist/2);
		_projectileList.add(subProjectile);
	}
	*/
	
	/**
	 * Method used for moving the projectile at a set speed in a direction
	 */
	private void moveTo(){
		
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir);
	}
		
	/**
	 * Method used for destroying the projectile
	 * @param index is used for determing which projectile in the list should be destroyed
	 * @param _projectileList is used in order to determine which list something should be removed from
	 */
	public void destroy(int index, ArrayList<Projectile> _projectileList){
		
		if(_projectileList.size() > 0 && _projectileList.get(index) != null){
			_projectileList.remove(index);
		}
	}
}