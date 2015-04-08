package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Player extends GameObject{
	
	//PLAYER STATS ===========================================
	protected float hitPoints = 100;
	protected float damage = 100;
	protected float meleeRange = 100;
	protected float speedMultiplier = 5.0f;
	protected float AttackSpeed = 5.0f; //Attacks per second
	//=======================================================
	
	protected float isReady;
	protected boolean isAttackReady = false;
	protected long StartTime = System.currentTimeMillis();
	protected long EndTime = 0;
	
	protected boolean isMeleeAttacking;
	protected boolean isRangedAttacking;
	protected static Color playerTestCol = new Color(0,0,255);
	
	Player() {
		
		super();
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		ID = 1;
		
	}
	
	Player(Vector2f _vector) {
		 
		super(_vector);
		
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		ID = 1;
	}
	
	public void MoveSelf(Vector2f _target){
		
		_target = _target.add(vector);
		
		Vector2f dir = _target.sub(vector);
		
		dir.normalise();
		dir = dir.scale(speedMultiplier);	
		vector = vector.add(dir); 
	}
	
	public void isMeleeAttacking(Vector2f vector){
		if(this.isAttackReady){	
			//System.out.println("Debugging: X = " + vector.getX() + " & Y = " + vector.getX());
			this.isMeleeAttacking = true;
			isAttackReady=false;
		}
		else
			this.isMeleeAttacking = false;
	}
	
	public void isRangedAttacking(Vector2f vector){
		if(this.isAttackReady){	
			//System.out.println("Debugging: X = " + vector.getX() + " & Y = " + vector.getX());
			this.isRangedAttacking = true;
			isAttackReady=false;
		}
		else
			this.isRangedAttacking = false;
	}
	
	public boolean setAttackReady(){//End time - StartTime = CD. If CD >= 1000 then move on 
		if(this.isAttackReady == false){ 
			this.EndTime = System.currentTimeMillis();//StartTime should start from without
			if((this.EndTime-this.StartTime) >= 1000/AttackSpeed){
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
	//Setting the weapon loot to the player
	public void setLootEquipment(Loot weap){
		if(weap instanceof Weapon){
		this.damage = weap.wepDMG;
		this.AttackSpeed = weap.attackSpeed;
		}
		else if(weap instanceof Armor){
			this.hitPoints=weap.hpBonus;
		}
	}
}
