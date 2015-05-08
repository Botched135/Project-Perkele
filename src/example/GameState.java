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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.geom.*;



public class GameState extends BasicGameState {
	
	//VARIABLE DECLARATION
			Player player = new Player(new Vector2f(mapBoundWidth/2, mapBoundHeight/2));
			private Inventory inventory = new Inventory(player);
			private ArrayList <Loot> lootList = new ArrayList <Loot>();
			private ArrayList <Enemy> enemyList = new ArrayList <Enemy>();
			private ArrayList <EnemyIndicator> enemyIndicatorList = new ArrayList <EnemyIndicator>();
			private ArrayList <Projectile> projectileList = new ArrayList <Projectile>();
			private ArrayList <healthGlobe> healthGlobeList = new ArrayList <healthGlobe>();
			private ArrayList <Loot> inventoryList = new ArrayList <Loot>();	//Inventory place 0 = Armor.	Inventory place 1 = Weapon
			private ArrayList <Vector2f> spawnPos = new ArrayList <Vector2f>();
			
			//Enemy wave system
			private Random randPos = new Random();
			private int spawnPosVari;
			private int wave;
			private int currentWave;
			private double waveStartTimer;
			private double waveTimeDif;
			private boolean waveStart;
			private int waveDelay = 5000; //Amount of miliseconds before the next wave start
			private int enemyMeleeAmount = 2;
			private int enemyRangedAmount = 1;
			private int randEnemyPos;
			public static int bossLevel = 2;
			

			protected static Vector2f mousePos;
			
			//Images =================================================
			
			private static TiledMap bgMap = null;
			private Image playerDamageWarning = null; 
			private Image guiButtomEquipmentUnderlay = null;
			private Image[] playerEquippedMeleeWepList = new Image[6];
			private Image[] playerEquippedRangedWepList = new Image[6];
			private Image[] playerEquippedArmorList = new Image[6];
			
			
			//Sounds
			public static Sound mainTheme = null;
			private Sound evilLaugh = null;
			private Sound endScream = null;
			
			//Misc.
			public static boolean devMode = false;
			private DecimalFormat df = new DecimalFormat("#.##");
			private float waveTextOpacity = 255;
			private TrueTypeFont font;
			private boolean antiAlias = true;
			float playerDamageWarningOpacity = 0;
			float PDWcounter = 0.01f;
			public static boolean wepSwap = false;
			public static int mapWidth = 32*(119); // <-- change the number in the parenthesis according to the amount of tiles for the maps width
			public static int mapHeight = 32*(80);
			public static int mapBoundWidth = mapWidth - (32*2*(20)); // <-- change the number in the parenthesis according to the amount of tiles for the maps width
			public static int mapBoundHeight = mapHeight - (32*2*(11)); // <-- change the number in the parenthesis according to the amount of tiles for the maps height
			public static int collisionLayer;
			

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		Menu.resetGame = false;

		mainTheme = new Sound("data/mainTheme.wav");
		evilLaugh = new Sound("data/evilLaugh.wav");
		endScream = new Sound("data/endScream.wav");
		
		
		bgMap = new TiledMap("data/map.tmx");
		playerDamageWarning = new Image("data/playerDamageWarning.png");
		guiButtomEquipmentUnderlay = new Image("data/guiButtomEquipmentUnderlay.png");
		
		playerEquippedMeleeWepList[0] = new Image("data/meleeWeapon0.png");
		playerEquippedMeleeWepList[1] = new Image("data/meleeWeapon1.png");
		playerEquippedMeleeWepList[2] = new Image("data/meleeWeapon2.png");
		playerEquippedMeleeWepList[3] = new Image("data/meleeWeapon3.png");
		playerEquippedMeleeWepList[4] = new Image("data/meleeWeapon4.png");
		playerEquippedMeleeWepList[5] = new Image("data/meleeWeapon5.png");
		
		playerEquippedRangedWepList[0] = new Image("data/rangedWeapon0.png");
		playerEquippedRangedWepList[1] = new Image("data/rangedWeapon1.png");
		playerEquippedRangedWepList[2] = new Image("data/rangedWeapon2.png");
		playerEquippedRangedWepList[3] = new Image("data/rangedWeapon3.png");
		playerEquippedRangedWepList[4] = new Image("data/rangedWeapon4.png");
		playerEquippedRangedWepList[5] = new Image("data/rangedWeapon5.png");
		
		playerEquippedArmorList[0] = new Image("data/armorSprite0.png");
		playerEquippedArmorList[1] = new Image("data/armorSprite1.png");
		playerEquippedArmorList[2]= new Image("data/armorSprite2.png");
		playerEquippedArmorList[3]= new Image("data/armorSprite3.png");
		playerEquippedArmorList[4] = new Image("data/armorSprite4.png");
		playerEquippedArmorList[5] = new Image("data/armorSprite5.png");
		
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
		
		Font awtFont = new Font("Times New Roman", Font.BOLD, 30);
		font = new TrueTypeFont(awtFont, antiAlias);
		
	}
	//UPDATE FUNCTIONS ==================================================================================================================================
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		if(gc.getInput().isKeyPressed(Input.KEY_O)) {
			devMode = !devMode;
		}
		
		if(Menu.resetGame == true){
			
			player = null;
			player = new Player(new Vector2f(mapBoundWidth/2, mapBoundHeight/2));
			inventory = null;
			inventory = new Inventory(player);
			lootList = null;
			lootList = new ArrayList <Loot>();
			enemyList = null;
			enemyList = new ArrayList <Enemy>();
			enemyIndicatorList = null;
			enemyIndicatorList = new ArrayList <EnemyIndicator>();
			projectileList = null;
			projectileList = new ArrayList <Projectile>();
			healthGlobeList = null;
			healthGlobeList = new ArrayList <healthGlobe>();
			inventoryList = null;
			inventoryList = new ArrayList <Loot>();	//Inventory place 0 = Armor.	Inventory place 1 = Weapon			
			
			Player.armorID = 0;
			Player.meleeWepID = 0;
			Player.rangedWepID = 0;
			
			inventory.init(gc, sbg);
			player.init(gc, sbg);
			
			wave = 0;
			currentWave = 0;
			waveStartTimer = 0;
			waveTimeDif = 0;
			waveStart = true;
			
			Menu.resetGame = false;
		}
		
		collisionLayer = bgMap.getLayerIndex("Collision");
		
		mousePos = new Vector2f((gc.getInput().getMouseX() + (player.vector.getX())-Window.WIDTH/2), (gc.getInput().getMouseY() + (player.vector.getY()))-Window.HEIGHT/2);	
		
		if(gc.getInput().isKeyDown(Input.KEY_M)){
			Menu.menuTheme.stop();
		}
		
		//PLAYER STUFF ====================================================================================================================================
		player.update(gc, sbg, enemyList, projectileList, healthGlobeList);
		
		//UDATES PLAYER SPRITE
		if(player.hitPoints<=0){
			EndScreen.wave = currentWave;
			endScream.play();
			mainTheme.stop();
			sbg.enterState(2, new FadeOutTransition(new Color(135,7,10),1200), new FadeInTransition(new Color(135,7,10),2500));
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
		//Updating the 8 different spawn positions enemies can spawn from based on the players position.
		spawnPos.set(0, new Vector2f(player.vector.getX() - Window.WIDTH/2 - (63 + spawnPosVari -2),player.vector.getY() - Window.HEIGHT/2 - (63 + spawnPosVari -1)));
		spawnPos.set(1, new Vector2f(player.vector.getX() + spawnPosVari -2, player.vector.getY() - Window.HEIGHT/2 - (63 + spawnPosVari -1)));
		spawnPos.set(2, new Vector2f(player.vector.getX() + Window.WIDTH/2 + (63 + spawnPosVari -2), player.vector.getY() - Window.HEIGHT/2 - (63 + spawnPosVari -1)));
		spawnPos.set(3, new Vector2f(player.vector.getX() + Window.WIDTH/2 + (63 + spawnPosVari -2), player.vector.getY() + spawnPosVari -2));
		spawnPos.set(4, new Vector2f(player.vector.getX() + Window.WIDTH/2 + (63 + spawnPosVari -2), player.vector.getY() + Window.HEIGHT/2 + (63 + spawnPosVari -1)));
		spawnPos.set(5, new Vector2f(player.vector.getX() + spawnPosVari -2, player.vector.getY() + Window.HEIGHT/2 + (63 + spawnPosVari -1)));
		spawnPos.set(6, new Vector2f(player.vector.getX() - Window.WIDTH/2 - (63 + spawnPosVari -2), player.vector.getY() + Window.HEIGHT/2 + (63 + spawnPosVari -1)));
		spawnPos.set(7, new Vector2f(player.vector.getX() - Window.WIDTH/2 - (63 + spawnPosVari -2), player.vector.getY() + spawnPosVari -2));
		
		//Swapping active weapon
		if(gc.getInput().isKeyPressed(Input.KEY_Q)) {
			wepSwap = !wepSwap;
		}
		
		if(devMode == true){
			gc.setShowFPS(true);
		}
		else{
			gc.setShowFPS(false);
		}
		
		//This satement for spawning enemies are to be removed
		if(gc.getInput().isKeyPressed(Input.KEY_E) && devMode == true) {
			enemyList.add(new Enemy(new Vector2f(mousePos.getX(), mousePos.getY()),0));
			enemyList.get(enemyList.size()-1).init(gc, sbg);
			enemyList.get(enemyList.size()-1).SetEnemyLevel(currentWave);
			
			enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
			enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
		}
		//This satement for spawning enemies are to be removed
		if(gc.getInput().isKeyPressed(Input.KEY_R) && devMode == true) {
			enemyList.add(new Enemy(new Vector2f(mousePos.getX(), mousePos.getY()),1));
			enemyList.get(enemyList.size()-1).init(gc, sbg);
			enemyList.get(enemyList.size()-1).SetEnemyLevel(currentWave);
			
			enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
			enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
		}

		//Enemy wave counter which activates when the timer reaches a set amount of time.
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
			//Detecting if a boss should be spawned instead
			if(wave%10 == 0){ //Detecting if the wave should be a boss wave
				randEnemyPos = randPos.nextInt(8); //Choosing one of the 8 spawn positions based on random int.
				spawnPosVari = randPos.nextInt(5); //Used to variate the position slightly
				boolean enemySpawned = false;
				while(enemySpawned == false){		//Detecting if the current random spawn position is outside the arena, and if it is chose a new one.			
					if(	spawnPos.get(randEnemyPos).getX() <= 0 ||
							spawnPos.get(randEnemyPos).getX() >= mapBoundWidth ||
							spawnPos.get(randEnemyPos).getY() <= 0 ||
							spawnPos.get(randEnemyPos).getY() >= mapBoundHeight) { 
						randEnemyPos = randPos.nextInt(8);
					}
					else{ //Spawning of the boss
						enemyList.add(new Enemy(spawnPos.get(randEnemyPos),2)); //<-- last argument is the type of enemy to spawn
						enemyList.get(enemyList.size()-1).init(gc, sbg);
						enemyList.get(enemyList.size()-1).SetEnemyLevel(wave, bossLevel);
						enemySpawned = true;
						enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
						enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
						bossLevel++;
					}
				}
			}
			else{ //If the wave aint a boss wave, spawn regular enemies instead
				//loop for spawning melee enemies
				for(int i = 0; i < enemyMeleeAmount; i++) {
					randEnemyPos = randPos.nextInt(8);
					spawnPosVari = randPos.nextInt(5);
					boolean enemySpawned = false;
					while(enemySpawned == false){					
						if(	spawnPos.get(randEnemyPos).getX() <= 0 ||
								spawnPos.get(randEnemyPos).getX() >= mapBoundWidth ||
								spawnPos.get(randEnemyPos).getY() <= 0 ||
								spawnPos.get(randEnemyPos).getY() >= mapBoundHeight) { 
								randEnemyPos = randPos.nextInt(8);
						}
						else{
							Vector2f tempPos = new Vector2f(spawnPos.get(randEnemyPos));
							for(int j = 0; j < enemyList.size()-1; j++){ //Detecting if the enemy spawned prior is at the same place as the new one. If it is move the pos a little
								if(tempPos.getX() == enemyList.get(j).vector.getX() && tempPos.getY() == enemyList.get(j).vector.getY()){
									tempPos.add(new Vector2f(3,2));
								}
							}
							enemyList.add(new Enemy(tempPos,0)); //<-- last argument is the type of enemy to spawn
							enemyList.get(enemyList.size()-1).init(gc, sbg);
							enemyList.get(enemyList.size()-1).SetEnemyLevel(wave);
							enemySpawned = true;
							enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
							enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
						}
					}				
				}
				//loop for spawning ranged enemies
				for(int i = 0; i < enemyRangedAmount; i++) {
					randEnemyPos = randPos.nextInt(8);
					spawnPosVari = randPos.nextInt(5);
					boolean enemySpawned = false;
					while(enemySpawned == false){					
						if(	spawnPos.get(randEnemyPos).getX() <= 0 ||
								spawnPos.get(randEnemyPos).getX() >= mapBoundWidth ||
								spawnPos.get(randEnemyPos).getY() <= 0 ||
								spawnPos.get(randEnemyPos).getY() >= mapBoundHeight) { 
								randEnemyPos = randPos.nextInt(8);
						}
						else{
							Vector2f tempPos = new Vector2f(spawnPos.get(randEnemyPos));
							for(int j = 0; j < enemyList.size()-1; j++){
								if(tempPos.getX() == enemyList.get(j).vector.getX() && tempPos.getY() == enemyList.get(j).vector.getY()){
									tempPos.add(new Vector2f(3,2));
								}	
							}
							enemyList.add(new Enemy(tempPos,1)); //<-- last argument is the type of enemy to spawn
							enemyList.get(enemyList.size()-1).init(gc, sbg);
							enemyList.get(enemyList.size()-1).SetEnemyLevel(wave);
							enemySpawned = true;
							enemyIndicatorList.add(new EnemyIndicator(player, enemyList.get(enemyList.size()-1).vector));
							enemyIndicatorList.get(enemyIndicatorList.size()-1).init(gc, sbg);
						}
					}	
				}
				if(wave%2 == 0){ //Detecting if ranged enemies or melee enemies should be incremented
					enemyMeleeAmount++;
				}
				else{
					enemyRangedAmount++;
				}
				
			}
		}
		
		//UPDATING ENEMY CLASS
		if(enemyList.size() > 0){
			for(int i = enemyList.size()-1; i >= 0; i--) {
		
				enemyList.get(i).update(i, gc, sbg, delta, player, enemyList, projectileList, lootList, healthGlobeList, enemyIndicatorList);

			}
		}
		
		//PROJECTILES STUFF =================================================================================================================
		
				if(projectileList.size() > 0){
					for(int i = projectileList.size()-1; i >= 0; i--){
					
						projectileList.get(i).update(i, projectileList);
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
		bgMap.render(32*(-20), 32*(-11));
		g.translate((player.vector.getX())-(Window.WIDTH/2), (player.vector.getY())-(Window.HEIGHT/2));
		
		//inventory.render(gc, sbg, g);

		
		//RENDER TEXT (and miscellaneous)
		g.setColor(new Color(0,0,0));
		g.drawString("Wave Number: " + wave, 5, 30); //Displaying the wave number
		if(devMode == true){
			g.drawString("DEVELOPER MODE ACTIVATED!", 5, 50);
		}

		for(int i = enemyList.size()-1; i >= 0; i-- ){
			if(GameState.mousePos.distance(enemyList.get(i).vector) < enemyList.get(i).hitboxX){

				g.drawString(enemyList.get(i).EnemyName, (Window.WIDTH/2)-(4*enemyList.get(i).EnemyName.length()), 10);
				g.setColor(new Color(255,0,0));
				g.drawRect(Window.WIDTH/2 - 110, 30, 230.7f, 15);
				g.fillRect(Window.WIDTH/2 - 110, 30,230.7f*(enemyList.get(i).hitpoints/enemyList.get(i).maxHitpoints), 15);
				g.drawRect(Window.WIDTH/2 - 110, 30,230.7f*(enemyList.get(i).hitpoints/enemyList.get(i).maxHitpoints), 15);
				g.setColor(new Color(255,255,255));
				break;
			}
			
		}
		
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
			for(int i = projectileList.size()-1; i >= 0; i--) {
				projectileList.get(i).render(gc, sbg, g);
			}
		}
		g.setColor(new Color(255,255,255));
		
		//RENDER ENEMY SPRITES ==============================================================================================================================
		
		if(enemyList.size() > 0){
			for(int i = 0; i <= enemyList.size()-1; i++) {
				enemyList.get(i).render(gc, sbg, g, player);
			}
		}
		
		//RENDER LOOT SPRITES ==============================================================================================================================
		if(lootList.size() > 0){
			for(int i = lootList.size()-1; i >= 0; i--) {
				lootList.get(i).render(i, gc, sbg, g);
			}
		}
		if(healthGlobeList.size() > 0){
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

		//RENDER Loot info on "shift" hovering ========================================================================================================
		g.translate((-player.vector.getX())+(Window.WIDTH/2), (-player.vector.getY())+(Window.HEIGHT/2));
		
		if(gc.getInput().isKeyDown(Input.KEY_LSHIFT)){
			
			if(lootList.size() > 0){
				for(int i = lootList.size()-1; i >= 0; i--) {
	
					if(mousePos.distance(lootList.get(i).vector) < lootList.get(i).hitboxX){
						
						if(lootList.get(i).vector.getY() - player.vector.getY() >= 170){
							
							g.setColor(new Color(70,70,70));
							if(lootList.get(i).Name.length() > 15){
								g.drawRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, (float)(9.4*lootList.get(i).Name.length())+50, 22);
								g.setColor(new Color(70,70,70));
								g.fillRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, (float)(9.4*lootList.get(i).Name.length())+50, 22);
							}
							else{
							g.drawRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 214, 22);
							g.setColor(new Color(70,70,70));
							g.fillRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 214, 22);
							}
							if(lootList.get(i) instanceof Weapon){
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								if(lootList.get(i).Name.length() > 15){
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()+(-22*lootList.get(i).numberOfStats-56), (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame. 20 pixels pr. attribute!
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()+(-22*lootList.get(i).numberOfStats-56), (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								else{
									g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-(41*lootList.get(i).numberOfStats), (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame. 20 pixels pr. attribute!
									g.setColor(new Color(100,100,100));
									g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-(41*lootList.get(i).numberOfStats), (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								g.setColor(new Color(255,255,255));
								if((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2 >= (player.playerMeleeMinDamage + player.playerMeleeMaxDamage)/2){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()+((lootList.get(i).numberOfStats*-22)-56));
								if((lootList.get(i).attackSpeed >= player.playerMeleeAttackSpeed)){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()+((lootList.get(i).numberOfStats*-20)-42));
								if(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2) * lootList.get(i).attackSpeed >= ((player.playerMeleeMinDamage + player.playerMeleeMaxDamage)/2)* player.playerMeleeAttackSpeed){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()+((lootList.get(i).numberOfStats*-20)-22));

								if(lootList.get(i).Vamp>0){
							        if((lootList.get(i).Vamp > player.playerVamp)){
							    	  g.setColor(new Color(0,255,0));
							        }
							        else{
							    	  g.setColor(new Color(255,0,0));
							    }
							        g.drawString("Life Steal: "+df.format(((lootList.get(i).Vamp))), lootList.get(i).vector.getX()+40, lootList.get(i).vector.getY()+((lootList.get(i).numberOfStats*-20)-2));
								}
							}
							
							if(lootList.get(i) instanceof Armor){
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								if(lootList.get(i).Name.length() > 15){
									g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()+(-22*lootList.get(i).numberOfStats-56), (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame. 20 pixels pr. attribute!
									g.setColor(new Color(100,100,100));
									g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()+(-22*lootList.get(i).numberOfStats-56), (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
									}
								else{
									g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-(41*lootList.get(i).numberOfStats), (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame. 20 pixels pr. attribute!
									g.setColor(new Color(100,100,100));
									g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-(41*lootList.get(i).numberOfStats), (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								
								g.setColor(new Color(255,255,255));
								if(lootList.get(i).Armor >= player.Armor){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Armor: " + (int)lootList.get(i).Armor, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-122 );
								
								 if(lootList.get(i).lifeRegen >= player.lifeRegen){
								 	g.setColor(new Color(0,255,0));
								 }
								 else{
									 g.setColor(new Color(255,0,0));
								}
								g.drawString("Liferegen: " + lootList.get(i).lifeRegen, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-102);
								
								if(lootList.get(i).hpBonus >0){
								if(lootList.get(i).hpBonus >= player.MaxHitPoints - player.baseHp){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("HpBonus: " + (int)lootList.get(i).hpBonus, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-82);
								}
							}
							if(lootList.get(i) instanceof RangedWeapon){
								g.setColor(new Color(255,255,255));
				
									if(lootList.get(i).Name.length() > 15){
										g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()+(-22*lootList.get(i).numberOfStats-56), (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame. 20 pixels pr. attribute!
										g.setColor(new Color(100,100,100));
										g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()+(-22*lootList.get(i).numberOfStats-56), (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
										}
									else{
										g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-(41*lootList.get(i).numberOfStats), (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame. 20 pixels pr. attribute!
										g.setColor(new Color(100,100,100));
										g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-(41*lootList.get(i).numberOfStats), (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
									}
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								if((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2 >= (player.playerRangedMinDamage + player.playerRangedMaxDamage)/2){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()+((lootList.get(i).numberOfStats*-22)-56));
								if((lootList.get(i).attackSpeed >= player.playerRangedAttackSpeed)){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()+((lootList.get(i).numberOfStats*-20)-42));
								if(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2) * lootList.get(i).attackSpeed >= ((player.playerRangedMinDamage + player.playerRangedMaxDamage)/2)* player.playerRangedAttackSpeed){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()+((lootList.get(i).numberOfStats*-20)-22));
							}
						}
						
						else{
							
							g.setColor(new Color(70,70,70));
							if(lootList.get(i).Name.length() > 15){
								g.drawRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, (float)(9.4*lootList.get(i).Name.length())+50, 22);
								g.setColor(new Color(70,70,70));
								g.fillRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, (float)(9.4*lootList.get(i).Name.length())+50, 22);
							}
							else{
							g.drawRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 214, 22);
							g.setColor(new Color(70,70,70));
							g.fillRect(lootList.get(i).vector.getX()-25, lootList.get(i).vector.getY()-62, 214, 22);
							}
							
							if(lootList.get(i) instanceof Weapon){
								
								g.setColor(new Color(255,255,255));
								if(lootList.get(i).Name.length() > 15){
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								else{
									g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
									g.setColor(new Color(100,100,100));
									g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								if((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2 >= (player.playerMeleeMinDamage + player.playerMeleeMaxDamage)/2){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								if((lootList.get(i).attackSpeed >= player.playerMeleeAttackSpeed)){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								if(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2) * lootList.get(i).attackSpeed >= ((player.playerMeleeMinDamage + player.playerMeleeMaxDamage)/2) * player.playerMeleeAttackSpeed){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY());
								if(lootList.get(i).Vamp >0){
								if((lootList.get(i).Vamp >= player.playerVamp)){
							    	g.setColor(new Color(0,255,0));
							    }
							    else{
							    	g.setColor(new Color(255,0,0));
							    }
								
							    g.drawString("Life Steal: "+df.format(((lootList.get(i).Vamp))), lootList.get(i).vector.getX()+40, lootList.get(i).vector.getY()+20);
								}
							}
							
							if(lootList.get(i) instanceof Armor){
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								if(lootList.get(i).Name.length() >15){
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								else{
									g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
									g.setColor(new Color(100,100,100));
									g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								
								g.setColor(new Color(255,255,255));
								if(lootList.get(i).Armor >= player.Armor){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Armor: " + (int)lootList.get(i).Armor, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								if(lootList.get(i).lifeRegen >= player.lifeRegen){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Liferegen: " + lootList.get(i).lifeRegen, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								if(lootList.get(i).hpBonus >= player.MaxHitPoints - player.baseHp){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("HpBonus: " + (int)lootList.get(i).hpBonus, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-0);
							}
							if(lootList.get(i) instanceof RangedWeapon){
								g.setColor(new Color(255,255,255));
								if(lootList.get(i).Name.length() > 15){
								g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								g.setColor(new Color(100,100,100));
								g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (float)(9.4*lootList.get(i).Name.length())-14, 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								else{
									g.drawRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
									g.setColor(new Color(100,100,100));
									g.fillRect(lootList.get(i).vector.getX()+39, lootList.get(i).vector.getY()-40, (9.4f*17.5f-14), 20*lootList.get(i).numberOfStats); // <--- change the last parameter (Y-height) to accommodate the number of attributes that needs fitting inside the frame
								}
								
								g.setColor(new Color(255,255,255));
								g.drawString(lootList.get(i).Name+" lvl:"+lootList.get(i).lootLevel, lootList.get(i).vector.getX()-24, lootList.get(i).vector.getY()-61);
								if((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2 >= (player.playerRangedMinDamage + player.playerRangedMaxDamage)/2){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Damage: " + (int)lootList.get(i).wepMinDMG +" - "+ (int)lootList.get(i).wepMaxDMG, lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-40);
								if((lootList.get(i).attackSpeed >= player.playerRangedAttackSpeed)){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("Att. Speed: " + df.format(lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY()-20);
								if(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2) * lootList.get(i).attackSpeed >= (player.playerRangedMinDamage + player.playerRangedMaxDamage)/2 * player.playerRangedAttackSpeed){
									g.setColor(new Color(0,255,0));
								}
								else{
									g.setColor(new Color(255,0,0));
								}
								g.drawString("DPS: " + df.format(((lootList.get(i).wepMinDMG + lootList.get(i).wepMaxDMG)/2)*lootList.get(i).attackSpeed), lootList.get(i).vector.getX()+40 , lootList.get(i).vector.getY());
							}
						}
					}
				}
			}	
		}
		
		if(currentWave < wave) { 
			waveTextOpacity = 2; //alpha value used to display start of new wave
			currentWave = wave;
		}
		if(waveTextOpacity > 0){ //If statement for displaying a new wave
			waveTextOpacity -= 0.01f;
			g.setColor(new Color(255, 255, 255, waveTextOpacity));
			font.drawString(player.vector.getX()  - 92, player.vector.getY() - 120, "- W A V E - " + wave);
		}
		
		g.translate((player.vector.getX())-(Window.WIDTH/2), (player.vector.getY())-(Window.HEIGHT/2));
		
		if(wepSwap == false){
			playerEquippedRangedWepList[Player.rangedWepID].draw(48, 640);
			guiButtomEquipmentUnderlay.draw(18, 618);
			playerEquippedMeleeWepList[Player.meleeWepID].draw(18, 618);
		}
		else{
			playerEquippedMeleeWepList[Player.meleeWepID].draw(48, 640);
			guiButtomEquipmentUnderlay.draw(18, 618);
			playerEquippedRangedWepList[Player.rangedWepID].draw(18, 618);
			}
			
			playerEquippedArmorList[Player.armorID].draw(1200, 617);
		
	}
	public int getID() {
		return 1;			// The ID of this state
	}

}
