package ma.greenlightgame.client.entity.player;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.platform.EntityPlatform;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.network.UDPClientHandler;
import ma.greenlightgame.client.physics.Physics;
import ma.greenlightgame.client.utils.DebugDraw;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;

public class EntityPlayerControllable {
	private EntityPlayer player;
	
	private float moveSpeed;
	private float jumpForce;
	
	private boolean hasChanged;
	private boolean isJumping;
	private boolean isFalling;
	
	public EntityPlayerControllable(EntityPlayer player) {
		this.player = player;
		
		isJumping = false;
		isFalling = true;
	}
	
	public void update(float delta) {
		handleInput(delta);
		
		if(player.getY() + player.totalHeight / 2 < 0) {
			player.onDead();
		}
		
		if(hasChanged) {
			Client.sendUDP(NetworkMessage.PLAYER_INFO, UDPClientHandler.getId(), player.getX(), player.getY(), player.getVelocityX(), player.getVelocityY(), player.getRotation());
		}
	}
	
	public void drawDebug() {
		DebugDraw.drawLine(player.getX(), player.getY(), Input.getMouseX(), Input.getMouseY());
	}
	
	public void checkCollision(EntityPlatform[] walls) {
		for(EntityPlatform wall : walls) {
			final boolean wasColliding = player.wallColliders.contains(wall);
			final boolean isColliding = Physics.intersecs(player, wall);
			
			if(wasColliding || isColliding) {
				final int wallTop = wall.getY() + wall.getHeight() / 2;
				final int wallBottom = wall.getY() - wall.getHeight() / 2;
				
				int top = player.getY() + player.totalHeight / 2;
				int bottom = player.getY() - player.totalHeight / 2;
				
				boolean collidingTop = top <= wallTop;
				boolean collidingBottom = bottom >= wallBottom;
				
				if(!wasColliding && isColliding) {
					player.onCollisionEnter(wall);
					
					if(collidingBottom) {
						isJumping = false;
						isFalling = false;
						
						while(player.getY() - player.totalHeight / 2 < wallTop) {
							player.setY(player.getY() + 1);
						}
						
						player.setY(player.getY() - 1);
					} else if(collidingTop) {
						if(!isFalling) {
							player.setVelocityY(0);;
						}
						
						isJumping = false;
						isFalling = true;
						
						while(player.getY() + player.totalHeight / 2 > wallBottom) {
							player.setY(player.getY() - 1);
						}
						
						player.setY(player.getY() + 1);
					}
					
					player.setVelocityY(0);
				} else if(wasColliding && isColliding) {
					if(collidingBottom) {
						if(!isJumping) {
							player.setVelocityY(0);
						}
					} else if(!collidingTop && !collidingBottom) {
						player.setVelocityX(0);
					}
				} else if(wasColliding && !isColliding) {
					player.onCollisionExit(wall);
				}
			}
		}
	}
	
	private void handleInput(float delta) {
		if(Input.getKey(KeyCode.D) && !Input.getKey(KeyCode.A)) {
			move(moveSpeed);
		} else if(!Input.getKey(KeyCode.D) && Input.getKey(KeyCode.A)) {
			move(-moveSpeed);
		} else {
			move(0);
		}
		
		player.lookAt(Input.getMouseX(), Input.getMouseY());
		
		if(Input.isKeyDown(KeyCode.W) || Input.isKeyDown(KeyCode.SPACE)) {
			jump();
		}
	}
	
	private void move(float amount) {
		player.setVelocityX(amount);
		
		hasChanged = true;
	}
	
	private void jump() {
		if(!isJumping && !isFalling) {
			player.setVelocityY(jumpForce);
			
			isJumping = true;
			hasChanged = true;
		}
	}
	
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	
	public void setJumpForce(float jumpForce) {
		this.jumpForce = jumpForce;
	}
	
	public boolean isJumping() {
		return isJumping;
	}
}
