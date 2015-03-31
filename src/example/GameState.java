package example;

import java.util.ArrayList;

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
		private ArrayList <Projectile> projectileList = new ArrayList <Projectile>();
		private ArrayList <Circle> lootRenderList = new ArrayList <Circle>();
		private ArrayList <Circle> projectileRenderList = new ArrayList <Circle>();
		private ArrayList <Circle> enemyRenderList = new ArrayList <Circle>();
		private ArrayList <Loot> inventoryList = new ArrayList <Loot>();	//Inventory place 0 = Armor.	Inventory place 1 = Weapon

		protected static Vector2f mousePos;
		
	
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		mousePos = new Vector2f((gc.getInput().getMouseX() + (player.vector.getX())-Window.WIDTH/2), (gc.getInput().getMouseY() + (player.vector.getY()))-Window.HEIGHT/2);
		System.out.println("MouseX: " + mousePos.getX() + "   Y: " + mousePos.getY());
		

		
		player.isAttacking = false;

		//UPDATE PLAYER ATTACK
		player.setAttackReady();
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			player.isAttacking(mousePos);
		}
		
		//PLAYER SHOOTS ARROW AT "mousePos"
		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
			player.isAttacking(mousePos);
			projectileList.add(new Arrow(player, mousePos, 6));
			
			Circle tempCircle = new Circle(projectileList.get(projectileList.size()-1).vector.getX(), projectileList.get(projectileList.size()-1).vector.getY(), projectileList.get(projectileList.size()-1).hitboxX);
			projectileRenderList.add(tempCircle);
		}
		
		//UPDATING PLAYER COLLISION WITH ENEMIES
		/*for(int i = 0; i<enemyList.size(); i++){
			if(player.isColliding(enemyList.get(i)))
				System.out.println("player is colliding with enemy nr. "+ i);
		}*/
		
		//PLAYER MOVEMENT INPUT
		
		if(gc.getInput().isKeyDown(Input.KEY_A)) {
			player.MoveSelf(new Vector2f(-1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_W)) {
			player.MoveSelf(new Vector2f(0f, -1.0f));
			}
		if(gc.getInput().isKeyDown(Input.KEY_D)) {
			player.MoveSelf(new Vector2f(1.0f, 0f));
		}
		if(gc.getInput().isKeyDown(Input.KEY_S)) {
			player.MoveSelf(new Vector2f(0.0f, 1.0f));
		}
		
		//UDATES PLAYER SPRITE
		playerTestCircle = new Circle(Window.WIDTH/2, Window.HEIGHT/2, player.hitboxX);
		playerMeleeRangeCircle = new Circle(Window.WIDTH/2, Window.HEIGHT/2, player.meleeRange);
		playerToMouseTestLine = new Line(Window.WIDTH/2, Window.HEIGHT/2, Mouse.getX(), Window.HEIGHT-Mouse.getY());
				
				
		//PROJECTILES STUFF =================================================================================================================
		
		if(projectileList.size() > 0){
			for(int i = projectileList.size()-1; i >= 0; i--){
			
				projectileList.get(i).stateManager(i, projectileList, enemyList);
			}
			//UPDATES PROJECTILE SPRITES
			for(int i = projectileList.size()-1; i >= 0; i--) {
				projectileRenderList.set(i, new Circle(projectileList.get(i).vector.getX(), projectileList.get(i).vector.getY(), projectileList.get(i).hitboxX));
			}
		}
		
		//LOOT STUFF ======================================================================================================================================
		//LOOT!!!!! - by using "space key" as input and picking it up using "V".
		if(gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			Loot.spawnLoot(lootList, lootRenderList);
		}
		
		for(int i = 0; i < lootList.size()-1; i++){
			lootList.get(i).stateManager(player, lootList, lootRenderList, gc, i+1);
		}
		
		if(lootList.size() > 0) {
			if(gc.getInput().isKeyPressed(Input.KEY_V)) {
				for(int i = lootList.size()-1; i >= 0; i--) {
					if(lootList.get(i).pickUp(player) == true) {
							//either a method for picking up armor or a weapon
						if(lootList.get(i).ID ==4){
							inventoryList.add(1,lootList.get(i));
						}
						else if(lootList.get(i).ID ==3){
							inventoryList.add(0,lootList.get(i));
						}
							lootList.remove(i);
					}
				}
			}
			for(int i = 0; i < lootList.size(); i++) {
				lootRenderList.set(i, new Circle(lootList.get(i).vector.getX(), lootList.get(i).vector.getY(), lootList.get(i).hitboxX));
			}
		}
		//EQUIPMENT
		if(inventoryList.size()<2){
		inventoryList.add(null);//Initially just setting the inventory to null to avoid crashing. 
		inventoryList.add(null);//Im pretty sure that there is an easier way, but they have to be initialized before we can do anything.
		}
		if(inventoryList.get(0)!=null){
			player.setLootEquipment(inventoryList.get(0));
			inventoryList.remove(0);
		}
		
		if(inventoryList.get(1)!=null){
			player.setLootEquipment(inventoryList.get(1));
		}
		
		
		//ENEMY STUFF =================================================================================================================================================	
		//ENEMY!!!!!! - by using "E key" as input
		if(gc.getInput().isKeyPressed(Input.KEY_E)) {
			enemyList.add(new Enemy(new Vector2f(mousePos.getX(),  mousePos.getY())));
			for(int i = enemyList.size()-1; i < enemyList.size(); i++) {					
				Circle tempCircle = new Circle(mousePos.getX(), Window.HEIGHT - mousePos.getY(), enemyList.get(i).hitboxX);
				enemyRenderList.add(tempCircle);
			}
		}
		//UPDATING ENEMIES
		if(enemyList.size() > 0){
			
			//UPDATES ENEMY LOGIC
			for(int i = enemyList.size()-1; i >= 0; i--) {
				enemyList.get(i).stateManager(player, enemyList);
				
			}
				
			//KILL ENEMIES (remove them from array list)
			for(int i = enemyList.size()-1; i >= 0; i--) {
				if(enemyList.get(i).hitpoints <= 0){
					enemyList.remove(i);

				}
			}
				
			//UPDATES ENEMY SPRITES
			for(int i = 0; i < enemyList.size(); i++) {
				enemyRenderList.set(i, new Circle(enemyList.get(i).vector.getX(), enemyList.get(i).vector.getY(), enemyList.get(i).hitboxX));
			}
		}
		
		//======================================================================================================================================================
		//BACK TO MAIN MENU (and clears the game container) - key input is "ESCAPE"
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			gc.reinit();	//Clears the the GameContainer
			sbg.enterState(0);
		}
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		//RENDER TEXT (and miscellaneous)
		g.setColor(new Color(255,255,255));
		g.drawString("Number of enemies: " + enemyList.size(), 10, 50);
		g.drawString("Number of loot: " + lootList.size(), 10, 65);
		g.draw(playerToMouseTestLine);
		g.drawString("Attack is Ready: "+player.setAttackReady(), 10, 80);
		g.drawString("The player is attacking : "+player.isAttacking, 10, 95);
		g.drawString("Hit Points:                " + player.hitPoints, 1000, 40);
		g.drawString("Attack Speed(Att pr. sec): " +player.AttackSpeed, 1000, 55);
		g.drawString("Damage:                    " +player.damage, 1000, 70);
		g.drawString("DPS:                       " +player.damage*player.AttackSpeed, 1000, 85);
		
		
		//RENDER PLAYER ==============================================================================================================================
		g.setColor(new Color(0,255,255));
		g.draw(playerTestCircle);
		g.setColor(new Color(255,255,0,80));
		g.draw(playerMeleeRangeCircle);
		
		//TRANSLATE (move "camera") ACCORDING TO PLAYER MOVEMENT ============================================================================================
		
		g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
		
		//RENDER PROJECTILE SPRITES
		if(projectileList.size() > 0){
			g.setColor(Arrow.arrowTestCol);
			for(int i = projectileList.size()-1; i >= 0; i--) {
				g.draw(projectileRenderList.get(i));
			}
		}
		
		//RENDER ENEMY SPRITES ==============================================================================================================================
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
		
		//RENDER LOOT SPRITES ==============================================================================================================================
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
				if(lootList.get(i).ID == 3) {
					g.drawString("A" + Integer.toString(i), lootList.get(i).vector.getX() -5, lootList.get(i).vector.getY() -5);
				}
				else if(lootList.get(i).ID == 4) {
					g.drawString("W" + Integer.toString(i), lootList.get(i).vector.getX() -5, lootList.get(i).vector.getY() -5);
				}
				
			}
		}
	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
