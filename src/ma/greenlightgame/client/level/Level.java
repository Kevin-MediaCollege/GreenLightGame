package ma.greenlightgame.client.level;

import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.entity.platform.EntityPlatform;
import ma.greenlightgame.client.renderer.Background;

public abstract class Level {
	protected boolean loaded;
	
	private List<Entity> entities;
	
	private Background background;
	
	public Level() {
		entities = new ArrayList<Entity>();
	}
	
	public void update(float delta) {
		for(Entity entity : entities) {
			entity.update(delta);
		}
	}
	
	public void render() {
		background.render();
		
		for(Entity entity : entities) {
			entity.render();
		}
	}
	
	public void drawDebug() {
		for(Entity entity : entities) {
			entity.drawDebug();
		}
	}
	
	public void load() {}
	
	public EntityPlatform getWallAt(int x, int y) {
		for(Entity entity : entities) {
			if(entity instanceof EntityPlatform) {
				if(entity.getX() == x && entity.getY() == y) {
					return (EntityPlatform)entity;
				}
			}
		}
		
		return null;
	}
	
	public EntityPlatform[] getWalls() {
		List<EntityPlatform> walls = new ArrayList<EntityPlatform>();
		
		for(Entity entity : entities) {
			if(entity instanceof EntityPlatform) {
				walls.add((EntityPlatform)entity);
			}
		}
		
		return walls.toArray(new EntityPlatform[walls.size()]);
	}
	
	public boolean loaded() {
		return loaded;
	}
	
	protected void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	protected void setBackground(Background background) {
		this.background = background;
	}
}
