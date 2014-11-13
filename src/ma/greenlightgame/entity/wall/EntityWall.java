package ma.greenlightgame.entity.wall;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import java.awt.Rectangle;

import ma.greenlightgame.entity.Entity;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.physics.Collider;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;

/** @author Kevin Krol
 * @since Nov 13, 2014 */
public class EntityWall extends Entity {
	private static final String WALLS_FOLDER = "Tiles/";
	
	private static Texture[] walls;
	
	private Texture texture;
	
	public EntityWall(int x, int y, int textureId) {
		super(x, y);
		
		this.texture = walls[textureId];
	}
	
	@Override
	public void update(Input input, float delta) {}
	
	@Override
	public void renderBounds() {
		Collider.renderBounds(getBounds());
	}
	
	@Override
	public void render(Renderer r){
		r.drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
	}
	
	public static void load() {
		walls = new Texture[] {
				new Texture(WALLS_FOLDER + "HWall.jpg", GL_NEAREST),
				new Texture(WALLS_FOLDER + "VWall.jpg", GL_NEAREST),
		};
	}
}
