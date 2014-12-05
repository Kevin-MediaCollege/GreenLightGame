package ma.greenlightgame.client.entity.platform;

import ma.greenlightgame.client.renderer.SpriteSheet;
import ma.greenlightgame.client.renderer.SpriteSheet.Sprite;
import ma.greenlightgame.client.renderer.Texture;

public class EntityPlatformStatic extends EntityPlatform {
	private static final String SPRITE_SHEET = "tiles/platform_level_1";
	
	private static final int SPRITE_SHEET_UPDATE_TIMER = 1;
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 75;
	
	private static Texture spriteSheetTexture;
	private static Sprite[] sprites;
		
	private int spriteSheetUpdateTimer;
	
	public EntityPlatformStatic(int x, int y) {
		super(x, y, WIDTH, HEIGHT);
		
		spriteSheet = new SpriteSheet(spriteSheetTexture, sprites);
		
		spriteSheetUpdateTimer = SPRITE_SHEET_UPDATE_TIMER;
	}

	@Override
	public void update(float delta) {
		if(spriteSheetUpdateTimer <= 0) {
			spriteSheet.nextSprite();
			
			spriteSheetUpdateTimer = SPRITE_SHEET_UPDATE_TIMER;
		}
		
		spriteSheetUpdateTimer--;
	}
	
	public static void load() {
		spriteSheetTexture = new Texture(SPRITE_SHEET + ".png");
		sprites = Sprite.loadSprites(SPRITE_SHEET	+ ".lua");
	}
}
