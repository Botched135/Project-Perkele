package example;

import org.newdawn.slick.geom.*;
import org.newdawn.slick.Color;

public class Loot extends GameObject {

	protected static Color lootTestCol = new Color(255,255,0);
	

	
	public boolean pickUp(Player _player) {
		if(GameState.mousePos.distance(vector) < hitboxX && vector.distance(_player.vector) < _player.meleeRange + hitboxX){
			return true;
		}
		else {
			return false;
		}
	}
	
	
	void stateManager(Player _player){
		
		//No states at this point (might never be any - if thats the case, remove this method
	}
}
