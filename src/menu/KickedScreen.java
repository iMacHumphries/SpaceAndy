package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.Button;
import constants.Constants;

public class KickedScreen extends BasicGameState implements ActionListener {

	private String message;
	private Button menuButton;
	private StateBasedGame game;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		this.game = game;
		menuButton = new Button(this, "Dang");
		menuButton.setHeight(30);
		menuButton.setWidth(100);
		menuButton.setX(gc.getWidth()/2 - 100);
		menuButton.setY(gc.getHeight()/2 + 50);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString(message, gc.getWidth()/2 - message.length()*5, gc.getHeight()/2);
		menuButton.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		menuButton.update(gc, game, delta);
	}

	@Override
	public int getID() {
		return Constants.KICKED_GAME_STATE;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuButton) {
			//ServerMenu menu = (ServerMenu)game.getState(Constants.SERVER_MENU_STATE);
			message = "";
			game.enterState(Constants.SERVER_MENU_STATE);
		}
 	}
}
