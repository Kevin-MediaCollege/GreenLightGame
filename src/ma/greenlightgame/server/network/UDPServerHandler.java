package ma.greenlightgame.server.network;

import java.io.IOException;
import java.net.InetAddress;

import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;
import ma.greenlightgame.server.client.ClientHandler;
import ma.greenlightgame.server.network.UDPServer.IUDPServerHandler;

public class UDPServerHandler implements IUDPServerHandler {
	private ClientHandler clientHandler;
	
	private UDPServer udpServer;
	
	public UDPServerHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
		
		udpServer = new UDPServer(Config.getInt(Config.SERVER_PORT), this);
	}
	
	@Override
	public void onMesssageReceived(InetAddress address, int port, byte[] message) {		
		String[] msg = Utils.bytesToString(message).split(NetworkData.SEPERATOR);
		
		int type = toInt(msg[0]);
		
		switch(type) {
		case NetworkMessage.CLIENT_REQUEST_CONNECT:
			clientHandler.onJoinRequested(address, port);
			break;
		case NetworkMessage.PLAYER_INFO:
			clientHandler.onPlayerUpdate(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]), toFloat(msg[4]));
			break;
		case NetworkMessage.PLAYER_COLLISION:
			clientHandler.broadcast(NetworkMessage.PLAYER_COLLISION, msg[1], msg[2], msg[3], msg[4]);
			break;
		default:
			System.err.println("Server received an unsupported message type: " + type);
			break;
		}
	}
	
	public void destroy() {
		udpServer.close();
	}
	
	public void sendMessage(InetAddress address, int port, int type, Object... message) {
		String msg = Integer.toString(type) + NetworkData.SEPERATOR;
		
		for(int i = 0; i < message.length; i++)
			msg += (message[i] + NetworkData.SEPERATOR);
		
		try {
			udpServer.send(address, port, msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private float toFloat(String string) {
		return Float.parseFloat(string);
	}
	
	private int toInt(String string) {
		return Integer.parseInt(string);
	}
	
	private boolean toBool(String string) {
		return Boolean.parseBoolean(string);
	}
}
