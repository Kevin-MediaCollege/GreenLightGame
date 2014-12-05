package ma.greenlightgame.client.renderer;

public class SpriteSheet {
	private int textureId;
	
	public SpriteSheet(int textureId) {
		this.textureId = textureId;
	}
	
	public void render(int x, int y, Sprite sprite) {
		float u1 = sprite.u;
		float v1 = sprite.v + sprite.h;
		
		float u2 = sprite.u + sprite.w;
		float v2 = sprite.v + sprite.h;
		
		float u3 = sprite.u + sprite.w;
		float v3 = sprite.v;
		
		float u4 = sprite.u;
		float v4 = sprite.v;
		
		Renderer.drawSprite(textureId, u1, v1, u2, v2, u3, v3, u4, v4, x, y, sprite.width,
				sprite.height);
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
