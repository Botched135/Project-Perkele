package example;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.StateBasedGame;

public class Inventory {
	
	//VARIABLE DECLARATION
	private ArrayList <Loot> inventoryLootList = new ArrayList <Loot>();
	protected boolean toggleInventory;
	protected Player player;
		//Inventory window boundaries
		protected float xOrigin = 900;
		protected float yOrigin = 100;
		protected float height = 500;
		protected float width = 300;
		
		DecimalFormat df = new DecimalFormat("#.##");
	
	//CONTRUCTERS - Go for default only?
	Inventory(Player _player){
		
		toggleInventory = false;
		player = _player;
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {		
		
		for(int x = 0; x < 4; x++){
			for(int y = 0; y < 3; y++){
			
				System.out.println("hey");
				inventoryLootList.add(new Weapon());
				inventoryLootList.get(x+(x*y)).init(gc, sbg);
				inventoryLootList.get(x+(x*y)).vector.set(xOrigin+20+1+(64*1), yOrigin+(height/2)+35+1+(64*1));
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		
		if(gc.getInput().isKeyPressed(Input.KEY_I)){
			toggleInventory = !toggleInventory;
		}
		
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		if(toggleInventory == true){
		
			g.translate((player.vector.getX())-(Window.WIDTH/2), (player.vector.getY())-(Window.HEIGHT/2));

			//Inventory window, borders and descriptive texts:
			g.setColor(new Color(80,80,80));
			g.fillRect(xOrigin, yOrigin, width, height);
			g.setColor(new Color(50,50,50));
			g.fillRect(xOrigin+47, yOrigin+70, 70, 70);
			g.fillRect(xOrigin+187, yOrigin+70, 70, 70);
		
			for(int y = 0; y < 5; y++){
				g.fillRect(xOrigin+187, yOrigin+y*2+150+(y*25), 100, 25);
			}
		
			for(int x = 0; x < 6; x++){
				for(int y = 0; y < 5; y++){
					g.fillRect(xOrigin+12+x+(45*x), yOrigin+(height/2)+10+y+(45*y), 45, 45);
				}
			}
			g.setColor(new Color(255,255,255));
			g.drawRect(xOrigin, yOrigin, width, height);
			g.drawRect(xOrigin, yOrigin+30, width, height-30);
			g.drawRect(xOrigin, yOrigin+30, width, height-30);
			g.drawString("Inventory",xOrigin+110, yOrigin+8);
			g.drawString("Weapon",xOrigin+53, yOrigin+50);
			g.drawString("Armor",xOrigin+200, yOrigin+50);
			g.drawString("Hit Points", xOrigin+30, yOrigin+155);
			g.drawString("Attack Speed", xOrigin+30, yOrigin+180);
			g.drawString("Damage", xOrigin+30, yOrigin+205);
			g.drawString("DPS",xOrigin+30, yOrigin+230);
			g.drawString("Armor",xOrigin+30, yOrigin+255);
		
			g.drawString(df.format(player.hitPoints), xOrigin+195, yOrigin+154);
			g.drawString(df.format(player.AttackSpeed), xOrigin+195, yOrigin+180);
			g.drawString(df.format(player.MinDamage)+"-"+df.format(player.MaxDamage), xOrigin+195, yOrigin+208);
			g.drawString(df.format(((player.MinDamage+player.MaxDamage)/2)*player.AttackSpeed), xOrigin+195, yOrigin+235);
			g.drawString(df.format(player.Armor), xOrigin+195, yOrigin+260);
		
			//Draw the items in the bag
			inventoryLootList.get(1).render(gc, sbg, g);
			for(int i = 0; i < 12; i++){
				inventoryLootList.get(i).render(gc, sbg, g);
			}	
		}
	}
	
	//Adds clicked item to inventory bag space if there is enough inventory bag space
	public static void addToInventory(){
			
	}
}

