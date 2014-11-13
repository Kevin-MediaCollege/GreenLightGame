package ma.greenlightgame.entity;

import java.awt.Rectangle;

import ma.greenlightgame.input.Input;
import ma.greenlightgame.renderer.Renderer;

public interface IEntity {
	void update(Input input, float delta);
	
	void render(Renderer renderer);
	
	void drawBounds();
	
	Rectangle getBounds();
	
	int getX();
	int getY();
	
	boolean isColliding();
}
