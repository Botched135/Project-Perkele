package example;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class healthGlobe extends Loot {
	
	private Image healthGlobeSprite = null;
	//protected float hpBonus = 10;
	
	healthGlobe() {
		 
		super();
		hitboxX = 32.0f;
		hitboxY = 32.0f;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
			healthGlobeSprite = new Image("data/healthGlobeSprite.png");
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, ArrayList<Loot> _lootList){
		
		separate(_lootList);
	}
	
	public void render(int index, GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
			healthGlobeSprite.draw(vector.getX()-32, vector.getY()-32);
	}
	
	//METHODS
	void separate(ArrayList<Loot> _lootList){

		float desiredSeparation = hitboxX*2;
		Vector2f sum = new Vector2f(0.0f, 0.0f);
		int count = 0;
		
		for(int i = 0; i < _lootList.size(); i++){
			
			float dist = vector.distance(_lootList.get(i).vector);
			if(dist > 0 && dist < desiredSeparation){
				Vector2f tempVec1 = new Vector2f(_lootList.get(i).vector.getX(), _lootList.get(i).vector.getY());
				Vector2f tempVec2 = new Vector2f(vector.getX(), vector.getY());
				Vector2f diff = tempVec2.sub(tempVec1);
				diff.normalise();
				//diff.scale(1/dist);
				sum.add(diff);
				count ++;
			}
			if(count > 0){
				sum.scale(1/count);
				sum.normalise();
				sum.scale(speedMultiplier);				
				vector.add(sum);
			}
		}
	}
	
}
