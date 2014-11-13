package ma.greenlightgame.physics;

import java.awt.Rectangle;

public class Collider {
	private int x;
	private int y;
	
	public Collider(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getBounds(int width, int height) {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}