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
		private Circle playerMeleeRangeCircle = new Circle(player.vector.getX(), player.vector.getY(), 150);
		private Line playerToMouseTestLine = new Line(player.vector.getX(), player.vector.getY(), Mouse.getX(), Mouse.getY());
		private ArrayList <Loot> lootList = new ArrayList <Loot>();
		private ArrayList <Enemy> enemyList = new ArrayList <Enemy>();
		private ArrayList <Circle> lootRenderList = new ArrayList <Circle>();
		private ArrayList <Circle> enemyRenderList = new ArrayList <Circle>();
		private Random randLoot = new Random();
		private Random randDrop = new Random();
		private int lootDropDist = 50;
		protected static Vector2f mousePos;
		
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		mousePos = new Vector2f(gc.getInput().getMouseX(), gc.getInput().getMouseY());
		
		//UPDATING PLAYER
		
		player.isAttacking = true;
		
		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON))
			player.isAttacking(mousePos);
		
		//UPDATING PLAYER COLLISION WITH ENEMIES
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
		playerMeleeRangeCircle = new Circle(player.vector.getX(), player.vector.getY(), player.meleeRange);
		playerToMouseTestLine = new Line(player.vector.getX(), player.vector.getY(), Mouse.getX(), Window.HEIGHT-Mouse.getY());
		
			
		//LOOT SPAWNING - by using "space key" as input.
		if(gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			int dropping = randDrop.nextInt(100);
			if(dropping > 50) {
				lootList.add(new Loot());
				for(int i = lootList.size()-1; i < lootList.size(); i++) {
					Loot tempLoot = lootList.get(i);
					
					float tempRandX = randLoot.nextInt(lootDropDist);
					float tempRandY = randLoot.nextInt(lootDropDist);
					float tempX = mousePos.getX() + (tempRandX)-(lootDropDist/2);
					float tempY = mousePos.getY() + (tempRandY)-(lootDropDist/2);
					
					tempLoot.vector.set(new Vector2f(tempX, tempY));				
					Circle tempCircle = new Circle(tempX , tempY, tempLoot.hitboxX);
					lootRenderList.add(tempCircle); 
				}
			}
		}
			
		//ENEMY SPAWNING - by using "E key" as input
		if(gc.getInput().isKeyPressed(Input.KEY_E)) {
			enemyList.add(new Enemy(new Vector2f((float)Mouse.getX(), (float)(Window.HEIGHT - Mouse.getY()))));
			for(int i = enemyList.size()-1; i < enemyList.size(); i++) {					
				Circle tempCircle = new Circle(mousePos.getX(), Window.HEIGHT - mousePos.getY(), enemyList.get(i).hitboxX);
				enemyRenderList.add(tempCircle);
			}
		}
		//UPDATING ENEMIES
		if(enemyList.size() > 0){
			
			//UPDATES ENEMY LOGIC
			for(int i = enemyList.size()-1; i >= 0; i--) {
				System.out.println("mouseX: " + mousePos.getX() + "   mouseY: " + mousePos.getY() +"   enemyX: " + enemyList.get(i).vector.getX() +"   enemyY: " + enemyList.get(i).vector.getY());
				enemyList.get(i).stateManager(player);
				
			}
				
			//KILL ENEMIES (remove them from array list)
			for(int i = enemyList.size()-1; i >= 0; i--) {
				System.out.println("enemy[" + i + "] hitpoints: " + enemyList.get(i).hitpoints);
				if(enemyList.get(i).hitpoints <= 0){
					enemyList.remove(i);
					//for(int j = i; j < enemyList.size()-1; j++){
						//enemyList.set(j,enemyList.get(j+1));
					//}
				}
			}
				
			//UPDATES ENEMY SPRITES
			for(int i = 0; i < enemyList.size(); i++) {
				enemyRenderList.set(i, new Circle(enemyList.get(i).vector.getX(), enemyList.get(i).vector.getY(), enemyList.get(i).hitboxX));
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
		g.drawString("Red = idle", 10, 25);
		g.drawString("Green = hovered", 10, 40);
		g.drawString("White = collide with", 10, 55);
		g.drawString("Yellow player ring = melee range", 10, 70);
		g.drawString("Number of enemies: " + enemyList.size(), 10, 85);
		g.drawString("Number of loot: " + lootList.size(), 10, 100);
		g.draw(playerToMouseTestLine);
		
		
		//RENDER PLAYER
		g.setColor(new Color(0,255,255));
		g.draw(playerTestCircle);
		g.setColor(new Color(255,255,0,80));
		g.draw(playerMeleeRangeCircle);
		
		
		//RENDER ENEMY SPRITES
		if(enemyList.size() > 0){
			g.setColor(Enemy.enemyTestCol);
			for(int i = enemyList.size()-1; i >= 0; i--) {
				
				//Set hover color
				if(mousePos.distance(enemyList.get(i).vector) < enemyList.get(i).hitboxX){
					
					g.setColor(new Color(0,255,0));
					
				}
				//set collision color
				else if(player.isColliding(enemyList.get(i)) == true){
					
					g.setColor(new Color(255,255,255));
					
				}
				else{
					
					g.setColor(Enemy.enemyTestCol);
				}
				g.draw(enemyRenderList.get(i));
				g.drawString(Integer.toString(i), enemyList.get(i).vector.getX(), enemyList.get(i).vector.getY());
			}
		}
		
		//RENDER LOOT SPRITES
		if(lootList.size() > 0){
			g.setColor(Loot.lootTestCol);
			for(int i = lootList.size()-1; i >= 0; i--) {
				
				//Set hover color
				if(mousePos.distance(lootList.get(i).vector) < lootList.get(i).hitboxX){
					
					g.setColor(new Color(0,255,0));
					
				}
				//set collision color
				else if(player.isColliding(lootList.get(i)) == true){
					
					g.setColor(new Color(255,255,255));
					
				}
				else{
					
					g.setColor(Loot.lootTestCol);
					
				}
					
				g.draw(lootRenderList.get(i));
				g.drawString(Integer.toString(i), lootList.get(i).vector.getX(), lootList.get(i).vector.getY());
			}
		}
	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
