package ma.greenlightgame.server.network;

import java.net.InetAddress;

import ma.greenlightgame.server.network.UDPServer.IUDPServerHandler;

public class UDPServerHandler implements IUDPServerHandler {
	@Override
	public void onMesssageReceived(InetAddress address, int port, byte[] message) {
		System.out.println("MESSAGE RECEIVED!");
	}
}
