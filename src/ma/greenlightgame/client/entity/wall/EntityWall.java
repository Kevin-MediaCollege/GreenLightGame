package ma.greenlightgame.client.entity.wall;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class EntityWall extends Entity {
	private static final String WALLS_FOLDER = "Tiles/";
	
	private static Texture[] walls;
	
	private List<Entity> colliders;
	
	private Texture texture;
	
	public EntityWall(int x, int y, int textureId) {
		super(x, y);
		
		colliders = new ArrayList<Entity>();
		
		this.texture = walls[textureId];
	}
	
	@Override
	public void update(Input input, float delta) {}
	
	@Override
	public void render(Renderer renderer){
		renderer.drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture.getWidth(), texture.getHeight());
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
	
	public static void load() {
		walls = new Texture[] {
				new Texture(WALLS_FOLDER + "HWall.jpg"),
				new Texture(WALLS_FOLDER + "VWall.jpg"),
		};
	}
}
