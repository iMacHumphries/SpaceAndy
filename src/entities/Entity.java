package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 * Entity.java - Base class for renderable sprite objects.
 * 
 * @author Ben & Andy
 * @version 25-NOV-2015
 */
public abstract class Entity {
	protected float x;
	protected float y;
	protected float dirX;
	protected float dirY;
	protected float rotz;
	protected float width;
	protected float height;
	protected float scale;
	protected Image image;

	protected boolean shouldRemove;

	public Entity() {
		scale = 1.0f;
		shouldRemove = false;
	}

	public abstract void update(GameContainer gc, int delta);

	public void move(float dx, float dy) {
		x = x + dx;
		y = y + dy;
	}

	public void updateDirection(float dirX, float dirY) {
		this.dirX = dirX;
		this.dirY = dirY;
	}

	public void rotate(float z) {
		rotz = rotz + z;
	}

	public boolean intersects(Entity e) {
		Shape r1 = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
		Shape r2 = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		return r1.intersects(r2) || r1.contains(r2);
	}

	public void setImage(String filename) {
		try {
			image = new Image("rez/" + filename);
			width = image.getWidth();
			height = image.getHeight();
		} catch (SlickException e) {

			e.printStackTrace();
		}

	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getDirX() {
		return dirX;
	}

	public float getDirY() {
		return dirY;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getScale() {
		return scale;
	}

	public Image getImage() {
		return image;
	}

	public float getRotz() {
		return rotz;
	}

	public void setShouldRemove(boolean rem) {
		this.shouldRemove = rem;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setRotz(float rotz) {
		this.rotz = rotz;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

}
