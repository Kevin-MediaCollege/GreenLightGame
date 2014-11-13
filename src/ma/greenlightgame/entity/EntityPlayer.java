package ma.greenlightgame.entity;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import java.awt.Rectangle;

import ma.greenlightgame.Game;
import ma.greenlightgame.entity.wall.EntityWall;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.input.Input.KeyCode;
import ma.greenlightgame.physics.Physics;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;

public class EntityPlayer extends Entity {
	private static final String HEAD_FOLDER = "Character/head/"; 
	private static final String BODY_FOLDER = "Character/body/"; 
	private static final String LEGS_FOLDER = "Character/legs/"; 
	
	private static Texture[] headTextures;
	private static Texture[] bodyTextures;
	private static Texture[] legsTextures;
	
	private Texture head;
	private Texture body;
	private Texture legs;
	
	private float velocity;
	
	private int totalWidth;
	private int totalHeight;
	
	private boolean isJumping;
	
	public EntityPlayer() {
		super(200, 400);
		
		this.velocity = -9;
		
		head = headTextures[0];
		body = bodyTextures[0];
		legs = legsTextures[0];
		
		totalWidth = body.getWidth();
		totalHeight = (head.getHeight() + body.getHeight() + legs.getHeight());
	}
	
	@Override
	public void update(Input input, float delta) {
		if(input.getKey(KeyCode.D)) {
			x += 5;
		} else if(input.getKey(KeyCode.A)) {
			x -= 5;
		}
		
		if(input.getKey(KeyCode.W) || input.getKey(KeyCode.SPACE)) {
			if(!isJumping)
				setVelocity(15);
		}
		
		y += velocity;
		
		if(velocity > -9)
			velocity -= 0.6f;
	}
	
	@Override
	public void render(Renderer renderer) {
		renderer.drawTexture(head, x, y + (head.getHeight() + body.getHeight()), 	head.getWidth(), head.getHeight());
		renderer.drawTexture(body, x, y + head.getHeight(), 						body.getWidth(), body.getHeight());
		renderer.drawTexture(legs, x, y, 											legs.getWidth(), legs.getHeight());
	}
	
	public void checkCollision(Game game) {
		final EntityWall[] walls = game.getEntityManager().getWalls();
		
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
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, totalWidth, totalHeight);
	}
	
	public void setVelocity(float velocity) {
		this.velocity += velocity;
		
		isJumping = true;
	}
	
	public boolean isJumping() {
		return isJumping;
	}
	
	public static void load() {
		headTextures = new Texture[] {
				new Texture(HEAD_FOLDER + "H1.jpg", GL_NEAREST)
		};
		
		bodyTextures = new Texture[] {
				new Texture(BODY_FOLDER + "H2.jpg", GL_NEAREST)
		};
		
		legsTextures = new Texture[] {
				new Texture(LEGS_FOLDER + "H3.png", GL_NEAREST)
		};
	}
}
