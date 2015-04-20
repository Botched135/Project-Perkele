package example;

import java.text.DecimalFormat;
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
	
	//VARIABLE DECLARATION
			Player player = new Player(new Vector2f(Window.WIDTH/2, Window.HEIGHT/2));
			private Circle playerTestCircle = new Circle(player.vector.getX(), player.vector.getY(), player.hitboxX);
			private Circle playerMeleeRangeCircle = new Circle(player.vector.getX(), player.vector.getY(), 150);
			private Line playerToMouseTestLine = new Line(player.vector.getX(), player.vector.getY(), Mouse.getX(), Mouse.getY());
			private Inventory inventory = new Inventory(player);
			private ArrayList <Loot> lootList = new ArrayList <Loot>();
			private ArrayList <Enemy> enemyList = new ArrayList <Enemy>();
			private ArrayList <Projectile> projectileList = new ArrayList <Projectile>();
			private ArrayList <Circle> projectileRenderList = new ArrayList <Circle>();
			private ArrayList <Circle> enemyRenderList = new ArrayList <Circle>();
			private ArrayList <Loot> inventoryList = new ArrayList <Loot>();	//Inventory place 0 = Armor.	Inventory place 1 = Weapon

			protected static Vector2f mousePos;
			
			//Misc.
			DecimalFormat df = new DecimalFormat("#.##");

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		inventory.init(gc, sbg);
		player.init(gc, sbg);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		mousePos = new Vector2f((gc.getInput().getMouseX() + (player.vector.getX())-Window.WIDTH/2), (gc.getInput().getMouseY() + (player.vector.getY()))-Window.HEIGHT/2);	
		
		inventory.update(gc, sbg, delta);
		
		//PLAYER STUFF ====================================================================================================================================
		player.update(gc, sbg, projectileList, projectileRenderList);
		
		//UDATES PLAYER SPRITE
		playerTestCircle = new Circle(Window.WIDTH/2, Window.HEIGHT/2, player.hitboxX);
		playerMeleeRangeCircle = new Circle(Window.WIDTH/2, Window.HEIGHT/2, player.meleeRange);
		playerToMouseTestLine = new Line(Window.WIDTH/2, Window.HEIGHT/2, Mouse.getX(), Window.HEIGHT-Mouse.getY());
			
		
		//LOOT STUFF ======================================================================================================================================
		
		if(lootList.size() > 0){
			for(int i = lootList.size()-1; i >= 0; i--){
				lootList.get(i).update(i, gc, sbg, lootList, inventoryList, player);
			}
		}
		//EQUIPMENT
		if(inventoryList.size()<2){
		inventoryList.add(null);//Initially just setting the inventory to null to avoid crashing. 
		inventoryList.add(null);//I'm pretty sure that there is an easier way, but they have to be initialized before we can do anything.
		inventoryList.set(0, new Armor());
		inventoryList.set(1, new Weapon());
		}
		if(inventoryList.get(0)!=null){
			player.setLootEquipment(inventoryList.get(0));
			//inventoryList.remove(0);
		}
		
		if(inventoryList.get(1)!=null){
			player.setLootEquipment(inventoryList.get(1));
		}
		
		inventoryList.get(0).vector.set(Window.WIDTH/2, Window.HEIGHT/2);
		inventoryList.get(1).vector.set(100, 100);
		inventoryList.get(0).init(gc, sbg);
		inventoryList.get(1).init(gc, sbg);
		//inventoryList.get(2).vector.set(600, 1000);
		
		System.out.println("X: " + inventoryList.get(0).vector.getX() + "    Y: " + inventoryList.get(0).vector.getY());
		
		//ENEMY STUFF =================================================================================================================================================	
		//ENEMY!!!!!! - by using "E key" as input
		if(gc.getInput().isKeyPressed(Input.KEY_E)) {
			enemyList.add(new Enemy(new Vector2f(mousePos.getX(),  mousePos.getY())));
			enemyList.get(enemyList.size()-1).init(gc, sbg);
			enemyList.get(enemyList.size()-1).SetEnemyLevel();
			for(int i = enemyList.size()-1; i < enemyList.size(); i++) {					
				Circle tempCircle = new Circle(mousePos.getX(), Window.HEIGHT - mousePos.getY(), enemyList.get(i).hitboxX);
				enemyRenderList.add(tempCircle);
			}
		}
		//UPDATING ENEMIES
		if(enemyList.size() > 0){
		
			for(int i = enemyList.size()-1; i >= 0; i--) {
				enemyList.get(i).update(i, gc, sbg, delta, player, enemyList, projectileList, lootList);
				
			}
				
			//UPDATES ENEMY SPRITES
			for(int i = 0; i < enemyList.size(); i++) {
				enemyRenderList.set(i, new Circle(enemyList.get(i).vector.getX(), enemyList.get(i).vector.getY(), enemyList.get(i).hitboxX));
			}
		}
		
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
		
		//======================================================================================================================================================
		//BACK TO MAIN MENU (and clears the game container) - key input is "ESCAPE"
		if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			gc.reinit();	//Clears the the GameContainer
			sbg.enterState(0);
		}
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	
		//inventory.render(gc, sbg, g);

		
		//RENDER TEXT (and miscellaneous)
		g.setColor(new Color(255,255,255));
		g.drawString("Number of enemies: " + enemyList.size(), 10, 50);
		g.drawString("Number of loot: " + lootList.size(), 10, 65);
		g.draw(playerToMouseTestLine);
		g.drawString("Attack is Ready: "+player.setAttackReady(), 10, 80);
		g.drawString("Press 'I'" + "" + " to toggle inventory", 10, 95);
		g.drawString("Player Damage: "+player.PlayerDamage, 10, 140);
		for(int i = enemyList.size()-1; i >= 0; i-- ){
			if(GameState.mousePos.distance(enemyList.get(i).vector) < enemyList.get(i).hitboxX){

				g.drawString(enemyList.get(i).EnemyName, (Window.WIDTH/2)-(4*enemyList.get(i).EnemyName.length()), 10);
				g.setColor(new Color(255,0,0));
				g.drawRect(Window.WIDTH/2 - 110, 30, 3*(76.9f), 15);
				g.fillRect(Window.WIDTH/2 - 110, 30,(3*(enemyList.get(i).hitpoints/enemyList.get(i).EnemyLevel)/1.3f), 15);
				g.drawRect(Window.WIDTH/2 - 110, 30,(3*(enemyList.get(i).hitpoints/enemyList.get(i).EnemyLevel)/1.3f), 15);
				g.setColor(new Color(255,255,255));
				break;
			}
			
		}
		if(inventoryList.get(1)!=null)
		g.drawString("Loot Level on equiped weapon: "+inventoryList.get(1).lootLevel, 10, 110);
		if(enemyList.size()>0)
			g.drawString("Enemy Level: "+enemyList.get(enemyList.size()-1).EnemyLevel, 10, 125);	
		
		/*
		g.drawString("Hit Points:                " + player.hitPoints, 1000, 40);
		g.drawString("Attack Speed(Att pr. sec): " +player.AttackSpeed, 1000, 55);
		g.drawString("Damage:                    " +player.damage, 1000, 70);
		g.drawString("DPS:                       " +player.damage*player.AttackSpeed, 1000, 85);
		*/
		
		//TRANSLATE (move "camera") ACCORDING TO PLAYER MOVEMENT ============================================================================================
		
		g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
		
		//RENDER PROJECTILE SPRITES
		if(projectileList.size() > 0){
			g.setColor(Arrow.arrowTestCol);
			for(int i = projectileList.size()-1; i >= 0; i--) {
				g.draw(projectileRenderList.get(i));
			}
		}
		g.setColor(new Color(255,255,255));
		
		//RENDER ENEMY SPRITES ==============================================================================================================================
		
		if(enemyList.size() > 0){
			for(int i = enemyList.size()-1; i >= 0; i--) {
				enemyList.get(i).render(i, gc, sbg, g, player);
			}
		}
		
		//RENDER LOOT SPRITES ==============================================================================================================================
		if(lootList.size() > 0){
			//g.setColor(Loot.lootTestCol);
			for(int i = lootList.size()-1; i >= 0; i--) {
				lootList.get(i).render(i, gc, sbg, g);
			}
			/*
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
					g.drawString("lvl:" + lootList.get(i).lootLevel, lootList.get(i).vector.getX()-10, lootList.get(i).vector.getY()-17);
					g.drawString("A" + Integer.toString(i), lootList.get(i).vector.getX() -5, lootList.get(i).vector.getY() -5);
				}
				else if(lootList.get(i).ID == 4) {
					g.drawString("lvl:" + lootList.get(i).lootLevel, lootList.get(i).vector.getX()-10, lootList.get(i).vector.getY()-17);
					g.drawString("W" + Integer.toString(i), lootList.get(i).vector.getX() -5, lootList.get(i).vector.getY() -5);
				}
				
			}
		}
		*/
		}
		
		//RENDER INVENTORY ===========================================================================================================================
		
		inventory.render(gc, sbg, g);
		inventoryList.get(0).render(gc, sbg, g);
		inventoryList.get(1).render(gc, sbg, g);
		
		//RENDER PLAYER ==============================================================================================================================
		
		player.render(gc, sbg, g);
		g.setColor(new Color(0,255,255));
		g.draw(playerTestCircle);
		g.setColor(new Color(255,255,0,80));
		g.draw(playerMeleeRangeCircle);
		
		//RENDER Loot info on "shift" hovering ========================================================================================================
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			
			g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
			
			if(lootList.size() > 0){
				for(int i = lootList.size()-1; i >= 0; i--) {
	
					if(mousePos.distance(lootList.get(i).vector) < lootList.get(i).hitboxX){
						
						if(lootList.get(i).vector.getY() - player.vector.getY() >= 170){
							
							g.setColor(new Color(70,70,70));
							g.drawRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 244, 22);
							g.setColor(new Color(70,70,70));
							g.fillRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 244, 22);
							
							if(lootList.get(i) instanceof Weapon){
								
								g.setColor(new Color(255,255,255));
								g.drawString("Item Name ##", lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-123, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame. 20 pixels pr. attribute!
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-123, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
																		
								g.setColor(new Color(255,255,255));
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-123);
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-103);
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-83);
							}
							
							if(lootList.get(i) instanceof Armor){
								
								g.setColor(new Color(255,255,255));
								g.drawString("Item Name ##", lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-82, 180, 20); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-82, 180, 20); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								
								g.setColor(new Color(255,255,255));
								g.drawString("Armor: " + (int)lootList.get(i).Armor, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-82 );
							}
						}
						
						else{
							
							g.setColor(new Color(70,70,70));
							g.drawRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 244, 22);
							g.setColor(new Color(70,70,70));
							g.fillRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 244, 22);
							
							if(lootList.get(i) instanceof Weapon){
								
								g.setColor(new Color(255,255,255));
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								
								g.setColor(new Color(255,255,255));
								g.drawString("Item Name ##", lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY());
							}
							
							if(lootList.get(i) instanceof Armor){
								
								g.setColor(new Color(255,255,255));
								g.drawString("Item Name ##", lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 20); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 20); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								
								g.setColor(new Color(255,255,255));
								g.drawString("Armor: " + (int)lootList.get(i).Armor, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
							}
						}
					}
				}
			}
		}
	}


	public int getID() {
		return 1;			// The ID of this state
	}

}
