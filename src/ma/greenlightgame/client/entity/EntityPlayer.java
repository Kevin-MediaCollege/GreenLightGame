package ma.greenlightgame.client.entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.physics.Physics;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;
import ma.greenlightgame.client.utils.DebugDraw;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;

public class EntityPlayer extends Entity {
	private static final String HEAD_FOLDER = "Character/head/"; 
	private static final String BODY_FOLDER = "Character/body/"; 
	private static final String LEGS_FOLDER = "Character/legs/"; 
	
	private static Texture[] headTextures;
	private static Texture[] bodyTextures;
	private static Texture[] legsTextures;
	
	private final boolean isOwn;
	
	private List<EntityWall> wallColliders;
	
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
	
	public EntityPlayer(boolean isOwn) {
		super(200, 400);
		
		wallColliders = new ArrayList<EntityWall>();
		
		this.velocity = -9;
		this.isOwn = isOwn;
		this.isJumping = false;
		this.colliding = false;
		
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
		
		handleInput(input);
		
		y += velocity;
		
		if(velocity > -9)
			velocity -= 0.6f;
		
		if(x != oldX || y != oldY || rotation != oldRotation)
			Client.sendMessage(NetworkMessage.PLAYER_INFO, Client.getOwnId(), x, y, rotation);
	}
	
	@Override
	public void render(Renderer renderer) {
		renderer.drawTexture(head, x, y + head.getHeight(), head.getWidth(), head.getHeight(), rotation);
		renderer.drawTexture(body, x, y, 					body.getWidth(), body.getHeight());
		renderer.drawTexture(legs, x, y - body.getHeight(), legs.getWidth(), legs.getHeight());
	}
	
	@Override
	public void drawDebug() {
		super.drawDebug();
		
		if(isOwn)
			DebugDraw.drawLine(x, y, mouseX, mouseY);
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
				
				Client.sendMessage(NetworkMessage.PLAYER_COLLISION, Client.getOwnId(), wall.getX(), wall.getY(), true);
			} else if(intersects && alreadyColliding) {
				velocity = 0;
				isJumping = false;
			} else if(!intersects && alreadyColliding) {
				onCollisionExit(wall);
				
				Client.sendMessage(NetworkMessage.PLAYER_COLLISION, Client.getOwnId(), wall.getX(), wall.getY(), false);
			}
		}
	}
	
	private void handleInput(Input input) {		
		if(input.getKey(KeyCode.D)) {
			x += 5;
		} else if(input.getKey(KeyCode.A)) {
			x -= 5;
		}
		
		rotation = Utils.angleTo(x, y + body.getHeight(), mouseX, mouseY);
		
		if(input.getKey(KeyCode.W) || input.getKey(KeyCode.SPACE))
			if(!isJumping)
				setVelocity(15);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (totalWidth / 2), y - (totalHeight / 2), totalWidth, totalHeight);
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
