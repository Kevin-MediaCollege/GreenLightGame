package ma.greenlightgame.client.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.Arm.EntityArm;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.network.UDPClientHandler;
import ma.greenlightgame.client.physics.Physics;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;
import ma.greenlightgame.client.utils.DebugDraw;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;

public class EntityPlayer extends Entity {
	private static final String HEAD_FOLDER = "character/head/";
	private static final String BODY_FOLDER = "character/body/";
	private static final String LEGS_FOLDER = "character/legs/";
	
	private static final float MOVE_SPEED = 8;
	private static final float JUMP_FORCE = 23;
	private static final float GRAVITY = 0.9f;
	private static final float TERMINAL_VELOCITY = -13f;
	
	private static Texture[] headTextures;
	private static Texture[] bodyTextures;
	private static Texture[] legsTextures;
	
	private final boolean isOwn;
	
	private List<EntityWall> wallColliders;
	
	private EntityArm arms;
	
	private Texture head;
	private Texture body;
	private Texture legs;
	
	private float velocity;
	
	private float oldRotation;
	
	private int totalWidth;
	private int totalHeight;
	
	private int mouseX;
	private int mouseY;
	
	private int oldX;
	private int oldY;
	
	private boolean isJumping;
	private boolean attacking;
	
	public EntityPlayer(boolean isOwn) {
		super(0, 0);
		
		wallColliders = new ArrayList<EntityWall>();
		
		this.velocity = TERMINAL_VELOCITY;
		this.isOwn = isOwn;
		this.isJumping = false;
		this.colliding = false;
		
		arms = new EntityArm(this);
		
		head = headTextures[0];
		body = bodyTextures[0];
		legs = legsTextures[0];
		
		totalWidth = body.getWidth();
		totalHeight = (head.getHeight() + body.getHeight() + legs.getHeight());
	}
	
	@Override
	public void update(Input input, float delta) {
		if(!isOwn)
			return;
		
		oldRotation = rotation;
		
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		
		oldX = x;
		oldY = y;
		
		handleInput(input, delta);
		
		y += (velocity * delta);
		
		if(velocity > TERMINAL_VELOCITY)
			velocity -= (GRAVITY * delta);
		
		arms.updatePosition(x, y);
		
		if(y < 0)
			onDead();
		
		if(x != oldX || y != oldY || rotation != oldRotation)
			Client.sendUDP(NetworkMessage.PLAYER_INFO, UDPClientHandler.getId(), x, y, rotation);
	}
	
	@Override
	public void render(Renderer renderer) {
		renderer.drawTexture(head, x, y + head.getHeight(), head.getWidth(), head.getHeight(), rotation);
		renderer.drawTexture(body, x, y, body.getWidth(), body.getHeight());
		renderer.drawTexture(legs, x, y - body.getHeight(), legs.getWidth(), legs.getHeight());
		
		arms.render(renderer);
	}
	
	@Override
	public void drawDebug() {
		super.drawDebug();
		
		if(isOwn)
			DebugDraw.drawLine(x, y, mouseX, mouseY);
		
		arms.drawDebug();
	}
	
	public void onDead() {
		// TODO: On dead
	}
	
	public void onHit() {
		// TODO: On hit
		
		x += 500;
	}
	
	public void checkAttackCollision(EntityPlayer[] players) {
		for(EntityPlayer player : players)
			if(player != this)
				if(Physics.intersecs(player, arms) && attacking)
					player.onHit();
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
	
	public void checkCollision(EntityWall[] walls) {
		for(EntityWall wall : walls) {
			boolean alreadyColliding = wallColliders.contains(wall);
			boolean intersects = Physics.intersecs(this, wall);
			
			if(intersects && !alreadyColliding) {
				onCollisionEnter(wall);
				
				velocity = 0;
				isJumping = false;
				
				Client.sendUDP(NetworkMessage.PLAYER_COLLISION, UDPClientHandler.getId(), wall.getX(), wall.getY(), true);
			} else if(intersects && alreadyColliding) {
				velocity = 0;
				isJumping = false;
			} else if(!intersects && alreadyColliding) {
				onCollisionExit(wall);
				
				Client.sendUDP(NetworkMessage.PLAYER_COLLISION, UDPClientHandler.getId(), wall.getX(), wall.getY(), false);
			}
		}
	}
	
	private void handleInput(Input input, float delta) {
		if(input.getKey(KeyCode.D)) {
			move(1, (int)(MOVE_SPEED * delta));
		} else if(input.getKey(KeyCode.A)) {
			move(-1, (int)(MOVE_SPEED * delta));
		}
		
		if(input.isKeyDown(KeyCode.E)) {
			attacking = true;
			arms.setSide(mouseX < x ? -1 : 1);
		} else if(input.isKeyUp(KeyCode.E)) {
			attacking = false;
			arms.setSide(0);
		}
		
		rotation = Utils.angleTo(x, y + body.getHeight(), mouseX, mouseY);
		
		if(input.getKey(KeyCode.W) || input.getKey(KeyCode.SPACE))
			if(!isJumping)
				setVelocity(JUMP_FORCE * delta);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (totalWidth / 2), y - (totalHeight / 2), totalWidth, totalHeight);
	}
	
	private void move(int direction, int amt) {
		x += (amt * direction);
	}
	
	public void setVelocity(float velocity) {
		this.velocity += velocity;
		
		isJumping = true;
	}
	
	public boolean isOwn() {
		return isOwn;
	}
	
	public boolean isJumping() {
		return isJumping;
	}
	
	public boolean isAttacking() {
		return attacking;
	}
	
	public static void load() {
		headTextures = new Texture[] {
				new Texture(HEAD_FOLDER + "H1.jpg")
			};
		
		bodyTextures = new Texture[] {
				new Texture(BODY_FOLDER + "H2.jpg")
			};
		
		legsTextures = new Texture[] {
				new Texture(LEGS_FOLDER + "H3.png")
			};
	}
}
