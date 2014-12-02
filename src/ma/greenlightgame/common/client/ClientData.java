package ma.greenlightgame.common.client;

public class ClientData {
	private final int id;
	
	private float rotation;
	
	private float velocityX;
	private float velocityY;
	
	private int x;
	private int y;
	
	public ClientData(int id) {
		this.id = id;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getID() {
		return id;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
