package example;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Blood extends GameObject {
	
	//Images
	private Image smallBlood = null;
	private Image bigBlood = null;
	
	Blood(Enemy _enemy, int dead){
		if(dead == 0) //if they are alive
		this.vector = new Vector2f(_enemy.vector.getX()-16, _enemy.vector.getY()-16);
		else
			this.vector = new Vector2f(_enemy.vector.getX(), _enemy.vector.getY());
	}
	public void init() throws SlickException{
		
		smallBlood = new Image("data/rangedWeaponTestSprite.png");
		bigBlood = new Image("data/meleeWeaponTestSprite.png");
	}
	public void update(){
		
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		smallBlood.draw(vector.getX()-16, vector.getY()-16);
		bigBlood.draw(vector.getX()-32, vector.getY()-32);
	}
}
