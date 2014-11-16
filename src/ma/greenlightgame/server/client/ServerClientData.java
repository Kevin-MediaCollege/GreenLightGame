package ma.greenlightgame.server.client;

import java.net.InetAddress;

import ma.greenlightgame.common.client.ClientData;

public class ServerClientData extends ClientData {
	private final InetAddress address;
	
	private final int port;
	
	public ServerClientData(int id, InetAddress address, int port) {
		super(id);
		
		this.address = address;
		this.port = port;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
}