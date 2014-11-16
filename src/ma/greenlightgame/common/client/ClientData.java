package ma.greenlightgame.common.client;

public class ClientData {
	private final int id;
	
	private float rotation;
	
	private int x;
	private int y;
	
	public ClientData(int id) {
		this.id = id;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
