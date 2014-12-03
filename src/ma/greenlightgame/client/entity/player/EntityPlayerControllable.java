package ma.greenlightgame.client.entity.player;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.network.UDPClientHandler;
import ma.greenlightgame.client.physics.Physics;
import ma.greenlightgame.client.utils.DebugDraw;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;

import org.lwjgl.input.Mouse;

public class EntityPlayerControllable extends EntityPlayer {
	private static final float MOVE_SPEED = 8;
	private static final float JUMP_FORCE = 23;
	
	private float oldVelocityX;
	private float oldVelocityY;
	
	private float oldRotation;
	
	private int oldX;
	private int oldY;
	
	private boolean isJumping;
	private boolean isFalling;
	
	public EntityPlayerControllable(int id) {
		super(id);
		
		isJumping = false;
		isFalling = true;
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
			final boolean wasColliding = wallColliders.contains(wall);
			final boolean isColliding = Physics.intersecs(this, wall);
			
			if(wasColliding || isColliding) {
				final int wallTop = wall.getY() + (wall.getHeight() / 2);
				final int wallBottom = wall.getY() - (wall.getHeight() / 2);
				
				int top = y + (totalHeight / 2);
				int bottom = y - (totalHeight / 2);
				
				boolean collidingTop = top <= wallTop;
				boolean collidingBottom = bottom >= wallBottom;
				
				if(!wasColliding && isColliding) {
					onCollisionEnter(wall);
					
					if(collidingBottom) {
						isJumping = false;
						isFalling = false;
						
						while(y - (totalHeight / 2) < wallTop)
							y++;
						
						y--;
					} else if(collidingTop) {
						if(!isFalling)
							velocityY = 0;
						
						isJumping = false;
						isFalling = true;
						
						while(y + (totalHeight / 2) > wallBottom)
							y--;
						
						y++;
					}
					
					velocityY = 0;
				} else if(wasColliding && isColliding) {
					if(collidingBottom) {
						if(!isJumping)
							velocityY = 0;
					} else if(!collidingTop && !collidingBottom) {
						velocityX = 0;
					}
				} else if(wasColliding && !isColliding) {
					onCollisionExit(wall);
				}
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
		
		if(Input.isKeyDown(KeyCode.W) || Input.isKeyDown(KeyCode.SPACE)) {
			if(!isJumping && !isFalling) {
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
