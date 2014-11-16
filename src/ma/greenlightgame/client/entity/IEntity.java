package ma.greenlightgame.client.entity;

import java.awt.Rectangle;

import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.renderer.Renderer;

public interface IEntity {
	void update(Input input, float delta);
	void render(Renderer renderer);
	void drawDebug();
	
	void setRotation(float rotation);
	void setX(int x);
	void setY(int y);
	
	Rectangle getBounds();
	
	float getRotation();
	
	int getX();
	int getY();
	
	boolean isColliding();
}
