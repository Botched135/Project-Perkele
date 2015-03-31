package example;

import java.util.Random;

public class Weapon extends Loot{
	

	protected int wepDMG = randDmg.nextInt(100);
	protected float attackSpeed = randSpeed.nextFloat()*5;
	
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
