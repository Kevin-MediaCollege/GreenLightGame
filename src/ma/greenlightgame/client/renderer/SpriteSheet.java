package ma.greenlightgame.client.renderer;

public class SpriteSheet {
	private int textureId;
	
	private int width;
	private int height;
	
	public SpriteSheet(Texture texture) {
		textureId = texture.getId();
		width = texture.getWidth();
		height = texture.getHeight();
	}
	
	public void render(int x, int y, Sprite sprite) {
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
		
		Renderer.drawSprite(textureId, u1, v1, u2, v2, u3, v3, u4, v4, x, y, sprite.width, sprite.height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
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
	}
}
