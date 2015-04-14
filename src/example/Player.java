package example;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Player extends GameObject{
	
	//VARIABLE DECLARATION ===========================================================================================================================================================
	//PLAYER STATS ===========================================
	protected float hitPoints = 100;
	protected float damage = 100;
	protected float MinDamage = 75;
	protected float MaxDamage = 125;
	protected float PlayerDamage;
	protected float meleeRange = 100;
	protected float speedMultiplier = 5.0f;
	protected float AttackSpeed = 5.0f; //Attacks per second
	protected float Armor = 0; //Damage reductions
	//=======================================================
	
	protected float isReady;
	protected boolean isAttackReady = false;
	protected long StartTime = System.currentTimeMillis();
	protected long EndTime = 0;
	
	protected boolean isMeleeAttacking;
	protected boolean isRangedAttacking;
	protected static Color playerTestCol = new Color(0,0,255);
	
	protected Random randDmg = new Random();
	
	//CONSTRUCTORS ===========================================================================================================================================================
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
	//UPDATE FUNCTION/METHOD ===========================================================================================================================================================
	public void update(GameContainer gc, StateBasedGame sbg, ArrayList<Projectile> _projectileList, ArrayList<Circle> _projectileRenderList){
		
		isMeleeAttacking = false;
		isRangedAttacking = false;

		//UPDATE PLAYER ATTACK
		setAttackReady();
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			isMeleeAttacking(GameState.mousePos);
		}
		
		//PLAYER SHOOTS ARROW TOWARDS "mousePos"
		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
			if(isAttackReady == true){
				isRangedAttacking(GameState.mousePos);
				_projectileList.add(new Arrow(this, GameState.mousePos, 10));
			
				Circle tempCircle = new Circle(_projectileList.get(_projectileList.size()-1).vector.getX(), _projectileList.get(_projectileList.size()-1).vector.getY(), _projectileList.get(_projectileList.size()-1).hitboxX);
				_projectileRenderList.add(tempCircle);
			}
		}
		
		//UPDATING PLAYER COLLISION WITH ENEMIES
		/*for(int i = 0; i<enemyList.size(); i++){
			if(player.isColliding(enemyList.get(i)))
				System.out.println("player is colliding with enemy nr. "+ i);
		}*/
		
		//PLAYER MOVEMENT INPUT
		
		if(gc.getInput().isKeyDown(Input.KEY_A)) {
			MoveSelf(new Vector2f(-1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_W)) {
			MoveSelf(new Vector2f(0f, -1.0f));
			}
		if(gc.getInput().isKeyDown(Input.KEY_D)) {
			MoveSelf(new Vector2f(1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_S)) {
			MoveSelf(new Vector2f(0.0f, 1.0f));
		}
	}
	
	
	//METHODS ===========================================================================================================================================================
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
		this.MinDamage = weap.wepMinDMG;
		this.MaxDamage = weap.wepMaxDMG;
		this.AttackSpeed = weap.attackSpeed;
		}
		else if(weap instanceof Armor){
			this.Armor=weap.Armor;
		}
	}
	public void AttackDamage(){
		PlayerDamage = (randDmg.nextFloat()*(this.MaxDamage-this.MinDamage))+this.MinDamage;
	}
}
