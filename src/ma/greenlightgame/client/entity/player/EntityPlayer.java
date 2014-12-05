package ma.greenlightgame.client.entity.player;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.entity.platform.EntityPlatform;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;
import ma.greenlightgame.common.utils.Utils;

public class EntityPlayer extends Entity {
	private static final float GRAVITY = 0.9f;
	private static final float TERMINAL_VELOCITY = -13f;
	
	protected List<EntityPlatform> wallColliders;
	
	protected EntityPlayerControllable controller;
	
	protected Texture head;
	protected Texture body;
	
	protected float velocityX;
	protected float velocityY;
	
	protected int totalWidth;
	protected int totalHeight;
	
	protected boolean attacking;
	protected boolean alive;
	
	public EntityPlayer(Texture head, Texture body) {
		super();
		
		wallColliders = new ArrayList<EntityPlatform>();
		
		this.head = head;
		this.body = body;
		
		velocityY = TERMINAL_VELOCITY;
		velocityX = 0;
		colliding = false;
		alive = true;
		
		totalWidth = body.getWidth();
		totalHeight = head.getHeight() + body.getHeight();
	}
	
	@Override
	public void update(float delta) {
		x += velocityX * delta;
		y += velocityY * delta;
		
		if(velocityY > TERMINAL_VELOCITY) {
			velocityY -= GRAVITY * delta;
		}
		
		if(controller != null) {
			controller.update(delta);
		}
	}
	
	@Override
	public void render() {
		if(alive) {
			Renderer.drawTexture(body.getId(), x, y, body.getWidth(), body.getHeight());
			Renderer.drawTexture(head.getId(), x, y + head.getHeight(), head.getWidth(), head.getHeight(), rotation);
		} else {
			Renderer.drawTexture(body.getId(), x, y, body.getWidth(), body.getHeight(), 0, true, 0.45f);
			Renderer.drawTexture(head.getId(), x, y + head.getHeight(), head.getWidth(), head.getHeight(), rotation, true, 0.45f);
		}
	}
	
	@Override
	public void drawDebug() {
		super.drawDebug();
		
		if(controller != null) {
			controller.drawDebug();
		}
	}
	
	public void onDead() {
		// TODO: On dead
		alive = false;
	}
	
	public void onHit(EntityPlayer from) {
		// TODO: On hit
		
		if(from.getX() < x) {
			x += 500;
		} else {
			x -= 500;
		}
	}
	
	public void onCollisionEnter(EntityPlatform wall) {
		wallColliders.add(wall);
		wall.onCollisionEnter(this);
		
		colliding = true;
	}
	
	public void onCollisionExit(EntityPlatform wall) {
		wallColliders.remove(wall);
		wall.onCollisionExit(this);
		
		if(wallColliders.isEmpty()) {
			colliding = false;
		}
	}
	
	public void onAttackChange(int side, boolean attacking) {
		this.attacking = attacking;
	}
	
	public void lookAt(int x, int y) {
		rotation = Utils.angleTo(this.x, this.y + body.getHeight(), x, y);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - totalWidth / 2, y - totalHeight / 2, totalWidth, totalHeight);
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public EntityPlayerControllable getController() {
		return controller;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public boolean isAttacking() {
		return attacking;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isControllable() {
		return controller != null;
	}
}
