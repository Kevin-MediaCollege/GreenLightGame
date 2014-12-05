package ma.greenlightgame.client.entity;

import java.awt.Rectangle;

public interface IEntity {
	void update(float delta);
	
	void render();
	
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
