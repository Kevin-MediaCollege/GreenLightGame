package ma.greenlightgame.physics;

import java.awt.Rectangle;

import ma.greenlightgame.entity.Entity;

public class Physics {
	public static boolean intersecs(Entity entityA, Entity entityB) {
		final Rectangle boundsA = entityA.getBounds();
		final Rectangle boundsB = entityB.getBounds();
		
		if(boundsA.intersects(boundsB))
			return true;
		
		return false;
	}
}
