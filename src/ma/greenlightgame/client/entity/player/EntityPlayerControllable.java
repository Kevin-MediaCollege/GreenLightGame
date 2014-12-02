package ma.greenlightgame.client.entity.player;

import org.lwjgl.input.Mouse;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.network.UDPClientHandler;
import ma.greenlightgame.client.physics.Physics;
import ma.greenlightgame.client.utils.DebugDraw;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;

public class EntityPlayerControllable extends EntityPlayer {
	private static final float MOVE_SPEED = 8;
	private static final float JUMP_FORCE = 23;
	
	private float oldVelocityX;
	private float oldVelocityY;
	
	private float oldRotation;
	
	private int oldX;
	private int oldY;
	
	private boolean isJumping;
	
	public EntityPlayerControllable(int id) {
		super(id);
		
		this.isJumping = false;
	}
	
	@Override
	public void update(float delta) {
		try {
			oldVelocityX = velocityX;
			oldVelocityY = velocityY;
			oldRotation = rotation;
		} finally {
			super.update(delta);
		}
		
		handleInput(delta);
		
		if(y < 0)
			onDead();
		
		if(hasChanged())
			Client.sendUDP(NetworkMessage.PLAYER_INFO, UDPClientHandler.getId(), x, y, velocityX, velocityY, rotation);
	}
	
	@Override
	public void drawDebug() {
		super.drawDebug();
		
		DebugDraw.drawLine(x, y, Input.getMouseX(), Input.getMouseY());
	}
	
	public void checkCollision(EntityWall[] walls) {
		for(EntityWall wall : walls) {
			boolean alreadyColliding = wallColliders.contains(wall);
			boolean intersects = Physics.intersecs(this, wall);
			boolean fromTopSide = ((this.y-((this.totalHeight / 8) * 4)) > (wall.getY())? true:false);
			boolean fromBottomSide = ((this.y+((this.totalHeight/8) * 4)) < (wall.getY())? true: false);
			
			if(intersects && !alreadyColliding) {
				onCollisionEnter(wall);
				if(!fromTopSide && !fromBottomSide)
					velocityX = 0;
				
				velocityY = 0;
				//isJumping = false;
				Client.sendUDP(NetworkMessage.PLAYER_COLLISION, UDPClientHandler.getId(), wall.getX(), wall.getY(), true);
			} else if(intersects && alreadyColliding) {

				if(fromTopSide){
					System.out.println((this.y-((this.totalHeight / 8)*5)) + " <- playerY|||wallY -> " + wall.getY());
					System.out.println(this.x + " <- playerX|||wallX -> " + wall.getX());
					isJumping = false;
					velocityY = 0;
				}else if(!fromBottomSide){
					velocityX = 0;
				}
				
			} else if(!intersects && alreadyColliding) {
				onCollisionExit(wall);
				
				Client.sendUDP(NetworkMessage.PLAYER_COLLISION, UDPClientHandler.getId(), wall.getX(), wall.getY(), false);
			}
		}
	}
	
	public void checkAttackCollision(EntityPlayer[] players) {
		for(EntityPlayer player : players){
			if(player != this){
				if(Physics.intersecs(player, arms) && attacking){
					Client.sendUDP(NetworkMessage.PLAYER_HIT, player.id, UDPClientHandler.getId());
				}
			}
		}
	}
	
	private void handleInput(float delta) {
		if(Input.getKey(KeyCode.D) && !Input.getKey(KeyCode.A)) {
			velocityX = MOVE_SPEED * delta;			
		} else if(!Input.getKey(KeyCode.D) && Input.getKey(KeyCode.A)) {
			velocityX = -MOVE_SPEED * delta;
		} else {
			velocityX = 0;
		}
		
		if(Input.isKeyDown(KeyCode.E)) {
			onAttackChange(Mouse.getX() < x ? -1 : 1, true);
			Client.sendUDP(NetworkMessage.PLAYER_ATTACK, UDPClientHandler.getId(), arms.getSide(), attacking);
		} else if(Input.isKeyUp(KeyCode.E)) {
			onAttackChange(0, false);
			Client.sendUDP(NetworkMessage.PLAYER_ATTACK, UDPClientHandler.getId(), arms.getSide(), attacking);
		}
		
		rotation = Utils.angleTo(x, y + body.getHeight(), Input.getMouseX(), Input.getMouseY());
		
		if(Input.getKey(KeyCode.W) || Input.getKey(KeyCode.SPACE)) {
			if(!isJumping) {
				velocityY = JUMP_FORCE * delta;
				isJumping = true;
			}
		}
	}
	
	public boolean isJumping() {
		return isJumping;
	}
	
	private boolean hasChanged() {
		if(x != oldX)
			return true;
		
		if(y != oldY)
			return true;
		
		if(velocityX != oldVelocityX)
			return true;
		
		if(velocityY != oldVelocityY)
			return true;
		
		if(rotation != oldRotation)
			return true;
		
		return false;
	}
}
