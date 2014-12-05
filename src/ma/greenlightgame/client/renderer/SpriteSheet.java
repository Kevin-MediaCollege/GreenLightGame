package ma.greenlightgame.client.renderer;

public class SpriteSheet {
	private Sprite[] sprites;
	
	private int textureId;
	
	private int width;
	private int height;
	
	private int activeSprite;
	
	public SpriteSheet(Texture texture, Sprite[] sprites) {
		textureId = texture.getId();
		width = texture.getWidth();
		height = texture.getHeight();
		
		this.sprites = sprites;
		
		activeSprite = 0;
	}
	
	public void render(int x, int y) {
		Sprite sprite = sprites[activeSprite];
		
		float u = sprite.u;
		float v = height - sprite.v - sprite.h;
		float w = sprite.w;
		float h = sprite.h;
		
		float u1 = u / width;
		float v1 = (v + h) / height;
		
		float u2 = (u + w) / width;
		float v2 = (v + h) / height;
		
		float u3 = (u + w) / width;
		float v3 = v / height;
		
		float u4 = u / width;
		float v4 = v / height;
		
		//System.out.println("rendering: " + activeSprite);
		Renderer.drawSprite(textureId, u1, v1, u2, v2, u3, v3, u4, v4, x, y, sprite.width, sprite.height);
	}
	
	public void nextSprite() {
		activeSprite++;
		//System.out.println(activeSprite);
		if(activeSprite >= sprites.length) {
			activeSprite = 0;
		}
	}
	
	public void prevSprite() {
		activeSprite--;
		
		if(activeSprite <= 0) {
			activeSprite = sprites.length - 1;
		}
	}
	
	public void gotoSprite(int index) {
		activeSprite = index;
		
		if(activeSprite > sprites.length) {
			activeSprite = sprites.length;
		} else if(activeSprite < 0) {
			activeSprite = 0;
		}
	}
	
	public static class Sprite {
		private float u;
		private float v;
		private float w;
		private float h;
		
		private int width;
		private int height;
		
		public Sprite(float u, float v, float w, float h, int width, int height) {
			this.u = u;
			this.v = v;
			this.w = w;
			this.h = h;
			
			this.width = width;
			this.height = height;
		}
		
		public static Sprite[] loadSprites(String fileName) {
			return new Sprite[] {
					new Sprite(0,   0,   300, 75, 300, 75), new Sprite(301, 0,   300, 75, 300, 75), new Sprite(602, 0,   300, 75, 300, 75),
					new Sprite(0,   75,  300, 75, 300, 75), new Sprite(301, 75,  300, 75, 300, 75),	new Sprite(602, 75,  300, 75, 300, 75),
					new Sprite(0,   150, 300, 75, 300, 75), new Sprite(301, 150, 300, 75, 300, 75), new Sprite(602, 150, 300, 75, 300, 75),
					new Sprite(0,   225, 300, 75, 300, 75), new Sprite(301, 225, 300, 75, 300, 75), new Sprite(602, 225, 300, 75, 300, 75),
					new Sprite(0,   300, 300, 75, 300, 75), new Sprite(301, 300, 300, 75, 300, 75), new Sprite(602, 300, 300, 75, 300, 75),
					new Sprite(0,   375, 300, 75, 300, 75), new Sprite(301, 375, 300, 75, 300, 75), new Sprite(602, 375, 300, 75, 300, 75),
					new Sprite(0,   450, 300, 75, 300, 75), new Sprite(301, 450, 300, 75, 300, 75), new Sprite(602, 450, 300, 75, 300, 75),
					new Sprite(0,   525, 300, 75, 300, 75), new Sprite(301, 525, 300, 75, 300, 75), new Sprite(602, 525, 300, 75, 300, 75),
					new Sprite(0,   600, 300, 75, 300, 75), new Sprite(301, 600, 300, 75, 300, 75),	new Sprite(602, 600, 300, 75, 300, 75),
					new Sprite(0,   675, 300, 75, 300, 75), new Sprite(301, 675, 300, 75, 300, 75),	new Sprite(602, 675, 300, 75, 300, 75),
					new Sprite(0,   750, 300, 75, 300, 75)
				};
		}
	}
}
