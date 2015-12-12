package game;
import menu.ServerMenu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class SpaceGameController extends StateBasedGame {

	private SpaceGame game;
	private ServerMenu menu;
	
	public SpaceGameController() {
		super("Space Andy");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		menu = new ServerMenu();
		game = new SpaceGame();
	
		this.addState(menu);
		this.addState(game);
	}
	
	@Override
	public boolean closeRequested()
	{	
		game.closeRequested();
		return true;
	}
}
