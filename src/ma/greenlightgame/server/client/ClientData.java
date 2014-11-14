package ma.greenlightgame.server.client;

import java.net.InetAddress;

public class ClientData {
	private final InetAddress address;
	
	private final int port;
	private final int id;
	
	private float rotation;
	
	private int x;
	private int y;
	
	public ClientData(int id, InetAddress address, int port) {
		this.id = id;
		
		this.address = address;
		this.port = port;
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
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
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
