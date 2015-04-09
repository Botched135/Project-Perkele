package example;

import java.util.Random;

public class Weapon extends Loot{
	

	//protected int wepDMG = (randDmg.nextInt(100))*LootLevel;
	//protected float attackSpeed = (randSpeed.nextFloat())*2*LootLevel;
	
	Weapon() {
		 
		super();
		hitboxX = 10.0f;
		hitboxY = 10.0f;
		ID = 4;
		wepDMG = 20+LootLevel*(randDmg.nextInt(80));
		attackSpeed = 0.5f+2*LootLevel*(randSpeed.nextFloat());
		

	}
	
	Weapon(Enemy _enemy) {
		hitboxX = 10.0f;
		hitboxY = 10.0f;
		ID = 4;
	}
	
	

}
