package ma.greenlightgame.server.network;

import java.net.InetAddress;

import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;
import ma.greenlightgame.server.Server;
import ma.greenlightgame.server.network.UDPServer.IUDPServerHandler;

public class UDPServerHandler implements IUDPServerHandler {
	private int clientId = 0;
	
	@Override
	public void onMesssageReceived(InetAddress address, int port, byte[] message) {		
		String[] msg = Utils.bytesToString(message).split(NetworkData.SEPERATOR);
		
		int type = Integer.parseInt(msg[0]);
		
		switch(type) {
		case NetworkMessage.CLIENT_REQUEST_CONNECT:
			Server.sendMessage(address, port, NetworkMessage.CLIENT_ACCEPTED, clientId);
			clientId++;
			break;
		case NetworkMessage.PLAYER_POSITION:
			break;
		case NetworkMessage.PLAYER_ROTATION:
			break;
		default:
			System.err.println("Server received an unsupported message type: " + type);
			break;
		}
	}
}
