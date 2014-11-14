package ma.greenlightgame.client.network;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.network.UDPClient.IUDPClientHandler;
import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;

public class UDPClientHandler implements IUDPClientHandler {
	@Override
	public void onMessageReceived(byte[] message) {
		String[] msg = Utils.bytesToString(message).split(NetworkData.SEPERATOR);
		
		int type = Integer.parseInt(msg[0]);
		
		switch(type) {
		case NetworkMessage.CLIENT_ACCEPTED:
			Client.setClientId(Integer.parseInt(msg[1]));
			break;
		default:
			System.err.println("Client received an unsupported message type: " + msg[0]);
		}
	}
	
	@Override
	public void onUnableToConnect() {
		System.out.println("Unable to connect");
	}
}
