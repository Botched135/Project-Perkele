package example;

public class Armor extends Loot{
	
	protected float hpBonnus;
	
	Armor() {
		 
		super();
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		ID = 3;

	}
	
	Armor(Enemy _enemy) {
		hitboxX = 50.0f;
		hitboxY = 50.0f;
		ID = 3;
	}
	
	public void getInfo() {
		
	}

}
