package entities;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class Explosion extends Entity {

	private final String FILE = "res/particle-system-explode.xml";
	private ParticleSystem system;
	
	public Explosion(float f, float g) {
		this.x = f;
		this.y = g;
		
		try {
			system = ParticleIO.loadConfiguredSystem(FILE);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void render(GameContainer gc, Graphics g)
			throws SlickException {
		system.render(x, y);
	}
	
	@Override
	public void update(GameContainer gc, int delta) {
		system.update(delta);
	}
	
	public ParticleSystem getSystem() {
		return system;
	}
}
