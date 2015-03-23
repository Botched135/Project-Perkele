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
		Player player = new Player();
		private Circle playerTestCircle = new Circle(player.getXPos(),player.getYPos(), player.hitboxX);
		private Line playerToMouseTestLine = new Line(player.getXPos(),player.getYPos(), Mouse.getX(), Mouse.getY());
		private ArrayList <Loot> lootList = new ArrayList <Loot>();
		private ArrayList <Enemy> enemyList = new ArrayList <Enemy>();
		private ArrayList <Circle> lootRenderList = new ArrayList <Circle>();
		private ArrayList <Circle> enemyRenderList = new ArrayList <Circle>();
		private Random randLoot = new Random();
		private int lootDropDist = 50;
		private float mouseXPOS,mouseYPOS;
		

	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		mouseXPOS = gc.getInput().getMouseX();
		mouseYPOS = gc.getInput().getMouseY();
		
		//UPDATING PLAYER
		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON))
			player.isAttacking(mouseXPOS,mouseYPOS);
		//PLAYER MOVEMENT INPUT
		if(gc.getInput().isKeyDown(Input.KEY_A)) {
			player.changeXPos(-1.0f);
		}
		if(gc.getInput().isKeyDown(Input.KEY_W)) {
			player.changeYPos(-1.0f);
			}
		if(gc.getInput().isKeyDown(Input.KEY_D)) {
			player.changeXPos(1.0f);
		}
		if(gc.getInput().isKeyDown(Input.KEY_S)) {
			player.changeYPos(1.0f);
		}
		
		//UDATES PLAYER SPRITE
		playerTestCircle = new Circle(player.getXPos(),player.getYPos(), player.hitboxX); 
		playerToMouseTestLine = new Line(player.getXPos(),player.getYPos(), Mouse.getX(), Window.HEIGHT-Mouse.getY());
		
			
		//LOOT SPAWNING - by using "space key" as input
		if(gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			lootList.add(new Loot());
			for(int i = lootList.size()-1; i < lootList.size(); i++) {
				Loot tempLoot = lootList.get(i);
				tempLoot.setXPos(randLoot.nextInt(lootDropDist));
				tempLoot.setYPos(randLoot.nextInt(lootDropDist));
				
				Circle tempCircle = new Circle(Mouse.getX() + tempLoot.getXPos()-(lootDropDist/2) ,Window.HEIGHT - Mouse.getY() + tempLoot.getYPos()-(lootDropDist/2), 50f);
				lootRenderList.add(tempCircle); 
			}
		}
			
		//ENEMY SPAWNING - by using "E key" as input
		if(gc.getInput().isKeyPressed(Input.KEY_E)) {
			enemyList.add(new Enemy((float)Mouse.getX(), (float)(Window.HEIGHT-Mouse.getY())));
			for(int i = enemyList.size()-1; i < enemyList.size(); i++) {					
				Circle tempCircle = new Circle(Mouse.getX(), Window.HEIGHT - Mouse.getY(), 50f);
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
				enemyRenderList.set(i, new Circle(enemyList.get(i).getXPos(), enemyList.get(i).getYPos(), 50f));
			}
		}
			
		/* Print out distance from player object to latest enemy object
		 *
		* if(enemyList.size() > 0){
		System.out.println(enemyList.get(0).distToPlayer);
		}
		*/
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
