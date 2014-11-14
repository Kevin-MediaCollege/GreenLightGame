package ma.greenlightgame.server;

import java.io.IOException;
import java.net.InetAddress;

import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.server.network.UDPServer;
import ma.greenlightgame.server.network.UDPServerHandler;

public class Server {
	private static Server instance;
	
	private UDPServer udpConnection;
	
	public Server() {
		instance = this;
	}
	
	public void update(Input input, float delta) {
		if(input.isKeyDown(KeyCode.G)) {
			if(udpConnection == null) {
				udpConnection = new UDPServer(Config.getInt(Config.SERVER_PORT), new UDPServerHandler());
			}
		}
	}
	
	public void destroy() {
		if(udpConnection != null)
			udpConnection.close();
	}
	
	public static  void sendMessage(InetAddress address, int port, int type, Object... message) {
		if(instance.udpConnection == null)
			return;
		
		String msg = Integer.toString(type) + NetworkData.SEPERATOR;
		
		for(int i = 0; i < message.length; i++)
			msg += (message[i] + NetworkData.SEPERATOR);
		
		try {
			instance.udpConnection.send(address, port, msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
