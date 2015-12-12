package menu;

import game.SpaceGame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import server.LocalServerFinder;
import server.LocalServerFinder.LocalServerListener;
import util.Button;
import constants.Constants;

public class ServerMenu extends BasicGameState implements ActionListener, LocalServerListener {
	private TextField hostTextField;
	private Button joinBt;
	private StateBasedGame sbg;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		this.sbg = game;
		hostTextField = new TextField(gc, new TrueTypeFont (new Font("Verdana", Font.BOLD, 30), false), gc.getWidth()/2 - 250, gc.getHeight()/2 - 25, 500, 50);
		hostTextField.setText("Host address...");
		hostTextField.setCursorPos("Host address...".length());
		hostTextField.setBorderColor(Color.white);
		joinBt = new Button(this, "Join");
		joinBt.setWidth(40);
		joinBt.setHeight(20);
		joinBt.setX(hostTextField.getX() + hostTextField.getWidth());
		joinBt.setY(hostTextField.getY() + hostTextField.getHeight()/2);
		
		LocalServerFinder localFinder = new LocalServerFinder(this);
		Thread thread = new Thread(localFinder);
		thread.start();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		hostTextField.render(gc, g);
		joinBt.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		joinBt.update(gc, game, delta);
	}

	@Override
	public int getID() {
		return Constants.SERVER_MENU_STATE;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joinBt) {
			if (hostTextField.getText() == null || hostTextField.getText().length() < "0.0.0.0".length()) return;
			
			((SpaceGame)sbg.getState(Constants.SPACE_GAME_STATE)).init(hostTextField.getText());
			sbg.enterState(Constants.SPACE_GAME_STATE);
		}
			
	}

	@Override
	public void didFinishSearchingForHosts(ArrayList<InetAddress> hosts) {
		for(InetAddress ip : hosts) {
			hostTextField.setText(ip.getHostAddress());
		}
		
	}

}
