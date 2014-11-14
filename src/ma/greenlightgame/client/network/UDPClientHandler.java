package ma.greenlightgame.client.network;

import ma.greenlightgame.client.network.UDPClient.IUDPClientHandler;

public class UDPClientHandler implements IUDPClientHandler {
	@Override
	public void onMessageReceived(byte[] message) {
		
	}
	
	@Override
	public void onUnableToConnect() {
		System.out.println("Unable to connect");
	}
}
