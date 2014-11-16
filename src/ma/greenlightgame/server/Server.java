package ma.greenlightgame.server;

import java.net.InetAddress;

import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.server.client.ClientHandler;
import ma.greenlightgame.server.network.UDPServerHandler;

public class Server {
	private UDPServerHandler udpServerHandler;
	
	private ClientHandler clientHandler;
	
	private boolean isIngame;
	
	public Server() {
		clientHandler = new ClientHandler(this);
		
		udpServerHandler = new UDPServerHandler(clientHandler);
		
		isIngame = false;
	}
	
	public void update(Input input, float delta) {
		if(input.isKeyDown(KeyCode.P)) {
			clientHandler.broadcast(NetworkMessage.GAME_START);
			isIngame = true;
		}
	}
	
	public void destroy() {
		udpServerHandler.destroy();
	}
	
	public void sendUDP(InetAddress address, int port, int type, Object... message) {
		udpServerHandler.sendMessage(address, port, type, message);
	}
	
	public void setIsIngame(boolean isIngame) {
		this.isIngame = isIngame;
	}
	
	public boolean getIsIngame() {
		return isIngame;
	}
}