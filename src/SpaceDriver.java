import game.SpaceGameController;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import constants.Constants;

/**
 * Starts the game.
 * 
 * @author Ben & Andy
 * @version 25-NOV-2015
 */
public class SpaceDriver {

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = null;
		container = new AppGameContainer(new SpaceGameController());
		container.setDisplayMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, false);
		container.setShowFPS(Constants.SHOW_FPS);
		container.setVSync(Constants.VSYNC_ENABLED);
		container.setSmoothDeltas(true);
		container.start();	
	}

}
