package ma.greenlightgame.client.entity.enemy;

import java.awt.Rectangle;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class EntityEnemy extends Entity {
	private Texture head;
	private Texture body;
	private Texture legs;
	
	public EntityEnemy(int x, int y, Texture head, Texture body, Texture legs) {
		super();
		
		this.x = x;
		this.y = y;
		
		this.head = head;
		this.body = body;
		this.legs = legs;
	}
	
	@Override
	public void update(float delta) {}
	
	@Override
	public void render(Renderer renderer) {
		renderer.drawTexture(head, x, y + 50, 	50, 50);
		renderer.drawTexture(body, x, y, 		50, 50);
		renderer.drawTexture(legs, x, y - 50, 	50, 50);
	}
	
	@Override
	public Rectangle getBounds() {
		return null;
	}
}
