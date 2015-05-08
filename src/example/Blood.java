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
	
	private int type = 0;
	
	Blood(Enemy _enemy, int dead){
		if(dead == 0){ //if they are alive
		this.vector = new Vector2f(_enemy.vector.getX()-16, _enemy.vector.getY()-16);		}
		else{
			this.vector = new Vector2f(_enemy.vector.getX(), _enemy.vector.getY());
		}
		type = dead;
		
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		
		smallBlood = new Image("data/smallBlood.png");
		bigBlood = new Image("data/bigBlood.png");
		System.out.println("Hurr");
	}
	public void update(GameContainer gc, StateBasedGame sbg){
		
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		if(type == 0){
		smallBlood.draw(GameState.mousePos.getX(), GameState.mousePos.getY());

		System.out.println("Hurr");
		}
		else{
		bigBlood.draw(GameState.mousePos.getX(), GameState.mousePos.getY());
		}
	}
}
