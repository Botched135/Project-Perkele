package example;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Vector2f;

public class Projectile extends GameObject {
	
	//VARIABLE DECLARATION
	protected GameObject owner;
	protected Vector2f target;
	protected float speedMultiplier = 0;
	protected float duration = 3;
	protected long startTime = System.currentTimeMillis();

	//CONSTRUCTERS
	Projectile(GameObject _owner){
	
		vector.set(_owner.vector.getX(), _owner.vector.getY());
		
	}
	
	//METHODS
	void stateManager(int index, ArrayList<Projectile> _projectileList, ArrayList<Enemy> _enemyList){
		
		//Moves towards a snapshotted vector position of the enemies target
		moveTo(target);
				
		//Destroys projectile when its duration runs out
		if(((System.currentTimeMillis()) - startTime)/1000 >= duration){
					
			destroy(index, _projectileList);
		}
		//Destroy projectile if owned by a player and it hits an enemy
		//if(owner.ID == 1 && isColliding(_enemyList) == true){
					
		for(int i = 0; i <= _enemyList.size()-1; i++){
			if(_enemyList.size() > 0 && this.vector.distance(_enemyList.get(i).vector) < this.hitboxX + _enemyList.get(i).hitboxX){
				destroy(index, _projectileList);
			}
			
			//destroy(index, _projectileList);
		}
				
	}
	
	public void spawnSubProjectile(ArrayList<Projectile> _projectileList, GameObject _owner){
		
		int maxRandDist = 10;
		Random randLoot = new Random();
		float tempRandX = randLoot.nextInt(maxRandDist);
		float tempRandY = randLoot.nextInt(maxRandDist);
		Projectile subProjectile = new Projectile(_owner);
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
	
	public void moveTo(Vector2f _target){
		
		Vector2f snapshotTargetVector = new Vector2f(_target);
		Vector2f dir = new Vector2f(0.0f, 0.0f);
	
		dir = snapshotTargetVector.sub(vector);
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir);

	}
	
	public boolean isColliding(ArrayList<Enemy> _enemyList){
		
		for(int i = 0; i < _enemyList.size()-1; i++){
			if(_enemyList.size() > 0 && this.vector.distance(_enemyList.get(i).vector) < this.hitboxX + _enemyList.get(i).hitboxX){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
		
	void destroy(int index, ArrayList<Projectile> _projectileList){
		
		_projectileList.remove(index);
	}
}