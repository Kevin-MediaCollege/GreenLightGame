package ma.greenlightgame.client.entity.player;

import ma.greenlightgame.client.renderer.Texture;

public class EntityPlayerMechGuy extends EntityPlayer {
	private static final String HEAD_TEXTURE = "character/mechguy/mechguy_head.png";
	private static final String BODY_TEXTURE = "character/mechguy/mechguy_body.png";
	
	private static final float MOVE_SPEED = 8;
	
	private static final float JUMP_FORCE = 23;
	
	private static Texture headTexture;
	private static Texture bodyTexture;
	
	public EntityPlayerMechGuy(boolean controllable) {
		super(headTexture, bodyTexture);
		
		if(controllable) {
			controller = new EntityPlayerControllable(this);
			
			controller.setMoveSpeed(MOVE_SPEED);
			controller.setJumpForce(JUMP_FORCE);
		}
	}
	
	public static void load() {
		headTexture = new Texture(HEAD_TEXTURE);
		bodyTexture = new Texture(BODY_TEXTURE);
	}
}
