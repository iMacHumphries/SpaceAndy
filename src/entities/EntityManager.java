package entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityManager {

	private ArrayList<Entity> entities;
	private ArrayList<Entity> toRemove;

	public EntityManager() {
		entities = new ArrayList<Entity>();
		toRemove = new ArrayList<Entity>();
	}

	public void addEntity(Entity enitity) {
		entities.add(enitity);
	}

	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public void removePlayerBot(PlayerBot playerBot) {
		for (Entity e : this.entities) {
			if (e instanceof PlayerBot && ((PlayerBot) e).getName().equals(playerBot.getName())) {
				e.shouldRemove = true;
			}
		}
	}

	public void movePlayerBot(PlayerBot playerBot, int x, int y, int rotZ) {
		boolean found = false;
		for (int i = 0; !found && i < entities.size(); i++) {
			if (entities.get(i) instanceof PlayerBot) {
				PlayerBot bot = (PlayerBot) entities.get(i);
				if (bot.getName().equals(playerBot.getName())) {
					found = true;
					bot.setX(x);
					bot.setY(y);
					bot.setRotz(rotZ);
				}
			}
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (Entity e : entities) {
			
			if (e instanceof Explosion) {
				((Explosion) e).render(gc, g);
				return;
			}
			
			Image image = e.getImage();
			image.setCenterOfRotation(e.getWidth() / 2, e.getHeight() / 2);
			image.setRotation(e.getRotz() + 90);
			image.draw(e.getX(), e.getY(), e.getScale(), e.getColor());

			if (e instanceof PlayerBot) {
				g.drawString(((PlayerBot) e).getName(), e.getX() - e.getWidth() / 2, e.getY() - e.getHeight() / 2);
			}
		}
	}
	
	public ArrayList<Laser> getLasers() {
		ArrayList<Laser> lasers = new ArrayList<>();
		for (Entity e : this.entities) {
			if (e instanceof Laser)
				lasers.add((Laser) e);
		}	
		return lasers;
	}

	public ArrayList<PlayerBot> getPlayers() {
		ArrayList<PlayerBot> players = new ArrayList<>();
		for (Entity e : this.entities) {
			if (e instanceof PlayerBot)
				players.add((PlayerBot) e);
		}	
		return players;
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		for (Entity e : entities) {
			if (!e.shouldRemove)
				e.update(gc, delta);
			else
				toRemove.add(e);
		}
		
		for (Entity e : toRemove) {
			removeEntity(e);
		}
		toRemove.clear();

	}

}
