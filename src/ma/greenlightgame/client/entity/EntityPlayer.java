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
	private static final float TERMINAL_yVelocity = -13f;
	private static final float TERMINAL_xVelocity = 0;
	
	private static Texture[] headTextures;
	private static Texture[] bodyTextures;
	private static Texture[] legsTextures;
	
	private final int id;
	
	private final boolean isOwn;
	
	private List<EntityWall> wallColliders;
	
	private EntityArm arms;
	
	private Texture head;
	private Texture body;
	private Texture legs;
	
	private float xVelocity;
	private float yVelocity;
	
	private float oldRotation;
	
	private int totalWidth;
	private int totalHeight;
	
	private int mouseX;
	private int mouseY;
	
	private int oldX;
	private int oldY;
	
	private boolean isJumping;
	private boolean attacking;
	
	public EntityPlayer(int id, boolean isOwn) {
		super(0, 0);
		
		wallColliders = new ArrayList<EntityWall>();
		
		this.id = id;
		this.yVelocity = TERMINAL_yVelocity;
		this.xVelocity = TERMINAL_xVelocity;
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
		arms.updatePosition(x, y);
		
		if(!isOwn)
			return;
		
		oldRotation = rotation;
		
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		
		oldX = x;
		oldY = y;
		
		handleInput(input, delta);
		
		y += (yVelocity * delta);
		x += (xVelocity * delta);
		
		if(yVelocity > TERMINAL_yVelocity)
			yVelocity -= (GRAVITY * delta);
		
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
	
	public void onHit(EntityPlayer from) {
		// TODO: On hit
		System.out.println(from.getX() + " " + x);
		
		if(from.getX() < x) {
			x += 500;
		} else {
			x -= 500;
		}
	}
	
	public void checkAttackCollision(EntityPlayer[] players) {
		for(EntityPlayer player : players)
			if(player != this)
				if(Physics.intersecs(player, arms) && attacking)
					Client.sendUDP(NetworkMessage.PLAYER_HIT, player.id, UDPClientHandler.getId());
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
	
	public void checkCollision(EntityWall[] walls) {
		for(EntityWall wall : walls) {
			boolean alreadyColliding = wallColliders.contains(wall);
			boolean intersects = Physics.intersecs(this, wall);
			boolean fromTopSide = ((this.y-((this.totalHeight / 8) * 4)) > (wall.y)? true:false);
			boolean fromBottomSide = ((this.y+((this.totalHeight/8) * 4)) < (wall.y)? true: false);
			
			if(intersects && !alreadyColliding) {
				onCollisionEnter(wall);
				if(!fromTopSide && !fromBottomSide)
					xVelocity = 0;
				
				yVelocity = 0;
				//isJumping = false;
				Client.sendUDP(NetworkMessage.PLAYER_COLLISION, UDPClientHandler.getId(), wall.getX(), wall.getY(), true);
			} else if(intersects && alreadyColliding) {

				if(fromTopSide){
					System.out.println((this.y-((this.totalHeight / 8)*5)) + " <- playerY|||wallY -> " + wall.y);
					System.out.println(this.x + " <- playerX|||wallX -> " + wall.x);
					isJumping = false;
					yVelocity = 0;
				}else if(!fromBottomSide){
					xVelocity = 0;
				}
				
			} else if(!intersects && alreadyColliding) {
				onCollisionExit(wall);
				
				Client.sendUDP(NetworkMessage.PLAYER_COLLISION, UDPClientHandler.getId(), wall.getX(), wall.getY(), false);
			}
		}
	}
	
	private void handleInput(Input input, float delta) {
		if(input.isKeyDown(KeyCode.D) && !input.isKeyDown(KeyCode.A)) {
			setXVelocity(MOVE_SPEED * delta);
		} else if(input.isKeyDown(KeyCode.A)) {
			setXVelocity(-MOVE_SPEED * delta);
		}else if(input.isKeyUp(KeyCode.D) )	{
			if(xVelocity >= 0)
				setXVelocity(0);
		}else if(input.isKeyUp(KeyCode.A))	{
			if(xVelocity <= 0)
				setXVelocity(0);
		}
		
		if(input.isKeyDown(KeyCode.E)) {
			onAttackChange(mouseX < x ? -1 : 1, true);
			Client.sendUDP(NetworkMessage.PLAYER_ATTACK, UDPClientHandler.getId(), arms.getSide(), attacking);
		} else if(input.isKeyUp(KeyCode.E)) {
			onAttackChange(0, false);
			Client.sendUDP(NetworkMessage.PLAYER_ATTACK, UDPClientHandler.getId(), arms.getSide(), attacking);
		}
		
		rotation = Utils.angleTo(x, y + body.getHeight(), mouseX, mouseY);
		
		if(input.getKey(KeyCode.W) || input.getKey(KeyCode.SPACE))
			if(!isJumping)
				setYVelocity(JUMP_FORCE * delta);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - (totalWidth / 2), y - (totalHeight / 2), totalWidth, totalHeight);
	}
	
	private void setXVelocity(float velocity) {
		this.xVelocity = velocity;
	}
	
	public void setYVelocity(float velocity) {
		this.yVelocity += velocity;
		
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
