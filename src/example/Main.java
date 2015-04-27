package example;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame
{
	public Main(String gamename)
	{
		super(gamename);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer gc;
			gc = new AppGameContainer(new Main("Perkele"));
			gc.setDisplayMode(Window.WIDTH, Window.HEIGHT, Window.Fullscreen);
			gc.setTargetFrameRate(Window.FPS);
			gc.setVSync(true);
			gc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new Menu());
		this.addState(new GameState());
	}
}