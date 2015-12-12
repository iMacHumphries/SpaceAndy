package game;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.BufferedImageUtil;

import constants.Constants;
import entities.*;
import server.MultiplayerManager;
import util.ChatBox;

/**
 * SpaceGame.java - Main Controller
 * 
 * @author Ben & Andy
 * @version 25-NOV-2015
 */
public class SpaceGame extends BasicGameState {

	private EntityManager entityManager;
	private MultiplayerManager multiplayerManager;
	private Player player;
	private Image background;
	private ChatBox chatBox;
	private TextField chatField;
	private boolean isTyping;

	/**
	 * Constructor
	 */
	public SpaceGame() {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {

		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		player.render(gc, g);
		entityManager.render(gc, g);
		g.drawString(Constants.VERSION, 80, 10);

		chatBox.setHighLighted(isTyping);
		chatBox.render(gc, game, g);
		chatField.render(gc, g);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		isTyping = false;
		chatBox = new ChatBox(0, gc.getHeight());
		chatField = new TextField(gc, new TrueTypeFont(new Font("Verdana",
				Font.BOLD, 15), false), 0, gc.getHeight() - 20, 500, 20);
		chatField.setCursorVisible(true);
		background = loadImage("res/space.png");
	}

	public void init(String serverIp) {
		entityManager = new EntityManager();
		multiplayerManager = new MultiplayerManager(entityManager,chatBox, serverIp);
		player = new Player(multiplayerManager, "SonOfBoo");
		multiplayerManager.login(player);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.update(gc, delta);
		multiplayerManager.update(gc, delta);

		for (Laser l : entityManager.getLasers()) {
			if (!l.getUsername().equals(player.getName())
					&& l.intersects(player)) {
				l.setShouldRemove(true);
				multiplayerManager.kill(player.getName());
			}
		}

		for (PlayerBot p : entityManager.getPlayers()) {
			for (Laser l : entityManager.getLasers()) {
				if (l.intersects(p) && !l.getUsername().equals(p.getName())) {
					l.setShouldRemove(true);
					multiplayerManager.kill(p.getName());
				}
			}
		}

		Input input = gc.getInput();

		if (input.isKeyPressed(Input.KEY_ENTER)) {
			if (isTyping && chatField.getText().length() > 0) {
				// finished typing
				multiplayerManager.sendMessage(player, chatField.getText());
				chatField.setText("");
				chatField.setFocus(false);
			} else {
				// Start typing
				chatField.setFocus(true);

			}
			isTyping = !isTyping;
		}

		player.update(gc, delta);
		player.checkInput(input, isTyping);

		if (isTyping)
			return;

		if (input.isKeyPressed(Input.KEY_SPACE) || input.isMousePressed(0)) {
			Laser laser = player.shoot();
			multiplayerManager.shootLaser(laser);
		}

	}

	public boolean closeRequested() {
		if (multiplayerManager != null)
			multiplayerManager.disconnect(player);
		return true;
	}

	public static Image loadImage(String path) {
		Image image = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(path));
			Texture texture = BufferedImageUtil.getTexture("", bufferedImage);

			image = new Image(texture.getImageWidth(), texture.getImageHeight());

			image.setTexture(texture);
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public int getID() {
		return Constants.SPACE_GAME_STATE;
	}

}
