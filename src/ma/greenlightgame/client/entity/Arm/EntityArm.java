package ma.greenlightgame.client.entity.Arm;

import java.awt.Rectangle;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.entity.EntityPlayer;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class EntityArm  extends Entity{
	 int mouseX;

	 private Texture texture;
	 private static Texture[] armTexture;
	 public static boolean armSide;
	
	public EntityArm(int x, int y) {
		super(x, y);
		texture 	= armTexture[0];
		getBounds();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Input input, float delta) {}

	@Override
	public void render(Renderer renderer) {
		if(EntityPlayer.atk == true){
			if(armSide){
				renderer.drawTexture(texture, x + texture.getWidth(), y,texture.getWidth(), texture.getHeight());
			}else{
				renderer.drawTexture(texture, x - texture.getWidth(), y,texture.getWidth(), texture.getHeight());	
				
			}
		}
		
	}

	@Override
	public Rectangle getBounds() {
		int leftArmX 	= texture.getWidth() + (texture.getWidth() / 2);
		int rightArmX 	= texture.getWidth() / 2;
		if(EntityPlayer.atk){
			if(armSide){
				return new Rectangle(x + rightArmX , y - (texture.getHeight() / 2), texture.getWidth(), texture.getHeight());
			}else{
				return new Rectangle(x - leftArmX, y - (texture.getHeight() / 2), texture.getWidth(), texture.getHeight());
					
			}
		}else{
			return new Rectangle(x,y,0,0);
		}
		
	}
	
	public static void load() {
		armTexture = new Texture[] {
				new Texture("character/head/H1.jpg")
		};
		
	}

}
