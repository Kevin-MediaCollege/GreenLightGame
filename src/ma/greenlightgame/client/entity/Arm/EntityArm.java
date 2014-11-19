package ma.greenlightgame.client.entity.Arm;

import java.awt.Rectangle;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.entity.EntityPlayer;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class EntityArm extends Entity {
	private static Texture[] armTexture;
	
	private EntityPlayer player;
	
	private Texture texture;
	
	private int side;
	
	public EntityArm(EntityPlayer player) {
		super(0, 0);
		
		this.player = player;
		
		texture = armTexture[0];
		
		side = 0;
	}
	
	@Override
	public void update(Input input, float delta) {}
	
	@Override
	public void render(Renderer renderer) {
		if(player.isAttacking()) {
			if(side == -1) {
				renderer.drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
			} else if(side == 1) {
				renderer.drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
			}
		}		
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture.getWidth(), texture.getHeight());
	}
	
	public void updatePosition(int x, int y) {
		this.x = x + (texture.getWidth() * side);
		this.y = y;
	}
	
	public void setSide(int side) {
		this.side = side;
	}
	
	public static void load() {
		armTexture = new Texture[] {
				new Texture("character/head/H1.jpg")
			};
	}
}