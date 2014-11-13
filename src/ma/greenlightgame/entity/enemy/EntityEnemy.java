package ma.greenlightgame.entity.enemy;

import java.awt.Rectangle;

import ma.greenlightgame.entity.Entity;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;

/** @author Kevin Krol
 * @since Nov 13, 2014 */
public class EntityEnemy extends Entity {
	private Texture head;
	private Texture body;
	private Texture legs;
	
	public EntityEnemy(int x, int y, Texture head, Texture body, Texture legs) {
		super(x, y);
		
		this.head = head;
		this.body = body;
		this.legs = legs;
	}
	
	@Override
	public void update(Input input, float delta) {}
	
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
