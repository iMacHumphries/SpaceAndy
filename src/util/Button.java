package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Button {
	private ActionListener delegate;
	private String text;
	private int x;
	private int y;
	private int width;
	private int height;
	private final Color highlightColor = Color.gray;
	private final Color defaultColor = Color.white;
	private Color color;

	public Button(ActionListener delegate, String text) {
		this.delegate = delegate;
		this.text = text;
		color = defaultColor;
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		Color temp = g.getColor();
		
		g.setColor(color);
		g.drawRect(x, y, width, height);
		g.drawString(text, x, y);
		
		g.setColor(temp);
	}

	//public void addAcit
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void update(GameContainer gc, StateBasedGame game, int delta) {
		if (gc.getInput().isMousePressed(0)) {
			if (this.containsPoint(gc.getInput().getMouseX(), gc.getInput()
					.getMouseY())) {
				color = highlightColor;
				System.out.println("click");
				delegate.actionPerformed(new ActionEvent(this,0,"command"));
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	color = defaultColor;
				            }
				        }, 
				        100 
				);
			}
		}
		
	}

	private boolean containsPoint(int x, int y) {
		return x > this.x && x < this.x + this.width && y > this.y
				&& y < this.y + this.height;
	}

}
