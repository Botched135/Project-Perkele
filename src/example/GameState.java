package example;

import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
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
			private ArrayList <EnemyIndicator> enemyIndicatorList = new ArrayList <EnemyIndicator>();
			private ArrayList <Projectile> projectileList = new ArrayList <Projectile>();
			private ArrayList <healthGlobe> healthGlobeList = new ArrayList <healthGlobe>();
			private ArrayList <Loot> inventoryList = new ArrayList <Loot>();	//Inventory place 0 = Armor.	Inventory place 1 = Weapon
			private ArrayList <Vector2f> spawnPos = new ArrayList <Vector2f>();
			
			//Enemy wave system
			protected Random randPos = new Random();
			protected int spawnPosVari;
			protected int wave;
			protected int currentWave;
			protected double waveStartTimer;
			protected double waveTimeDif;
			protected boolean waveStart;
			protected int waveDelay = 5000; //Amount of miliseconds before the next wave start
			protected int enemyMeleeAmount = 2;
			protected int enemyRangedAmount = 1;
			protected int randEnemyPos;
			

			protected static Vector2f mousePos;
			
			//Images =================================================
			
			public static TiledMap bgMap = null;
			private Image playerDamageWarning = null; 
			private Image guiButtomEquipmentUnderlay = null;
			private ArrayList <Image> equippedLootList = new ArrayList <Image>();
			
			//Sounds
			//public static Sound mainTheme = null;
			
			//Misc.
			DecimalFormat df = new DecimalFormat("#.##");
			protected float waveTextOpacity = 255;
			private TrueTypeFont font;
			private boolean antiAlias = true;
			float playerDamageWarningOpacity = 0;
			float PDWcounter = 0.01f;
			public static int mapWidth = 32*(100); // <-- change the number in the parenthesis according to the amount of tiles for the maps width
			public static int mapHeight = 32*(100); // <-- change the number in the parenthesis according to the amount of tiles for the maps height
			public static int collisionLayer;
			
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		//mainTheme = new Sound("data/mainTheme.ogg");
		
		bgMap = new TiledMap("data/map.tmx");
		playerDamageWarning = new Image("data/playerDamageWarning.png");
		guiButtomEquipmentUnderlay = new Image("data/guiButtomEquipmentUnderlay.png");
		equippedLootList.add(new Image("data/armorTestSprite.png"));
		equippedLootList.add(new Image("data/meleeWeaponTestSprite.png"));
		equippedLootList.add(new Image("data/rangedWeaponTestSprite.png"));
		
		inventory.init(gc, sbg);
		player.init(gc, sbg);
		spawnPos.add(new Vector2f(0, 0));
		spawnPos.add(new Vector2f(0, 0));
		spawnPos.add(new Vector2f(0, 0));
		spawnPos.add(new Vector2f(0, 0));
		spawnPos.add(new Vector2f(0, 0));
		spawnPos.add(new Vector2f(0, 0));
		spawnPos.add(new Vector2f(0, 0));
		spawnPos.add(new Vector2f(0, 0));
		
		wave = 0;
		currentWave = 0;
		waveStartTimer = 0;
		waveTimeDif = 0;
		waveStart = true;
		
		Font awtFont = new Font("Times New Roman", Font.BOLD, 30);
		font = new TrueTypeFont(awtFont, antiAlias);
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		collisionLayer = bgMap.getLayerIndex("Collision");
		
		mousePos = new Vector2f((gc.getInput().getMouseX() + (player.vector.getX())-Window.WIDTH/2), (gc.getInput().getMouseY() + (player.vector.getY()))-Window.HEIGHT/2);	
		
		if(gc.getInput().isKeyDown(Input.KEY_M)){
		
			//Menu.menuTheme.stop();
			
		}
		

		inventory.update(gc, sbg, delta);
		
		//PLAYER STUFF ====================================================================================================================================
		player.update(gc, sbg, enemyList, projectileList, healthGlobeList);
		
		//UDATES PLAYER SPRITE
		playerTestCircle = new Circle(Window.WIDTH/2, Window.HEIGHT/2, player.hitboxX);
		playerMeleeRangeCircle = new Circle(Window.WIDTH/2, Window.HEIGHT/2, player.meleeRange);
		playerToMouseTestLine = new Line(Window.WIDTH/2, Window.HEIGHT/2, Mouse.getX(), Window.HEIGHT-Mouse.getY());
		if(player.hitPoints<=0){
			EndScreen.wave = currentWave;
			sbg.enterState(2);
		}
			
		
		//LOOT STUFF ======================================================================================================================================
		
		if(lootList.size() > 0){
			for(int i = lootList.size()-1; i >= 0; i--){
				lootList.get(i).update(i, gc, sbg, lootList, inventoryList, enemyList, player);
			}
		}
		
		//update Health Globes
		if(healthGlobeList.size() > 0){
			for(int i = healthGlobeList.size()-1; i >= 0; i--){
				healthGlobeList.get(i).update(gc, sbg, lootList);
			}
		}
		
		//EQUIPMENT
		if(inventoryList.size()<3){
			for(int i =0; i <3;i++){
				inventoryList.add(null);
			}
		inventoryList.set(0, new Armor());
		inventoryList.set(1, new Weapon());
		inventoryList.set(2, new RangedWeapon());
		}
		for(int i = 0; i <3;i++){
		if(inventoryList.get(i)!=null){
			player.setLootEquipment(inventoryList.get(i));
			//inventoryList.remove(0);
		}
		}
		
	
		
		inventoryList.get(0).vector.set(Window.WIDTH/2, Window.HEIGHT/2);
		inventoryList.get(1).vector.set(100, 100);
		inventoryList.get(2).vector.set(200,200);
		inventoryList.get(0).init(gc, sbg);
		inventoryList.get(1).init(gc, sbg);
		inventoryList.get(2).init(gc, sbg);
		//inventoryList.get(2).vector.set(600, 1000);
		
		
		//ENEMY STUFF =================================================================================================================================================	
		//Update enemy spawn pos
		spawnPos.set(0, new Vector2f(player.vector.getX() - Window.WIDTH/2 - (63 + spawnPosVari -2),player.vector.getY() - Window.HEIGHT/2 - (63 + spawnPosVari -1)));
		spawnPos.set(1, new Vector2f(player.vector.getX() + spawnPosVari -2, player.vector.getY() - Window.HEIGHT/2 - (63 + spawnPosVari -1)));
		spawnPos.set(2, new Vector2f(player.vector.getX() + Window.WIDTH/2 + (63 + spawnPosVari -2), player.vector.getY() - Window.HEIGHT/2 - (63 + spawnPosVari -1)));
		spawnPos.set(3, new Vector2f(player.vector.getX() + Window.WIDTH/2 + (63 + spawnPosVari -2), player.vector.getY() + spawnPosVari -2));
		spawnPos.set(4, new Vector2f(player.vector.getX() + Window.WIDTH/2 + (63 + spawnPosVari -2), player.vector.getY() + Window.HEIGHT/2 + (63 + spawnPosVari -1)));
		spawnPos.set(5, new Vector2f(player.vector.getX() + spawnPosVari -2, player.vector.getY() + Window.HEIGHT/2 + (63 + spawnPosVari -1)));
		spawnPos.set(6, new Vector2f(player.vector.getX() - Window.WIDTH/2 - (63 + spawnPosVari -2), player.vector.getY() + Window.HEIGHT/2 + (63 + spawnPosVari -1)));
		spawnPos.set(7, new Vector2f(player.vector.getX() - Window.WIDTH/2 - (63 + spawnPosVari -2), player.vector.getY() + spawnPosVari -2));
		
		if(gc.getInput().isKeyPressed(Input.KEY_E)) {
			enemyList.add(new Enemy(new Vector2f(mousePos.getX(), mousePos.getY()),0));
			enemyList.get(enemyList.size()-1).init(gc, sbg);
			enemyList.get(enemyList.size()-1).SetEnemyLevel(wave);
			
			enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
			enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
		}
		
		if(gc.getInput().isKeyPressed(Input.KEY_R)) {
			enemyList.add(new Enemy(new Vector2f(mousePos.getX(), mousePos.getY()),1));
			enemyList.get(enemyList.size()-1).init(gc, sbg);
			enemyList.get(enemyList.size()-1).SetEnemyLevel(wave);
			
			enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
			enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
		}

		//Enemy wave stuff
		if(waveStartTimer == 0 && enemyList.size() == 0) {
			wave++;
			waveStart = false;
			waveStartTimer = System.currentTimeMillis();
			
		}
		else {
			waveTimeDif = (System.currentTimeMillis() - waveStartTimer);
			if(waveTimeDif > waveDelay) {
				waveStart = true;
				waveStartTimer = 0;
				waveTimeDif = 0;
			}
		}
		if(waveStart && enemyList.size() == 0) { //Spawning of a wave
			//for loop for spawning melee enemies
			for(int i = 0; i < enemyMeleeAmount; i++) {
				randEnemyPos = randPos.nextInt(8);
				spawnPosVari = randPos.nextInt(5);
				enemyList.add(new Enemy(spawnPos.get(randEnemyPos),0)); //<-- last argument is the type of enemy to spawn
				enemyList.get(enemyList.size()-1).init(gc, sbg);
				enemyList.get(enemyList.size()-1).SetEnemyLevel(wave);
				
				enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
				enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
			}
			//for loop for spawning ranged enemies
			for(int i = 0; i < enemyRangedAmount; i++) {
				randEnemyPos = randPos.nextInt(8);
				spawnPosVari = randPos.nextInt(5);
				enemyList.add(new Enemy(spawnPos.get(randEnemyPos),1)); //<-- last argument is the type of enemy to spawn
				enemyList.get(enemyList.size()-1).init(gc, sbg);
				enemyList.get(enemyList.size()-1).SetEnemyLevel(wave);
				
				enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
				enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
			}
			enemyMeleeAmount += 2;
			enemyRangedAmount++;
		}
		
		//UPDATING ENEMIES
		if(enemyList.size() > 0){
			for(int i = enemyList.size()-1; i >= 0; i--) {
		
				enemyList.get(i).update(i, gc, sbg, delta, player, enemyList, projectileList, lootList, healthGlobeList, enemyIndicatorList);

			}
		}
		
		//PROJECTILES STUFF =================================================================================================================
		
				if(projectileList.size() > 0){
					for(int i = projectileList.size()-1; i >= 0; i--){
					
						projectileList.get(i).stateManager(player, i, projectileList, enemyList);
					}
				}
		
				
		
		//ENEMY INDICATOR UPDATE
				
				if(enemyIndicatorList.size() > 0){
					for(int i = enemyIndicatorList.size()-1; i >= 0; i--) {
						
						enemyIndicatorList.get(i).update(player, enemyList.get(i).vector, gc, sbg, delta);

					}
				}
		//======================================================================================================================================================
		//BACK TO MAIN MENU (and clears the game container) - key input is "ESCAPE"
		
			if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
				//gc.reinit();	//Clears the GameContainer
				sbg.enterState(0);
		}
	}
	
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
		bgMap.render(0,0);
		g.translate((player.vector.getX())-(Window.WIDTH/2), (player.vector.getY())-(Window.HEIGHT/2));
		
		//inventory.render(gc, sbg, g);

		
		//RENDER TEXT (and miscellaneous)
		g.setColor(new Color(255,255,255));
		g.drawString("Number of enemies: " + enemyList.size(), 10, 50);
		g.drawString("Number of loot: " + lootList.size(), 10, 65);
		g.drawString("Melee Attack is Ready: "+player.setAttackReady(), 10, 80);
		g.drawString("Ranged Attack is Ready: "+player.setRangedAttackReady(),10,95);

		//g.drawString("Player Damage: "+player.PlayerDamage, 10, 140);
		for(int i = enemyList.size()-1; i >= 0; i-- ){
			if(GameState.mousePos.distance(enemyList.get(i).vector) < enemyList.get(i).hitboxX){

				g.drawString(enemyList.get(i).EnemyName, (Window.WIDTH/2)-(4*enemyList.get(i).EnemyName.length()), 10);
				g.setColor(new Color(255,0,0));
				g.drawRect(Window.WIDTH/2 - 110, 30, 230.7f, 15);
				g.fillRect(Window.WIDTH/2 - 110, 30,230.7f*(enemyList.get(i).hitpoints/enemyList.get(i).maxHitpoints), 15);
				g.drawRect(Window.WIDTH/2 - 110, 30,230.7f*(enemyList.get(i).hitpoints/enemyList.get(i).maxHitpoints), 15);
				System.out.println(enemyList.get(i).hitpoints/enemyList.get(i).maxHitpoints);
				g.setColor(new Color(255,255,255));
				break;
			}
			
		}
		
		/*
		g.drawString("Hit Points:                " + player.hitPoints, 1000, 40);
		g.drawString("Attack Speed(Att pr. sec): " +player.AttackSpeed, 1000, 55);
		g.drawString("Damage:                    " +player.damage, 1000, 70);
		g.drawString("DPS:                       " +player.damage*player.AttackSpeed, 1000, 85);
		*/
		
		//TRANSLATE (move "camera") ACCORDING TO PLAYER MOVEMENT ============================================================================================
		
		//ENEMY INDICATOR SPRITES
		
		if(enemyIndicatorList.size() > 0){
			for(int i = enemyIndicatorList.size()-1; i >= 0; i--) {
				if(		enemyList.get(i).vector.getX() > player.vector.getX() + Window.WIDTH/2 ||
						enemyList.get(i).vector.getX() < player.vector.getX() - Window.WIDTH/2 ||
						enemyList.get(i).vector.getY() < player.vector.getY() - Window.HEIGHT/2 ||
						enemyList.get(i).vector.getY() > player.vector.getY() + Window.HEIGHT/2
						)
					
					enemyIndicatorList.get(i).render(gc, sbg, g);

			}
		}
		
		g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
		
		//RENDER PROJECTILE SPRITES
		if(projectileList.size() > 0){
			g.setColor(Arrow.arrowTestCol);
			for(int i = projectileList.size()-1; i >= 0; i--) {
				projectileList.get(i).render(gc, sbg, g);
				//g.draw(projectileRenderList.get(i));
			}
		}
		g.setColor(new Color(255,255,255));
		
		//RENDER ENEMY SPRITES ==============================================================================================================================
		
		if(enemyList.size() > 0){
			for(int i = enemyList.size()-1; i >= 0; i--) {
				enemyList.get(i).render(gc, sbg, g);
			}
		}
		
		//RENDER LOOT SPRITES ==============================================================================================================================
		if(lootList.size() > 0){
			//g.setColor(Loot.lootTestCol);
			for(int i = lootList.size()-1; i >= 0; i--) {
				lootList.get(i).render(i, gc, sbg, g);
			}
		}
		
		if(healthGlobeList.size() > 0){
			//g.setColor(Loot.lootTestCol);
			for(int i = healthGlobeList.size()-1; i >= 0; i--) {
				healthGlobeList.get(i).render(i, gc, sbg, g);
			}
		}
		
		
		//RENDER PLAYER DAMAGE WARNING =================================================================================================================
		
		if(player.hitPoints < player.MaxHitPoints*0.2){
			
			g.translate((player.vector.getX())-(Window.WIDTH/2), (player.vector.getY())-(Window.HEIGHT/2));
			
			if(playerDamageWarningOpacity > 1){
				PDWcounter = PDWcounter*(-1);
			}
			
			if(playerDamageWarningOpacity < 0){
				PDWcounter = PDWcounter*(-1);
			}
			playerDamageWarningOpacity += PDWcounter;
			
			playerDamageWarning.draw(0,0, new Color(255,255,255, 1 - playerDamageWarningOpacity));
			
			g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
		}
		
		//RENDER INVENTORY ===========================================================================================================================
		
		inventory.render(gc, sbg, g);
		inventoryList.get(0).render(gc, sbg, g);
		inventoryList.get(1).render(gc, sbg, g);
		inventoryList.get(2).render(gc, sbg, g);
		
		//RENDER PLAYER ==============================================================================================================================
		
		player.render(gc, sbg, g);
		g.setColor(new Color(0,255,255));
		//g.draw(playerTestCircle);
		g.setColor(new Color(255,255,0,80));
		//g.draw(playerMeleeRangeCircle);
		//g.draw(playerToMouseTestLine);
		
		//RENDER Loot info on "shift" hovering ========================================================================================================
		g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
		
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			
			//g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
			
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
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
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
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-122, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-122, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								
								g.setColor(new Color(255,255,255));
								g.drawString("Armor: " + (int)lootList.get(i).Armor, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-122 );
								g.drawString("Liferegen: " + lootList.get(i).lifeRegen, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-102);
								g.drawString("HpBonus: " + (int)lootList.get(i).hpBonus, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-82);
							}
							if(lootList.get(i) instanceof RangedWeapon){
								g.setColor(new Color(255,255,255));
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY());
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
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY());
							}
							
							if(lootList.get(i) instanceof Armor){
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								
								g.setColor(new Color(255,255,255));
								g.drawString("Armor: " + (int)lootList.get(i).Armor, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								g.drawString("Liferegen: " + lootList.get(i).lifeRegen, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								g.drawString("HpBonus: " + (int)lootList.get(i).hpBonus, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-0);
							}
							if(lootList.get(i) instanceof RangedWeapon){
								g.setColor(new Color(255,255,255));
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, 180, 60); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY());
							}
						}
					}
				}
			}	
		}
		
		if(currentWave < wave) {
			waveTextOpacity = 2;
			currentWave = wave;
		}
		if(waveTextOpacity > 0){
			waveTextOpacity -= 0.01f;
			g.setColor(new Color(255, 255, 255, waveTextOpacity));
			font.drawString(player.vector.getX()  - 92, player.vector.getY() - 120, "- W A V E - " + wave);
		}
		
		g.translate((player.vector.getX())-(Window.WIDTH/2), (player.vector.getY())-(Window.HEIGHT/2));
		if(player.meleeWepID == 4){
			equippedLootList.get(2).draw(48, 640);
		}
		
		guiButtomEquipmentUnderlay.draw(18, 618);
		
		if(player.meleeWepID == 4){
			equippedLootList.get(1).draw(18, 618);
		}
		if(player.armorID == 3){
			equippedLootList.get(0).draw(1200, 617);
		} 
		
	}
	public int getID() {
		return 1;			// The ID of this state
	}

}
