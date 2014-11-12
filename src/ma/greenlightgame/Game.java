package ma.greenlightgame;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;

public class Game {
	private Texture t;
	
	public Game() {
		t = new Texture("test.png", GL_NEAREST);
		
		new Character();
	}
	
	public void update() {
		
	}
	
	public void render(final Renderer renderer) {
		renderer.drawTexture(t, 512, 128, 256, 256);
		renderer.drawSprite(t, 0, 0, 51, 51, 100, 100, 256, 256);
	}
}
