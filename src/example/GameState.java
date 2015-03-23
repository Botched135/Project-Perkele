package example;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.*;



public class GameState extends BasicGameState {

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
		
		//VARIABLE DECLARATION
		Player player = new Player(new Vector2f(Window.WIDTH/2, Window.HEIGHT/2));
		private Circle playerTestCircle = new Circle(player.vector.getX(), player.vector.getY(), player.hitboxX);
		private Line playerToMouseTestLine = new Line(player.vector.getX(), player.vector.getY(), Mouse.getX(), Mouse.getY());
		private ArrayList <Loot> lootList = new ArrayList <Loot>();
		private ArrayList <Enemy> enemyList = new ArrayList <Enemy>();
		private ArrayList <Circle> lootRenderList = new ArrayList <Circle>();
		private ArrayList <Circle> enemyRenderList = new ArrayList <Circle>();
		private Random randLoot = new Random();
		private Random randDrop = new Random();
		private int lootDropDist = 50;
		private Vector2f mousePos;
		
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		mousePos = new Vector2f(gc.getInput().getMouseX(), gc.getInput().getMouseY());
		
		//UPDATING PLAYER
		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON))
			player.isAttacking(mousePos);
		
		//UPDATING COLLISION
		for(int i = 0; i<enemyList.size(); i++){
			if(player.isColliding(enemyList.get(i)))
				System.out.println("player is colliding with enemy nr. "+ i);
		}
		//PLAYER MOVEMENT INPUT
		if(gc.getInput().isKeyDown(Input.KEY_A)) {
			player.changeXPos(new Vector2f(-1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_W)) {
			player.changeYPos(new Vector2f(0f, -1.0f));
			}
		if(gc.getInput().isKeyDown(Input.KEY_D)) {
			player.changeXPos(new Vector2f(1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_S)) {
			player.changeYPos(new Vector2f(0.0f, 1.0f));
		}
		
		//UDATES PLAYER SPRITE
		playerTestCircle = new Circle(player.vector.getX(), player.vector.getY(), player.hitboxX); 
		playerToMouseTestLine = new Line(player.vector.getX(), player.vector.getY(), Mouse.getX(), Window.HEIGHT-Mouse.getY());
		
			
		//LOOT SPAWNING - by using "space key" as input.
		if(gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			int dropping = randDrop.nextInt(100);
			if(dropping > 50) {
				lootList.add(new Loot());
				for(int i = lootList.size()-1; i < lootList.size(); i++) {
					Loot tempLoot = lootList.get(i);
					tempLoot.vector.set(new Vector2f(randLoot.nextInt(lootDropDist), randLoot.nextInt(lootDropDist)));
	
				
					Circle tempCircle = new Circle(mousePos.getX() + (tempLoot.vector.getX())-(lootDropDist/2) , mousePos.getY() + (tempLoot.vector.getY())-(lootDropDist/2), 50f);
					lootRenderList.add(tempCircle); 
				}
			}
		}
			
		//ENEMY SPAWNING - by using "E key" as input
		if(gc.getInput().isKeyPressed(Input.KEY_E)) {
			enemyList.add(new Enemy(new Vector2f((float)Mouse.getX(), (float)(Window.HEIGHT - Mouse.getY()))));
			for(int i = enemyList.size()-1; i < enemyList.size(); i++) {					
				Circle tempCircle = new Circle(mousePos.getX(), Window.HEIGHT - mousePos.getY(), 50f);
				enemyRenderList.add(tempCircle);
			}
		}
		//UPDATING ENEMIES
		if(enemyList.size() > 0){
				
			//UPDATES ENEMY LOGIC
			for(int i = 0; i < enemyList.size(); i++) {
				enemyList.get(i).stateManager(player);
			}
				
			//UPDATES ENEMY SPRITES
			for(int i = 0; i < enemyList.size(); i++) {
				enemyRenderList.set(i, new Circle(enemyList.get(i).vector.getX(), enemyList.get(i).vector.getY(), 50f));
			}
		}
		
		//BACK TO MAIN MENU (and clears the game container) - key input is "ESCAPE"
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			gc.reinit();	//Clears the the GameContainer
			sbg.enterState(0);
		}
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		//RENDER TEXT (and miscellaneous)
		g.setColor(new Color(255,255,255));
		g.drawString("HEJ TOSSER!", 500, 200);
		g.draw(playerToMouseTestLine);
		
		
		//RENDER PLAYER
		g.setColor(Player.playerTestCol);
		g.draw(playerTestCircle);
		
		
		//RENDER ENEMY SPRITES
		if(enemyList.size() > 0){
			g.setColor(Enemy.enemyTestCol);
			for(int i = 0; i < enemyRenderList.size(); i++) {
				g.draw(enemyRenderList.get(i));
			}
		}
		
		//RENDER LOOT SPRITES
		if(lootList.size() > 0){
			g.setColor(Loot.lootTestCol);
			for(int i = 0; i < lootRenderList.size(); i++) {
				g.draw(lootRenderList.get(i));
			}
		}
	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
