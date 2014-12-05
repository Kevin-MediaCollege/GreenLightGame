package ma.greenlightgame.client.entity.wall;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.Entity;
import ma.greenlightgame.client.renderer.SpriteSheet;
import ma.greenlightgame.client.renderer.SpriteSheet.Sprite;

public class EntityWall extends Entity {
	private static final int SPRITE_UPDATE_TIME = 2;
	
	private static Sprite[] sprites;
	
	private List<Entity> colliders;
	
	private SpriteSheet spriteSheet;
	
	private int width;
	private int height;
	
	private int currentSprite;
	private int timeToNextSprite;
	
	public EntityWall(int x, int y, int width, int height, SpriteSheet spriteSheet) {
		super();
		
		sprites = new Sprite[] {
			new Sprite(0,   0,   300, 75, width, height), new Sprite(301, 0,   300, 75, width, height), new Sprite(602, 0,   300, 75, width, height),
			new Sprite(0,   75,  300, 75, width, height), new Sprite(301, 75,  300, 75, width, height),	new Sprite(602, 75,  300, 75, width, height),
			new Sprite(0,   150, 300, 75, width, height), new Sprite(301, 150, 300, 75, width, height), new Sprite(602, 150, 300, 75, width, height),
			new Sprite(0,   225, 300, 75, width, height), new Sprite(301, 225, 300, 75, width, height), new Sprite(602, 225, 300, 75, width, height),
			new Sprite(0,   300, 300, 75, width, height), new Sprite(301, 300, 300, 75, width, height), new Sprite(602, 300, 300, 75, width, height),
			new Sprite(0,   375, 300, 75, width, height), new Sprite(301, 375, 300, 75, width, height), new Sprite(602, 375, 300, 75, width, height),
			new Sprite(0,   450, 300, 75, width, height), new Sprite(301, 450, 300, 75, width, height), new Sprite(602, 450, 300, 75, width, height),
			new Sprite(0,   525, 300, 75, width, height), new Sprite(301, 525, 300, 75, width, height), new Sprite(602, 525, 300, 75, width, height),
			new Sprite(0,   600, 300, 75, width, height), new Sprite(301, 600, 300, 75, width, height),	new Sprite(602, 600, 300, 75, width, height),
			new Sprite(0,   675, 300, 75, width, height), new Sprite(301, 675, 300, 75, width, height),	new Sprite(602, 675, 300, 75, width, height),
			new Sprite(0,   750, 300, 75, width, height)
		};
		
		colliders = new ArrayList<Entity>();
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.spriteSheet = spriteSheet;
		
		currentSprite = 0;
		timeToNextSprite = SPRITE_UPDATE_TIME;
	}
	
	@Override
	public void update(float delta) {		
		if(timeToNextSprite <= 0) {
			timeToNextSprite = SPRITE_UPDATE_TIME;
			currentSprite++;
			
			System.out.println(currentSprite + " / " + sprites.length);
			
			if(currentSprite >= sprites.length)
				currentSprite = 0;
		}
		
		timeToNextSprite--;
	}
	
	@Override
	public void render() {
		spriteSheet.render(x, y, sprites[currentSprite]);
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - width / 2, y - height / 2, width, height);
	}
	
	public void onCollisionEnter(Entity entity) {
		if(colliders.contains(entity))
			return;
		
		colliders.add(entity);
		colliding = true;
	}
	
	public void onCollisionExit(Entity entity) {
		if(!colliders.contains(entity))
			return;
		
		colliders.remove(entity);
		
		if(colliders.isEmpty()) {
			colliding = false;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
