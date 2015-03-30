package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Enemy extends GameObject {
	
	//VARIABLE DECLARATION
	
	protected float hitpoints = 100;
	protected float speedMultiplier = 0.5f;
	protected Vector2f steering;
	protected float AttackSpeed = 1;
	protected long StartTime = System.currentTimeMillis();
	protected long EndTime = 0;
	protected boolean isAttackReady = false;

	protected static Color enemyTestCol = new Color(255,0,0);
	
	//CONTRUCTERS
	
	Enemy(){
		
		super(); 
		ID = 2;
		steering = new Vector2f(0.0f, 0.0f);
	}
	
	Enemy(Vector2f _vector) {
		 
		super(_vector);
	 
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		ID = 2;
		steering = new Vector2f(0.0f, 0.0f);
		
		//Makes sure that the entire sprite (test circle at this point) is inside the window when spawned.
		
		float tempX;
		float tempY;
		
		if(_vector.getX() > Window.WIDTH - hitboxX){
			
			tempX = Window.WIDTH - hitboxX;
		}
		else if(_vector.getX() < 0 + hitboxX){
			
			tempX = hitboxX;
		}
		else{
			
			tempX = _vector.getX();
		}

		if(_vector.getY() > Window.HEIGHT - hitboxY){
			
			tempY = Window.HEIGHT - hitboxY;
		
		}
		else if(_vector.getY() < hitboxY){
			tempY = hitboxY;
			
		}
		else{
			
			tempY = _vector.getY();
		}
		
		_vector.set(tempX, tempY);
	}
	
	//METHODS
	
	//stateManager chooses the state of the Enemy based on certain criteria
	void stateManager(Player _player){
			
		beingMeleeAttacked(_player);
		
		if(vector.distance(_player.vector) < 300){
		
		Vector2f temp = new Vector2f(_player.vector.getX(), _player.vector.getY());
		seekState(_player.vector);
		_player.vector.set(temp.getX(), temp.getY()); 
		}
	}
	
	//fleeState makes the enemy seek out the player
	void seekState(Vector2f _target){
				
		MoveTo(_target);
		
	}
	
	public void MoveTo(Vector2f _target){
		
		Vector2f dir = new Vector2f(0.0f, 0.0f);
	
		dir = _target.sub(vector);
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir);

	}
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
	
	void beingMeleeAttacked (Player _player){
		
		if(_player.isAttacking && GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX){
			
			this.hitpoints -= _player.damage;
			//System.out.println("ATTACKED!");
		}
	}
}
