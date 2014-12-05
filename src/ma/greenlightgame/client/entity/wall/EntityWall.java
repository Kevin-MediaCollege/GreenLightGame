package ma.greenlightgame.client.entity.wall;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.renderer.Renderer;

public class EntityWall extends Entity {
	private List<Entity> colliders;
	
	private int textureId;
	
	private int width;
	private int height;
	
	public EntityWall(int x, int y, int width, int height, int textureId) {
		super();
		
		this.x = x;
		this.y = y;
		
		colliders = new ArrayList<Entity>();
		
		this.textureId = textureId;
		
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void update(float delta) {}
	
	@Override
	public void render() {
		Renderer.drawTexture(textureId, x, y, width, height);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (width / 2), y - (height / 2), width, height);
	}
	
	public void onCollisionEnter(Entity entity) {
		if(colliders.contains(entity))
			return;
		
		colliders.add(entity);
		colliding = true;
	}
	
	public void onCollisionExit(Entity entity) {
		if(!colliders.contains(entity))
			return;
		
		colliders.remove(entity);
		
		if(colliders.isEmpty())
			colliding = false;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
