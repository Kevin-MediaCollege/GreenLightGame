package ma.greenlightgame.physics;

import java.awt.Rectangle;

import ma.greenlightgame.entityclasses.EntityC;

public class CollisionBounds {

	public double x, y;
	
	EntityC entc;
	
	public Rectangle getBounds(int width, int height){
		return new Rectangle((int)x, (int)y, width, height);
	}
}
