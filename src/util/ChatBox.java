package util;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ChatBox {

	private ArrayList<String> messages;
	public int x;
	public int y;
	private boolean isHighLighted;

	public ChatBox(int x, int y) {
		this.x = x;
		this.y = y;
		messages = new ArrayList<String>();
	}

	public void addMessageFromPlayer(String player, String msg) {
		messages.add("[" + player + "] " + msg);
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		int textFieldOffset = 20;
		int y_offset = 20 ;
		int y_start = y_offset + textFieldOffset;
		Color old = g.getColor();
		if (!isHighLighted) {
			g.setColor(new Color(1, 1, 1, 0.4f));
		} else {
			g.setColor(new Color(1, 1, 1, 1.0f));
		}
		int count = 0;
		for (int i = messages.size()-1; i >= 0 && count < 10; i--) {
			g.drawString(messages.get(i), x, y - y_start);
			y_start += y_offset;
			count++;
		}
		g.setColor(old);
	}

	public void setHighLighted(boolean high) {
		isHighLighted = high;
	}
}
