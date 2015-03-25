package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Loot extends GameObject {

	protected static Color lootTestCol = new Color(255,255,0);
	
	//CONTRUCTERS
	Loot() {
			 
		super();
		hitboxX = 50.0f;
		hitboxY = 50.0f;
	}
	
	Loot(Enemy _enemy) {
		hitboxX = 50.0f;
		hitboxY = 50.0f;
	}
	
	
	void stateManager(Player _player){
		
		//No states at this point (might never be any - if thats the case, remove this method
	}
}
