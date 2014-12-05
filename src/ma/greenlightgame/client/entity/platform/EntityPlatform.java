package ma.greenlightgame.client.entity.platform;

import java.awt.Rectangle;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.renderer.SpriteSheet;

public abstract class EntityPlatform extends Entity {
	protected SpriteSheet spriteSheet;
	
	protected int width;
	protected int height;
	
	private int numColliders;
	
	public EntityPlatform(int x, int y, int width, int height) {
		super();
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		numColliders = 0;
	}
	
	@Override
	public void render() {
		spriteSheet.render(x, y);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - width / 2, y - height / 2, width, height);
	}
	
	public void onCollisionEnter(Entity entity) {
		numColliders++;
		colliding = true;
	}
	
	public void onCollisionExit(Entity entity) {
		numColliders--;
		
		if(numColliders == 0) {
			colliding = false;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
