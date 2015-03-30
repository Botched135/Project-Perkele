package example;

public class Weapon extends Loot{
	
	protected float wepDMG = 50;
	protected float attackSpeed=0.2f;
	
	Weapon() {
		 
		super();
		hitboxX = 10.0f;
		hitboxY = 10.0f;
		ID = 4;

	}
	
	Weapon(Enemy _enemy) {
		hitboxX = 10.0f;
		hitboxY = 10.0f;
		ID = 4;
	}
	
	

}
