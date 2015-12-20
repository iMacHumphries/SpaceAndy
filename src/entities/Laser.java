package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;

public class Laser extends Entity {

	private final float SPEED = 1.0f;
	private String username;
	
	public Laser(String username, Color color, float dx, float dy, float x, float y, float rotz) {
		setImage("blueLaser.png");
		Vector2f vec = new Vector2f(dx, dy);
		vec.normalise();
		this.color = color;
		this.username = username;
		this.dirX = vec.x;
		this.dirY = vec.y;
		this.rotz = rotz;
		this.x = x;
		this.y = y;
	}

	@Override
	public void update(GameContainer gc, int delta) {
		
		Vector2f vec = new Vector2f(dirX, dirY);
		vec.scale(SPEED * delta);
		x += vec.x;
		y += vec.y;

		// going off right
		if (x > gc.getWidth() || x < -width || y > gc.getHeight() || y < -height) {
			shouldRemove = true;
		}

	}

	public String getUsername() {
		return username;
	}
}
