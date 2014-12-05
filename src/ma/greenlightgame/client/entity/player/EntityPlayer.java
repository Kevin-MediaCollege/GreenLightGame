package ma.greenlightgame.client.entity.player;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.entity.platform.EntityPlatform;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class EntityPlayer extends Entity {
	private static final float GRAVITY = 0.9f;
	private static final float TERMINAL_VELOCITY = -13f;
	
	protected List<EntityPlatform> wallColliders;
	
	protected EntityArm arms;
	
	protected static Texture head;
	protected static Texture body;
	
	protected float velocityX;
	protected float velocityY;
	
	protected int totalWidth;
	protected int totalHeight;
	
	protected boolean attacking;
	protected boolean alive;
	
	protected int id;
	
	public EntityPlayer(int id) {
		super();
		
		this.id = id;
		
		wallColliders = new ArrayList<EntityPlatform>();
		
		velocityY = TERMINAL_VELOCITY;
		velocityX = 0;
		colliding = false;
		alive = true;
		
		arms = new EntityArm(this);
		
		totalWidth = body.getWidth();
		totalHeight = head.getHeight() + body.getHeight();
	}
	
	@Override
	public void update(float delta) {
		arms.update(delta);
		
		if(Input.isKeyDown(KeyCode.L)) {
			alive = false;
		}
		
		y += velocityY * delta;
		x += velocityX * delta;
		
		if(velocityY > TERMINAL_VELOCITY) {
			velocityY -= GRAVITY * delta;
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
		
		if(attacking) {
			arms.render();
		}
	}
	
	@Override
	public void drawDebug() {
		super.drawDebug();
		
		if(attacking) {
			arms.drawDebug();
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
		arms.setSide(side);
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
	
	public boolean isAttacking() {
		return attacking;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public static void load() {
		head = new Texture("character/mechguy/mechguy_head.png");
		body = new Texture("character/mechguy/mechguy_body.png");
	}
}
