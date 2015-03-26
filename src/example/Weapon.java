package example;

public class Weapon extends Loot{
	
	protected float wepDMG;
	protected float attackSpeed;
	
	Weapon() {
		 
		super();
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		ID = 4;

	}
	
	Weapon(Enemy _enemy) {
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		ID = 4;
	}

}
