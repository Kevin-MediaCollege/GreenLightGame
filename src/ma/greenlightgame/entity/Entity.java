package ma.greenlightgame.entity;

import java.awt.Color;

import ma.greenlightgame.physics.Physics;

public abstract class Entity implements IEntity {
	protected int x;
	protected int y;
	
	protected boolean colliding;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		
		colliding = false;
	}
	
	@Override
	public void drawBounds() {
		Color color = new Color(0, 1, 0);
		
		if(isColliding())
			color = new Color(1, 0, 0);
		
		Physics.drawBounds(getBounds(), color);
	}
	
	@Override
	public int getX() {
		return y;
	}
	
	@Override
	public int getY() {
		return x;
	}
	
	@Override
	public boolean isColliding() {
		return colliding;
	}
}
