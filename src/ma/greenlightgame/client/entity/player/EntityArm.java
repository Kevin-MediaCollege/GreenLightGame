package ma.greenlightgame.client.entity.player;

import java.awt.Rectangle;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class EntityArm extends Entity {
	private static Texture[] armTexture;
	
	private EntityPlayer player;
	
	private Texture texture;
	
	private int side;
	
	public EntityArm(EntityPlayer player) {
		super();
		
		this.player = player;
		
		texture = armTexture[0];
		
		side = 0;
	}
	
	@Override
	public void update(float delta) {}
	
	@Override
	public void render() {
		x = player.getX() + texture.getWidth() * side;
		y = player.getY();
		
		Renderer.drawTexture(texture.getId(), x, y, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - texture.getWidth() / 2, y - texture.getHeight() / 2,
				texture.getWidth(), texture.getHeight());
	}
	
	public void setSide(int side) {
		this.side = side;
	}
	
	public int getSide() {
		return side;
	}
	
	public static void load() {
		armTexture = new Texture[] {new Texture("character/head/H1.jpg")};
	}
}