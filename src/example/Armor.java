package example;

public class Armor extends Loot{
	
	protected float hpBonus;
	
	Armor() {
		 
		super();
		hitboxX = 10.0f;
		hitboxY = 10.0f;
		ID = 3;

	}
	
	Armor(Enemy _enemy) {
		hitboxX = 10.0f;
		hitboxY = 10.0f;
		ID = 3;
	}
	
	public void hpBonus() {
		
	}
	
	public void getInfo() {
		
	}

}
