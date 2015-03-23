package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Loot extends GameObject {

	protected static Color lootTestCol = new Color(255,255,0);
	
	Loot() {
			 
		super();
		hitboxX = 25.0f;
		hitboxY = 25.0f;
	}
	
	Loot(Enemy _enemy) {
		hitboxX = 25.0f;
		hitboxY = 25.0f;
	}
}
