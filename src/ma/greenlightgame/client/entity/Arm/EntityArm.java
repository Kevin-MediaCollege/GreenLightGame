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
		if(player.isAttacking() && side != 0)
			renderer.drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	public void drawDebug() {
		if(side != 0)
			super.drawDebug();
	};
	
	@Override
	public Rectangle getBounds() {
		if(side != 0)
			return new Rectangle(x - (texture.getWidth() / 2), y - (texture.getHeight() / 2), texture.getWidth(), texture.getHeight());
		
		return new Rectangle();
	}
	
	public void updatePosition(int x, int y) {
		if(side != 0) {
			this.x = x + (texture.getWidth() * side);
			this.y = y;
		} else {
			x = 0;
			y = 0;
		}
	}
	
	public void setSide(int side) {
		this.side = side;
	}
	
	public int getSide() {
		return side;
	}
	
	public static void load() {
		armTexture = new Texture[] {
				new Texture("character/head/H1.jpg")
			};
	}
}