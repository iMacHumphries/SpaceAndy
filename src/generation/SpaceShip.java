package generation;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;

public class SpaceShip extends ImageBuffer {

	public SpaceShip(int width, int height) {
		super(width, height);
		
		Random random = new Random();
		
		 for (int x=0; x<width; x++) {
	         for (int y=0; y<height; y++) {
	            this.setRGBA(x, y, 0, 50, 0, 255);      
	         }
	      }
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.getImage().draw(100, 100);
	}

}
