import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import constants.Constants;
import entities.*;
import server.MultiplayerManager;
import server.packets.Packet;

/**
 * SpaceGame.java - Main Controller
 * 
 * @author Ben & Andy
 * @version 25-NOV-2015
 */
public class SpaceGame extends BasicGame {

	private EntityManager entityManager;
	private MultiplayerManager multiplayerManager;
	private Player player;
	private Image background;

	/**
	 * Constructor
	 */
	public SpaceGame() {
		super("Space Andy");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		player.render(gc, g);
		entityManager.render(gc, g);
		g.drawString(Constants.VERSION, 80, 10);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		entityManager = new EntityManager();
		multiplayerManager = new MultiplayerManager(entityManager);
		player = new Player(multiplayerManager, "SonOfBoo");
		multiplayerManager.login(player);

		background = new Image("rez/space.png");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE) || gc.getInput().isMousePressed(0)) {
			Laser laser = player.shoot();
			multiplayerManager.shootLaser(laser);
		}
		player.update(gc, delta);
		entityManager.update(gc, delta);
		multiplayerManager.update(gc, delta);
		
		for (Laser l : entityManager.getLasers()) {
			if (!l.getUsername().equals(player.getName()) && l.intersects(player)) {
				l.setShouldRemove(true);
				multiplayerManager.kill(player.getName());
				//player.setShouldRemove(true);
			}
		}
		
		for (PlayerBot p : entityManager.getPlayers()) {
			for (Laser l : entityManager.getLasers()) {
				if (l.intersects(p) && !l.getUsername().equals(p.getName())) {
					//p.setShouldRemove(true);
					l.setShouldRemove(true);
					multiplayerManager.kill(p.getName());
				}
			}
		}
		
	}

	@Override
	public boolean closeRequested() {
		multiplayerManager.disconnect(player);
		return true;
	}

}
