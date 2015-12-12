package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends Entity {

	private float maxSpeed;
	private float currentSpeed;
	private float maxTurnSpeed;
	private float currentTurnSpeed;
	private String name;

	private PlayerListener delegate;

	public interface PlayerListener {
		void playerDidMove(Player player);
	}

	public Player(PlayerListener delegate, String name) {
		this.delegate = delegate;
		this.name = name;
		maxSpeed = 0.5f;
		maxTurnSpeed = 0.25f;
		currentSpeed = 0;
		currentTurnSpeed = 0;
		setImage("blueShip.png");
		x = 500;
		y = 50;
	}

	public Laser shoot() {
		return new Laser(name, dirX, dirY, x + width / 2, y + height / 2, rotz);
	}

	@Override
	public void update(GameContainer gc, int delta) {

		float distance = currentSpeed * delta;
		dirX = (float) Math.cos(Math.toRadians(rotz));
		dirY = (float) Math.sin(Math.toRadians(rotz));
		float dx = (distance * dirX);
		float dy = (distance * dirY);

		rotate((currentTurnSpeed * delta));
		move(dx, dy);

		// going off right
		if (x > gc.getWidth()) {
			x = -width;
		} else if (x < -width) {
			// Off left
			x = gc.getWidth();
		} else if (y > gc.getHeight()) {
			// Off bot
			y = -height;
		} else if (y < -height) {
			// Off top
			y = gc.getHeight();
		}

		if (currentSpeed != 0 || currentTurnSpeed != 0) {
			delegate.playerDidMove(this);
		}
	}

	public void checkInput(Input input, boolean isTyping) {
		currentTurnSpeed = 0;
		if (!isTyping && (input.isKeyDown(Input.KEY_W)
				|| input.isKeyDown(Input.KEY_UP))) {
			if (currentSpeed < maxSpeed)
				currentSpeed += 0.01f;
		} else if (currentSpeed > 0)
			currentSpeed -= 0.01f;
		else
			currentSpeed = 0;

		if (!isTyping) {
			if (input.isKeyDown(Input.KEY_A) || input.isKeyDown(Input.KEY_LEFT)) {
				currentTurnSpeed = -maxTurnSpeed;
			} else if (input.isKeyDown(Input.KEY_D)
					|| input.isKeyDown(Input.KEY_RIGHT)) {
				currentTurnSpeed = maxTurnSpeed;
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {

		if (this.shouldRemove)
			return;

		image.setCenterOfRotation(width / 2, height / 2);
		image.setRotation(rotz + 90);

		image.draw(x, y, scale, Color.green);
		g.drawString(name, x - width / 2, y - height);
	}

	public String getName() {
		return name;
	}
}
