package ma.greenlightgame.client.entity.player;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class EntityPlayer extends Entity {
	private static final String HEAD_FOLDER = "character/head/";
	private static final String BODY_FOLDER = "character/body/";
	private static final String LEGS_FOLDER = "character/legs/";
	
	private static final float GRAVITY = 0.9f;
	private static final float TERMINAL_VELOCITY = -13f;
	
	private static Texture[] headTextures;
	private static Texture[] bodyTextures;
	private static Texture[] legsTextures;
	
	protected List<EntityWall> wallColliders;
	
	protected EntityArm arms;
	
	protected Texture head;
	protected Texture body;
	protected Texture legs;
	
	protected float velocityX;
	protected float velocityY;
	
	protected int totalWidth;
	protected int totalHeight;
	
	protected boolean attacking;
	
	protected int id;
	
	public EntityPlayer(int id) {
		super();
		
		this.id = id;
		
		wallColliders = new ArrayList<EntityWall>();
		
		this.velocityY = TERMINAL_VELOCITY;
		this.velocityX = 0;
		this.colliding = false;
		
		arms = new EntityArm(this);
		
		head = headTextures[0];
		body = bodyTextures[0];
		legs = legsTextures[0];
		
		totalWidth = body.getWidth();
		totalHeight = (head.getHeight() + body.getHeight() + legs.getHeight());
	}
	
	@Override
	public void update(float delta) {
		arms.updatePosition(x, y);
		
		y += (velocityY * delta);
		x += (velocityX * delta);
		
		if(velocityY > TERMINAL_VELOCITY)
			velocityY -= (GRAVITY * delta);
	}
	
	@Override
	public void render(Renderer renderer) {
		renderer.drawTexture(head.getId(), x, y + head.getHeight(), head.getWidth(), head.getHeight(), rotation);
		renderer.drawTexture(body.getId(), x, y, body.getWidth(), body.getHeight());
		renderer.drawTexture(legs.getId(), x, y - body.getHeight(), legs.getWidth(), legs.getHeight());
		
		arms.render(renderer);
	}
	
	@Override
	public void drawDebug() {
		super.drawDebug();
		
		arms.drawDebug();
	}
	
	public void onDead() {
		// TODO: On dead
	}
	
	public void onHit(EntityPlayer from) {
		// TODO: On hit
		
		if(from.getX() < x) {
			x += 500;
		} else {
			x -= 500;
		}
	}
	
	public void onCollisionEnter(EntityWall wall) {
		wallColliders.add(wall);
		wall.onCollisionEnter(this);
		
		colliding = true;
	}
	
	public void onCollisionExit(EntityWall wall) {
		wallColliders.remove(wall);
		wall.onCollisionExit(this);
		
		if(wallColliders.isEmpty())
			colliding = false;
	}
	
	public void onAttackChange(int side, boolean attacking) {
		this.attacking = attacking;
		arms.setSide(side);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (totalWidth / 2), y - (totalHeight / 2), totalWidth, totalHeight);
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
	
	public static void load() {
		headTextures = new Texture[] {new Texture(HEAD_FOLDER + "H1.jpg")};
		
		bodyTextures = new Texture[] {new Texture(BODY_FOLDER + "H2.jpg")};
		
		legsTextures = new Texture[] {new Texture(LEGS_FOLDER + "H3.png")};
	}
}
