package ma.greenlightgame.common.screen;

import ma.greenlightgame.client.renderer.Renderer;

public interface Screen {
	void update();
	
	void render(Renderer renderer);
	
	void drawDebug();
}