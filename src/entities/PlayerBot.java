package entities;

import org.newdawn.slick.GameContainer;

public class PlayerBot extends Entity {
	private String name;

	public PlayerBot(String name) {
		this.name = name;
		setImage("blueShip.png");
	}

	@Override
	public void update(GameContainer gc, int delta) {
		// No input!
	}
	
	public String getName() {
		return name;
	}
}
