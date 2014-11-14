package ma.greenlightgame.client.entity;

import java.awt.Rectangle;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.physics.Physics;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;
import ma.greenlightgame.client.utils.DebugDraw;
import ma.greenlightgame.common.utils.Utils;

public class EntityPlayer extends Entity {
	private static final String HEAD_FOLDER = "Character/head/"; 
	private static final String BODY_FOLDER = "Character/body/"; 
	private static final String LEGS_FOLDER = "Character/legs/"; 
	
	private static Texture[] headTextures;
	private static Texture[] bodyTextures;
	private static Texture[] legsTextures;
	
	private final boolean isOwn;
	
	private Texture head;
	private Texture body;
	private Texture legs;
	
	private float velocity;
	
	private int totalWidth;
	private int totalHeight;
	
	private int mouseX;
	private int mouseY;
	
	private int oldX;
	private int oldY;
	
	private boolean isJumping;
	
	public EntityPlayer(boolean isOwn) {
		super(200, 400);
		
		this.velocity = -9;
		this.isOwn = isOwn;
		this.isJumping = false;
		
		head = headTextures[0];
		body = bodyTextures[0];
		legs = legsTextures[0];
		
		totalWidth = body.getWidth();
		totalHeight = (head.getHeight() + body.getHeight() + legs.getHeight());
	}
	
	@Override
	public void update(Input input, float delta) {
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		
		oldX = x;
		oldY = y;
		
		if(isOwn)
			handleInput(input);
		
		y += velocity;
		
		if(velocity > -9)
			velocity -= 0.6f;
		
		if(x != oldX || y != oldY) {
			// TODO: Send new x and y
		}
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
	
	public void checkCollision(Client client) {
		final EntityWall[] walls = client.getEntityManager().getWalls();
		
		for(EntityWall wall : walls) {
			colliding = false;
			wall.colliding = false;
			
			if(Physics.intersecs(this, wall)) {
				colliding = true;
				wall.colliding = true;
				
				velocity = 0;
				isJumping = false;
				break;
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
		
		if(input.getKey(KeyCode.W) || input.getKey(KeyCode.SPACE)) {
			if(!isJumping)
				setVelocity(15);
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (totalWidth / 2), y - (totalHeight / 2), totalWidth, totalHeight);
	}
	
	public void setVelocity(float velocity) {
		this.velocity += velocity;
		
		isJumping = true;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
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
